package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealRepository {
    Meal save(Meal meal);
    Meal save(Meal meal, int userId);

    void delete(int id);
    void delete(int id, int userId);

    Meal get(int id);
    Meal get(int id, int userId);

    Collection<Meal> getAll();
    Collection<Meal> getAll(int userId);

}
