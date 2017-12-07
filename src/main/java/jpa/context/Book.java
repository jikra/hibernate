package jpa.context;

import javax.persistence.*;

/**
 * @author jiricizek <jiri.cizek@cleverlance.com>
 */
@Entity
@Table(schema = "context", name = "book")
public class Book {

    @Id
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "nove_id")
    private Author author;

    public Author getAuthor() {

        return author;
    }

    public void setAuthor(Author author) {

        this.author = author;
    }

    public Long getId() {

        return id;
    }

    public void setId(Long id) {

        this.id = id;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }
}
