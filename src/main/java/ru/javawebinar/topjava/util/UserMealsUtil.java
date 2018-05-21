package ru.javawebinar.topjava.util;

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
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
        System.out.println(getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExceed> userMealWithExceeds = new ArrayList<>();

//        Map<LocalDate, Integer> mapSumCalories = new HashMap<>();
//
//        for (Iterator<UserMeal> mealIterator = mealList.iterator(); mealIterator.hasNext(); ) {
//            UserMeal next = mealIterator.next();
//            Integer agregatedCaloriesPerDay = mapSumCalories.getOrDefault(next.getDateTime().toLocalDate(),0);
//            mapSumCalories.put(next.getDateTime().toLocalDate(), agregatedCaloriesPerDay + next.getCalories());
//        }

        Map<LocalDate, Integer> mapSumCalories = mealList.stream()
                .collect(
                        Collectors.toMap(
                                m -> m.getDateTime().toLocalDate(),
                                m -> m.getCalories(),
                                (m1, m2) -> m1 + m2
                        )
                );

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
        return userMealWithExceeds == null ? Collections.emptyList() : userMealWithExceeds;
    }
}
// Оцените Time complexity вашего алгоритма: моя оценка О(2*N) т.к. в решении задействовано 2 foreach цикла по N элементам