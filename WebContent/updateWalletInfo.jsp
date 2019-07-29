<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

  <script src="js/jquery-1.12.4.js"></script>
 <script src="js/jquery.dataTables.min.js"></script>

 <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"> </script>
 


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
	<body>

	 <p>Message: ${message}</p>
	 <form action="/RetailChannel/FileUploadHandler" method="post" enctype="multipart/form-data">
	 <input type="file" name="uploadList" id="uploadList" class="uploadList" >
     	
	
	<input type="submit" value="Submit" id="submitButton" ><br>
	<a href="dashboard.jsp">Home</a>
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

  </body>
   
</html>