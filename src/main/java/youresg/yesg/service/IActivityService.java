package youresg.yesg.service;

import youresg.yesg.dto.record.ActivityDto;

import java.util.List;

public interface IActivityService {

    List<ActivityDto> findAllActivities();

}
