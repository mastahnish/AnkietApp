package com.solutions.myo.ankietapp.firebase.database.utils;

@Deprecated
public class ConvertHelper {
    private ConvertHelper() {
    }

    /*public static <T> T convertPropertyToObject(Map<String, Object> prop, Class<T> mClass) {
        if (prop == null || prop.size() <= 0 || mClass == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        return mapper.convertValue(prop, mClass);
    }

    public static Map<String, Object> convertObjectToProperty(Object data) {
        if (data == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        return mapper.convertValue(data, Map.class);
    }

    public static List<Object> convertObjectToListProperty(Object data) {
        if (data == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        return mapper.convertValue(data, List.class);
    }
*/
}
