import ihm.ConfirmQuit;
import ihm.ImageFond;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.imageio.ImageIO;
import javax.swing.*;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.jlp;

public class FenetreJeuencours extends JFrame implements ActionListener,KeyListener,Runnable {
	public List<TripletNote> ListNote; 
	public long timeStart;
	public boolean paused = false;
	public JOptionPane confirmStopPartie,fenFinPartie;
	public ConfirmQuit confirmQuit;
	Color couleur1,couleur2;

	private JButton butpause;
	private JButton butquit;

	public CadreJeu cadreJeu;
	TextField valScore = new TextField("0000");
	int currentScore = 00;
	public TextField valBestScore = new TextField("0000");
	JTextArea rythm = new JTextArea(" . ");

	public mp3Player mpplay;
	private final List<Character> pressed = new ArrayList<Character>();		// list de touches pressées en même temps								

	public FenetreJeuencours(Color couleur1,Color couleur2,List<TripletNote> listNote){
		super("Jeu en cours");            
		setSize(new Dimension(940,540));     
		setMinimumSize(new Dimension(940,540));

		this.ListNote=listNote;
		this.timeStart=timeStart;
		this.couleur1=couleur1;
		this.couleur2=couleur2;

		JPanel paneau = new JPanel();
		BorderLayout lay = new BorderLayout(20,20); 
		paneau.setBackground(couleur1);
		paneau.setLayout(lay);

		//panneau droite
		JPanel panDroite = new JPanel(new GridLayout(5,2,20,20));
		panDroite.setBackground(this.couleur1);
		panDroite.setMaximumSize(new Dimension(100,600));
		paneau.add(panDroite,BorderLayout.EAST);
		//bouton valider&retour
		butpause=new JButton("Pause");
		butpause.setBackground(couleur2);
		panDroite.add(butpause);

		butpause.addActionListener(this);
		butpause.setMnemonic(KeyEvent.VK_P);
		butpause.addKeyListener(this);
		butquit=new JButton("Abandonner Partie");
		butquit.setBackground(couleur2);
		panDroite.add(butquit);
		butquit.addActionListener(this);

		//scores
		Label score = new Label("Score : ", Label.LEFT);
		panDroite.add(score);

		valScore.setEditable(false); 
		panDroite.add(valScore);

		Label meilleurScore = new Label("Meilleur Score : ", Label.LEFT);
		panDroite.add(meilleurScore);
		valBestScore.setEditable(false); 
		panDroite.add(valBestScore);
		
		
		rythm.setEditable(false); 
		panDroite.add(rythm);

		//Ajout  du cadre de jeu 
		cadreJeu = new CadreJeu(this.couleur1,listNote);
		paneau.add(cadreJeu,BorderLayout.CENTER);

		this.setContentPane(paneau);
		this.setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //opération par défaut a la fermeture

		//sortie application
		WindowListener sortieApp = new  WindowAdapter(){
			public void windowClosing (WindowEvent e){
				ConfirmQuit.popup();
				try {
					pause();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JavaLayerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}; 
		this.addWindowListener(sortieApp);
	}


	public void start(){
		Thread t = new Thread(this);
		t.start();
	}

	public void run() {
		long sleep = 30;
		try {
			Thread.sleep(200);   //il faut attendre le lancement du lecteur de musique pour pouvoir synchroniser avec le deplacement des notes
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		setTimeStart (Controleur.musicPlayer.getTimeStart());
		System.out.println(this.timeStart);
		while(true){
			if(!this.paused) {repaint();}
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				System.out.println("Interrupted: " + e.getMessage());
			}         
		}
	}
	
	private void setTimeStart(long timeStart2) {
		this.timeStart = timeStart2;
		cadreJeu.setTimeStart(timeStart2);
	}


	public void repaint(){
		cadreJeu.repaint();
		valScore.setText(String.valueOf(currentScore));
	}

	public void actionPerformed(ActionEvent even) {
		Object source = even.getSource();
		if (source == this.butpause) {
			System.out.println("pause1");
			try {
				this.pause();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JavaLayerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (source == this.butquit) {

		}
	}

	
	//Gestion des appuis sur les touches

	public void keyReleased(KeyEvent even){
		if (pressed.size() == 3){  // 3 keys sont pressées
			System.out.println(pressed.size()+"touche");
			pressed.clear();
			rythm.setText(Controleur.calculScore(new TripletNote(new boolean[]{true,true,true},System.currentTimeMillis(),-1)));
		}
		else if (pressed.size() == 2 ) { // 2 keys sont pressées
			
			switch (even.getKeyCode()) { // on cherche quelle combinaison de touche est pressée
			case KeyEvent.VK_J :
				if (pressed.get(1)== 'k'){
					System.out.println(pressed.size()+"touche");
					rythm.setText(Controleur.calculScore(new TripletNote(new boolean[]{true,true,false},System.currentTimeMillis(),-1)));
				}else if(pressed.get(1)== 'l'){
					System.out.println(pressed.size()+"touche");
					rythm.setText(Controleur.calculScore(new TripletNote(new boolean[]{true,false,true},System.currentTimeMillis(),-1)));
				}
				break;
			case KeyEvent.VK_K:
				if (pressed.get(1)== 'j'){
					System.out.println(pressed.size()+"touche");
					rythm.setText(Controleur.calculScore(new TripletNote(new boolean[]{true,true,false},System.currentTimeMillis(),-1)));
				}else if(pressed.get(1)== 'l'){
					System.out.println(pressed.size()+"touche");
					rythm.setText(Controleur.calculScore(new TripletNote(new boolean[]{false,true,true},System.currentTimeMillis(),-1)));
				}
				break;
			case KeyEvent.VK_L :
				if (pressed.get(1)== 'k'){
					System.out.println(pressed.size()+"touche");
					rythm.setText(Controleur.calculScore(new TripletNote(new boolean[]{false,true,true},System.currentTimeMillis(),-1)));
				}else if(pressed.get(1)== 'j'){
					rythm.setText(Controleur.calculScore(new TripletNote(new boolean[]{true,false,true},System.currentTimeMillis(),-1)));
				}
				break;
			}
			pressed.clear();
		}else if(pressed.size()==1) { 					//une seule touche est pressé et relachée
			System.out.println(pressed.size()+"touche");
			pressed.remove((Character)even.getKeyChar());
			switch (even.getKeyCode()) {
			case KeyEvent.VK_J :
				rythm.setText(Controleur.calculScore(new TripletNote(new boolean[]{true,false,false},System.currentTimeMillis(),-1)));
				break;
			case KeyEvent.VK_K:
				rythm.setText(Controleur.calculScore(new TripletNote(new boolean[]{false,true,false},System.currentTimeMillis(),-1)));
				break;
			case KeyEvent.VK_L :
				rythm.setText(Controleur.calculScore(new TripletNote(new boolean[]{false,false,true},System.currentTimeMillis(),-1)));
				break;
			}
		}
	}


	public void keyPressed(KeyEvent e) {
		int src = e.getKeyCode();
		if (src==KeyEvent.VK_J || src==KeyEvent.VK_K ||src==KeyEvent.VK_L){
			pressed.add(e.getKeyChar());      // a chaque appui sur j,k ou l, cette touche est ajoutée à la liste des touches pressées
		}else if(src ==KeyEvent.VK_P){
			try {
				pause();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (JavaLayerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void keyTyped(KeyEvent e) {
	};

	public void pause() throws InterruptedException, IOException, JavaLayerException{
		this.paused=true;
		Controleur.musicPlayer.pause();
	}
	public void resume() throws JavaLayerException, FileNotFoundException {
		this.paused=false;
		this.cadreJeu.timeResume=System.currentTimeMillis();
		Controleur.musicPlayer.start();
	}
}	

