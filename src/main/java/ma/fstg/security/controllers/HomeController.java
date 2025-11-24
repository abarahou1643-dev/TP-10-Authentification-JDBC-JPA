package ma.fstg.security.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/dashboard";
    }

    @GetMapping("/home")
    public String homePage() {
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Collection<String> roles = auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        model.addAttribute("username", username);
        model.addAttribute("roles", roles);
        model.addAttribute("isAdmin", roles.contains("ROLE_ADMIN"));

        return "dashboard";
    }

    @GetMapping("/admin/manage")
    public String adminPage(Model model) {
        model.addAttribute("message", "🔧 Page d'administration - Réservée aux ADMINISTRATEURS");
        model.addAttribute("pageTitle", "Espace Administration");
        return "admin/manage";
    }

    @GetMapping("/user/profile")
    public String userProfile(Model model) {
        model.addAttribute("message", "👤 Profil utilisateur - Accessible aux USER et ADMIN");
        model.addAttribute("pageTitle", "Profil Utilisateur");
        return "user/profile";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        if (error != null) {
            model.addAttribute("errorMessage", "❌ Identifiants invalides!");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "✅ Vous avez été déconnecté avec succès.");
        }
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            model.addAttribute("username", auth.getName());
            model.addAttribute("roles", auth.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));
        }
        return "access-denied";
    }
}