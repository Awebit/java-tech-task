package com.rezdy.lunch.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rezdy.lunch.service.domain.Recipe;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class LunchControllerIT {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testLunchEndpoint() throws Exception  {
        MvcResult result = mockMvc.perform(get("/lunch").param("date", "2020-11-17"))
                .andExpect(status().isOk())
                .andReturn();

        List<Recipe> recipes = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>(){});

        Assertions.assertThat(recipes.size()).isEqualTo(5);
        Assertions.assertThat(recipes.get(0).getTitle()).isEqualTo("Fry-up");
        Assertions.assertThat(recipes.get(1).getTitle()).isEqualTo("Hotdog");
        Assertions.assertThat(recipes.get(2).getTitle()).isEqualTo("Omelette");
        Assertions.assertThat(recipes.get(3).getTitle()).isEqualTo("Salad");
        Assertions.assertThat(recipes.get(4).getTitle()).isEqualTo("Ham and Cheese Toastie");
    }
}
