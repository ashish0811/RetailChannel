 <%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Random"%>
<%@page import="com.onepay.retail.api.DataManager"%>
<%@page import="com.onepay.retail.api.DBConnection"%>
<%@page import="org.json.JSONObject"%>
<%@page import="java.sql.*"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page import="java.util.List" %>
 
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

  <script src="js/jquery-1.12.4.js"></script>
 <script src="js/jquery.dataTables.min.js"></script>

 <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"> </script>
 
<script>
  $(document).ready(function() {
    $('#example').DataTable( {
        "pagingType": "full_numbers"
    } );
} ); 
 

 /*  jQuery.ajax({
      url: "/ReIssueTag?vehicleNo="#vehicleNo,
      type: "POST",
      data: {vehicle: "amit"},
      dataType: "json",
      beforeSend: function(x) {
        if (x && x.overrideMimeType) {
          x.overrideMimeType("application/j-son;charset=UTF-8");
        }
      },
      success: function(result) {
	     //Write your code here
      }
}); */      
  
$('#search').click(function(){
	test = $('#vehicleNo').val();
	alert("1");
	alert(test);
});

$("#fadeOutBtn").click(function(){

	selectedDiv = $('#divChooseTxt').val();
	$("#"+selectedDiv).fadeOut(1500, "swing", function(){

	        });
	    });

$('#addTextBtn').click(function(){
    test = $('#divTxt').val();
    console.log(test);
    
    document.getElementById('thisdiv').innerHTML = test;
    
});
</script>

<style>
table {
    border-collapse: collapse;
    font-size: 12px;
    border-spacing: 0;
    /* width: 100%; */
    border: 1px solid #ddd;
}
th{
background-color: #ece5e5;
}
th, td {
    text-align: left;
    padding: 8px;
}

tr:nth-child(even){background-color: #f2f2f2}

.dataTables_filter{ 
	font-size: 13px;
	margin-left: 200px;
	margin-top: -23px;
    padding-bottom: 18px;
   /*  display: none; */
			}
.dataTables_length
	{ 
	width : 200px; 
	font-size: 13px;
	/* padding: 10px; */
	}
.dataTables_info{
    margin-top: 19px;
    width: 250px;
    font-size: 14px;
}
#example_paginate{
	margin-top: -15px;
    padding-left: 700px;
   font-size: 14px;
}


/* #example_first{ padding: 16px; background-color:#11131126;}
#example_previous{ padding: 16px; background-color:#11131126;}
.paginate_button { padding: 10px; background-color:#11131126; }
#example_next{padding: 16px; background-color:#11131126;}
#example_last{padding: 16px; background-color:#11131126;} */
</style>

<style>
#example_paginate {
    display: inline-block;
}

#example_paginate a {
    color: black;
    float: left;
    padding: 7px 13px;
    text-decoration: none;
}

#example_paginate a.active {
    background-color: #4CAF50;
    color: white;
    border-radius: 5px;
}

#example_paginate a:hover:not(.active) {
    background-color: #ddd;
    border-radius: 5px;
}
</style>

</head>
	
	<%
		if(request.getParameter("balance")!=null){
			int updateAmount = Integer.parseInt(request.getParameter("balance"));
			String partner = request.getParameter("partner");
			String remarks = request.getParameter("remarks");
			System.out.println("amount**********"+updateAmount+"partner***********"+partner);
			
			String txnid = new SimpleDateFormat("yyMMddHHmmssss").format(new Date());
			System.out.println("*****************"+txnid);
			
			String s = new DataManager().processRefundTopup(partner, updateAmount+"","Topup", "ACTopup",txnid );
			Connection con = null;
			PreparedStatement pstmt=null;
			/*java.sql.Statement stmt = null;
			java.sql.Statement stmt1 = null;*/
			//ResultSet rs = null;
			String result=null;
			//ResultSet rs1 = null;
			JSONObject obj = new JSONObject();
			try {
				/* String sql="update account_master set balance = balance + ? and remarks = ? where partner_id=?";
				
				System.out.println (sql);
				
				
				//con = DBConnection().getConnection();
 				con = new DBConnection().getConnection();
 				pstmt=con.prepareStatement(sql);
 				pstmt.setInt(1,updateAmount);
 				pstmt.setString(2,remarks);
 				pstmt.setString(3,partner);
				//stmt = con.createStatement();
				pstmt.executeUpdate(); */
				
				
			    //request.setAttribute("status","Success");
				if(s.equals("0")){
					%>
					 <center> <h4> <font color="red"> Transaction Failed. Go <a href="dashboard.jsp">Back</a>  </font></h4></center>
					 
					<%
					//fails
				}else
				{
					%>
					
					<center> <h4> <font color="green"> Balance updated successfully. Go <a href="dashboard.jsp">Back</a> </font></h4></center>
					
					<%
				}
				
				//response.sendRedirect("dashboard.jsp");
			}catch (Exception e) {
				request.setAttribute("status","Failed");
				obj.put("response_code", "EE");
				e.printStackTrace();
			} 
			finally {
				try{
					/* if(rs!=null)
						rs.close(); */
					if(pstmt!=null)
						pstmt.close();
					
					if(con!=null)
						con.close();
				}catch (Exception e1) {

					e1.printStackTrace();
				}
			}
		}else{
	
	%>

	<body>

<!-- <div>
	

</div> -->
     <!-- <form action="updateBalance.jsp" method="post"  id="myForm1" name="myForm1">
     	
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input type="submit" value="Search" id="search">
     </form> -->
	 <p><p>
	 
	 <form action="/RetailChannel/updateBalance.jsp" method="post">
	 <%-- <%
	  String status = (String) request.getAttribute("status");
	 System.out.println("******************"+status);
	%>
	
	<%
	 if(status == "Success"){%>
	 <center> <h4> <font color="green"> Balance updated successfully. </font></h4></center>
	 	 
	 <% } 
	 else if(status == "Failed"){%>
	 <center> <h4> <font color="green"> Error occurred while updating balance. </font></h4></center>
	 	 
	 <% } %> --%>
	Amount: <input type="text" name="balance" value="" maxlength="10" id="balance"><br>
	Remarks: 	 <textarea rows="4" cols="50" id="remarks" name="remarks"></textarea><br>
	 
     Partner:	<select id="partner" name="partner">
     	<option value="paynearby">Paynearby</option>
     	<option value="aadhaarShila">Aadhaarshila</option>
     	</select>
	
	<br>
	<input type="submit" value="Submit" id="submitButton" >
	
				</form>




	

 <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"> </script>

<!-- <script>

jQuery(document).ready(function ()
	    {
	
	 $(".tid" ).on("click", function() {
			//alert("hello");
		  if($(".tid:checked" ).length > 0)
		  {
		    $('#submitButton').prop('disabled', false);
		  }
		  else
		  {
		    $('#submitButton').prop('disabled', true);
		  }  
		});
	    });
	    
</script> -->
<%} %>
  </body>
   
</html>