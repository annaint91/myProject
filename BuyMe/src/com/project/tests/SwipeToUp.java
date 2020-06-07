package com.project.tests;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;

public class SwipeToUp {
	
	static AndroidDriver<MobileElement> driver;
	
	public static void main(String[] args) throws MalformedURLException, InterruptedException {
		DesiredCapabilities cap=new DesiredCapabilities();
	     //cap.setCapability("deviceName", "emulator-5554");
	     //cap.setCapability("udid", "emulator-5554");
	     //cap.setCapability("appPackage", "io.appium.android.apis");
	     //cap.setCapability("appActivity", "io.appium.android.apis.ApiDemos");
	    cap.setCapability("platformName", "Android");
	    cap.setCapability("platformVersion", "8.0");
	     
		cap.setCapability("deviceName", "5200b8c24f8ec4b9");
		cap.setCapability("automationName", "UiAutomator2");
		cap.setCapability("app", "C:\\apk\\BUYME-3.3.1.97.apk");
		cap.setCapability("noReset", "true");
	    driver=new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), cap);
	    driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        
	    
	    Point value = null;
		value = driver.findElement(By.id("t_title")).getLocation(); 
	    swipeDown(400, value);
	    
		value = driver.findElement(By.xpath("//*[@text='גיפט קארד לארוחת בוקר ובתי קפה']")).getLocation();

	    swipeDown(400, value);
	    
	    
	    swipeDown(300, value);
		value = driver.findElement(By.xpath("//*[@text='גיפט קארד למתנות לידה']")).getLocation();

		
		swipeDown(200, value);
		Thread.sleep(2000);
		value = driver.findElement(By.xpath("//*[@text='גיפט קארד למלונות יוקרה']")).getLocation();

		swipe(285, 1400, 285, 1100);
		//Thread.sleep(2000);
		swipe(285, 1200, 285, 900);
		//Thread.sleep(2000);
		swipe(285, 1100, 285, 700);
		//Thread.sleep(2000);
		swipe(285, 1000, 285, 600);
		//Thread.sleep(2000);
		swipe(285, 900, 285, 500);
		//Thread.sleep(2000);
		swipe(285, 800, 285, 400);
		//Thread.sleep(2000);
		swipe(285, 700, 285, 300);
		//Thread.sleep(2000);
		swipe(285, 600, 285, 200);
	    System.out.println("swipe action terminate");
	}
	
	public static void swipeDown(int pixelsToSwipe, Point value) {

		  try {
		   //Point value = null;
		   //value = driver.findElement(By.id("t_title")).getLocation();
		   int x = value.x;
		   int y = value.y;
		   int y1 = value.y + pixelsToSwipe;

		   swipe(x,y1,x,y);
		   
		  } catch(Exception e) {
		   System.out.println(e.getMessage());
		  }

		 }
	
	public static void swipe(int fromX,int fromY,int toX,int toY) {
		  
		  TouchAction action = new TouchAction(driver);
		  action.press(PointOption.point(fromX,fromY)).waitAction(new WaitOptions().withDuration(Duration.ofMillis(4000))) //you can change wait durations as per your requirement
		  .moveTo(PointOption.point(toX, toY)).release().perform();
		 }
		   

		}


