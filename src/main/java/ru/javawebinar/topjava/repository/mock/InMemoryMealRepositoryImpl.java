package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;
import java.util.Comparator;
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

    @Override
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
            throw new NotFoundException("You can't save meal for other user");
        }
        return this.save(meal);
    }

    @Override
    public void delete(int id) {
        repository.remove(id);
    }

    @Override
    public void delete(int id, int userId) {
        if (repository.get(id).getUserId() != userId) {
            throw new NotFoundException("You can't delete meal for other user");
        }
    }

    @Override
    public Meal get(int id) {
        return repository.get(id);
    }

    @Override
    public Meal get(int id, int userId) {
        if (!repository.containsKey(id)) {
            throw new NotFoundException("Such meal doesn't exists in the list");
        } else if (repository.get(id).getUserId() != userId) {
            throw new NotFoundException("You can't get meal for other user");
        }
        return this.get(id);
    }

    @Override
    public Collection<Meal> getAll() {
        Collection<Meal> userMealList = repository.values().stream().sorted(Comparator.comparing(Meal::getDate).reversed()).collect(Collectors.toList());
        return userMealList.isEmpty() ? null : userMealList;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        Collection<Meal> userMealList =
                repository.values().stream().filter(meal -> meal.getUserId() == userId).sorted(Comparator.comparing(Meal::getDate).reversed()).collect(Collectors.toSet());
        return userMealList.isEmpty() ? null : userMealList;
    }
}

