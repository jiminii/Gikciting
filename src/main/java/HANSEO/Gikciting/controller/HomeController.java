package HANSEO.Gikciting.controller;

import HANSEO.Gikciting.domain.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    //@GetMapping("/") 처음 localhost에 접속 하였을 때 호출
    public String Login(){
        return "Login";
    }
}
