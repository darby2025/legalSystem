<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css"
	integrity="sha384-WskhaSGFgHYWDcbwN70/dfYBj47jz9qbsMId/iRN3ewGhXQFZCSftd1LZCfmhktB"
	crossorigin="anonymous">
<link
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet">
<script>
function goBack() {
    window.history.back();
}
</script>
<title>Dashboard</title>

</head>
<body>

	<!-- Image and text -->
	<span class="d-block p-2 bg-dark text-white"><center>Welcome to EDP Lawyers Cassy Tool</center></span>
	<nav class="navbar navbar-expand-lg navbar-light bg-light"> <a
		class="navbar-brand" href="home"><strong>EDP Lawyers Cassy Tool</strong> </a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarText" aria-controls="navbarText"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarText">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item"><a class="nav-link" href="case">Cases</a></li>
			<li class="nav-item"><a class="nav-link" href="document">Documents</a></li>
			<li class="nav-item"><a class="nav-link" href="reminder">Reminder</a></li>
			<li class="nav-item"><a class="nav-link" href="lawyer">Lawyer</a></li>
			<li class="nav-item"><a class="nav-link" href="case-logs">Logs</a></li>
			<li class="nav-item"><a class="nav-link" href="acts">Acts</a></li>			
			<li class="nav-item"><a class="nav-link" href="user">Users</a></li>
			<li class="nav-item"><a class="nav-link" href="updates">Updates</a></li>
		</ul>
		<span class="navbar-text"> <i class="fa fa-user-circle" style="font-size:1.2rem;color:#17a2b8">&nbsp; <a href="viewuser?name=${name }">${name}</a></i></span> &nbsp;&nbsp;&nbsp;
		<a href="" data-toggle="modal" data-target="#exampleModalCenter"><i class="fa fa-sign-out" style="font-size:30px;color:red" ></i></a>
	</div>
	</nav>
	<ol class="breadcrumb">
          <li class="breadcrumb-item"><a href="home">Dashboard</a></li>
        <%@ page import="java.text.*,java.util.*" %>
           <div align="right" style="margin-left:60%;font-weight: bold; color:  #343a40;"">
             <% SimpleDateFormat d=new SimpleDateFormat("dd-MM-yyyy"); %>
             <% SimpleDateFormat t=new SimpleDateFormat(" HH:mm aa"); %>
           Date:  <%= d.format(new Date()) %> &nbsp;&nbsp;Time: <%= t.format(new Date()) %>
        </div>
          <!-- Breadcrumb Menu-->
         
        </ol>
	<br><br>
	
<div class="container" id="homediv">
				<br>
				<br>
				<br>
				<br>
				<div class="jumbotron text-center">
					<h1>Sorry!</h1>
					<p>you are not authorized to access this page</p>
					<button type="submit" onclick="goBack()" class="btn btn-primary">Go Back</button>
				</div>	
				<br>
				<br>
				<br>
			</div>
 <br><br>
<!-- Image and text -->
	<span class="d-block p-2 bg-dark text-white"><center> &copy; 2018, DCM Textiles</center></span>
	<!-- Modal -->
<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="exampleModalLongTitle">Logging Out!</h5>
        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">&times;</span>
        </button>
      </div>
      <div class="modal-body">
        <p>Are you sure you want to Logout?</p>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">No</button>
       <a href="logout"> <button type="button" class="btn btn-primary">Yes</button></a>
      </div>
    </div>
  </div>
</div> 	
	<!-- jQuery first, then Popper.js, then Bootstrap JS -->
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
		integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"
		integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T"
		crossorigin="anonymous"></script>

</body>
</html>