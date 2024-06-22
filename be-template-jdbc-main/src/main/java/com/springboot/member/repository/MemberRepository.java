package com.springboot.member.repository;

import com.springboot.member.entity.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
//CrudRepository 인터페이스 제공. 기능을 사용하기 위해 MemberRepository가 CrudRepository 상속
// <Member - 저장소가 관리할 엔티티 클래스, Long - 엔티티의 기본키로 @Id 가 붙은 멤버 변수 타입
public interface MemberRepository extends CrudRepository<Member, Long> {

    // find + by + sql 쿼리문에서 where절의 열명(where절 열의 조건이 되는 데이터)
    // 엔티티의 클래스의 멤버 변수 명을 적어야 함
    // 테이블에 이미 등록된 이메일 주소가 있는지 확인하는 용도
    // Optional<Member>타입으로 반환하는데
    // Optional은 값이 있을 수도 없을 수도 있음을 나타내고
    // Member은 반환할 회원 정보 객체를 나타냄
    public Optional<Member> findByEmail(String email);

}
