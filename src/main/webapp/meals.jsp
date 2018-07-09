<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }
        .exceeded {
            color: red;
        }
    </style>
</head>
<body>


<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<a href="meals?action=create">Add Meal</a>
<hr/>

<form method="get" action="meals">
    <dl>
        <dd>От даты: <input type="date"  name="date_start_jsp" ></dd>
        <dd>До даты: <input type="date"  name="date_end_jsp" ></dd>
    </dl>

    <dl>
        <dd>От времени:<input type="time"  name="time_start_jsp" ></dd>
        <dd>До времени:<input type="time"  name="time_end_jsp" ></dd>
    </dl>

    <button type="submit">Отфильтровать</button>
    <button onclick="window.history.back()" type="button">Отменить</button>
</form>

<section>

    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
 </section>
</body>
</html>