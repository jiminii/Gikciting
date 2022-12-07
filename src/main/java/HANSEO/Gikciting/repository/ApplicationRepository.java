package HANSEO.Gikciting.repository;

import HANSEO.Gikciting.domain.Application;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository {
    Application save(Application application); //저장
    List<Application> findAll();               //모두 불러오기
    //Optional java8부터 지원 : null값일 경우 Optional객체로 감싼다
    Optional<Application> findById(Long id);    //PK(id)로 하나 불러오기
    Optional<Application> findByStudentNum(String StudentNum);


    void updateConfirm(Application application);
}