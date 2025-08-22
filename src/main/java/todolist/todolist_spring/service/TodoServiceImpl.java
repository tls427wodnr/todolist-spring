package todolist.todolist_spring.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import todolist.todolist_spring.model.Todo;
import todolist.todolist_spring.model.TodoStatus;
import todolist.todolist_spring.repository.TodoRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository repo;

    public TodoServiceImpl(TodoRepository repo) {
        this.repo = repo;
    }

    @Transactional
    @Override
    public Long create(String title, TodoStatus status) {
        return repo.insert(title, status == null ? TodoStatus.TODO : status);
    }

    @Transactional(readOnly = true)
    @Override
    public Todo get(Long id) {
        return repo.findById(id).orElseThrow(() -> new NoSuchElementException("todo not found: " + id));
    }

    @Transactional(readOnly = true)
    @Override
    public List<Todo> list() {
        return repo.findAll();
    }

    @Transactional
    @Override
    public void update(Long id, String title, TodoStatus status) {
        int cnt = repo.update(id, title, status);
        if (cnt != 1) throw new NoSuchElementException("todo not found: " + id);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        int cnt = repo.delete(id);
        if (cnt != 1) throw new NoSuchElementException("todo not found: " + id);
    }
}
