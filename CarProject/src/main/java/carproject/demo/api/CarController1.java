package carproject.demo.api;

import carproject.demo.model.Car;
import carproject.demo.sevice.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

@RequestMapping("/api/v1/car/list")
@RestController
public class CarController1 {

    final CarService carService;

    @Autowired
    public CarController1(CarService carService) {
        this.carService = carService;
    }

    @GetMapping(path = "{lprice}-{hprice}")
    public ArrayList<Car> ListingCars(@PathVariable("lprice") @NotNull double lprice, @PathVariable("hprice") @NotNull double hprice)
    {
        return carService.listCars(lprice,hprice);
    }
}
