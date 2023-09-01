package youresg.yesg.service.Impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youresg.yesg.domain.board.*;
import youresg.yesg.domain.member.Member;
import youresg.yesg.domain.member.MemberRepository;
import youresg.yesg.dto.board.*;
import youresg.yesg.service.IBoardService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
@Service
public class BoardServiceImpl implements IBoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final HashtagRepository hashtagRepository;
    private  final BoardHashtagRepository boardHashtagRepository;

    @Override
    @Transactional
    public List<BoardDto> findAllBoards(){
        List<Board> allBoard = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for(Board board : allBoard){
            boardDtoList.add(BoardDto.toDto(board));

        }
        return boardDtoList;
    }

    @Override
    public List<BoardDto> findAllBoardsBySearch(BoardSearchDto boardSearchDto){
        return boardRepository.findBySearch(boardSearchDto.getKeyword()).stream()
                .map(BoardDto:: toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BoardDto> findAllBoardsByHashtagName(String tagName){

        Optional<Hashtag>optionalHashtag = hashtagRepository.findByName(tagName);
        if(optionalHashtag.isEmpty()){
            return Collections.emptyList();
        }
        Hashtag hashtag = optionalHashtag.get();
        List<BoardHashtag>boardHashtags = hashtag.getBoardHashtagList();
        List<BoardDto> boardDtoList = new ArrayList<>();
        for(BoardHashtag boardHashtag : boardHashtags){
            boardDtoList.add(BoardDto.toDto(boardHashtag.getBoard()));
        }
        return boardDtoList;
    }


    @Override
    public Optional<BoardDto> findBoardById(Long boardId){
       Optional<Board>optionalBoard = boardRepository.findById(boardId);
        return optionalBoard.map(BoardDto :: toDto);
    }

    @Transactional
    @Override
    public BoardDto saveHashtag(Board board, String hashtag){
        Optional<Hashtag> hashtagContent = hashtagRepository.findByName(hashtag);
        BoardHashtag boardHashtag;
        if (hashtagContent.isEmpty()) {
            log.info("not exist hashtag, so newly making");
            Hashtag buildHashtag = Hashtag.builder().tagName(hashtag).build();
            hashtagRepository.save(buildHashtag);
            boardHashtag = BoardHashtag.builder()
                    .hashtag(buildHashtag)
                    .board(board)
                    .build();
            boardHashtag.addEntity(board, buildHashtag);

        }
        else{
            log.info("already exist hashtag");
            boardHashtag = BoardHashtag.builder()
                    .hashtag(hashtagContent.get())
                    .board(board)
                    .build();
            boardHashtag.addEntity(board, hashtagContent.get());

        }

        board.addEntity(boardHashtag);
        boardRepository.save(board);
        return BoardDto.toDto(boardHashtag.getBoard());
    }


    @Transactional
    @Override
    public BoardDto createBoard(BoardDto boardDto, Long memberId){
        Member member = memberRepository.findById(memberId).orElseThrow();
        boardDto.setMember(member);
        Board board = boardDto.toEntity();
        Board savedBoard = boardRepository.save(board);

        if(!boardDto.getHashtags().isEmpty()){
            for (String hashtagContent : boardDto.getHashtags()) {
                saveHashtag(savedBoard, hashtagContent);
            }
        }
        return BoardDto.toDto(savedBoard);
    }

    @Transactional
    @Override
    public BoardDto updateBoard(Long boardId, BoardDto updateBoardDto) {
        Board board = boardRepository.findById(boardId).get();
        board.update(updateBoardDto);
        updateHashtag(board, updateBoardDto);
        return BoardDto.toDto(board);
    }

    @Transactional
    @Override
    public void deleteBoard(Long boardId) {
        Board boards = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + boardId));
        boardRepository.delete(boards);
    }

    public void updateHashtag(Board board, BoardDto updatedBoardDto) {
        for (BoardHashtag boardHashtag : board.getBoardHashtagList()) {
            boardHashtagRepository.delete(boardHashtag);
        }
        board.getBoardHashtagList().clear();

        for (String hashtag : updatedBoardDto.getHashtags()) {
            saveHashtag(board, hashtag);
        }

    }


}
