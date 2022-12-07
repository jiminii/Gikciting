package HANSEO.Gikciting.domain;

import lombok.Data;

@Data  //lambok의 Data:  Getter, Setter 자동 주입
public class Application {
    private Long id;   //신청 번호
    private String name;   //작성자
    private String password; //신청 비밀번호
    private String studentNum; //학번
    private String major; //전공
    private String file; //신청서 캡처본
    private int confirm; //승인된 글인지
}
