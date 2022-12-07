package HANSEO.Gikciting.repository;

import HANSEO.Gikciting.domain.Application;
import HANSEO.Gikciting.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member); //저장
    //Optional java8부터 지원 : null값일 경우 Optional객체로 감싼다
    Optional<Member> findByStudentNum(String StudentNum);
    void updatePassword(Member member, String password);

    void updateRoomNum(Member memner, Long roomNum);
    void delete(String studentNum);

}
