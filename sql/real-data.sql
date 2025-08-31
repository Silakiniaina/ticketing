INSERT INTO _role_(label) VALUES
('MANAGER'),
('CLIENT');

INSERT INTO reservation_setting VALUES(0,0);

INSERT INTO _user_(last_name,email,password,first_name,birth_date) VALUES
('admin','admin@admin.com','admin','admin','2005-07-05'),
('client','client@client.com','client','client','2005-07-05');

INSERT INTO user_role(role_id, user_id) VALUES 
(1,1),
(2,2);


INSERT INTO city (label) VALUES
('Antananarivo'),
('Paris CDG'),
('Mauritius'),
('Addis Abeba');

INSERT INTO type_seat (label) VALUES
('Economique'),
('Affaire');

INSERT INTO plane (model, fabrication_date) VALUES
('Air Madagascar', '2015-06-12'),
('Air France', '2018-03-22');

INSERT INTO plane_seat (type_seat_id, plane_id, quantity) VALUES
(1, 1, 120),
(2, 1, 20),
(1, 2, 150),
(2, 2, 25);

INSERT INTO flight (departure_datetime, arrival_datetime, plane_id, arrival_city_id, departure_city_id) VALUES
('2025-08-31 08:00:00', '2025-09-01 16:00:00', 1, 4, 1),
('2025-09-03 21:00:00', '2025-09-04 03:00:00', 2, 3, 2);

INSERT INTO flight_seat_promotion(flight_id, type_seat_id, seat_number, price, promotion_date) VALUES
(1,1,3,200,'2025-08-17'),
(1,1,3,300,'2025-08-23'),
(2,1,2,350,'2025-09-02'),

