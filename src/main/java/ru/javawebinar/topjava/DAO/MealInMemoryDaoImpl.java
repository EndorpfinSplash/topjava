package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.MealServlet;

import java.util.List;

public class MealInMemoryDaoImpl extends AbstractDAO<Integer, Meal> {
    @Override
    public List<Meal> findAll() {
        return MealServlet.mealList;
    }

    @Override
    public Meal findEntityById(Integer id) {
        return MealServlet.mealList.get(id);
    }

    @Override
    public boolean delete(Integer id) {
        return MealServlet.mealList.remove(id);
    }

    @Override
    public boolean create(Meal entity) {
        return MealServlet.mealList.add(entity);
    }

    @Override
    public Meal update(Meal entity) {
        return MealServlet.mealList.set(entity.getId(), entity);
    }
}
