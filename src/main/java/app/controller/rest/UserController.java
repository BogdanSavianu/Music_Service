package app.controller.rest;

import app.dto.UserDTO;
import app.model.User;
import app.service.UserService;
import app.single_point_access.ServiceSinglePointAccess;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController

// every request to http://localhost:8080/user will enter on this controller
@RequestMapping("/user")
public class UserController {

    private UserService userService = ServiceSinglePointAccess.getUserService();

    // Map http://localhost:8080/user/all
    // Get - to take data from server
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {

        // Return a Response which has a status and a body (data)
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    // {id} - is a value sent by url and is named path variable
    // {id} - will be taken from path - Path Variable
    // Attention - GET doesn't have a body
    @GetMapping("/id/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    // Post - create data
    // RequestBody - is the data sent to server through request
    // For POST, PUT, DELETE we can send information both : Path Variable & RequestBody
    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.save(user));
    }

    // Put - update data
    // Put with path variable
    @PutMapping("/updateName/{id}/{name}")
    public ResponseEntity<User> updateUserName(@PathVariable Integer id, @PathVariable String name) {
        User user = userService.findById(id);
        user.setName(name);
        User userUpdated = userService.update(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/update")
    public ResponseEntity<User> update(@RequestBody User user) {
        User userFromDB = userService.findById(user.getId());
        userFromDB.setName(user.getName());
        userFromDB.setEmail(user.getEmail());
        userFromDB.setPassword(user.getPassword());
        userFromDB.setPlaylists(user.getPlaylists());
        User userUpdated = userService.update(userFromDB);
        return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
    }

    // Delete
    @DeleteMapping("/delete")
    public ResponseEntity<Boolean> deleteById(@RequestBody Integer id) {
        User user = userService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(userService.delete(user));
    }

    @Operation(summary = "Get details (name, email, password, list of songs) from all users")
    @GetMapping("/details_all")
    public ResponseEntity<List<UserDTO>> getAllUserDetails() {

        List<User> users = userService.findAll();
        List<UserDTO> usersDTO = new ArrayList<>();

        for (User user : users) {
            UserDTO userDTO = new UserDTO();
            userDTO.setName(user.getName());
            userDTO.setEmail(user.getEmail());
            userDTO.setPassword(user.getPassword());
            userDTO.setPlaylists(user.getPlaylists());

            usersDTO.add(userDTO);
        }

        return ResponseEntity.status(HttpStatus.OK).body(usersDTO);
    }

}