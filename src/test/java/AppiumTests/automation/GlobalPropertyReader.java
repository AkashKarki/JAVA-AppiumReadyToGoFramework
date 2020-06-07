package AppiumTests.automation;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
public class GlobalPropertyReader {
	static FileInputStream propertiesFile;
	static Properties prop=new Properties();
	
	public static String getGlobalProperty(String Property) {
	try {
		propertiesFile= new FileInputStream("global.properties");
		prop.load(propertiesFile);
	} catch (IOException e) {
		System.out.println(e.getMessage());
	}
	
	return (String)prop.get(Property);
	
	}

}
