package tn.esprit.rh.achat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.entities.Produit;
import tn.esprit.rh.achat.entities.SecteurActivite;
import tn.esprit.rh.achat.repositories.CategorieProduitRepository;
import tn.esprit.rh.achat.repositories.ProduitRepository;
import tn.esprit.rh.achat.repositories.SecteurActiviteRepository;
import tn.esprit.rh.achat.repositories.StockRepository;
import tn.esprit.rh.achat.services.ProduitServiceImpl;
import tn.esprit.rh.achat.services.SecteurActiviteServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ContextConfiguration(classes = {SecteurActiviteServiceImpl.class})
@ExtendWith(SpringExtension.class)
class SecteurTest {
    @MockBean
    private CategorieProduitRepository categorieProduitRepository;

    @MockBean
    private SecteurActiviteRepository secteurActiviteRepository;

    @Autowired
    private SecteurActiviteServiceImpl secteurActiviteService;
    @MockBean
    private StockRepository stockRepository;


    @Test
    void testAddSecteurActivite() {
        // Arrange
        SecteurActivite secteurActiviteToAdd = new SecteurActivite();
        secteurActiviteToAdd.setIdSecteurActivite(1L);
        secteurActiviteToAdd.setCodeSecteurActivite("Test Sector");

        when(secteurActiviteRepository.save(any(SecteurActivite.class))).thenReturn(secteurActiviteToAdd);

        // Act
        SecteurActivite addedSecteurActivite = secteurActiviteService.addSecteurActivite(secteurActiviteToAdd);

        // Assert
        assertEquals(secteurActiviteToAdd.getIdSecteurActivite(), addedSecteurActivite.getIdSecteurActivite());
        assertEquals(secteurActiviteToAdd.getCodeSecteurActivite(), addedSecteurActivite.getCodeSecteurActivite());

        // Verify that the save method was called with the correct parameter
        verify(secteurActiviteRepository).save(any(SecteurActivite.class));
    }
    @Test
    void testRetrieveAllSectuer() {
        ArrayList<SecteurActivite> secteurList = new ArrayList<>();
        when(secteurActiviteRepository.findAll()).thenReturn(secteurList);
        List<SecteurActivite> actualRetrieveAllSectuerResult = secteurActiviteService.retrieveAllSecteurActivite();
        assertSame(secteurList, actualRetrieveAllSectuerResult);
        assertTrue(actualRetrieveAllSectuerResult.isEmpty());
        verify(secteurActiviteRepository).findAll();
    }

    @Test
    void testDeleteSectur() {
        doNothing().when(secteurActiviteRepository).deleteById((Long) any());
        secteurActiviteService.deleteSecteurActivite(123L);
        verify(secteurActiviteRepository).deleteById((Long) any());
    }

    @Test
    void testRetrieveSecteurActivite() {
        // Arrange
        Long secteurId = 1L;
        SecteurActivite expectedSecteurActivite = new SecteurActivite();
        expectedSecteurActivite.setIdSecteurActivite(secteurId);
        expectedSecteurActivite.setCodeSecteurActivite("Test Sector");

        when(secteurActiviteRepository.findById(secteurId)).thenReturn(Optional.of(expectedSecteurActivite));

        // Act
        SecteurActivite retrievedSecteurActivite = secteurActiviteService.retrieveSecteurActivite(secteurId);

        // Assert
        assertNotNull(retrievedSecteurActivite);
        assertEquals(expectedSecteurActivite.getIdSecteurActivite(), retrievedSecteurActivite.getIdSecteurActivite());
        assertEquals(expectedSecteurActivite.getCodeSecteurActivite(), retrievedSecteurActivite.getCodeSecteurActivite());

        // Verify that the findById method was called with the correct parameter
        verify(secteurActiviteRepository).findById(secteurId);
    }
}
