package com.indi.electricity.mall;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import com.sun.tracing.dtrace.ModuleName;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class GeneratorNew {


    public static final Map<String, String> CustomFileMap = new HashMap<String, String>() {{
        put("test.txt", "/templates/test.vm");
    }};

    // 设置自定义属性
    public static final Map<String, Object> CustomMap = new HashMap<String, Object>() {{
        put("abc", 123);
    }};

    // 用户名 TODO:改为自己的名字
    private static final String AUTHOR = "ph";

    //模块名称  TODO:修改成自己的项目名
    private static final String ProjectName = "electricity-mall-sharding-jdbc";

    //模块名称  TODO:修改成自己的模块名
    private static final String ModuleName = "order";

    //基本包  TODO:修改成自己的包
    private static final String ParentPackage = "com.indi.electricity.mall";


    // 设置需要生成的表名 TODO:修改成自己的表名
    private static final String[] TableName = new String[]{"order"};


    //过滤表前缀,result: t_simple -> simple  TODO:根据实际情况修改
//    private static final String[] TablePrefix = new String[]{"t_", "c_"};
    private static final String[] TablePrefix = new String[]{};

    //过滤字段后缀 result: deleted_flag -> deleted  TODO:根据实际情况修改
    private static final String[] FieldSuffix = new String[]{};
//    private static final String[] FieldSuffix = new String[]{"_flag"};

    // 无需生成的字段 TODO:根据实际情况修改
//    private static final String[] IgnoreColumns = new String[]{"id","creator","create_time","update_time","remark"};
    private static final String[] IgnoreColumns = new String[]{};

    private static final String PROPERTY = System.getProperty("user.dir") + "/" + ProjectName;

    // mapper,service,controller输出路径 TODO:可根据实际情况修改
    private static final String OutputDir = PROPERTY + "/src/main/java";

    // *mapper.Xml输出路径 TODO:可根据实际情况修改
    private static final String MapperXmlPath = PROPERTY + "/" + "/src/main/resources/mapper/" + ModuleName;

    public static void main(String[] args) {
        AutoGenerator generator = new AutoGenerator(DATA_SOURCE_CONFIG);
        generator
                // 1. 全局配置
                .global(globalConfig().build())
                // 2. 包配置
                .packageInfo(packageConfig().build())
                // 3. 策略配置
                .strategy(strategyConfig()
                        .addInclude(TableName)
                        .addTablePrefix(TablePrefix)
                        .addFieldSuffix(FieldSuffix)
                        .entityBuilder()
//                        .superClass(BaseEntity.class)
                        .addIgnoreColumns(IgnoreColumns) // 不生成字段
                        .enableLombok()
                        .enableTableFieldAnnotation()//实体类字段注解
                        //填充字段设置,result: 新增@TableField(value = "xxx", fill = FieldFill.xxx)注解,基于数据库字段填充,基于模型属性填充
                        .addTableFills(new Column("create_time", FieldFill.INSERT))
                        .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
                        // 逻辑删除字段设置,result: 新增@TableLogic注解,忽略字段设置,result: 不生成,基于数据库字段,基于模型属性
                        .logicDeleteColumnName("deleted").logicDeletePropertyName("deleteFlag")
                        .versionColumnName("version").versionPropertyName("version")// 乐观锁字段,result: 新增@Version注解,基于数据库字段, 基于模型属性
                        .formatFileName("%sEntity")
                        .mapperBuilder()
                        .enableMapperAnnotation()//开启mapper注解
                        .formatMapperFileName("%sMapper").formatXmlFileName("%sMapper")
                        .controllerBuilder().formatFileName("%sController")
                        .enableRestStyle()//开启restcontroller
                        .serviceBuilder()
                        .formatServiceFileName("I%sService").formatServiceImplFileName("%sServiceImpl")
                        .build())
//                .injection(injectionConfig().customMap(CustomMap).customFile(CustomFileMap).build())
                // 自定义模板生成的文件路径

                // 4. 模板配置
//                .template(templateConfig().build())
                // 模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
                //.templateEngine(new BeetlTemplateEngine())
//                .templateEngine(new FreemarkerTemplateEngine())
                // 5. 执行
//                .execute(new FreemarkerTemplateEngine());
                .execute();
    }


    /**
     * 执行初始化数据库脚本
     */
    public static void before() throws SQLException {
        Connection conn = DATA_SOURCE_CONFIG.getConn();
        InputStream inputStream = GeneratorNew.class.getResourceAsStream("/sql/init.sql");
        ScriptRunner scriptRunner = new ScriptRunner(conn);
        scriptRunner.setAutoCommit(true);
        scriptRunner.runScript(new InputStreamReader(inputStream));
        conn.close();
    }

    /**
     * 数据源配置
     */
    private static final DataSourceConfig DATA_SOURCE_CONFIG = new DataSourceConfig
            .Builder(Const.URL, Const.USER_NAME, Const.PASSWORD).build();

    /**
     * 策略配置
     */
    private static StrategyConfig.Builder strategyConfig() {
        return new StrategyConfig.Builder();


    }

    /**
     * 全局配置
     */
    private static GlobalConfig.Builder globalConfig() {
        return new GlobalConfig.Builder()
                .author(AUTHOR) // 设置作者
                .disableOpenDir()//禁止打开输出目录
                .enableSwagger() // 开启 swagger 模式
                .fileOverride() // 覆盖已生成文件
                .outputDir(OutputDir); // 指定输出目录
    }


    /**
     * 包配置
     */
    private static PackageConfig.Builder packageConfig() {
        return new PackageConfig
                .Builder()
                .parent(ParentPackage) // 设置父
                .moduleName(ModuleName)
                .entity("entity")
                .service("service")
                .serviceImpl("service.impl")
                .mapper("mapper")
                .xml("mapper")
                .controller("controller")
                //.other("other")
                .pathInfo(Collections.singletonMap(OutputFile.mapperXml, MapperXmlPath));
    }

    /**
     * 模板配置
     */
    private static TemplateConfig.Builder templateConfig() {
        return new TemplateConfig.Builder()
                .entity("/templates/entity.java")
                .service("/templates/service.java")
                .serviceImpl("/templates/serviceImpl.java")
                .mapper("/templates/mapper.java")
                .mapperXml("/templates/mapper.xml")
                .controller("/templates/controller.java");
    }

    /**
     * 注入配置
     */
    private static InjectionConfig.Builder injectionConfig() {
        // 测试自定义输出文件之前注入操作，该操作再执行生成代码前 debug 查看
        return new InjectionConfig.Builder().beforeOutputFile((tableInfo, objectMap) -> {
            System.out.println("tableInfo: " + tableInfo.getEntityName() + " objectMap: " + objectMap.size());
        });
    }


}
