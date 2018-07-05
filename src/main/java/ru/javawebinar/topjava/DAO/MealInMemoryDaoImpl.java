package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.MealServlet;

import java.util.ArrayList;

public class MealInMemoryDaoImpl extends AbstractDAO<Integer, Meal> {
    @Override
    public ArrayList<Meal> findAll() {
        return MealServlet.mealList;
    }

    @Override
    public Meal findEntityById(Integer id) {
        return MealServlet.mealList.get(id);
    }

    @Override
    public boolean delete(Integer id) {
        return MealServlet.mealList.remove(MealServlet.mealList.get(id));
    }

    @Override
    public Meal create(Meal entity) {
        MealServlet.mealList.add(entity);
        return entity;
    }

    @Override
    public Meal update(Meal entity) {
        return MealServlet.mealList.set(entity.getId(), entity);
    }
}
