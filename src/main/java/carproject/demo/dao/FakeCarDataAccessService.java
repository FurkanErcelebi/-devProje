package carproject.demo.dao;

import carproject.demo.model.Car;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Repository("fakeDao")
public class FakeCarDataAccessService /*implements CarDao*/{

    public static ArrayList<Car> carArrayList=new ArrayList<Car>();

    /*@Override
    public int InsertNewCar(UUID id, Car newCar) {
        carArrayList.add(new Car(id,
                newCar.getVIN(),
                newCar.getMake(),
                newCar.getModel(),
                newCar.getYear(),
                newCar.getMileage(),
                newCar.getPrice()));
        return 1;
    }

    @Override
    public ArrayList<Car> selectAllCars() {
        return carArrayList;
    }

    @Override
    public Optional<Car> selectCarByVIN(UUID id) {
        return carArrayList.stream().filter(car -> car.getVIN().equals(id)).findFirst();
    }

    @Override
    public int deleteCarById(UUID id) {
        Optional<Car> selectedCar= null;//selectCarByVIN(id);
        if (!selectedCar.isPresent()){
            return 0;
        }
        carArrayList.remove(selectedCar.get());
        return 1;
    }

    @Override
    public int updateCarById(UUID id, Car altCar) {
        return 0*//*selectCarByVIN(id)
                .map(car -> {
                    int indexOfCar=carArrayList.indexOf(car);
                    if (indexOfCar>=0)
                    {
                        carArrayList.set(indexOfCar,new Car(id,
                                altCar.getVIN(),
                                altCar.getMake(),
                                altCar.getModel(),
                                altCar.getYear(),
                                altCar.getMileage(),
                                altCar.getPrice()));
                        return 1;
                    }
                    return 0;
                })
                .orElse(0)*//*;
    }

    @Override
    public ArrayList<Car> listCarByPrice(double lprice, double hprice) {
        return null;
    }*/
}
