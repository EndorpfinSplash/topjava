package ru.javawebinar.topjava.model;

import org.apache.commons.lang3.mutable.MutableBoolean;

import java.time.LocalDateTime;

public class UserMealWithExceed {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private MutableBoolean exceed;

    public UserMealWithExceed(LocalDateTime dateTime, String description, int calories, MutableBoolean exceed) {
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }

    @Override
    public String toString() {
        return this.dateTime.toLocalDate().toString() + " " +
                this.description + " " +
                this.calories + " " +
                this.exceed + "\n";
    }
}
