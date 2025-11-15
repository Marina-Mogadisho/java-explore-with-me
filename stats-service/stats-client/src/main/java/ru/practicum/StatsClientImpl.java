package ru.practicum;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.practicum.dto.HitDto;
import ru.practicum.dto.StatsDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Класс StatsClientImpl — клиент для взаимодействия с удалённым сервером статистики, отправляя данные о "хитах"
 * и получая статистику с помощью REST-запросов через RestTemplate.
 * Он инкапсулирует детали HTTP-запросов и форматирования, предоставляя простой API.
 */
@Component
public class StatsClientImpl implements StatsClient {

    // Поле для HTTP-клиента RestTemplate, который используется для выполнения REST-запросов.
    private final RestTemplate restTemplate;
    private static final String HIT_ENDPOINT = "/hit";
    private static final String STATS_ENDPOINT = "/stats";
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern(HitDto.DATE_FORMAT_PATTERN);
    private final String baseUrl;


    @Autowired
    public StatsClientImpl(@Value("${stats-server.url}") String statsServerUrl) {
        this.restTemplate = new RestTemplate();
        this.baseUrl = statsServerUrl;
    }

    @Override
    public HitDto addHit(String app, String uri, String ip, LocalDateTime timestamp) {
        /*
        Метод создаёт объект с информацией о событии (хите), отправляет его на сервер через POST-запрос,
         получает и возвращает ответ от сервера в виде объекта того же типа.
         */
        // postForObject — это метод, который отправляет HTTP POST-запрос на указанный URL
        // и получает ответ, автоматически преобразуя его в нужный тип.
        /*
        baseUrl + HIT_ENDPOINT, Формируется адрес, куда отправлять запрос.
       – baseUrl — базовый адрес сервера
       – HIT_ENDPOINT — строка "/hit"
       В итоге — URL для запроса например "http://example.com/hit"

       Создаётся объект HitDto с параметрами, которые пришли в метод.
       Этот объект будет тело запроса (данные, которые мы отправляем на сервер в POST).

       HitDto.class - параметр, который говорит, в какой класс преобразовать ответ от сервера.
       Ожидается, что сервер вернёт JSON-объект, подходящий под класс HitDto,
       и restTemplate автоматически превратит ответ в объект HitDto.
         */
        return restTemplate.postForObject(baseUrl + HIT_ENDPOINT,

                new HitDto(app, uri, ip, timestamp),
                HitDto.class);
    }

    @Override
    public List<StatsDto> getStats(LocalDateTime start, LocalDateTime end, List<String> uris, boolean unique) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(STATS_ENDPOINT)
                .queryParam("start", start.format(dtf))
                .queryParam("end", end.format(dtf))
                .queryParam("uris", uris)
                .queryParam("unique", unique);

        String resultUrl = builder.build().toUriString();

        StatsDto[] statsArray = restTemplate.getForObject(baseUrl + resultUrl, StatsDto[].class);
        if (statsArray != null) {
            return Arrays.asList(statsArray);
        } else {
            return new ArrayList<>();
        }
    }
}

