package UET.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "patientRecord")
public class PatientRecord implements Serializable {
    private static final long serialVersionUID = 2L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "sick")
    private String sick;

    @Column(name = "sickStatus")
    private String sickStatus;

    @ManyToOne
    @JoinTable(name = "patientRecord_hospital_id")
    @OrderBy("id")
    private Hospital hospital;

    @ManyToOne
    @JoinTable(name = "patientRecord_patient_id")
    @OrderBy("id")
    private Patient patient;

    @ManyToOne
    @JoinTable(name = "patientRecord_employee_id")
    @OrderBy("id")
    private Employee employee;

    public PatientRecord() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSick() {
        return sick;
    }

    public void setSick(String sick) {
        this.sick = sick;
    }

    public String getSickStatus() {
        return sickStatus;
    }

    public void setSickStatus(String sickStatus) {
        this.sickStatus = sickStatus;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
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
