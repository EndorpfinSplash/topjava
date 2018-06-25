package ru.javawebinar.topjava.util;

import org.apache.commons.lang3.mutable.MutableBoolean;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

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
        System.out.println(getFilteredWithExceededOneCycle(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExceed> getFilteredWithExceededOneCycle(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> mapSumCaloriesPerDay = new HashMap<>();
        Map<LocalDate, MutableBoolean> mapExceededDays = new HashMap<>();
        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();

        for (UserMeal userMeal : mealList) {
            LocalDate localDateForMeal = userMeal.getDateTime().toLocalDate();
            mapSumCaloriesPerDay.put(localDateForMeal, userMeal.getCalories() + mapSumCaloriesPerDay.getOrDefault(localDateForMeal, 0));
            if (!mapExceededDays.containsKey(localDateForMeal)) {
                mapExceededDays.put(localDateForMeal, new MutableBoolean(false));
            }
            else if (mapSumCaloriesPerDay.get(localDateForMeal) > caloriesPerDay) {
                mapExceededDays.get(localDateForMeal).setValue(true);
            }
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                userMealWithExceeds.add(
                        new UserMealWithExceed(
                                userMeal.getDateTime(),
                                userMeal.getDescription(),
                                userMeal.getCalories(),
                                mapExceededDays.get(localDateForMeal)
                        )
                );
            }
        }
        return userMealWithExceeds;
    }

}
// Оцените Time complexity вашего алгоритма: моя оценка О(N) т.к. в решении задействованы foreach циклы по N элементам