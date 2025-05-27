-- defaultdb.books definition

CREATE TABLE "books" (
  "id" int NOT NULL AUTO_INCREMENT,
  "isbn" varchar(20) NOT NULL,
  "title" varchar(255) NOT NULL,
  "author" varchar(100) NOT NULL,
  "genre" varchar(50) DEFAULT NULL,
  "year" int DEFAULT NULL,
  "description" text,
  PRIMARY KEY ("id"),
  UNIQUE KEY "uq_books_isbn" ("isbn")
);


-- defaultdb.challenges definition

CREATE TABLE "challenges" (
  "id" int NOT NULL AUTO_INCREMENT,
  "name" varchar(100) NOT NULL,
  "start_date" date NOT NULL,
  "end_date" date NOT NULL,
  PRIMARY KEY ("id")
);


-- defaultdb.users definition

CREATE TABLE "users" (
  "id" int NOT NULL AUTO_INCREMENT,
  "username" varchar(50) NOT NULL,
  "email" varchar(100) NOT NULL,
  "password_hash" varchar(255) NOT NULL,
  PRIMARY KEY ("id"),
  UNIQUE KEY "uq_users_username" ("username"),
  UNIQUE KEY "uq_users_email" ("email")
);


-- defaultdb.user_books definition

CREATE TABLE "user_books" (
  "id" int NOT NULL AUTO_INCREMENT,
  "user_id" int NOT NULL,
  "book_id" int NOT NULL,
  "is_read" tinyint(1) NOT NULL DEFAULT '0',
  "read_date" date DEFAULT NULL,
  PRIMARY KEY ("id"),
  UNIQUE KEY "uq_user_book" ("user_id","book_id"),
  KEY "fk_user_books_book" ("book_id"),
  CONSTRAINT "fk_user_books_book" FOREIGN KEY ("book_id") REFERENCES "books" ("id") ON DELETE CASCADE,
  CONSTRAINT "fk_user_books_user" FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON DELETE CASCADE
);


-- defaultdb.user_challenges definition

CREATE TABLE "user_challenges" (
  "id" int NOT NULL AUTO_INCREMENT,
  "user_id" int NOT NULL,
  "challenge_id" int NOT NULL,
  PRIMARY KEY ("id"),
  UNIQUE KEY "uq_user_challenge" ("user_id","challenge_id"),
  KEY "fk_user_challenge_challenge" ("challenge_id"),
  CONSTRAINT "fk_user_challenge_challenge" FOREIGN KEY ("challenge_id") REFERENCES "challenges" ("id") ON DELETE CASCADE,
  CONSTRAINT "fk_user_challenge_user" FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON DELETE CASCADE
);