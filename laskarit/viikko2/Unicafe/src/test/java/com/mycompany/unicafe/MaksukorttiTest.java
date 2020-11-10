package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    @Test
    public void alkuSaldoOikein(){
        assertTrue(kortti.saldo()==10);

    }
    @Test
    public void saldoLisaantyy(){
        kortti.lataaRahaa(5);
        assertTrue(kortti.saldo()==15);
    }
    @Test
    public void vahennnysToimii(){
        kortti.otaRahaa(5);
         assertTrue(kortti.saldo()==5);
    }
     @Test
     public void eiVoiMennaNeg(){
         kortti.otaRahaa(20);
         assertTrue(kortti.saldo()==10);
     }
     @Test
     public void trueJosRahatRiittaa(){
         assertTrue(kortti.otaRahaa(5));
     }
     @Test
     public void falseJosRahatEiRiita(){
         assertTrue(kortti.otaRahaa(20)==false);

     }
     @Test
     public void toStringToimii(){
         assertEquals(kortti.toString(), "saldo: 0.10");
        // assertTrue(kortti.toString().equals( "saldo: 10") );
     }

   
}
