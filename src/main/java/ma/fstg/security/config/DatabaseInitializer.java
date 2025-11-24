package ma.fstg.security.config;

import lombok.extern.slf4j.Slf4j;
import ma.fstg.security.entities.Role;
import ma.fstg.security.entities.User;
import ma.fstg.security.repositories.RoleRepository;
import ma.fstg.security.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;

@Slf4j
@Component
public class DatabaseInitializer {

    @Bean
    @Transactional
    CommandLineRunner initDatabase(RoleRepository roleRepository,
                                   UserRepository userRepository,
                                   BCryptPasswordEncoder passwordEncoder) {

        return args -> {
            log.info("🚀 Initialisation de la base de données...");

            // Création des rôles s'ils n'existent pas
            Role roleAdmin = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> {
                        Role newRole = new Role("ROLE_ADMIN");
                        log.info("📝 Création du rôle: ROLE_ADMIN");
                        return roleRepository.save(newRole);
                    });

            Role roleUser = roleRepository.findByName("ROLE_USER")
                    .orElseGet(() -> {
                        Role newRole = new Role("ROLE_USER");
                        log.info("📝 Création du rôle: ROLE_USER");
                        return roleRepository.save(newRole);
                    });

            // Création de l'administrateur
            if (!userRepository.existsByUsername("admin")) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("1234"));
                admin.setActive(true);
                admin.setRoles(new HashSet<>(Arrays.asList(roleAdmin, roleUser)));

                userRepository.save(admin);
                log.info("👑 Administrateur créé: admin / 1234");
            }

            // Création de l'utilisateur standard
            if (!userRepository.existsByUsername("user")) {
                User user = new User();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("1111"));
                user.setActive(true);
                user.setRoles(new HashSet<>(Arrays.asList(roleUser)));

                userRepository.save(user);
                log.info("👤 Utilisateur créé: user / 1111");
            }

            log.info("✅ Initialisation de la base de données terminée!");
            log.info("==============================================");
            log.info("📋 Comptes disponibles:");
            log.info("   👑 Admin: admin / 1234");
            log.info("   👤 User: user / 1111");
            log.info("==============================================");
        };
    }
}