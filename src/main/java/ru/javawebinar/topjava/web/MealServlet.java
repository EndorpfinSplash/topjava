package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.AbstractDAO;
import ru.javawebinar.topjava.dao.MealInMemoryDaoImpl;
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
    private static String INSERT_OR_EDIT = "/meal_add_edit_form.jsp";
    private static String LIST_MEAL = "/meal.jsp";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static final Logger log = getLogger(MealServlet.class);
    public static ArrayList<Meal> mealList = MealList.getMealList();

    AbstractDAO<Integer, Meal> mealInMemoryDAO = new MealInMemoryDaoImpl();

    public static List<MealWithExceed> getListMealExceeded(ArrayList<Meal> mealList) {
        return MealsUtil.getFilteredWithExceeded(mealList, LocalTime.MIN, LocalTime.MAX, 2000);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String forwardJspDestination;
        String action = request.getParameter("action");
        request.setAttribute("formatterForJSP", DATE_TIME_FORMATTER);
        int mealId;

        switch (action == null ? "default_action" : action) {
            case "delete":
                mealId = Integer.parseInt(request.getParameter("mealId").trim());
                mealInMemoryDAO.delete(mealId);
                response.sendRedirect("meal");
                return;
            case "edit":
                mealId = Integer.parseInt(request.getParameter("mealId").trim());
                forwardJspDestination = INSERT_OR_EDIT;
                Meal meal = mealInMemoryDAO.findEntityById(mealId);
                request.setAttribute("mealForEditJSP", meal);
                break;
            case "insert":
                forwardJspDestination = INSERT_OR_EDIT;
                break;
            default:
                forwardJspDestination = LIST_MEAL;
                request.setAttribute("mealListForJSP", getListMealExceeded(mealInMemoryDAO.findAll()));
        }

        request.getRequestDispatcher(forwardJspDestination).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("formatterForJSP", DATE_TIME_FORMATTER);

        String mealId = request.getParameter("mealid");
        String mealDescription = request.getParameter("mealDescription");
        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("mealLocalDateTime"), DATE_TIME_FORMATTER);
        int mealCalories = Integer.parseInt(request.getParameter("calories"));

        if (mealId == null || mealId.isEmpty()) {
            Meal meal = new Meal(localDateTime, mealDescription, mealCalories);
            mealInMemoryDAO.create(meal);
        } else {
            mealInMemoryDAO.update(new Meal(Integer.parseInt(mealId), localDateTime, mealDescription, mealCalories));
        }

        request.setAttribute("mealListForJSP", getListMealExceeded(mealInMemoryDAO.findAll()));
        request.getRequestDispatcher(LIST_MEAL).forward(request, response);
    }
}
