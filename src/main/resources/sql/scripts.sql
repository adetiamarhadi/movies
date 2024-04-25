create database if not exists moviedb;

use moviedb;

CREATE TABLE `movies` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) not null,
  `description` text not null,
  `rating` decimal(10,2) not null default 0,
  `image` varchar(255),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

INSERT INTO `movies` (`title`, `description`, `rating`, `image`)
values
("Mencuri Raden Saleh", "Menceritakan tentang enam orang anak muda yang merencanakan pencurian lukisan bersejarah Penangkapan Pangeran Diponegoro karya maestro Raden Saleh yang nggak ternilai harganya", 7.3, "https://upload.wikimedia.org/wikipedia/id/e/ea/Poster_teatrikal_Mencuri_Raden_Saleh.jpg"),
("13 Bom di Jakarta", "Film ini menceritakan tentang ancaman dari sekelompok teroris yang hendak melancarkan serangannya dengan menaruh 13 bom yang tersebar di seluruh Jakarta. Badan Intelijen dan agen rahasia pun diutus untuk melakukan upaya investigasi teror tersebut hingga akhirnya menyeret Oscar dan William yang diduga terlibat.", 7.2, "https://upload.wikimedia.org/wikipedia/id/2/27/Poster_13_Bom_di_Jakarta.jpg"),
("The Raid", "bercerita tentang serbuan sebuah satuan polisi ke bangunan seorang gembong kriminal, Tama yang berujung menjadi bencana. Rama, seorang polisi yang ikut terjebak di dalam gedung 15 lantai tersebut harus mati-matian mencari jalan keluar bagaimanapun caranya!", 7.6, null);
