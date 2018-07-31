package ru.javawebinar.topjava.service.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

@ActiveProfiles(Profiles.JDBC)
public class MealServiceTestJdbc extends AbstractMealServiceTest {

    @Autowired
    private MealService service;
}
