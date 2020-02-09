CREATE SCHEMA IF NOT EXISTS `onlinepharmacy` DEFAULT CHARACTER SET utf8 ;
USE `onlinepharmacy` ;

CREATE TABLE IF NOT EXISTS `onlinepharmacy`.`countries` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 11
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `onlinepharmacy`.`dosages` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 17
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `onlinepharmacy`.`manufacturers` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `country_id` INT(11) NOT NULL,
  `website` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_manufacturers_country_idx` (`country_id` ASC) VISIBLE,
  CONSTRAINT `FK_manufacturers_country`
    FOREIGN KEY (`country_id`)
    REFERENCES `onlinepharmacy`.`countries` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `onlinepharmacy`.`drugs` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `label` VARCHAR(50) NOT NULL,
  `dosage_id` INT(11) NOT NULL,
  `volume` SMALLINT(6) NOT NULL,
  `volume_type` ENUM('мл', 'мг', 'г', 'шт') NOT NULL DEFAULT 'шт',
  `manufacturer_id` INT(11) NOT NULL,
  `price` DECIMAL(15,2) NOT NULL,
  `by_prescription` TINYINT(1) NOT NULL DEFAULT '0',
  `description` TEXT NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  INDEX `FK_drugs_manufacturers_id_idx` (`manufacturer_id` ASC) VISIBLE,
  INDEX `fk_drugs_dosages_idx` (`dosage_id` ASC) VISIBLE,
  CONSTRAINT `FK_drugs_manufacturers_id`
    FOREIGN KEY (`manufacturer_id`)
    REFERENCES `onlinepharmacy`.`manufacturers` (`id`),
  CONSTRAINT `fk_drugs_dosages`
    FOREIGN KEY (`dosage_id`)
    REFERENCES `onlinepharmacy`.`dosages` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 14
DEFAULT CHARACTER SET = utf8
ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `onlinepharmacy`.`users` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(255) NOT NULL,
  `password_md5` CHAR(32) NOT NULL,
  `role` ENUM('admin', 'manager', 'doctor', 'user', 'guest') NOT NULL DEFAULT 'guest',
  `email` VARCHAR(255) NOT NULL,
  `salt` VARCHAR(50) NULL DEFAULT NULL,
  `first_name` VARCHAR(50) NULL DEFAULT NULL,
  `last_name` VARCHAR(50) NULL DEFAULT NULL,
  `address` VARCHAR(255) NULL DEFAULT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 12
DEFAULT CHARACTER SET = utf8
ROW_FORMAT = DYNAMIC;

CREATE TABLE IF NOT EXISTS `onlinepharmacy`.`orders` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `customer_id` INT(11) NOT NULL,
  `date` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `amount` DECIMAL(19,2) NOT NULL,
  `status` ENUM('in_process', 'payment_confirmation', 'paid', 'completed') NOT NULL DEFAULT 'in_process',
  PRIMARY KEY (`id`),
  INDEX `fk_orders_users_data_idx` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `fk_orders_users_credentials`
    FOREIGN KEY (`customer_id`)
    REFERENCES `onlinepharmacy`.`users` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 26
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `onlinepharmacy`.`drugs_ordered` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `order_id` INT(11) NOT NULL,
  `drug_id` INT(11) NOT NULL,
  `quantity` INT(11) NULL DEFAULT '0',
  `price` DECIMAL(15,2) NULL DEFAULT '0.00',
  PRIMARY KEY (`id`),
  INDEX `fk_drugs_ordered_orders_idx` (`order_id` ASC) VISIBLE,
  INDEX `FK_drugs_ordered_drugs_id_idx` (`drug_id` ASC) VISIBLE,
  CONSTRAINT `FK_drugs_ordered_drugs_id`
    FOREIGN KEY (`drug_id`)
    REFERENCES `onlinepharmacy`.`drugs` (`id`),
  CONSTRAINT `fk_drugs_ordered_orders_id`
    FOREIGN KEY (`order_id`)
    REFERENCES `onlinepharmacy`.`orders` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 52
DEFAULT CHARACTER SET = utf8;

CREATE TABLE IF NOT EXISTS `onlinepharmacy`.`orders_events` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `order_id` INT(11) NOT NULL,
  `order_status` ENUM('in_process', 'payment_confirmation', 'paid', 'completed') CHARACTER SET 'utf8' COLLATE 'utf8_bin' NOT NULL DEFAULT 'in_process',
  `date_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  INDEX `fk_orders_events_orders_id_idx` (`order_id` ASC) VISIBLE,
  CONSTRAINT `fk_orders_events_orders_id`
    FOREIGN KEY (`order_id`)
    REFERENCES `onlinepharmacy`.`orders` (`id`)
    ON DELETE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 35
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_bin;

CREATE TABLE IF NOT EXISTS `onlinepharmacy`.`prescriptions` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `valid_until` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `drug_id` INT(11) NOT NULL,
  `customer_id` INT(11) NOT NULL,
  `doctor_id` INT(11) NULL DEFAULT NULL,
  `status` ENUM('requested', 'approved', 'overdue', 'rejected') NOT NULL DEFAULT 'requested',
  PRIMARY KEY (`id`),
  INDEX `FK_prescriptions_user_id_idx` (`customer_id` ASC, `doctor_id` ASC) VISIBLE,
  INDEX `FK_prescriptions_doctor_id_idx` (`doctor_id` ASC) VISIBLE,
  INDEX `FK_prescriptions_drugs_id_idx` (`drug_id` ASC) VISIBLE,
  CONSTRAINT `FK_prescriptions_doctor_id`
    FOREIGN KEY (`doctor_id`)
    REFERENCES `onlinepharmacy`.`users` (`id`),
  CONSTRAINT `FK_prescriptions_drugs_id`
    FOREIGN KEY (`drug_id`)
    REFERENCES `onlinepharmacy`.`drugs` (`id`),
  CONSTRAINT `FK_prescriptions_users_id`
    FOREIGN KEY (`customer_id`)
    REFERENCES `onlinepharmacy`.`users` (`id`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8;