package UET.controller;

import UET.model.DTO.EmployeeDTO;
import UET.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/doctors")
//    @PreAuthorize("hasRole('ADMIN')")
    public String getDoctors(Model model) {
        employeeService.showEmployees(model, "ROLE_DOCTOR");
        model.addAttribute("role", "doctor");
        return "employees";
    }

    @GetMapping("/nurses")
//    @PreAuthorize("hasRole('ADMIN')")
    public String getNurses(Model model) {
        employeeService.showEmployees(model, "ROLE_NURSE");
        model.addAttribute("role", "nurse");
        return "employees";
    }

    @GetMapping("/receptionists")
//    @PreAuthorize("hasRole('ADMIN')")
    public String getReceptionists(Model model) {
        employeeService.showEmployees(model, "ROLE_RECEPTIONIST");
        model.addAttribute("role", "receptionist");
        return "employees";
    }

    @PostMapping("/created/doctor")
//    @PreAuthorize("hasRole('ADMIN')")
    public RedirectView createDoctor(@ModelAttribute("employeeDTO") @Valid EmployeeDTO employeeDTO, RedirectAttributes model) {
        employeeService.createdEmployee(employeeDTO, "ROLE_DOCTOR", model);
        return new RedirectView("/employee/doctors");
    }

    @PostMapping("/created/nurse")
//    @PreAuthorize("hasRole('ADMIN')")
    public String createNurse(@ModelAttribute("employeeDTO") @Valid EmployeeDTO employeeDTO, RedirectAttributes model) {
        employeeService.createdEmployee(employeeDTO, "ROLE_NURSE", model);
        return "redirect:/employee/nurses";
    }

    @PostMapping("/created/receptionist")
//    @PreAuthorize("hasRole('ADMIN')")
    public String createReceptionist(@ModelAttribute("employeeDTO") @Valid EmployeeDTO employeeDTO, RedirectAttributes model) {
        employeeService.createdEmployee(employeeDTO, "ROLE_RECEPTIONIST", model);
        return "redirect:/employee/receptionists";
    }
}
