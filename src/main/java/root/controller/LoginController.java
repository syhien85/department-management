package root.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginGet() {

        return "login.html";
    }

    @PostMapping("/login")
    public String loginPost(
        @RequestParam("username") String username,
        @RequestParam("password") String password,
        HttpSession session
    ) {

        if (username.equals("admin") && password.equals("123123")) {

            session.setAttribute("loginUser", username);
            session.setMaxInactiveInterval(15 * 60 * 1000); // 15min

            return "redirect:/user/list";
            // return "redirect:/welcome";
        } else {
            return "redirect:/login";
        }
    }
}
