package org.example.board.mvc.repository;


import org.example.board.domain.Board;
import org.example.board.DBUtil;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BoardRepositoryMysql implements BoardRepository {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    @Override
    public int insert(Board board) {
        int result = 0;
        try {
            String sql = " INSERT INTO " +
                    " board(title ,writer ,content, created_at) VALUES(?,?,?,NOW()) ";
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, board.getTitle());
            ps.setString(2, board.getWriter());
            ps.setString(3, board.getContent());
            result = ps.executeUpdate();
        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }finally {
            DBUtil.close(ps, conn);
        }
        return result;
    }


//    @Override
//    public int update(Board board, String category) throws SQLException {
//        int result = 0;
//
//        try {
//            String sql = "UPDATE BOARD_TB SET ";
//            if("title".equals(category)){
//                sql += "TITLE = " + board.getTitle(); // ' 없어도 문자열 잘 들어가려나.....
//            }else if("writer".equals(category)){
//                sql += "WRITER = " + board.getWriter();
//            }else if("content".equals(category)){
//                sql += "CONTENT = " + board.getContent();
//            }
//            sql += " WHERE NO = " + board.getNo();
//            conn = DBUtil.getConnection();
//            ps = conn.prepareStatement(sql);
//            result = ps.executeUpdate();
//        }catch (SQLException ex){
//            System.out.println("update error");
//            throw ex;
//        }finally {
//            DBUtil.close(ps, conn);
//        }
//
//        return result;
//    }


    @Override
    public int update(Board board) {
        // 위의 update 버전은 수정하고싶은게 뭔지 정확히 찝어서 그것만 수정하기였고
        // 이 update는 수정하고 싶은게 여러개 이면 한꺼번에 수정하도록 값의 유무 체크해서 진행하는 버전
        int result = 0;

        try {
            String sql = "UPDATE board SET ";

            if(board.getTitle()!=null && board.getTitle().length()>0){ // 제목에 변경하고자 값이 확실히 있는지 체크해서
                sql += " TITLE='"+board.getTitle()+"', ";
            }
            if(board.getWriter()!=null && board.getWriter().length()>0){
                sql += " WRITER='"+board.getWriter()+"', ";
            }
            if(board.getContent()!=null && board.getContent().length()>0){
                sql += " CONTENT='"+board.getContent()+"', ";
            }
            if(board.getReadCount() != null && board.getReadCount() > 0)
                sql += " READ_COUNT='"+board.getReadCount()+"', ";

            sql = sql.substring(0, sql.length()-2);

            sql += " WHERE id = "+board.getId();
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            result = ps.executeUpdate();
        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }finally {
            DBUtil.close(ps, conn);
        }

        return result;
    }

    @Override
    public int delete(Long id) {
        int result = 0;
        try {
            String sql = "DELETE from board where id = ?";
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            result = ps.executeUpdate();
        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }finally {
            DBUtil.close(ps, conn);
        }
        return result;
    }

    @Override
    public List<Board> selectAll() {
        List<Board> list = new ArrayList<>();
        try {
            String sql = " SELECT id, title, writer, content, read_count, created_at FROM board";
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                list.add(makeBoardDTO(rs));
            }
        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }finally {
            DBUtil.close(rs, ps, conn);
        }
        return list;
    }

    @Override
    public Optional<Board> selectOne(Long id) {
        Board board = null;

        try {
            String sql = "SELECT id, title, writer,content, read_count, created_at FROM board WHERE id=?";
            conn = DBUtil.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            rs = ps.executeQuery();
            if(rs.next()){ // 글번호 이상하면 없을수는 있음.
                board = makeBoardDTO(rs);
            }
        }catch (SQLException ex){
            throw new RuntimeException(ex);
        }finally {
            DBUtil.close(rs, ps, conn);
        }

        return Optional.ofNullable(board);
    }

    private Board makeBoardDTO(ResultSet rs) throws SQLException {
        Board boardDTO = new Board();
        boardDTO.setId(rs.getLong("id"));
        boardDTO.setTitle(rs.getString("title"));
        boardDTO.setWriter(rs.getString("writer"));
        boardDTO.setContent(rs.getString("content"));
        boardDTO.setReadCount(rs.getInt("read_count"));
        boardDTO.setCreatedAt(rs.getString("created_at"));
        return boardDTO;
    }
}
