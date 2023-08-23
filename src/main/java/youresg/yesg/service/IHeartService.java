package youresg.yesg.service;

import youresg.yesg.dto.board.HeartDto;
import youresg.yesg.dto.member.MemberDto;

import java.util.List;

public interface IHeartService {

    Boolean findHeart(HeartDto heartDto);
    Boolean saveHeart(HeartDto heartDto);
    List<MemberDto> findHeartedMembersByBoardId(Long boardId);

}
