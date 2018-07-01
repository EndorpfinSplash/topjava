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
        <%--<th>Exceed</th>--%>
    </tr>
    <c:forEach var="meal_record" items="${mealListForJSP}">
    <tr bgcolor = ${meal_record.isExceed()?  "green": "red"} >
            <%--<td>${meal_record.getDateTime()}</td>--%>
        <td>${meal_record.getDateTimeForJSP()}</td>
        <td>${meal_record.getDescription()}</td>
        <td>${meal_record.getCalories()}</td>
            <%--<td>${num.isExceed()}</td>--%>
    </tr>
    </c:forEach>

</table>

</body>
</html>