<%--
  Created by IntelliJ IDEA.
  User: Hai Nguyen
  Date: 2/7/2023
  Time: 4:48 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Deleting Product</title>
</head>
<body>
<h1>Delete Product</h1>
<p>
  <a href="/Product">Back to Product list</a>
</p>
<form method="post">
  <h3>Are you sure?</h3>
  <fieldset>
    <legend>Product information</legend>
    <table>
      <tr>
        <td> Product Name: </td>
        <td>${requestScope["product"].getProductName()}</td>
      </tr>
      <tr>
        <td>Price: </td>
        <td>${requestScope["product"].getPrice()}</td>
      </tr>
      <tr>
        <td>Description: </td>
        <td>${requestScope["product"].getDescription()}</td>
      </tr>
      <tr>
        <td>Producer: </td>
        <td>${requestScope["product"].getProducer()}</td>
      </tr>
      <tr>
        <td><input type="submit" value="Delete product"></td>
        <td><a href="/Product">Back to Product list</a></td>
      </tr>
    </table>
  </fieldset>
</form>
</body>
</html>
