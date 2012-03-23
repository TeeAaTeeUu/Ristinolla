package ristinolla;

    /**
     * Hoitaa ristinolla-pelin kaiken logiikan ristien ja nollien tallentamisesta, lisäämisestä ja kyselemisestä voiton tarkistukseen
     *
     */

public class logiikka {

    /**
     * Sisältää annetut ristit ja nollat, tyhjät ovat arvolla -1
     *
     */
    private int[] ruudut;
    /**
     * Sisältää jo läpikäydyt ruudut, jottei moneen kertaan tarvitse käydä läpi.
     *
     */
//    private boolean[] tarkistetutRuudut;
//    /**
//     * vähintään 5
//     *
//     */
    private int leveys;

    /**
     * palauttaa korkeuden
     * 
     * @return korkeus
     *
     */
    public int getKorkeus() {
        return korkeus;
    }

    /**
     * palauttaa leveyden
     * 
     * @return leveys
     *
     */
    public int getLeveys() {
        return leveys;
    }
    /**
     * vähintään 5
     *
     */
    private int korkeus;

    /**
     * Luo olion alustaen ruudukon
     *
     * @param   leveys   jos pienempi kuin 5, niin on 5
     * 
     * @param   korkeus   jos pienempi kuin 5, niin on 5
     * 
     */
    public logiikka(int leveys, int korkeus) {
        if (leveys < 5) {
            leveys = 5;
        }
        if (korkeus < 5) {
            korkeus = 5;
        }
        int koko = leveys * korkeus;
        this.ruudut = new int[koko];
        for (int i = 0; i < this.ruudut.length; i++) {
            this.ruudut[i] = -1;
        }
        this.leveys = leveys;
        this.korkeus = korkeus;
//        this.tarkistetutRuudut = new boolean[koko];
        this.tulostaRuudut();
    }

    /**
     * palauttaa ruudukon ristin, nollan tai tyhjän: 1=risti, 0=nolla, -1=tyhjä
     * 
     * @param   rivi   monennellako rivillä
     * 
     * @param   monesko   monesko vasemmalta laskien
     * 
     * @return 1=risti, 0=nolla, -1=tyhjä
     *
     */
    public int getPaikka(int rivi, int monesko) {
        return this.getPaikka((rivi - 1) * this.leveys + monesko - 1);
    }

    /**
     * palauttaa ruudukon ristin, nollan tai tyhjän: 1=risti, 0=nolla, -1=tyhjä
     * 
     * @param   ruutu   monesko ruutu vasemmalta oikealla, ylhäältä alas
     * 
     * @return 1=risti, 0=nolla, -1=tyhjä
     *
     */
    
    public int getPaikka(int ruutu) {
        return ruudut[ruutu];
    }
    
    /**
     * Lisää rivin ristinolla peliin haluttuun reunaan
     * 
     * @param   mika   0 = ylä rivi, 1 = oikea rivi, 2 = ala rivi, 3 = vasen rivi
     *
     */

    public void lisaaRivi(int mika) {
        if (mika == 0) {
            this.lisaaRiviYla();
        }
        if (mika == 1) {
            this.lisaaRiviOikea();
        }
        if (mika == 2) {
            this.lisaaRiviAla();
        }
        if (mika == 3) {
            this.lisaaRiviVasen();
        }
    }

    /**
     * Lisää rivin ristinolla peliin vasemmalle
     *
     */
    
    public void lisaaRiviVasen() {
        this.leveys++;
        int[] uusiRuudut = new int[this.leveys * this.korkeus];

        int laskuri = 0;
        int laskuriVanha = 0;
        for (int i = 0; i < uusiRuudut.length; i++) {
            if (laskuri > 0) {
                uusiRuudut[i] = this.ruudut[laskuriVanha];
                laskuriVanha++;
            } else {
                uusiRuudut[i] = -1;
            }
            laskuri++;
            if (laskuri == this.leveys) {
                laskuri = 0;
            }
        }
        this.ruudut = uusiRuudut;
    }
    
