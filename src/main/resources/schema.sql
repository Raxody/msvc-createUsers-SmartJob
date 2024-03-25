CREATE TABLE users (
   uuid VARCHAR(255) PRIMARY KEY,
   name VARCHAR(255),
   email VARCHAR(255),
   password VARCHAR(255),
   creation_date TIMESTAMP,
   last_login TIMESTAMP,
   modified_date TIMESTAMP,
   active BOOLEAN,
   token VARCHAR(255)
);

CREATE TABLE phones (
    uuid VARCHAR(255) PRIMARY KEY,
    number NUMERIC,
    city_code NUMERIC,
    country_code NUMERIC,
    user_uuid VARCHAR(255),
    FOREIGN KEY (user_uuid) REFERENCES users(uuid)
);
