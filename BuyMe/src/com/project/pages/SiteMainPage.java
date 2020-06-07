package com.project.pages;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.touch.TouchActions;
import org.openqa.selenium.support.PageFactory;

import com.project.city.BasePage;
import com.project.city.MobileActions;
import com.project.utility.ProjectUtility;

import io.appium.java_client.PerformsTouchActions;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.TapOptions;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.MultiTouchAction;


import com.framework.utility.GeneralUtility;
import com.framework.utility.RobotUtils;

public class SiteMainPage extends BasePage {

	static final Logger log = LogManager.getLogger(SiteMainPage.class.getName());
	static String callNumber;
	MobileActions mobileActions;

	public SiteMainPage(WebDriver driver) throws IOException {
		super(driver);
		PageFactory.initElements(driver, this);
		}

	
	
	
	public boolean getIntoRegistration(String first_name, String mail,String password1, String password2 ) throws Exception {
		boolean bVerify = false;
		
		
		// Begin flow of the registration
		click(By.xpath("//a/span[text()='הרשמה']"));
		click(By.xpath("//div/div[1]/div/div/div[3]/p/span"));
		
		//Insert Registration details
		clickJS(By.xpath("//img[@src='/images/icon_user_black.svg']"));
		Actions action = new Actions(driver);
		action.sendKeys(Keys.TAB).sendKeys(first_name).build().perform();
		action.sendKeys(Keys.TAB).sendKeys(mail).build().perform();
		action.sendKeys(Keys.TAB).sendKeys(password1).build().perform();
		action.sendKeys(Keys.TAB).sendKeys(password2).build().perform();
		
		
		//Register
		click(By.xpath("//div//button[@type='submit'][contains(text(),'הרשמה')]"));

		//Verify the text appear in page
		bVerify = IsObjectExist(By.xpath("//div//button[@type='submit'][contains(text(),'הרשמה')]"));
		if (!bVerify)
			log.error("Failed Registration");

		return bVerify;
	}

