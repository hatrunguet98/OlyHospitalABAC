package UET.repository;

import UET.Enum.EDepartment;
import UET.model.Department;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DepartmentRepository extends CrudRepository<Department, String> {
    Department findByType(EDepartment eDepartment);

    Department findById(Integer id);

    List<Department> findAll();
}
