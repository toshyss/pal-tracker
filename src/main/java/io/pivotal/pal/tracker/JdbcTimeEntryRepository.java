package io.pivotal.pal.tracker;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private JdbcTemplate jdbcTemplate;
    private KeyHolder keyHolder;

    public JdbcTimeEntryRepository(MysqlDataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        keyHolder = new GeneratedKeyHolder();
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        //final String sql = "insert into time_entries(id, project_id, user_id, date, hours) values(?, ?, ?, ?, ?)";
        final String sql = "insert into time_entries(project_id, user_id, date, hours) values(?, ?, ?, ?)";
        //jdbcTemplate.update(sql, new Object[]{timeEntry.getId(), timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours()});
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
//            ps.setLong(1, keyHolder.getKey().longValue());
            ps.setLong(1, timeEntry.getProjectId());
            ps.setLong(2, timeEntry.getUserId());
            ps.setObject(3, timeEntry.getDate());
            ps.setInt(4, timeEntry.getHours());
            return ps;
        }, keyHolder);

        timeEntry.setId(keyHolder.getKey().longValue());

        return timeEntry;
    }

    @Override
    public TimeEntry find(long id) {
        final String sql = "select * from time_entries where id = ?";
        List<TimeEntry> entries = jdbcTemplate.query(
                sql,
                new Object[]{id},
                new BeanPropertyRowMapper<>(TimeEntry.class)
        );
        //Map<String, Object> map = jdbcTemplate.queryForMap(sql, id);
        if (entries.isEmpty()) {
            return null;
        }
        return entries.get(0);
    }

    @Override
    public List<TimeEntry> list() {
        final String sql = "select * from time_entries";
        List<TimeEntry> entries = jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(TimeEntry.class)
                );

        return entries;
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        final String sql = "update time_entries set project_id = ?, user_id = ?, date = ?, hours = ? where id = ?";

        int rows = jdbcTemplate.update(sql, new Object[]{timeEntry.getProjectId(), timeEntry.getUserId(), timeEntry.getDate(), timeEntry.getHours(), id});
        if (rows == 0) {
            return null;
        }
        timeEntry.setId(id);

        return timeEntry;
    }

    @Override
    public void delete(long id) {
        final String sql = "DELETE FROM time_entries where id = ?";
        jdbcTemplate.update(sql,
                new Object[]{id});
    }
}
