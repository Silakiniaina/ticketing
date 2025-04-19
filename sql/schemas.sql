CREATE TABLE city(
    id INTEGER,
    label VARCHAR(200)  NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE role(
    id INTEGER,
    label VARCHAR(50)  NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE type_seat(
    id VARCHAR(50) ,
    label VARCHAR(150)  NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE plane(
    id VARCHAR(50) ,
    model VARCHAR(150)  NOT NULL,
    fabrication_date DATE NOT NULL,
    PRIMARY KEY(id),
    UNIQUE(model)
);

CREATE TABLE flight(
    id INTEGER,
    departure_datetime TIMESTAMP NOT NULL,
    arrival_datetime TIMESTAMP,
    plane_id VARCHAR(50)  NOT NULL,
    arrival_city_id INTEGER NOT NULL,
    departure_city_id INTEGER NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(plane_id) REFERENCES plane(id),
    FOREIGN KEY(arrival_city_id) REFERENCES city(id),
    FOREIGN KEY(departure_city_id) REFERENCES city(id)
);

CREATE TABLE user_(
    id INTEGER,
    last_name VARCHAR(150)  NOT NULL,
    email VARCHAR(50)  NOT NULL,
    password VARCHAR(50)  NOT NULL,
    first_name VARCHAR(256) ,
    birth_date DATE,
    passport TEXT,
    PRIMARY KEY(id),
    UNIQUE(email)
);

CREATE TABLE gender(
    id INTEGER,
    label VARCHAR(150)  NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE reservation(
    id INTEGER,
    reservation_datetime TIMESTAMP DEFAULT NOW(),
    price NUMERIC(18,2) NOT NULL,
    promotion NUMERIC(5,2)  DEFAULT 0,
    user_id INTEGER NOT NULL,
    flight_id INTEGER NOT NULL,
    type_seat_id VARCHAR(50)  NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(user_id) REFERENCES user_(id),
    FOREIGN KEY(flight_id) REFERENCES flight(id),
    FOREIGN KEY(type_seat_id) REFERENCES type_seat(id)
);

CREATE TABLE plane_seat(
    type_seat_id VARCHAR(50) ,
    plane_id VARCHAR(50) ,
    quantity INTEGER NOT NULL,
    PRIMARY KEY(type_seat_id, plane_id),
    FOREIGN KEY(type_seat_id) REFERENCES type_seat(id),
    FOREIGN KEY(plane_id) REFERENCES plane(id)
);

CREATE TABLE flight_seat_promotion(
    type_seat_id VARCHAR(50) ,
    flight_id INTEGER,
    percentage NUMERIC(5,2) DEFAULT 0,
    seat_number INTEGER DEFAULT 0,
    PRIMARY KEY(type_seat_id, flight_id),
    FOREIGN KEY(type_seat_id) REFERENCES type_seat(id),
    FOREIGN KEY(flight_id) REFERENCES flight(id)
);

CREATE TABLE user_role(
    role_id INTEGER,
    user_id INTEGER,
    PRIMARY KEY(role_id, user_id),
    FOREIGN KEY(role_id) REFERENCES role(id),
    FOREIGN KEY(user_id) REFERENCES user_(id)
);

CREATE TABLE user_gender(
    user_id INTEGER,
    gender_id INTEGER,
    PRIMARY KEY(user_id, gender_id),
    FOREIGN KEY(user_id) REFERENCES user_(id),
    FOREIGN KEY(gender_id) REFERENCES gender(id)
);

CREATE TABLE flight_price_type_seat(
    type_seat_id VARCHAR(50) ,
    flight_id INTEGER,
    price NUMERIC(18,2) NOT NULL,
    PRIMARY KEY(type_seat_id, flight_id),
    FOREIGN KEY(type_seat_id) REFERENCES type_seat(id),
    FOREIGN KEY(flight_id) REFERENCES flight(id)
);
