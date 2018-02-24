-- phpMyAdmin SQL Dump
-- version 4.6.0
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Jun 30, 2016 at 07:35 PM
-- Server version: 5.5.49
-- PHP Version: 5.6.22

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `placeshare`
--

-- --------------------------------------------------------

--
-- Table structure for table `category`
--

CREATE TABLE `category` (
  `id` int(11) NOT NULL,
  `name` varchar(65) COLLATE utf8mb4_unicode_ci NOT NULL,
  `tag` varchar(65) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `device_token`
--

CREATE TABLE `device_token` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `token` varchar(200) COLLATE utf8mb4_unicode_ci NOT NULL,
  `os` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL,
  `status` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `modified_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `device_token`
--

INSERT INTO `device_token` (`id`, `user_id`, `token`, `os`, `status`, `created_at`, `modified_at`) VALUES
(1, 1, 'dATNcVUFnVw:APA91bE42SK4OXtOygCU6fxyr6Src_G8GQ9ghfKwdsICTXyXnRxJcGlhmzX_23-0dxUOBT69BZ0SSOsIXxIg3hCftFipuLczdV2WZ5p9A-Ip0sYfPyi-en51LRw38lHU_x3Ugbeh5xon', 'android', 1, '2016-06-19 00:00:00', '2016-06-19 00:00:00'),
(2, 2, 'eGMF2wjuO8I:APA91bExbPMj9i7_DkkqUiTyqrX7S6MmeVnLDXIzRwl-Fis3b4L8T_ZCJ5v39BB1gzl2G4ti1MAP_dOesJ7c42ZNZrCGNQkI5jSp1tGJBccsJnbeZ_912HN3QbdE4xgzaUAZlBx4j4pf', 'android', 1, '2016-06-19 00:00:00', '2016-06-19 00:00:00'),
(3, 1, 'f225hykzEls:APA91bG0V3TZo5ebi5_yb8TgEhrPeU_C7iOo7N8qSQhhOVIwcIALYeNPaPygTbnBdk3yv5Qrzepk4KHkcneQES2uy3KTnEqlPBX0ryTxyxapReaIwXkIZJ7gkVJb9hUlzh9OrNaS_8SC', 'android', 1, '2016-06-20 00:00:00', '2016-06-20 00:00:00'),
(4, 2, 'cJnbLV73fxA:APA91bFGW1PMar8Mm7Osh02RGiKlb7tWWD1eVLNuznFdX82taQZNzE-jcPq9MM-5XrdrdNdKm3DnylMFAqxWdNp2As1e3fMQq9wgVE5f7Dh_KRD9Unzn4nyCux4s1bzN1aF9o1BaGmvk', 'android', 1, '2016-06-20 00:00:00', '2016-06-20 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `follow_place`
--

CREATE TABLE `follow_place` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `place_id` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `modified_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `follow_place`
--

INSERT INTO `follow_place` (`id`, `user_id`, `place_id`, `type`, `created_at`, `modified_at`) VALUES
(1, 1, 1, 1, '2016-06-20 01:44:03', '2016-06-20 01:44:03'),
(2, 2, 1, 1, '2016-06-20 02:54:54', '2016-06-20 02:54:54'),
(3, 2, 16, 1, '2016-06-20 05:55:41', '2016-06-20 05:55:41'),
(4, 2, 4, 0, '2016-06-20 06:30:36', '2016-06-20 06:30:36'),
(5, 1, 4, 1, '2016-06-20 08:23:21', '2016-06-20 08:23:21');

-- --------------------------------------------------------

--
-- Table structure for table `like_post`
--

CREATE TABLE `like_post` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `post_id` int(11) NOT NULL,
  `type` int(11) NOT NULL,
  `created_at` datetime NOT NULL,
  `modified_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `like_post`
--

INSERT INTO `like_post` (`id`, `user_id`, `post_id`, `type`, `created_at`, `modified_at`) VALUES
(1, 1, 140, 1, '2016-06-18 16:56:56', '2016-06-18 16:56:56'),
(3, 2, 140, 1, '2016-07-01 00:05:18', '2016-07-01 00:05:18'),
(4, 3, 3, 1, '2016-07-01 00:06:30', '2016-07-01 00:06:30'),
(5, 1, 3, 1, '2016-07-01 00:10:04', '2016-07-01 00:10:04'),
(6, 2, 3, 1, '2016-07-01 00:10:10', '2016-07-01 00:10:10'),
(7, 4, 3, 1, '2016-07-01 00:10:14', '2016-07-01 00:10:14');

-- --------------------------------------------------------

--
-- Table structure for table `photo`
--

CREATE TABLE `photo` (
  `id` int(11) NOT NULL,
  `post_id` int(11) NOT NULL,
  `url` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime NOT NULL,
  `modified_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `photo`
--

INSERT INTO `photo` (`id`, `post_id`, `url`, `created_at`, `modified_at`) VALUES
(1, 3, 'http://www.mcn.vn/old/images/MCN/Tam/261113/huongrung1.jpg', '2016-06-18 02:16:54', '2016-06-18 02:16:54'),
(2, 3, 'http://images.muachungdeals.net/Upload/15046/PR/_2013121152121610.jpg', '2016-06-18 00:00:00', '2016-06-18 00:00:00'),
(3, 34, 'http://itshareplus.com/upload/1466382895_ngoinhayeuthuong.jpg', '2016-06-20 07:36:37', '2016-06-20 07:36:37'),
(9, 69, 'http://itshareplus.com/upload/1467280627_image_20160630_165655.jpg', '2016-06-30 16:57:07', '2016-06-30 16:57:07'),
(12, 75, 'http://itshareplus.com/upload/1467286899_XBox_Blue.png', '2016-06-30 18:41:39', '2016-06-30 18:41:39'),
(13, 75, 'http://itshareplus.com/upload/1467286899_xbox_green.ico', '2016-06-30 18:41:39', '2016-06-30 18:41:39'),
(14, 99, 'http://itshareplus.com/upload/1467289732_XBox_Blue.png', '2016-06-30 19:28:52', '2016-06-30 19:28:52'),
(15, 99, 'http://itshareplus.com/upload/1467289732_XBox.png', '2016-06-30 19:28:52', '2016-06-30 19:28:52'),
(16, 100, 'http://itshareplus.com/upload/1467289937_image_20160630_193205.jpg', '2016-06-30 19:32:17', '2016-06-30 19:32:17'),
(17, 101, 'http://itshareplus.com/upload/1467289984_image_20160630_193258.jpg', '2016-06-30 19:33:04', '2016-06-30 19:33:04'),
(18, 104, 'http://itshareplus.com/upload/1467290538_image_20160630_194205.jpg', '2016-06-30 19:42:18', '2016-06-30 19:42:18'),
(19, 104, 'http://itshareplus.com/upload/1467290538_image_20160630_194214.jpg', '2016-06-30 19:42:18', '2016-06-30 19:42:18'),
(20, 105, 'http://itshareplus.com/upload/1467294197_image_20160630_204311.jpg', '2016-06-30 20:43:17', '2016-06-30 20:43:17'),
(21, 106, 'http://itshareplus.com/upload/1467296145_1467294191904.jpg', '2016-06-30 21:15:45', '2016-06-30 21:15:45'),
(22, 107, 'http://itshareplus.com/upload/1467296148_1467294191904.jpg', '2016-06-30 21:15:48', '2016-06-30 21:15:48'),
(23, 108, 'http://itshareplus.com/upload/1467296175_1467219215082.jpg', '2016-06-30 21:16:15', '2016-06-30 21:16:15'),
(24, 109, 'http://itshareplus.com/upload/1467296437_received_716325155176303.jpeg', '2016-06-30 21:20:37', '2016-06-30 21:20:37'),
(25, 110, 'http://itshareplus.com/upload/1467296566_20160630_202832_HDR.jpg', '2016-06-30 21:22:46', '2016-06-30 21:22:46'),
(26, 110, 'http://itshareplus.com/upload/1467296566_1467222561548.jpg', '2016-06-30 21:22:46', '2016-06-30 21:22:46'),
(27, 110, 'http://itshareplus.com/upload/1467296566_1467220960254.jpg', '2016-06-30 21:22:46', '2016-06-30 21:22:46'),
(28, 110, 'http://itshareplus.com/upload/1467296566_1467220956310.jpg', '2016-06-30 21:22:46', '2016-06-30 21:22:46'),
(29, 111, 'http://itshareplus.com/upload/1467296591_20160630_202832_HDR.jpg', '2016-06-30 21:23:11', '2016-06-30 21:23:11'),
(30, 111, 'http://itshareplus.com/upload/1467296591_1467222561548.jpg', '2016-06-30 21:23:11', '2016-06-30 21:23:11'),
(31, 111, 'http://itshareplus.com/upload/1467296591_1467220960254.jpg', '2016-06-30 21:23:11', '2016-06-30 21:23:11'),
(32, 111, 'http://itshareplus.com/upload/1467296591_1467220956310.jpg', '2016-06-30 21:23:11', '2016-06-30 21:23:11'),
(33, 112, 'http://itshareplus.com/upload/1467296615_20160630_202832_HDR.jpg', '2016-06-30 21:23:35', '2016-06-30 21:23:35'),
(34, 112, 'http://itshareplus.com/upload/1467296615_1467222561548.jpg', '2016-06-30 21:23:35', '2016-06-30 21:23:35'),
(35, 112, 'http://itshareplus.com/upload/1467296615_1467220960254.jpg', '2016-06-30 21:23:35', '2016-06-30 21:23:35'),
(36, 112, 'http://itshareplus.com/upload/1467296615_1467220956310.jpg', '2016-06-30 21:23:35', '2016-06-30 21:23:35'),
(37, 113, 'http://itshareplus.com/upload/1467296633_20160630_202832_HDR.jpg', '2016-06-30 21:23:53', '2016-06-30 21:23:53'),
(38, 113, 'http://itshareplus.com/upload/1467296633_1467222561548.jpg', '2016-06-30 21:23:53', '2016-06-30 21:23:53'),
(39, 113, 'http://itshareplus.com/upload/1467296633_1467220960254.jpg', '2016-06-30 21:23:53', '2016-06-30 21:23:53'),
(40, 113, 'http://itshareplus.com/upload/1467296633_1467220956310.jpg', '2016-06-30 21:23:53', '2016-06-30 21:23:53'),
(41, 114, 'http://itshareplus.com/upload/1467296638_20160630_202832_HDR.jpg', '2016-06-30 21:23:58', '2016-06-30 21:23:58'),
(42, 114, 'http://itshareplus.com/upload/1467296638_1467222561548.jpg', '2016-06-30 21:23:58', '2016-06-30 21:23:58'),
(43, 114, 'http://itshareplus.com/upload/1467296638_1467220960254.jpg', '2016-06-30 21:23:58', '2016-06-30 21:23:58'),
(44, 114, 'http://itshareplus.com/upload/1467296638_1467220956310.jpg', '2016-06-30 21:23:58', '2016-06-30 21:23:58'),
(45, 115, 'http://itshareplus.com/upload/1467296642_20160630_202832_HDR.jpg', '2016-06-30 21:24:02', '2016-06-30 21:24:02'),
(46, 115, 'http://itshareplus.com/upload/1467296642_1467222561548.jpg', '2016-06-30 21:24:02', '2016-06-30 21:24:02'),
(47, 115, 'http://itshareplus.com/upload/1467296642_1467220960254.jpg', '2016-06-30 21:24:02', '2016-06-30 21:24:02'),
(48, 115, 'http://itshareplus.com/upload/1467296642_1467220956310.jpg', '2016-06-30 21:24:02', '2016-06-30 21:24:02'),
(49, 116, 'http://itshareplus.com/upload/1467296646_20160630_202832_HDR.jpg', '2016-06-30 21:24:06', '2016-06-30 21:24:06'),
(50, 116, 'http://itshareplus.com/upload/1467296646_1467222561548.jpg', '2016-06-30 21:24:06', '2016-06-30 21:24:06'),
(51, 116, 'http://itshareplus.com/upload/1467296646_1467220960254.jpg', '2016-06-30 21:24:06', '2016-06-30 21:24:06'),
(52, 116, 'http://itshareplus.com/upload/1467296646_1467220956310.jpg', '2016-06-30 21:24:06', '2016-06-30 21:24:06'),
(53, 117, 'http://itshareplus.com/upload/1467296651_20160630_202832_HDR.jpg', '2016-06-30 21:24:11', '2016-06-30 21:24:11'),
(54, 117, 'http://itshareplus.com/upload/1467296651_1467222561548.jpg', '2016-06-30 21:24:11', '2016-06-30 21:24:11'),
(55, 117, 'http://itshareplus.com/upload/1467296651_1467220960254.jpg', '2016-06-30 21:24:11', '2016-06-30 21:24:11'),
(56, 117, 'http://itshareplus.com/upload/1467296651_1467220956310.jpg', '2016-06-30 21:24:11', '2016-06-30 21:24:11'),
(57, 118, 'http://itshareplus.com/upload/1467300010_1467294191904.jpg', '2016-06-30 22:20:10', '2016-06-30 22:20:10'),
(58, 120, 'http://itshareplus.com/upload/1467300570_1467294191904.jpg', '2016-06-30 22:29:30', '2016-06-30 22:29:30'),
(59, 120, 'http://itshareplus.com/upload/1467300570_20160630_202849_HDR.jpg', '2016-06-30 22:29:30', '2016-06-30 22:29:30'),
(60, 121, 'http://itshareplus.com/upload/1467300572_1467294191904.jpg', '2016-06-30 22:29:32', '2016-06-30 22:29:32'),
(61, 121, 'http://itshareplus.com/upload/1467300572_20160630_202849_HDR.jpg', '2016-06-30 22:29:32', '2016-06-30 22:29:32'),
(62, 122, 'http://itshareplus.com/upload/1467300594_received_694354884037057.jpeg', '2016-06-30 22:29:54', '2016-06-30 22:29:54'),
(63, 122, 'http://itshareplus.com/upload/1467300594_1467220960254.jpg', '2016-06-30 22:29:54', '2016-06-30 22:29:54'),
(64, 122, 'http://itshareplus.com/upload/1467300594_1467220965442.jpg', '2016-06-30 22:29:54', '2016-06-30 22:29:54'),
(65, 122, 'http://itshareplus.com/upload/1467300594_1467222554949.jpg', '2016-06-30 22:29:54', '2016-06-30 22:29:54'),
(66, 124, 'http://itshareplus.com/upload/1467300639_1467220960254.jpg', '2016-06-30 22:30:39', '2016-06-30 22:30:39'),
(67, 124, 'http://itshareplus.com/upload/1467300639_1467220965442.jpg', '2016-06-30 22:30:39', '2016-06-30 22:30:39'),
(68, 124, 'http://itshareplus.com/upload/1467300639_1467222554949.jpg', '2016-06-30 22:30:39', '2016-06-30 22:30:39'),
(69, 128, 'http://itshareplus.com/upload/1467301371_1467222554949.jpg', '2016-06-30 22:42:51', '2016-06-30 22:42:51'),
(70, 128, 'http://itshareplus.com/upload/1467301371_1467220960254.jpg', '2016-06-30 22:42:51', '2016-06-30 22:42:51'),
(71, 129, 'http://itshareplus.com/upload/1467302799_1467290525311.jpg', '2016-06-30 23:06:39', '2016-06-30 23:06:39'),
(72, 129, 'http://itshareplus.com/upload/1467302799_1467290410686.jpg', '2016-06-30 23:06:39', '2016-06-30 23:06:39'),
(73, 130, 'http://itshareplus.com/upload/1467303137_Photo_20160630_231151.jpg', '2016-06-30 23:12:17', '2016-06-30 23:12:17'),
(74, 131, 'http://itshareplus.com/upload/1467303137_Photo_20160630_231151.jpg', '2016-06-30 23:12:17', '2016-06-30 23:12:17'),
(75, 132, 'http://itshareplus.com/upload/1467303139_Photo_20160630_231151.jpg', '2016-06-30 23:12:19', '2016-06-30 23:12:19'),
(76, 133, 'http://itshareplus.com/upload/1467303140_Photo_20160630_231151.jpg', '2016-06-30 23:12:20', '2016-06-30 23:12:20'),
(77, 134, 'http://itshareplus.com/upload/1467303140_Photo_20160630_231151.jpg', '2016-06-30 23:12:20', '2016-06-30 23:12:20'),
(78, 135, 'http://itshareplus.com/upload/1467303141_Photo_20160630_231151.jpg', '2016-06-30 23:12:21', '2016-06-30 23:12:21'),
(79, 136, 'http://itshareplus.com/upload/1467303930_received_1803608233193569.jpeg', '2016-06-30 23:25:30', '2016-06-30 23:25:30'),
(80, 137, 'http://itshareplus.com/upload/1467303969_1467294191904.jpg', '2016-06-30 23:26:09', '2016-06-30 23:26:09'),
(81, 138, 'http://itshareplus.com/upload/1467304853_1467220960254.jpg', '2016-06-30 23:40:53', '2016-06-30 23:40:53'),
(82, 138, 'http://itshareplus.com/upload/1467304853_1467220956310.jpg', '2016-06-30 23:40:53', '2016-06-30 23:40:53'),
(83, 138, 'http://itshareplus.com/upload/1467304853_1467222554949.jpg', '2016-06-30 23:40:53', '2016-06-30 23:40:53'),
(84, 138, 'http://itshareplus.com/upload/1467304853_1467220965442.jpg', '2016-06-30 23:40:53', '2016-06-30 23:40:53'),
(85, 139, 'http://itshareplus.com/upload/1467305200_1467290281341.jpg', '2016-06-30 23:46:40', '2016-06-30 23:46:40'),
(86, 139, 'http://itshareplus.com/upload/1467305200_1467290210818.jpg', '2016-06-30 23:46:40', '2016-06-30 23:46:40'),
(87, 140, 'http://itshareplus.com/upload/1467306057_2.jpg', '2016-07-01 00:00:57', '2016-07-01 00:00:57'),
(88, 140, 'http://itshareplus.com/upload/1467306057_3.jpg', '2016-07-01 00:00:57', '2016-07-01 00:00:57'),
(89, 140, 'http://itshareplus.com/upload/1467306057_4.jpg', '2016-07-01 00:00:57', '2016-07-01 00:00:57'),
(90, 140, 'http://itshareplus.com/upload/1467306057_1.jpg', '2016-07-01 00:00:57', '2016-07-01 00:00:57');

-- --------------------------------------------------------

--
-- Table structure for table `place`
--

CREATE TABLE `place` (
  `id` int(11) NOT NULL,
  `creator` int(11) DEFAULT NULL,
  `google_place_id` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `lat` double NOT NULL,
  `lng` double NOT NULL,
  `name` varchar(65) COLLATE utf8mb4_unicode_ci NOT NULL,
  `address` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `website` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `phone_number` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `inter_phone_number` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `products` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `keyword` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime NOT NULL,
  `modified_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `place`
--

INSERT INTO `place` (`id`, `creator`, `google_place_id`, `lat`, `lng`, `name`, `address`, `website`, `phone_number`, `inter_phone_number`, `description`, `products`, `keyword`, `created_at`, `modified_at`) VALUES
(1, NULL, 'ChIJB9hsVBovdTERjLDmr_qQhoA', 0, 0, 'Hương Rừng 良い衣装', '6B Nguyễn Cảnh Chân, Nguyễn Cư Trinh, Quận 1, Hồ Chí Minh', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(2, NULL, 'ChIJB9hsVBovdTaERjLDmr_qQhoA', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(3, NULL, 'ChIJB9hsVBovdTEjLDmr_qQhoA', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(5, NULL, 'ChIJ52GFUQMvdTER5eufCYurbzU', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(6, NULL, 'ChIJ8zexvxsvdTERziMo3kSioak', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(7, NULL, 'ChIJ0eysmxAvdTERNXzaoE6o5k0', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(8, NULL, 'ChIJB1miixkvdTERwruelC-0jc4', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(9, NULL, 'ChIJ2R9aixkvdTERhOxPxb-RtNU', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(10, NULL, 'ChIJMZo8-hkvdTERfQTJeNX7_3g', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(11, NULL, 'ChIJfz1ekBsvdTERBr4iAVbwVBs', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(12, NULL, 'ChIJ7cLMsBkvdTERtehmn59OhSc', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(13, NULL, 'ChIJnSBc0BkvdTERxkEhCK8lg-8', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(14, NULL, 'ChIJDbQ0GB0vdTERnXqnMrKYtZE', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(15, NULL, 'ChIJ41G3owIvdTERckHu7T2z_qw', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(16, 1, NULL, 10.759372, 106.668334, 'Tiệm hớt tóc 20k', 'hẻm Ngô Gia Tự, gần KTX ĐH Y Dược', '', '', '', '', 'hair cut,25000;', 'Beauty salon', '2016-06-20 00:00:00', '2016-06-20 00:00:00'),
(17, NULL, '16', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(18, NULL, '16', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(19, NULL, '4', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(20, NULL, '4', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(21, NULL, 'ChIJ41IgvxcvdTERY5O6qKBpRP4', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(22, NULL, 'ChIJj4LADDsvdTERM2zkFk6finA', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(23, NULL, 'ChIJP5V8qBcvdTERwSXQDX_nJVc', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(24, NULL, 'ChIJ7TdvNzgvdTERHldFIP7djW0', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(25, NULL, 'ChIJq40Xq0ovdTER2OUHxZjVuVE', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(26, NULL, 'ChIJwRZq7vsudTERovOIsztOAPA', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(28, 2, NULL, 10.759694, 106.669106, 'Thế giới di động', '180 Nguyễn Tri Phương, Phường 9, Quận 5', 'thegioididong.com', '08243598225', '843288166452', 'Nơi mua bán trao đổi thiết bị di động', 'iphone 6,15000000;galaxy s7,12000000;zenfone 2,6000000;galaxy A5,5000000;laptop asus,15000000;laptop dell,12000000;', 'Shopping Mall', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(29, 2, NULL, 10.760789, 106.668818, 'Hoàng Hà Mobile', 'Nguyễn Chí Thanh', '', '08583855554', '', '', 'iphone 6,18000000;', 'Shopping Mall', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(30, NULL, 'ChIJN-2f6OQudTERiQzJ-oHMkEA', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(31, NULL, 'ChIJN-2f6OQudTERiQzJ-oHMkEA', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(32, NULL, 'ChIJcYbkpugudTEReWbfCN1So1o', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(33, NULL, 'ChIJbftC1_wudTERvT_iUOVWbUQ', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(34, NULL, 'ChIJEae7feMudTEROyhHeKgZmxg', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(35, NULL, 'ChIJ_fExZeMudTERza4ijP8G6vY', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(36, NULL, 'ChIJ_fExZeMudTERza4ijP8G6vY', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00'),
(37, NULL, 'ChIJ5QCuTRkvdTERY_6WvdDFkEs', 0, 0, '', '', '', '', '', '', '', '', '0000-00-00 00:00:00', '0000-00-00 00:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `post`
--

CREATE TABLE `post` (
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `place_id` int(11) NOT NULL,
  `status` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime NOT NULL,
  `modified_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `post`
--

INSERT INTO `post` (`id`, `user_id`, `place_id`, `status`, `created_at`, `modified_at`) VALUES
(1, 1, 1, 'Có thương hiệu, nhất là món chồn, nhân viên đẹp, lịch sự, nhưng giá hơi mắc', '2016-06-18 00:55:17', '2016-06-18 00:55:17'),
(2, 2, 1, 'Gần trường mình mà lần đầu tiên mới được đi ăn. Ngon quá cơ mà hơi mắc, cũng phải thôi vì các chị em phục vụ rất tận tình. ', '2016-06-18 01:23:38', '2016-06-18 01:23:38'),
(3, 1, 1, 'Check in Hương Rừng', '2016-06-18 02:16:54', '2016-06-18 02:16:54'),
(4, 1, 1, 'BUFFET TRƯA\r\n- Người lớn: 180.000 đồng / khách\r\n- Trẻ em (cao từ 1m – 1m3): 90.000 đồng / khách\r\n Thời gian phục vụ buffet trưa: 11h - 14h\r\n    \r\nBUFFET TỐI\r\n- Người lớn: 395.000 đồng / khách\r\n- Trẻ em (cao từ 1m – 1m3): 197.500 đồng / khách\r\n Thời gian phục vụ buffet tối: 17h30 - 21h30\r\n \r\nĐặc biệt: Thưởng thức bia Draugh miễn phí từ 18h đến 21h mỗi ngày.', '2016-06-19 23:13:40', '2016-06-19 23:13:40'),
(31, 2, 16, 'Hớt tóc ở đây rẻ quá. ahihi', '2016-06-20 05:56:56', '2016-06-20 05:56:56'),
(32, 2, 4, 'Hôm nay thức tới 7h sáng zui quá Hiệp nhỉ?', '2016-06-20 06:16:58', '2016-06-20 06:16:58'),
(33, 2, 4, 'hic', '2016-06-20 06:18:43', '2016-06-20 06:18:43'),
(34, 1, 4, 'Lại sắp phải chuyển chỗ, rời xa ngôi nhà đã gắn bó 1 năm này rồi. Haizz!', '2016-06-20 07:36:37', '2016-06-20 07:36:37'),
(136, 2, 28, 'đẹp quá', '2016-06-30 23:25:30', '2016-06-30 23:25:30'),
(137, 2, 28, 'hehw', '2016-06-30 23:26:09', '2016-06-30 23:26:09'),
(140, 1, 1, 'check in tại Hương Rừng quán. Hihi', '2016-07-01 00:00:57', '2016-07-01 00:00:57');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(65) COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(65) COLLATE utf8mb4_unicode_ci NOT NULL,
  `full_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `avatar` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `facebook_token` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime NOT NULL,
  `modified_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `username`, `password`, `full_name`, `avatar`, `facebook_token`, `created_at`, `modified_at`) VALUES
(1, 'hiepit', '123456', 'Mai Thành Hiệp', 'http://itshareplus.com/upload/hiepit_avatar.jpg', '', '2016-06-15 00:00:00', '2016-06-15 00:00:00'),
(2, 'tutran95', '123456', 'Trần Tuấn Tú', 'http://itshareplus.com/upload/tu_tran.jpg', '', '2016-06-18 00:00:00', '2016-06-18 00:00:00'),
(3, 'admin', '123456', 'admin', 'http://itshareplus.com/upload/hiepit_avatar.jpg', '', '2016-07-01 00:00:00', '2016-07-01 00:00:00'),
(4, 'admin1', '123456', 'admin1', 'http://itshareplus.com/upload/hiepit_avatar.jpg', '', '2016-07-01 00:00:00', '2016-07-01 00:00:00');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `device_token`
--
ALTER TABLE `device_token`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `follow_place`
--
ALTER TABLE `follow_place`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `like_post`
--
ALTER TABLE `like_post`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `photo`
--
ALTER TABLE `photo`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `place`
--
ALTER TABLE `place`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `post`
--
ALTER TABLE `post`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `device_token`
--
ALTER TABLE `device_token`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
--
-- AUTO_INCREMENT for table `follow_place`
--
ALTER TABLE `follow_place`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
--
-- AUTO_INCREMENT for table `like_post`
--
ALTER TABLE `like_post`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `photo`
--
ALTER TABLE `photo`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=91;
--
-- AUTO_INCREMENT for table `place`
--
ALTER TABLE `place`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;
--
-- AUTO_INCREMENT for table `post`
--
ALTER TABLE `post`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=141;
--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
