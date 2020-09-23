package carproject.demo.sevice;

import carproject.demo.dao.CarDao;
import carproject.demo.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Service
public class CarService {

    private final CarDao carDao;

    @Autowired
    public CarService(@Qualifier("mysql") CarDao carDao) {
        this.carDao = carDao;
    }

    public int AddNewCar(Car newCar)
    {
        return carDao.InsertNewCar(newCar);
    }

    public ArrayList<Car> getAllCars() throws SQLException {
        return carDao.selectAllCars();
    }

    public Optional<Car> getCarById(String VIN)
    {
        return carDao.selectCarByVIN(VIN);
    }

    public int deleteCar(UUID id)
    {
        return carDao.deleteCarById(id);
    }

    public int updateCar(UUID id ,Car altCar)
    {
        return carDao.updateCarById(id,altCar);
    }

    public ArrayList<Car> listCars(double lprice,double hprice)
    {
        return carDao.listCarByPrice(lprice,hprice);
    }
}
