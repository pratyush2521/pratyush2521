package utilities;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 * @author Pratyush
 */
public class GenericLib {
	static String filePath = "./src/main/java/utilities/xpath.properties";

	public String getXpath(String key) {
		String value = null;
		try {
			Properties prop = new Properties();
			prop.load(new FileInputStream(new File(filePath)));
			value = prop.getProperty(key);
		} catch (FileNotFoundException e) {
			System.out.println("FileNotFoundException" + " " + value);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException" + " " + value);
			e.printStackTrace();
		}
		return value;
	}
}