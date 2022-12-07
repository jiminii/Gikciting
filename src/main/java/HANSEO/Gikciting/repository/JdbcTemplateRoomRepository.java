package HANSEO.Gikciting.repository;

import HANSEO.Gikciting.domain.Room;
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


@Repository
public class JdbcTemplateRoomRepository implements RoomRepository{
    final private JdbcTemplate jdbcTemplate;

    public JdbcTemplateRoomRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Room save(Room room) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("room");

        Map<String,Object> parameters = new HashMap<>();
        parameters.put("studentNum",room.getStudentNum());
        parameters.put("applicationId",room.getApplicationId());
        parameters.put("roomNum",room.getRoomNum());

        jdbcInsert.execute(new MapSqlParameterSource(parameters));

        return room;
    }

    @Override
    public List<Room> findByRoomNum(Long roomNum) {
        return jdbcTemplate.query("select * from room where roomNum=?;", roomRowMapper(),roomNum);
    }
    public List<Room> findAll(){
        return jdbcTemplate.query("select * from room;", roomRowMapper());
    }
    @Override
    public void updateRoomNum(Long roomNum, String studentNum) {
        jdbcTemplate.update("update room set roomNum=? where studentNum=?;",roomNum,studentNum);
    }

    public int countMember(Long roomNum){
        List<Room> result= jdbcTemplate.query("select * from room where roomNum=?;",roomRowMapper(),roomNum);

        return result.size();
    }
    private RowMapper<Room> roomRowMapper(){
        return (rs, rowNum) -> {
            Room room = new Room();

            room.setId(rs.getLong("id"));
            room.setRoomNum(rs.getLong("roomNum"));
            room.setApplicationId(rs.getLong("applicationId"));
            room.setStudentNum(rs.getString("studentNum"));

            return room;
        };
    }
}
