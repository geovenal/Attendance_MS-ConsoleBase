-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 18, 2024 at 12:50 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `attendance_ms`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbladmins`
--

CREATE TABLE `tbladmins` (
  `admin_id` int(11) NOT NULL,
  `acc_id` int(11) NOT NULL,
  `admin_fname` varchar(50) NOT NULL,
  `admin_mname` varchar(50) NOT NULL,
  `admin_lname` varchar(50) NOT NULL,
  `archived` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbladmins`
--

INSERT INTO `tbladmins` (`admin_id`, `acc_id`, `admin_fname`, `admin_mname`, `admin_lname`, `archived`) VALUES
(4, 6, 'First', 'Middle', 'Last', 0),
(8, 10, 'Geo', 'Amparo', 'Bernardo', 0);

-- --------------------------------------------------------

--
-- Table structure for table `tblattendances`
--

CREATE TABLE `tblattendances` (
  `attendance_id` int(11) NOT NULL,
  `attd_date` varchar(50) NOT NULL,
  `attd_day` varchar(50) NOT NULL,
  `attd_time_in` varchar(50) DEFAULT '',
  `attd_time_out` varchar(50) DEFAULT '',
  `attd_status` varchar(25) DEFAULT '',
  `student_id` int(11) NOT NULL,
  `class_id` int(11) NOT NULL,
  `admin_id` int(11) NOT NULL,
  `co_admin_id` int(11) DEFAULT NULL,
  `archived` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tblattendances`
--

INSERT INTO `tblattendances` (`attendance_id`, `attd_date`, `attd_day`, `attd_time_in`, `attd_time_out`, `attd_status`, `student_id`, `class_id`, `admin_id`, `co_admin_id`, `archived`) VALUES
(74, '11-17-2024', 'Sunday', '13:47', '13:47', 'EARLY', 1, 1, 8, NULL, 0),
(75, '11-17-2024', 'Sunday', '--+---', '--+---', 'ABSENT', 2, 1, 8, NULL, 0),
(76, '11-14-2024', 'Thursday', '--+---', '--+---', 'ABSENT', 1, 1, 8, NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tblclasses`
--

CREATE TABLE `tblclasses` (
  `class_id` int(11) NOT NULL,
  `class_code` varchar(50) NOT NULL,
  `class_name` varchar(50) NOT NULL,
  `class_time_in` varchar(50) NOT NULL,
  `class_time_out` varchar(50) NOT NULL,
  `archived` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tblclasses`
--

INSERT INTO `tblclasses` (`class_id`, `class_code`, `class_name`, `class_time_in`, `class_time_out`, `archived`) VALUES
(1, '002', 'Batch 1', '12:00', '17:00', 0),
(2, '003', 'Batch 4', '08:00', '12:00', 0);

-- --------------------------------------------------------

--
-- Table structure for table `tblco_admins`
--

CREATE TABLE `tblco_admins` (
  `co_admin_id` int(11) NOT NULL,
  `acc_id` int(11) NOT NULL,
  `admin_id` int(11) NOT NULL,
  `co_admin_fname` varchar(50) NOT NULL,
  `co_admin_mname` varchar(50) NOT NULL,
  `co_admin_lname` varchar(50) NOT NULL,
  `archived` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tblco_admins`
--

INSERT INTO `tblco_admins` (`co_admin_id`, `acc_id`, `admin_id`, `co_admin_fname`, `co_admin_mname`, `co_admin_lname`, `archived`) VALUES
(6, 31, 8, 'Coad', 'coad', 'coad', 0);

-- --------------------------------------------------------

--
-- Table structure for table `tblstudents`
--

CREATE TABLE `tblstudents` (
  `student_id` int(11) NOT NULL,
  `acc_id` int(11) NOT NULL,
  `admin_id` int(11) NOT NULL,
  `co_admin_id` int(11) DEFAULT NULL,
  `student_fname` varchar(50) NOT NULL,
  `student_mname` varchar(50) NOT NULL,
  `student_lname` varchar(50) NOT NULL,
  `class_id` int(11) DEFAULT NULL,
  `archived` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tblstudents`
--

INSERT INTO `tblstudents` (`student_id`, `acc_id`, `admin_id`, `co_admin_id`, `student_fname`, `student_mname`, `student_lname`, `class_id`, `archived`) VALUES
(1, 12, 8, NULL, 'Gab', 'Bernardo', 'Amparo', 2, 0),
(2, 16, 8, NULL, 'Alex', 'Garcia', 'Cruz', 1, 0),
(5, 19, 8, NULL, 'Tom', 'J', 'Cat', NULL, 0),
(11, 25, 8, NULL, 'sad', 'G', 'red', NULL, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tblusers_acc`
--

CREATE TABLE `tblusers_acc` (
  `acc_id` int(11) NOT NULL,
  `user_email` varchar(50) NOT NULL,
  `user_pass` varchar(50) NOT NULL,
  `user_role` varchar(50) NOT NULL,
  `archived` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tblusers_acc`
--

INSERT INTO `tblusers_acc` (`acc_id`, `user_email`, `user_pass`, `user_role`, `archived`) VALUES
(6, 'admin6@gmail.com', 'adminadmin', 'admin', 0),
(10, 'geo2@gmail.com', 'red', 'admin', 0),
(12, 'gheo@gmail.com', 'gheo', 'student', 0),
(16, 'alex@gmail.com', 'alex', 'student', 0),
(17, 'tom@gmail.com', 'cat', 'student', 0),
(19, 'jerry@gmail.com', 'tom', 'student', 0),
(20, 'jeffrey@gmail.com', 'jeffrey', 'student', 0),
(21, 'student@gmail.com', 'student', 'student', 0),
(22, 'first@gmail.com', 'first', 'student', 0),
(24, 'sda', 'sad', 'student', 0),
(25, 'das', 'me', 'student', 0),
(31, 'coad@gmail.com', 'coad', 'co-admin', 0),
(32, 'sdasa', 'sdsa', 'co-admin', 0),
(33, 'sdasadas', 'dsadsa', 'co-admin', 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbladmins`
--
ALTER TABLE `tbladmins`
  ADD PRIMARY KEY (`admin_id`),
  ADD KEY `tbladmins_tblusers_accfk` (`acc_id`);

--
-- Indexes for table `tblattendances`
--
ALTER TABLE `tblattendances`
  ADD PRIMARY KEY (`attendance_id`),
  ADD KEY `tblattendances_tblstudents_fk` (`student_id`),
  ADD KEY `tblattendances_tblclasses_fk` (`class_id`),
  ADD KEY `tblattendances_tblco_admins_fk` (`co_admin_id`),
  ADD KEY `tblattendances_tbladmins_fk` (`admin_id`);

--
-- Indexes for table `tblclasses`
--
ALTER TABLE `tblclasses`
  ADD PRIMARY KEY (`class_id`),
  ADD UNIQUE KEY `class_id` (`class_code`) USING BTREE;

--
-- Indexes for table `tblco_admins`
--
ALTER TABLE `tblco_admins`
  ADD PRIMARY KEY (`co_admin_id`) USING BTREE,
  ADD KEY `tblco_admins_tblusers_acc_fk` (`acc_id`),
  ADD KEY `tblco_admins_tbladmins_fk` (`admin_id`);

--
-- Indexes for table `tblstudents`
--
ALTER TABLE `tblstudents`
  ADD PRIMARY KEY (`student_id`),
  ADD KEY `tblstudents_tblusers_acc_fk` (`acc_id`),
  ADD KEY `tblstudents_tbladmins_fk` (`admin_id`),
  ADD KEY `tblstudents_tblclasses_fk` (`class_id`),
  ADD KEY `tblstudents_tblco_admins_fk` (`co_admin_id`);

--
-- Indexes for table `tblusers_acc`
--
ALTER TABLE `tblusers_acc`
  ADD PRIMARY KEY (`acc_id`),
  ADD UNIQUE KEY `username` (`user_email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbladmins`
--
ALTER TABLE `tbladmins`
  MODIFY `admin_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `tblattendances`
--
ALTER TABLE `tblattendances`
  MODIFY `attendance_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=78;

--
-- AUTO_INCREMENT for table `tblclasses`
--
ALTER TABLE `tblclasses`
  MODIFY `class_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `tblco_admins`
--
ALTER TABLE `tblco_admins`
  MODIFY `co_admin_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `tblstudents`
--
ALTER TABLE `tblstudents`
  MODIFY `student_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `tblusers_acc`
--
ALTER TABLE `tblusers_acc`
  MODIFY `acc_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbladmins`
--
ALTER TABLE `tbladmins`
  ADD CONSTRAINT `tbladmins_tblusers_accfk` FOREIGN KEY (`acc_id`) REFERENCES `tblusers_acc` (`acc_id`) ON UPDATE CASCADE;

--
-- Constraints for table `tblattendances`
--
ALTER TABLE `tblattendances`
  ADD CONSTRAINT `tblattendances_tbladmins_fk` FOREIGN KEY (`admin_id`) REFERENCES `tbladmins` (`admin_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `tblattendances_tblclasses_fk` FOREIGN KEY (`class_id`) REFERENCES `tblclasses` (`class_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tblattendances_tblco_admins_fk` FOREIGN KEY (`co_admin_id`) REFERENCES `tblco_admins` (`co_admin_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tblattendances_tblstudents_fk` FOREIGN KEY (`student_id`) REFERENCES `tblstudents` (`student_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tblco_admins`
--
ALTER TABLE `tblco_admins`
  ADD CONSTRAINT `tblco_admins_tbladmins_fk` FOREIGN KEY (`admin_id`) REFERENCES `tbladmins` (`admin_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tblco_admins_tblusers_acc_fk` FOREIGN KEY (`acc_id`) REFERENCES `tblusers_acc` (`acc_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `tblstudents`
--
ALTER TABLE `tblstudents`
  ADD CONSTRAINT `tblstudents_tbladmins_fk` FOREIGN KEY (`admin_id`) REFERENCES `tbladmins` (`admin_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `tblstudents_tblclasses_fk` FOREIGN KEY (`class_id`) REFERENCES `tblclasses` (`class_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `tblstudents_tblco_admins_fk` FOREIGN KEY (`co_admin_id`) REFERENCES `tblco_admins` (`co_admin_id`) ON UPDATE CASCADE,
  ADD CONSTRAINT `tblstudents_tblusers_acc_fk` FOREIGN KEY (`acc_id`) REFERENCES `tblusers_acc` (`acc_id`) ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
