-- phpMyAdmin SQL Dump
-- version 4.2.7.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Sep 03, 2021 at 06:52 PM
-- Server version: 5.6.20
-- PHP Version: 5.5.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `laravel_english_class`
--

-- --------------------------------------------------------

--
-- Table structure for table `failed_jobs`
--

CREATE TABLE IF NOT EXISTS `failed_jobs` (
  `id` bigint(20) unsigned NOT NULL,
  `uuid` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `connection` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `queue` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `payload` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `exception` longtext COLLATE utf8mb4_unicode_ci NOT NULL,
  `failed_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `migrations`
--

CREATE TABLE IF NOT EXISTS `migrations` (
  `id` int(10) unsigned NOT NULL,
  `migration` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `migrations`
--

INSERT INTO `migrations` (`id`, `migration`, `batch`) VALUES
(1, '2014_10_12_000000_create_users_table', 1),
(2, '2014_10_12_100000_create_password_resets_table', 1),
(3, '2019_08_19_000000_create_failed_jobs_table', 1),
(4, '2021_08_18_021922_create_dosen_models_table', 1),
(5, '2021_08_18_040552_create_kelas_models_table', 2),
(7, '2021_08_18_042222_create_meeting_models_table', 3),
(8, '2021_08_18_075527_create_meeting_materi_models_table', 4),
(9, '2021_08_19_072704_create_meeting_kuis_models_table', 5),
(10, '2021_08_19_083521_create_meeting_record_models_table', 6),
(11, '2021_08_20_022959_create_siswa_models_table', 7);

-- --------------------------------------------------------

--
-- Table structure for table `password_resets`
--

CREATE TABLE IF NOT EXISTS `password_resets` (
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `token` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tb_dosen`
--

CREATE TABLE IF NOT EXISTS `tb_dosen` (
  `dosen_id` bigint(20) unsigned NOT NULL,
  `dosen_nidn` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dosen_nama` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `dosen_tgl_lahir` date DEFAULT NULL,
  `dosen_jk` enum('Laki-Laki','Perempuan') COLLATE utf8mb4_unicode_ci NOT NULL,
  `dosen_notelp` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `dosen_alamat` text COLLATE utf8mb4_unicode_ci,
  `dosen_email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `dosen_password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `dosen_jabatan` enum('Dosen','Admin') COLLATE utf8mb4_unicode_ci NOT NULL,
  `dosen_status` enum('Aktif','Nonaktif') COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `tb_dosen`
--

INSERT INTO `tb_dosen` (`dosen_id`, `dosen_nidn`, `dosen_nama`, `dosen_tgl_lahir`, `dosen_jk`, `dosen_notelp`, `dosen_alamat`, `dosen_email`, `dosen_password`, `dosen_jabatan`, `dosen_status`, `created_at`, `updated_at`) VALUES
(1, '12346', 'Dian', '2021-08-18', 'Perempuan', '0876324723467', 'Padang', 'dian@gmail.com', '$2y$10$YKaKLkcvCcOHr71vDPJWN.sSZX/.KVcUKVsGzRsO5hfj5Setr0wbe', 'Dosen', 'Aktif', NULL, '2021-08-17 22:28:14'),
(2, '12345', 'Shally', '2021-08-18', 'Perempuan', '0854675834', 'Padang', 'shally@gmail.com', '$2y$10$0A.Tu8riKJGWKvuszLXTeO7/.hFa.8qIMvSE4ud9Bp7duwnVhlzhy', 'Dosen', 'Aktif', '2021-08-17 21:03:29', '2021-08-17 21:03:29');

-- --------------------------------------------------------

--
-- Table structure for table `tb_kelas`
--

CREATE TABLE IF NOT EXISTS `tb_kelas` (
  `kelas_id` bigint(20) unsigned NOT NULL,
  `kelas_nama` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `tb_kelas`
--

INSERT INTO `tb_kelas` (`kelas_id`, `kelas_nama`, `created_at`, `updated_at`) VALUES
(1, 'SI 1', '2021-08-17 21:14:17', '2021-08-17 21:14:17'),
(2, 'SI 2', '2021-08-17 21:14:27', '2021-08-17 21:14:27'),
(3, 'SI 3', '2021-08-17 21:14:32', '2021-08-17 21:14:32'),
(4, 'SI 4', '2021-08-17 21:14:38', '2021-08-17 21:14:38'),
(5, 'SI 5', '2021-08-17 21:14:43', '2021-08-17 21:14:43');

-- --------------------------------------------------------

--
-- Table structure for table `tb_meeting`
--

CREATE TABLE IF NOT EXISTS `tb_meeting` (
`meeting_id` bigint(20) unsigned NOT NULL,
  `dosen_id` int(11) NOT NULL,
  `meeting_nama` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `meeting_status` enum('Aktif','Nonaktif') COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci AUTO_INCREMENT=4 ;

--
-- Dumping data for table `tb_meeting`
--

INSERT INTO `tb_meeting` (`meeting_id`, `dosen_id`, `meeting_nama`, `meeting_status`, `created_at`, `updated_at`) VALUES
(1, 1, 'Meeting 1', 'Aktif', '2021-08-17 22:24:13', '2021-08-17 22:24:13'),
(2, 2, 'Meeting 1', 'Aktif', '2021-08-18 00:50:39', '2021-08-18 00:50:39'),
(3, 1, 'Meeting 2', 'Nonaktif', '2021-08-18 00:50:53', '2021-08-18 00:50:53');

-- --------------------------------------------------------

--
-- Table structure for table `tb_meeting_kuis`
--

CREATE TABLE IF NOT EXISTS `tb_meeting_kuis` (
`meeting_kuis_id` bigint(20) unsigned NOT NULL,
  `meeting_id` int(11) NOT NULL,
  `meeting_kuis_soal` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `meeting_kuis_pil_a` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `meeting_kuis_pil_b` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `meeting_kuis_pil_c` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `meeting_kuis_pil_d` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `meeting_kuis_pil_e` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `meeting_kuis_jawaban` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci AUTO_INCREMENT=3 ;

--
-- Dumping data for table `tb_meeting_kuis`
--

INSERT INTO `tb_meeting_kuis` (`meeting_kuis_id`, `meeting_id`, `meeting_kuis_soal`, `meeting_kuis_pil_a`, `meeting_kuis_pil_b`, `meeting_kuis_pil_c`, `meeting_kuis_pil_d`, `meeting_kuis_pil_e`, `meeting_kuis_jawaban`, `created_at`, `updated_at`) VALUES
(1, 1, 'asdfg', 'sdf', 'sdfg', 'sdf', 'dfg', 'dsfg', 'sdf', '2021-08-19 01:12:08', '2021-08-19 01:15:39'),
(2, 1, 'dfghjsdfg', 'dx', 'gfv', 'ertg', 'reghb', 'erfgvb', 'erfgvb', '2021-08-25 17:00:00', '2021-08-25 17:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `tb_meeting_materi`
--

CREATE TABLE IF NOT EXISTS `tb_meeting_materi` (
`meeting_materi_id` bigint(20) unsigned NOT NULL,
  `meeting_id` int(11) NOT NULL,
  `meeting_materi_tipe` enum('Listening','Speaking') COLLATE utf8mb4_unicode_ci NOT NULL,
  `meeting_materi_audio1` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `meeting_materi_audio2` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `meeting_materi_teks` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci AUTO_INCREMENT=8 ;

--
-- Dumping data for table `tb_meeting_materi`
--

INSERT INTO `tb_meeting_materi` (`meeting_materi_id`, `meeting_id`, `meeting_materi_tipe`, `meeting_materi_audio1`, `meeting_materi_audio2`, `meeting_materi_teks`, `created_at`, `updated_at`) VALUES
(6, 1, 'Listening', '1629357877-audio1.mp3', NULL, 'b', '2021-08-19 00:24:37', '2021-08-19 00:24:37'),
(7, 1, 'Speaking', '1629357877-audio1.mp3', '1629357877-audio1.mp3', 'aaa', '2021-08-22 17:00:00', '2021-08-22 17:00:00');

-- --------------------------------------------------------

--
-- Table structure for table `tb_meeting_record`
--

CREATE TABLE IF NOT EXISTS `tb_meeting_record` (
`meeting_record_id` bigint(20) unsigned NOT NULL,
  `meeting_materi_id` int(11) NOT NULL,
  `siswa_id` int(11) NOT NULL,
  `meeting_record_tipe` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `meeting_record_audio` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `meeting_record_nilai` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `updated_at` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00'
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci AUTO_INCREMENT=2 ;

--
-- Dumping data for table `tb_meeting_record`
--

INSERT INTO `tb_meeting_record` (`meeting_record_id`, `meeting_materi_id`, `siswa_id`, `meeting_record_tipe`, `meeting_record_audio`, `meeting_record_nilai`, `created_at`, `updated_at`) VALUES
(1, 7, 2, 'A', 'audio_2021-09-01-16.07.21.mp3', NULL, '2021-09-01 09:07:33', '2021-09-01 09:07:33');

-- --------------------------------------------------------

--
-- Table structure for table `tb_meeting_skor`
--

CREATE TABLE IF NOT EXISTS `tb_meeting_skor` (
`meeting_skor_id` int(11) NOT NULL,
  `meeting_materi_id` int(11) NOT NULL,
  `siswa_id` int(11) NOT NULL,
  `skor` int(11) NOT NULL
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Dumping data for table `tb_meeting_skor`
--

INSERT INTO `tb_meeting_skor` (`meeting_skor_id`, `meeting_materi_id`, `siswa_id`, `skor`) VALUES
(1, 6, 2, 100),
(2, 6, 2, 0),
(3, 6, 2, 0),
(4, 6, 2, 100);

-- --------------------------------------------------------

--
-- Table structure for table `tb_siswa`
--

CREATE TABLE IF NOT EXISTS `tb_siswa` (
  `siswa_id` bigint(20) unsigned NOT NULL,
  `siswa_nim` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `siswa_nama` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `siswa_tgl_lahir` date DEFAULT NULL,
  `siswa_jk` enum('Laki-Laki','Perempuan') COLLATE utf8mb4_unicode_ci NOT NULL,
  `siswa_notelp` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `siswa_alamat` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `siswa_password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `siswa_status` enum('Aktif','Nonaktif') COLLATE utf8mb4_unicode_ci NOT NULL,
  `kelas_id` int(11) NOT NULL,
  `dosen_id` int(11) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `tb_siswa`
--

INSERT INTO `tb_siswa` (`siswa_id`, `siswa_nim`, `siswa_nama`, `siswa_tgl_lahir`, `siswa_jk`, `siswa_notelp`, `siswa_alamat`, `siswa_password`, `siswa_status`, `kelas_id`, `dosen_id`, `created_at`, `updated_at`) VALUES
(2, '23242', 'Illum vel laboriosa', '1995-03-02', 'Perempuan', '23', 'Padang', '$2y$10$TpCNsj8pu7h2vqOxXqAkMe0e3aky1.6Nt4uSeZAh5SdSN3jNA/ysS', 'Aktif', 5, 1, '2021-08-19 20:22:52', '2021-08-19 20:26:02'),
(3, '112', 'In facilis molestias', '1984-01-04', 'Perempuan', '12312', 'Padang', '$2y$10$G4kQYcdg51ECVuMylsSgauUFwkU9VQpO.U1sGa.O54S6yCGCignIi', 'Nonaktif', 5, 2, '2021-08-19 20:23:57', '2021-08-19 20:23:57');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` bigint(20) unsigned NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `email_verified_at` timestamp NULL DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `remember_token` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tb_meeting`
--
ALTER TABLE `tb_meeting`
 ADD PRIMARY KEY (`meeting_id`);

--
-- Indexes for table `tb_meeting_kuis`
--
ALTER TABLE `tb_meeting_kuis`
 ADD PRIMARY KEY (`meeting_kuis_id`);

--
-- Indexes for table `tb_meeting_materi`
--
ALTER TABLE `tb_meeting_materi`
 ADD PRIMARY KEY (`meeting_materi_id`);

--
-- Indexes for table `tb_meeting_record`
--
ALTER TABLE `tb_meeting_record`
 ADD PRIMARY KEY (`meeting_record_id`);

--
-- Indexes for table `tb_meeting_skor`
--
ALTER TABLE `tb_meeting_skor`
 ADD PRIMARY KEY (`meeting_skor_id`);

--
-- Indexes for table `tb_siswa`
--
ALTER TABLE `tb_siswa`
 ADD PRIMARY KEY (`siswa_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tb_meeting`
--
ALTER TABLE `tb_meeting`
MODIFY `meeting_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=4;
--
-- AUTO_INCREMENT for table `tb_meeting_kuis`
--
ALTER TABLE `tb_meeting_kuis`
MODIFY `meeting_kuis_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=3;
--
-- AUTO_INCREMENT for table `tb_meeting_materi`
--
ALTER TABLE `tb_meeting_materi`
MODIFY `meeting_materi_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=8;
--
-- AUTO_INCREMENT for table `tb_meeting_record`
--
ALTER TABLE `tb_meeting_record`
MODIFY `meeting_record_id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=2;
--
-- AUTO_INCREMENT for table `tb_meeting_skor`
--
ALTER TABLE `tb_meeting_skor`
MODIFY `meeting_skor_id` int(11) NOT NULL AUTO_INCREMENT,AUTO_INCREMENT=5;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
