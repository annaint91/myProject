package com.project.tests;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.sound.midi.Sequence;

import org.testng.Assert;
import org.testng.ITestResult;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Interaction;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.framework.pages.BasePage;
import com.framework.utility.GeneralUtility;
import com.project.city.AppDataProviders;
import com.project.utility.ProjectUtility;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.screenrecording.BaseStartScreenRecordingOptions;
import io.appium.java_client.screenrecording.CanRecordScreen;

import com.project.pages.MainPage;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;


@Test(groups={"seleniumtest"})
public class BuyMe_App_Sanity {
	
	private static final Logger log = LogManager.getLogger(BuyMe_App_Sanity.class.getName());
//	private static final String ESCAPE_PROPERTY = "org.uncommons.reportng.escape-output";
   
	
	private Timestamp timestamp;

	//private static final String APP = "https://github.com/cloudgrey-io/the-app/releases/download/v1.9.0/TheApp-v1.9.0.apk";
	private static final String APP = "C:\\apk\\BUYME-3.3.1.97.apk";
    private static final String APPIUM = "http://localhost:4723/wd/hub";
    private AndroidDriver<MobileElement> driver;
    private MainPage mp;
	
	
	@BeforeClass
	public void Tests_prepration_BeforeClass() throws Exception {

				
		DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        caps.setCapability("platformVersion", "8");
        caps.setCapability("deviceName", "5200b8c24f8ec4b9");
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("app", APP);
        caps.setCapability("noReset", "false");
        
       
        caps.setCapability("appPackage", "il.co.mintapp.buyme");
        caps.setCapability("appActivity","il.co.mintapp.buyme.activities.common.SplashScreen");
      
        				
        //Send the caps to the Appium Server
        driver = new AndroidDriver<MobileElement>(new URL(APPIUM), caps);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		  
		
	}

	@BeforeMethod()
	public void Test_prepration_BeforeMethod() throws Exception {
		mp = new MainPage(driver);
	}
	
	
	// בדיקת כניסה למערכת
				@Test(enabled = true, priority = 1)
				public void app_intro_google() throws Exception {
					
					timestamp = new Timestamp(System.currentTimeMillis());
					String methodName = "Login with Google";
			
					try {
							driver.startRecordingScreen();
							Assert.assertTrue(mp.loginUsingGoogle());
							GeneralUtility.reportLogMovie(stopMovieReturnFile(driver,methodName));
							GeneralUtility.reportLogScreenshotShrink(GeneralUtility.printScreen(driver, "finish " + methodName  ));

						
					} catch (AssertionError ar) {
						log.error(methodName + " Test result failed on assertion error: Please review log file");
						GeneralUtility.sendReporter(methodName +  " Test result failed on assertion error: ");
						GeneralUtility.reportLogScreenshot(GeneralUtility.printScreen(driver, "Failed_Assert " + methodName  ));
						log.log(Level.ERROR, ar);
						Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
					} catch (Exception ex) {
						log.error(methodName + " Test result failed on assertion error: Please review log file");
						GeneralUtility.sendReporter(methodName +  " Test result failed on assertion error: ");
						GeneralUtility.reportLogScreenshot(GeneralUtility.printScreen(driver, "Failed_Assert " + methodName  ));
						log.log(Level.ERROR, ex);
						Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
					}
					
					timestamp = new Timestamp(System.currentTimeMillis());
					GeneralUtility.sendReporter("Test finish: Iteration:1,  time:" + timestamp);
					log.info("Test finish: Iteration: 1, time:" + timestamp);
				}

