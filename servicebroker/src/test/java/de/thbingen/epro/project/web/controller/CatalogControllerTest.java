package de.thbingen.epro.project.web.controller;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.Assert.*;

/**
 * Testing Catalog Controller.
 *
 * @author larsatzinger
 * @version 1.0
 * @since 1.0
 */

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Ignore
public class CatalogControllerTest {

    private static final String API_VERSION = "2.14";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CatalogController catalogController;

    @Test
    public void contexLoads() throws Exception {
        assertNotNull(catalogController);
    }

    @Test
    public void getCatalog() throws Exception {
        mockMvc.perform(get("/v2/catalog").header("X-Broker-API-Version", API_VERSION))
                .andDo(print())
                .andExpect(status().isOk());

    }


}