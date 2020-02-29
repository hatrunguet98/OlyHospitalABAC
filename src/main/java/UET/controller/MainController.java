package UET.controller;

import UET.model.User;
import UET.service.PatientService;
import UET.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {
    @Autowired
    UserService userService;
    @Autowired
    PatientService patientService;

    @GetMapping("/")
    public String index() {
        User user = userService.getUserAth();
        if (user == null) return "login-form";
        else {
            if (user.getRoles().get(0).getName().equals("ROLE_DOCTOR")) {
                return "redirect:/patientRecord/read/doctor";
            }
            if (user.getRoles().get(0).getName().equals("ROLE_RECEPTIONIST")) {
                return "redirect:/patientRecord/create";
            }
            if (user.getRoles().get(0).getName().equals("ROLE_NURSE")) {
                return "redirect:/patientRecord/read/department";
            }
            if (user.getRoles().get(0).getName().equals("ROLE_PATIENT")) {
                return "redirect:/patientRecord/read";
            }
            if (user.getRoles().get(0).getName().equals("ROLE_ADMIN")) {
                return "redirect:/employee/doctors";
            }
            return null;
        }
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }

    @GetMapping("/login-form")
    public String getLogin() {
        return "login-form";
    }

    @PostMapping("/login")
    public String postLogin() {
        return "login-form";
    }

    @GetMapping("/registration")
    public String register() {
        return "registration";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginErro", true);
        return "login-form";
    }

    @GetMapping("/profile/**")
    public String showProfile() {
        return "profile";
    }

    @GetMapping("/profile/admin")
    public String showProfileAdmin() {
        return "profileAdmin";
    }

    @GetMapping("/secretary/profile/**")
    public String showProfileSaler() {
        return "secretaryProfile";
    }
}
