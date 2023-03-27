<%--
  Created by IntelliJ IDEA.
  User: Hai Nguyen
  Date: 2/7/2023
  Time: 3:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Product List</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<h1 class="text-center">Product List</h1>
<p>
  <button><a href="/Product?action=create">Create new customer</a></button>
  <form class="d-flex" method="post">
  <input class="form-control me-2" type="text" placeholder="Search">
  <button class="btn btn-primary" type="button">Search</button>
  </form>
</p>
<table class="table table-dark container table-bordered">
  <tr>
    <td>Product Name</td>
    <td>Price</td>
    <td>Description</td>
    <td>Producer</td>
    <td>.</td>
    <td>.</td>
  </tr>
  <c:forEach items='${requestScope["product"]}' var="p">
    <tr>
      <td><a href="/Product?action=view&id=${p.getId()}">${p.getProductName()}</a></td>
      <td>${p.getPrice()}</td>
      <td>${p.getDescription()}</td>
      <td>${p.getProducer()}</td>
      <td><a href="/Product?action=edit&id=${p.getId()}">edit</a></td>
      <td><a href="/Product?action=delete&id=${p.getId()}">delete</a></td>
    </tr>
  </c:forEach>
</table>
</body>
</html>
