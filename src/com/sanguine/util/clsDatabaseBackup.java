package com.sanguine.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.sanguine.controller.clsGlobalFunctions;



@Controller
public class clsDatabaseBackup {


	private clsGlobalFunctions objGlobalFun;
	
	  public String funTakeBackUpDB(String dbName) throws Exception
	    {
	        //funCheckBackUpFilePath();

	        Date dtCurrentDate = new Date();
	        String date = dtCurrentDate.getDate() + "-" + (dtCurrentDate.getMonth() + 1) + "-" + (dtCurrentDate.getYear() + 1900);
	        String time = dtCurrentDate.getHours() + "-" + dtCurrentDate.getMinutes();
	        String fileName = date + "_" + time + dbName;
	        
	        
	        String batchFilePath = System.getProperty("user.dir") + "\\mysqldbbackup"+dbName+".bat";
	        String filePath = System.getProperty("user.dir") + "/DBBackup";
	        File file = new File(filePath);
	        if (!file.exists())
	        {
	            file.mkdir();
	        }

	        File batchFile = new File(batchFilePath);
	        if (!batchFile.exists())
	        {
	            batchFile.createNewFile();
	        }
	        BufferedWriter objWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(batchFile), "UTF8"));
	        objWriter.write("@echo off");
	        objWriter.newLine();
	        objWriter.write("for /f \"tokens=1\" %%i in ('date /t') do set DATE_DOW=%%i");
	        objWriter.newLine();
	        objWriter.write("for /f \"tokens=2\" %%i in ('date /t') do set DATE_DAY=%%i");
	        objWriter.newLine();
	        objWriter.write("for /f %%i in ('echo %date_day:/=-%') do set DATE_DAY=%%i");
	        objWriter.newLine();

	        objWriter.write("for /f %%i in ('time /t') do set DATE_TIME=%%i");
	        objWriter.newLine();
	        objWriter.write("for /f %%i in ('echo %date_time::=-%') do set DATE_TIME=%%i");
	        objWriter.newLine();

	        String fileFullNamemms = filePath + "\\" + fileName + ".sql";
	        String dumpPath=clsGlobalFunctions.urlSqlDump;

	        
	        objWriter.write(dumpPath + " --hex-blob " + " -u " + clsGlobalFunctions.urluser + " -p" + clsGlobalFunctions.urlPassword + " -h " + "localhost" + " --default-character-set=utf8 --max_allowed_packet=64M --add-drop-table --skip-add-locks --skip-comments --add-drop-database --databases " + " " + dbName + ">" + "\"" + fileFullNamemms + "\" ");
	        
	      //  objWriter.write(dumpPath + " --hex-blob " + " -u " + clsGlobalFunctions.urluser + " -p" + clsGlobalFunctions.urlPassword + " -h " + "localhost" + " --default-character-set=utf8 --max_allowed_packet=64M --add-drop-table --skip-add-locks --skip-comments --add-drop-database --databases " + " " + dbWebbook + ">" + "\"" + fileFullName + "\" ");
	        
	       
	        objWriter.flush();
	        objWriter.close();

	        Process p = Runtime.getRuntime().exec("cmd /c " + "\"" + batchFilePath + "\"");

	        return fileName;
	    }
	  
/*
	 private boolean funCheckBackUpFilePath()
	    {
	        boolean isValidPath = true;
	        try
	        {
	            String p = clsPosConfigFile.gDatabaseBackupFilePath.replaceAll("\"", "");
	            File f = new File(p);
	            if (!f.getParentFile().exists())
	            {
	                isValidPath = false;
	                //JOptionPane.showMessageDialog(null, "Invalid MySQL File Path!!!\nPlease Check DBConfig File.");
	                //System.exit(0);
	            }
	            else if (!f.getPath().split("bin")[1].equals("\\mysqldump"))
	            {
	                isValidPath = false;
	                //JOptionPane.showMessageDialog(null, "Invalid MySQL File Path!!!\nPlease Check DBConfig File.");
	                //System.exit(0);
	            }
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	            isValidPath = false;
	            //System.exit(0);
	        }

	        return isValidPath;
	    }
*/
}
