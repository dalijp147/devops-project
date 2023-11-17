package tn.esprit.rh.achat;


import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tn.esprit.rh.achat.entities.Stock;
import tn.esprit.rh.achat.repositories.StockRepository;
import tn.esprit.rh.achat.services.StockServiceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {StockServiceImpl.class})
@ExtendWith(SpringExtension.class)
class StockServiceImplTest {
    @Autowired
    StockServiceImpl stockService;
    @MockBean
    private StockRepository stockRepository;

    @Test
    @Order(1)
    void retrieveAllStocks() {
        ArrayList<Stock> stockList = new ArrayList<>();
        when(stockRepository.findAll()).thenReturn(stockList);
        List<Stock> actualRetrieveAllStocksResult = stockService.retrieveAllStocks();
        assertSame(stockList, actualRetrieveAllStocksResult);
        assertTrue(actualRetrieveAllStocksResult.isEmpty());
        verify(stockRepository).findAll();

    }

    @Test
    void addStock() {
        Stock stock1 = new Stock();
        stock1.setLibelleStock("stock 2");
        stockService.addStock(stock1);

        List<Stock> RetrieveAllStocksResult = stockService.retrieveAllStocks();
        assertEquals(0,RetrieveAllStocksResult.size());





    }

    @Test
    void deleteStock() {
        doNothing().when(stockRepository).deleteById((Long) any());
        stockService.deleteStock(123L);
        verify(stockRepository).deleteById((Long) any());
    }

    @Test
    void retrieveStock() {

        Stock stock = new Stock();
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));
        Stock stock1 = stockService.retrieveStock(1L);
        assertSame(stock, stock1);
    }
    @Test
    void updateStock() {
        Stock stock = new Stock();
        stock.setLibelleStock("Stock 1");
        when(stockRepository.save(stock)).thenReturn(stock);

        Stock updatedStock = stockService.updateStock(stock);
        assertSame(stock, updatedStock);
        verify(stockRepository).save(stock);
    }






}
