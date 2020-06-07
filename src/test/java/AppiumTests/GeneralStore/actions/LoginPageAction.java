package AppiumTests.GeneralStore.actions;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import AppiumTests.automation.GetPageElement;
import io.appium.java_client.android.AndroidDriver;

public class LoginPageAction extends GetPageElement {
	AndroidDriver<WebElement> Driver;
	
	
	public LoginPageAction(AndroidDriver<WebElement> Driver) {
		super(Driver,"LoginPage");
		this.Driver=Driver;
	}
	
	
	public void verifyUserIsOnLoginPage() {
		element("LoginPageLabel");
		System.out.println("User is on Login Page.");
		element("CountyDropdown");
		System.out.println("Select County is Displayed..");
		element("NameField");
		System.out.println("Name Filed is Displayed.");
	}
	
	public void selectCountyFromDropDown(String County) {
		isElementDisplayed("CountyDropdown");
		element("CountyDropdown").click();
		System.out.println("Country DropDown Clicked");
		isElementDisplayed("CountyList");
		System.out.println("List of County is Displayed.");
		ScrollToTextOnAndroid(County);
		FindElementByAndroidUIAutomator(County).click();
		System.out.println(County+" Selected in DropDown.");
	}
	
	public void enterTextOnNameField(String Name) {
		isElementDisplayed("NameField");
		element("NameField").clear();
		element("NameField").sendKeys(Name);
		System.out.println(Name+" entered in the Name Filed.");
		Driver.hideKeyboard();
	}
	
	public void selectGenderButton(String Gender) {
		FindElementByAndroidUIAutomator(Gender).click();
		System.out.println(Gender+" Radio Button Clicked.");
	}
	
	public void clickLetsShopButton() {
		FindElementByAndroidUIAutomator("Let's  Shop").click();
		System.out.println("'Let's  Shop' Button Clicked.");
	}


	public void verifyErrorToEnterName() {
		String MsgDisplayed=element("ErrorToastMsg").getText().trim();
		System.out.println("Message Displayed::"+MsgDisplayed);
		Assert.assertEquals(MsgDisplayed,"Please enter your name");
		
	}

}
