package AppiumTests.automation;
import org.testng.ITestResult;

public class UtilityClass {
	
	
	
	

	public void afterMethod(CreateTestSession AppTest,ITestResult result) {
		if (result.getStatus() == ITestResult.FAILURE) {
			AppTest.Custom.AttachScreenshortToExtentReport(result.getMethod().getMethodName());
		}

	}

}
