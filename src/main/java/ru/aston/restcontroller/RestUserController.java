package ru.aston.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.aston.DAO.UserDao;
import ru.aston.model.User;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rest")
public class RestUserController {
    private final UserDao userDao;

    public RestUserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userDao.index());
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody @Valid User user) {
        try {
            userDao.save(user);
            return ResponseEntity.ok(userDao.show(user.getId()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") int id, @RequestBody @Valid User user) {
        try {
            userDao.update(id, user);
            return ResponseEntity.ok(userDao.show(user.getId()));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") int id) {
        try {
            userDao.delete(id);
            return ResponseEntity.ok("Delete success.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
