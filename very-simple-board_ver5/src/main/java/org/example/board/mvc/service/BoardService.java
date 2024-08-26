package org.example.board.mvc.service;



import org.example.board.domain.Board;
import org.example.board.exception.BoardNotFoundException;
import org.example.board.mvc.repository.BoardRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// SQL 실행은 리포지토리가 담당하고 나는 뭘 검사하고 판단하고 조작해내는 비즈니스 로직
@Service
public class BoardService {
    private static final int COUNT_PER_PAGE = 10; // 한 페이지당 보여줄 글의 갯수
    private BoardRepository repo;

    public BoardService(BoardRepository repo) {
        this.repo = repo;
    }

    // 글 읽기 수행할 때 작성자와 읽는 사용자가 일치하는지 검사해서 조회수 증가 update 여부를 판단하거나
    // 이미 이 글을 읽은 사용자는 조회수가 중복해서 증가하지 않도록 검사하거나 하는 로직을 수행하는 메서드
    // public Board read(int id, int loginId) throws SQLException
    // 지금은 처리 X
    public Board read(Long id) {
        Optional<Board> board = repo.selectOne(id);
        if(board.isEmpty()) throw new BoardNotFoundException("해당 글을 찾을 수 없습니다.");
        return board.get();
    }

    public int write(Board board) {
        return repo.insert(board);
    }

    public int edit(Board board) {
        return repo.update(board);
    }

    public int delete(Long id) {
        return repo.delete(id);
    }

    public int addReadCount(Board board) {
        board.setReadCount(board.getReadCount() + 1);
        return repo.update(board);
    }

    // 현재 보고자하는 페이지 정보가 들어왔을 때, 실제 해당 페이지에 보여져야 하는 List<Board>를 포함해서 페이지가 총 몇개 필요하고,
    // 하단 페이지 링크는 1-10 or 11~20같은 페이지 구간 계산
    public Map<String, Object> getBoards(int page) {
        int totalCount = repo.selectCount(); // 총 게시글의 갯수;
        int totalPageCount = totalCount % COUNT_PER_PAGE == 0 ? // 예를들어 글이 총 20개라면 2페이지가 필요하지만
                totalCount / COUNT_PER_PAGE : totalCount / COUNT_PER_PAGE + 1;  // 21~29개라면 3페이지가 필요함(그래서 + 1)

        int startPage = (page - 1) / 10 * 10 + 1; // 현재 페이지가 11, 12, 13, ... , 20이었을 때 -1해서 10~19로 바꾸고 /10*10 하면 11, 12, ..., 19 다 동일하게 10으로 동일됨
        int endPage = startPage + 9; // 한화면에 보여줄 페이지의 수는 총 10개 (예: 11~20)

        if(totalPageCount < endPage) endPage = totalPageCount; // 만약 총 페이지의 갯수가 계산된 마지막 페이지 수보다 많다면 총 페이지의 갯수가 마지막 페이지가 됨

        List<Board> boards = repo.selectPageList((page - 1) * COUNT_PER_PAGE, COUNT_PER_PAGE); // 0페이지 0~9번 인덱스 ,1페이지 10~19번 인덱스

        Map<String, Object> resultData = new HashMap<>();
        resultData.put("page", page);
        resultData.put("startPage", startPage);
        resultData.put("endPage", endPage);
        resultData.put("totalPageCount", totalPageCount);
        resultData.put("boards", boards);

        return resultData;
    }
}
