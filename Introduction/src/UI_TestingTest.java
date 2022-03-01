import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UI_TestingTest {

	@Test
	//1st Test case
	public void mainTest() throws InterruptedException {

		// Setting up chromeDriver
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\ilias.tsafos\\Documents\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();

		// Used it because Selenium throws ElementNotVisibleException exception
		// doesn't creates issues on performance because when selenium will find the web
		// element , it no longer waits for all the seconds to run
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// Step 1 : Open url
		String url = "https://demo-accounts.vivapayments.com/Account/Login?ReturnUrl=%2Fconnect%2Fauthorize%2Fcallback%3Fclient_id%3D8muh0tyiezip7tu7w00sszp.selfcare.apps.vivapayments.com%26redirect_uri%3Dhttps%253A%252F%252Fdemo.vivapayments.com%252F%26response_type%3Dcode%2520id_token%26scope%3Dopenid%2520roles%26state%3DOpenIdConnect.AuthenticationProperties%253DRtMx5h9XmfJR_ct7scuofXhDy3YitXUBtcCqxNz1yELp3HxIoZxZIujsOUO1mkZNVtwgWan_aFdP4VbDRLXyTaSFtiaosDKAxuRrFDiB2YFk-TXt7ceSsUf4Wixdesl6I-VFRhEU6TYktbRQ-QIGLx1AYJa4-JiJ1qX5IKtoboPhVGNxdmDf6tCIaWOyx75tC6SeAKkE4gJls7xPFkPjhHCmo4b2W5Ij22oF6qdnqfI%26response_mode%3Dform_post%26ui_locales%3Den%26nonce%3D637811126225910339.N2UyMWM1MWItMTFkNy00ZjBmLTkxNjgtOTA1MTZiZTQzNzYwMjA4ZDM1NjEtMGU5Zi00ZmZiLTk0ZjEtZmY5NjAzYWM3NTIx%26x-client-SKU%3DID_NET461%26x-client-ver%3D5.3.0.0";
		driver.get(url);

		// Step 2 : Check if Greece is the default selected country in the dropdown. If not, then select it
		WebElement selectCountry = driver.findElement(By.id("login-country-code"));
		selectCountry.click();
		WebElement checkDefault = driver.findElement(By.xpath(
				"/html[1]/body[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]"));
		
		// Checking if Greece is default country
		boolean Default = checkDefault.getAttribute("id").contains("md-input-90u03z3mm");
		try {
			Assert.assertEquals(Default, false);
			driver.findElement(By.xpath("//body/div[5]/div[1]/ul[1]/div[1]/li[11]/button[1]")).click();
		} catch (AssertionError e) {
			System.out.println("Greece is the default language");
			throw e;
		}
		System.out.println("Greece selected successfully");

		// Step 3 : Filling in username/pass combination
		WebElement phoneNumber = driver.findElement(
				By.xpath("/html[1]/body[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[3]/div[1]/div[1]/div[2]/input[1]"));
		phoneNumber.click();
		phoneNumber.sendKeys("6921111111");

		WebElement password = driver.findElement(
				By.xpath("/html[1]/body[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[3]/div[1]/div[2]/input[1]"));
		password.click();
		password.sendKeys("qwe$123");

		WebElement loginButton = driver.findElement(By.xpath(
				"/html[1]/body[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[3]/div[1]/div[4]/button[1]/div[1]/div[1]"));
		loginButton.click();

		// Step 4 : Validating that continue button is disabled
		WebElement continueButton = driver
				.findElement(By.xpath("/html[1]/body[1]/div[4]/div[1]/div[1]/div[1]/div[3]/button[1]"));

		boolean isEnabled = continueButton.isEnabled();
		
		// TestNG validation for continue button
		try {
			Assert.assertEquals(isEnabled, false);
		} catch (AssertionError e) {
			System.out.println("Button is enabled");
			throw e;
		}
		System.out.println("Button is disabled");

		// Step 5 : Use 111111 as 6-digit code
		WebElement sixDigitcode = driver
				.findElement(By.xpath("/html[1]/body[1]/div[4]/div[1]/div[1]/div[1]/div[2]/div[2]/input[1]"));
		sixDigitcode.sendKeys("111111");
		continueButton.click();

		// Step 6 : Pick the Test Automation business
		WebElement businessBlueButton = driver.findElement(By.xpath(
				"/html[1]/body[1]/div[3]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[3]/div[1]/button[1]"));
		businessBlueButton.click();

		// Step 7 : Click request money button
		WebElement requestMoneyButton = driver
				.findElement(By.xpath("/html[1]/body[1]/main[1]/div[6]/div[1]/a[2]/div[1]/div[1]"));
		requestMoneyButton.click();

		// Step 8 : Validate that revenue is 0,00
		WebElement revenueAmount = driver.findElement(By.xpath(
				"/html[1]/body[1]/main[1]/div[6]/div[1]/div[3]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/h3[1][contains(text(),'€ 0,00')]"));
		String amount = revenueAmount.getText();
		try {
			Assert.assertEquals(amount, "€ 0,00");
		} catch (AssertionError e) {
			System.out.println("The amount isn't 0,00");
			throw e;
		}
		System.out.println("Success Validation : The amount is indeed 0,00");

		// Step 9 : Click New Notification button
		WebElement notificationButton = driver
				.findElement(By.xpath("/html[1]/body[1]/main[1]/div[6]/div[2]/div[1]/div[1]/div[2]/div[1]/a[1]"));
		notificationButton.click();

		// Step 10 : Filling in reference,amount,description,customer name , customer
		// Filling in customer name
		WebElement name = driver.findElement(By.xpath(
				"/html[1]/body[1]/main[1]/div[6]/div[2]/form[1]/div[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[1]/input[1]"));
		name.click();
		String customerName = "Test Automation";
		name.sendKeys(customerName);

		// Filling in reference field
		WebElement reference = driver.findElement(By.xpath(
				"/html[1]/body[1]/main[1]/div[6]/div[2]/form[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]"));
		reference.click();

		Random r = new Random();
		int day1 = r.nextInt(4);
		ArrayList<Integer> arl = new ArrayList<Integer>();
		arl.add(day1);

		if (day1 == 0) {
			int day2 = r.nextInt(9) + 1;
			arl.add(day2);
		} else if (day1 == 1 || day1 == 2) {
			int day2 = r.nextInt(10);
			arl.add(day2);
		} else if (day1 == 3) {
			int day2 = r.nextInt(2);
			arl.add(day2);
		}

		int monthFirstDigit = r.nextInt(2);
		arl.add(monthFirstDigit);

		if (monthFirstDigit == 0) {
			int monthSecondDigit = r.nextInt(9) + 1;
			arl.add(monthSecondDigit);
		} else if (monthFirstDigit == 1) {
			int monthSecondDigit = r.nextInt(3);
			arl.add(monthSecondDigit);
		}
		int year = 202;
		int yearLastDigit = r.nextInt(10);
		arl.add(year);
		arl.add(yearLastDigit);

		// Splitting customer name into 2 strings
		String[] newStr = customerName.split("\\s+");
		String firstName = newStr[0];
		String lastName = newStr[1];

		// Getting the first char of firstName and lastName
		char ch1 = firstName.charAt(0);
		char ch2 = lastName.charAt(0);
		String customerNameInitial = "_" + Character.toString(ch1) + Character.toString(ch2);

		// Finalize the variable for reference field
		String absoluteDay = Integer.toString(arl.get(0)) + Integer.toString(arl.get(1)) + Integer.toString(arl.get(2))
				+ Integer.toString(arl.get(3)) + Integer.toString(arl.get(4)) + Integer.toString(arl.get(5))
				+ customerNameInitial;
		reference.sendKeys(absoluteDay);
		
		System.out.println(absoluteDay);


		// Filling in Description
		// Create a string of all characters
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		// Create random string builder
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		StringBuilder sb3 = new StringBuilder();
		StringBuilder sb4 = new StringBuilder();
		StringBuilder sb5 = new StringBuilder();

		// Create an object of Random class
		Random random = new Random();

		// Specify length of random string
		int length = r.nextInt(7) + 1;
		int length2 = r.nextInt(7) + 1;
		int length3 = r.nextInt(7) + 1;
		int length4 = r.nextInt(7) + 1;
		int length5 = r.nextInt(7) + 1;

		for (int i = 0; i < length; i++) {
			// Generate random index number
			int index = random.nextInt(alphabet.length());
			// Get character specified by index
			// from the string
			char randomChar = alphabet.charAt(index);
			// Append the character to string builder
			sb.append(randomChar);
		}

		for (int i = 0; i < length2; i++) {
			int index2 = random.nextInt(alphabet.length());
			char randomChar2 = alphabet.charAt(index2);
			sb2.append(randomChar2);
		}

		for (int i = 0; i < length3; i++) {
			int index3 = random.nextInt(alphabet.length());
			char randomChar3 = alphabet.charAt(index3);
			sb3.append(randomChar3);
		}
		for (int i = 0; i < length4; i++) {
			int index4 = random.nextInt(alphabet.length());
			char randomChar4 = alphabet.charAt(index4);
			sb4.append(randomChar4);
		}

		for (int i = 0; i < length5; i++) {
			int index5 = random.nextInt(alphabet.length());
			char randomChar5 = alphabet.charAt(index5);
			sb5.append(randomChar5);
		}

		String randomString = sb.toString();
		String randomString2 = sb2.toString();
		String randomString3 = sb3.toString();
		String randomString4 = sb4.toString();
		String randomString5 = sb5.toString();

		// Finalize variable for description field
		String paymentDescription = randomString + " " + randomString2 + " " + randomString3 + " " + randomString4 + " "
				+ randomString5;
		System.out.println("The 5 random strings that you created are : "+ paymentDescription);

		WebElement desc = driver.findElement(By.xpath(
				"/html[1]/body[1]/main[1]/div[6]/div[2]/form[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/input[1]"));
		desc.click();
		desc.sendKeys(paymentDescription);

		// Filling in amount
		WebElement amountTotal = driver.findElement(By.xpath(
				"/html[1]/body[1]/main[1]/div[6]/div[2]/form[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/input[1]"));
		amountTotal.click();

		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("document.getElementById('OrderAmount').setAttribute('value', '12')");

		// Filling in Customer e-mail
		WebElement email = driver.findElement(By.xpath(
				"/html[1]/body[1]/main[1]/div[6]/div[2]/form[1]/div[1]/div[1]/div[1]/div[1]/div[4]/div[1]/div[1]/input[1]"));
		email.click();

		String newString = customerName.replaceAll(" ", "_");
		email.sendKeys(newString + "@test.com");

		// Step 11 : Validate that this is not a repeated payment
		WebElement repeatedPayment = driver.findElement(By.xpath(
				"/html[1]/body[1]/main[1]/div[6]/div[2]/form[1]/div[1]/div[1]/div[1]/div[1]/div[5]/div[1]/div[4]/div[1]/div[1]"));
		boolean isRepeated = repeatedPayment.getAttribute("class").contains("checked");
		// Validation of not repeated
		try {
			Assert.assertEquals(isRepeated, false);
		} catch (AssertionError e) {
			System.out.println("The payment is repeated");
			throw e;
		}
		System.out.println("Success validation : The payment isn't repeated");

		// Step 12 : Click on send notification
		WebElement sendNotification = driver.findElement(By.xpath(
				"/html[1]/body[1]/main[1]/div[6]/div[2]/form[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/button[2]"));
		sendNotification.click();

		// Step 13 : Validation of the details that i filled  in step 10
		WebElement info = driver.findElement(By.xpath(
				"/html[1]/body[1]/main[1]/div[6]/div[2]/div[2]/div[1]/div[1]/div[1]/div[4]/div[1]/table[1]/tbody[1]/tr[1]/td[8]/a[1]"));
		info.click();

		Thread.sleep(1000); 
		
		// Validating name of customer
		WebElement validationOfCustomerName = driver.findElement(By.xpath(
				"/html[1]/body[1]/main[1]/div[6]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/table[1]/tbody[1]/tr[3]/td[2]"));
		try {
			Assert.assertEquals(validationOfCustomerName.getText(), "Test Automation");
		} catch (AssertionError e) {
			System.out.println("Validation of customer name Failed");
			throw e;
		}
		System.out.println("Success validation of Customer name");

		// Validating amount
		WebElement validationOfAmount = driver.findElement(By.xpath(
				"/html[1]/body[1]/main[1]/div[6]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/table[1]/tbody[1]/tr[9]/td[2]"));
		try {
			Assert.assertEquals(validationOfAmount.getText(), "12,00");
		} catch (AssertionError e) {
			System.out.println("Validation of Amount Failed");
			throw e;
		}
		System.out.println("Success validation of Amount");

		// Validating Description
		WebElement validationOfDescription = driver.findElement(By.xpath(
				"/html[1]/body[1]/main[1]/div[6]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/table[1]/tbody[1]/tr[6]/td[2]"));
		try {
			Assert.assertEquals(validationOfDescription.getText(), paymentDescription);
		} catch (AssertionError e) {
			System.out.println("Validation of Description Failed");
			throw e;
		}
		System.out.println("Success validation of Description");

		// Validating email
		WebElement validationOfEmail = driver.findElement(By.xpath(
				"/html[1]/body[1]/main[1]/div[6]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/table[1]/tbody[1]/tr[4]/td[2]"));
		try {
			Assert.assertEquals(validationOfEmail.getText(), newString + "@test.com");
		} catch (AssertionError e) {
			System.out.println("Validation of email Failed");
			throw e;
		}
		System.out.println("Success validation of Email");

		// Validating Merchant Reference
		WebElement validationReference = driver.findElement(By.xpath(
				"	/html[1]/body[1]/main[1]/div[6]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/table[1]/tbody[1]/tr[5]/td[2]"));
		try {
			Assert.assertEquals(validationReference.getText(), absoluteDay);
		} catch (AssertionError e) {
			System.out.println("Validation of Merchant Reference Failed");
			throw e;
		}
		System.out.println("Success validation of Merchant Reference");

		WebElement validationOforderCode = driver.findElement(By.xpath(
				"/html[1]/body[1]/main[1]/div[6]/div[3]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/table[1]/tbody[1]/tr[1]/td[2]"));
		try {
			Assert.assertEquals(validationOforderCode.getText().length(), 16);
		} catch (AssertionError e) {
			System.out.println("Validation of order code failed");
			throw e;
		}
		
        //storing  order code to a variable so i can match it for the next step
		String ordCode = validationOforderCode.getText();
	

		WebElement closeInfoPage = driver
				.findElement(By.xpath("//body/main[1]/div[6]/div[3]/div[1]/div[1]/div[1]/button[1]"));
		closeInfoPage.click();
		
		Thread.sleep(1000); 
		
		// Step 14 : Click On Preview Button
		WebElement previewButton = driver.findElement(By.xpath(
				"/html[1]/body[1]/main[1]/div[6]/div[2]/div[2]/div[1]/div[1]/div[1]/div[4]/div[1]/table[1]/tbody[1]/tr[1]/td[8]/a[2]"));
		
		//Extracting url from the preview button , so i can use and verify it in the next step
		String link = previewButton.getAttribute("href");
		previewButton.click();
		
		       
		//Step 15 : Validate that you are redirected in the redirect checkout page
		Thread.sleep(1000); 
	
		driver.get(link);
		
		Thread.sleep(5000); 
		
		String currentURL = driver.getCurrentUrl();
		
		try {
			Assert.assertEquals(currentURL, "https://demo.vivapayments.com/web2?ref="+ordCode+"&lang=el-GR");
		} catch (AssertionError e) {
			System.out.println("Something went wrong with your redirect url page");
			throw e;
		}
		System.out.println("You redirected correctly to the checkout page");
		
		//Step 16 : Validate that the amount of payment is correct
		WebElement finalPaymentAmount = driver.findElement(By.xpath(
				"/html[1]/body[1]/div[1]/div[1]/main[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[3]/div[2]/form[1]/div[1]/div[1]/div[3]/div[1]/div[1]/div[1]/button[1]/span[1]/span[1]/span[2]"));
		
		//Validation of finalPaymentAmount
		try {
			Assert.assertEquals(finalPaymentAmount.getText(), "12,00");
		} catch (AssertionError e) {
			System.out.println("Validation of finalPaymentAmount Failed");
			throw e;
		}
		System.out.println("Success validation of final Payment amount");

	}
}
