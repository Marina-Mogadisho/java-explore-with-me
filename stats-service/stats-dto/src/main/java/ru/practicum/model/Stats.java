package ru.practicum.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Класс статистики
 * хранит статистическую информацию о запросах к сервису.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stats {

    //Название сервиса
    //например: ewm-main-service
    private String app;

    //URI сервиса
    //например: /events/1
    private String uri;

    //Количество просмотров
    private Long hits;

}
