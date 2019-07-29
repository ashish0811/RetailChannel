<%@page import="com.onepay.retail.api.DataManager"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   <%--  <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ page import="com.hdfc.dao.GetReportDao" %> --%>
    <%@ page import="java.util.List" %>
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Customer Report</title>

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

<form action="/NETCREPORTS/customer.jsp"  method="post">	
				 				 		
						<div class="col-md-12">						
						<div class="col-md-3">
							<div class="form-group">
								<label class="control-label">
									From Date<span class="symbol required"></span><font color="red"> <span id="from_datespan"></span></font>
								</label>
								
								<div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" >
									<input class="form-control" onkeypress="return Date1(event);" maxlength="10" name="from_date" id="from_date" type="text" onkeyup="javascript:valueChange('from_datespan');"  placeholder="yyyy-mm-dd" readonly="readonly">
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
								</div>
							</div>
						</div>
										
										
						<div class="col-md-3">
							<div class="form-group">
								<label class="control-label">
									To Date<span class="symbol required"></span><font color="red"><span id="to_datespan"></span></font>
								</label>
								<div class="input-group date form_date" data-date="" data-date-format="yyyy-mm-dd" >
									<input class="form-control" name="to_date" onkeypress="return Date1(event);" maxlength="10" id="to_date" type="text" onkeyup="javascript:valueChange('to_datespan');" placeholder="yyyy-mm-dd" readonly="readonly">
									<span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
								</div>
							</div>
							
						</div>
						<input type="button" value="Submit" onclick="javascript:return validateDate();" style=" margin-top: 27px;  margin-left: 38px;"/>
						
						</div>				
					</form>
					
					


 <%
		String to_date = request.getParameter("to_date");	
		String from_date = request.getParameter("from_date"); 
		System.out.println("to_date  ::  "+to_date);
		System.out.println("from_date  ::  "+from_date);


 
       // GetReportDao obj = new GetReportDao();
        List<List<String>> result = new DataManager().dispAdminReports(to_date,from_date);

		if (result != null && result.size() >=1) {
			List<String> headerNames = result.get(0);
			System.out.println(result.get(0));
			request.setAttribute("headerNames", headerNames);			
			result.remove(0);
		}
		/*	for(int i = 0; i< result.size() ; i++)
			{
				System.out.println(" values : " +result.get(i));
			}
		*/
		System.out.println(" Result values : " +result);
		request.setAttribute("resultData", result);
	 %> 
	 
 <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                
                  <div class="x_content">
                  
                   <logic:present name="resultData"> 
                   
                    <table id="datatable-buttons" class="table table-striped table-bordered">
                    
                      <thead>
	
					<tr style="background-color:  white;">
					
						<c:forEach items="${headerNames}" var="name">
							<th style="width: 145px;text-align: center; padding: 13px;" align="center">${name}</th>
						</c:forEach>
						
					</tr>
			
					</thead>


                     <tbody>
				    <c:forEach items="${resultData}" var="values" varStatus="count">
				    
						<tr style="background-color:  white;">
					
                            <c:forEach items="${values}" var="value">
						       <td>${value}</td>    <!-- style="width: 145px;text-al1ign: center; padding: 13px;" -->
						   	</c:forEach>
                         
                        </tr>
					</c:forEach>
				</tbody>
                    </table>
                    
                    </logic:present>  
                  </div>
                </div>
              </div>



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