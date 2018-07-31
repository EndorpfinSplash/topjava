package ru.javawebinar.topjava.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import ru.javawebinar.topjava.service.MealService;

@ActiveProfiles(Profiles.JPA)
public class MealServiceTestJpa extends AbstractMealServiceTest {

    @Autowired
    private MealService service;
}
