package HANSEO.Gikciting.domain;

import lombok.Data;

@Data
public class Member {
    private String studentNum; //학번
    private String name;   //이름
    private String password; //신청 비밀번호
    private String major; //전공
    private Long roomNum; //신청할 방이름
    private int authority; //관리자 권한
}
