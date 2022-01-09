package hexlet.code;

import hexlet.code.formatters.Json;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.util.List;
import java.util.Map;

class Formatter {
    public static String format(final List<Map<String, Object>> diff, final String formatName) throws Exception {
        switch (formatName) {
            case "stylish":
                return Stylish.format(diff);
            case "plain":
                return Plain.format(diff);
            case "no-format":
                return diff.toString();
            case "json":
                return Json.format(diff);
            default:
                throw new Exception("Unknown format type");
        }
    }
}
