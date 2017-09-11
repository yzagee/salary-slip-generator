package org.apptivo.hr;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.Version;


public class SalarySlipGenerator {
	
	private static String defaultFromEmailAddress = "hr@apptivo.com";
	private static String defaultToEmailAddress = "bkolagani@apptivo.co.in";
	
	// PART I - COL I
	static int ROW_EMP_ID = 10;
	static int COL_EMP_ID = 3;
	
	static int ROW_EMP_NAME = 11;
	static int COL_EMP_NAME = 3;
	
	static int ROW_EMP_DES = 12;
	static int COL_EMP_DES = 3;
	
	static int ROW_BANK_NAME = 13;
	static int COL_BANK_NAME = 3;
	
	static int ROW_ACCT_NUM = 14;
	static int COL_ACCT_NUM = 3;
	
	static int ROW_EMP_DOJ = 15;
	static int COL_EMP_DOJ = 3;
	
	static int ROW_EMP_DOB = 16;
	static int COL_EMP_DOB = 3;
	
	static int ROW_EMP_PAN = 17;
	static int COL_EMP_PAN = 3;
	
	// PART I - COL II
	static int ROW_LOC = 10;
	static int COL_LOC = 6;
	
	static int ROW_EMP_ESI = 11;
	static int COL_EMP_ESI = 6;
	
	static int ROW_EMP_EPF = 12;
	static int COL_EMP_EPF = 6;
	
	static int ROW_EMP_UAN = 13;
	static int COL_EMP_UAN = 6;
	
	static int ROW_EMPTY = 14;
	static int COL_EMPtY = 6;
	
	static int ROW_TOT_DAYS = 15;
	static int COL_TOT_DAYS = 6;
	
	static int ROW_LOP = 16;
	static int COL_LOP = 6;
	
	static int ROW_WORK_DAYS = 17;
	static int COL_WORK_DAYS = 6;
	
	// PART II - COL I
	static int ROW_BASIC = 23;
	static int COL_BASIC = 4;
	
	static int ROW_HRA = 24;
	static int COL_HRA = 4;
	
	static int ROW_CONVEYANCE = 25;
	static int COL_CONVEYANCE = 4;
	
	static int ROW_OTHER_ALLOWANCES = 26;
	static int COL_OTHER_ALLOWANCES = 4;
	
	static int ROW_LEAVE_ENCASHMENT = 27;
	static int COL_LEAVE_ENCASHMENT = 4;
	
	static int ROW_BONUS_QTRLY = 28;
	static int COL_BONUS_QTRLY = 4;
	
	static int ROW_TOTAL_EARNINGS = 29;
	static int COL_TOTAL_EARNINGS = 4;
	
	static int ROW_TAKE_HOME = 30;
	static int COL_TAKE_HOME = 4;
	
	// PART II - COL II
	static int ROW_EPF_EMPLOYEE = 23;
	static int COL_EPF_EMPLOYEE = 7;
	
	static int ROW_ESIC_EMPLOYEE = 24;
	static int COL_ESIC_EMPLOYEE = 7;
	
	static int ROW_PROF_TAX = 25;
	static int COL_PROF_TAX = 7;
	
	static int ROW_SAL_ADVANCE = 26;
	static int COL_SAL_ADVANCE = 7;
	
	static int ROW_INCOME_TAX = 27;
	static int COL_INCOME_TAX = 7;
	
	static int ROW_LABOUR_FUND = 28;
	static int COL_LABOUR_FUND = 7;
	
	static int ROW_TOTAL_DEDUCTION = 29;
	static int COL_TOTAL_DEDUCTION = 7;
	
	static int ROW_DATE = 30;
	static int COL_DATE = 7;
	
	static int EMP_EMAIL_COL = 19;
	
	private static Properties properties;
	
	private static String employeeMasterSheetName;
	private static String payslipSheetName;
	private static String salaryDate;
	private static String salaryMonth;
	private static String salaryYear;
	private static String dataFilePath;
	private static String templatePath;
	private static String templateName;
	private static String htmlFilePath;
	private static String headerImagePath;
	private static String pdfFilePath;
	private static String smtpHost;
	private static String toEmail;
	private static String fromEmail;
	private static String pdfTemplateDir;
	private static String wkhtmlToPdfCmdName;

