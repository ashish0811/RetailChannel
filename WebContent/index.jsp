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
<%
  	response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");
    response.setDateHeader("Expires", -1);
    response.setDateHeader("Last-Modified", 0);
    %>
 <%
System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<");

	if(request.getParameter("txnRefNo")!=null && !request.getParameter("txnRefNo").isEmpty()){
		
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>txnRefNo and barcode<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
		System.out.println(request.getParameter("txnRefNo").toString()+"    "+request.getParameter("barcode").toString());
		
		new DataManager().UpdateBarCode(request.getParameter("txnRefNo").toString(),request.getParameter("barcode").toString(),request.getParameter("status").toString(),request.getParameter("remarks").toString());
	}
%> 
   
<br><br>
<form action="index.jsp"  method="post">	
				 				 		
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
							
							<button onclick="location.href='logout.jsp'" type="button" style="color: blue;margin-left: 1224px;">Logout </button>
							
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
	 


  <%  if(to_date !=null && from_date != null)   { %>
	 
  
 <form id="downloadForm" name="downloadForm" action="DownloadKycDocs" method="post"> <!--  enctype="multipart/form-data" -->
 
 <input type="hidden" name="downloadFileName" id="downloadFileName" value="">
  <input type="hidden" name="actionName" id="actionName" value="">
 
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
					
                            <td style="width: 45px;text-align: center;" align="center">${values[0]}</td>
						    <td style="width: 45px;text-align: center;" align="center">${values[1]}</td>
						    <td style="width: 45px;text-align: center;" align="center">${values[2]}</td>
						    <td style="width: 45px;text-align: center;" align="center">${values[3]}</td>
						    <td style="width: 45px;text-align: center;" align="center">${values[4]}</td>
						    <td style="width: 45px;text-align: center;" align="center">${values[5]}</td>
						    <td style="width: 45px;text-align: center;" align="center">${values[6]}</td>
						    <td style="width: 45px;text-align: center;" align="center">${values[7]}</td>
						    <td style="width: 45px;text-align: center;" align="center">${values[8]}</td>
						    <td style="width: 45px;text-align: center;" align="center">${values[9]}</td>
						    
						    <td style="width: 45px;text-align: center;" align="center">${values[10]}</td>
						    <td style="width: 45px;text-align: center;" align="center">${values[11]}</td>
						    <td style="width: 45px;text-align: center;" align="center">${values[12]}</td>
						    <td style="width: 45px;text-align: center;" align="center">${values[13]}</td>
						    
						    <%-- <td style="width: 45px;text-align: center;" align="center">${values[14]}</td> --%>
						    
						   <%--  <td style="width: 45px;text-align: center;" align="center">${values[15]}</td>
						    <td style="width: 45px;text-align: center;" align="center">${values[16]}</td>  --%>
						    
			<td style="width: 45px;text-align: center;" align="center">
					<c:if test="${values[14] != 'NA' && not empty values[14]}">
						<a href="javascript:downloadDoc('${values[14]}')" > Download Aadhaar </a>     <%-- <c:out value="${values[14]}"></c:out> --%>
						<%-- <a href="javascript:downloadDoc('${values[14]}','${values[1]}')" ><c:out value="${values[6]}"></c:out></a> --%>
					</c:if>
					<c:if test="${values[14] == 'NA' || empty values[14]}">
						Doc Not Found
					</c:if>
				</td>
				
				<td style="width: 45px;text-align: center;" align="center">
					<c:if test="${values[15] != 'NA' && not empty values[15]}">
						<a href="javascript:downloadDoc('${values[15]}')" >  Download PAN </a>  <%-- <c:out value="${values[15]}"></c:out> --%>
						<%-- <a href="javascript:downloadDoc('${values[14]}','${values[1]}')" ><c:out value="${values[6]}"></c:out></a> --%>
					</c:if>
					<c:if test="${values[15] == 'NA' || empty values[15]}">
						Doc Not Found
					</c:if>
				</td>
				
				<td style="width: 45px;text-align: center;" align="center">
					<c:if test="${values[16] != 'NA' && not empty values[16]}">
						<a href="javascript:downloadDoc('${values[16]}')" > Download RC </a>  <%-- <c:out value="${values[16]}"></c:out> --%>
						<%-- <a href="javascript:downloadDoc('${values[14]}','${values[1]}')" ><c:out value="${values[6]}"></c:out></a> --%>
					</c:if>
					<c:if test="${values[16] == 'NA' || empty values[16]}">
						Doc Not Found
					</c:if>
				</td>
						    
						    <td>
							<c:if test="${values[17] == 'NA' || empty values[17]}">
							  <a href="javascript:test('${values[0]}','${values[18]}')" >  Click  To Add </a>
							</c:if>
						 
							 <c:if test="${values[17] != 'NA' && not empty values[17]}">
							 ${values[17]}
							 </c:if>   
						</td>
						<td style="width: 45px;text-align: center;" align="center">${values[18]}</td>
						<td style="width: 45px;text-align: center;" align="center">${values[19]}</td>
						    
                     
                        </tr>
					</c:forEach>
				</tbody>
                    </table>
                    
                    </logic:present>  
                  </div>
                </div>
                
              </div>
              
             
	</form>
	
	<form name="frmBarCode" method="post" id="frmBarCode" action="index.jsp"> 
	              <div class="modal fade" id="myModal" role="dialog">
	    	<div class="modal-dialog">
	<!--  <input type="hidden" name="ref_id" id="ref_id" value="">
	 --> <!-- <input type="hidden" name="actionName" id="actionName" value=""> --> 
	    	<input type="hidden" name="txnRefNo" id="txnRefNo" value="">
	    	<input type="hidden" name="txnType" id="txnType" value="">
	      <!-- Modal content-->
	      <div class="modal-content">
	      
	      
	        <div class="modal-header">
	          <h4 class="modal-title">Update Status</h4>
	        </div>
	        
	        <div class="modal-body">
	          Select Status <select name="status" id="status">
	          <option value="Success">Success</option>
	           <option value="Refund">Refund</option>
	          </select>
	          
	        </div>
	        
	        <div class="modal-body">
	         Enter Remarks<input type="text" name="remarks" id="remarks"></input>
	          
	        </div>
	        <div class="modal-body">
	         Enter Barcode/Receipt No. <input type="text" name="barcode" id="barcode"></input>
	          
	        </div>
	        <div class="modal-footer">
	          <input type="submit" class="btn btn-default" name="barcodeSubmit" >
	        </div>
	      </div>
	      
	    </div>
	  </div>
  </form>
	
  <%} %>


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
		
		function test(refNo,txnTypeval) 
		{
			//alert("hello");	
			document.getElementById("txnRefNo").value=refNo;
			document.getElementById("txnType").value=txnTypeval;
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