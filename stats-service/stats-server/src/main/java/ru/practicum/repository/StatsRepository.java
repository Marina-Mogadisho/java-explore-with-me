package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.model.Hit;
import ru.practicum.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends JpaRepository<Hit, Long> {
    /**
     * Метод getAllHits
     * - Считает общее количество просмотров (hits) для каждого сочетания app (приложение) и uri (адрес).
     * Берёт данные за период между start и end. Группирует результаты по app и uri.
     * Возвращает список объектов Stats с количеством просмотров, отсортированный по убыванию количества просмотров.
     */
    @Query("select new ru.practicum.model.Stats(h.app, h.uri, COUNT(h.ip))" +
            " FROM Hit AS h where h.timestamp between :start and :end" +
            " GROUP BY h.app, h.uri ORDER BY COUNT(h.ip) DESC"
    )
    List<Stats> getAllHits(@Param("start") LocalDateTime start,
                           @Param("end") LocalDateTime end);

    /**
     * Метод getAllUniqueHits
     * - Считает количество уникальных просмотров (уникальных IP-адресов) для каждого сочетания app и uri.
     * Тоже за период от start до end.
     * Группирует результаты по app и uri.
     * Возвращает список объектов Stats с количеством просмотров,
     * отсортированный по убыванию количества просмотров, считает только уникальные ip.
     */
    @Query("select new ru.practicum.model.Stats(h.app, h.uri, COUNT(DISTINCT h.ip))" +
            " FROM Hit AS h where h.timestamp between :start and :end" +
            " GROUP BY h.app, h.uri ORDER BY COUNT(DISTINCT h.ip) DESC"
    )
    List<Stats> getAllUniqueHits(@Param("start") LocalDateTime start,
                                 @Param("end") LocalDateTime end);

    /**
     * Дополнительно фильтрует только по списку указанных URL-адресов (uriList).
     * То есть берёт статистику только для выбранных адресов.
     */
    @Query("select new ru.practicum.model.Stats(h.app, h.uri, COUNT(DISTINCT h.ip))" +
            " FROM Hit AS h where (h.timestamp between :start and :end) and h.uri IN :uriList" +
            " GROUP BY h.app, h.uri ORDER BY COUNT(DISTINCT h.ip) DESC"
    )
    List<Stats> getAllUniqueHitsByUris(@Param("start") LocalDateTime start,
                                       @Param("end") LocalDateTime end,
                                       @Param("uriList") List<String> uris);

    /**
     * Также фильтрует по списку URL из uriList.
     * Возвращает количество просмотров (не уникальных) только для выбранных адресов.
     */
    @Query("select new ru.practicum.model.Stats(h.app, h.uri, COUNT(h.ip))" +
            " FROM Hit AS h where (h.timestamp between :start and :end) and h.uri IN :uriList" +
            " GROUP BY h.app, h.uri ORDER BY COUNT(h.ip) DESC"
    )
    List<Stats> getAllHitsByUris(@Param("start") LocalDateTime start,
                                 @Param("end") LocalDateTime end,
                                 @Param("uriList") List<String> uris);
}
