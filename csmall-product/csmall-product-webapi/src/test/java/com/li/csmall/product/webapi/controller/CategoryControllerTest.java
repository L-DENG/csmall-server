package com.li.csmall.product.webapi.controller;

import com.li.csmall.common.web.State;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    @Sql("classpath:truncate.sql")
    public void testAddNewSuccessfully() throws Exception{
        String name = "fruit";
        String parentId = "0";
        String keywords = "keywords of fruit";
        String sort = "20";
        String icon = "need to upload";
        String isDisplay = "1";
        String url = "/categories/add-new";

        mockMvc.perform(
                        MockMvcRequestBuilders.post(url)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("name",name)
                                .param("parentId",parentId)
                                .param("keywords",keywords)
                                .param("sort",sort)
                                .param("icon",icon)
                                .param("isDisplay",isDisplay)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(//
                        MockMvcResultMatchers
                                .jsonPath("state")
                                .value(20000))
                .andDo(MockMvcResultHandlers.print());//print log
    }
    @Test
    @Sql({"classpath:truncate.sql","classpath:insert.sql"})
    public void testAddNewFailCausedByDuplication() throws Exception{
        String name = "category-001";
        String parentId = "0";
        String keywords = "keywords of fruit";
        String sort = "20";
        String icon = "need to upload";
        String isDisplay = "1";
        String url = "/categories/add-new";

        mockMvc.perform(
                        MockMvcRequestBuilders.post(url)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("name",name)
                                .param("parentId",parentId)
                                .param("keywords",keywords)
                                .param("sort",sort)
                                .param("icon",icon)
                                .param("isDisplay",isDisplay)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(//
                        MockMvcResultMatchers
                                .jsonPath("state")
                                .value(State.ERR_CATEGORY_NAME_DUPLICATE.getValue()))
                .andDo(MockMvcResultHandlers.print());//print log
    }


    @Test
    @Sql({"classpath:truncate.sql","classpath:insert.sql"})
    public void testAddNewFailCausedByNullName() throws Exception{
        String name = null;
        String parentId = "0";
        String keywords = "keywords of fruit";
        String sort = "20";
        String icon = "need to upload";
        String isDisplay = "1";
        String url = "/categories/add-new";

        mockMvc.perform(
                        MockMvcRequestBuilders.post(url)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("name",name)
                                .param("parentId",parentId)
                                .param("keywords",keywords)
                                .param("sort",sort)
                                .param("icon",icon)
                                .param("isDisplay",isDisplay)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(//
                        MockMvcResultMatchers
                                .jsonPath("state")
                                .value(State.ERR_BAD_REQUEST.getValue()))
                .andDo(MockMvcResultHandlers.print());//print log
    }

    @Test
    @Sql({"classpath:truncate.sql","classpath:insert_all.sql"})
    public void testListByParentIdSuccessfully() throws Exception{
        String parentId = "5";
        String url = "/categories/list-by-parent";

        mockMvc.perform(
                        MockMvcRequestBuilders.get(url)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("parentId",parentId)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(//
                        MockMvcResultMatchers
                                .jsonPath("state")
                                .value(State.ok.getValue()))
                .andDo(MockMvcResultHandlers.print());//print log
    }

    @Test
    @Sql({"classpath:truncate.sql","classpath:insert_all.sql"})
    public void testGetDetailsByIdSuccessfully() throws Exception{
        String id = "1";
        String url = "/categories/"+id+"/get-details";

        mockMvc.perform(
                        MockMvcRequestBuilders.get(url)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("parentId",id)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(//
                        MockMvcResultMatchers
                                .jsonPath("state")
                                .value(State.ok.getValue()))
                .andDo(MockMvcResultHandlers.print());//print log
    }

    @Test
    @Sql({"classpath:truncate.sql","classpath:insert_all.sql"})
    public void testGetDetailsByIdFailDueToNotFound() throws Exception{
        String id = "-1";
        String url = "/categories/"+id+"/get-details";

        mockMvc.perform(
                        MockMvcRequestBuilders.get(url)
                                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                .param("parentId",id)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(//
                        MockMvcResultMatchers
                                .jsonPath("state")
                                .value(State.ERR_CATEGORY_NOT_FOUND.getValue()))
                .andDo(MockMvcResultHandlers.print());//print log
    }
}
