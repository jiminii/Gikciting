package HANSEO.Gikciting.controller;

import HANSEO.Gikciting.domain.Application;
import HANSEO.Gikciting.domain.Member;
import HANSEO.Gikciting.domain.RoomApplication;
import HANSEO.Gikciting.service.ApplicationService;
import HANSEO.Gikciting.service.MemberService;
import HANSEO.Gikciting.service.RoomApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
//@Controller: Spring Bean에 ApplicationController 객체 생성하여 저장한다.
public class ApplicationController{

    // 회원가입 신청 Application
    private final ApplicationService applicationService;
    private final RoomApplicationService roomApplicationService;
    private final MemberService  memberService;
    @Autowired
    //Controller annotaion에 의해 Controller객체가 Bean에 저장될 때 호출되는 생성자에서 객체를 만든다.
    //Spring은 싱글톤으로 다루어지므로 객체가 하나만 만들어진다.
    //spring bean에 등록되어있는 service class의 객체를 만들어준다. <- 의존성 주입
    public ApplicationController(ApplicationService applicationService,MemberService memberService,
                                 RoomApplicationService roomApplicationService){
        this.applicationService = applicationService;
        this.memberService =  memberService;
        this.roomApplicationService = roomApplicationService;
    }

    //회원가입 신청 클릭 시
    @GetMapping("/ApplicationNew")
    public String createForm(){
        return "Join";
    }

    //회원가입 제출 시
    @PostMapping("/ApplicationNew")
    public String create(ApplicationForm form){
        Application application = new Application();

        if(form.getStudentNum().isEmpty()) {
            System.out.println("모두 입력해주세요.");
        }

        application.setName(form.getName());
        application.setStudentNum(form.getStudentNum());
        application.setPassword(form.getPassword());
        application.setMajor(form.getMajor());

        String url = "RESOURCES/images/" //prefix
                                    +form.getFile();

        application.setFile(url);

        applicationService.join(application);

        return "redirect:/"; //회원 가입 후 Login으로 이동
    }
    //설정 -> 회원 신청 목록 클릭 시
    @GetMapping("applications")
    public String list(@RequestParam("studentNum") String studentNum,Model model){
        Member member = memberService.findOne(studentNum).get();
        model.addAttribute("member",member);

        List<Application> applications = applicationService.findApplications();
        model.addAttribute("applications",applications);

        return "Applications";
    }
    //Romm Application Controller
    //방 신청할 시
    @PostMapping("/ApplyRoom")
    public String apply(RoomForm form,Model model){
        RoomApplication application = new RoomApplication();

        Member member =  memberService.findOne(form.getStudentNum()).get();

        application.setName(member.getName());
        application.setStudentNum(member.getStudentNum());
        application.setRoomNum(form.getRoom());
        application.setMajor(member.getMajor());

        roomApplicationService.join(application);
        model.addAttribute("member",member);

        return "Index";
    }

    //설정 -> 방 신청목록 클릭 시
    @GetMapping("roomApplications")
    public String Rooms(@RequestParam("studentNum") String studentNum ,Model model){
        Member member = memberService.findOne(studentNum).get();
        model.addAttribute("member",member);

        List<RoomApplication> applications = roomApplicationService.findApplications();
        model.addAttribute("applications",applications);

        /*
        *
        * List<Rooms> rooms = roomsService.findRooms();
        * model.addAttribute("rooms",room);
        *
        * */

        return "RoomApplications";

    }

}