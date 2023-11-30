package me.marquez.upbit.internal;

import com.google.gson.*;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoField;

public class DataMapper {

    protected static final Gson GSON;


    static {
        GSON = new GsonBuilder().registerTypeAdapter(BigDecimal.class, new JsonSerializer<BigDecimal>() {
            @Override
            public JsonElement serialize(BigDecimal src, Type type, JsonSerializationContext jsonSerializationContext) {
                return new JsonPrimitive(src.toPlainString());
            }
        }).registerTypeAdapter(OffsetDateTime.class, new JsonSerializer<OffsetDateTime>() {
            private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
            @Override
            public JsonElement serialize(OffsetDateTime offsetDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
                return new JsonPrimitive(offsetDateTime.format(dateTimeFormatter));
            }
        }).registerTypeAdapter(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
            private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            @Override
            public JsonElement serialize(LocalDateTime localDateTime, Type type, JsonSerializationContext jsonSerializationContext) {
                return new JsonPrimitive(localDateTime.format(dateTimeFormatter));
            }
        }).registerTypeAdapter(LocalTime.class, new JsonSerializer<LocalTime>() {
            private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            @Override
            public JsonElement serialize(LocalTime localTime, Type type, JsonSerializationContext jsonSerializationContext) {
                return new JsonPrimitive(localTime.format(timeFormatter));
            }
        }).registerTypeAdapter(BigDecimal.class, new JsonDeserializer<BigDecimal>() {
            @Override
            public BigDecimal deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                String text = jsonElement.getAsString();
                return new BigDecimal(text);
            }
        }).registerTypeAdapter(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
            private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            @Override
            public LocalDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                String text = jsonElement.getAsString();
                return LocalDateTime.parse(text, dateTimeFormatter);
            }
        }).registerTypeAdapter(OffsetDateTime.class, new JsonDeserializer<OffsetDateTime>() {
            private final DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .optionalStart()
                    .appendOffset("+HH:mm", "Z")
                    .optionalEnd()
                    .toFormatter();
            @Override
            public OffsetDateTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                String text = jsonElement.getAsString();
                return OffsetDateTime.parse(text, dateTimeFormatter);
            }
        }).registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
            private final DateTimeFormatter dateFormatter = new DateTimeFormatterBuilder()
                    .appendValue(ChronoField.YEAR, 4)  // 연도는 항상 4자리
                    .optionalStart()
                    .appendLiteral('-')
                    .appendValue(ChronoField.MONTH_OF_YEAR, 2) // 월
                    .appendLiteral('-')
                    .appendValue(ChronoField.DAY_OF_MONTH, 2)  // 일
                    .optionalEnd()
                    .optionalStart()
                    .appendValue(ChronoField.MONTH_OF_YEAR, 2) // 월
                    .appendValue(ChronoField.DAY_OF_MONTH, 2)  // 일
                    .optionalEnd()
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.SMART);
            @Override
            public LocalDate deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                String text = jsonElement.getAsString();
                return LocalDate.parse(text, dateFormatter);
            }
        }).registerTypeAdapter(LocalTime.class, new JsonDeserializer<LocalTime>() {
            private final DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder()
                    .appendPattern("HH")
                    .appendOptional(DateTimeFormatter.ofPattern(":mm:ss"))
                    .appendOptional(DateTimeFormatter.ofPattern("mmss"))
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);
            @Override
            public LocalTime deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                String text = jsonElement.getAsString();
                return LocalTime.parse(text, timeFormatter);
            }
        }).create();
    }

    public static MultiValuedMap<String, String> jsonToMap(String json) {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        JsonElement element = JsonParser.parseString(json);
        element.getAsJsonObject().asMap().forEach((key, value) -> {
            if(value.isJsonArray()) {
                value.getAsJsonArray().forEach(child -> {
                    map.put(key, child.getAsString());
                });
            }else if(!value.isJsonNull()) {
                map.put(key, value.getAsString());
            }
        });
        return map;
    }

    public static <T> T jsonToObject(String json, Class<T> clazz) {
        return GSON.fromJson(json, clazz);
    }

    public static String objectToJson(Object object) {
        return GSON.toJson(object);
    }
}
