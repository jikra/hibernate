package jpa.context;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jiricizek <jiri.cizek@cleverlance.com>
 */
@Entity
@Table(schema = "context", name = "author")
public class Author {

    @Id
    private Long id;

    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Book> books = new ArrayList<>();

    public void addBook(Book book) {
        books.add(book);
        book.setAuthor(this);
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public List<Book> getBooks() {

        return books;
    }

    public void setBooks(List<Book> books) {

        this.books = books;
    }
}
