package HANSEO.Gikciting.repository;

import HANSEO.Gikciting.domain.Room;

import java.util.List;
import java.util.Optional;

public interface RoomRepository {
    Room save(Room room); //저장
    //방 번호로 불러오기
    List<Room> findByRoomNum(Long roomNum);

    //방에 학번으로 방 번호 변경하기
    void updateRoomNum(Long roomNum, String studentNum);

    int countMember(Long roomNum);

    List<Room> findAll();
}
