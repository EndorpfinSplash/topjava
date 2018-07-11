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
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public List<Meal> getAll() {
        log.info("getAll");
        return service.getAllForUser(SecurityUtil.authUserId());
    }

    public List<MealWithExceed> getAllForUser() {
        log.info("getAll");
        return MealsUtil.getWithExceeded(service.getAllForUser(SecurityUtil.authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealWithExceed> getAllForUser(LocalDate dateTimeStart, LocalDate dateTimeEnd, LocalTime localTimeStart, LocalTime localTimeEnd) {
        log.info("getAll");

        dateTimeStart = dateTimeStart == null ? LocalDate.MIN : dateTimeStart;
        dateTimeEnd = dateTimeEnd == null ? LocalDate.MAX : dateTimeEnd;
        localTimeStart = localTimeStart == null ? LocalTime.MIN : localTimeStart;
        localTimeEnd = localTimeEnd == null ? LocalTime.MAX : localTimeEnd;

        return MealsUtil.getWithExceeded(
                service.getAllForUser(SecurityUtil.authUserId(), dateTimeStart, dateTimeEnd, localTimeStart, localTimeEnd),
                MealsUtil.DEFAULT_CALORIES_PER_DAY);
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
