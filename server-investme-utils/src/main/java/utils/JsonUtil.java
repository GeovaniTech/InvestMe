package utils;

import java.lang.reflect.Type;

import com.google.gson.Gson;

public class JsonUtil {
    private static final Gson gson = new Gson();

    public static <T> T convertJson(String json, Type type) {
        return gson.fromJson(json, type);
    }
}
