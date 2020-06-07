package AppiumTests.GeneralStore.test;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import AppiumTests.automation.CreateTestSession;
import AppiumTests.automation.UtilityClass;
import AppiumTests.automation.CreateTestEmulator;

public class GeneralStoreBasicTest2 extends UtilityClass {

	CreateTestSession AppTest;
	public CreateTestEmulator emulator;

	@Parameters({ "DeviceName" })
	@BeforeTest
	public void beforeTest(String DeviceName) {
		System.out.println("Before Test 2");
		this.emulator = new CreateTestEmulator(DeviceName);
	}

	@Parameters({ "DeviceName" })
	@BeforeClass
	public void beforeClass(String DeviceName) {
		System.out.println("Before class 2");
		AppTest = new CreateTestSession(DeviceName);
	}

	@Test
	public void Step1_Verify_User_is_On_Application() {
		AppTest.LoginPage.verifyUserIsOnLoginPage();
	}

	@Test
	public void Step2_Verify_User_is_Able_To_Login() {

		AppTest.LoginPage.selectCountyFromDropDown("Bahamas");
		AppTest.LoginPage.enterTextOnNameField("Akash");
		AppTest.LoginPage.selectGenderButton("Male");
		AppTest.LoginPage.clickLetsShopButton();
		AppTest.ProductPage.verifyUserOnProductPage();
	}

	@Test
	public void Step3_Verify_Error_Message_On_Incorrect_Info() {
		AppTest.ProductPage.clickNaviagteBackButton();
		AppTest.LoginPage.verifyUserIsOnLoginPage();
		AppTest.LoginPage.enterTextOnNameField(" ");
		AppTest.LoginPage.clickLetsShopButton();
		AppTest.LoginPage.verifyErrorToEnterName();
	}

	@Test
	public void Step4_Verify_User_Able_To_Add_Product_To_Cart() {
		Step2_Verify_User_is_Able_To_Login();
		AppTest.ProductPage.verifyItemDisplayedOnProductPage("Air Jordan 9 Retro");
		AppTest.ProductPage.clickAddtoCartForItemOnProductPage("Air Jordan 9 Retro");
		AppTest.ProductPage.verifyCountOfProductAddToCartIcon("1");
		AppTest.ProductPage.clickAddToCartIcon();
		AppTest.CheckOut.verifyUserOnCartPage();
		AppTest.CheckOut.verifyProductIsDisplayedOnCartPage("Air Jordan 9 Retro");
	}

	@Test
	public void Step5_Verify_User_Able_To_Add_Multiple_Project_And_CheckOut_Ammount() {
		AppTest.CheckOut.navigateBackOnWindow();
		AppTest.ProductPage.verifyUserOnProductPage();
		AppTest.ProductPage.verifyItemDisplayedOnProductPage("Jordan Lift Off");
		AppTest.ProductPage.clickAddtoCartForItemOnProductPage("Jordan Lift Off");
		AppTest.ProductPage.verifyCountOfProductAddToCartIcon("2");
		AppTest.ProductPage.clickAddToCartIcon();
		AppTest.CheckOut.verifyUserOnCartPage();
		AppTest.CheckOut.verifyProductIsDisplayedOnCartPage("Air Jordan 9 Retro");
		AppTest.CheckOut.verifyProductIsDisplayedOnCartPage("Jordan Lift Off");
		AppTest.CheckOut.verifyNumberOfProductDisplayedOnCartPage("2");
		AppTest.CheckOut.verifyTotalAmountofProducts("$ 285.97");
	}

	@Test
	public void Step6_Verify_Price_Of_Product_On_Check_Out_Page() {
		AppTest.CheckOut.verifyPriceOfProductOnCheckOut("Air Jordan 9 Retro", "$170.97");

	}

	@Test
	public void Step7_Verify_Terms_And_Conditions_On_Check_Out_Page() {
		AppTest.CheckOut.LongPressTermsAndConditions();
		AppTest.CheckOut.verifyTermsAndConditionsPoupWindow();
		AppTest.CheckOut.clickCloseonTermsAndConditionsPoupWindow();
		AppTest.CheckOut.verifyTermsAndConditionsPoupWindowNotDisplayed();
	}

	@Test
	public void Step8_Click_Send_Me_Email_And_Verify_Satus() {
		AppTest.CheckOut.clickSendMeEmailCheckBox();
		AppTest.CheckOut.verifySendMeEmailCheckBoxState();
	}

	@Test
	public void Step9_Click_Visit_To_Website_Button() {
		AppTest.CheckOut.clickVisitToWebsiteButton();
	}

	@Test
	public void Step10_Switch_To_Web_View() {
		// AppTest.CheckOut.switchToWebView("WEBVIEW");
	}

	@AfterMethod
	public void onFailure(ITestResult result) {
		afterMethod(AppTest, result);
	}

	@AfterClass(alwaysRun = true)
	public void TerminnateSession() {
		AppTest.closeBrowserSession();
	}

	@Parameters({ "DeviceName" })
	@AfterTest(alwaysRun = true)
	public void CloseEmulator(String DeviceName) {
		System.out.println("After Test 2");
		emulator.stopEmulator(DeviceName);
	}
}
