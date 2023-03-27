<%--
  Created by IntelliJ IDEA.
  User: Hai Nguyen
  Date: 2/7/2023
  Time: 3:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create new customer</title>
    <style>
        .message{
            color:green;
        }
    </style>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
    <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<h1>Create new Product</h1>
<p>
    <c:if test='${requestScope["message"] != null}'>
        <span class="message">${requestScope["message"]}</span>
    </c:if>
</p>
<p>
    <a href="/Product">Back to Product list</a>
</p>
<form method="post">
    <fieldset>
        <legend>Product information</legend>
        <table>
            <tr>
                <td> Product Name: </td>
                <td><input type="text" name="ProductName" id="name"></td>
            </tr>
            <tr>
                <td>Price: </td>
                <td><input type="text" name="price" id="price"></td>
            </tr>
            <tr>
                <td>Description: </td>
                <td><input type="text" name="description" id="description"></td>
            </tr>
            <tr>
                <td>Producer: </td>
                <td><input type="text" name="producer" id="producer"></td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="Create Product"></td>
            </tr>
            <div class="row mt-2">
                <label class="col-3">Image: </label> <input style="padding-left: 0" class="col-9" id="imgInput" type="file" name="image" value="${requestScope.customer.getImage()}">
            </div>
            <div class="row mt-2">
                <img class="offset-3" id="idImage" width="200px" height="auto">
            </div>
        </table>
    </fieldset>
</form>
<script>
    let imgInput = document.getElementById("imgInput");
    let idImage = document.getElementById("idImage");
    imgInput.onchange = evt => {
        const [file] = imgInput.files
        if (file) {
            idImage.src = URL.createObjectURL(file)
        }
    }
</script>
</body>
</html>
