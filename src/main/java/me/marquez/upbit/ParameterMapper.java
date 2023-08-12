package me.marquez.upbit;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.lang.reflect.Field;

public class ParameterMapper {

    public static MultiValuedMap<String, String> classToParameter(Object object) {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        for(Field field : object.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            String key = field.getName();
            try {
                Object value = field.get(object);
                if(value == null) continue;
                if(field.getType().isArray()) {
                    Object[] array = (Object[])value;
                    for(Object element : array) {
                        map.put(key, String.valueOf(element));
                    }
                }else map.put(key, String.valueOf(value));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }
}
