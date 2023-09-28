package ru.nfm.calendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nfm.calendar.model.Calendar;

@Repository
@Transactional(readOnly = true)
public interface CalendarRepository extends JpaRepository<Calendar, Integer> {


}
