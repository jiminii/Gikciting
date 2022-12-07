package HANSEO.Gikciting.service;

import HANSEO.Gikciting.domain.Application;
import HANSEO.Gikciting.repository.ApplicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//spring bean에 service 클래스로 등록
@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public ApplicationService(ApplicationRepository applicationRepository){
        this.applicationRepository = applicationRepository;
    }
    /*
    * 회원 가입
    * */
    public Long join(Application application){

        application.setConfirm(0);
        applicationRepository.save(application);
        return application.getId();
    }


    /*
    * 전체 조회
    * */
    public List<Application> findApplications(){
        return applicationRepository.findAll();
    }
    /*
    * 하나 조회
    * */
    public Optional<Application> findOne(Long id){
        return applicationRepository.findById(id);
    }
    public void confirm(Application application){ applicationRepository.updateConfirm(application);}
}
