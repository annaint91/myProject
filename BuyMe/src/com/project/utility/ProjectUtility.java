package com.project.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.testng.SkipException;

import com.framework.pages.BasePage;
import com.framework.utility.GeneralUtility;
import com.framework.utility.SOAPUtils;




public class ProjectUtility {
	static final Logger log = LogManager.getLogger(ProjectUtility.class.getName());

	

// return the DataPower Soap Endpoint
	public static String returnOSBEnvSoapEndPoint() throws IOException {
		String soapEndPoint = null;
		switch (getProperty("environment").toLowerCase()) {

		case "test": {
			soapEndPoint = getProperty("soapOSBbaseEndpointTest");
			break;
		}

		case "train": {
			soapEndPoint = getProperty("soapOSBbaseEndpointTrain");
			break;
		}
		case "production": {
			soapEndPoint = getProperty("soapOSBbaseEndpointProd");
			break;
		}
		case "uat":
		case "int": {
			soapEndPoint = getProperty("soapOSBbaseEndpointUat");
			break;
		}
		case "dev": {
			soapEndPoint = getProperty("soapOSBbaseEndpointUat");
			break;
		}

		} // switch
		return soapEndPoint;
	}

// return the Wiz Interfaces Endpoint
	public static String returnWizEnvJSPEndPoint() throws IOException {
		String JSPEndPoint = null;
		switch (getProperty("environment").toLowerCase()) {
		case "int":
		case "uat": {
			JSPEndPoint = getProperty("JSPWIZBaseEndpointUat");
			break;
		}
		case "test": {
			JSPEndPoint = getProperty("JSPWIZBaseEndpointTest");
			break;
		}
		case "train": {
			JSPEndPoint = getProperty("JSPWIZBaseEndpointTrain");
			break;
		}
		case "production": {
			JSPEndPoint = getProperty("JSPWIZBaseEndpointProd");
			break;
		}

		case "dev": {
			JSPEndPoint = getProperty("JSPWIZBaseEndpointUat");
			break;
		}

		} // switch
		return JSPEndPoint;
	}