    /**
     * Lisää rivin ristinolla peliin oikealle
     *
     */

    public void lisaaRiviOikea() {
        this.leveys++;
        int[] uusiRuudut = new int[this.leveys * this.korkeus];

        int laskuri = 0;
        int laskuriVanha = 0;
        for (int i = 0; i < uusiRuudut.length; i++) {
            if (laskuri < this.leveys - 1) {
                uusiRuudut[i] = this.ruudut[laskuriVanha];
                laskuriVanha++;
            } else {
                uusiRuudut[i] = -1;
            }
            laskuri++;
            if (laskuri == this.leveys) {
                laskuri = 0;
            }
        }
        this.ruudut = uusiRuudut;
    }
    
    /**
     * Lisää rivin ristinolla peliin ylös
     *
     */

    public void lisaaRiviYla() {
        this.korkeus++;
        int[] uusiRuudut = new int[this.leveys * this.korkeus];

        int laskuriVanha = 0;
        for (int i = 0; i < uusiRuudut.length; i++) {
            if (i <= this.leveys - 1) {
                uusiRuudut[i] = -1;
            } else {
                uusiRuudut[i] = this.ruudut[laskuriVanha];
                laskuriVanha++;
            }
        }
        this.ruudut = uusiRuudut;
    }
    
    /**
     * Lisää rivin ristinolla peliin alas
     *
     */

    public void lisaaRiviAla() {
        this.korkeus++;
        int[] uusiRuudut = new int[this.leveys * this.korkeus];

        int laskuriVanha = 0;
        for (int i = 0; i < uusiRuudut.length; i++) {
            if (i <= uusiRuudut.length - this.leveys - 1) {
                uusiRuudut[i] = this.ruudut[laskuriVanha];
                laskuriVanha++;
            } else {
                uusiRuudut[i] = -1;
            }
        }
        this.ruudut = uusiRuudut;
    }

    /**
     * Tarkistaa, tarvitaanko uutta riviä kasvattamaan ruudukkoa, ja jos tarvitaan, niin tekee sen ja palauttaa mitä tehtiin.
     * 
     * @return taulukko, jossa 0 = ylä rivi, 1 = vasen rivi, 2 = ala rivi, 3 = vasen rivi
     *
     */
    
    public boolean[] tarviikoLisataRivi() {
        boolean[] mitaTehtiin = new boolean[4];
        
        boolean jatkuuYla = true;
        boolean jatkuuVasen = true;
        boolean jatkuuOikea = true;
        boolean jatkuuAla = true;
        
        for (int i = 0; i < this.ruudut.length - 1; i++) {
            if (jatkuuYla) {
                if (i <= (this.leveys * 2) - 1) {
                    if (this.ruudut[i] == 1 | this.ruudut[i] == 0) {
                        this.lisaaRiviYla();
                        jatkuuYla = false;
                        mitaTehtiin[0] = true;
                    }
                }
            }
            if (jatkuuVasen) {
                if (i % this.leveys < 2) {
                    if (this.ruudut[i] == 1 | this.ruudut[i] == 0) {
                        this.lisaaRiviVasen();
                        jatkuuVasen = false;
                        mitaTehtiin[3] = true;
                    }
                }
            }
            if (jatkuuOikea) {
                if (this.leveys - (i % this.leveys) - 1 < 2) {
                    if (this.ruudut[i] == 1 | this.ruudut[i] == 0) {
                        this.lisaaRiviOikea();
                        jatkuuOikea = false;
                        mitaTehtiin[1] = true;
                    }
                }
            }
            if (jatkuuAla) {
                if (i >= this.ruudut.length - (this.leveys * 2) - 1) {
                    if (this.ruudut[i] == 1 | this.ruudut[i] == 0) {
                        this.lisaaRiviAla();
                        jatkuuAla = false;
                        mitaTehtiin[2] = true;
                    }
                }
            }
        }
        return mitaTehtiin;
    }

