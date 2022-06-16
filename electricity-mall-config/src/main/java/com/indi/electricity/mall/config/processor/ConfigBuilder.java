package com.indi.electricity.mall.config.processor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.indi.electricity.mall.config.service.ISystemConfigService;
import com.indi.electricity.mall.vo.KeyValueVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ConfigBuilder {

    private final Logger logger = LoggerFactory.getLogger(ConfigBuilder.class);

    @Autowired
    private ISystemConfigService configService;

    @Autowired
    private ConfigParser configParser;

    public RstConf<?> build(String path, String defaultValue, boolean findChild,  JavaType javaType, Field field) {
        String valueStr;
        if (!findChild) {
            valueStr = configService.tryToGetValue(path);
        } else {
            List<KeyValueVo> children = configService.tryToGetChildren(path);
            Map<Object, Object> childMap = children.stream()
                    .collect(Collectors.toMap(KeyValueVo::getKey, KeyValueVo::getValue, (a, b) -> a));
            try {
                valueStr = ConfigParser.MAPPER.writeValueAsString(childMap);
            } catch (JsonProcessingException e) {
                logger.error("config process error, path: {}, msg: {}", path, e.getMessage());
                valueStr = null;
            }
        }
        Object value = configParser.parse(javaType, valueStr, defaultValue, field);
        return new RstConf<>(value);
    }
}
