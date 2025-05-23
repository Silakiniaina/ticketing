CREATE TABLE city(
   id INT,
   label VARCHAR(200) NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE _role_(
   id INT,
   label VARCHAR(50) NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE type_seat(
   id VARCHAR(50),
   label VARCHAR(150) NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE plane(
   id VARCHAR(50),
   model VARCHAR(150) NOT NULL,
   fabrication_date DATE NOT NULL,
   PRIMARY KEY(id),
   UNIQUE(model)
);

CREATE TABLE flight(
   id INT,
   departure_datetime TIMESTAMP NOT NULL,
   arrival_datetime TIMESTAMP,
   plane_id VARCHAR(50) NOT NULL,
   arrival_city_id INT NOT NULL,
   departure_city_id INT NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(plane_id) REFERENCES plane(id),
   FOREIGN KEY(arrival_city_id) REFERENCES city(id),
   FOREIGN KEY(departure_city_id) REFERENCES city(id)
);

CREATE TABLE _user_(
   id INT,
   last_name VARCHAR(150) NOT NULL,
   email VARCHAR(50) NOT NULL,
   password VARCHAR(50) NOT NULL,
   first_name VARCHAR(256),
   birth_date DATE,
   passport TEXT,
   PRIMARY KEY(id),
   UNIQUE(email)
);

CREATE TABLE gender(
   id INT,
   label VARCHAR(150) NOT NULL,
   PRIMARY KEY(id)
);

CREATE TABLE reservation(
   id INT,
   reservation_datetime TIMESTAMP DEFAULT NOW(),
   price DECIMAL(18,2) NOT NULL,
   promotion DECIMAL(5,2) DEFAULT 0,
   user_id INT NOT NULL,
   flight_id INT NOT NULL,
   type_seat_id VARCHAR(50) NOT NULL,
   PRIMARY KEY(id),
   FOREIGN KEY(user_id) REFERENCES _user_(id),
   FOREIGN KEY(flight_id) REFERENCES flight(id),
   FOREIGN KEY(type_seat_id) REFERENCES type_seat(id)
);

CREATE TABLE plane_seat(
   type_seat_id VARCHAR(50),
   plane_id VARCHAR(50),
   quantity INT NOT NULL,
   PRIMARY KEY(type_seat_id, plane_id),
   FOREIGN KEY(type_seat_id) REFERENCES type_seat(id),
   FOREIGN KEY(plane_id) REFERENCES plane(id)
);

CREATE TABLE flight_seat_promotion(
   type_seat_id VARCHAR(50),
   flight_id INT,
   percentage DECIMAL(5,2) DEFAULT 0,
   seat_number INT DEFAULT 0,
   PRIMARY KEY(type_seat_id, flight_id),
   FOREIGN KEY(type_seat_id) REFERENCES type_seat(id),
   FOREIGN KEY(flight_id) REFERENCES flight(id)
);

CREATE TABLE user_role(
   role_id INT,
   user_id INT,
   PRIMARY KEY(role_id, user_id),
   FOREIGN KEY(role_id) REFERENCES _role_(id),
   FOREIGN KEY(user_id) REFERENCES _user_(id)
);

CREATE TABLE user_gender(
   user_id INT,
   gender_id INT,
   PRIMARY KEY(user_id, gender_id),
   FOREIGN KEY(user_id) REFERENCES _user_(id),
   FOREIGN KEY(gender_id) REFERENCES gender(id)
);

CREATE TABLE flight_price_type_seat(
   type_seat_id VARCHAR(50),
   flight_id INT,
   price DECIMAL(18,2) NOT NULL,
   PRIMARY KEY(type_seat_id, flight_id),
   FOREIGN KEY(type_seat_id) REFERENCES type_seat(id),
   FOREIGN KEY(flight_id) REFERENCES flight(id)
);
