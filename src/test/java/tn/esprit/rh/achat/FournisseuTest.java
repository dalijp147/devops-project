package tn.esprit.rh.achat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import tn.esprit.rh.achat.entities.DetailFournisseur;
import tn.esprit.rh.achat.entities.Fournisseur;
import tn.esprit.rh.achat.repositories.DetailFournisseurRepository;
import tn.esprit.rh.achat.repositories.FournisseurRepository;
import tn.esprit.rh.achat.repositories.ProduitRepository;
import tn.esprit.rh.achat.repositories.SecteurActiviteRepository;
import tn.esprit.rh.achat.services.FournisseurServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = {FournisseurServiceImpl.class})
@ExtendWith(SpringExtension.class)
class FournisseuTest {

    @MockBean
    private DetailFournisseurRepository detailFournisseurRepository;
    @MockBean
    private ProduitRepository produitRepository;

    @MockBean
    private SecteurActiviteRepository secteurActiviteRepository;
    @MockBean
    private FournisseurRepository fournisseurRepository;
    @Autowired
    private FournisseurServiceImpl fournisseurServiceImpl;


    @Test
    void testRetreiveAllFournisseur() {
        ArrayList<Fournisseur> fournisseurList = new ArrayList<Fournisseur>();
        when(fournisseurRepository.findAll()).thenReturn(fournisseurList);

        List<Fournisseur> actualRetreiveAllFournisseurResult = fournisseurServiceImpl.retrieveAllFournisseurs();
        assertSame(fournisseurList, actualRetreiveAllFournisseurResult);
        assertTrue(actualRetreiveAllFournisseurResult.isEmpty());
        verify(fournisseurRepository).findAll();
    }

    @Test
    void testDeleteFournisseur() {
        doNothing().when(fournisseurRepository).deleteById((Long) any());
        fournisseurServiceImpl.deleteFournisseur(12L);
        verify(fournisseurRepository).deleteById((Long) any());
    }

    @Test
    void addFournisseur() {
        Fournisseur newFournisseur = new Fournisseur();
        newFournisseur.setIdFournisseur(1L);
        newFournisseur.setLibelle("New Libelle");

        // Mock the behavior of the FournisseurRepository
        when(fournisseurRepository.save(any(Fournisseur.class))).thenReturn(newFournisseur);

        // Call the service method to add the Fournisseur
        Fournisseur addedFournisseur = fournisseurServiceImpl.addFournisseur(newFournisseur);

        // Verify that the Fournisseur was added correctly
        assertSame(newFournisseur, addedFournisseur);

        // Verify that the repository's save method was called
        verify(fournisseurRepository, times(1)).save(any(Fournisseur.class));
    }

    @Test
    void testGetFournisseurById() {
        // Create a sample Fournisseur entity with a known ID
        Long fournisseurId = 1L;
        Fournisseur expectedFournisseur = new Fournisseur();
        expectedFournisseur.setIdFournisseur(fournisseurId);
        expectedFournisseur.setLibelle("Sample Libelle");

        // Mock the behavior of the FournisseurRepository's findById method
        when(fournisseurRepository.findById(fournisseurId)).thenReturn(Optional.of(expectedFournisseur));

        // Call the service method to get the Fournisseur by ID
        Fournisseur retrievedFournisseur = fournisseurServiceImpl.retrieveFournisseur(fournisseurId);

        // Verify that the Fournisseur was retrieved correctly
        assertSame(expectedFournisseur, retrievedFournisseur);

        // Verify that the repository's findById method was called
        verify(fournisseurRepository, times(1)).findById(fournisseurId);
    }

}