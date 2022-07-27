package com.spring.cloud.user.service;

import com.spring.cloud.user.entity.User;
import com.spring.cloud.user.feingclients.BikeFeignClient;
import com.spring.cloud.user.feingclients.CarFeignClient;
import com.spring.cloud.user.model.Bike;
import com.spring.cloud.user.model.Car;
import com.spring.cloud.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final RestTemplate restTemplate;

    private final CarFeignClient carFeignClient;

    private final BikeFeignClient bikeFeignClient;

    public List<User> getAll() throws Exception {
        return userRepository.findAll();
    }

    public User getUserSave(User user) throws Exception{
        User userNew = userRepository.save(user);
        return userNew;
    }

    public User getUserById(Integer id) throws Exception{
        return userRepository.findById(id).orElseThrow();
    }

    public List<Car> getCars(int userId) {
        List<Car> cars = restTemplate.getForObject("http://localhost:8002/car/carsUser/" + userId, List.class);
        return cars;
    }

    public List<Bike> getBikes(int userId) {
        List<Bike> bikes = restTemplate.getForObject("http://localhost:8003/bike/bikesUser/" + userId, List.class);
        return bikes;
    }

    public Car saveCar(int userId, Car car){
        car.setUserId(userId);
        return carFeignClient.saveCar(car);
    }

    public Bike saveBike(int userId, Bike bike){
        bike.setUserId(userId);
        return bikeFeignClient.saveBike(bike);
    }

    public Map<String, Object> getUserAndVehicles(int userId){
        Map<String, Object> result = new HashMap<>();
        User user = userRepository.findById(userId).orElse(null);
        if (user == null){
            result.put("Message", "user does not exist");
            return result;
        }
        result.put("User", user);
        List<Car> cars = carFeignClient.getCars(userId);
        if(cars.isEmpty()){
            result.put("Cars", "User does not have a car");
        }else{
            result.put("Cars", cars);
        }
        List<Bike> bikes = bikeFeignClient.getBikes(userId);
        if (bikes.isEmpty()) {
            result.put("Bikes", "User does not a bike");
        }else{
            result.put("Bikes", bikes);
        }
        return result;
    }
}
