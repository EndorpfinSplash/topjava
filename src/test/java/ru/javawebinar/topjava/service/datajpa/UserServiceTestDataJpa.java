package ru.javawebinar.topjava.service.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

@ActiveProfiles(Profiles.DATAJPA)
public class UserServiceTestDataJpa extends AbstractUserServiceTest {
    @Autowired
    private UserService service;
}
