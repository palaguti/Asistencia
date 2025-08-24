package com.esfe.Asistencia.controladores;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/")
public class Homecontroller {
    @RequestMapping
    public String index() {
        return "home/index"; 
    }
    @GetMapping("/login")
    public String mostrarLogin() {
        return "home/formLogin"; 
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        SecurityContextLogoutHandler logoutHandler = new SecurityContextLogoutHandler();
        logoutHandler.logout(request, null, null);
        return "redirect:/"; 
    }

}
