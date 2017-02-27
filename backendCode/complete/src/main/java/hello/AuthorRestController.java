package hello;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.http.auth.AUTH;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by ashleycastiglione on 10/23/16.
 */
@RestController

public class AuthorRestController {

    @Autowired
    DynamoAuthor dynamoAuthor;

    @GetMapping("/author/{id}")
    public AuthorInfoResponse getAuthor(@PathVariable int id){
        Item author = dynamoAuthor.getAuthor(id);
        ItemCollection items = dynamoAuthor.getItem(author.get("name").toString());
        Set<String> books = new HashSet<>();
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            books.add(iterator.next().getString("title"));
        }
        AuthorInfoResponse authorInfoResponse = new
                AuthorInfoResponse(author.getString("name"), author.getString("gender"), books);
        return authorInfoResponse;
    }

    class AuthorInfoResponse{
        @JsonProperty
        String authorName;
        @JsonProperty
        String sex;
        @JsonProperty
        Set<String> bookTitles;

        public AuthorInfoResponse(String authorName, String sex, Set<String> bookTitles) {
            this.authorName = authorName;
            this.sex = sex;
            this.bookTitles = bookTitles;
        }
    }

    @GetMapping("/authors/{id}")
    public List<AuthorInfoResponse> getAuthorByUserId(@PathVariable int id) {
        ItemCollection items = dynamoAuthor.getItemByUserId(id);
        List<AuthorInfoResponse> authors = new ArrayList<>();
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
           //System.out.println(iterator.next().getString("name"));
            Item item = iterator.next();
            ItemCollection bookItems = dynamoAuthor.getItem(item.get("name").toString());
            Set<String> books = new HashSet<>();
            Iterator<Item> bookIterator = bookItems.iterator();
            while (bookIterator.hasNext()) {
                books.add(bookIterator.next().getString("title"));
            }
            AuthorInfoResponse authorInfoResponse = new
                    AuthorInfoResponse(item.getString("name"), item.getString("gender"), books);
            authors.add(authorInfoResponse);


        }

        return authors;
    }

    @PostMapping("/author")
    public String postAuthor(@RequestParam(value = "id", required = true) int id,
                           @RequestParam(value = "name", required = true) String name,
                           @RequestParam(value = "gender", required = true) String gender,
                           @RequestParam(value = "userId", required = true) Long userId){
        return dynamoAuthor.postAuthor(id, name, gender, userId).toString();
    }


}
