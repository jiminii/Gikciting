package HANSEO.Gikciting.repository;

import HANSEO.Gikciting.domain.Member;
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
public class JdbcTemplateMemberRepository implements MemberRepository{

    final private JdbcTemplate jdbcTemplate;

    public JdbcTemplateMemberRepository(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member");

        Map<String,Object> parameters = new HashMap<>();
        parameters.put("name",member.getName());
        parameters.put("studentNum",member.getStudentNum());
        parameters.put("password",member.getPassword());
        parameters.put("major",member.getMajor());
        parameters.put("roomNum",member.getRoomNum());
        parameters.put("authority",member.getAuthority());

        System.out.println(parameters);

        jdbcInsert.execute(new MapSqlParameterSource(parameters));

        return member;
    }

    @Override
    public Optional<Member> findByStudentNum(String StudentNum) {
        List<Member> result = jdbcTemplate.query("select * from member where studentNum=?;", memberRowMapper(),StudentNum);
        return result.stream().findAny();
    }

    @Override
    public void updatePassword(Member member,String password) {
        //Spring jdbcTemplate에서 제공하는 update함수 사용
        jdbcTemplate.update("update member set password=? where studentNum=?;",password,member.getStudentNum());
    }

    @Override
    public void updateRoomNum(Member member, Long roomNum) {
        jdbcTemplate.update("update member set roomNum=? where studentNum=?;",roomNum,member.getStudentNum());

    }

    @Override
    public void delete(String studentNum) {
        jdbcTemplate.update("delete from member where studentNum=?;",studentNum);

    }

    private RowMapper<Member> memberRowMapper(){
        return (rs, rowNum) -> {
            Member member = new Member();

            member.setName(rs.getString("name"));
            member.setPassword(rs.getString("password"));
            member.setStudentNum(rs.getString("studentNum"));
            member.setMajor(rs.getString("major"));
            member.setRoomNum(rs.getLong("roomNum"));
            member.setAuthority(rs.getInt("authority"));

            return member;
        };
    }
}
