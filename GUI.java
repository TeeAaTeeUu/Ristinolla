package ristinolla;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

    /**
     * Hoitaa ristinolla-pelin grafiikkapuolen hallinnan, sekä myös sen, että risti ja nolla lisätään vuorotellen. 
     *
     */

public class GUI extends JFrame {
    /**
     * Ristinolla pelin logiikka, johon viitataan, kun tarvitaan esim lisätä risti tai tarkistaa voitto
     *
     */
    private logiikka logiikka;
    /**
     * ruudut sisältävä pelikenttä, joka sisältää esim kaikki JButton ruudut
     *
     */
    private JPanel pelikentta;
    /**
     * Asettelu olio, joka muuttuu pelin edetessä, jos kentän kokoa pitää kasvattaa
     *
     */
    private GridLayout ruudukko;
    /**
     * sisältää ruutuGUI-oliot listassa, jossa järjestys on sama kuin pelilogiikan taulukossa
     *
     */
    private ruutuGUI[] ruudut;
    /**
     * Yksinkertainen tapa pitää kirjaa siitä, kumpi lisätään seuraavaksi, risti vai nolla
     *
     */
    private int vuorolaskuri = 0;
    /**
     * Voiton tarkistuksessa pitää tiedon tallessa, kumpi voitti jos kumpikaan
     *
     */
    private int tulikoVoittoa;
    /**
     * Kun false, niin peli on päättynyt, eikä uusien ristien tai nollien lisääminen enään onnistu
     *
     */
    private boolean peliKaynnissa;
    /**
     * True vain pelin alussa, jonka jälkeen false, ja ikkunaa siirretään enää tarpeen vaatiessa
     *
     */
    private boolean start = true;
    /**
     * Päävalikko olio, ja sisältää pelivalikon ja infovalikon
     *
     */
    private JMenuBar paavalikko;
    /**
     * sisältää uuden pelin aloittamisen ja ikkunan sulkemisen
     *
     */
    private JMenu pelivalikko;
    /**
     * Sisältää tietoa pelistä linkin ponnahdusikkunaan
     *
     */
    private JMenu infovalikko;
    /**
     * Uuden ristinollapelin aloittaminen pienellä ruudukolla
     *
     */
    private JMenuItem pieni;
    /**
     * Uuden ristinollapelin aloittaminen normaalikokoisella ruudukolla
     *
     */
    private JMenuItem normaali;
    /**
     * Uuden ristinollapelin aloittaminen suurella ruudukolla
     *
     */
    private JMenuItem suuri;
    /**
     * Pelin lopetus
     *
     */
    private JMenuItem lopeta;
    /**
     * Tietoa pelistä ponnashdusikkunan avaaminen
     *
     */
    private JMenuItem tietoaPelista;
    /**
     * Pitää tiedon siitä, mihin reunaan lisättiin tyhjä rivi jos lisättiin, jotta ikkunaa osataan siirtää oikealla tavalla
     *
     */
    private boolean[] tarviikoLisataRivi;
    
    /**
     * Laittaa rattaat pyörimään, ja luo pelin valikot ja kentät kuntoon, jonka jälkeen jäädään odottelemaan käyttäjän liikkeitä
     *
     */

    public GUI() {
        this.logiikka = new logiikka(10, 10);

        this.luoValikot();

        this.valmistelePeli();
    }
    
    /**
     * Luo JPanel olion, ja siihen lisäilee ulkoasuruudukon, johon sitten ruutunapit lisäillään.
     * Luo napeille kuuntelijat, jotka lisäät "tyhjiin" ruutuihin ristin tai nolla, ja tarkistavat tuliko voitto.
     * Sijoittaa ikkunan alussa pois vasemmasta yläkulmasta, ja uusien tyhjien rivien tilanteessa siirtää ikkunaa oikeaan suuntaan niin, että ruudukko pysyy muuten paikallaan.
     *
     */

