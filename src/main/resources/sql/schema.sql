drop database garage_pro;

create database garage_pro;

use garage_pro;

CREATE TABLE customer (
                          customerId VARCHAR(10) PRIMARY KEY,
                          customerName VARCHAR(100),
                          customerContactInfromation VARCHAR(15) UNIQUE KEY,
                          customerAddress VARCHAR(255),
                          customerEmail VARCHAR(100)
);

CREATE TABLE vehicle (
                         vehicleId VARCHAR(50) PRIMARY KEY,
                         vehicleModel VARCHAR(80),
                         vehicleLicensePlate VARCHAR(15), -- Changed to VARCHAR as license plates can contain letters
                         cus_id VARCHAR(50),
                         FOREIGN KEY (cus_id) REFERENCES customer(customerId) ON UPDATE CASCADE ON DELETE CASCADE
);



CREATE TABLE booking (
                         bookingId VARCHAR(50) PRIMARY KEY,
                         customerId VARCHAR(50),
                         vehicleId VARCHAR(50),
                         date DATE,
                         FOREIGN KEY (customerId) REFERENCES customer(customerId) ON UPDATE CASCADE ON DELETE CASCADE,
                         FOREIGN KEY (vehicleId) REFERENCES vehicle(vehicleId) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE job (
                     jobId VARCHAR(50) PRIMARY KEY,
                     employeeId VARCHAR(50),
                     description VARCHAR(255),
                     date DATE,
                     vehicleId VARCHAR(50),
                     total double,
                     FOREIGN KEY (vehicleId) REFERENCES vehicle(vehicleId)ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE payment (
                         jobId VARCHAR(50),
                         paymentId VARCHAR(50) PRIMARY KEY,
                         amount DECIMAL(10, 2),
                         date DATE,
                         itemAmount DOUBLE,
                         FOREIGN KEY (jobId) REFERENCES job(jobId) ON UPDATE CASCADE ON DELETE CASCADE


);


CREATE TABLE employee (
                          employeeId VARCHAR(50) PRIMARY KEY,
                          salary DECIMAL(10, 2),
                          name VARCHAR(255),
                          skill VARCHAR(255),
                          contactInformation VARCHAR(15),
                          Email VARCHAR(255)
);


CREATE TABLE admin (
                       userName VARCHAR(255) PRIMARY KEY,
                       password VARCHAR(255) NOT NULL,
                       email VARCHAR(255) NOT NULL
);

CREATE TABLE inventory (
                           inventoryId VARCHAR(50) PRIMARY KEY,
                           description VARCHAR(255),
                           supplierId VARCHAR(50),
                           partName VARCHAR(255),
                           qty INT,
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
                               jobId VARCHAR(50) not null,
                               inventoryId VARCHAR(50),
                               quantityUsed INT,
                               unitePrice double not null ,
                               totalPrice double,
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