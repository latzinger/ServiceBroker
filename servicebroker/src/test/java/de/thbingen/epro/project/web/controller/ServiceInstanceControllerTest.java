/**
 * TODO add description
 *
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.controller;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Ignore
public class ServiceInstanceControllerTest {
    public static final String instanceId = "xxx-instance-xxx";

    @Autowired
    private MockMvc mockMvc;


    @Ignore
    @Test
    public void fetchServiceInstance() {
    }

    @Test
    public void createServiceInstance() {
    }

    @Ignore
    @Test
    public void deleteServiceInstance() {
    }

    @Ignore
    @Test
    public void updateServiceInstance() {
    }

    @Ignore
    @Test
    public void lastOperation() {
    }
}