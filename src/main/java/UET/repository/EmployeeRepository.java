package UET.repository;

import UET.model.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, String> {
    Employee findById(Integer id);
}
