package UET.repository;

import UET.model.PatientRecord;
import org.springframework.data.repository.CrudRepository;

public interface PatientRecordRepository extends CrudRepository<PatientRecord, String> {
    PatientRecord findById(Integer id);
}
