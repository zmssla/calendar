package com.office.calendar.member.security;

import com.office.calendar.member.MemberDao;
import com.office.calendar.member.MemberDto;
import com.office.calendar.member.jpa.MemberEntity;
import com.office.calendar.member.jpa.MemberRepository;
import com.office.calendar.member.mapper.MemberMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class MemberDetailsService implements UserDetailsService {

    final private MemberRepository memberRepository;

    @Autowired
    public MemberDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loadUserByUsername()");

        Optional<MemberEntity> optionalMember = memberRepository.findByMemId(username);
        if (optionalMember.isPresent()) {
            MemberEntity findedMemberEntity = optionalMember.get();

            return User.builder()
                    .username(findedMemberEntity.getMemId())
                    .password(findedMemberEntity.getMemPw())
                    .roles(findedMemberEntity.getAuthorityEntity().getAuthRoleName())  // PRE_USER, USER
                    .build();

        }

        return null;
    }

}
