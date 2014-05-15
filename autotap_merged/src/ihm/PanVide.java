package ihm;
import java.awt.Color;

import javax.swing.*;

public class PanVide extends JPanel {
	public JFrame fen;

	Color couleur1;
	Color couleur2;
	
	public PanVide(Color couleur1,Color couleur2){

		this.couleur1=couleur1;
		this.couleur2=couleur2;
		this.setBackground(couleur1);
	}
}
