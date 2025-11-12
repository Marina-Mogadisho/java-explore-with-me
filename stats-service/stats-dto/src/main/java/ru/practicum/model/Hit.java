package ru.practicum.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Objects;


/**
 * Класс Hit используется для анализа запросов к приложению
 * хранит информацию о каждом запросе пользователя к сервису
 */

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "hits")
public class Hit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    //Идентификатор сервиса для которого записывается информация
    //например: ewm-main-service
    @NotBlank
    @Column(name = "app")
    private String app;

    //URI для которого был осуществлен запрос, универсальный идентификатор ресурса
    //например: /events/1
    @NotBlank
    @Column(name = "uri")
    private String uri;

    //IP-адрес пользователя, осуществившего запрос
    //например: 192.163.0.1
    @NotBlank
    @Column(name = "ip")
    private String ip;

    //Дата и время, когда был совершен запрос к эндпоинту (в формате "yyyy-MM-dd HH:mm:ss")
    // пример: 2022-09-06 11:00:23
    @Column(name = "created")
    private LocalDateTime timestamp;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o)
                .getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this)
                .getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Hit hit = (Hit) o;
        return getId() != null && Objects.equals(getId(), hit.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy
                ? ((HibernateProxy) this)
                .getHibernateLazyInitializer()
                .getPersistentClass().hashCode()
                : getClass().hashCode();
    }
}

