package HANSEO.Gikciting.domain;

import lombok.Data;

@Data
public class RoomApplication {
    private Long id;   //신청 번호 (pk)
    private String name;   //작성자
    private String studentNum; //학번
    private String major; //전공
    private Long roomNum; //방 번호
    private Long confirm;
}
