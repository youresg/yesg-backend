package youresg.yesg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import youresg.yesg.domain.record.Record;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
