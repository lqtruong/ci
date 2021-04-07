package com.pycogroup.training.order.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

public class OrderControllerTest extends MvcControllerTest {

  @Test
  public void getById() throws Exception {
    final MockHttpServletRequestBuilder getByIdRequest = get("/orders/{id}", "6043e3e364df61448783836c")
        .contentType(MediaType.APPLICATION_JSON);
    mvc.perform(
        get("/orders/{id}", "6043e3e364df61448783836c")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());
  }

}
