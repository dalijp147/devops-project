package tn.esprit.rh.achat;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.entities.Reglement;
import tn.esprit.rh.achat.repositories.FactureRepository;
import tn.esprit.rh.achat.repositories.ReglementRepository;
import tn.esprit.rh.achat.services.ReglementServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;


import static org.mockito.Mockito.*;

import java.util.*;

@ContextConfiguration(classes = {ReglementServiceImpl.class})
@ExtendWith(SpringExtension.class)
public class ReglementTest {


    @MockBean
    private FactureRepository factureRepository;

    @MockBean
    private ReglementRepository reglementRepository;

    @Autowired
    private ReglementServiceImpl reglementService;



    @Test
    void retrieveAllReglements() {
        ArrayList<Reglement> reglementArrayList = new ArrayList<Reglement>();
        when(reglementRepository.findAll()).thenReturn(reglementArrayList);

        List<Reglement> actualretrieveAllReglementsResult = reglementService.retrieveAllReglements();
        assertSame(reglementArrayList, actualretrieveAllReglementsResult);
        assertTrue(actualretrieveAllReglementsResult.isEmpty());
        verify(reglementRepository).findAll();
    }



    @Test
    void ajoutReglement(){
        Reglement reglement = new Reglement();
        reglement.setIdReglement(1L);
        reglement.setMontantPaye(1f);
        reglement.setPayee(false);
        when(reglementRepository.save(any(Reglement.class))).thenReturn(reglement);

        Reglement reglementAjoute = reglementService.addReglement(reglement);

        assertSame(reglement, reglementAjoute);

        verify(reglementRepository, times(1)).save(any(Reglement.class));
    }


    @Test
    void getByIDReglement() {
        Long idReglement = 1L;
        Reglement reg = new Reglement();
        reg.setIdReglement(idReglement);
        reg.setMontantPaye(1f);

        when(reglementRepository.findById(idReglement)).thenReturn(Optional.of(reg));

        Reglement reglement = reglementService.retrieveReglement(idReglement);

        assertSame(reg, reglement);

        verify(reglementRepository, times(1)).findById(idReglement);

    }




}
