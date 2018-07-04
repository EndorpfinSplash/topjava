package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MealList {

    public static int id_counter;

    public static ArrayList<Meal> mealList;

    public static ArrayList<Meal> getMealList() {
        if (mealList == null) {
            mealList = new ArrayList(Arrays.asList(
                    new Meal(id_counter++, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                    new Meal(id_counter++, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                    new Meal(id_counter++, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                    new Meal(id_counter++, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                    new Meal(id_counter++, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                    new Meal(id_counter++, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)));
        }
        return mealList;
    }



}
