package com.office.calendar.member.mapper;

import com.office.calendar.member.MemberDto;
import org.apache.ibatis.annotations.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.List;

@Mapper
public interface MemberMapper {

    //@Select("SELECT COUNT(*) FROM USER_MEMBER WHERE ID = #{id}")
    public boolean isMember(String id);

    //@Insert("INSERT INTO USER_MEMBER(ID, PW, MAIL, PHONE) VALUES(#{memberDto.id}, #{encodedPW}, #{memberDto.mail}, #{memberDto.phone})")
    public int insertMember(@Param("memberDto") MemberDto memberDto, @Param("encodedPW") String encodedPW);

    //@Select("SELECT * FROM USER_MEMBER WHERE ID = #{id}")
    public MemberDto selectMemberByID(String id);

    //@Update("UPDATE USER_MEMBER SET PW = #{encodedPW}, MAIL = #{memberDto.mail}, PHONE = #{memberDto.phone}, MOD_DATE = NOW() WHERE NO = #{memberDto.no}")
    public int updateMember(@Param("memberDto") MemberDto memberDto, @Param("encodedPW") String encodedPW);

    //@Select("SELECT * FROM USER_MEMBER WHERE ID = #{id} AND MAIL = #{mail}")
    public MemberDto selectMemberByIdAndMail(MemberDto memberDto);

    //@Update("UPDATE USER_MEMBER SET PW = #{encode}, MOD_DATE = NOW() WHERE ID = #{id}")
    public int updatePassword(@Param("id") String id, @Param("encode") String encode);
}
