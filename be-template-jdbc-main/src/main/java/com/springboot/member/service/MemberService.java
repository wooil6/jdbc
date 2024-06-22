package com.springboot.member.service;

import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.member.entity.Member;
import com.springboot.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * V2
 * - 메서드 구현
 * - DI 적용
 */
@Service
public class MemberService {
    // MemberRepository를 이용해야하기에 생성자를 통해 id를 받음
    private MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member createMember(Member member) {
        // TODO should business logic
        // 등록하기 전에 이미 있는 이메일인지 확인
         verifyExistsEmail(member.getEmail());

        return memberRepository.save(member);

    }

    public Member updateMember(Member member) {
        // TODO should business logic
        // 존재하는 회원인지 확인
        Member findMember = findVerifiedMember(member.getMemberId());

        // 정보 업데이트
        // name과 phone 중에 하나가 null일 수 있음
        // Optional.ofNullable() - null값도 입력받을 수 있어서 null이더라도 다음 메서드 실행
        // .ifPresent() - Optional객체에 값이 존재하면 주어진 람다식 실행
        Optional.ofNullable(member.getName())
                .ifPresent(name -> findMember.setName(name));
        Optional.ofNullable(member.getPhone())
                .ifPresent(phone -> findMember.setPhone(phone));

        return memberRepository.save(findMember);
    }

    public Member findMember(long memberId) {
        // TODO should business logic

        return findVerifiedMember(memberId);
    }

    public List<Member> findMembers() {
        // TODO should business logic

        return (List<Member>) memberRepository.findAll();
    }

    public void deleteMember(long memberId) {
        // TODO should business logic
        Member findMember = findVerifiedMember(memberId);

        memberRepository.delete(findMember);

    }

    public void verifyExistsEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        // .isPresent() - Optional 객체가 값을 가지고 있는지 확인
        if(member.isPresent())
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
    }

    public Member findVerifiedMember(long memberId) {
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        // orElseThrow() - optional객체가 값을 가지고 있으면 값을 반환하고 없으면 예외처리
        Member findMember = optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }
}