	private static boolean useProxy;
	private static String proxyHost;
	private static int proxyPort;
	
	// Finds the workbook instance for XLSX file
	private static XSSFWorkbook myWorkBook = null;
	
	static {
		loadProperties();
		
		employeeMasterSheetName = properties.getProperty("employee.master.sheet.name");
		payslipSheetName = properties.getProperty("payslip.sheet.name");
		System.out.println("payslipSheetName:" + payslipSheetName);
		salaryDate = properties.getProperty("salary.date");
		salaryMonth = properties.getProperty("salary.month");
		salaryYear = properties.getProperty("salary.year");
		templatePath = properties.getProperty("template.path");
		templateName = properties.getProperty("template.name");
		htmlFilePath = properties.getProperty("html.dir");
		headerImagePath = properties.getProperty("header.image.path");
		pdfFilePath = properties.getProperty("pdf.dir");
		smtpHost = properties.getProperty("mail.smtp.host");
		toEmail = properties.getProperty("to.email.address", defaultToEmailAddress);
		fromEmail = properties.getProperty("from.email.address", defaultFromEmailAddress);
		pdfTemplateDir = properties.getProperty("PDF_TEMPLATE_DIR");
		wkhtmlToPdfCmdName = properties.getProperty("wkhtml.cmd.name");
		
		useProxy = Boolean.parseBoolean(properties.getProperty("use.proxy", "false"));
		proxyHost = properties.getProperty("proxy.host");
		proxyPort = Integer.parseInt(properties.getProperty("proxy.port", "0"));
	}
	