    /**
     * lisää ristin jos tyhjä paikka, ja palauttaa tällöin truen
     * 
     * @param   moneskoRivi   monennellako rivillä
     * 
     * @param   moneskoRuutu   monesko vasemmalta laskien
     * 
     * @return onnistuiko lisääminen
     *
     */
    public boolean lisaaRisti(int moneskoRivi, int moneskoRuutu) {
        return this.lisaaRisti((moneskoRivi - 1) * this.leveys + moneskoRuutu - 1); // pakolliset +-1 muutokset
    }
    
    /**
     * lisää ristin jos tyhjä paikka, ja palauttaa tällöin truen
     * 
     * @param   ruutu   monesko ruutu vasemmalta oikealle, ylhäältä alas
     * 
     * @return onnistuiko lisääminen
     *
     */

    public boolean lisaaRisti(int ruutu) {
        int paikka = this.getPaikka(ruutu);
        if (paikka == 0 || paikka == 1) {
            return false;
        }
        ruudut[ruutu] = 1;
        return true;
    }

    /**
     * lisää nollan jos tyhjä paikka, ja palauttaa tällöin truen
     * 
     * @param   moneskoRivi   monennellako rivillä
     * 
     * @param   moneskoRuutu   monesko vasemmalta laskien
     * 
     * @return onnistuiko lisääminen
     *
     */
    public boolean lisaaNolla(int moneskoRivi, int moneskoRuutu) {
        return this.lisaaNolla((moneskoRivi - 1) * this.leveys + moneskoRuutu - 1); // pakolliset +-1 muutokset
    }
    
    /**
     * lisää nollan jos tyhjä paikka, ja palauttaa tällöin truen
     * 
     * @param   ruutu   monesko ruutu vasemmalta oikealle, ylhäältä alas
     * 
     * @return onnistuiko lisääminen
     *
     */

    public boolean lisaaNolla(int ruutu) {
        int paikka = this.getPaikka(ruutu);
        if (paikka == 0 || paikka == 1) {
            return false;
        }
        ruudut[ruutu] = 0;
        return true;
    }

    /**
     * tarkistaa tuliko voittoa, ja palauttaa 1, jos risti, 0, jos nolla, ja -1, jos ei voittoa.
     * 
     * @param   huuto   tulostaa voiton huudon, jos true.
     * 
     * @return 1 = risti voitti, 0 = nolla voitti, -1 = ei voittoa
     *
     */
    public int tarkistaVoitto(boolean huuto) {
//        for (int i = 0; i < this.tarkistetutRuudut.length; i++) {
//            this.tarkistetutRuudut[i] = false;
//        }
        this.tulostaRuudut();
        for (int i = 0; i < ruudut.length; i++) {
            int tulos = -1;
//            if (this.tarkistetutRuudut[i] == false) {
            tulos = this.tarkistaRuutu(i);
            if (tulos == 1) {
                if (huuto) {
                    System.out.println("Risti voitti!");
                }
                return 1;
            } else if (tulos == 0) {
                if (huuto) {
                    System.out.println("Nolla voitti!");
                }
                return 0;
            }
//                System.out.println(i);
//            }
        }
        return -1;
    }

    /**
     * tarkistaa tuliko voittoa, ja palauttaa 1, jos risti, 0, jos nolla, ja -1, jos ei voittoa.
     * Tulostaa voiton huudon.
     * 
     * @return 1 = risti voitti, 0 = nolla voitti, -1 = ei voittoa
     *
     */
    public int tarkistaVoitto() {
        return this.tarkistaVoitto(true);
    }

    /**
     * tarkistaa tuliko voittoa.
     * 
     * @param   huuto   tulostaa voiton huudon, jos true.
     * 
     * @return tuliko voittoa
     *
     */
    public boolean onkoVoittoa(boolean huuto) {
        int temp = this.tarkistaVoitto(huuto);
        if (temp == 1 || temp == 0) {
            return true;
        }
        return false;
    }