	public boolean getIntoSystem(String mail, String password, String limit, String area, String catagory) throws Exception {
		boolean bVerify = false;
		
		
		// Begin flow of the login google
		click(By.xpath("//a/span[text()='כניסה']"));
		fillText(By.xpath("//input[@placeholder='מייל']"), mail);
		WebElement we = driver.findElement(By.xpath("//input[@placeholder='סיסמה']"));
		fillText(By.xpath("//input[@placeholder='סיסמה']"), password);
		GeneralUtility.Sleep(2);
		
		we.submit();
		GeneralUtility.Sleep();
		
		// limit search
		click(By.xpath("//form/div/a[@class='chosen-single']/span[text()='סכום']"));
		GeneralUtility.Sleep(2);
		click(By.xpath("//div/ul/li[contains(text(),'"+ limit +"')]"));
		//300-499 ש"ח
		
	
		// Area search
		GeneralUtility.Sleep();
		click(By.xpath("//form/div/a/span[text()='אזור']"));
		GeneralUtility.Sleep(2);
		click(By.xpath("//div/ul/li[text()='"+area+"']"));
		//מרכז
			
		
		// Category search
		click(By.xpath("//form//a/span[text()='קטגוריה']"));
		GeneralUtility.Sleep(2);
		click(By.xpath("//div/ul/li[text()='"+ catagory +"']"));	
		click(By.xpath("//form/a[text()='תמצאו לי מתנה']"));
		//מתנות לבית
		
		//Assert		
		bVerify = IsObjectExist(By.xpath("//span[text()='תוצאות חיפוש']"));
		String new_url = driver.getCurrentUrl();
		
		//Assert URL Changed
		if (bVerify) {
			if (new_url.contains("category=204")&&(new_url.contains("region=11")))
				bVerify = true;
			else
				bVerify = false;

		}
		
		//Verify text in new page
		if (bVerify) {
			bVerify = IsObjectExist(By.xpath("//div/div[text()='מארז קלאסי- כולל משלוח']"));
		}
		
		
		if (!bVerify)
			log.error("login and search a present");
		return bVerify;
	}

	
	public boolean getIntoSystem_negative(String mail, String password) throws Exception {
		boolean bVerify = false;
		
		
		// Begin flow of the login google
		click(By.xpath("//a/span[text()='כניסה']"));
		fillText(By.xpath("//input[@placeholder='מייל']"), mail);
		WebElement we = driver.findElement(By.xpath("//input[@placeholder='סיסמה']"));
		fillText(By.xpath("//input[@placeholder='סיסמה']"), password);
		GeneralUtility.Sleep(2);
		
		we.submit();
		GeneralUtility.Sleep();
		bVerify = IsObjectExist(By.xpath("//*[@id=\"parsley-id-12\"]/li[text()='כל המתנות מחכות לך! אבל קודם צריך מייל וסיסמה']"));
		
		
			
		if (!bVerify)
			log.error("login and search a present");
		return bVerify;
	}

	
	public boolean chooseGiftScreen(String limit, String area, String catagory) throws Exception {
		boolean bVerify = false;
		
		
		// Begin flow of the login google
		click(By.xpath("//div/div[text()='מארז קלאסי- כולל משלוח']"));
		
		//Verify the color of the text
		bVerify = IsObjectExist(By.cssSelector(" li:nth-child(2) > div > div.text > div.step-title.highlighted"));
		
		WebElement we = driver.findElement(By.cssSelector(" li:nth-child(2) > div > div.text > div.step-title.highlighted"));
		String rgbFormat = we.getCssValue("color");
		
		if (bVerify) bVerify = rgbFormat.equals("rgba(250, 180, 66, 1)");
	
		
		if (!bVerify)
			log.error("Failed to choose gift");
		return bVerify;
	}

	
	public boolean chooseGiftScreen(int number) throws Exception {
		boolean bVerify = false;
		
		
		// Begin flow of the login google
		switch (number) {
		case 1:
			click(By.xpath("//div/div[text()='מארז קלאסי- כולל משלוח']"));
			break;
		}
		
		
		WebElement we = driver.findElement(By.cssSelector(" li:nth-child(2) > div > div.text > div.step-title.highlighted"));
		String rgbFormat = we.getCssValue("color");
		
		bVerify = rgbFormat.equals("rgba(250, 180, 66, 1)");
		
		
		
		if (!bVerify)
			log.error("Failed select gift");
		return bVerify;
	}
	
	public boolean senderReceiverInformationScreen(String reciver, String sender, String blessing, String mail) throws Exception {
		boolean bVerify = false;
		
		
		
		fillText(By.xpath("//input[@data-parsley-required-message='מי הזוכה המאושר? יש להשלים את שם המקבל/ת']"), reciver);
		fillText(By.xpath("//input[@data-parsley-required-message='למי יגידו תודה? שכחת למלא את השם שלך']"), sender);
		click(By.xpath("//*/a/span[text()='לאיזה אירוע?']"));
		click(By.xpath("//div/ul/li[text()='יום הולדת']"));
		
		fillText(By.xpath("//label/textarea"), blessing);
		
		GeneralUtility.Sleep();
		Actions action = new Actions(driver);
		action.sendKeys(Keys.PAGE_DOWN).build().perform();
		GeneralUtility.Sleep();

		
		clickJS(By.xpath("//div[2]/div/label[1]/input"));
		GeneralUtility.Sleep(3);

		RobotUtils.typeTextSubmit("c:\\data\\hb.jpg");
		GeneralUtility.Sleep(3);

		
		click(By.cssSelector(" span > span.icon.icon-envelope"));
		fillText(By.xpath("//div/div/input[@type=\"email\"]"), "test@gmail.com");
		click(By.xpath("//button[text()='שמירה']"));
		click(By.xpath("//button[text()='תשלום']"));
		
		
		bVerify = IsObjectExist(By.xpath("//*[@id=\"step-form-wrapper\"]/ul/li[1]/button[text()='כרטיס אשראי']"));
		
		
		
		if (!bVerify)
			log.error("Failed setting information: send receive");
		return bVerify;
	}

}
