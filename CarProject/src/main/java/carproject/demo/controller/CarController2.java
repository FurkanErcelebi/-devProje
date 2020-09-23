package carproject.demo.controller;

import carproject.demo.model.Car;
import carproject.demo.sevice.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.sql.SQLException;

@Controller
public class CarController2 {

    private final CarService carService;

    @Autowired
    public CarController2(CarService carService) {
        this.carService = carService;
    }

     @GetMapping(path = "/cars/new-addcar")
    //@RequestMapping(value = "new-addcar")
    public String BeginAddCars(Car car)//,Model model)
    {
        //model.addAttribute("car",car);
        return "CarAdding";
    }

    @PostMapping(path = "/cars/addcar")
    public String EndAddCars(@Valid Car car, Model model,BindingResult result) throws SQLException
    {
        if (result.hasErrors())
        {
            return "CarAdding";
        }

        carService.AddNewCar(car);
        model.addAttribute("cars",carService.getAllCars());
        return "redirect:table";
    }

    @GetMapping(path = "/cars/table")
    public String ShowCars(Model model) throws SQLException {

        model.addAttribute("cars",carService.getAllCars());
        return "CarTable";
    }


    public String DeleteACarByVIN(Model model)
    {
        return "CarDelete";
    }
    public String SearcACarByVIN(Model model)
    {
        return "CarSearch";
    }
    public String ListCarsByPrice(Model model)
    {
        return "CarListing";
    }
}
