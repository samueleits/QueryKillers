package com.tbr.lettura.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tbr.lettura.model.Libro;
import com.tbr.lettura.model.LibroUser;
import com.tbr.lettura.model.Users;
import com.tbr.lettura.repository.LibroRepository;
import com.tbr.lettura.repository.LibroUserRepository;
import com.tbr.lettura.repository.UserRepository;

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
}
   
