package com.epam.bookshop;

import com.epam.bookshop.controller.BookController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application.properties")
@Sql(value = {"/create-book-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-book-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookController controller;

    @Test
    public void find_bookId_OK() throws Exception {
        mockMvc.perform(get("/book/5"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", is(5)))
                .andExpect(jsonPath("$.name", is("It")))
                .andExpect(jsonPath("$.genre", is("HORROR")))
                .andExpect(jsonPath("$.price", is(46.6)));
    }

    @Test
    public void find_allBooks_OK() throws Exception {
        mockMvc.perform(get("/book"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Harry Potter")))
                .andExpect(jsonPath("$[0].genre", is("ADVENTURE")))
                .andExpect(jsonPath("$[0].price", is(25.7)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Sherlock Holmes")))
                .andExpect(jsonPath("$[1].genre", is("DETECTIVE")))
                .andExpect(jsonPath("$[1].price", is(21.9)));
    }

    @Test
    public void find_booksPagination_OK() throws Exception {
        mockMvc.perform(get("/book/?page=1&size=3"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(4)))
                .andExpect(jsonPath("$[0].name", is("The Lord of the Rings")))
                .andExpect(jsonPath("$[1].id", is(5)))
                .andExpect(jsonPath("$[1].name", is("It")));
    }

    @Test
    public void find_booksSort_OK() throws Exception {
        mockMvc.perform(get("/book/?sort=id,desc"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$[0].id", is(5)))
                .andExpect(jsonPath("$[0].name", is("It")))
                .andExpect(jsonPath("$[4].id", is(1)))
                .andExpect(jsonPath("$[4].name", is("Harry Potter")));
    }

    @Test
    public void find_booksFilter_OK() throws Exception {
        mockMvc.perform(get("/book/?search=price>30,genre:FANTASY"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(4)))
                .andExpect(jsonPath("$[0].name", is("The Lord of the Rings")))
                .andExpect(jsonPath("$[0].genre", is("FANTASY")))
                .andExpect(jsonPath("$[0].price", is(51.20)));
    }

    @Test
    public void find_booksPaginationSortFilter_OK() throws Exception {
        mockMvc.perform(get("/book/?start=0&size=3&sort=price,desc&search=price<30"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Harry Potter")))
                .andExpect(jsonPath("$[0].genre", is("ADVENTURE")))
                .andExpect(jsonPath("$[0].price", is(25.70)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("Sherlock Holmes")))
                .andExpect(jsonPath("$[1].genre", is("DETECTIVE")))
                .andExpect(jsonPath("$[1].price", is(21.90)));
    }

    @Sql(value = {"/create-book-after.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    public void find_allBooksNotFound_404() throws Exception {
        mockMvc.perform(get("/book"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("There are no books."));
    }

    @Test
    public void find_bookIdNotFound_404() throws Exception {
        mockMvc.perform(get("/book/6"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("No book found with id 6"));
    }

    @Test
    public void save_book_OK() throws Exception {
        String bookInJson = "{\"name\": \"Robinson Crusoe\",\"genre\": \"ADVENTURE\",\"price\": 54.50}";
        mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(bookInJson))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(6))
                .andExpect(jsonPath("$.name").value("Robinson Crusoe"))
                .andExpect(jsonPath("$.genre").value("ADVENTURE"))
                .andExpect(jsonPath("$.price").value(54.50));
    }

    @Test
    public void save_bookEmptyName_400() throws Exception {
        String bookInJson = "{\"genre\": \"ADVENTURE\",\"price\": 54.50}";
        mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(bookInJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Name cannot be empty."));
    }

    @Test
    public void save_bookEmptyPrice_400() throws Exception {
        String bookInJson = "{\"name\": \"Robinson Crusoe\",\"genre\": \"ADVENTURE\"}";
        mockMvc.perform(post("/book")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(bookInJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Price cannot be empty."));
    }

    @Test
    public void update_book_OK() throws Exception {
        String bookInJson = "{\"name\": \"Harry Potter the philosopher's stone\",\"genre\": \"\",\"price\": 35.50}";
        mockMvc.perform(put("/book/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(bookInJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Harry Potter the philosopher's stone"))
                .andExpect(jsonPath("$.genre").value("OTHER"))
                .andExpect(jsonPath("$.price").value(35.50));
    }

    @Test
    public void update_bookNotFound_404() throws Exception {
        String bookInJson = "{\"name\": \"Harry Potter the philosopher's stone\",\"genre\": \"ADVENTURE\",\"price\": 35.50}";
        mockMvc.perform(put("/book/6")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(bookInJson))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(content().string("No book found with id 6"));
    }

    @Test
    public void update_bookIncorrectPrice_400() throws Exception {
        String bookInJson = "{\"name\": \"Harry Potter the philosopher's stone\",\"genre\": \"ADVENTURE\",\"price\": -5}";
        mockMvc.perform(put("/book/5")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(bookInJson))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Price cannot be less than 0."));
    }

    @Test
    public void delete_book_204() throws Exception {
        mockMvc.perform(delete("/book/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}