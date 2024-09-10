package com.sparta.basicspringsession.service;

import com.sparta.basicspringsession.dto.*;
import com.sparta.basicspringsession.entity.Member;
import com.sparta.basicspringsession.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    @Transactional
    public MemberSaveResponseDto saveMember(MemberSaveRequestDto requestDto) {
        Member member = new Member(requestDto.getName());
        Member savedMember = memberRepository.save(member);
        return new MemberSaveResponseDto(savedMember.getId(), savedMember.getName());
    }

    public List<MemberSimpleResponseDto> getMembers() {
        List<Member> members = memberRepository.findAll();
        List<MemberSimpleResponseDto> membersDto = new ArrayList<>();
        for (Member member : members) {
            MemberSimpleResponseDto memberSimpleResponseDto = new MemberSimpleResponseDto(member.getName());
            membersDto.add(memberSimpleResponseDto);
        }
        return membersDto;
    }

    public MemberDetailResponseDto getMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(()->new NullPointerException("멤버가 존재하지 않습니다."));
        return new MemberDetailResponseDto(member.getId(), member.getName());
    }

    @Transactional
    public MemberUpdateResponseDto updateMember(Long memberId, MemberUpdateRequestDto requestDto) {
        Member member = memberRepository.findById(memberId).orElseThrow(()->new NullPointerException("멤버가 존재하지 않습니다."));
        member.update(requestDto.getName());
        return new MemberUpdateResponseDto(member.getId(), member.getName());
    }

    public void deleteMember(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow(()->new NullPointerException("멤버가 존재하지 않습니다."));
        memberRepository.delete(member);
    }
}
