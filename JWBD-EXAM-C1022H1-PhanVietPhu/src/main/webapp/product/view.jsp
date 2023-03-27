<%--
  Created by IntelliJ IDEA.
  User: Hai Nguyen
  Date: 2/7/2023
  Time: 5:19 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>View Product</title>
</head>
<body>
<h1>Product details</h1>
<p>
  <a href="/Product">Back to product list</a>
</p>
<table>
  <tr>
    <td>Name: </td>
    <td>${requestScope["product"].getProductName()}</td>
  </tr>
  <tr>
    <td>Email: </td>
    <td>${requestScope["product"].getPrice()}</td>
  </tr>
  <tr>
    <td>Address: </td>
    <td>${requestScope["product"].getDescription()}</td>
  </tr>
  <tr>
    <td>Address: </td>
    <td>${requestScope["product"].getProducer()}</td>
  </tr>
</table>
</body>
</html>