package ma.fstg.security.services;

import lombok.extern.slf4j.Slf4j;
import ma.fstg.security.entities.Role;
import ma.fstg.security.entities.User;
import ma.fstg.security.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("🔐 Tentative de connexion pour: {}", username);

        User user = userRepository.findByUsernameWithRoles(username)
                .orElseThrow(() -> {
                    log.error("❌ Utilisateur non trouvé: {}", username);
                    return new UsernameNotFoundException("Utilisateur non trouvé: " + username);
                });

        if (!user.isActive()) {
            log.error("🚫 Compte désactivé: {}", username);
            throw new UsernameNotFoundException("Compte désactivé: " + username);
        }

        log.info("✅ Utilisateur trouvé: {} avec {} rôle(s)", username, user.getRoles().size());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.isActive(),
                true,  // accountNonExpired
                true,  // credentialsNonExpired
                true,  // accountNonLocked
                getAuthorities(user.getRoles())
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}