package tn.esprit.rh.achat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import tn.esprit.rh.achat.entities.Reglement;
import tn.esprit.rh.achat.repositories.ReglementRepository;
import tn.esprit.rh.achat.services.ReglementServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {ReglementRepository.class})
@SpringBootTest
@Slf4j
class ReglementServiceImplMockitoTest {


    @Mock
    ReglementRepository reglementRepository;

    @InjectMocks
    ReglementServiceImpl reglementService;
    Reglement r =(Reglement.builder()
            .montantPaye(20.00F)
            .dateReglement(new Date()).build());

    @Test
    void TestRetrieveReglement() {
        Mockito.when(reglementRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(r));
        Reglement reglement = reglementService.retrieveReglement(2L);
        Mockito.verify(reglementRepository).findById(Mockito.anyLong());
        assertNotNull(reglement);
    }


    @Test
    void TestRetrieveAllReglements() {
        List<Reglement> reglementList = new ArrayList<Reglement>() {
            private static final long serialVersionUID = 1L;
            {
                add(Reglement.builder()
                        .montantPaye(206.00F)
                        .dateReglement(new Date()).build());
                add(Reglement.builder()
                        .montantRestant(230.00F)
                        .dateReglement(new Date()).build());
            }
        };
        Mockito.when(reglementRepository.findAll()).thenReturn(reglementList);
        List<Reglement> actualRetrieveAllReglementResult = reglementService.retrieveAllReglements();
        assertSame(reglementList , actualRetrieveAllReglementResult );
        log.info("Retrieved : "+actualRetrieveAllReglementResult.toString());
        log.info("Actual : "+reglementList.toString());
        Mockito.verify(reglementRepository).findAll();

    }
}
