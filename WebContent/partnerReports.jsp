<!DOCTYPE html>
<%@page import="java.util.List"%>
<%-- <%@page import="com.payone.pg.db.DataManager"%> --%>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Partner Reports</title>
 

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

<link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>

 <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
 <link rel="stylesheet" href="/resources/demos/style.css">
 <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
 <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>


 <script>
  $(function(){
         $("#fromDate").datepicker({ dateFormat: 'yy-mm-dd' });
         $("#toDate").datepicker({ dateFormat: 'yy-mm-dd' }).bind("",function(){
             var minValue = $(this).val();
             minValue = $.datepicker.parseDate("yy-mm-dd", minValue);
             minValue.setDate(minValue.getDate());
             $("#fromDate").datepicker( "option", "minDate", minValue );
         })
     });
  
  </script>
<%
 List<List<String>> abc = (List<List<String>>)request.getAttribute("Preports");
 String userId = session.getAttribute("userId").toString();
 /* out.print(userId); */
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
	
	<h3 style="color: white;margin-right: 1000px; margin-top: -18px;">Balance:- <%=(String)session.getAttribute("balance") %></h3>
</div>


<form method="POST" action="partnerReports">

			<select class="type" name="TXNType" id="TXNType" placeholder="Select type"> <font color="red"> <span id="TXNType_span"></span></font>
			  <option value="">Select Type</option>
			  <option value="Topup">TOPUP</option>
			  <option value="Register">Registration</option>
			  <option value="Ledger">Ledger</option>  
			</select>
			<label >
				From Date:<span class="symbol required"></span><font color="red"> <span id="from_datespan"></span></font>
			</label>
			<input type="text" id="fromDate" class="fromDate" name="fromDate" placeholder="fromDate">
			<label class="control-label">
				To Date:<span class="symbol required"></span><font color="red"> <span id="to_datespan"></span></font>
			</label>
			<input type="text" id="toDate" class="toDate" name="toDate" placeholder="ToDate">
			
			
			<input type="hidden" name="actionType" value ="reportDisp"/> 
			<input type="hidden" name="custid" value="<%=userId%>"/>
 			<!-- <input class="submit" id="submitButton"  type="submit"> -->
 			<input type="button" value="Submit" onclick="javascript:return validateDate();" /> 
			<br/>	
			<br/>
</form>			

				


<% if(abc == null){ %>
<div></div>
<%}else{ 
	
	String Ledger_value = request.getAttribute("Ledger").toString();
	if(Ledger_value.equalsIgnoreCase("false"))
	{
%>
<div id="dvData">
<table>
	<tr align = "center" style="background-color: #4981af;color: whitesmoke;">
		<td>TXN Id</td>
		<td>Retailer Id</td>
		<td>Partner Id</td>
		<td>Transaction Type</td>
		<td>Bank Id</td>
		<td>Parter Reference No</td>
		<td>Amount</td>
		<td>Mobile No</td>
		<td>Bar Code</td>
		<td>Req Datetime</td>
		<td>Transaction Datetime</td>
		<td>Status</td>
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
			<td><%=abc.get(i).get(11)%></td>
		</tr>
	<%
	}
 	%>
</table>
</div>
<%} 


else
{ %>

<div id="dvData">
<table>
	<tr align = "center" style="background-color: #4981af;color: whitesmoke;">
		<td>TxnId</td>
		<td>Status</td>
		<td>RetailerId</td>
		<td>AccountId</td>
		<td>OpeningBal</td>
		<td>DebitAmount</td>
		<td>CreditAmount</td>
		<td>ClosingBalance</td>
		<td>TxnType</td>
		<td>BankId</td>
		<td>PartnerRefNo</td>
		<td>PhoneNo</td>
		<td>Remarks</td>
		<td>ReqDateTime</td>
		<td>TxnDateTime</td>
		
		
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
			<td><%=abc.get(i).get(11)%></td>
			<td><%=abc.get(i).get(12)%></td>
			<td><%=abc.get(i).get(13)%></td>
			
		</tr>
	<%
	}
 	%>
</table>
</div>
<%} // else %> 
<%} %>

<br><br>


<a href="#" class="export" style="text-shadow:none;border:none;box-shadow:none; color: #4981af;background-color: transparent;font-weight: bold;margin-left: 64px;">Download</a>
<%-- <form method="POST" action="partnerReports">
</br>
    <input type="hidden" name="actionType" value="download">
    <input type="hidden" name="custid" value="<%=userId%>"/>
	<input type="Submit" value="DOWNLOAD" style="text-shadow:none;border:none;box-shadow:none; color: #4981af;background-color: transparent;font-weight: bold;margin-left: 64px;">
</form> --%>

