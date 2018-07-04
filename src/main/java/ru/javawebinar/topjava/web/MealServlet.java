package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.DAO.AbstractDAO;
import ru.javawebinar.topjava.DAO.MealInMemoryDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealList;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    public static ArrayList<Meal> mealList = MealList.getMealList();

    AbstractDAO<Integer, Meal> mealInMemoryDAO = new MealInMemoryDaoImpl();

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static List<MealWithExceed> getListMealExceeded() {
        return MealsUtil.getFilteredWithExceeded( mealList, LocalTime.MIN, LocalTime.MAX, 2000);
    }

    private static String INSERT_OR_EDIT = "/meal_add_edit_form.jsp";
    private static String LIST_MEAL = "/meal.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String forwardJspDestination;
        String action = request.getParameter("action");
        request.setAttribute("formatterForJSP", DATE_TIME_FORMATTER);

        if (action.equalsIgnoreCase("delete")){
            int mealId = Integer.parseInt(request.getParameter("mealId").trim());
            mealInMemoryDAO.delete(mealId);
            forwardJspDestination = LIST_MEAL;
            request.setAttribute("mealListForJSP", getListMealExceeded());
        } else if (action.equalsIgnoreCase("edit")){
            forwardJspDestination = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(request.getParameter("mealId").trim());
            Meal meal = mealInMemoryDAO.findEntityById(mealId);
            request.setAttribute("mealForEditJSP",meal);
        } else if (action.equalsIgnoreCase("listMeal")){
            forwardJspDestination = LIST_MEAL;
            request.setAttribute("mealListForJSP", getListMealExceeded());
        } else {
            forwardJspDestination = INSERT_OR_EDIT;
        }

        request.getRequestDispatcher(forwardJspDestination).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String mealId = request.getParameter("mealid");
        String mealDescription = request.getParameter("mealDescription");
        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("mealLocalDateTime"), DATE_TIME_FORMATTER);
        int mealCalories = Integer.parseInt(request.getParameter("calories"));

        if (mealId == null || mealId.isEmpty()) {
            Meal meal = new Meal( localDateTime, mealDescription, mealCalories);
            mealInMemoryDAO.create(meal);
        } else {
            mealInMemoryDAO.update(new Meal(Integer.parseInt(mealId), localDateTime, mealDescription, mealCalories) );
        }

        request.setAttribute("mealListForJSP", getListMealExceeded());
        request.getRequestDispatcher(LIST_MEAL).forward(request, response);
    }
}
