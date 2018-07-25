package com.sanguine.excise.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sanguine.service.clsGlobalFunctionsService;

@Controller
public class clsExciseStructureUpdateDaoImpl implements clsExciseStructureUpdateDao {

	@Autowired
	private SessionFactory exciseSessionFactory;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private clsGlobalFunctionsService objGlobalFunctionsService;

	public void funExciseUpdateStructure(String clientCode) {

		String sql = "";
		sql = "CREATE TABLE IF NOT EXISTS `tblbrandmaster` ( " + "`strBrandCode` varchar(20) NOT NULL, " + "`intId` bigint(20) NOT NULL AUTO_INCREMENT, " + "`strBrandName` varchar(200) NOT NULL, " + "`intPegSize` int(10) unsigned NOT NULL DEFAULT '0', " + "`dblStrength` decimal(18,3) NOT NULL DEFAULT '0.000', " + "`dblMRP` decimal(18,3) NOT NULL DEFAULT '0.000', "
				+ "`dblPegPrice` decimal(18,3) NOT NULL DEFAULT '0.000', " + "`strShortName` varchar(50) DEFAULT NULL, " + "`strSubCategoryCode` varchar(20) NOT NULL, " + "`strSizeCode` varchar(50) NOT NULL, " + "`strUserCreated` varchar(20) NOT NULL, " + "`strUserEdited` varchar(20) NOT NULL, " + "`dteDateCreated` datetime NOT NULL, " + "`dteDateEdited` datetime NOT NULL, "
				+ "`strClientCode` varchar(20) NOT NULL, " + "PRIMARY KEY (`strBrandCode`,`strClientCode`)," + "KEY `intId` (`intId`) " + ") ENGINE=InnoDB AUTO_INCREMENT=948 DEFAULT CHARSET=utf8;";

		funExciseExecuteQuery(sql);

		sql = "CREATE TABLE IF NOT EXISTS `tblcategorymaster` ( " + "`strCategoryCode` varchar(20) NOT NULL, " + "`intId` bigint(20) NOT NULL AUTO_INCREMENT, " + "`strCategoryName` varchar(200) NOT NULL, " + "`strUserCreated` varchar(20) NOT NULL, " + "`strUserEdited` varchar(20) NOT NULL, " + "`dteDateCreated` datetime NOT NULL," + "`dteDateEdited` datetime NOT NULL, "
				+ "`strClientCode` varchar(20) NOT NULL, " + "PRIMARY KEY (`strCategoryCode`,`strClientCode`), " + "KEY `intId` (`intId`) " + ") ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;";

		funExciseExecuteQuery(sql);

		sql = "CREATE TABLE IF NOT EXISTS `tblcitymaster` ( " + "`strCityCode` varchar(20) NOT NULL, " + "`intId` bigint(20) NOT NULL AUTO_INCREMENT, " + "`strCityName` varchar(200) NOT NULL, " + "`strUserCreated` varchar(20) NOT NULL, " + "`strUserEdited` varchar(20) NOT NULL, " + "`dteDateCreated` datetime NOT NULL, " + "`dteDateEdited` datetime NOT NULL, "
				+ "`strClientCode` varchar(20) NOT NULL, " + "PRIMARY KEY (`strCityCode`,`strClientCode`)," + "KEY `intId` (`intId`) " + ") ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;";

		funExciseExecuteQuery(sql);
		sql = "CREATE TABLE IF NOT EXISTS `tblexciselocationmaster` ( " + "`strLocCode` varchar(10) NOT NULL, " + "`intId` bigint(20) NOT NULL DEFAULT '0', " + "`strLocName` varchar(150) NOT NULL DEFAULT '', " + "`strLocDesc` varchar(150) NOT NULL DEFAULT '', " + "`strAvlForSale` char(1) NOT NULL DEFAULT 'N', " + "`strExciseNo` varchar(30) NOT NULL DEFAULT '', "
				+ "`strType` varchar(15) NOT NULL DEFAULT '', " + "`strPickable` char(1) NOT NULL DEFAULT 'N', " + "`strReceivable` varchar(255) DEFAULT NULL, " + "`strPropertyCode` varchar(10) NOT NULL DEFAULT '', " + "`strExternalCode` varchar(50) NOT NULL DEFAULT '', " + "`strMonthEnd` varchar(10) DEFAULT '', " + "`strActive` char(1) NOT NULL DEFAULT 'N', "
				+ "`strUserCreated` varchar(10) NOT NULL, " + "`dteDateCreated` datetime NOT NULL, " + "`strUserModified` varchar(10) NOT NULL," + "`dteLastModified` datetime NOT NULL, " + "`strClientCode` varchar(10) NOT NULL, " + "`strLocPropertyCode` varchar(255) DEFAULT NULL, " + "PRIMARY KEY (`strLocCode`,`strClientCode`) " + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

		funExciseExecuteQuery(sql);
		sql = "CREATE TABLE IF NOT EXISTS `tblexcisepermitmaster` ( " + "`strPermitCode` varchar(20) COLLATE latin1_general_ci NOT NULL, " + "`intId` bigint(20) NOT NULL AUTO_INCREMENT, " + "`strPermitName` varchar(150) COLLATE latin1_general_ci NOT NULL, " + "`strPermitNo` varchar(30) COLLATE latin1_general_ci NOT NULL, "
				+ "`dtePermitExp` varchar(50) COLLATE latin1_general_ci NOT NULL DEFAULT 'Life Time', " + "`strPermitType` varchar(50) COLLATE latin1_general_ci NOT NULL, " + "`strUserCreated` varchar(20) COLLATE latin1_general_ci NOT NULL, " + "`strUserEdited` varchar(20) COLLATE latin1_general_ci NOT NULL, " + "`dteDateCreated` datetime NOT NULL, " + "`dteDateEdited` datetime NOT NULL, "
				+ "`strClientCode` varchar(20) COLLATE latin1_general_ci NOT NULL, " + "PRIMARY KEY (`strPermitCode`,`strClientCode`), " + "KEY `intId` (`intId`) " + ") ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;";

		funExciseExecuteQuery(sql);
		sql = "CREATE TABLE IF NOT EXISTS `tblexciseposlinkup` ( " + "`strPOSItemCode` varchar(20) NOT NULL, " + "`strPOSItemName` varchar(100) NOT NULL, " + "`strBrandCode` varchar(20) NOT NULL DEFAULT '', " + "`strBrandName` varchar(100) NOT NULL DEFAULT '', " + "`intConversionFactor` int(11) NOT NULL DEFAULT '1', " + "`strClientCode` varchar(20) NOT NULL, "
				+ "PRIMARY KEY (`strPOSItemCode`,`strClientCode`) " + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";

		funExciseExecuteQuery(sql);
		sql = "CREATE TABLE IF NOT EXISTS `tblexcisepossale` ( " + "`intId` bigint(20) NOT NULL AUTO_INCREMENT, " + "`strPOSCode` varchar(20) NOT NULL, " + "`strPOSItemCode` varchar(20) NOT NULL, " + "`strPOSItemName` varchar(100) NOT NULL, " + "`dteBillDate` datetime NOT NULL, " + "`intQuantity` bigint(11) unsigned NOT NULL DEFAULT '0', " + "`strBrandCode` varchar(255) DEFAULT NULL, "
				+ "`strBillNo` varchar(30) NOT NULL DEFAULT '', " + "`dblRate` decimal(18,4) NOT NULL DEFAULT '0.0000', " + "`strClientCode` varchar(20) NOT NULL, " + "PRIMARY KEY (`intId`) " + ") ENGINE=InnoDB AUTO_INCREMENT=2713 DEFAULT CHARSET=utf8;";

		funExciseExecuteQuery(sql);
		sql = "CREATE TABLE IF NOT EXISTS `tblexcisepropertymaster` ( " + "`strClientCode` varchar(20) NOT NULL, " + "`strBrandMaster` varchar(20) NOT NULL DEFAULT 'Custom', " + "`strSizeMaster` varchar(20) NOT NULL DEFAULT 'Custom', " + "`strSubCategory` varchar(20) NOT NULL DEFAULT 'Custom', " + "`strCategory` varchar(20) NOT NULL DEFAULT 'Custom', "
				+ "`strSupplier` varchar(20) NOT NULL DEFAULT 'Custom', " + "`strRecipe` varchar(20) NOT NULL DEFAULT 'Custom', " + "`strCity` varchar(20) NOT NULL DEFAULT 'Custom', " + "`strPermit` varchar(20) NOT NULL DEFAULT 'Custom', " + " strUserCreated` varchar(20) NOT NULL, " + "`strUserEdited` varchar(20) NOT NULL, " + "`dteDateCreated` datetime NOT NULL, "
				+ "`dteDateEdited` datetime NOT NULL, " + "PRIMARY KEY (`strClientCode`) " + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		funExciseExecuteQuery(sql);

		sql = "CREATE TABLE IF NOT EXISTS `tblexciserecipermasterdtl` ( " + "`intId` bigint(20) NOT NULL AUTO_INCREMENT, " + "`strRecipeCode` varchar(20) NOT NULL, " + "`strBrandCode` varchar(10) NOT NULL DEFAULT '', " + "`dblQty` decimal(18,3) NOT NULL DEFAULT '0.000', " + "`strClientCode` varchar(10) NOT NULL, " + "PRIMARY KEY (`intId`) " + ") ENGINE=InnoDB DEFAULT CHARSET=utf8; ";

		funExciseExecuteQuery(sql);

		sql = "CREATE TABLE IF NOT EXISTS `tblexciserecipermasterhd` ( " + "`strRecipeCode` varchar(20) NOT NULL, " + "`intId` bigint(20) NOT NULL DEFAULT '0', " + "`strParentCode` varchar(10) NOT NULL DEFAULT '', " + "`strParentName` varchar(100) NOT NULL DEFAULT '', " + "`dteValidFrom` date NOT NULL, " + "`dteValidTo` date NOT NULL, " + "`strUserCreated` varchar(10) NOT NULL, "
				+ "`dteCreatedDate` datetime NOT NULL, " + "`strUserModified` varchar(10) NOT NULL, " + "`dteLastModified` datetime NOT NULL, " + "`strClientCode` varchar(10) NOT NULL, " + "PRIMARY KEY (`strRecipeCode`,`strClientCode`) " + ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		funExciseExecuteQuery(sql);

		sql = "CREATE TABLE IF NOT EXISTS `tblexcisesaledata` ( " + "`intBillNo` bigint(20) NOT NULL, " + "`dteBillDate` datetime NOT NULL, " + "`strLicenceCode` varchar(10) NOT NULL, " + "`strSalesCode` varchar(20) NOT NULL, " + "`strItemCode` varchar(20) NOT NULL, " + "`intTotalPeg` int(10) NOT NULL, " + "`intQty` int(11) NOT NULL DEFAULT '0', " + "`strPermitCode` varchar(20) NOT NULL, "
				+ "`dblTotalAmt` decimal(18,4) NOT NULL DEFAULT '0.0000', " + "`strUserCreated` varchar(10) NOT NULL, " + "`dteCreatedDate` datetime NOT NULL, " + "`strUserModified` varchar(10) NOT NULL, " + "`dteLastModified` datetime NOT NULL, " + "`strClientCode` varchar(10) NOT NULL, " + "`strSourceEntry` varchar(50) NOT NULL DEFAULT 'Manual', " + "PRIMARY KEY (`intBillNo`,`strClientCode`) "
				+ ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		funExciseExecuteQuery(sql);
		sql = " CREATE TABLE IF NOT EXISTS `tblexcisestockpostingdtl` (   " + "`intId` bigint(20) NOT NULL AUTO_INCREMENT, " + "  `strPSPCode` varchar(20) NOT NULL, " + "  `strBrandCode` varchar(10) NOT NULL, " + "  `intSysBtl` bigint(20) unsigned NOT NULL DEFAULT '0',  " + " `intSysML` bigint(20) unsigned NOT NULL DEFAULT '0',  " + " `intSysPeg` bigint(20) unsigned NOT NULL DEFAULT '0',  "
				+ " `intPhyBtl` bigint(20) unsigned NOT NULL DEFAULT '0',  " + " `intPhyML` bigint(20) unsigned NOT NULL DEFAULT '0',  " + " `intPhyPeg` bigint(20) unsigned NOT NULL DEFAULT '0',  " + " `strClientCode` varchar(10) NOT NULL, " + "  PRIMARY KEY (`intId`)  ) " + " ENGINE=InnoDB DEFAULT CHARSET=utf8; ";
		funExciseExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tblexcisestockpostinghd` (  " + "`strPSPCode` varchar(20) NOT NULL,  " + "`intId` bigint(20) NOT NULL,  `dtePostingDate` datetime NOT NULL,  " + "`strLicenceCode` varchar(20) NOT NULL,  `strUserCreated` varchar(10) NOT NULL,  " + "`dteDateCreated` datetime NOT NULL," + "  `strUserModified` varchar(10) NOT NULL,  "
				+ " `dteDateEdited` datetime NOT NULL,    " + "`strClientCode` varchar(10) NOT NULL, " + "  PRIMARY KEY (`strPSPCode`,`strClientCode`) " + ") ENGINE=InnoDB DEFAULT CHARSET=utf8; ";
		funExciseExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tbllicencemaster` ( " + "  `strLicenceCode` varchar(5) NOT NULL,  " + " `intId` bigint(20) NOT NULL AUTO_INCREMENT,  " + " `strLicenceNo` varchar(50) NOT NULL,   " + "`strLicenceName` varchar(100) NOT NULL,  " + " `strCity` varchar(100) NOT NULL DEFAULT 'C00001',   " + "`strAddress1` varchar(200) NOT NULL DEFAULT '',  "
				+ " `strAddress2` varchar(200) NOT NULL DEFAULT '',  " + " `strAddress3` varchar(200) NOT NULL DEFAULT '',  " + " `strExternalCode` varchar(50) NOT NULL DEFAULT '',  " + " `strContactPerson` varchar(100) NOT NULL DEFAULT '',   " + "`longTelephoneNo` bigint(20) NOT NULL,   " + "`strEmailId` varchar(100) NOT NULL DEFAULT '',  "
				+ " `strBusinessCode` varchar(100) NOT NULL DEFAULT '',  " + " `strVATNo` varchar(100) NOT NULL DEFAULT '',  " + " `strTINNo` varchar(100) NOT NULL DEFAULT '', " + "  `strPINCode` varchar(50) NOT NULL DEFAULT '',   " + "`longMobileNo` bigint(20) NOT NULL, " + "  `strUserCreated` varchar(20) NOT NULL,   " + "`strUserEdited` varchar(20) NOT NULL,  "
				+ " `dteDateCreated` datetime NOT NULL,  " + " `dteDateEdited` datetime NOT NULL,   " + "`strClientCode` varchar(20) NOT NULL, " + "  PRIMARY KEY (`strLicenceCode`,`strClientCode`), " + "  KEY `intId` (`intId`) ) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;";
		funExciseExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tblmanualsalesdtl` (  " + "`intId` bigint(20) NOT NULL AUTO_INCREMENT, " + "   `intSalesHd` bigint(20) NOT NULL,  " + " `strBrandCode` varchar(20) NOT NULL,  " + " `intBtl` int(11) NOT NULL DEFAULT '0',  " + " `intPeg` int(11) NOT NULL DEFAULT '0',  " + " `intML` int(11) NOT NULL DEFAULT '0',  " + " `strBillGenFlag` char(2) NOT NULL DEFAULT 'N', "
				+ "  `strClientCode` varchar(20) NOT NULL,  " + " PRIMARY KEY (`intId`)) ENGINE=InnoDB AUTO_INCREMENT=53929 DEFAULT CHARSET=utf8; ";
		funExciseExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tblmanualsaleshd` (  " + " `intId` bigint(20) NOT NULL AUTO_INCREMENT,  " + "`strLicenceCode` varchar(20) NOT NULL, " + " `dteBillDate` date NOT NULL,   " + " `strUserCreated` varchar(20) NOT NULL, " + "  `strUserEdited` varchar(20) NOT NULL,   " + " `dteDateCreated` datetime NOT NULL,  "
				+ " `dteDateEdited` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,   " + "`strClientCode` varchar(20) NOT NULL,  " + " `strSourceEntry` varchar(50) NOT NULL DEFAULT 'Manual', " + "  PRIMARY KEY (`intId`,`strClientCode`) " + ") ENGINE=InnoDB AUTO_INCREMENT=1788 DEFAULT CHARSET=utf8; ";
		funExciseExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tblonedaypass` (" + "   `intId` bigint(20) NOT NULL AUTO_INCREMENT,  " + " `dteDate` date NOT NULL,  " + " `intFromNo` bigint(20) NOT NULL, " + "  `intToNo` bigint(20) NOT NULL,  " + " `strUserCreated` varchar(20) COLLATE latin1_general_ci NOT NULL,  " + " `strUserEdited` varchar(20) COLLATE latin1_general_ci NOT NULL,  "
				+ " `dteDateCreated` datetime NOT NULL,  " + " `dteDateEdited` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  " + " `strClientCode` varchar(20) COLLATE latin1_general_ci NOT NULL, " + "  PRIMARY KEY (`intId`) ) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci; ";
		funExciseExecuteQuery(sql);

		sql = "CREATE TABLE IF NOT EXISTS `tblopeningstock` (   " + " `intId` bigint(20) NOT NULL AUTO_INCREMENT,  " + " `strBrandCode` varchar(20) NOT NULL, " + "  `intOpBtls` int(11) NOT NULL DEFAULT '0',  " + " `intOpPeg` int(11) NOT NULL DEFAULT '0',  " + " `intOpML` int(11) NOT NULL DEFAULT '0',   " + "`strUserCreated` varchar(20) NOT NULL,   " + "`strUserEdited` varchar(20) NOT NULL, "
				+ "  `dteDateCreated` datetime NOT NULL,   " + "`dteDateEdited` datetime NOT NULL,   " + "`strClientCode` varchar(20) NOT NULL,  " + " PRIMARY KEY (`intId`) ) ENGINE=InnoDB AUTO_INCREMENT=1543 DEFAULT CHARSET=utf8;a";
		funExciseExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tblpermitmaster` (   " + "`intId` bigint(20) NOT NULL AUTO_INCREMENT,  " + " `strPermitCode` varchar(20) NOT NULL," + "   `strPermitName` varchar(150) NOT NULL, " + "  `strPermitNo` varchar(30) NOT NULL,   " + "`StrPermitPlace` varchar(100) NOT NULL,  " + " `dtePermitIssue` date NOT NULL,   " + "`dtePermitExp` date NOT NULL, "
				+ "  `strPermitStatus` varchar(5) NOT NULL," + "   `dtePermitEdited` datetime NOT NULL,   " + "`strPermitUserCode` varchar(20) NOT NULL,  " + " `strClientCode` varchar(10) NOT NULL DEFAULT '1',   " + "PRIMARY KEY (`intId`) ) ENGINE=InnoDB AUTO_INCREMENT=1608 DEFAULT CHARSET=utf8; ";
		funExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tblratemaster` ( " + "  `strBrandCode` varchar(20) COLLATE latin1_general_ci NOT NULL" + ", `dblRate` decimal(18,3) NOT NULL DEFAULT '0.000', " + "  `strUserCreated` varchar(20) COLLATE latin1_general_ci NOT NULL, " + "  `strUserEdited` varchar(20) COLLATE latin1_general_ci NOT NULL, " + "  `dteDateCreated` datetime NOT NULL, "
				+ "  `dteDateEdited` datetime NOT NULL, " + "  `strClientCode` varchar(20) COLLATE latin1_general_ci NOT NULL, " + "  PRIMARY KEY (`strBrandCode`,`strClientCode`)" + "   ) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci; ";
		funExciseExecuteQuery(sql);

		sql = "CREATE TABLE IF NOT EXISTS `tblsizemaster` ( " + "  `strSizeCode` varchar(20) NOT NULL, " + "  `intId` bigint(20) NOT NULL AUTO_INCREMENT, " + "  `strSizeName` varchar(200) NOT NULL, " + "  `intQty` int(11) NOT NULL DEFAULT '0'," + "   `strUOM` varchar(20) NOT NULL," + "   `strUserCreated` varchar(20) NOT NULL, " + "  `strUserEdited` varchar(20) NOT NULL,"
				+ "   `dteDateCreated` datetime NOT NULL, " + "  `dteDateEdited` datetime NOT NULL,  " + " `strNarration` varchar(200) NOT NULL DEFAULT '', " + "  `strClientCode` varchar(20) NOT NULL,  " + " PRIMARY KEY (`strSizeCode`,`strClientCode`),  " + " KEY `intId` (`intId`) ) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;  ";
		funExciseExecuteQuery(sql);

		sql = "CREATE TABLE IF NOT EXISTS `tblsubcategorymaster` ( " + "  `strSubCategoryCode` varchar(20) NOT NULL," + "   `intId` bigint(20) NOT NULL AUTO_INCREMENT," + "   `strSubCategoryName` varchar(200) NOT NULL, " + "  `intPegSize` int(11) NOT NULL DEFAULT '30',  " + " `strCategoryCode` varchar(20) NOT NULL, " + "  `intMaxSaleQty` int(10) unsigned NOT NULL DEFAULT '0',"
				+ "   `intAvailableSizes` int(10) unsigned NOT NULL DEFAULT '0', " + "  `strIsDecimal` varchar(50) NOT NULL DEFAULT 'decimal',  " + " `strUserCreated` varchar(20) NOT NULL, " + "  `strUserEdited` varchar(20) NOT NULL, " + "  `dteDateCreated` datetime NOT NULL, " + "  `dteDateEdited` datetime NOT NULL,  " + " `strClientCode` varchar(20) NOT NULL, "
				+ "  PRIMARY KEY (`strSubCategoryCode`,`strClientCode`),  " + " KEY `intId` (`intId`) ) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;  ";

		funExciseExecuteQuery(sql);
		sql = " CREATE TABLE IF NOT EXISTS `tblsuppliermaster` (" + "  `strSupplierCode` varchar(20) NOT NULL, " + " `intId` bigint(20) NOT NULL AUTO_INCREMENT," + "   `strSupplierName` varchar(100) NOT NULL," + "   `strAddress` varchar(500) NOT NULL DEFAULT '', " + "  `strVATNo` varchar(100) NOT NULL DEFAULT '',  " + " `strTINNo` varchar(100) NOT NULL DEFAULT '', "
				+ "  `strCityCode` varchar(100) NOT NULL DEFAULT '',  " + " `strPINCode` varchar(50) NOT NULL DEFAULT '', " + "  `strContactPerson` varchar(100) NOT NULL DEFAULT '', " + "  `longTelephoneNo` bigint(20) NOT NULL, " + "  `strEmailId` varchar(100) NOT NULL DEFAULT '', " + "  `longMobileNo` bigint(20) NOT NULL, " + "  `strUserCreated` varchar(20) NOT NULL, "
				+ "  `strUserEdited` varchar(20) NOT NULL, " + "  `dteDateCreated` datetime NOT NULL,  " + " `dteDateEdited` datetime NOT NULL,  " + " `strClientCode` varchar(20) NOT NULL, " + "  PRIMARY KEY (`strSupplierCode`,`strClientCode`),  " + " KEY `intId` (`intId`) ) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8; ";

		funExciseExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tbltpdtl` (" + "  `intId` bigint(20) unsigned NOT NULL AUTO_INCREMENT, " + "  `strTPCode` varchar(20) NOT NULL,  " + " `strBrandCode` varchar(20) NOT NULL,  " + " `intBottals` int(11) NOT NULL DEFAULT '0', " + "  `dblBrandTotal` decimal(18,3) NOT NULL DEFAULT '0.000',  " + " `dblBrandTax` decimal(18,3) NOT NULL DEFAULT '0.000', "
				+ "  `dblBrandFee` decimal(18,3) NOT NULL DEFAULT '0.000', " + "  `intBrandCases` int(11) NOT NULL DEFAULT '0', " + "  `strBrandBatchNo` varchar(100) DEFAULT '''''', " + "  `monofmfg` varchar(50) DEFAULT '''''',  " + " `strRemark` varchar(500) DEFAULT '''''',  " + " `strClientCode` varchar(20) NOT NULL, "
				+ "  PRIMARY KEY (`intId`) ) ENGINE=InnoDB AUTO_INCREMENT=4345 DEFAULT CHARSET=utf8; ";
		funExciseExecuteQuery(sql);

		sql = " CREATE TABLE IF NOT EXISTS `tbltphd` (  " + " `strTPCode` varchar(20) NOT NULL, " + "  `intId` bigint(20) NOT NULL AUTO_INCREMENT, " + "  `strSupplierCode` varchar(20) NOT NULL,  " + " `strLicenceCode` varchar(20) NOT NULL, " + "  `strTPNo` varchar(20) NOT NULL," + "   `dblTotalPurchase` decimal(18,3) NOT NULL DEFAULT '0.000'," + "   `strInvoiceNo` varchar(100) NOT NULL,  "
				+ " `strTpDate` date NOT NULL,  " + " `dblTotalTax` decimal(18,3) NOT NULL DEFAULT '0.000', " + "  `dblTotalFees` decimal(18,3) NOT NULL DEFAULT '0.000', " + "  `dblTotalBill` decimal(18,3) NOT NULL DEFAULT '0.000', " + "  `strUserCreated` varchar(20) NOT NULL,  " + " `strUserEdited` varchar(20) NOT NULL,  " + " `dteDateCreated` datetime NOT NULL,  "
				+ " `dteDateEdited` datetime NOT NULL,  " + " `strClientCode` varchar(20) NOT NULL,  " + " `Column 17` varchar(20) NOT NULL,  " + " PRIMARY KEY (`strTPCode`,`strClientCode`),  " + " KEY `intId` (`intId`) ) ENGINE=InnoDB AUTO_INCREMENT=2020 DEFAULT CHARSET=utf8; ";
		funExciseExecuteQuery(sql);

		sql = "CREATE TABLE IF NOT EXISTS `tblmonthend` ( " + " `strLocCode` VARCHAR(255) NOT NULL, " + " `strMonthEnd` VARCHAR(255) NOT NULL, " + " `dtDateCreated` VARCHAR(25) NOT NULL DEFAULT '', " + " `dtLastModified` VARCHAR(25) NOT NULL DEFAULT '', " + " `strClientCode` VARCHAR(25) NOT NULL DEFAULT '', " + " `strUserCreated` VARCHAR(255) NOT NULL DEFAULT '', "
				+ " `strUserModified` VARCHAR(25) NOT NULL DEFAULT '', " + " PRIMARY KEY (`strLocCode`, `strMonthEnd`) " + " ) " + " COLLATE='latin1_swedish_ci' " + " ENGINE=InnoDB ;";

		funExciseExecuteQuery(sql);

		sql = " DROP TABLE IF EXISTS `tbltreemast`; " + " CREATE TABLE IF NOT EXISTS `tbltreemast` (  " + "  `strFormName` varchar(50) NOT NULL,  " + "  `strFormDesc` varchar(200) NOT NULL,  " + "   `strRootNode` varchar(50) NOT NULL,  " + "   `intRootIndex` int(11) NOT NULL,  " + " `strType` varchar(50) NOT NULL, " + "  `intFormKey` int(11) NOT NULL, " + " `intFormNo` int(11) NOT NULL, "
				+ " `strImgSrc` varchar(50) NOT NULL, " + " `strImgName` varchar(100) NOT NULL, " + " `strModule` varchar(15) NOT NULL, " + " `strTemp` int(11) NOT NULL, " + " `strActFile` varchar(3) NOT NULL,  " + " `strHelpFile` varchar(150) NOT NULL, " + " `strProcessForm` varchar(4) NOT NULL DEFAULT 'NA', " + " `strAutorisationForm` varchar(4) NOT NULL DEFAULT 'NA', "
				+ " `strRequestMapping` varchar(50) DEFAULT NULL, " + " `strAdd` varchar(255) DEFAULT NULL, " + " `strAuthorise` varchar(255) DEFAULT NULL, " + " `strDelete` varchar(255) DEFAULT NULL, " + " `strDeliveryNote` varchar(255) DEFAULT NULL, " + " `strDirect` varchar(255) DEFAULT NULL, " + " `strEdit` varchar(255) DEFAULT NULL, " + " `strGRN` varchar(255) DEFAULT NULL, "
				+ " `strGrant` varchar(255) DEFAULT NULL, " + " `strMinimumLevel` varchar(255) DEFAULT NULL, " + " `strOpeningStock` varchar(255) DEFAULT NULL, " + " `strPrint` varchar(255) DEFAULT NULL, " + " `strProductionOrder` varchar(255) DEFAULT NULL, " + " `strProject` varchar(255) DEFAULT NULL, " + " `strPurchaseIndent` varchar(255) DEFAULT NULL, "
				+ " `strPurchaseOrder` varchar(255) DEFAULT NULL, " + " `strPurchaseReturn` varchar(255) DEFAULT NULL, " + " `strRateContractor` varchar(255) DEFAULT NULL, " + " `strRequisition` varchar(255) DEFAULT NULL, " + " `strSalesOrder` varchar(255) DEFAULT NULL, " + " `strSalesProjection` varchar(255) DEFAULT NULL, " + " `strSalesReturn` varchar(255) DEFAULT NULL, "
				+ " `strServiceOrder` varchar(255) DEFAULT NULL, " + " `strSubContractorGRN` varchar(255) DEFAULT NULL, " + " `strView` varchar(255) DEFAULT NULL, " + " `strWorkOrder` varchar(255) DEFAULT NULL, " + " `strAuditForm` varchar(255) DEFAULT NULL, " + " `strMIS` varchar(255) DEFAULT NULL, " + " PRIMARY KEY (`strFormName`) " + " ) ENGINE=InnoDB DEFAULT CHARSET=latin1; ";

		funExecuteQuery(sql);

		/*
		 * sql= " DELETE FROM `tbltreemast`; " ; funExecuteQuery(sql);
		 */

		// indexing Entry

		sql = "ALTER TABLE `tbltreemast` CHANGE COLUMN `strFormDesc` `strFormDesc` VARCHAR(200) NOT NULL AFTER `strFormName`; ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tblopeningstock` " + "ADD COLUMN `strLicenceCode` VARCHAR(20) NOT NULL AFTER `intId`;";
		funExciseExecuteQuery(sql);

		sql = "ALTER TABLE `tbllicencemaster` ADD COLUMN `strPropertyCode` VARCHAR(10) NOT NULL AFTER `strClientCode`, " + " DROP PRIMARY KEY, ADD PRIMARY KEY (`strLicenceCode`, `strClientCode`, `strPropertyCode`); ";
		funExciseExecuteQuery(sql);

		sql = " ALTER TABLE `tblcompanymaster`	DROP PRIMARY KEY, DROP INDEX `intId`,	ADD PRIMARY KEY (`intId`); ";
		funExecuteQuery(sql);

		sql = "ALTER TABLE `tbllicencemaster` " + "ADD COLUMN `strMonthEnd` VARCHAR(10) NULL DEFAULT '' AFTER `strPropertyCode`;";
		funExciseExecuteQuery(sql);

		sql = "ALTER TABLE `tblexcisesaledata` " + " ADD COLUMN `strPOSItem` VARCHAR(25) NOT NULL DEFAULT '' AFTER `strSourceEntry`," + " ADD COLUMN `strRemark` VARCHAR(75) NOT NULL DEFAULT '' AFTER `strPOSItem` ";

		funExciseExecuteQuery(sql);

		/*----------------WebStock Forms only---------------------------*/

		/*
		 * sql=
		 * " INSERT INTO `tbltreemast` (`strFormName`, `strFormDesc`, `strRootNode`, `intRootIndex`, `strType`, `intFormKey`, `intFormNo`, `strImgSrc`, `strImgName`, `strModule`, `strTemp`, `strActFile`, `strHelpFile`, `strProcessForm`, `strAutorisationForm`, `strRequestMapping`, `strAdd`, `strAuthorise`, `strDelete`, `strDeliveryNote`, `strDirect`, `strEdit`, `strGRN`, `strGrant`, `strMinimumLevel`, `strOpeningStock`, `strPrint`, `strProductionOrder`, `strProject`, `strPurchaseIndent`, `strPurchaseOrder`, `strPurchaseReturn`, `strRateContractor`, `strRequisition`, `strSalesOrder`, `strSalesProjection`, `strSalesReturn`, `strServiceOrder`, `strSubContractorGRN`, `strView`, `strWorkOrder`, `strAuditForm`, `strMIS`) VALUES "
		 * 
		 * 
		 * +
		 * " ('Cost Center\\r\\n', 'Cost Center\\r\\n', 'Reports', 7, 'O', 1, 1, '1', 'default.png', '1', 1, '1', '1', 'NA', 'NA', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmAccountHolderMaster', 'Account Holder Master', 'Master', 2, 'M', 2, 2, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmAccountHolderMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmACGroupMaster', 'Group Master', 'Master', 4, 'M', 4, 4, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmACGroupMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmAgeingReport', 'Ageing Rport', 'Sub Contracting Report', 3, 'R', 73, 1, '1', 'default.png', '6', 1, '1', '1', 'No', 'No', 'frmAgeingReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmAttributeMaster', 'Attribute Master', 'Master', 8, 'M', 1, 1, '12', 'Attribute-Master.png', '1', 1, '1', '1', 'NO', '1', 'frmAttributeMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmAttributeValueMaster', 'Attribute Value Master', 'Master', 8, 'M', 2, 2, '3', 'Attribute-Value-Master.png', '3', 3, '3', '3', 'NO', 'YES', 'frmAttributeValueMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmAuditFlash', 'Audit Flash', 'Tools', 8, 'L', 48, 26, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmAuditFlash.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmAuthorisationTool', 'Authorisation', 'Tools', 8, 'L', 10, 2, '1', 'Authourisation.png', '1', 1, '1', '1', 'NO', 'NO', 'frmAuthorisationTool.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmBankMaster', 'Bank Master', 'Master', 3, 'M', 3, 3, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmBankMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmBillPassing', 'Bill Passing', 'Accounts', 5, 'T', 3, 1, '1', 'Bill-Passing.png', '1', 1, '1', '1', 'NO', 'YES', 'frmBillPassing.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmBOMMaster', 'Reciepe Master', 'Master', 8, 'M', 4, 3, '2', 'ReciepeMaster-BOM-Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmBOMMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmCharacteristicsMaster', 'Characteristics Master', 'Master', 8, 'M', 5, 4, '1', 'Characteristics-Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmCharMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmChargeMaster', 'Charge Master', 'Master', 5, 'M', 5, 5, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmChargeMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmChargeProcessing', 'Charge Processing', 'Processing', 2, 'P', 1, 1, '1', 'default.png', '5', 1, '1', '1', 'YES', 'NO', 'frmChargeProcessing.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmCompanyMaster', 'Company Master', 'Master', 1, 'M', 1, 10, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmCompanyMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmCompanyTypeMaster', 'Company Type Master', 'Master', 1, 'M', 1, 12, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmCompanyTypeMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmConsReceiptValMiscSuppReqReport', 'Consolidated Receipt Value Misc Suppler Required Report', 'Purchases', 7, 'R', 51, 29, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmConsReceiptValMiscSuppReqReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmConsReceiptValStoreWiseBreskUPReport', 'Consolidated Receipt value Store Wise BreakUP', 'Purchases', 7, 'R', 51, 30, '1', 'default.png', '1', 1, '1', '1', 'NO', 'No', 'frmConsReceiptValStoreWiseBreskUPReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmCostOfIssue', 'Cost Of Issue', 'Stores', 9, 'R', 42, 20, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmCostOfIssue.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmCustomerMaster', 'Customer Master', 'Master', 1, 'M', 52, 1, '1', 'default.png', '6', 1, '1', '1', 'NO', 'No', 'frmCustomerMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmDebtorLedger', 'Debtor Ledger Tool', 'Tools', 1, 'L', 1, 1, '12', 'default.png', '5', 1, '1', '1', 'NO', '1', 'frmDebtorLedger.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmDebtorReceipt', 'Debtor Receipt', 'Transaction', 1, 'T', 1, 1, '12', 'default.png', '5', 1, '1', '1', 'NO', '1', 'frmDebtorReceipt.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmDeleteTransaction', 'Delete Transaction', 'Tools', 8, 'L', 46, 24, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmDeleteTransaction.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmDeliveryChallan', 'Delivery Challan', 'Sales', 2, 'T', 56, 3, '1', 'default.png', '6', 1, '1', '1', 'NO', 'Yes', 'frmDeliveryChallan.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmDeliveryChallanList', 'Delivery Challan List', 'Sub contracting Report', 3, 'R', 63, 5, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmDeliveryChallanList.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmDeliveryChallanSlip', 'Delivery Challan Slip', 'Sub contracting Report', 3, 'R', 62, 4, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmDeliveryChallanSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmDeliveryChallanSlipInvoice', 'Delivery Challan Slip Invoice', 'Sub contracting Report', 3, 'R', 74, 2, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmDeliveryChallanSlipInvoice.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmDeliveryNote', 'Delivery Note', 'Sales', 2, 'T', 67, 8, '1', 'default.png', '6', 1, '1', '1', 'NO', 'Yes', 'frmDeliveryNote.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmDeliveryNoteList', 'Delivery Note List', 'Sub contracting Report', 3, 'R', 68, 10, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmDeliveryNoteList.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmDeliveryNoteSlip', 'Delivery Note Slip', 'Sub contracting Report', 3, 'R', 67, 9, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmDeliveryNoteSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmDocumentReconciliation', 'Document Reconciliation', 'Tools', 8, 'L', 10, 2, '1', 'Document_Reconciliation_Fla.png', '1', 1, '1', '1', 'NO', 'NO', 'frmDocumentReconciliation.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmDueDateMonitoringReport', 'Due Date Monitoring Report', 'Sub Contracting Report', 3, 'R', 75, 3, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmDueDateMonitoringReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmEditOtherInfo', 'Edit Other Info', 'Master', 1, 'M', 1, 15, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmEditOtherInfo.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmEmailSending', 'Sending Email', 'Tools', 8, 'L', 51, 29, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmEmailSending.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseAbstractReport', 'Abstract Report', 'Reports', 3, 'R', 21, 3, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseAbstractReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseAuditFlash', 'Excise Audit Flash', 'Tools', 4, 'L', 14, 3, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseAuditFlash.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseBillGenerate', 'Bill Generate', 'Transaction', 2, 'L', 19, 3, '1', 'default.png', '2', 1, '1', '1', 'YES', 'NO', 'frmExciseBillGenerate.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseBrandMaster', 'Brand Master', 'Master', 1, 'M', 3, 1, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseBrandMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseCashMemoReport', 'Cash Memo Report', 'Reports', 3, 'R', 184, 8, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseCashMemoReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseBrandWiseInquiry', 'Brand Wise Inquiry', 'Reports', 3, 'R', 186, 10, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseBrandWiseInquiry.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseCategoryMaster', 'Category Master', 'Master', 1, 'M', 13, 5, '1', 'default.png', '0', 1, '1', '1', 'NO', 'NO', 'frmExciseCategoryMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseChallan', 'Excise Challan', 'Sales', 2, 'T', 57, 4, '1', 'default.png', '6', 1, '1', '1', 'NO', 'Yes', 'frmExciseChallan.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseChataiReport', 'Chatai Report', 'Reports', 3, 'R', 23, 5, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseChataiReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseCityMaster', 'City Master', 'Master', 1, 'M', 18, 9, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseCityMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseDeleteTransaction', 'Delete Transction', 'Transaction', 2, 'R', 20, 5, '1', 'default.png', '2', 1, '1', '1', 'YES', 'NO', 'frmExciseDeleteTransaction.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseFLR3A', 'FLR- III (3A) Report', 'Reports', 3, 'R', 2, 1, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseFLR3A.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseFLR4Report', 'FLR- 4 Report', 'Reports', 3, 'R', 22, 4, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseFLR4Report.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseFLR6', 'FLR-VI (6A) Report', 'Reports', 3, 'R', 5, 2, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseFLR6.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseLicenceMaster', 'Licence Master', 'Master', 1, 'M', 15, 7, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseLicenceMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseLocationMaster', 'Location Master', 'Master', 1, 'M', 17, 8, '1', 'Location-Master.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseLocationMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseManualSale', 'Excise Sale', 'Transaction', 2, 'T', 6, 2, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseManualSale.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseOneDayPass', 'One Day Pass', 'Transaction', 2, 'T', 24, 4, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseOneDayPass.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseOpeningStock', 'Opening Stock', 'Transaction', 2, 'T', 8, 5, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseOpeningStock.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExcisePermitMaster', 'Permit Master', 'Master', 1, 'M', 24, 9, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExcisePermitMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExcisePhysicalStkPosting', 'Excise Physical Stk Posting', 'Store', 5, 'L', 4, 1, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExcisePhysicalStkPosting.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExcisePOSDataExportImport', 'Excise POS DataExport Import', 'Store', 5, 'L', 7, 2, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExcisePOSDataExportImport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExcisePOSLinkUp', 'POS Excise Link Up', 'Tools', 4, 'L', 10, 1, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExcisePOSLinkUp.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExcisePOSSale', 'Excise POS Sale', 'Tools', 4, 'L', 13, 2, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExcisePOSSale.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExcisePropertySetUp', 'Property Set Up', 'Master', 1, 'M', 14, 6, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExcisePropertySetUp.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExcisePurchaseAnylasisReport', 'Purchase Anylasis Report', 'Reports', 3, 'R', 182, 6, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExcisePurchaseAnylasisReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExcisePurchaseReport', 'Purchase Report', 'Reports', 3, 'R', 183, 7, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExcisePurchaseReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseRecipeMaster', 'Excise Recipe Master', 'Master', 1, 'M', 16, 5, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseRecipeMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseSalesReport', 'Sales Report', 'Reports', 3, 'R', 185, 9, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseSalesReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseSizeMaster', 'Size Master', 'Master', 1, 'M', 11, 3, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseSizeMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseStructureUpdate', 'Excise Structure Update', 'Tools', 4, 'L', 15, 4, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseStructureUpdate.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseSubCategoryMaster', 'Sub Category Master', 'Master', 1, 'M', 16, 4, '1', 'default.png', '0', 1, '1', '1', 'NO', 'NO', 'frmExciseSubCategoryMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseSupplierMaster', 'Supplier Master', 'Master', 1, 'M', 9, 4, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseSupplierMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseTransportPass', 'Transport Pass', 'Transaction', 2, 'T', 1, 1, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseTransportPass.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExpiryFlash', 'Expiry Flash', 'Tools', 8, 'L', 49, 27, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmExpiryFlash.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmFoodCost', 'Cost Analysis', 'Stores', 9, 'R', 53, 33, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmFoodCost.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmGRN', 'GRN(Good Receiving Note)', 'Receiving', 4, 'T', 6, 1, '1', 'GRN.png', '1', 1, '1', '1', 'YES', 'YES', 'frmGRN.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Yes', NULL), "
		 * +
		 * " ('frmGRNRegisterReport', 'GRN Register Report', 'Receiving Reports', 9, 'R', 73, 5, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmGRNRegisterReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmGrnSlip', 'Grn Slip', 'Receiving Reports', 9, 'R', 35, 4, '1', 'GRN-Slip.png', '1', 1, '1', '1', 'NO', 'NO', 'frmGrnSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmGRNSummaryReport', 'GRN Summary Report', 'Receiving Reports', 9, 'R', 85, 6, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmGRNSummaryReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmGroupConsumption', 'Group Consumption Report', 'Stores', 7, 'R', 52, 31, '1', 'default.pmg', '1', 1, '1', '1', 'NO', 'NO', 'frmGroupConsumption.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmGroupMaster', 'Group Master', 'Master', 8, 'M', 7, 5, '1', 'Group_Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmGroupMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmPOSItemMasterImport', 'Import POS Item', 'Tools', 8, 'L', 87, 4, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmPOSItemMasterImport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmInterfaceMaster', 'Interface Master', 'Master', 7, 'M', 7, 7, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmInterfaceMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmInvoicingPrinting', 'Invoicing Printing', 'Reports', 6, 'R', 20, 1, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmInvoicingPrinting.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmInwardOutwardRegister', 'Inward Outward Register', 'Sub Contracting Report', 3, 'R', 77, 5, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmInwardOutwardRegister.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmIssueListingIndigeous', 'Issue Listing Indigeous', 'sub contracting Report', 3, 'R', 78, 6, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmIssueListingIndigeous.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmItemVariancePriceFlash', 'Item Variance Price Flash', 'Tools', 8, 'R', 55, 34, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmItemVariancePriceFlash.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmJOAllocation', 'Job Order Allocation', 'Sales', 2, 'T', 65, 6, '1', 'default.png', '6', 1, '1', '1', 'NO', 'Yes', 'frmJOAllocation.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmJobOrder', 'Job Order', 'Sales', 2, 'T', 59, 5, '1', 'default.png', '6', 1, '1', '1', 'NO', 'Yes', 'frmJobOrder.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmJobOrderAllocationSlip', 'Job Order Allocation Slip', 'Sub contracting Report', 3, 'R', 66, 8, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmJobOrderAllocationSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmJobOrderList', 'Job Order List', 'Sub contracting Report', 3, 'R', 64, 6, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmJobOrderList.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmJobOrderSlip', 'Job Order Slip', 'Sub contracting Report', 3, 'R', 63, 5, '1', 'defailt.png', '6', 1, '1', '1', 'NO', 'NO', 'frmJobOrderSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmJobWorkMonitoringReport', 'Job Work Monitoring Report', 'Sub Contracting Report', 3, 'R', 79, 6, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmJobWorkMonitoringReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmJobWorkRegister', 'Job Work Register', 'Sub Contracting Report', 3, 'R', 80, 7, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmJobWorkRegister.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmJV', 'JV Entry', 'Transaction', 1, 'T', 1, 1, '12', 'default.png', '5', 1, '1', '1', 'NO', '1', 'frmJV.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmJVreport', 'JV Report', 'Reports', 6, 'R', 70, 2, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmJVReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmLetterMaster', 'Letter Master', 'Master', 10, 'M', 10, 10, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmLetterMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmLetterProcessing', 'Letter Processing', 'Processing', 3, 'P', 2, 2, '1', 'default.png', '5', 1, '1', '1', 'YES', 'NO', 'frmLetterProcessing.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmLocationMaster', 'Location Master', 'Master', 7, 'M', 8, 6, '1', 'Location-Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmLocationMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmLocationWiseProductSlip', 'LocationWise Product Slip', 'Stores', 9, 'R', 41, 19, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmLocationWiseProductSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmLockerMaster', 'Locker Master', 'Master', 1, 'M', 1, 13, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmLockerMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmMaterialIssueRegisterReport', 'Material Issue Register Report', 'Stores', 9, 'R', 81, 35, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmMaterialIssueRegisterReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmMaterialIssueSlip', 'Material Issue Slip', 'Stores', 9, 'R', 34, 2, '1', 'Material-Issue-Slip.png', '1', 1, '1', '1', 'NO', 'NA', 'frmMaterialIssueSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmMaterialReq', 'Material Requisition', 'Cost Center', 1, 'T', 9, 1, '1', 'Requisition-Form.png', '1', 1, '1', '1', 'YES', 'YES', 'frmMaterialReq.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Yes', NULL), "
		 * +
		 * " ('frmMaterialReqSlip', 'Material Requisition Slip', 'Cost Center\\r\\n', 9, 'R', 37, 5, '1', 'Requisition-Slip.png', '1', 1, '1', '1', 'NO', 'NO', 'frmMaterialReqSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmMaterialReturn', 'Material Return', 'Cost Center', 1, 'T', 10, 2, '1', 'default.png', '1', 1, '1', '1', 'YES', 'YES', 'frmMaterialReturn.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Yes', NULL), "
		 * +
		 * " ('frmMaterialReturnDetail', 'Material Return Slip', 'Cost Center\\r\\n', 7, 'R', 54, 33, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmMaterialReturnDetail.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmMemberPhoto', 'Member Photo', 'Master', 1, 'M', 1, 14, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmMemberPhoto.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmMIS', 'Material Issue Slip', 'Store', 2, 'T', 11, 1, '1', 'Material-Issue-Slip.png', '1', 1, '1', '1', 'YES', 'YES', 'frmMIS.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Yes', NULL), "
		 * +
		 * " ('frmMISSummaryReport', 'MIS Summary Report', 'Stores', 9, 'R', 84, 36, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmMISSummaryReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmMonthEnd', 'Month End', 'Tools', 8, 'L', 2, 2, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmMonthEnd.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmNarrationMaster', 'Narration Master', 'Master', 1, 'M', 6, 6, '6', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmNarrationMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmNonMovingItemsReport', 'Non Moving Items Report', 'Stores', 7, 'R', 48, 26, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmNonMovingItemsReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmOpeningStock', 'Opening Stock', 'Store', 2, 'T', 12, 2, '1', 'Opening-Stocks.png', '1', 1, '1', '1', 'NO', 'YES', 'frmOpeningStock.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Yes', NULL), "
		 * +
		 * " ('frmOpeningStockSlip', 'Opening Stock Slip', 'Stores', 7, 'R', 50, 28, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmOpeningStockSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmParameterSetup', 'Parameter Setup', 'Setup', 3, 'S', 1, 1, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmParameterSetup.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmPayment', 'Payment', 'Transaction', 1, 'T', 1, 1, '12', 'default.png', '5', 1, '1', '1', 'NO', '1', 'frmPayment.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmPaymentReport', 'Payment Report', 'Reports', 6, 'R', 72, 4, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmPaymentReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmPendingDocFlash', 'Pending Document Flash', 'Tools', 8, 'L', 47, 25, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmPendingDocFlash.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmPendingNonStkMIS', 'Pending NonStkable MIS', 'Tools', 8, 'L', 48, 26, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmPendingNonStkMIS.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmPhysicalStkPosting', 'Physical Stk Posting', 'Store', 2, 'T', 13, 3, '1', 'Physical-Stock-Posting.png', '1', 1, '1', '1', 'NO', 'YES', 'frmPhysicalStkPosting.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Yes', NULL), "
		 * +
		 * " ('frmPhysicalStockPostingSlip', 'Physical Stock Posting Slip', 'Stores', 9, 'R', 10, 2, '1', 'Physical-Stock-Posting.png', '1', 1, '1', '1', 'NA', 'NA', 'frmPhysicalStockPostingSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmPOSLinkUp', 'POS Link Up', 'Tools', 8, 'L', 1, 1, '12', 'Attribute-Master.png', '1', 1, '1', '1', 'NO', '1', 'frmPOSLinkUp.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmPOSSalesDtl', 'POS Sales', 'Tools', 8, 'L', 1, 1, '12', 'Attribute-Master.png', '1', 1, '1', '1', 'NO', '1', 'frmPOSSalesDtl.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmProcessMaster', 'Process Master', 'Master', 8, 'M', 69, 17, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmProcessMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmProduction', 'Material Production', 'Production', 6, 'T', 14, 3, '1', 'Productions.png', '1', 1, '1', '1', 'NO', 'YES', 'frmProduction.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmProductionOrder', 'Meal Planing', 'Production', 6, 'T', 15, 1, '1', 'Production-Order.png', '1', 1, '1', '1', 'YES', 'YES', 'frmProductionOrder.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmProductionOrderSlip', 'Meal Planing Slip', 'Production\\r\\n', 9, 'R', 38, 9, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmProductionOrderSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmProductList', 'Product List', 'Listing', 9, 'R', 43, 21, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmProductList.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmProductMaster', 'Product Master', 'Master', 8, 'M', 16, 7, '1', 'Product-Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmProductMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmProductwiseSupplierwise', 'Productwise Supplierwise', 'Receiving Reports', 9, 'R', 44, 22, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmProductwiseSupplierwise.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmProdWiseSuppRateHis', 'Product Wise Supp Rate Histroy Report', 'Receiving Reports', 9, 'R', 40, 19, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmProdWiseSuppRateHis.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmPropertyMaster', 'Property Master', 'Master', 7, 'M', 17, 8, '1', 'Property-Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmPropertyMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmPurchaseIndent', 'Purchase Indent', 'Store', 2, 'T', 19, 4, '1', 'Purchase-Indent.png', '1', 1, '1', '1', 'YES', 'YES', 'frmPurchaseIndent.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Yes', NULL), "
		 * +
		 * " ('frmPurchaseIndentSlip', 'Purchase Indent Slip', 'Purchases', 9, 'R', 35, 3, '1', 'Purchase-Indent-Slip.png', '1', 1, '1', '1', 'NO', 'NO', 'frmPurchaseIndentSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmPurchaseOrder', 'Purchase Order', 'Purchase', 3, 'T', 20, 1, '1', 'Purchase-Order.png', '1', 1, '1', '1', 'YES', 'YES', 'frmPurchaseOrder.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Yes', NULL), "
		 * +
		 * " ('frmPurchaseOrderSlip', 'Purchase Order Slip', 'Purchases', 9, 'R', 36, 4, '1', 'Purchase-Order-Slip.png', '1', 1, '1', '1', 'NO', 'No', 'frmPurchaseOrderSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmPurchaseReturn', 'Purchase Return', 'Purchase', 3, 'T', 21, 2, '1', 'Purchase-Return.png', '1', 1, '1', '1', 'YES', 'YES', 'frmPurchaseReturn.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Yes', NULL), "
		 * +
		 * " ('frmPurchaseReturnSlip', 'Purchase Return Slip', 'Purchases', 7, 'R', 53, 32, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmPurchaseReturnSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmRateContract', 'Rate Contract', 'Purchase', 3, 'T', 22, 3, '1', 'Rate-Contract.png', '1', 1, '1', '1', 'NO', 'YES', 'frmRateContract.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmReasonMaster', 'Reason Master', 'Master', 8, 'M', 22, 10, '1', 'Reason-Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmReasonMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmReceipt', 'Receipt', 'Transaction', 1, 'T', 1, 1, '12', 'default.png', '5', 1, '1', '1', 'NO', '1', 'frmReceipt.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmReceiptissueConsolidated', 'Receipt Issue Consolidated', 'Stores', 9, 'R', 52, 32, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmReceiptIssueConsolidated.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmReceiptRegister', 'Receipt Register Report', 'Receiving Reports', 9, 'R', 46, 24, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmReceiptRegister.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmRecipes', 'Recipes List', 'Listing', 9, 'R', 39, 10, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmRecipesList.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmReciptReport', 'Recipt Report', 'Reports', 6, 'R', 71, 3, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmReciptReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmReorderLevelReport', 'Reorder Level Report', 'Stores', 7, 'R', 47, 25, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmReorderLevelReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmReorderLevelwise', 'Locationwise Productwise Reorder', 'Stores', 9, 'R', 45, 23, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmReorderLevelwise.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmSalesOrder', 'Sales Order', 'Sales', 2, 'T', 51, 1, '1', 'default.png', '6', 1, '1', '1', 'YES', 'YES', 'frmSalesOrder.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmSalesOrderBOM', 'Sales Order BOM', 'Sales', 2, 'T', 55, 2, '1', 'default.png', '6', 1, '1', '1', 'NO', 'Yes', 'frmSalesOrderBOM.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmSalesOrderList', 'Sales Order List', 'Reports', 3, 'R', 61, 2, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmSalesOrderList.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmSalesOrderSlip', 'Sales Order Slip', 'Reports', 3, 'R', 60, 3, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmSalesOrderSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmSalesOrderStatus', 'Sales Order Status', 'Sales', 2, 'L', 68, 9, '1', 'default.png', '6', 1, '1', '1', 'NO', 'Yes', 'frmSalesOrderStatus.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmSanctionAutherityMaster', 'Sanction Autherity Master', 'Master', 8, 'M', 8, 8, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmSanctionAutherityMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmScrapGenerated', 'Scrap Generated', 'Sub Contracting Report', 3, 'R', 82, 8, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmScrapGenerated.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmSecurityShell', 'Security Shell', 'Master', 7, 'M', 23, 11, '1', 'Security-Shell.png', '1', 1, '1', '1', 'NO', 'YES', 'frmSecurityShell.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmSetup', 'Property Setup', 'Tools', 8, 'L', 24, 12, '1', 'Setup.png', '1', 1, '1', '1', 'NO', 'YES', 'frmSetup.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmSlowMovingItemsReport', 'Slow Moving Items Report', 'Stores', 7, 'R', 49, 27, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmSlowMovingItemsReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmStkVarianceFlash', 'Stock Variance Flash', 'Tools', 8, 'L', 50, 28, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmStkVarianceFlash.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmStockAdjustment', 'Stock Adjustment', 'Store', 2, 'T', 25, 5, '1', 'Stock-Adjustment.png', '1', 1, '1', '1', 'NO', 'YES', 'frmStockAdjustment.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Yes', NULL), "
		 * +
		 * " ('frmStockAdjustmentFlash', 'Stock Adjustment Flash', 'Tools', 8, 'L', 25, 13, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmStockAdjustmentFlash.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmStockAdjustmentSlip', 'Stock Adjustment Slip', 'Stores', 9, 'R', 33, 1, '1', 'Stock-Adjustment-Slip.png', '1', 1, '1', '1', 'NO', 'NA', 'frmStockAdjustmentSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmStockFlash', 'Stock Flash', 'Tools', 8, 'L', 10, 10, '1', 'Stocks-Flash.png', '1', 1, '1', '1', 'NO', 'NO', 'frmStockFlash.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmStockLedger', 'Stock Ledger', 'Tools', 8, 'L', 10, 12, '1', 'Stock-Ledger.png', '1', 1, '1', '1', 'NO', 'NO', 'frmStockLedger.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmStockLedgerReportCRM', 'Stock Ledger', 'SuB Contracting Report', 3, 'R', 83, 8, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmStockLedgerReportCRM.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmStockTransfer', 'Stock Transfer', 'Cost Center', 1, 'T', 26, 3, '1', 'Stock-Transfer.png', '1', 1, '1', '1', 'NO', 'YES', 'frmStockTransfer.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 'Yes', NULL), "
		 * +
		 * " ('frmStockTransferSlip', 'Stock Transfer Slip', 'Stores', 9, 'R', 37, 6, '1', 'Stock-Transfer-Slip.png', '1', 1, '1', '1', 'NO', 'NO', 'frmStockTransferSlip.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmStructureUpdate', 'Structure Update', 'Tools', 8, 'L', 10, 2, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmStructureUpdate.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmSubCategoryMaster', 'Sub Category Master', 'Master', 1, 'M', 1, 11, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmSubCategoryMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmSubContractorGRN', 'Sub Contractor GRN', 'Sales', 2, 'T', 66, 7, '1', 'default.png', '6', 1, '1', '1', 'NO', 'Yes', 'frmSubContractorGRN.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmSubContractorGRNSlip', 'Sub-Contractor GRN Slip', 'Sub contracting Report', 3, 'R', 65, 7, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmSubContractorGRNSlip.html', '(NULL)', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmSubContractorMaster', 'Sub Contractor Master', 'Master', 1, 'M', 58, 2, '1', 'default.png', '6', 1, '1', '1', 'NO', 'NO', 'frmSubContractorMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmSubGroupMaster', 'Sub Group Master', 'Master', 8, 'M', 27, 13, '1', 'Sub-Group-Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmSubGroupMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmSundryDebtorMaster', 'Sundry Debtor Master', 'Master', 9, 'M', 9, 9, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmSundryDebtorMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmSupplierMaster', 'Supplier Master', 'Master', 8, 'M', 28, 14, '1', 'Supplier-Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmSupplierMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmTaxMaster', 'Tax Master', 'Master', 8, 'M', 29, 15, '1', 'Tax-Master.png', '1', 1, '1', '1', 'NO', 'YES', 'frmTaxMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmTCMaster', 'TC Master', 'Master', 8, 'M', 34, 18, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NA', 'frmTCMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmUDReportCategoryMaster', 'UD Report Category Master', 'Master', 8, 'M', 1, 1, '12', 'Attribute-Master.png', '1', 1, '1', '1', 'NO', '1', 'frmUDReportCategoryMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmUOMMaster', 'UOM Master', 'Master', 8, 'M', 35, 19, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmUOMMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmUserDefinedReport', 'User Defined Report', 'Tools', 8, 'L', 1, 1, '12', 'Attribute-Master.png', '1', 1, '1', '1', 'NO', '1', 'frmUserDefinedReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmUserDefinedReportView', 'User Defined Report View', 'Reports', 7, 'R', 25, 25, '12', 'Attribute-Master.png', '1', 1, '1', '1', 'NO', '1', 'frmUserDefinedReportView.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmUserMaster', 'User Master', 'Master', 7, 'M', 31, 17, '1', 'User-Management.png', '1', 1, '1', '1', 'NO', 'YES', 'frmUserMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmWebBooksAccountMaster', 'Account Master', 'Master', 1, 'M', 1, 1, '1', 'default.png', '5', 1, '1', '1', 'NO', 'NO', 'frmWebBooksAccountMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmWebBooksDeleteTransaction', 'Delete Transaction', 'Transaction', 1, 'T', 1, 1, '12', 'default.png', '5', 1, '1', '1', 'NO', '1', 'frmWebBooksDeleteTransaction.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmWebClubDependentMaster', 'Dependent Master', 'Master', 1, 'M', 1, 2, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmDependentMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmWebClubGeneralMaster', 'General Master', 'Master', 1, 'M', 1, 9, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmGeneralMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmWebClubGroupMaster', 'Group Master', 'Master', 1, 'M', 1, 7, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmWebClubGroupMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmWebClubMemberCategoryMaster', 'Member Category Master', 'Master', 1, 'M', 1, 3, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmMemberCategoryMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmWebClubMemberHistory', 'Member History', 'Master', 1, 'M', 1, 4, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmMemberHistory.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmWebClubMemberPreProfile', 'Member Pre-Profile', 'Master', 1, 'M', 1, 5, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmMemberPreProfile.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmWebClubMemberProfile', 'Member Profile', 'Master', 1, 'M', 1, 1, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmMemberProfile.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmWebClubMembershipFormGenration', 'Membership Form Genration', 'Master', 1, 'M', 1, 6, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmMembershipFormGenration.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmWebClubPersonMaster', 'Person Master', 'Master', 1, 'M', 1, 8, '1', 'default.png', '4', 1, '1', '1', 'NO', 'NO', 'frmWebClubPersonMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmWhatIfAnalysis', 'What If Analysis Tool', 'Tools', 8, 'L', 10, 2, '1', 'What-If-Analysis-Tool.png', '1', 1, '1', '1', 'NO', 'NO', 'frmWhatIfAnalysis.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmWorkOrder', 'Work Order', 'Production', 6, 'T', 32, 2, '1', 'Work-Order.png', '1', 1, '1', '1', 'YES', 'YES', 'frmWorkOrder.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('Listing', 'Listing', 'Reports', 7, 'O', 1, 1, '1', 'default.png', '1', 1, '1', '1', 'NA', 'NA', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('Master', 'Master', 'Tools', 8, 'O', 1, 1, '1', 'default.png', '1', 1, '1', '1', 'No', 'No', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('Production\\r\\n', 'Production\\r\\n', 'Reports', 7, 'O', 1, 1, '1', 'default.png', '1', 1, '1', '1', 'NA', 'NA', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('Purchases', 'Purchases', 'Reports', 7, 'O', 1, 1, '1', 'default.png', '1', 1, '1', '1', 'NA', 'NA', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('Receiving Reports', 'Receiving Reports', 'Reports', 7, 'O', 1, 1, '1', 'default.png', '1', 1, '1', '1', 'NA', 'NA', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('Stores', 'Stores', 'Reports', 7, 'O', 1, 1, '1', 'default.png', '1', 1, '1', '1', 'NA', 'NA', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('Sub Contracting Report', 'Sub Contracting Report', 'Reports', 3, 'O', 1, 1, '1', 'default.png', '6', 1, '1', '1', 'No', 'No', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),  "
		 * +
		 * " ('frmDatabaseDataImport', 'Data Import', 'Tools', 8, 'L', 86, 1, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmDatabaseDataImport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmRecipeCostiong', 'Recipes Costing', 'Listing', 9, 'R', 87, 11, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmRecipeCosting.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmLossCalculationReport', 'Loss Calculation Report', 'Listing', 9, 'R', 88, 13, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmLossCalculationReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmMasterList', 'Master List', 'Listing', 9, 'R', 44, 22, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmMasterList.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseCategoryWiseSale', 'Category Wise Sale', 'Reports', 3, 'R', 187, 11, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseCategoryWiseSale.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmBrandWiseClosingReport', 'Brand WiseClosing Report', 'Reports', 3, 'R', 188, 12, '12', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmBrandWiseClosingReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) ,"
		 * +
		 * " ('frmExciseUserMaster', 'Excise User Master', 'Master', 1, 'M', 25, 10, '2', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseUserMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseSecurityShell','Excise Security Shell', 'Tools', 8, 'T', 11, 11, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseSecurityShell.html',NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmTallyLinkUp', 'Tally Link Up', 'Tools', 8, 'L', 189, 23, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmTallyLinkUp.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmDocumentListingFlashReport', 'Document Listing Flash Report', 'Tools', 8, 'L', 190, 24, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmDocumentListingFlashReport.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmProductPurchaseReciept', 'Product Purchase Reciept', 'Receiving Reports', 9, 'R', 191, 25, '1', 'default.png', '1', 1, '1', '1', 'NO', 'NO', 'frmProductPurchaseReciept.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * " ('frmExciseMonthEnd', 'Excise Month End', 'Tools', 8, 'T', 12, 12, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExciseMonthEnd.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL), "
		 * +
		 * "('frmExcisePropertyMaster', 'Excise Property Master', 'Master', 1, 'M', 26, 11, '1', 'default.png', '2', 1, '1', '1', 'NO', 'NO', 'frmExcisePropertyMaster.html', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL) "
		 * ; funExecuteQuery(sql);
		 */
		/*----------------Excise Forms End---------------------------*/

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
	private int funExciseExecuteQuery(String sql) {
		try {
			Query query = exciseSessionFactory.getCurrentSession().createSQLQuery(sql);
			query.executeUpdate();
		} finally {
			return 0;
		}

	}

	@Override
	public void funExciseClearTransaction(String clientCode, String[] str) {
		for (int i = 0; i < str.length; i++) {
			String sql = "";
			String moduleName = str[i];

			switch (moduleName) {

			case "Transport Pass": {
				sql = " delete from tbltphd where strClientCode='" + clientCode + "' ";
				funExciseExecuteQuery(sql);
				sql = "delete from  tbltpdtl where strClientCode='" + clientCode + "' ";
				funExciseExecuteQuery(sql);
				break;
			}

			case "Excise Sale": {
				sql = "delete from  tblmanualsaleshd where strClientCode='" + clientCode + "' ";
				funExciseExecuteQuery(sql);
				sql = "delete from tblmanualsalesdtl where strClientCode='" + clientCode + "' ";
				funExciseExecuteQuery(sql);
				break;
			}
			case "Bill Generate": {
				sql = "delete from tblexcisesaledata where strClientCode='" + clientCode + "' ";
				funExciseExecuteQuery(sql);
				break;
			}
			case "One Day Pass": {
				sql = "delete from tblonedaypass where strClientCode='" + clientCode + "'  ";
				funExciseExecuteQuery(sql);
				break;
			}
			case "Opening Stock": {
				sql = "delete from tblopeningstock where strClientCode='" + clientCode + "' ";
				funExciseExecuteQuery(sql);
				break;
			}
			}

		}
	}

	@Override
	public void funExciseClearMaster(String clientCode, String[] str) {

		for (int i = 0; i < str.length; i++) {
			String sql = "";
			String masterName = str[i];
			switch (masterName) {

			case "Licence Master": {
				sql = "truncate table  tbllicencemaster ";
				funExciseExecuteQuery(sql);
				break;
			}

			case "Brand Master": {
				sql = "truncate table tblbrandmaster  ";
				funExciseExecuteQuery(sql);
				break;
			}
			case "Size Master": {
				sql = "truncate table   tblsizemaster";
				funExciseExecuteQuery(sql);
				break;
			}
			case "Supplier Master": {
				sql = "truncate table  tblsuppliermaster ";
				funExciseExecuteQuery(sql);
				break;
			}
			case "Excise Recipe Masterr": {
				sql = "truncate table tblexciserecipermasterhd  ";
				funExciseExecuteQuery(sql);
				sql = "truncate table tblexciserecipermasterdtl  ";
				funExciseExecuteQuery(sql);
				break;
			}

			case "Property Set Up ": {
				sql = "truncate table tblexcisepropertymaster  ";
				funExciseExecuteQuery(sql);
				break;
			}

			case "Location Master": {
				sql = "truncate table  tblexciselocationmaster ";
				funExciseExecuteQuery(sql);
				break;
			}
			case " Permit Master": {
				sql = "truncate table tblexcisepermitmaster  ";
				funExciseExecuteQuery(sql);
				break;
			}
			case "City Master": {
				sql = "truncate table   tblcitymaster";
				funExciseExecuteQuery(sql);
				break;
			}

			}
		}
	}
}