		// בחירת מוצר לפי קטגוריה
				@Test(enabled = true, priority = 2)
				public void app_HomeScreen() throws Exception {
					
					timestamp = new Timestamp(System.currentTimeMillis());
					String methodName = "Search by category";
			
					try {
							driver.startRecordingScreen();
							Assert.assertTrue(mp.searchByCategory("גיפט קארד למסעדות שף","BUYME KOSHER - שובר למסעדות כשרות", "200"));
							GeneralUtility.reportLogMovie(stopMovieReturnFile(driver,methodName));
							GeneralUtility.reportLogScreenshotShrink(GeneralUtility.printScreen(driver, "finish " + methodName  ));

						
					} catch (AssertionError ar) {
						log.error(methodName + " Test result failed on assertion error: Please review log file");
						GeneralUtility.sendReporter(methodName +  " Test result failed on assertion error: ");
						GeneralUtility.reportLogScreenshot(GeneralUtility.printScreen(driver, "Failed_Assert " + methodName  ));
						log.log(Level.ERROR, ar);
						Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
					} catch (Exception ex) {
						log.error(methodName + " Test result failed on assertion error: Please review log file");
						GeneralUtility.sendReporter(methodName +  " Test result failed on assertion error: ");
						GeneralUtility.reportLogScreenshot(GeneralUtility.printScreen(driver, "Failed_Assert " + methodName  ));
						log.log(Level.ERROR, ex);
						Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
					}
					
					timestamp = new Timestamp(System.currentTimeMillis());
					GeneralUtility.sendReporter("Test finish: Iteration:1,  time:" + timestamp);
					log.info("Test finish: Iteration: 1, time:" + timestamp);
				}


				
				// הזנת פרטי שולח ומקבל
				@Test(enabled = true, priority = 3)
				public void app_SenderReciverInfo() throws Exception {
					
					timestamp = new Timestamp(System.currentTimeMillis());
					String methodName = "Sender and receiver information";
			
					try {
							driver.startRecordingScreen();
							Assert.assertTrue(mp.senderReceiverInfo("Yossi", "יום הולדת", "Good Luck my Friend", "Me"));
							GeneralUtility.reportLogMovie(stopMovieReturnFile(driver,methodName));
							GeneralUtility.reportLogScreenshotShrink(GeneralUtility.printScreen(driver, "finish " + methodName  ));

						
					} catch (AssertionError ar) {
						log.error(methodName + " Test result failed on assertion error: Please review log file");
						GeneralUtility.sendReporter(methodName +  " Test result failed on assertion error: ");
						GeneralUtility.reportLogScreenshot(GeneralUtility.printScreen(driver, "Failed_Assert " + methodName  ));
						log.log(Level.ERROR, ar);
						Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
					} catch (Exception ex) {
						log.error(methodName + " Test result failed on assertion error: Please review log file");
						GeneralUtility.sendReporter(methodName +  " Test result failed on assertion error: ");
						GeneralUtility.reportLogScreenshot(GeneralUtility.printScreen(driver, "Failed_Assert " + methodName  ));
						log.log(Level.ERROR, ex);
						Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
					}
					
					timestamp = new Timestamp(System.currentTimeMillis());
					GeneralUtility.sendReporter("Test finish: Iteration:1,  time:" + timestamp);
					log.info("Test finish: Iteration: 1, time:" + timestamp);
				}

		
				// פרטי שליחה
				@Test(enabled = true, priority = 4)
				public void app_howToSend() throws Exception {
					
					timestamp = new Timestamp(System.currentTimeMillis());
					String methodName = "Sender and receiver information";
			
					try {
							driver.startRecordingScreen();				
							Assert.assertTrue(mp.howToSendInfo("mail", "test@gmail.com", "₪200"));
							GeneralUtility.reportLogMovie(stopMovieReturnFile(driver, methodName));
							GeneralUtility.reportLogScreenshotShrink(GeneralUtility.printScreen(driver, "finish " + methodName));

					} catch (AssertionError ar) {
						log.error(methodName + " Test result failed on assertion error: Please review log file");
						GeneralUtility.sendReporter(methodName +  " Test result failed on assertion error: ");
						GeneralUtility.reportLogScreenshot(GeneralUtility.printScreen(driver, "Failed_Assert " + methodName  ));
						log.log(Level.ERROR, ar);
						Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
					} catch (Exception ex) {
						log.error(methodName + " Test result failed on assertion error: Please review log file");
						GeneralUtility.sendReporter(methodName +  " Test result failed on assertion error: ");
						GeneralUtility.reportLogScreenshot(GeneralUtility.printScreen(driver, "Failed_Assert " + methodName  ));
						log.log(Level.ERROR, ex);
						Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
					}
					
					timestamp = new Timestamp(System.currentTimeMillis());
					GeneralUtility.sendReporter("Test finish: Iteration:1,  time:" + timestamp);
					log.info("Test finish: Iteration: 1, time:" + timestamp);
				}

	
	
			// בדיקת יתרה בכרטיס
			@Test(enabled = false, priority = 5, dataProvider = "iteration", dataProviderClass = AppDataProviders.class)
			public void app_check_credit(int iteration) throws Exception {
				
				timestamp = new Timestamp(System.currentTimeMillis());
				String methodName = "test1";
		
				try {
						GeneralUtility.Sleep(3);
						Assert.assertTrue(mp.checkCard("170"));
						GeneralUtility.reportLogScreenshotShrink(GeneralUtility.printScreen(driver, "finish " + methodName  ));

					
				} catch (AssertionError ar) {
					log.error(methodName + " Test result failed on assertion error: Please review log file");
					GeneralUtility.sendReporter(methodName +  " Test result failed on assertion error: ");
					GeneralUtility.reportLogScreenshot(GeneralUtility.printScreen(driver, "Failed_Assert " + methodName  ));
					log.log(Level.ERROR, ar);
					Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
				} catch (Exception ex) {
					log.error(methodName + " Test result failed on assertion error: Please review log file");
					GeneralUtility.sendReporter(methodName +  " Test result failed on assertion error: ");
					GeneralUtility.reportLogScreenshot(GeneralUtility.printScreen(driver, "Failed_Assert " + methodName  ));
					log.log(Level.ERROR, ex);
					Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
				}
				
				timestamp = new Timestamp(System.currentTimeMillis());
				GeneralUtility.sendReporter("Test finish: Iteration: " + iteration +" time:" + timestamp);
				log.info("Test finish: Iteration: " + iteration +" time:" + timestamp);
			}

		
			
