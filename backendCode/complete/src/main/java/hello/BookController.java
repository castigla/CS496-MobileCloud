package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Created by ashleycastiglione on 10/7/16.
 */

@Controller
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @RequestMapping("/yourBooks")
    public String yourBooks(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("books", bookRepository.findAll());
        return "yourBooks";
    }

    @RequestMapping("/addBook")
    public String addBook() {

        return "addBook";
    }

//    @GetMapping("/book")
//    public String getBook(Model model) {
//        model.addAttribute("book", new Book());
//        return "book";
//    }
//
//    @PostMapping("/book")
//    public String bookSubmit(@ModelAttribute Book book, Model model) {
//        bookRepository.save(book);
//        model.addAttribute("books", bookRepository.findAll());
//        return "redirect:yourBooks";
//    }

    @RequestMapping("/editBook")
    public String editbook(@RequestParam(value = "id", required = false) Long id, Model model){
        model.addAttribute("book", bookRepository.findOne(id));
        return "editBook";
    }

    @RequestMapping("/saveExistingBook")
    public String bookEdit(@ModelAttribute Book book, Model model) {
        bookRepository.save(book);
        model.addAttribute("books", bookRepository.findAll());
        return "redirect:yourBooks";
    }
}
