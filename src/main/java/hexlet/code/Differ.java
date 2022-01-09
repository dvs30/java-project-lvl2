package hexlet.code;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Comparator;
import java.util.Objects;

public class Differ {
    public static String generate(String filepath1, String filepath2) throws Exception {
        return generate(filepath1, filepath2, "stylish");
    }

    public static String generate(String filepath1, String filepath2, String format) throws Exception {
        Map<String, Object> firstFile = readFile(filepath1);
        Map<String, Object> secondFile = readFile(filepath2);
        List<Map<String, Object>> difference = findDifference(firstFile, secondFile);
        return Formatter.format(difference, format);
    }

    private static Map<String, Object> readFile(String filepath) throws Exception {
        String typeOfFile = filepath.substring(filepath.lastIndexOf(".") + 1);
        Map<String, Object> result = Parser.parse(Files.readString(Paths.get(filepath)), typeOfFile);
        return result;
    }

    private static List<Map<String, Object>> findDifference(
            Map<String, Object> firstFile,
            Map<String, Object> secondFile) {
        List<Map<String, Object>> difference = new ArrayList<>();
        Set<String> keys = new HashSet<>(firstFile.keySet());
        keys.addAll(secondFile.keySet());

        for (String key : keys) {
            Object firstValue = firstFile.get(key);
            Object secondValue = secondFile.get(key);
            if (!firstFile.containsKey(key)) {
                difference.add(doEntry("added", key, secondValue));
            } else if (!secondFile.containsKey(key)) {
                difference.add(doEntry("removed", key, firstValue));
            } else if (Objects.equals(firstFile, secondFile)) {
                difference.add(doEntry("unchanged", key, firstValue));
            } else {
                difference.add(doEntry("updated", key, secondValue, firstValue));
            }
        }
        difference.sort(Comparator.comparing(map -> map.get("fieldName").toString()));
        return difference;
    }

    private static Map<String, Object> doEntry(
            final String status,
            final String key,
            final Object value,
            final Object oldValue) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", status);
        result.put("fieldName", key);
        result.put("value", value);
        result.put("oldValue", oldValue);
        return result;
    }

    private static Map<String, Object> doEntry(
            final String status,
            final String key,
            final Object value) {

        Map<String, Object> result = new HashMap<>();
        result.put("status", status);
        result.put("fieldName", key);
        result.put("value", value);
        return result;
    }
}
