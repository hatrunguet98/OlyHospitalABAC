package UET.model;

import UET.Enum.EDepartment;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "department")
public class Department implements Serializable {
    private static final long serialVersionUID = 40L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private EDepartment type;

    @OrderBy("id")
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Patient> patients;

    @OrderBy("id")
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY)
    private List<Employee> employees;

    public Department() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EDepartment getType() {
        return type;
    }

    public void setType(EDepartment type) {
        this.type = type;
    }

    public List<Patient> getPatients() {
        return patients;
    }

    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
