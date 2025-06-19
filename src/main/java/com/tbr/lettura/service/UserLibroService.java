package com.tbr.lettura.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbr.lettura.model.Libro;
import com.tbr.lettura.model.LibroUser;
import com.tbr.lettura.model.Users;
import com.tbr.lettura.repository.LibroRepository;
import com.tbr.lettura.repository.LibroUserRepository;
import com.tbr.lettura.repository.UserRepository;

import jakarta.transaction.Transactional;

/**
 * Service che gestisce la logica relativa alle associazioni tra utenti e libri.
 * Fornisce metodi per collegare, ottenere e gestire i libri associati agli utenti.
 */
@Service
public class UserLibroService {
    @Autowired
    LibroUserRepository libroUserRepository;
    @Autowired
    LibroRepository libroRepository;
    @Autowired
    UserRepository userRepository;

    
    /**
     * metodo che modifica l'attributo lettod o non letto del libro in base all'id 
     * dell'utente e all'id del libro
     * /
     * Toggles the "read" status of a book for a given user.
     * If the book has not been associated with the user yet, it is created with "read" status set to true.
     * If the book is already associated, its "read" status is toggled.
     * The current date is set as the read date if the book is toggled to "read".
     * If the book is toggled to "unread", the read date is set to null.
     *
     * @param userId the id of the user
     * @param libroId the id of the book
     * @return the new read status of the book
     */
    public boolean toggleLibroLetto(int userId, int libroId) {
        // 1. Recupera le entità Users e Libro reali dal database
        // È fondamentale recuperare le entità complete perché il findByUserAndBook le richiede.
        Users user = userRepository.findById(userId) // Ora userId è int, non serve intValue()
                                  .orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + userId));
        Libro libro = libroRepository.findById(libroId) // Ora libroId è int, non serve intValue()
                                   .orElseThrow(() -> new RuntimeException("Libro non trovato con ID: " + libroId));
        // 2. Cerca la relazione LibroUser usando il metodo efficiente del repository
        Optional<LibroUser> existingLibroUser = libroUserRepository.findByUserAndBook(user, libro);

        LibroUser libroUserToSave;
        boolean newReadStatus;

        if (existingLibroUser.isPresent()) {
            // Se la relazione esiste, la aggiorna
            libroUserToSave = existingLibroUser.get();
            newReadStatus = !libroUserToSave.isRead(); // Usa isRead() come da convenzione
            libroUserToSave.setRead(newReadStatus); // Usa setRead()

            if (newReadStatus) {
                libroUserToSave.setRead_date(LocalDate.now()); // Imposta la data se diventa letto
            } else {
                libroUserToSave.setRead_date(null); // Rimuovi la data se non è più letto
            }
        } else {
            // Se la relazione non esiste, ne crea una nuova e la imposta come "letta"
            libroUserToSave = new LibroUser(user, libro, true, LocalDate.now());
            newReadStatus = true; // Quando si crea per la prima volta, si presume che venga marcato come letto
        }

        // Salva l'entità (nuova o aggiornata)
        libroUserRepository.save(libroUserToSave);

        return newReadStatus; // Restituisce il nuovo stato di lettura
    }

