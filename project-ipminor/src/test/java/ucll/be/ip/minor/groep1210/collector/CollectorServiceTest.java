package ucll.be.ip.minor.groep1210.collector;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ucll.be.ip.minor.groep1210.CollectorService;
import ucll.be.ip.minor.groep1210.model.Collector;
import ucll.be.ip.minor.groep1210.model.CollectorRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class  CollectorServiceTest {

    @Mock
    CollectorRepository collectorRepository;

    @InjectMocks
    CollectorService collectorService;


    @Test
    public void givenNoCollectors_whenValidCollectorAdded_ThenCollectorIsAddedAndCollectorIsReturned() {

        Collector doja = CollectorBuilder.aCollectorDoja().build();

        when(collectorRepository.save(any())).thenReturn(doja);

        Collector addedCollector = collectorService.addCollector(doja);

        assertThat(doja.getNaam()).isSameAs(addedCollector.getNaam());
    }

/*
    @Test // (expected = ServiceException.class)
    // THIS IS NOT ENGOUGH BECAUSE THEN YOU DON'T KNOW IF RIGHT MESSAGE IS THROWN
    public void givenPatients_whenValidPatientAddedWithAlreadyUsedEmail_ThenPatientIsNotAddedAndErrorIsReturnd() {
        // given
        Patient elke = PatientBuilder.aPatientElke().build();

        when(patientRepository.findByEmail(elke.getEmail())).thenReturn(elke);

        // when
        final Throwable raisedException = catchThrowable(() -> patientService.addPatient(elke));

        // then
        assertThat(raisedException).isInstanceOf(ServiceException.class)
                .hasMessageContaining("email.already.used");
    }*/

}
