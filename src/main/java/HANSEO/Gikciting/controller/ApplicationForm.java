package HANSEO.Gikciting.controller;

import lombok.Data;

@Data
public class ApplicationForm {
    /*
    * join에서 입력 -> ApplicationNew로 데이터 이동 -> ApplicationForm에 담김
    * */
    private String name;   //작성자
    private String password; //신청 비밀번호
    private String major; //전공
    private String studentNum; //학번
    private String file; //신청서 캡처본
}
