<%@page import="com.onepay.retail.api.DataManager"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%-- <%@ page import="com.hdfc.dao.GetReportDao" %> --%>
    <%@ page import="java.util.List" %>
 
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Dashboard</title>

<!-- VVS -->
<!-- Bootstrap -->
    <link href="reportjs/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link href="reportjs/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <!-- NProgress -->
    <link href="reportjs/nprogress/nprogress.css" rel="stylesheet">
    <!-- iCheck -->
    <link href="reportjs/iCheck/skins/flat/green.css" rel="stylesheet">
    <!-- Datatables -->
    <link href="reportjs/datatables.net-bs/css/dataTables.bootstrap.min.css" rel="stylesheet">
    <link href="reportjs/datatables.net-buttons-bs/css/buttons.bootstrap.min.css" rel="stylesheet">
    <link href="reportjs/datatables.net-fixedheader-bs/css/fixedHeader.bootstrap.min.css" rel="stylesheet">
    <link href="reportjs/datatables.net-responsive-bs/css/responsive.bootstrap.min.css" rel="stylesheet">
    <link href="reportjs/datatables.net-scroller-bs/css/scroller.bootstrap.min.css" rel="stylesheet">
	<link href="reportjs/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
	<link href="reportjs/bootstrap/dist/css/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom Theme Style -->
    <link href="reportjs/build/css/custom.min.css" rel="stylesheet">


<!--  EnD VVS -->

</head>
<body>


<br><br>
<form action="index.jsp"  method="post">	
				 				 		
						
							
							<a href="updateBalance.jsp">Update Balance</a><br>
							<a href="updateWalletInfo.jsp">Update Wallet List</a><br>
							<a href="index.jsp">View Transaction Report</a><br>
							<a href="adminLedgerReport.jsp">View Ledger Report</a><br>
							<button onclick="location.href='logout.jsp'" type="button" style="color: blue;margin-left: 1224px;">Logout </button>
							
							
						
			
					</form>
					
				

 
	
	
	
 

    <!-- jQuery -->
    <script data-cfasync="false" src="/cdn-cgi/scripts/f2bf09f8/cloudflare-static/email-decode.min.js"></script><script src="reportjs/jquery/dist/jquery.min.js"></script>
    <!-- Bootstrap -->
    <script src="reportjs/bootstrap/dist/js/bootstrap.min.js"></script>
    <!-- FastClick -->
    <script src="reportjs/fastclick/lib/fastclick.js"></script>
    <!-- NProgress -->
    <script src="reportjs/nprogress/nprogress.js"></script>
    <!-- iCheck -->
    <script src="reportjs/iCheck/icheck.min.js"></script>
    <!-- Datatables -->
    <script src="reportjs/datatables.net/js/jquery.dataTables.min.js"></script>
    <script src="reportjs/datatables.net-bs/js/dataTables.bootstrap.min.js"></script>
    <script src="reportjs/datatables.net-buttons/js/dataTables.buttons.min.js"></script>
    <script src="reportjs/datatables.net-buttons-bs/js/buttons.bootstrap.min.js"></script>
    <script src="reportjs/datatables.net-buttons/js/buttons.flash.min.js"></script>
    <script src="reportjs/datatables.net-buttons/js/buttons.html5.min.js"></script>
    <script src="reportjs/datatables.net-buttons/js/buttons.print.min.js"></script>
    <script src="reportjs/datatables.net-fixedheader/js/dataTables.fixedHeader.min.js"></script>
    <script src="reportjs/datatables.net-keytable/js/dataTables.keyTable.min.js"></script>
    <script src="reportjs/datatables.net-responsive/js/dataTables.responsive.min.js"></script>
    <script src="reportjs/datatables.net-responsive-bs/js/responsive.bootstrap.js"></script>
    <script src="reportjs/datatables.net-scroller/js/dataTables.scroller.min.js"></script>
    <script src="reportjs/jszip/dist/jszip.min.js"></script>
    <script src="reportjs/pdfmake/build/pdfmake.min.js"></script>
    <script src="reportjs/pdfmake/build/vfs_fonts.js"></script>

    <!-- Custom Theme Scripts -->
    <script src="reportjs/build/js/custom.min.js"></script>
    
    
    <script type="text/javascript" src="js/bootstrap-datetimepicker.js" charset="UTF-8"></script>
    
    <script type="text/javascript">
		$('.form_date').datetimepicker({
       
        weekStart: 1,
        todayBtn:  1,
		autoclose: 1,
		todayHighlight: 1,
		startView: 2,
		minView: 2,
		forceParse: 0
    });
		</script>
		
		 <script>
		    jQuery(document).ready(function () {
		        Main.init();		      
		        UINotifications.init();
		    });
		    
		    
		</script> 
		<script type="text/javascript">
		
		function test(refNo) 
		{
			//alert("hello");	
			document.getElementById("txnRefNo").value=refNo;
			//$("#txnRefNo").val()=refNo;
			//alert(x);	
			$('#myModal').modal('show');
			//document.frmBarCode.submit();<a href="javascript:test('${values[0]}')" data-toggle="modal" data-target="#myModal">  Click  To Add </a>

		}
		
		
		function downloadDoc(fileName) 
		{
			
			document.getElementById("actionName").value = "DownloadKycDoc";
			document.getElementById("downloadFileName").value = fileName;
			
			// alert("hii"+document.getElementById("downloadFileName").value);
			document.downloadForm.submit();	
			
		}
		
		function validateDate()
		{
			 var from = $("#from_date").val();
			 var to = $("#to_date").val();
		 if(document.getElementById("from_date").value == "")
        	{
        		document.getElementById("from_date").focus();
        		document.getElementById("from_datespan").innerHTML = "select From Date.";
        		document.getElementById("to_datespan").innerHTML = "";
        		valueBackground("from_datespan");
        	 
        		return false;      			
        	}
        	else if(document.getElementById("to_date").value == "")
        	{
        		document.getElementById("to_date").focus();
        		document.getElementById("to_datespan").innerHTML = "select To Date.";
        		document.getElementById("from_datespan").innerHTML = "";
        		valueBackground("to_datespan");
        	 
        		return false;      			
        	}
		 
		

		 else if(Date.parse(from) > Date.parse(to)){
			 document.getElementById("from_date").focus();
     		document.getElementById("from_datespan").innerHTML = "select valid Date.";
     		document.getElementById("to_datespan").innerHTML = "";
     		valueBackground("from_datespan");
     		
     		return false;      			 
		 }
		
		 
        	else
        		{
        		// alert("Submit");
        		document.forms[0].submit();
        		 return true;
        		}
		}
		</script>

</body>
</html>