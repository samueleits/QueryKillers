package com.tbr.lettura.service;

import com.tbr.lettura.model.Libro;
import com.tbr.lettura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LibroService {
     @Autowired
    private LibroRepository libroRepository;
   
    /**
     * metodo che restituisce tutti i libri / 
    * Retrieves a list of all books from the repository.
    *
    * @return a List of Libro objects representing all the books available.
    */
    public List<Libro> getLibri() {
        return libroRepository.findAll();
    }
   
    
   
    /**
     * metodo che restituisce un libro in base all'id /
     * Retrieves a book from the repository by its id.
     *
     * @param id the id of the book to be retrieved
     * @return a Libro object representing the book with the given id
     *         or null if there is no book in the repository with the given id
     */
    public Libro getLibroById(int id) {
        for(Libro l : libroRepository.findAll()) {
            if(l.getId() == id) {
                return l;
            }
        }
        return null;
    }

   
    
    /**
     * metodo che restituisce tutti i libri in base all'autore / 
    * Retrieves a list of books from the repository by the author's name.
    *
    * @param author the author's name to search for
    * @return a List of Libro objects written by the specified author
    */

     public List<Libro> getLibroByAuthor(String author) {
        List<Libro> libriByAuthor = new ArrayList<>();
        for(Libro l : libroRepository.findAll()) {
            if(l.getAuthor().toLowerCase().equals(author.toLowerCase())) {
                libriByAuthor.add(l);
            }
        }
        return libriByAuthor;
    }
    
    
    /**
     * metodo che restituisce tutti i libri in base al titolo / 
     * Retrieves a list of books from the repository by the book's title.
     * 
     * @param title the title of the book to search for
     * @return a List of Libro objects with the given title
     */
    public List<Libro> getLibroByTitle(String title) {
        List<Libro> libriByTitle = new ArrayList<>();
        for(Libro l : libroRepository.findAll()) {
            if(l.getTitle().toLowerCase().equals(title.toLowerCase())) {
                libriByTitle.add(l);
            }
        }
        return libriByTitle;
    }
    /**
     * metodo che restituisce tutti i libri in base al genere / 
     * Retrieves a list of books from the repository by the book's genre.
     * 
     * @param genre the genre of the books to search for
     * @return a List of Libro objects with the given genre
     */
     public List<Libro> getLibroByGenre(String genre) {
        List<Libro> libriByGenre = new ArrayList<>();
        for(Libro l : libroRepository.findAll()) {
            if(l.getGenre().toLowerCase().equals(genre.toLowerCase())) {
                libriByGenre.add(l);
            }
        }
        return libriByGenre;
    }
    
    /**
     * metodo che restituisce tutti i libri in base all'anno di pubblicazione /
     * Retrieves a list of books from the repository by the book's year of publication.
     * 
     * @param year the year of publication of the books to search for
     * @return a List of Libro objects with the given year of publication
     */
     public List<Libro> getLibroByYear(int year) {
        List<Libro> libriByYear = new ArrayList<>();
        for(Libro l : libroRepository.findAll()) {
            if(l.getYear() == year) {
                libriByYear.add(l);
            }
        }
        return libriByYear;
    }
    
    /**
     * metodo che restituisce tutti i libri che sono stati pubblicati tra due anni /
    * Retrieves a list of books from the repository that were published between 
    * two specified years.
    *
    * @param firstYear the starting year of the publication range
    * @param secondYear the ending year of the publication range
    * @return a List of Libro objects representing books published between the given years
    */

      public List<Libro> getLibroByBetweenYear(int firstYear, int secondYear) {
        List<Libro> libriByBetweenYear = new ArrayList<>();
        for(Libro l : libroRepository.findAll()) {
            if(l.getYear() >= firstYear && l.getYear() <= secondYear) {
                libriByBetweenYear.add(l);
            }
        }
        return libriByBetweenYear;
    }
    
    /**
     * metodo che aggiunge un libro /
     * Adds a book to the repository.
     *
     * @param libro the book to be added
     */
    public void addLibro(Libro libro) {
        libroRepository.save(libro);
    }
    /**
     * metodo che cancella un libro /
     * Deletes a book from the repository by its id.
     * 
     * @param id the id of the book to be deleted
     */
     public void deleteLibro(int id) {
        libroRepository.deleteById(id);
    }
}