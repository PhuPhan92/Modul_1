<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Greeting</title>
</head>
<body>
<form action="tranf" method="post">
    <h4>USD</h4>
    <input type="text" name="input" value="1">
    <h4>USD</h4>
    <input type="text" name="rate" value="24000">
    <button type="submit"> tranf </button>
    <h1>Giá trị chuyển đổi</h1>
    <h2>${output} VNĐ</h2>
</form>


</body>
</html>
