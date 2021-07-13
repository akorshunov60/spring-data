CREATE SCHEMA IF NOT EXISTS `eglobus` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `eglobus`.`users` (
                                                 `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                                                 `name` VARCHAR(45) NOT NULL,
                                                 PRIMARY KEY (`id`),
                                                 UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `eglobus`.`orders` (
                                                  `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                                                  `user_id` INT UNSIGNED NOT NULL,
                                                  PRIMARY KEY (`id`),
                                                  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
                                                  INDEX `user_id_idx` (`user_id` ASC) VISIBLE,
                                                  CONSTRAINT `user_id`
                                                      FOREIGN KEY (`user_id`)
                                                          REFERENCES `eglobus`.`users` (`id`)
                                                          ON DELETE NO ACTION
                                                          ON UPDATE NO ACTION)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `eglobus`.`products` (
                                                    `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                                                    `name` VARCHAR(45) NOT NULL,
                                                    `price` DECIMAL(10,2) UNSIGNED NULL,
                                                    PRIMARY KEY (`id`),
                                                    UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;

CREATE TABLE IF NOT EXISTS `eglobus`.`order_products` (
                                                          `id` INT UNSIGNED NOT NULL AUTO_INCREMENT,
                                                          `order_id` INT UNSIGNED NOT NULL,
                                                          `product_id` INT UNSIGNED NOT NULL,
                                                          `quantity` INT UNSIGNED NOT NULL,
                                                          `last_price` DECIMAL(10,2) UNSIGNED NOT NULL,
                                                          PRIMARY KEY (`id`),
                                                          UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE,
                                                          INDEX `fk_order_id_idx` (`order_id` ASC) VISIBLE,
                                                          INDEX `fk_product_id_idx` (`product_id` ASC) VISIBLE,
                                                          CONSTRAINT `fk_order_id`
                                                              FOREIGN KEY (`order_id`)
                                                                  REFERENCES `eglobus`.`orders` (`id`)
                                                                  ON DELETE NO ACTION
                                                                  ON UPDATE NO ACTION,
                                                          CONSTRAINT `fk_product_id`
                                                              FOREIGN KEY (`product_id`)
                                                                  REFERENCES `eglobus`.`products` (`id`)
                                                                  ON DELETE NO ACTION
                                                                  ON UPDATE NO ACTION)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
    COLLATE = utf8mb4_0900_ai_ci;
