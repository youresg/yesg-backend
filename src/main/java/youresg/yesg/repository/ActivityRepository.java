package youresg.yesg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import youresg.yesg.domain.activity.Activity;

public interface ActivityRepository extends JpaRepository<Activity, Long> {
}
