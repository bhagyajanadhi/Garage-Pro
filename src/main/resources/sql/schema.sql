create database Garage_pro;

use Garage_pro;

CREATE TABLE customer (
                          customerId VARCHAR(50) PRIMARY KEY,
                          customerName VARCHAR(80),
                          customerContactInformation VARCHAR(15),
                          customerAddress VARCHAR(80),
                          customerEmail VARCHAR(80),
                          vehicle_id VARCHAR(50)
);

CREATE TABLE vehicle (
                         vehicleId VARCHAR(50) PRIMARY KEY,
                         vehicleModel VARCHAR(80),
                         vehicleLicensePlate VARCHAR(15), -- Changed to VARCHAR as license plates can contain letters
                         cus_id VARCHAR(50),
                         FOREIGN KEY (cus_id) REFERENCES customer(customerId) ON UPDATE CASCADE ON DELETE CASCADE
);



ALTER TABLE customer
    ADD CONSTRAINT fk_vehicle_id
        FOREIGN KEY (vehicle_id) REFERENCES vehicle(vehicleId) ON UPDATE CASCADE ON DELETE CASCADE;

CREATE TABLE booking (
                         bookingId VARCHAR(50) PRIMARY KEY,
                         customerId VARCHAR(50),
                         vehicleId VARCHAR(50),
                         date DATE,
                         timeslot TIME,
                         FOREIGN KEY (customerId) REFERENCES customer(customerId) ON UPDATE CASCADE ON DELETE CASCADE,
                         FOREIGN KEY (vehicleId) REFERENCES vehicle(vehicleId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE payment (
                         paymentId VARCHAR(50) PRIMARY KEY,
                         amount DECIMAL(10, 2),
                         date DATE
);


CREATE TABLE booking_service (
                                 customerId VARCHAR(50),
                                 bookingId VARCHAR(50),
                                 FOREIGN KEY (customerId) REFERENCES customer(customerId) ON UPDATE CASCADE ON DELETE CASCADE,
                                 FOREIGN KEY (bookingId) REFERENCES booking(bookingId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE service (
                         jobId VARCHAR(50),
                         serviceId VARCHAR(50) PRIMARY KEY,
                         description VARCHAR(255),
                         price DECIMAL(10, 2)
);

CREATE TABLE job (
                     jobId VARCHAR(50) PRIMARY KEY,
                     employeeId VARCHAR(50),
                     description VARCHAR(255),
                     date DATE,
                     vehicleId VARCHAR(50),
                     FOREIGN KEY (vehicleId) REFERENCES vehicle(vehicleId) ON UPDATE CASCADE ON DELETE CASCADE
);

ALTER TABLE service
    ADD CONSTRAINT fk_jobId
        FOREIGN KEY (jobId) REFERENCES job(jobId) ON UPDATE CASCADE ON DELETE CASCADE;

CREATE TABLE employee (
                          employeeId VARCHAR(50) PRIMARY KEY,
                          serviceId VARCHAR(50),
                          salaryId VARCHAR(50),
                          name VARCHAR(255),
                          skill VARCHAR(255),
                          contactInformation VARCHAR(15),
                          Email VARCHAR(255),
                          FOREIGN KEY (serviceId) REFERENCES service(serviceId) ON UPDATE CASCADE ON DELETE CASCADE
);




CREATE TABLE salary (
                        salaryId VARCHAR(50) PRIMARY KEY,
                        description VARCHAR(255),
                        employeeId VARCHAR(50),
                        date DATE,
                        FOREIGN KEY (employeeId) REFERENCES employee(employeeId) ON UPDATE CASCADE ON DELETE CASCADE
);

ALTER TABLE employee
    ADD CONSTRAINT fk_salaryId
        FOREIGN KEY (salaryId) REFERENCES salary(salaryId) ON UPDATE CASCADE ON DELETE CASCADE;

CREATE TABLE admin (
                       email VARCHAR(255) PRIMARY KEY,
                       employeeId VARCHAR(50),
                       role VARCHAR(255),
                       password VARCHAR(255),
                       adminId VARCHAR(50),
                       username VARCHAR(255),
                       FOREIGN KEY (employeeId) REFERENCES employee(employeeId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE inventory (
                           inventoryId VARCHAR(50) PRIMARY KEY,
                           description VARCHAR(255),
                           supplierId VARCHAR(50),
                           partName VARCHAR(255),
                           stockLevel INT,
                           unitPrice DECIMAL(10, 2)
);
CREATE TABLE supplier (
                          supplierId VARCHAR(50) PRIMARY KEY,
                          name VARCHAR(255),
                          paymentTerm VARCHAR(255),
                          contactInformation VARCHAR(255)
);

ALTER TABLE inventory
    ADD CONSTRAINT fk_supplierId
        FOREIGN KEY (supplierId) REFERENCES supplier(supplierId) ON UPDATE CASCADE ON DELETE CASCADE;

CREATE TABLE price_history (
                               priceHistoryId VARCHAR(50) PRIMARY KEY,
                               inventoryId VARCHAR(50),
                               price DECIMAL(10, 2),
                               date DATE,
                               FOREIGN KEY (inventoryId) REFERENCES inventory(inventoryId) ON UPDATE CASCADE ON DELETE CASCADE
);
CREATE TABLE job_inventory (
                               jobInventoryId VARCHAR(50) PRIMARY KEY,
                               inventoryId VARCHAR(50),
                               jobId VARCHAR(50),
                               quantityUsed INT,
                               FOREIGN KEY (inventoryId) REFERENCES inventory(inventoryId) ON UPDATE CASCADE ON DELETE CASCADE,
                               FOREIGN KEY (jobId) REFERENCES job(jobId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE supplier_inventory (
                                    supplierInventoryId VARCHAR(50) PRIMARY KEY,
                                    supplierId VARCHAR(50),
                                    inventoryId VARCHAR(50),
                                    FOREIGN KEY (supplierId) REFERENCES supplier(supplierId) ON UPDATE CASCADE ON DELETE CASCADE,
                                    FOREIGN KEY (inventoryId) REFERENCES inventory(inventoryId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE loan (
                      LoanId VARCHAR(50) PRIMARY KEY,
                      amount DECIMAL(10, 2),
                      supplierId VARCHAR(50),
                      duedate DATE,
                      paymentstatus VARCHAR(255),
                      FOREIGN KEY (supplierId) REFERENCES supplier(supplierId) ON UPDATE CASCADE ON DELETE CASCADE
);
