public boolean getStatoLetturaLibro(int userId, int libroId) {
        // 1. Recupera le entità Users e Libro
        // È fondamentale recuperare le entità complete perché il findByUserAndBook le richiede.
        // Se utente o libro non esistono, lanciamo un'eccezione, come fatto nel metodo precedente.
        Users user = userRepository.findById(userId)
                                  .orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + userId));
        Libro libro = libroRepository.findById(libroId)
                                   .orElseThrow(() -> new RuntimeException("Libro non trovato con ID: " + libroId));

        // 2. Cerca la relazione LibroUser utilizzando il metodo di Spring Data JPA.
        // Questo metodo è efficiente e genera una query SQL ottimizzata.
        Optional<LibroUser> existingLibroUser = libroUserRepository.findByUserAndBook(user, libro);

        // 3. Gestisci il risultato dell'Optional:
        //    - Se la relazione LibroUser esiste (Optional.isPresent()), prendi l'oggetto LibroUser
        //      e restituisci il valore del suo campo 'isRead()'.
        //    - Se la relazione LibroUser NON esiste (Optional.isEmpty()), significa che non c'è
        //      alcuna registrazione per quella coppia utente-libro, quindi il libro non è letto.
        //      In questo caso, restituiamo 'false'.
        return existingLibroUser.map(LibroUser::isRead) // Mappa l'Optional<LibroUser> a Optional<Boolean>
                                .orElse(false); // Restituisce false se l'Optional è vuoto
    }

    
    /**
    *  mostra un elenco di tutti i libri letti da un utente/
    * Retrieves a list of books that have been marked as read by a specific user.
    *
    * This method first ensures that the specified user exists. It then finds all
    * book-user relationships where the user has marked the book as read. The
    * method extracts the book objects from these relationships and returns them
    * as a list.
    *
    * @param userId the id of the user whose read books are to be retrieved
    * @return a List of Libro objects that the specified user has read
    * @throws RuntimeException if the user with the given id is not found
    */
    public List<Libro> getLibriLettiDaUtente(int userId) {
        // 1. Recupera l'entità Users.
        // È importante assicurarsi che l'utente esista prima di cercare i suoi libri.
        Users user = userRepository.findById(userId)
                                  .orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + userId));

        // 2. Trova tutte le relazioni LibroUser per questo utente dove isRead è true.
        // Utilizziamo il nuovo metodo del repository.
        List<LibroUser> libriUtenteLetti = libroUserRepository.findByUserAndIsRead(user, true);

        // 3. Estrai gli oggetti Libro da ogni relazione LibroUser e restituisci la lista.
        // Usiamo Java Streams per trasformare List<LibroUser> in List<Libro>.
        return libriUtenteLetti.stream()
                               .map(LibroUser::getBook) // Per ogni LibroUser, ottieni il Libro associato
                               .collect(Collectors.toList()); // Raccogli i Libro in una nuova List
    }

    /**
    * mostra un elenco di tutti i libri non letti da un utente/
    * Retrieves a list of books that have not been marked as read by a specific user.
    *
    * This method first ensures that the specified user exists. It then finds all
    * book-user relationships where the user has not marked the book as read. The
    * method extracts the book objects from these relationships and returns them
    * as a list.
    *
    * @param userId the id of the user whose unread books are to be retrieved
    * @return a List of Libro objects that the specified user has not read
    * @throws RuntimeException if the user with the given id is not found
    */

    public List<Libro> getLibriNonLettiDaUtente(int userId) {
        // 1. Recupera l'entità Users.
        Users user = userRepository.findById(userId)
                                  .orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + userId));

        // 2. Trova tutte le relazioni LibroUser per questo utente dove isRead è FALSE.
        // Riutilizziamo findByUserAndIsRead, semplicemente cambiando il valore del boolean.
        List<LibroUser> libriUtenteNonLetti = libroUserRepository.findByUserAndIsRead(user, false);

        // 3. Estrai gli oggetti Libro da ogni relazione LibroUser e restituisci la lista.
        return libriUtenteNonLetti.stream()
                                  .map(LibroUser::getBook)
                                  .collect(Collectors.toList());
    }