    /**
     * tarkistaa tuliko voittoa.
     * Tulostaa voiton huudon.
     * 
     * @return tuliko voittoa
     *
     */
    public boolean onkoVoittoa() {
        return this.onkoVoittoa(true);
    }

    /**
     * Tulostaa ruudukon niin, että tyhjä on "_", risti "X" ja nolla "0".
     *
     */
    public void tulostaRuudut() {
        for (int i = 0; i < ruudut.length; i++) {
            if (this.ruudut[i] == -1) {
                System.out.print(" _ ");
            } else {
                if (this.ruudut[i] == 0) {
                    System.out.print(" 0 ");
                } else if (this.ruudut[i] == 1) {
                    System.out.print(" X ");
                }
            }

            if ((i + 1) % this.leveys == 0) {
                System.out.println(" ");
            }
        }
    }

    /**
     * tarkistaa löytyykö suoraa alkaen kyseisestä ruudusta
     * 
     * @param   ruutu   nollasta alkaen monesko ruutu, vasemmalta ylhäältä eteenpäin laskien.
     * 
     * @return löytyykö tästä ruudusta lähtien viidensuoraa, 1 = ristisuora, 0 = nolla suora, -1 = ei viidensuoraa
     *
     */
    public int tarkistaRuutu(int ruutu) {
        return tarkistaRuutu(ruutu, -1, 0, -1);
    }

    /**
     * tarkistaa löytyykö suoraa alkaen kyseisestä ruudusta
     * 
     * @param   ruutu   nollasta alkaen monesko ruutu, vasemmalta ylhäältä eteenpäin laskien.
     * 
     * @param   mikaMerkki   jos risti niin "1", jos nolla niin "0".
     * 
     * @param   monesko   Monesko suoralla, jos vasta kokeilu, niin "0". Pitää kirjaa, jotta osaa pysähtyä viidennessä.
     * 
     * @param   mikaSuunta   tallentaa suunnan, millaista suoraa ollaan laskemassa 1=sivuttain, 2=kaakko, 3=alas, 4=lounas.
     * 
     * @return onko tämä ruutu osana suoraa, 1 = ristisuora, 0 = nolla suora, -1 = ei sioraa
     *
     */
    public int tarkistaRuutu(int ruutu, int mikaMerkki, int monesko, int mikaSuunta) {
        if (ruutu < 0) {                                    //tarkistaa, oliko annettu ruutu hyväksyttävä
            return -1;
        } else if (ruutu >= this.leveys * this.korkeus) {
            return -1;
        }

        if (mikaMerkki == -1) {                             //jos ensimmäinen kerta, niin mikä kyseinen ruutu on, risti vai ruutu
            if (ruudut[ruutu] == -1) {
                return -1;                                  // jos ruutu oli tyhjä, joten tarkistus loppuu
            } else if (ruudut[ruutu] == 0) {
                mikaMerkki = 0;
            } else {
                mikaMerkki = 1;
            }

        } else if (mikaMerkki == 1) {                       //jos oli ristisuoraa tarkistetaan, niin onko kyseinene ruutu myös risti, ja oliko jo viides suorassa
            if (ruudut[ruutu] == 0) {
                return -1;
            } else if (ruudut[ruutu] == -1) {
                return -1;
            } else if (ruudut[ruutu] == 1 && monesko == 4) {
                return 1;
            }
        } else {
            if (ruudut[ruutu] == 1) {                       ////jos oli nollasuoraa tarkistetaan, niin onko kyseinene ruutu myös nolla, ja oliko jo viides suorassa
                return -1;
            } else if (ruudut[ruutu] == -1) {
                return -1;
            } else if (ruudut[ruutu] == 0 && monesko == 4) {
                return 0;
            }
        }
        int temp;

        if (mikaSuunta == 1 | mikaSuunta == -1) {                           // pitää huolen siitä, että vain suora kelpaa, nyt sivuttain
            if ((ruutu + 1) % this.leveys != 0 && ruutu - 1 < this.leveys * this.korkeus) { // jottei mennä yli laidan
                temp = tarkistaRuutu(ruutu + 1, mikaMerkki, monesko + 1, 1); // päivittää monesko oli, pitää suunnan
                if (temp == 1 | temp == 0) {
                    return temp;
                }
            }
        }

        if (mikaSuunta == 2 | mikaSuunta == -1) {                           // pitää huolen siitä, että vain suora kelpaa, nyt kaakkoon
            if (ruutu < (korkeus - 1) * this.leveys - 2 && (ruutu + 1) % this.leveys != 0) { // jottei mennä yli laidan
                temp = tarkistaRuutu(ruutu + this.leveys + 1, mikaMerkki, monesko + 1, 2); // päivittää monesko oli, pitää suunnan
                if (temp == 1 | temp == 0) {
                    return temp;
                }
            }
        }

        if (mikaSuunta == 3 | mikaSuunta == -1) {                           // pitää huolen siitä, että vain suora kelpaa, nyt pystysuunnassa
            if (ruutu < (this.korkeus - 1) * this.leveys - 1) { // jottei mennä yli laidan
                temp = tarkistaRuutu(ruutu + this.leveys, mikaMerkki, monesko + 1, 3); // päivittää monesko oli, pitää suunnan
                if (temp == 1 | temp == 0) {
                    return temp;
                }
            }
        }

        if (mikaSuunta == 4 | mikaSuunta == -1) {                           // pitää huolen siitä, että vain suora kelpaa, nyt lounaaseen
            if (ruutu % this.leveys != 0 && ruutu < (this.korkeus - 1) * this.leveys) { // jottei mennä yli laidan
                temp = tarkistaRuutu(ruutu + this.leveys - 1, mikaMerkki, monesko + 1, 4); // päivittää monesko oli, pitää suunnan
                if (temp == 1 | temp == 0) {
                    return temp;
                }
            }
        }

        return -1; //jos tänne asti päästiin, niin suora ei jatku
    }

