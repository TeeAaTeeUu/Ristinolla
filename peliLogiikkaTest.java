/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ristinolla;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author TeeAaTeeUu
 */
public class peliLogiikkaTest {
    
    private logiikka peliLogiikka;
    
    public peliLogiikkaTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testLuokoPelin() {
        logiikka instance = new logiikka(-5, -8);
        
        assertTrue(instance.getKorkeus() > 4 && instance.getLeveys() > 4);
    }
    
    @Test
    public void testLisaaNolla() {
        logiikka instance = new logiikka(-5, -8);
        instance.lisaaNolla(2, 4);
        
        assertTrue(instance.getPaikka(2, 4) == 0);
    }
    
    @Test
    public void testLisaaRisti() {
        logiikka instance = new logiikka(-5, -8);
        instance.lisaaRisti(4, 2);
        
        assertTrue(instance.getPaikka(4, 2) == 1);
    }
    
    @Test
    public void testEiOleVoitto() {
        logiikka instance = new logiikka(10, 10);
        instance.lisaaRisti(4, 2);
        instance.lisaaRisti(4, 3);
        instance.lisaaRisti(4, 4);
        instance.lisaaRisti(4, 5);
        instance.lisaaRisti(5, 6);
        instance.lisaaNolla(3, 2);
        instance.lisaaRisti(2, 2);
        
        
        assertTrue(instance.onkoVoittoa() == false);
    }
    
    @Test
    public void testOnVoitto() {
        logiikka instance = new logiikka(10, 10);
        instance.lisaaRisti(4, 2);
        instance.lisaaRisti(4, 3);
        instance.lisaaRisti(4, 4);
        instance.lisaaRisti(4, 5);
        instance.lisaaRisti(4, 6);
        instance.lisaaNolla(3, 2);
        instance.lisaaRisti(2, 2);
        
        
        assertTrue(instance.onkoVoittoa());
    }
    
    @Test
    public void testRistiVoitti() {
        logiikka instance = new logiikka(10, 10);
        instance.lisaaRisti(4, 2);
        instance.lisaaRisti(4, 3);
        instance.lisaaRisti(4, 4);
        instance.lisaaRisti(4, 5);
        instance.lisaaRisti(4, 6);
        instance.lisaaNolla(3, 2);
        instance.lisaaRisti(2, 2);     
        
        assertTrue(instance.tarkistaVoitto() == 1);
    }
    
    @Test
    public void testNollaVoitti() {
        logiikka instance = new logiikka(10, 10);
        instance.lisaaRisti(4, 2);
        instance.lisaaRisti(4, 3);
        instance.lisaaRisti(4, 4);
        instance.lisaaRisti(4, 5);
        instance.lisaaRisti(5, 6);
        instance.lisaaNolla(2, 7);
        instance.lisaaNolla(3, 7);
        instance.lisaaNolla(4, 7);
        instance.lisaaNolla(5, 7);
        instance.lisaaNolla(6, 7); 
        
        assertTrue(instance.tarkistaVoitto() == 0);
    }
    
    @Test
    public void testNollaVoittiKaakkoon() {
        logiikka instance = new logiikka(10, 10);
        instance.lisaaRisti(4, 2);
        instance.lisaaRisti(4, 3);
        instance.lisaaRisti(4, 4);
        instance.lisaaRisti(4, 5);
        instance.lisaaRisti(5, 6);
        instance.lisaaNolla(2, 5);
        instance.lisaaNolla(3, 6);
        instance.lisaaNolla(4, 7);
        instance.lisaaNolla(5, 8);
        instance.lisaaNolla(6, 9); 
        
        assertTrue(instance.tarkistaVoitto() == 0);
    }
    
    @Test
    public void rivinLisausOnnistuiRikkomatta() {
        logiikka instance = new logiikka(10, 10);
        instance.lisaaRisti(4, 2);
        instance.lisaaRisti(4, 3);
        instance.lisaaRisti(4, 4);
        instance.lisaaRisti(4, 5);
        instance.lisaaRisti(5, 6);
        instance.lisaaNolla(2, 5);
        instance.lisaaNolla(3, 6);
        instance.lisaaNolla(4, 7);
        instance.lisaaNolla(6, 9);
        
        boolean[] mitaTehtiin;
        
        mitaTehtiin = instance.tarviikoLisataRivi();
        
        if (mitaTehtiin[0] && mitaTehtiin[1] && !mitaTehtiin[2] && mitaTehtiin[3])
            instance.lisaaNolla(6, 9);
        
        assertTrue(instance.tarkistaVoitto() == 0);
    }

}