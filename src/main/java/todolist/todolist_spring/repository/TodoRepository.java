package todolist.todolist_spring.repository;

import todolist.todolist_spring.model.Todo;
import todolist.todolist_spring.model.TodoStatus;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {
    Long insert(String title, TodoStatus status);
    Optional<Todo> findById(Long id);
    List<Todo> findAll();
    int update(Long id, String title, TodoStatus status);
    int delete(Long id);
}