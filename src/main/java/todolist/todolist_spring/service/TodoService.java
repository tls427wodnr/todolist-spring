package todolist.todolist_spring.service;

import todolist.todolist_spring.model.Todo;
import todolist.todolist_spring.model.TodoStatus;

import java.util.List;

public interface TodoService {
    Long create(String title, TodoStatus status);
    Todo get(Long id);
    List<Todo> list();
    void update(Long id, String title, TodoStatus status);
    void delete(Long id);
}
