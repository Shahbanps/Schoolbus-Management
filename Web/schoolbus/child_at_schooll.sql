/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.7.9 : Database - child_at_school
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`child_at_school` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `child_at_school`;

/*Table structure for table `aayas` */

DROP TABLE IF EXISTS `aayas`;

CREATE TABLE `aayas` (
  `aaya_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `bus_id` int(11) DEFAULT NULL,
  `first_name` varchar(200) DEFAULT NULL,
  `last_name` varchar(200) DEFAULT NULL,
  `house_name` varchar(200) DEFAULT NULL,
  `place` varchar(200) DEFAULT NULL,
  `phone` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`aaya_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `aayas` */

insert  into `aayas`(`aaya_id`,`login_id`,`bus_id`,`first_name`,`last_name`,`house_name`,`place`,`phone`) values (1,5,1,'rtfghj','qqqq','qqqq','dfgh','0987654321');

/*Table structure for table `attendance` */

DROP TABLE IF EXISTS `attendance`;

CREATE TABLE `attendance` (
  `attendance_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `astatus` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`attendance_id`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;

/*Data for the table `attendance` */

insert  into `attendance`(`attendance_id`,`student_id`,`date`,`astatus`) values (15,1,'2021-04-10','absent'),(14,2,'2021-04-10','absent');

/*Table structure for table `availablity` */

DROP TABLE IF EXISTS `availablity`;

CREATE TABLE `availablity` (
  `availablity_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`availablity_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `availablity` */

insert  into `availablity`(`availablity_id`,`student_id`,`date`,`status`) values (4,2,'2020-01-27','Present');

/*Table structure for table `buses` */

DROP TABLE IF EXISTS `buses`;

CREATE TABLE `buses` (
  `bus_id` int(11) NOT NULL AUTO_INCREMENT,
  `register_number` varchar(20) DEFAULT NULL,
  `seat_capacity` int(11) DEFAULT NULL,
  `latitude` varchar(20) DEFAULT NULL,
  `longitude` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`bus_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `buses` */

insert  into `buses`(`bus_id`,`register_number`,`seat_capacity`,`latitude`,`longitude`) values (1,'2815',35,'9.9767285','76.2864683'),(2,'4334',50,'0','0');

/*Table structure for table `child_line` */

DROP TABLE IF EXISTS `child_line`;

CREATE TABLE `child_line` (
  `child_line_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `office_place` varchar(200) DEFAULT NULL,
  `landmark` varchar(200) DEFAULT NULL,
  `phone` varchar(200) DEFAULT NULL,
  `email` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`child_line_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `child_line` */

insert  into `child_line`(`child_line_id`,`login_id`,`office_place`,`landmark`,`phone`,`email`) values (1,7,'vypin','iowheio','1111111111','edc@retfg.com');

/*Table structure for table `classes` */

DROP TABLE IF EXISTS `classes`;

CREATE TABLE `classes` (
  `class_id` int(11) NOT NULL AUTO_INCREMENT,
  `course` varchar(20) DEFAULT NULL,
  `batch` varchar(20) DEFAULT NULL,
  `sub_name` varchar(20) DEFAULT NULL,
  `tutor_name` varchar(20) DEFAULT NULL,
  `date_time` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`class_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `classes` */

insert  into `classes`(`class_id`,`course`,`batch`,`sub_name`,`tutor_name`,`date_time`) values (1,'2 nd','A','Maths','Anitha','2020-01-28 10:00:00'),(2,'2 nd','A','English','Anu','2020-01-28 11:00:00');

/*Table structure for table `complaints` */

DROP TABLE IF EXISTS `complaints`;

CREATE TABLE `complaints` (
  `complaint_id` int(11) NOT NULL AUTO_INCREMENT,
  `aaya_id` int(11) DEFAULT NULL,
  `student_id` int(11) DEFAULT NULL,
  `complaint` varchar(200) DEFAULT NULL,
  `date` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`complaint_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `complaints` */

insert  into `complaints`(`complaint_id`,`aaya_id`,`student_id`,`complaint`,`date`) values (1,1,1,'qqqqqqqqqqq','2021-04-10');

/*Table structure for table `driver_journey` */

DROP TABLE IF EXISTS `driver_journey`;

CREATE TABLE `driver_journey` (
  `driver_journey_id` int(11) NOT NULL AUTO_INCREMENT,
  `driver_id` int(11) DEFAULT NULL,
  `route_id` int(11) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`driver_journey_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `driver_journey` */

insert  into `driver_journey`(`driver_journey_id`,`driver_id`,`route_id`,`status`) values (2,1,1,'assigned');

/*Table structure for table `drivers` */

DROP TABLE IF EXISTS `drivers`;

CREATE TABLE `drivers` (
  `driver_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `bus_id` int(11) DEFAULT NULL,
  `first_name` varchar(20) DEFAULT NULL,
  `last_name` varchar(20) DEFAULT NULL,
  `contact_no` varchar(20) DEFAULT NULL,
  `image` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`driver_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `drivers` */

insert  into `drivers`(`driver_id`,`login_id`,`bus_id`,`first_name`,`last_name`,`contact_no`,`image`) values (1,4,1,'Arun','S','9879689875','static/uploads/d49e282e-2ecf-425e-b69b-b81674568bf3Screenshot (8).png');

/*Table structure for table `holidays` */

DROP TABLE IF EXISTS `holidays`;

CREATE TABLE `holidays` (
  `holiday_id` int(11) NOT NULL AUTO_INCREMENT,
  `holiday_name` varchar(20) DEFAULT NULL,
  `holiday_date` date DEFAULT NULL,
  PRIMARY KEY (`holiday_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `holidays` */

insert  into `holidays`(`holiday_id`,`holiday_name`,`holiday_date`) values (1,'Annuval day','2020-01-18');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `user_type` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`user_type`) values (1,'admin','admin','admin'),(2,'dd','dd','parents'),(3,'hh','hh','parents'),(4,'ar','ar','drivers'),(5,'ccc','ccc','aayas'),(6,'cl','cl','child_line'),(7,'child','child','child_line');

/*Table structure for table `messages` */

DROP TABLE IF EXISTS `messages`;

CREATE TABLE `messages` (
  `message_id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL,
  `message_description` varchar(500) DEFAULT NULL,
  `reply_description` varchar(500) DEFAULT NULL,
  `message_date` date DEFAULT NULL,
  PRIMARY KEY (`message_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `messages` */

insert  into `messages`(`message_id`,`parent_id`,`message_description`,`reply_description`,`message_date`) values (1,1,'hii','reply','2020-01-27'),(2,2,'hello','pending','2020-01-27'),(4,1,'hi','pending','2021-04-10');

/*Table structure for table `parents` */

DROP TABLE IF EXISTS `parents`;

CREATE TABLE `parents` (
  `parent_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `first_name` varchar(20) DEFAULT NULL,
  `last_name` varchar(20) DEFAULT NULL,
  `occupation` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(20) DEFAULT NULL,
  `house_name` varchar(20) DEFAULT NULL,
  `place` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`parent_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `parents` */

insert  into `parents`(`parent_id`,`login_id`,`first_name`,`last_name`,`occupation`,`phone`,`email`,`house_name`,`place`) values (1,2,'Dany','S.K','Doctor','9879689875','dany@gmail.com','Girivillla','vyttila'),(2,3,'Hari','S','Teacher','9879684566','hari@gmail.com','Rosevillla','Kochi');

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) DEFAULT NULL,
  `amount` double DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `payment` */

insert  into `payment`(`payment_id`,`student_id`,`amount`,`date`) values (1,2,500,'2020-01-28');

/*Table structure for table `places` */

DROP TABLE IF EXISTS `places`;

CREATE TABLE `places` (
  `place_id` int(11) NOT NULL AUTO_INCREMENT,
  `place_name` varchar(20) DEFAULT NULL,
  `latitude` varchar(20) DEFAULT NULL,
  `longitude` varchar(20) DEFAULT NULL,
  `route_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`place_id`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

/*Data for the table `places` */

insert  into `places`(`place_id`,`place_name`,`latitude`,`longitude`,`route_id`) values (1,'Kochi','9.976318481410548','76.28690500132753',1),(2,'Kadavanthra','9.976318481410548','76.28690500132753',1),(3,'Palarivattom','9.976318481410548','76.28690500132753',2),(4,'Kaloor','9.976318481410548','76.28690500132753',2),(5,'Ernakulam south','9.964201160411797','76.28786087036133',1);

/*Table structure for table `routes` */

DROP TABLE IF EXISTS `routes`;

CREATE TABLE `routes` (
  `route_id` int(11) NOT NULL AUTO_INCREMENT,
  `route_name` varchar(20) DEFAULT NULL,
  `route_details` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`route_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `routes` */

insert  into `routes`(`route_id`,`route_name`,`route_details`) values (1,'Kochi-vyttila','10 km'),(2,'Kochi-Kakkanad','15km');

/*Table structure for table `students` */

DROP TABLE IF EXISTS `students`;

CREATE TABLE `students` (
  `student_id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) DEFAULT NULL,
  `relation_with_parent` varchar(20) DEFAULT NULL,
  `route_id` int(11) DEFAULT NULL,
  `place_id` int(11) DEFAULT NULL,
  `first_name` varchar(20) DEFAULT NULL,
  `last_name` varchar(20) DEFAULT NULL,
  `image` varchar(500) DEFAULT NULL,
  `course` varchar(20) DEFAULT NULL,
  `batch` varchar(20) DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `gender` varchar(20) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `student_status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`student_id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `students` */

insert  into `students`(`student_id`,`parent_id`,`relation_with_parent`,`route_id`,`place_id`,`first_name`,`last_name`,`image`,`course`,`batch`,`dob`,`gender`,`phone`,`student_status`) values (1,1,'Father',1,1,'Minu','Dany','static/uploads/686ada3f-fe73-40c1-b9ee-68b7571ac920level 1 adminkudu-1.png','2','A','2009-01-19','female','9879689875','active'),(2,1,'Father',1,1,'Achu','Dany','static/uploads/a6b284f8-0053-4ab6-8a01-baded65dc7f8level 1 adminkudu.png','4','A','2005-01-19','male','9879684566','active');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
