<!DOCTYPE html>
<%@page import="java.util.List"%>
<%-- <%@page import="com.payone.pg.db.DataManager"%> --%>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Reports</title>
 

<style>
table, th, td {
    border: 1px solid black;
    width: 1360px;    
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
}
.mobile{
	width: 196px;
    /* text-align: center; */
    height: 27px;
    align-items: center;
    margin-left: 46px;
}
.pass{
	width: 196px;
    /* text-align: center; */
    height: 27px;
    align-items: center;
    margin-left: 46px;
}
.boxTable{
	width: 300px;
    border: 1px solid #ddd;
    padding: 25px;
    margin-left: 470px;
    height: 150px;
}

.submit{
	margin-left: 92px;
    height: 27px;
    width: 93px;
    text-align: center;
    font-family: sans-serif;
    background-color: #4981af;
    
}
.loginBox{
	margin-top: 25px;
}
.copyright{
	margin-top: 265px;
    margin-left: 553px;
}
.status{
	color:red;
	margin-left:518px;
}
</style>

<% 
	String status = null;
	if(request.getAttribute("invalid") != null){
		//status = request.getAttribute("invalid").toString();
		status = "*Invalid Username and Password";
	}
	
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
<!-- <div class="Title">Transaction Reports</div> -->
<br/>
<br/>
<form method="post" action="signin">
</br>
</br>
<div class="boxTable">
	<div class="loginBox">
		<input class="mobile" type="text" name ="userid" placeholder=" UserId" required/></br></br>
		<input class="pass" type="password" name ="password" placeholder=" Password" required/></br></br>
		<input class="submit" type="Submit" value="Submit">
	</div>	
</div>
	
</form>

</br>
<% if(status != null) {%>
		<span class="status"><%=status%></span>		
<%}else{
%>
<span></span>
<%
}
%>

</body>
<div class="copyright">
	 <span>&nbsp;1Pay. All rights reserved</span>
</div>
</html>
