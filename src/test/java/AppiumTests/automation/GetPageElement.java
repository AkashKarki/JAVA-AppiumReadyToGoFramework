package AppiumTests.automation;

import static io.appium.java_client.touch.LongPressOptions.longPressOptions;
import static io.appium.java_client.touch.TapOptions.tapOptions;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.offset.ElementOption;
import io.appium.java_client.TouchAction;

public class GetPageElement {

	String Application;
	String tier;
	String FilePath;
	AndroidDriver<WebElement> driver;
	String PageName;
	BufferedReader BR;
	String ElementLine;
	BufferedReader br;
	@SuppressWarnings("rawtypes")
	TouchAction Touch;

	@SuppressWarnings("rawtypes")
	public GetPageElement(AndroidDriver<WebElement> driver, String PageName) {
		this.driver = driver;
		this.PageName = PageName;
		Touch = new TouchAction(driver);
		if(System.getProperty("ApplicationName")!=null) {
			Application=System.getProperty("ApplicationName");
		}
		else {
		Application = GlobalPropertyReader.getGlobalProperty("ApplicationName");
		}
		
		if(System.getProperty("tier")!=null) {
			tier=System.getProperty("tier");
		}
		else {
			tier = GlobalPropertyReader.getGlobalProperty("tier");
		}
		
		FilePath = "src/test/resources/Page Object Factory/" + Application + "/" + tier;
	}
	

	public WebElement element(String element) {
		String elementToken[] = getElementFromFile(element);
		return driver.findElement(getLocator(elementToken[1].trim(), elementToken[2].trim()));
	}

	public WebElement element(String element, String replacement) {
		String elementToken[] = getElementFromFile(element);
		elementToken[2] = elementToken[2].replaceAll("\\#\\{.+?\\}", replacement);
		return driver.findElement(getLocator(elementToken[1].trim(), elementToken[2].trim()));
	}

	public WebElement element(String element, String replacement, String replacement2) {
		String elementToken[] = getElementFromFile(element);
		elementToken[2] = elementToken[2].replaceFirst("\\#\\{.+?\\}", replacement);
		elementToken[2] = elementToken[2].replaceFirst("\\#\\{.+?\\}", replacement2);
		System.out.println(elementToken[2]);
		return driver.findElement(getLocator(elementToken[1].trim(), elementToken[2].trim()));
	}

	public List<WebElement> elements(String element) {
		String elementToken[] = getElementFromFile(element);
		return driver.findElements(getLocator(elementToken[1].trim(), elementToken[2].trim()));
	}

	public List<WebElement> elements(String element, String replacement) {
		String elementToken[] = getElementFromFile(element);
		elementToken[2] = elementToken[2].replaceAll("\\#\\{.+?\\}", replacement);
		return driver.findElements(getLocator(elementToken[1].trim(), elementToken[2].trim()));
	}

	public List<WebElement> elements(String element, String replacement, String replacement2) {
		String elementToken[] = getElementFromFile(element);
		elementToken[2] = elementToken[2].replaceFirst("\\#\\{.+?\\}", replacement);
		elementToken[2] = elementToken[2].replaceFirst("\\#\\{.+?\\}", replacement2);
		return driver.findElements(getLocator(elementToken[1].trim(), elementToken[2].trim()));
	}

	public void isElementDisplayed(String elementToken) {
		element(elementToken);
		System.out.println(elementToken + " is Displayed..");

	}

	public void isElementDisplayed(String elementToken, String replacement) {
		element(elementToken, replacement);
		System.out.println(elementToken + " is Displayed..");

	}

	public void isElementDisplayed(String elementToken, String replacement, String replacement2) {
		element(elementToken, replacement, replacement2);
		System.out.println(elementToken + " is Displayed..");

	}

	public String[] getElementFromFile(String elementToken) {
		File FileDir = new File(FilePath);
		File File = new File(FileDir, PageName + ".txt");
		// System.out.println("Page Object File Path::"+File);
		try {
			if (!File.exists()) {
				System.out.println("file not found");
			} else {

				br = new BufferedReader(new FileReader(File));
				String line = br.readLine();
				while (line != null) {
					if (line.split(":", 3)[0].equalsIgnoreCase(elementToken)) {
						ElementLine = line.trim();
						break;
					}
					line = br.readLine();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		// System.out.println("Found Element::"+ElementLine);
		return ElementLine.split(":", 3);

	}

	private By getLocator(String Type, String element) {
		switch (Type.toLowerCase().trim()) {
		case "id":
			return By.id(element);
		case "xpath":
			return By.xpath(element);
		case "name":
			return By.name(element);
		case "classname":
			return By.className(element);
		case "css":
			return By.cssSelector(element);

		default:
			System.out.print("Locator Type Not Found");
		}
		return null;
	}

	public void ScrollToTextOnAndroid(String Text) {

		driver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().textMatches(\""
						+ Text + "\").instance(0))");
		// driver.findElementByAndroidUIAutomator("new UiScrollable(new
		// UiSelector()).scrollIntoView(text(\""+Text+"\"));");
		System.out.println("Element Scroll to text::" + Text);
	}

	public WebElement FindElementByAndroidUIAutomator(String Text) {
		return driver.findElementByAndroidUIAutomator("text(\"" + Text + "\")");
	}

	public static void hardWait(int timeInSec) {
		try {
			Thread.sleep(timeInSec * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void switchToMobileView(String ViewToSwitch) {
		waitforAndroidLoaderToDisppper();
		Set<String> ViewSet = driver.getContextHandles();
		for (String view : ViewSet) {
			System.out.println(view);
			if (view.contains(ViewToSwitch)) {
				driver.context(view);
				System.out.println("Switched To view::" + view);
				return;
			}
		}
		Assert.assertTrue(false, "Web View not Found");
	}

	public void waitforAndroidLoaderToDisppper() {
		int counter = 0;
		System.out.println("Waiting for Android loader to Disapper..");
		while (counter <= 2000) {
			List<WebElement> Loader = driver.findElementsByClassName("android.widget.ProgressBar");
			if (Loader.size() == 0) {
				System.out.println("Android Loader Disappered..");
				return;
			}

			else {
				counter++;
			}
		}
	}

	public void longPressOnElement(WebElement element) {
		Touch.longPress(longPressOptions().withElement(ElementOption.element(element))).perform();
		System.out.println("long press Action preformed");
	}

	public void tapOnElement(WebElement element) {
		Touch.tap(tapOptions().withElement(ElementOption.element(element))).perform();
		System.out.println("Tap Action preformed");
	}

	public void navigateBackOnWindow() {
		driver.navigate().back();
	}

}
