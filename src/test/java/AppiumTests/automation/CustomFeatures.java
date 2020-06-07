package AppiumTests.automation;

import java.io.File;
import org.apache.commons.io.FileUtils;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.FileHandler;

import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.utils.FileUtil;

import AppiumTests.extentReport.ExtentTestManager;
import io.appium.java_client.android.AndroidDriver;

public class CustomFeatures {
	AndroidDriver<WebElement> Driver;
	
	
	public CustomFeatures(AndroidDriver<WebElement> Driver) {
		this.Driver=Driver;
	}
	
	public synchronized void AttachScreenshortToExtentReport(String MethodName) {
		String fileSeperator = System.getProperty("file.separator");
		File scrFile = ((TakesScreenshot) Driver).getScreenshotAs(OutputType.FILE);
		String TargetDir=System.getProperty("user.home")+fileSeperator+"Downloads"+fileSeperator+"Screenshot"+fileSeperator+getRandomString()+getCurrentDateInyyyyMMddHHmmDateFormats()+".jpg";
		File targetFile = new File(TargetDir);
		if (!targetFile.exists()) {
			if (targetFile.mkdirs()) {
				System.out.println("Directory: " + targetFile.getAbsolutePath() + " is created!");
			} else {
				System.out.println("Failed to create directory: " + targetFile.getAbsolutePath());
			}

		}
		try {
			FileUtils.copyFile(scrFile,targetFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			ExtentTestManager.getTest().fail("Test Case Failed Snapshot is below " + ExtentTestManager.getTest().addScreenCaptureFromPath(targetFile.getAbsolutePath()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	
	public static String getRandomString() {
		return  generateRandomString(6);
	}

	public static String getCurrentDateInyyyyMMddHHmmDateFormats() {
		DateFormat dateFormat = new SimpleDateFormat("YYYYMMDDHHMMSSmmm");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String generateRandomString(int number) {
		// function to generate a random string of length n
		// chose a Character random from this String
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";

		// create StringBuffer size of AlphaNumericString
		StringBuilder sb = new StringBuilder(number);

		for (int i = 0; i < number; i++) {

			// generate a random number between
			// 0 to AlphaNumericString variable length
			int index = (int) (AlphaNumericString.length() * Math.random());

			// add Character one by one in end of sb
			sb.append(AlphaNumericString.charAt(index));
		}

		return sb.toString();
	}
	
	public static void hardWait(int timeInSec) {
		try {
			Thread.sleep(timeInSec * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
