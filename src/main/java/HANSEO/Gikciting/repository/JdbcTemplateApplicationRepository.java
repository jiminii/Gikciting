package HANSEO.Gikciting.repository;

import HANSEO.Gikciting.domain.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JdbcTemplateApplicationRepository implements ApplicationRepository{

    final private JdbcTemplate jdbcTemplate;

    public JdbcTemplateApplicationRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Application save(Application application) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("user_application").usingGeneratedKeyColumns("num");

        Map<String,Object> parameters = new HashMap<>();
        parameters.put("name",application.getName());
        parameters.put("file",application.getFile());
        parameters.put("studentNum",application.getStudentNum());
        parameters.put("password",application.getPassword());
        parameters.put("major",application.getMajor());
        parameters.put("confirm",application.getConfirm());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        application.setId(key.longValue());

        return application;
    }

    @Override
    public List<Application> findAll() {
        Integer confirm = 0;
        //confirm이 0인, 승인 되지 않은 것만 반환.
        return jdbcTemplate.query("select * from user_application where confirm=?;", applicationRowMapper(),confirm);
    }

    @Override
    public Optional<Application> findById(Long id) {
        List<Application> result = jdbcTemplate.query("select * from user_application where num=?;", applicationRowMapper(),id);
        return result.stream().findAny();
    }

    @Override
    public Optional<Application> findByStudentNum(String StudentNum) {
        List<Application> result = jdbcTemplate.query("select * from user_application where studentNum=?;", applicationRowMapper(),StudentNum);
        return result.stream().findAny();
    }

    @Override
    public void updateConfirm(Application application) {
        int confirm =1;
        jdbcTemplate.update("update user_application set confirm=? where num=?;",confirm,application.getId());

    }

    private RowMapper<Application> applicationRowMapper(){
        return (rs, rowNum) -> {
            Application application = new Application();

            application.setId(rs.getLong("num"));
            application.setName(rs.getString("name"));
            application.setFile(rs.getString("file"));
            application.setPassword(rs.getString("password"));
            application.setStudentNum(rs.getString("studentNum"));
            application.setMajor(rs.getString("major"));
            application.setConfirm(rs.getInt("confirm"));

            return application;
        };
    }
}
