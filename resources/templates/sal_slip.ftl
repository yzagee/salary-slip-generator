<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Salary Slip</title>
</head>
<body style="font-family: arial;font-size: 13px;">

	<div id="iner-body" style="float: none;margin: 0 auto;width: 850px;">

		<div class="container" style="width: 850px;">

        	<div id="header">
		<div class="logo text-center" style="text-align:center;">
			<img src="${imagePath!'images'}/${imageName}.png" style="width: 170px;">
			<h3 style="text-align: center; margin-top: 0px;">PAYSLIP FOR MONTH OF ${salaryMonth!} ${salaryYear!}</h3>
		</div>
	</div>

			<div class="row" style="xborder: 1px solid rgba(61, 57, 57, 0.46); padding:30px 10px 30px 10px; float: left;width:850px;">
				<div class="col-sm-12" style="float: left;width:100%;">
					<div class="col-sm-6" style="padding:0px; float: left;width:50%;">
						<table id="myTable" style="border: 1px solid #ccc;width:100%;border-right: 0px;border-collapse: collapse;">
							<tr class="header" style="border: 1px solid #ccc;border-right:0px;">
								<td style="font-weight: 500; color: #4e4949;padding: 8px">Employee ID</td>
								<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${empId!}</td>
							</tr>
								<tr style="border: 1px solid #ccc;border-right: 0px;">
								<td style="font-weight: 500;color: #4e4949;padding: 8px">Name</td>
								<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${empName!}</td>
							</tr>
							<tr style="border: 1px solid #ccc;border-right: 0px;">
								<td style="font-weight: 500;color: #4e4949;padding: 8px">Bank Name</td>
								<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${bankName!}</td>
							</tr>
								<tr style="border: 1px solid #ccc;border-right: 0px;">
								<td style="font-weight: 500; color: #4e4949;padding: 8px">Account.No</td>
								<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${accountNumber!}</td>
							</tr>
								<tr style="border: 1px solid #ccc;border-right: 0px;">
								<td style="font-weight: 500;color: #4e4949;padding: 8px">Designation</td>
								<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${designation!}</td>
							</tr>
							<tr style="border: 1px solid #ccc;border-right: 0px;">
								<td style="font-weight: 500; color: #4e4949;padding: 8px">Date of Joining</td>
								<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${doj!}</td>
							</tr>
								<tr style="border: 1px solid #ccc;border-right: 0px;">
								<td style="font-weight: 500; color: #4e4949;padding: 8px">Date of Birth</td>
								<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${dob!}</td>
							</tr>
							<tr style="border: 1px solid #ccc;border-right: 0px;">
								<td style="font-weight: 500; color: #4e4949;padding: 8px">PAN No</td>
								<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${panNumber!}</td>
							</tr>
						</table>
					</div>
					<div class="col-sm-6" style="padding:0px; float: left;width:50%;">
						<table id="myTable" style="width:100%;border: 1px solid #ccc;border-collapse: collapse;">
							<tr class="header">
								<td style="font-weight: 500;color: #4e4949;padding: 8px">Location</td>
								<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${location!}</td>
							</tr>
							<tr style="border: 1px solid #ccc;border-right: 0px;">
								<td style="font-weight: 500; color: #4e4949;padding: 8px">ESIC No/United India Insurance</td>
								<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${esiNumber!}</td>
							</tr>
								<tr style="border: 1px solid #ccc;border-right: 0px;">
								<td style="font-weight: 500;color: #4e4949;padding: 8px">EPF No</td>
								<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${epfNumber!}</td>
							</tr>
								<tr style="border: 1px solid #ccc;border-right: 0px;">
								<td style="font-weight: 500;color: #4e4949;padding: 8px">EPF Universal Account No(UAN)</td>
								<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${uan!}</td>
							</tr>
							<tr style="border: 1px solid #ccc;border-right: 0px;">
								<td style="font-weight: 500; color: #4e4949;padding: 8px">Leave taken up to ${month!}</td>
								<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${leaves!}</td>
							</tr>
								<tr style="border: 1px solid #ccc;border-right: 0px;">
								<td style="font-weight: 500;color: #4e4949;padding: 8px">Total Days</td>
								<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${totalDays!0}</td>
							</tr>
							<tr style="border: 1px solid #ccc;border-right: 0px;">
								<td style="font-weight: 500;color: #4e4949;padding: 8px">L0P</td>
								<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${lop!0}</td>
							</tr>
								<tr style="border: 1px solid #ccc;border-right: 0px;">
								<td style="font-weight: 500;color: #4e4949;padding: 8px">Work Days</td>
								<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${workDays!0}</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
			<div class="clerfix"></div>
			<!--end-->
			<div id="item" style="margin-top:30px; float: left;width: 850px;">
				<div class="row" style="xborder: 1px solid rgba(61, 57, 57, 0.46); padding:30px 10px 30px 10px; float: left;width: 850px;">
					<div class="col-sm-12" style="float: left; width:100%;">
						<div class="col-sm-6" style="padding:0px; float: left;width:50%;">
							<table style=" border: 1px solid #ccc;border-bottom: 0px;width:100%;border-right:0px;">
								<tr class="header" style="border: 1px solid #ccc;border-right:0px;">
									<th style="font-weight: 800; color: #4e4949; text-align:center;padding: 8px;">EARNINGS</th>
								</tr>
							</table>
							<table id="myTable" style="border: 1px solid #ccc; width:100%;border-right: 0px;border-collapse: collapse;">
								<tr class="header" style="border: 1px solid #ccc;border-right:0px;">
									<td style="font-weight:bold; color: #000; background-color:#c5d9f1;padding: 8px;border-right: 1px solid #d4d4d4;font-size: 12px;">SALARY HEAD</td>
									<td style=" background-color:#c5d9f1;padding: 8px;color:#000;font-weight:bold;font-size: 12px;">AMOUNT(Rs)</td>
								</tr>
								<tr class="header" style="border: 1px solid #ccc;border-right:0px;">
									<td style="font-weight: 500; color: #4e4949;padding: 8px">Basic</td>
									<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${basic!0}</td>
								</tr>
									<tr style="border: 1px solid #ccc;border-right: 0px;">
									<td style="font-weight: 500;color: #4e4949;padding: 8px">HRA</td>
									<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${hra!0}</td>
								</tr>
								<tr style="border: 1px solid #ccc;border-right: 0px;">
									<td style="font-weight: 500;color: #4e4949;padding: 8px">Conveyance</td>
									<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${conveyance!0}</td>
								</tr>
									<tr style="border: 1px solid #ccc;border-right: 0px;">
									<td style="font-weight: 500; color: #4e4949;padding: 8px">Other </td>
									<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${otherAllowances!0}</td>
								</tr>
									<tr style="border: 1px solid #ccc;border-right: 0px;">
									<td style="font-weight: 500;color: #4e4949;padding: 8px">Leave Enhancement</td>
									<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${leaveEncashment!0}</td>
								</tr>
									<tr style="border: 1px solid #ccc;border-right: 0px;">
									<td style="font-weight: 500; color: #4e4949;padding: 8px">Performance Bonus</td>
									<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${bonusQuaterly!0}</td>
								</tr>
								<tr style="border: 1px solid #ccc;border-right: 0px;">
									<td style="font-weight: 500; color: #4e4949;padding: 8px">Total Earnings</td>
									<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${totalEarnings!0}</td>
								</tr>
									<tr style="border: 1px solid #ccc;border-right: 0px;">
									<td style="font-weight: 500; color: #4e4949;padding: 8px">Take Home Pay</td>
									<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${takeHome!0}</td>
								</tr>
							</table>
						</div>
						<div class="col-sm-6" style="padding:0px; float: left;width:50%;">
							<table style=" border: 1px solid #ccc;border-bottom: 0px;width:100%;">
								<tr class="header" style="border: 1px solid #ccc;">
									<th style="font-weight: 800; color: #4e4949; text-align:center;padding: 8px;">DEDUCTIONS</th>
								</tr>
							</table>
							<table id="myTable" style="border: 1px solid #ccc; width:100%;border-collapse: collapse;">
								<tr class="header" style="border: 1px solid #ccc;border-right:0px;">
									<td style="font-weight:bold; color: #000; background-color:#c5d9f1;padding: 8px;border-right: 1px solid #d4d4d4;font-size: 12px;">SALARY HEAD</td>
									<td style=" background-color:#c5d9f1;padding: 8px; color:#000;font-weight:bold;font-size: 12px;">AMOUNT(Rs)</td>
								</tr>
								<tr class="header">
									<td style="font-weight: 500;color: #4e4949;padding: 8px">PF Employee</td>
									<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${epfEmployee!0}</td>
								</tr>
									<tr style="border: 1px solid #ccc;border-right: 0px;">
									<td style="font-weight: 500; color: #4e4949;padding: 8px">ESI Employee</td>
									<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${esicEmployee!0}</td>
								</tr>
									<tr style="border: 1px solid #ccc;border-right: 0px;">
									<td style="font-weight: 500;color: #4e4949;padding: 8px">Professional tax</td>
									<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${professionalTax!0}</td>
								</tr>
								<tr>
									<td style="font-weight: 500;color: #4e4949;padding: 8px">Salary adv</td>
									<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${salaryAdvance!0}</td>
								</tr>
									<tr style="border: 1px solid #ccc;border-right: 0px;">
									<td style="font-weight: 500; color: #4e4949;padding: 8px">Income tax</td>
									<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${incomeTax!0}</td>
								</tr>
								<tr style="border: 1px solid #ccc;border-right: 0px;">
									<td style="font-weight: 500;color: #4e4949;padding: 8px">-</td>
									<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${labourFund!0}</td>
								</tr>
									<tr style="border: 1px solid #ccc;border-right: 0px;">
									<td style="font-weight: 500;color: #4e4949;padding: 8px">Total Deduction</td>
									<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${totalDeduction!0}</td>
								</tr>
								<tr style="border: 1px solid #ccc;border-right: 0px;">
									<td style="font-weight: 500;color: #4e4949;padding: 8px">Date</td>
									<td style="background-color: rgba(90, 94, 94, 0.09);padding: 8px;font-weight: bold;">${salaryDate!}</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
			<div class="clerfix"></div>
			<div class="row" style="padding:0px; float: left;width:860px;">
				<div calss="col-sm-12" style="width:100%;">
					<div class="footer-irem" style="margin-top:40px; margin-bottom:30px;">
						<h4>The Computer generated document, is not valid unless authorized</h4>
						<h5 style="margin-top:40px; text-transform: capitalize; font-weight:600">Authorization</h5>
					</div>
					<div class="clerfix"></div>
					<div class="register" style="text-align:center;     background-color: #f6f6f6; padding: 15px 10px 10px 10px; margin-top: 30px;margin-bottom: 30px;">
						<h6 style="line-height:18px; text-transform: capitalize;font-size: 13px;">
							Registered Office:<br>
							${companyName} Software Private Limited,<br>
							#1,Anthu street,Santhome High Road,Chennai-600 004
						</h6>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="../AppData/Roaming/Skype/My Skype Received Files/js/jquery.min.js"></script><!-- jQuery -->
	<script type="text/javascript" src="../AppData/Roaming/Skype/My Skype Received Files/js/bootstrap.min.js"></script><!-- Bootstrap -->
	</body>
</html>
