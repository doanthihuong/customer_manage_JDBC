
<%--
  Created by IntelliJ IDEA.
  User: Doan Thi Huong
  Date: 5/26/2022
  Time: 9:05 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1> Danh sách khách hàng</h1>
<a href="/customers?action=create">Tạo mới</a>
<a href="/customers?action=find">Tìm kiếm</a>
<c:forEach var="cus" items="${dskh}">
    <h2 >${cus.id},${cus.name},${cus.age}   <a href="/customers?action=edit&id=${cus.id}">Sửa</a>
        <a href="/customers?action=delete&id=${cus.id}">Xóa</a></h2>

</c:forEach>

</body>
</html>
