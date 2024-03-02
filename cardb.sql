-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th7 03, 2023 lúc 06:17 PM
-- Phiên bản máy phục vụ: 10.4.25-MariaDB
-- Phiên bản PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `cardb`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `car_tb`
--

CREATE TABLE `car_tb` (
  `idCar` int(11) NOT NULL,
  `Brand` varchar(255) NOT NULL,
  `Model` varchar(250) NOT NULL,
  `Status` varchar(200) NOT NULL,
  `Price` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `car_tb`
--

INSERT INTO `car_tb` (`idCar`, `Brand`, `Model`, `Status`, `Price`) VALUES
(4, 'fff', 'frtt', 'Booked', 2878),
(5, 'fff', 'frtt', 'đg', 2878),
(7, 'fff', 'frtt', 'đg', 2878),
(8, 'fff', 'frtt', 'đg', 2878);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `customer_tb`
--

CREATE TABLE `customer_tb` (
  `idCustomer` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `gender` varchar(255) NOT NULL,
  `diachi` varchar(250) NOT NULL,
  `phone` varchar(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `customer_tb`
--

INSERT INTO `customer_tb` (`idCustomer`, `username`, `gender`, `diachi`, `phone`) VALUES
(1, 'Thuy Tien', 'Nam', 'fc', '43'),
(455, 'tien123', 'Nam', 'Hậu giang', '0923337770');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `rent_tb`
--

CREATE TABLE `rent_tb` (
  `RentId` int(11) NOT NULL,
  `CarReg` varchar(100) NOT NULL,
  `CusName` varchar(250) NOT NULL,
  `RentDate` date NOT NULL,
  `ReturnDate` date NOT NULL,
  `RentFee` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `rent_tb`
--

INSERT INTO `rent_tb` (`RentId`, `CarReg`, `CusName`, `RentDate`, `ReturnDate`, `RentFee`) VALUES
(3, '3', 'Thuy Tien', '2023-07-20', '2023-07-28', '5465'),
(6, '6', 'Thuy Tien', '2023-07-02', '2023-07-04', '7578'),
(7, '1', 'Thuy Tien', '2023-07-04', '2023-07-20', '74523'),
(8, '2', 'Thuy Tien', '2023-07-10', '2023-07-14', '7587'),
(9, '2', 'Thuy Tien', '2023-07-10', '2023-07-14', '7587'),
(10, '2', 'Thuy Tien', '2023-07-10', '2023-07-14', '7587'),
(11, '2', 'Thuy Tien', '2023-07-10', '2023-07-14', '7587'),
(12, '1', 'Thuy Tien', '2023-07-04', '2023-07-06', '564'),
(13, '2', 'Thuy Tien', '2023-07-18', '2023-07-26', '87587'),
(14, '6', 'Thuy Tien', '2023-07-19', '2023-07-28', '545'),
(15, '3', 'Thuy Tien', '2023-07-10', '2023-07-12', '6551'),
(26, '4', 'Thuy Tien', '2023-07-03', '2023-07-06', '4354');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `return_tb`
--

CREATE TABLE `return_tb` (
  `RetId` int(13) NOT NULL,
  `CarReg` varchar(100) NOT NULL,
  `CusName` varchar(250) NOT NULL,
  `RetDate` date NOT NULL,
  `Delay` int(11) NOT NULL,
  `Fine` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `return_tb`
--

INSERT INTO `return_tb` (`RetId`, `CarReg`, `CusName`, `RetDate`, `Delay`, `Fine`) VALUES
(1, '102', 'tien123', '2023-07-11', 1, 1),
(2, '102', 'Thuy Tien', '2023-07-19', 2, 1554),
(3, '102', 'tien123', '2023-07-11', 2, 199998),
(4, '11', 'tien123', '2023-07-14', 1, 22),
(5, '4', 'Thuy Tien', '2023-07-08', 1, 545),
(6, '6', 'Thuy Tien', '2023-07-06', 2, 15156),
(7, '1', 'Thuy Tien', '2023-07-22', 2, 149046),
(8, '3', 'Thuy Tien', '2023-07-29', 1, 5465),
(9, '1', 'Thuy Tien', '2023-07-07', 3, 750),
(10, '4', 'Thuy Tien', '2023-07-08', 1, 545),
(11, '2', 'Thuy Tien', '2023-07-17', 3, 22761),
(12, '2', 'Thuy Tien', '2023-07-15', 1, 7587),
(13, '1', 'Thuy Tien', '2023-07-15', 1, 7587);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Đang đổ dữ liệu cho bảng `user`
--

INSERT INTO `user` (`id`, `username`, `password`) VALUES
(1, 'admin', '123');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `car_tb`
--
ALTER TABLE `car_tb`
  ADD PRIMARY KEY (`idCar`);

--
-- Chỉ mục cho bảng `customer_tb`
--
ALTER TABLE `customer_tb`
  ADD PRIMARY KEY (`idCustomer`);

--
-- Chỉ mục cho bảng `rent_tb`
--
ALTER TABLE `rent_tb`
  ADD PRIMARY KEY (`RentId`);

--
-- Chỉ mục cho bảng `return_tb`
--
ALTER TABLE `return_tb`
  ADD PRIMARY KEY (`RetId`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
