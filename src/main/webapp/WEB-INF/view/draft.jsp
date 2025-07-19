<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
<title>Document Draft</title>
</head>
<body>
<span class="d-block p-2 bg-dark text-white"><center>Document Draft</center></span>
<nav class="navbar navbar-light bg-light">
    <a class="navbar-brand" href="home"><strong>Back to Home</strong></a>
</nav>
<div class="container">
    <br>
    <c:if test="${not empty error}">
        <div class="alert alert-danger">${error}</div>
    </c:if>
    <form action="draft/generate" method="post">
        <div class="form-group">
            <label for="template">Template Name</label>
            <select class="form-control" id="template" name="template">
                <c:forEach var="t" items="${templates}">
                    <option value="${t}">${t}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="values">Values (JSON)</label>
            <textarea id="values" name="values" class="form-control" rows="5" placeholder='{"client_name":"John"}'></textarea>
        </div>
        <div class="form-group">
            <label>Output Type</label>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="type" id="pdf" value="pdf" checked>
                <label class="form-check-label" for="pdf">PDF</label>
            </div>
            <div class="form-check">
                <input class="form-check-input" type="radio" name="type" id="word" value="word">
                <label class="form-check-label" for="word">Word</label>
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Generate</button>
    </form>
    <br>
</div>
</body>
</html>
