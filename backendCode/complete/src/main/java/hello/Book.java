package hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String author;
    private String title;
    private Boolean bookRead;
    private int publicationYear;
    private String bookType;
    private int rating;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }


    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", bookRead=" + bookRead +
                ", publicationYear=" + publicationYear +
                ", bookType='" + bookType + '\'' +
                ", rating=" + rating +
                '}';
    }

    public Book(String author, String title, Boolean read, int publicationYear, String type, int rating) {
        this.author = author;
        this.title = title;
        this.bookRead = read;
        this.publicationYear = publicationYear;
        this.bookType = type;
        this.rating = rating;
    }

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public Boolean getBookRead() {
        return bookRead;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public String getBookType() {
        return bookType;
    }

    protected Book() {}

    public void setId(Long id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBookRead(Boolean bookRead) {
        this.bookRead = bookRead;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }

    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Book book = (Book) o;

        return id.equals(book.id);

    }
}