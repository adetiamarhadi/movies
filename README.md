
## Application Specification
* Java 17
* SpringBoot 3.2.5
* Docker
    * MySQL 5.7

## How To

* Create MySQL 5.7 instance using Docker
* Connect to MySQL using your IDE with credentials that you already set
* Run below scripts to initialize the database

```
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
```

#### Before running application, please setup application.properties based on credentials that already you set.

```
spring.datasource.url=jdbc:mysql://{host}:{port}/moviedb
spring.datasource.username={username}
spring.datasource.password={password}
```

### Run App

```
  mvn spring-boot:run
```

### Run Test

```
  mvn test
```

## API Reference

### Get all movies

```http
  GET /Movies
```

#### Response

```
  [
    {
        "id": 1,
        "title": "Mencuri Raden Saleh",
        "description": "Menceritakan tentang enam orang anak muda yang merencanakan pencurian lukisan bersejarah Penangkapan Pangeran Diponegoro karya maestro Raden Saleh yang nggak ternilai harganya",
        "rating": 7.3,
        "image": "https://upload.wikimedia.org/wikipedia/id/e/ea/Poster_teatrikal_Mencuri_Raden_Saleh.jpg",
        "createdAt": 1713938241,
        "updatedAt": 1713938241
    },
    {
        "id": 2,
        "title": "13 Bom di Jakarta",
        "description": "Film ini menceritakan tentang ancaman dari sekelompok teroris yang hendak melancarkan serangannya dengan menaruh 13 bom yang tersebar di seluruh Jakarta. Badan Intelijen dan agen rahasia pun diutus untuk melakukan upaya investigasi teror tersebut hingga akhirnya menyeret Oscar dan William yang diduga terlibat.",
        "rating": 7.2,
        "image": "https://upload.wikimedia.org/wikipedia/id/2/27/Poster_13_Bom_di_Jakarta.jpg",
        "createdAt": 1713938241,
        "updatedAt": 1713938241
    },
    {
        "id": 3,
        "title": "The Raid",
        "description": "bercerita tentang serbuan sebuah satuan polisi ke bangunan seorang gembong kriminal, Tama yang berujung menjadi bencana. Rama, seorang polisi yang ikut terjebak di dalam gedung 15 lantai tersebut harus mati-matian mencari jalan keluar bagaimanapun caranya!",
        "rating": 7.6,
        "image": null,
        "createdAt": 1713938241,
        "updatedAt": 1713938241
    }
  ]
```

### Get movie

```http
  GET /Movies/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `number` | **Required**. Movie id |

#### Response

```
  {
    "id": 1,
    "title": "Mencuri Raden Saleh",
    "description": "Menceritakan tentang enam orang anak muda yang merencanakan pencurian lukisan bersejarah Penangkapan Pangeran Diponegoro karya maestro Raden Saleh yang nggak ternilai harganya",
    "rating": 7.3,
    "image": "https://upload.wikimedia.org/wikipedia/id/e/ea/Poster_teatrikal_Mencuri_Raden_Saleh.jpg",
    "createdAt": 1713938241,
    "updatedAt": 1713938241
  }
```

### Insert new movie

```http
  POST /Movies
```

#### Request

```
  {
    "title": "Hari Bersama",
    "description": "Song by SO7",
    "rating": 7.1,
    "image": "https://img.so7.id/NewBrand.jpg"
  }
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `title`      | `string` | **Required**. Movie title |
| `description`      | `string` | **Required**. Movie description |
| `rating`      | `number` | Movie rating |
| `image`      | `string` | Movie image |

#### Response

```
  {
    "id": 6,
    "title": "Hari Bersama",
    "description": "Song by SO7",
    "rating": 7.1,
    "image": "https://img.so7.id/NewBrand.jpg",
    "createdAt": 1713949671,
    "updatedAt": 1713949671
  }
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `number` | Movie id |
| `title`      | `string` | Movie title |
| `description`      | `string` | Movie description |
| `rating`      | `number` | Movie rating |
| `image`      | `string` | Movie image |
| `createdAt`      | `number` | Date time in epoch unix timestamp |
| `updatedAt`      | `number` | Date time in epoch unix timestamp |

### Update movie

```http
  PATCH /Movies/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `number` | **Required**. Movie id |

#### Request

```
  {
    "title": "Update Hari Bersama",
    "description": "Song by SO7",
    "rating": 7.1,
    "image": "https://img.so7.id/NewBrand.jpg"
  }
```

#### Response

```
  {
    "id": 6,
    "title": "Update Hari Bersama",
    "description": "Song by SO7",
    "rating": 7.1,
    "image": "https://img.so7.id/NewBrand.jpg",
    "createdAt": 1713949671,
    "updatedAt": 1713949671
  }
```

### Delete movie

```http
  DELETE /Movies/${id}
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id`      | `number` | **Required**. Movie id |

```http
  204 No Content
```

### Errors

| HTTP Code | Message                       |
| :-------- | :-------------------------------- |
| `400`      | There seems to be a problem with your request. Please check and try again. |
| `404`      | No results found for your request. |
| `500`      | There was a problem processing your request. Please contact us for assistance if this issue persists. |