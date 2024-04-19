package integrationTests;

import com.example.bankingSolution.BankingSolutionApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = BankingSolutionApplication.class)
@AutoConfigureMockMvc
@Transactional
public class AccountServiceTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAccountSuccess() throws Exception {
        mockMvc.perform(get("/api/account/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerId").value(1))
                .andExpect(jsonPath("$.balances[0].amount").value(10.00))
                .andExpect(jsonPath("$.balances[0].currency").value("EUR"))
                .andExpect(jsonPath("$.balances[1].amount").value(2500.00))
                .andExpect(jsonPath("$.balances[1].currency").value("USD"));
    }

    @Test
    public void testGetAccountFail() throws Exception {
        mockMvc.perform(get("/api/account/get/55"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("No account with ID: 55"));
    }

    @Test
    void testCreateAccountSuccess() throws Exception {
        String jsonRequest = "{\"customerId\":3,\"country\":\"HR\",\"currencies\":[\"EUR\"]}";
        mockMvc.perform(post("/api/account/add")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerId").value(3))
                .andExpect(jsonPath("$.balances[0].amount").value(0.0))
                .andExpect(jsonPath("$.balances[0].currency").value("EUR"));
    }

    @Test
    void testCreateAccountCustomerDoesNotExist() throws Exception {
        String jsonRequest = "{\"customerId\":15,\"country\":\"IT\",\"currencies\":[\"EUR\"]}";
        mockMvc.perform(post("/api/account/add")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("No customer with ID: 15"));
    }

    @Test
    void testCreateAccountInvalidCountry() throws Exception {
        String jsonRequest = "{\"customerId\":5,\"country\":\"OO\",\"currencies\":[\"EUR\"]}";
        mockMvc.perform(post("/api/account/add")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid country: OO"));
    }

    @Test
    void testCreateAccountInvalidCurrency() throws Exception {
        String jsonRequest = "{\"customerId\":5,\"country\":\"IT\",\"currencies\":[\"CUR\"]}";
        mockMvc.perform(post("/api/account/add")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Invalid currency: CUR"));
    }

    @Test
    void testCreateAccountNoCurrencies() throws Exception {
        String jsonRequest = "{\"customerId\":5,\"country\":\"IT\",\"currencies\":[]}";
        mockMvc.perform(post("/api/account/add")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Account should have at least 1 currency."));
    }

}

