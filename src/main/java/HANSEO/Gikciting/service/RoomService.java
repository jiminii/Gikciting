package HANSEO.Gikciting.service;

import HANSEO.Gikciting.domain.Member;
import HANSEO.Gikciting.domain.Room;
import HANSEO.Gikciting.domain.RoomApplication;
import HANSEO.Gikciting.repository.RoomApplicationRepository;
import HANSEO.Gikciting.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    private final RoomRepository roomRepository;
    public RoomService(RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }
    public void insertRoom(Room room){
        roomRepository.save(room);
    }
    public List<Room> findByMembers(Long roomNum){
        return roomRepository.findByRoomNum(roomNum);
    }

    public void updateRoomNum(Long roomNum, String studentNum){
        roomRepository.updateRoomNum(roomNum, studentNum);
    }
    public int howManyMember(Long roomNum){
        return roomRepository.countMember(roomNum);
    }
    public List<Room> findAll(){
        return roomRepository.findAll();
    }

}
