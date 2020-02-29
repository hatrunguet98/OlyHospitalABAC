package UET.model;

import UET.Enum.EHospital;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "hospital")
public class Hospital implements Serializable {
    private static final long serialVersionUID = 40L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private EHospital type;

    @OrderBy("id")
    @OneToMany(mappedBy = "hospital", fetch = FetchType.LAZY)
    private List<PatientRecord> patientRecords;

    @OrderBy("id")
    @OneToMany(mappedBy = "hospital", fetch = FetchType.LAZY)
    private List<Employee> employees;

    @OrderBy("id")
    @OneToMany(mappedBy = "hospital", fetch = FetchType.LAZY)
    private List<Patient> patient;

    public Hospital() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public EHospital getType() {
        return type;
    }

    public void setType(EHospital type) {
        this.type = type;
    }

    public List<PatientRecord> getPatientRecords() {
        return patientRecords;
    }

    public void setPatientRecords(List<PatientRecord> patientRecords) {
        this.patientRecords = patientRecords;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Patient> getPatient() {
        return patient;
    }

    public void setPatient(List<Patient> patient) {
        this.patient = patient;
    }
}
