package UET.model.DTO;

import UET.model.Department;
import UET.model.Employee;
import UET.model.Hospital;

import java.util.List;

public class PatientRecordDTOList {
    private List<PatientRecordDTO> patientRecordDTOs;
    private Hospital hospital;
    private Department department;
    private Employee employee;

    public PatientRecordDTOList() {
    }

    public List<PatientRecordDTO> getPatientRecordDTOs() {
        return patientRecordDTOs;
    }

    public void setPatientRecordDTOs(List<PatientRecordDTO> patientRecordDTOs) {
        this.patientRecordDTOs = patientRecordDTOs;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
