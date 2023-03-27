<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
<h1>Sandwich Condiments</h1>
<form action="save" method="post">
     <span> <input type="checkbox" name="condiments" value="Lettuce">Lettuce</span>
     <span> <input type="checkbox" name="condiments" value="Tomato">Tomato</span>
     <span> <input type="checkbox" name="condiments" value="Mustard">Mustard</span>
     <span> <input type="checkbox" name="condiments" value="Sprouts">Sprouts</span>
    <input type="submit" value="Save">
</form>
<h3>the spices chosen are: <c:forEach var="p" items="${requestScope.condiments}" > ${p}</c:forEach></h3>
</body>
</html>
