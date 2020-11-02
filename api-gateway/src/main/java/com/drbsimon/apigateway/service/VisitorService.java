package com.drbsimon.apigateway.service;

import com.drbsimon.apigateway.model.dto.UserCredentialsDTO;
import com.drbsimon.apigateway.model.Role;
import com.drbsimon.apigateway.model.dto.WatchListDTO;
import com.drbsimon.apigateway.model.Visitor;
import com.drbsimon.apigateway.security.service.ParseVisitorSecurityService;
import com.drbsimon.apigateway.utils.PatternUtil;
import com.drbsimon.apigateway.security.service.JwtTokenServices;
import com.drbsimon.apigateway.service.dao.VisitorDao;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Data
@RequiredArgsConstructor
@Service
@Slf4j
public class VisitorService {
    private final JwtTokenServices jwtTokenServices;
    private final PasswordEncoder passwordEncoder;
    private final VisitorDao visitorDao;
    private final ParseVisitorSecurityService parseVisitorSecurityService;

    public ResponseEntity registerUser(UserCredentialsDTO userCredentials) {

        ResponseEntity failedRegistrationMessage = checkRegistrationForError(userCredentials);
        if (failedRegistrationMessage != null) return failedRegistrationMessage;

        List<Role> roles = Collections.singletonList(Role.ROLE_USER);
        visitorDao.save(userCredentials, roles);
        return successfulRegistrationResponse(userCredentials.getUsername(), roles);
    }

    // TODO: check if e-mail is already taken in database
    // TODO: check if validation could generate error messages instead of registration service.
    // TODO: check if possible to modify to collect all possible errors in one message.
    public ResponseEntity checkRegistrationForError(UserCredentialsDTO userCredentials) {
        String username = userCredentials.getUsername();
        String password = userCredentials.getPassword();
        String firstname = userCredentials.getFirstname();
        String lastname = userCredentials.getLastname();
        String email = userCredentials.getEmail();
        List<String> errorList = new ArrayList<>();

        if (visitorDao.findVisitorBy(username).isPresent()) {
            return failedRegisterMessage("Username already exists! Please choose a different username!");
        }

        if (!PatternUtil.isValidUsername(username, errorList)) {
            String error = String.join(", ", errorList);
            String errorMessage = "Username should have " + error + "!";
            return failedRegisterMessage(errorMessage);
        }

        if (!PatternUtil.isValidPassword(password, errorList)) {
            String error = String.join(", ", errorList);
            String errorMessage = "Password should contain " + error + "!";
            return failedRegisterMessage(errorMessage);
        }

        if (!PatternUtil.isValidName(firstname, errorList)) {
            String error = String.join(", ", errorList);
            String errorMessage = "Firstname " + error + "!";
            return failedRegisterMessage(errorMessage);
        }

        if (!PatternUtil.isValidName(lastname, errorList)) {
            String error = String.join(", ", errorList);
            String errorMessage = "Lastname " + error + "!";
            return failedRegisterMessage(errorMessage);
        }

        if (!PatternUtil.isValidEmail(email, errorList)) {
            return failedRegisterMessage("Not valid email!");
        }

        return null;
    }

    private ResponseEntity successfulRegistrationResponse(String username, List<Role> roles) {
        Map<String, Object> responseMessage = new HashMap<>();

        String token = jwtTokenServices.createToken(username, Collections.singletonList(roles.stream().toString()));
        responseMessage.put("correct", true);
        responseMessage.put("username", username);
        responseMessage.put("roles", roles);
        responseMessage.put("token", token);
        return ResponseEntity.ok(responseMessage);
    }

    private ResponseEntity failedRegisterMessage(String message) {
        Map<String, Object> responseMessage = new HashMap<>();
        responseMessage.put("correct", false);
        responseMessage.put("msg", message);
        return ResponseEntity.ok(responseMessage);
    }

    public String getLoggedInVisitorNameAndRole(){
        String username = parseVisitorSecurityService.findLoggedInUsername();
        UserDetails visitor = parseVisitorSecurityService.loadUserByUsername(username);
        return username + "\n" + visitor.getAuthorities();
    }

    public WatchListDTO getVisitorWatchlist() {
        Visitor user = parseVisitorSecurityService.parseVisitorFromToken();
        if (user == null) return new WatchListDTO(new ArrayList<>());
        List<Integer> watchlistIds = user.getWatchList();
        return new WatchListDTO(watchlistIds);
    }

    public boolean addToWatchList(Integer movie_db_id) {
        Visitor visitor = parseVisitorSecurityService.parseVisitorFromToken();
        List<Integer> watchList = visitor.getWatchList();
        if (watchList.contains(movie_db_id)) return false;

        watchList.add(movie_db_id);
        visitor.setWatchList(watchList);
        visitorDao.save(visitor);
        return true;
    }

    public boolean deleteFromWatchList(Integer movie_db_id) {
        Visitor visitor = parseVisitorSecurityService.parseVisitorFromToken();
        List<Integer> watchList = visitor.getWatchList();
        if (!watchList.contains(movie_db_id)) return false;

        watchList.remove(movie_db_id);
        visitor.setWatchList(watchList);
        visitorDao.save(visitor);
        return true;
    }
}
