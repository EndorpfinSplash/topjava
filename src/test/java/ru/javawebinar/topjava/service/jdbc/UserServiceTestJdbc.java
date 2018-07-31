package ru.javawebinar.topjava.service.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

@ActiveProfiles(Profiles.JDBC)
public class UserServiceTestJdbc extends AbstractUserServiceTest {
    @Autowired
    private UserService service;
}
