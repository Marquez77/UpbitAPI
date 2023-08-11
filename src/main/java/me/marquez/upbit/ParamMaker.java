package me.marquez.upbit;

import java.util.HashMap;
import java.util.function.Supplier;

public class ParamMaker {
    private final HashMap<String, String> params = new HashMap<>();
    private ParamMaker() {}
    public static ParamMaker create() {
        return new ParamMaker();
    }

    public ParamMaker add(String key, Object value) {
        params.put(key, value instanceof String ? (String)value : String.valueOf(value));
        return this;
    }

    public ParamMaker addIf(String key, Object value, Supplier<Boolean> condition) {
        return condition.get() ? add(key, value) : this;
    }

    public HashMap<String, String> build() {
        return params;
    }
}
