package UET.controller;

import UET.model.*;
import UET.model.DTO.PatientRecordDTO;
import UET.model.DTO.PatientRecordDTOList;
import UET.repository.PatientRecordRepository;
import UET.security.abac.spring.ContextAwarePolicyEnforcement;
import UET.service.PatientService;
import UET.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
@RequestMapping("/patientRecord")
public class PatientRecordController {
    @Autowired
    PatientService patientService;
    @Autowired
    PatientRecordRepository patientRecordRepository;
    @Autowired
    UserService userService;
    @Autowired
    private ContextAwarePolicyEnforcement policy;

    @GetMapping("/create")
    @PreAuthorize("hasRole('RECEPTIONIST')")
    public String getFormPatientRecord(Model model) {
        userService.showDoctors(model);
        return "createPatientRecord";
    }

    @GetMapping("/read/doctor")
    public String showPatientRecordByDoctor(Model model) {
        PatientRecordDTOList patientRecordList = patientService.showPatientRecordByDoctor(model);
        if (ObjectUtils.isEmpty(patientRecordList)) {
            return "redirect:/";
        }
        policy.checkPermission(patientRecordList, "READ_PATIENTRECORD_BY_DOCTOR");
        return "patientRecordDoctor";
    }

    @GetMapping("/read/department")
    public String showPatientRecordByDepartment(Model model) {
        PatientRecordDTOList patientRecordDTOList = patientService.showPatientRecordByDepartment(model);
        policy.checkPermission(patientRecordDTOList, "READ_PATIENTRECORD_BY_DEPARTMENT");
        return "patientRecordNurse";
    }

    @GetMapping("/read")
    public String showPatientRecordForPatient(Model model) {
        User user = userService.getUserAth();
        Patient patient = new Patient();
        if (ObjectUtils.isEmpty(user)) {
            return "redirect:/";
        }
        if (!ObjectUtils.isEmpty(user.getPatient())) {
            patient = user.getPatient();
        }
        policy.checkPermission(patient, "READ_PATIENTRECORD");
        patientService.showRecordOfPatient(patient, model);
        return "patientRecord";
    }

    @GetMapping("/read/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model) {
        Patient patient = patientService.findById(id);
        if (ObjectUtils.isEmpty(patient)) {
            return "redirect:/";
        }
        policy.checkPermission(patient, "READ_PATIENTRECORD_BY_DOCTOR");
        patientService.getPatientRecordDoctor(patient, model);
        return "editPatientRecord";
    }

    @GetMapping("/read/hospital")
    public String readOtherHospital(@ModelAttribute("id") @Valid Integer id, Model model) {
        PatientRecord patientRecord = patientService.findPatientRecordById(id);
        if (ObjectUtils.isEmpty(patientRecord)) {
            return "redirect:/";
        }
        policy.checkPermission(patientRecord, "READ_PATIENTRECORD_IN_OTHER_HOSPITAL");
        patientService.getPatientRecordOtherHospital(patientRecord, model);
        return "editPatientRecord";
    }

    @PostMapping("/create")
    @PreAuthorize("hasPermission(null,'CREAT_PATIENTRECORD')")
    public RedirectView createdPatientRecord(
            @ModelAttribute("patientRecordDTO") @Valid PatientRecordDTO patientRecordDTO,
            RedirectAttributes model
    ) {
        patientService.createdPatientRecord(patientRecordDTO, model);
        return new RedirectView("/patientRecord/create");
    }

    @PostMapping("/update/{id}")
    public String editPatientRecord(@ModelAttribute("patientRecord") @Valid PatientRecordDTO patientRecordDTO, @PathVariable("id") Integer id, Model model) {
        Patient patient = patientService.findById(id);
        policy.checkPermission(patient, "UPDATE_PATIENTRECORD");
        patientService.editPatientRecord(patientRecordDTO, patient, model);
        return "editPatientRecord";
    }
}