/**
 * mostra un elenco di tutti i libri letti da un utente, filtrati per genere
 * /
 * Retrieves book recommendations for a specific user based on their reading history.
 *
 * This method first verifies that the user exists. It then collects the genres
 * of books that the user has already read and uses these genres to filter out
 * unread books from the entire catalog. Books are recommended if they have not
 * been read by the user and belong to a genre that the user has previously read.
 *
 * If no genres can be extracted from the user's reading history, an empty list
 * is returned, indicating that no genre-based recommendations can be made.
 *
 * @param userId the id of the user for whom recommendations are to be retrieved
 * @return a List of Libro objects that are recommended for the specified user
 * @throws RuntimeException if the user with the given id is not found
 */

 public List<Libro> getRaccomandazioniPerUtente(int userId) {
        // 1. Verifica che l'utente esista.
         userRepository.findById(userId)
                                  .orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + userId));

        // 2. Ottieni i generi dei libri che l'utente ha già letto.
        // Riutilizziamo getLibriLettiDaUtente per ottenere la lista di Libri letti.
        List<Libro> libriLetti = getLibriLettiDaUtente(userId);

        Set<String> generiLetti = libriLetti.stream()
                                            .map(Libro::getGenre) // Ottieni il genere di ogni libro letto
                                            .filter(genre -> genre != null && !genre.trim().isEmpty()) // Filtra generi null/vuoti
                                            .collect(Collectors.toSet()); // Raccogli in un Set per evitare duplicati

        // Se l'utente non ha letto alcun libro o non ci sono generi validi, non possiamo fare raccomandazioni basate sul genere.
        // In questo caso, potremmo restituire tutti i libri non letti o una lista vuota.
        // Per semplicità, qui restituiamo una lista vuota se non ci sono generi.
        if (generiLetti.isEmpty()) {
            return List.of(); // Restituisce una lista immutabile vuota (Java 9+)
        }

        // 3. Ottieni tutti i libri che non sono stati letti dall'utente.
        // Riutilizziamo getLibriNonLettiDaUtente.
        // ATTENZIONE: Questo usa la versione RIVEDUTA di getLibriNonLettiDaUtente,
        // che restituisce libri *nella sua libreria* marcati come false.
        // Per le raccomandazioni, VOGLIAMO TUTTI I LIBRI NON LETTI DAL CATALOGO.
        // Quindi, dobbiamo riutilizzare o reimplementare la logica originale
        // di "libri non presenti tra i letti".

        // APPROCCIO MIGLIORE PER QUESTO METODO:
        // a) Ottieni tutti i libri nel catalogo.
        List<Libro> allLibri = libroRepository.findAll();

        // b) Ottieni gli ID dei libri che l'utente ha letto.
        Set<Integer> libriLettiIds = libriLetti.stream()
                                               .map(Libro::getId)
                                               .collect(Collectors.toSet());

        // c) Filtra i libri del catalogo che non sono stati letti e che corrispondono ai generi.
        List<Libro> raccomandazioni = allLibri.stream()
            .filter(libro -> !libriLettiIds.contains(libro.getId())) // Il libro non è tra quelli già letti
            .filter(libro -> libro.getGenre() != null && generiLetti.contains(libro.getGenre())) // Il genere del libro è tra quelli di interesse
            .collect(Collectors.toList());

        return raccomandazioni;
    }

