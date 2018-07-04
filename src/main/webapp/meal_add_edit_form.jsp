<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>MealEditForm</title>
</head>
<body>

<form method="POST" action='meal' name="frmAddUser">
    Meal ID : <input type="text" readonly="readonly" name="mealid"
                     value="<c:out value="${mealWithExceedForJSP.id}" />"/> <br/>
    Meal Description : <input
        type="text" name="mealDescription"
        value="<c:out value="${mealWithExceedForJSP.description}" />"/> <br/>
    Calories : <input
        type="text" name="calories"
        value="<c:out value="${mealWithExceedForJSP.calories}" />"/> <br/>

    <%--DOB : <input--%>
        <%--type="text" name="dob"--%>
        <%--value="<fmt:formatDate pattern="MM/dd/yyyy" value="${user.dob}" />"/> <br/>--%>
    <%----%>
    <%--Email : <input type="text" name="email"--%>
                   <%--value="<c:out value="${user.email}" />"/> <br/> --%>

    <input type="submit" value="Submit"/>
</form>

</body>
</html>
