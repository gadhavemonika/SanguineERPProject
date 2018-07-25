package com.sanguine.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.sanguine.model.clsCurrencyMasterModel;
import com.sanguine.service.clsCurrencyMasterService;

public class clsNumberToWords {

	private String amtInWords;

	private static final String[] tensNames = { "", " Ten", " Twenty", " Thirty", " Forty", " Fifty", " Sixty", " Seventy", " Eighty", " Ninety" };

	private static final String[] numNames = { "", " One", " Two", " Three", " Four", " Five", " Six", " Seven", " Eight", " Nine", " Ten", " Eleven", " Twelve", " Thirteen", " Fourteen", " Fifteen", " Sixteen", " Seventeen", " Eighteen", " Nineteen" };

	public clsNumberToWords() {
	}

	private static String convertLessThanOneThousand(int number) {
		String soFar;

		if (number % 100 < 20) {
			soFar = numNames[number % 100];
			number /= 100;
		} else {
			soFar = numNames[number % 10];
			number /= 10;

			soFar = tensNames[number % 10] + soFar;
			number /= 10;
		}
		if (number == 0)
			return soFar;
		return numNames[number] + " Hundred" + soFar;
	}

	public static String convert(long number) {
		// 0 to 999 999 999 999
		if (number == 0) {
			return "zero";
		}

		String snumber = Long.toString(number);

		// pad with "0"
		String mask = "000000000000";
		DecimalFormat df = new DecimalFormat(mask);
		snumber = df.format(number);

		// XXXnnnnnnnnn
		int billions = Integer.parseInt(snumber.substring(0, 3));
		// nnnXXXnnnnnn
		int millions = Integer.parseInt(snumber.substring(3, 6));
		// nnnnnnXXXnnn
		int hundredThousands = Integer.parseInt(snumber.substring(6, 9));
		// nnnnnnnnnXXX
		int thousands = Integer.parseInt(snumber.substring(9, 12));

		String tradBillions;
		switch (billions) {
		case 0:
			tradBillions = "";
			break;
		case 1:
			tradBillions = convertLessThanOneThousand(billions) + " Billion ";
			break;
		default:
			tradBillions = convertLessThanOneThousand(billions) + " Billion ";
		}
		String result = tradBillions;

		String tradMillions;
		switch (millions) {
		case 0:
			tradMillions = "";
			break;
		case 1:
			tradMillions = convertLessThanOneThousand(millions) + " Million ";
			break;
		default:
			tradMillions = convertLessThanOneThousand(millions) + " Million ";
		}
		result = result + tradMillions;

		String tradHundredThousands;
		switch (hundredThousands) {
		case 0:
			tradHundredThousands = "";
			break;
		case 1:
			tradHundredThousands = "One Thousand ";
			break;
		default:
			tradHundredThousands = convertLessThanOneThousand(hundredThousands) + " Thousand ";
		}
		result = result + tradHundredThousands;

		String tradThousand;
		tradThousand = convertLessThanOneThousand(thousands);
		result = result + tradThousand;

		// remove extra spaces!
		return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
	}

	/**
	 * testing
	 * 
	 * @param args
	 */
	public String getNumberInWorld(double d) {

		String fileName = BigDecimal.valueOf(d).toPlainString();
		String[] fileNameSplit = fileName.split("\\.");
		long p1 = Long.parseLong(fileNameSplit[0]);
		long p2 = Long.parseLong(fileNameSplit[1]);
		/*
		 * System.out.println(fileNameSplit[0]);
		 * System.out.println(fileNameSplit[1]);
		 */
		String NumberInWord = "";
		NumberInWord += clsNumberToWords.convert(p1);
		NumberInWord += " And ";
		NumberInWord += clsNumberToWords.convert(p2);
		NumberInWord += " Paisa Only ";
		System.out.println("****** " + NumberInWord);

		return NumberInWord;

	}

	public String getNumberInWorld(double d, String shortName) 
	{
		
		String fileName = String.valueOf(d);

		String[] fileNameSplit = fileName.split("\\.");
		long p1 = Long.parseLong(fileNameSplit[0]);
		long p2 = Long.parseLong(fileNameSplit[1]);
		/*
		 * System.out.println(fileNameSplit[0]);
		 * System.out.println(fileNameSplit[1]);
		 */
		String NumberInWord = "";
		NumberInWord += clsNumberToWords.convert(p1);
		NumberInWord += " and ";
		NumberInWord += clsNumberToWords.convert(p2);
		NumberInWord += " " + shortName + " Only ";
		System.out.println("****** " + NumberInWord);

		return NumberInWord;

	}

