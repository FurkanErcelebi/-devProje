CREATE TABLE IF NOT EXISTS Car(
   id  VARCHAR(36) NOT NULL ,
   VIN VARCHAR(5) NOT NULL ,
   make VARCHAR(15) NOT NULL ,
   model VARCHAR(25) NOT NULL ,
   year INTEGER ,
   mileage INTEGER ,
   price DOUBLE,
   PRIMARY KEY(id)
)ENGINE=InnoDB;