</body>



<div class="copyright">
	 <span>&nbsp;1Pay. All rights reserved</span>
</div>


	<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"> </script>
	<script type="text/javascript" src="js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
  	<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  	<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  	<script src="js/bootstrap-datetimepicker.js"></script>
	<script src="js/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript" src="js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
    
    
		<script type="text/javascript">
		function validateDate()
		{
			$('#submitButton').prop('disabled', true);
			 var from = $("#fromDate").val();
			 var to = $("#toDate").val();
			 
			 
		 if(document.getElementById("TXNType").selectedIndex == 0 || document.getElementById("TXNType").value == "")
        	{
        		document.getElementById("TXNType").focus();
        		document.getElementById("TXNType_span").innerHTML = "select TXNType";
        		document.getElementById("year_span").innerHTML = "";
        		document.getElementById("to_datespan").innerHTML = "";
        		valueBackground("TXNType");
        	 
        		return false;      			
        	}
			 
		 else if(document.getElementById("fromDate").value == "")
        	{
        		document.getElementById("fromDate").focus();
        		document.getElementById("from_datespan").innerHTML = "select From Date.";
        		document.getElementById("to_datespan").innerHTML = "";
        		valueBackground("from_datespan");
        	 
        		return false;      			
        	}
        	else if(document.getElementById("toDate").value == "")
        	{
        		document.getElementById("toDate").focus();
        		document.getElementById("to_datespan").innerHTML = "select To Date.";
        		document.getElementById("from_datespan").innerHTML = "";
        		valueBackground("to_datespan");
        	 
        		return false;      			
        	}
		 
		

		 else if(Date.parse(from) > Date.parse(to)){
			 document.getElementById("fromDate").focus();
     		document.getElementById("from_datespan").innerHTML = "select valid Date.";
     		document.getElementById("to_datespan").innerHTML = "";
     		valueBackground("from_datespan");
     		
     		return false;      			 
		 }
		
		 
        	else
        		{
        		// alert("Submit");
        		 $('#submitButton').prop('disabled', false);
        		document.forms[0].submit();
        		 return true;
        		}
		}
		
		</script>
		
		<script type="text/javascript">
			$(document).ready(function() {

				  function exportTableToCSV($table, filename) {

				    var $rows = $table.find('tr:has(td)'),

				      // Temporary delimiter characters unlikely to be typed by keyboard
				      // This is to avoid accidentally splitting the actual contents
				      tmpColDelim = String.fromCharCode(11), // vertical tab character
				      tmpRowDelim = String.fromCharCode(0), // null character

				      // actual delimiter characters for CSV format
				      colDelim = '","',
				      rowDelim = '"\r\n"',

				      // Grab text from table into CSV formatted string
				      csv = '"' + $rows.map(function(i, row) {
				        var $row = $(row),
				          $cols = $row.find('td');

				        return $cols.map(function(j, col) {
				          var $col = $(col),
				            text = $col.text();

				          return text.replace(/"/g, '""'); // escape double quotes

				        }).get().join(tmpColDelim);

				      }).get().join(tmpRowDelim)
				      .split(tmpRowDelim).join(rowDelim)
				      .split(tmpColDelim).join(colDelim) + '"';

				    // Deliberate 'false', see comment below
				    if (false && window.navigator.msSaveBlob) {

				      var blob = new Blob([decodeURIComponent(csv)], {
				        type: 'text/csv;charset=utf8'
				      });

				      // Crashes in IE 10, IE 11 and Microsoft Edge
				      // See MS Edge Issue #10396033
				      // Hence, the deliberate 'false'
				      // This is here just for completeness
				      // Remove the 'false' at your own risk
				      window.navigator.msSaveBlob(blob, filename);

				    } else if (window.Blob && window.URL) {
				      // HTML5 Blob        
				      var blob = new Blob([csv], {
				        type: 'text/csv;charset=utf-8'
				      });
				      var csvUrl = URL.createObjectURL(blob);

				      $(this)
				        .attr({
				          'download': filename,
				          'href': csvUrl
				        });
				    } else {
				      // Data URI
				      var csvData = 'data:application/csv;charset=utf-8,' + encodeURIComponent(csv);

				      $(this)
				        .attr({
				          'download': filename,
				          'href': csvData,
				          'target': '_blank'
				        });
				    }
				  }

				  // This must be a hyperlink
				  $(".export").on('click', function(event) {
				    // CSV
				    var args = [$('#dvData>table'), 'RatailChannelReport.csv'];

				    exportTableToCSV.apply(this, args);

				    // If CSV, don't do event.preventDefault() or return false
				    // We actually need this to be a typical hyperlink
				  });
				});
			
			
			
			</script>

</html>
