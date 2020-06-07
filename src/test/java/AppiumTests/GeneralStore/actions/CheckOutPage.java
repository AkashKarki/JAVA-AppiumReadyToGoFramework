package AppiumTests.GeneralStore.actions;


import java.util.List;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import AppiumTests.automation.GetPageElement;
import io.appium.java_client.android.AndroidDriver;




public class CheckOutPage extends GetPageElement{

	
AndroidDriver<WebElement> Driver;

	
	public CheckOutPage(AndroidDriver<WebElement> Driver) {
		super(Driver,"CheckOutPage");
		this.Driver=Driver;
		
	}
	
	public void verifyUserOnCartPage() {
		FindElementByAndroidUIAutomator("Cart");
		System.out.println("User is on Cart Page.");
	}
	
	public void verifyProductIsDisplayedOnCartPage(String Product) {
		FindElementByAndroidUIAutomator(Product);
		System.out.println(Product+" is displayed on Cart Page.");
	}

	public void verifyNumberOfProductDisplayedOnCartPage(String Count) {
		List<WebElement> Products=elements("NumberOfProductOnCheckOut");
		System.out.println("Number of Product Expected::"+Count);
		Assert.assertEquals(Integer.parseInt(Count), Products.size());
	}

	public void verifyTotalAmountofProducts(String Amount) {
		String AmountDisplayed=element("TotalAmountOnCheckOut").getText().trim();
		System.out.println("Amount Displayed::"+AmountDisplayed);
		Assert.assertEquals(Amount, AmountDisplayed);
		
	}

	public void verifyPriceOfProductOnCheckOut(String Product,String Price) {
		String DisplayedPrice=element("PriceOfProduct",Product).getText();
		Assert.assertEquals(DisplayedPrice, Price,"[Assertion Failed]:: Incorrect Price Displayed..");
		System.out.println("For "+Product+" Price Displayed::"+Price);
	}

	public void LongPressTermsAndConditions() {
		longPressOnElement(element("TermsAndConditions"));
		System.out.println("Long Press action perform on Terms and conditions");
	}
	
	public void verifyTermsAndConditionsPoupWindow() {
		WebElement termsAndConditionsPoup=element("TermsPopup");
		String TextDisplayed=termsAndConditionsPoup.getText().trim();
		Assert.assertEquals(TextDisplayed, "Terms Of Conditions","[Assertion Failed]::Terms Of Conditions popup is not displayed");
		System.out.println("Terms Of Conditions popup displayed");
	}

	public void clickCloseonTermsAndConditionsPoupWindow() {
		element("CloseButtonTermsPopup").click();
		System.out.println("CLOSE button cliked on Terms And Conditions Poup Window");
	}
	
	public void verifyTermsAndConditionsPoupWindowNotDisplayed() {
		hardWait(2);
		List<WebElement> termsAndConditionsPoup=elements("TermsPopup");
		Assert.assertTrue(termsAndConditionsPoup.size()==0, "[Assertion Failed]:: Terms Of Conditions popup displayed");
		System.out.println("[Assertion Passed]::Terms Of Conditions popup is not displayed");
	}

	public void clickSendMeEmailCheckBox() {
		WebElement SendMeEmail=element("SendEmailCheckbox");
		System.out.println(SendMeEmail.getText().trim());
		tapOnElement(SendMeEmail);
		System.out.println("Send Me Email Check Box Clicked");
		
	}
	
	public void verifySendMeEmailCheckBoxState() {
		WebElement SendMeEmail=element("SendEmailCheckbox");
		System.out.println(SendMeEmail.getAttribute("checked"));
		Assert.assertTrue(Boolean.parseBoolean(SendMeEmail.getAttribute("checked")));
	}

	public void clickVisitToWebsiteButton() {
		WebElement VisitToWebsiteButton=element("visitToWebSiteButton");
		System.out.println(VisitToWebsiteButton.getText()+" Button Cliked");
		VisitToWebsiteButton.click();
	}
	
	public void switchToWebView(String ViewToSwitch) {
		switchToMobileView(ViewToSwitch);
	}
		
	
}
