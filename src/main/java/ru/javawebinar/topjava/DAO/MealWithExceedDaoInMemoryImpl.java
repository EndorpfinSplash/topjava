package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.web.MealServlet;

import java.util.List;

public class MealWithExceedDaoInMemoryImpl extends AbstractDAO<Integer,MealWithExceed> {
    @Override
    public List<MealWithExceed> findAll() {
        return MealServlet.getListMealExceeded();
    }

    @Override
    public MealWithExceed findEntityById(Integer id) {
        return  MealServlet.getListMealExceeded().get(id);
    }

    @Override
    public boolean delete(Integer id) {
        return  MealServlet.getListMealExceeded().remove(id);
    }

    @Override
    public boolean delete(MealWithExceed entity) {
        return  MealServlet.getListMealExceeded().remove(entity);
    }

    @Override
    public boolean create(MealWithExceed entity) {
        return  MealServlet.getListMealExceeded().add(entity);
    }

    @Override
    public MealWithExceed update(MealWithExceed entity) {
        return  MealServlet.getListMealExceeded().set( entity.getId(), entity);
    }
}
