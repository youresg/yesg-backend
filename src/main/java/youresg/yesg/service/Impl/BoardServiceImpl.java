package youresg.yesg.service.Impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youresg.yesg.domain.board.Board;
import youresg.yesg.domain.board.BoardRepository;
import youresg.yesg.domain.member.Member;
import youresg.yesg.domain.member.MemberRepository;
import youresg.yesg.dto.board.*;
import youresg.yesg.service.IBoardService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardServiceImpl implements IBoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<BoardDto> findAllBoards(){
        return boardRepository.findAll().stream()
                .map(BoardDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BoardDto> findAllBoardsBySearch(BoardSearchDto boardSearchDto){
        return boardRepository.findBySearch(boardSearchDto.getKeyword()).stream()
                .map(BoardDto:: toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BoardDto> findAllBoardsByHashtagId(String tagName){
        return boardRepository.findByHashtag(tagName).stream()
                .map(BoardDto::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BoardDto> findBoardById(Long boardId){
        Optional<Board>optionalBoard = boardRepository.findById(boardId);
        return optionalBoard.map(BoardDto :: toDto);
    }

    @Transactional
    @Override
    public BoardDto createBoard(BoardDto boardDto, Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow();
        boardDto.setMember(member);
        Board board = boardDto.toEntity();
        Board savedBoard = boardRepository.save(board);
        return BoardDto.toDto(savedBoard);
    }

    @Transactional
    @Override
    public BoardDto updateBoard(Long boardId, BoardDto boardDto){
        Board board = boardRepository.findById(boardId).get();
        board.update(boardDto);
        return BoardDto.toDto(board);
    }

    @Transactional
    @Override
    public void deleteBoard(Long boardId){
        Board boards = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id="+boardId));
        boardRepository.delete(boards);
    }


}
