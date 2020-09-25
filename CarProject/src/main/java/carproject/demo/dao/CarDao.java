package carproject.demo.dao;

import carproject.demo.model.Car;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarDao {
     int InsertNewCar(UUID id,Car newCar);

     default int InsertNewCar(Car newCar)
     {
         UUID id=UUID.randomUUID();
         return InsertNewCar(id,newCar);
     }

     ArrayList<Car> selectAllCars() throws SQLException;

     /*Optional<Car>*/ Car selectCarByVIN(String VIN);

     int deleteCarById(UUID id);

     int updateCarById(UUID id,Car altCar);

     ArrayList<Car> listCarByPrice(double lprice,double hprice);

}
