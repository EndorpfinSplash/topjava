package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
@Controller
public class MealRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Collection<MealWithExceed> getAll() {
        log.info("getAll");
        return MealsUtil.getWithExceeded(service.getAll(SecurityUtil.authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Collection<MealWithExceed> getAll(LocalDate dateTimeStart, LocalDate dateTimeEnd, LocalTime localTimeStart, LocalTime localTimeEnd) {
        log.info("getAll with filter");

        return this.getAll().stream()
                .filter(mealWithExceed1 -> DateTimeUtil.isBetween(
                        mealWithExceed1.getDateTime().toLocalDate(),
                        dateTimeStart,
                        dateTimeEnd
                         )
                )
                .filter(mealWithExceed1 -> DateTimeUtil.isBetween(
                        mealWithExceed1.getDateTime().toLocalTime(),
                        localTimeStart ,
                        localTimeEnd )
                )
                .collect(Collectors.toList());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id, SecurityUtil.authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        meal.setUserId(SecurityUtil.authUserId());
        checkNew(meal);
        return service.create(meal, SecurityUtil.authUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal);
        assureIdConsistent(meal, id);
        meal.setUserId(SecurityUtil.authUserId());
        service.update(meal, SecurityUtil.authUserId());
    }

}
