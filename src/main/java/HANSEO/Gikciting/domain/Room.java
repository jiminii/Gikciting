package HANSEO.Gikciting.domain;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Room {
        private Long id;            //회원 번호
        private Long roomNum;       //방 번호
        private String studentNum;  //학번
        private Long applicationId; //신청서 번호
}
