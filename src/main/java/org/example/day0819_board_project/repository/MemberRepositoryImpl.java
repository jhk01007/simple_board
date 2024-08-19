package org.example.day0819_board_project.repository;

import org.example.day0819_board_project.domain.Member;
import org.example.day0819_board_project.exception.IdDuplicateException;
import org.example.day0819_board_project.model.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MemberRepositoryImpl implements MemberRepository {
    private MemberRepositoryImpl() {
    }

    private static MemberRepositoryImpl memberRepository = new MemberRepositoryImpl();

    public static MemberRepositoryImpl getInstance() {
        return memberRepository;
    }

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public int insert(Member member) {
        int result = 0;
        try {
            String sql = " INSERT INTO " +
                    " member(member_id, password) VALUES(?, ?) ";
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, member.getMemberId());
            ps.setString(2, member.getPassword());
            result = ps.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new IdDuplicateException("아이디가 중복됩니다.");
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            DBUtil.close(ps, conn);
        }
        return result;
    }

    @Override
    public List<Member> findAll() {
        List<Member> list = new ArrayList<>();
        try {
            String sql = " SELECT id, member_id, password FROM member";
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Member(
                        rs.getLong("id"),
                        rs.getString("member_id"),
                        rs.getString("password")));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = null;

        try {
            String sql = " SELECT id, member_id, password FROM member WHERE id=" + id;
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) { // 글번호 이상하면 없을수는 있음.
                member = new Member(
                        rs.getLong("id"),
                        rs.getString("member_id"),
                        rs.getString("password"));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            DBUtil.close(rs, ps, conn);
        }

        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByMemberId(String memberId) {
        Member member = null;

        try {
            String sql = " SELECT id, member_id, password FROM member WHERE member_id=?";
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, memberId);
            rs = ps.executeQuery();
            if (rs.next()) { // 글번호 이상하면 없을수는 있음.
                member = new Member(
                        rs.getLong("id"),
                        rs.getString("member_id"),
                        rs.getString("password"));
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            DBUtil.close(rs, ps, conn);
        }

        return Optional.ofNullable(member);
    }


}
