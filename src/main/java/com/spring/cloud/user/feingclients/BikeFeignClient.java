package com.spring.cloud.user.feingclients;

import com.spring.cloud.user.model.Bike;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
//import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "bike", url = "http://localhost:8003/bike")
//@RequestMapping("/bike")
public interface BikeFeignClient {

    @PostMapping
    public Bike saveBike(@RequestBody Bike bike);

    @GetMapping("bikesUser/{userId}")
    public List<Bike> getBikes(@PathVariable int userId);

}
