package ucll.be.ip.minor.groep1210.Coin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ucll.be.ip.minor.groep1210.CoinService;
import ucll.be.ip.minor.groep1210.ServiceException;
import ucll.be.ip.minor.groep1210.model.Coin;
import ucll.be.ip.minor.groep1210.model.CoinRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CoinServiceTest {
    @Mock
    CoinRepository coinRepository;

    @InjectMocks
    CoinService coinService;

    @Test
    public void GivenNoCoins_WhenValidCoinIsAdded_ThenCoinIsAddedAndCoinIsReturned() {
        Coin coin = CoinBuilder.aValidCoinTestcoin().build();

        when(coinRepository.save(coin)).thenReturn(coin);

        Coin addedCoin = coinService.addCoin(coin);

        assertThat(coin.getNaam()).isSameAs(addedCoin.getNaam());
    }

    @Test
    public void GivenCoins_WhenValidCoinIsAddedWithAlreadyExistingName_ThenCoinIsNotAddedAndErrorIsThrown() {
        Coin munt = CoinBuilder.aValidCoinTestcoin().build();
        Coin duplicateCoin = CoinBuilder.aValidCoinWithDuplicateName().build();

        // These 2 methods are required to check wether or not a munt can be added or updated
        when(coinRepository.existsCoinsByNaam(munt.getNaam())).thenReturn(true);


        final Throwable raisedException = catchThrowable(() -> coinService.addCoin(duplicateCoin));

        assertThat(raisedException).isInstanceOf(ServiceException.class).hasMessageContaining("coin.exists");
    }

    @Test
    public void GivenMunten_WhenNonExistingMuntIsDeleted_ThenErrorIsThrown() {
        Coin coin = CoinBuilder.aValidCoinTestcoin().build();

        when(coinRepository.findById(coin.getId())).thenReturn(Optional.empty());

        final Throwable raisedException = catchThrowable(() -> coinService.deleteCoinWithId(coin.getId()));

        assertThat(raisedException).isInstanceOf(ServiceException.class).hasMessageContaining("coin.missing.id");
    }
}
