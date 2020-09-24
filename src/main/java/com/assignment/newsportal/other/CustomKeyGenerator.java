//package com.assignment.newsportal.other;
//
//import org.springframework.cache.interceptor.KeyGenerator;
//import org.springframework.util.StringUtils;
//
//import java.lang.reflect.Method;
//
//public class CustomKeyGenerator implements KeyGenerator {
//    @Override
//    public Object generate(Object o, Method method, Object... objects) {
//        return StringUtils.arrayToDelimitedString(objects, "-");
//    }
//}
//