	public static void loadProperties() {

		InputStream is = null;
		try {
			properties = new Properties();
			is = new FileInputStream(new File("./sal.slip.properties"));
			properties.load(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean generateHTML(String templatePath, String templateName, String filePath, String fileName, HashMap<String, Object> data) {
		
		boolean success = false;
		System.out.println("Creating HTML file using the employee data...");
		//Freemarker configuration object
		Configuration cfg = new Configuration(new Version("2.3.25-incubating"));
		
		try {
			
			File tmplateFolder = new File(templatePath);
			cfg.setDirectoryForTemplateLoading(tmplateFolder);
			Template template = cfg.getTemplate(templateName);
			
			// File output
			Writer file = new FileWriter(new File(filePath + fileName));
			template.process(data, file);
			file.flush();
			file.close();
			System.out.println("Succuessfully created HTML file...");
			success = true;
		} catch (Exception e) {
			success = false;
			System.out.println("Exception occurred while generating HTML file:" + e);
			e.printStackTrace();
		}
		
		return success;
	}
	
	public static void sendEmail(String from, String to, String filePath, String fileName, byte[] data, String mimeType) {
		
		System.out.println("Sending email from " + from + " to " + to);
		// Assuming you are sending email from localhost

		// Get system properties
		Properties emailProperties = System.getProperties();

		// Setup mail server
		emailProperties.setProperty("mail.smtp.host", smtpHost);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(emailProperties);

		try {
			MimeBodyPart textBodyPart = new MimeBodyPart();
			textBodyPart.setText("Please find attached the payslip for the month of " + salaryMonth + " " + salaryYear);
			
			BodyPart pdfBodyPart = new MimeBodyPart();
			
			DataSource dataSource = null;
			if (data != null) {
				dataSource = new ByteArrayDataSource(data, mimeType);
			} else {
				InputStream is = new FileInputStream(new File(filePath + fileName));
				dataSource = new ByteArrayDataSource(is, mimeType);
			}
			DataHandler dataHandler = new DataHandler(dataSource);

			pdfBodyPart.setDataHandler(dataHandler);
			pdfBodyPart.setFileName(fileName);
			
			Multipart multiPart = new MimeMultipart();
			multiPart.addBodyPart(textBodyPart);
			multiPart.addBodyPart(pdfBodyPart);
			
			MimeMessage message = new MimeMessage(session);
			//message.setFrom(new InternetAddress(from));
			message.setSender(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Payslip for the month of " + salaryMonth + " " + salaryYear);
			message.setContent(multiPart);

			// Send message
			Transport.send(message);
			System.out.println("Sent email successfully....");
		} catch (Exception mex) {
			System.out.println("Exception occurred while sending email:" + mex);
			mex.printStackTrace();
		}
	}
	
	static String getValue(XSSFSheet sheet, int rowNum, int colNum) throws Exception {
		
		//System.out.println("\tFetching value for row: " + rowNum + ", col: " + colNum);
		Row row = sheet.getRow(rowNum);
		Cell cell = row.getCell(colNum);
		
		CellType cellType = cell.getCellTypeEnum();
		String value = "";
		switch (cellType) {
			case STRING:
				value = cell.getStringCellValue();
				break;
			case NUMERIC:
				value = Double.toString(cell.getNumericCellValue());
				break;
			case FORMULA:
				//System.out.println("Formula type cell.. not handling now!!");
				if ((rowNum == ROW_EMP_DOJ && colNum == COL_EMP_DOJ) || (rowNum == ROW_EMP_DOB && colNum == COL_EMP_DOB)) {
					double d = cell.getNumericCellValue();
					Date date = DateUtil.getJavaDate(d);
					value = new SimpleDateFormat("dd-MMM-yyyy").format(date);
				} else {
					double d = -1;
					try {
						d = cell.getNumericCellValue();
						BigDecimal bd = BigDecimal.valueOf(d);
						value = bd.setScale(0, RoundingMode.HALF_UP).toString();
					} catch(Exception ee) {
						//ee.printStackTrace();
						try {
							value = cell.getStringCellValue();
						} catch (Exception eee) {
							System.out.println("##### ERROR FETCHING VALUE FROM ROW: " + rowNum + ", COL: " + colNum + " #####");
							throw eee;
						}
					}
				}
				/*} else if ((rowNum == ROW_EMP_EPF && colNum == COL_EMP_EPF) || (rowNum == ROW_TOT_DAYS && colNum == COL_TOT_DAYS) || 
						(rowNum == ROW_LOP && colNum == COL_LOP) || (rowNum == ROW_WORK_DAYS && colNum == COL_WORK_DAYS) || 
						(rowNum == ROW_BASIC && colNum == COL_BASIC) || (rowNum == ROW_HRA && colNum == COL_HRA) || 
						(rowNum == ROW_CONVEYANCE && colNum == COL_CONVEYANCE) || (rowNum == ROW_OTHER_ALLOWANCES && colNum == COL_OTHER_ALLOWANCES) || 
						(rowNum == ROW_LEAVE_ENCASHMENT && colNum == COL_LEAVE_ENCASHMENT) || (rowNum == ROW_BONUS_QTRLY && colNum == COL_BONUS_QTRLY) || 
						(rowNum == ROW_TOTAL_EARNINGS && colNum == COL_TOTAL_EARNINGS) || (rowNum == ROW_TAKE_HOME && colNum == COL_TAKE_HOME) || 
						(rowNum == ROW_EPF_EMPLOYEE && colNum == COL_EPF_EMPLOYEE) || (rowNum == ROW_ESIC_EMPLOYEE && colNum == COL_ESIC_EMPLOYEE) || 
						(rowNum == ROW_PROF_TAX && colNum == COL_PROF_TAX) || (rowNum == ROW_SAL_ADVANCE && colNum == COL_SAL_ADVANCE) || 
						(rowNum == ROW_INCOME_TAX && colNum == COL_INCOME_TAX) || (rowNum == ROW_LABOUR_FUND && colNum == COL_LABOUR_FUND) || 
						(rowNum == ROW_TOTAL_DEDUCTION && colNum == COL_TOTAL_DEDUCTION) //|| (rowNum == ROW_EMP_PAN && colNum == COL_EMP_PAN) || 
						//(rowNum == ROW_EMP_ESI && colNum == COL_EMP_ESI)) {
					double d = cell.getNumericCellValue();
					BigDecimal bd = BigDecimal.valueOf(d);
					value = bd.setScale(0, RoundingMode.HALF_UP).toString();
				} else {
					value = cell.getStringCellValue();
				}*/
				break;
			case BOOLEAN:
				value = Boolean.toString(cell.getBooleanCellValue());
				break;
			default :
		}
		
		return value;
	}
	
	public static HashMap<String, Object> getEmployeeData(String dataFilePath, String empId)
	{
		System.out.println("Fetching data for employee: " + empId);
		HashMap<String, Object> data = new HashMap<String, Object>();
		try {
			if (myWorkBook == null) {
				File myFile = new File(dataFilePath);
				FileInputStream fis = new FileInputStream(myFile);
				
				// Finds the workbook instance for XLSX file
				myWorkBook = new XSSFWorkbook (fis);
			}
			
			// Return first sheet from the XLSX workbook
			XSSFSheet mySheet = myWorkBook.getSheet(payslipSheetName);
			
			Row empIdRow = mySheet.getRow(10);
			Cell empIdCell = empIdRow.getCell(3);
			
			empIdCell.setCellValue(empId);
			
			XSSFFormulaEvaluator.evaluateAllFormulaCells(myWorkBook);
			
			//String tmpEmpId = getValue(mySheet, ROW_EMP_ID, COL_EMP_ID);
			String empName = getValue(mySheet, ROW_EMP_NAME, COL_EMP_NAME);
			String designation = getValue(mySheet, ROW_EMP_DES, COL_EMP_DES);
			String bankName = getValue(mySheet, ROW_BANK_NAME, COL_BANK_NAME);
			String accountNumber = getValue(mySheet, ROW_ACCT_NUM, COL_ACCT_NUM);
			String doj = getValue(mySheet, ROW_EMP_DOJ, COL_EMP_DOJ);
			String dob = getValue(mySheet, ROW_EMP_DOB, COL_EMP_DOB);
			String panNumber = getValue(mySheet, ROW_EMP_PAN, COL_EMP_PAN);
			
			String location = getValue(mySheet, ROW_LOC, COL_LOC);
			String esiNumber = getValue(mySheet, ROW_EMP_ESI, COL_EMP_ESI);
			String epfNumber = getValue(mySheet, ROW_EMP_EPF, COL_EMP_EPF);
			String uan = getValue(mySheet, ROW_EMP_UAN, COL_EMP_UAN);
			String empty = "";
			String leaves = "";
			String totalDays = getValue(mySheet, ROW_TOT_DAYS, COL_TOT_DAYS);
			String lop = getValue(mySheet, ROW_LOP, COL_LOP);
			String workDays = getValue(mySheet, ROW_WORK_DAYS, COL_WORK_DAYS);
			
			/*System.out.println(empId);
			System.out.println(empName);
			System.out.println(designation);
			System.out.println(bankName);
			System.out.println(accountNumber);
			System.out.println(doj);
			System.out.println(dob);
			System.out.println(panNumber);
			
			System.out.println();
			
			System.out.println(location);
			System.out.println(esiNumber);
			System.out.println(epfNumber);
			System.out.println(uan);
			System.out.println(empty);
			System.out.println(totalDays);
			System.out.println(lop);
			System.out.println(workDays);*/
			
			String basic = getValue(mySheet, ROW_BASIC, COL_BASIC);
			String hra = getValue(mySheet, ROW_HRA, COL_HRA);
			String conveyance = getValue(mySheet, ROW_CONVEYANCE, COL_CONVEYANCE);
			String otherAllowances = getValue(mySheet, ROW_OTHER_ALLOWANCES, COL_OTHER_ALLOWANCES);
			String leaveEncashment = getValue(mySheet, ROW_LEAVE_ENCASHMENT, COL_LEAVE_ENCASHMENT);
			String bonusQuarterly = getValue(mySheet, ROW_BONUS_QTRLY, COL_BONUS_QTRLY);
			String totalEarnings = getValue(mySheet, ROW_TOTAL_EARNINGS, COL_TOTAL_EARNINGS);
			String takeHome = getValue(mySheet, ROW_TAKE_HOME, COL_TAKE_HOME);

			String epfEmployee = getValue(mySheet, ROW_EPF_EMPLOYEE, COL_EPF_EMPLOYEE);
			String esicEmployee = getValue(mySheet, ROW_ESIC_EMPLOYEE, COL_ESIC_EMPLOYEE);
			String professionalTax = getValue(mySheet, ROW_PROF_TAX, COL_PROF_TAX);
			String salaryAdvance = getValue(mySheet, ROW_SAL_ADVANCE, COL_SAL_ADVANCE);
			String incomeTax = getValue(mySheet, ROW_INCOME_TAX, COL_INCOME_TAX);
			String labourFund = getValue(mySheet, ROW_LABOUR_FUND, COL_LABOUR_FUND);
			String totalDeduction = getValue(mySheet, ROW_TOTAL_DEDUCTION, COL_TOTAL_DEDUCTION);

			//=============================================
			
			data.put("empId", empId);
			data.put("empName", empName);
			data.put("designation", designation);
			data.put("bankName", bankName);
			data.put("accountNumber", accountNumber);
			data.put("doj", doj);
			data.put("dob", dob);
			data.put("panNumber", panNumber);
			
			data.put("location", location);
			data.put("esiNumber", esiNumber);
			data.put("epfNumber", epfNumber);
			data.put("uan", uan);
			data.put("empty", empty);
			data.put("leaves", leaves);
			data.put("totalDays", totalDays);
			data.put("lop", lop);
			data.put("workDays", workDays);

			data.put("basic", basic);
			data.put("hra", hra);
			data.put("conveyance", conveyance);
			data.put("otherAllowances", otherAllowances);
			data.put("leaveEncashment", leaveEncashment);
			data.put("bonusQuaterly", bonusQuarterly);
			data.put("totalEarnings", totalEarnings);
			data.put("takeHome", takeHome);

			data.put("epfEmployee", epfEmployee);
			data.put("esicEmployee", esicEmployee);
			data.put("professionalTax", professionalTax);
			data.put("salaryAdvance", salaryAdvance);
			data.put("incomeTax", incomeTax);
			data.put("labourFund", labourFund);
			data.put("totalDeduction", totalDeduction);
			data.put("salaryDate", salaryDate);

			System.out.println("Fetched data successfully...");
		} catch (Exception e) {
			System.out.println("Exception occurred while fetching data for employee: " + empId + ":" + e);
			e.printStackTrace();
			data = null;
		}
		
		return data;
	}
	
	public static byte[] convertHTMLToPDF(String htmlFilePath, String htmlFileName, String pdfFilePath, String pdfFileName, String headerString, String footerString, int marginTop,
			int marginBottom, int marginLeft, int marginRight, int headerSpacing, int footerSpacing,
			boolean isLandscape)	{
		byte[] pdfData = null;
		try
		{
			// Convert html string to a html file
			final Long currentTimeInMillis = System.currentTimeMillis();

			String headerFilePath = null;
			if (headerString != null && !headerString.trim().isEmpty())
			{
				headerFilePath = pdfTemplateDir + "/" + currentTimeInMillis + "_header.html";
				File headerFile = new File(headerFilePath);
				FileOutputStream headerFileOS = new FileOutputStream(headerFile);
				headerFileOS.write(headerString.getBytes());
				headerFileOS.close();
			}

			String footerFilePath = null;
			if (footerString != null && !footerString.trim().isEmpty())
			{
				footerFilePath = pdfTemplateDir + "/" + currentTimeInMillis + "_footer.html";
				File footerFile = new File(footerFilePath);
				FileOutputStream footerFileOS = new FileOutputStream(footerFile);
				footerFileOS.write(footerString.getBytes());
				footerFileOS.close();
			}

			final String htmlPath = htmlFilePath + htmlFileName;
			final String pdfPath = pdfFilePath + pdfFileName;
			
			
			if(wkhtmlToPdfCmdName == null || wkhtmlToPdfCmdName.isEmpty())
			{
				wkhtmlToPdfCmdName = "wkhtmltopdf";
			}
			
			StringBuilder commandSB = new StringBuilder(wkhtmlToPdfCmdName);

			if (isLandscape)
			{
				commandSB.append(" -O landscape");
			}

			if (useProxy)
			{
				commandSB.append(" --proxy " + proxyHost + ":" + proxyPort);
			}

			if (marginTop > -1)
			{
				commandSB.append(" --margin-top " + marginTop);
			}

			if (marginBottom > -1)
			{
				commandSB.append(" --margin-bottom " + marginBottom);
			}
			
			if (marginLeft > -1)
			{
				commandSB.append(" --margin-left " + marginLeft);
			}

			if (marginRight > -1)
			{
				commandSB.append(" --margin-right " + marginRight);
			}

			if (headerSpacing > -1)
			{
				commandSB.append(" --header-spacing " + headerSpacing);
			}

			if (footerSpacing > -1)
			{
				commandSB.append(" --footer-spacing " + footerSpacing);
			}

			if (headerFilePath != null)
			{
				commandSB.append(" --header-html " + headerFilePath);
			}

			if (footerFilePath != null)
			{
				commandSB.append(" --footer-html " + footerFilePath);
			}

			commandSB.append(" " + htmlPath + " " + pdfPath);

			System.out.println("Command for conversion: " + commandSB);
			System.out.println("Conversion from HTML to PDF starts...");

			Date startTime = new Date();
			final Process process = Runtime.getRuntime().exec(commandSB.toString());

			process.waitFor();
			System.out.println("Conversion from HTML to PDF ends...");
			System.out.println("Time taken for conversion:" + (new Date().getTime() - startTime.getTime()));

			Thread.sleep(500);

			for (int j = 0; j < 12000; j++)
			{
				try
				{
					final File pdfFile = new File(pdfPath);
					final long length = pdfFile.length();
					if (length > 0)
					{
						Thread.sleep(1000);
						final long newLength = pdfFile.length();

						if (length != newLength)
						{
							continue;
						}
					}
					FileInputStream pdfFis = new FileInputStream(pdfFile);
					int available = pdfFis.available();
					if (available == 0)
					{
						Thread.sleep(1000);
					}
					else
					{
						final ByteArrayOutputStream bos = new ByteArrayOutputStream();
						while (true)
						{
							pdfData = new byte[available];
							final int readCount = pdfFis.read(pdfData);
							bos.write(pdfData);

							available = pdfFis.available();
							if (available == 0)
							{
								break;
							}
						}
						pdfFis.close();
						pdfData = bos.toByteArray();
						break;
					}
				}
				catch (final Exception exception)
				{
					Thread.sleep(1000);
				}
			}
		}
		catch (final IOException ioException)
		{
			ioException.printStackTrace();
		}
		catch (final InterruptedException interruptedException)
		{
			interruptedException.printStackTrace();
		}
		finally
		{

		}

		if (pdfData != null)
		{
			System.out.println("Size of the pdf (in bytes): " + pdfData.length);
		}
		return pdfData;
	}
	
	public static void main(String[] args) throws IOException {
		
		if (args.length == 0) {
			System.out.println("Invalid arguments. Please refer readme.txt for details.");
			System.out.println("Example: java -jar SalarySlipGenerator.jar apptivo individual AIDC_9999 syedavalli@apptivo.co.in");
			return;
		}
		
		String company = args[0];
		System.out.println("Company:" + company);
		String command = args[1];
		System.out.println("Command:" + command);
		dataFilePath = properties.getProperty(company.toLowerCase() + ".data.file.path");
		System.out.println("Data file path:" + dataFilePath);
		
		try {
			File myFile = new File(dataFilePath);
			FileInputStream fis = new FileInputStream(myFile);
			
			// Finds the workbook instance for XLSX file
			myWorkBook = new XSSFWorkbook (fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if ("all".equals(command)) {
			sendAllEmails(company);
		} else if ("individual".equals(command)) {
			String empId = args[2];
			String email = null;
			if (args.length > 3) {
				email = args[3].trim();
			}
			sendIndividualEmail(company, empId, email);
		}
		
		myWorkBook.close();
	}
	
	private static byte[] generatePdf(String company, String empId) {
		
		HashMap<String, Object> empData = getEmployeeData(dataFilePath, empId);
		
		if (empData == null) {
			System.out.println("##### ERROR FETCHING EMPLOYEE DETAILS. #####");
			return null;
		}
		
		empData.put("imagePath", headerImagePath);
		empData.put("imageName", company.toLowerCase());
		empData.put("companyName", company);
		empData.put("salaryMonth", salaryMonth.toUpperCase());
		empData.put("salaryYear", salaryYear);
		
		String htmlFileName = empId + ".html";
		String pdfFileName = empId + ".pdf";
		
		boolean isSuccess = generateHTML(templatePath, templateName, htmlFilePath, htmlFileName, empData);
		if (!isSuccess) {
			System.out.println("##### ERROR GENERATING PAYSLIP HTML. NOT GENERATING PDF #####");
			return null;
		}
		
		return convertHTMLToPDF(htmlFilePath, htmlFileName, pdfFilePath, pdfFileName, null, null, -1, -1, -1, -1, -1, -1, false);
	}
	
	private static void sendIndividualEmail(String company, String empId, String email) {
		System.out.println("Send salary slip to an individual: " + empId);
		loadEmpIds();
		
		System.out.println("\n\n--------------------------------------------------------------------------------------");
		System.out.println(" Employee Id : " + empId);
		System.out.println("--------------------------------------------------------------------------------------");
		byte[] pdfData = generatePdf(company, empId);
		
		if (pdfData != null) {
			String pdfFileName = empId + ".pdf";
			if (email == null) {
				email = empIdEmailIdMap.get(empId);
			}
			sendEmail(fromEmail, email, pdfFilePath, pdfFileName, pdfData, "application/pdf");
		} else {
			System.out.println("##### ERROR generating PDF. Not sending email. #####");
		}
		System.out.println("--------------------------------------------------------------------------------------");
	}
	
	private static void sendAllEmails(String company) {
		System.out.println("Send all salary slips");
		// Read list of emp ids
		loadEmpIds();
		
		// Generate all PDFs
		byte[] pdfData = null;
		for (final String empId : empIds) {
			System.out.println("\n\n--------------------------------------------------------------------------------------");
			System.out.println(" Employee Id : " + empId);
			System.out.println("--------------------------------------------------------------------------------------");
			pdfData = generatePdf(company, empId);
			
			if (pdfData != null) {
				String emailId = empIdEmailIdMap.get(empId);
				System.out.println("Email id of the emplooyee: " + emailId);
				String pdfFileName = empId + ".pdf";
				sendEmail(fromEmail, emailId, pdfFilePath, pdfFileName, pdfData, "application/pdf");
			} else {
				System.out.println("##### ERROR generating PDF. Not sending email. #####");
			}
			System.out.println("--------------------------------------------------------------------------------------");
		}
	}
	
	private static List<String> empIds = new ArrayList<>();
	private static HashMap<String, String> empIdEmailIdMap = new HashMap<>();
	private static void loadEmpIds() {
		try {
			//File myFile = new File("/Users/satya/Desktop/work/salary_slips/data/APPTIVO.xlsx");
			File myFile = new File(dataFilePath);
			FileInputStream fis = new FileInputStream(myFile);
			
			// Finds the workbook instance for XLSX file
			XSSFWorkbook myWorkBook = new XSSFWorkbook (fis);
			
			// Return first sheet from the XLSX workbook
			XSSFSheet mySheet = myWorkBook.getSheet("Employee Master");
			
			int firstRow = 4;
			int lastRow = mySheet.getLastRowNum();
			
			Row row = null;
			Cell cell = null;
			for (int i = firstRow; i < lastRow; i++) {
				row = mySheet.getRow(i);
				if (row != null) {
					cell = row.getCell(1);
					if (cell != null) {
						String empId = cell.getStringCellValue();
						if (empId != null && !empId.trim().isEmpty()) {
							empIds.add(empId);
							cell = row.getCell(EMP_EMAIL_COL);
							if (cell != null) {
								String emailId = cell.getStringCellValue();
								//System.out.println("EMail id : " + emailId);
								if (emailId != null && !emailId.trim().isEmpty()) {
									empIdEmailIdMap.put(empId, emailId);
								} else {
									empIdEmailIdMap.put(empId, toEmail);
								}
							}
						}
					}
				}
			}
			
			System.out.println("### Employee ids and their email ids");
			for (Map.Entry<String, String> entry : empIdEmailIdMap.entrySet()) {
				System.out.println(entry.getKey() + " : " + entry.getValue());
			}
			
			//System.out.println("Total empIds: " + empIds.size());
			//System.out.println(empIds);
			
			//Row empIdRow = mySheet.getRow(10);
			//Cell empIdCell = empIdRow.getCell(3);
			
			myWorkBook.close();
			//System.out.println("Fetched data successfully...");
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace();
		}
	}
	
	public static void main1(String[] args) throws IOException {
		
		if (args.length == 0) {
			System.out.println("Invalid arguments. Please refer readme.txt for details.");
			System.out.println("Example: java -jar SalarySlipGenerator.jar apptivo individual AIDC_9999 syedavalli@apptivo.co.in");
			return;
		}
		
		String company = args[0];
		System.out.println("Company:" + company);
		dataFilePath = properties.getProperty(company.toLowerCase() + ".data.file.path");
		loadEmpIds();
	}
}
