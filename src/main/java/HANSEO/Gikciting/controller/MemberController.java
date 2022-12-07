package HANSEO.Gikciting.controller;

import HANSEO.Gikciting.domain.Application;
import HANSEO.Gikciting.domain.Member;
import HANSEO.Gikciting.domain.Room;
import HANSEO.Gikciting.service.ApplicationService;
import HANSEO.Gikciting.service.MemberService;
import HANSEO.Gikciting.service.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;
    private final ApplicationService applicationService;
    private final RoomService roomService;
    public MemberController(ApplicationService applicationService, MemberService memberService,RoomService roomService){
        this.applicationService = applicationService;
        this.memberService = memberService;
        this.roomService=roomService;
    }

    //Applications에서 승인 시
    @GetMapping("createMember")
    public String createMember(@RequestParam("num") int id, Model model) {
        Application application = applicationService.findOne((long)id).get();

        //user_applicationm의 confirm 컬럼을 1로 변경
        applicationService.confirm(application);

        Member member = new Member();

        member.setName(application.getName());
        member.setStudentNum(application.getStudentNum());
        member.setPassword(application.getPassword());
        member.setMajor(application.getMajor());

        memberService.join(member);

        return "redirect:/";
    }

    //Login 시
    @PostMapping("/MemberNew")
    public String create(LoginForm form,Model model){

        if(memberService.findOne(form.getStudentNum()).isEmpty()) {
            System.out.println("error : 일치하는 id없음");
            return "redirect:/"; //회원 가입 후 Login으로 이동
        }
        Member member=memberService.findOne(form.getStudentNum()).orElse(null);

        /*
        System.out.println(member.getPassword());
        System.out.println(form.getPassword());
        System.out.println(!member.getPassword().equals(form.getPassword()));
        */

        if(!member.getPassword().equals(form.getPassword())|| member.getPassword()==null){
            //error페이지를 만들어 browser에 출력해야한다. 하지만..
            System.out.println("error : id, password다름");
            return "redirect:/"; //회원 가입 후 Login으로 이동
        }
        else {
            model.addAttribute("member",member);

            return "Index"; //로그인 후 Index로 이동
        }
    }

    //로고 클릭 시 Index로 이동
    @GetMapping("/index")
    public String index(@RequestParam("studentNum") String studentNum, Model model){
        Member member = memberService.findOne(studentNum).get();

        model.addAttribute("member",member);
        return "Index";
    }
    // Logout 시
    @GetMapping("/LogoutMember")
    public String logout(){
        //localhost:8080으로 이동 -> Login을 보여준다
        return "redirect:/";
    }

    //설정 클릭 시
    @GetMapping("/settings")
    public String Setting(@RequestParam("studentNum") String studentNum, Model model){
        Member member = memberService.findOne(studentNum).get();

        model.addAttribute("member",member);
        return "Settings";
    }

    //방 신청 현황 클릭 시
    @GetMapping("/ShowRooms")
    public String createForm(@RequestParam("studentNum") String studentNum,Model model){

        Member member = memberService.findOne(studentNum).get();
        List<Room> rooms = roomService.findAll();

        model.addAttribute("member",member);
        model.addAttribute("rooms",rooms);
        return "Dormitory_index";
    }

    //Settings -> 비밀번호 변경 클릭 시
    @PostMapping("/updatePassword")
    public String updatePassword(UpdatePasswordForm form,Model model){

        Member member = memberService.findOne(form.getStudentNum()).get();

        model.addAttribute("member",member);

        memberService.updatePassword(member,form.getPassword());

        return "settings";
    }

    // Settings -> 회원 탈퇴 클릭 시
    @GetMapping("deleteMember")
    public String deleteMember(@RequestParam("studentNum") String studentNum, Model model){
        Member member = memberService.findOne(studentNum).get();

        memberService.deleteMember(studentNum);

        return "Login";
    }
}
