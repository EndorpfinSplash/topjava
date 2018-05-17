package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 511)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();

        Map<LocalDate, Integer> mapSumCalories = new HashMap<>();

        for (Iterator<UserMeal> mealIterator = mealList.iterator(); mealIterator.hasNext(); ) {
            UserMeal next = mealIterator.next();
            Integer agregatedCaloriesPerDay = mapSumCalories.getOrDefault(next.getDateTime().toLocalDate(),0);
            mapSumCalories.put(next.getDateTime().toLocalDate(), agregatedCaloriesPerDay + next.getCalories());
        }

        for (Iterator<UserMeal> mealIterator = mealList.iterator(); mealIterator.hasNext(); ) {
            UserMeal next = mealIterator.next();
            if (TimeUtil.isBetween(next.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealWithExceeds.add(new UserMealWithExceed(
                        next.getDateTime(),
                        next.getDescription(), next.getCalories(),
                        mapSumCalories.get(next.getDateTime().toLocalDate()) > caloriesPerDay)
                );
            }
        }
        return userMealWithExceeds;
    }
}
