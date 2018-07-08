package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public abstract class AbstractMealController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Collection<MealWithExceed> getAll() {
        log.info("getAll");
        return MealsUtil.getWithExceeded(service.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Collection<MealWithExceed> getAll(int userId) {
        log.info("getAll for user ID");
        return MealsUtil.getWithExceeded(service.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Collection<MealWithExceed> getAll(int userId, LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd) {
        log.info("getAll for user ID with filter");

        return this.getAll(userId).stream()
                .filter(mealWithExceed1 -> DateTimeUtil.isBetween(mealWithExceed1.getDateTime(), dateTimeStart, dateTimeEnd))
                .filter(mealWithExceed1 -> DateTimeUtil.isBetween(mealWithExceed1.getDateTime().toLocalTime(), dateTimeStart.toLocalTime(), dateTimeEnd.toLocalTime()))
                .collect(Collectors.toList());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(Meal meal) {
        log.info("update {} with id={}", meal);
        //  assureIdConsistent(meal, id);
        service.update(meal);
    }

}
