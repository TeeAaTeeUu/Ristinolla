package ristinolla;

import java.util.Scanner;

public class ristinolla {

    /**
     * ristinollan logiikan olio.
     *
     */
    private logiikka logiikka;
    /**
     * ristinollan leveys.
     *
     */
    private int leveys;
    /**
     * ristinollan korkeus.
     *
     */
    private int korkeus;
    
    /**
     * Scanner lukija on käyttäjän näppäimistön lukua varten.
     *
     */
    private static Scanner lukija = new Scanner(System.in);
    
    /**
     * Vielä toimimaton puskurin tyhjennus metodi. Pitäisi siis estää paskan jääminen puskuriin.
     *
     */
    private void puskurinTyhjennys() {
        String dump;
        while (lukija.hasNext()) {
            dump = lukija.next();
        }
    }

    /**
     * Kyselee käytäjältä innokkaasti ja väsymättä kokonaislukua leveydelle ja korkeudelle pelikentässä.
     * 
     * @param   mita   jos 1, niin risti, jos 0, niin nolla.
     *
     */
    private int alkuKysely(String mita) {
        System.out.println("Kuinka " + mita + " kenttä on?");
        boolean olikoHuono = false;
        int temp = -1;
        do {
            try {
                temp = lukija.nextInt();
//                this.puskurinTyhjennys();
            } catch (Exception e) {
                olikoHuono = true;
                System.out.println("Ei ollut kokonaisnumero, kokeile uudestaan:");
            }
        } while (olikoHuono);
        return temp;
    }

    /**
     * Kyselee käytäjältä innokkaasti ja väsymättä riittävän suurta leveyttä ja korkeutta
     *
     */
    private void kysyKoko() {
        boolean ekaYritys = true;
        do {
            if (ekaYritys == false) {
                System.out.println("Ei ollut riittävän leveä.");
            }
            this.leveys = this.alkuKysely("leveä");
            ekaYritys = false;
        } while (this.leveys < 4);

        ekaYritys = true;
        do {
            if (ekaYritys == false) {
                System.out.println("Ei ollut riittävän korkea.");
            }
            this.korkeus = this.alkuKysely("korkea");
            ekaYritys = false;
        } while (this.korkeus < 4);
    }

    /**
     * Kysyy koon, luo logiikka-olion ja päivittää varmuuden välttämiseksi leveyden ja korkeuden, jos olisikin mennyt jotain väärin.
     *
     */
    public ristinolla() {
        this.kysyKoko();
        this.logiikka = new logiikka(this.leveys, this.korkeus);
        
        this.leveys = this.logiikka.getLeveys();
        this.korkeus = this.logiikka.getKorkeus();
    }
    
    /**
     * Luo logiikka-olion ja päivittää leveyden ja korkeuden varmuuden varmistamiseksi.
     * 
     * @param   leveys   ruudukon leveys
     * 
     * @param   korkeus   ruudukon korkeus
     *
     */
    public ristinolla(int leveys, int korkeus) {
        this.logiikka = new logiikka(leveys, korkeus);
        
        this.leveys = this.logiikka.getLeveys();
        this.korkeus = this.logiikka.getKorkeus();
    }

    /**
     * Kyselee paikkaa ruudukossa ja palauttaa tämän taulukossa.
     * 
     * @param   merkki   pitäisi olla "risti" tai "nolla"
     *
     */
    private int[] paikkaKysely(String merkki) {
        System.out.println("Anna seuraava " + merkki + " muodossa \"rivi\" \"monesko\":");
        boolean olikoHuono = false;
        int[] temp = new int[2];
        do {
            try {
                temp[0] = lukija.nextInt();
                temp[1] = lukija.nextInt();
//                this.puskurinTyhjennys();
            } catch (Exception e) {
                olikoHuono = true;
                System.out.println("Ei ollut kokonaisnumero tai oikeassa muodossa, kokeile uudestaan:");
            }
        } while (olikoHuono);
        return temp;
    }

    /**
     * Kyselee paikkaa ruudukossa tarkistellen, että oli sopiva.
     * 
     * @param   mikaMerkki   1, jos risti, 0, jos nolla.
     */
    public int[] kysyPaikkaa(int mikaMerkki) {
        String merkki;
        int[] paikka = new int[2];
        if (mikaMerkki == 1) {
            merkki = "risti";
        } else {
            if (mikaMerkki == 0) {
                merkki = "nolla";
            } else {
                System.out.println("virhe");
                merkki = "virhe";
            }
        }
        boolean ekaYritys = true;
        do {
            if (ekaYritys == false) {
                System.out.println("Oli huono paikka.");
            }
            paikka = this.paikkaKysely(merkki);
            ekaYritys = false;
        } while (this.logiikka.getKorkeus() < paikka[0] && this.logiikka.getLeveys() < paikka[1] && paikka[0] > 0 && paikka[1] > 0);
        return paikka;
    }

    /**
     * Pyörittää peliä vuorotellen ristillä ja nollalla, lopettaa jos toinen saa viiden suoran ruudukossa.
     * 
     */
    public void pelaa() {
        int[] ristiPaikka = new int[2];
        int[] nollaPaikka = new int[2];
        int laskuri = 0;

        do {
            this.logiikka.tulostaRuudut();

            if (laskuri % 2 == 0) {
                ristiPaikka = this.kysyPaikkaa(1);
                this.logiikka.lisaaRisti(ristiPaikka[0], ristiPaikka[1]);
            } else {
                nollaPaikka = this.kysyPaikkaa(0);
                this.logiikka.lisaaNolla(nollaPaikka[0], nollaPaikka[1]);
            }
            laskuri++;
        } while (this.logiikka.onkoVoittoa() == false);
    }

//    public static void main(String[] args) {
//        ristinolla peli = new ristinolla();
//        peli.pelaa();
//    }
}
