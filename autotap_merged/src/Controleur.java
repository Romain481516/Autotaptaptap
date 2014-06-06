import ihm.PanVide;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.player.jlp;

public class Controleur {

	public static FenetreJeuencours windowGame;
	public static mp3Player musicPlayer;
	public static long startTime;
	public static Color couleur1 = new Color (180,230,180); 
	public static Color couleur2 = new Color (240,250,250); 
	public static FenetrePrincipale fen;
	public static Bibliotheque biblio;
	public static int indListpart;
	public static String level;

	public static void main (String args[])
	{	
		File sauvegardetaptap = new File("sauvegardetaptap");
		if (!sauvegardetaptap.exists()) {
			if (sauvegardetaptap.mkdir()) {
				System.out.println("repertoire de sauvegarde cr��!");
			} else {
				System.out.println("�chec de la cr�ation du r�pertoire de sauvegarde!");
			}
		}	else {
			System.out.println("repertoire existe d�j�");
		}
		initBiblio();
		initInterfaceGraph();
	}


	private static void initBiblio() {
		biblio = new Bibliotheque("Sauvegarde\\Bibliotheque.xml");
		
		
	}

	private static void initInterfaceGraph() {  //cr�ation fenetre principale et des panneaux
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


	public static void lancerPartie(int indexListpart,String niveau) throws FileNotFoundException, JavaLayerException { //l'index donne la place de la partition dans la Jlist et dans la biblio
		indListpart=indexListpart;
		level = niveau;
		startMusique(biblio.ListePartition.get(indexListpart).cheminFichierAudio); //lancement du lecteur mp3
		System.out.println("debutjeu avec "+ biblio.ListePartition.get(indexListpart).getNom()  + startTime);
		windowGame = new FenetreJeuencours(couleur1,couleur2,biblio.ListePartition.get(indexListpart).accessNote(niveau));
		windowGame.paused=false;
		windowGame.start(); // lancement du thread de mouvement des notes
	}

	public static void startMusique(String cheminFichierAudio) throws FileNotFoundException{
		try {
			musicPlayer = new mp3Player();
			musicPlayer.firstRun = true;
			try {
				musicPlayer.initPlayer(cheminFichierAudio,1);
				musicPlayer.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (JavaLayerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void endOfGame(){
		String input = JOptionPane.showInputDialog(null,"Nom du joueur:");
		if (input!=null) {
		try {
			biblio.ListePartition.get(indListpart).addScore(new Score(input, level, windowGame.currentScore, biblio.ListePartition.get(indListpart).getNom()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();}
		}
		windowGame.setVisible(false);  //suppression de la fenetre de jeu
		windowGame =null;
	}

	public static List<Partition> getListMus(){
		return biblio.getListePartition();
	}

	public static List<Score> getListScore(){
		return biblio.allScore();
	}

	public static void supprimerPartition(int listIndex) {
		biblio.deletePartition(biblio.getListePartition().get(listIndex));
		fen.panSuppParti.miseaJourListeMusique(getListMus());
	}

	public static void analyse(File chansonATraiter) {
	}

	public static String calculScore(TripletNote tripletNote){
		String message ="pas en rythme...";
		long closestTime = 500;   // on cherche dans les 20 derni�res notes affich�es celle la plus proche de l'appui du joueur
		for(int i = windowGame.cadreJeu.indexLastNote; i < windowGame.cadreJeu.indexLastNote + 20 &&  i < windowGame.ListNote.size(); i++){
			if(windowGame.ListNote.get(i).getTouche() == tripletNote.getTouche()){
				if (Math.abs((tripletNote.debut-windowGame.timeStart)-windowGame.ListNote.get(i).debut)<closestTime){
					closestTime = Math.abs(Math.abs((tripletNote.debut-windowGame.timeStart)-windowGame.ListNote.get(i).debut));
				}
			};
		}
		//System.out.println("test"+String.valueOf(closestTime));
		if (closestTime<30){message = "perfect : 500 points !"; windowGame.currentScore += 500;}
		else if (closestTime<150){message = "good : 200 points !"; windowGame.currentScore += 200;}
		else if (closestTime<300){message = "not bad : 100 points !"; windowGame.currentScore += 100;}
		return message;
	}
}


