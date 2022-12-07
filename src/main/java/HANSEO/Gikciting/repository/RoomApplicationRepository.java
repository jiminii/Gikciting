package HANSEO.Gikciting.repository;

import HANSEO.Gikciting.domain.Application;
import HANSEO.Gikciting.domain.RoomApplication;

import java.util.List;
import java.util.Optional;

public interface RoomApplicationRepository {
    RoomApplication save(RoomApplication roomApplication); //저장
    List<RoomApplication> findAll();               //모두 불러오기
    void updateConfirm(RoomApplication application);

    //Optional java8부터 지원 : null값일 경우 Optional객체로 감싼다
    Optional<RoomApplication> findById(Long id);    //PK(id)로 하나 불러오기
    Optional<RoomApplication> findByStudentNum(String StudentNum);

}
