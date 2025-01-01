package ucll.be.ip.minor.groep1210.Coin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ucll.be.ip.minor.groep1210.CoinRestController;
import ucll.be.ip.minor.groep1210.CoinService;
import ucll.be.ip.minor.groep1210.model.Coin;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(CoinRestController.class)
public class CoinRestControllerTest {
    ObjectMapper mapper = new ObjectMapper();

    @MockBean
    CoinService service;

    @Autowired
    MockMvc coinRestController;

    @Autowired
    MessageSource messageSource;

    Coin test, test2, test3, noYear;

    @Before
    public void setUp() {
        test = CoinBuilder.aValidCoinTestcoin().build();
        test2 = CoinBuilder.aValidCoinTestcoin2().build();
        test3 = CoinBuilder.aValidCoinTestcoin3().build();
        noYear = CoinBuilder.anInvalidCointWithNoYear().build();
    }


    @Test
    public void givenCoins_whenGetRequestToGetAllMunten_thenJSONwithAllCoinsIsReturned() throws Exception {
        List<Coin> coins = Arrays.asList(test, test2, test3, noYear);

        given(service.getAllCoins()).willReturn(coins);

        coinRestController.perform(get("/api/coin/overview")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].naam", Is.is(test.getNaam())))
                .andExpect(jsonPath("$[1].naam", Is.is(test2.getNaam())))
                .andExpect(jsonPath("$[2].naam", Is.is(test3.getNaam())))
                .andExpect(jsonPath("$[3].naam", Is.is(noYear.getNaam())));
    }


    @Test
    public void givenNoCoin_whenPostRequestToAddAValidCoinIsExecuted_thenJSONisReturned() throws Exception {
        List<Coin> coin = Arrays.asList(test);

        when(service.addCoin(test)).thenReturn(test);
        when(service.getAllCoins()).thenReturn(coin);

        coinRestController.perform(post("/api/coin/add")
                        .content(mapper.writeValueAsString(test))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].naam", Is.is(test.getNaam())));
    }


    @Test
    public void givenCoins_whenGetRequestToSearchByYear_thenJSONwithAllCoinsWithThatYearIsReturned() throws Exception {
        List<Coin> coins = Arrays.asList(test);

        given(service.getAllCoinsWithGivenYear(test.getJaartal())).willReturn(coins);

        coinRestController.perform(get("/api/coin/search/year/" + test.getJaartal())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].jaartal", Is.is(test.getJaartal())));
    }

    @Test
    public void givenMunten_whenGetRequestToSearchByCountry_thenJSONwithAllMuntenWithCountryAreReturned() throws Exception {
        List<Coin> coins = Arrays.asList(test);

        given(service.getAllCoinsWithLandContains(test.getLand())).willReturn(coins);

        coinRestController.perform(get("/api/coin/search/country?value=Belgium")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].land", Is.is(test.getLand())));
    }
}
