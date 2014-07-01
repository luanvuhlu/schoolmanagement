SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';


-- -----------------------------------------------------
-- Table `SchoolManagement`.`staff`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SchoolManagement`.`staff` (
  `No` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `birthday` DATE NULL,
  `address` VARCHAR(100) NULL,
  `phone` VARCHAR(15) NULL,
  `email` VARCHAR(45) NULL,
  `experience` VARCHAR(45) NULL,
  `education_Background` TEXT NULL,
  `gender` TINYINT NULL,
  `status` TINYINT NULL DEFAULT '1',
  PRIMARY KEY (`No`))
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `SchoolManagement`.`admission`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SchoolManagement`.`admission` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `date` DATE NULL,
  `prev_Institute` VARCHAR(45) NULL,
  `reason_Leaving` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`))
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `SchoolManagement`.`batch`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SchoolManagement`.`batch` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `date` DATE NULL,
  `status` TINYINT NULL DEFAULT 1,
  PRIMARY KEY (`ID`));


-- -----------------------------------------------------
-- Table `SchoolManagement`.`academic`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SchoolManagement`.`academic` (
  `register_No` INT NOT NULL AUTO_INCREMENT,
  `batch_ID` INT NULL,
  INDEX `fk_academic_batch1_idx` (`batch_ID` ASC),
  PRIMARY KEY (`register_No`),
  CONSTRAINT `fk_academic_batch1`
    FOREIGN KEY (`batch_ID`)
    REFERENCES `SchoolManagement`.`batch` (`ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `SchoolManagement`.`student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SchoolManagement`.`student` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `No` VARCHAR(10) NULL,
  `name` VARCHAR(45) NULL,
  `birthday` DATE NULL,
  `address` VARCHAR(100) NULL,
  `phone` VARCHAR(15) NULL,
  `email` VARCHAR(45) NULL,
  `gender` TINYINT NULL,
  `admission_ID` INT NOT NULL,
  `academic_register_No` INT NOT NULL,
  `status` TINYINT NULL DEFAULT 1,
  PRIMARY KEY (`ID`),
  INDEX `fk_student_admission1_idx` (`admission_ID` ASC),
  INDEX `fk_student_academic1_idx` (`academic_register_No` ASC),
  CONSTRAINT `fk_student_admission1`
    FOREIGN KEY (`admission_ID`)
    REFERENCES `SchoolManagement`.`admission` (`ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_student_academic1`
    FOREIGN KEY (`academic_register_No`)
    REFERENCES `SchoolManagement`.`academic` (`register_No`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `SchoolManagement`.`feedback`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SchoolManagement`.`feedback` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `staff_No` INT NOT NULL,
  `content` TEXT NOT NULL,
  `date` DATE NULL,
  `student_ID` INT NOT NULL,
  `status` TINYINT NULL DEFAULT 0,
  PRIMARY KEY (`ID`),
  INDEX `fk_feedback_staff1_idx` (`staff_No` ASC),
  INDEX `fk_feedback_student1_idx` (`student_ID` ASC),
  CONSTRAINT `fk_feedback_staff1`
    FOREIGN KEY (`staff_No`)
    REFERENCES `SchoolManagement`.`staff` (`No`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_feedback_student1`
    FOREIGN KEY (`student_ID`)
    REFERENCES `SchoolManagement`.`student` (`ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `SchoolManagement`.`subject`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SchoolManagement`.`subject` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `status` TINYINT NULL DEFAULT 1,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `SchoolManagement`.`remark`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SchoolManagement`.`remark` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `content` TEXT NOT NULL,
  `date` DATE NULL,
  `staff_No` INT NOT NULL,
  `student_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_remark_staff1_idx` (`staff_No` ASC),
  INDEX `fk_remark_student1_idx` (`student_ID` ASC),
  CONSTRAINT `fk_remark_staff1`
    FOREIGN KEY (`staff_No`)
    REFERENCES `SchoolManagement`.`staff` (`No`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_remark_student1`
    FOREIGN KEY (`student_ID`)
    REFERENCES `SchoolManagement`.`student` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `SchoolManagement`.`department`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SchoolManagement`.`department` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `status` TINYINT NULL DEFAULT 1,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `SchoolManagement`.`position`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SchoolManagement`.`position` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `department_ID` INT NOT NULL,
  `status` TINYINT NULL DEFAULT 1,
  PRIMARY KEY (`ID`),
  INDEX `fk_position_department1_idx` (`department_ID` ASC),
  CONSTRAINT `fk_position_department1`
    FOREIGN KEY (`department_ID`)
    REFERENCES `SchoolManagement`.`department` (`ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `SchoolManagement`.`promotion`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SchoolManagement`.`promotion` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `position_ID` INT NOT NULL,
  `staff_No` INT NOT NULL,
  `date` DATE NULL,
  INDEX `fk_promotion_has_staff_staff1_idx` (`staff_No` ASC),
  INDEX `fk_promotion_has_staff_promotion1_idx` (`position_ID` ASC),
  PRIMARY KEY (`ID`),
  CONSTRAINT `fk_promotion_has_staff_promotion1`
    FOREIGN KEY (`position_ID`)
    REFERENCES `SchoolManagement`.`position` (`ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_promotion_has_staff_staff1`
    FOREIGN KEY (`staff_No`)
    REFERENCES `SchoolManagement`.`staff` (`No`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `SchoolManagement`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SchoolManagement`.`user` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `usrn` VARCHAR(45) NOT NULL,
  `pswd` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NULL,
  `role` TINYINT NULL,
  `email` VARCHAR(45) NULL,
  `status` TINYINT NULL DEFAULT 1,
  PRIMARY KEY (`ID`))
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `SchoolManagement`.`extra_curricular`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SchoolManagement`.`extra_curricular` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `status` TINYINT NULL DEFAULT 1,
  `location` VARCHAR(200) NULL,
  `start` DATE NULL,
  `end` DATE NULL,
  PRIMARY KEY (`ID`))
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `SchoolManagement`.`extra_curricular_has_student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SchoolManagement`.`extra_curricular_has_student` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `extra_curricular_ID` INT NOT NULL,
  `notes` TEXT NULL,
  `student_ID` INT NOT NULL,
  INDEX `fk_extra_curricular_has_student_extra_curricular1_idx` (`extra_curricular_ID` ASC),
  PRIMARY KEY (`ID`),
  INDEX `fk_extra_curricular_has_student_student1_idx` (`student_ID` ASC),
  CONSTRAINT `fk_extra_curricular_has_student_extra_curricular1`
    FOREIGN KEY (`extra_curricular_ID`)
    REFERENCES `SchoolManagement`.`extra_curricular` (`ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_extra_curricular_has_student_student1`
    FOREIGN KEY (`student_ID`)
    REFERENCES `SchoolManagement`.`student` (`ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `SchoolManagement`.`family_staff`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SchoolManagement`.`family_staff` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `married` TINYINT NULL,
  `staff_No` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_family_Staff_staff1_idx` (`staff_No` ASC),
  CONSTRAINT `fk_family_Staff_staff1`
    FOREIGN KEY (`staff_No`)
    REFERENCES `SchoolManagement`.`staff` (`No`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `SchoolManagement`.`admin`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SchoolManagement`.`admin` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(45) NOT NULL,
  `name` VARCHAR(45) NULL,
  `email` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`))
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `SchoolManagement`.`authentication`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SchoolManagement`.`authentication` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `time` TIMESTAMP NOT NULL,
  `ID_People` INT NULL,
  `role` TINYINT NULL,
  `action` TINYINT NULL,
  PRIMARY KEY (`ID`))
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `SchoolManagement`.`family_student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SchoolManagement`.`family_student` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `student_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_family_student_student1_idx` (`student_ID` ASC),
  CONSTRAINT `fk_family_student_student1`
    FOREIGN KEY (`student_ID`)
    REFERENCES `SchoolManagement`.`student` (`ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `SchoolManagement`.`relatives_student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SchoolManagement`.`relatives_student` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `relation` VARCHAR(45) NULL,
  `name` VARCHAR(45) NULL,
  `phone` VARCHAR(15) NULL,
  `address` VARCHAR(100) NULL,
  `occupation` VARCHAR(45) NULL,
  `gender` TINYINT NULL,
  `family_student_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_relatives_student_family_student1_idx` (`family_student_ID` ASC),
  CONSTRAINT `fk_relatives_student_family_student1`
    FOREIGN KEY (`family_student_ID`)
    REFERENCES `SchoolManagement`.`family_student` (`ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `SchoolManagement`.`relatives_staff`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SchoolManagement`.`relatives_staff` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `relation` VARCHAR(45) NULL,
  `name` VARCHAR(45) NULL,
  `phone` VARCHAR(15) NULL,
  `address` VARCHAR(100) NULL,
  `occupation` VARCHAR(45) NULL,
  `gender` TINYINT NULL,
  `family_Staff_ID` INT NOT NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_relatives_staff_family_Staff1_idx` (`family_Staff_ID` ASC),
  CONSTRAINT `fk_relatives_staff_family_Staff1`
    FOREIGN KEY (`family_Staff_ID`)
    REFERENCES `SchoolManagement`.`family_staff` (`ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `SchoolManagement`.`family_Student_has_family_people_student`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SchoolManagement`.`family_Student_has_family_people_student` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `family_Student_ID` INT NOT NULL,
  `family_people_student_ID` INT NOT NULL,
  INDEX `fk_family_Student_has_family_people_student_family_people_s_idx` (`family_people_student_ID` ASC),
  PRIMARY KEY (`ID`),
  CONSTRAINT `fk_family_Student_has_family_people_student_family_people_stu1`
    FOREIGN KEY (`family_people_student_ID`)
    REFERENCES `SchoolManagement`.`relatives_student` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;


-- -----------------------------------------------------
-- Table `SchoolManagement`.`mark`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `SchoolManagement`.`mark` (
  `ID` INT NOT NULL AUTO_INCREMENT,
  `subject_ID` INT NOT NULL,
  `point` TINYINT NULL,
  `date` DATE NULL,
  `student_ID` INT NOT NULL,
  INDEX `fk_student_has_subject_subject1_idx` (`subject_ID` ASC),
  PRIMARY KEY (`ID`),
  INDEX `fk_mark_student1_idx` (`student_ID` ASC),
  CONSTRAINT `fk_student_has_subject_subject1`
    FOREIGN KEY (`subject_ID`)
    REFERENCES `SchoolManagement`.`subject` (`ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_mark_student1`
    FOREIGN KEY (`student_ID`)
    REFERENCES `SchoolManagement`.`student` (`ID`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci;

USE SchoolManagement;
INSERT INTO admin(`username`, `password`) VALUES ('admin', 'password');

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
