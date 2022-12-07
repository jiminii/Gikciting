package HANSEO.Gikciting.repository;

import HANSEO.Gikciting.domain.RoomApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcTemplateRoomApplicationRepository implements RoomApplicationRepository{
    final private JdbcTemplate jdbcTemplate;

    public JdbcTemplateRoomApplicationRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public RoomApplication save(RoomApplication roomApplication) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("room_application").usingGeneratedKeyColumns("id");

        Map<String,Object> parameters = new HashMap<>();
        parameters.put("name",roomApplication.getName());
        parameters.put("studentNum",roomApplication.getStudentNum());
        parameters.put("major",roomApplication.getMajor());
        parameters.put("roomNum",roomApplication.getRoomNum());
        parameters.put("confirm",roomApplication.getConfirm());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        roomApplication.setId(key.longValue());

        return roomApplication;
    }

    @Override
    public List<RoomApplication> findAll() {
        return jdbcTemplate.query("select * from room_application where confirm=0;", roomApplicationRowMapper());
    }

    @Override
    public void updateConfirm(RoomApplication application) {
        int confirm =1;
        jdbcTemplate.update("update room_application set confirm=? where id=?;",confirm,application.getId());
    }
    @Override
    public Optional<RoomApplication> findById(Long id) {
        List<RoomApplication> result = jdbcTemplate.query("select * from room_application where id=?;", roomApplicationRowMapper(),id);
        return result.stream().findAny();
    }

    @Override
    public Optional<RoomApplication> findByStudentNum(String StudentNum) {
        List<RoomApplication> result = jdbcTemplate.query("select * from room_application where studentNum=?;", roomApplicationRowMapper(),StudentNum);
        return result.stream().findAny();
    }
    private RowMapper<RoomApplication> roomApplicationRowMapper(){
        return (rs, rowNum) -> {
            RoomApplication roomApplication = new RoomApplication();

            roomApplication.setId(rs.getLong("id"));
            roomApplication.setName(rs.getString("name"));
            roomApplication.setStudentNum(rs.getString("studentNum"));
            roomApplication.setMajor(rs.getString("major"));
            roomApplication.setRoomNum(rs.getLong("roomNum"));
            roomApplication.setConfirm(rs.getLong("confirm"));

            return roomApplication;
        };
    }
}
