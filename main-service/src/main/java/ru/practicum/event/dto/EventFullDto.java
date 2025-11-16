package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.event.EventStates;
import ru.practicum.dto.HitDto;
import ru.practicum.user.dto.UserShortDto;

import java.time.LocalDateTime;

/**
 * DTO for {@link ru.practicum.event.Event}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventFullDto {

    private Long id;

    @NotNull
    private CategoryDto category;

    @NotNull
    private UserShortDto initiator;

    @NotBlank
    @Size(max = 120)
    private String title;

    @NotBlank
    @Size(min = 20, max = 2000)
    private String annotation;

    @NotBlank
    @Size(max = 7000)
    private String description;

    @NotNull
    @JsonFormat(pattern = HitDto.DATE_FORMAT_PATTERN)
    private LocalDateTime eventDate;

    private LocationDto location = new LocationDto();

    private Boolean paid;

    private Integer participantLimit;

    @NotNull
    @JsonFormat(pattern = HitDto.DATE_FORMAT_PATTERN)
    private LocalDateTime createdOn;

    @NotNull
    @JsonFormat(pattern = HitDto.DATE_FORMAT_PATTERN)
    private LocalDateTime publishedOn;

    private Boolean requestModeration;

    private EventStates state;

    private Long views;

    //Количество одобренных заявок на участие в данном событии
    private long confirmedRequests;
}