package org.lab;

import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseType;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider;
import org.junit.jupiter.api.BeforeEach;
import org.lab.security.service.CustomUserDetailsService;
import org.lab.security.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@ActiveProfiles("integration-test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureEmbeddedDatabase(type = DatabaseType.POSTGRES, provider = DatabaseProvider.ZONKY)
public abstract class ApplicationIT {
    @LocalServerPort
    protected int port;

    @Autowired
    private WebApplicationContext context;
    @Autowired
    protected MockMvc mockMvcWithSecurity;
    protected MockMvc mockMvcWithoutSecurity;
    @Autowired
    protected JwtService jwtService;

    @MockitoBean
    protected CustomUserDetailsService userDetailsService;

    @BeforeEach
    void setUp() {
        mockMvcWithoutSecurity = MockMvcBuilders.webAppContextSetup(context).build();
    }
}
