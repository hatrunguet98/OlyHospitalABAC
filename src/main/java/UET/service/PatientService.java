package UET.service;

import UET.model.DTO.PatientRecordDTO;
import UET.model.*;
import UET.model.DTO.PatientRecordDTOList;
import UET.model.entity.ObjectMapperUtils;
import UET.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService {
    @Autowired
    PatientRecordRepository patientRecordRepository;
    @Autowired
    PatientRepository patientRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    public void createdPatientRecord(PatientRecordDTO patientRecordDTO, RedirectAttributes model) {
        String username = patientRecordDTO.getNumberPhone();
        Employee employee = employeeRepository.findById(patientRecordDTO.getIdDoctor());
        if (!userService.checkPatientExist(username, employee.getHospital().getType().name())) {
            User accPatient = this.createAccPatient(patientRecordDTO);
            Patient patient = this.checkPatientExist(username, patientRecordDTO);
            List patientRecords = new ArrayList();
            if (patient.getPatientRecords() != null)
                patientRecords.addAll(patient.getPatientRecords());
            PatientRecord patientRecord = new PatientRecord();
            patientRecord.setHospital(employee.getHospital());
            patientRecord.setEmployee(employee);
            patientRecords.add(patientRecord);
            patient.setDepartment(employee.getDepartment());
            patient.setUser(accPatient);
            patient.setHospital(employee.getHospital());
            patient.setEmployee(employee);
            patientRepository.save(patient);
            patientRecord.setPatient(patient);
            patientRecordRepository.save(patientRecord);
        } else {
            model.addFlashAttribute("error", true);
        }
    }

    private Patient checkPatientExist(String userName, PatientRecordDTO patientRecordDTO) {
        Patient patient = patientRepository.findByNumberPhone(userName);
        if (ObjectUtils.isEmpty(patient)) {
            patient = new Patient();
        }
        patient.setFullName(patientRecordDTO.getName());
        patient.setAddress(patientRecordDTO.getAddress());
        patient.setAge(patientRecordDTO.getAge());
        patient.setNumberPhone(userName);
        return patient;
    }

    private User createAccPatient(PatientRecordDTO patientRecordDTO) {
        String username = patientRecordDTO.getNumberPhone();
        User patient = new User();
        patient.setUsername(username);
        patient.setPassword(passwordEncoder.encode("123456"));
        List<Role> roles = new ArrayList<>();
        roles.add(roleRepository.findByName("ROLE_PATIENT"));
        patient.setRoles(roles);
        return patient;
    }

    private PatientRecordDTOList getPatientRecordDTOList(List<Patient> patients, Hospital hospital) {
        List<PatientRecordDTO> patientRecordDTOs = new ArrayList<>();
        PatientRecordDTOList patientRecordDTOList = new PatientRecordDTOList();
        for (Patient patient : patients) {
            PatientRecordDTO patientRecordDTO = ObjectMapperUtils.map(patient, PatientRecordDTO.class);
            patientRecordDTO.setName(patient.getFullName());
            patientRecordDTO.setIdPatient(patient.getId());
            PatientRecord patientRecord = patient.getPatientRecords()
                    .stream().filter(pR -> pR.getHospital().getType().equals(hospital.getType()))
                    .findFirst().orElse(null);
            if (!ObjectUtils.isEmpty(patientRecord)) {
                if (!StringUtils.isEmpty(patientRecord.getSick()))
                    patientRecordDTO.setSick(patientRecord.getSick());
                if (!StringUtils.isEmpty(patientRecord.getSickStatus()))
                    patientRecordDTO.setSickStatus(patientRecord.getSickStatus());
                patientRecordDTOs.add(patientRecordDTO);
            }
        }
        patientRecordDTOList.setHospital(hospital);
        patientRecordDTOList.setPatientRecordDTOs(patientRecordDTOs);
        return patientRecordDTOList;
    }

    private List<PatientRecordDTO> getPatientRecordDTOs(List<PatientRecord> patientRecords, String hospitalName) {
        List<PatientRecordDTO> patientRecordDTOList = new ArrayList<>();
        for (PatientRecord patientRecord : patientRecords) {
            Patient patient = patientRecord.getPatient();
            PatientRecordDTO patientRecordDTO = ObjectMapperUtils.map(patient, PatientRecordDTO.class);
            patientRecordDTO.setName(patient.getFullName());
            patientRecordDTO.setIdPatient(patient.getId());
            if (!StringUtils.isEmpty(patientRecord)) {
                if (!StringUtils.isEmpty(patientRecord.getSick()))
                    patientRecordDTO.setSick(patientRecord.getSick());
                if (!StringUtils.isEmpty(patientRecord.getSickStatus()))
                    patientRecordDTO.setSickStatus(patientRecord.getSickStatus());
            }
            if (patientRecord.getPatient().getHospital().getType().name().equals(hospitalName))
                patientRecordDTOList.add(patientRecordDTO);
        }
        return patientRecordDTOList;
    }

    public PatientRecordDTOList showPatientRecordByDepartment(Model model) {
        User user = userService.getUserAth();
        Department department = new Department();
        Hospital hospital = user.getEmployee().getHospital();
        String hospitalName = hospital.getType().name();
        model.addAttribute("hospital", hospitalName);
        if (!ObjectUtils.isEmpty(user.getEmployee().getDepartment())) {
            department = user.getEmployee().getDepartment();
        }
        if (!ObjectUtils.isEmpty(department)) {
            PatientRecordDTOList patientRecordDTOList = this.getPatientRecordDTOList(department.getPatients(), hospital);
            patientRecordDTOList.setDepartment(department);
            model.addAttribute("patientRecords", patientRecordDTOList.getPatientRecordDTOs());
            return patientRecordDTOList;
        }
        return new PatientRecordDTOList();
    }

    public PatientRecordDTOList showPatientRecordByDoctor(Model model) {
        PatientRecordDTOList patientRecordDTOList = new PatientRecordDTOList();
        User doctorAuth = userService.getUserAth();
        Employee employee = doctorAuth.getEmployee();
        String hospitalName = employee.getHospital().getType().name();
        model.addAttribute("hospital", hospitalName);
        if (!ObjectUtils.isEmpty(employee)) {
            List<PatientRecordDTO> patientRecordDTOs = this.getPatientRecordDTOs(doctorAuth.getEmployee().getPatientRecords(), hospitalName);
            model.addAttribute("patientRecords", patientRecordDTOs);
            patientRecordDTOList.setEmployee(employee);
            patientRecordDTOList.setDepartment(employee.getDepartment());
            patientRecordDTOList.setHospital(employee.getHospital());
            patientRecordDTOList.setPatientRecordDTOs(patientRecordDTOs);
        }
        return patientRecordDTOList;
    }

    public void showRecordOfPatient(Patient patient, Model model) {
        String hospitalName = patient.getHospital().getType().name();
        PatientRecord patientRecord = patient.getPatientRecords()
                .stream().filter(pR -> pR.getHospital().getType().name().equals(hospitalName))
                .findFirst().orElse(new PatientRecord());
        model.addAttribute("hospital", hospitalName);
        model.addAttribute("patientRecord", this.mapPatientRecordToDTO(patientRecord));
    }

    public void editPatientRecord(PatientRecordDTO patientRecordDTO, Patient patient, Model model) {
        if (!ObjectUtils.isEmpty(patient)) {
            PatientRecord patientRecord = patient.getPatientRecords()
                    .stream().filter(pR -> pR.getHospital().getType().equals(patient.getHospital().getType()))
                    .findFirst().orElse(new PatientRecord());
//            patient.setNumberPhone(patientRecordDTO.getNumberPhone());
//            patient.setAge(patientRecordDTO.getAge());
//            patient.setFullName(patientRecordDTO.getName());
//            patient.setAddress(patientRecordDTO.getAddress());
            patientRecord.setSick(patientRecordDTO.getSick());
            patientRecord.setSickStatus(patientRecordDTO.getSickStatus());
            patientRecordRepository.save(patientRecord);
            String hospitalName = patient.getHospital().getType().name();
            if (hospitalName.equals(patientRecord.getHospital().getType().name()))
                model.addAttribute("isEdit", true);
            model.addAttribute("patientRecordHospitalOthers", patient.getPatientRecords());
            model.addAttribute("hospital", hospitalName);
            model.addAttribute("patientRecord", this.mapPatientRecordToDTO(patientRecord));
        }
    }

    public void getPatientRecordDoctor(Patient patient, Model model) {
        String hospitalName = patient.getHospital().getType().name();
        PatientRecord patientRecord = patient.getPatientRecords()
                .stream().filter(pR -> pR.getHospital().getType().equals(patient.getHospital().getType()))
                .findFirst().orElse(new PatientRecord());
        model.addAttribute("hospital", hospitalName);
        if (hospitalName.equals(patientRecord.getHospital().getType().name()))
            model.addAttribute("isEdit", true);
        model.addAttribute("patientRecordHospitalOthers", patient.getPatientRecords());
        model.addAttribute("patientRecord", this.mapPatientRecordToDTO(patientRecord));
    }

    public void getPatientRecordOtherHospital(PatientRecord patientRecord, Model model) {
        String hospitalName = patientRecord.getPatient().getHospital().getType().name();
        model.addAttribute("hospital", hospitalName);
        model.addAttribute("hospitalCurrent", patientRecord.getHospital().getType().name());
        if (hospitalName.equals(patientRecord.getHospital().getType().name()))
            model.addAttribute("isEdit", true);
        model.addAttribute("patientRecordHospitalOthers", patientRecord.getPatient().getPatientRecords());
        model.addAttribute("patientRecord", this.mapPatientRecordToDTO(patientRecord));
    }

    public Patient findById(Integer id) {
        return patientRepository.findById(id);
    }

    public PatientRecord findPatientRecordById(Integer id) {
        return patientRecordRepository.findById(id);
    }

    private PatientRecordDTO mapPatientRecordToDTO(PatientRecord patientRecord) {
        PatientRecordDTO patientRecordDTO = new PatientRecordDTO();
        Patient patient = patientRecord.getPatient();
        patientRecordDTO.setAge(patient.getAge());
        patientRecordDTO.setAddress(patient.getAddress());
        patientRecordDTO.setIdPatient(patient.getId());
        patientRecordDTO.setName(patient.getFullName());
        patientRecordDTO.setNumberPhone(patient.getNumberPhone());
        if (!StringUtils.isEmpty(patientRecord.getSick()))
            patientRecordDTO.setSick(patientRecord.getSick());
        if (!StringUtils.isEmpty(patientRecord.getSickStatus()))
            patientRecordDTO.setSickStatus(patientRecord.getSickStatus());
        return patientRecordDTO;
    }
}
