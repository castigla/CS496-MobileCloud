package hello;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController

public class BookRestController {

    @Autowired
    DynamoBook dynamoBook;

    @GetMapping("/book/{id}")
    public String getBook(@PathVariable int id){
        return dynamoBook.getItem(id).toJSONPretty();
    }

    @PostMapping("/book")
    public String postBook(@RequestParam(value = "id", required = true) int id,
                           @RequestParam(value = "title", required = false) String title,
                           @RequestParam(value = "author", required = false) String author,
                           @RequestParam(value = "publicationYear", required = false) Long publicationYear,
                           @RequestParam(value = "bookType", required = false) String bookType,
                           @RequestParam(value = "bookRead", required = false) boolean bookRead,
                           @RequestParam(value = "rating", required = false) Long rating,
                           @RequestParam(value = "username", required = true) String username){
        if (dynamoBook.getItem(id) == null) {
            return dynamoBook.postItem(id, title, author, publicationYear, bookType, bookRead, rating, username).toString();
        }
        else return "Item already exists. Use /putBook to update an existing item";

    }

    @DeleteMapping("/book")
    public String deleteBook(@RequestParam(value = "id", required = true) int id){
        return dynamoBook.deleteItem(id);
    }

    @PutMapping("/book")
    public String putBook(@RequestParam(value = "id", required = true) int id,
                           @RequestParam(value = "title", required = false) String title,
                           @RequestParam(value = "author", required = false) String author,
                           @RequestParam(value = "publicationYear", required = false) Long publicationYear,
                           @RequestParam(value = "bookType", required = false) String bookType,
                           @RequestParam(value = "bookRead", required = false) boolean bookRead,
                           @RequestParam(value = "rating", required = false) Long rating){
        return dynamoBook.putItem(id, title).toString();

    }

    @GetMapping("/books")
    public String getBooks(){
        return dynamoBook.getItems();
        //return "test";
    }

    @GetMapping("/user/books")
    public String getBooksByUsername(@RequestParam(value = "username", required = true) String username){
        return dynamoBook.getItemsByUsername(username);

    }

    //need new post mapping, get the parameters with the info for the book
}
