import ihm.PanVide;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.File;
import java.util.*;

public class Controleur {

	public static FenetreJeuencours fenetreJeuencours;
	public static Color couleur1 = new Color (180,230,180); 
	public static Color couleur2 = new Color (240,250,250); 
	public static FenetrePrincipale fen;
	public static Bibliotheque biblio;

	public static void main (String args[])
	{	
		File sauvegardetaptap = new File("sauvegardetaptap");
		if (!sauvegardetaptap.exists()) {
			if (sauvegardetaptap.mkdir()) {
				System.out.println("repertoire de sauvegarde créé!");
			} else {
				System.out.println("échec de la création du répertoire de sauvegarde!");
			}
		}	else {
			System.out.println("repertoire existe déjà");
		}
		initBiblio();
		initInterfaceGraph();
	}


	private static void initBiblio() {
		biblio = new Bibliotheque("Sauvegarde\\ExempleBiblioSchema.txt");	
	}

	private static void initInterfaceGraph() {
		fen = new FenetrePrincipale(couleur1,couleur2);
		PanVide panVide = new PanVide(couleur1,couleur2);
		PanSuppParti panSuppParti = new PanSuppParti(couleur1,couleur2);
		PanChoixMus panChoixMus = new PanChoixMus(couleur1,couleur2);
		PanScores panScores = new PanScores(couleur1, couleur2);
		fen.panVide=panVide; fen.panSuppParti=panSuppParti; fen.panChoixMus=panChoixMus; fen.panScores=panScores;
		panVide.fen=fen;  panChoixMus.fen=fen; panScores.fen=fen; panSuppParti.fen=fen;

		fen.setContentPane(fen.panVide);      
		fen.setVisible(true);
	}

	public static void debutPartie() {
	}

	public static void lancerPartie(int indexListpart,String niveau) { //l'index donne la place de la partition dans la Jlist et dans la biblio
		System.out.println("debutjeu avec "+ biblio.ListePartition.get(indexListpart).getNom() + "niveau"+ niveau);
		FenetreJeuencours wingame = new FenetreJeuencours(couleur1,couleur2,biblio.ListePartition.get(indexListpart).accessNote(niveau),System.currentTimeMillis());
		fenetreJeuencours = wingame;
		wingame.start();
	}

	public static List<Partition> getListMus(){
		return biblio.getListePartition();
	}

	public static List<Score> getListScore(){
		return biblio.allScore();
	}

	public static void supprimerPartition() {
		System.out.println("message erreur si echec suppression");
		fen.panSuppParti.miseaJourListeMusique(getListMus());
	}

	public static void analyse(File chansonATraiter) {
	}

	public static String calculScore(Note KP){
		String message ="pas en rythme";
		long closestTime = 500;   // on cherche dans les 20 dernières notes affichées celle la plus proche de l'appui du joueur
		for(int i = fenetreJeuencours.cadreJeu.indexLastNote; i < fenetreJeuencours.cadreJeu.indexLastNote + 20 &&  i < fenetreJeuencours.ListNote.size(); i++){
			if(fenetreJeuencours.ListNote.get(i).getTouche() == KP.hauteur){
				if (Math.abs((KP.instantNote-fenetreJeuencours.timeStart)-fenetreJeuencours.ListNote.get(i).debut)<closestTime){
					closestTime = Math.abs(Math.abs((KP.instantNote-fenetreJeuencours.timeStart)-fenetreJeuencours.ListNote.get(i).debut));
				}
			};
		}
		System.out.println("test"+String.valueOf(closestTime));
		if (closestTime<15){message = "perfect"; fenetreJeuencours.currentScore += 500;}
		else if (closestTime<100){message = "good"; fenetreJeuencours.currentScore += 200;}
		else if (closestTime<200){message = "not bad"; fenetreJeuencours.currentScore += 100;}
		return message;
	}
}
