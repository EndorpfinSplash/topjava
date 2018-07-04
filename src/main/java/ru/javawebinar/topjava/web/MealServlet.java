package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.DAO.AbstractDAO;
import ru.javawebinar.topjava.DAO.MealInMemoryDaoImpl;
import ru.javawebinar.topjava.DAO.MealWithExceedDaoInMemoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealList;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    public static ArrayList<Meal> mealList = MealList.getMealList();

    public static List<MealWithExceed> getListMealExceeded() {
        return MealsUtil.getFilteredWithExceeded( mealList, LocalTime.MIN, LocalTime.MAX, 2000);
    }

    private static String INSERT_OR_EDIT = "/meal_add_edit_form.jsp";
    private static String LIST_MEAL = "/meal.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String forwardJspDestination = "";
        String action = request.getParameter("action");

        AbstractDAO<Integer, MealWithExceed> mealWithExceedInMemoryDao = new MealWithExceedDaoInMemoryImpl();
        AbstractDAO<Integer, Meal> mealInMemoryDAO = new MealInMemoryDaoImpl();

        if (action.equalsIgnoreCase("delete")){
            int mealId = Integer.parseInt(request.getParameter("mealId").trim());
            System.out.println(mealId);
            System.out.println(mealInMemoryDAO.delete(mealId));
            forwardJspDestination = LIST_MEAL;
            request.setAttribute("mealListForJSP", mealWithExceedInMemoryDao.findAll());
        } else if (action.equalsIgnoreCase("edit")){
            forwardJspDestination = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(request.getParameter("mealId").trim());
            MealWithExceed mealWithExceed = mealWithExceedInMemoryDao.findEntityById(mealId);
            request.setAttribute("mealWithExceedForJSP", mealWithExceed);
        } else if (action.equalsIgnoreCase("listMeal")){
            forwardJspDestination = LIST_MEAL;
            request.setAttribute("mealListForJSP", mealWithExceedInMemoryDao.findAll());
        } else {
            forwardJspDestination = INSERT_OR_EDIT;
        }
//        request.setAttribute("mealListForJSP", mealWithExceedInMemoryDao.findAll());

        request.getRequestDispatcher(forwardJspDestination).forward(request, response);
        //       response.sendRedirect("meal.jsp");
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        AbstractDAO<Integer, Meal> mealInMemoryDAO = new MealInMemoryDaoImpl();
        AbstractDAO<Integer, MealWithExceed> mealWithExceedInMemoryDao = new MealWithExceedDaoInMemoryImpl();

        String mealId = request.getParameter("mealid");
        String mealDescription = request.getParameter("mealDescription");
        int mealCalories = Integer.parseInt(request.getParameter("calories"));

        if (mealId == null || mealId.isEmpty()) {
            Meal meal = new Meal(MealList.id_counter++, LocalDateTime.now(), mealDescription, mealCalories);
            System.out.println(meal);
            mealInMemoryDAO.create(meal);
        } else {
            mealInMemoryDAO.update(new Meal(Integer.parseInt(mealId.trim()), LocalDateTime.now(), mealDescription, mealCalories) );
        }

        request.setAttribute("mealListForJSP", mealWithExceedInMemoryDao.findAll());
        request.getRequestDispatcher(LIST_MEAL).forward(request, response);

    }
}
