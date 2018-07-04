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
                     value="<c:out value="${mealForEditJSP.id}" />"/> <br/>

    LocalDateTime:<input
        type="text" name="mealLocalDateTime"
        value="<c:out value="${mealForEditJSP.dateTime.format(formatterForJSP)}" />"/> <br/>

    Meal Description : <input
        type="text" name="mealDescription"
        value="<c:out value="${mealForEditJSP.description}" />"/> <br/>
    Calories : <input
        type="text" name="calories"
        value="<c:out value="${mealForEditJSP.calories}" />"/> <br/>

    <input type="submit" value="Add/Edit meal"/>
</form>

</body>
</html>
