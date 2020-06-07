package AppiumTests.GeneralStore.actions;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import AppiumTests.automation.GetPageElement;
import io.appium.java_client.android.AndroidDriver;

public class ProductPageAction extends GetPageElement{
	AndroidDriver<WebElement> Driver;
	
	
	public ProductPageAction(AndroidDriver<WebElement> Driver) {
		super(Driver,"ProductPage");
		this.Driver=Driver;
	}
	
	public void verifyUserOnProductPage() {
		FindElementByAndroidUIAutomator("Products");
		System.out.println("User is on Product Page.");
	}
	
	public void clickNaviagteBackButton() {
		element("NavigateBackIcon").click();
		System.out.println("Navigate Back Icon clicked..");
	}
	
	public void verifyItemDisplayedOnProductPage(String ProductName) {
		ScrollToTextOnAndroid(ProductName);
		System.out.println(ProductName+" Displayed on Product Page.");
	}
	
	public void clickAddtoCartForItemOnProductPage(String ProductName) {
		isElementDisplayed("AddProductToCart",ProductName);
		element("AddProductToCart",ProductName).click();
		System.out.println("Add to cart button Clicked for "+ProductName);
	}
	
	public void verifyCountOfProductAddToCartIcon(String Count) {
		isElementDisplayed("ProductCountOnAddToCart");
		String CountDisplayed=element("ProductCountOnAddToCart").getText().trim();
		System.out.println("Count of Products Displayed on Add to Cart Icon::"+CountDisplayed);
		Assert.assertEquals(CountDisplayed, Count);
		
	}

	public void clickAddToCartIcon() {
		element("appbar_btn_cart").click();
		System.out.println("Add to Cart Icon Clicked.");
		
	}

}