	public static String getProperty(String key) throws IOException {
		Properties prop = new Properties();
// If want to get the data properties from project environmet
// String workDir = System.getProperty("user.dir");
// InputStream input = new
// FileInputStream(workDir+"\\src\\com\\hotportal\\data\\datafile.properties");

		InputStream input = null;
		try {
			input = new FileInputStream(GeneralUtility.getUserDir(System.getProperty("user.dir")) + "\\datafile.properties");
		} catch (IOException ex) {
			log.error("Could not find the utility file " + "crm.datafile.properties");
			ex.printStackTrace();
		}

		prop.load(input);
		String sValue = prop.getProperty(key);

		if (sValue == null)
			log.error("Missing value for key: " + key);
		else {
			if (sValue.toLowerCase().contains("workdir")) {
				sValue = sValue.replace("workDir", GeneralUtility.getUserDir(System.getProperty("user.dir")));
			}
		}

		return sValue;
	}

//	public static String handleSlots(String trx_id, String start_date, String end_date, String promotion_type)
//			throws Exception {
//// Set the SOAP Environment base Endpoint
//		String SoapEnv = ProjectUtility.returnOSBEnvSoapEndPoint();
//		String xmlRequest = "";
//		String xmlRequest2 = "";
//		/*** Starting the GetSlots Flow ***/
//// Read the XML Request File into a String
//		if (promotion_type.equals("TSA")) {
//			xmlRequest = SOAPUtils.getSoapRequestFromFile(
//					GeneralUtility.getUserDir(System.getProperty("user.dir")) + "\\XmlRequests\\getSlots.xml");
//		}
//		if (promotion_type.equals("KWO")) {
//			xmlRequest = SOAPUtils.getSoapRequestFromFile(
//					GeneralUtility.getUserDir(System.getProperty("user.dir")) + "\\XmlRequests\\getSlotsInternet.xml");
//		}
//		xmlRequest = xmlRequest.replace("###1", trx_id);
//		xmlRequest = xmlRequest.replace("###2", start_date);
//		xmlRequest = xmlRequest.replace("###3", end_date);
//// Send the Soap request to check for available time slots for a technician
//// arrival
//		GeneralUtility.Sleep();
//
//		String soapResponse = SOAPUtils.sendSoapWebService(SoapEnv + "/EAI/Services/Calendar/GetSlots",
//				"http://www.hot.net.il/eai/contract/GetSlots/opGetSlots", xmlRequest);
//		if (!soapResponse.contains("OK</ns0:return_description>")) {
//			throw new Exception("Failed on GetSlots WS");
//		}
//		int count = 1;
//		boolean isFound = false;
//		String isAvailableSlot;
//		while (!isFound) {
//			GeneralUtility.Sleep();
//			isAvailableSlot = extractRegExpFromString("slot_status>", "</ns2:slot_status", soapResponse, count);
//			if (isAvailableSlot.equals("A")) {
//				isFound = true;
//				break;
//			}
//			count++;
//		}
//		String sched_date = extractRegExpFromString("slot_date>", "</ns2:slot_date", soapResponse, count);
//		String time_begin = extractRegExpFromString("start_time>", "</ns2:start_time>", soapResponse, count);
//		time_begin = time_begin.replaceAll(":", "");
//		time_begin = time_begin.substring(0, 4);
//		/*** Starting the ReserveSlots Flow ***/
//// Read the XML Request File into a String
//		if (promotion_type.equals("TSA")) {
//			xmlRequest2 = SOAPUtils.getSoapRequestFromFile(
//					GeneralUtility.getUserDir(System.getProperty("user.dir")) + "\\XmlRequests\\reserveSlot.xml");
//		}
//		if (promotion_type.equals("KWO")) {
//			xmlRequest2 = SOAPUtils.getSoapRequestFromFile(GeneralUtility.getUserDir(System.getProperty("user.dir"))
//					+ "\\XmlRequests\\reserveSlotInternet.xml");
//		}
//		xmlRequest2 = xmlRequest2.replace("###1", trx_id);
//		xmlRequest2 = xmlRequest2.replace("###2", sched_date);
//		xmlRequest2 = xmlRequest2.replace("###3", time_begin);
//// Send the Soap Request to set a technician arrival
//		String soapResponse2 = SOAPUtils.sendSoapWebService(SoapEnv + "/EAI/Services/Calendar/ReserveSlot",
//				"http://www.hot.net.il/eai/contract/ReserveSlot/opReserveSlot", xmlRequest2);
//		if (!soapResponse2.contains("OK</ns0:return_description>")) {
//			throw new Exception("Failed on ReserveSlot WS");
//		}
//		String reservation_id = extractRegExpFromString(
//				"<ns1:reservation_id xmlns:ns1=\"http://www.hot.net.il/eai/schema/ReserveSlot\">",
//				"</ns1:reservation_id>", soapResponse2, 1);
//		return reservation_id;
//	}

	
//		public static String AddEquipmentToCustomer(String id, String promotion_type) throws Exception {
//		log.info("Waiting 60 sec before Starting the WiZ Equipment Flow...");
//		GeneralUtility.Sleep(60);
//		/*** Starting the Add Equipment To Customer Flow ***/
//// Starting with 5 Wizard Queries to Bring the correct Data for the
//// UpdateEquipmentActions interface
//		String[] DBconnections = CRMDataProviders.returnConnectionParams();
//// get Site Number/Account Number from SSN (ID)
//		String account_number = DBUtility.getValueFromDb(
//				"Select w.account_number from wiz_customer_descrip w where w.ssn='" + id + "' and DLN='ISR'",
//				"account_number", DBconnections[6], DBconnections[7], DBconnections[8]);
//// get work-order id using account_number
//		String work_order = DBUtility.getValueFromDb(
//				"SELECT w.work_order_number from wiz_work_order w where w.account_number=" + account_number,
//				"WORK_ORDER_NUMBER", DBconnections[6], DBconnections[7], DBconnections[8]);
//		String outlet_location = DBUtility.getValueFromDb(
//				"select o.outlet_location from wiz_wo_occurrence o where o.work_order_number =" + work_order,
//				"outlet_location", DBconnections[6], DBconnections[7], DBconnections[8]);
//
////get serial of smart card
//		String sql = ""; /*
//							 * "Select b.serial_number from" + " (select e.serial_number " +
//							 * "from wiz_equip e " + "where e.converter_type = 'SC' " +
//							 * " and e.account_number = 0 " + " and e.equip_location_code = 'ERP' " +
//							 * " and e.manufacturer = 'ND' " + " and e.headend = 3 and rownum<100 " +
//							 * "ORDER BY DBMS_RANDOM.VALUE) b " + "where rownum<2";
//							 */
//
//		sql = "Select b.serial_number from(select * from "
//				+ " wiz_equip e where e.converter_type = 'SC' and e.account_number = 0 "
//				+ " and not e.serial_number like 'FIX%'"
//				+ "and e.equip_location_code = 'ERP' and e.manufacturer = 'ND' and "
//				+ " e.headend = 3 and rownum < 30 ORDER BY DBMS_RANDOM.VALUE) b where rownum < 2";
//
//		String smartCardSerial = DBUtility.getValueFromDb(sql, "serial_number", DBconnections[6], DBconnections[7],
//				DBconnections[8]);
//
////get serial of converter
//		String sql2 = "";
//
//		sql2 = "Select b.serial_number from (select e.serial_number from wiz_equip e where e.converter_type in ('HP','HV') "
//				+
//
//				" and e.account_number = 0 and not e.serial_number like 'FIX%' and e.equip_location_code = 'ERP' and e.headend = 3 and rownum < 30 ORDER BY DBMS_RANDOM.VALUE) "
//				+ " b where rownum<2";
//		String converterSerial = DBUtility.getValueFromDb(sql2, "serial_number", DBconnections[6], DBconnections[7],
//				DBconnections[8]);
//
//		String hotbox2Serial = DBUtility.getValueFromDb(
//				"select e.serial_number " + "from wiz_equip e " + "where e.converter_type in ('GT') "
//						+ " and e.account_number = 0 " + " and not e.serial_number like 'FIX%'"
//						+ " and e.equip_location_code = 'ERP' " + " and e.headend = 3 " + " and rownum < 2",
//				"serial_number", DBconnections[6], DBconnections[7], DBconnections[8]);
//		log.info("Current path is " + GeneralUtility.getUserDir(System.getProperty("user.dir")));
//		String xmlRequest4 = "";
//		if (promotion_type.equals("TSA")) {
//			log.info("converter serial is: " + converterSerial);
//			xmlRequest4 = new String(
//					Files.readAllBytes(Paths.get(GeneralUtility.getUserDir(System.getProperty("user.dir"))
//							+ "\\XmlRequests\\UpdateEquipmentActions.xml")));
//
//			xmlRequest4 = xmlRequest4.replace("###1", work_order);
//			xmlRequest4 = xmlRequest4.replace("###2", addDays("yyyyMMdd", 0) + "000000");
//			xmlRequest4 = xmlRequest4.replace("###3", smartCardSerial);
//			xmlRequest4 = xmlRequest4.replace("###4", outlet_location);
//			xmlRequest4 = xmlRequest4.replace("###5", converterSerial);
//		}
//
//		if (promotion_type.equals("KWO")) {
//			log.info("hotbox2 serial is: " + hotbox2Serial);
//			xmlRequest4 = new String(
//					Files.readAllBytes(Paths.get(GeneralUtility.getUserDir(System.getProperty("user.dir"))
//							+ "\\XmlRequests\\UpdateEquipmentActionsInternet.xml")));
//
//			xmlRequest4 = xmlRequest4.replace("###1", work_order);
//			xmlRequest4 = xmlRequest4.replace("###2", addDays("yyyyMMdd", 0) + "000000");
//			xmlRequest4 = xmlRequest4.replace("###3", hotbox2Serial);
//		}
//
//		log.info("Sending request to update equipement: " + xmlRequest4);
//		StringEntity entity = new StringEntity(xmlRequest4);
//		HttpClient httpclient = HttpClients.createDefault();
//
//		HttpPost httppost = new HttpPost(returnWizEnvJSPEndPoint() + "/html/XMLUpdateEquipAction.jsp"); // wiz endpoint
//		httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
//		httppost.setEntity(entity);
//// Execute and get the response.
//		GeneralUtility.Sleep(3);
//		HttpResponse response = httpclient.execute(httppost);
//		ContentType contentType = ContentType.getOrDefault(entity);
//		Charset charset = contentType.getCharset();
//		Reader reader = new InputStreamReader(response.getEntity().getContent(), charset);
//		final int bufferSize = 1024;
//		final char[] buffer = new char[bufferSize];
//		final StringBuilder out = new StringBuilder();
//		for (;;) {
//			int rsz = reader.read(buffer, 0, buffer.length);
//			if (rsz < 0)
//				break;
//			out.append(buffer, 0, rsz);
//		}
//		log.info("Response from Wizard: " + out.toString());
//		if (!out.toString().contains("EquipReturnMessage>OK")) {
//			throw new Exception("Failed on UpdateEquipment from WiZ Request");
//		}
//		return account_number;
//	}
//
//	private static String AddNotRealEquipmentToCustomer(String id, String promotion_type) throws Exception {
//		log.info("Waiting 60 sec before Starting the WiZ Equipment Flow...");
//		GeneralUtility.Sleep(60);
//		/*** Starting the Add Equipment To Customer Flow ***/
//// Starting with 5 Wizard Queries to Bring the correct Data for the
//// UpdateEquipmentActions interface
//		String[] DBconnections = CRMDataProviders.returnConnectionParams();
//// get Site Number/Account Number from SSN (ID)
//		String account_number = DBUtility.getValueFromDb(
//				"Select w.account_number from wiz_customer_descrip w " + "where w.ssn='" + id + "' and DLN='ISR'",
//				"account_number", DBconnections[6], DBconnections[7], DBconnections[8]);
//// get work-order id using account_number
//		String work_order = DBUtility.getValueFromDb(
//				"SELECT w.work_order_number from wiz_work_order w " + "where w.account_number=" + account_number,
//				"WORK_ORDER_NUMBER", DBconnections[6], DBconnections[7], DBconnections[8]);
//		String outlet_location = DBUtility.getValueFromDb(
//				"select o.outlet_location from wiz_wo_occurrence o where o.work_order_number =" + work_order,
//				"outlet_location", DBconnections[6], DBconnections[7], DBconnections[8]);
//
////get serial of smart card
//		String sql = "Select b.serial_number from" + " (select e.serial_number " + "from wiz_equip e "
//				+ "where e.converter_type = 'SC' " + " and e.account_number = 0 "
//				+ " and e.equip_location_code = 'ERP' " + " and e.manufacturer = 'ND' "
//				+ " and e.headend = 3 and rownum<100 " + "ORDER BY DBMS_RANDOM.VALUE) b " + "where rownum<2";
//
//		sql = "Select b.serial_number from(select * from "
//				+ " wiz_equip e where e.converter_type = 'SC' and e.account_number = 0 "
//				+ "and e.equip_location_code = 'ERP' and e.manufacturer = 'ND' and "
//				+ " e.headend = 3 and rownum < 30 ORDER BY DBMS_RANDOM.VALUE) b where rownum < 2";
//
//		String smartCardSerial = DBUtility.getValueFromDb(sql, "serial_number", DBconnections[6], DBconnections[7],
//				DBconnections[8]);
//
////get serial of converter
//		String sql2 = "Select b.serial_number from " + "(select e.serial_number " + "from wiz_equip e "
//				+ "where e.converter_type in ('HP','HV') " + " and e.account_number = 0 "
//				+ " and e.equip_location_code = 'ERP' " + "and e.headend = 3 and rownum<100 "
//				+ "ORDER BY DBMS_RANDOM.VALUE) b" + " where rownum<2";
//		sql2 = "Select b.serial_number from (select e.serial_number from wiz_equip e where e.converter_type in ('HP','HV') "
//				+ " and e.account_number = 0 and e.equip_location_code = 'ERP' and e.headend = 3 and rownum < 30 ORDER BY DBMS_RANDOM.VALUE) "
//				+ " b where rownum<2";
//		String converterSerial = DBUtility.getValueFromDb(sql2, "serial_number", DBconnections[6], DBconnections[7],
//				DBconnections[8]);
//
//		String hotbox2Serial = DBUtility.getValueFromDb(
//						"select e.serial_number " + "from wiz_equip e " + "where e.converter_type in ('GT') "
//								+ " and e.account_number = 0 " + " and e.equip_location_code = 'ERP' "
//								+ " and e.headend = 3 " + " and rownum < 2",
//						"serial_number", DBconnections[6], DBconnections[7], DBconnections[8]);
//		log.info("Current path is " + GeneralUtility.getUserDir(System.getProperty("user.dir")));
//		String xmlRequest4 = "";
//		if (promotion_type.equals("TSA")) {
//			log.info("converter serial is: " + converterSerial);
//			xmlRequest4 = new String(
//					Files.readAllBytes(Paths.get(GeneralUtility.getUserDir(System.getProperty("user.dir"))
//							+ "\\XmlRequests\\UpdateEquipmentActions.xml")));
//
//			xmlRequest4 = xmlRequest4.replace("###1", work_order);
//			xmlRequest4 = xmlRequest4.replace("###2", addDays("yyyyMMdd", 0) + "000000");
//			xmlRequest4 = xmlRequest4.replace("###3", smartCardSerial);
//			xmlRequest4 = xmlRequest4.replace("###4", outlet_location);
//			xmlRequest4 = xmlRequest4.replace("###5", converterSerial);
//		}
//
//		if (promotion_type.equals("KWO")) {
//			log.info("hotbox2 serial is: " + hotbox2Serial);
//			xmlRequest4 = new String(
//					Files.readAllBytes(Paths.get(GeneralUtility.getUserDir(System.getProperty("user.dir"))
//							+ "\\XmlRequests\\UpdateEquipmentActionsInternet.xml")));
//
//			xmlRequest4 = xmlRequest4.replace("###1", work_order);
//			xmlRequest4 = xmlRequest4.replace("###2", addDays("yyyyMMdd", 0) + "000000");
//			xmlRequest4 = xmlRequest4.replace("###3", hotbox2Serial);
//		}
//
//		log.info("Sending request to update equipement: " + xmlRequest4);
//		StringEntity entity = new StringEntity(xmlRequest4);
//		HttpClient httpclient = HttpClients.createDefault();
//
//		HttpPost httppost = new HttpPost(returnWizEnvJSPEndPoint() + "/html/XMLUpdateEquipAction.jsp"); // wiz endpoint
//		httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
//		httppost.setEntity(entity);
//// Execute and get the response.
//		GeneralUtility.Sleep(3);
//		HttpResponse response = httpclient.execute(httppost);
//		ContentType contentType = ContentType.getOrDefault(entity);
//		Charset charset = contentType.getCharset();
//		Reader reader = new InputStreamReader(response.getEntity().getContent(), charset);
//		final int bufferSize = 1024;
//		final char[] buffer = new char[bufferSize];
//		final StringBuilder out = new StringBuilder();
//		for (;;) {
//			int rsz = reader.read(buffer, 0, buffer.length);
//			if (rsz < 0)
//				break;
//			out.append(buffer, 0, rsz);
//		}
//		log.info("Response from Wizard: " + out.toString());
//		if (!out.toString().contains("EquipReturnMessage>OK")) {
//			throw new Exception("Failed on UpdateEquipment from WiZ Request");
//		}
//		return account_number;
//	}
//
//// extract a sub-string from a string using Regexp
//	public static String extractRegExpFromString(String LB, String RB, String data, int count_num) {
//		Pattern pattern = Pattern.compile(LB + "(.*?)" + RB);
//		Matcher matcher = pattern.matcher(data);
//		String ans;
//		int count = 1;
//		while (matcher.find()) {
//			ans = matcher.group(1);
//			if (count == count_num)
//				return ans;
//			count++;
//		}
//		return null;
//	}
//
//	public static String getEffectiveDate() { // yyyy-MM-ddThh:mm:ss.00090+02:00
//		Date date1 = new Date();
//		Date date2 = new Date();
//		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//		String formattedDate1 = sdf1.format(date1);
//		SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss");
//		String formattedDate2 = sdf2.format(date2);
//		String eff_date = formattedDate1 + "T" + formattedDate2 + ".00090+02:00";
//		return eff_date;
//	}
//
//	public static String getEffectiveDateShort() { // yyyy-MM-ddThh:mm:ss.090+02:00
//		Date date1 = new Date();
//		Date date2 = new Date();
//		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//		String formattedDate1 = sdf1.format(date1);
//		SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss");
//		String formattedDate2 = sdf2.format(date2);
//		String eff_date = formattedDate1 + "T" + formattedDate2 + ".090+02:00";
//		return eff_date;
//	}
//
//	public static String getEffectiveDateInXDays(int days) { // yyyy-MM-ddThh:mm:ss.00090+02:00
//		Date date2 = new Date();
//		String formattedDate1 = addDays("yyyy-MM-dd", 30);
//		SimpleDateFormat sdf2 = new SimpleDateFormat("hh:mm:ss");
//		String formattedDate2 = sdf2.format(date2);
//		String eff_date = formattedDate1 + "T" + formattedDate2 + ".00090+02:00";
//		return eff_date;
//	}
//
//	public static String addDays(String sPattern, int days) {
//		Date d = new Date();
//		Calendar c = Calendar.getInstance();
//		c.setTime(d);
//		c.add(Calendar.DATE, days);
//		d.setTime(c.getTime().getTime());
//		SimpleDateFormat sdf = new SimpleDateFormat(sPattern);
//		String formattedDate = sdf.format(d);
//		return formattedDate;
//	}
//
//
//
//	public static String replaceEscapeCharcters(String sValue) throws Exception {
//		try {
//			sValue = sValue.replace("'", "''");
//		} catch (Exception ex) {
//			log.error("Could not execute successfully the replace escape char method");
//		}
//		return sValue;
//	}
//
//// This method will create an active user with TSA promotion (HOT TV customer
//// with 1 converter)
//	public static String createNewCustomer() throws Exception {
//		String id = GeneralUtility.getValidID(); // generate rand ID
//		String trx_id = GeneralUtility.returnUniqTransactionID(); // generate rand TrXID
//		String start_date = getEffectiveDate(); // ${__time(yyyy-MM-dd)}T${__time(hh:mm:ss)}.090+02:00
//		String end_date = getEffectiveDateInXDays(30); // ${__time(yyyy-MM-dd)}T${__time(hh:mm:ss)}.090+02:00
//		String reservation_id = handleSlots(trx_id, start_date, end_date, "TSA");
//		GeneralUtility.Sleep(2);
//
//		setSalesOrder(trx_id, id, reservation_id, "TSA");
//		GeneralUtility.Sleep(2);
//
//		String account_number = AddEquipmentToCustomer(id, "TSA");
//		GeneralUtility.Sleep(2);
//
//		log.info("Created customer with cust_id: " + account_number);
//		return account_number;
//	}
//
//// This method will create an active user with KWO promotion (HOT Internet Customer
//// with a single HotBox2 Router)
//	public static String createNewInternetCustomer() throws Exception {
//		String id = GeneralUtility.getValidID(); // generate rand ID
//		String trx_id = GeneralUtility.returnUniqTransactionID(); // generate rand TrXID
//		String start_date = getEffectiveDate(); // ${__time(yyyy-MM-dd)}T${__time(hh:mm:ss)}.090+02:00
//		String end_date = getEffectiveDateInXDays(30); // ${__time(yyyy-MM-dd)}T${__time(hh:mm:ss)}.090+02:00
//		String reservation_id = handleSlots(trx_id, start_date, end_date, "KWO");
//		setSalesOrder(trx_id, id, reservation_id, "KWO");
//		String account_number = AddEquipmentToCustomer(id, "KWO");
//
//		log.info("Created customer with cust_id: " + account_number);
//		return account_number;
//
//	}
//
//	public static String createNewInternetCustomerWithNonRealEquipment() throws Exception {
//		String id = GeneralUtility.getValidID(); // generate rand ID
//		String trx_id = GeneralUtility.returnUniqTransactionID(); // generate rand TrXID
//		String start_date = getEffectiveDate(); // ${__time(yyyy-MM-dd)}T${__time(hh:mm:ss)}.090+02:00
//		String end_date = getEffectiveDateInXDays(30); // ${__time(yyyy-MM-dd)}T${__time(hh:mm:ss)}.090+02:00
//		String reservation_id = handleSlots(trx_id, start_date, end_date, "KWO");
//		setSalesOrder(trx_id, id, reservation_id, "KWO");
//		String account_number = AddNotRealEquipmentToCustomer(id, "KWO");
//
//		log.info("Created customer with cust_id: " + account_number);
//		return account_number;
//
//	}
//
//// public static boolean sendSOAPMarkMessageAsRead(String trx_id, String guid1, String guid2, String guid3) throws Exception {
//// // TODO Auto-generated method stub
//// // Set the SOAP Environment base Endpoint
//// String SoapEnv = CRMUtility.returnOSBEnvSoapEndPoint();
//// String xmlRequest="";
//
//	public static boolean sendSOAPMarkMessageAsRead(String trx_id, String guid1, String guid2, String guid3)
//			throws Exception {
//
//// Set the SOAP Environment base Endpoint
//		String SoapEnv = ProjectUtility.returnOSBEnvSoapEndPoint();
//		String xmlRequest = "";
//
//		/*** Starting the MarkMSGsAsRead interface Flow ***/
//// Read the XML Request File into a String
//		xmlRequest = SOAPUtils.getSoapRequestFromFile(
//				GeneralUtility.getUserDir(System.getProperty("user.dir")) + "\\XmlRequests\\markMessagesAsRead.xml");
//		xmlRequest = xmlRequest.replace("###1", trx_id);
//		xmlRequest = xmlRequest.replace("###2", guid1);
//		xmlRequest = xmlRequest.replace("###3", guid2);
//		xmlRequest = xmlRequest.replace("###4", guid3);
//// Send the Soap request to check for available time slots for a technician
//// arrival
//		SOAPUtils.sendSoapWebServiceWithEmptyReply(SoapEnv + "/EAI/Services/Mail/MarkMessageAsRead",
//				"http://www.hot.net.il/eai/contract/MarkMessageAsRead/opMarkMessageAsRead", xmlRequest);
//
//		return true;
//	}
//
//	public static boolean cancelWO(String WO_number) throws Exception {
//
//		String trx_id = GeneralUtility.returnUniqTransactionID(); // generate rand TrXID
//		String request_date = GeneralUtility.getEffectiveDateInXDays(0); // ${__time(yyyy-MM-dd)}T${__time(hh:mm:ss)}.090+02:00
//		String cancelCode = "05U";
//// Set the SOAP Environment base Endpoint
//		String SoapEnv = returnOSBEnvSoapEndPoint();
//		/*** Starting the GetSlots Flow ***/
//// Read the XML Request File into a String
//		String xmlRequest = SOAPUtils.getSoapRequestFromFile(
//				GeneralUtility.getUserDir(System.getProperty("user.dir")) + "\\XmlRequests\\CancelWO.xml");
//		xmlRequest = xmlRequest.replace("###TRANC", trx_id);
//		xmlRequest = xmlRequest.replace("###DATE", request_date);
//		xmlRequest = xmlRequest.replace("###WO_NUMBER", WO_number);
//
////
//// Send the Soap request to check for available time slots for a technician
//// arrival
//		String soapResponse = SOAPUtils.sendSoapWebService(SoapEnv + "/EAI/Services/WorkOrder/CancelWO",
//				"http://www.hot.net.il/eai/contract/CancelWO/opCancelWO", xmlRequest);
//		if (!soapResponse.contains("OK</ns0:return_description>")) {
//			GeneralUtility.sendReporter("Failed when sending SOAP for canceling WO: " + xmlRequest);
//			log.error("Failed when sending SOAP for canceling WO: " + xmlRequest);
//			throw new Exception("Failed on Cancel WO");
//		} else {
//			GeneralUtility.sendReporter("Sending SOAP for canceling WO Successfully ");
//			log.info("Sending SOAP for canceling WO Successfully ");
//			return true;
//		}
//	}
//
////
//	public static String createCustomerNotActive() throws Exception {
//		String id = GeneralUtility.getValidID(); // generate rand ID
//		String trx_id = GeneralUtility.returnUniqTransactionID(); // generate rand TrXID
//		String start_date = GeneralUtility.getEffectiveDateInXDays(2); // ${__time(yyyy-MM-dd)}T${__time(hh:mm:ss)}.090+02:00
//		String end_date = GeneralUtility.getEffectiveDateInXDays(2); // ${__time(yyyy-MM-dd)}T${__time(hh:mm:ss)}.090+02:00
//		String[] reservation_data = handleSlotsReturnStartTime(trx_id, start_date, end_date);
//		setSalesOrder(trx_id, id, reservation_data[0]);
//		String account_number = TechnicianUpdate(id, reservation_data[1]);
//		return account_number;
//	}
//
//	
//	private static String[] handleSlotsReturnStartTime(String trx_id, String start_date, String end_date)
//			throws Exception {
//// Set the SOAP Environment base Endpoint
//		String SoapEnv = returnOSBEnvSoapEndPoint();
//		/*** Starting the GetSlots Flow ***/
//// Read the XML Request File into a String
//		String xmlRequest = SOAPUtils.getSoapRequestFromFile(
//				GeneralUtility.getUserDir(System.getProperty("user.dir")) + "\\XmlRequests\\getSlots.xml");
//		xmlRequest = xmlRequest.replace("###1", trx_id);
//		xmlRequest = xmlRequest.replace("###2", start_date);
//		xmlRequest = xmlRequest.replace("###3", end_date);
//// Send the Soap request to check for available time slots for a technician
//// arrival
//		String soapResponse = SOAPUtils.sendSoapWebService(SoapEnv + "/EAI/Services/Calendar/GetSlots",
//				"http://www.hot.net.il/eai/contract/GetSlots/opGetSlots", xmlRequest);
//		if (!soapResponse.contains("OK</ns0:return_description>")) {
//			throw new Exception("Failed on GetSlots WS");
//		}
//		int count = 1;
//		boolean isFound = false;
//		String isAvailableSlot;
//		while (!isFound) {
//			isAvailableSlot = extractRegExpFromString("slot_status>", "</ns2:slot_status", soapResponse, count);
//			if (isAvailableSlot == null) {
//				log.error("soapResponse seems to be with no slots... " + "\n" + soapResponse);
//				throw new Exception("Failed on GetSlots WS");
//			}
//			if (isAvailableSlot.equals("A")) {
//				isFound = true;
//				break;
//			}
//			count++;
//		}
//		String sched_date = extractRegExpFromString("slot_date>", "</ns2:slot_date", soapResponse, count);
//		String time_begin = extractRegExpFromString("start_time>", "</ns2:start_time>", soapResponse, count);
//		time_begin = time_begin.replaceAll(":", "");
//		time_begin = time_begin.substring(0, 4);
//		/*** Starting the ReserveSlots Flow ***/
//// Read the XML Request File into a String
//		String xmlRequest2 = SOAPUtils.getSoapRequestFromFile(
//				GeneralUtility.getUserDir(System.getProperty("user.dir")) + "\\XmlRequests\\reserveSlot.xml");
//		xmlRequest2 = xmlRequest2.replace("###1", trx_id);
//		xmlRequest2 = xmlRequest2.replace("###2", sched_date);
//		xmlRequest2 = xmlRequest2.replace("###3", time_begin);
//// Send the Soap Request to set a technician arrival
//		String soapResponse2 = SOAPUtils.sendSoapWebService(SoapEnv + "/EAI/Services/Calendar/ReserveSlot",
//				"http://www.hot.net.il/eai/contract/ReserveSlot/opReserveSlot", xmlRequest2);
//		if (!soapResponse2.contains("OK</ns0:return_description>")) {
//			throw new Exception("Failed on ReserveSlot WS");
//		}
//		String reservation_id = extractRegExpFromString(
//				"<ns1:reservation_id xmlns:ns1=\"http://www.hot.net.il/eai/schema/ReserveSlot\">",
//				"</ns1:reservation_id>", soapResponse2, 1);
//
//		return new String[] { reservation_id, time_begin };
//	}
//
//	public static String[] returnAppEndPoint() throws IOException {
//		String[] appServerDetails = new String[3];
//		switch (getProperty("environment").toLowerCase()) {
//		case "int": {
//			appServerDetails[0] = getProperty("WizAppInt");
//			appServerDetails[1] = getProperty("WizAppIntUser");
//			appServerDetails[2] = getProperty("WizAppIntPass");
//			break;
//		}
//		case "test": {
//			appServerDetails[0] = getProperty("WizAppTest");
//			appServerDetails[1] = getProperty("WizAppTestUser");
//			appServerDetails[2] = getProperty("WizAppTestPass");
//			break;
//		}
//		case "train": {
//			appServerDetails[0] = getProperty("WizAppTrain");
//			appServerDetails[1] = getProperty("WizAppTrainUser");
//			appServerDetails[2] = getProperty("WizAppTrainPass");
//			break;
//		}
//		case "production": {
//
//			break;
//		}
//		case "uat": {
//
//			break;
//		}
//		} // switch
//		return appServerDetails;
//	}
//
//	public static String createWO_CH(String accountNumber, String closeDate) throws Exception {
//// Set the SOAP Environment base Endpoint
//		String SoapEnv = returnOSBEnvSoapEndPoint();
//		/*** Starting the GetSlots Flow ***/
//// Read the XML Request File into a String
//		String xmlRequest = SOAPUtils.getSoapRequestFromFile(
//				GeneralUtility.getUserDir(System.getProperty("user.dir")) + "\\XmlRequests\\CustomerProduct_WO_CH.xml");
//		xmlRequest = xmlRequest.replace("###ACCOUNT_NUMBER", accountNumber);
//		xmlRequest = xmlRequest.replace("###EFFECTIVE_DATE", closeDate);
//// Send the Soap request to check for available time slots for a technician
//// arrival
//		String soapResponse = SOAPUtils.sendSoapWebService(SoapEnv + "/EAI/Services/Customer/CustomerProduct",
//				"http://www.hot.net.il/eai/contrant/CustomerProducts/NewOperation", xmlRequest);
//		if (!soapResponse.contains("OK</ns0:return_description>")) {
//			throw new Exception("Failed on GetSlots WS");
//		}
//
//		String woNumber = GeneralUtility.extractRegExpFromString(
//				"<ns1:work_order_number xmlns:ns1=\"http://www.hot.net.il/eai/schema/CustomerProduct\">",
//				"</ns1:work_order_number>", soapResponse, 1);
//		return woNumber;
//	}
//
//	public static String[] createNewCRMCustomer(String id, String trx_id) throws Exception {
//
//		String[] customer_details = new String[3];
//
//// Set the SOAP Environment base Endpoint
//		String SoapEnv = returnOSBEnvSoapEndPoint();
//		/*** Starting the GetSlots Flow ***/
//// Read the XML Request File into a String
//		String xmlRequest = SOAPUtils.getSoapRequestFromFile(
//				GeneralUtility.getUserDir(System.getProperty("user.dir")) + "\\XmlRequests\\CreateCRMCustomer.xml");
//		xmlRequest = xmlRequest.replace("###1", id);
//		xmlRequest = xmlRequest.replace("###2", trx_id);
//// Send the Soap request to check for available time slots for a technician
//// arrival
//		String soapResponse = SOAPUtils.sendSoapWebService(SoapEnv + "/EAI/Services/Customer/CreateCRMCustomer",
//				"http://www.hot.net.il/eai/contract/CreateCRMCustomer/opCreateCRMCustomer", xmlRequest);
//		if (!soapResponse.contains("OK</ns0:return_description>")) {
//			throw new Exception("Failed on CreateCRMCustomer WS");
//		}
//		int count = 1;
//
////extract the CRM case and site IDs
//		String case_id = extractRegExpFromString(
//				"<ns1:case_id xmlns:ns1=\"http://www.hot.net.il/eai/schema/CreateCRMCustomer\">", "</ns1:case_id>",
//				soapResponse, count);
//		String site_id = extractRegExpFromString(
//				"<ns1:site_id xmlns:ns1=\"http://www.hot.net.il/eai/schema/CreateCRMCustomer\">", "</ns1:site_id>",
//				soapResponse, count);
//		String service_address_id = extractRegExpFromString(
//				"<ns1:service_address_id xmlns:ns1=\"http://www.hot.net.il/eai/schema/CreateCRMCustomer\">",
//				"</ns1:service_address_id>", soapResponse, count);
//
//		return new String[] { case_id, site_id, service_address_id };
//	}
//
//	public static String[] createConnect(String id, String trx_id, String case_id, String site_id) throws Exception {
//
//// Set the SOAP Environment base Endpoint
//		String SoapEnv = returnOSBEnvSoapEndPoint();
//		/*** Starting the CreateConnect Flow ***/
//// Read the XML Request File into a String
//		String xmlRequest = SOAPUtils.getSoapRequestFromFile(
//				GeneralUtility.getUserDir(System.getProperty("user.dir")) + "\\XmlRequests\\CreateConnect.xml");
//		xmlRequest = xmlRequest.replace("###1", id);
//		xmlRequest = xmlRequest.replace("###2", trx_id);
//		xmlRequest = xmlRequest.replace("###3", case_id);
//		xmlRequest = xmlRequest.replace("###4", site_id);
//// Send the Soap request to check for available time slots for a technician
//// arrival
//		String soapResponse = SOAPUtils.sendSoapWebService(SoapEnv + "/EAI/Services/Customer/CreateConnect",
//				"http://www.hot.net.il/eai/contract/CreateConnect/opCreateConnect", xmlRequest);
//		if (!soapResponse.contains("OK</ns0:return_description>") && !soapResponse.contains("ns1:work_order_number")) {
//			throw new Exception("Failed on CreateConnect WS");
//		}
//		int count = 1;
//
////extract the CRM case and site IDs
//		String account_num = extractRegExpFromString(
//				"<ns1:account_number xmlns:ns1=\"http://www.hot.net.il/eai/schema/CreateConnect\">",
//				"</ns1:account_number>", soapResponse, count);
//		String work_order_num = extractRegExpFromString(
//				"ns1:work_order_number xmlns:ns1=\"http://www.hot.net.il/eai/schema/CreateConnect\">",
//				"</ns1:work_order_number>", soapResponse, count);
//
//		return new String[] { account_num, work_order_num };
//
//	}
//
//	public static void SetDemographicDetails(String trx_id, String account_number) throws Exception {
//// Set the SOAP Environment base Endpoint
//		String SoapEnv = returnOSBEnvSoapEndPoint();
//		/*** Starting the SetDemographicDetails Flow ***/
//// Read the XML Request File into a String
//		String xmlRequest = SOAPUtils.getSoapRequestFromFile(
//				GeneralUtility.getUserDir(System.getProperty("user.dir")) + "\\XmlRequests\\SetDemographicDetails.xml");
//		xmlRequest = xmlRequest.replace("###1", trx_id);
//		xmlRequest = xmlRequest.replace("###2", account_number);
//// Send the Soap request to check for available time slots for a technician
//// arrival
//		String soapResponse = SOAPUtils.sendSoapWebService(SoapEnv + "/EAI/Services/Customer/SetDemographicDetails",
//				"http://www.hot.net.il/eai/contract/SetDemographicDetails/opSetDemographicDetails", xmlRequest);
//		if (!soapResponse.contains("OK</ns0:return_description>")) {
//			throw new Exception("Failed on SetDemographicDetails WS");
//		}
//
//	}
//
//	public static void DebitAuthorization(String account_number, String id, String trx_id, String start_date)
//			throws Exception {
//// Set the SOAP Environment base Endpoint
//		String SoapEnv = returnOSBEnvSoapEndPoint();
//		/*** Starting the DebitAuthorization Flow ***/
//// Read the XML Request File into a String
//		String xmlRequest = SOAPUtils.getSoapRequestFromFile(
//				GeneralUtility.getUserDir(System.getProperty("user.dir")) + "\\XmlRequests\\DebitAuthorization.xml");
//		xmlRequest = xmlRequest.replace("###1", trx_id);
//		xmlRequest = xmlRequest.replace("###2", account_number);
//		xmlRequest = xmlRequest.replace("###3", id);
//		xmlRequest = xmlRequest.replace("###4", start_date);
//// Send the Soap request to check for available time slots for a technician
//// arrival
//		String soapResponse = SOAPUtils.sendSoapWebService(SoapEnv + "/EAI/Services/Customer/DebitAuthorization",
//				"http://www.hot.net.il/eai/contract/DebitAuthorization/opDebitAuthorization", xmlRequest);
//		if (!soapResponse.contains("OK</ns0:return_description>")) {
//			throw new Exception("Failed on DebitAuthorization WS");
//		}
//
//	}
//
//	public static void DebitAuthorizationStandingOrder(String account_number, String id, String trx_id,
//			String start_date, String service_address_id) throws Exception {
//
//		log.info("Sending Direct Debit Rest API request to WIZ");
//		String xmlRequest4 = new String(
//				Files.readAllBytes(Paths.get(GeneralUtility.getUserDir(System.getProperty("user.dir"))
//						+ "\\XmlRequests\\DebitAuthorizationDirectDebit.xml")));
//
//		xmlRequest4 = xmlRequest4.replace("###1", account_number);
//		xmlRequest4 = xmlRequest4.replace("###2", service_address_id);
//
//		log.info("sending Request: " + xmlRequest4);
//		StringEntity entity = new StringEntity(xmlRequest4);
//		HttpClient httpclient = HttpClients.createDefault();
//
//		HttpPost httppost = new HttpPost(returnWizEnvJSPEndPoint() + "/html/XMLCustomerDebitAuth.jsp"); // wiz endpoint
//		httppost.setHeader("Content-Type", "application/x-www-form-urlencoded");
//
//		httppost.setEntity(entity);
//// Execute and get the response.
//
//		GeneralUtility.Sleep(3);
//		HttpResponse response = httpclient.execute(httppost);
//
//		ContentType contentType = ContentType.getOrDefault(entity);
//		Charset charset = contentType.getCharset();
//		Reader reader = new InputStreamReader(response.getEntity().getContent(), charset);
//
//		final int bufferSize = 1024;
//		final char[] buffer = new char[bufferSize];
//		final StringBuilder out = new StringBuilder();
//
//		for (;;) {
//			int rsz = reader.read(buffer, 0, buffer.length);
//			if (rsz < 0)
//				break;
//			out.append(buffer, 0, rsz);
//		}
//
//		log.info("Response from Wizard: " + out.toString());
//		if (!out.toString().contains("<ReturnStatus>10499")) {
//			throw new Exception("Failed on XMLCustomerDebitAuth from WiZ Request");
//		}
//
//		/*
//		 * log.info(
//		 * "========================================================================================="
//		 * ); log.info("Finished add equipment to Wizard: " + equipmentType +
//		 * " serial number: " + converterS erial); log.info(
//		 * "========================================================================================="
//		 * ); return converterSerial;
//		 */
//
//	}
//
//	public static void CustomerProduct(String account_number, String case_id, String trx_id, String start_date)
//			throws Exception {
//// Set the SOAP Environment base Endpoint
//		String SoapEnv = returnOSBEnvSoapEndPoint();
//		/*** Starting the CustomerProduct Flow ***/
//// Read the XML Request File into a String
//		String xmlRequest = SOAPUtils.getSoapRequestFromFile(GeneralUtility.getUserDir(System.getProperty("user.dir"))
//				+ "\\XmlRequests\\CustomerProductForStatus.xml");
//		xmlRequest = xmlRequest.replace("###1", trx_id);
//		xmlRequest = xmlRequest.replace("###2", account_number);
//		xmlRequest = xmlRequest.replace("###3", case_id);
//		xmlRequest = xmlRequest.replace("###4", start_date);
//
//// Send the Soap request to check for available time slots for a technician
//// arrival
//		String soapResponse = SOAPUtils.sendSoapWebService(SoapEnv + "/EAI/Services/Customer/CustomerProduct",
//				"http://www.hot.net.il/eai/contrant/CustomerProducts/NewOperation", xmlRequest);
//
//		if (!soapResponse.contains("OK</ns0:return_description>")) {
//			throw new Exception("Failed on CustomerProduct WS");
//		}
//
//	}
//
//	public static void CustomerProduct(String account_number, String case_id, String trx_id, String start_date,
//			String service_address_id) throws Exception {
//// Set the SOAP Environment base Endpoint
//		String SoapEnv = returnOSBEnvSoapEndPoint();
//		/*** Starting the CustomerProduct Flow ***/
//// Read the XML Request File into a String
//		String xmlRequest = SOAPUtils.getSoapRequestFromFile(GeneralUtility.getUserDir(System.getProperty("user.dir"))
//				+ "\\XmlRequests\\CustomerProductForDirectDebit.xml");
//		xmlRequest = xmlRequest.replace("###1", trx_id);
//		xmlRequest = xmlRequest.replace("###2", account_number);
//		xmlRequest = xmlRequest.replace("###3", case_id);
//		xmlRequest = xmlRequest.replace("###4", start_date);
//		xmlRequest = xmlRequest.replace("###5", service_address_id);
//
//// Send the Soap request to check for available time slots for a technician
//// arrival
//		String soapResponse = SOAPUtils.sendSoapWebService(SoapEnv + "/EAI/Services/Customer/CustomerProduct",
//				"http://www.hot.net.il/eai/contrant/CustomerProducts/NewOperation", xmlRequest);
//
//		if (!soapResponse.contains("OK</ns0:return_description>")) {
//			throw new Exception("Failed on CustomerProduct WS");
//		}
//
//	}
//
//	public static void SetWOTask(String trx_id, String account_number, String work_order_number, String reservation_id,
//			String case_id) throws Exception {
//
//// Set the SOAP Environment base Endpoint
//		String SoapEnv = returnOSBEnvSoapEndPoint();
//		/*** Starting the CustomerProduct Flow ***/
//// Read the XML Request File into a String
//		String xmlRequest = SOAPUtils.getSoapRequestFromFile(
//				GeneralUtility.getUserDir(System.getProperty("user.dir")) + "\\XmlRequests\\SetWOTaskConnect.xml");
//		xmlRequest = xmlRequest.replace("###1", trx_id);
//		xmlRequest = xmlRequest.replace("###2", account_number);
//		xmlRequest = xmlRequest.replace("###3", work_order_number);
//		xmlRequest = xmlRequest.replace("###4", reservation_id);
//		xmlRequest = xmlRequest.replace("###5", case_id);
//
//// Send the Soap request to check for available time slots for a technician
//// arrival
//		String soapResponse = SOAPUtils.sendSoapWebService(SoapEnv + "/EAI/Services/WorkOrder/SetWOTask",
//				"http://www.hot.net.il/eai/contrant/CustomerProducts/NewOperation", xmlRequest);
//
//		if (!soapResponse.contains("OK</ns0:return_description>")) {
//			throw new Exception("Failed on CustomerProduct WS");
//		}
//
//	}
//
//	public static String[] returnLoginParams() {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
