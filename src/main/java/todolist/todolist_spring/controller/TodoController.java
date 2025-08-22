package todolist.todolist_spring.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import todolist.todolist_spring.model.Todo;
import todolist.todolist_spring.model.TodoStatus;
import todolist.todolist_spring.service.TodoService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    public record CreateTodoReq(@NotBlank String title, TodoStatus status) {}
    public record UpdateTodoReq(@NotBlank String title, TodoStatus status) {}

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Long> create(@Valid @RequestBody CreateTodoReq req) {
        Long id = service.create(req.title(), req.status());
        return Map.of("id", id);
    }

    @GetMapping("/{id}")
    public Todo get(@PathVariable Long id) { return service.get(id); }

    @GetMapping
    public List<Todo> list() { return service.list(); }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @Valid @RequestBody UpdateTodoReq req) {
        service.update(id, req.title(), req.status());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}