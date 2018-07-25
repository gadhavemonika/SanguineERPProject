package com.sanguine.dao;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.sanguine.model.clsCompanyMasterModel;
import com.sanguine.model.clsPropertySetupModel;
import com.sanguine.service.clsGlobalFunctionsService;
import com.sanguine.service.clsSetupMasterService;

@Repository("clsStructureUpdateDao")
public class clsStructureUpdateDaoImpl implements clsStructureUpdateDao {
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private SessionFactory webBooksSessionFactory;

	@Autowired
	private SessionFactory webPMSSessionFactory;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	@Autowired
	private clsSetupMasterService objSetupMasterService;

	@Override
	public void funUpdateStructure(String clientCode, HttpServletRequest req) {
		String sql = "";
		sql = "ALTER TABLE `tblpropertymaster` DROP COLUMN `strAdd1`;";
		funExecuteQuery(sql);
		sql = "ALTER TABLE `tblpropertymaster` DROP COLUMN `strAdd2`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblpropertymaster` DROP COLUMN `strCity`;";
		funExecuteQuery(sql);
		sql = "ALTER TABLE `tblpropertymaster` DROP COLUMN `strState`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblpropertymaster` DROP COLUMN `strCountry`;";
		funExecuteQuery(sql);
		sql = "ALTER TABLE `tblpropertymaster` DROP COLUMN `strPin`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblpropertymaster` DROP COLUMN `strPhone`;";
		funExecuteQuery(sql);
		sql = "ALTER TABLE `tblpropertymaster` DROP COLUMN `strMobile`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblpropertymaster` DROP COLUMN `strFax`;";
		funExecuteQuery(sql);
		sql = "ALTER TABLE `tblpropertymaster` DROP COLUMN `strContact`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblpropertymaster` DROP COLUMN `strEmail`;";
		funExecuteQuery(sql);

		sql = "CREATE TABLE IF NOT EXISTS `tblposlinkup` (" + "  `strPOSItemCode` varchar(20) NOT NULL," + " `strPOSItemName` varchar(100) NOT NULL," + " `strWSItemCode` varchar(20) NOT NULL DEFAULT ''," + " `strWSItemName` varchar(100) NOT NULL DEFAULT ''," + "  `strClientCode` varchar(20) NOT NULL," + "  PRIMARY KEY (`strPOSItemCode`,`strClientCode`)" + ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";

		funExecuteQuery(sql);

		sql = "CREATE TABLE IF NOT EXISTS `tblpossalesdtl` (" + " `strPOSItemCode` varchar(20) NOT NULL," + "  `strPOSItemName` varchar(100) NOT NULL," + "  `dblQuantity` decimal(18,4) NOT NULL," + "  `dblRate` decimal(18,4) NOT NULL," + "  `strPOSCode` varchar(20) NOT NULL," + "  `dteBillDate` datetime NOT NULL," + "  `strClientCode` varchar(20) NOT NULL,"
				+ "  `strWSItemCode` varchar(20) NOT NULL DEFAULT ''," + "  `strSACode` varchar(30) NOT NULL DEFAULT ''" + ") ENGINE=InnoDB DEFAULT CHARSET=latin1;";
		funExecuteQuery(sql);

		sql = "CREATE TABLE IF NOT EXISTS `tblpotaxdtl` (" + " `strPOCode` varchar(15) NOT NULL," + " `intId` bigint(20) NOT NULL AUTO_INCREMENT," + " `strTaxCode` varchar(15) NOT NULL," + " `strTaxDesc` varchar(100) NOT NULL," + "  `strTaxableAmt` decimal(18,4) NOT NULL DEFAULT '0.0000'," + "  `strTaxAmt` decimal(18,4) NOT NULL DEFAULT '0.0000'," + "  `strClientCode` varchar(15) NOT NULL,"
				+ "  PRIMARY KEY (`intId`)) ENGINE=InnoDB DEFAULT CHARSET=latin1";
		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tbltallylinkup` ( " + "  `strGroupCode` varchar(50) NOT NULL, " + "  `strGDes` varchar(50) NOT NULL DEFAULT '',  " + " `strGroupName` varchar(255) NOT NULL DEFAULT '', " + "  `strTallyCode` varchar(50) NOT NULL,  " + " `strUserCreated` varchar(50) NOT NULL, " + "  `strUserEdited` varchar(50) NOT NULL, " + "  `dteCreatedDate` datetime NOT NULL,  "
				+ " `dteLastModified` datetime NOT NULL, " + "  `strClientCode` varchar(50) NOT NULL," + " PRIMARY KEY (`strGroupCode`)) ENGINE=InnoDB DEFAULT CHARSET=latin1; ";
		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tblimportdatadoc` ( `strOldDocCode` VARCHAR(50) NOT NULL DEFAULT ''," + " `strNewDocCode` VARCHAR(50) NOT NULL DEFAULT ''," + " `strTableName` VARCHAR(50) NOT NULL DEFAULT '', " + "	`strSubCode1` VARCHAR(50) NOT NULL DEFAULT ''," + " `strSubCode2` VARCHAR(50) NOT NULL DEFAULT ''," + " `strSubCode3` VARCHAR(50) NOT NULL DEFAULT '',"
				+ " `strTableType` VARCHAR(50) NOT NULL DEFAULT ''," + " `strClientCode` VARCHAR(50) NOT NULL DEFAULT '' )" + "  COLLATE='latin1_swedish_ci' ENGINE=InnoDB  ";
		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tblcountrymaster` ( 	`strCountryCode` VARCHAR(10) NOT NULL," + "	`strCountryName` VARCHAR(50) NOT NULL," + "	`strUserCreated` VARCHAR(20) NOT NULL," + "	`dtCreatedDate` DATETIME NOT NULL," + "	`strUserModified` VARCHAR(20) NOT NULL," + "	`dtLastModified` DATETIME NOT NULL," + "	`strClientCode` VARCHAR(20) NOT NULL,"
				+ "	`strPropertyCode` VARCHAR(20) NOT NULL DEFAULT ''," + "	`intGId` BIGINT(20) NOT NULL," + "	PRIMARY KEY (`strCountryCode`, `strClientCode`) )" + " COLLATE='latin1_swedish_ci'" + " ENGINE=InnoDB ; ";
		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tblstatemaster` (	`strStateCode` VARCHAR(10) NOT NULL," + "	`strStateName` VARCHAR(50) NOT NULL," + "	`strStateDesc` VARCHAR(50) NOT NULL DEFAULT ''," + "	`strCountryCode` VARCHAR(10) NOT NULL," + "	`strUserCreated` VARCHAR(20) NOT NULL," + "	`dtCreatedDate` DATETIME NOT NULL," + "	`strUserModified` VARCHAR(20) NOT NULL,"
				+ "	`dtLastModified` DATETIME NOT NULL," + "	`strClientCode` VARCHAR(20) NOT NULL," + "	`strPropertyCode` VARCHAR(20) NOT NULL DEFAULT ''," + "	`intGId` BIGINT(20) NOT NULL," + "	PRIMARY KEY (`strStateCode`, `strClientCode`) )" + " COLLATE='latin1_swedish_ci'" + " ENGINE=InnoDB ; ";
		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tblcitymaster` (	`strCityCode` VARCHAR(10) NOT NULL," + "	`strCityName` VARCHAR(50) NOT NULL," + "	`strCountryCode` VARCHAR(10) NOT NULL," + "	`strStateCode` VARCHAR(10) NOT NULL," + "	`strUserCreated` VARCHAR(20) NOT NULL," + "	`dtCreatedDate` DATETIME NOT NULL," + "	`strUserModified` VARCHAR(20) NOT NULL," + "	`dtLastModified` DATETIME NOT NULL,"
				+ "	`intGId` BIGINT(20) NOT NULL," + "	`strClientCode` VARCHAR(20) NOT NULL," + "	`strPropertyCode` VARCHAR(20) NOT NULL DEFAULT ''," + "	PRIMARY KEY (`strCityCode`, `strClientCode`) ) COLLATE='latin1_swedish_ci'" + " ENGINE=InnoDB ; ";
		funExecuteQuery(sql);

		sql = "  CREATE TABLE IF NOT EXISTS `tblvehiclemaster` ( " + "`strVehCode` VARCHAR(15) NOT NULL," + "	`strVehNo` VARCHAR(15) NOT NULL," + "	`strDesc` VARCHAR(50) NOT NULL DEFAULT ''," + "	`strUserCreated` VARCHAR(50) NOT NULL," + "	`dtCreatedDate` DATE NOT NULL," + "	`strUserModified` VARCHAR(50) NOT NULL," + "	`dtLastModified` DATE NOT NULL,	" + "`strClientCode` VARCHAR(15) NOT NULL,"
				+ " `intId` BIGINT(20) NOT NULL," + "	PRIMARY KEY (`strVehCode`, `strClientCode`) ) " + " COLLATE='latin1_swedish_ci' ENGINE=InnoDB ;  ";

		funExecuteQuery(sql);

		sql = "  CREATE TABLE IF NOT EXISTS `tblroutemaster` (" + "	`strRouteCode` VARCHAR(15) NOT NULL," + "	`strRouteName` VARCHAR(255) NOT NULL," + "	`strDesc` VARCHAR(255) NOT NULL DEFAULT ''," + "	`strUserCreated` VARCHAR(50) NOT NULL," + "	`strUserModified` VARCHAR(50) NOT NULL," + "	`dtCreatedDate` DATETIME NOT NULL," + " 	`dtLastModified` DATETIME NOT NULL, "
				+ "	`strClientCode` VARCHAR(15) NOT NULL," + " `intId` BIGINT(20) NOT NULL, " + "	PRIMARY KEY (`strRouteCode`, `strClientCode`) ) " + " COLLATE='latin1_swedish_ci' ENGINE=InnoDB ;  ";
		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tblvehroutedtl` ( " + " 	`intId` BIGINT NOT NULL AUTO_INCREMENT,	" + " `strRouteCode` VARCHAR(15) NOT NULL," + "	`strVehCode` VARCHAR(15) NOT NULL,	" + "`dtFromDate` DATETIME NOT NULL," + "	`dtToDate` DATETIME NOT NULL," + "	`strUserCreated` VARCHAR(50) NOT NULL," + "	`strUserModified` VARCHAR(50) NOT NULL," + "	`dtCreatedDate` DATETIME NOT NULL,"
				+ "	`dtLastModified` DATETIME NOT NULL," + "	`strClientCode` VARCHAR(15) NOT NULL," + "	PRIMARY KEY (`intId`, `strClientCode`) )" + " COLLATE='latin1_swedish_ci' ENGINE=InnoDB ;   ";

		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tbluserlocdtl` ( " + "	`strUserCode` VARCHAR(20) NOT NULL," + "	`strPropertyCode` VARCHAR(10) NOT NULL," + "	`strLocCode` VARCHAR(20) NOT NULL," + "	`strClientCode` VARCHAR(20) NOT NULL " + " ) COLLATE='latin1_swedish_ci' " + " ENGINE=InnoDB; ";

		funExecuteQuery(sql);

		sql = " CREATE TABLE `tbltransportermaster` ( 	`strTransCode` VARCHAR(15) NOT NULL, " + "	`strTransName` VARCHAR(50) NOT NULL, " + "	`strDesc` VARCHAR(50) NOT NULL DEFAULT ''," + " `strUserCreated` VARCHAR(50) NOT NULL, " + "	`dteCreatedDate` DATE NOT NULL," + "	`strUserModified` VARCHAR(50) NOT NULL," + "	`dteLastModified` VARCHAR(50) NOT NULL," + "	`strClientCode` VARCHAR(50) NOT NULL,"
				+ "	`intId` BIGINT(20) NOT NULL DEFAULT '0'," + "	PRIMARY KEY (`strTransCode`, `strClientCode`) ) " + " COLLATE='latin1_swedish_ci' ENGINE=InnoDB; ";
		funExecuteQuery(sql);

		sql = " CREATE TABLE `tbltransportermasterdtl` ( 	`strTransCode` VARCHAR(15) NOT NULL, " + " `intId` BIGINT(20) NOT NULL AUTO_INCREMENT, " + "	`strVehCode` VARCHAR(15) NOT NULL, " + "	`strVehNo` VARCHAR(20) NOT NULL DEFAULT '', " + "	`strClientCode` VARCHAR(15) NOT NULL," + "	PRIMARY KEY (`intId`) ) " + " COLLATE='latin1_swedish_ci' " + " ENGINE=InnoDB AUTO_INCREMENT=13;  ";
		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tbllinkup` ( 	`strSGCode` VARCHAR(50) NOT NULL, " + "	`strGDes` VARCHAR(50) NOT NULL DEFAULT '', " + "	`strSGName` VARCHAR(255) NOT NULL DEFAULT '', " + "	`strAccountCode` VARCHAR(50) NOT NULL, " + "	`strUserCreated` VARCHAR(50) NOT NULL, " + "	`strUserEdited` VARCHAR(50) NOT NULL, 	" + " `dteCreatedDate` DATETIME NOT NULL, "
				+ "	`dteLastModified` DATETIME NOT NULL, " + "	`strClientCode` VARCHAR(50) NOT NULL, " + "	`strExSuppCode` VARCHAR(50) NOT NULL DEFAULT '', " + "	`strExSuppName` VARCHAR(255) NOT NULL DEFAULT '', " + "	PRIMARY KEY (`strSGCode`) ) " + " COLLATE='latin1_swedish_ci' ENGINE=InnoDB ; ";

		funExecuteQuery(sql);

		sql = " CREATE TABLE `tblproductstandard` (  	`id` BIGINT(10) NOT NULL AUTO_INCREMENT,  " + "	 `strProdCode` VARCHAR(10) NOT NULL,  " + "	`dblQty` DECIMAL(18,2) NOT NULL DEFAULT '0.00',  " + "	`dblUnitPrice` DECIMAL(18,2) NOT NULL DEFAULT '0.00', " + " 	`dblTotalPrice` DECIMAL(18,2) NOT NULL DEFAULT '0.00',  " + "	`strRemarks` VARCHAR(250) NOT NULL DEFAULT '',  "
				+ "	`strClientCode` VARCHAR(10) NOT NULL DEFAULT '',  " + "	`strStandardType` VARCHAR(20) NOT NULL, " + " 	PRIMARY KEY (`id`, `strClientCode`) " + " ) COLLATE='latin1_swedish_ci' " + " ENGINE=InnoDB AUTO_INCREMENT=16 ; ";

		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tblcurrencymaster` (  " + "	`strCurrencyCode` VARCHAR(30) NOT NULL, " + "	`intID` INT(11) NOT NULL DEFAULT '0', " + "	`strCurrencyName` VARCHAR(30) NOT NULL DEFAULT '', " + "	`strShortName` VARCHAR(30) NOT NULL DEFAULT '', " + "	`strBankName` VARCHAR(30) NOT NULL DEFAULT '', " + "	`strSwiftCode` VARCHAR(30) NOT NULL DEFAULT '', "
				+ "	`strIbanNo` VARCHAR(30) NOT NULL DEFAULT '', " + "	`strRouting` VARCHAR(30) NOT NULL DEFAULT '', " + "	`strUserCreated` VARCHAR(10) NOT NULL, " + "	`dtCreatedDate` DATETIME NOT NULL, " + "	`strUserModified` VARCHAR(10) NOT NULL, " + "	`dtLastModified` DATETIME NOT NULL, " + "	`strAccountNo` VARCHAR(30) NOT NULL DEFAULT '', "
				+ "	`dblConvToBaseCurr` DECIMAL(18,2) NOT NULL DEFAULT '1.00', " + "	`strSubUnit` VARCHAR(30) NOT NULL DEFAULT '', " + "	`strClientCode` VARCHAR(30) NOT NULL, " + "	PRIMARY KEY (`strCurrencyCode`, `strClientCode`) ) " + " COLLATE='latin1_swedish_ci' ENGINE=InnoDB ; ";

		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS  `tbltransactiontime` (  " + "	`strLocCode` VARCHAR(50) NOT NULL,  " + "	`strPropertyCode` VARCHAR(50) NOT NULL,  " + "	`strClientCode` VARCHAR(50) NOT NULL, " + "	`tmeFrom` TIME NOT NULL,  	`tmeTo` TIME NOT NULL,  " + "	`strTransactionName` VARCHAR(50) NOT NULL DEFAULT '', " + " 	PRIMARY KEY (`strLocCode`, `strPropertyCode`, `strClientCode`)  ) "
				+ " COLLATE='latin1_swedish_ci' ENGINE=InnoDB ;    ";
		funExecuteQuery(sql);

		sql = "  CREATE TABLE IF NOT EXISTS  `tbldeliveryschedulehd` (  " + "	`strDSCode` VARCHAR(50) NOT NULL, " + " 	`strPOCode` VARCHAR(50) NOT NULL, " + "	`strAgainst` VARCHAR(50) NOT NULL, " + "	`dteDSDate` DATE NOT NULL, " + "	`dteScheduleDate` DATE NOT NULL, " + "	`dblTotalAmount` DOUBLE NOT NULL, " + "	`strUserCreated` VARCHAR(50) NOT NULL, " + "	`strUserEdited` VARCHAR(50) NOT NULL, "
				+ "	`strLocCode` VARCHAR(50) NOT NULL, " + " 	`dteDateCreated` DATE NOT NULL, " + "	`dteDateEdited` DATE NOT NULL, " + "	`strClientCode` VARCHAR(50) NOT NULL, " + "	`strNarration` VARCHAR(300) NOT NULL, " + "	`strCloseDS` VARCHAR(1) NOT NULL DEFAULT 'N', " + "	PRIMARY KEY (`strDSCode`, `strClientCode`) " + " ) COLLATE='latin1_swedish_ci' ENGINE=InnoDB ; ";

		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS  `tbldeliveryscheduledtl` ( " + "	`strDSCode` VARCHAR(50) NOT NULL, " + "	`strProdCode` VARCHAR(50) NOT NULL, " + "	`dblQty` DOUBLE NOT NULL DEFAULT '0', " + "	`strUOM` VARCHAR(50) NOT NULL, " + "	`dblUnitPrice` DOUBLE NOT NULL, " + "	`dblTotalPrice` DOUBLE NOT NULL, " + "	`strClientCode` VARCHAR(50) NOT NULL, " + "	`dblWeight` DOUBLE NOT NULL "
				+ " ) COLLATE='latin1_swedish_ci' ENGINE=InnoDB ; ";

		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tbllinklocotherproploc` ( " + " `strPropertyCode` VARCHAR(10) NOT NULL, " + " `strLocCode` VARCHAR(10) NOT NULL, " + " `strToLoc` VARCHAR(10) NOT NULL, " + " `strUserCreated` VARCHAR(10) NULL DEFAULT NULL, " + " `dtCreatedDate` DATETIME NOT NULL, " + " `strUserModified` VARCHAR(10) NULL DEFAULT NULL, " + " `dtLastModified` DATETIME NULL DEFAULT NULL, "
				+ " `strClientCode` VARCHAR(10) NOT NULL, " + " PRIMARY KEY (`strPropertyCode`, `strLocCode`, `strToLoc`) " + " ) COLLATE='latin1_swedish_ci' ENGINE=InnoDB ;";

		funExecuteQuery(sql);

		sql = "CREATE TABLE IF NOT EXISTS `tblsessionmaster` ( " + " `strSessionCode` VARCHAR(10) NOT NULL, " + " `intSId` BIGINT(20) NOT NULL DEFAULT '0', " + " `strSessionName` VARCHAR(30) NOT NULL DEFAULT '', " + " `strSDesc` VARCHAR(50) NOT NULL DEFAULT '', " + " `strUserCreated` VARCHAR(50) NOT NULL DEFAULT '', " + " `dtCreatedDate` DATETIME NOT NULL, "
				+ " `strUserModified` VARCHAR(50) NOT NULL DEFAULT '', " + " `dtLastModified` DATETIME NOT NULL, " + " `strClientCode` VARCHAR(10) NOT NULL " + " ) " + " COLLATE='latin1_swedish_ci' " + " ENGINE=InnoDB ; ";

		funExecuteQuery(sql);

		sql = "CREATE TABLE IF NOT EXISTS  `tblbudgetmasterhd` ( " + " `strPropertyCode` VARCHAR(10) NOT NULL, " + " `strFinYear` VARCHAR(15) NOT NULL, " + " `strStartMonth` VARCHAR(20) NOT NULL, " + " `strBudgetCode` VARCHAR(10) NOT NULL, " + "  `strClientCode` VARCHAR(50) NOT NULL, " + " `strUserCreated` VARCHAR(50) NOT NULL, " + " `strUserEdited` VARCHAR(50) NOT NULL, "
				+ " `dteDateCreated` DATE NOT NULL, " + " `dteDateEdited` DATE NOT NULL, " + " `intBId` BIGINT(20) NOT NULL DEFAULT '0', " + " PRIMARY KEY (`strBudgetCode`, `strClientCode`) " + " ) " + " COLLATE='latin1_swedish_ci' " + " ENGINE=InnoDB;";

		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tblbudgetmasterdtl` ( " + " `strBudgetCode` VARCHAR(10) NOT NULL, " + " `strGroupCode` VARCHAR(50) NOT NULL, " + " `strMonth1` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', " + " `strMonth2` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', " + " `strMonth3` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', " + " `strMonth4` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', "
				+ " `strMonth5` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', " + " `strMonth6` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', " + " `strMonth7` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', " + " `strMonth8` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', " + " `strMonth9` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', " + " `strMonth10` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', "
				+ " `strMonth11` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', " + " `strMonth12` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', " + " `strClientCode` VARCHAR(50) NOT NULL " + " ) " + " COLLATE='latin1_swedish_ci' " + " ENGINE=InnoDB ;";
		funExecuteQuery(sql);



		// Indexing in table

		sql = "ALTER TABLE `tblpossalesdtl`	ADD COLUMN `intId` BIGINT NOT NULL AUTO_INCREMENT AFTER `strWSItemCode`,DROP PRIMARY KEY,ADD PRIMARY KEY (`intId`, `strClientCode`);";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblmisdtl` " + " ADD INDEX `strMISCode_strProdCode_strClientCode` (`strMISCode`, `strProdCode`, `strClientCode`);";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblreqdtl` " + " ADD INDEX `strReqCode_strProdCode_strClientCode` (`strReqCode`, `strProdCode`, `strClientCode`);";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblstocktransferhd` " + " ADD COLUMN `dblTotalAmt` DECIMAL(18,4) NOT NULL DEFAULT '0.0000' AFTER `intLevel` ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblstocktransferdtl` " + " ADD COLUMN `dblTotalPrice` DECIMAL(18,4) NOT NULL DEFAULT '0.0000' AFTER `strClientCode` ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblpurchaseindenddtl` " + " CHANGE COLUMN `dblQty` `dblQty` DECIMAL(18,4) NULL DEFAULT NULL AFTER `strProdCode`;";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblproductionorderhd` HANGE COLUMN `strAuthorise` `strAuthorise` VARCHAR(3) NULL DEFAULT NULL AFTER `strBOMFlag`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblbommasterhd`	ADD COLUMN `strBOMType` VARCHAR(1) NOT NULL DEFAULT 'R' AFTER `strMethod`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblproductmaster` ADD COLUMN `dblYieldPer` DECIMAL(10,0) NOT NULL DEFAULT '100.00' AFTER `strNonStockableItem`;  ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblproductmaster`  ADD COLUMN `strBarCode` VARCHAR(50) NOT NULL DEFAULT 'NA' AFTER `dblYieldPer`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblproductionorderhd` CHANGE COLUMN `strAuthorise` `strAuthorise` VARCHAR(3) NULL DEFAULT NULL AFTER `strBOMFlag`;";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblpartymaster` ADD COLUMN `strPartyIndi` VARCHAR(1) NOT NULL DEFAULT '' AFTER `strClientCode` ; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblrateconthd`	CHANGE COLUMN `strAuthorise` `strAuthorise` VARCHAR(3) NOT NULL DEFAULT 'No' AFTER `dtLastModified`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblproductmaster` CHANGE COLUMN `strDesc` `strDesc` VARCHAR(1000) NOT NULL DEFAULT '' AFTER `strSaleNo`, " + "	CHANGE COLUMN `strSpecification` `strSpecification` VARCHAR(1000) NOT NULL DEFAULT '' AFTER `strType`;  ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblworkorderdtl` DROP PRIMARY KEY; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblgrnhd`	CHANGE COLUMN `strNarration` `strNarration` VARCHAR(1000) NOT NULL DEFAULT '' AFTER `dblTotal`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblgrnhd` CHANGE COLUMN `strBillNo` `strBillNo` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strPONo`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblprodchar` CHANGE COLUMN `dblValue` `strTollerance` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strCharCode` ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblprodchar` ADD COLUMN `intId` BIGINT NOT NULL AUTO_INCREMENT AFTER `strProdCode`, DROP PRIMARY KEY,	ADD PRIMARY KEY (`intId`, `strClientCode`); ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblcompanymaster`	DROP PRIMARY KEY, DROP INDEX `intId`,	ADD PRIMARY KEY (`intId`); ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblpropertysetup`	ADD COLUMN `strShowAllProdToAllLoc` CHAR(1) NULL DEFAULT 'Y' AFTER `strMonthEnd` ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblattachdocument` ADD COLUMN `strModuleName` VARCHAR(50) NOT NULL AFTER `strClientCode`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblstocktransferhd` CHANGE COLUMN `strWOCode` `strWOCode` VARCHAR(500) NOT NULL DEFAULT '' AFTER `strMaterialIssue`; ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblpropertysetup`CHANGE COLUMN `strRangeAdd` `strRangeAdd` VARCHAR(255) NULL DEFAULT NULL AFTER `strMask`;";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblprodsuppmaster`	ADD COLUMN `dblMargin` DECIMAL(18,2) NOT NULL DEFAULT '0' AFTER `strUOM`;  ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tbltaxhd` ADD COLUMN `strTaxOnTaxCode` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strClientCode`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblpropertysetup`	ADD COLUMN `strLocWiseProductionOrder` CHAR(1) NULL DEFAULT 'Y' AFTER `strShowAllProdToAllLoc`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblproductmaster` ADD COLUMN `dblMRP` DECIMAL(18,2) NOT NULL DEFAULT '0.00' AFTER `strBarCode`;  ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblpropertysetup` " + " ADD COLUMN `strShowStockInOP` CHAR(1) NULL DEFAULT 'Y' AFTER `strLocWiseProductionOrder`, " + " ADD COLUMN `strShowAvgQtyInOP` CHAR(1) NULL DEFAULT 'Y' AFTER `strShowStockInOP`, " + " ADD COLUMN `strShowStockInSO` CHAR(1) NULL DEFAULT 'Y' AFTER `strShowAvgQtyInOP`, "
				+ " ADD COLUMN `strShowAvgQtyInSO` CHAR(1) NULL DEFAULT 'Y' AFTER `strShowStockInSO`, " + " ADD COLUMN `strDivisionAdd` VARCHAR(255) NOT NULL DEFAULT '' AFTER `strShowAvgQtyInSO`; ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblsubgroupmaster` ADD COLUMN `strExciseable` VARCHAR(1) NOT NULL DEFAULT 'N' AFTER `strClientCode`; ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tbltaxhd` ADD COLUMN `strCalTaxOnMRP` VARCHAR(1) NOT NULL DEFAULT 'N' AFTER `strTaxOnSubGroup`;";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblproductmaster` ADD COLUMN `strPickMRPForTaxCal` VARCHAR(50) NOT NULL DEFAULT 'N' AFTER `dblMRP`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblsubgroupmaster`ADD COLUMN `strExciseChapter` VARCHAR(20) NOT NULL DEFAULT '' AFTER `strExciseable` ; ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblpropertysetup`	ADD COLUMN `strEffectOfDiscOnPO` VARCHAR(1) NULL DEFAULT 'Y' AFTER `strDivisionAdd`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblpropertysetup`	ADD COLUMN `strInvFormat` VARCHAR(30) NOT NULL AFTER `strEffectOfDiscOnPO`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblproductmaster` ADD COLUMN `strExciseable` VARCHAR(1) NOT NULL DEFAULT 'N' AFTER `strPickMRPForTaxCal`; ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tbltaxhd` ADD COLUMN `dblAbatement` DECIMAL(18,2) NOT NULL DEFAULT '0' AFTER `strCalTaxOnMRP`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tbltaxhd` ADD COLUMN `strTOTOnMRPItems` VARCHAR(1) NOT NULL DEFAULT 'N' AFTER `dblAbatement`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblpropertysetup`	ADD COLUMN `strECCNo` VARCHAR(150) NOT NULL DEFAULT '' AFTER `strInvFormat`;";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblvehiclemaster` ADD COLUMN `intId` BIGINT NOT NULL AFTER `strClientCode`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tbluserdtl` 	ADD COLUMN `strModule` VARCHAR(10) NULL DEFAULT NULL AFTER `strClientCode`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tbluserdefinedreport`  ALTER `strTable` DROP DEFAULT; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tbluserdefinedreport` CHANGE COLUMN `strTable` `strTable` VARCHAR(500) NOT NULL AFTER `strType`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tbluserdefinedreport` ALTER `strGroupBy` DROP DEFAULT, ALTER `strSortBy` DROP DEFAULT; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tbluserdefinedreport` " + "	CHANGE COLUMN `strGroupBy` `strGroupBy` VARCHAR(500) NOT NULL AFTER `strFieldSize`, " + "	CHANGE COLUMN `strSortBy` `strSortBy` VARCHAR(500) NOT NULL AFTER `strGroupBy`;  ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblpartymaster` 	ADD COLUMN `strECCNo` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strOperational`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblsubgroupmaster` ADD COLUMN `intSortingNo` INT(10) NOT NULL AFTER `strExciseChapter`; ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblsubgroupmaster`  ADD COLUMN `strSGDescHeader` VARCHAR(100) NOT NULL DEFAULT '' AFTER `intSortingNo`;";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblexcelimport` 	ADD COLUMN `strBarCode` VARCHAR(50) NOT NULL DEFAULT '' AFTER `dblPriceChangePercentage`; ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblexcelimport` 	CHANGE COLUMN `strBankAdd1` `strBankAdd1` VARCHAR(100) NOT NULL DEFAULT '' AFTER `strBankName`, " + " CHANGE COLUMN `strBankAdd2` `strBankAdd2` VARCHAR(100) NOT NULL DEFAULT '' AFTER `strBankAdd1`,  " + "	CHANGE COLUMN `strMAdd1` `strMAdd1` VARCHAR(100) NOT NULL DEFAULT '' AFTER `strAcCrCode`, "
				+ "	CHANGE COLUMN `strMAdd2` `strMAdd2` VARCHAR(100) NOT NULL DEFAULT '' AFTER `strMAdd1`, " + "	CHANGE COLUMN `strBAdd1` `strBAdd1` VARCHAR(100) NOT NULL DEFAULT '' AFTER `strMCountry`, " + "	CHANGE COLUMN `strBAdd2` `strBAdd2` VARCHAR(100) NOT NULL DEFAULT '' AFTER `strBAdd1`, " + "	CHANGE COLUMN `strSAdd1` `strSAdd1` VARCHAR(100) NOT NULL DEFAULT '' AFTER `strBCountry`, "
				+ "	CHANGE COLUMN `strSAdd2` `strSAdd2` VARCHAR(100) NOT NULL DEFAULT '' AFTER `strSAdd1`; ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblpartymaster` " + "	ADD COLUMN `dtInstallions` DATE NOT NULL DEFAULT '1900-01-01' AFTER `strECCNo`, " + "	ADD COLUMN `strAccManager` VARCHAR(100) NOT NULL DEFAULT '' AFTER `dtInstallions`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblpartymaster`  " + " ADD COLUMN `strDebtorCode` VARCHAR(20) NOT NULL DEFAULT '' AFTER `strAccManager`; ";
		funExecuteQuery(sql);

		// sql =" ALTER TABLE `tblarlinkup`"
		// +
		// "	ADD COLUMN `strExSuppCode` VARCHAR(50) NOT NULL DEFAULT ''  AFTER `strClientCode`, "
		// +
		// "	ADD COLUMN `strExSuppName` VARCHAR(255) NOT NULL DEFAULT ''  AFTER `strExSuppCode`; ";
		//
		// funExecuteQuery(sql);

		sql = " ALTER TABLE `tblstockpostingdtl`" + "	CHANGE COLUMN `strDisplyVariance` `strDisplyVariance` VARCHAR(100) NOT NULL DEFAULT '' AFTER `strDisplyQty`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblstockadjustmentdtl` " + "	CHANGE COLUMN `dblQty` `dblQty` DECIMAL(18,4) NOT NULL DEFAULT '0.00' AFTER `strProdCode`,  " + "	CHANGE COLUMN `dblPrice` `dblPrice` DECIMAL(18,4) NOT NULL DEFAULT '0.00' AFTER `strType`, " + "	CHANGE COLUMN `dblWeight` `dblWeight` DECIMAL(18,4) NOT NULL DEFAULT '0.00' AFTER `dblPrice`; ";

		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblstockadjustmentdtl`" + "	ADD COLUMN `strWSLinkedProdCode` VARCHAR(20) NOT NULL DEFAULT '' AFTER `strDisplayQty`; ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tbluserlocdtl` " + "	ADD COLUMN `strModule` VARCHAR(20) NOT NULL DEFAULT '' AFTER `strClientCode`;";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblcompanymaster` " + "	ADD COLUMN `strWebBookAPGLModule` VARCHAR(3) NULL DEFAULT 'No' AFTER `strPassword`; ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblpropertysetup` " + "	ADD COLUMN `strSMSProvider` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strECCNo`, " + " ADD COLUMN `strSMSAPI` VARCHAR(300) NOT NULL DEFAULT '' AFTER `strSMSProvider`,  " + "	ADD COLUMN `strSMSContent` VARCHAR(500) NOT NULL DEFAULT '' AFTER `strSMSAPI`;";
		funExecuteQuery(sql);

		sql = "  ALTER TABLE `tblpurchaseindendhd` " + "	ADD COLUMN `strAgainst` VARCHAR(20) NOT NULL DEFAULT '' AFTER `strClosePI`, " + "	ADD COLUMN `strDocCode` VARCHAR(20) NOT NULL DEFAULT '' AFTER `strAgainst`; ";
		funExecuteQuery(sql);

		sql = " DROP FUNCTION IF EXISTS `funGetUOM`; ";
		funExecuteQuery(sql);

		sql = " CREATE DEFINER=`root`@`localhost` FUNCTION `funGetUOM`(  	`transQty` varchar(20),  	`recipeConv` double,  	`issueConv` double,  	`receivedUOM` varchar(10),  	`recipeUOM` varchar(10)  )  " + " RETURNS varchar(30) CHARSET latin1 " + "LANGUAGE SQL " + "DETERMINISTIC " + "CONTAINS SQL " + "SQL SECURITY DEFINER " + "COMMENT '' " + "BEGIN " + "  DECLARE uomString varchar(30); "
				+ " set uomString=concat(if(Left(transQty,INSTR(transQty,'.')-1), concat(Left(transQty,INSTR(transQty,'.')-1) " + "        ,concat(' ',concat(receivedUOM,'.'))),'') " + "        ,if(MID(transQty,INSTR(transQty,'.'),LENGTH(transQty)) " + "        , concat((MID(transQty,INSTR(transQty,'.'),LENGTH(transQty)))*(recipeConv * issueConv), concat(' ',recipeUOM)),'')); "
				+ " RETURN (uomString); " + " END;  ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblpartymaster` " + "	ADD COLUMN `strGSTNo` VARCHAR(20) NOT NULL DEFAULT '' AFTER `strDebtorCode`;    ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tbltaxhd` " + "	ADD COLUMN `strShortName` VARCHAR(20) NOT NULL DEFAULT '' AFTER `strTOTOnMRPItems`, " + "	ADD COLUMN `strGSTNo` VARCHAR(20) NOT NULL DEFAULT '' AFTER `strShortName`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblproductmaster` " + "	ADD COLUMN `strProdNameMarathi` VARCHAR(200) NOT NULL DEFAULT '' " + " AFTER `strExciseable`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblproductmaster` " + "	ADD COLUMN `strManufacturerCode` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strProdNameMarathi`; ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tbluserhd` " + " ADD COLUMN `strShowDashBoard` VARCHAR(1) NOT NULL DEFAULT 'N' AFTER `strClientCode`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tbltaxhd` CHANGE COLUMN `strGSTNo` " + " `strGSTNo` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strShortName`;";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblpropertysetup`  " + " CHANGE COLUMN `strInvFormat` `strInvFormat` VARCHAR(150) NOT NULL AFTER `strEffectOfDiscOnPO`;  ";

		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblpropertysetup` " + "	ADD COLUMN `strInvNote` VARCHAR(500) NOT NULL DEFAULT '' AFTER `strSMSContent`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblpropertysetup` " + "	ADD COLUMN `strCurrencyCode` VARCHAR(15) NOT NULL DEFAULT '' AFTER `strInvNote`;  ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblpropertysetup` " + "	ADD COLUMN `strShowAllPropCustomer` VARCHAR(1) NOT NULL DEFAULT 'Y' AFTER `strCurrencyCode`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblpartymaster` " + "	ADD COLUMN `strPNHindi` VARCHAR(200) NOT NULL DEFAULT ''  AFTER `strGSTNo`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblpartymaster` " + "	ADD COLUMN `strLocCode` VARCHAR(15) NOT NULL DEFAULT '' AFTER `strPNHindi`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblpartymaster` " + "	ADD COLUMN `strPropCode` VARCHAR(10) NOT NULL DEFAULT '01' AFTER `strLocCode`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblreorderlevel` " + "	ADD COLUMN `dblPrice` DOUBLE NOT NULL DEFAULT '0.0' AFTER `strClientCode`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblinvoicehd` " + "	ADD COLUMN `strCurrencyCode` VARCHAR(15) NOT NULL DEFAULT '' AFTER `strSettlementCode`, " + "	ADD COLUMN `dblCurrencyConv` DOUBLE NOT NULL DEFAULT '1.0' AFTER `strCurrencyCode`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblpropertysetup` " + "	ADD COLUMN `strEffectOfInvoice` VARCHAR(20) NOT NULL DEFAULT 'DC' AFTER `strShowAllPropCustomer`;  ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblbommasterdtl` " + "	CHANGE COLUMN `dblQty` `dblQty` DECIMAL(18,4) NOT NULL DEFAULT '0.00' AFTER `strChildCode`,  " + "	CHANGE COLUMN `dblWeight` `dblWeight` DECIMAL(18,4) NOT NULL DEFAULT '0.00' AFTER `dblQty`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblreqhd`  " + "	ADD COLUMN `strReqFrom` VARCHAR(10) NOT NULL DEFAULT 'System' AFTER `dtReqiredDate`;  ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblstockpostinghd` " + "	ADD COLUMN `strPhyStkFrom` VARCHAR(10) NOT NULL DEFAULT 'System' AFTER `strConversionUOM`;  ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblstockpostingdtl` " + "	ADD COLUMN `dblActualRate` DECIMAL(10,2) NOT NULL DEFAULT '0.00' AFTER `strDisplyVariance`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblstockpostingdtl` " + "	ADD COLUMN `dblActualValue` DECIMAL(10,2) NOT NULL DEFAULT '0.00' AFTER `dblActualRate`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tbllinkup` " + "	ADD COLUMN `strPropertyCode` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strExSuppName`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblpropertysetup` " + "	ADD COLUMN `strEffectOfGRNWebBook` VARCHAR(20) NOT NULL DEFAULT 'Payment' AFTER `strEffectOfInvoice`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tbllinkup` 	DROP PRIMARY KEY,  " + "	ADD PRIMARY KEY (`strSGCode`, `strPropertyCode`, `strClientCode`);  ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblproductstandard` " + "	ADD COLUMN `strLocCode` VARCHAR(20) NOT NULL AFTER `strStandardType`, " + "	ADD COLUMN `strPropertyCode` VARCHAR(20) NOT NULL AFTER `strLocCode`, " + "	DROP COLUMN `strLocCode`, " + "	DROP COLUMN `strPropertyCode` ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblproductstandard` " + "	DROP PRIMARY KEY, " + "	ADD PRIMARY KEY (`strProdCode`, `strLocCode`, `strPropertyCode`, `strClientCode`); ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblgrnhd` " + "	ADD COLUMN `dblRoundOff` DOUBLE(18,4) NULL DEFAULT '0' AFTER `intLevel`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblproductstandard`  " + "	CHANGE COLUMN `id` `id` BIGINT(10) NOT NULL DEFAULT '0' FIRST, " + "	ADD COLUMN `strLocCode` VARCHAR(20) NOT NULL AFTER `strStandardType`, " + "	ADD COLUMN `strPropertyCode` VARCHAR(20) NOT NULL AFTER `strLocCode`, " + "	DROP PRIMARY KEY, " + "	ADD PRIMARY KEY (`strProdCode`, `strClientCode`, `strLocCode`, `strPropertyCode`);  ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblproductmaster` " + "	CHANGE COLUMN `strNonStockableItem` `strNonStockableItem` VARCHAR(3) NULL DEFAULT 'N' AFTER `strProductImage` ";
		funExecuteQuery(sql);

		sql = " update tblproductmaster  set strNonStockableItem='Y' where strNonStockableItem='Yes' ";
		funExecuteQuery(sql);

		sql = " update tblproductmaster  set strNonStockableItem='Y' where strNonStockableItem='YES' ";
		funExecuteQuery(sql);

		sql = " update tblproductmaster  set strNonStockableItem='N' where strNonStockableItem='No' ";
		funExecuteQuery(sql);

		sql = " update tblproductmaster  set strNonStockableItem='N' where strNonStockableItem='NO' ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tbllinkup`ADD COLUMN `strSGType` VARCHAR(50) NOT NULL DEFAULT '' " + "AFTER `strPropertyCode`;";

		funExecuteQuery(sql);

		sql = "ALTER TABLE `tbllinkup`DROP PRIMARY KEY, " + "ADD PRIMARY KEY (`strSGCode`, `strPropertyCode`, `strClientCode`, `strSGType`);";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblpropertysetup` " + "	ADD COLUMN `strMultiCurrency` VARCHAR(1) NOT NULL DEFAULT 'N' AFTER `strEffectOfGRNWebBook` ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblproductmaster` " + "	CHANGE COLUMN `strProdName` `strProdName` VARCHAR(300) NOT NULL AFTER `strPartNo` ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblexcelimport` " + "	CHANGE COLUMN `strProdName` `strProdName` VARCHAR(300) NOT NULL DEFAULT '' FIRST ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblexcelimport` ADD COLUMN `strManufacturerCode` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strBarCode`, " + " ADD COLUMN `strManufacturerName` VARCHAR(100) NOT NULL DEFAULT '' AFTER `strManufacturerCode`; ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblpartymaster` " + " CHANGE COLUMN `strPName` `strPName` VARCHAR(100) NOT NULL DEFAULT '' AFTER `intPId`; ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblreqhd` ADD COLUMN `strSessionCode` VARCHAR(10) NOT NULL DEFAULT ''  AFTER `strReqFrom` ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tbltransportermasterdtl` DROP COLUMN `intId`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tbltaxhd` ADD COLUMN `strExternalCode` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strGSTNo`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblpartymaster` ADD COLUMN `strExternalCode` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strPropCode`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tbllocationmaster` ADD COLUMN `strUnderLocCode` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strLocPropertyCode`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblpropertysetup` " + " ADD COLUMN `strShowAllPartyToAllLoc` CHAR(1) NULL DEFAULT 'Y' AFTER `strMultiCurrency`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblreasonmaster` ADD COLUMN `dtExpiryDate` DATETIME NOT NULL AFTER `strClientCode`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tbllinkup` ADD COLUMN `strModuleType` VARCHAR(10) NOT NULL AFTER `strOperationType`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblpurchaseorderhd` " + "ADD COLUMN `dblClearingAgentCharges` DECIMAL(18,4) NOT NULL DEFAULT '0.0000' AFTER `dblCIF`, " + "ADD COLUMN `dblVATClaim` DECIMAL(18,4) NOT NULL DEFAULT '0.0000' AFTER `dblClearingAgentCharges`";
		funExecuteQuery(sql);
		
		sql = " ALTER TABLE `tblpropertysetup`	ADD COLUMN `strRateHistoryFormat` VARCHAR(30) NOT NULL AFTER `strSOKOTPrint`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblpropertysetup`	ADD COLUMN `strPOSlipFormat` VARCHAR(30) NOT NULL AFTER `strRateHistoryFormat`; ";
		funExecuteQuery(sql);
		
		sql = "ALTER TABLE `tblpropertysetup` ADD COLUMN `strSRSlipFormat` VARCHAR(30) NOT NULL AFTER `strPOSlipFormat`;";
		funExecuteQuery(sql);
		
		sql = " select strMasterCode from tbllinkup ";
		int i=funExecute(sql);
		
		if(i==1)
		{
			sql = "DROP TABLE IF EXISTS `tbllinkup`; ";
			funExecuteQuery(sql);
			
			sql="CREATE TABLE `tbllinkup` ( "
				+" `strMasterCode` VARCHAR(50) NOT NULL, "
				+" `strMasterDesc` VARCHAR(50) NOT NULL DEFAULT '', "
				+" `strMasterName` VARCHAR(255) NOT NULL DEFAULT '', "
				+" `strAccountCode` VARCHAR(50) NOT NULL, "
				+" `strUserCreated` VARCHAR(50) NOT NULL, "
				+" `strUserEdited` VARCHAR(50) NOT NULL, "
				+" `dteCreatedDate` DATETIME NOT NULL, "
				+" `dteLastModified` DATETIME NOT NULL, "
				+" `strClientCode` VARCHAR(50) NOT NULL, "
				+" `strExSuppCode` VARCHAR(50) NOT NULL DEFAULT '', "
				+" `strExSuppName` VARCHAR(255) NOT NULL DEFAULT '', "
				+" `strPropertyCode` VARCHAR(10) NOT NULL DEFAULT '', "
				+" `strSGType` VARCHAR(50) NOT NULL DEFAULT '', "
				+" `strOperationType` VARCHAR(10) NOT NULL, "
				+" `strModuleType` VARCHAR(10) NOT NULL, "
				+" PRIMARY KEY (`strMasterCode`, `strClientCode`, `strPropertyCode`, `strOperationType`, `strModuleType`) "
				+" ) "
				+" COLLATE='latin1_swedish_ci' "
				+" ENGINE=InnoDB ;" ;
			funExecuteQuery(sql);
		}

		
		sql = " ALTER TABLE `tblproductmaster` " + " ADD COLUMN `strComesaItem` VARCHAR(1) NOT NULL DEFAULT 'N' AFTER `strManufacturerCode`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tbltaxhd` " + " ADD COLUMN `strNotApplicableForComesa` VARCHAR(1) NOT NULL DEFAULT 'N' AFTER `strPartyIndicator`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblpartymaster` " + " ADD COLUMN `strComesaRegion` VARCHAR(1) NOT NULL DEFAULT 'N' AFTER `dblRejectionPercentage`;";
		funExecuteQuery(sql);
		
		sql="ALTER TABLE `tblpartymaster` ADD COLUMN `dblReturnDiscount` DECIMAL(18,2) NOT NULL DEFAULT '0.00' AFTER `strCurrency`; ";
		funExecuteQuery(sql);
		
		sql="ALTER TABLE `tbllinkup` "
			+" CHANGE COLUMN `strGDes` `strGDes` VARCHAR(100) NOT NULL DEFAULT '' AFTER `strSGCode`;" ;
		funExecuteQuery(sql);

		sql = "update tblproductmaster a  set a.dblReceiveConversion='1.00' where a.dblReceiveConversion='0.00' ;";
		funExecuteQuery(sql);

		sql = "update tblproductmaster a  set a.dblIssueConversion='1.00' where a.dblIssueConversion='0.00' ; ";
		funExecuteQuery(sql);

		sql = "update tblproductmaster a  set a.dblRecipeConversion='1.00' where a.dblRecipeConversion='0.00' ; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblcurrentstock` " + " CHANGE COLUMN `strProdName` `strProdName` VARCHAR(300) NOT NULL AFTER `strProdCode`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblpurchaseorderhd`" + " ADD COLUMN `dblFOB` DECIMAL(18,4) NOT NULL DEFAULT '0.00' AFTER `strPropCode`, " + " ADD COLUMN `dblFreight` DECIMAL(18,4) NOT NULL DEFAULT '0.00' AFTER `dblFOB`, " + "	ADD COLUMN `dblInsurance` DECIMAL(18,4) NOT NULL DEFAULT '0.00' AFTER `dblFreight`, "
				+ " ADD COLUMN `dblOtherCharges` DECIMAL(18,4) NOT NULL DEFAULT '0.00' AFTER `dblInsurance`," + "	ADD COLUMN `dblCIF` DECIMAL(18,4) NOT NULL DEFAULT '0.00' AFTER `dblOtherCharges`; ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblcurrencymaster` CHANGE COLUMN `dblConvToBaseCurr` `dblConvToBaseCurr` DECIMAL(18,6) NOT NULL DEFAULT '0.00' AFTER `strAccountNo`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblpartymaster` ADD COLUMN `strCurrency` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strExternalCode`;";
		funExecuteQuery(sql);
		
		sql="ALTER TABLE `tblpurchaseorderhd` ADD COLUMN `dblExchangeRate` DECIMAL(18,4) NOT NULL DEFAULT '0.0000' AFTER `dblVATClaim`;";
		funExecuteQuery(sql);
		
		sql="ALTER TABLE `tblpropertysetup` ADD COLUMN `strShowAllTaxesOnTransaction` VARCHAR(1) NOT NULL DEFAULT 'Y' AFTER `strShowAllPartyToAllLoc`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblgrnhd` "
				+"	ADD COLUMN `strAuthLevel1` VARCHAR(10) NOT NULL DEFAULT '' AFTER `dblRoundOff`, "
				+"	ADD COLUMN `strAuthLevel2` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel1`, "
				+"	ADD COLUMN `strAuthLevel3` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel2`, "
				+"	ADD COLUMN `strAuthLevel4` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel3`, "
				+"	ADD COLUMN `strAuthLevel5` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel4` ";
		funExecuteQuery(sql);
		
		sql = "	ALTER TABLE `tblpurchaseorderhd` "
				+"	ADD COLUMN `strAuthLevel1` VARCHAR(10) NOT NULL DEFAULT '' AFTER `dblExchangeRate`, "
				+"	ADD COLUMN `strAuthLevel2` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel1`, "
				+"	ADD COLUMN `strAuthLevel3` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel2`, "
				+"	ADD COLUMN `strAuthLevel4` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel3`, "
				+ "	ADD COLUMN `strAuthLevel5` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel4` ";
		funExecuteQuery(sql);
		
		sql = "		ALTER TABLE `tblbillpasshd`\r\n" + 
				"	ADD COLUMN `strAuthLevel1` VARCHAR(10) NOT NULL DEFAULT '' AFTER `intLevel`,\r\n" + 
				"	ADD COLUMN `strAuthLevel2` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel1`,\r\n" + 
				"	ADD COLUMN `strAuthLevel3` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel2`,\r\n" + 
				"	ADD COLUMN `strAuthLevel4` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel3`,\r\n" + 
				"	ADD COLUMN `strAuthLevel5` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel4`;";
		funExecuteQuery(sql);
		
		sql = "			ALTER TABLE `tblreqhd`\r\n" + 
				"	ADD COLUMN `strAuthLevel1` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strSessionCode`,\r\n" + 
				"	ADD COLUMN `strAuthLevel2` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel1`,\r\n" + 
				"	ADD COLUMN `strAuthLevel3` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel2`,\r\n" + 
				"	ADD COLUMN `strAuthLevel4` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel3`,\r\n" + 
				"	ADD COLUMN `strAuthLevel5` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel4`;";
		funExecuteQuery(sql);
		
		sql = "				ALTER TABLE `tblmaterialreturnhd`\r\n" + 
				"	ADD COLUMN `strAuthLevel1` VARCHAR(10) NOT NULL DEFAULT '' AFTER `intLevel`,\r\n" + 
				"	ADD COLUMN `strAuthLevel2` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel1`,\r\n" + 
				"	ADD COLUMN `strAuthLevel3` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel2`,\r\n" + 
				"	ADD COLUMN `strAuthLevel4` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel3`,\r\n" + 
				"	ADD COLUMN `strAuthLevel5` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel4`;";
		funExecuteQuery(sql);
		
		sql = "					ALTER TABLE `tblmishd`\r\n" + 
				"	ADD COLUMN `strAuthLevel1` VARCHAR(10) NOT NULL DEFAULT '' AFTER `dblTotalAmt`,\r\n" + 
				"	ADD COLUMN `strAuthLevel2` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel1`,\r\n" + 
				"	ADD COLUMN `strAuthLevel3` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel2`,\r\n" + 
				"	ADD COLUMN `strAuthLevel4` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel3`,\r\n" + 
				"	ADD COLUMN `strAuthLevel5` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel4`;";
		funExecuteQuery(sql);
		
		sql = "					ALTER TABLE `tblinitialinventory`\r\n" + 
				"	ADD COLUMN `strAuthLevel1` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strConversionUOM`,\r\n" + 
				"	ADD COLUMN `strAuthLevel2` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel1`,\r\n" + 
				"	ADD COLUMN `strAuthLevel3` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel2`,\r\n" + 
				"	ADD COLUMN `strAuthLevel4` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel3`,\r\n" + 
				"	ADD COLUMN `strAuthLevel5` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel4`;";
		funExecuteQuery(sql);
		
		sql = "						ALTER TABLE `tblstockpostinghd`\r\n" + 
				"	ADD COLUMN `strAuthLevel1` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strPhyStkFrom`,\r\n" + 
				"	ADD COLUMN `strAuthLevel2` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel1`,\r\n" + 
				"	ADD COLUMN `strAuthLevel3` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel2`,\r\n" + 
				"	ADD COLUMN `strAuthLevel4` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel3`,\r\n" + 
				"	ADD COLUMN `strAuthLevel5` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel4`;";
		funExecuteQuery(sql);
		
		sql = "							ALTER TABLE `tblproductionhd`\r\n" + 
				"	ADD COLUMN `strAuthLevel1` VARCHAR(10) NOT NULL DEFAULT '' AFTER `intLevel`,\r\n" + 
				"	ADD COLUMN `strAuthLevel2` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel1`,\r\n" + 
				"	ADD COLUMN `strAuthLevel3` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel2`,\r\n" + 
				"	ADD COLUMN `strAuthLevel4` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel3`,\r\n" + 
				"	ADD COLUMN `strAuthLevel5` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel4`;";
		funExecuteQuery(sql);
		
		sql = "								ALTER TABLE `tblproductionorderhd`\r\n" + 
				"	ADD COLUMN `strAuthLevel1` VARCHAR(10) NOT NULL DEFAULT '' AFTER `intLevel`,\r\n" + 
				"	ADD COLUMN `strAuthLevel2` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel1`,\r\n" + 
				"	ADD COLUMN `strAuthLevel3` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel2`,\r\n" + 
				"	ADD COLUMN `strAuthLevel4` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel3`,\r\n" + 
				"	ADD COLUMN `strAuthLevel5` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel4`;";
		funExecuteQuery(sql);
		
		sql = "									ALTER TABLE `tblpurchasereturnhd`\r\n" + 
				"	ADD COLUMN `strAuthLevel1` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strPRNo`,\r\n" + 
				"	ADD COLUMN `strAuthLevel2` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel1`,\r\n" + 
				"	ADD COLUMN `strAuthLevel3` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel2`,\r\n" + 
				"	ADD COLUMN `strAuthLevel4` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel3`,\r\n" + 
				"	ADD COLUMN `strAuthLevel5` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel4`;\r\n" + 
				"	";
		funExecuteQuery(sql);
		
		sql = "										ALTER TABLE `tblpurchaseindendhd`\r\n" + 
				"	ADD COLUMN `strAuthLevel1` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strDocCode`,\r\n" + 
				"	ADD COLUMN `strAuthLevel2` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel1`,\r\n" + 
				"	ADD COLUMN `strAuthLevel3` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel2`,\r\n" + 
				"	ADD COLUMN `strAuthLevel4` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel3`,\r\n" + 
				"	ADD COLUMN `strAuthLevel5` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel4`;";
		funExecuteQuery(sql);
		
		sql = "										ALTER TABLE `tblrateconthd`\r\n" + 
				"	ADD COLUMN `strAuthLevel1` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strClientCode`,\r\n" + 
				"	ADD COLUMN `strAuthLevel2` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel1`,\r\n" + 
				"	ADD COLUMN `strAuthLevel3` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel2`,\r\n" + 
				"	ADD COLUMN `strAuthLevel4` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel3`,\r\n" + 
				"	ADD COLUMN `strAuthLevel5` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel4`;";
		funExecuteQuery(sql);
		
		sql = "											ALTER TABLE `tblstockadjustmenthd`\r\n" + 
				"	ADD COLUMN `strAuthLevel1` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strConversionUOM`,\r\n" + 
				"	ADD COLUMN `strAuthLevel2` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel1`,\r\n" + 
				"	ADD COLUMN `strAuthLevel3` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel2`,\r\n" + 
				"	ADD COLUMN `strAuthLevel4` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel3`,\r\n" + 
				"	ADD COLUMN `strAuthLevel5` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel4`;";
		funExecuteQuery(sql);
		
		sql = "												ALTER TABLE `tblstocktransferhd`\r\n" + 
				"	ADD COLUMN `strAuthLevel1` VARCHAR(10) NOT NULL DEFAULT '' AFTER `dblTotalAmt`,\r\n" + 
				"	ADD COLUMN `strAuthLevel2` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel1`,\r\n" + 
				"	ADD COLUMN `strAuthLevel3` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel2`,\r\n" + 
				"	ADD COLUMN `strAuthLevel4` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel3`,\r\n" + 
				"	ADD COLUMN `strAuthLevel5` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel4`;";
		funExecuteQuery(sql);
		
		sql = "													ALTER TABLE `tblworkorderhd`\r\n" + 
				"	ADD COLUMN `strAuthLevel1` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strToLocCode`,\r\n" + 
				"	ADD COLUMN `strAuthLevel2` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel1`,\r\n" + 
				"	ADD COLUMN `strAuthLevel3` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel2`,\r\n" + 
				"	ADD COLUMN `strAuthLevel4` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel3`,\r\n" + 
				"	ADD COLUMN `strAuthLevel5` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel4`;";
		funExecuteQuery(sql);
		
		sql="ALTER TABLE `tblattachdocument` CHANGE COLUMN `strContentType` `strContentType` VARCHAR(200) NOT NULL AFTER `binContent`";
		funExecuteQuery(sql);

		sql="ALTER TABLE `tblattachdocument` CHANGE COLUMN `strActualFileName` `strActualFileName` VARCHAR(200) NOT NULL DEFAULT '' AFTER `strCode`";
		funExecuteQuery(sql);
		
		sql="ALTER TABLE `tblattachdocument` CHANGE COLUMN `strChangedFileName` `strChangedFileName` VARCHAR(200) NOT NULL DEFAULT '' AFTER `strActualFileName`";
		funExecuteQuery(sql);
		
		
		sql=" ALTER TABLE `tblstockadjustmentdtl` "
			+" ADD COLUMN `dblParentQty` DECIMAL(10,4) NOT NULL DEFAULT '0.0' AFTER `strWSLinkedProdCode`; ";
		funExecuteQuery(sql);
		
	    sql="ALTER TABLE `tblpossalesdtl` "
		+" ADD COLUMN `strCostCenterCode` VARCHAR(50) NOT NULL DEFAULT '' AFTER `intId`, "
		+" ADD COLUMN `strCostCenterName` VARCHAR(255) NOT NULL DEFAULT '' AFTER `strCostCenterCode`, "
		+" ADD COLUMN `strLocationCode` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strCostCenterName`, "
		+" ADD COLUMN `dblAmount` DECIMAL(10,2) NOT NULL DEFAULT '0.00' AFTER `strLocationCode`, "
		+" ADD COLUMN `dblPercent` DECIMAL(10,2) NOT NULL DEFAULT '0.00' AFTER `dblAmount`, "
		+" ADD COLUMN `dblPercentAmt` DECIMAL(10,2) NOT NULL DEFAULT '0.00' AFTER `dblPercent`; ";
	
	    funExecuteQuery(sql);
	
	
		sql="ALTER TABLE `tblgrnhd`"
			+ "ADD COLUMN `dblFOB` DECIMAL(18,4) NOT NULL DEFAULT '0.0000' AFTER `strAuthLevel5`,"
			+ "ADD COLUMN `dblFreight` DECIMAL(18,4) NOT NULL DEFAULT '0.0000' AFTER `dblFOB`,"
			+ "ADD COLUMN `dblInsurance` DECIMAL(18,4) NOT NULL DEFAULT '0.0000' AFTER `dblFreight`,"
			+ "ADD COLUMN `dblOtherCharges` DECIMAL(18,4) NOT NULL DEFAULT '0.0000' AFTER `dblInsurance`";
		funExecuteQuery(sql);
	
		sql="ALTER TABLE `tbllinkup` "
				   +" ADD COLUMN `strWebBookAccCode` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strModuleType`, "
				   +" ADD COLUMN `strWebBookAccName` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strWebBookAccCode`;";
		funExecuteQuery(sql);
	
	
		sql="ALTER TABLE `tbllinkup` "
				+" CHANGE COLUMN `strOperationType` `strOperationType` VARCHAR(20) NOT NULL AFTER `strSGType`;" ;
		funExecuteQuery(sql);

		sql="ALTER TABLE `tbllinkup` "
			+" CHANGE COLUMN `strMasterDesc` `strMasterDesc` VARCHAR(255) NOT NULL DEFAULT '' AFTER `strMasterCode`;" ;	
		
		funExecuteQuery(sql);
		
		sql = " DROP TABLE IF EXISTS `tbltreemast`; ";
		funExecuteQuery(sql);
		
		sql="ALTER TABLE `tbluserhd` "
			+" ADD COLUMN `strReorderLevel` VARCHAR(1) NOT NULL DEFAULT 'N' AFTER `strShowDashBoard`;" ;
		funExecuteQuery(sql);
				
		sql="ALTER TABLE `tbllinkup` "
			+" CHANGE COLUMN `strWebBookAccCode` `strWebBookAccCode` VARCHAR(20) NOT NULL DEFAULT '' AFTER `strModuleType`;" ;
		funExecuteQuery(sql);
		
		sql="ALTER TABLE `tbltaxhd` ADD COLUMN `strTaxReversal` VARCHAR(1) NOT NULL DEFAULT 'N' AFTER `strNotApplicableForComesa`;";
		funExecuteQuery(sql);

		sql="ALTER TABLE `tblgrnhd` ADD COLUMN `dblVATClaim` DECIMAL(18,4) NOT NULL DEFAULT '0.0000' AFTER `dblOtherCharges`, "
			+ "ADD COLUMN `dblClearingCharges` DECIMAL(18,4) NOT NULL DEFAULT '0.0000' AFTER `dblVATClaim`;";
		funExecuteQuery(sql);
		
		sql="ALTER TABLE `tblpropertysetup` "
		   +" ADD COLUMN `strSOKOTPrint` VARCHAR(1) NOT NULL DEFAULT 'N' AFTER `strShowAllTaxesOnTransaction`" ;
		funExecuteQuery(sql);
		
		sql="ALTER TABLE `tblpossalesdtl` "
				+"ADD COLUMN `strItemType` VARCHAR(20) NOT NULL DEFAULT '' AFTER `dblPercentAmt` ";
			funExecuteQuery(sql);
			
		sql="ALTER TABLE `tblcompanymaster` ADD COLUMN `strYear` VARCHAR(1) NOT NULL DEFAULT 'A' AFTER `strWebBookAPGLModule`; ";
		funExecuteQuery(sql);
		
		sql="ALTER TABLE `tblpurchaseorderhd`  CHANGE COLUMN `dblConversion` `dblConversion` DECIMAL(18,4) NOT NULL DEFAULT '1.0' AFTER `dtPayDate`; ";
		funExecuteQuery(sql);
		
		sql=" update tblpurchaseorderhd set dblConversion='1.0' where dblConversion=0.0000 ";
		funExecuteQuery(sql);
		
		sql="ALTER TABLE `tblgrnhd` CHANGE COLUMN `dblConversion` `dblConversion` DECIMAL(18,4) NOT NULL DEFAULT '1.0' AFTER `strConsignedCountry`; ";
		funExecuteQuery(sql);
		
		sql=" update tblgrnhd set dblConversion='1.0' where dblConversion=0.0000 ";
		funExecuteQuery(sql);
		
		sql = " ALTER TABLE `tblpurchasereturnhd` ADD COLUMN `dblConversion` DECIMAL(18,2) NOT NULL DEFAULT '1.00' AFTER `strAuthLevel5`; ";
		funExecuteQuery(sql);
		
		sql = " ALTER TABLE `tblpurchasereturnhd` ADD COLUMN `strCurrency` VARCHAR(50) NOT NULL DEFAULT '' AFTER `dblConversion`; ";
		funExecuteQuery(sql);
		
		sql="ALTER TABLE `tblgrnhd` ADD COLUMN `strJVNo` VARCHAR(50) NOT NULL DEFAULT '' AFTER `dblClearingCharges` ";
		funExecuteQuery(sql);
		
		sql="ALTER TABLE `tblpurchasereturnhd` ADD COLUMN `strJVNo` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strCurrency` ";
		funExecuteQuery(sql);
		
		sql="ALTER TABLE `tblpurchasereturnhd` ADD COLUMN `strJVNo` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strCurrency` ";
		funExecuteQuery(sql);
		
		sql="ALTER TABLE `tblinvoicehd` ADD COLUMN `strJVNo` VARCHAR(50) NOT NULL DEFAULT '' AFTER `dblExtraCharges` ";
		funExecuteQuery(sql);
		
		sql="ALTER TABLE `tblsalesreturnhd` ADD COLUMN `strJVNo` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strCurrency` ";
		funExecuteQuery(sql);
		

		sql=" ALTER TABLE `tblstocktransferhd` ADD COLUMN `strReqCode` VARCHAR(255) NOT NULL DEFAULT ' ' AFTER `strAuthLevel5`;";
		funExecuteQuery(sql);
		
		
		String propCode=req.getSession().getAttribute("propertyCode").toString();
		sql=" update tblpartymaster set strCurrency=(select strCurrencyCode from tblpropertysetup) where strCurrency='' or LEFT(strCurrency,3)!='CU0' "
		  + " and strPropCode='"+propCode+"' ";
		funExecuteQuery(sql);
		
		sql = " CREATE TABLE IF NOT EXISTS `tbltreemast` (  " + "  `strFormName` varchar(50) NOT NULL,  " + "  `strFormDesc` varchar(200) NOT NULL,  " + "   `strRootNode` varchar(50) NOT NULL,  " + "   `intRootIndex` int(11) NOT NULL,  " + " `strType` varchar(50) NOT NULL, " + "  `intFormKey` int(11) NOT NULL, " + " `intFormNo` int(11) NOT NULL, " + " `strImgSrc` varchar(50) NOT NULL, "
				+ " `strImgName` varchar(100) NOT NULL, " + " `strModule` varchar(15) NOT NULL, " + " `strTemp` int(11) NOT NULL, " + " `strActFile` varchar(3) NOT NULL,  " + " `strHelpFile` varchar(150) NOT NULL, " + " `strProcessForm` varchar(4) NOT NULL DEFAULT 'NA', " + " `strAutorisationForm` varchar(4) NOT NULL DEFAULT 'NA', " + " `strRequestMapping` varchar(50) DEFAULT NULL, "
				+ " `strAdd` varchar(255) DEFAULT NULL, " + " `strAuthorise` varchar(255) DEFAULT NULL, " + " `strDelete` varchar(255) DEFAULT NULL, " + " `strDeliveryNote` varchar(255) DEFAULT NULL, " + " `strDirect` varchar(255) DEFAULT NULL, " + " `strEdit` varchar(255) DEFAULT NULL, " + " `strGRN` varchar(255) DEFAULT NULL, " + " `strGrant` varchar(255) DEFAULT NULL, "
				+ " `strMinimumLevel` varchar(255) DEFAULT NULL, " + " `strOpeningStock` varchar(255) DEFAULT NULL, " + " `strPrint` varchar(255) DEFAULT NULL, " + " `strProductionOrder` varchar(255) DEFAULT NULL, " + " `strProject` varchar(255) DEFAULT NULL, " + " `strPurchaseIndent` varchar(255) DEFAULT NULL, " + " `strPurchaseOrder` varchar(255) DEFAULT NULL, "
				+ " `strPurchaseReturn` varchar(255) DEFAULT NULL, " + " `strRateContractor` varchar(255) DEFAULT NULL, " + " `strRequisition` varchar(255) DEFAULT NULL, " + " `strSalesOrder` varchar(255) DEFAULT NULL, " + " `strSalesProjection` varchar(255) DEFAULT NULL, " + " `strSalesReturn` varchar(255) DEFAULT NULL, " + " `strServiceOrder` varchar(255) DEFAULT NULL, "
				+ " `strSubContractorGRN` varchar(255) DEFAULT NULL, " + " `strView` varchar(255) DEFAULT NULL, " + " `strWorkOrder` varchar(255) DEFAULT NULL, " + " `strAuditForm` varchar(255) DEFAULT NULL, " + " `strMIS` varchar(255) DEFAULT NULL, " + " PRIMARY KEY (`strFormName`, `strModule`) " + " ) ENGINE=InnoDB DEFAULT CHARSET=latin1; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tbltreemast` ADD COLUMN `strInvoice` VARCHAR(255) NULL DEFAULT NULL AFTER `strMIS` ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tbltreemast` " + "		ADD COLUMN `strDeliverySchedule` VARCHAR(255) NULL DEFAULT NULL AFTER `strInvoice`; ";
		funExecuteQuery(sql);
				
		sql=" ALTER TABLE `tbltreemast` "
				  + " ADD COLUMN `strFormAccessYN` VARCHAR(1) NOT NULL DEFAULT 'Y' AFTER `strDeliverySchedule`; " ;
		funExecuteQuery(sql);

		sql = " DELETE FROM `tbltreemast`; ";
		funExecuteQuery(sql);

		/*----------------WebStock Forms only---------------------------*/
		String strIndustryType = "",strWebStockModule="";
		List<clsCompanyMasterModel> listClsCompanyMasterModel = objSetupMasterService.funGetListCompanyMasterModel();
		if (listClsCompanyMasterModel.size() > 0) {
			clsCompanyMasterModel objCompanyMasterModel = listClsCompanyMasterModel.get(0);
			strIndustryType = objCompanyMasterModel.getStrIndustryType();
			strWebStockModule = objCompanyMasterModel.getStrWebStockModule();
		}

		
		if("No".equalsIgnoreCase(strWebStockModule))
		{
			sql = " INSERT INTO `tbltreemast` (`strFormName`, `strFormDesc`, `strRootNode`, `intRootIndex`, `strType`, `intFormKey`, `intFormNo`, `strImgSrc`, `strImgName`, `strModule`, `strTemp`, `strActFile`, `strHelpFile`, `strProcessForm`, `strAutorisationForm`, `strRequestMapping`, `strAdd`, `strAuthorise`, `strDelete`, `strDeliveryNote`, `strDirect`, `strEdit`, `strGRN`, `strGrant`, `strMinimumLevel`, `strOpeningStock`, `strPrint`, `strProductionOrder`, `strProject`, `strPurchaseIndent`, `strPurchaseOrder`, `strPurchaseReturn`, `strRateContractor`, `strRequisition`, `strSalesOrder`, `strSalesProjection`, `strSalesReturn`, `strServiceOrder`, `strSubContractorGRN`, `strView`, `strWorkOrder`, `strAuditForm`, `strMIS`) VALUES "
					+ " ('frmGroupMaster', 'Group Master', 'Master', 8, 'M', 7, 5, '1', 'Group_Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmGroupMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
					+ " ('frmLocationMaster', 'Location Master', 'Master', 7, 'M', 8, 6, '1', 'Location-Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmLocationMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
					+ " ('frmSupplierMaster', 'Supplier Master', 'Master', 8, 'M', 28, 14, '1', 'Supplier-Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmSupplierMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
					+ " ('frmPropertyMaster', 'Property Master', 'Master', 7, 'M', 17, 8, '1', 'Property-Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmPropertyMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
					+ " ('frmTaxMaster', 'Tax Master', 'Master', 8, 'M', 29, 15, '1', 'Tax-Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmTaxMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
					+ " ('frmProductMaster', 'Product Master', 'Master', 8, 'M', 16, 7, '1', 'Product-Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmProductMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
					+ " ('frmSubGroupMaster', 'Sub Group Master', 'Master', 8, 'M', 27, 13, '1', 'Sub-Group-Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmSubGroupMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
					+ " ('frmSetup', 'Property Setup', 'Tools', 8, 'L', 24, 12, '1', 'Setup.png', '1', 1, '1', '1', 'NO', 'YES', 'frmSetup.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ";
			funExecuteQuery(sql);
		}
		else
		{
		sql = " INSERT INTO `tbltreemast` (`strFormName`, `strFormDesc`, `strRootNode`, `intRootIndex`, `strType`, `intFormKey`, `intFormNo`, `strImgSrc`, `strImgName`, `strModule`, `strTemp`, `strActFile`, `strHelpFile`, `strProcessForm`, `strAutorisationForm`, `strRequestMapping`, `strAdd`, `strAuthorise`, `strDelete`, `strDeliveryNote`, `strDirect`, `strEdit`, `strGRN`, `strGrant`, `strMinimumLevel`, `strOpeningStock`, `strPrint`, `strProductionOrder`, `strProject`, `strPurchaseIndent`, `strPurchaseOrder`, `strPurchaseReturn`, `strRateContractor`, `strRequisition`, `strSalesOrder`, `strSalesProjection`, `strSalesReturn`, `strServiceOrder`, `strSubContractorGRN`, `strView`, `strWorkOrder`, `strAuditForm`, `strMIS`) VALUES "
				+ " ('Cost Center\\r\\n', 'Cost Center\\r\\n', 'Reports', 7, 'O', 1, 1, '1', 'default.png', '1', 1, '1', '1', 'NA', 'NA', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmAccountHolderMaster', 'Account Holder Master', 'Master', 2, 'M', 2, 2, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmAccountHolderMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmACGroupMaster', 'Group Master', 'Master', 4, 'M', 4, 4, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmACGroupMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmAgeingReport', 'Ageing Rport', 'Sub Contracting Report', 3, 'R', 73, 1, '1', 'default.png', '6', 1, '1', '1', 'No', 'No', 'frmAgeingReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmAttributeMaster', 'Attribute Master', 'Master', 8, 'M', 1, 1, '12', 'Attribute-Master.png', '1', 1, '1', '1', 'NO', '1', 'frmAttributeMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmAttributeValueMaster', 'Attribute Value Master', 'Master', 8, 'M', 2, 2, '3', 'Attribute-Value-Master.png', '3', 3, '3', '3', 'NO', 'YES', 'frmAttributeValueMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmAuditFlash', 'Audit Flash', 'Tools', 8, 'L', 48, 26, '1', 'audit flash.png', '1', 1, '1', '1', 'NO', 'NO', 'frmAuditFlash.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmAuthorisationTool', 'Authorisation', 'Tools', 8, 'L', 10, 2, '1', 'Authourisation.png', '1', 1, '1', '1', 'NO', 'NO', 'frmAuthorisationTool.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmBankMaster', 'Bank Master', 'Master', 3, 'M', 3, 3, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmBankMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmBillPassing', 'Bill Passing', 'Accounts', 5, 'T', 3, 1, '1', 'Bill-Passing.png', '1', 1, '1', '1', 'NO', 'YES', 'frmBillPassing.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),";
		if (strIndustryType.equalsIgnoreCase("Manufacture")) {
			sql += " ('frmBOMMaster', 'BOM Master', 'Master', 8, 'M', 4, 3, '2', 'ReciepeMaster-BOM-Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmBOMMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), ";
		}

		else {
			sql += " ('frmBOMMaster', 'Recipe Master', 'Master', 8, 'M', 4, 3, '2', 'ReciepeMaster-BOM-Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmBOMMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), ";
		}

		sql += " ('frmCharacteristicsMaster', 'Characteristics Master', 'Master', 8, 'M', 5, 4, '1', 'Characteristics-Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmCharMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmChargeMaster', 'Charge Master', 'Master', 5, 'M', 5, 5, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmChargeMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmChargeProcessing', 'Charge Processing', 'Processing', 2, 'P', 1, 1, '1', 'default.png', '5', 1, '1', '1', 'YES', 'NO', 'frmChargeProcessing.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmCompanyMaster', 'Company Master', 'Master', 1, 'M', 1, 10, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmCompanyMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmCompanyTypeMaster', 'Company Type Master', 'Master', 1, 'M', 1, 12, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmCompanyTypeMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmConsReceiptValMiscSuppReqReport', 'Consolidated Receipt Value All Supplier Required Report', 'Purchases', 7, 'R', 51, 29, '1', 'Consolidated Receipt Value Misc Suppler Required Report.png', '1', 1, '1', '1', 'NO', 'NO', 'frmConsReceiptValMiscSuppReqReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmConsReceiptValStoreWiseBreskUPReport', 'Consolidated Receipt value Store Wise BreakUP', 'Purchases', 7, 'R', 51, 30, '1', 'Consolidated Receipt value Store Wise BreakUP.png', '1', 1, '1', '1', 'NO', 'No', 'frmConsReceiptValStoreWiseBreskUPReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmCostOfIssue', 'Cost Of Issue', 'Stores', 9, 'R', 42, 20, '1', 'cost of issue.png', '1', 1, '1', '1', 'NO', 'NO', 'frmCostOfIssue.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmCustomerMaster', 'Customer Master', 'Master', 1, 'M', 52, 1, '1', 'default.png', '6', 1, '1', '1', 'NO', 'No', 'frmCustomerMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmDebtorLedger', 'Debtor Ledger Tool', 'Tools', 1, 'L', 1, 1, '12', 'default.png', '5', 1, '1', '1', 'NO', '1', 'frmDebtorLedger.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
//				+ " ('frmDebtorReceipt', 'Debtor Receipt', 'Transaction', 1, 'T', 1, 1, '12', 'default.png', '5', 1, '1', '1', 'NO', '1', 'frmDebtorReceipt.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmDeleteTransaction', 'Delete Transaction', 'Tools', 8, 'L', 46, 24, '1', 'delete trasaction.png', '1', 1, '1', '1', 'NO', 'NO', 'frmDeleteTransaction.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmDeliveryChallan', 'Delivery Challan', 'Sales', 2, 'T', 56, 3, '1', 'default.png', '6', 1, '1', '1', 'NO', 'Yes', 'frmDeliveryChallan.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmDeliveryChallanList', 'Delivery Challan List', 'Sub contracting Report', 3, 'R', 63, 5, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmDeliveryChallanList.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmDeliveryChallanSlip', 'Delivery Challan Slip', 'Sub contracting Report', 3, 'R', 62, 4, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmDeliveryChallanSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmDeliveryChallanSlipInvoice', 'Delivery Challan Slip Invoice', 'Sub contracting Report', 3, 'R', 74, 2, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmDeliveryChallanSlipInvoice.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmDeliveryNote', 'Delivery Note', 'Sales', 2, 'T', 67, 8, '1', 'default.png', '6', 1, '1', '1', 'NO', 'Yes', 'frmDeliveryNote.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmDeliveryNoteList', 'Delivery Note List', 'Sub contracting Report', 3, 'R', 68, 10, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmDeliveryNoteList.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmDeliveryNoteSlip', 'Delivery Note Slip', 'Sub contracting Report', 3, 'R', 67, 9, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmDeliveryNoteSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmDocumentReconciliation', 'Document Reconciliation', 'Tools', 8, 'L', 10, 2, '1', 'Document_Reconciliation_Fla.png', '1', 1, '1', '1', 'NO', 'NO', 'frmDocumentReconciliation.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmDueDateMonitoringReport', 'Due Date Monitoring Report', 'Sub Contracting Report', 3, 'R', 75, 3, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmDueDateMonitoringReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmEditOtherInfo', 'Edit Other Info', 'Master', 1, 'M', 1, 15, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmEditOtherInfo.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmEmailSending', 'Sending Email', 'Tools', 8, 'L', 51, 29, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmEmailSending.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseAbstractReport', 'Abstract Report', 'Reports', 3, 'R', 21, 3, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseAbstractReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseAuditFlash', 'Excise Audit Flash', 'Tools', 4, 'L', 14, 3, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseAuditFlash.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseBillGenerate', 'Bill Generate', 'Transaction', 2, 'L', 19, 3, '1', 'default.png', '2', 1, '1', '1', 'YES', 'NO', 'frmExciseBillGenerate.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseBrandMaster', 'Brand Master', 'Master', 1, 'M', 3, 1, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseBrandMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseCashMemoReport', 'Cash Memo Report', 'Reports', 3, 'R', 184, 8, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseCashMemoReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseBrandWiseInquiry', 'Brand Wise Inquiry', 'Reports', 3, 'R', 186, 10, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseBrandWiseInquiry.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseCategoryMaster', 'Category Master', 'Master', 1, 'M', 13, 5, '1', 'default.png', '0', 1, '1', '1', 'NO', 'NO', 'frmExciseCategoryMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
//				+ " ('frmExciseChallan', 'Excise Challan', 'Sales', 2, 'T', 57, 4, '1', 'default.png', '6', 1, '1', '1', 'NO', 'Yes', 'frmExciseChallan.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseChataiReport', 'Chatai Report', 'Reports', 3, 'R', 23, 5, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseChataiReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseCityMaster', 'City Master', 'Master', 1, 'M', 18, 9, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseCityMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseDeleteTransaction', 'Delete Transction', 'Transaction', 2, 'R', 20, 5, '1', 'default.png', '2', 1, '1', '1', 'YES', 'NO', 'frmExciseDeleteTransaction.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseFLR3A', 'FLR- III (3A) Report', 'Reports', 3, 'R', 2, 1, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseFLR3A.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseFLR4Report', 'FLR- 4 Report', 'Reports', 3, 'R', 22, 4, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseFLR4Report.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseFLR6', 'FLR-VI (6A) Report', 'Reports', 3, 'R', 5, 2, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseFLR6.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseLicenceMaster', 'Licence Master', 'Master', 1, 'M', 15, 7, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseLicenceMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseLocationMaster', 'Location Master', 'Master', 1, 'M', 17, 8, '1', 'Location-Master.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseLocationMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseManualSale', 'Excise Sale', 'Transaction', 2, 'T', 6, 2, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseManualSale.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseOneDayPass', 'One Day Pass', 'Transaction', 2, 'T', 24, 4, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseOneDayPass.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseOpeningStock', 'Opening Stock', 'Transaction', 2, 'T', 8, 5, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseOpeningStock.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExcisePermitMaster', 'Permit Master', 'Master', 1, 'M', 24, 9, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExcisePermitMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExcisePhysicalStkPosting', 'Excise Physical Stk Posting', 'Store', 5, 'L', 4, 1, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExcisePhysicalStkPosting.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExcisePOSDataExportImport', 'Excise POS DataExport Import', 'Store', 5, 'L', 7, 2, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExcisePOSDataExportImport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExcisePOSLinkUp', 'POS Excise Link Up', 'Tools', 4, 'L', 10, 1, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExcisePOSLinkUp.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExcisePOSSale', 'Excise POS Sale', 'Tools', 4, 'L', 13, 2, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExcisePOSSale.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExcisePropertySetUp', 'Property Set Up', 'Master', 1, 'M', 14, 6, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExcisePropertySetUp.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExcisePurchaseAnylasisReport', 'Purchase Anylasis Report', 'Reports', 3, 'R', 182, 6, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExcisePurchaseAnylasisReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExcisePurchaseReport', 'Purchase Report', 'Reports', 3, 'R', 183, 7, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExcisePurchaseReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseRecipeMaster', 'Excise Recipe Master', 'Master', 1, 'M', 16, 5, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseRecipeMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseSalesReport', 'Sales Report', 'Reports', 3, 'R', 185, 9, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseSalesReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseSizeMaster', 'Size Master', 'Master', 1, 'M', 11, 3, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseSizeMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseStructureUpdate', 'Excise Structure Update', 'Tools', 4, 'L', 15, 4, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseStructureUpdate.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseSubCategoryMaster', 'Sub Category Master', 'Master', 1, 'M', 16, 4, '1', 'default.png', '0', 1, '1', '1', 'NO', 'NO', 'frmExciseSubCategoryMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseSupplierMaster', 'Supplier Master', 'Master', 1, 'M', 9, 4, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseSupplierMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseTransportPass', 'Transport Pass', 'Transaction', 2, 'T', 1, 1, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseTransportPass.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExpiryFlash', 'Expiry Flash', 'Tools', 8, 'L', 49, 27, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmExpiryFlash.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmFoodCost', 'Cost Analysis', 'Stores', 9, 'R', 53, 33, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmFoodCost.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmGRN', 'GRN(Good Receiving Note)', 'Receiving', 4, 'T', 6, 1, '1', 'GRN.png', '1', 1, '1', '1', 'YES', 'YES', 'frmGRN.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Yes', NULL), "
				+ " ('frmGRNRegisterReport', 'GRN Register Report', 'Receiving Reports', 9, 'R', 73, 5, '1', 'GRN Register Report.png', '1', 1, '1', '1', 'NO', 'NO', 'frmGRNRegisterReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmGrnSlip', 'Grn Slip', 'Receiving Reports', 9, 'R', 35, 4, '1', 'GRN-Slip.png', '1', 1, '1', '1', 'NO', 'NO', 'frmGrnSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmGRNSummaryReport', 'GRN Summary Report', 'Receiving Reports', 9, 'R', 85, 6, '1', 'GRN Summary Report.png', '1', 1, '1', '1', 'NO', 'NO', 'frmGRNSummaryReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmGroupConsumption', 'Group Consumption Report', 'Stores', 7, 'R', 52, 31, '1', 'default.pmg', '1', 1, '1', '1', 'NO', 'NO', 'frmGroupConsumption.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmGroupMaster', 'Group Master', 'Master', 8, 'M', 7, 5, '1', 'Group_Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmGroupMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmPOSItemMasterImport', 'Import POS Item', 'Tools', 8, 'L', 87, 4, '1', 'import pos item.png', '1', 1, '1', '1', 'NO', 'NO', 'frmPOSItemMasterImport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmInterfaceMaster', 'Interface Master', 'Master', 7, 'M', 7, 7, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmInterfaceMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmInvoicingPrinting', 'Invoicing Printing', 'Reports', 6, 'R', 20, 1, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmInvoicingPrinting.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmInwardOutwardRegister', 'Inward Outward Register', 'Sub Contracting Report', 3, 'R', 77, 5, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmInwardOutwardRegister.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmIssueListingIndigeous', 'Issue Listing Indigeous', 'sub contracting Report', 3, 'R', 78, 6, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmIssueListingIndigeous.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmItemVariancePriceFlash', 'Item Variance Price Flash', 'Tools', 8, 'R', 55, 34, '1', 'Item Variance Price Flash.png', '1', 1, '1', '1', 'NO', 'NO', 'frmItemVariancePriceFlash.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmJOAllocation', 'Job Order Allocation', 'Sales', 2, 'T', 65, 6, '1', 'default.png', '6', 1, '1', '1', 'NO', 'Yes', 'frmJOAllocation.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmJobOrder', 'Job Order', 'Sales', 2, 'T', 59, 5, '1', 'default.png', '6', 1, '1', '1', 'NO', 'Yes', 'frmJobOrder.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmJobOrderAllocationSlip', 'Job Order Allocation Slip', 'Sub contracting Report', 3, 'R', 66, 8, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmJobOrderAllocationSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmJobOrderList', 'Job Order List', 'Sub contracting Report', 3, 'R', 64, 6, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmJobOrderList.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmJobOrderSlip', 'Job Order Slip', 'Sub contracting Report', 3, 'R', 63, 5, '1', 'defailt.png', '6', 1, '1', '1', 'NO', 'NO', 'frmJobOrderSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmJobWorkMonitoringReport', 'Job Work Monitoring Report', 'Sub Contracting Report', 3, 'R', 79, 6, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmJobWorkMonitoringReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmJobWorkRegister', 'Job Work Register', 'Sub Contracting Report', 3, 'R', 80, 7, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmJobWorkRegister.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmJV', 'JV Entry', 'Transaction', 1, 'T', 1, 1, '12', 'default.png', '5', 1, '1', '1', 'NO', '1', 'frmJV.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmJVreport', 'JV Report', 'Reports', 6, 'R', 70, 2, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmJVReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmLetterMaster', 'Letter Master', 'Master', 10, 'M', 10, 10, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmLetterMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmLetterProcessing', 'Letter Processing', 'Processing', 3, 'P', 2, 2, '1', 'default.png', '5', 1, '1', '1', 'YES', 'NO', 'frmLetterProcessing.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmLocationMaster', 'Location Master', 'Master', 7, 'M', 8, 6, '1', 'Location-Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmLocationMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmLocationWiseProductSlip', 'LocationWise Product Slip', 'Stores', 9, 'R', 41, 19, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmLocationWiseProductSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmLockerMaster', 'Locker Master', 'Master', 1, 'M', 1, 13, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmLockerMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmMaterialIssueRegisterReport', 'Material Issue Register Report', 'Stores', 9, 'R', 81, 35, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmMaterialIssueRegisterReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmMaterialIssueSlip', 'Material Issue Slip', 'Stores', 9, 'R', 34, 2, '1', 'Material-Issue-Slip.png', '1', 1, '1', '1', 'NO', 'NA', 'frmMaterialIssueSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmMaterialReq', 'Material Requisition', 'Cost Center', 1, 'T', 9, 1, '1', 'Requisition-Form.png', '1', 1, '1', '1', 'YES', 'YES', 'frmMaterialReq.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Yes', NULL), "
				+ " ('frmMaterialReqSlip', 'Material Requisition Slip', 'Cost Center\\r\\n', 9, 'R', 37, 5, '1', 'Requisition-Slip.png', '1', 1, '1', '1', 'NO', 'NO', 'frmMaterialReqSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmMaterialReturn', 'Material Return', 'Cost Center', 1, 'T', 10, 2, '1', 'default.png', '1', 1, '1', '1', 'YES', 'YES', 'frmMaterialReturn.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Yes', NULL), "
				+ " ('frmMaterialReturnDetail', 'Material Return Slip', 'Cost Center\\r\\n', 7, 'R', 54, 33, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmMaterialReturnDetail.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmMemberPhoto', 'Member Photo', 'Master', 1, 'M', 1, 14, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmMemberPhoto.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmMIS', 'Material Issue Slip', 'Store', 2, 'T', 11, 1, '1', 'Material-Issue-Slip.png', '1', 1, '1', '1', 'YES', 'YES', 'frmMIS.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Yes', NULL), "
				+ " ('frmMISSummaryReport', 'MIS Summary Report', 'Stores', 9, 'R', 84, 36, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmMISSummaryReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmMonthEnd', 'Month End', 'Tools', 8, 'L', 2, 2, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmMonthEnd.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmNarrationMaster', 'Narration Master', 'Master', 1, 'M', 6, 6, '6', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmNarrationMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmNonMovingItemsReport', 'Non Moving Items Report', 'Stores', 7, 'R', 48, 26, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmNonMovingItemsReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmOpeningStock', 'Opening Stock', 'Store', 2, 'T', 12, 2, '1', 'Opening-Stocks.png', '1', 1, '1', '1', 'NO', 'YES', 'frmOpeningStock.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Yes', NULL), "
				+ " ('frmOpeningStockSlip', 'Opening Stock Slip', 'Stores', 7, 'R', 50, 28, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmOpeningStockSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmParameterSetup', 'Parameter Setup', 'Setup', 3, 'S', 1, 1, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmParameterSetup.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmPayment', 'Payment', 'Transaction', 1, 'T', 1, 1, '12', 'default.png', '5', 1, '1', '1', 'NO', '1', 'frmPayment.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmPaymentReport', 'Payment Report', 'Reports', 6, 'R', 72, 4, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmPaymentReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmPendingDocFlash', 'Pending Document Flash', 'Tools', 8, 'L', 47, 25, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmPendingDocFlash.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmPendingNonStkMIS', 'Pending NonStkable MIS', 'Tools', 8, 'L', 48, 26, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmPendingNonStkMIS.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmPhysicalStkPosting', 'Physical Stk Posting', 'Store', 2, 'T', 13, 3, '1', 'Physical-Stock-Posting.png', '1', 1, '1', '1', 'NO', 'YES', 'frmPhysicalStkPosting.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Yes', NULL), "
				+ " ('frmPhysicalStockPostingSlip', 'Physical Stock Posting Slip', 'Stores', 9, 'R', 10, 2, '1', 'Physical-Stock-Posting.png', '1', 1, '1', '1', 'NA', 'NA', 'frmPhysicalStockPostingSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmPOSLinkUp', 'POS Link Up', 'Tools', 8, 'L', 1, 1, '12', 'Attribute-Master.png', '1', 1, '1', '1', 'NO', '1', 'frmPOSLinkUp.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmPOSSalesDtl', 'POS Sales', 'Tools', 8, 'L', 1, 1, '12', 'Attribute-Master.png', '1', 1, '1', '1', 'NO', '1', 'frmPOSSalesDtl.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmProcessMaster', 'Process Master', 'Master', 8, 'M', 69, 17, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmProcessMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmProduction', 'Material Production', 'Production', 6, 'T', 14, 3, '1', 'Productions.png', '1', 1, '1', '1', 'NO', 'YES', 'frmProduction.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), ";
		if (strIndustryType.equalsIgnoreCase("Manufacture")) {
			sql += " ('frmProductionOrder', 'Production Order', 'Production', 6, 'T', 15, 1, '1', 'Production-Order.png', '1', 1, '1', '1', 'YES', 'YES', 'frmProductionOrder.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), ";
		}

		else {
			sql += " ('frmProductionOrder', 'Meal Planing ', 'Production', 6, 'T', 15, 1, '1', 'Production-Order.png', '1', 1, '1', '1', 'YES', 'YES', 'frmProductionOrder.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), ";
		}

		sql += " ('frmProductionOrderSlip', 'Meal Planing Slip', 'Production\\r\\n', 9, 'R', 38, 9, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmProductionOrderSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmProductList', 'Product List', 'Listing', 9, 'R', 43, 21, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmProductList.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmProductMaster', 'Product Master', 'Master', 8, 'M', 16, 7, '1', 'Product-Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmProductMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmProductwiseSupplierwise', 'Productwise Supplierwise', 'Receiving Reports', 9, 'R', 44, 22, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmProductwiseSupplierwise.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmProdWiseSuppRateHis', 'Product Wise Supp Rate Histroy Report', 'Receiving Reports', 9, 'R', 40, 19, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmProdWiseSuppRateHis.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmPropertyMaster', 'Property Master', 'Master', 7, 'M', 17, 8, '1', 'Property-Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmPropertyMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmPurchaseIndent', 'Purchase Indent', 'Store', 2, 'T', 19, 4, '1', 'Purchase-Indent.png', '1', 1, '1', '1', 'YES', 'YES', 'frmPurchaseIndent.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Yes', NULL), "
				+ " ('frmPurchaseIndentSlip', 'Purchase Indent Slip', 'Purchases', 9, 'R', 35, 3, '1', 'Purchase-Indent-Slip.png', '1', 1, '1', '1', 'NO', 'NO', 'frmPurchaseIndentSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmPurchaseOrder', 'Purchase Order', 'Purchase', 3, 'T', 20, 1, '1', 'Purchase-Order.png', '1', 1, '1', '1', 'YES', 'YES', 'frmPurchaseOrder.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Yes', NULL), "
				+ " ('frmPurchaseOrderSlip', 'Purchase Order Slip', 'Purchases', 9, 'R', 36, 4, '1', 'Purchase-Order-Slip.png', '1', 1, '1', '1', 'NO', 'No', 'frmPurchaseOrderSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmPurchaseReturn', 'Purchase Return', 'Purchase', 3, 'T', 21, 2, '1', 'Purchase-Return.png', '1', 1, '1', '1', 'YES', 'YES', 'frmPurchaseReturn.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Yes', NULL), "
				+ " ('frmPurchaseReturnSlip', 'Purchase Return Slip', 'Purchases', 7, 'R', 53, 32, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmPurchaseReturnSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmRateContract', 'Rate Contract', 'Purchase', 3, 'T', 22, 3, '1', 'Rate-Contract.png', '1', 1, '1', '1', 'NO', 'YES', 'frmRateContract.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmReasonMaster', 'Reason Master', 'Master', 8, 'M', 22, 10, '1', 'Reason-Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmReasonMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmReceipt', 'Receipt', 'Transaction', 1, 'T', 1, 1, '12', 'default.png', '5', 1, '1', '1', 'NO', '1', 'frmReceipt.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmReceiptissueConsolidated', 'Receipt Issue Consolidated', 'Stores', 9, 'R', 52, 32, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmReceiptIssueConsolidated.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmReceiptRegister', 'Receipt Register Report', 'Receiving Reports', 9, 'R', 46, 24, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmReceiptRegister.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmRecipes', 'Recipes List', 'Listing', 9, 'R', 39, 10, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmRecipesList.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmReciptReport', 'Recipt Report', 'Reports', 6, 'R', 71, 3, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmReciptReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmReorderLevelReport', 'Reorder Level Report', 'Stores', 7, 'R', 47, 25, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmReorderLevelReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmReorderLevelwise', 'Locationwise Productwise Reorder', 'Stores', 9, 'R', 45, 23, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmReorderLevelwise.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmSalesOrder', 'Sales Order', 'Sales', 2, 'T', 51, 1, '1', 'default.png', '6', 1, '1', '1', 'YES', 'YES', 'frmSalesOrder.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmSalesOrderBOM', 'Sales Order BOM', 'Sales', 2, 'T', 55, 2, '1', 'default.png', '6', 1, '1', '1', 'NO', 'Yes', 'frmSalesOrderBOM.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmSalesOrderList', 'Sales Order List', 'Reports', 3, 'R', 61, 2, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmSalesOrderList.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmSalesOrderSlip', 'Sales Order Slip', 'Reports', 3, 'R', 60, 3, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmSalesOrderSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmSalesOrderStatus', 'Sales Order Status', 'Sales', 2, 'T', 68, 9, '1', 'default.png', '6', 1, '1', '1', 'NO', 'Yes', 'frmSalesOrderStatus.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmSanctionAutherityMaster', 'Sanction Autherity Master', 'Master', 8, 'M', 8, 8, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmSanctionAutherityMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmScrapGenerated', 'Scrap Generated', 'Sub Contracting Report', 3, 'R', 82, 8, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmScrapGenerated.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmSecurityShell', 'Security Shell', 'Master', 7, 'M', 23, 11, '1', 'Security-Shell.png', '1', 1, '1', '1', 'NO', 'YES', 'frmSecurityShell.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmSetup', 'Property Setup', 'Tools', 8, 'L', 24, 12, '1', 'Setup.png', '1', 1, '1', '1', 'NO', 'YES', 'frmSetup.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmSlowMovingItemsReport', 'Slow Moving Items Report', 'Stores', 7, 'R', 49, 27, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmSlowMovingItemsReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmStkVarianceFlash', 'Stock Variance Flash', 'Tools', 8, 'L', 50, 28, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmStkVarianceFlash.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmStockAdjustment', 'Stock Adjustment', 'Store', 2, 'T', 25, 5, '1', 'Stock-Adjustment.png', '1', 1, '1', '1', 'NO', 'YES', 'frmStockAdjustment.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Yes', NULL), "
				+ " ('frmStockAdjustmentFlash', 'Stock Adjustment Flash', 'Tools', 8, 'L', 25, 13, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmStockAdjustmentFlash.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmStockAdjustmentSlip', 'Stock Adjustment Slip', 'Stores', 9, 'R', 33, 1, '1', 'Stock-Adjustment-Slip.png', '1', 1, '1', '1', 'NO', 'NA', 'frmStockAdjustmentSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmStockFlash', 'Stock Flash', 'Tools', 8, 'L', 10, 10, '1', 'Stocks-Flash.png', '1', 1, '1', '1', 'NO', 'NO', 'frmStockFlash.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmStockLedger', 'Stock Ledger', 'Tools', 8, 'L', 10, 12, '1', 'Stock-Ledger.png', '1', 1, '1', '1', 'NO', 'NO', 'frmStockLedger.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				// +" ('frmStockLedgerReportCRM', 'Stock Ledger', 'SuB Contracting Report', 3, 'R', 83, 8, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmStockLedgerReportCRM.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmStockTransfer', 'Stock Transfer', 'Cost Center', 1, 'T', 26, 3, '1', 'Stock-Transfer.png', '1', 1, '1', '1', 'NO', 'YES', 'frmStockTransfer.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Yes', NULL), "
				+ " ('frmStockTransferSlip', 'Stock Transfer Slip', 'Stores', 9, 'R', 37, 6, '1', 'Stock-Transfer-Slip.png', '1', 1, '1', '1', 'NO', 'NO', 'frmStockTransferSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmStructureUpdate', 'Structure Update', 'Tools', 8, 'L', 10, 2, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmStructureUpdate.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmSubCategoryMaster', 'Sub Category Master', 'Master', 1, 'M', 1, 11, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmSubCategoryMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmSubContractorGRN', 'Sub Contractor GRN', 'Sales', 2, 'T', 66, 7, '1', 'default.png', '6', 1, '1', '1', 'NO', 'Yes', 'frmSubContractorGRN.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmSubContractorGRNSlip', 'Sub-Contractor GRN Slip', 'Sub contracting Report', 3, 'R', 65, 7, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmSubContractorGRNSlip.html', '(NULL)', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmSubContractorMaster', 'Sub Contractor Master', 'Master', 1, 'M', 58, 2, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmSubContractorMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmSubGroupMaster', 'Sub Group Master', 'Master', 8, 'M', 27, 13, '1', 'Sub-Group-Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmSubGroupMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmSundryDebtorMaster', 'Sundry Debtor Master', 'Master', 9, 'M', 9, 9, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmSundryDebtorMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmSupplierMaster', 'Supplier Master', 'Master', 8, 'M', 28, 14, '1', 'Supplier-Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmSupplierMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmTaxMaster', 'Tax Master', 'Master', 8, 'M', 29, 15, '1', 'Tax-Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmTaxMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmTCMaster', 'TC Master', 'Master', 8, 'M', 34, 18, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NA', 'frmTCMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmUDReportCategoryMaster', 'UD Report Category Master', 'Master', 8, 'M', 1, 1, '12', 'Attribute-Master.png', '1', 1, '1', '1', 'NO', '1', 'frmUDReportCategoryMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmUOMMaster', 'UOM Master', 'Master', 8, 'M', 35, 19, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmUOMMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmUserDefinedReport', 'User Defined Report', 'Tools', 8, 'L', 1, 1, '12', 'Attribute-Master.png', '1', 1, '1', '1', 'NO', '1', 'frmUserDefinedReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmUserDefinedReportView', 'User Defined Report View', 'Reports', 7, 'R', 25, 25, '12', 'Attribute-Master.png', '1', 1, '1', '1', 'NO', '1', 'frmUserDefinedReportView.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmUserMaster', 'User Master', 'Master', 7, 'M', 31, 17, '1', 'User-Management.png', '1', 1, '1', '1', 'NO', 'YES', 'frmUserMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmWebBooksAccountMaster', 'Account Master', 'Master', 1, 'M', 1, 1, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmWebBooksAccountMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmWebBooksDeleteTransaction', 'Delete Transaction', 'Transaction', 1, 'T', 1, 1, '12', 'default.png', '5', 1, '1', '1', 'NO', '1', 'frmWebBooksDeleteTransaction.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmWebClubDependentMaster', 'Dependent Master', 'Master', 1, 'M', 1, 2, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmDependentMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmWebClubGeneralMaster', 'General Master', 'Master', 1, 'M', 1, 9, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmGeneralMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmWebClubGroupMaster', 'Group Master', 'Master', 1, 'M', 1, 7, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmWebClubGroupMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmWebClubMemberCategoryMaster', 'Member Category Master', 'Master', 1, 'M', 1, 3, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmMemberCategoryMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmWebClubMemberHistory', 'Member History', 'Master', 1, 'M', 1, 4, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmMemberHistory.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmWebClubMemberPreProfile', 'Member Pre-Profile', 'Master', 1, 'M', 1, 5, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmMemberPreProfile.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmWebClubMemberProfile', 'Member Profile', 'Master', 1, 'M', 1, 1, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmMemberProfile.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmWebClubMembershipFormGenration', 'Membership Form Genration', 'Master', 1, 'M', 1, 6, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmMembershipFormGenration.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmWebClubPersonMaster', 'Person Master', 'Master', 1, 'M', 1, 8, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmWebClubPersonMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmWhatIfAnalysis', 'What If Analysis Tool', 'Tools', 8, 'L', 10, 2, '1', 'What-If-Analysis-Tool.png', '1', 1, '1', '1', 'NO', 'NO', 'frmWhatIfAnalysis.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmWorkOrder', 'Work Order', 'Production', 6, 'T', 32, 2, '1', 'Work-Order.png', '1', 1, '1', '1', 'YES', 'YES', 'frmWorkOrder.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('Listing', 'Listing', 'Reports', 7, 'O', 1, 1, '1', 'default.png', '1', 1, '1', '1', 'NA', 'NA', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('Master', 'Master', 'Tools', 8, 'O', 1, 1, '1', 'default.png', '1', 1, '1', '1', 'No', 'No', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('Production\\r\\n', 'Production\\r\\n', 'Reports', 7, 'O', 1, 1, '1', 'default.png', '1', 1, '1', '1', 'NA', 'NA', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('Purchases', 'Purchases', 'Reports', 7, 'O', 1, 1, '1', 'default.png', '1', 1, '1', '1', 'NA', 'NA', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('Receiving Reports', 'Receiving Reports', 'Reports', 7, 'O', 1, 1, '1', 'default.png', '1', 1, '1', '1', 'NA', 'NA', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('Stores', 'Stores', 'Reports', 7, 'O', 1, 1, '1', 'default.png', '1', 1, '1', '1', 'NA', 'NA', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('Sub Contracting Report', 'Sub Contracting Report', 'Reports', 3, 'O', 1, 1, '1', 'default.png', '6', 1, '1', '1', 'No', 'No', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),  "
				+ " ('frmDatabaseDataImport', 'Data Import', 'Tools', 8, 'L', 86, 1, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmDatabaseDataImport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmRecipeCostiong', 'Recipes Costing', 'Listing', 9, 'R', 87, 11, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmRecipeCosting.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmLossCalculationReport', 'Loss Calculation Report', 'Listing', 9, 'R', 88, 13, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmLossCalculationReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmMasterList', 'Master List', 'Listing', 9, 'R', 44, 22, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmMasterList.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseCategoryWiseSale', 'Category Wise Sale', 'Reports', 3, 'R', 187, 11, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseCategoryWiseSale.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmBrandWiseClosingReport', 'Brand WiseClosing Report', 'Reports', 3, 'R', 188, 12, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmBrandWiseClosingReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ,"
				+ " ('frmExciseUserMaster', 'Excise User Master', 'Master', 1, 'M', 25, 10, '2', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseUserMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseSecurityShell','Excise Security Shell', 'Tools', 8, 'T', 11, 11, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseSecurityShell.html',NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmTallyLinkUp', 'Tally Link Up', 'Tools', 8, 'L', 189, 23, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmTallyLinkUp.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmDocumentListingFlashReport', 'Document Listing Flash Report', 'Tools', 8, 'L', 190, 24, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmDocumentListingFlashReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmProductPurchaseReciept', 'Product Purchase Reciept', 'Receiving Reports', 9, 'R', 191, 25, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmProductPurchaseReciept.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExciseMonthEnd', 'Excise Month End', 'Tools', 8, 'T', 12, 12, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseMonthEnd.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExcisePropertyMaster', 'Excise Property Master', 'Master', 1, 'M', 26, 11, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExcisePropertyMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "

				+ " ('frmCountryMaster', 'Country Master', 'Master', 8, 'M', 18, 102, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmWSCountryMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmStateMaster', 'State Master', 'Master', 8, 'M', 19, 103, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmWSStateMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmCityMaster', 'City Master', 'Master', 8, 'M', 20, 104, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmWSCityMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmVehicleMaster', 'Vehicle Master', 'Master', 8, 'M', 21, 105, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmVehicleMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmRouteMaster', 'Route Master', 'Master', 8, 'M', 22, 106, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmRouteMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmVehicleRouteMaster', 'Vehicle Route Master', 'Master', 8, 'M', 23, 105, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmVehicleRouteMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmAvgConsumptionReport', 'Avg Consumption Report', 'Stores', 9, 'R', 85, 36, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmAvgConsumptionReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),  "
				+ " ('frmStockTransferFlash', 'Stock Transfer Flash', 'Tools', 8, 'L', 86, 86, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmStockTransferFlash.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),  "
				+ " ('frmTransporterMaster', 'Transporter Master', 'Master', 8, 'M', 22, 106, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmTransporterMaster.html' , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),  "
				+ " ('frmExportTallyFile', 'Export Tally File', 'Tools', 8, 'L', 87, 87, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmExportTallyFile.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),  "
				+ " ('frmPurchaseRegisterReport', 'Purchase Register Report', 'Purchases', 7, 'R', 54, 33, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmPurchaseRegisterReport.html' , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('Excise Report', 'Excise Report', 'Reports', 7, 'O', 1, 1, '1', 'default.png', '1', 1, '1', '1', 'NA', 'NA', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmRG-23A-Part-I', 'From RG-23A-Part-I', 'Excise Report', 7, 'R', 1, 108, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmRG-23A-Part-I.html' , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmARLinkUp', 'AR Link Up', 'Tools', '8', 'L', '190', '24', '1', 'default.png', '1', '1', '1', '1', 'NO', 'NO', 'frmARLinkUp.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmSaleVSPurchase', 'Sale vs Purchase', 'Stores', 7, 'R', 1, 1, '1', 'default.png', '1', 1, '1', '1','NA', 'NA', 'frmSaleVSPurchase.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmStockAdjustmentMonthly', 'Stock Adjustment Monthly', 'Stores', '9', 'R', '33', '1', '1', 'Stock-Adjustment-Slip.png', '1', '1', '1', '1', 'NO', 'NO', 'frmStockAdjustmentMonthly.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmGrnAndInvoiceComparision', 'Comparision GRN and  Invoice', 'Stores', '8', 'R', '1', '1', '1', 'default.png', '1', '1', '1', '1', 'NO', 'NO', 'frmGrnAndInvoiceComparision.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmBulkProductUpdate', 'Bulk Product Update', 'Master', '8', 'M', '23', '108', '1', 'Product-Master.png', '1', '1', '1', '1', 'NO', 'YES', 'frmBulkProductUpdate.html' , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmFundFlow', 'Fund Flow Report', 'Tools', '8', 'L', '87', '88', '1', 'default.png', '1', '1', '1', '1', 'NO', 'NO', 'frmFundFlowReport.html' , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmTransactionFlash', 'Transaction Flash', 'Tools', '8', 'L', '87', '89', '1', 'default.png', '1', '1', '1', '1', 'NO', 'NO', 'frmTransactionFlash.html' , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmManufactureMaster', 'Manufacture Master', 'Master', 1, 'M', 61, 2, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmManufactureMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmProductWiseGRNReport', 'Loc.Cat. Product Wise GRN Report', 'Receiving Reports', 9, 'R', 192, 26, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmProductWiseGRNReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmMISLocationWiseCategoryWiseReport', 'MIS LocationWise CategroyWise Report', 'Stores', '9', 'R', '86', '37', '1', 'default.png', '1', '1', '1', '1', 'NO', 'NO', 'frmMISLocationWiseCategoryWiseReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmSupplierWiseProductGRNReport', 'Supp.Cat. Product Wise GRN Report', 'Receiving Reports', 9, 'R', 193, 27, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmSupplierWiseProductGRNReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ,"
				+ " ('frmTransactionListing', 'Transaction Listing', 'Tools', '8', 'L', '88', '90', '1', 'default.png', '1', '1', '1', '1', 'NO', 'NO', 'frmTransactionListing.html' , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmCurrencyMaster', 'Currency Master', 'Master', '8', 'M', '23', '106', '1', 'default.png', '1', '1', '1', '1', 'NO', 'NO', 'frmCurrencyMaster.html' , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmMISLocationWiseReport', 'MIS LocationWise  Report', 'Stores', '9', 'R', '86', '37', '1', 'default.png', '1', '1', '1', '1', 'NO', 'NO', 'frmMISLocationWiseReport.html' , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				// for Excise structure update form show in excise module; //
				// START shown those form which is needed///
				// +" ('frmExciseStructureUpdate', 'Excise Structure Update', 'Tools', 4, 'L', 15, 4, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseStructureUpdate.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmPendingRecipe', 'Pending Recipe List', 'Listing', 9, 'R', 45, 23, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmPendingRecipe.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmStandardRequisition', 'Standard Requisition', 'Cost Center', '1', 'T', '27', '3', '1', 'default.png', '1', '1', '1', '1', 'NO', 'YES', 'frmStandardRequisition.html',  NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmProfitLossWebBook', 'Profit Loss Report', 'Reports', '6', 'R', '10', '10', '1', 'default.png', '5', '1', '1', '1', 'NO', 'NO', 'frmProfitLossWebBook.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ";

		funExecuteQuery(sql);

		sql = " INSERT INTO `tbltreemast` (`strFormName`, `strFormDesc`, `strRootNode`, `intRootIndex`, `strType`, `intFormKey`, `intFormNo`, `strImgSrc`, `strImgName`, `strModule`, `strTemp`, `strActFile`, `strHelpFile`, `strProcessForm`, `strAutorisationForm`, `strRequestMapping`, `strAdd`, `strAuthorise`, `strDelete`, `strDeliveryNote`, `strDirect`, `strEdit`, `strGRN`, `strGrant`, `strMinimumLevel`, `strOpeningStock`, `strPrint`, `strProductionOrder`, `strProject`, `strPurchaseIndent`, `strPurchaseOrder`, `strPurchaseReturn`, `strRateContractor`, `strRequisition`, `strSalesOrder`, `strSalesProjection`, `strSalesReturn`, `strServiceOrder`, `strSubContractorGRN`, `strView`, `strWorkOrder`, `strAuditForm`, `strMIS`, `strInvoice`, `strDeliverySchedule`) VALUES "
				+ " ('frmInvoiceProfitReport', 'Invoice Profit', 'Reports', 3, 'R', 62, 5, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmInvoiceProfitReport.html' , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmSalesReturnSlip', 'Sales Return Slip', 'Reports', '3', 'R', '89', '7', '1', 'default.png', '6', '1', '1', '1', 'NO', 'NO', 'frmSalesReturnSlip.html' , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmDeliverySchedule', 'Delivery Schedule', 'Purchase', 3, 'T', 23, 3, '1', 'default.png', '1', 1, '1', '1', 'NO', 'YES', 'frmDeliverySchedule.html' , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ,"
				+ " ('frmLinkLocToOtherPropLoc', 'Link Locations ', 'Tools', '8', 'L', '10', '2', '1', 'default.png', '1', '1', '1', '1', 'NO', 'NO', 'frmLinkLocToOtherPropLoc.html' , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmSessionMaster', 'Session Master', 'Master', '8', 'M', '24', '108', '1', 'default.png', '1', '1', '1', '1', 'NO', 'NO', 'frmSessionMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ,"
				+ " ('frmBudgetFlash', 'Budget Flash', 'Tools', '8', 'L', '89', '91', '1', 'default.png', '1', '1', '1', '1', 'NO', 'NO', 'frmBudgetFlash.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ "  ('frmBudgetMaster', 'Budget Master', 'Master', 8, 'M', 24, 109, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmBudgetMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ,"
		        + " ('frmMISMonthProdWiseStkFlash', 'MIS Flash', 'Tools', '8', 'L', '90', '92', '1', 'default.png', '1', '1', '1', '1', 'NO', 'NO', 'frmMISMonthProdWiseStkFlash.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ,"
		        + " ('frmFoodCostPerConsumption', 'Food Cost(As Per Consumption)', 'Stores', '9', 'R', '86', '37', '1', 'default.png', '1', '1', '1', '1', 'NO', 'NO', 'frmFoodCostPerConsumption.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		        + " ('frmWebStockRepostingJV', 'Reposting JV', 'Tools', '8', 'L', '91', '93', '1', 'default.png', '1', '1', '1', '1', 'NO', 'NO', 'frmWebStockRepostingJV.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmStockReq', 'Stock Requisition', 'Cost Center', 1, 'T', 9, 1, '1', 'Requisition-Form.png', '1', 1, '1', '1', 'YES', 'YES', 'frmStockReq.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Yes', NULL,NULL,NULL), "
				+ " ('frmStockReqSlip', 'Stock Requisition Slip', 'Cost Center', 9, 'R', 37, 5, '1', 'Requisition-Slip.png', '1', 1, '1', '1', 'NO', 'NO', 'frmStockReqSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,NULL,NULL) ";
		funExecuteQuery(sql);
		}
		
		// //--------------------END----------------------------/////

		funExecuteQuery(sql);

		/*----------------WebStock Forms END---------------------------*/

		/*----------------CRM Forms Only---------------------------*/

		sql = " CREATE TABLE IF NOT EXISTS `tbldeliverychallandtl` ( `strDCCode` varchar(15) NOT NULL, " + "  `strProdCode` varchar(10) NOT NULL DEFAULT '', " + "  `dblQty` decimal(18,2) NOT NULL DEFAULT '0.00', " + " `dblPrice` decimal(18,2) NOT NULL DEFAULT '0.00', " + "  `dblWeight` double NOT NULL DEFAULT '0',  " + " `strProdType` varchar(255) NOT NULL DEFAULT '', "
				+ "  `strPktNo` varchar(50) NOT NULL DEFAULT '',  " + " `strRemarks` varchar(250) NOT NULL DEFAULT '',  " + " `intindex` bigint(20) NOT NULL DEFAULT '0',  " + " `strInvoiceable` varchar(1) NOT NULL DEFAULT 'N', " + "  `strSerialNo` varchar(200) NOT NULL, " + "  `strClientCode` varchar(20) NOT NULL, " + "  `intId` bigint(20) NOT NULL AUTO_INCREMENT, " + "   PRIMARY KEY (`intId`) )"
				+ " ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1; ";
		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tbldeliverychallanhd` ( " + "  `strDCCode` varchar(15) NOT NULL,  `intid` bigint(20) NOT NULL, " + " `dteDCDate` date NOT NULL DEFAULT '1900-01-01',  `strAgainst` varchar(20) NOT NULL DEFAULT '', " + " `strSOCode` varchar(15) NOT NULL DEFAULT '',  `strCustCode` varchar(10) NOT NULL DEFAULT '', " + " `strPONo` varchar(50) NOT NULL DEFAULT '', "
				+ " `strNarration` varchar(300) NOT NULL DEFAULT '',  `strPackNo` varchar(20) NOT NULL DEFAULT '', " + " `strLocCode` varchar(15) NOT NULL DEFAULT '',  `strVehNo` varchar(20) NOT NULL DEFAULT '', " + " `strMInBy` varchar(50) NOT NULL DEFAULT '',  `strTimeInOut` varchar(10) NOT NULL DEFAULT '', "
				+ " `strUserCreated` varchar(10) NOT NULL,  `dteCreatedDate` date NOT NULL DEFAULT '1900-01-01', " + " `strUserModified` varchar(10) NOT NULL,  `dteLastModified` date NOT NULL DEFAULT '1900-01-01',  " + " `strAuthorise` varchar(2) NOT NULL DEFAULT 'N',  `strDktNo` varchar(50) NOT NULL DEFAULT '', "
				+ "  `strSAdd1` varchar(50) NOT NULL DEFAULT '',  `strSAdd2` varchar(50) NOT NULL DEFAULT '', " + " `strSCity` varchar(50) NOT NULL DEFAULT '',  `strSState` varchar(50) NOT NULL DEFAULT '', " + " `strSCtry` varchar(50) NOT NULL DEFAULT '',  `strSPin` varchar(50) NOT NULL DEFAULT '', " + " `strDCNo` varchar(50) NOT NULL DEFAULT '',  `strReaCode` varchar(15) NOT NULL DEFAULT '', "
				+ " `strSerialNo` varchar(50) NOT NULL DEFAULT '',  `strWarrPeriod` varchar(50) NOT NULL DEFAULT '', " + " `strWarraValidity` varchar(50) NOT NULL DEFAULT '',  `strClientCode` varchar(20) NOT NULL, " + "  PRIMARY KEY (`strDCCode`,`strClientCode`)) " + " ENGINE=InnoDB DEFAULT CHARSET=latin1; ";
		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tbldeliverynotedtl` ( " + " `intId` bigint(20) NOT NULL AUTO_INCREMENT,  `strDNCode` varchar(50) NOT NULL, " + " `strProdCode` varchar(50) NOT NULL,  `strProcessCode` varchar(50) NOT NULL DEFAULT '', " + " `dblQty` decimal(18,4) NOT NULL DEFAULT '0.0000',  `dblWeight` decimal(18,4) NOT NULL DEFAULT '0.0000', "
				+ " `dblRate` decimal(18,4) NOT NULL DEFAULT '0.0000',  `strProdChar` varchar(100) NOT NULL DEFAULT '', " + " `strRemarks` varchar(1000) NOT NULL DEFAULT '', " + " `strClientCode` varchar(50) NOT NULL,  PRIMARY KEY (`intId`)) " + "ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1; ";
		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tbldeliverynotehd` ( " + " `strDNCode` varchar(50) NOT NULL,  `intId` bigint(20) NOT NULL DEFAULT '0', " + " `strTypeAgainst` varchar(10) NOT NULL DEFAULT '',  `strTypeCode` varchar(50) NOT NULL DEFAULT 'Enquiry', " + " `dteDNDate` date NOT NULL,  `strDNType` varchar(50) NOT NULL DEFAULT 'GRN',"
				+ " `strGRNCode` varchar(50) NOT NULL DEFAULT '', `strJACode` varchar(50) NOT NULL, " + " `strSCCode` varchar(50) NOT NULL,  `strFrom` varchar(50) NOT NULL DEFAULT '', " + " `strLocCode` varchar(50) NOT NULL DEFAULT '',  `strSCAdd1` varchar(50) NOT NULL DEFAULT '', " + " `strSCAdd2` varchar(50) NOT NULL DEFAULT '',  `strSCCity` varchar(50) NOT NULL DEFAULT '',"
				+ "  `strSCState` varchar(50) NOT NULL DEFAULT '',  `strSCCountry` varchar(50) NOT NULL DEFAULT '', " + " `strSCPin` varchar(20) NOT NULL DEFAULT '',  `strFAdd1` varchar(50) NOT NULL DEFAULT '', " + " `strFAdd2` varchar(50) NOT NULL DEFAULT '',  `strFCity` varchar(50) NOT NULL DEFAULT '', "
				+ " `strFState` varchar(50) NOT NULL DEFAULT '',  `strFCountry` varchar(50) NOT NULL DEFAULT '', " + " `strFPin` varchar(20) NOT NULL DEFAULT '',  `dteExpDate` date NOT NULL, " + " `strVehNo` varchar(1000) NOT NULL DEFAULT '',  `strNarration` varchar(200) NOT NULL DEFAULT '', " + " `strMInBy` varchar(50) NOT NULL DEFAULT '',  `strTimeInOut` varchar(1000) NOT NULL DEFAULT '', "
				+ " `dblTotal` decimal(18,4) NOT NULL DEFAULT '0.0000',  `strExpDet` varchar(5) NOT NULL DEFAULT 'No', " + " `strAuthorise` char(5) NOT NULL DEFAULT 'No',  `strUserCreated` varchar(50) NOT NULL, " + " `dteCreatedDate` datetime NOT NULL,  `strUserModified` varchar(10) NOT NULL, " + " `dteLastModified` datetime NOT NULL,  `strClientCode` varchar(50) NOT NULL DEFAULT '',"
				+ "  PRIMARY KEY (`strDNCode`,`strClientCode`)) ENGINE=InnoDB DEFAULT CHARSET=latin1; ";
		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tblsalesorderbom` ( " + " `intId` bigint(20) NOT NULL AUTO_INCREMENT,  `strSOCode` varchar(20) NOT NULL, " + " `strProdCode` varchar(20) NOT NULL,  `strProcessCode` varchar(20) NOT NULL, " + " `strParentCode` varchar(20) NOT NULL,  `strParentProcessCode` varchar(20) NOT NULL, "
				+ " `strChildCode` varchar(20) NOT NULL,  `dblQty` decimal(18,4) NOT NULL DEFAULT '0.0000',  " + "`dblWeight` decimal(18,4) NOT NULL DEFAULT '0.0000',  `intIndex` int(11) NOT NULL DEFAULT '0', " + " `strRemarks` varchar(250) NOT NULL DEFAULT '',  `strUserCreated` varchar(20) NOT NULL," + "  `dteDateCreated` datetime NOT NULL,  `strUserModified` varchar(20) NOT NULL, "
				+ " `dteLastModified` datetime NOT NULL,  `strClientCode` varchar(20) NOT NULL, " + " PRIMARY KEY (`intId`)) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1; ";
		funExecuteQuery(sql);

		sql = "CREATE TABLE IF NOT EXISTS `tblsalesorderdtl` ( " + " `strSOCode` varchar(15) NOT NULL,  `strProdCode` varchar(15) NOT NULL DEFAULT '', " + " `dblQty` decimal(18,2) NOT NULL DEFAULT '0.00',  " + "`dblDiscount` decimal(18,2) NOT NULL DEFAULT '0.00',  `strTaxType` varchar(20) NOT NULL DEFAULT '', "
				+ " `dblTaxableAmt` decimal(18,2) NOT NULL DEFAULT '0.00',  `dblTax` decimal(18,2) NOT NULL DEFAULT '0.00', " + " `dblTaxAmt` decimal(18,2) NOT NULL DEFAULT '0.00',  `dblUnitPrice` decimal(18,2) NOT NULL DEFAULT '0.00'," + "  `dblWeight` decimal(18,2) NOT NULL DEFAULT '0.00',  `strProdChar` varchar(255) NOT NULL DEFAULT '', "
				+ " `strRemarks` varchar(255) NOT NULL DEFAULT '',  `intindex` bigint(20) NOT NULL, " + " `strClientCode` varchar(20) NOT NULL,  `intId` bigint(20) NOT NULL AUTO_INCREMENT," + "  PRIMARY KEY (`intId`)) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;";
		funExecuteQuery(sql);

		sql = "  CREATE TABLE IF NOT EXISTS `tblsalesorderhd` ( " + " `strSOCode` varchar(15) NOT NULL,  `intid` bigint(20) NOT NULL," + "  `dteSODate` date NOT NULL,  `strCustCode` varchar(10) NOT NULL DEFAULT '', " + " `strCustPONo` varchar(50) NOT NULL DEFAULT '',  `strStatus` varchar(20) NOT NULL DEFAULT '', "
				+ " `dteFulmtDate` date NOT NULL DEFAULT '1900-01-01',  `strLocCode` varchar(15) NOT NULL DEFAULT '', " + " `strPayMode` varchar(50) NOT NULL DEFAULT '',  `strBAdd1` varchar(50) NOT NULL DEFAULT '', " + " `strBAdd2` varchar(50) NOT NULL DEFAULT '',  `strBCity` varchar(20) NOT NULL DEFAULT '', "
				+ " `strBState` varchar(20) NOT NULL DEFAULT '',  `strBCountry` varchar(20) NOT NULL DEFAULT '', " + " `strBPin` varchar(15) NOT NULL DEFAULT '',  `strSAdd1` varchar(50) NOT NULL DEFAULT '', " + " `strSAdd2` varchar(50) NOT NULL DEFAULT '',  `strSCity` varchar(20) NOT NULL DEFAULT '',  "
				+ " `strSState` varchar(20) NOT NULL DEFAULT '',  `strSCountry` varchar(20) NOT NULL DEFAULT '',  " + " `strSPin` varchar(15) NOT NULL DEFAULT '',  `dblSubTotal` decimal(18,2) NOT NULL DEFAULT '0.00', " + " `dblDisRate` decimal(18,2) NOT NULL DEFAULT '0.00',  `dblDisAmt` decimal(18,2) NOT NULL DEFAULT '0.00', "
				+ " `dblTaxAmt` decimal(18,2) NOT NULL DEFAULT '0.00',  `dblExtra` decimal(18,2) NOT NULL DEFAULT '0.00', " + " `dblTotal` decimal(18,2) NOT NULL DEFAULT '0.00',  `strNarration` varchar(250) NOT NULL DEFAULT '', " + " `strUserCreated` varchar(10) NOT NULL,  `dteDateCreated` date NOT NULL, " + " `strUserModified` varchar(10) NOT NULL,  `dteLastModified` date NOT NULL, "
				+ " `strBOMFlag` varchar(1) NOT NULL DEFAULT 'N',  `strAuthorise` varchar(1) NOT NULL DEFAULT '', " + " `strImgName` varchar(100) NOT NULL DEFAULT '',  `dteCPODate` date NOT NULL DEFAULT '1900-01-01', " + " `strSysModel` varchar(50) NOT NULL DEFAULT '',  `strCranenModel` varchar(50) NOT NULL DEFAULT '', "
				+ " `strMaxCap` varchar(50) NOT NULL DEFAULT '',  `strWipeRopeDia` varchar(50) NOT NULL DEFAULT '', " + " `strNoFall` varchar(50) NOT NULL DEFAULT '',  `strSuppVolt` varchar(50) NOT NULL DEFAULT '', " + " `strBoomLen` varchar(50) NOT NULL DEFAULT '',  `strCurrency` varchar(10) NOT NULL DEFAULT '', "
				+ " `strReaCode` varchar(15) NOT NULL DEFAULT '',  `strAgainst` varchar(50) NOT NULL DEFAULT '', " + " `strCode` varchar(20) NOT NULL DEFAULT '',  `strWarrPeriod` varchar(50) NOT NULL DEFAULT '', " + " `strWarraValidity` varchar(50) NOT NULL DEFAULT '',  `intwarmonth` bigint(20) NOT NULL, "
				+ " `dblConversion` decimal(18,2) NOT NULL DEFAULT '0.00',  `strCloseSO` varchar(1) NOT NULL DEFAULT 'N', " + " `strClientCode` varchar(20) NOT NULL,  PRIMARY KEY (`strSOCode`)) ENGINE=InnoDB DEFAULT CHARSET=latin1; ";
		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tblscreturndtl` ( " + " `strSRCode` varchar(15) NOT NULL,  `intId` bigint(20) NOT NULL AUTO_INCREMENT, " + " `strProdCode` varchar(10) NOT NULL DEFAULT '',  `dblQty` decimal(18,2) NOT NULL DEFAULT '0.00', " + " `dblQtyRej` decimal(18,2) NOT NULL DEFAULT '0.00',  `dblWeight` decimal(18,2) NOT NULL DEFAULT '0.00', "
				+ " `dblPrice` decimal(18,2) NOT NULL DEFAULT '0.00',  `strProdChar` varchar(50) NOT NULL DEFAULT '', " + " `strDNProdCode` varchar(50) NOT NULL DEFAULT '',  `strDNProdName` varchar(50) NOT NULL DEFAULT '', " + " `strDNProcess` varchar(50) NOT NULL DEFAULT '',  `dblDNQty` decimal(18,2) NOT NULL DEFAULT '0.00', "
				+ " `dblDNWeight` decimal(18,2) NOT NULL DEFAULT '0.00',  `strDNProdChar` varchar(50) NOT NULL DEFAULT '', " + " `dblScrap` decimal(18,2) NOT NULL DEFAULT '0.00',  `strDNCode` varchar(50) NOT NULL DEFAULT '', " + " `dblDCQty` decimal(18,2) NOT NULL DEFAULT '0.00',  `dblExpQty` decimal(18,2) NOT NULL DEFAULT '0.00', "
				+ " `intExpIndex` bigint(20) NOT NULL DEFAULT '1',  `dblDCWt` decimal(18,2) NOT NULL DEFAULT '0.00', " + " `strRemarks` varchar(50) NOT NULL DEFAULT '',  `dblQtyRbl` decimal(18,2) NOT NULL DEFAULT '0.00', " + " `dblRblWt` bigint(20) NOT NULL DEFAULT '0',  `intIndex` bigint(20) NOT NULL DEFAULT '1', " + " `strClientCode` varchar(20) NOT NULL,  PRIMARY KEY (`intId`)) "
				+ " ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;";
		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tblscreturnhd` ( " + "  `strSRCode` varchar(15) NOT NULL,  `strSRNo` varchar(50) NOT NULL DEFAULT ''," + "  `intid` bigint(20) NOT NULL,  `dteSRDate` date NOT NULL,  `strSCCode` varchar(15) NOT NULL, " + " `strSCDNCode` varchar(20) NOT NULL,  `strVerRemark` varchar(50) NOT NULL DEFAULT '', "
				+ " `strDispAction` varchar(255) NOT NULL DEFAULT '',  `strPartDel` varchar(3) NOT NULL DEFAULT '', " + " `strLocCode` varchar(15) NOT NULL DEFAULT '',  `strJWCode` varchar(15) NOT NULL DEFAULT '', " + " `strVehNo` varchar(15) NOT NULL DEFAULT '',  `strMInBy` varchar(50) NOT NULL DEFAULT '', "
				+ " `strTimeInOut` varchar(10) NOT NULL DEFAULT '',  `strAgainst` varchar(50) NOT NULL DEFAULT '', " + " `strNo` varchar(50) NOT NULL DEFAULT '',  `strInRefNo` varchar(50) NOT NULL DEFAULT '', " + " `dblTotQty` decimal(18,2) NOT NULL DEFAULT '0.00',  `dblTotWt` decimal(18,2) NOT NULL DEFAULT '0.00', "
				+ " `dblTotAmt` decimal(18,2) NOT NULL DEFAULT '0.00',  `dteInRefDate` date NOT NULL DEFAULT '1991-01-01', " + " `dteSCDCDate` date NOT NULL DEFAULT '1991-01-01',  `strUserCreated` varchar(15) NOT NULL, " + " `dteCreatedDate` date NOT NULL DEFAULT '1991-01-01',  `strUserModified` varchar(15) NOT NULL, "
				+ " `dteLastModified` date NOT NULL DEFAULT '1991-01-01',  `strAuthorise` varchar(1) NOT NULL DEFAULT 'N', " + " `strClientCode` varchar(20) NOT NULL, " + " PRIMARY KEY (`strSRCode`,`strClientCode`)) ENGINE=InnoDB DEFAULT CHARSET=latin1; ";
		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tbljoborderhd` (   `strJOCode` varchar(20) NOT NULL,  " + " `intId` bigint(20) NOT NULL,  `dteJODate` date NOT NULL, " + " `strSOCode` varchar(20) NOT NULL DEFAULT '',  `strProdCode` varchar(20) NOT NULL, " + " `dblQty` decimal(18,4) NOT NULL DEFAULT '0.0000',  `strParentJOCode` varchar(20) NOT NULL, "
				+ " `strStatus` varchar(30) NOT NULL,  `strUserCreated` varchar(20) NOT NULL,  `dteDateCreated` datetime NOT NULL, " + " `strUserModified` varchar(20) NOT NULL,  `dteLastModified` datetime NOT NULL,  `strClientCode` varchar(20) NOT NULL, " + " `strAuthorise` varchar(5) NOT NULL DEFAULT 'NA', " + " PRIMARY KEY (`strJOCode`,`strClientCode`)) "
				+ " ENGINE=InnoDB DEFAULT CHARSET=latin1; ";
		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tbljoborderallocationdtl` (  `intId` bigint(20) NOT NULL AUTO_INCREMENT, " + " `strJACode` varchar(20) NOT NULL,  `strJOCode` varchar(20) NOT NULL,  `strProdCode` varchar(20) NOT NULL, " + " `strNatureOfProcessing` varchar(50) NOT NULL DEFAULT '',  `dblQty` decimal(18,4) NOT NULL DEFAULT '0.0000', "
				+ " `strProdNo` varchar(50) NOT NULL DEFAULT '',  `strRemarks` varchar(250) NOT NULL DEFAULT '', " + " `dblRate` decimal(18,4) NOT NULL DEFAULT '0.0000',  `intIndex` bigint(20) unsigned NOT NULL DEFAULT '0', " + " `strClientCode` varchar(20) NOT NULL,  PRIMARY KEY (`intId`) )" + "  ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1; ";
		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tbljoborderallocationhd` (  `strJACode` varchar(20) NOT NULL, " + " `intId` bigint(20) NOT NULL,  `strJANo` varchar(50) NOT NULL,  `strSCCode` varchar(20) NOT NULL," + "  `dteJADate` date NOT NULL,  `strRef` varchar(50) NOT NULL DEFAULT '',  `dteRefDate` date NOT NULL, "
				+ " `strDispatchMode` varchar(50) NOT NULL DEFAULT '',  `strPayment` varchar(50) NOT NULL DEFAULT '', " + " `strTaxes` varchar(50) NOT NULL DEFAULT '',  `dbltotQty` decimal(18,4) NOT NULL DEFAULT '0.0000', " + " `strUserCreated` varchar(20) NOT NULL,  `dteDateCreated` datetime NOT NULL,  `strUserModified` varchar(20) NOT NULL, "
				+ " `dteLastModified` datetime NOT NULL,  `strClientCode` varchar(20) NOT NULL, " + " `strAuthorise` char(5) NOT NULL DEFAULT 'false',  PRIMARY KEY (`strJACode`,`strClientCode`))" + " ENGINE=InnoDB DEFAULT CHARSET=latin1; ";
		funExecuteQuery(sql);

		sql = " CREATE TABLE  IF NOT EXISTS  `tbltranschardtl` (`strTransCode` VARCHAR(15) NOT NULL, " + " `strProdCode` VARCHAR(15) NOT NULL, " + "	`strCharCode` VARCHAR(15) NOT NULL," + "	`strSpecf` VARCHAR(15) NOT NULL," + "	`intId` BIGINT NOT NULL AUTO_INCREMENT," + "	PRIMARY KEY (`intId`))  " + " COLLATE='latin1_swedish_ci'ENGINE=InnoDB; ";

		funExecuteQuery(sql);

		sql = " CREATE TABLE  IF NOT EXISTS  `tblinvoicehd` ( " + "	`strInvCode` VARCHAR(15) NOT NULL, " + "	`intid` BIGINT(20) NOT NULL, `dteInvDate` DATE NOT NULL DEFAULT '1900-01-01', " + "	`strAgainst` VARCHAR(20) NOT NULL DEFAULT '', " + "	`strSOCode` VARCHAR(15) NOT NULL DEFAULT '', " + "	`strCustCode` VARCHAR(10) NOT NULL DEFAULT '',  " + "	`strPONo` VARCHAR(50) NOT NULL DEFAULT '', "
				+ "	`strNarration` VARCHAR(300) NOT NULL DEFAULT '',  " + "	`strPackNo` VARCHAR(20) NOT NULL DEFAULT ''," + "	`strLocCode` VARCHAR(15) NOT NULL DEFAULT ''," + "	`strVehNo` VARCHAR(20) NOT NULL DEFAULT '',	" + "`strMInBy` VARCHAR(50) NOT NULL DEFAULT '',	" + "`strTimeInOut` VARCHAR(10) NOT NULL DEFAULT ''," + "	`strUserCreated` VARCHAR(10) NOT NULL,"
				+ "	`dteCreatedDate` DATE NOT NULL DEFAULT '1900-01-01'," + "	`strUserModified` VARCHAR(10) NOT NULL,	" + "`dteLastModified` DATE NOT NULL DEFAULT '1900-01-01'," + "	`strAuthorise` VARCHAR(2) NOT NULL DEFAULT 'N'," + "	`strDktNo` VARCHAR(50) NOT NULL DEFAULT ''," + "	`strSAdd1` VARCHAR(50) NOT NULL DEFAULT ''," + "	`strSAdd2` VARCHAR(50) NOT NULL DEFAULT '',"
				+ "	`strSCity` VARCHAR(50) NOT NULL DEFAULT ''," + "	`strSState` VARCHAR(50) NOT NULL DEFAULT ''," + "	`strSCtry` VARCHAR(50) NOT NULL DEFAULT ''," + "	`strSPin` VARCHAR(50) NOT NULL DEFAULT '',	" + "`strInvNo` VARCHAR(50) NOT NULL DEFAULT ''," + "	`strReaCode` VARCHAR(15) NOT NULL DEFAULT ''," + "	`strSerialNo` VARCHAR(50) NOT NULL DEFAULT '',"
				+ "	`strWarrPeriod` VARCHAR(50) NOT NULL DEFAULT ''," + "	`strWarraValidity` VARCHAR(50) NOT NULL DEFAULT ''," + "	`strClientCode` VARCHAR(20) NOT NULL," + "	PRIMARY KEY (`strInvCode`, `strClientCode`) )" + " COLLATE='latin1_swedish_ci' ENGINE=InnoDB; ";

		funExecuteQuery(sql);

		sql = " CREATE TABLE  IF NOT EXISTS  `tblinvoicedtl` (" + "	`strInvCode` VARCHAR(15) NOT NULL," + "	`strProdCode` VARCHAR(10) NOT NULL DEFAULT '',	" + "`dblQty` DECIMAL(18,2) NOT NULL DEFAULT '0.00'," + "	`dblPrice` DECIMAL(18,2) NOT NULL DEFAULT '0.00'," + "	`dblWeight` DOUBLE NOT NULL DEFAULT '0'," + "	`strProdType` VARCHAR(255) NOT NULL DEFAULT '',"
				+ "	`strPktNo` VARCHAR(50) NOT NULL DEFAULT '',	" + "`strRemarks` VARCHAR(250) NOT NULL DEFAULT ''," + "	`intindex` BIGINT(20) NOT NULL DEFAULT '0'," + "	`strInvoiceable` VARCHAR(1) NOT NULL DEFAULT 'N'," + "	`strSerialNo` VARCHAR(200) NOT NULL,	" + "`strClientCode` VARCHAR(20) NOT NULL," + "	`intId` BIGINT(20) NOT NULL AUTO_INCREMENT, " + "	PRIMARY KEY (`intId`) )"
				+ " COLLATE='latin1_swedish_ci'" + " ENGINE=InnoDB ; ";

		funExecuteQuery(sql);

		sql = "CREATE TABLE  IF NOT EXISTS  `tblinvtaxdtl` ( " + " `intId` BIGINT(20) NOT NULL AUTO_INCREMENT, " + "`strClientCode` VARCHAR(255) NULL DEFAULT NULL, " + "`strInvCode` VARCHAR(255) NOT NULL DEFAULT '', " + "`strTaxAmt` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', " + "`strTaxCode` VARCHAR(255) NOT NULL DEFAULT '', " + "`strTaxDesc` VARCHAR(255) NOT NULL DEFAULT '', "
				+ "`strTaxableAmt` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', " + "PRIMARY KEY (`intId`) " + ") " + "COLLATE='latin1_swedish_ci' " + "ENGINE=InnoDB; ";

		funExecuteQuery(sql);

		sql = "CREATE TABLE IF NOT EXISTS `tblsalesreturnhd` ( " + " `strSRCode` varchar(20) NOT NULL,  " + " `dteSRDate` datetime NOT NULL, " + " `strAgainst` varchar(50) NOT NULL, " + " `strDCCode` varchar(20) NOT NULL, " + " `strCustCode` varchar(10) NOT NULL, " + " `strLocCode` varchar(10) NOT NULL, " + " `dblTotalAmt` decimal(18,2) NOT NULL, " + " `strUserCreated` varchar(10) NOT NULL, "
				+ " `strUserEdited` varchar(10) NOT NULL, " + " `dteDateCreated` datetime NOT NULL, " + " `dteDateEdited` datetime NOT NULL, " + " `strClientCode` varchar(20) NOT NULL, " + " PRIMARY KEY (`strSRCode`,`strClientCode`) " + " ) ENGINE=InnoDB DEFAULT CHARSET=latin1; ";
		funExecuteQuery(sql);

		sql = "CREATE TABLE IF NOT EXISTS `tblsalesreturndtl` ( " + "`strSRCode` varchar(20) NOT NULL, " + "`strProdCode` varchar(10) NOT NULL, " + "`dblQty` decimal(18,2) NOT NULL, " + "`dblPrice` decimal(18,2) NOT NULL, " + "`dblWeight` decimal(18,2) NOT NULL, " + "`strRemarks` varchar(200) NOT NULL DEFAULT '', " + "`strClientCode` varchar(10) NOT NULL "
				+ ") ENGINE=InnoDB DEFAULT CHARSET=latin1; ";
		funExecuteQuery(sql);

		sql = "CREATE TABLE IF NOT EXISTS `tblsalesreturntaxdtl` ( " + " `intId` BIGINT(20) NOT NULL AUTO_INCREMENT, " + " `strClientCode` VARCHAR(255) NULL DEFAULT NULL, " + " `strSRCode` VARCHAR(255) NOT NULL DEFAULT '', " + " `strTaxAmt` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', " + " `strTaxCode` VARCHAR(255) NOT NULL DEFAULT '', " + " `strTaxDesc` VARCHAR(255) NOT NULL DEFAULT '', "
				+ " `strTaxableAmt` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', " + " PRIMARY KEY (`intId`) " + " ) " + " COLLATE='latin1_swedish_ci' " + " ENGINE=InnoDB " + " AUTO_INCREMENT=13 ; ";

		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tblsaleschar` (	" + " `strSOCode` VARCHAR(15) NOT NULL," + "  `strProdCode` VARCHAR(15) NOT NULL," + "	`strCharCode` VARCHAR(15) NOT NULL,	" + " `strCharValue` VARCHAR(255) NOT NULL,	" + " PRIMARY KEY (`strSOCode`, `strProdCode`, `strCharCode`)) " + " COLLATE='latin1_swedish_ci' ENGINE=InnoDB;   ";
		funExecuteQuery(sql);

		sql = "CREATE TABLE IF NOT EXISTS `tblexcisemaster` ( " + " `strExciseCode` VARCHAR(10) NOT NULL, " + " `strExciseChapterNo` VARCHAR(20) NOT NULL DEFAULT '', " + " `strSGCode` VARCHAR(10) NOT NULL DEFAULT '', " + " `dblExcisePercent` DOUBLE(18,2) NOT NULL DEFAULT '0.0', " + " `strCessTax` VARCHAR(10) NOT NULL DEFAULT 'N', " + " `strRank` VARCHAR(10) NOT NULL DEFAULT '', "
				+ " `strDesc` VARCHAR(255) NOT NULL DEFAULT '', " + " `strClientCode` VARCHAR(15) NOT NULL, " + " PRIMARY KEY (`strExciseCode`, `strClientCode`) " + " ) " + " COLLATE='latin1_swedish_ci' " + " ENGINE=InnoDB;";
		funExecuteQuery(sql);

		sql = "CREATE TABLE IF NOT EXISTS `tblinvprodtaxdtl` ( 	`strInvCode` VARCHAR(20) NOT NULL, " + "	`strCustCode` VARCHAR(20) NOT NULL, `strProdCode` VARCHAR(20) NOT NULL, " + "	`strDocNo` VARCHAR(20) NOT NULL, " + "	`dblValue` DECIMAL(18,2) NOT NULL, " + "	`strClientCode` VARCHAR(20) NOT NULL )" + " COLLATE='latin1_swedish_ci' ENGINE=InnoDB ; ";

		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tblinvsalesorderdtl` ( " + "	`strInvCode` VARCHAR(20) NOT NULL,	" + " `strSOCode` VARCHAR(20) NOT NULL,	" + " `dteInvDate` DATETIME NOT NULL," + " `strClientCode` VARCHAR(20) NOT NULL )" + "  COLLATE='latin1_swedish_ci' ENGINE=InnoDB; ";
		funExecuteQuery(sql);

		sql = " CREATE TABLE `tblinvtaxgst` (  	`strInvCode` VARCHAR(15) NOT NULL, " + " 	`strTaxCode` VARCHAR(15) NOT NULL DEFAULT '', " + " 	`strProdCode` VARCHAR(15) NOT NULL DEFAULT '', " + " 	`dblTaxableAmt` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', " + " 	`dblCGSTPer` DECIMAL(18,4) NOT NULL DEFAULT '0.0000',  " + "	`dblCGSTAmt` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', "
				+ "	`dblSGSTPer` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', " + "	`dblSGSTAmt` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', " + "	`dblIGSTCGSTPer` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', " + "	`dblIGSTCGSTAmt` DECIMAL(18,4) NOT NULL DEFAULT '0.0000',  " + "	`dblIGSTSGSTPer` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', " + "	`dblIGSTSGSTAmt` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', "
				+ "	`strClientCode` VARCHAR(20) NOT NULL DEFAULT '', " + "	PRIMARY KEY (`strInvCode`,`strTaxCode`, `strProdCode`, `strClientCode`) ) " + " COLLATE='latin1_swedish_ci' ENGINE=InnoDB ; ";

		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tblinvsettlementdtl` ( " + "	`strInvCode` VARCHAR(15) NOT NULL DEFAULT '',  " + "	`strSettlementCode` VARCHAR(10) NOT NULL, " + "	`dblSettlementAmt` DECIMAL(18,2) NOT NULL, " + "	`dblPaidAmt` DECIMAL(18,2) NULL DEFAULT '0.00', " + "	`strExpiryDate` VARCHAR(15) NOT NULL DEFAULT '', " + "	`strCardName` VARCHAR(50) NOT NULL DEFAULT '0', "
				+ "	`strRemark` VARCHAR(50) NOT NULL DEFAULT '0', " + "	`strClientCode` VARCHAR(10) NOT NULL DEFAULT '0', " + "	`strCustomerCode` VARCHAR(15) NULL DEFAULT NULL, " + "	`dblActualAmt` DECIMAL(18,2) NOT NULL DEFAULT '0.00', " + "	`dblRefundAmt` DECIMAL(18,2) NOT NULL DEFAULT '0.00', " + "	`strGiftVoucherCode` VARCHAR(50) NULL DEFAULT '', "
				+ "	`strDataPostFlag` VARCHAR(1) NOT NULL DEFAULT  'N', " + "	`strFolioNo` VARCHAR(10) NOT NULL DEFAULT '', " + "	`strRoomNo` VARCHAR(10) NOT NULL DEFAULT '', " + "	`dteInvDate` DATE NOT NULL, " + "	PRIMARY KEY (`strInvCode`, `strClientCode`, `dteInvDate`, `strSettlementCode`), " + "  INDEX `strInvCode` (`strInvCode`) ) " + " COLLATE='utf8_general_ci'  ENGINE=InnoDB  ; ";

		funExecuteQuery(sql);
		
		sql="CREATE TABLE `tblsalesordertaxdtl` ( "
		  +" `intId` BIGINT(20) NOT NULL AUTO_INCREMENT, "
		  +" `strClientCode` VARCHAR(255) NULL DEFAULT NULL, "
		  +" `strSOCode` VARCHAR(20) NOT NULL DEFAULT '', "
		  +" `strTaxAmt` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', "
		  +" `strTaxCode` VARCHAR(20) NOT NULL DEFAULT '', "
		  +" `strTaxDesc` VARCHAR(100) NOT NULL DEFAULT '', "
		  +" `strTaxableAmt` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', "
		  +" PRIMARY KEY (`intId`) "
		  +" ) "
		  +" COLLATE='latin1_swedish_ci' "
		  +" ENGINE=InnoDB "
		  +" AUTO_INCREMENT=50; ";
		
		funExecuteQuery(sql);
		
		
		sql="CREATE TABLE `tblpurchasereturntaxdtl` ( "
				+ " `intId` BIGINT(20) NOT NULL AUTO_INCREMENT, "
				+ " `strClientCode` VARCHAR(255) NULL DEFAULT NULL,"
				+ " `strPRCode` VARCHAR(20) NOT NULL DEFAULT '',"
				+ " `strTaxAmt` DECIMAL(18,4) NOT NULL DEFAULT '0.0000',"
				+ " `strTaxCode` VARCHAR(20) NOT NULL DEFAULT '',"
				+ " `strTaxDesc` VARCHAR(100) NOT NULL DEFAULT '',"
				+ " `strTaxableAmt` DECIMAL(18,4) NOT NULL DEFAULT '0.0000',"
				+ " PRIMARY KEY (`intId`)"
				+ " )"
				+ " COLLATE='latin1_swedish_ci'"
				+ " ENGINE=InnoDB"
				+ " AUTO_INCREMENT=57"
				+ " ;";
		funExecuteQuery(sql);
		
		// Indexing of CRM tables

		sql = " ALTER TABLE `tblscreturnhd` CHANGE COLUMN `strVehNo` `strVehNo` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strJWCode` ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblscreturndtl` CHANGE COLUMN `strDNProdName` `strDNProdName` VARCHAR(500) NOT NULL DEFAULT '' AFTER `strDNProdCode` ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblinvoicehd`ADD COLUMN `dblTaxAmt` DOUBLE NOT NULL DEFAULT '0.0' AFTER `strClientCode`,ADD COLUMN `dblTotalAmt` DOUBLE NOT NULL DEFAULT '0.0' AFTER `dblTaxAmt`,ADD COLUMN `dblSubTotalAmt` DOUBLE NOT NULL DEFAULT '0.0' AFTER `dblTotalAmt`; ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblinvoicedtl`ADD COLUMN `dblUnitPrice` DOUBLE NOT NULL DEFAULT '0.0' AFTER `intId`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tbldeliverychallanhd`ADD COLUMN `strCloseDC` CHAR(1) NOT NULL DEFAULT 'N' AFTER `strClientCode`; ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblsalesreturnhd` ADD COLUMN `dblTaxAmt` DOUBLE NOT NULL DEFAULT '0.0' AFTER `strClientCode`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblsalesreturndtl`ADD COLUMN `intId` BIGINT NOT NULL AUTO_INCREMENT AFTER `strClientCode`, ADD PRIMARY KEY (`intId`); ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblproductionorderhd` CHANGE COLUMN `strCode` `strCode` VARCHAR(500) NULL DEFAULT NULL AFTER `strAgainst`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblworkorderhd`	ADD COLUMN `strFromLocCode` VARCHAR(15) NOT NULL DEFAULT '' AFTER `intLevel` ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblworkorderhd`	ADD COLUMN `strToLocCode` VARCHAR(15) NOT NULL DEFAULT '' AFTER `strFromLocCode`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblproductionorderhd`	CHANGE COLUMN `strStatus` `strStatus` VARCHAR(20) NULL DEFAULT 'N' AFTER `dtOPDate`; ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblinvoicehd` CHANGE COLUMN `dteInvDate` `dteInvDate` DATETIME NOT NULL DEFAULT '1900-01-01' AFTER `intid`; ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblinvoicehd` ADD COLUMN `dblDiscount` DOUBLE NOT NULL DEFAULT '0.0' AFTER `dblSubTotalAmt`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblinvoicedtl` ADD COLUMN `dblAssValue` DOUBLE NOT NULL DEFAULT '0.0' AFTER `dblUnitPrice` ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblinvoicehd` ADD COLUMN `dblDiscountAmt` DOUBLE NOT NULL DEFAULT '0.0' AFTER `dblDiscount`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblinvoicedtl` ADD COLUMN `dblBillRate` DOUBLE NOT NULL DEFAULT '0.0' AFTER `dblAssValue`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblprodsuppmaster` ADD COLUMN `dblStandingOrder` DECIMAL(18,2) NOT NULL DEFAULT '0.00' AFTER `dblMargin`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblprodsuppmaster` ADD COLUMN `dblStandardValue` DECIMAL(18,2) NOT NULL DEFAULT '0.00' AFTER `dblMargin`; ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblprodsuppmaster` CHANGE COLUMN `dblStandardValue` `dblStandingOrder` DECIMAL(18,2) NOT NULL DEFAULT '0.00' AFTER `dblMargin`; ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblpartymaster` ADD COLUMN `dblDiscount` DOUBLE(18,2) NOT NULL DEFAULT '0.0' AFTER `strPartyIndi`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblexcisemaster`ADD COLUMN `strUserCreated` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strClientCode`, " + " ADD COLUMN `dtCreatedDate` DATE NOT NULL AFTER `strUserCreated`, " + " ADD COLUMN `strUserModified` VARCHAR(50) NOT NULL DEFAULT '' AFTER `dtCreatedDate`, " + " ADD COLUMN `dtLastModified` DATE NOT NULL AFTER `strUserModified`;";

		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblsalesorderdtl` ADD COLUMN `dblAcceptQty` DECIMAL(18,2) NOT NULL DEFAULT '0' AFTER `intId`;";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblinvtaxdtl` 	CHANGE COLUMN `strTaxAmt` `dblTaxAmt` DECIMAL(18,4) NOT NULL DEFAULT '0.0000' " + " AFTER `strInvCode`,  CHANGE COLUMN `strTaxableAmt` `dblTaxableAmt` DECIMAL(18,4) NOT NULL DEFAULT '0.0000' AFTER `strTaxDesc`;  ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblinvoicedtl`ADD COLUMN `strSOCode` VARCHAR(15) NOT NULL DEFAULT '' AFTER `dblBillRate`,ADD COLUMN `strCustCode` VARCHAR(15) NOT NULL DEFAULT '' AFTER `strSOCode`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblinvoicedtl`DROP COLUMN `intId`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblinvoicehd` ADD COLUMN `strExciseable` VARCHAR(1) NOT NULL DEFAULT 'N' AFTER `dblDiscountAmt` ;";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblinvoicehd` CHANGE COLUMN `strSOCode` `strSOCode` VARCHAR(500) NOT NULL DEFAULT '' AFTER `strAgainst`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblinvprodtaxdtl` ADD COLUMN `dblTaxableAmt` DECIMAL(18,2) NOT NULL AFTER `strClientCode`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblinvsalesorderdtl` ADD COLUMN `strDCCode` VARCHAR(15) NOT NULL DEFAULT '' AFTER `strClientCode`; ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tbldeliverychallanhd` CHANGE COLUMN `dteDCDate` `dteDCDate` DATETIME NOT NULL DEFAULT '1900-01-01' AFTER `intid`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblinvoicehd`	ADD COLUMN `strDulpicateFlag` VARCHAR(1) NOT NULL DEFAULT 'N' AFTER `strExciseable`;";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblsaleschar`	DROP PRIMARY KEY; ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblinvoicehd` ADD COLUMN `dblGrandTotal` DECIMAL(18,2) NOT NULL DEFAULT '0.00' AFTER `strDulpicateFlag`;";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblsaleschar` ADD COLUMN `strAdvOrderNo` VARCHAR(255) NOT NULL AFTER `strCharValue`; ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblscreturndtl` ADD COLUMN `dblAcceptQty` DOUBLE NOT NULL DEFAULT '0.00' AFTER `strClientCode`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tbldeliverychallanhd` ADD COLUMN `strSettlementCode` VARCHAR(20) NOT NULL AFTER `strCloseDC` ;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblinvoicehd` ADD COLUMN `strSettlementCode` VARCHAR(20) NOT NULL AFTER `dblGrandTotal`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblsalesorderhd` ADD COLUMN `strSettlementCode` VARCHAR(20) NOT NULL AFTER `strClientCode`;";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblpartymaster` " + "	ADD COLUMN `strPNHindi` VARCHAR(200) NOT NULL DEFAULT ''  AFTER `strGSTNo`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblpartymaster` " + "	ADD COLUMN `strLocCode` VARCHAR(15) NOT NULL DEFAULT '' AFTER `strPNHindi`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblpartymaster` " + "	ADD COLUMN `strPropCode` VARCHAR(10) NOT NULL DEFAULT '01' AFTER `strLocCode`; ";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblinvoicedtl` " + " 	ADD COLUMN `strUOM` VARCHAR(15) NOT NULL DEFAULT '' AFTER `strCustCode`, " + "	ADD COLUMN `dblUOMConversion` DOUBLE NOT NULL DEFAULT '1.00' AFTER `strUOM`; ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblsalesorderhd` ADD COLUMN `longMobileNo` BIGINT(20) NOT NULL DEFAULT '0' AFTER `strSettlementCode`;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblsalesorderhd` ADD COLUMN `strMobileNo` VARCHAR(20) NOT NULL DEFAULT '' AFTER `strSettlementCode`;";
		funExecuteQuery(sql);

		sql = " ALTER TABLE `tblinvoicehd` " + " ADD COLUMN `strMobileNo` VARCHAR(20) NOT NULL DEFAULT '' AFTER `dblCurrencyConv`; ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblinvoicedtl` " + " ADD COLUMN `dblProdDiscAmount` DECIMAL(18,2) NOT NULL DEFAULT '0.00' AFTER `dblUOMConversion`;";
		funExecuteQuery(sql);

		sql = "CREATE TABLE IF NOT EXISTS `tblmanufacturemaster` ( " + " `strManufacturerCode` VARCHAR(10) NOT NULL, " + " `strManufacturerName` VARCHAR(100) NOT NULL, " + " `strUserCreated` VARCHAR(20) NOT NULL, " + " `dteDateCreated` DATETIME NOT NULL, " + " `strUserModified` VARCHAR(20) NOT NULL, " + " `dteLastModified` DATETIME NOT NULL, " + " `intGId` BIGINT(20) NOT NULL, "
				+ " `strClientCode` VARCHAR(20) NOT NULL, " + " PRIMARY KEY (`strManufacturerCode`, `strClientCode`) " + " ) " + " COLLATE='latin1_swedish_ci' " + " ENGINE=InnoDB;";
		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tbldayend` ( " + "	`strLocCode` VARCHAR(10) NOT NULL, " + "	`strDayEnd` VARCHAR(15) NOT NULL, " + "	`dtDateCreated` VARCHAR(25) NOT NULL DEFAULT '', " + "	`dtLastModified` VARCHAR(25) NOT NULL DEFAULT '', " + "	`strClientCode` VARCHAR(25) NOT NULL DEFAULT '', " + "	`strUserCreated` VARCHAR(255) NOT NULL DEFAULT '', "
				+ "	`strUserModified` VARCHAR(25) NOT NULL DEFAULT '', " + "	PRIMARY KEY (`strLocCode`, `strDayEnd`, `strClientCode`) ) " + " COLLATE='latin1_swedish_ci' ENGINE=InnoDB ; ";
		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tblcustomertypemaster` (" + "`strCustTypeCode` VARCHAR(10) NOT NULL, " + "`intCustTypeId` BIGINT(20) NOT NULL, " + "`strCustTypeDesc` VARCHAR(100) NOT NULL, " + "`strUserCreated` VARCHAR(10) NOT NULL, " + "`strUserEdited` VARCHAR(10) NOT NULL, " + "`dteDateCreated` DATETIME NOT NULL, " + "`dteDateEdited` DATETIME NOT NULL, "
				+ "`strClientCode` VARCHAR(10) NOT NULL, " + "PRIMARY KEY (`strCustTypeCode`, `strClientCode`)) " + "COLLATE='latin1_swedish_ci'" + "ENGINE=InnoDB;";
		funExecuteQuery(sql);
		
		sql = "ALTER TABLE `tblsalesreturnhd` ADD COLUMN `dblDiscAmt` DOUBLE NOT NULL DEFAULT '0.0' AFTER `dblTaxAmt`;";
		funExecuteQuery(sql);
		
		sql = "ALTER TABLE `tblsalesreturnhd` ADD COLUMN `dblDiscPer` DOUBLE NOT NULL DEFAULT '0.0' AFTER `dblDiscAmt`;";
		funExecuteQuery(sql);
		
		sql="ALTER TABLE `tblinvoicehd` "
			+ " ADD COLUMN `strCheckBy` VARCHAR(100) NOT NULL DEFAULT '' AFTER `strMobileNo`,"
			+ "	ADD COLUMN `strApprovedBy` VARCHAR(100) NOT NULL DEFAULT '' AFTER `strCheckBy`,"
			+ "	ADD COLUMN `strPreparedBy` VARCHAR(100) NOT NULL DEFAULT '' AFTER `strApprovedBy`;";
		funExecuteQuery(sql);

		sql="ALTER TABLE `tblinvoicehd` "
			+ " ADD COLUMN `strAuthLevel1` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strPreparedBy`,"
			+ " ADD COLUMN `strAuthLevel2` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel1`,"
			+ " ADD COLUMN `strAuthLevel3` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel2`,"
			+ " ADD COLUMN `strAuthLevel4` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel3`,"
			+ " ADD COLUMN `strAuthLevel5` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel4`;";
		funExecuteQuery(sql);
		
		sql="ALTER TABLE `tblinvoicehd` "
			+ " CHANGE COLUMN `strCheckBy` `strCheckByNarration` VARCHAR(300) NOT NULL DEFAULT '' AFTER `strMobileNo`, "
			+ " CHANGE COLUMN `strApprovedBy` `strApprovedByNarration` VARCHAR(300) NOT NULL DEFAULT '' AFTER `strCheckByNarration`, "
			+ " CHANGE COLUMN `strPreparedBy` `strPreparedByNarration` VARCHAR(300) NOT NULL DEFAULT '' AFTER `strApprovedByNarration`;";
		funExecuteQuery(sql);
		
		sql="ALTER TABLE `tblinvoicehd` ADD COLUMN `intLevel` INT(8) NOT NULL DEFAULT '0' AFTER `strAuthLevel5`;";
		funExecuteQuery(sql);
		
		sql="ALTER TABLE `tblinvoicehd` CHANGE COLUMN `strAuthorise` `strAuthorise` VARCHAR(3) NOT NULL DEFAULT 'No' AFTER `dteLastModified`;";
		funExecuteQuery(sql);
		
		
		sql="ALTER TABLE `tblsalesorderhd` "
			+" ADD COLUMN `strAuthLevel1` VARCHAR(10) NOT NULL DEFAULT ' ' AFTER `longMobileNo`, "
			+" ADD COLUMN `strAuthLevel2` VARCHAR(10) NOT NULL DEFAULT ' ' AFTER `strAuthLevel1`, "
			+" ADD COLUMN `strAuthLevel3` VARCHAR(10) NOT NULL DEFAULT ' ' AFTER `strAuthLevel2`, "
			+" ADD COLUMN `strAuthLevel4` VARCHAR(10) NOT NULL DEFAULT ' ' AFTER `strAuthLevel3`, "
			+" ADD COLUMN `strAuthLevel5` VARCHAR(10) NOT NULL DEFAULT ' ' AFTER `strAuthLevel4`, "
			+" ADD COLUMN `intLevel` INT(8) NOT NULL DEFAULT '0' AFTER `strAuthLevel5`; ";
		funExecuteQuery(sql);
		
		sql=" ALTER TABLE `tblsalesorderhd` "
		   +" CHANGE COLUMN `strAuthorise` `strAuthorise` VARCHAR(3) NOT NULL DEFAULT '' AFTER `strBOMFlag`; ";
		funExecuteQuery(sql);
		
		sql="CREATE TABLE `tblsalesperson` ( "
				+ "`strSalesPersonCode` VARCHAR(10) NOT NULL,"
				+ "`strSalesPersonName` VARCHAR(50) NOT NULL,"
				+ "`intID` INT(11) NOT NULL DEFAULT '0',"
				+ "`strClientCode` VARCHAR(10) NOT NULL,"
				+ "PRIMARY KEY (`strSalesPersonCode`, `strClientCode`)"
				+ ")"
				+ "COLLATE='latin1_swedish_ci'"
				+ "ENGINE=InnoDB;";
		funExecuteQuery(sql);
		
		sql=" ALTER TABLE `tblsalesreturnhd` "
				   +" ADD COLUMN `strAuthLevel1` VARCHAR(10) NOT NULL DEFAULT ' ' AFTER `dblDiscPer`, "
				   +" ADD COLUMN `strAuthLevel2` VARCHAR(10) NOT NULL DEFAULT ' ' AFTER `strAuthLevel1`, "
				   +" ADD COLUMN `strAuthLevel3` VARCHAR(10) NOT NULL DEFAULT ' ' AFTER `strAuthLevel2`, "
				   +" ADD COLUMN `strAuthLevel4` VARCHAR(10) NOT NULL DEFAULT ' ' AFTER `strAuthLevel3`, "
				   +" ADD COLUMN `strAuthLevel5` VARCHAR(10) NOT NULL DEFAULT ' ' AFTER `strAuthLevel4`, "
				   +" ADD COLUMN `intLevel` INT(8) NOT NULL DEFAULT ' 0' AFTER `strAuthLevel5`;" ;
				
		funExecuteQuery(sql);
				
		sql=" ALTER TABLE `tblsalesreturnhd` "
						+" ADD COLUMN `strAuthorise` VARCHAR(3) NOT NULL DEFAULT ' ' AFTER `intLevel`;";
				
		funExecuteQuery(sql);
		
		sql=" CREATE TABLE IF NOT EXISTS `tblproformainvoicedtl` ("
				+ "	`strInvCode` VARCHAR(15) NOT NULL, "
					+ " `strProdCode` VARCHAR(10) NOT NULL DEFAULT '', "
					+ " `dblQty` DECIMAL(18,2) NOT NULL DEFAULT '0.00', "
					+ " `dblPrice` DECIMAL(18,2) NOT NULL DEFAULT '0.00', "
					+ "	`dblWeight` DOUBLE NOT NULL DEFAULT '0', "
					+ " `strProdType` VARCHAR(255) NOT NULL DEFAULT '', "
					+ " `strPktNo` VARCHAR(50) NOT NULL DEFAULT '', "
					+ " `strRemarks` VARCHAR(250) NOT NULL DEFAULT '', "
					+ " `intindex` BIGINT(20) NOT NULL DEFAULT '0', "
					+ " `strInvoiceable` VARCHAR(1) NOT NULL DEFAULT 'N', "
					+ " `strSerialNo` VARCHAR(200) NOT NULL, "
					+ " `strClientCode` VARCHAR(20) NOT NULL, "
					+ " `dblUnitPrice` DOUBLE NOT NULL DEFAULT '0', "
					+ " `dblAssValue` DOUBLE NOT NULL DEFAULT '0', "
					+ " `dblBillRate` DOUBLE NOT NULL DEFAULT '0', "
					+ " `strSOCode` VARCHAR(15) NOT NULL DEFAULT '', "
					+ " `strCustCode` VARCHAR(15) NOT NULL DEFAULT '', "
					+ " `strUOM` VARCHAR(15) NOT NULL DEFAULT '', "
					+ " `dblUOMConversion` DOUBLE NOT NULL DEFAULT '1', "
					+ " `dblProdDiscAmount` DECIMAL(18,2) NOT NULL DEFAULT '0.00' "
				+ " ) "
				+ " COLLATE='latin1_swedish_ci' "
				+ " ENGINE=InnoDB ; "; 
		funExecuteQuery(sql);
		
		sql=" CREATE TABLE IF NOT EXISTS  `tblproformainvoicehd` ( "
				+ " `strInvCode` VARCHAR(15) NOT NULL, "
				+ " `intid` BIGINT(20) NOT NULL, "
				+ " `dteInvDate` DATETIME NOT NULL DEFAULT '1900-01-01 00:00:00', "
				+ " `strAgainst` VARCHAR(20) NOT NULL DEFAULT '', "
				+ " `strSOCode` VARCHAR(500) NOT NULL DEFAULT '', "
				+ " `strCustCode` VARCHAR(10) NOT NULL DEFAULT '', "
				+ " `strPONo` VARCHAR(50) NOT NULL DEFAULT '', "
				+ " `strNarration` VARCHAR(300) NOT NULL DEFAULT '', "
				+ " `strPackNo` VARCHAR(20) NOT NULL DEFAULT '', "
				+ " `strLocCode` VARCHAR(15) NOT NULL DEFAULT '', "
				+ " `strVehNo` VARCHAR(20) NOT NULL DEFAULT '', "
				+ " `strMInBy` VARCHAR(50) NOT NULL DEFAULT '', "
				+ " `strTimeInOut` VARCHAR(10) NOT NULL DEFAULT '', "
				+ " `strUserCreated` VARCHAR(10) NOT NULL, "
				+ " `dteCreatedDate` DATE NOT NULL DEFAULT '1900-01-01', "
				+ " `strUserModified` VARCHAR(10) NOT NULL, "
				+ " `dteLastModified` DATE NOT NULL DEFAULT '1900-01-01', "
				+ " `strAuthorise` VARCHAR(3) NOT NULL DEFAULT 'No', "
				+ " `strDktNo` VARCHAR(50) NOT NULL DEFAULT '', "
				+ " `strSAdd1` VARCHAR(50) NOT NULL DEFAULT '', "
				+ " `strSAdd2` VARCHAR(50) NOT NULL DEFAULT '', "
				+ " `strSCity` VARCHAR(50) NOT NULL DEFAULT '', "
				+ " `strSState` VARCHAR(50) NOT NULL DEFAULT '', "
				+ " `strSCtry` VARCHAR(50) NOT NULL DEFAULT '', "
				+ " `strSPin` VARCHAR(50) NOT NULL DEFAULT '', "
				+ " `strInvNo` VARCHAR(50) NOT NULL DEFAULT '', "
				+ " `strReaCode` VARCHAR(15) NOT NULL DEFAULT '', "
				+ " `strSerialNo` VARCHAR(50) NOT NULL DEFAULT '', "
				+ " `strWarrPeriod` VARCHAR(50) NOT NULL DEFAULT '', "
				+ " `strWarraValidity` VARCHAR(50) NOT NULL DEFAULT '', "
				+ " `strClientCode` VARCHAR(20) NOT NULL, "
				+ " `dblTaxAmt` DOUBLE NOT NULL DEFAULT '0', "
				+ " `dblTotalAmt` DOUBLE NOT NULL DEFAULT '0', "
				+ " `dblSubTotalAmt` DOUBLE NOT NULL DEFAULT '0', "
				+ " `dblDiscount` DOUBLE NOT NULL DEFAULT '0', "
				+ " `dblDiscountAmt` DOUBLE NOT NULL DEFAULT '0', "
				+ " `strExciseable` VARCHAR(1) NOT NULL DEFAULT 'N', "
				+ " `strDulpicateFlag` VARCHAR(1) NOT NULL DEFAULT 'N', "
				+ " `dblGrandTotal` DECIMAL(18,2) NOT NULL DEFAULT '0.00', "
				+ " `strSettlementCode` VARCHAR(20) NOT NULL, "
				+ " `strCurrencyCode` VARCHAR(15) NOT NULL DEFAULT '', "
				+ " `dblCurrencyConv` DOUBLE NOT NULL DEFAULT '1', "
				+ " `strMobileNo` VARCHAR(20) NOT NULL DEFAULT '', "
				+ " `strCheckByNarration` VARCHAR(300) NOT NULL DEFAULT '', "
				+ " `strApprovedByNarration` VARCHAR(300) NOT NULL DEFAULT '', "
				+ " `strPreparedByNarration` VARCHAR(300) NOT NULL DEFAULT '', "
				+ " `strAuthLevel1` VARCHAR(10) NOT NULL DEFAULT '', "
				+ " `strAuthLevel2` VARCHAR(10) NOT NULL DEFAULT '', "
				+ " `strAuthLevel3` VARCHAR(10) NOT NULL DEFAULT '', "
				+ " `strAuthLevel4` VARCHAR(10) NOT NULL DEFAULT '', "
				+ " `strAuthLevel5` VARCHAR(10) NOT NULL DEFAULT '', "
				+ " `intLevel` INT(8) NOT NULL DEFAULT '0', "
				+ " PRIMARY KEY (`strInvCode`, `strClientCode`) "
			+ " ) "
			+ " COLLATE='latin1_swedish_ci' "
			+ " ENGINE=InnoDB "
			+ " ;"; 
		funExecuteQuery(sql);
			
		sql=" CREATE TABLE IF NOT EXISTS  `tblproformainvtaxdtl` ( "
				+" `intId` BIGINT(20) NOT NULL AUTO_INCREMENT, "
				+" 	`strClientCode` VARCHAR(255) NULL DEFAULT NULL, "
				+" 	`strInvCode` VARCHAR(255) NOT NULL DEFAULT '', "
				+" 	`dblTaxAmt` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', "
				+" 	`strTaxCode` VARCHAR(255) NOT NULL DEFAULT '', "
				+" 	`strTaxDesc` VARCHAR(255) NOT NULL DEFAULT '', "
				+" 	`dblTaxableAmt` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', "
				+" 	PRIMARY KEY (`intId`) "
				+" ) "
				+" COLLATE='latin1_swedish_ci' "
				+" ENGINE=InnoDB "
				+" AUTO_INCREMENT=396 "
				+" ; ";
		funExecuteQuery(sql);		
		
		sql=" CREATE TABLE IF NOT EXISTS  `tblproformainvprodtaxdtl` ( "
				+" `strInvCode` VARCHAR(20) NOT NULL, "
				+" 	`strCustCode` VARCHAR(20) NOT NULL, " 
				+" 	`strProdCode` VARCHAR(20) NOT NULL, "
				+" 	`strDocNo` VARCHAR(20) NOT NULL, "
				+" 	`dblValue` DECIMAL(18,2) NOT NULL, "
				+" 	`strClientCode` VARCHAR(20) NOT NULL, "
				+" 	`dblTaxableAmt` DECIMAL(18,2) NOT NULL "
				+" ) "
				+" COLLATE='latin1_swedish_ci' "
				+" ENGINE=InnoDB "
				+" ; "
				+" ";
		funExecuteQuery(sql);
		
		sql=" CREATE TABLE IF NOT EXISTS `tblproforminvsalesorderdtl` ( "
				+" `strInvCode` VARCHAR(20) NOT NULL, " 
				+" 	`strSOCode` VARCHAR(20) NOT NULL, "
				+" 	`dteInvDate` DATETIME NOT NULL, "
				+" 	`strClientCode` VARCHAR(20) NOT NULL, "
				+" 	`strDCCode` VARCHAR(15) NOT NULL DEFAULT '' "
				+" ) "
				+" COLLATE='latin1_swedish_ci' "
				+" ENGINE=InnoDB "
				+" ; ";
		funExecuteQuery(sql);
		
		sql="CREATE TABLE `tblcategorymaster` ("
				+ " `strCategoryCode` VARCHAR(255) NOT NULL,"
				+ " `strCategoryDesc` VARCHAR(255) NULL DEFAULT NULL,"
				+ " `dtCreatedDate` VARCHAR(255) NULL DEFAULT NULL,"
				+ " `dtLastModified` VARCHAR(255) NULL DEFAULT NULL,"
				+ " `intId` BIGINT(20) NULL DEFAULT NULL,"
				+ " `strClientCode` VARCHAR(255) NULL DEFAULT NULL,"
				+ " `strUserCreated` VARCHAR(255) NULL DEFAULT NULL,"
				+ " `strUserModified` VARCHAR(255) NULL DEFAULT NULL,"
				+ " PRIMARY KEY (`strCategoryCode`))"
				+ " COLLATE='latin1_swedish_ci' ENGINE=InnoDB; ";
		funExecuteQuery(sql);
		
		sql="CREATE TABLE `tblregionmaster` ("
				+ " `strRegionCode` VARCHAR(255) NOT NULL,"
				+ " `strRegionDesc` VARCHAR(255) NULL DEFAULT NULL,"
				+ " `dtCreatedDate` VARCHAR(255) NULL DEFAULT NULL,"
				+ " `dtLastModified` VARCHAR(255) NULL DEFAULT NULL,"
				+ " `intId` BIGINT(20) NULL DEFAULT NULL,"
				+ " `strClientCode` VARCHAR(255) NULL DEFAULT NULL,"
				+ " `strUserCreated` VARCHAR(255) NULL DEFAULT NULL,"
				+ " `strUserModified` VARCHAR(255) NULL DEFAULT NULL,"
				+ " PRIMARY KEY (`strRegionCode`))"
				+ " COLLATE='latin1_swedish_ci' ENGINE=InnoDB; ";
		funExecuteQuery(sql);
		
		sql=" CREATE TABLE `tblvoidedproductinvoicedtl` (`strInvCode` VARCHAR(15) NOT NULL,"
				+ " `strProdCode` VARCHAR(10) NOT NULL DEFAULT '',"
				+ " `dblQty` DECIMAL(18,2) NOT NULL DEFAULT '0.00',"
				+ " `dblPrice` DECIMAL(18,2) NOT NULL DEFAULT '0.00',"
				+ " `dblWeight` DOUBLE NOT NULL DEFAULT '0',"
				+ " `strProdType` VARCHAR(255) NOT NULL DEFAULT '',"
				+ " `strPktNo` VARCHAR(50) NOT NULL DEFAULT '',"
				+ " `strRemarks` VARCHAR(250) NOT NULL DEFAULT '',"
				+ " `intindex` BIGINT(20) NOT NULL DEFAULT '0',"
				+ " `strInvoiceable` VARCHAR(1) NOT NULL DEFAULT 'N',"
				+ " `strSerialNo` VARCHAR(200) NOT NULL,"
				+ " `strClientCode` VARCHAR(20) NOT NULL,"
				+ " `dblUnitPrice` DOUBLE NOT NULL DEFAULT '0',"
				+ " `dblAssValue` DOUBLE NOT NULL DEFAULT '0',"
				+ " `dblBillRate` DOUBLE NOT NULL DEFAULT '0',"
				+ " `strSOCode` VARCHAR(15) NOT NULL DEFAULT '',"
				+ " `strCustCode` VARCHAR(15) NOT NULL DEFAULT '',"
				+ " `strUOM` VARCHAR(15) NOT NULL DEFAULT '',"
				+ " `dblUOMConversion` DOUBLE NOT NULL DEFAULT '1',"
				+ " `dblProdDiscAmount` DECIMAL(18,2) NOT NULL DEFAULT '0.00'"
				+ " )COLLATE='latin1_swedish_ci'ENGINE=InnoDB;" ;
		funExecuteQuery(sql);
		
		sql="ALTER TABLE `tblpartymaster` ADD COLUMN `strApplForWT` VARCHAR(1) NOT NULL DEFAULT 'N' AFTER `strComesaRegion`;";
		funExecuteQuery(sql);
		
		sql=" ALTER TABLE `tblinvoicehd` 	ADD COLUMN `strDeliveryNote` VARCHAR(200) NOT NULL DEFAULT '' AFTER `intLevel`\r\n" + 
				" , 	ADD COLUMN `strSupplierRef` VARCHAR(20) NOT NULL DEFAULT '' AFTER `strDeliveryNote`\r\n" + 
				" , 	ADD COLUMN `strOtherRef` VARCHAR(20) NOT NULL DEFAULT '' AFTER `strSupplierRef`\r\n" + 
				" , 	ADD COLUMN `strBuyersOrderNo` VARCHAR(20) NOT NULL DEFAULT '' AFTER `strOtherRef`\r\n" + 
				" , 	ADD COLUMN `dteBuyerOrderNoDated` DATETIME NULL AFTER `strBuyersOrderNo`\r\n" + 
				" , 	ADD COLUMN `strDispatchDocNo` VARCHAR(20) NOT NULL DEFAULT '' AFTER `dteBuyerOrderNoDated`\r\n" + 
				" , 	ADD COLUMN `dteDispatchDocNoDated` DATETIME NULL AFTER `strDispatchDocNo`\r\n" + 
				" , 	ADD COLUMN `strDispatchThrough` VARCHAR(50) NOT NULL DEFAULT '' AFTER `dteDispatchDocNoDated`\r\n" + 
				" , 	ADD COLUMN `strDestination` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strDispatchThrough`  ";				
		funExecuteQuery(sql);

		sql="ALTER TABLE `tbldeliverychallanhd`"
			+ "ADD COLUMN `strAuthLevel1` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strSettlementCode`,"
			+ "ADD COLUMN `strAuthLevel2` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel1`,"
			+ "ADD COLUMN `strAuthLevel3` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel2`,"
			+ "ADD COLUMN `strAuthLevel4` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel3`,"
			+ "ADD COLUMN `strAuthLevel5` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strAuthLevel4`,"
			+ "ADD COLUMN `intLevel` INT(8) NOT NULL DEFAULT '0' AFTER `strAuthLevel5`;";
		funExecuteQuery(sql);
		
		sql="ALTER TABLE `tbldeliverychallanhd` "
			+ "CHANGE COLUMN `strAuthorise` `strAuthorise` VARCHAR(3) NOT NULL DEFAULT '' AFTER `dteLastModified`;";
		funExecuteQuery(sql);

		sql="ALTER TABLE `tblinvoicehd` ADD COLUMN `strCloseIV` VARCHAR(1) NOT NULL DEFAULT 'N' AFTER `strDestination`;";
		funExecuteQuery(sql);
		
		sql="ALTER TABLE `tbldeliverychallanhd` ADD COLUMN `strInvCode` VARCHAR(20) NOT NULL DEFAULT '' AFTER `intLevel`;";
		funExecuteQuery(sql);
		
		sql="ALTER TABLE `tblpartymaster` ADD COLUMN `strRegion` VARCHAR(100) NOT NULL DEFAULT '' AFTER `strApplForWT`; ";
		funExecuteQuery(sql);
		

		sql="ALTER TABLE `tblinvoicehd` ADD COLUMN `dblExtraCharges` DECIMAL(18,4) NOT NULL DEFAULT '0.0000' AFTER `strCloseIV`;";
		funExecuteQuery(sql);
		
		sql = " ALTER TABLE `tblsalesreturnhd` ADD COLUMN `dblConversion` DECIMAL(18,2) NOT NULL DEFAULT ' 1.00' AFTER `strAuthorise`; ";
		funExecuteQuery(sql);
		
		sql = " ALTER TABLE `tblsalesreturnhd` ADD COLUMN `strCurrency` VARCHAR(50) NOT NULL DEFAULT '' AFTER `dblConversion`; ";
		funExecuteQuery(sql);
		
		
		
		

		sql = " INSERT INTO `tbltreemast` (`strFormName`, `strFormDesc`, `strRootNode`, `intRootIndex`, `strType`, `intFormKey`, `intFormNo`, `strImgSrc`, `strImgName`, `strModule`, `strTemp`, `strActFile`, `strHelpFile`, `strProcessForm`, `strAutorisationForm`, `strRequestMapping`, `strAdd`, `strAuthorise`, `strDelete`, `strDeliveryNote`, `strDirect`, `strEdit`, `strGRN`, `strGrant`, `strMinimumLevel`, `strOpeningStock`, `strPrint`, `strProductionOrder`, `strProject`, `strPurchaseIndent`, `strPurchaseOrder`, `strPurchaseReturn`, `strRateContractor`, `strRequisition`, `strSalesOrder`, `strSalesProjection`, `strSalesReturn`, `strServiceOrder`, `strSubContractorGRN`, `strView`, `strWorkOrder`, `strAuditForm`, `strMIS`) VALUES "
				+ " ('frmInovice', 'Invoice', 'Sales', 2, 'T', 69, 10, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmInovice.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),"
				+ " ('frmInvoiceSlip', 'Invoice Slip', 'Reports', 3, 'R', 61, 4, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmInvoiceSlip.html' , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ "  ('frmSalesReturn', 'Sales Return', 'Sales', 2, 'T', 70, 11, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmSalesReturn.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmCategoryWiseSalesOrderReport', 'Production Compilation Report', 'Sales Order Report', 4, 'R', 80, 1, '1', 'default.png', '6', 1, '1', '1', 'NO', 'MO', 'frmCategoryWiseSalesOrderReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), " // Categorywise SO Report
				+ " ('frmCustomerWiseCategorySalesOrderReport', 'Customer Wise Category Sales Order Report', 'Sales Order Report', 4, 'R', 80, 1, '1', 'default.png', '6', 1, '1', '1', 'NO', 'MO', 'frmCustomerWiseCategorySalesOrderReport.html' , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmAdvanceOrderReports', 'Advance Order Reports', 'Sales Order Report', 4, 'R', 81, 2, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmAdvanceOrderReports.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),  "
				+ " ('frmCustomerWiseLocationWiseSOReport', 'Customer Wise Location Wise SO Report', 'Sales Order Report', 4, 'R', 81, 1, '1', 'default.png', '6', 1, '1', '1', 'NO', 'MO', 'frmCustomerWiseLocationWiseSOReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmPendingCustomerList', 'Pending Customer List', 'Sales Order Report', 4, 'R', 83, 5, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmPendingCustomerList.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),  "
				+ " ('frmProductPriceList', 'Product Price List', 'Sales Order Report', 4, 'R', 84, 6, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmProductPriceList.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),  "
				+ " ('frmExciseMaster', 'Excise Master', 'Master', 8, 'M', 23, 107, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmExciseMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ,"
				+ " ('frmInvoiceProductVariance', 'Invoice Product Variance', 'Sales', 2, 'T', 71, 12, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmInvoiceProductVariance.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmPendingCustomerSO', 'Pending Customer SO', 'Sales', 2, 'T', 85, 1, '1', 'default.png', '6', 1, '1', '1', 'YES', 'YES', 'frmPendingCustomerSO.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmLocationwiseCategorywiseSOReport', 'Locationwise Categorywise SO', 'Sales Order Report', 4, 'R', 85, 7, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmLocationwiseCategorywiseSOReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmSOCustomersList', 'Sales Order Customers List', 'Sales Order Report', 4, 'R', 83, 5, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmSOCustomersList.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),  "
				+ " ('frmShopOrderList', 'Shop Order List', 'Sales Order Report', 4, 'R', 84, 5, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmShopOrderList.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),  "
				+ " ('frmConsolidateCustomerWiseSalesOrder', 'Consolidate CustomerWise SO', 'Sales Order Report', 4, 'R', 85, 8, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmConsolidateCustomerWiseSalesOrder.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),  "
				+ " ('frmConsolidateCustomerWiseAvgSalesOrder', 'Daily Production(Customers Avg)', 'Sales Order Report', 4, 'R', 86, 8, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmConsolidateCustomerWiseAvgSalesOrder.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),  "
				+ " ('frmCRMSecurityShell', 'Security Shell', 'Master', 1, 'M', 59, 2, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmCRMSecurityShell.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),  "
				+ " ('frmInvoiceFlash', 'Invoice Flash', 'Tools', 4, 'L', 1, 2, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmInvoiceFlash.html' , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmCRMStructureUpdate', 'Structure Update', 'Tools', 4, 'L', 2, 2, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmCRMStructureUpdate.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ "  ('frmProductionAdvOrderReport', 'Production Advance Order Report', 'Sales Order Report', 4, 'R', 86, 9, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmProductionAdvOrderReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ "  ('frmSalesRegisterReport', 'Sales Register Report', 'Sales Order Report', 4, 'R', 90, 13, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmSalesRegisterReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ "  ('frmDailyProductionReportItemWise', 'Daily Production(Item Wise)', 'Sales Order Report', 4, 'R', 87, 10, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmDailyProductionReportItemWise.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ "  ('frmDutyPayableReport', 'Duty Payable Report', 'Sales Order Report', 4, 'R', 88, 11, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmDutyPayableReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ "  ('frmRG-1DailyStockAccount', 'RG-1 Daily Stock Account', 'Sales Order Report', 4, 'R', 89, 12, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmRG-1DailyStockAccount.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,NULL), "
				+ "  ('frmCommercialTaxInnvoice', 'Commercial Tax Innvoice', 'Sales', '2', 'T', '72', '13', '1', 'default.png', '6', '1', '1', '1', 'NO', 'NO', 'frmCommercialTaxInnvoice.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,NULL), "
				+ "  ('frmRetailBilling', 'Retail Billing', 'Sales', 2, 'T', 86, 13, '1', 'default.png', '6', 1, '1', '1', 'NO', 'Yes', 'frmRetailBilling.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ "  ('frmCRMSettlementMaster', 'Settlement Master', 'Master', 1, 'M', 60, 2, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmCRMSettlementMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ "  ('frmDataImport', 'Data Import', 'Master', '1', 'M', '61', '2', '1', 'default.png', '6', '1', '1', '1', 'NO', 'NO', 'frmDataImport.html' , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ "  ('Master', 'Master', 'Tools', '4', 'O', '1', '1', '1', 'default.png', '6', '1', '1', '1', 'No', 'No', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ "  ('Sales Order Report', 'Sales Order Report', 'Reports', '4', 'O', '1', '1', '1', 'default.png', '6', '1', '1', '1', 'No', 'No', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ "  ('frmBillwiseTaxFlash', 'Billwise Tax Flash', 'Tools', '4', 'L', '1', '4', '1', 'default.png', '6', '1', '1', '1', 'NO', 'NO', 'frmBillwiseTaxFlash.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ "  ('frmDayEnd', 'Day End', 'Tools', '4', 'L', '1', '5', '1', 'default.png', '6', '1', '1', '1', 'NO', 'Yes', 'frmCRMDayEnd.html' , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ "  ('frmPacketing', 'Packeting', 'Master', '1', 'M', '62', '2', '1', 'default.png', '6', '1', '1', '1', 'NO', 'NO', 'frmpacketing.html' , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ,"
				+ "  ('frmWebBookLinkup', 'WebBooks Linkup', 'Tools', '4', 'L', '5', '7', '1', 'default.png', '6', '1', '1', '1', 'NO', 'No', 'frmCRMWebBooksLinkup.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ "	 ('frmCRMAuthorizationTool', 'Authorization', 'Tools', '4', 'L', '1', '1', '1', 'default.png', '6', '1', '1', '1', 'NO', 'NO', 'frmAuthorisationTool.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),"
				+ "  ('frmSalesPersonMaster', 'Sales Person Master', 'Master', 1, 'M', 91, 2, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmSalesPersonMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),"
				+ "  ('frmSalesReturnFlash', 'Sales Return Flash', 'Tools', 4, 'L', 119, 7, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmSalesReturnFlash.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),"
				+ "	 ('frmProFormaInvoice', 'ProForma Invoice', 'Sales', 2, 'T', 70, 11, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmProFormaInvoice.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ "  ('frmMonthWiseProductWiseSale', 'Month Wise Prod Wise Sale', 'Reports', 3, 'R', 90, 8, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmMonthWiseProductWiseSale.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),"
				+ "  ('frmCRMCategoryMaster', 'Category Master', 'Master', 1, 'M', 63, 2, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmCRMCategoryMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ "  ('frmCRMRegionMaster', 'Region Master', 'Master', 1, 'M', 64, 3, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmCRMRegionMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		        +"   ('frmCRMDashboard', 'Dashboard', 'Reports', 4, 'R', 65, 4, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmCRMDashboard.html' , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) , "
				+"  ('frmWebBookRepostingJV', 'Reposting JV', 'Tools', 4, 'L', 5, 8, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmWebBookRepostingJV.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) , "	
				+" ('frmProductWiseFlash', 'ProductWise Flash', 'Tools', 4, 'L', 1, 2, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmProductWiseFlash.html' , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		        +" ('frmCustomerWiseInvoiceFlash', 'CustWise Invoice Flash', 'Tools', 4, 'L', 1, 2, '1', 'default.png', '6',1, '1', '1', 'NO', 'NO', 'frmCustomerWiseInvoiceFlash.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "  
				+" ('frmSubGroupWiseItemSalesReport', 'Sub GroupWise Item Sales Report', 'Reports',3, 'R', 91, 8, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmSubGroupWiseItemSalesReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),"
		        +" ('frmSalesAndSalesReturnSummaryReport', 'Sales And Sales Return Summary Report', 'Reports',3, 'R', 92, 8, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmSalesAndSalesReturnSummaryReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),"
		        + " ('frmVoidInvoiceReport', 'Void Invoice Report', 'Reports', 3, 'R', 68, 4, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmVoidInvoiceReport.html' , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL)," 
				+" ('frmWithholdingTaxReport', 'Withholding Tax Report', 'Reports',3, 'R', 93, 8, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmWithholdingTaxReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL)";
		System.out.println(sql);
			funExecuteQuery(sql);

		/*----------------CRM Forms End---------------------------*/

		
		
		
		
		/*----------------Excise Forms Only---------------------------*/

		sql = " CREATE TABLE IF NOT EXISTS `tblpermitmaster`" + " (  `intId` bigint(20) NOT NULL AUTO_INCREMENT, " + "  `strPermitCode` varchar(20) NOT NULL,  " + " `strPermitName` varchar(150) NOT NULL,  " + " `strPermitNo` varchar(30) NOT NULL, " + "  `StrPermitPlace` varchar(100) NOT NULL, " + "  `dtePermitIssue` date NOT NULL, " + "  `dtePermitExp` date NOT NULL,  "
				+ " `strPermitStatus` varchar(5) NOT NULL, " + "  `dtePermitEdited` datetime NOT NULL,  " + " `strPermitUserCode` varchar(20) NOT NULL, " + "  `strClientCode` varchar(10) NOT NULL DEFAULT '1', " + "   PRIMARY KEY (`intId`)  ) " + " ENGINE=InnoDB AUTO_INCREMENT=1608 DEFAULT CHARSET=utf8;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblpartymaster` ADD COLUMN `strOperational` VARCHAR(1) NOT NULL DEFAULT 'Y' AFTER `dblDiscount`;";
		funExecuteQuery(sql);

		sql = "CREATE TABLE IF NOT EXISTS `tbllocwiseuser` (`strUserCode` VARCHAR(10) NOT NULL" + ",`strLocCode` VARCHAR(10) NOT NULL,`strPropCode` VARCHAR(10) NOT NULL" + ",`strClientCode` VARCHAR(10) NOT NULL) " + "COLLATE='latin1_swedish_ci' ENGINE=InnoDB;";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tbltaxhd` ADD COLUMN `strTaxOnSubGroup` VARCHAR(1) NOT NULL DEFAULT 'N' AFTER `strTaxOnTaxCode`;";
		funExecuteQuery(sql);

		sql = "CREATE TABLE IF NOT EXISTS `tbltaxsubgroupdtl` (`strTaxCode` VARCHAR(20) NOT NULL" + ",`strSGCode` VARCHAR(20) NOT NULL,`strClientCode` VARCHAR(20) NOT NULL)" + " COLLATE='latin1_swedish_ci'ENGINE=InnoDB;";
		funExecuteQuery(sql);

		sql = " INSERT INTO `tbltreemast` (`strFormName`, `strFormDesc`, `strRootNode`, `intRootIndex`, `strType`, `intFormKey`, `intFormNo`, `strImgSrc`, `strImgName`, `strModule`, `strTemp`, `strActFile`, `strHelpFile`, `strProcessForm`, `strAutorisationForm`, `strRequestMapping`, `strAdd`, `strAuthorise`, `strDelete`, `strDeliveryNote`, `strDirect`, `strEdit`, `strGRN`, `strGrant`, `strMinimumLevel`, `strOpeningStock`, `strPrint`, `strProductionOrder`, `strProject`, `strPurchaseIndent`, `strPurchaseOrder`, `strPurchaseReturn`, `strRateContractor`, `strRequisition`, `strSalesOrder`, `strSalesProjection`, `strSalesReturn`, `strServiceOrder`, `strSubContractorGRN`, `strView`, `strWorkOrder`, `strAuditForm`, `strMIS`) VALUES "
				+ " ('frmExciseStockFlash', 'Excise Stock Flash', 'Tools', '4', 'T', '16', '13', '12', 'default.png', '2', '1', '1', '1', 'NO', 'NO', 'frmExciseStockFlash.html',NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ";

		funExecuteQuery(sql);
		/*----------------Excise Forms End---------------------------*/

		/*----------------WebClub Forms Only---------------------------*/
		sql = " INSERT INTO `tbltreemast` (`strFormName`, `strFormDesc`, `strRootNode`, `intRootIndex`, `strType`, `intFormKey`, `intFormNo`, `strImgSrc`, `strImgName`, `strModule`, `strTemp`, `strActFile`, `strHelpFile`, `strProcessForm`, `strAutorisationForm`, `strRequestMapping`, `strAdd`, `strAuthorise`, `strDelete`, `strDeliveryNote`, `strDirect`, `strEdit`, `strGRN`, `strGrant`, `strMinimumLevel`, `strOpeningStock`, `strPrint`, `strProductionOrder`, `strProject`, `strPurchaseIndent`, `strPurchaseOrder`, `strPurchaseReturn`, `strRateContractor`, `strRequisition`, `strSalesOrder`, `strSalesProjection`, `strSalesReturn`, `strServiceOrder`, `strSubContractorGRN`, `strView`, `strWorkOrder`, `strAuditForm`, `strMIS`) VALUES "
				+ " ('frmWebClubSecurityShellMaster', 'Security Shell', 'Master', 1, 'M', 1, 10, '1', 'default.png', '4', 1, '1', '1', 'NO', 'YES', 'frmWebClubSecurityShell.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ";

		funExecuteQuery(sql);

		/*----------------WebClub Forms End---------------------------*/
		//
		//
		/*----------------WebBook Forms Only---------------------------*/

		sql = " CREATE TABLE IF NOT EXISTS `tblbudget` (" + "	`strAccCode` VARCHAR(10) NOT NULL," + "	`strAccName` VARCHAR(50) NOT NULL DEFAULT ''," + "	`strMonth` VARCHAR(25) NOT NULL," + " 	`strYear` VARCHAR(10) NOT NULL," + " 	`dblBudgetAmt` DECIMAL(18,4) NOT NULL DEFAULT '0.0000'," + "  	`intID` BIGINT(20) NOT NULL AUTO_INCREMENT, " + "	`strClientCode` VARCHAR(10) NOT NULL, "
				+ "	PRIMARY KEY (`intID`, `strClientCode`) ) " + " COLLATE='latin1_swedish_ci' ENGINE=InnoDB AUTO_INCREMENT=5 ; ";
		funExecuteWebBooksQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tblscbillhd`  " + " (	`strVoucherNo` VARCHAR(15) NOT NULL," + "	`strSuppCode` VARCHAR(15) NOT NULL,	" + "	`strSuppName` VARCHAR(255) NOT NULL,	" + " `strBillNo` VARCHAR(50) NOT NULL," + "	`dteBillDate` DATE NOT NULL," + "	`dteDueDate` DATE NOT NULL,	`dblTotalAmount` DOUBLE NOT NULL," + "	`strModuleType` VARCHAR(10) NOT NULL,"
				+ "	`dteVoucherDate` DATE NOT NULL,	`strUserCreated` VARCHAR(10) NOT NULL," + "	`strUserEdited` VARCHAR(10) NOT NULL," + "	`dteDateCreated` DATETIME NOT NULL,	`dteDateEdited` DATETIME NOT NULL," + "	`strClientCode` VARCHAR(10) NOT NULL," + "	`strPropertyCode` VARCHAR(10) NOT NULL,	" + " `strNarration` VARCHAR(255) NOT NULL DEFAULT ''," + "	`intVouchNum` VARCHAR(10) NOT NULL,"
				+ "	`intVoucherMonth` INT(2) NOT NULL, " + " `strAcCode` VARCHAR(10) NOT NULL,  " + "	`strAcName` VARCHAR(255) NOT NULL, " + "	PRIMARY KEY (`strVoucherNo`, `strClientCode`) )" + " COLLATE='latin1_swedish_ci' ENGINE=InnoDB; ";
		funExecuteWebBooksQuery(sql);

		sql = "CREATE TABLE IF NOT EXISTS `tblscbilldtl` " + " (	`strVoucherNo` VARCHAR(15) NOT NULL,	" + " `strClientCode` VARCHAR(10) NOT NULL,	" + " `strACCode` VARCHAR(15) NOT NULL,	" + " `strACName` VARCHAR(255) NOT NULL,	" + " `strCrDr` VARCHAR(2) NOT NULL,	" + " `dblCrAmt` DOUBLE(18,4) NOT NULL,	" + " `dblDrAmt` DOUBLE(18,4) NOT NULL,	"
				+ " `strNarration` VARCHAR(255) NOT NULL DEFAULT '',	" + " `strPropertyCode` VARCHAR(10) NOT NULL)  " + " COLLATE='latin1_swedish_ci' ENGINE=InnoDB ; ";
		funExecuteWebBooksQuery(sql);

		sql = "CREATE TABLE IF NOT EXISTS `tblscbillgrndtl`" + " (	`strVoucherNo` VARCHAR(15) NOT NULL,	" + "`strClientCode` VARCHAR(10) NOT NULL,	" + "`strGRNCode` VARCHAR(15) NOT NULL,	" + "`dteGRNDate` DATE NOT NULL,	" + "`dblGRNAmt` DOUBLE(18,4) NOT NULL DEFAULT '0.00',	" + "`dteGRNDueDate` DATE NOT NULL,	" + "`strGRNBIllNo` VARCHAR(50) NOT NULL DEFAULT '',	"
				+ "`strPropertyCode` VARCHAR(2) NOT NULL ) " + " COLLATE='latin1_swedish_ci' ENGINE=InnoDB ; ";
		funExecuteWebBooksQuery(sql);

		sql = "CREATE TABLE IF NOT EXISTS `tblpaymentgrndtl` ( " + "	`strClientCode` VARCHAR(255) NOT NULL, " + "	`strVouchNo` VARCHAR(255) NULL DEFAULT NULL, " + "	`dblGRNAmt` DOUBLE NOT NULL, " + "	`dteBillDate` VARCHAR(255) NULL DEFAULT NULL, " + "	`dteGRNDate` VARCHAR(255) NULL DEFAULT NULL, " + "	`dteGRNDueDate` VARCHAR(255) NULL DEFAULT NULL, "
				+ "	`strGRNBIllNo` VARCHAR(255) NULL DEFAULT NULL, " + "	`strGRNCode` VARCHAR(255) NULL DEFAULT NULL, " + "	`strPropertyCode` VARCHAR(255) NULL DEFAULT NULL ) " + " COLLATE='latin1_swedish_ci' ENGINE=InnoDB ;  ";
		funExecuteWebBooksQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tblsundarycreditormaster` ( " + "	`intGId` BIGINT(20) NOT NULL AUTO_INCREMENT, " + "	`strCreditorCode` VARCHAR(20) NOT NULL, " + "	`strPrefix` VARCHAR(10) NOT NULL, " + "	`strFirstName` VARCHAR(50) NOT NULL, " + "	`strMiddleName` VARCHAR(50) NOT NULL, " + "	`strLastName` VARCHAR(50) NOT NULL, " + "	`strCategoryCode` VARCHAR(100) NOT NULL, "
				+ "	`strAddressLine1` VARCHAR(200) NOT NULL, " + "	`strAddressLine2` VARCHAR(200) NOT NULL, " + "	`strAddressLine3` VARCHAR(200) NOT NULL, " + "	`strCity` VARCHAR(100) NOT NULL, " + "	`longZipCode` VARCHAR(50) NOT NULL, " + "	`strTelNo1` VARCHAR(50) NOT NULL, " + "	`strTelNo2` VARCHAR(50) NOT NULL, " + "	`strFax` VARCHAR(50) NOT NULL, "
				+ "	`strArea` VARCHAR(50) NOT NULL DEFAULT '', " + "	`strEmail` VARCHAR(50) NOT NULL DEFAULT '', " + "	`strContactPerson1` VARCHAR(50) NOT NULL DEFAULT '', " + "	`strContactDesignation1` VARCHAR(50) NOT NULL DEFAULT '', " + "	`strContactEmail1` VARCHAR(50) NOT NULL DEFAULT '', " + "	`strContactTelNo1` VARCHAR(50) NOT NULL DEFAULT '', "
				+ "	`strContactPerson2` VARCHAR(50) NOT NULL DEFAULT '', " + "	`strContactDesignation2` VARCHAR(50) NOT NULL DEFAULT '', " + "	`strContactEmail2` VARCHAR(50) NOT NULL DEFAULT '', " + "	`strContactTelNo2` VARCHAR(50) NOT NULL DEFAULT '', " + "	`strLandmark` VARCHAR(50) NOT NULL DEFAULT '', " + "	`strCreditorFullName` VARCHAR(100) NOT NULL DEFAULT '', "
				+ "	`strExpired` VARCHAR(5) NOT NULL DEFAULT 'No', " + "	`strExpiryReasonCode` VARCHAR(20) NOT NULL DEFAULT 'NA', " + "	`strECSYN` VARCHAR(5) NOT NULL DEFAULT 'N', " + "	`strAccountNo` VARCHAR(100) NOT NULL, " + "	`strHolderName` VARCHAR(100) NOT NULL, " + "	`strMICRNo` VARCHAR(100) NOT NULL, " + "	`dblECS` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', "
				+ "	`strSaveCurAccount` VARCHAR(50) NOT NULL DEFAULT '', " + "	`strAlternateCode` VARCHAR(50) NOT NULL DEFAULT '', " + "	`dblOutstanding` DECIMAL(18,4) NOT NULL DEFAULT '0.0000', " + "	`strStatus` VARCHAR(20) NOT NULL DEFAULT '', " + "	`intDays1` VARCHAR(50) NOT NULL DEFAULT '0', 	" + "`intDays2` VARCHAR(50) NOT NULL DEFAULT '0', "
				+ "	`intDays3` VARCHAR(50) NOT NULL DEFAULT '0', " + "	`intDays4` VARCHAR(50) NOT NULL DEFAULT '0', " + "	`intDays5` VARCHAR(50) NOT NULL DEFAULT '0', " + "	`dblCrAmt` DECIMAL(18,2) NOT NULL DEFAULT '0.00',  " + "	`dblDrAmt` DECIMAL(18,2) NOT NULL DEFAULT '0.00', " + "	`dteLetterProcess` DATETIME NOT NULL DEFAULT '1900-01-01 00:00:00', "
				+ "	`strReminder1` VARCHAR(15) NOT NULL DEFAULT '', " + "	`strReminder2` VARCHAR(15) NOT NULL DEFAULT '', " + "	`strReminder3` VARCHAR(15) NOT NULL DEFAULT '', " + "	`strReminder4` VARCHAR(15) NOT NULL DEFAULT '', " + "	`strReminder5` VARCHAR(15) NOT NULL DEFAULT '', " + "	`dblLicenseFee` VARCHAR(50) NOT NULL DEFAULT '0.0000', "
				+ "	`dblAnnualFee` VARCHAR(50) NOT NULL DEFAULT '0.0000', " + "	`strRemarks` VARCHAR(200) NOT NULL DEFAULT 'NA', " + "	`strClientApproval` VARCHAR(5) NOT NULL DEFAULT 'No', " + "	`strAMCLink` VARCHAR(200) NOT NULL DEFAULT '', " + "	`strCurrencyType` VARCHAR(20) NOT NULL DEFAULT '', " + "	`strAccountHolderCode` VARCHAR(20) NOT NULL DEFAULT '', "
				+ "	`strAccountHolderName` VARCHAR(100) NOT NULL DEFAULT '', " + "	`strAMCCycle` VARCHAR(20) NOT NULL DEFAULT '',  " + "	`dteStartDate` DATETIME NOT NULL, " + "	`strAMCRemarks` VARCHAR(200) NOT NULL DEFAULT 'NA', " + "	`strClientComment` VARCHAR(200) NOT NULL DEFAULT 'NA', " + "	`strBillingToCode` VARCHAR(50) NOT NULL DEFAULT '', "
				+ "	`dblAnnualFeeInCurrency` VARCHAR(50) NOT NULL DEFAULT '0.0000', " + "	`dblLicenseFeeInCurrency` VARCHAR(50) NOT NULL DEFAULT '0.0000', " + "	`strState` VARCHAR(50) NOT NULL DEFAULT '', " + "	`strRegion` VARCHAR(50) NOT NULL DEFAULT '', " + "	`strCountry` VARCHAR(50) NOT NULL DEFAULT '', " + "	`strConsolidated` VARCHAR(5) NOT NULL DEFAULT 'No', "
				+ "	`intCreditDays` VARCHAR(50) NOT NULL DEFAULT '0', " + "	`strCreditorStatusCode` VARCHAR(10) NOT NULL DEFAULT '', " + "	`longMobileNo` VARCHAR(50) NOT NULL, " + "	`strECSActivate` VARCHAR(3) NOT NULL DEFAULT '', " + "	`strReminderStatus1` VARCHAR(50) NOT NULL DEFAULT '', " + "	`dteRemainderDate1` VARCHAR(50) NOT NULL DEFAULT '1900-01-01', "
				+ " 	`strReminderStatus2` VARCHAR(50) NOT NULL DEFAULT '', " + "	`dteRemainderDate2` VARCHAR(50) NOT NULL DEFAULT '1900-01-01', " + "	`strReminderStatus3` VARCHAR(50) NOT NULL DEFAULT '', " + "	`dteRemainderDate3` VARCHAR(50) NOT NULL DEFAULT '1900-01-01', " + " 	`strReminderStatus4` VARCHAR(50) NOT NULL DEFAULT '', "
				+ "	`dteRemainderDate4` VARCHAR(50) NOT NULL DEFAULT '1900-01-01', " + "	`strReminderStatus5` VARCHAR(50) NOT NULL DEFAULT '', " + "	`dteRemainderDate5` VARCHAR(50) NOT NULL DEFAULT '1900-01-01', " + "	`strAllInvoiceHeader` VARCHAR(500) NOT NULL DEFAULT '', " + "	`strUserCreated` VARCHAR(10) NOT NULL, " + "	`strUserEdited` VARCHAR(10) NOT NULL, "
				+ "	`dteDateCreated` DATETIME NOT NULL, " + "	`dteDateEdited` DATETIME NOT NULL, " + "	`strClientCode` VARCHAR(20) NOT NULL, " + "	`strPropertyCode` VARCHAR(10) NOT NULL, " + "	`strBlocked` VARCHAR(255) NULL DEFAULT NULL, " + "	PRIMARY KEY (`strCreditorCode`, `strClientCode`), " + "	INDEX `intGId` (`intGId`) ) COLLATE='latin1_swedish_ci' " + " ENGINE=InnoDB AUTO_INCREMENT=13 ;  ";

		funExecuteWebBooksQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS   `tblledgersummary` ( " + "	`dteVochDate` VARCHAR(100) NOT NULL,  	" + "`strVoucherNo` VARCHAR(100) NOT NULL,  " + "	`strTransType` VARCHAR(50) NOT NULL,  " + "	`strChequeBillNo` VARCHAR(50) NOT NULL,  " + "	`dteBillDate` VARCHAR(100) NOT NULL,  " + "	`dblDebitAmt` DOUBLE NOT NULL DEFAULT '0.00',  " + "	`dblCreditAmt` DOUBLE NOT NULL DEFAULT '0.00',  "
				+ "	`dblBalanceAmt` DOUBLE NOT NULL DEFAULT '0.00',  " + "	`strBalCrDr` VARCHAR(50) NOT NULL DEFAULT '',  " + "	`strNarration` VARCHAR(100) NOT NULL DEFAULT '',  " + "	`strTransTypeForOrderBy` VARCHAR(100) NOT NULL DEFAULT '',  " + "	`strUserCode` VARCHAR(100) NOT NULL,  " + "	`strPropertyCode` VARCHAR(10) NOT NULL,  " + "	`strClientCode` VARCHAR(100) NOT NULL,  "
				+ "	PRIMARY KEY (`strVoucherNo`, `strClientCode`,`strTransType`)  ) " + " COLLATE='latin1_swedish_ci' ENGINE=InnoDB ; ";

		funExecuteWebBooksQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS  `tblcurrentaccountbal` ( " + "	`strAccountCode` VARCHAR(50) NOT NULL, " + "	`strAccountName` VARCHAR(50) NOT NULL, " + "	`strDrCrCode` VARCHAR(50) NOT NULL, " + "	`dteBalDate` VARCHAR(50) NOT NULL,  " + "	`dblCrAmt` DOUBLE NOT NULL DEFAULT '0.00',  " + "	`dblDrAmt` DOUBLE NOT NULL DEFAULT '0.00',  " + "	`dblBalAmt` DOUBLE NOT NULL DEFAULT '0.00', "
				+ "	`strTransecType` VARCHAR(50) NOT NULL DEFAULT '', " + " 	`strUserCode` VARCHAR(50) NOT NULL, " + "	`strPropertyCode` VARCHAR(50) NOT NULL, " + "	`strClientCode` VARCHAR(50) NOT NULL,  " + "	PRIMARY KEY (`strAccountCode`, `strClientCode`) ) " + " COLLATE='latin1_swedish_ci' ENGINE=InnoDB ; ";
		funExecuteWebBooksQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS  `tblsundarycreditoropeningbalance` ( 			" + "		`strCreditorCode` VARCHAR(50) NOT NULL, 		" + "		`strAccountCode` VARCHAR(50) NOT NULL, 			" + "		`strAccountName` VARCHAR(150) NOT NULL, 		" + "			`dblOpeningbal` DOUBLE NOT NULL DEFAULT '0.00', 			" + "		`strCrDr` VARCHAR(50) NOT NULL, 		" + "			`strClientCode` VARCHAR(50) NOT NULL, 		"
				+ "			PRIMARY KEY (`strCreditorCode`, `strClientCode`) 		" + "		) 				COLLATE='latin1_swedish_ci' 		" + "		ENGINE=InnoDB ;	 ";
		funExecuteWebBooksQuery(sql);

		sql = "	CREATE TABLE IF NOT EXISTS  `tblsundarydebtoropeningbalance` ( 		" + "			`strDebtorCode` VARCHAR(50) NOT NULL, 		" + "			`strAccountCode` VARCHAR(50) NOT NULL, 		" + "			`strAccountName` VARCHAR(150) NOT NULL, 	" + "				`dblOpeningbal` DOUBLE NOT NULL DEFAULT '0.00', 	" + "				`strCrDr` VARCHAR(50) NOT NULL, 	" + "				`strClientCode` VARCHAR(50) NOT NULL, 		"
				+ "			PRIMARY KEY (`strDebtorCode`, `strClientCode`) 		" + "		) 				COLLATE='latin1_swedish_ci' 		" + "		ENGINE=InnoDB ; ";
		funExecuteWebBooksQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tblreceiptinvdtl` ( " + "	`strClientCode` VARCHAR(255) NOT NULL, " + "	`strVouchNo` VARCHAR(255) NOT NULL, 	" + "`dblInvAmt` DOUBLE NULL DEFAULT NULL, " + "	`dteBillDate` VARCHAR(255) NULL DEFAULT NULL, " + "	`dteInvDate` VARCHAR(255) NULL DEFAULT NULL, " + "	`dteInvDueDate` VARCHAR(255) NULL DEFAULT NULL, "
				+ "	`strInvBIllNo` VARCHAR(255) NULL DEFAULT NULL," + " 	`strInvCode` VARCHAR(255) NULL DEFAULT NULL, " + "	`strPropertyCode` VARCHAR(255) NULL DEFAULT NULL,   " + "	`dblPayedAmt` DOUBLE NOT NULL DEFAULT '0' " + " ) COLLATE='latin1_swedish_ci' ENGINE=InnoDB ; ";
		funExecuteWebBooksQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tblsundarycreditoropeningbalance` ( " + "	`strCreditorCode` VARCHAR(50) NOT NULL, " + "`strAccountCode` VARCHAR(50) NOT NULL, " + "	`strAccountName` VARCHAR(150) NOT NULL, " + "	`dblOpeningbal` DOUBLE NOT NULL DEFAULT '0', " + "	`strCrDr` VARCHAR(50) NOT NULL, " + "	`strClientCode` VARCHAR(50) NOT NULL, "
				+ "	PRIMARY KEY (`strCreditorCode`, `strClientCode`) ) " + " COLLATE='latin1_swedish_ci' ENGINE=InnoDB ; ";

		funExecuteWebBooksQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tblsundarydebtoropeningbalance` ( " + "	`strDebtorCode` VARCHAR(50) NOT NULL, " + "	`strAccountCode` VARCHAR(50) NOT NULL, " + " 	`strAccountName` VARCHAR(150) NOT NULL, " + "	`dblOpeningbal` DOUBLE NOT NULL DEFAULT '0', " + "	`strCrDr` VARCHAR(50) NOT NULL, " + "	`strClientCode` VARCHAR(50) NOT NULL, "
				+ "	PRIMARY KEY (`strDebtorCode`, `strClientCode`) ) " + " COLLATE='latin1_swedish_ci' ENGINE=InnoDB ;  ";

		funExecuteWebBooksQuery(sql);

		sql="CREATE TABLE `tblemployeemaster` ( "
				+ "`strEmployeeCode` VARCHAR(20) NOT NULL,"
				+ "`strEmployeeName` VARCHAR(50) NOT NULL,"
				+ "`intID` BIGINT(20) NOT NULL,"
				+ "`strUserCreated` VARCHAR(50) NOT NULL,"
				+ "`strUserModified` VARCHAR(50) NOT NULL,"
				+ "`dteCreatedDate` DATETIME NOT NULL,"
				+ "`dteLastModified` DATETIME NOT NULL,"
				+ "`strClientCode` VARCHAR(20) NOT NULL,"
				+ "PRIMARY KEY (`strEmployeeCode`, `strClientCode`))"
				+ "COLLATE='latin1_swedish_ci'"
				+ "ENGINE=InnoDB;";
		funExecuteWebBooksQuery(sql);
		
		sql=" CREATE TABLE `tbluserdefinedreporthd` ("
				+ " `strReportId` VARCHAR(15) NOT NULL,"
				+ " `intid` BIGINT(20) NOT NULL,"
				+ " `strReportName` VARCHAR(200) NOT NULL,"
				+ " `dteUserDefDate` DATETIME NOT NULL,"
				+ " `dteDateCreated` DATETIME NOT NULL,"
				+ " `dteDateEdited` DATETIME NOT NULL,"
				+ " `strUserCreated` VARCHAR(50) NOT NULL,"
				+ " `strUserModified` VARCHAR(50) NOT NULL,"
				+ " `strClientCode` VARCHAR(50) NOT NULL,"
				+ " PRIMARY KEY (`strReportId`, `strClientCode`)"
				+ " )COLLATE='latin1_swedish_ci'"
				+ " ENGINE=InnoDB;";
		funExecuteWebBooksQuery(sql);
		
		
		sql=" CREATE TABLE `tbluserdefinedreportdtl` ("
				+ " `strReportId` VARCHAR(15) NOT NULL,"
				+ " `intSrNo` INT(10) NOT NULL,"
				+ " `strType` VARCHAR(30) NOT NULL,"
				+ " `strColumn` VARCHAR(50) NOT NULL,"
				+ " `strOperator` VARCHAR(50) NOT NULL,"
				+ " `strFGroup` VARCHAR(20) NOT NULL,"
				+ " `strTGroup` VARCHAR(20) NOT NULL,"
				+ " `strFAccount` VARCHAR(20) NOT NULL,"
				+ " `strTAccount` VARCHAR(20) NOT NULL,"
				+ " `strDescription` VARCHAR(200) NOT NULL,"
				+ " `strConstant` VARCHAR(50) NOT NULL,"
				+ " `strFormula` VARCHAR(50) NOT NULL,"
				+ " `strPrint` VARCHAR(1) NOT NULL,"
				+ " `strClientCode` VARCHAR(50) NOT NULL"
				+ " )COLLATE='latin1_swedish_ci'"
				+ " ENGINE=InnoDB;";
		funExecuteWebBooksQuery(sql);
		
		sql = " ALTER TABLE `tblpaymentdtl` " + " ADD COLUMN `strDebtorCode` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strPropertyCode` ";

		funExecuteWebBooksQuery(sql);

		sql = " ALTER TABLE `tblpaymentgrndtl` " + "	ADD COLUMN `dblPayedAmt` DOUBLE NOT NULL DEFAULT '0.00' AFTER `strPropertyCode`; ";

		funExecuteWebBooksQuery(sql);

		sql = " ALTER TABLE `tblauditdtl` " + "	CHANGE COLUMN `strAccountName` " + " `strAccountName` VARCHAR(100) NOT NULL AFTER `strAccountCode`;  ";
		funExecuteWebBooksQuery(sql);

		sql = " ALTER TABLE `tblaudithd` " + "	CHANGE COLUMN `strTransType` `strTransType` " + " VARCHAR(10) NOT NULL DEFAULT '' AFTER `strMasterPOS`;  ";
		funExecuteWebBooksQuery(sql);

		sql = " ALTER TABLE `tblscbillhd` " + "	ADD COLUMN `strCreditorCode` VARCHAR(10) NOT NULL AFTER `strSuppName`;";
		funExecuteWebBooksQuery(sql);

		sql = "ALTER TABLE `tblledgersummary` "
				+" CHANGE COLUMN `dteVochDate` `dteVochDate` DATETIME NULL DEFAULT NULL AFTER `strVoucherNo`, "
				+" CHANGE COLUMN `dteBillDate` `dteBillDate` DATETIME NULL DEFAULT NULL AFTER `dblDebitAmt`" ;
		funExecuteWebBooksQuery(sql);
		
		sql = " ALTER TABLE `tblsundarydebtormaster` "
			+ " ADD COLUMN `strAccountCode` VARCHAR(10) NOT NULL AFTER `strBlocked`; " ;
		funExecuteWebBooksQuery(sql);
		
		sql = "ALTER TABLE `tblsundarycreditormaster` "
				+ " ADD COLUMN `strAccountCode` VARCHAR(10) NOT NULL AFTER `strUserEdited` ; " ;
		funExecuteWebBooksQuery(sql);
		
		sql="ALTER TABLE `tbljvhd` ADD COLUMN `strSource` VARCHAR(20) NOT NULL DEFAULT 'User' AFTER `intVouchNum`"
				+ ",ADD COLUMN `strSourceDocNo` VARCHAR(20) NOT NULL AFTER `strSource`;";
		funExecuteWebBooksQuery(sql);
		
		sql=" ALTER TABLE `tblchargemaster` "
		 +" ADD COLUMN `strCriteria` VARCHAR(20) NOT NULL DEFAULT '' AFTER `dtLastModified`, "
		 +" ADD COLUMN `strCondition` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strCriteria`, "
		 +" ADD COLUMN `dblConditionValue` DECIMAL(18,4) NOT NULL DEFAULT '0.00' AFTER `strCondition`; ";
		funExecuteWebBooksQuery(sql);
	

		sql="ALTER TABLE `tblacmaster`"
				+ "ADD COLUMN `strEmployeeCode` VARCHAR(20) NOT NULL AFTER `intOpeningBal`";
		funExecuteWebBooksQuery(sql);
		
		sql= "ALTER TABLE `tblacmaster` "
			+" CHANGE COLUMN `strAccountCode` `strAccountCode` VARCHAR(20) NOT NULL AFTER `intGId`;" ;
		funExecuteWebBooksQuery(sql);
		
		sql="ALTER TABLE `tbljvdtl` CHANGE COLUMN `strAccountCode` `strAccountCode` VARCHAR(20) NOT NULL AFTER `strVouchNo`;";
		funExecuteWebBooksQuery(sql);
		
		sql="ALTER TABLE `tblpaymentdtl` CHANGE COLUMN `strAccountCode` `strAccountCode` VARCHAR(20) NOT NULL AFTER `strVouchNo`;";
		funExecuteWebBooksQuery(sql);
		
		sql="ALTER TABLE `tblreceiptdtl` CHANGE COLUMN `strAccountCode` `strAccountCode` VARCHAR(20) NOT NULL AFTER `strVouchNo`;";
		funExecuteWebBooksQuery(sql);

		sql="ALTER TABLE `tbljvdebtordtl` CHANGE COLUMN `strAccountCode` `strAccountCode` VARCHAR(20) NOT NULL AFTER `strGuest`;";
		funExecuteWebBooksQuery(sql);
	
		sql="ALTER TABLE `tblpaymentdebtordtl` CHANGE COLUMN `strAccountCode` `strAccountCode` VARCHAR(20) NOT NULL AFTER `strGuest`;";	
		funExecuteWebBooksQuery(sql);
		
		sql="ALTER TABLE `tblreceiptdebtordtl` CHANGE COLUMN `strAccountCode` `strAccountCode` VARCHAR(20) NOT NULL AFTER `strGuest`;";	
		funExecuteWebBooksQuery(sql);
		
		sql="CREATE TABLE `tblemployeeopeningbalance` (\r\n" + 
				"	`strClientCode` VARCHAR(20) NOT NULL,\r\n" + 
				"	`strEmployeeCode` VARCHAR(255) NOT NULL,\r\n" + 
				"	`dblOpeningbal` VARCHAR(255) NULL DEFAULT NULL,\r\n" + 
				"	`strAccountCode` VARCHAR(255) NULL DEFAULT NULL,\r\n" + 
				"	`strAccountName` VARCHAR(255) NULL DEFAULT NULL,\r\n" + 
				"	`strCrDr` VARCHAR(10) NULL DEFAULT NULL\r\n" + 
				")\r\n" + 
				"COLLATE='latin1_swedish_ci'\r\n" + 
				"ENGINE=InnoDB\r\n" + 
				";\r\n" + 
				"";
		funExecuteWebBooksQuery(sql);
		
		sql="ALTER TABLE `tblemployeemaster` ALTER `dteCreatedDate` DROP DEFAULT,ALTER `dteLastModified` DROP DEFAULT;";
		funExecuteWebBooksQuery(sql);
		
		sql="ALTER TABLE `tblemployeemaster` "
			+ "CHANGE COLUMN `dteCreatedDate` `dteCreatedDate` DATETIME NOT NULL AFTER `strEmployeeCode`,"
			+ "CHANGE COLUMN `dteLastModified` `dteLastModified` DATETIME NOT NULL AFTER `dteCreatedDate`,"
			+ "ADD COLUMN `strPropertyCode` VARCHAR(10) NOT NULL AFTER `strUserModified`;";
		funExecuteWebBooksQuery(sql);
		
		sql="ALTER TABLE `tblacmaster` ADD COLUMN `strCrDr` VARCHAR(5) NOT NULL DEFAULT '' AFTER `strEmployeeCode`;";
		funExecuteWebBooksQuery(sql);
		
		sql="ALTER TABLE `tbljvdtl` CHANGE COLUMN `strOneLine` `strOneLine` VARCHAR(20) NOT NULL DEFAULT '' AFTER `strNarration` ";
		funExecuteWebBooksQuery(sql);
		
		sql="ALTER TABLE `tblreceipthd` ADD COLUMN `strChequeClearence` VARCHAR(5) NOT NULL DEFAULT '' AFTER `intOnHold`; ";
		funExecuteWebBooksQuery(sql);

		sql="ALTER TABLE `tblreceipthd` DROP COLUMN `strChequeClearence`; ";
		funExecuteWebBooksQuery(sql);
		
		sql="ALTER TABLE `tblsundarydebtormaster` ADD COLUMN `dblConversion` DOUBLE NOT NULL DEFAULT '1' AFTER `strAccountCode`;";;
		funExecuteWebBooksQuery(sql);
		
		sql="ALTER TABLE `tblsundarycreditormaster` ADD COLUMN `dblConversion` DOUBLE NOT NULL DEFAULT '1' AFTER `strAccountCode`;";
		funExecuteWebBooksQuery(sql);

		
		sql="ALTER TABLE `tblpaymenthd` ADD COLUMN `strCurrency` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strPropertyCode` ";
		funExecuteWebBooksQuery(sql);
		
		sql="ALTER TABLE `tblpaymenthd` ADD COLUMN `dblConversion` DECIMAL(18,4) NOT NULL DEFAULT '1.0' AFTER `strCurrency` ";
		funExecuteWebBooksQuery(sql);
		
		sql="ALTER TABLE `tblreceipthd` ADD COLUMN `strCurrency` VARCHAR(10) NOT NULL DEFAULT '' AFTER `intOnHold` ";
		funExecuteWebBooksQuery(sql);
		
		sql="ALTER TABLE `tblreceipthd` ADD COLUMN `dblConversion` DECIMAL(18,4) NOT NULL DEFAULT '1.0' AFTER `strCurrency` ";
		funExecuteWebBooksQuery(sql);
		
		sql="ALTER TABLE `tbljvhd` ADD COLUMN `strCurrency` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strSourceDocNo`,"
				+ "ADD COLUMN `dblConversion` DECIMAL(18,4) NOT NULL DEFAULT '1.0' AFTER `strCurrency`";
		funExecuteWebBooksQuery(sql);
		
		sql="ALTER TABLE `tblauditdebtordtl` CHANGE COLUMN `strAccountCode` `strAccountCode` VARCHAR(15) NOT NULL AFTER `strGuest` ";
		funExecuteWebBooksQuery(sql);
		
		sql="ALTER TABLE `tblauditdtl` CHANGE COLUMN `strAccountCode` `strAccountCode` VARCHAR(15) NOT NULL AFTER `strTransNo` ";
		funExecuteWebBooksQuery(sql);
		

	    sql ="update tblreceipthd set strCurrency='' where strCurrency is null ";
		funExecuteWebBooksQuery(sql);
		
		sql = "ALTER TABLE `tblreceipthd` CHANGE COLUMN `strCurrency` `strCurrency` VARCHAR(10) NOT NULL DEFAULT '' AFTER `dblConversion` ";
		funExecuteWebBooksQuery(sql);
		
		sql ="update tblpaymenthd set strCurrency='' where strCurrency is null ";
		funExecuteWebBooksQuery(sql);
		
		sql = "ALTER TABLE `tblpaymenthd` CHANGE COLUMN `strCurrency` `strCurrency` VARCHAR(10) NOT NULL DEFAULT '' AFTER `dblConversion` ";
		funExecuteWebBooksQuery(sql);
		
		sql ="update tbljvhd set strCurrency='' where strCurrency is null ";
		funExecuteWebBooksQuery(sql);
		
		sql = "ALTER TABLE `tbljvhd` CHANGE COLUMN `strCurrency` `strCurrency` VARCHAR(10) NOT NULL DEFAULT '' AFTER `dblConversion` ";
		funExecuteWebBooksQuery(sql);

		sql="ALTER TABLE `tblledgersummary` ADD COLUMN `strDebtorName` VARCHAR(50) NOT NULL AFTER `strUserCode`;";
		funExecuteWebBooksQuery(sql);
		
		sql="ALTER TABLE `tblledgersummary` CHANGE COLUMN `strDebtorName` `strDebtorName` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strUserCode`;";
		funExecuteWebBooksQuery(sql);
		
		sql="ALTER TABLE `tblsundarydebtormaster` ADD COLUMN `strOperational` VARCHAR(3) NOT NULL DEFAULT '' AFTER `dblConversion`;";;
		funExecuteWebBooksQuery(sql);
		
		sql="ALTER TABLE `tblsundarycreditormaster` ADD COLUMN `strOperational` VARCHAR(3) NOT NULL DEFAULT '' AFTER `dblConversion`;";;
		funExecuteWebBooksQuery(sql);

		sql="update tblsundarycreditormaster set strOperational='Yes' where strOperational=''";
		funExecuteWebBooksQuery(sql);
		
		sql="update tblsundarydebtormaster set strOperational='Yes' where strOperational=''";
		funExecuteWebBooksQuery(sql);
		
		sql="ALTER TABLE `tblacmaster` "
		+" CHANGE COLUMN `intOpeningBal` `intOpeningBal` DECIMAL(18,4) NOT NULL DEFAULT '0.0000' AFTER `strChequeNo`;" ;
		funExecuteWebBooksQuery(sql);
		
		sql="ALTER TABLE `tblacmaster` DROP COLUMN `dblOpeningBal`";
		funExecuteWebBooksQuery(sql);
		
		
		sql="update tblreceipthd a set a.dblConversion=1.0 where a.dblConversion=0.0;";
		funExecuteWebBooksQuery(sql);
		
		
		sql="update tblpaymenthd a set a.dblConversion=1.0 where a.dblConversion=0.0;";
		funExecuteWebBooksQuery(sql);
		
		
		sql="update tbljvhd a set a.dblConversion=1.0 where a.dblConversion=0.0;";
		funExecuteWebBooksQuery(sql);
		
		sql=" ALTER TABLE `tblledgersummary` "
		   +" CHANGE COLUMN `strBalCrDr` `strBalCrDr` VARCHAR(255) NOT NULL DEFAULT '' AFTER `dteBillDate`, "
		   +" DROP PRIMARY KEY, "
		   +" ADD PRIMARY KEY (`strClientCode`, `strVoucherNo`, `strBalCrDr`);";
		funExecuteWebBooksQuery(sql);
		
		sql = " INSERT INTO `tbltreemast` (`strFormName`, `strFormDesc`, `strRootNode`, `intRootIndex`, `strType`, `intFormKey`, `intFormNo`, `strImgSrc`, `strImgName`, `strModule`, `strTemp`, `strActFile`, `strHelpFile`, `strProcessForm`, `strAutorisationForm`, `strRequestMapping`, `strAdd`, `strAuthorise`, `strDelete`, `strDeliveryNote`, `strDirect`, `strEdit`, `strGRN`, `strGrant`, `strMinimumLevel`, `strOpeningStock`, `strPrint`, `strProductionOrder`, `strProject`, `strPurchaseIndent`, `strPurchaseOrder`, `strPurchaseReturn`, `strRateContractor`, `strRequisition`, `strSalesOrder`, `strSalesProjection`, `strSalesReturn`, `strServiceOrder`, `strSubContractorGRN`, `strView`, `strWorkOrder`, `strAuditForm`, `strMIS`) VALUES "
				+ " ('frmSundryCreditorBill', 'Sundry Creditor Bill', 'Transaction', '1', 'T', '1', '1', '12', 'default.png', '5', '1', '1', '1', 'NO', '1', 'frmSundryCreditorBill.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmCreditorsOutStandingReport', 'Creditors Out Standing Report', 'Reports', '6', 'R', '6', '6', '1', 'default.png', '5', '1', '1', '1', 'NO', 'NO', 'frmCreditorsOutStandingReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ,"
				+ " ('frmDebitorOutStandingReport', 'Debitor Out Standing Report', 'Reports', '6', 'R', '7', '7', '1', 'default.png', '5', '1', '1', '1', 'NO', 'NO',  'frmDebitorOutStandingReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ,"
//				+ " ('frmDebtorOutStandingList', 'Debtor OutStanding List', 'Reports', '6', 'R', '8', '8', '1', 'default.png', '5', '1', '1', '1', 'NO', 'NO', 'frmDebtorOutStandingList.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ,"
				+ " ('frmTrialBalanceReport', 'Trial Balance Report', 'Reports', '6', 'R', '8', '8', '1', 'default.png', '5', '1', '1', '1', 'NO', 'NO', 'frmTrialBalanceReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmCashBook', 'Cash Book', 'Reports', '6', 'R', '9', '9', '1', 'default.png', '5', '1', '1', '1', 'NO', 'NO',  'frmCashBook.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ,"
				+ " ('frmTaxRegister', 'Tax Register', 'Reports', '6', 'R', '9', '9', '1', 'default.png', '5', '1', '1', '1', 'NO', 'NO', 'frmTaxRegister.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmARSecurityShell', 'Security Shell', 'Master', 7, 'M', 12, 12, '1', 'Security-Shell.png', '5', 1, '1', '1', 'NO', 'YES', 'frmARSecurityShell.html' , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmCreditorLegder', 'Creditor Ledger Tool', 'Tools', 1, 'L', 2, 2, '12', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmCreditorLedger.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmSundryCreditorMaster', 'Sundry Creditor Master', 'Master', 12, 'M', 12, 12, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmSundryCreditorMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmCreditorTrialBalanceReport', 'Creditor Trial Balance', 'Reports', 6, 'R', 73, 8, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmCreditorTrialBalanceReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmGeneralLedger', 'General Ledger Tool', 'Tools', 1, 'L', 2, 3, '12', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmGeneralLedger.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmDebtorTrialBalanceReport', 'Debtor Trial Balance', 'Reports', 6, 'R', 74, 9, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmDebtorTrialBalanceReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ,"
				+"  ('frmEmployeeMaster', 'Employee Master', 'Master', 13, 'M', 13, 13, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmEmployeeMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		        +"  ('frmWebBooksAgeingReport', 'Ageing Report', 'Reports', '6', 'R', '74', '74', '1', 'default.png', '5', '1', '1', '1', 'NO', 'NO', 'frmWebBooksAgeingReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		        +"  ('frmJVBookReport', 'JV Book Report', 'Reports', 6, 'R', 71, 13, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmJVBookReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),"
		        +"  ('frmIncomeStatement', 'Income Statement', 'Reports', '6', 'R', '1', '1', '1', 'default.png', '5', '1', '1', '1', 'NO', 'NO', 'frmIncomeStatement.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		        +"  ('frmBalanceSheet', 'Balance Sheet', 'Reports', '6', 'R', '1', '1', '1', 'default.png', '5', '1', '1', '1', 'NO', 'NO', 'frmBalanceSheet.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		        + " ('frmEmployeeLedger', 'Employee Ledger Tool', 'Tools', '1', 'L', '1', '1', '13', 'default.png', '5', '1', '1', '1', 'NO', '1', 'frmEmployeeLedger.html',NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),"
		        + " ('frmTaxReportDayWise', 'Tax Report Day Wise', 'Reports', 6, 'R', 75, 75, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmTaxReportDayWise.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),"
		        + " ('frmBankBalanceReport', 'Bank Balance Report', 'Reports', '6', 'R', '14', '14', '1', 'default.png', '5', '1', '1', '1', 'NO', 'NO', 'frmBankBalanceReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),"
				+ " ('frmEmployeeTrailBalanceReport', 'Employee Trail Balance Report', 'Reports', 6, 'R', 76, 76, '1', 'defaults.png', '5', 1, '1', '1', 'NO', 'NO', 'frmEmployeeTrailBalanceReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),"
				+ " ('frmChartOfAccountReport', 'Chart Of Account', 'Reports', 6, 'R', 77, 77, '1', 'defaults.png', '5', 1, '1', '1', 'NO', 'NO', 'frmChartOfAccountReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),"
				+ " ('frmDebtorTrailBalanceFlash', 'Debtor Trial Balance flash', 'Tools', 1, 'L', 2, 4, '1', 'defaults.png', '5', 1, '1', '1', 'NO', 'NO', 'frmDebtorTrailBalanceFlash.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),"
				+"  ('frmChequeIssued', 'Cheque Issued', 'Reports', 6, 'R', 70, 2, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmChequeIssued.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ,"
				+" ('frmChequeReceived', 'Cheque Received', 'Reports', 6, 'R', 70, 2, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmChequeReceived.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmBankReconciliation', 'Bank Reconciliation', 'Transaction', 1, 'T', 1, 2, '13', 'default.png', '5', '1', '1', '1', 'NO', '1', 'frmBankReconciliation.html',NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmBankReconciliationReport', 'Bank Reconciliation Report', 'Reports', '6', 'R', '9', '9', '1', 'default.png', '5', '1', '1', '1', 'NO', 'NO',  'frmBankReconciliationReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ,"
				+"  ('frmCreditorTrailBalanceFlash', 'Creditor Trail Balance Flash', 'Tools', 1, 'L', 3, 5, '1', 'defaults.png', '5', 1, '1', '1', 'NO', 'NO', 'frmCreditorTrailBalanceFlash.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ,"
     		    + " ('frmTrailBalanceFlash', 'Trial Balance flash', 'Tools', 1, 'L', 6, 6, '1', 'defaults.png', '5', 1, '1', '1', 'NO', 'NO', 'frmTrailBalanceFlash.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmBankBook', 'Bank Book', 'Reports', '6', 'R', '10', '10', '1', 'default.png', '5', '1', '1', '1', 'NO', 'NO',  'frmBankBook.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+"  ('frmUserDefineReport', 'User Defined Report', 'Master', '12', 'M', '20', '20', '1', 'default.png', '5', '1', '1', '1', 'NO', 'NO','frmUserDefineReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+"  ('frmUserDefineReportProcessing', 'User Defined Report Processing', 'Processing', '2', 'P', '3', '3', '1', 'default.png', '5', '1', '1', '1', 'NO', 'NO','frmUserDefineReportProcessing.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ";
		funExecuteQuery(sql);
		
		/*----------------WebBook Forms End---------------------------*/

		
		
		
		
		/*----------------WebPMS Forms Start---------------------------*/

		sql = " CREATE TABLE `tblBillDiscount` (  " + "	`strBillNo` VARCHAR(15) NOT NULL, " + "	`dteBillDate` VARCHAR(15) NOT NULL,  " + " `strCheckInNo` VARCHAR(15) NOT NULL, " + "	`strFolioNo` VARCHAR(15) NOT NULL, " + "	`strRoomNo` VARCHAR(10) NOT NULL, " + "	`dblDiscAmt` DECIMAL(18,2) NOT NULL DEFAULT '0.00', " + "	`dblGrandTotal` DECIMAL(18,2) NOT NULL DEFAULT '0.00',  "
				+ "	`strUserCreated` VARCHAR(10) NOT NULL," + "  `strUserEdited` VARCHAR(10) NOT NULL," + "  `dteDateCreated` DATETIME NOT NULL, " + "	`dteDateEdited` DATETIME NOT NULL,  " + "	`strClientCode` VARCHAR(10) NOT NULL, " + "	PRIMARY KEY (`strBillNo`, `strClientCode`) ) " + "  COLLATE='utf8_general_ci' ENGINE=InnoDB ;  ";

		funExecutePMSQuery(sql);

		sql="CREATE TABLE IF NOT EXISTS `tblmarketsource` (  "
			+" `strMarketSourceCode` VARCHAR(10) NOT NULL, "
			+" `strDescription` VARCHAR(100) NOT NULL, "
			+" `strReqSlipReqd` VARCHAR(1) NOT NULL, "
			+" `strUserCreated` VARCHAR(10) NOT NULL, "
			+" `strUserEdited` VARCHAR(10) NOT NULL, "
			+" `dteDateCreated` DATETIME NOT NULL, "
			+" `dteDateEdited` DATETIME NOT NULL, "
			+" `strClientCode` VARCHAR(10) NOT NULL, "
			+" PRIMARY KEY (`strMarketSourceCode`, `strClientCode`) "
			+" ) "
			+" COLLATE='utf8_general_ci' "
			+" ENGINE=InnoDB "; 
		funExecutePMSQuery(sql);
		
		sql = " CREATE TABLE IF NOT EXISTS `tblfloormaster` ( " 
				+" `strFloorCode` VARCHAR(10) NOT NULL DEFAULT '', " 
				+" `strFloorName` VARCHAR(50) NOT NULL DEFAULT '', " 
				+" `dblFloorAmt` DECIMAL(18,2) NOT NULL, " 
				+" `strUserCreated` VARCHAR(50) NOT NULL DEFAULT '', " 
				+" `strUserEdited` VARCHAR(50) NOT NULL DEFAULT '', " 
				+" `dteDateCreated` DATETIME NOT NULL, " 
				+" `dteDateEdited` DATETIME NOT NULL, " 
				+" `strClientCode` VARCHAR(50) NOT NULL DEFAULT '', " 
				+" PRIMARY KEY (`strFloorCode`, `strClientCode`) " 
				+" ) " 
				+" COLLATE='utf8_general_ci' " 
				+" ENGINE=InnoDB ";
		funExecutePMSQuery(sql);
		
		sql="CREATE TABLE IF NOT EXISTS `tblreservationroomratedtl` ( "
		   +" `strReservationNo` VARCHAR(15) NOT NULL, "
		   +" `dtDate` VARCHAR(10) NOT NULL, "
		   +" `strRoomNo` VARCHAR(10) NOT NULL, "
		   +" `dblRoomRate` DECIMAL(18,2) NOT NULL, "
		   +" `strClientCode` VARCHAR(10) NOT NULL "
		   +" ) "
		   +" COLLATE='utf8_general_ci' "
		   +" ENGINE=InnoDB ; " ;
		funExecutePMSQuery(sql);
		
		sql=" CREATE TABLE IF NOT EXISTS `tblwalkinroomratedtl` ( " 
				+" `strWalkinNo` VARCHAR(15) NOT NULL, " 
				+" `dtDate` VARCHAR(10) NOT NULL, " 
				+" `strRoomNo` VARCHAR(10) NOT NULL, " 
				+" `dblRoomRate` DECIMAL(18,2) NOT NULL, " 
				+" `strClientCode` VARCHAR(10) NOT NULL " 
				+" ) " 
				+" COLLATE='utf8_general_ci' " 
				+" ENGINE=InnoDB ; ";
		
		funExecutePMSQuery(sql);
		
		sql="CREATE TABLE `tblvoidbillhd` ( "
			+ " `strBillNo` VARCHAR(15) NOT NULL, "
			+ " `dteBillDate` DATETIME NOT NULL, "
			+ " `strCheckInNo` VARCHAR(15) NOT NULL, "
			+ " `strFolioNo` VARCHAR(15) NOT NULL, "
			+ " `strRoomNo` VARCHAR(10) NOT NULL  DEFAULT '', "
			+ " `strExtraBedCode` VARCHAR(10) NOT NULL  DEFAULT '', "
			+ " `strRegistrationNo` VARCHAR(15) NOT NULL, "
			+ " `strReservationNo` VARCHAR(15) NOT NULL  DEFAULT '', "
			+ " `dblGrandTotal` DECIMAL(18,2) NOT NULL  DEFAULT 0, "
			+ " `strUserCreated` VARCHAR(10) NOT NULL, "
			+ " `strUserEdited` VARCHAR(10) NOT NULL, "
			+ " `dteDateCreated` DATETIME NOT NULL, "
			+ " `dteDateEdited` DATETIME NOT NULL, "
			+ " `strClientCode` VARCHAR(10) NOT NULL, "
			+ " `strBillSettled` VARCHAR(5) NULL DEFAULT '', "
			+ " `strVoidType` VARCHAR(10) NULL DEFAULT '', "
			+ " PRIMARY KEY (`strBillNo`, `strClientCode`) "
			+ " ) "
			+ " COLLATE='utf8_general_ci' "
			+ " ENGINE=InnoDB; ";
		funExecutePMSQuery(sql);
		
		sql="CREATE TABLE `tblvoidbilldtl` ( "
				+ " `strBillNo` VARCHAR(15) NOT NULL, "
				+ " `strFolioNo` VARCHAR(15) NOT NULL, "
				+ " `dteDocDate` DATETIME NOT NULL, "
				+ " `strDocNo` VARCHAR(10) NOT NULL  DEFAULT '', "
				+ " `strPerticulars` VARCHAR(200) NOT NULL DEFAULT '', "
				+ " `strRevenueType` VARCHAR(255) NOT NULL DEFAULT '', "
				+ " `strRevenueCode` VARCHAR(15) NOT NULL DEFAULT '',  "
				+ " `dblDebitAmt` DECIMAL(18,2) NOT NULL DEFAULT 0, "
				+ " `dblCreditAmt` DECIMAL(18,2) NOT NULL DEFAULT 0, "
				+ " `dblBalanceAmt` DECIMAL(18,2) NOT NULL  DEFAULT 0, "
				+ " `strClientCode` VARCHAR(10) NOT NULL, "
				+ " INDEX `FK3FECBEF7DA3532E0` (`strBillNo`, `strClientCode`) "
				+ " ) "
				+ " COLLATE='utf8_general_ci' "
				+ " ENGINE=InnoDB  ";
		funExecutePMSQuery(sql);
		
		sql="CREATE TABLE `tblvoidbilltaxdtl` (	"
				+ "`strBillNo` VARCHAR(15) NOT NULL,	"
				+ "`strDocNo` VARCHAR(15) NOT NULL,	"
				+ "`strTaxCode` VARCHAR(10) NOT NULL,	"
				+ "`dblTaxableAmt` DECIMAL(18,2) NOT NULL, "
				+ "`dblTaxAmt` DECIMAL(18,2) NOT NULL,"
				+ "	`strClientCode` VARCHAR(10) NOT NULL,"
				+ " INDEX `FK19AB8F56DA3532E0` (`strBillNo`, `strClientCode`)) "
				+ "COLLATE='utf8_general_ci' "
				+ "ENGINE=InnoDB ;";
		funExecutePMSQuery(sql);
		
		sql="CREATE TABLE `tblchangedroomtypedtl` ("
				+ "`strDocNo` VARCHAR(255) NOT NULL,"
				+ "`strType` VARCHAR(50) NOT NULL,"
				+ "`strRoomNo` VARCHAR(50) NULL DEFAULT NULL,"
				+ "`strRoomType` VARCHAR(50) NULL DEFAULT NULL,"
				+ "`strGuestCode` VARCHAR(50) NOT NULL,"
				+ "`dteFromDate` VARCHAR(200) NOT NULL DEFAULT '',"
				+ "`dteToDate` VARCHAR(200) NOT NULL DEFAULT '',"
				+ "`strClientCode` VARCHAR(255) NOT NULL,"
				+ "INDEX `strRoomType` (`strRoomType`),INDEX `strClientCode` (`strClientCode`),INDEX `strDocNo` (`strDocNo`)"
				+ ")COLLATE='utf8_general_ci'ENGINE=InnoDB;";
		funExecutePMSQuery(sql);
		
		
		sql="CREATE TABLE `tblroompackagedtl` ("
				+ "`strWalkinNo` VARCHAR(15) NOT NULL,"
				+ "`strReservationNo` VARCHAR(15) NOT NULL,"
				+ " `strCheckInNo` VARCHAR(15) NOT NULL,"
				+ "`strPackageCode` VARCHAR(15) NOT NULL,"
				+ "`strIncomeHeadCode` VARCHAR(15) NOT NULL,"
				+ "`dblIncomeHeadAmt` DOUBLE NOT NULL,"
				+ "`strClientCode` VARCHAR(255) NOT NULL"
				+ ")COLLATE='utf8_general_ci'ENGINE=InnoDB;";
		funExecutePMSQuery(sql);
		
		sql=" CREATE TABLE `tblpackagemasterhd` ("
				+ "`strPackageCode` VARCHAR(10) NOT NULL,"
				+ "`strPackageName` VARCHAR(100) NOT NULL,"
				+ "`dblPackageAmt` DECIMAL(18,2) NOT NULL DEFAULT '0.00',"
				+ " `strUserCreated` VARCHAR(10) NOT NULL,"
				+ "`strUserEdited` VARCHAR(10) NOT NULL,"
				+ "`dteDateCreated` DATETIME NOT NULL,"
				+ "`dteDateEdited` DATETIME NOT NULL,"
				+ "`strClientCode` VARCHAR(10) NOT NULL,"
				+ "PRIMARY KEY (`strPackageCode`, `strClientCode`)"
				+ ")COLLATE='utf8_general_ci'ENGINE=InnoDB ; ";
		funExecutePMSQuery(sql);
		
		sql=" CREATE TABLE `tblpackagemasterdtl` ("
				+ "`strPackageCode` VARCHAR(15) NOT NULL,"
				+ "`strIncomeHeadCode` VARCHAR(15) NOT NULL,"
				+ "`dblAmt` DECIMAL(18,2) NOT NULL DEFAULT '0.00',"
				+ "`strClientCode` VARCHAR(10) NOT NULL,"
				+ "INDEX `FKB780D4CDF98AA58E` (`strPackageCode`, `strClientCode`)"
				+ ")COLLATE='utf8_general_ci'ENGINE=InnoDB; ";
		funExecutePMSQuery(sql);
		
		
		sql=" CREATE TABLE `tblchangeroom` ("
				+ "`strRoomNo` VARCHAR(10) NOT NULL,"
				+ "`strRoomTypeCode` VARCHAR(10) NOT NULL,"
				+ "`strFolioNo` VARCHAR(15) NOT NULL,"
				+ "`strGuestCode` VARCHAR(500) NOT NULL,"
				+ "`strReason` VARCHAR(50) NOT NULL,"
				+ "`strRemark` VARCHAR(200) NOT NULL,"
				+ "`strUserEdited` VARCHAR(10) NOT NULL,"
				+ "`dteChangeDate` DATETIME NOT NULL"
				+ ")COLLATE='utf8_general_ci'ENGINE=InnoDB; ";
		funExecutePMSQuery(sql);
		
		
		sql = " ALTER TABLE `tblroomcancelation` " + "	CHANGE COLUMN `strReservationNo` `strReservationNo` VARCHAR(12) NOT NULL FIRST;  ";

		funExecutePMSQuery(sql);

		sql="ALTER TABLE `tblreservationroomratedtl` "
			+" CHANGE COLUMN `strRoomNo` `strRoomType` VARCHAR(10) NOT NULL AFTER `dtDate`;";
		funExecutePMSQuery(sql);
		
		sql = " ALTER TABLE `tblreservationhd` " + "	ADD COLUMN `strNoRoomsBooked` VARCHAR(10) NOT NULL DEFAULT '1' AFTER `intNoOfChild`; ";
		funExecutePMSQuery(sql);

		sql = " ALTER TABLE `tblreservationdtl`" + "	ADD COLUMN `strPayee` VARCHAR(1) NOT NULL AFTER `strGuestCode`; ";

		funExecutePMSQuery(sql);

		sql = " ALTER TABLE `tblfoliohd` " + "	ADD COLUMN `strWalkInNo` VARCHAR(15) NOT NULL DEFAULT '' AFTER `strGuestCode`; ";

		funExecutePMSQuery(sql);

		sql = "  ALTER TABLE `tblreceipthd` " + "	ADD COLUMN `strFlagOfAdvAmt` VARCHAR(3) NOT NULL DEFAULT 'N' AFTER `strClientCode`; ";

		funExecutePMSQuery(sql);

		sql = " ALTER TABLE `tblreservationhd` 	" + "	ADD COLUMN `strGuestcode` VARCHAR(15) NOT NULL AFTER `strNoRoomsBooked`; ";

		funExecutePMSQuery(sql);

		sql = " ALTER TABLE `tblbilldtl` " + "	CHANGE COLUMN `strRevenueType` `strRevenueType` VARCHAR(255) NOT NULL AFTER `strPerticulars`; ";

		funExecutePMSQuery(sql);

		sql = " ALTER TABLE `tblbillhd` " + "	ADD COLUMN `strBillSettled` VARCHAR(1) NOT NULL DEFAULT 'N' AFTER `strClientCode`; ";
		funExecutePMSQuery(sql);

		sql = "  ALTER TABLE `tblpropertysetup` " + "	ADD COLUMN `strSMSProvider` VARCHAR(50) NOT NULL DEFAULT '' AFTER `tmeCheckOutTime`, " + "	ADD COLUMN `strSMSAPI` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strSMSProvider`, " + "	ADD COLUMN `strReservationSMSContent` VARCHAR(500) NOT NULL DEFAULT '' AFTER `strSMSAPI`, "
				+ "	ADD COLUMN `strCheckInSMSContent` VARCHAR(500) NOT NULL DEFAULT '' AFTER `strReservationSMSContent`, " + "	ADD COLUMN `strAdvAmtSMSContent` VARCHAR(500) NOT NULL DEFAULT '' AFTER `strCheckInSMSContent`, " + "	ADD COLUMN `strCheckOutSMSContent` VARCHAR(500) NOT NULL DEFAULT '' AFTER `strAdvAmtSMSContent`; ";
		funExecutePMSQuery(sql);

		sql="ALTER TABLE `tblguestmaster` "
				+ " ADD COLUMN `strGSTNo` VARCHAR(50) NOT NULL AFTER `strClientCode`;" ;
		funExecutePMSQuery(sql);
		
		sql="ALTER TABLE `tblroomcancelation` "
				+ " ADD COLUMN `strRemarks` VARCHAR(200) NOT NULL AFTER `strUserEdited`;";
		funExecutePMSQuery(sql);
		
		sql="ALTER TABLE `tblroomcancelation` ADD COLUMN `strReasonCode` VARCHAR(50) NOT NULL AFTER `strRemarks`;" ;
		funExecutePMSQuery(sql);
		
		sql="ALTER TABLE `tblreservationhd` ADD COLUMN `strOTANo` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strGuestcode`;" ;
		funExecutePMSQuery(sql);
		
		sql="ALTER TABLE `tblreservationhd` ADD COLUMN `strMarketSourceCode` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strOTANo`;;" ;
		funExecutePMSQuery(sql);
		
		sql="ALTER TABLE `tblreservationdtl` ADD COLUMN `strRemark` VARCHAR(200) NOT NULL AFTER `strPayee`; ";
		funExecutePMSQuery(sql);
		
		sql=" ALTER TABLE `tblreservationdtl` ADD COLUMN `strAddress` VARCHAR(200) NOT NULL AFTER `strRemark`; ";
		funExecutePMSQuery(sql);
		
		sql="ALTER TABLE `tblwalkindtl` ADD COLUMN `strRemark` VARCHAR(100) NOT NULL AFTER `strGuestCode`; ";
		funExecutePMSQuery(sql);
		
		sql="ALTER TABLE `tblguestmaster` ADD COLUMN `strUIDNo` VARCHAR(50) NOT NULL AFTER `strGSTNo`; ";
		funExecutePMSQuery(sql);
		
		sql="ALTER TABLE `tblguestmaster` ADD COLUMN `dteAnniversaryDate` DATETIME NOT NULL AFTER `strUIDNo`; ";
		funExecutePMSQuery(sql);
		
		sql="ALTER TABLE `tblpropertysetup` ADD COLUMN `strRoomLimit` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strSMSProvider`; ";
		funExecutePMSQuery(sql);
		
		sql="ALTER TABLE `tblreservationhd` ADD COLUMN `strIncomeHeadCode` VARCHAR(100) NOT NULL DEFAULT '' AFTER `strMarketSourceCode`; ";
		funExecutePMSQuery(sql);
		
		sql= " ALTER TABLE `tblwalkinhd` ADD COLUMN `strIncomeHeadCode` VARCHAR(100) NOT NULL DEFAULT '' AFTER `strRoomNo` ; ";
		funExecutePMSQuery(sql);
		
//		sql = " ALTER TABLE `tblroom` ALTER `strFloorNo` DROP DEFAULT ; ";
//		funExecutePMSQuery(sql);
//		
//		sql= " ALTER TABLE `tblroom` CHANGE COLUMN `strFloorNo` `strFloorCode` VARCHAR(50) NOT NULL AFTER `strRoomTypeCode` ; ";
//		funExecutePMSQuery(sql); 
		
		sql="ALTER TABLE `tblroom` "
			+" CHANGE COLUMN `strFloorNo` `strFloorCode` VARCHAR(50) NOT NULL AFTER `strRoomTypeCode`;" ;
		funExecutePMSQuery(sql);
				
		sql="ALTER TABLE `tblreservationhd` ADD COLUMN `tmeDropTime` VARCHAR(10) NOT NULL AFTER `strIncomeHeadCode`;";
		funExecutePMSQuery(sql);
		
		sql="ALTER TABLE `tblreservationhd` ADD COLUMN `tmePickUpTime` VARCHAR(10) NOT NULL AFTER `tmeDropTime`; ";
		funExecutePMSQuery(sql);
		

		
		sql="ALTER TABLE `tblguestmaster` "
		+" ADD COLUMN `strDefaultAddr` VARCHAR(20) NOT NULL DEFAULT '' AFTER `dteAnniversaryDate`, "
		+" ADD COLUMN `strAddressLocal` VARCHAR(500) NOT NULL DEFAULT '' AFTER `strDefaultAddr`, "
		+" ADD COLUMN `strCityLocal` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strAddressLocal`, "
		+" ADD COLUMN `strStateLocal` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strCityLocal`, "
		+" ADD COLUMN `strCountryLocal` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strStateLocal`, "
		+" ADD COLUMN `intPinCodeLocal` INT(11) NOT NULL DEFAULT 0 AFTER `strCountryLocal`, "
		+" ADD COLUMN `strAddrPermanent` VARCHAR(500) NOT NULL DEFAULT '' AFTER `intPinCodeLocal`, "
		+" ADD COLUMN `strCityPermanent` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strAddrPermanent`, "
		+" ADD COLUMN `strStatePermanent` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strCityPermanent`, "
		+" ADD COLUMN `strCountryPermanent` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strStatePermanent`, "
		+" ADD COLUMN `intPinCodePermanent` INT(11) NOT NULL DEFAULT 0 AFTER `strCountryPermanent`, "
		+" ADD COLUMN `strAddressOfc` VARCHAR(500) NOT NULL DEFAULT '' AFTER `intPinCodePermanent`, "
		+" ADD COLUMN `strCityOfc` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strAddressOfc`, "
		+" ADD COLUMN `strStateOfc` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strCityOfc`, "
		+" ADD COLUMN `strCountryOfc` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strStateOfc`, "
		+" ADD COLUMN `intPinCodeOfc` INT(11) NOT NULL DEFAULT 0 AFTER `strCountryOfc`; ";
		funExecutePMSQuery(sql);

		sql="ALTER TABLE `tblcheckinhd` "
	       +" ADD COLUMN `strNoPostFolio` VARCHAR(1) NOT NULL DEFAULT 'N' AFTER `intNoOfChild`; ";
		funExecutePMSQuery(sql);
		
		sql="ALTER TABLE `tblwalkinroomratedtl` "
          +" CHANGE COLUMN `strRoomNo` `strRoomType` VARCHAR(10) NOT NULL AFTER `dtDate`; ";
		funExecutePMSQuery(sql);
		
		sql=" ALTER TABLE `tblcheckinhd` " 
				+" ADD COLUMN `strRemarks` VARCHAR(200) NOT NULL AFTER `strNoPostFolio`, " 
				+" ADD COLUMN `strReasonCode ` VARCHAR(50) NOT NULL AFTER `strRemarks`; ";
		funExecutePMSQuery(sql);
		
		sql="ALTER TABLE `tblvoidbillhd` " 
				+" CHANGE COLUMN `strVoidType` `strVoidType` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strBillSettled`, " 
				+" ADD COLUMN `strReasonCode` VARCHAR(10) NOT NULL DEFAULT '' AFTER `strVoidType`, " 
				+" ADD COLUMN `strReasonName` VARCHAR(100) NOT NULL DEFAULT '' AFTER `strReasonCode`, " 
				+" ADD COLUMN `strRemark` VARCHAR(250) NOT NULL DEFAULT '' AFTER `strReasonName`; "; 		
		funExecutePMSQuery(sql);
		
		sql="ALTER TABLE `tblbillhd` "
			    + " ADD COLUMN `strGSTNo` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strBillSettled`;"; 		
		funExecutePMSQuery(sql);
		
		
		sql="ALTER TABLE `tblpropertysetup`"
				+ "ADD COLUMN `strGSTNo` VARCHAR(50) NOT NULL DEFAULT '' AFTER `strRoomLimit`";
		funExecutePMSQuery(sql);
		
		sql=" ALTER TABLE `tblbillhd`"
			+ "ADD COLUMN `strCompanyName` VARCHAR(100) NOT NULL DEFAULT '' AFTER `strGSTNo`;" ;
		funExecutePMSQuery(sql);
		
		sql=" ALTER TABLE `tblroompackagedtl`"
				+ " ADD COLUMN `strType` VARCHAR(15) NOT NULL AFTER `dblIncomeHeadAmt`,"
				+ " ADD COLUMN `strRoomNo` VARCHAR(15) NOT NULL AFTER `strType`; ";
		funExecutePMSQuery(sql);
			
		
	
		
		
		
		
		
		// For PMS Form Of Tree master Start///
		sql = " INSERT INTO `tbltreemast` (`strFormName`, `strFormDesc`, `strRootNode`, `intRootIndex`, `strType`, `intFormKey`, `intFormNo`, `strImgSrc`, `strImgName`, `strModule`, `strTemp`, `strActFile`, `strHelpFile`, `strProcessForm`, `strAutorisationForm`, `strRequestMapping`, `strAdd`, `strAuthorise`, `strDelete`, `strDeliveryNote`, `strDirect`, `strEdit`, `strGRN`, `strGrant`, `strMinimumLevel`, `strOpeningStock`, `strPrint`, `strProductionOrder`, `strProject`, `strPurchaseIndent`, `strPurchaseOrder`, `strPurchaseReturn`, `strRateContractor`, `strRequisition`, `strSalesOrder`, `strSalesProjection`, `strSalesReturn`, `strServiceOrder`, `strSubContractorGRN`, `strView`, `strWorkOrder`, `strAuditForm`, `strMIS`) VALUES "

				+ " ('frmAgentCommision', 'Agent Commision', 'Master', 1, 'M', 13, 13, '1', 'default.png', '3', 1, '1', '1', 'NO', 'NO', 'frmAgentCommision.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmAgentMaster', 'Agent Master', 'Master', 1, 'M', 12, 12, '1', 'default.png', '3', 1, '1', '1', 'NO', 'NO', 'frmAgentMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				// +" ('frmAttributeValueMaster', 'Attribute Value Master', 'Master', 1, 'M', 15, 15, '3', 'Attribute-Value-Master.png', '3', 3, '3', '3', 'NO', 'YES', 'frmAttributeValueMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmBaggageMaster', 'Baggage Master', 'Master', 1, 'M', 21, 21, '1', 'default.png', '3', 3, '3', '3', 'NO', 'YES', 'frmBaggageMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmBathTypeMaster', 'BathType Master', 'Master', 1, 'M', 19, 19, '2', 'default.png', '3', 3, '3', '3', 'NO', 'YES', 'frmBathTypeMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmBillingInstructions', 'Billing Instructions', 'Master', 1, 'M', 14, 14, '1', 'default.png', '3', 3, '3', '3', 'NO', 'NO', 'frmBillingInstructions.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmBillPrinting', 'Bill Printing', 'Transaction', 2, 'T', 10, 10, '5', 'default.png', '3', 5, '5', '5', 'NO', 'NO', 'frmBillPrinting.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmBookerMaster', 'Booker Master', 'Master', 1, 'M', 16, 16, '1', 'default.png', '3', 1, '1', '1', 'NO', 'NO', 'frmBookerMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmBookingType', 'Booking Type', 'Master', 1, 'M', 5, 5, '1', 'default.png', '3', 3, '3', '3', 'NO', 'NO', 'frmBookingType.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmBusinessSource', 'BusinessSource Master', 'Master', 1, 'M', 17, 17, '8', 'default.png', '3', 3, '3', '3', 'NO', 'YES', 'frmBusinessSource.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmChargePosting', 'ChargePosting Master', 'Master', 1, 'M', 18, 18, '10', 'default.png', '3', 3, '3', '3', 'NO', 'YES', 'frmChargePosting.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmCheckIn', 'Check In', 'Transaction', 2, 'T', 2, 2, '1', 'default.png', '3', 1, '1', '1', 'NO', 'NO', 'frmCheckIn.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmCheckInList', 'Check In List', 'Reports', 3, 'R', 3, 3, '5', 'default.png', '3', 1, '1', '1', 'NO', 'NO', 'frmCheckInList.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmCheckOut', 'Check Out', 'Transaction', 2, 'T', 3, 3, '1', 'default.png', '3', 2, '2', '2', 'NO', 'NO', 'frmCheckOut.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmCheckOutList', 'Check Out List', 'Reports', 3, 'R', 4, 4, '6', 'default.png', '3', 1, '1', '1', 'NO', 'NO', 'frmCheckOutList.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmCorporateMaster', 'Corporate Master', 'Master', 1, 'M', 11, 11, '1', 'default.png', '3', 1, '1', '1', 'NO', 'NO', 'frmCorporateMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmDayEnd', 'Day End', 'Transaction', 2, 'T', 11, 11, '13', 'default.png', '3', 3, '3', '3', 'NO', 'YES', 'frmDayEnd.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmDepartmentMaster', 'Department Master', 'Master', 1, 'M', 10, 10, '1', 'default.png', '3', 3, '3', '3', 'NO', 'YES', 'frmDepartmentMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExpectedArrivalList', 'Expected Arrival List', 'Reports', 3, 'R', 6, 6, '1', 'default.png', '3', 1, '1', '1', 'NA', 'NA', 'frmExpectedArrivalList.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExpectedDepartureList', 'Expected Departure List', 'Reports', 3, 'R', 5, 5, '1', 'default.png', '3', 1, '1', '1', 'NO', 'NO', 'frmExpectedDepartureList.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmExtraBed', 'ExtraBed Master', 'Master', 1, 'M', 4, 4, '9', 'default.png', '3', 3, '3', '3', 'NO', 'YES', 'frmExtraBed.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmFolioPosting', 'Folio Posting', 'Transaction', 2, 'T', 8, 8, '1', 'default.png', '3', 5, '5', '3', 'NO', 'YES', 'frmFolioPosting.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmFolioPrinting', 'Folio Printing', 'Transaction', 2, 'T', 9, 9, '5', 'default.png', '3', 5, '5', '5', 'NO', 'NO', 'frmFolioPrinting.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmGuestMaster', 'Guest Master', 'Master', 1, 'M', 8, 8, '1', 'default.png', '3', 3, '3', '3', 'NO', 'NO', 'frmGuestMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmIncomehead', 'IncomeHead Master', 'Master', 1, 'M', 2, 2, '7', 'default.png', '3', 3, '3', '3', 'NO', 'YES', 'frmIncomehead.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmPlanMaster', 'Plan Master', 'Master', 1, 'M', 22, 22, '6', 'default.png', '3', 3, '3', '3', 'NO', 'YES', 'frmPlanMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmPMSPayment', 'Payment', 'Transaction', 2, 'T', 7, 7, '15', 'default.png', '3', 3, '3', '3', 'NO', 'YES', 'frmPMSPayment.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmPMSTaxMaster', 'Tax Master', 'Master', 1, 'M', 6, 6, '1', 'default.png', '3', 3, '3', '3', 'NO', 'NO', 'frmPMSTaxMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmPostRoomTerrif', 'Post Room Terrif', 'Transaction', 2, 'T', 5, 5, '1', 'default.png', '3', 3, '3', '3', 'NO', 'NO', 'frmPostRoomTerrif.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmPropertySetup', 'Property Setup', 'Tools', 4, 'L', 1, 1, '12', 'default.png', '3', 1, '1', '1', 'NO', 'NO', 'frmPropertySetup.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmReason', 'Reason Master', 'Master', 1, 'M', 7, 7, '5', 'default.png', '3', 3, '3', '3', 'NO', 'YES', 'frmReason.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmReservation', 'Reservation', 'Transaction', 2, 'T', 1, 1, '1', 'default.png', '3', 2, '2', '2', 'NO', 'NO', 'frmReservation.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmRevenueHeadReport', 'Revenue Head Report', 'Reports', 3, 'R', 1, 1, '1', 'default.png', '3', 1, '1', '1', 'NO', 'NO', 'frmRevenueHeadReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmRoomCancellation', 'Room Cancellation', 'Transaction', 2, 'T', 6, 6, '1', 'default.png', '3', 3, '3', '3', 'NO', 'NO', 'frmRoomCancellation.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmRoomMaster', 'Room Master', 'Master', 1, 'M', 1, 1, '1', 'default.png', '3', 3, '3', '3', 'NO', 'NO', 'frmRoomMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmRoomStatusDiary', 'Room Status Diary', 'Tools', 4, 'L', 1, 2, '12', 'default.png', '3', 1, '1', '1', 'NO', 'NO', 'frmRoomStatusDiary.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmRoomTypeMaster', 'RoomType Master', 'Master', 1, 'M', 3, 3, '4', 'default.png', '3', 3, '3', '3', 'NO', 'YES', 'frmRoomTypeMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmSettlementMaster', 'Settlement Master', 'Master', 1, 'M', 9, 9, '11', 'default.png', '3', 3, '3', '3', 'NO', 'YES', 'frmSettlementMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmTaxGroupMaster', 'Tax Group Master', 'Master', 1, 'M', 20, 20, '1', 'default.png', '3', 3, '3', '3', 'NO', 'NO', 'frmTaxGroupMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmTaxReport', 'Tax Summary', 'Reports', 3, 'R', 2, 2, '5', 'default.png', '3', 5, '5', '5', 'NO', 'NO', 'frmTaxReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmWalkin', 'Walk In', 'Transaction', 2, 'T', 4, 4, '12', 'default.png', '3', 3, '3', '3', 'NO', 'YES', 'frmWalkin.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmCancelationReport', 'Cancellation Report', 'Reports', 3, 'R', 3, 6, '6', 'default.png', '3', 1, '1', '1', 'NO', 'NO', 'frmCancelationReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmPMSFlash', 'PMS Flash', 'Tools', 1, 'L', 3, 43, '1', 'default.png', '3', 1, '1', '1', 'NO', 'NO', 'frmPMSFlash.html' , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "

				+ " ('frmBillDiscount', 'Bill Discount', 'Transaction', '2', 'T', '12', '12', '1', 'default.png', '3', '3', '3', '3', 'NO', 'YES', 'frmBillDiscount.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmSplitBill', 'Split Bill', 'Transaction', '2', 'T', '3', '3', '1', 'default.png', '3', '2', '2', '2', 'NO', 'NO', 'frmSplitBill.html' , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmReservationFlash', 'Reservation Flash', 'Tools', '4', 'L', '1', '3', '12', 'default.png', '3', '1', '1', '1', 'NO', 'NO', 'frmReservationFlash.html' , NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmGuestListReport', 'Guest List Report', 'Reports', '3', 'R', '7', '7', '5', 'default.png', '3', '5', '5', '5', 'NO', 'NO', 'frmGuestListReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmRoomTypeInventory', 'Room Type Inventory', 'Reports', '3', 'R', '6', '7', '1', 'default.png', '3', '1', '1', '1','NO', 'NO', 'frmRoomTypeInventoryReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmDashboard', 'Dashboard', 'Reports', 3, 'R', 3, 3, '5', 'default.png', '3', 1, '1', '1','NO', 'NO', 'frmDashboard.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
				+ " ('frmPaymentDashboard', 'Payment Dashboard', 'Reports', 3, 'R', 3, 3, '5', 'default.png', '3', 1, '1', '1','NO', 'NO', 'frmPaymentDashboard.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
                +"  ('frmMarketSource', 'MarketSource Master', 'Master', 1, 'M', 23, 23, '1', 'default.png', '3', 3, '3', '3', 'NO', 'NO', 'frmMarketSource.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), " 
		        +"  ('frmCheckoutDiscount', 'Checkout Discount Master', 'Master', '1', 'M', '1', '1', '1', 'default.png', '3', '3', '3', '3', 'NO', 'NO', 'frmCheckoutDiscountMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		        +"  ('frmGuestHistoryReport', 'Guest History Report', 'Reports', 3, 'R', 8, 8, '5', 'default.png', '3', 5, '5', '5', 'NO', 'NO', 'frmGuestHistoryReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ,"
		        +"  ('frmNoShowReport', 'No Show Report', 'Tools', '1', 'L', '3', '44', '1', 'default.png', '3', '1', '1', '1', 'NO', 'NO', 'frmNoShowReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ,"
		        +"  ('frmFloorMaster', 'Floor Master', 'Master', 1, 'M', 24, 24, '1', 'default.png', '3', 3, '3', '3', 'NO', 'NO', 'frmFloorMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) , "
				+"  ('frmReOpenFolio', 'ReOpen Folio', 'Transaction', 2, 'T', 2, 2, '1', 'default.png', '3', 1, '1', '1', 'NO', 'NO', 'frmReOpenFolio.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ,"
		        +"  ('frmProvisionBill', 'Provision Bill', 'Reports', 3, 'R', 6, 6, '1', 'default.png', '3', 1, '1', '1', 'NA', 'NA', 'frmProvisionBill.html',NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),  "
		        + "  ('frmVoidBill', 'Void Bill', 'Transaction', 2, 'T', 15, 15, '1', 'default.png', '3', 2, '2', '2', 'NO', 'NO', 'frmVoidBill.html',NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),  "
		        + " ('frmChangeRoom', 'Change Room', 'Transaction', 2, 'T', 2, 2, '1', 'default.png', '3', 1, '1', '1', 'NO', 'NO', 'frmChangeRoom.html',NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ,"
	            +" ('frmPaxReport', 'Pax Report', 'Reports', '3', 'R', '9', '9', '5', 'default.png', '3', '5', '5', '5','NA', 'NA', 'frmPaxReport.html',NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), " 
		 		+" ('frmVoidBillReport', 'Void Bill Report', 'Reports', 3, 'R', 10, 10, '5', 'default.png', '3', 5, '5', '5','NA', 'NA', 'frmVoidBillReport.html',NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL,NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 		+" ('frmChangeGSTNoOnBill', 'Change GST No On Bill', 'Transaction', 2, 'T', 2, 2, '1', 'default.png', '3', 1, '1', '1', 'NO', 'NO', 'frmChangeGSTNoOnBill.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),"
		 		+ " ('frmChangedRoomTypeReport', 'Changed RoomType Report', 'Reports', 2, 'R', 2, 2, '1', 'default.png', '3', 1, '1', '1', 'NO', 'NO', 'frmChangedRoomTypeReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),"
		 		+ " ('frmModifyBillReport', 'Modify Bill Report', 'Reports', 3, 'R', 11, 11, '5', 'default.png', '3', 5, '5', '5', 'NO', 'NO', 'frmModifyBillReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),"
		 		+ " ('frmChangeRoomReport', 'Change Room Report', 'Reports', '3', 'R', '11', '11', '5', 'default.png', '3', '5', '5', '5', 'NO', 'NO','frmChangeRoomReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),"
		 		+ " ('frmPMSSecurityShell', 'Security Shell', 'Master', 1, 'M', 8, 8, '1', 'default.png', '3', 3, '3', '3', 'NO', 'NO', 'frmPMSSecurityShell.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ";
		
		
		funExecuteQuery(sql);

		
		sql = "UPDATE `tbltreemast` SET `strFormName`='frmPostRoomTariff', `strFormDesc`='Post Room Tariff' WHERE  `strFormName`='frmPostRoomTerrif' AND `strModule`='3';";
		funExecuteQuery(sql);
		
		sql = "UPDATE `tbltreemast` SET `strFormDesc`='Reservation Cancellation' WHERE  `strFormName`='frmRoomCancellation' AND `strModule`='3';";
		funExecuteQuery(sql);

		// / END ///

		/*----------------WebPMS Forms End---------------------------*/

	}

	@SuppressWarnings("finally")
	private int funExecuteQuery(String sql) {
		try {
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
			query.executeUpdate();
		} catch (Exception e) {
		} finally {
			return 0;
		}
	}
	
	
	@SuppressWarnings("finally")
	private int funExecute(String sql) {
		int i=0;
		try {
			Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
			query.list();
		} catch (Exception e) {
			i=1;
		} finally {
			return i;
		}
	}
	

	@SuppressWarnings("finally")
	private int funExecuteWebBooksQuery(String sql) {
		try {
			Query query = webBooksSessionFactory.getCurrentSession().createSQLQuery(sql);
			query.executeUpdate();
		} catch (Exception e) {
		} finally {
			return 0;
		}
	}

	@SuppressWarnings("finally")
	private int funExecutePMSQuery(String sql) {
		try {
			Query query = webPMSSessionFactory.getCurrentSession().createSQLQuery(sql);
			query.executeUpdate();
		} catch (Exception e) {
		} finally {
			return 0;
		}
	}

	@Override
	public void funClearTransaction(String clientCode, String[] str) {
		for (int i = 0; i < str.length; i++) {
			String sql = "";
			switch (str[i]) {

			// //////////////////////WebStock Transaction
			// Start///////////////////////////////////////////

			case "Bill Passing": {
				sql = "truncate table tblbillpassdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblbillpasshd";
				funExecuteQuery(sql);
				sql = "truncate table tblbillpassingtaxdtl";
				funExecuteQuery(sql);
				break;
			}

			case "GRN(Good Receiving Note)": {
				sql = "truncate table tblgrndtl";
				funExecuteQuery(sql);
				sql = "truncate table tblgrnhd";
				funExecuteQuery(sql);
				sql = "truncate table tblgrntaxdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblgrnmisdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblbatchhd";
				funExecuteQuery(sql);

				break;
			}

			case "Opening Stock": {
				sql = "truncate table tblinitialinvdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblinitialinventory";
				funExecuteQuery(sql);
				break;
			}

			case "Material Return": {
				sql = "truncate table tblmaterialreturndtl";
				funExecuteQuery(sql);
				sql = "truncate table tblmaterialreturnhd";
				funExecuteQuery(sql);
				break;
			}

			case "Material Issue Slip": {
				sql = "truncate table tblmisdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblmishd";
				funExecuteQuery(sql);
				sql = "truncate table tblmrpidtl";
				funExecuteQuery(sql);
				break;
			}

			case "Material Production": {
				sql = "truncate table tblproductiondtl";
				funExecuteQuery(sql);
				sql = "truncate table tblproductionhd";
				funExecuteQuery(sql);
				break;
			}
			case "Meal Planing": {
				sql = "truncate table tblproductionorderdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblproductionorderhd";
				funExecuteQuery(sql);
				break;
			}

			case "Purchase Indent": {
				sql = "truncate table tblpurchaseindenddtl";
				funExecuteQuery(sql);
				sql = "truncate table tblpurchaseindendhd";
				funExecuteQuery(sql);
				break;
			}

			case "Purchase Order": {
				sql = "truncate table tblpurchaseorderdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblpurchaseorderhd";
				funExecuteQuery(sql);
				sql = "truncate table tblpotaxdtl";
				funExecuteQuery(sql);
				break;
			}

			case "Purchase Return": {
				sql = "truncate table tblpurchasereturndtl";
				funExecuteQuery(sql);
				sql = "truncate table tblpurchasereturnhd";
				funExecuteQuery(sql);
				break;
			}

			case "Material Requisition": {
				sql = "truncate table tblreqdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblreqhd";
				funExecuteQuery(sql);
				break;
			}

			case "Stock Adjustment": {
				sql = "truncate table tblstockadjustmentdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblstockadjustmenthd";
				funExecuteQuery(sql);
				break;
			}

			case "Physical Stk Posting": {
				sql = "truncate table tblstockpostingdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblstockpostinghd";
				funExecuteQuery(sql);
				break;
			}

			case "Stock Transfer": {
				sql = "truncate table tblstocktransferdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblstocktransferhd";
				funExecuteQuery(sql);
				break;
			}

			case "Work Order": {
				sql = "truncate table tblworkorderdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblworkorderhd";
				funExecuteQuery(sql);
				break;
			}

			case "Rate Contract": {
				sql = "truncate table tblratecontdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblrateconthd";
				funExecuteQuery(sql);
				break;
			}

			// //////////////////////WebStock Transaction
			// End///////////////////////////////////////////

			// //////////////////////WebCRM Transaction
			// Start///////////////////////////////////////////

			case "Delivery Challan": {
				sql = "truncate table tbldeliverychallanhd";
				funExecuteQuery(sql);
				sql = "truncate table tbldeliverychallandtl";
				funExecuteQuery(sql);
				break;
			}

			case "Delivery Note": {
				sql = "truncate table tbldeliverynotehd";
				funExecuteQuery(sql);
				sql = "truncate table tbldeliverynotedtl";
				funExecuteQuery(sql);
				break;
			}

			case "Excise Challan": {

				break;
			}

			case "Invoice": {
				sql = "truncate table tblinvoicedtl";
				funExecuteQuery(sql);
				sql = "truncate table tblinvoicehd";
				funExecuteQuery(sql);
				sql = "truncate table tblinvprodtaxdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblinvsalesorderdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblinvtaxdtl";
				funExecuteQuery(sql);

				break;
			}

			case "Job Order Allocation": {
				sql = "truncate table tbljoborderallocationhd";
				funExecuteQuery(sql);
				sql = "truncate table tbljoborderallocationdtl";
				funExecuteQuery(sql);
				break;
			}

			case "Job Order": {
				sql = "truncate table tbljoborderhd";
				funExecuteQuery(sql);

				break;

			}

			case "Sales Order": {
				sql = "truncate table tblsalesorderhd";
				funExecuteQuery(sql);
				sql = "truncate table tblsalesorderdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblsaleschar";
				funExecuteQuery(sql);

				break;

			}

			case "Sales Order BOM": {
				sql = "truncate table tblsalesorderbom";
				funExecuteQuery(sql);

				break;

			}

			case "Sub Contractor GRN": {
				sql = "truncate table tblscreturnhd";
				funExecuteQuery(sql);
				sql = "truncate table tblscreturndtl";
				funExecuteQuery(sql);

				break;

			}

			// //////////////////////WebCRM Transaction
			// END///////////////////////////////////////////
			default: {
				sql = "truncate table tbltempitemstock";
				funExecuteQuery(sql);
				sql = "truncate table tblpossalesdtl";
				funExecuteQuery(sql);

			}

			}

		}
	}

	@Override
	public void funClearTransactionByPropertyAndLoction(String clientCode, String[] str, String propName, String locName) {
		for (int i = 0; i < str.length; i++) {
			String sql = "";
			List<String> listPropertyCode = new ArrayList<>();
			String sqlPropertyCode = "select strPropertyCode from tblpropertymaster where strPropertyName='" + propName + "' and strClientCode='" + clientCode + "' ";
			listPropertyCode = objGlobalFunctionsService.funGetDataList(sqlPropertyCode, "sql");
			String propCode = listPropertyCode.get(0);
			List<String> listlocCode = new ArrayList<>();
			String sqllocCode = "select strLocCode from tblpropertymaster where strLocName='" + locName + "' and strClientCode='" + clientCode + "' ";
			listlocCode = objGlobalFunctionsService.funGetDataList(sqllocCode, "sql");
			String locCode = listlocCode.get(0);
			String condition = "";

			switch (str[i]) {

			// //////////////////////WebStock Transaction
			// Start///////////////////////////////////////////
			case "Bill Passing": {
				sql = "truncate table tblbillpassdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblbillpasshd";
				funExecuteQuery(sql);
				sql = "truncate table tblbillpassingtaxdtl";
				funExecuteQuery(sql);
				break;
			}

			case "GRN(Good Receiving Note)": {
				sql = "DELETE FROM tblgrndtl WHERE strIssueLocation='" + locCode + "' and strGRNCode LIKE '%" + propCode + "'";
				funExecuteQuery(sql);
				sql = "DELETE FROM tblgrnhd WHERE strlocCode='" + locCode + "' and strGRNCode LIKE '%" + propCode + "'";
				funExecuteQuery(sql);
				sql = "DELETE FROM tblgrntaxdtl WHERE strIssueLocation='" + locCode + "' and strGRNCode LIKE '%" + propCode + "'";
				funExecuteQuery(sql);
				sql = "DELETE FROM tblgrnmisdtl WHERE strIssueLocation='" + locCode + "' and strGRNCode LIKE '%" + propCode + "'";
				funExecuteQuery(sql);
				sql = "DELETE FROM tblbatchhd WHERE strIssueLocation='" + locCode + "' and strGRNCode LIKE '%" + propCode + "'";
				funExecuteQuery(sql);

				break;
			}

			case "Opening Stock": {
				sql = "truncate table tblinitialinvdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblinitialinventory";
				funExecuteQuery(sql);
				break;
			}

			case "Material Return": {
				sql = "truncate table tblmaterialreturndtl";
				funExecuteQuery(sql);
				sql = "truncate table tblmaterialreturnhd";
				funExecuteQuery(sql);
				break;
			}

			case "Material Issue Slip": {
				sql = "truncate table tblmisdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblmishd";
				funExecuteQuery(sql);
				sql = "truncate table tblmrpidtl";
				funExecuteQuery(sql);
				break;
			}

			case "Material Production": {
				sql = "truncate table tblproductiondtl";
				funExecuteQuery(sql);
				sql = "truncate table tblproductionhd";
				funExecuteQuery(sql);
				break;
			}
			case "Meal Planing": {
				sql = "truncate table tblproductionorderdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblproductionorderhd";
				funExecuteQuery(sql);
				break;
			}

			case "Purchase Indent": {
				sql = "truncate table tblpurchaseindenddtl";
				funExecuteQuery(sql);
				sql = "truncate table tblpurchaseindendhd";
				funExecuteQuery(sql);
				break;
			}

			case "Purchase Order": {
				sql = "truncate table tblpurchaseorderdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblpurchaseorderhd";
				funExecuteQuery(sql);
				sql = "truncate table tblpotaxdtl";
				funExecuteQuery(sql);
				break;
			}

			case "Purchase Return": {
				sql = "truncate table tblpurchasereturndtl";
				funExecuteQuery(sql);
				sql = "truncate table tblpurchasereturnhd";
				funExecuteQuery(sql);
				break;
			}

			case "Material Requisition": {
				sql = "truncate table tblreqdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblreqhd";
				funExecuteQuery(sql);
				break;
			}

			case "Stock Adjustment": {
				sql = "truncate table tblstockadjustmentdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblstockadjustmenthd";
				funExecuteQuery(sql);
				break;
			}

			case "Physical Stk Posting": {
				sql = "truncate table tblstockpostingdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblstockpostinghd";
				funExecuteQuery(sql);
				break;
			}

			case "Stock Transfer": {
				sql = "truncate table tblstocktransferdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblstocktransferhd";
				funExecuteQuery(sql);
				break;
			}

			case "Work Order": {
				sql = "truncate table tblworkorderdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblworkorderhd";
				funExecuteQuery(sql);
				break;
			}

			case "Rate Contract": {
				sql = "truncate table tblratecontdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblrateconthd";
				funExecuteQuery(sql);
				break;
			}
			default: {
				sql = "truncate table tbltempitemstock";
				funExecuteQuery(sql);
				sql = "truncate table tblpossalesdtl";
				funExecuteQuery(sql);

			}

			// //////////////////////WebStock Transaction
			// End///////////////////////////////////////////

			// //////////////////////WebCRM Transaction
			// Start///////////////////////////////////////////

			case "Delivery Challan": {
				sql = "truncate table tbldeliverychallanhd";
				funExecuteQuery(sql);
				sql = "truncate table tbldeliverychallandtl";
				funExecuteQuery(sql);
				break;
			}

			case "Delivery Note": {
				sql = "truncate table tbldeliverynotehd";
				funExecuteQuery(sql);
				sql = "truncate table tbldeliverynotedtl";
				funExecuteQuery(sql);
				break;
			}

			case "Excise Challan": {

				break;
			}

			case "Invoice": {
				sql = "truncate table tblinvoicedtl";
				funExecuteQuery(sql);
				sql = "truncate table tblinvoicehd";
				funExecuteQuery(sql);
				sql = "truncate table tblinvprodtaxdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblinvsalesorderdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblinvtaxdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblinvtaxgst";
				funExecuteQuery(sql);

				break;
			}

			case "Job Order Allocation": {
				sql = "truncate table tbljoborderallocationhd";
				funExecuteQuery(sql);
				sql = "truncate table tbljoborderallocationdtl";
				funExecuteQuery(sql);
				break;
			}

			case "Job Order": {
				sql = "truncate table tbljoborderhd";
				funExecuteQuery(sql);

				break;

			}

			case "Sales Order": {
				sql = "truncate table tblsalesorderhd";
				funExecuteQuery(sql);
				sql = "truncate table tblsalesorderdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblsaleschar";
				funExecuteQuery(sql);

				break;

			}

			case "Sales Order BOM": {
				sql = "truncate table tblsalesorderbom";
				funExecuteQuery(sql);

				break;

			}

			case "Sub Contractor GRN": {
				sql = "truncate table tblscreturnhd";
				funExecuteQuery(sql);
				sql = "truncate table tblscreturndtl";
				funExecuteQuery(sql);

				break;

			}

			// //////////////////////WebCRM Transaction
			// END///////////////////////////////////////////

			}

		}
	}

	@Override
	public void funClearMaster(String clientCode, String[] str) {

		for (int i = 0; i < str.length; i++) {
			String sql = "";
			switch (str[i]) {

			// //////////////////////WebStock Master
			// Start///////////////////////////////////////////
			case "Attachement Master": {
				sql = "truncate table tblattachdocument";
				funExecuteQuery(sql);
				break;
			}
			case "Attribute Master": {
				sql = "truncate table tblattributemaster";
				funExecuteQuery(sql);
				break;
			}
			case "Attribute Value Master": {
				sql = "truncate table tblattvaluemaster";
				funExecuteQuery(sql);
				break;
			}
			case "Reciepe Master": {
				sql = "truncate table tblbommasterdtl";
				funExecuteQuery(sql);
				sql = "truncate table tblbommasterhd";
				funExecuteQuery(sql);
				break;
			}
			case "Currency Master": {
				sql = "truncate table tblcurrencymaster";
				funExecuteQuery(sql);
				break;
			}
			case "Group Master": {
				sql = "truncate table tblgroupmaster";
				funExecuteQuery(sql);
				break;
			}
			case "Location Master": {
				sql = "truncate table tbllocationmaster";
				funExecuteQuery(sql);
				break;
			}
			case "Product Master": {

				sql = "truncate table tblprodchar";
				funExecuteQuery(sql);
				sql = "truncate table tblproductmaster";
				funExecuteQuery(sql);
				sql = "truncate table tblprodsuppmaster";
				funExecuteQuery(sql);
				sql = "truncate table tblreorderlevel";
				funExecuteQuery(sql);
				sql = "truncate table tblprodprocess";
				funExecuteQuery(sql);
				break;
			}
			case "Supplier Master": {
				sql = "truncate table tblpartymaster";
				funExecuteQuery(sql);
				break;
			}
			case "Property Master": {
				sql = "truncate table tblpropertymaster";
				funExecuteQuery(sql);
				sql = "truncate table tblpropertysetup";
				funExecuteQuery(sql);
				sql = "truncate table tblworkflow";
				funExecuteQuery(sql);
				sql = "truncate table tblworkflowforslabbasedauth";
				funExecuteQuery(sql);
				sql = "truncate table tblprocessmaster";
				funExecuteQuery(sql);
				sql = "truncate table tblprocesssetup";
				funExecuteQuery(sql);
				break;
			}
			case "Reason Master": {
				sql = "truncate table tblreasonmaster";
				funExecuteQuery(sql);
				break;
			}
			case "Settlement Master": {
				sql = "truncate table tblsettlementmaster";
				funExecuteQuery(sql);
				break;
			}
			case "Sub Group Master": {
				sql = "truncate table tblsubgroupmaster";
				funExecuteQuery(sql);
				break;
			}
			case "Tax Master": {
				sql = "truncate table tbltaxdtl";
				funExecuteQuery(sql);
				sql = "truncate table tbltaxhd";
				funExecuteQuery(sql);
				sql = "truncate table tbltaxsettlementmaster";
				funExecuteQuery(sql);
				break;
			}
			case "TC Master": {
				sql = "truncate table tbltcmaster";
				funExecuteQuery(sql);
				sql = "truncate table tbltctransdtl";
				funExecuteQuery(sql);
				break;
			}
			case "UOM Master": {
				sql = "truncate table tbluommaster";
				funExecuteQuery(sql);
				break;
			}
			case "User Master": {
				sql = "truncate table tbluserhd";
				funExecuteQuery(sql);
				sql = "truncate table tbluserdtl";
				funExecuteQuery(sql);
				break;
			}

			// //////////////////////WebStock Master
			// End///////////////////////////////////////////

			// //////////////////////WebCRM Master
			// Start///////////////////////////////////////////

			case "Security Shell": {
				sql = "truncate table tbluserdtl";
				funExecuteQuery(sql);
				break;
			}

			case "Customer Master": {
				sql = "Delete from tblpartymaster where strPType='cust'";
				funExecuteQuery(sql);
				break;
			}

			case "Sub Contractor Master": {
				sql = "Delete from tblpartymaster where strPType='subc'";
				funExecuteQuery(sql);
				break;
			}

			// //////////////////////WebCRM Master
			// End///////////////////////////////////////////

			}
		}
	}
}
