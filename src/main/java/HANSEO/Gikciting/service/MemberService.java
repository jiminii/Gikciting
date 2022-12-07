package HANSEO.Gikciting.service;

import HANSEO.Gikciting.domain.Member;
import HANSEO.Gikciting.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*
     * 회원 가입
     * */
    public String join(Member member){
        member.setRoomNum(0L);
        member.setAuthority(0);

        memberRepository.save(member);

        return member.getStudentNum();
    }
    /*
     * 하나 조회
     * */
    public Optional<Member> findOne(String studentNum){
        return memberRepository.findByStudentNum(studentNum);
    }

    public void updatePassword(Member member, String password){
        memberRepository.updatePassword(member,password);
    }
    public void updateRoomNum(Member member, Long roomNum){
        memberRepository.updateRoomNum(member,roomNum);
    }
    public void deleteMember(String studentNum){
        memberRepository.delete(studentNum);
    }
}
