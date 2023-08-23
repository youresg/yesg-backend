package youresg.yesg.service;

import youresg.yesg.dto.board.BoardDto;
import youresg.yesg.dto.board.HashtagDto;

import java.util.List;

public interface IHashtagService {

    List<BoardDto> findAllBoardsByHashtagId(Long hashtagId);
    HashtagDto createHashtag(HashtagDto hashtagDto);
    void deleteHashtag(Long hashtagId);

}
