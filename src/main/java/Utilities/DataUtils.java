package Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Collection;
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

    public static String getProperty(String key) {
        try {
            return System.getProperty(key);
        } catch (Exception e) {
            LogsUtils.error("Error getting property for key:" + e.getMessage());
            return "";
        }
    }

    public static Properties loadProperties() {
        try {
            Properties properties = new Properties();
            Collection<File> propertiesFiles;
            propertiesFiles = FileUtils.listFiles(new File("src/main/resources"), new String[]{"properties"}, true); //get all files with extension properties
            propertiesFiles.forEach(file -> {
                try {
                    properties.load(new FileInputStream(file));
                } catch (Exception e) {
                    LogsUtils.error("Error loading properties file:" + file.getName() + e.getMessage());
                }
                properties.putAll(System.getProperties());
                System.getProperties().putAll(properties);
            });
            LogsUtils.info("Properties file loaded successfully");
            return properties;
        } catch (Exception e) {
            LogsUtils.error("Error loading properties file" + e.getMessage());
            return null;
        }
    }

}
