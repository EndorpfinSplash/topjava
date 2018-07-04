<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>

    <style>
        table, th, td {
            border: 1px solid black;
        }
    </style>

</head>
<body>
<h3><a href="index.html">Home</a></h3>
<%--<h2>Meals</h2>--%>

<table>
    <caption><h2>Meals</h2></caption>
    <tr>
        <th>Time</th>
        <th>Desctiption</th>
        <th>Calories</th>
    </tr>
    <c:forEach var="meal_record" items="${mealListForJSP}">
    <tr bgcolor = ${meal_record.isExceed()?  "#ff6461": "#8cff94"} >
        <td>${meal_record.getDateTime().format(formatterForJSP)}</td>
        <td>${meal_record.getDescription()}</td>
        <td>${meal_record.getCalories()}</td>
        <td><a href="meal?action=edit&mealId= <c:out value="${meal_record.id}"/>">Update</a></td>
        <td><a href="meal?action=delete&mealId=<c:out value="${meal_record.id}"/>">Delete</a></td>
    </tr>
    </c:forEach>

</table>
<p><a href="meal?action=insert">Add meal</a></p>

</body>
</html>