    public void valmistelePeli() {
        this.pelikentta = new JPanel();

        peliKaynnissa = true;

        Dimension d = new Dimension(24, 24);                                                        //laitetaan ruudulle koko
        Insets rajat = new Insets(0, 0, 0, 0);

        this.ruudukko = new GridLayout(logiikka.getKorkeus(), logiikka.getLeveys());                //tehdään gridlayout ruudukon mukaan, jolloin ruutuja vain helppo lisäillä
        this.pelikentta.setLayout(this.ruudukko);
        this.ruudut = new ruutuGUI[logiikka.getKorkeus() * logiikka.getLeveys()];                   //luodaan ruudukun kokoinen taulukko ruutu-olioita varten

        for (int i = 0; i < (logiikka.getKorkeus() * logiikka.getLeveys()); i++) {
            this.ruudut[i] = new ruutuGUI(i);
            this.pelikentta.add(this.ruudut[i]);
            this.ruudut[i].setMargin(rajat);
            this.ruudut[i].setPreferredSize(d);
            this.ruudut[i].setVisible(true);

            this.ruudut[i].addMouseListener(new MouseAdapter() {                                    //lisätään kuuntelija

                @Override
                public void mouseClicked(MouseEvent klik) {
                    ruutuGUI painettuRuutu = (ruutuGUI) klik.getSource();

                    if (peliKaynnissa) {
                        if (vuorolaskuri % 2 == 0) {
                            if (logiikka.lisaaRisti(painettuRuutu.getRuutu())) {
                                painettuRuutu.setText("X");
                                vuorolaskuri++;
                            }
                        } else {
                            if (logiikka.lisaaNolla(painettuRuutu.getRuutu())) {
                                painettuRuutu.setText("0");
                                vuorolaskuri++;
                            }
                        }
                        painettuRuutu.setBackground(Color.white);
                    }
                    tulikoVoittoa = logiikka.tarkistaVoitto(false);
                    if (tulikoVoittoa == 1 || tulikoVoittoa == 0) {
                        peliKaynnissa = false;
                        if (tulikoVoittoa == 1) {
                            JOptionPane.showMessageDialog(null, "Risti voitti!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Nolla voitti!");
                        }

                    }
                    tarviikoLisataRivi = logiikka.tarviikoLisataRivi();
                    if (tarviikoLisataRivi[0] || tarviikoLisataRivi[1] || tarviikoLisataRivi[2] || tarviikoLisataRivi[3]) {
                        remove(pelikentta);
                        valmistelePeli();
                    }
                }
            });
        }

        paivitaPeli();

        setLayout(new BorderLayout());
        add("Center", pelikentta);
        setTitle("RistiNolla-peli");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        if (start) {                                                            //tehdään joko ikkunan alkusijoius, tai tarvittaessa pieni korjaussiirto ruudukon paikalla pysymiseksi
            asetaIkkunanAlkusijainti();
            start = false;
        } else {
            Point paikka = getLocationOnScreen();
            
            int x = 0;
            int y = 0;
            if (tarviikoLisataRivi[3])
                x = 24;
            if (tarviikoLisataRivi[0])
                y = 24;
            
            setLocation((int) paikka.getX() - x, (int) paikka.getY() - y);
        }
        
        pelikentta.doLayout();
        pack();
        setVisible(true);
    }
    
    /**
     * Luo valikot, ja laittaa näillekin kuuntelijat, jotta uusi peli voidaan aloittaa tai "tietoa pelistä" ponnahdusikkuna voidaan avata.
     *
     */

    public void luoValikot() {
        this.paavalikko = new JMenuBar();
        this.pelivalikko = new JMenu("Peli");
        this.infovalikko = new JMenu("Info");

        this.pieni = new JMenuItem("Uusi peli: pieni (10x10)");
        this.pieni.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                logiikka = new logiikka(10, 10);
                remove(pelikentta);
                valmistelePeli();
            }
        });
        pelivalikko.add(this.pieni);

        this.normaali = new JMenuItem("Uusi peli: normaali (15x15)");
        this.normaali.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                logiikka = new logiikka(15, 15);
                remove(pelikentta);
                valmistelePeli();
            }
        });
        pelivalikko.add(this.normaali);

        this.suuri = new JMenuItem("Uusi peli: suuri (20x20)");
        this.suuri.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                logiikka = new logiikka(20, 20);
                remove(pelikentta);
                valmistelePeli();
            }
        });
        pelivalikko.add(this.suuri);

        pelivalikko.addSeparator();

        this.lopeta = new JMenuItem("Lopeta");
        this.lopeta.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });
        pelivalikko.add(lopeta);

        this.tietoaPelista = new JMenuItem("Tietoa pelistä");
        this.tietoaPelista.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                String pelinTiedot = "Ristinolla peli\n"
                        + "Ohjelmoinut Tatu Tallberg, 2011\n"
                        + "Apuna Alexander Meaneyn Miinaharavapeli.";
                JOptionPane.showMessageDialog(null, pelinTiedot,
                        "Tietoa pelistä", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        infovalikko.add(tietoaPelista);

        paavalikko.add(pelivalikko);
        paavalikko.add(infovalikko);

        setJMenuBar(paavalikko);
    }
    
    /**
     * Kun lisää rivejä lisätään ikkunaan, niin päivittää uudet ruudut vastaamaan pelilogiikan ruudukkoa
     *
     */

    public void paivitaPeli() {
        int ruutu;
        for (int i = 0; i <= this.ruudut.length - 1; i++) {
            ruutu = this.logiikka.getPaikka(i);

            if (ruutu == -1) {
                this.ruudut[i].setText(null);
                this.ruudut[i].setBackground(null);
            }
            if (ruutu == 0) {
                this.ruudut[i].setText("0");
                this.ruudut[i].setBackground(Color.white);
            }
            if (ruutu == 1) {
                this.ruudut[i].setText("X");
                this.ruudut[i].setBackground(Color.white);
            }
        }
        this.pelikentta.updateUI();
    }
    
    /**
     * Asettaa ikkunan alkusijainnin kivasti irti vakiopaikalta vasemmasta yläkulmasta
     *
     */

    public void asetaIkkunanAlkusijainti() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        int x = (dim.width) / 4;
        int y = (dim.height) / 5;

        //System.out.println("leveys: " + x); //debug
        //System.out.println("korkeus: " + y); //debug

        this.setLocation(x, y);
    }
    
    /**
     * Pyöräyttää koko homman pyörimään
     *
     */

    public static void main(String args[]) {
        GUI peli = new GUI();
    }
}
