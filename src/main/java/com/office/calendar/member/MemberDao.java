package com.office.calendar.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MemberDao {

    final private JdbcTemplate jdbcTemplate;

    @Autowired
    public MemberDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

    }

    public boolean isMember(String id) {

        String sql = "SELECT COUNT(*) FROM USER_MEMBER " +
                     "WHERE ID = ?";

        int result = -1;

        try {
            result = jdbcTemplate.queryForObject(sql, Integer.class, id);

        } catch (Exception e) {
            e.printStackTrace();

        }

        return (result > 0) ? true : false;

    }

    public int insertMember(MemberDto memberDto, String encodedPW) {

        String sql = """
                        INSERT INTO 
                           USER_MEMBER(ID, PW, MAIL, PHONE) 
                        VALUES(?, ?, ?, ?)
                    """;

        int result = -1;

        try {
            result = jdbcTemplate.update(sql,
                                            memberDto.getId(),
                                            encodedPW,
                                            memberDto.getMail(),
                                            memberDto.getPhone());

        } catch (Exception e) {
            e.printStackTrace();

        }

        return result;

    }

    public MemberDto selectMemberByID(String id) {

        String sql =
                """
                    SELECT * FROM USER_MEMBER 
                    WHERE ID = ?
                """;

        List<MemberDto> memberDtos = new ArrayList<>();

        try {

            RowMapper<MemberDto> rowMapper =
                    BeanPropertyRowMapper.newInstance(MemberDto.class);
            memberDtos = jdbcTemplate.query(sql, rowMapper, id);

        } catch (Exception e) {
            e.printStackTrace();

        }

        return memberDtos.size() > 0 ? memberDtos.get(0) : null;

    }

    public int updateMember(MemberDto memberDto, String encodedPW) {

        String sql =
                """
                    UPDATE 
                        USER_MEMBER 
                    SET 
                        PW = ?, 
                        MAIL = ?, 
                        PHONE = ?, 
                        MOD_DATE = NOW()
                    WHERE
                        NO = ?
                """;

        int result = -1;
        try {
            result = jdbcTemplate.update(sql,
                                            encodedPW,
                                            memberDto.getMail(),
                                            memberDto.getPhone(),
                                            memberDto.getNo());

        } catch (Exception e) {
            e.printStackTrace();

        }

        return result;

    }

    public MemberDto selectMemberByIdAndMail(MemberDto memberDto) {

        String sql =
                """
                    SELECT * FROM USER_MEMBER 
                    WHERE ID = ? AND MAIL = ?;
                """;

        List<MemberDto> memberDtos = new ArrayList<>();

        try {

            RowMapper<MemberDto> rowMapper =
                    BeanPropertyRowMapper.newInstance(MemberDto.class);
            memberDtos = jdbcTemplate.query(
                                            sql,
                                            rowMapper,
                                            memberDto.getId(),
                                            memberDto.getMail());

        } catch (Exception e) {
            e.printStackTrace();

        }

        return memberDtos.size() > 0 ? memberDtos.get(0) : null;

    }

    public int updatePassword(String id, String encode) {

        String sql =
                """
                    UPDATE 
                        USER_MEMBER 
                    SET
                        PW = ?, 
                        MOD_DATE = NOW() 
                    WHERE 
                        ID = ?
                """;

        int result = -1;
        try {
            result = jdbcTemplate.update(sql, encode, id);

        } catch (Exception e) {
            e.printStackTrace();

        }

        return result;

    }
}
