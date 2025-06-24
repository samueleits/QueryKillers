/*
Introduzione
Il presente documento descrive la struttura del database per il "TBR Project".

Questo database è progettato per gestire informazioni relative a libri, utenti, sfide di lettura e la loro interazione.
L'obiettivo principale è fornire una piattaforma per gli utenti per tenere traccia dei libri letti,
partecipare a sfide di lettura e gestire il proprio progresso.

Il database "TBR Project" è un sistema relazionale ideato per supportare una piattaforma di lettura.
Le sue tabelle principali definiscono le entità fondamentali del sistema e le relazioni tra di esse.

Struttura del Database:
*/

/*
books: Contiene i dettagli sui libri disponibili nella piattaforma.
Include informazioni come ISBN, titolo, autore, genere, anno di pubblicazione e una descrizione.
*/
CREATE TABLE "books" (
  "id" int NOT NULL AUTO_INCREMENT,
  "isbn" varchar(20) NOT NULL,
  "title" varchar(255) NOT NULL,
  "author" varchar(100) NOT NULL,
  "genre" varchar(50) DEFAULT NULL,
  "year" int DEFAULT NULL,
  "description" text,
  "cover" varchar(500) DEFAULT NULL,
  PRIMARY KEY ("id"),
  UNIQUE KEY "uq_books_isbn" ("isbn")
);

/*
challenges: Definisce le sfide di lettura disponibili, specificando il nome della sfida e le date di inizio e fine.
*/
CREATE TABLE "challenges" (
  "id" int NOT NULL AUTO_INCREMENT,
  "name" varchar(100) NOT NULL,
  "start_date" date NOT NULL,
  "end_date" date NOT NULL,
  /*
  "creator_id" int NOT NULL,
  */
  PRIMARY KEY ("id")
  /*
  CONSTRAINT "fk_challenge_creator" FOREIGN KEY ("creator_id") REFERENCES "users" ("id") ON DELETE CASCADE
  */
);

/*
users: Memorizza i dati anagrafici e di autenticazione degli utenti registrati, inclusi username, email e password criptata.
*/
CREATE TABLE "users" (
  "id" int NOT NULL AUTO_INCREMENT,
  "username" varchar(50) NOT NULL,
  "email" varchar(100) NOT NULL,
  "password_hash" varchar(255) NOT NULL,
  PRIMARY KEY ("id"),
  UNIQUE KEY "uq_users_username" ("username"),
  UNIQUE KEY "uq_users_email" ("email")
);

/*
user_books: Rappresenta la relazione molti-a-molti tra utenti e libri,
tracciando quali libri un utente ha letto (o sta leggendo) e la data di completamento.
Questa tabella include chiavi esterne (user_id, book_id) che referenziano rispettivamente le tabelle users e books.
*/
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

/*
user_challenges: Gestisce la partecipazione degli utenti alle sfide di lettura, registrando l'utente,
la sfida a cui partecipa e il punteggio accumulato in quella sfida.
Anche questa tabella include chiavi esterne (user_id, challenge_id) che referenziano users e challenges.
*/
CREATE TABLE "user_challenges" (
  "id" int NOT NULL AUTO_INCREMENT,
  "user_id" int NOT NULL,
  "challenge_id" int NOT NULL,
  "score" int NOT NULL DEFAULT '0',
  PRIMARY KEY ("id"),
  UNIQUE KEY "uq_user_challenge" ("user_id","challenge_id"),
  KEY "fk_user_challenge_challenge" ("challenge_id"),
  CONSTRAINT "fk_user_challenge_challenge" FOREIGN KEY ("challenge_id") REFERENCES "challenges" ("id") ON DELETE CASCADE,
  CONSTRAINT "fk_user_challenge_user" FOREIGN KEY ("user_id") REFERENCES "users" ("id") ON DELETE CASCADE
);

