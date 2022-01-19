package helper;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
/**
 * 
 * @author Pratyush
 *
 */
public class LoggerHelper {

	private static boolean root=false;
	public static Logger getLogger(Class cls){
		if(root){
			return Logger.getLogger(cls);
		}
		PropertyConfigurator.configure("./src/main/java/utilities/log4j.properties");
		root = true;
		return Logger.getLogger(cls);
	}
}