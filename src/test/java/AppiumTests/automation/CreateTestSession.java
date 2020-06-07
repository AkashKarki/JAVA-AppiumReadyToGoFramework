package AppiumTests.automation;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import AppiumTests.GeneralStore.actions.CheckOutPage;
import AppiumTests.GeneralStore.actions.LoginPageAction;
import AppiumTests.GeneralStore.actions.ProductPageAction;
import io.appium.java_client.android.AndroidDriver;
import AppiumTests.automation.PortFinder;
import io.appium.java_client.remote.MobileCapabilityType;
import AppiumTests.automation.GlobalPropertyReader;
import static AppiumTests.automation.CustomFeatures.hardWait;

public class CreateTestSession {
	public LoginPageAction LoginPage;
	public ProductPageAction ProductPage;
	public CheckOutPage CheckOut;
	public CustomFeatures Custom;
	protected AndroidDriver<WebElement> Driver = null;
	public String Application = null;
	public String tier = null;
	public String Device = null;
	public String AppType = null;
	public String Browser = null;
	public String EmulatorName = null;
	public static boolean AppiumServer = true;
	public int AppiumPort = 0;
	String AppiumIp = null;
	PortFinder fp = new PortFinder();
	String AndroidVersion;

	public CreateTestSession(String EmulatorName) {
		this.EmulatorName = EmulatorName;
		if(System.getProperty("AndroidVersion")!=null) {
			this.AndroidVersion=System.getProperty("AndroidVersion");
		}
		else {
			this.AndroidVersion = GlobalPropertyReader.getGlobalProperty("AndroidVersion");
		}
		StartAppiumServer();
		launchApplication();
		initializeActionClass();
	}
	

	private void initializeActionClass() {
		LoginPage = new LoginPageAction(Driver);
		ProductPage = new ProductPageAction(Driver);
		CheckOut = new CheckOutPage(Driver);
		Custom =new CustomFeatures(Driver);

	}

	public void launchApplication() {
		System.out.println("In Launch Application");
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
		
		if(System.getProperty("Device")!=null) {
			Device=System.getProperty("Device");
		}
		else {
			Device = GlobalPropertyReader.getGlobalProperty("Device");
		}
		
		if(System.getProperty("Apptype")!=null) {
			AppType=System.getProperty("Apptype");
		}
		else {
			AppType = GlobalPropertyReader.getGlobalProperty("Apptype");
		}
		
		File app = new File(
				"src/test/resources/Test Applications/" + Application + "/" + tier + "/" + Application + ".apk");
		File ChromeDriver = new File("src/test/resources/Driver");
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UIAutomator2");

		if (Device.equalsIgnoreCase("Emulator")) {
			capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, EmulatorName);
			capabilities.setCapability("avd", EmulatorName);
			System.out.println("Driver to Emulator::" + EmulatorName);
		}

		capabilities.setCapability("platformVersion", AndroidVersion);

		int wadPort = fp.findwdaLocalPortFreePort();
		capabilities.setCapability("wdaLocalPort", wadPort);

		int sytemPortt = fp.findSystemPortFreePort();
		capabilities.setCapability("systemPort", sytemPortt);
		capabilities.setCapability("mjpegServerPort", fp.findmjpegServerPortFreePort());

		/*
		 * else if (Device.equalsIgnoreCase("RealDevice")) {
		 * capabilities.setCapability(MobileCapabilityType.DEVICE_NAME,
		 * "Android Device"); }
		 */

		if (AppType.equalsIgnoreCase("Native")) {
			capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		}

		capabilities.setCapability("avdLaunchTimeout", 420000);
		capabilities.setCapability("avdReadyTimeout", 420000);
		capabilities.setCapability("deviceReadyTimeout", 300);
		capabilities.setCapability("androidDeviceReadyTimeout", 300);
		capabilities.setCapability("uiautomator2ServerInstallTimeout", 420000);
		capabilities.setCapability("uiautomator2ServerLaunchTimeout", 420000);
		
		

		/*
		 * else if (AppType.equalsIgnoreCase("WebApp")) { Browser =
		 * GlobalPropertyReader.getGlobalProperty("Browser");
		 * capabilities.setCapability(MobileCapabilityType.BROWSER_NAME,
		 * Browser.toLowerCase()); }
		 */

		capabilities.setCapability("chromedriverExecutableDir", ChromeDriver.getAbsolutePath());

		try {

			Driver = new AndroidDriver<>(new URL("http://" + AppiumIp + ":" + AppiumPort + "/wd/hub"), capabilities);
			Driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			System.out.println("======================================================");
			System.out.println("            Test Application:: " + Application);
			System.out.println("            Test Tier:: " + tier);
			System.out.println("            Test On:: " + Device);
			System.out.println("            Application Type:: " + AppType);
			System.out.println("======================================================");

		} catch (WebDriverException e) {
			hardWait(6);
			try {
				checkIfServerIsRunnning(AppiumPort);
				Driver = new AndroidDriver<>(new URL("http://" + AppiumIp + ":" + AppiumPort + "/wd/hub"),
						capabilities);
				Driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				System.out.println("======================================================");
				System.out.println("            Test Application:: " + Application);
				System.out.println("            Test Tier:: " + tier);
				System.out.println("            Test On:: " + Device);
				System.out.println("            Application Type:: " + AppType);
				System.out.println("======================================================");
			} catch (MalformedURLException e1) {
				e1.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public synchronized void StartAppiumServer() {
		System.out.println("Starting Appium Server..");
		AppiumPort = fp.findAppuimFreePort();
		AppiumIp = "0.0.0.0";

		Runtime runtime = Runtime.getRuntime();
		try {
			runtime.exec("cmd.exe /c start cmd.exe /k \"appium -a " + AppiumIp + " -p " + AppiumPort);
			Thread.sleep(30000);
			System.out.println("Appium Server Created..");

		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void closeBrowserSession() {
		Driver.quit();
		// service.stop();
		// stopServer();
		// System.out.println("Appuim Server Stopped.");
		// stopEmulator(EmulatorName);
	}

	public boolean checkIfServerIsRunnning(int port) {

		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
		} catch (IOException e) {
			// If control comes here, then it means that the port is in use
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}

}
