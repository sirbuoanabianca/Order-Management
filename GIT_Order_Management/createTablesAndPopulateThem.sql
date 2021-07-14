DROP DATABASE IF EXISTS warehouse;
CREATE DATABASE IF NOT EXISTS warehouse;
USE warehouse;

CREATE TABLE IF NOT EXISTS `client`
(`id` INT NOT NULL AUTO_INCREMENT,
`name` VARCHAR(50) NOT NULL,
`address` VARCHAR(100) NOT NULL,
`email` VARCHAR(50) NOT NULL,
`birth_date` DATE,
 PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS `product`
(`id` INT NOT NULL AUTO_INCREMENT,
`name` VARCHAR(100) NOT NULL,
`stock` INT NOT NULL,
`price` DECIMAL(6,2) NOT NULL,
 PRIMARY KEY(id)
);

CREATE TABLE IF NOT EXISTS `order`
(`id` INT NOT NULL AUTO_INCREMENT,
`id_client` INT NOT NULL, 
`id_product` INT NOT NULL,
`quantity` INT NOT NULL,
CONSTRAINT `fk_order_client` FOREIGN KEY (`id_client`) REFERENCES `client`(`id`) ON DELETE CASCADE,
CONSTRAINT `fk_order_product` FOREIGN KEY (`id_product`) REFERENCES `product`(`id`) ON DELETE CASCADE,
 PRIMARY KEY(id)
);


INSERT INTO `client`(`name`,`address`,`email`,`birth_date`)
VALUES
('Popescu Maria','str.Observator,Cluj-Napoca,Cluj',"popescu.maria@gmail.com","2001-06-15"),
('Constantin Ana','str.Baritiu,Cluj-Napoca,Cluj',"ana2000@yahoo.com","2000-01-01"),
('Petre Andra','str.Gheorghe Lazar,Cluj-Napoca,Cluj',"andraPetre@gmail.com","2002-02-12"),
('Rusu Emilia','str.Salcamului,Campia Turzii,Cluj',"emiliaRusu@gmail.com","1989-10-08"),
('Suciu Dan','str.Izvorului,Campia Turzii,Cluj',"dan_suciu7@yahoo.com","1970-04-25"),
('Szekely Alexandra','str.Aurel Vlaicu,Campia Turzii,Cluj',"ale79szekely@yahoo.com","1979-12-02"),
('Ionescu Raluca','str.Garii,Turda,Cluj',"ionescu.raluca1@gmail.com","1989-10-08"),
('Ifrim George','str.Avram Iancu,Fagaras,Brasov',"george5ifrim@yahoo.com","1993-05-13"),
('Aron Mihai','str.Mihai Eminescu,Timisoara,Timis',"amihai6@yahoo.com","1996-03-12"),
('Onaca Silviu','str.Decebal,Medias,Sibiu',"onaca_silviu@gmail.com","1990-11-11"),
('Cret Alina','str.1 Decembrie 1989,Iasi,Iasi',"alina81@yahoo.com","1981-12-30"),
('Pop Stefan','str.Republicii,Barlad,Vaslui',"stefanPop@gmail.com","1998-02-12");

INSERT INTO `product`(`name`,`stock`,`price`)
VALUES
('Robot de aspirare Roborock Cleaner',30,1900),
('Masina de cusut Minerva',16,850),
('Frigider minibar Star-Light',45,399.99),
('Boxe Horizon Acustico',33,489.99),
('Imprimanta laser monocrom HP Laserjet',9,320);

--  DELETE FROM `order`;



