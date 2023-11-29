package me.marquez.upbit;

import com.google.gson.*;
import me.marquez.upbit.entity.date.HourMinuteSecond;
import me.marquez.upbit.entity.date.YearMonthDay;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.time.YearMonth;

public class ParameterMapper {

    private static final Gson gson;

    static {
        gson = new GsonBuilder().registerTypeAdapter(YearMonthDay.class, new JsonDeserializer<YearMonthDay>() {
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
