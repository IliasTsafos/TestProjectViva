import java.util.ArrayList;
import java.util.Random;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class API_TestingTest {

	@Test
	//2nd Test case
	void validateOrderCodeLength() {

		RestAssured.baseURI = "https://demo.vivapayments.com/api";
		RequestSpecification httpRequest = RestAssured.given();
		JSONObject requestParams = new JSONObject();

		// Implementing the same details as TC1/UI testing

		String customerName = "Test Automation";
		String email = customerName.replaceAll(" ", "_");

		// create a string of all characters
		String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

		// create random string builder
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		StringBuilder sb3 = new StringBuilder();
		StringBuilder sb4 = new StringBuilder();
		StringBuilder sb5 = new StringBuilder();

		// create an object of Random class
		Random r = new Random();

		// specify length of random string
		int length = r.nextInt(7) + 1;
		int length2 = r.nextInt(7) + 1;
		int length3 = r.nextInt(7) + 1;
		int length4 = r.nextInt(7) + 1;
		int length5 = r.nextInt(7) + 1;

		for (int i = 0; i < length; i++) {
			// generate random index number
			int index = r.nextInt(alphabet.length());
			// get character specified by index
			// from the string
			char randomChar = alphabet.charAt(index);
			// append the character to string builder
			sb.append(randomChar);
		}

		for (int i = 0; i < length2; i++) {
			int index2 = r.nextInt(alphabet.length());
			char randomChar2 = alphabet.charAt(index2);
			sb2.append(randomChar2);
		}

		for (int i = 0; i < length3; i++) {
			int index3 = r.nextInt(alphabet.length());
			char randomChar3 = alphabet.charAt(index3);
			sb3.append(randomChar3);
		}
		for (int i = 0; i < length4; i++) {
			int index4 = r.nextInt(alphabet.length());
			char randomChar4 = alphabet.charAt(index4);
			sb4.append(randomChar4);
		}

		for (int i = 0; i < length5; i++) {
			int index5 = r.nextInt(alphabet.length());
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

		// finalize the variable for reference field
		String absoluteDay = Integer.toString(arl.get(0)) + Integer.toString(arl.get(1)) + Integer.toString(arl.get(2))
				+ Integer.toString(arl.get(3)) + Integer.toString(arl.get(4)) + Integer.toString(arl.get(5))
				+ customerNameInitial;

		requestParams.put("Customer Name", customerName);
		requestParams.put("amount", "12");
		requestParams.put("email", email + "@test.com");
		requestParams.put("Description", paymentDescription);
		requestParams.put("Reference", absoluteDay);

		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Basic Y2M4YWY0MmYtOTFkZS00MTE3LTlhOGMtMjQzZjc0NDdiYzljOkxnSyNLeA==");

		httpRequest.body(requestParams.toJSONString());
		System.out.println(requestParams);
		Response response = httpRequest.request(io.restassured.http.Method.POST, "/orders");

		String responseBody = response.getBody().asString();
		System.out.println(responseBody);

		// Extracting order code from the response
		String createdCode = response.jsonPath().getString("OrderCode");

		try {
			Assert.assertEquals(createdCode.length(), 16);
		} catch (AssertionError e) {
			System.out.println("Something went wrong with your order code");
			throw e;
		}
		System.out.println("Your order code has exactly 16 characters ! ");

	}

}
