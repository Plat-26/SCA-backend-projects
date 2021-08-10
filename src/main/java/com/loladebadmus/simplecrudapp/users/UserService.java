package com.loladebadmus.simplecrudapp.users;

import com.loladebadmus.simplecrudapp.errors.DuplicateDataException;
import com.loladebadmus.simplecrudapp.errors.FailedRegistrationException;
import com.loladebadmus.simplecrudapp.errors.ResourceNotFoundException;
import com.loladebadmus.simplecrudapp.registration.email.EmailSender;
import com.loladebadmus.simplecrudapp.rentals.Rental;
import com.loladebadmus.simplecrudapp.rentals.RentalRepository;
import com.loladebadmus.simplecrudapp.registration.token.ConfirmationTokenService;
import com.loladebadmus.simplecrudapp.security.oauth.GoogleOauthUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RentalRepository rentalRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;

    @Autowired
    public UserService(UserRepository userRepository, RentalRepository rentalRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationTokenService confirmationTokenService, EmailSender emailSender) {
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
        this.emailSender = emailSender;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(UUID id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
    }

    public User getUserByName(String username) {
        return userRepository.getUserByName(username).orElseThrow(
                () -> new ResourceNotFoundException("This user is missing, check name or register")
        );
    }

    @Transactional
    public void updateUser(UUID id, User newUser) {
        User user =  userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
        if(!Objects.equals(user.getEmail(), newUser.getEmail())) {
            Optional<User> userOptional = userRepository.findByEmail(newUser.getEmail());
            if(userOptional.isPresent()) {
                throw new DuplicateDataException("email", newUser.getEmail());
            }
        }
        user.setEmail(newUser.getEmail());
        user.setFirstName(newUser.getFirstName().isEmpty() ? user.getFirstName() : newUser.getFirstName());
        user.setLastName(newUser.getLastName().isEmpty() ? user.getLastName() : newUser.getLastName());
        userRepository.save(user);
    }

    public void deleteUser(UUID id) {
        boolean exists = userRepository.existsById(id);
        if(!exists) {
            throw new ResourceNotFoundException(id);
        }
        Rental rental = rentalRepository.findByUserId(id).get();
        rental.setUser(null);
        User user = userRepository.findById(id).get();
        user.setRentals(null);
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("This user email is not associated with a user, go to /register to continue")
        );
    }

    @Transactional
    public String signUpUser(User user) {
        boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
        if(userExists) {
            User savedUser = userRepository.findByEmail(user.getEmail()).get();
            if(!savedUser.getEnabled()) {
                throw new FailedRegistrationException("Already signed up, click the link in email to enable account");
            }
            throw new FailedRegistrationException("This email is already taken, go to /login");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        return confirmationTokenService.createConfirmationToken(user);
    }

    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }

    @Transactional
    public User processOAuthPostLogin(GoogleOauthUserDTO googleOauthUser) {
        User oauthUser;

        boolean isPresent = userRepository.findByEmail(googleOauthUser.getEmail()).isPresent();

         if(isPresent) {
             oauthUser = userRepository.findByEmail(googleOauthUser.getEmail()).get();
         } else {
             oauthUser = new User();
         }
         oauthUser.setEnabled(true);
         oauthUser.setEmail(googleOauthUser.getEmail());
         oauthUser.setUserRole(UserRole.USER);
         oauthUser.setFirstName(googleOauthUser.getFirstName());
         oauthUser.setLastName(googleOauthUser.getLastName());
         oauthUser.setLocked(false);
         oauthUser.setProvider(UserProvider.GOOGLE);
         return userRepository.save(oauthUser);
    }

    @Transactional
    public String updatePassword(User user, String password) {
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return "password reset successful";
    }
}
