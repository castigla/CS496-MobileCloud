package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ashleycastiglione 11/28/16.
 */

@RestController
public class UserRestController {

    @Autowired
    DynamoUser dynamoUser;

//    @GetMapping("/user/{id}")
//    public String getUser(@PathVariable int id){
//        return dynamoUser.getItem(id).toJSONPretty();}

    @PostMapping("/user")
    public String postUser(@RequestParam(value = "username", required = true) String username,
                           @RequestParam(value = "password", required = true) String password,
                           @RequestParam(value = "location", required = false) String location) {

       // if (dynamoUser.getItem(username, password) == null) {
            return dynamoUser.postItem(username, password, location);
       // } else return "User already exists.";

    }

    @GetMapping("/user")
    public String getUser(@RequestParam(value = "username", required = true) String username,
                          @RequestParam(value = "password", required = true) String password){
        return dynamoUser.getItem(username, password);
    }
}
