package com.project.tests;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Arrays;
import java.util.Date;
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

import atu.testrecorder.ATUTestRecorder;
import atu.testrecorder.exceptions.ATUTestRecorderException;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidStartScreenRecordingOptions;
import io.appium.java_client.screenrecording.BaseStartScreenRecordingOptions;
import io.appium.java_client.screenrecording.CanRecordScreen;

import com.project.pages.MainPage;
import com.project.pages.SiteMainPage;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.Test;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;


@Test(groups={"seleniumtest"})
public class BuyMe_WEB_Sanity {
	
	private static final Logger log = LogManager.getLogger(BuyMe_WEB_Sanity.class.getName());
//	private static final String ESCAPE_PROPERTY = "org.uncommons.reportng.escape-output";
   
	
	private Timestamp timestamp;

	ATUTestRecorder recorder;
    private WebDriver driver;
    private SiteMainPage smp;
	
	
	@BeforeClass
	public void Tests_prepration_BeforeClass() throws Exception {
	  
		
	}

	@BeforeMethod()
	public void Test_prepration_BeforeMethod() throws Exception {
		
		
		
		driver = GeneralUtility.getWebDriver("chrome", "https://buyme.co.il/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		smp = new SiteMainPage(driver);
		
	}
	
	
	// registration_screen
				@Test(enabled = true, priority = 1)
				public void site_registration_screen() throws Exception {
					
					timestamp = new Timestamp(System.currentTimeMillis());
					String methodName = "registration_screen";
			
					try {
						  DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
						  Date date = new Date();
						  String movieName = "TestVideo-"+dateFormat.format(date);
						  recorder = new ATUTestRecorder(System.getProperty("user.dir")+"\\ScriptVideos\\",movieName,false);
						  recorder.start();  
						
						
							Assert.assertTrue(smp.getIntoRegistration("firstName","test@gmail.com","password","password1"), "כישלון ברישום למערכת  ");
							GeneralUtility.reportLogScreenshotShrink(GeneralUtility.printScreen(driver, "Successfully register to the site " + methodName  ));
							
							recorder.stop();
							GeneralUtility.reportLogMovie(new File(System.getProperty("user.dir")+"\\ScriptVideos\\",movieName+".mov"));

							

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

				
				// home_screen
				@Test(enabled = true, priority = 2)
				public void site_home_screen() throws Exception {
					
					timestamp = new Timestamp(System.currentTimeMillis());
					String methodName = "send present process";
			
					try {
						
						  DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
						  Date date = new Date();
						  String movieName = "TestVideo-"+dateFormat.format(date);
						  recorder = new ATUTestRecorder(System.getProperty("user.dir")+"\\ScriptVideos\\",movieName,false);
						  recorder.start();  
						
							Assert.assertTrue(smp.getIntoSystem("hagai1973@walla.com", "Tamar2020", "200","מרכז","מתנות עד הבית"), "Failed to login");
							GeneralUtility.reportLogScreenshotShrink(GeneralUtility.printScreen(driver, "Successfully search a gift " + methodName  ));
							Assert.assertTrue(smp.chooseGiftScreen(1), "Failed to select business");
							GeneralUtility.reportLogScreenshotShrink(GeneralUtility.printScreen(driver, "Successfully choose a gift " + methodName  ));
							Assert.assertTrue(smp.senderReceiverInformationScreen("Anna","Banna","Happy Birthday","test@gmail.com"), "Failed to select business");
							GeneralUtility.reportLogScreenshotShrink(GeneralUtility.printScreen(driver, "Successfully set send recive information " + methodName  ));
							
							
							recorder.stop();
							GeneralUtility.reportLogMovie(new File(System.getProperty("user.dir")+"\\ScriptVideos\\",movieName+".mov"));

						
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

				
				// home_screen
				@Test(enabled = true, priority = 3)
				public void getIntoSystem_Negative() throws Exception {
					
					timestamp = new Timestamp(System.currentTimeMillis());
					String methodName = "send present process";
			
					try {
						  DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
						  Date date = new Date();
						  String movieName = "TestVideo-"+dateFormat.format(date);
						  recorder = new ATUTestRecorder(System.getProperty("user.dir")+"\\ScriptVideos\\",movieName,false);
						  recorder.start();  
						
							Assert.assertTrue(smp.getIntoSystem_negative("", ""), "Failed to get into system");
							GeneralUtility.reportLogScreenshotShrink(GeneralUtility.printScreen(driver, "Successfully check error message in login page " + methodName  ));

							recorder.stop();
							GeneralUtility.reportLogMovie(new File(System.getProperty("user.dir")+"\\ScriptVideos\\",movieName+".mov"));

						
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

	
	
	@AfterMethod
	public void Exit_Clean_After_Test() throws IOException, InterruptedException, ATUTestRecorderException {
		log.info("After method ...");
		GeneralUtility.tearDown(driver);
		GeneralUtility.endOfTestDelimiter();
		
	}

	@AfterClass
	public void End_Exectuion() throws IOException, InterruptedException {
		log.info("After Class ...");
//		GeneralUtility.tearDown(driver);
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

