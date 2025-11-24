

#  Spring Security JPA - Authentification

> Système d'authentification sécurisé avec Spring Boot, JPA et MySQL

##  Stack Technique

- **Backend**: Spring Boot 3.2.0, Spring Security 6.0, Spring Data JPA
- **Frontend**: Thymeleaf, Bootstrap 5, Font Awesome 6
- **Base de données**: MySQL 8.0
- **Sécurité**: BCrypt, CSRF Protection, Session Management
- **Outils**: Maven, Lombok, DevTools

##  Installation

### 1. Prérequis
```bash
Java 17+
MySQL 8.0+
Maven 3.6+
```

### 2. Configuration Base de Données
```sql
CREATE DATABASE security_db;
```

### 3. Configuration Application
```properties
# src/main/resources/application.properties
spring.datasource.url=jdbc:mysql://localhost:3306/security_db
spring.datasource.username=root
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=create
spring.jpa.show-sql=true
```

### 4. Lancement
```bash
mvn spring-boot:run
```
Ouvrir: http://localhost:8080

##  Comptes de Test

| Utilisateur | Mot de passe | Rôles        |
|-------------|--------------|-------------|
| admin       | 1234         | ROLE_ADMIN   |
| user        | 1111         | ROLE_USER    |

##  Architecture

```
src/
├── main/
│   ├── java/ma/fstg/security/
│   │   ├── config/           # Configurations
│   │   ├── controllers/      # Contrôleurs web
│   │   ├── entities/         # Entités JPA
│   │   ├── repositories/     # Repositories Spring Data
│   │   └── services/         # Services métier
│   └── resources/
│       ├── static/css/       # Styles personnalisés
│       └── templates/        # Vues Thymeleaf
```

##  Sécurité

### Configuration Spring Security
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    // Authentification JPA personnalisée
    // Encodage BCrypt
    // Règles d'accès par rôle
}
```

### Entités de Sécurité
```java
@Entity
public class User {
    private String username;
    private String password; // BCrypt
    private boolean active;
    @ManyToMany(fetch = EAGER)
    private Set<Role> roles;
}

@Entity
public class Role {
    private String name; // ROLE_ADMIN, ROLE_USER
}
```

##  Pages Disponibles

| Page | URL | Accès |
|------|-----|-------|
| Connexion | `/login` | Public |
| Tableau de bord | `/dashboard` | Authentifié |
| Administration | `/admin/manage` | ROLE_ADMIN |
| Profil utilisateur | `/user/profile` | ROLE_USER |
| Accès refusé | `/access-denied` | Authentifié |

## 🛠️ Développement

### Structure des Entités
```java
// User.java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(unique = true)
    private String username;
    
    private String password;
    private boolean active;
    
    @ManyToMany(fetch = EAGER)
    private Set<Role> roles;
}
```

### Service d'Authentification
```java
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) {
        // Chargement depuis la base via JPA
        // Conversion des rôles en GrantedAuthority
    }
}
```

##  Interface

### Styles Personnalisés
- Design moderne avec Bootstrap 5
- Palette de couleurs professionnelle
- Animations CSS fluides
- Interface responsive

### Fonctionnalités UI
- Navigation conditionnelle par rôle
- Messages d'alerte contextuels
- Tableaux interactifs
- Modales Bootstrap

##  Base de Données

### Tables Générées
- `users` - Utilisateurs du système
- `roles` - Rôles disponibles
- `users_roles` - Relation many-to-many

### Initialisation des Données
```java
@Component
public class DatabaseInitializer {
    // Crée automatiquement:
    // - Rôles: ROLE_ADMIN, ROLE_USER
    // - Utilisateurs: admin, user
    // - Mots de passe encodés BCrypt
}
```

##  API Endpoints

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| GET | `/` | Redirection dashboard |
| GET | `/login` | Formulaire de connexion |
| POST | `/login` | Authentification |
| POST | `/logout` | Déconnexion |
| GET | `/dashboard` | Tableau de bord principal |
| GET | `/admin/**` | Espace administration |
| GET | `/user/**` | Espace utilisateur |

## Dépannage

### Problèmes Courants

1. **Erreur de connexion MySQL**
   ```bash
   # Vérifier que MySQL est démarré
   sudo systemctl start mysql
   
   # Vérifier les credentials dans application.properties
   ```

2. **Styles non appliqués**
   ```bash
   # Vérifier la structure des dossiers
   src/main/resources/static/css/custom.css
   
   # Redémarrer l'application
   mvn spring-boot:run
   ```

3. **Accès refusé**
   - Utiliser les comptes de test
   - Vérifier les rôles en base de données

### Logs de Débogage
```properties
# application.properties
logging.level.ma.fstg.security=DEBUG
logging.level.org.springframework.security=DEBUG
```

##  Fonctionnalités Avancées

### Sécurité
-  Authentification JPA
-  Autorisations par rôle
-  Encodage BCrypt
-  Protection CSRF
-  Gestion des sessions

### Interface
-  Design responsive
-  Navigation conditionnelle
-  Messages d'alerte
-  Tableaux interactifs

### Administration
-  Gestion des utilisateurs
-  Gestion des rôles
-  Tableau de bord admin
-  Statistiques de sécurité

##  Déploiement

### Développement
```bash
mvn spring-boot:run
```

### Production
```bash
mvn clean package
java -jar target/spring-security-jpa-0.0.1-SNAPSHOT.jar
```


Authentification sécurisée avec Spring Boot + JPA + MySQL.


##  Test Accounts
- **Admin**: `admin` / `1234`
- **User**: `user` / `1111`

##  Project Structure
```
src/
├── config/          # SecurityConfig, DatabaseInitializer
├── controllers/     # HomeController
├── entities/        # User, Role
├── repositories/    # UserRepository, RoleRepository
└── services/        # CustomUserDetailsService
```

##  Features
- JPA Authentication
- Role-based Authorization  
- BCrypt Password Encoding
- MySQL Persistence
- Modern UI with Bootstrap

##  Pages
- `/login` - Authentication
- `/dashboard` - Main dashboard
- `/admin/manage` - Admin panel
- `/user/profile` - User profile




https://github.com/user-attachments/assets/b10b1b0c-f6c4-42e3-8940-2f4a24d4b0af




https://github.com/user-attachments/assets/37bb372c-8fb2-4d96-aec7-3e9913d6d80f




https://github.com/user-attachments/assets/ddc9ce83-cf25-4bed-979f-cccbb8c77727





https://github.com/user-attachments/assets/14219af9-42ee-4411-9777-c8027815029a

