package carproject.demo.api;

import carproject.demo.model.Car;
import carproject.demo.sevice.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

@RequestMapping("/api/v1/car")
@RestController
public class CarController {

    private final CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    public String AddCars(@RequestBody Car newCar)
    {
        if (carService.AddNewCar(newCar)==1) return "Yeni araba kaydı eklendi";
        else return "Yeni kayıt ekelnilemedi";
    }

    @GetMapping
    public ArrayList<Car> ShowCars() throws SQLException {
        return carService.getAllCars();
    }

    @NotNull @Valid
    @GetMapping(path = "{VIN}")
    public Car SearchingCar(@PathVariable("VIN")String VIN )
    {
        return carService.getCarById(VIN).orElse(null);
    }

    @DeleteMapping(path = "{id}")
    public String DeletingCar(@PathVariable("id") UUID id)
    {
        if (carService.deleteCar(id)==1) return "Kayıt silindi";
        else return "Kayıt silinemedi";
    }

    @PutMapping(path = "{id}")
    public String UpdatingCar(@PathVariable("id") UUID id ,@NotNull @Valid @RequestBody  Car altCar)
    {
        if (carService.updateCar(id,altCar)==1) return "Kayıt güncellendi";
        else return "Kayıt güncelleme başarısız oldu";
    }

}
