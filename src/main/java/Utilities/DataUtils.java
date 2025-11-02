package Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Properties;

public class DataUtils {
    private static final String TEST_DATA_PATH = "src/test/resources/TestData/";

    public static String getJsonData(String fileName, String key) {
        try {
            FileReader reader = new FileReader(TEST_DATA_PATH + fileName + ".json");
            JsonElement jsonElement = JsonParser.parseReader(reader);
            return jsonElement.getAsJsonObject().get(key).getAsString();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getPropertyValue(String fileName, String key) {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream(TEST_DATA_PATH + fileName + ".properties"));
            return properties.getProperty(key);
        } catch (Exception e) {
            return null;
        }

    }

    public static String generateRandomZipCode() {
        return String.valueOf(10000 + new java.util.Random().nextInt(90000));
    }

}
