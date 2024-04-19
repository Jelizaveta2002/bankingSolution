package integrationTests;

import com.example.bankingSolution.BankingSolutionApplication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BankingSolutionApplication.class)
@AutoConfigureMockMvc
@Transactional
public class TransactionServiceTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetTransactionSuccess() throws Exception {
        mockMvc.perform(get("/api/transaction/get/2")
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(3))
                .andExpect(jsonPath("$[0].accountId").value(2))
                .andExpect(jsonPath("$[0].amount").value(80.00))
                .andExpect(jsonPath("$[0].currency").value("SEK"))
                .andExpect(jsonPath("$[0].direction").value("OUT"))
                .andExpect(jsonPath("$[0].description").value("Gasoline"))
                .andExpect(jsonPath("$[1].id").value(4))
                .andExpect(jsonPath("$[1].accountId").value(2))
                .andExpect(jsonPath("$[1].amount").value(150.00))
                .andExpect(jsonPath("$[1].currency").value("SEK"))
                .andExpect(jsonPath("$[1].direction").value("IN"))
                .andExpect(jsonPath("$[1].description").value("Present"))
                .andExpect(jsonPath("$[2].id").value(5))
                .andExpect(jsonPath("$[2].accountId").value(2))
                .andExpect(jsonPath("$[2].amount").value(100.00))
                .andExpect(jsonPath("$[2].currency").value("EUR"))
                .andExpect(jsonPath("$[2].direction").value("IN"))
                .andExpect(jsonPath("$[2].description").value("Salary"));
    }

    @Test
    public void testGetTransactionInvalidAccount() throws Exception {
        mockMvc.perform(get("/api/transaction/get/33")
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("No account with ID: 33"));
    }

    @Test
    void testCreateTransactionSuccess() throws Exception {
        String jsonRequest = "{\"accountId\": 2, \"amount\": 10, \"currency\": \"EUR\", \"direction\": \"IN\", \"description\": \"Present\"}";

        mockMvc.perform(post("/api/transaction/add")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountId").value(2))
                .andExpect(jsonPath("$.amount").value(10))
                .andExpect(jsonPath("$.currency").value("EUR"))
                .andExpect(jsonPath("$.direction").value("IN"))
                .andExpect(jsonPath("$.description").value("Present"))
                .andExpect(jsonPath("$.balanceAfterTransaction.amount").value(110.0))
                .andExpect(jsonPath("$.balanceAfterTransaction.currency").value("EUR"));
    }

    @Test
    void testCreateTransactionInvalidAccount() throws Exception {
        String jsonRequest = "{\"accountId\": 33, \"amount\": 10, \"currency\": \"EUR\", \"direction\": \"IN\", \"description\": \"100 to balance\"}";

        mockMvc.perform(post("/api/transaction/add")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("No account with ID: 33"));
    }

    @Test
    void testCreateTransactionAccountDoesNotHaveCurrency() throws Exception {
        String jsonRequest = "{\"accountId\": 1, \"amount\": 10, \"currency\": \"SEK\", \"direction\": \"IN\", \"description\": \"Present\"}";

        mockMvc.perform(post("/api/transaction/add")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Account with ID 1 does not have balance with currency SEK"));
    }

    @Test
    void testCreateTransaction() throws Exception {
        String jsonRequest = "{\"accountId\": 1, \"amount\": 15, \"currency\": \"EUR\", \"direction\": \"OUT\", \"description\": \"Coffee\"}";

        mockMvc.perform(post("/api/transaction/add")
                        .contentType("application/json")
                        .content(jsonRequest))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Insufficient funds"));
    }
}
