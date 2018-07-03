package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.MealWithExceed;

import java.util.List;

public class MealWithExceedDaoInMemoryImpl extends AbstractDAO<Integer,MealWithExceed> {
    @Override
    public List<MealWithExceed> findAll() {
        return null;
    }

    @Override
    public MealWithExceed findEntityById(Integer id) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public boolean delete(MealWithExceed entity) {
        return false;
    }

    @Override
    public boolean create(MealWithExceed entity) {
        return false;
    }

    @Override
    public MealWithExceed update(MealWithExceed entity) {
        return null;
    }
}
