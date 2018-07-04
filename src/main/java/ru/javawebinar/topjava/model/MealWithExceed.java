package ru.javawebinar.topjava.model;

import ru.javawebinar.topjava.Entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MealWithExceed extends Entity {
    private final LocalDateTime dateTime;

    private final String description;

    private final int calories;

    private final boolean exceed;

    final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm");


    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDateTimeForJSP() {
        return dateTime.format(FORMATTER);
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public boolean isExceed() {
        return exceed;
    }

    public MealWithExceed(int id, LocalDateTime dateTime, String description, int calories, boolean exceed) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
        this.exceed = exceed;
    }


    @Override
    public String toString() {
        return "UserMealWithExceed{" +
                "dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                ", exceed=" + exceed +
                '}';
    }
}