		// בדיקת תוצאות חיפוש מתנה
		@Test(enabled = false, priority = 6, dataProvider = "iteration", dataProviderClass = AppDataProviders.class)
		public void app_searchGift(int iteration) throws Exception {
			
			timestamp = new Timestamp(System.currentTimeMillis());
			String methodName = "test1";
	
			try {
					GeneralUtility.Sleep(3);
					Assert.assertTrue(mp.searchGift());
					GeneralUtility.reportLogScreenshotShrink(GeneralUtility.printScreen(driver, "finish " + methodName  ));

				
			} catch (AssertionError ar) {
				log.error(methodName + " Test result failed on assertion error: Please review log file");
				GeneralUtility.sendReporter(methodName +  " Test result failed on assertion error: ");
				GeneralUtility.reportLogScreenshot(GeneralUtility.printScreen(driver, "Failed_Assert " + methodName  ));
				log.log(Level.ERROR, ar);
				Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
			} catch (Exception ex) {
				log.error(methodName + " Test result failed on assertion error: Please review log file");
				GeneralUtility.sendReporter(methodName +  " Test result failed on assertion error: ");
				GeneralUtility.reportLogScreenshot(GeneralUtility.printScreen(driver, "Failed_Assert " + methodName  ));
				log.log(Level.ERROR, ex);
				Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
			}
			
			timestamp = new Timestamp(System.currentTimeMillis());
			GeneralUtility.sendReporter("Test finish: Iteration: " + iteration +" time:" + timestamp);
			log.info("Test finish: Iteration: " + iteration +" time:" + timestamp);
		}

	// גלילה
			@Test(enabled = false, priority = 7, dataProvider = "iteration", dataProviderClass = AppDataProviders.class)
			public void app_swipe(int iteration) throws Exception {
				
					timestamp = new Timestamp(System.currentTimeMillis());
					String methodName = "test1";
			
					try {
							
							Assert.assertTrue(mp.do_swipe(driver));
							GeneralUtility.reportLogScreenshotShrink(GeneralUtility.printScreen(driver, "finish " + methodName  ));

						
					} catch (AssertionError ar) {
						log.error(methodName + " Test result failed on assertion error: Please review log file");
						GeneralUtility.sendReporter(methodName +  " Test result failed on assertion error: ");
						GeneralUtility.reportLogScreenshot(GeneralUtility.printScreen(driver, "Failed_Assert " + methodName  ));
						log.log(Level.ERROR, ar);
						Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
					} catch (Exception ex) {
						log.error(methodName + " Test result failed on assertion error: Please review log file");
						GeneralUtility.sendReporter(methodName +  " Test result failed on assertion error: ");
						GeneralUtility.reportLogScreenshot(GeneralUtility.printScreen(driver, "Failed_Assert " + methodName  ));
						log.log(Level.ERROR, ex);
						Reporter.getCurrentTestResult().setStatus(ITestResult.FAILURE);
					}
					
					timestamp = new Timestamp(System.currentTimeMillis());
					GeneralUtility.sendReporter("Test finish: Iteration: " + iteration +" time:" + timestamp);
					log.info("Test finish: Iteration: " + iteration +" time:" + timestamp);
				}

	
	@AfterMethod
	public void Exit_Clean_After_Test() throws IOException, InterruptedException {
		log.info("After method ...");
		GeneralUtility.endOfTestDelimiter();
	}

	@AfterClass
	public void End_Exectuion() throws IOException, InterruptedException {
		log.info("After Class ...");
		GeneralUtility.tearDown(driver);
		timestamp = new Timestamp(System.currentTimeMillis());
		log.info("Test Finished: " + timestamp);
	}
	
	
	public static File stopMovieReturnFile(AndroidDriver<MobileElement> driver, String title) throws IOException {
		//Save Film Recording
		  String sTime = GeneralUtility.getTimeStampString();
		  String base64String = ((CanRecordScreen)driver).stopRecordingScreen();
		  byte[] data = Base64.decodeBase64(base64String); 
		  String  destinationPath="images/"+ sTime +".mp4"; 
		  Path path =  Paths.get(destinationPath); 
		  Files.write(path, data);

		  String workingDir = GeneralUtility.getUserDir(System.getProperty("user.dir"));
			// InetAddress address = InetAddress.getLocalHost();
			// String hostIP = address.getHostAddress();
			String hostIP = "localhost";

			/*
			 * workingDir = workingDir.toLowerCase().replaceAll("c:", hostIP); workingDir =
			 * workingDir.toLowerCase().replaceAll("d:", hostIP);
			 */
			workingDir = "\\" + "\\" + workingDir.replace("C:", hostIP);
			String SnapShotLocation = workingDir + "\\" + "images" + "\\" + sTime + ".mp4";

			File movieLocation = new File(SnapShotLocation);
			//FileUtils.copyFile(scrFile, snapLocation);

			// log.info("reporting ip: " + System.getProperty("reporting.ip"));
			//File snapLocationForReport = new File(SnapShotLocation.replace("//", hostIP));
			log.info("Snap location: " + movieLocation.getAbsolutePath());
			return movieLocation;
		  
		  

		
		
	}
	
}

