package com.loladebadmus.simplecrudapp.users;

import com.loladebadmus.simplecrudapp.errors.DuplicateDataException;
import com.loladebadmus.simplecrudapp.errors.FailedRegistrationException;
import com.loladebadmus.simplecrudapp.errors.ResourceNotFoundException;
import com.loladebadmus.simplecrudapp.rentals.Rental;
import com.loladebadmus.simplecrudapp.rentals.RentalRepository;
import com.loladebadmus.simplecrudapp.registration.token.ConfirmationToken;
import com.loladebadmus.simplecrudapp.registration.token.ConfirmationTokenService;
import com.loladebadmus.simplecrudapp.security.oauth.GoogleOauthUserDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
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

    @Autowired
    public UserService(UserRepository userRepository, RentalRepository rentalRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationTokenService confirmationTokenService) {
        this.userRepository = userRepository;
        this.rentalRepository = rentalRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
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
        if(!Objects.equals(user.getName(), newUser.getName())) {
            Optional<User> userOptional = userRepository.getUserByName(newUser.getName());
            if(userOptional.isPresent()) {
                throw new DuplicateDataException("name", newUser.getName());
            }
        }
        user.setName(newUser.getName());
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

                //todo send confirmation token associated with user again
            }
            throw new FailedRegistrationException("This email is already taken, go to /login");
        }
        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusHours(12),
                user
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    public int enableAppUser(String email) {
        return userRepository.enableAppUser(email);
    }

    public User processOAuthPostLogin(GoogleOauthUserDTO googleOauthUser) {
         Boolean isPresent = userRepository.findByEmail(googleOauthUser.getEmail()).isPresent();

         if(isPresent) {
             //todo: merge user data
         }

         User newUser = new User();
         newUser.setEnabled(true);
         newUser.setEmail(googleOauthUser.getEmail());
         newUser.setUserRole(UserRole.USER);
         newUser.setName(googleOauthUser.getFirstName());
         newUser.setLastName(googleOauthUser.getLastName());
         newUser.setLocked(false);
         return userRepository.save(newUser);
    }
}
