package com.spring.cloud.user.feingclients;

import com.spring.cloud.user.model.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
//import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "car", url = "http://localhost:8002/car")
//@RequestMapping("/car")
public interface CarFeignClient {

    @PostMapping
    public Car saveCar(@RequestBody Car car);

    @GetMapping("carsUser/{userId}")
    public List<Car> getCars(@PathVariable int userId);
}
