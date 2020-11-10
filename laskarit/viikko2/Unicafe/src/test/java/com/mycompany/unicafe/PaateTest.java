package com.mycompany.unicafe;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Random; 

public class PaateTest{

 Kassapaate paate;
Maksukortti kortti;
Random rand;


    @Before
    public void setUp() {
        paate = new Kassapaate();
        kortti = new Maksukortti(1000);
        rand = new Random();
    }

    @Test
    public void syoKateisellaEdullisestiTasaraha(){
        assertTrue(paate.syoEdullisesti(240)==0);
    }
    @Test
    public void syoKateisellaEdullisestiVaihtorahat(){
        assertTrue(paate.syoEdullisesti(250)==10);
    }
    @Test
    public void syoKateisellaEdullisestiRahatEiRiita(){
        assertTrue(paate.syoEdullisesti(10)==10);
    }
     @Test
    public void syoKateisellaMaukkaastiTasaraha(){
        assertTrue(paate.syoMaukkaasti(400)==0);
    }
    @Test
    public void syoKateisellaMaukkaastiVaihtorahat(){
        assertTrue(paate.syoMaukkaasti(410)==10);
    }
    @Test
    public void syoKateisellaMaukkaastiRahatEiRiita(){
        assertTrue(paate.syoMaukkaasti(10)==10);
    }

    //korttitestit
     @Test
    public void syoKortillaEdullisestiTasaraha(){
        assertTrue(paate.syoEdullisesti(kortti));
    }
   
    @Test
    public void syoKortillaEdullisestiRahatEiRiita(){
        kortti.otaRahaa(999);
        assertTrue(!paate.syoEdullisesti(kortti));
    }
     @Test
    public void syoKortillaMaukkaastiTasaraha(){
        assertTrue(paate.syoMaukkaasti(kortti));
    }
   
    @Test
    public void syoKortillaMaukkaastiRahatEiRiita(){
           kortti.otaRahaa(700);
        assertTrue(paate.syoMaukkaasti(kortti)==false);
    }
    @Test
    public void nollaLounastaSyotyMaukkaat(){
        assertTrue(paate.maukkaitaLounaitaMyyty()==0 );
    }
     @Test
    public void nollaLounastaSyotyEdulliset(){
        assertTrue(paate.edullisiaLounaitaMyyty()==0 );
    }
        @Test
    public void yksiLounaSyotyMaukkaat(){
        paate.syoMaukkaasti(1000);
        assertTrue(paate.maukkaitaLounaitaMyyty()==1 );
    }
     @Test
    public void yksiLounasSyotyEdulliset(){
        paate.syoEdullisesti(1000);
        assertTrue(paate.edullisiaLounaitaMyyty()==1 );
    }
    @Test
    public void paljonEdullisiaSyoty(){
      int lounaita = 10 + rand.nextInt(1000);
      for(int i = 0; i<lounaita; i++){
        paate.syoEdullisesti(1000); 
      }
      assertTrue(paate.edullisiaLounaitaMyyty()== lounaita);

    }
    @Test
        public void paljonMaukkaitaSyoty(){
      int lounaita = 10 + rand.nextInt(1000);
      for(int i = 0; i<lounaita; i++){
        paate.syoMaukkaasti(1000); 
      }
      assertTrue(paate.maukkaitaLounaitaMyyty()== lounaita);

    }
    @Test
    public void korttiLatautuu(){
        paate.lataaRahaaKortille(kortti, 10);
        assertTrue(kortti.saldo()==1010);
    }
      @Test
    public void eiVoiNostaa(){
        paate.lataaRahaaKortille(kortti, -10);
        assertTrue(kortti.saldo()==1000);
    }

       @Test
    public void kortinLatausNostaaKassaa(){
        paate.lataaRahaaKortille(kortti, 100);
        assertTrue(paate.kassassaRahaa()==100100);
    }



   





}