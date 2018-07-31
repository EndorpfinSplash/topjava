package ru.javawebinar.topjava.service.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.service.UserService;

@ActiveProfiles(Profiles.JPA)
public class UserServiceTestJpa extends AbstractUserServiceTest {
    @Autowired
    private UserService service;
}
