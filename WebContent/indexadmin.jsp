<!DOCTYPE html>
<%@page import="java.util.List"%>
<%-- <%@page import="com.payone.pg.db.DataManager"%> --%>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Reports</title>
 

<style>
table, th, td {
    border: 1px solid #ddd;
    width: 1316px;
    margin-left: 23px;    
}
table{
	border-collapse: collapse;
}
body{
	margin:auto;
}
header { 
	
     background-color: #4981af;
     border-color : #4981af;
     width: 100%;
     height: 84px;
}
.logo {
	position: fixed;
	left:100px;
	top:9px;
}
.Title{
    text-align: center;
    font-size: 23px;
    font-family: SANS-SERIF;
    margin-top: 22px;
}
.type{
	width: 196px;
    /* text-align: center; */
    height: 27px;
    align-items: center;
    margin-left: 65px;
}
.toDate{
	width: 196px;
    height: 21px;
    align-items: center;
    margin-left: 23px;
   /*  text-align: center; */
}
.fromDate{
	width: 196px;
    height: 21px;
    align-items: center;
    margin-left: 23px;
    /* text-align: center; */
}
.submit{
	margin-left: 26px;
    height: 27px;
    width: 93px;
    text-align: center;
    font-family: sans-serif;
}
.copyright{
	/* margin-top: 265px; */
	margin-top : 240px;
    margin-left: 553px;
}
.headerBar{
	background-color: #4981af;
    width: 69px;
    margin-left: 23px;
    height: 24px;
    margin-bottom: 39px;
    width: 1317px;
    
	
}
</style>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

 <script>
 /*  $( function() {
    $( "#toDate" ).datepicker({ format:'yyyy-mm-dd'});
    
  } ); */

  $(function(){
         $("#fromDate").datepicker({ dateFormat: 'yy-mm-dd' });
         $("#toDate").datepicker({ dateFormat: 'yy-mm-dd' }).bind("change",function(){
             var minValue = $(this).val();
             minValue = $.datepicker.parseDate("yy-mm-dd", minValue);
             minValue.setDate(minValue.getDate());
             $("#fromDate").datepicker( "option", "minDate", minValue );
         })
     });
  
  </script>
<%
 List<List<String>> abc = (List<List<String>>)request.getAttribute("reports");
%>

</head>
<body>
<header>
	<div class="logo">
		<a href="javascript:void(0);">
			<img src="images/logo.jpg" height="70px" alt="One pay logo" />
		</a>
	</div>	
</header>
<div class="Title">Transaction Reports</div>
<br/>
<br/>

<div class="headerBar">
	<a href="logout.jsp" style="color: white;margin-left: 1224px;">Logout</a>
</div>
<form method="POST" action="reports">
<select class="type" name="TXNType" placeholder="Select type">
  <option value="">Select Type</option>
  <option value="Topup">TOPUP</option>
  <option value="Register">Registration</option>
  <option value="Ledger">Ledger</option>  
</select>
 
<input id="toDate" class="toDate" type="text" name="toDate" placeholder="ToDate" required />

<input id="fromDate" class="fromDate" type="text" name="fromDate" placeholder="FromDate" required />
<input type="hidden" name="actionType" value ="reportDisp">

<input class="submit" type="submit">
<br/>	
<br/>
</form>


<% if(abc == null){ %>
<div></div>
<%}else{ %>
<table>
	<tr align = "center" style="background-color: #4981af;color: whitesmoke;">
		<td>TXN Id</td>
		<td>Retailer Id</td>
		<td>Partner Id</td>
		<td>Transaction Type</td>
		<td>Bank Id</td>
		<td>Parter Ref No</td>
		<td>Amount</td>
		<td>Mobile No</td>
		<td>Bar Code</td>
		<td>Req Datetime</td>
		<td>Txn Datetime</td>
	</tr>
	<%
		
		for(int i=0;i<abc.size();i++){ %>
		<tr align = "center">
			<td><%=abc.get(i).get(0)%></td>
			<td><%=abc.get(i).get(1)%></td>
			<td><%=abc.get(i).get(2)%></td>
			<td><%=abc.get(i).get(3)%></td>
			<td><%=abc.get(i).get(4)%></td>
			<td><%=abc.get(i).get(5)%></td>
			<td><%=abc.get(i).get(6)%></td>
			<td><%=abc.get(i).get(7)%></td>
			<td><%=abc.get(i).get(8)%></td>
			<td><%=abc.get(i).get(9)%></td>
			<td><%=abc.get(i).get(10)%></td>
		</tr>
	<%
	}
 	%>
</table>
<%} %>
<!--  
<form method="POST" action="reports">
</br>
</br>
<input type="hidden" name="actionType" value="download">
	<input type="Submit" value="DOWNLOAD" style="text-shadow:none;border:none;box-shadow:none; color: #4981af;background-color: transparent;font-weight: bold;margin-left: 64px;">
</form>-->
<script src="js/bootstrap-datetimepicker.js"></script>
<script src="js/bootstrap-datetimepicker.min.js"></script>

</body>
<div class="copyright">
	 <span>&nbsp;1Pay. All rights reserved</span>
</div>
</html>
