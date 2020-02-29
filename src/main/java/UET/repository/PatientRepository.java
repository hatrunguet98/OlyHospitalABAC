package UET.repository;

import UET.model.Patient;
import org.springframework.data.repository.CrudRepository;

public interface PatientRepository extends CrudRepository<Patient, String> {
    Patient findById(Integer id);
    Patient findByNumberPhone(String numberPhone);
}
