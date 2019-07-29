<%
	session.removeAttribute("userId");
	session.removeAttribute("invalid");
	response.sendRedirect("login.jsp");
	
%>