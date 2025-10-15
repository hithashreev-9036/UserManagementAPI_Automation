package common;
import java.io.FileInputStream;
import java.util.Properties;


public class ConfigReader {
static Properties prop = new Properties();
    
    static {
        try {
            FileInputStream fis = new FileInputStream("src/test/resources/config/config.properties");
            prop.load(fis);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return prop.getProperty(key);
    }
}
