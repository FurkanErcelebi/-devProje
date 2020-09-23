package carproject.demo.dao.impl;

import carproject.demo.dao.CarDao;
import carproject.demo.model.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@Repository("mysql")
public class CarDataAccessService implements CarDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CarDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int InsertNewCar(UUID id, Car newCar)
    {
        final String sqlquery="INSERT INTO car (id, VIN, make, model, year, mileage, price) VALUES ('"
                +id.toString()+"','"
                +newCar.getVIN()+"','"
                +newCar.getMake()+"','"
                +newCar.getModel()+"',"
                +newCar.getYear()+","
                +newCar.getMileage()+","
                +newCar.getPrice()+");";

        return jdbcTemplate.update(sqlquery);
    }

    @Override
    public ArrayList<Car> selectAllCars(){
        final  String sqlquery="SELECT id,VIN,make,model,year,mileage,price FROM  car;";

        return (ArrayList<Car>) jdbcTemplate.query(sqlquery,(resultSet, i) -> {
            String newstr=resultSet.getString("id");
            UUID id= UUID.fromString(newstr);
            String VIN=resultSet.getString("VIN");
            String make=resultSet.getString("make");
            String model=resultSet.getString("model");
            int year=resultSet.getInt("year");
            int mileage=resultSet.getInt("mileage");
            double price=resultSet.getDouble("price");
            return new Car(id,VIN,make,model,year,mileage,price);
        });
        /*ArrayList<Car> aCar=new ArrayList<Car>();
        aCar.add(new Car(UUID.randomUUID(),"1FMEU","Lexus","LS",2011,153219,108037.92));
        return aCar;*/
    }

    @Override
    public Optional<Car> selectCarByVIN(String VIN) {
        final  String sqlquery="SELECT id,VIN,make,model,year,mileage,price FROM  car WHERE VIN=?;";


        Car car= jdbcTemplate.queryForObject(
                sqlquery,
                new Object[]{VIN},
                (resultSet, i) -> {
                    String newstr=resultSet.getString("id");
                    UUID id= UUID.fromString(newstr);
                    String carVIN=resultSet.getString("VIN");
                    String make=resultSet.getString("make");
                    String model=resultSet.getString("model");
                    int year=resultSet.getInt("year");
                    int mileage=resultSet.getInt("mileage");
                    double price=resultSet.getDouble("price");
                    return new Car(id,carVIN,make,model,year,mileage,price);
                });

        return Optional.ofNullable(car);
        /*Optional<Car> car = null;
        return  car;*/
    }

    @Override
    public int deleteCarById(UUID id) {
        final String sqlquery="DELETE FROM car WHERE id='"+id.toString()+"';";

        return jdbcTemplate.update(sqlquery);
    }

    @Override
    public int updateCarById(UUID id, Car altCar) {
        final String sqlquery="UPDATE car SET make='"+altCar.getMake()
                +"',model='"+altCar.getModel()
                +"',year="+altCar.getYear()
                +",mileage="+altCar.getMileage()
                +",price="+altCar.getPrice()
                +" WHERE id='"+id.toString()+"';";

        return jdbcTemplate.update(sqlquery);
    }

    @Override
    public ArrayList<Car> listCarByPrice(double lprice, double hprice) {
        final String sqlquery="SELECT id,VIN,make,model,year,mileage,price FROM  car WHERE price<="+Double.toString(hprice)+" AND "+Double.toString(lprice)+"<=price;";

        return (ArrayList<Car>) jdbcTemplate.query(sqlquery,(resultSet, i) -> {
            String newstr=resultSet.getString("id");
            UUID id= UUID.fromString(newstr);
            String VIN=resultSet.getString("VIN");
            String make=resultSet.getString("make");
            String model=resultSet.getString("model");
            int year=resultSet.getInt("year");
            int mileage=resultSet.getInt("mileage");
            double price=resultSet.getDouble("price");
            return new Car(id,VIN,make,model,year,mileage,price);
        });
    }
}
