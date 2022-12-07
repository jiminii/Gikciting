package HANSEO.Gikciting.controller;

import HANSEO.Gikciting.domain.Member;
import HANSEO.Gikciting.domain.Room;
import HANSEO.Gikciting.domain.RoomApplication;
import HANSEO.Gikciting.service.MemberService;
import HANSEO.Gikciting.service.RoomApplicationService;
import HANSEO.Gikciting.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RoomController {
    private final MemberService memberService;
    private final RoomApplicationService applicationService;
    private final RoomService roomService;
    public RoomController(RoomApplicationService applicationService, MemberService memberService, RoomService roomService){
        this.applicationService = applicationService;
        this.memberService = memberService;
        this.roomService = roomService;
    }

    //RoomApplications에서 승인 시
    @GetMapping("insertRooms")
    public String createMember(@RequestParam("id") int id, Model model) {
        RoomApplication application = applicationService.findOne((long)id).get(); //신청서 가져오기

        //user_applicationm의 confirm 컬럼을 1로 변경
        applicationService.confirm(application);
        List<RoomApplication> applications = applicationService.findApplications();
        model.addAttribute("applications",applications);
        //신청서에 있는 학번으로 멤버 객체
        Member member= memberService.findOne(application.getStudentNum()).get();
        model.addAttribute("member",member);

        //신청서 작성한 member의 RoomNum Update
        memberService.updateRoomNum(member,application.getRoomNum());

        //같은 roomNum을 가진 tuple이 4개 미만이라면 삽입 아니면 return
        int cnt = roomService.howManyMember(application.getRoomNum());

        if(cnt<4){
            Room room = new Room();
            room.setRoomNum(application.getRoomNum());
            room.setStudentNum(application.getStudentNum());
            room.setApplicationId(application.getId());
            //Id(회원 번호는 AutoIncrement)
            roomService.insertRoom(room);
        }
        else{
            System.out.println("회원 삽입 실패 error : 이미 4명이 상이 예정된 방입니다.");
        }
        return "RoomApplications";
    }
}
