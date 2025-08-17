INSERT INTO _role_(label) VALUES
('MANAGER'),
('CLIENT');

INSERT INTO _user_(last_name,email,password,first_name,birth_date) VALUES
('admin','admin@admin.com','admin','admin','2005-07-05'),
('client','client@client.com','client','client','2005-07-05');

INSERT INTO user_role(role_id, user_id) VALUES 
(1,1),
(2,2);

INSERT INTO flight_price_type_seat(flight_id,type_seat_id,price) VALUES
(1,1,1000),
(1,2,1500),
(2,1,1000),
(2,2,1500),
(5,1,1000),
(5,2,1500);