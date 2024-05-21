package org.mjulikelion.messengerapplication.repository;

import org.mjulikelion.messengerapplication.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {
    boolean existsByEmail(String email);  //이메일로 멤버 존재 여부 확인
    Member findMemberByName(String name);   //이름으로 멤버 찾기
    Member findMemberByEmail(String email); //이메일로 멤버 찾기
}
