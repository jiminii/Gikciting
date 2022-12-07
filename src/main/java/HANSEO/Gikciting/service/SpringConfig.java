package HANSEO.Gikciting.service;

import HANSEO.Gikciting.domain.Room;
import HANSEO.Gikciting.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class SpringConfig {

    private DataSource dataSource;

    @Autowired
    public SpringConfig(DataSource dataSource){
        this.dataSource= dataSource;
    }
    @Bean
    public ApplicationService applicationService(){
        return new ApplicationService(applicationRepository());
    }
    @Bean
    public ApplicationRepository applicationRepository(){
        return new JdbcTemplateApplicationRepository(dataSource);
    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository());
    }
    @Bean
    public MemberRepository memberRepository(){
        return new JdbcTemplateMemberRepository(dataSource);
    }

    @Bean
    public RoomApplicationService roomApplicationService(){
        return new RoomApplicationService(roomApplicationRepository());
    }
    @Bean
    public RoomApplicationRepository roomApplicationRepository(){
        return new JdbcTemplateRoomApplicationRepository(dataSource);
    }
    @Bean
    public RoomService roomService(){
        return new RoomService(roomRepository());
    }
    @Bean
    public RoomRepository roomRepository(){
        return new JdbcTemplateRoomRepository(dataSource);
    }
}
