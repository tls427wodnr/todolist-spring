package todolist.todolist_spring.model;

import java.time.LocalDateTime;

public record Todo(
        Long id,
        String title,
        TodoStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