/**
 * cerca libri in una libreria di un utente
 * /
 * Searches for books in a user's library based on a specified criterion and query.
 *
 * This method first verifies that the specified user exists. It then retrieves all
 * book-user relationships for the user and extracts the book objects. The books are
 * filtered in-memory according to the given criterion and query. Supported criteria
 * are title, author, and genre.
 *
 * @param userId the id of the user whose library is to be searched
 * @param criterio the criterion by which to search (e.g., "titolo", "autore", "genere")
 * @param query the search query string
 * @return a List of Libro objects that match the search criteria
 * @throws RuntimeException if the user with the given id is not found
 */

   public List<Libro> cercaLibriNellaLibreria(int userId, String criterio, String query) {
        // 1. Verifica che l'utente esista.
        Users user = userRepository.findById(userId)
                                  .orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + userId));

        // 2. Recupera TUTTE le relazioni LibroUser per questo utente.
        List<LibroUser> userBooks = libroUserRepository.findByUser(user);

        // 3. Estrai gli oggetti Libro da queste relazioni.
        List<Libro> libriNellaLibreria = userBooks.stream()
                                                .map(LibroUser::getBook) // Estrai l'oggetto Libro
                                                .collect(Collectors.toList());

        // 4. Applica il filtro in memoria.
        String lowerCaseQuery = query.toLowerCase(); // Converti la query in minuscolo una volta sola

        return libriNellaLibreria.stream()
            .filter(libro -> {
                if (libro == null) return false; // Salta libri null se per qualche ragione ci fossero
                switch (criterio.toLowerCase()) {
                    case "titolo":
                        return libro.getTitle() != null && libro.getTitle().toLowerCase().contains(lowerCaseQuery);
                    case "autore":
                        return libro.getAuthor() != null && libro.getAuthor().toLowerCase().contains(lowerCaseQuery);
                    case "genere":
                        return libro.getGenre() != null && libro.getGenre().toLowerCase().contains(lowerCaseQuery);
                    default:
                        return false; // Se il criterio non è valido, non includere il libro
                }
            })
            .collect(Collectors.toList());
    }

    
        /**
         * Aggiunge un libro alla libreria di un utente.
         * Crea una nuova relazione LibroUser tra l'utente e il libro,
         * impostando isRead su false e read_date su null.
         * Se il libro è già nella libreria dell'utente, lancia un'eccezione.
         * /
         * Adds a book to a user's library.
         * Creates a new LibroUser relationship between the user and the book,
         * setting isRead to false and read_date to null.
         * @param userId the id of the user
         * @param libroId the id of the book
         * @return the newly created LibroUser object
         * @throws RuntimeException if the user or book is not found
         * @throws RuntimeException if the book is already in the user's library
         */
     @Transactional // Assicura che l'operazione sia atomica
    public LibroUser aggiungiLibroAllaLibreria(int userId, int libroId) {
        // 1. Recupera Utente e Libro
        Users user = userRepository.findById(userId)
                                  .orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + userId));
        Libro libro = libroRepository.findById(libroId)
                                   .orElseThrow(() -> new RuntimeException("Libro non trovato con ID: " + libroId));

        // 2. Controlla se il libro è già nella libreria dell'utente
        Optional<LibroUser> existingLibroUser = libroUserRepository.findByUserAndBook(user, libro);

        if (existingLibroUser.isPresent()) {
            throw new RuntimeException("Il libro è già nella libreria dell'utente.");
        }

        // 3. Crea una nuova relazione LibroUser (inizialmente non letto, o potresti volerlo impostare come letto)
        LibroUser newLibroUser = new LibroUser();
        newLibroUser.setUser(user);
        newLibroUser.setBook(libro);
        newLibroUser.setRead(false); // Inizialmente non letto
        newLibroUser.setRead_date(null); // Nessuna data di lettura iniziale

        // 4. Salva la nuova relazione nel database
        return libroUserRepository.save(newLibroUser);
    }


/**
 * Rimuove un libro dalla libreria di un utente.
 *
 * Questo metodo elimina la relazione LibroUser tra l'utente e il libro specificato.
 * Prima verifica che l'utente e il libro esistano e che la relazione tra
 * l'utente e il libro sia presente. Se la relazione non esiste, lancia un'eccezione.
 * /
 * Removes a book from a user's library.
 *
 * @param userId l'id dell'utente da cui rimuovere il libro
 * @param libroId l'id del libro da rimuovere dalla libreria dell'utente
 * @throws RuntimeException se l'utente o il libro non viene trovato
 * @throws RuntimeException se il libro non è presente nella libreria dell'utente
 */
    @Transactional
    public void rimuoviLibroDallaLibreria(int userId, int libroId) {
        // 1. Recupera Utente e Libro
        Users user = userRepository.findById(userId)
                                  .orElseThrow(() -> new RuntimeException("Utente non trovato con ID: " + userId));
        Libro libro = libroRepository.findById(libroId)
                                   .orElseThrow(() -> new RuntimeException("Libro non trovato con ID: " + libroId));

        // 2. Trova la relazione LibroUser specifica
        Optional<LibroUser> existingLibroUser = libroUserRepository.findByUserAndBook(user, libro);

        if (existingLibroUser.isEmpty()) {
            throw new RuntimeException("Il libro non è presente nella libreria dell'utente.");
        }

        // 3. Elimina la relazione
        libroUserRepository.delete(existingLibroUser.get());
    }


}
   
