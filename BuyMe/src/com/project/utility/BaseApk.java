package com.project.utility;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;


public class BaseApk {
	
	public static AndroidDriver<AndroidElement> capabilities(String env, boolean noReset) throws MalformedURLException {
		WebDriver driver;

		File apk;
		 apk = new File("C:\\data\\Apk\\8.0.3\\android-release8.0.3_19.11.apk");
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("BROWSER_NAME", "Android");
		capabilities.setCapability("VERSION", "4.4.2"); 
		capabilities.setCapability("deviceName","R58M40THE7R");
		capabilities.setCapability("platformName","Android");
		capabilities.setCapability(MobileCapabilityType.APP, apk.getAbsolutePath());
	   
	  // capabilities.setCapability("appPackage", "com.android.calculator2");
	// This package name of your app (you can get it from apk info app)
		//capabilities.setCapability("appActivity","com.android.calculator2.Calculator"); // This is Launcher activity of your app (you can get it from apk info app)
	//Create RemoteWebDriver instance and connect to the Appium server
	 //It will launch the Calculator App in Android Device using the configurations specified in Desired Capabilities
	   driver = new RemoteWebDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//if (env.equals("test"))
			 apk = new File("C:\\data\\Apk\\8.0.3\\android-release8.0.3_19.11.apk");
	//	else
		//	 apk = new File("C:\\data\\Apk\\8.0.3\\android-release8.0.3_19.11.apk"); 
		
		//DesiredCapabilities capabilities = new DesiredCapabilities();
		
//		DesiredCapabilities capabilities = new DesiredCapabilities();
		 capabilities = new DesiredCapabilities();
		//capabilities.setCapability("BROWSER_NAME", "Android");
		capabilities.setCapability("platformName", "Android");
		capabilities.setCapability("platformVersion", "9");
		capabilities.setCapability("deviceName","R58M40THE7R");
		capabilities.setCapability("noReset","true");
		//capabilities.setCapability("appPackage","com.ctconnect.ramatgan");

//		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "R58M40THE7R");
		capabilities.setCapability(MobileCapabilityType.APP, apk.getAbsolutePath());
//		capabilities.setCapability("noReset", noReset);
		capabilities.setCapability("newCommandTimeout", 60 * 20);
		capabilities.setCapability("autoAcceptAlerts",true);
		
		driver = new AndroidDriver<>(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
	
		
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//return driver;
		return null;
	}
	
	
	public static boolean removeApp(String env, boolean noReset) throws MalformedURLException {
		AndroidDriver<AndroidElement> driver;

		File apk;
		
		if (env.equals("test"))
			 apk = new File("c:\\apk\\top-test.apk");
		else
			 apk = new File("c:\\apk\\top-production.apk"); 
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "0815f8a12fe02804");
		capabilities.setCapability(MobileCapabilityType.APP, apk.getAbsolutePath());
		//capabilities.setCapability("noReset", noReset);
		capabilities.setCapability("newCommandTimeout", 60 * 3);
		capabilities.setCapability("autoAcceptAlerts",true);
		
		driver = new AndroidDriver<>(new URL("http://127.0.0.1:4725/wd/hub"), capabilities);
		driver.removeApp("net.il.hot.android");

		return true;
	}
	
	
	public static AndroidDriver<AndroidElement> capabilities(boolean reset) throws MalformedURLException {
		AndroidDriver<AndroidElement> driver;

		File apk = new File("c:\\apk\\top-production.apk"); 
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "0815f8a12fe02804");
		capabilities.setCapability(MobileCapabilityType.APP, apk.getAbsolutePath());
		capabilities.setCapability("noReset", reset);
		driver = new AndroidDriver<>(new URL("http://127.0.0.1:4725/wd/hub"), capabilities);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		return driver;
	}

}
