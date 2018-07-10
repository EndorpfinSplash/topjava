package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.getUserId() != userId) {
            return null;
        }
        return this.save(meal);
    }

    public boolean delete(int id) {
        return repository.remove(id, repository.get(id));
    }

    @Override
    public boolean delete(int id, int userId) {

        if (repository.get(id).getUserId() != userId) {
            return false;
        }
        return repository.remove(id, repository.get(id));
    }

    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public Meal get(int id, int userId) {
        Meal meal = repository.getOrDefault(id, null);
        if (meal == null || userId != meal.getUserId()) {
            return null;
        }
        return meal;
    }

    @Override
    public List<Meal> getAll() {
        List<Meal> userMealList = repository.values().stream()
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
        return userMealList.isEmpty() ? null : userMealList;
    }

    @Override
    public List<Meal> getAll(int userId) {
        List<Meal> userMealList =
                repository.values().stream()
                        .filter(meal -> meal.getUserId() == userId)
                        .sorted(Comparator.comparing(Meal::getDate).reversed())
                        .collect(Collectors.toList());
        return userMealList.isEmpty() ? null : userMealList;
    }
}

