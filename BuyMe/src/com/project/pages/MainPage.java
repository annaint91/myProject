package com.project.pages;

import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import com.project.city.BasePage;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import com.framework.utility.AppiumUtils;
import com.framework.utility.GeneralUtility;

public class MainPage extends BasePage {

	static final Logger log = LogManager.getLogger(MainPage.class.getName());
	static String callNumber;

	public MainPage(WebDriver driver) throws IOException {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	
	
	@SuppressWarnings("unchecked")
	public boolean loginUsingGoogle() throws Exception{
		
		//Skip
		WebElement skipButton= returnSlowElement(By.id("skipButton"));
		skipButton.click();
		
		//Register with Google
		WebElement googleSelection = returnSlowElement(By.id("googleButton"));
		googleSelection.click();
		
		//tap account
		GeneralUtility.Sleep(8);
		AppiumUtils.tap((AndroidDriver<MobileElement>) driver, 500, 1000);
		
		//Verify the main page loaded
		boolean isExist = IsObjectExist(By.id("walletTab"));
		return isExist;
			
	}
	
	
	@SuppressWarnings("unchecked")
	public boolean gettingToProfilePage(String item) throws Exception{
		
		//Profile page
		WebElement profilePage= returnSlowElement(By.id("profileTab"));
		profilePage.click();
		

		AppiumUtils.swipe((AndroidDriver<MobileElement>) driver,285, 1400, 285, 1100);
		AppiumUtils.swipe((AndroidDriver<MobileElement>) driver,285, 1200, 285, 900);
		AppiumUtils.swipe((AndroidDriver<MobileElement>) driver,285, 1100, 285, 700);
	
		
		
		WebElement itemButton= returnSlowElement(By.xpath("//*[@text='"+item+"']"));
		itemButton.click();

		
		String settings = driver.getPageSource();
		log.info(settings);
		//tap account
	
		//Verify the page loaded
		boolean isExist = IsObjectExist(By.xpath("//*[@text='"+item+"']"));
		return isExist;
			
	}
	
	
	@SuppressWarnings("unchecked")
	public boolean searchByCategory(String category, String business, String budget) throws Exception{
		
		//Category selection
		WebElement categoryButton= returnSlowElement(By.xpath("//*[@text='"+category+"']"));
		categoryButton.click();
		
		//Select Business
		WebElement buymeKosher = returnSlowElement(By.xpath("//*[@text='"+business+"']"));
		buymeKosher.click();
		
		//Insert budget
		WebElement budgetLimit = returnSlowElement(By.id("priceEditText"));
		fillText(budgetLimit, budget);
		
		//Click continue
		WebElement selection = returnSlowElement(By.id("purchaseButton"));
		selection.click();
		
		
		
		//tap account
		//GeneralUtility.Sleep(5);
		//AppiumUtils.tap((AndroidDriver<MobileElement>) driver, 500, 1000);
		
		
		boolean isExist = IsObjectExist(By.id("step1Text"));
		return isExist;
			
	}
	
	@SuppressWarnings("unchecked")
	public boolean senderReceiverInfo(String toGift, String event, String blessing, String sender) throws Exception{
		
		AppiumUtils.swipe((AndroidDriver<MobileElement>) driver,285, 1400, 285, 1100);
		AppiumUtils.swipe((AndroidDriver<MobileElement>) driver,285, 1200, 285, 900);
		AppiumUtils.swipe((AndroidDriver<MobileElement>) driver,285, 1100, 285, 700);
		AppiumUtils.swipe((AndroidDriver<MobileElement>) driver,285, 1000, 285, 600);
		
		
		//toEditText
		WebElement toEditText = returnSlowElement(By.id("toEditText"));
		fillText(toEditText, toGift);
		
		
		
		//Select event
		WebElement toEvent = returnSlowElement(By.xpath("//*[@text='מהו סוג האירוע? נעזור לכם לכתוב ברכה']"));
		click(toEvent);
		
		//Select event
		WebElement buymeKosher = returnSlowElement(By.xpath("//*[@text='"+event+"']"));
		buymeKosher.click();

		//blessEditText
		WebElement blessEditText = returnSlowElement(By.id("blessEditText"));
		fillText(blessEditText, blessing);
		
		
		//blessEditText
		WebElement userFrom = returnSlowElement(By.id("userFrom"));
		fillText(userFrom, sender);
		
		
		//continue
		WebElement bContinue = returnSlowElement(By.id("goNextButton"));
		click(bContinue);
	
		
		boolean isExist = IsObjectExist(By.id("timingTitle"));
		return isExist;
			
	}
	
	
	
	public boolean howToSendInfo(String sendOption, String address, String price) throws Exception{
		
//		AppiumUtils.swipe((AndroidDriver<MobileElement>) driver,285, 1400, 285, 1100);
//		AppiumUtils.swipe((AndroidDriver<MobileElement>) driver,285, 1200, 285, 900);
	
		
		List<WebElement> checkBox = driver.findElements(By.className("android.widget.CheckBox"));

		//continue
		if (sendOption.equals("mail")) {
			checkBox.get(2).click();
			WebElement mailEdit = returnSlowElement(By.xpath("//*[@text='מייל:']"));
			fillText(mailEdit, address);
		}
			
	
		AppiumUtils.swipe((AndroidDriver<MobileElement>) driver,285, 1100, 285, 700);
		AppiumUtils.swipe((AndroidDriver<MobileElement>) driver,285, 1000, 285, 600);
		
		
		//Select event  goNextButton
		WebElement bContinue = returnSlowElement(By.id("goNextButton"));
		click(bContinue);

	
		boolean isExist = IsObjectExist(returnSlowElement(By.xpath("//*[@text='"+ price +"']")));
		return isExist;
			
	}
	
	
	
	public boolean checkCard(String credit) throws Exception{
			
		WebElement my_wallet= driver.findElement(By.id("walletTab"));
		my_wallet.click();
		
		
		WebElement we = returnSlowElement(By.xpath("//*[@text='המתנות שלי']"));
	
		we.click();
		
		we = returnSlowElement(By.id("sendToText"));  
		we.click();
		
		boolean isExist = isTextAppearInWL(By.id("giftPrice"), credit);
		return isExist;
			
	}
	
	
	public boolean searchGift() throws Exception{
		Actions action = new Actions(driver);
		action.sendKeys(Keys.DOWN).build().perform();
		
		
		WebElement my_wallet= driver.findElement(By.id("search"));
		my_wallet.click();
		
		
		
		WebElement search_field= driver.findElement(By.id("edit_text_search"));
		search_field.sendKeys("לנדוור");
		
//		Actions action = new Actions(driver);
		action.sendKeys(Keys.ENTER).build().perform();
		
		
		WebElement gift_results= driver.findElement(By.id("giftName"));
		String str = gift_results.getText();
		
		if (str.contains("בוקר לנדוור זוגי ברשת קפה לנדוור"))
			return true;
		else
			return false;
		
		
			
	}
	
public boolean do_swipe(AndroidDriver<MobileElement> driver) throws Exception{
		
		WebElement we = returnSlowElement(By.xpath("//*[@text='המתנות שלי']"));
		AppiumUtils.swipe(driver,285, 1400, 285, 1100);
		AppiumUtils.swipe(driver,285, 1200, 285, 900);
		AppiumUtils.swipe(driver,285, 1100, 285, 700);
		AppiumUtils.swipe(driver,285, 1000, 285, 600);
		AppiumUtils.swipe(driver,285, 900, 285, 500);

		
		boolean isExist = IsObjectExist(By.xpath("//*[@text='גיפט קארד למתנות לידה']"));
		return isExist;
			
	}
	
	
	
	public boolean sendMailFromApplication(String search) throws Exception {
		boolean bVerify = false;

		// Begin flow of the login
		log.info("Begin the flow of send mail from application");
		try {

			driver.findElement(By.xpath("//*[@text='letter']")).click();
			GeneralUtility.Sleep(3);
			driver.findElement(By.xpath("//*[@text='מ\"מ ראש העיר']")).click();
			GeneralUtility.Sleep(3);
			driver.findElement(By.xpath("//*[@text='* הודעתך']")).click();
			GeneralUtility.Sleep(3);

			Actions act = new Actions(driver);
			// act.sendKeys(Keys.ESCAPE).build().perform();
			act.sendKeys("This is a test - Please ignor - Hagai Tregerman, QA").build().perform();
			// act.sendKeys(Keys.ESCAPE).build().perform();

			// מ"מ ראש העיר
			driver.findElement(By.xpath("//*[@text='מ\"מ ראש העיר']")).click();

			driver.findElement(By.xpath("//*[@text='שליחה']")).click();

			GeneralUtility.Sleep(20);

			act.sendKeys(Keys.ENTER).build().perform();

			bVerify = IsObjectExist((By.xpath("//*[@text='אנחנו פה בשבילכם']")));

			// return bVerify;

		} catch (TimeoutException te) {
			log.fatal("Exception received: ");
			te.printStackTrace();
			return false;
		} catch (Exception e) {
			log.fatal("Exception received: ");
			e.printStackTrace();
			return false;
		}
		if (!bVerify)
			log.error("Create call failed in verify fields process");

		return bVerify;
	}

	public boolean getIntoCall(String search) throws Exception {
		boolean bVerify = false;

		// Begin flow of the login
		log.info("Begin the flow of the Search from application");
		try {
			
			GeneralUtility.Sleep(10);
			Actions act = new Actions(driver);
			List<WebElement> elementsTwo = driver.findElements(By.className("android.view.View"));

			for (WebElement we: elementsTwo) {
				if(we.getText().contains("109")){
					we.click();
					break;	
				}
				
			}
			GeneralUtility.Sleep(2);
			driver.findElement(By.xpath("//*[@text='פתיחת פנייה למוקד 109']")).click();
			GeneralUtility.Sleep(2);
			
			driver.findElement(By.xpath("//*[contains(@text,'רחוב')]")).click();
			
			act.sendKeys("A").sendKeys(Keys.BACK_SPACE).sendKeys(Keys.ARROW_DOWN).sendKeys(Keys.ENTER).build().perform();
			GeneralUtility.Sleep(2);
			
			driver.findElement(By.xpath("//*[@text='שלומציון']")).click();
			GeneralUtility.Sleep(2);
			driver.findElement(By.xpath("//*[contains(@text,'בית')]")).click();
			GeneralUtility.Sleep(2);
			act.sendKeys("10").build().perform();
			
			GeneralUtility.Sleep(2);
			
			driver.findElement(By.xpath("//*[contains(@text,'תיאור המפגע')]")).click();
			act.sendKeys("Test Automation: " + GeneralUtility.getDate("dd/MM/yyyy") ).build().perform();
			GeneralUtility.Sleep(2);
			
			driver.findElement(By.xpath("//*[@text='פרטי הדיווח']")).click();
			GeneralUtility.Sleep(2);
			driver.findElement(By.xpath("//*[@text='שליחה']")).click();

			GeneralUtility.Sleep(10);

			WebElement we = driver.findElement(By.xpath("//*[contains(@text,'הפניה')]"));
			String callNumber = we.getText();
			
			callNumber = callNumber.replace("מס' הפניה: ", "");
			log.info("Call Number: " + callNumber);

			
			bVerify = ((callNumber.length()>12)&&(callNumber.contains("-")));

		} catch (TimeoutException te) {
			log.fatal("Exception received: ");
			te.printStackTrace();
			return false;
		} catch (Exception e) {
			log.fatal("Exception received: ");
			e.printStackTrace();
			return false;
		}
		if (!bVerify)
			log.error("Create call failed in verify fields process");

		return bVerify;
	}

	public boolean searchFromApplication(String search) throws Exception {
		boolean bVerify = false;

		// Begin flow of the login
		log.info("Begin the flow of the Search from application");
		try {

			// WebElement we = driver.findElement(By.xpath("//*[@text='פורטל החינוך']"));

//	C:\share\SimpleChat\files\jpEg.jpg

			driver.findElement(By.xpath("//*[@text='חפש']")).click();
			GeneralUtility.Sleep(3);
			driver.findElement(By.className("android.widget.EditText")).sendKeys(search);

			GeneralUtility.Sleep(3);
			driver.findElement(By.xpath("//*[@text='search']")).click();

			GeneralUtility.Sleep(5);

			click(By.xpath("//*[@resource-id='searchItem'][@index=3]"));
			GeneralUtility.Sleep(5);
			click(By.xpath("//*[@text='פתיחה']"));

			GeneralUtility.Sleep(15);
			// bVerify =
			// IsObjectExist(By.xpath("//*[@id=\"selectedElementContainer\"]/div//td[text()='com.adobe.reader']"));

			String str = driver.getPageSource();
			bVerify = str.contains("המוסד לביטוח ל");

			// return bVerify;

		} catch (TimeoutException te) {
			log.fatal("Exception received: ");
			te.printStackTrace();
			return false;
		} catch (Exception e) {
			log.fatal("Exception received: ");
			e.printStackTrace();
			return false;
		}
		if (!bVerify)
			log.error("Create call failed in verify fields process");

		return bVerify;
	}

	public boolean getIntoCaseStatus() throws Exception {
		boolean bVerify = false;

		// Actions act = new Actions(driver);

		// Begin flow of the login
		log.info("Begin the flow of the check case status");
		try {
			GeneralUtility.Sleep(5);

			bVerify = IsObjectExist(By.xpath(
					"//android.widget.FrameLayout//android.view.View[1]//android.view.View[2]/android.view.View[2]/android.view.View[3]/android.view.View[1]//android.view.View/android.view.View[5]"));
			if (bVerify)
				driver.findElement(By.xpath(
						"//android.widget.FrameLayout//android.view.View[1]//android.view.View[2]/android.view.View[2]/android.view.View[3]/android.view.View[1]//android.view.View/android.view.View[5]"))
						.click();
			else
				throw new Exception("Error in finding element 1");
			// WebElement we =
			// driver.findElement(By.xpath("//android.widget.FrameLayout//android.view.View[1]//android.view.View[2]/android.view.View[2]/android.view.View[3]/android.view.View[1]//android.view.View/android.view.View[5]"));
			GeneralUtility.Sleep(5);

			Actions action = new Actions(driver);
			action.sendKeys(Keys.ARROW_DOWN).build().perform();

			GeneralUtility.Sleep(2);

//			Actions action1 = new Actions(driver);
//			action1.sendKeys(Keys.ARROW_DOWN).build().perform();

			bVerify = IsObjectExist(By.xpath("//*[@text='למעקב הפניות שלי במוקד']"));

			if (bVerify)
				driver.findElement(By.xpath("//*[@text='למעקב הפניות שלי במוקד']")).click();
			else
				throw new Exception("Error in finding element");

			GeneralUtility.Sleep(30);
			String str = driver.getPageSource();
			bVerify = str.contains("עבור טלפון נייד");

			// return bVerify;

		} catch (TimeoutException te) {
			log.fatal("Exception received: ");
			te.printStackTrace();
			return false;
		} catch (Exception e) {
			log.fatal("Exception received: ");
			e.printStackTrace();
			return false;
		}
		if (!bVerify)
			log.error("Create call failed in verify fields process");

		return bVerify;
	}

	public String returnCallNumber() {
		return callNumber;
	}

	public boolean deleteCall(String callNumber) throws Exception {

		// Verify the call number
		driver.switchTo().defaultContent();
		driver.switchTo().frame("contentIFrame1");
		boolean bVerify = verifyWLContainsText(By.xpath("//*[@id=\"pws_casenumber\"]/div[1]/span"), callNumber);

		// בדיקת מנעול
		if (bVerify) {
			bVerify = IsObjectExist(By.xpath("//*[@id=\"pws_neighborhoodid\"]/span/img"));
			bVerify = IsObjectExist(By.xpath("//*[@id=\"pws_public_cl\"]/img"));
			bVerify = IsObjectExist(By.xpath("//*[@id=\"pws_casenumber\"]/span/img"));

		}

		driver.switchTo().defaultContent();

		if (bVerify) {

			Actions act = new Actions(driver);
			act.doubleClick(driver.findElement(
					By.xpath("//*[@id=\"incident|NoRelationship|Form|Mscrm.Form.incident.Delete\"]/span/a/img")))
					.build().perform();

			GeneralUtility.Sleep(2);
			driver.switchTo().frame("InlineDialog_Iframe");

			act = new Actions(driver);
			act.click(driver.findElement(By.xpath("//*[@id=\"butBegin\"]/div"))).build().perform();
			// clickJS(By.xpath("//li[@id='incident|NoRelationship|Form|Mscrm.Form.incident.Delete']"));
		}

		GeneralUtility.Sleep(4);
		driver.switchTo().defaultContent();
		driver.switchTo().frame("contentIFrame0");
		bVerify = verifyWLContainsText(By.xpath("//*[@id=\"gridBodyTable\"]/tbody/tr[2]/td"),
				"אין רשומות פניה זמינות בתצוגה זו");

		// Click on CRMDynamics logo
		driver.switchTo().defaultContent();
		clickJS(By.id("navTabLogoTextId"));

		return bVerify;

	}

	public boolean createCall2() throws InterruptedException, IOException {
		boolean createCall = false;

		// Begin flow of the login
		log.info("Begin the flow of the login");
		try {

			click(By.id("homeButtonImage"));
			GeneralUtility.Sleep();
			clickJS(By.xpath("//a[@id='CS']/span[@class='navActionButtonIconContainer']//img[@alt='שירות']"));
			clickJS(By.xpath("//a[@id='CS']/span[@class='navActionButtonIconContainer']//img[@alt='שירות']"));
			GeneralUtility.Sleep();
			click(By.xpath("//*[@id=\"nav_cases\"]"));
			createCall = IsObjectExist(By.id("crmGrid_divDataArea"));

			createCall = true;
		} catch (TimeoutException te) {
			log.fatal("Exception received: ");
			te.printStackTrace();
			return false;
		} catch (Exception e) {
			log.fatal("Exception received: ");
			e.printStackTrace();
			return false;
		}

		return createCall;
	}

	public boolean logout() throws Exception {
		boolean bLogout = false;

		click(By.xpath("\\"));
		log.info("Exit application");

		bLogout = IsObjectExist(By.xpath("\\test"));

		return bLogout;
	}

	public void formClick(WebElement we, String sValue) throws Exception {
		Actions act = new Actions(driver);
		clickJS(we);
		GeneralUtility.Sleep(2);
		act.sendKeys(we, sValue).build().perform();
		GeneralUtility.Sleep();
		act.sendKeys(we, Keys.ENTER).build().perform();
	}

	public void formSendKeys(WebElement we, String sValue) throws Exception {
		Actions act = new Actions(driver);
		clickJS(we);
		GeneralUtility.Sleep(2);
		act.sendKeys(we, sValue).build().perform();
		GeneralUtility.Sleep();
		act.sendKeys(we, Keys.ENTER).build().perform();
	}
}
