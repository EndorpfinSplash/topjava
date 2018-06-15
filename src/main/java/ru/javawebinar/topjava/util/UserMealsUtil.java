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
        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));

//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> mapSumCalories = new HashMap<>();

        for (UserMeal userMeal : mealList) {
            LocalDate localDateForMeal = userMeal.getDateTime().toLocalDate();
            Integer sumCaloriesPerDay = mapSumCalories.getOrDefault(localDateForMeal, 0);
            mapSumCalories.put(localDateForMeal, userMeal.getCalories() + sumCaloriesPerDay);
        }


        for (UserMeal userMeal : mealList) {
            LocalDate localDateForMeal = userMeal.getDateTime().toLocalDate();
            Integer sumCaloriesPerDay = userMeal.getCalories() + mapSumCalories.getOrDefault(localDateForMeal, 0);
            mapSumCalories.put(localDateForMeal, sumCaloriesPerDay);
        }

        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();
        for (UserMeal userMeal : mealList) {
            LocalDate localDateForMeal = userMeal.getDateTime().toLocalDate();
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealWithExceeds.add(
                        new UserMealWithExceed(
                                userMeal.getDateTime(),
                                userMeal.getDescription(),
                                userMeal.getCalories(),
                                mapSumCalories.get(localDateForMeal) > caloriesPerDay
                        )
                );
            }
        }

        return userMealWithExceeds == null ? Collections.emptyList() : userMealWithExceeds;
    }
}
// Оцените Time complexity вашего алгоритма: моя оценка О(N) т.к. в решении задействованы foreach циклы по N элементам