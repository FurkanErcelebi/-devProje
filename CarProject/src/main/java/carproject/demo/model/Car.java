package carproject.demo.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import org.hibernate.annotations.Check;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.lang.Nullable;

import javax.persistence.Table;
import javax.validation.constraints.*;
import java.time.Year;
import java.util.UUID;

//@Table(name = "Car")
public class Car
{


    private final UUID id;

    @NotBlank
    @Size(min = 5,max = 5,message = "5 karakter uznluğunda olmalı")
    @Pattern(regexp = "[A-Z]*[\\d]?[A-Z]*[\\d]?[A-Z]*",message = "Numarada en fazla 2 sayıdan ve diğerleri büyük harflerden oluşmalı")
    private final String VIN;

    @NotBlank
    @Size(min = 1,max = 14,message = "En fazla 14 karakter uznluğunda ve dolu olmalı")
    private final String make;

    @NotBlank
    @Size(min = 1,max = 20,message = "En fazla 20 karakter uznluğunda ve dolu olmalı")
    private final String model;

    @Min(value = 1800 ,message = "1800 den az girmeyin")
    @Max(value = 2020,message = "2020 den fazla girmeyin")
    private final int year;

    @Max(value = 45000,message = "45000 den az girmeyin")
    @Min(value = 200000,message = "200000 den fazla girmeyin")
    private final int mileage;

    @Min(value = 2000,message = "2000 den az girmeyin")
    @Max(value = 200000,message = "200000 den fazla girmeyin")
    private final double price;

    public Car(@JsonProperty("id") UUID id,
               @JsonProperty("VIN") String VIN,
               @JsonProperty("make") String make,
               @JsonProperty("model") String model,
               @JsonProperty("year") int year,
               @JsonProperty("mileage") int mileage,
               @JsonProperty("price") double price) {
        this.id = id;
        this.VIN = VIN;
        this.make = make;
        this.model = model;
        this.year = year;
        this.mileage = mileage;
        this.price = price;
    }

    public UUID getId() {
        return id;
    }

    public String getVIN() {
        return VIN;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getMileage() {
        return mileage;
    }

    public double getPrice() {
        return price;
    }

    /*@Override
    public String toString()
    {
        String makespace="",modelspace="",mileagespace="",pricespace="";
        while(makespace.length()+make.length()<14)
        {
            makespace+=" ";
        }
        while(modelspace.length()+model.length()<20)
        {
            modelspace+=" ";
        }
        while(mileagespace.length()+Integer.toString(mileage).length()<7)
        {
            mileagespace+=" ";
        }
        while(pricespace.length()+Double.toString(price).length()<10)
        {
            pricespace+=" ";
        }

        return "|" + getVIN() + " " +
                "|" + getMake() + makespace +
                "|" + getModel() + modelspace +
                "|" + getYear() + " " +
                "|" + getMileage() + mileagespace +
                "|" + getPrice() + pricespace +
                "|";
    }*/
}
