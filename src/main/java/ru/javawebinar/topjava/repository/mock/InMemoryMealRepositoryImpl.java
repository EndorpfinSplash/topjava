package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
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
        return this.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {

        Meal meal = repository.get(id);

        if (meal == null || meal.getUserId() != userId) {
            return false;
        }
        return repository.remove(id, meal);
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
        return repository.values().stream()
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Meal> getAllForUser(int userId) {
        return getAllForUser(userId,LocalDate.MIN, LocalDate.MAX, LocalTime.MIN, LocalTime.MAX);
    }

    @Override
    public List<Meal> getAllForUser(int userId, LocalDate dateTimeStart, LocalDate dateTimeEnd, LocalTime localTimeStart, LocalTime localTimeEnd) {
        return repository.values().stream()
                .filter(meal -> userId==meal.getUserId())
                .filter(meal -> DateTimeUtil.isBetween(
                        meal.getDateTime().toLocalDate(),
                        dateTimeStart, dateTimeEnd))
                .filter(meal -> DateTimeUtil.isBetween(
                        meal.getDateTime().toLocalTime(),
                        localTimeStart, localTimeEnd))
                .sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }
}

