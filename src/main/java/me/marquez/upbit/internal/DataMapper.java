package me.marquez.upbit.internal;

import com.google.gson.*;
import me.marquez.upbit.entity.date.HourMinuteSecond;
import me.marquez.upbit.entity.date.YearMonthDay;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

public class DataMapper {

    protected static final Gson GSON;

    static {
        GSON = new GsonBuilder().registerTypeAdapter(YearMonthDay.class, new JsonDeserializer<YearMonthDay>() {
            @Override
            public YearMonthDay deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return YearMonthDay.of(jsonElement.getAsString());
            }
        }).registerTypeAdapter(HourMinuteSecond.class, new JsonDeserializer<HourMinuteSecond>() {
            @Override
            public HourMinuteSecond deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                return HourMinuteSecond.of(jsonElement.getAsString());
            }
        }).create();
    }

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
                        map.put(key + "[]", String.valueOf(element));
                    }
                }else map.put(key, String.valueOf(value));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public static <T> T jsonToClass(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }
}
