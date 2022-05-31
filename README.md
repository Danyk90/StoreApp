# StoreApp
Ecom Store App With User Auth
Following Are the app properties to run for the first time

spring.datasource.url=jdbc:postgresql://localhost:5432/ecom?createDatabaseIfNotExist=true
spring.datasource.username=postgres
spring.datasource.password=admin123

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto= create
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.show-sql=true
spring.jackson.serialization.fail-on-empty-beans=false

# App Properties
ecom.app.jwtSecret= ecomadminSecurityKey
ecom.app.jwtExpirationMs= 3600000
ecom.app.jwtRefreshExpirationMs= 86400000



----------------------------------------------
After that change spring.jpa.hibernate.ddl-auto= create to spring.jpa.hibernate.ddl-auto= update


After schema created 
Please Insert Roles

INSERT INTO store.roles(name) VALUES('ROLE_USER');
INSERT INTO store.roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO store.roles(name) VALUES('ROLE_ADMIN');
