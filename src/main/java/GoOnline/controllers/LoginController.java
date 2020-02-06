package GoOnline.controllers;

import GoOnline.dto.LoginData;
import GoOnline.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class LoginController implements ErrorController {

    @Autowired
    private UserService service;


    @GetMapping("/login")
    public String getStartPage(Model model) {
        model.addAttribute("loginData", new LoginData());
        return "login";
    }

    @GetMapping(value = "/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("loginData", new LoginData());
        return "register";
    }


    @PostMapping(value = "/register")
    public String register(@Valid @ModelAttribute("user") LoginData dto, BindingResult result, Model model, RedirectAttributes atr) {
        if (result.hasErrors()) {
            atr.addAttribute("baddata");
            return "register";
        }
        try {
            service.registerUser(dto);
        } catch (Exception e) {
            atr.addAttribute("userexists");
            return "register";
        }
        model.addAttribute("user", dto);
        return "redirect:/login";
    }

    @Override
    public String getErrorPath() {
        return "redirect:/login";
    }
}
