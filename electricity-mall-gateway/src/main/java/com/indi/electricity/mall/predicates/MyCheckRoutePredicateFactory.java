//package com.indi.electricity.mall.predicates;
//
//
//import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
//import org.springframework.cloud.gateway.handler.predicate.GatewayPredicate;
//import org.springframework.util.StringUtils;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.server.ServerWebExchange;
//
//import javax.validation.constraints.NotEmpty;
//import java.util.Arrays;
//import java.util.Iterator;
//import java.util.List;
//import java.util.function.Predicate;
//
///**
// * 必须：
// * xxxRoutePredicateFactory
// * 继承AbstractRoutePredicateFactory
// */
//public class MyCheckRoutePredicateFactory extends AbstractRoutePredicateFactory<MyCheckRoutePredicateFactory.Config> {
//    public static final String PARAM_KEY = "param";
//    public static final String REGEXP_KEY = "regexp";
//
//    public MyCheckRoutePredicateFactory() {
//        super(MyCheckRoutePredicateFactory.Config.class);
//    }
//
//    public MyCheckRoutePredicateFactory(Class<MyCheckRoutePredicateFactory.Config> configClass) {
//        super(configClass);
//    }
//
//    public List<String> shortcutFieldOrder() {
//        return Arrays.asList("param", "regexp");
//    }
//
//    public Predicate<ServerWebExchange> apply(MyCheckRoutePredicateFactory.Config config) {
//        return new GatewayPredicate() {
//            public boolean test(ServerWebExchange exchange) {
//                if (!StringUtils.hasText(config.regexp)) {
//                    return exchange.getRequest().getQueryParams().containsKey(config.param);
//                } else {
//                    List<String> values = (List) exchange.getRequest().getQueryParams().get(config.param);
//                    if (values == null) {
//                        return false;
//                    } else {
//                        Iterator var3 = values.iterator();
//
//                        String value;
//                        do {
//                            if (!var3.hasNext()) {
//                                return false;
//                            }
//
//                            value = (String) var3.next();
//                        } while (value == null || !value.matches(config.regexp));
//
//                        return true;
//                    }
//                }
//            }
//
//            public String toString() {
//                return String.format("Query: param=%s regexp=%s", config.getParam(), config.getRegexp());
//            }
//        };
//    }
//
//    @Validated
//    public static class Config {
//        @NotEmpty
//        private String param;
//        private String regexp;
//
//        public Config() {
//        }
//
//        public String getParam() {
//            return this.param;
//        }
//
//        public MyCheckRoutePredicateFactory.Config setParam(String param) {
//            this.param = param;
//            return this;
//        }
//
//        public String getRegexp() {
//            return this.regexp;
//        }
//
//        public MyCheckRoutePredicateFactory.Config setRegexp(String regexp) {
//            this.regexp = regexp;
//            return this;
//        }
//    }
//}