	public static void main(String[] args) {
		/*
		 * clsNumberToWords ob=new clsNumberToWords();
		 * ob.getNumberInWorld(1000.523);
		 */

		clsNumberToWords obj = new clsNumberToWords();
		System.out.println(obj.funConvertAmtInWords(201735.00));

	}

	public String funConvertAmtInWords(double amount) {
		String amountInWords = "";
		String strAmt = String.valueOf(amount);
		strAmt = strAmt.replace(".", ",");

		String strPaise = strAmt.split(",")[1];
		long paise = Long.parseLong(strPaise);

		if (strPaise.length() == 1) {
			paise = paise * 10;
		}
		String amt = funGetAmtInWords(Long.parseLong(strAmt.split(",")[0]));
		String paisa = funGetAmtInWords(paise);

		if (paisa.trim().isEmpty()) {
			paisa = "Zero";
		}

		amountInWords = amt + " And " + paisa + " Paisa Only";
		return amountInWords;
	}

	private String funGetAmtInWords(long amt) {
		amtInWords = "";
		funDisplayAmtInWords(amt);
		return amtInWords;
	}

	private int funDisplayAmtInWords(long amt) {
		String strAmt = String.valueOf(amt);

		switch (strAmt.length()) {
		case 1:
			amtInWords = amtInWords + " " + funZeroToNintyNineWords(amt);
			break;

		case 2:
			amtInWords = amtInWords + " " + funZeroToNintyNineWords(amt);
			break;

		case 3:
			long res = amt / 100;
			if (res > 0) {
				String text = funZeroToNintyNineWords(res);
				amtInWords = amtInWords + " " + text + " Hundred";
				long rem = amt % 100;
				funDisplayAmtInWords(rem);
			}
			break;

		case 4:
			long res1 = amt / 1000;
			if (res1 > 0) {
				String text = funZeroToNintyNineWords(res1);
				amtInWords = amtInWords + " " + text + " Thousand";
				long rem = amt % 1000;
				funDisplayAmtInWords(rem);
			}
			break;

		case 5:
			long res2 = amt / 1000;
			if (res2 > 0) {
				String text = funZeroToNintyNineWords(res2);
				amtInWords = amtInWords + " " + text + " Thousand";
				long rem = amt % 1000;
				funDisplayAmtInWords(rem);
			}
			break;

		case 6:
			long res3 = amt / 100000;
			if (res3 > 0) {
				String text = funZeroToNintyNineWords(res3);
				amtInWords = amtInWords + " " + text + " Lakh";
				long rem = amt % 100000;
				funDisplayAmtInWords(rem);
			}
			break;

		case 7:
			long res4 = amt / 100000;
			if (res4 > 0) {
				String text = funZeroToNintyNineWords(res4);
				amtInWords = amtInWords + " " + text + " Lakh";
				long rem = amt % 100000;
				funDisplayAmtInWords(rem);
			}
			break;
		}
		return 1;
	}

