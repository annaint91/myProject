package com.project.city;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;

import com.framework.utility.ExcelUtils;
import com.framework.utility.GeneralUtility;

import com.project.utility.ProjectUtility;



public class AppDataProviders {
	static final Logger log = LogManager.getLogger(AppDataProviders.class.getName());
	String[] connectionDetails = new String[9];

	public static String[] returnConnectionParams() throws IOException {

		String environment = ProjectUtility.getProperty("environment");
		String[] connectionDetails = new String[9];
		switch (environment) {

		case "test": {
			connectionDetails[0] = ProjectUtility.getProperty("crm_test"); // URL
			connectionDetails[1] = ProjectUtility.getProperty("browser"); // URL
//			connectionDetails[EnumDBsConnDetails.OSB_DB_CONN_STRING.getValue()] = CRMUtility
//					.getProperty("osbConnStringTest"); // OSB DB
//			connectionDetails[EnumDBsConnDetails.OSB_DB_USER.getValue()] = CRMUtility
//					.getProperty("osbConnStringUserTest");
//			connectionDetails[EnumDBsConnDetails.OSB_DB_PASS.getValue()] = CRMUtility
//					.getProperty("osbConnStringPassTest");
//
//			connectionDetails[EnumDBsConnDetails.CRM_DB_CONN_STRING.getValue()] = CRMUtility
//					.getProperty("crmConnectionstringCrmsol"); // CRM DB
//			connectionDetails[EnumDBsConnDetails.CRM_DB_USER.getValue()] = CRMUtility
//					.getProperty("crmConnectionuserCrmsol");
//			connectionDetails[EnumDBsConnDetails.CRM_DB_PASS.getValue()] = CRMUtility
//					.getProperty("crmConnectionpassCrmsol");

			break;
		}
		case "prod":	case "production":{
			//connectionDetails[EnumDBsConnDetails.OSB_DB_CONN_STRING.getValue()] = CRMUtility.getProperty("osbConnStringTest"); // OSB DB
			connectionDetails[0] = ProjectUtility.getProperty("cityPortalProd"); // OSB DB
			connectionDetails[1] = ProjectUtility.getProperty("browser"); // OSB DB
			break;
		}

		default:
			log.error("No proper environment was found");
			throw new SkipException("No proper Environment in crm xml...");
		}
		return connectionDetails;
	}

	@DataProvider(name = "getDataFromExcel_netflix")
	public Object[][] getDataFromExcel_netflix() throws Exception {
// Excel excel = new Excel();
		String sExcelPath = GeneralUtility.getUserDir(System.getProperty("user.dir"))
				+ ProjectUtility.getProperty("excelhot_netflix");
		String sExcelSheet = ProjectUtility.getProperty("excelhot_netflix_sheet");
		return ExcelUtils.getTableArray(sExcelPath, sExcelSheet);
	}

	
	@DataProvider(name = "iteration")
	public Object[][] iteration() {
// return new Object[][] { { 1 }, { 2 }, { 3 }, { 4 }, { 5 }, { 6 }, { 7 }, { 8 }, { 9 }, { 10 }, { 11 }, { 12 }, { 13 }, { 14 }, { 15 }, { 16 }, { 17 }, { 18 }, { 19 } , { 20 }};
//return new Object[][] { { 1 }, { 2 }, { 3 }, { 4 }, { 5 }, { 6 }, { 7 }, { 8 }, { 9 } };
//return new Object[][] { { 1 }, { 2 }, { 3 }, { 4 }};
		return new Object[][] { { 1 } };
//return new Object[][] { { 1 } };
	}
	
// @DataProvider(name = "getDataFromDB_AdvancedSearch")
// public Object[][] getDataFromDB_AdvancedSearch() throws Exception {
// // Excel excel = new Excel();
// String sSqlUsersDetails = Constant.getsSqlUsersDetails();
// String sSqlUsersDetails2 = Constant.getsSqlUsersDetails();
//
//
//
// log.info("Executed SQL: " + sSqlUsersDetails2);
// ResultSet resultSet = DBUtility.getResultSet_SetDB(sSqlUsersDetails2,
// GeneralUtility.getProperty("crmconnectionstringsolqa2"),
// GeneralUtility.getProperty("crmconnectionusersolqa2"),
// GeneralUtility.getProperty("crmconnectionpasssolqa2"));
//
// // For count the row
// ResultSet resultSetMiror = DBUtility.getResultSet(sSqlUsersDetails);
//
// ResultSetMetaData rsmd = resultSet.getMetaData();
// String[][] tabArray = null;
// int rowNum = DBUtility.ReturnRowCount(resultSetMiror);
// int colNum = rsmd.getColumnCount();
// tabArray = new String[rowNum][colNum];
//
// int ci = 0, cj;
//
// int columnsNumber = rsmd.getColumnCount();
// while (resultSet.next()) {
// cj = 0;
// for (int i = 1; i <= columnsNumber; i++, cj++) {
// if (i > 1) {
// System.out.print(", ");
// }
// String columnValue = resultSet.getString(i);
// tabArray[ci][cj] = columnValue;
// // System.out.print(columnValue + " " + rsmd.getColumnName(i));
// System.out.print(columnValue);
// }
// System.out.println("");
// ci++;
// }
// return tabArray;
// }

