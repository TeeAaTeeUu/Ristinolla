package ristinolla;

import javax.swing.*;

    /**
     * Laajentaa JButton luokkaa niin, että se sisältää tiedon monettako ruutua nappi kuvastaa laudalla. Tämä siksi, jotta logiikalle voidaan lähettää tieto painetusta napista.
     *
     */

public class ruutuGUI extends JButton {
    
    /**
     * Sisältää tiedon siitä, monettako ruutua olio kuvaa
     *
     */

    private int ruutu;
    
    /**
     * Luo olion lisäten oliolle tiedon siitä, monesko ruutu se on
     * 
     * @param   ruutu   monettako ruutua kyseinen olio vastaa
     *
     */

    public ruutuGUI(int ruutu) {
        this.ruutu = ruutu;
    }
    
    /**
     * palauttaa ruudun, jota olio esittää
     *
     */

    public int getRuutu() {
        return this.ruutu;
    }
}