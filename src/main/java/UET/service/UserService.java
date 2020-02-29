package UET.service;

import UET.model.*;
import UET.model.DTO.DoctorDTO;
import UET.repository.RoleRepository;
import UET.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PasswordEncoder passwordEncoder;

    public void showDoctors(Model model) {
        User userAth = this.getUserAth();
        Role role = roleRepository.findByName("ROLE_DOCTOR");
        String hospitalName = userAth.getEmployee().getHospital().getType().name();
        model.addAttribute("hospital", hospitalName);
        model.addAttribute("doctors", this.getDoctorInfo(role.getUsers(), hospitalName));
    }

    private List<DoctorDTO> getDoctorInfo(Set<User> doctors, String hospitalName) {
        List<DoctorDTO> doctorDTOS = new ArrayList<>();
        for (User user : doctors) {
            if (user.getEmployee().getHospital().getType().name().equals(hospitalName)) {
                DoctorDTO doctorDTO = new DoctorDTO();
                doctorDTO.setId(user.getEmployee().getId());
                doctorDTO.setFullName(user.getEmployee().getFullName());
                if (!ObjectUtils.isEmpty(user.getEmployee().getDepartment()))
                    doctorDTO.setDepartment(user.getEmployee().getDepartment().getType().name());
                else doctorDTO.setDepartment("");
                doctorDTOS.add(doctorDTO);
            }
        }
        return doctorDTOS;
    }

    public Boolean checkPassword(String passwordUser, String passwordOld) {
        return passwordEncoder.matches(passwordOld, passwordUser);
    }

    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public User getUserAth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(authentication.getName());
    }

    public boolean checkUserNameExist(String userName) {
        User user = userRepository.findByUsername(userName);
        if (ObjectUtils.isEmpty(user)) {
            return false;
        } else {
            return true;
        }
    }

    public boolean checkPatientExist(String userName, String hospitalName) {
        User user = userRepository.findByUsername(userName);
        if (ObjectUtils.isEmpty(user)) {
            return false;
        } else {
            if(!user.getPatient().getHospital().getType().name().equals(hospitalName))
                return false;
            return true;
        }
    }
}