	private String funZeroToNintyNineWords(long index) {
		String words = "";
		int ind = (int) index;
		switch (ind) {
		case 1:
			words = "One";
			break;

		case 2:
			words = "Two";
			break;

		case 3:
			words = "Three";
			break;

		case 4:
			words = "Four";
			break;

		case 5:
			words = "Five";
			break;

		case 6:
			words = "Six";
			break;

		case 7:
			words = "Seven";
			break;

		case 8:
			words = "Eight";
			break;

		case 9:
			words = "Nine";
			break;

		case 10:
			words = "Ten";
			break;

		case 12:
			words = "Twelve";
			break;

		case 13:
			words = "Thirteen";
			break;

		case 14:
			words = "Fourteen";
			break;

		case 15:
			words = "Fifteen";
			break;

		case 16:
			words = "Sixteen";
			break;

		case 17:
			words = "Seventeen";
			break;

		case 18:
			words = "Eighteen";
			break;

		case 19:
			words = "Nineteen";
			break;

		case 20:
			words = "Twenty";
			break;

		case 21:
			words = "Twenty One";
			break;

		case 22:
			words = "Twenty Two";
			break;

		case 23:
			words = "Twenty Three";
			break;

		case 24:
			words = "Twenty Four";
			break;

		case 25:
			words = "Twenty Five";
			break;

		case 26:
			words = "Twenty Six";
			break;

		case 27:
			words = "Twenty Seven";
			break;

		case 28:
			words = "Twenty Eight";
			break;

		case 29:
			words = "Twenty Nine";
			break;

		case 30:
			words = "Thirty";
			break;

		case 31:
			words = "Thirty One";
			break;

		case 32:
			words = "Thirty Two";
			break;

		case 33:
			words = "Thirty Three";
			break;

		case 34:
			words = "Thirty Four";
			break;

		case 35:
			words = "Thirty Five";
			break;

		case 36:
			words = "Thirty Six";
			break;

		case 37:
			words = "Thirty Seven";
			break;

		case 38:
			words = "Thirty Eight";
			break;

		case 39:
			words = "Thirty Nine";
			break;

		case 40:
			words = "Fourty";
			break;

		case 41:
			words = "Fourty One";
			break;

		case 42:
			words = "Fourty Two";
			break;

		case 43:
			words = "Fourty Three";
			break;

		case 44:
			words = "Fourty Four";
			break;

		case 45:
			words = "Fourty Five";
			break;

		case 46:
			words = "Fourty Six";
			break;

		case 47:
			words = "Fourty Seven";
			break;

		case 48:
			words = "Fourty Eight";
			break;

		case 49:
			words = "Fourty Nine";
			break;

		case 50:
			words = "Fifty";
			break;

		case 51:
			words = "Fifty One";
			break;

		case 52:
			words = "Fifty Two";
			break;

		case 53:
			words = "Fifty Three";
			break;

		case 54:
			words = "Fifty Four";
			break;

		case 55:
			words = "Fifty Five";
			break;

		case 56:
			words = "Fifty Six";
			break;

		case 57:
			words = "Fifty Seven";
			break;

		case 58:
			words = "Fifty Eight";
			break;

		case 59:
			words = "Fifty Nine";
			break;

		case 60:
			words = "Sixty";
			break;

		case 61:
			words = "Sixty One";
			break;

		case 62:
			words = "Sixty Two";
			break;

		case 63:
			words = "Sixty Three";
			break;

		case 64:
			words = "Sixty Four";
			break;

		case 65:
			words = "Sixty Five";
			break;

		case 66:
			words = "Sixty Six";
			break;

		case 67:
			words = "Sixty Seven";
			break;

		case 68:
			words = "Sixty Eight";
			break;

		case 69:
			words = "Sixty Nine";
			break;

		case 70:
			words = "Seventy";
			break;

		case 71:
			words = "Seventy One";
			break;

		case 72:
			words = "Seventy Two";
			break;

		case 73:
			words = "Seventy Three";
			break;

		case 74:
			words = "Seventy Four";
			break;

		case 75:
			words = "Seventy Five";
			break;

		case 76:
			words = "Seventy Six";
			break;

		case 77:
			words = "Seventy Seven";
			break;

		case 78:
			words = "Seventy Eight";
			break;

		case 79:
			words = "Seventy Nine";
			break;

		case 80:
			words = "Eighty";
			break;

		case 81:
			words = "Eighty One";
			break;

		case 82:
			words = "Eighty Two";
			break;

		case 83:
			words = "Eighty Three";
			break;

		case 84:
			words = "Eighty Four";
			break;

		case 85:
			words = "Eighty Five";
			break;

		case 86:
			words = "Eighty Six";
			break;

		case 87:
			words = "Eighty Seven";
			break;

		case 88:
			words = "Eighty Eight";
			break;

		case 89:
			words = "Eighty Nine";
			break;

		case 90:
			words = "Ninety";
			break;

		case 91:
			words = "Ninety One";
			break;

		case 92:
			words = "Ninety Two";
			break;

		case 93:
			words = "Ninety Three";
			break;

		case 94:
			words = "Ninety Four";
			break;

		case 95:
			words = "Ninety Five";
			break;

		case 96:
			words = "Ninety Six";
			break;

		case 97:
			words = "Ninety Seven";
			break;

		case 98:
			words = "Ninety Eight";
			break;

		case 99:
			words = "Ninety Nine";
			break;
		}
		return words;
	}

}
