package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Meal m WHERE m.id=:id and m.user.id = :user_id")
    int delete(@Param("id") int id, @Param("user_id") int user_id);

    @Override
    @Transactional
    Meal save(Meal meal);

    Optional<Meal> getByIdAndUserId(int id, int userId);

    List<Meal> getAllByUserId(int userId, Sort sort);

    @Query("SELECT m FROM Meal m WHERE m.user.id = :id and m.dateTime between :startDate and :endDate order by m.dateTime desc")
    List<Meal> getUserMealBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, @Param("id") int id);
}