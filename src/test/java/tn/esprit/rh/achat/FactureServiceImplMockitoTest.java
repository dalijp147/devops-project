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
import tn.esprit.rh.achat.entities.Facture;
import tn.esprit.rh.achat.repositories.FactureRepository;
import tn.esprit.rh.achat.services.FactureServiceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {FactureRepository.class})
@SpringBootTest
@Slf4j

class FactureServiceImplMockitoTest {

    @Mock
    FactureRepository factureRepository;

    @InjectMocks
    FactureServiceImpl factureService;
    Facture f =(Facture.builder()
            .montantRemise(20.00F)
            .dateCreationFacture(new Date()).build());


    @Test
    void TestRetrieveFacture() {
        Mockito.when(factureRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(f));
        Facture facture = factureService.retrieveFacture(2L);
        Mockito.verify(factureRepository).findById(Mockito.anyLong());
        assertNotNull(facture);
        log.info(facture.toString());

    }


    @Test
    void TestRetrieveAllFactures() {
        List<Facture> factureList = new ArrayList<Facture>() {
            private static final long serialVersionUID = 1L;
            {
                add(Facture.builder()
                        .montantRemise(206.00F)
                        .dateCreationFacture(new Date()).build());
                add(Facture.builder()
                        .montantRemise(230.00F)
                        .dateCreationFacture(new Date()).build());
            }
        };
        //ArrayList<Facture> factureList = new ArrayList<>();
        Mockito.when(factureRepository.findAll()).thenReturn(factureList);
        List<Facture> actualRetrieveAllFacturesResult = factureService.retrieveAllFactures();
        assertSame(factureList , actualRetrieveAllFacturesResult );
        Mockito.verify(factureRepository).findAll();
        log.info("Retrieved : "+actualRetrieveAllFacturesResult.toString());
        log.info("Actual : "+factureList.toString());

    }
}