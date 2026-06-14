-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 18 Mar 2024 pada 00.26
-- Versi server: 10.4.22-MariaDB
-- Versi PHP: 7.4.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `absensi3`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `list_presensi`
--

CREATE TABLE `list_presensi` (
  `id` int(11) NOT NULL,
  `id_lokasi` int(11) NOT NULL,
  `id_user` int(11) NOT NULL,
  `waktu` varchar(255) NOT NULL,
  `feel` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `list_presensi`
--

INSERT INTO `list_presensi` (`id`, `id_lokasi`, `id_user`, `waktu`, `feel`) VALUES
(1, 1, 4, '2024-03-17 15:12:00', 'semangat'),
(2, 1, 5, '2024-03-17 16:39:44', 'semangat');

-- --------------------------------------------------------

--
-- Struktur dari tabel `lokasi`
--

CREATE TABLE `lokasi` (
  `id` int(11) NOT NULL,
  `namaLokasi` varchar(255) NOT NULL,
  `kodeLokasi` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `lokasi`
--

INSERT INTO `lokasi` (`id`, `namaLokasi`, `kodeLokasi`) VALUES
(1, 'Depan Satpam', 'Lok_613d83e66cc5b0'),
(2, 'Sebelah Perpustakaan', 'Lok_613d8aed60e53c'),
(3, 'Depan Lab 1', 'Lok_613ddee335a410'),
(4, 'Rumah Amir', 'Lok_613ddf6f213154');

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `role` int(1) NOT NULL,
  `username` varchar(255) NOT NULL,
  `nohp` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `alamat` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data untuk tabel `user`
--

INSERT INTO `user` (`id`, `role`, `username`, `nohp`, `email`, `password`, `alamat`) VALUES
(2, 1, 'Admin Amir', '081299189981', 'admin@gmail.com', 'admin', 'Condet'),
(4, 0, 'Amir Ahmad', '081212812', 'amirsmpn150@gmail.com', '123', 'Condet'),
(5, 0, 'Septi', '081218212912', 'septi@gmail.com', '12345', 'Cipayung');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `list_presensi`
--
ALTER TABLE `list_presensi`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `lokasi`
--
ALTER TABLE `lokasi`
  ADD PRIMARY KEY (`id`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `list_presensi`
--
ALTER TABLE `list_presensi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT untuk tabel `lokasi`
--
ALTER TABLE `lokasi`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT untuk tabel `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
