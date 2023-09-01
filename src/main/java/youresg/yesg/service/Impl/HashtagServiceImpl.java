package youresg.yesg.service.Impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import youresg.yesg.domain.board.BoardHashtag;
import youresg.yesg.domain.board.BoardRepository;
import youresg.yesg.domain.board.Hashtag;
import youresg.yesg.domain.board.HashtagRepository;
import youresg.yesg.domain.member.MemberRepository;
import youresg.yesg.dto.board.BoardDto;
import youresg.yesg.dto.board.HashtagDto;
import youresg.yesg.service.IHashtagService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
@Slf4j
public class HashtagServiceImpl implements IHashtagService {

    private HashtagRepository hashtagRepository;
    private BoardRepository boardRepository;
    private MemberRepository memberRepository;

    @Override
    public List<BoardDto> findAllBoardsByHashtagId(Long hashtagId){
        Optional<Hashtag> optionalHashtag = hashtagRepository.findById(hashtagId);
        if(optionalHashtag.isEmpty()){
            return Collections.emptyList();
        }

        Hashtag hashtag = optionalHashtag.get();
        List<BoardHashtag>boardHashtags = hashtag.getBoardHashtagList();
        List<BoardDto>boardDtoList = new ArrayList<>();

        for(BoardHashtag boardHashtag : boardHashtags){
            boardDtoList.add(BoardDto.toDto(boardHashtag.getBoard()));
        }
        return boardDtoList;

    }
    @Transactional
    @Override
    public HashtagDto createHashtag(HashtagDto hashtagDto){
        Hashtag hashtag = hashtagDto.toEntity();
        Hashtag savedHashtag = hashtagRepository.save(hashtag);
        return HashtagDto.toDto(savedHashtag);

    }

    @Transactional
    @Override
    public void deleteHashtag(Long hashtagId){
        Hashtag hashtags = hashtagRepository.findById(hashtagId).orElseThrow(() -> new IllegalArgumentException("해당 해시태그가 없습니다. id="+hashtagId));
        hashtagRepository.delete(hashtags);
    }
}
