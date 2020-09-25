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
        String VIN=newCar.getVIN()
                ,make=newCar.getMake()
                ,model=newCar.getModel()
                ,newmake=""
                ,newmodel=""
                ,year=Integer.toString(newCar.getYear())
                ,mileage=Integer.toString(newCar.getMileage())
                ,price=Double.toString(newCar.getPrice()),regular_expression,matched;
        int result;
        boolean VINcorrect,makecorrect,modelcorrect,yearcorrect,mileagecorrect,pricecorrect;
        int deci,one;
        VINcorrect=false;
        regular_expression="[A-Z0-9]{5}";
        matched=VIN;
        if (!Pattern.matches(regular_expression,matched))
        {
            return "VIN 5 karakter uzunluğunda , büyük harf ve sayı karakterinden oluşmalı";
        }
        else {
            regular_expression="[A-Z]*[\\d]?[A-Z]*[\\d]?[A-Z]*";
            if (!Pattern.matches(regular_expression,matched))
            {
                return "VIN en fazla 2 sayıdan ve diğerleri büyük harflerden oluşmalı";
            }
            else VINcorrect=true;
        }
        makecorrect=false;
        newmake=make.replace(" ","_");
        if(newmake.length()>14)
        {
            return "Marka ismi 13 karakterden uzun olmamalı";
        }
        else if(newmake.length()==0)
        {
            return "Marka alanı boş";
        }
        else makecorrect=true;
        modelcorrect=false;
        newmodel=model.replace(" ","_");
        if(newmodel.length()>20)
        {
            return "Model ismini 20'den fazla karakter girmeyiniz";
        }
        else if (newmodel.length()==0)
        {
            return "Model alanı boş";
        }
        else modelcorrect=true;
        yearcorrect=false;
        Year curryear=Year.now();
        regular_expression="[0-9]{4}";
        matched=year;
        if (!Pattern.matches(regular_expression,matched))
        {
            return "Year numarası 4 karakterli ve sadece sayılardan olmalı";
        }
        else
        {
            one=Integer.parseInt(curryear.toString())%10;
            deci=(Integer.parseInt(curryear.toString())%100-one)/10;
            regular_expression="(18(\\d)(\\d))|(19(\\d)(\\d))|20[0-"+Integer.toString(deci)+"][0-"+Integer.toString(one)+"]";
            if (!Pattern.matches(regular_expression,year))
            {
                return "Year alanına 1800 ile "+curryear.toString()+" arasında bir yıl giriniz";
            }
            else yearcorrect=true;
        }
        mileagecorrect=false;
        regular_expression="[0-9]{5,6}";
        matched=mileage;
        if (!Pattern.matches(regular_expression,matched))
        {
            return "Mileage, numarası 6 veya 5 karakter zunluğunda ve sadece sayilardan olmalı";
        }
        else
        {
            regular_expression="([4-9][5-9](\\d)(\\d)(\\d)|(1)?(\\d)(\\d)(\\d)(\\d)(\\d))|200000";
            if (!Pattern.matches(regular_expression,matched))
            {
                return "Mileage, 45000 ile 200000 arasında bir değer olmalı";
            }
            else mileagecorrect=true;
        }
        pricecorrect=false;
        regular_expression="(.){7,9}";
        matched=price;
        if (!Pattern.matches(regular_expression,matched))
        {
            return "Price numarası 7 ile 9 karakterden arasında uzunluk olmalı";
        }
        else
        {
            regular_expression="[2-9](\\d)(\\d)(\\d).(\\d)(\\d)?|(1)?(\\d)(\\d)(\\d)(\\d)(\\d).(\\d)(\\d)?|200000.(\\d)(\\d)?";
            if (!Pattern.matches(regular_expression,matched))
            {
                return "Price , 2000.0 ile 200000.99 arasında sayısal bir değer olmalı ve nokta bulunmalı";
            }
            else pricecorrect = true;
        }

        if (VINcorrect && makecorrect && modelcorrect && yearcorrect && mileagecorrect && pricecorrect)
        {
            result=carService.AddNewCar(newCar);
            if (result==1) return "Yeni araba kaydı eklendi";
            else if (result==-1) return "Aynı VIN numarasınd araba kaydı var";
            else if (result==-2) return "Aynı marka ve modelde araba kaydı var";
        else return "Yeni kayıt eklenilemedi";
        }
        else return "Yeni kayıt eklenilemedi";
        
    }

    @GetMapping
    public ArrayList<Car> ShowCars() throws SQLException {
        return carService.getAllCars();
    }

    @NotNull @Valid
    @GetMapping(path = "{VIN}")
    public Car SearchingCar(@PathVariable("VIN")String VIN )
    {
        String regular_expression,matched;
        regular_expression="[A-Z0-9]{5}";
        matched=VIN;
        if (!Pattern.matches(regular_expression,matched))
        {
            return new Car(UUID.randomUUID(),"","VIN 5 karakter uzunluğuda girilir ", "Büyük harf ve sayı karakterinden oluşmalı",0,0,0);
        }
        else {
            regular_expression="[A-Z]*[\\d]?[A-Z]*[\\d]?[A-Z]*";
            if (!Pattern.matches(regular_expression,matched))
            {
                return new Car(UUID.randomUUID(),"","VIN en fazla 2 sayı girilir" ,"Sayılar dışı büyük harfler girilir",0,0,0);
            }
            else
            {
                return carService.getCarById(VIN);
            }
        }
    }

    @DeleteMapping(path = "{id}")
    public String DeletingCar(@PathVariable("id") UUID id)
    {
        int result;
        String matched,regular_expression;
        boolean correct=false;
        regular_expression="[0-9a-f]{8}(-)[0-9a-f]{4}(-)[0-9a-f]{4}(-)[0-9a-f]{4}(-)[0-9a-f]{12}";
        matched=id.toString();
        if (!Pattern.matches(regular_expression,matched))
        {
            return "Uygun id numarası girilmedi";
        }
        else
        {
            result = carService.deleteCar(id);
            if (result==1) return "Kayıt silindi";
            elae if (result==-1) return "Verdiğiniz numara için kayıt bulunamadı";
            else return "Kayıt silinemedi";
        }
    }

    @PutMapping(path = "{id}")
    public String UpdatingCar(@PathVariable("id") UUID id ,@NotNull @Valid @RequestBody  Car altCar)
    {
        int deci,one,result;
        String matched,regular_expression;
        boolean VINcorrect,makecorrect,modelcorrect,yearcorrect,mileagecorrect,pricecorrect,idcorrect=false;
        regular_expression="[0-9a-f]{8}(-)[0-9a-f]{4}(-)[0-9a-f]{4}(-)[0-9a-f]{4}(-)[0-9a-f]{12}";
        matched=id.toString();
        if (!Pattern.matches(regular_expression,matched))
        {
            return "Uygun id numarası girilmedi";
        }
        else idcorrect=true;
        String VIN=altCar.getVIN()
                ,make=altCar.getMake()
                ,model=altCar.getModel()
                ,newmake=""
                ,newmodel=""
                ,year=Integer.toString(altCar.getYear())
                ,mileage=Integer.toString(altCar.getMileage())
                ,price=Double.toString(altCar.getPrice());
        VINcorrect=false;
        regular_expression="[A-Z0-9]{5}";
        matched=VIN;
        if (!Pattern.matches(regular_expression,matched))
        {
            return "VIN 5 karakter uzunluğunda , büyük harf ve sayı karakterinden oluşmalı";
        }
        else {
            regular_expression="[A-Z]*[\\d]?[A-Z]*[\\d]?[A-Z]*";
            if (!Pattern.matches(regular_expression,matched))
            {
                return "VIN en fazla 2 sayıdan ve diğerleri büyük harflerden oluşmalı";
            }
            else VINcorrect=true;
        }
        makecorrect=false;
        newmake=make.replace(" ","_");
        if(newmake.length()>14)
        {
            return "Marka ismi 13 karakterden uzun olmamalı";
        }
        else if(newmake.length()==0)
        {
            return "Marka alanı boş";
        }
        else makecorrect=true;
        modelcorrect=false;
        newmodel=model.replace(" ","_");
        if(newmodel.length()>20)
        {
            return "Model ismini 20'den fazla karakter girmeyiniz";
        }
        else if (newmodel.length()==0)
        {
            return "Model alanı boş";
        }
        else modelcorrect=true;
        yearcorrect=false;
        Year curryear=Year.now();
        regular_expression="[0-9]{4}";
        matched=year;
        if (!Pattern.matches(regular_expression,matched))
        {
            return "Year numarası 4 karakterli ve sadece sayılardan olmalı";
        }
        else
        {
            one=Integer.parseInt(curryear.toString())%10;
            deci=(Integer.parseInt(curryear.toString())%100-one)/10;
            regular_expression="(18(\\d)(\\d))|(19(\\d)(\\d))|20[0-"+Integer.toString(deci)+"][0-"+Integer.toString(one)+"]";
            if (!Pattern.matches(regular_expression,year))
            {
                return "Year alanına 1800 ile "+curryear.toString()+" arasında bir yıl giriniz";
            }
            else yearcorrect=true;
        }
        mileagecorrect=false;
        regular_expression="[0-9]{5,6}";
        matched=mileage;
        if (!Pattern.matches(regular_expression,matched))
        {
            return "Mileage, numarası 6 veya 5 karakter zunluğunda ve sadece sayilardan olmalı";
        }
        else
        {
            regular_expression="([4-9][5-9](\\d)(\\d)(\\d)|(1)?(\\d)(\\d)(\\d)(\\d)(\\d))|200000";
            if (!Pattern.matches(regular_expression,matched))
            {
                return "Mileage, 45000 ile 200000 arasında bir değer olmalı";
            }
            else mileagecorrect=true;
        }
        pricecorrect=false;
        regular_expression="(.){7,9}";
        matched=price;
        if (!Pattern.matches(regular_expression,matched))
        {
            return "Price numarası 7 ile 9 karakterden arasında uzunluk olmalı";
        }
        else
        {
            regular_expression="[2-9](\\d)(\\d)(\\d).(\\d)(\\d)?|(1)?(\\d)(\\d)(\\d)(\\d)(\\d).(\\d)(\\d)?|200000.(\\d)(\\d)?";
            if (!Pattern.matches(regular_expression,matched))
            {
                return "Price , 2000.0 ile 200000.99 arasında sayısal bir değer olmalı ve nokta bulunmalı";
            }
            else pricecorrect = true;
        }

        if (idcorrect && VINcorrect && makecorrect && modelcorrect && yearcorrect && mileagecorrect && pricecorrect)
        {
            result = carService.updateCar(id,altCar);
            if (result==1) return "Kayıt güncellendi";
            if (result==-1) return "Aynı VIN numarada araba kaydı var";
            if (result==-2) retırn "Aynı marka ve modelde araba kaydı var"
            else return "Kayıt güncelleme başarısız oldu";
        }
        else return "Kayıt güncelleme başarısız oldu";
    }

}
