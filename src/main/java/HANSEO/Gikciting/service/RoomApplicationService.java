package HANSEO.Gikciting.service;

import HANSEO.Gikciting.domain.Application;
import HANSEO.Gikciting.domain.RoomApplication;
import HANSEO.Gikciting.repository.ApplicationRepository;
import HANSEO.Gikciting.repository.RoomApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomApplicationService {
    private final RoomApplicationRepository roomApplicationRepository;
    public RoomApplicationService(RoomApplicationRepository roomApplicationRepository){
        this.roomApplicationRepository = roomApplicationRepository;
    }
    /*
     * 가입 신청
     * */
    public Long join(RoomApplication roomApplication){

        roomApplication.setConfirm(0L);
        roomApplicationRepository.save(roomApplication);
        return roomApplication.getId();
    }

    /*
     * 전체 조회
     * */
    public List<RoomApplication> findApplications(){
        return roomApplicationRepository.findAll();
    }
    /*
     * 하나 조회
     * */
    public Optional<RoomApplication> findOne(Long id){
        return roomApplicationRepository.findById(id);
    }

    public void confirm(RoomApplication application){ roomApplicationRepository.updateConfirm(application);}

}
