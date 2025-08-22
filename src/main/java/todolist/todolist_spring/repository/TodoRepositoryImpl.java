package todolist.todolist_spring.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import todolist.todolist_spring.model.Todo;
import todolist.todolist_spring.model.TodoStatus;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class TodoRepositoryImpl implements TodoRepository {

    private final JdbcTemplate jdbc;

    public TodoRepositoryImpl(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<Todo> ROW_MAPPER = new RowMapper<>() {
        @Override public Todo mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Todo(
                    rs.getLong("id"),
                    rs.getString("title"),
                    TodoStatus.valueOf(rs.getString("status")),
                    rs.getObject("created_at", LocalDateTime.class),
                    rs.getObject("updated_at", LocalDateTime.class)
            );
        }
    };

    @Override
    public Long insert(String title, TodoStatus status) {
        var insert = new SimpleJdbcInsert(jdbc)
                .withTableName("todos")
                .usingGeneratedKeyColumns("id")
                .usingColumns("title", "status");

        Number key = insert.executeAndReturnKey(Map.of(
                "title", title,
                "status", status.name()
        ));
        return key.longValue();
    }

    @Override
    public Optional<Todo> findById(Long id) {
        String sql = "select id,title,status,created_at,updated_at from todos where id=?";
        return jdbc.query(sql, ROW_MAPPER, id).stream().findFirst();
    }

    @Override
    public List<Todo> findAll() {
        String sql = "select id,title,status,created_at,updated_at from todos order by updated_at desc";
        return jdbc.query(sql, ROW_MAPPER);
    }

    @Override
    public int update(Long id, String title, TodoStatus status) {
        String sql = """
            update todos
               set title=?,
                   status=?,
                   updated_at=current_timestamp
             where id=?
        """;
        return jdbc.update(sql, title, status.name(), id);
    }

    @Override
    public int delete(Long id) {
        return jdbc.update("delete from todos where id=?", id);
    }
}