	@DataProvider(name = "getDataFromDB_AdvancedSearchHot_No_Debt_LOGIN")
	public Object[][] getDataFromDB_AdvancedSearchHot_No_Debt_LOGIN() throws Exception {
// Excel excel = new Excel();
		connectionDetails = returnConnectionParams();
//String sSqlUsersDetails2 = Constant.getsSqlUsersDetails();

		String sSqlUsersDetails2 = " Select b.ssn , b.country_code ,b.phone, b.last_name, b.ht_account_number from "
				+ "(select u.ssn , x.country_code ,x.phone, u.last_name, o.ht_account_number "
				+ "from ps_bo_srch_phn x ,ps_rd_site o,wiz_customer_descrip u "
				+ "where x.bo_id = o.bo_id and u.account_number = o.ht_account_number and x.country_code like '05%' and x.primary_ind = 'Y' and u.dln in ('ISR') "
				+ "and x.cm_purpose_type_id in ('2004','12') " + "and o.ht_account_number in "
				+ "(select w.account_number from wiz_customer_hp_life w where w.customer_status = 'AC' and w.customer_type = 'REG') "
				+ "and o.ht_account_number in " + "(select a.account_number from wiz_customer_hp_life a where "
				+ "a.total_late_30_days_amt = 0 " + "and a.total_late_60_days_amt = 0 "
				+ "and a.total_late_90_days_amt = 0 " + "and a.total_late_120_days_amt = 0) "
				+ "and o.ht_account_number in " + "(select h.account_number from wiz_customer_hp_sub_account h "
				+ "where h.sub_account_number ='01' and h.sub_account_status = 'AC' " + "and h.new_payments=0 "
				+ "and h.late_30_days_amt=0 " + "and h.late_60_days_amt =0 " + "and h.current_due=0 "
				+ "and h.late_90_days_amt =0) "
				+ "and o.ht_account_number not in (select k.account_number from wiz_work_order k) "
				+ "ORDER BY DBMS_RANDOM.VALUE ) b " + "Where rownum < 30 " + "and LENGTH(b.ssn)=9";

		log.info("Executed SQL: " + sSqlUsersDetails2);
//		ResultSet resultSet = DBUtility.getResultSet_SetDB(sSqlUsersDetails2,
//				connectionDetails[EnumDBsConnDetails.CRM_DB_CONN_STRING.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_USER.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_PASS.getValue()]);

// For count the row
//		ResultSet resultSetMiror = DBUtility.getResultSet_SetDB(sSqlUsersDetails2,
//				connectionDetails[EnumDBsConnDetails.CRM_DB_CONN_STRING.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_USER.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_PASS.getValue()]);
		String[][] tabArray = null;
		try {
//			ResultSetMetaData rsmd = resultSet.getMetaData();
//			int rowNum = DBUtility.ReturnRowCount(resultSetMiror);
//			int colNum = rsmd.getColumnCount();
//			tabArray = new String[rowNum][colNum];
//			int ci = 0;
//			int cj = 0;
//			int columnsNumber = rsmd.getColumnCount();
//			while (resultSet.next()) {
//				cj = 0;
//				for (int i = 1; i <= columnsNumber; i++, cj++) {
//					if (i > 1) {
//						System.out.print(", ");
//					}
//					String columnValue = resultSet.getString(i);
//					tabArray[ci][cj] = columnValue;
//					System.out.print(columnValue);
//				}
//				System.out.println("");
//				ci++;
//			}
		} catch (Exception e) {
			log.error("Connection Failed! Check output console");
			e.printStackTrace();

		}

		return tabArray;
	}

//	@DataProvider(name = "getDataFromDB_AdvancedSearchHot_No_Debt_LOGIN_BUSNIESS")
//	public Object[][] getDataFromDB_AdvancedSearchHot_No_Debt_LOGIN_BUSNIESS() throws Exception {
//// Excel excel = new Excel();
//		connectionDetails = returnConnectionParams();
////String sSqlUsersDetails2 = Constant.getsSqlUsersDetails();
//
//		String sSqlUsersDetails2 = " Select b.ssn , b.country_code ,b.phone, b.last_name, b.ht_account_number from "
//				+ " (select u.ssn , x.country_code ,x.phone, u.last_name, o.ht_account_number, u.commercial_flag "
//				+ " from ps_bo_srch_phn x ,ps_rd_site o,wiz_customer_descrip u "
//				+ " where x.bo_id = o.bo_id and u.account_number = o.ht_account_number and x.country_code like '05%' and x.primary_ind = 'Y' --and u.dln in ('ISR') "
//				+ " and u.dln = 'SOH' " + " and o.ht_account_number in "
//				+ " (select h.account_number from wiz_customer_hp_sub_account h "
//				+ " where h.sub_account_status = 'AC' " + " and h.late_30_days_amt=0 " + " and h.late_60_days_amt =0 "
//				+ " and h.current_due=0 " + " and h.late_90_days_amt =0) "
//				+ " and o.ht_account_number not in (select k.account_number from wiz_work_order k) "
//				+ " ORDER BY DBMS_RANDOM.VALUE ) b " + " Where rownum < 3 " + " and LENGTH(b.ssn)=9 "
//				+ " and rownum < 2";

//		log.info("Executed SQL: " + sSqlUsersDetails2);
//		ResultSet resultSet = DBUtility.getResultSet_SetDB(sSqlUsersDetails2,
//				connectionDetails[EnumDBsConnDetails.CRM_DB_CONN_STRING.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_USER.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_PASS.getValue()]);
//
//// For count the row
//		ResultSet resultSetMiror = DBUtility.getResultSet_SetDB(sSqlUsersDetails2,
//				connectionDetails[EnumDBsConnDetails.CRM_DB_CONN_STRING.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_USER.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_PASS.getValue()]);
//		String[][] tabArray = null;
//		try {
//			ResultSetMetaData rsmd = resultSet.getMetaData();
//			int rowNum = DBUtility.ReturnRowCount(resultSetMiror);
//			int colNum = rsmd.getColumnCount();
//			tabArray = new String[rowNum][colNum];
//			int ci = 0;
//			int cj = 0;
//			int columnsNumber = rsmd.getColumnCount();
//			while (resultSet.next()) {
//				cj = 0;
//				for (int i = 1; i <= columnsNumber; i++, cj++) {
//					if (i > 1) {
//						System.out.print(", ");
//					}
//					String columnValue = resultSet.getString(i);
//					tabArray[ci][cj] = columnValue;
//					System.out.print(columnValue);
//				}
//				System.out.println("");
//				ci++;
//			}
//		} catch (Exception e) {
//			log.error("Connection Failed! Check output console");
//			e.printStackTrace();
//
//		}

//		return tabArray;
//	}

//	@DataProvider(name = "getDataFromDB_AdvancedSearchHot_No_Debt_LOGIN_BUSNIESS_2")
//	public Object[][] getDataFromDB_AdvancedSearchHot_No_Debt_LOGIN_BUSNIESS_2() throws Exception {
//// Excel excel = new Excel();
//		connectionDetails = returnConnectionParams();
////String sSqlUsersDetails2 = Constant.getsSqlUsersDetails();
//
//		String sSqlUsersDetails2 = " Select b.ssn , b.country_code ,b.phone, b.last_name, b.ht_account_number from "
//				+ " (select u.ssn , x.country_code ,x.phone, u.last_name, o.ht_account_number, u.commercial_flag "
//				+ " from ps_bo_srch_phn x ,ps_rd_site o,wiz_customer_descrip u "
//				+ " where x.bo_id = o.bo_id and u.account_number = o.ht_account_number and x.country_code like '05%' and x.primary_ind = 'Y' --and u.dln in ('ISR') "
//				+ " and u.dln = 'SOH' " + " and o.ht_account_number in "
//				+ " (select h.account_number from wiz_customer_hp_sub_account h "
//				+ " where h.sub_account_status = 'AC' " + " and h.late_30_days_amt=0 " + " and h.late_60_days_amt =0 "
//				+ " and h.current_due=0 " + " and h.late_90_days_amt =0) "
//				+ " and o.ht_account_number not in (select k.account_number from wiz_work_order k) "
//				+ " ORDER BY DBMS_RANDOM.VALUE ) b " + " Where rownum < 3 " + " and LENGTH(b.ssn)=9 "
//				+ " and rownum < 3";
//
//		log.info("Executed SQL: " + sSqlUsersDetails2);
//		ResultSet resultSet = DBUtility.getResultSet_SetDB(sSqlUsersDetails2,
//				connectionDetails[EnumDBsConnDetails.CRM_DB_CONN_STRING.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_USER.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_PASS.getValue()]);
//
//// For count the row
//		ResultSet resultSetMiror = DBUtility.getResultSet_SetDB(sSqlUsersDetails2,
//				connectionDetails[EnumDBsConnDetails.CRM_DB_CONN_STRING.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_USER.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_PASS.getValue()]);
//		String[][] tabArray = null;
//		try {
//			ResultSetMetaData rsmd = resultSet.getMetaData();
//			int rowNum = DBUtility.ReturnRowCount(resultSetMiror);
//			int colNum = rsmd.getColumnCount();
//			tabArray = new String[rowNum][colNum];
//			int ci = 0;
//			int cj = 0;
//			int columnsNumber = rsmd.getColumnCount();
//			while (resultSet.next()) {
//				cj = 0;
//				for (int i = 1; i <= columnsNumber; i++, cj++) {
//					if (i > 1) {
//						System.out.print(", ");
//					}
//					String columnValue = resultSet.getString(i);
//					tabArray[ci][cj] = columnValue;
//					System.out.print(columnValue);
//				}
//				System.out.println("");
//				ci++;
//			}
//		} catch (Exception e) {
//			log.error("Connection Failed! Check output console");
//			e.printStackTrace();
//
//		}
//
//		return tabArray;
//	}
//
	
	
	
//	@DataProvider(name = "getDataFromDB_AdvancedSearchHot_RELOCATION_HEDERA")
//	public Object[][] getDataFromDB_AdvancedSearchHot_RELOCATION_HEDERA() throws Exception {
//// Excel excel = new Excel();
//		connectionDetails = returnConnectionParams();
////String sSqlUsersDetails2 = Constant.getsSqlUsersDetails();
//
//		String sSqlUsersDetails2 = "Select b.ssn , b.country_code ,b.phone, b.last_name, b.ht_account_number from "
//				+ " (select u.ssn , x.country_code ,x.phone, u.last_name, o.ht_account_number "
//				+ " from ps_bo_srch_phn x ,ps_rd_site o,wiz_customer_descrip u "
//				+ " where x.bo_id = o.bo_id and u.account_number = o.ht_account_number and x.country_code like '05%' and x.primary_ind = 'Y' and u.dln in ('ISR') "
//				+ " and x.cm_purpose_type_id in ('2004','12') "
//				+ " and o.ht_account_number in (select hh.account_number "
//				+ " from wiz_customer_hp_life hh ,wiz_hp_description dd "
//				+ " where dd.service_address_id = hh.service_address_id "
//				+ " and dd.city = 'חדרה' and hh.customer_status = 'AC') " + " and o.ht_account_number in "
//				+ " (select w.account_number from wiz_customer_hp_life w where w.customer_status = 'AC' and w.customer_type = 'REG') "
//				+ " and o.ht_account_number in " + " (select a.account_number from wiz_customer_hp_life a where "
//				+ " a.total_late_30_days_amt = 0 " + " and a.total_late_60_days_amt = 0 "
//				+ " and a.total_late_90_days_amt = 0 " + " and a.total_late_120_days_amt = 0) "
//				+ " and o.ht_account_number in " + " (select h.account_number from wiz_customer_hp_sub_account h "
//				+ " where h.sub_account_number ='01' and h.sub_account_status = 'AC' " + " and h.new_payments=0 "
//				+ " and h.late_30_days_amt=0 " + " and h.late_60_days_amt =0 " + " and h.current_due=0 "
//				+ " and h.late_90_days_amt =0) "
//				+ " and o.ht_account_number not in (select k.account_number from wiz_work_order k) "
//				+ " ORDER BY DBMS_RANDOM.VALUE ) b " + " Where rownum < 2 and LENGTH(b.ssn)=9 ";
//
//		log.info("Executed SQL: " + sSqlUsersDetails2);
//		ResultSet resultSet = DBUtility.getResultSet_SetDB(sSqlUsersDetails2,
//				connectionDetails[EnumDBsConnDetails.CRM_DB_CONN_STRING.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_USER.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_PASS.getValue()]);
//
//// For count the row
//		ResultSet resultSetMiror = DBUtility.getResultSet_SetDB(sSqlUsersDetails2,
//				connectionDetails[EnumDBsConnDetails.CRM_DB_CONN_STRING.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_USER.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_PASS.getValue()]);
//		String[][] tabArray = null;
//		try {
//			ResultSetMetaData rsmd = resultSet.getMetaData();
//			int rowNum = DBUtility.ReturnRowCount(resultSetMiror);
//			int colNum = rsmd.getColumnCount();
//			tabArray = new String[rowNum][colNum];
//			int ci = 0;
//			int cj = 0;
//			int columnsNumber = rsmd.getColumnCount();
//			while (resultSet.next()) {
//				cj = 0;
//				for (int i = 1; i <= columnsNumber; i++, cj++) {
//					if (i > 1) {
//						System.out.print(", ");
//					}
//					String columnValue = resultSet.getString(i);
//					tabArray[ci][cj] = columnValue;
//					System.out.print(columnValue);
//				}
//				System.out.println("");
//				ci++;
//			}
//		} catch (Exception e) {
//			log.error("Connection Failed! Check output console");
//			e.printStackTrace();
//
//		}
//
//		return tabArray;
//	}

//	@DataProvider(name = "getDataFromDB_AdvancedSearchHot_No_Debt")
//	public Object[][] getDataFromDB_AdvancedSearchHot_No_Debt() throws Exception {
//// Excel excel = new Excel();
//		connectionDetails = returnConnectionParams();
////String sSqlUsersDetails2 = Constant.getsSqlUsersDetails();
//		String randomNumber = GeneralUtility.randomNumber(2);
//
//		String sSqlUsersDetails2 = " Select b.ssn , b.country_code ,b.phone, b.last_name, b.ht_account_number from "
//				+ " (select u.ssn , x.country_code ,x.phone, u.last_name, o.ht_account_number "
//				+ " from ps_bo_srch_phn x ,ps_rd_site o,wiz_customer_descrip u "
//				+ " where x.bo_id = o.bo_id and u.account_number = o.ht_account_number and x.country_code like '05%' and x.primary_ind = 'Y' and u.dln in ('ISR') "
//				+ " and x.cm_purpose_type_id in ('2004','12') " + " and o.ht_account_number in "
//				+ " (select w.account_number from wiz_customer_hp_life w where w.customer_status = 'AC' and w.customer_type = 'REG' "
//				+ " ) " + " and o.ht_account_number in "
//				+ " (select h.account_number from wiz_customer_hp_sub_account h "
//				+ " where h.sub_account_number ='02' and h.sub_account_status = 'AC' " + " and h.late_30_days_amt=0 "
//				+ " and h.late_60_days_amt =0 " + " and h.late_90_days_amt =0 " + " and h.current_due=0 " + " ) "
//				+ " and not exists" + " (select h.account_number from wiz_customer_hp_sub_account h "
//				+ " where h.account_number =3D o.ht_account_number " + " and h.account_number like '%#randomNumber#%' "
//				+ " and (h.late_30_days_amt>19 or " + " h.late_60_days_amt >19 or " + " h.late_90_days_amt >19 or "
//				+ " h.current_due>19 )" + " )"
//				+ " and o.ht_account_number not in (select k.account_number from wiz_work_order k) "
//				+ " ORDER BY DBMS_RANDOM.VALUE ) b " + " where LENGTH(b.ssn)=9 " + " AND rownum < 2 ";
//
//		sSqlUsersDetails2 = sSqlUsersDetails2.replace("#randomNumber#", randomNumber);
//
//		log.info("Executed SQL: " + sSqlUsersDetails2);
//		ResultSet resultSet = DBUtility.getResultSet_SetDB(sSqlUsersDetails2,
//				connectionDetails[EnumDBsConnDetails.CRM_DB_CONN_STRING.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_USER.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_PASS.getValue()]);
//
//// For count the row
//		ResultSet resultSetMiror = DBUtility.getResultSet_SetDB(sSqlUsersDetails2,
//				connectionDetails[EnumDBsConnDetails.CRM_DB_CONN_STRING.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_USER.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_PASS.getValue()]);
//		String[][] tabArray = null;
//		try {
//			ResultSetMetaData rsmd = resultSet.getMetaData();
//			int rowNum = DBUtility.ReturnRowCount(resultSetMiror);
//			int colNum = rsmd.getColumnCount();
//			tabArray = new String[rowNum][colNum];
//			int ci = 0;
//			int cj = 0;
//			int columnsNumber = rsmd.getColumnCount();
//			while (resultSet.next()) {
//				cj = 0;
//				for (int i = 1; i <= columnsNumber; i++, cj++) {
//					if (i > 1) {
//						System.out.print(", ");
//					}
//					String columnValue = resultSet.getString(i);
//					tabArray[ci][cj] = columnValue;
//					System.out.print(columnValue);
//				}
//				System.out.println("");
//				ci++;
//			}
//		} catch (Exception e) {
//			log.error("Connection Failed! Check output console");
//			e.printStackTrace();
//
//		}
//
//		return tabArray;
//	}

//	@DataProvider(name = "getDataFromDB_TV_AdvancedSearchHot_No_Debt")
//	public Object[][] getDataFromDB_TV_AdvancedSearchHot_No_Debt() throws Exception {
//// Excel excel = new Excel();
//		connectionDetails = returnConnectionParams();
////String sSqlUsersDetails2 = Constant.getsSqlUsersDetails();
//
//		String sSqlUsersDetails2 = "Select b.ssn , b.country_code ,b.phone, b.last_name, b.ht_account_number from "
//				+ " (select u.ssn , x.country_code ,x.phone, u.last_name, o.ht_account_number "
//				+ " from ps_bo_srch_phn x ,ps_rd_site o,wiz_customer_descrip u "
//				+ " where x.bo_id = o.bo_id and u.account_number = o.ht_account_number and x.country_code like '05%' and x.primary_ind = 'Y' and u.dln in ('ISR') "
//				+
////" and u.mobile_number IS NOT NULL AND u.mobile_number NOT LIKE ' '" +
//				" and x.cm_purpose_type_id in ('2004','12') " + " and o.ht_account_number in "
//				+ " (select w.account_number from wiz_customer_hp_life w where w.customer_status = 'AC' and w.customer_type = 'REG' "
//				+ " ) " + " and o.ht_account_number in "
//				+ " (select h.account_number from wiz_customer_hp_sub_account h "
//				+ " where h.sub_account_number ='02' and h.sub_account_status = 'AC' " + " and h.late_30_days_amt=0 "
//				+ " and h.late_60_days_amt =0 " + " and h.late_90_days_amt =0 " + " and h.current_due=0 " + " ) "
//				+ " and not exists " + " (select h.account_number from wiz_customer_hp_sub_account h "
//				+ " where h.account_number = o.ht_account_number " + " and (h.late_30_days_amt>19 or "
//				+ " h.late_60_days_amt >19 or " + " h.late_90_days_amt >19 or " + " h.current_due>19 ) " + " ) "
//				+ " and o.ht_account_number not in (select k.account_number from wiz_work_order k) "
//				+ " ORDER BY DBMS_RANDOM.VALUE ) b " + " where LENGTH(b.ssn)=9 " + " AND rownum < 2 ";
//
//		log.info("Executed SQL: " + sSqlUsersDetails2);
//		ResultSet resultSet = DBUtility.getResultSet_SetDB(sSqlUsersDetails2,
//				connectionDetails[EnumDBsConnDetails.CRM_DB_CONN_STRING.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_USER.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_PASS.getValue()]);
//
//// For count the row
//		ResultSet resultSetMiror = DBUtility.getResultSet_SetDB(sSqlUsersDetails2,
//				connectionDetails[EnumDBsConnDetails.CRM_DB_CONN_STRING.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_USER.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_PASS.getValue()]);
//		String[][] tabArray = null;
//		try {
//			ResultSetMetaData rsmd = resultSet.getMetaData();
//			int rowNum = DBUtility.ReturnRowCount(resultSetMiror);
//			int colNum = rsmd.getColumnCount();
//			tabArray = new String[rowNum][colNum];
//			int ci = 0;
//			int cj = 0;
//			int columnsNumber = rsmd.getColumnCount();
//			while (resultSet.next()) {
//				cj = 0;
//				for (int i = 1; i <= columnsNumber; i++, cj++) {
//					if (i > 1) {
//						System.out.print(", ");
//					}
//					String columnValue = resultSet.getString(i);
//					tabArray[ci][cj] = columnValue;
//					System.out.print(columnValue);
//				}
//				System.out.println("");
//				ci++;
//			}
//		} catch (Exception e) {
//			log.error("Connection Failed! Check output console");
//			e.printStackTrace();
//
//		}
//
//		return tabArray;
//	}
//

	
//	@DataProvider(name = "getDataFromDB_INTERNET_Hot_No_Debt")
//	public Object[][] getDataFromDB_INTERNET_Hot_No_Debt() throws Exception {
//// Excel excel = new Excel();
//		connectionDetails = returnConnectionParams();
////String sSqlUsersDetails2 = Constant.getsSqlUsersDetails();
//
//		String sSqlUsersDetails2 = " Select b.ssn , b.country_code ,b.phone, b.last_name, b.ht_account_number from "
//				+ " (select u.ssn , x.country_code ,x.phone, u.last_name, o.ht_account_number "
//				+ " from ps_bo_srch_phn x ,ps_rd_site o,wiz_customer_descrip u "
//				+ " where x.bo_id = o.bo_id and u.account_number = o.ht_account_number and x.country_code like '05%' and x.primary_ind = 'Y' and u.dln in ('ISR') "
//				+ " and x.cm_purpose_type_id in ('2004','12') " + " and o.ht_account_number in "
//				+ " (select w.account_number from wiz_customer_hp_life w where w.customer_status = 'AC' " + " ) "
//				+ " and o.ht_account_number in " + " (select h.account_number from wiz_customer_hp_sub_account h "
//				+ " where h.sub_account_number ='02' and h.sub_account_status = 'AC' " + " and h.late_30_days_amt=0 "
//				+ " and h.late_60_days_amt =0 " + " and h.late_90_days_amt =0 " + " and h.current_due=0 "
//				+ " and h.late_90_days_amt =0" + " and h.branch_account_number = 0) "
//				+ " and o.ht_account_number not in (select k.account_number from wiz_work_order k) "
//				+ " ORDER BY DBMS_RANDOM.VALUE ) b where LENGTH(b.ssn)=9 AND rownum < 2 ";
//
//		log.info("Executed SQL: " + sSqlUsersDetails2);
//		ResultSet resultSet = DBUtility.getResultSet_SetDB(sSqlUsersDetails2,
//				connectionDetails[EnumDBsConnDetails.CRM_DB_CONN_STRING.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_USER.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_PASS.getValue()]);
//
//// For count the row
//		ResultSet resultSetMiror = DBUtility.getResultSet_SetDB(sSqlUsersDetails2,
//				connectionDetails[EnumDBsConnDetails.CRM_DB_CONN_STRING.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_USER.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_PASS.getValue()]);
//		String[][] tabArray = null;
//		try {
//			ResultSetMetaData rsmd = resultSet.getMetaData();
//			int rowNum = DBUtility.ReturnRowCount(resultSetMiror);
//			int colNum = rsmd.getColumnCount();
//			tabArray = new String[rowNum][colNum];
//			int ci = 0;
//			int cj = 0;
//			int columnsNumber = rsmd.getColumnCount();
//			while (resultSet.next()) {
//				cj = 0;
//				for (int i = 1; i <= columnsNumber; i++, cj++) {
//					if (i > 1) {
//						System.out.print(", ");
//					}
//					String columnValue = resultSet.getString(i);
//					tabArray[ci][cj] = columnValue;
//					System.out.print(columnValue);
//				}
//				System.out.println("");
//				ci++;
//			}
//		} catch (Exception e) {
//			log.error("Connection Failed! Check output console");
//			e.printStackTrace();
//
//		}
//
//		return tabArray;
//	}
//
//	@DataProvider(name = "getDataFromDB_with_Debt")
//	public Object[][] getDataFromDB_with_Debt() throws Exception {
//// Excel excel = new Excel();
//		connectionDetails = returnConnectionParams();
////String sSqlUsersDetails2 = Constant.getsSqlUsersDetails();
//		String rand = GeneralUtility.randomNumber(2);
//		String sSqlUsersDetails2 = "Select b.ht_account_number from " + " (select o.ht_account_number "
//				+ " from ps_bo_srch_phn x , ps_rd_site o, wiz_customer_descrip u " + " where x.bo_id = o.bo_id "
//				+ " and u.account_number =o.ht_account_number " + " and u.account_number like '%" + rand + "%' "
//				+ " and x.country_code like '05%' " + " and x.country_code NOT like '051' "
//				+ " and x.primary_ind = 'Y' " + " and u.dln in ('ISR','PAS') " + " and o.ht_account_number in "
//				+ " (select w.account_number from wiz_customer_hp_life w where w.customer_status = 'AC' and w.customer_type = 'REG' ) "
//				+ " and o.ht_account_number not in (select d.account_number from wiz_customer_freezing d ) "
//				+ " and o.ht_account_number not in (select c.account_number from wiz_customer_freezing_hist c ) "
//				+ " and o.ht_account_number in " + " (select h.account_number " + " from wiz_customer_hp_sub_account h "
//				+ " where ( h.late_60_days_amt ) > 50 " + " and h.sub_account_number = 1 " + " and h.new_payments=0 "
//				+ " and h.sub_account_status = 'AC' " + " and h.account_number in " + " (select cc.account_number "
//				+ " from wiz_customer_hp_debit_auth cc " + " where cc.debit_method = 'C' "
//				+ " and cc.debit_status = 'I' " + " )) " + " ORDER BY DBMS_RANDOM.VALUE ) b WHERE rownum < 2";
//
//		log.info("Executed SQL: " + sSqlUsersDetails2);
//		ResultSet resultSet = DBUtility.getResultSet_SetDB(sSqlUsersDetails2,
//				connectionDetails[EnumDBsConnDetails.CRM_DB_CONN_STRING.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_USER.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_PASS.getValue()]);
//
//// For count the row
//		ResultSet resultSetMiror = DBUtility.getResultSet_SetDB(sSqlUsersDetails2,
//				connectionDetails[EnumDBsConnDetails.CRM_DB_CONN_STRING.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_USER.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_PASS.getValue()]);
//		String[][] tabArray = null;
//		try {
//			ResultSetMetaData rsmd = resultSet.getMetaData();
//			int rowNum = DBUtility.ReturnRowCount(resultSetMiror);
//			int colNum = rsmd.getColumnCount();
//			tabArray = new String[rowNum][colNum];
//			int ci = 0;
//			int cj = 0;
//			int columnsNumber = rsmd.getColumnCount();
//			while (resultSet.next()) {
//				cj = 0;
//				for (int i = 1; i <= columnsNumber; i++, cj++) {
//					if (i > 1) {
//						System.out.print(", ");
//					}
//					String columnValue = resultSet.getString(i);
//					tabArray[ci][cj] = columnValue;
////System.out.print(columnValue);
//				}
////System.out.println("");
//				ci++;
//			}
//		} catch (Exception e) {
//			log.error("Connection Failed! Check output console");
//			e.printStackTrace();
//
//		}
//
//		return tabArray;
//	}
//
//	@DataProvider(name = "getDataFromDB_with_Debt_No_Legal")
//	public Object[][] getDataFromDB_with_Debt_No_Legal() throws Exception {
//// Excel excel = new Excel();
//		connectionDetails = returnConnectionParams();
//		String sSqlUsersDetails2 = "";// Constant.getsSql_for_gettingAccountNumber_client_withDebt();
//
//		log.info("Executed SQL: " + sSqlUsersDetails2);
//		ResultSet resultSet = DBUtility.getResultSet_SetDB(sSqlUsersDetails2,
//				connectionDetails[EnumDBsConnDetails.CRM_DB_CONN_STRING.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_USER.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_PASS.getValue()]);
//
//// For count the row
//		ResultSet resultSetMiror = DBUtility.getResultSet_SetDB(sSqlUsersDetails2,
//				connectionDetails[EnumDBsConnDetails.CRM_DB_CONN_STRING.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_USER.getValue()],
//				connectionDetails[EnumDBsConnDetails.CRM_DB_PASS.getValue()]);
//		String[][] tabArray = null;
//		try {
//			ResultSetMetaData rsmd = resultSet.getMetaData();
//			int rowNum = DBUtility.ReturnRowCount(resultSetMiror);
//			int colNum = rsmd.getColumnCount();
//			tabArray = new String[rowNum][colNum];
//			int ci = 0;
//			int cj = 0;
//			int columnsNumber = rsmd.getColumnCount();
//			while (resultSet.next()) {
//				cj = 0;
//				for (int i = 1; i <= columnsNumber; i++, cj++) {
//					if (i > 1) {
//						System.out.print(", ");
//					}
//					String columnValue = resultSet.getString(i);
//					tabArray[ci][cj] = columnValue;
////System.out.print(columnValue);
//				}
////System.out.println("");
//				ci++;
//			}
//		} catch (Exception e) {
//			log.error("Connection Failed! Check output console");
//			e.printStackTrace();
//
//		}
//
//		return tabArray;
//	}
//
//	@DataProvider(name = "getAccountsForSendingBill")
//	public Object[][] getAccountsForSendingBill() throws Exception {
//		String[][] tabArray = null;
//
//		String sSqlQueryCustomer = "";// Constant.getClientsWithCurrentBillData();
//		String numberOfUsers = "1";
//		sSqlQueryCustomer = sSqlQueryCustomer.replace("NNN", numberOfUsers);
//
//		log.info("Query for billing is: " + sSqlQueryCustomer);
//		String connString = "";
//		String connStringUser = "";
//		String connStringPass = "";
//		String[] params = returnConnectionParams();
//
//// tabArray = DBUtility.getDataFromDB_AdvancedSearchUtil(sSqlQueryCustomer,connString,connStringUser,connStringPass);
//// String accounts = tabArray[0][0];
////Remove when have real numbers...
////accounts = "7872922";
//
//		String sSqlUsersDetails2 = "Select b.ssn , b.country_code ,b.phone, b.last_name, b.ht_account_number from "
//				+ " (select u.ssn , x.country_code ,x.phone, u.last_name, o.ht_account_number "
//				+ " from ps_bo_srch_phn x ,ps_rd_site o,wiz_customer_descrip u "
//				+ " where x.bo_id = o.bo_id and u.account_number = o.ht_account_number and x.country_code like '05%' and x.primary_ind = 'Y' and u.dln in ('ISR') "
//				+ " and x.cm_purpose_type_id in ('2004','12') " + " and o.ht_account_number in "
//				+ " (select w.account_number from wiz_customer_hp_life w where w.customer_status = 'AC' and w.customer_type = 'REG' "
//				+ " ) " + " and o.ht_account_number in "
//				+ " (select h.account_number from wiz_customer_hp_sub_account h " +
////" where h.sub_account_number ='02' " + //Internet client
//				" where h.sub_account_status = 'AC' " + " and h.late_30_days_amt=0 " + " and h.late_60_days_amt =0 "
//				+ " and h.late_90_days_amt =0 " + " and h.current_due=0 " + " and h.late_90_days_amt =0 "
//				+ " and h.branch_account_number = 0) "
//				+ " and o.ht_account_number not in (select k.account_number from wiz_work_order k) "
//				+ " ORDER BY DBMS_RANDOM.VALUE ) b " + " where LENGTH(b.ssn)=9 "
//				+ " and b.ht_account_number in (SELECT DISTINCT x.account_number FROM wiz__site_customer_stmt x WHERE (x.record_part_key = '20180801'))"
//				+ " AND rownum < 2 ";
//
//		connString = params[EnumDBsConnDetails.CRM_DB_CONN_STRING.getValue()];
//		connStringUser = params[EnumDBsConnDetails.CRM_DB_USER.getValue()];
//		connStringPass = params[EnumDBsConnDetails.CRM_DB_PASS.getValue()];
//
//		log.info("Executed SQL: " + sSqlUsersDetails2);
//		ResultSet resultSet = DBUtility.getResultSet_SetDB(sSqlUsersDetails2, connString, connStringUser,
//				connStringPass);
////For count the row
//		ResultSet resultSetMiror = DBUtility.getResultSet_SetDB(sSqlUsersDetails2, connString, connStringUser,
//				connStringPass);
////String[][] tabArray = null;
//		try {
//			ResultSetMetaData rsmd = resultSet.getMetaData();
//			int rowNum = DBUtility.ReturnRowCount(resultSetMiror);
//			int colNum = rsmd.getColumnCount();
//			tabArray = new String[rowNum][colNum];
//			int ci = 0;
//			int cj = 0;
//			int columnsNumber = rsmd.getColumnCount();
//			while (resultSet.next()) {
//				cj = 0;
//				for (int i = 1; i <= columnsNumber; i++, cj++) {
//					if (i > 1) {
//						System.out.print(", ");
//					}
//					String columnValue = resultSet.getString(i);
//					tabArray[ci][cj] = columnValue;
//					System.out.print(columnValue);
//				}
//				System.out.println("");
//				ci++;
//			}
//		} catch (Exception e) {
//			log.error("Connection Failed! Check output console");
//			e.printStackTrace();
//
//		}
//
//		return tabArray;
//	}
//
//	@DataProvider(name = "getAccountsForSendingBill_new")
//	public Object[][] getAccountsForSendingBill_new() throws Exception {
//		String[][] tabArray = null;
//
//		String sSqlQueryCustomer = "";// Constant.getClientsWithCurrentBillData();
//		String numberOfUsers = "2";
//		sSqlQueryCustomer = sSqlQueryCustomer.replace("NNN", numberOfUsers);
//
//		log.info("Query for billing is: " + sSqlQueryCustomer);
//		String connString = "";
//		String connStringUser = "";
//		String connStringPass = "";
//		String[] params = returnConnectionParams();
//
//// tabArray = DBUtility.getDataFromDB_AdvancedSearchUtil(sSqlQueryCustomer,connString,connStringUser,connStringPass);
//// String accounts = tabArray[0][0];
////Remove when have real numbers...
////accounts = "7872922";
//
//		String sSqlUsersDetails2 = "Select b.ssn , b.country_code ,b.phone, b.last_name, b.ht_account_number from "
//				+ " (select u.ssn , x.country_code ,x.phone, u.last_name, o.ht_account_number "
//				+ " from ps_bo_srch_phn x ,ps_rd_site o,wiz_customer_descrip u "
//				+ " where x.bo_id = o.bo_id and u.account_number = o.ht_account_number and x.country_code like '05%' and x.primary_ind = 'Y' and u.dln in ('ISR') "
//				+ " and x.cm_purpose_type_id in ('2004','12') " + " and o.ht_account_number in "
//				+ " (select w.account_number from wiz_customer_hp_life w where w.customer_status = 'AC' and w.customer_type = 'REG' "
//				+ " ) " + " and o.ht_account_number in "
//				+ " (select h.account_number from wiz_customer_hp_sub_account h "
//				+ " where h.sub_account_number ='02' and h.sub_account_status = 'AC' " + " and h.late_30_days_amt=0 "
//				+ " and h.late_60_days_amt =0 " + " and h.late_90_days_amt =0 " + " and h.current_due=0 " + " ) " + " "
//				+ " and not exists " + " (select h.account_number from wiz_customer_hp_sub_account h "
//				+ " where h.account_number = o.ht_account_number " + " -- and h.account_number like '%21%' "
//				+ " and (h.late_30_days_amt>19 or " + " h.late_60_days_amt >19 or " + " h.late_90_days_amt >19 or "
//				+ " h.current_due>19 ) " + " ) " + " " + " and exists "
//				+ " (SELECT DISTINCT x.account_number FROM wiz__site_customer_stmt x "
//				+ " WHERE (x.record_part_key = '20180601') " + " ) "
//				+ " and o.ht_account_number not in (select k.account_number from wiz_work_order k) "
//				+ " ORDER BY DBMS_RANDOM.VALUE ) b " + " where LENGTH(b.ssn)=9 " + " AND rownum < 2 ";
//
//		connString = params[EnumDBsConnDetails.CRM_DB_CONN_STRING.getValue()];
//		connStringUser = params[EnumDBsConnDetails.CRM_DB_USER.getValue()];
//		connStringPass = params[EnumDBsConnDetails.CRM_DB_PASS.getValue()];
//
//		log.info("Executed SQL: " + sSqlUsersDetails2);
//		ResultSet resultSet = DBUtility.getResultSet_SetDB(sSqlUsersDetails2, connString, connStringUser,
//				connStringPass);
////For count the row
//		ResultSet resultSetMiror = DBUtility.getResultSet_SetDB(sSqlUsersDetails2, connString, connStringUser,
//				connStringPass);
////String[][] tabArray = null;
//		try {
//			ResultSetMetaData rsmd = resultSet.getMetaData();
//			int rowNum = DBUtility.ReturnRowCount(resultSetMiror);
//			int colNum = rsmd.getColumnCount();
//			tabArray = new String[rowNum][colNum];
//			int ci = 0;
//			int cj = 0;
//			int columnsNumber = rsmd.getColumnCount();
//			while (resultSet.next()) {
//				cj = 0;
//				for (int i = 1; i <= columnsNumber; i++, cj++) {
//					if (i > 1) {
//						System.out.print(", ");
//					}
//					String columnValue = resultSet.getString(i);
//					tabArray[ci][cj] = columnValue;
//					System.out.print(columnValue);
//				}
//				System.out.println("");
//				ci++;
//			}
//		} catch (Exception e) {
//			log.error("Connection Failed! Check output console");
//			e.printStackTrace();
//
//		}
//
//		return tabArray;
//	}
	

// @DataProvider(name = "getDataFromExcel_AdvancedSearch")
// public Object[][] getDataFromExcel_AdvancedSearch() throws Exception {
// // Excel excel = new Excel();
// String sExcelPath = GeneralUtility.getProperty("excelhot");
// String sExcelSheet = GeneralUtility.getProperty("excelsheethot1");
//
// // String workingDir = System.getProperty("user.dir");
//
// return ExcelUtils.getTableArray(sExcelPath, sExcelSheet);
// }
//// read from exel sheet for nagtiveTest
//	@DataProvider(name = "getDataFromExcel_PersonalDetails")
//	public Object[][] getDataFromExcel_PersonalDetails() throws Exception {
//		
//		String sExcelPath1 = ProjectUtility.getProperty("excelNagtive3");
//		String sExcelSheet1 = ProjectUtility.getProperty("excelNagtivesheet");
//// Return the data in cells for Excel sheet
//		GeneralUtility.Sleep(0);
//		return ExcelUtils.getTableArray(sExcelPath1, sExcelSheet1);
//	}

}
