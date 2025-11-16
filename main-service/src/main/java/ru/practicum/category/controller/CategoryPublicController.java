package ru.practicum.category.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.service.CategoryService;
import ru.practicum.statslogger.StatsLogger;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryPublicController {

    private final CategoryService categoryService;
    private final StatsLogger statsLogger;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getCategories(@RequestParam(name = "from", defaultValue = "0") int from,
                                           @RequestParam(name = "size", defaultValue = "10") int size,
                                           HttpServletRequest request) {
        log.info("GET categories from={}, size={}", from, size);
        statsLogger.logIPAndPath(request);
        return categoryService.getCategories(from, size);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getCategory(@PathVariable("id") Long categoryId, HttpServletRequest request) {
        log.info("GET category id={}", categoryId);
        statsLogger.logIPAndPath(request);
        return categoryService.getCategory(categoryId);
    }

}
