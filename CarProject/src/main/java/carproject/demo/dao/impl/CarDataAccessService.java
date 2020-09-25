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
        
        if (selectCarByVIN(newCar.getVIN()).getVIN()!="0") return -1;
        if (selectCarByMakerandModel(newCar.getMake(),newCar.getModel()).getVIN()!="0") return -2;
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
    }

    @Override
    public /*Optional<Car>*/ Car selectCarByVIN(String VIN) {
        final  String sqlquery="SELECT id,VIN,make,model,year,mileage,price FROM  car WHERE VIN='"+VIN+"';"//?;";


        ArrayList<Car> car= (ArrayList<Car>) jdbcTemplate.query(sqlquery,/*queryForObject(
                sqlquery,
                new Object[]{VIN},*/
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

        if (car.isEmpty()) return new Car(UUID.randomUUID(),"0","Aradığınız VIN numarasında kayıtlı bir araba yok","",0,0,0);
        return car.get(0);
    }
    
     public Car selectCarByMakerandModel(String make,String model) {
        final  String sqlquery="SELECT id,VIN,make,model,year,mileage,price FROM  car WHERE make='"+make+"' AND model='"+model+"';";//?;";


        ArrayList<Car> car=  (ArrayList<Car>) jdbcTemplate.query(sqlquery,/*queryForObject(
                sqlquery,
                new Object[]{make},*/
                (resultSet, i) -> {
                    String newstr=resultSet.getString("id");
                    UUID id= UUID.fromString(newstr);
                    String VIN=resultSet.getString("VIN");
                    String carmake=resultSet.getString("make");
                    String carmodel=resultSet.getString("model");
                    int year=resultSet.getInt("year");
                    int mileage=resultSet.getInt("mileage");
                    double price=resultSet.getDouble("price");
                    return new Car(id,VIN,carmake,carmodel,year,mileage,price);
                });

        if (car.isEmpty()) return new Car(UUID.randomUUID(),"0","Aradığınız maker ve model alanı kayıtlı bir araba yok","",0,0,0);
        return car.get(0);
    }

    public /*Optional<Car>*/ Car selectCarById(UUID id) {
        final  String sqlquery="SELECT id,VIN,make,model,year,mileage,price FROM  car WHERE id=?;";


        ArrayList<Car> car= (ArrayList<Car>) jdbcTemplate.query(sqlquery,/*queryForObject(
                sqlquery,
                new Object[]{make},*/
                (resultSet, i) -> {
                    String newstr=resultSet.getString("id");
                    UUID carid= UUID.fromString(newstr);
                    String VIN=resultSet.getString("VIN");
                    String make=resultSet.getString("make");
                    String model=resultSet.getString("model");
                    int year=resultSet.getInt("year");
                    int mileage=resultSet.getInt("mileage");
                    double price=resultSet.getDouble("price");
                    return new Car(carid,VIN,make,model,year,mileage,price);
                });

        if (car.isEmpty()) return new Car(UUID.randomUUID(),"0","Aradığınız id numarasında kayıtlı bir araba yok","",0,0,0);
        return car.get(0);
    }

    @Override
    public int deleteCarById(UUID id) {
        final String sqlquery="DELETE FROM car WHERE id='"+id.toString()+"';";

        if (selectCarById(id).getVIN()=="0")
        {
            return -1;
        }
        else return jdbcTemplate.update(sqlquery);
    }

    @Override
    public int updateCarById(UUID id, Car altCar) {
        final String sqlquery="UPDATE car SET VIN='"+altCar.getVIN()+"'"
                +",make='"+altCar.getMake()
                +"',model='"+altCar.getModel()
                +"',year="+altCar.getYear()
                +",mileage="+altCar.getMileage()
                +",price="+altCar.getPrice()
                +" WHERE id='"+id.toString()+"';";

        if(selectCarByVIN(altCar.getVIN()).getVIN()!="0") {
            if (!selectCarByVIN(altCar.getVIN()).getId().equals(id)) return -1;
        }
        if (selectCarByMakerandModel(altCar.getMake(),altCar.getModel()).getVIN()!="0") {
            if (!selectCarByMakerandModel(altCar.getMake(), altCar.getModel()).getId().equals(id)) return -2;
        }
        else return jdbcTemplate.update(sqlquery);
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