    public static void main(String[] args) {
        logiikka peli = new logiikka(10, 10);

        System.out.println("testiväli");

//        peli.lisaaRisti(4, 6);
//        peli.lisaaRisti(5, 7);
//        peli.lisaaRisti(6, 8);
//        peli.lisaaRisti(7, 9);
//        peli.lisaaRisti(8, 10);
//
//        peli.lisaaNolla(5, 6);
//        peli.lisaaNolla(6, 6);
//        peli.lisaaNolla(7, 7);
//        peli.lisaaNolla(8, 8);
//        peli.lisaaNolla(9, 9);
//        peli.lisaaNolla(10, 10);
        
        peli.lisaaRisti(5, 4);
        peli.lisaaRisti(5, 5);
        peli.lisaaRisti(5, 6);
        peli.lisaaRisti(5, 7);
        peli.lisaaRisti(5, 8);

        peli.tulostaRuudut();

        System.out.println("testiväli");
        
        peli.tarviikoLisataRivi();
        
        System.out.println("testiväli");
        
        peli.tulostaRuudut();
        
        System.out.println("testiväli");

//        peli.lisaaRiviOikea();
//
//        peli.tulostaRuudut();
//
//        System.out.println("testiväli");
//
//        peli.lisaaRiviVasen();
//
//        peli.tulostaRuudut();
//
//        System.out.println("testiväli");
//
//        peli.lisaaRiviYla();
//
//        peli.tulostaRuudut();
//
//        System.out.println("testiväli");
//
//        peli.lisaaRiviAla();
//
//        peli.tulostaRuudut();
//
//        System.out.println("testiväli");

        System.out.println("tuliko voitto: " + peli.tarkistaVoitto());
    }
}
