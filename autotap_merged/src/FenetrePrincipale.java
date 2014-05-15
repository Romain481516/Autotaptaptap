import ihm.Backgroundmenubar;
import ihm.ConfirmQuit;
import ihm.PanVide;
import ihm.exploFichiers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

public class FenetrePrincipale extends JFrame implements ActionListener {

	public Color couleur1;
	public Color couleur2;

	public JFileChooser exploFichiers;
	public JOptionPane CreaPartition;
	public JOptionPane FenMauvaisFichier;
	public ConfirmQuit confirmQuit;

	public PanVide panVide;
	public PanSuppParti panSuppParti;
	public PanChoixMus panChoixMus;
	public PanScores panScores;


	private static Backgroundmenubar menuBar;
	private JMenuItem nouvpart, hiscores,suppart, creerpart;

	public FenetrePrincipale(Color couleur1,Color couleur2){

		super("Autotap Fever");                 //défini le titre
		setSize(new Dimension(500,300));    //la taille      
		setMinimumSize(new Dimension(500,250));

		this.couleur1=couleur1;
		this.couleur2=couleur2;



		//Creaer le menu bar.
		menuBar = new Backgroundmenubar();
		menuBar.setMaximumSize(new Dimension (1000,20));
		menuBar.setMinimumSize(new Dimension (400,20));
		menuBar.setColor(couleur2);

		//menus 
		nouvpart = new JMenuItem("Nouvelle Partie");
		hiscores = new JMenuItem("Meilleurs Scores");
		nouvpart.setBackground(couleur2);
		hiscores.setBackground(couleur2);
		JMenu gerpart = new JMenu("Gérer partitions..");
		menuBar.add(nouvpart);
		menuBar.add(hiscores);
		menuBar.add(gerpart);

		nouvpart.addActionListener(this);
		hiscores.addActionListener(this);


		//  JMenuItems pour gerer partition
		creerpart = new JMenuItem("Créer partition");	
		gerpart.add(creerpart);
		creerpart.addActionListener(this);

		suppart = new JMenuItem("Supprimer partition");
		gerpart.add(suppart);
		suppart.addActionListener(this);
		setJMenuBar(menuBar);

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //opération par défaut a la fermeture

		//sortie app
		WindowListener sortieApp = new  WindowAdapter(){
			public void windowClosing (WindowEvent e){
				ConfirmQuit.popup();
			}
		}; 
		this.addWindowListener(sortieApp);
	}

	public void actionPerformed(ActionEvent even) {
		Object source = even.getSource();
		if (source == this.nouvpart) {
			panChoixMus.miseaJourListeMusique(Controleur.getListMus());
			this.setContentPane(panChoixMus);
			this.setVisible(true);          
		}
		else if (source == this.hiscores) {
			panScores.miseaJourListeScore(Controleur.getListScore());
			this.setContentPane(panScores);
			this.setVisible(true);           
		}
		else if (source == this.suppart) {
			panSuppParti.miseaJourListeMusique(Controleur.getListMus());
			this.setContentPane(panSuppParti);
			this.setVisible(true);
		}
		else if (source == this.creerpart) {
			this.setContentPane(panVide);
			this.setVisible(true);
			exploFichiers explo = new exploFichiers();
			if ( explo.popup()== JFileChooser.APPROVE_OPTION) {
				Controleur.analyse(explo.getSelectedFile());
				System.out.println("analyse en cours");
			}    
		}
	}

}
