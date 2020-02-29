package UET.repository;

import UET.Enum.EHospital;
import UET.model.Hospital;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HospitalRepository extends CrudRepository<Hospital, String> {
    Hospital findByType(EHospital eDepartment);

    Hospital findById(Integer id);

    List<Hospital> findAll();
}
