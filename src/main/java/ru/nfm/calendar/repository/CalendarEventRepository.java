package ru.nfm.calendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nfm.calendar.model.CalendarEvent;

import java.time.Instant;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Integer> {

    @Query("SELECT e FROM CalendarEvent e WHERE e.calendar.id = :calendarId AND e.timeFrom >= :dtFrom AND e.timeTo < :dtTo")
    List<CalendarEvent> findCalendarEventsByDateTimeRange(@Param("calendarId") int calendarId,
                                                          @Param("dtFrom") Instant dtFrom,
                                                          @Param("dtTo") Instant dtTo);

    @Query("SELECT e FROM CalendarEvent e JOIN e.calendar.calendarUsers cu " +
            "WHERE cu.userProfile.id = :userId " +
            "AND e.calendar.id = :calendarId " +
            "AND e.timeFrom >= :dtFrom AND e.timeTo < :dtTo")
    List<CalendarEvent> findUserAttachedCalendarEventsByDateTimeRange(@Param("userId") int userId,
                                                                      @Param("calendarId") int calendarId,
                                                                      @Param("dtFrom") Instant dtFrom,
                                                                      @Param("dtTo") Instant dtTo);
}
