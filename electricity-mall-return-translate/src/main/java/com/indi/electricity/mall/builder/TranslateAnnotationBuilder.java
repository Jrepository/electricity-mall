package com.indi.electricity.mall.builder;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.indi.electricity.mall.annotation.TranslateField;
import com.indi.electricity.mall.annotation.TranslateFieldConst;
import com.indi.electricity.mall.annotation.TranslateFields;
import com.indi.electricity.mall.handler.IQueryHandler;
import com.indi.electricity.mall.utils.ReflectionUtil;
import com.indi.electricity.mall.utils.ThreadPoolUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.indi.electricity.mall.annotation.TranslateFieldConst.*;

@Component
@Log4j2
public class TranslateAnnotationBuilder {

    @Autowired
    IQueryHandler queryHandler;

    public void translateData(Collection<?> collections, Integer depth) {
        if (depth-- > 0) {
            Object oneData = collections.toArray()[0];
            Class<?> clazz = oneData.getClass();
            List<Field> fields = ReflectionUtil.getAllField(clazz);
            Integer finalDepth = depth;
            fields.forEach(field -> {
                translateField(clazz, field, collections, finalDepth);
            });
        }
    }


    private void translateField(Class<?> clazz, Field field, Collection<?> collections, Integer depth) {
        if (!field.isAnnotationPresent(TranslateFields.class)
                && !field.isAnnotationPresent(TranslateField.class)) {
            return;
        }
        List<Object> searchValueList = getSearchValue(field, collections);
        if (CollectionUtils.isEmpty(searchValueList)) {
            return;
        }
        TranslateFields translateFields = field.getAnnotation(TranslateFields.class);
        TranslateField[] translateFieldArr;
        if (translateFields == null) {
            TranslateField annotation = field.getAnnotation(TranslateField.class);
            translateFieldArr = new TranslateField[]{annotation};
        } else {
            translateFieldArr = translateFields.value();
        }


        Arrays.stream(translateFieldArr).forEach(oneData -> {
            try {
//                Object instance = oneData.getClass().newInstance();
                // 通过TranslateFields设置翻译信息时
                InvocationHandler h = Proxy.getInvocationHandler(oneData);
                // 获取 AnnotationInvocationHandler 的 memberValues 字段
                Field hField = h.getClass().getDeclaredField("memberValues");
                // 因为这个字段事 private final 修饰，所以要打开权限
                hField.setAccessible(true);
                // 获取 memberValues
                Map memberValues = (Map) hField.get(h);
                // 修改注解的属性值
                if (translateFields != null && oneData.searchClass() == Void.class
                        && translateFields.searchClass() != Void.class) {
                    memberValues.put("searchClass", translateFields.searchClass());
                }
                if (translateFields != null && TRANSLATE_FIELD_SEARCH_KEY.equals(oneData.searchKey())
                        && !TRANSLATE_FIELD_SEARCH_KEY.equals(translateFields.searchKey())) {
                    memberValues.put("searchKey", translateFields.searchKey());
                }
                if (translateFields != null && isTrue(oneData.searchMethod(), translateFields.searchMethod())) {
                    memberValues.put("searchMethod", translateFields.searchMethod());
                }
                if (translateFields != null && isTrue(oneData.sourceField(), translateFields.sourceField())) {
                    memberValues.put("sourceField", translateFields.sourceField());
                }
                if (translateFields != null && isTrue(oneData.ds(), translateFields.ds())) {
                    memberValues.put("ds", translateFields.ds());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


        Map<String, List<TranslateField>> translateFieldMap = Stream.of(translateFieldArr).collect(Collectors.groupingBy(item -> {
            if (StringUtils.isEmpty(item.searchMethod())) {// 根据指定方法转译
                return item.ds() + "#" + item.searchClass() + "#" + item.searchMethod();
            } else { //根据枚举或者实体类转译
                return item.ds() + "#" + item.searchClass() + "#" + item.searchKey();
            }
        }));
//        String ds = DynamicDataSourceContextHolder.peek();
        List<Runnable> runnables = new ArrayList<>();
        translateFieldMap.forEach((key, annotations) -> {
            runnables.add(() -> {
                String ds = key.split("#")[0];
                if (TranslateFieldConst.TRANSLATE_FIELD_DS_DEFAULT.equals(ds)) {
                    translateField(clazz, field, collections, annotations, searchValueList, depth);
                } else {
                    DynamicDataSourceContextHolder.push(ds);
                    translateField(clazz, field, collections, annotations, searchValueList, depth);
                    DynamicDataSourceContextHolder.poll();
//                    DynamicDataSourceContextHolder.clear();
                }

            });
        });
        List<CompletableFuture> futures = ThreadPoolUtil.runAsync(runnables);
        ThreadPoolUtil.allOf(futures);
    }

    private boolean isTrue(String field1, String field2) {
        return StringUtils.isEmpty(field1) && StringUtils.isNotEmpty(field2);
    }

    /**
     * 翻译字段
     *
     * @param clazz
     * @param field
     * @param dataList
     * @param annotationList  annotationList中searchClass、searchKey、searchMethod相同
     * @param searchValueList
     * @param depth
     */
    private void translateField(Class<?> clazz, Field field, Collection<?> dataList, List<TranslateField> annotationList, List<Object> searchValueList, Integer depth) {
        TranslateField translateField = annotationList.get(0);
        long count = annotationList.stream().filter(item -> StringUtils.isEmpty(item.sourceField())).count();
        List<String> sourceFieldList = new ArrayList<>();
        if (count == 0) {
            sourceFieldList = annotationList.stream().map(TranslateField::sourceField).collect(Collectors.toList());
        }

        List rstList;
        if (StringUtils.isEmpty(translateField.searchMethod())) {
            rstList = queryHandler.queryData(translateField.searchClass(), translateField.searchKey(), sourceFieldList, searchValueList);
        } else {
            rstList = queryHandler.queryData(translateField.searchClass(), translateField.searchMethod(), searchValueList);
        }

        if (CollectionUtils.isEmpty(rstList)) {
            return;
        }
//        Map<Object, List<Object>> rstMap;
//        if (StringUtils.isEmpty(translateField.sourceField())) {// 没有设置sourceField，将查询的结果映射成<searchKey对应的val,List>
//            rstMap = (Map<Object, List<Object>>) rstList.stream().collect(
//                    Collectors.groupingBy(item -> ReflectionUtil.getFieldValue(translateField.searchKey(), item)));
//        } else {// 设置sourceField，将查询的结果映射成<searchKey对应的val,List<sourceField对应的val>>
//            rstMap = (Map<Object, List<Object>>) rstList.stream().collect(
//                    Collectors.groupingBy(item -> ReflectionUtil.getFieldValue(translateField.searchKey(), item)
//                            , Collectors.mapping(i -> ReflectionUtil.getFieldValue(translateField.sourceField(), i), Collectors.toList())));
//        }
        Map<Object, List<Object>> rstMap = (Map<Object, List<Object>>) rstList.stream().collect(
                Collectors.groupingBy(item -> ReflectionUtil.getFieldValue(item, translateField.searchKey())));

        // 没有设置targetField，不需要转译
        // 此处的annotationList对于的target可能不同，所以分组处理
        Map<String, List<TranslateField>> targetFieldMap = annotationList.stream()
                .filter(oneData -> StringUtils.isNotBlank(oneData.targetField()))
                .collect(Collectors.groupingBy(item -> item.sourceField() + "#" + item.targetField()));

        targetFieldMap.keySet().forEach(key -> {
            String[] split = key.split("#");
            Field targetField = ReflectionUtil.getField(clazz, split[1]);
            Class<?> targetFieldClass = targetField.getType();

            String sourceField = split[0];
            dataList.forEach(oneData -> {
                Object fieldValue = ReflectionUtil.getFieldValue(oneData, field);
                if (!rstMap.containsKey(fieldValue)) {
                    return;
                }
                List<Object> valList = rstMap.get(fieldValue);
                //sourceField is empty,将返回值直接赋给targetField
                // 如：valList=[{1,"name1",...},{2,"name2",...}],sourceField=""
                // targetField =[{1,"name1",...},{2,"name2",...}]
                if (StringUtils.isEmpty(sourceField)) {
                    translateData(valList, depth);
                } else {
                    //sourceField not empty,将返回值中sourceField对应的数据赋给targetField
                    // 如：valList=[{1,"name1",...},{2,"name2",...}],sourceField=name
                    // targetField = ["name1","name2"]）
                    valList = valList.stream()
                            .map(item -> ReflectionUtil.getFieldValue(item, sourceField))
                            .collect(Collectors.toList());
                }
                //A.isAssignableFrom(B)
                //isAssignableFrom是用来判断子类(B)和父类(A)的关系的，或者接口的实现类(B)和接口(A)的关系的
                if (Collection.class.isAssignableFrom(targetFieldClass)) {
                    ReflectionUtil.setFieldValue(oneData, targetField, valList);
                } else {
                    ReflectionUtil.setFieldValue(oneData, targetField, valList.get(0));
                }
            });

        });
    }

    /**
     * 获取查询条件
     *
     * @param field
     * @param collections
     * @return
     */
    private List<Object> getSearchValue(Field field, Collection<?> collections) {
        List<Object> valueList = new ArrayList<>();
        collections.forEach(oneData -> {
            valueList.add(ReflectionUtil.getFieldValue(oneData, field));
        });
        return valueList;
    }
}
