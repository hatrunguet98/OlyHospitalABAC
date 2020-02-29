package UET.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fullName")
    private String fullName;
    @Column(name = "age")
    private Integer age;
    @Column(name = "numberPhone")
    private String numberPhone;
    @Column(name = "address")
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn
    private User user;

    @ManyToOne
    @JoinTable(name = "patient_department_id")
    @OrderBy("id")
    private Department department;

    @OrderBy("id")
    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private List<PatientRecord> patientRecords;

    @ManyToOne
    @JoinTable(name = "patient_hospital_id")
    @OrderBy("id")
    private Hospital hospital;

    @ManyToOne
    @JoinTable(name = "patient_employee_id")
    @OrderBy("id")
    private Employee employee;

    public Patient() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<PatientRecord> getPatientRecords() {
        return patientRecords;
    }

    public void setPatientRecords(List<PatientRecord> patientRecords) {
        this.patientRecords = patientRecords;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
