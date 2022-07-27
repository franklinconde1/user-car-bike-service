package com.spring.cloud.user.controller;

import com.spring.cloud.user.entity.User;
import com.spring.cloud.user.model.Bike;
import com.spring.cloud.user.model.Car;
import com.spring.cloud.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("user/{id}")
    public ResponseEntity<User> getById(@PathVariable Integer id) throws Exception {
        User userId = userService.getUserById(id);
        if (userId == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userId);
    }

    @GetMapping("users")
    public ResponseEntity<List<User>> getAll() throws Exception {
        List<User> users = userService.getAll();
        if (users.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

        @PostMapping
        public ResponseEntity<User> getUserSave(@RequestBody User user) throws Exception {
         User userNew = userService.getUserSave(user);
         return ResponseEntity.ok(userNew);
    }

        @GetMapping("cars/{userId}")
        public ResponseEntity<List<Car>> getCars(@PathVariable int userId) throws Exception {
            User user = userService.getUserById(userId);
            if (user == null){
                return ResponseEntity.notFound().build();
            }
            List<Car> cars = userService.getCars(userId);
            return ResponseEntity.ok(cars);
        }

        @GetMapping("bikes/{userId}")
        public ResponseEntity<List<Bike>> getBike(@PathVariable int userId) throws Exception {
            User user = userService.getUserById(userId);
            if (user == null){
            return ResponseEntity.notFound().build();
            }
            List<Bike> bikes = userService.getBikes(userId);
            return ResponseEntity.ok(bikes);
    }

        @PostMapping("savecar/{userId}")
        public ResponseEntity<Car> getSaveCar(@PathVariable int userId, @RequestBody Car car) throws Exception {
            if (userService.getUserById(userId) == null){
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(userService.saveCar(userId, car));
    }

        @PostMapping("savebike/{userId}")
        public ResponseEntity<Bike> getSaveBike(@PathVariable int userId, @RequestBody Bike bike) throws Exception {
            if (userService.getUserById(userId) == null){
                return ResponseEntity.notFound().build();
            }
        return ResponseEntity.ok(userService.saveBike(userId, bike));
    }

    @GetMapping("getAll/{userId}")
    public ResponseEntity<Map<String, Object>> getVehiclesOfUser(@PathVariable int userId){
        Map<String, Object> result = userService.getUserAndVehicles(userId);
        return ResponseEntity.ok(result);
    }
}
