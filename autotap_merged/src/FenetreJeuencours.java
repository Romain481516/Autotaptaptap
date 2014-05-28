import ihm.ConfirmQuit;
import ihm.ImageFond;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.jlp;

public class FenetreJeuencours extends JFrame implements ActionListener,KeyListener,Runnable {
	public List<TripletNote> ListNote;
	public long timeStart;
	public JOptionPane confirmStopPartie,fenFinPartie;
	public ConfirmQuit confirmQuit;
	Color couleur1,couleur2;

	public JButton butpause;
	public JButton butquit;

	public CadreJeu cadreJeu;
	TextField valScore = new TextField("0000");
	int currentScore = 00;
	public TextField valBestScore = new TextField("0000");

	public ArrayList<Note> listkeyPressed; //touches pressées pendant le repaint et le sleep
	public mp3Player mpplay;

	public FenetreJeuencours(Color couleur1,Color couleur2,List<TripletNote> listNote,long timeStart){
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

		//Ajout  du cadre de jeu 
		cadreJeu = new CadreJeu(this.couleur1,listNote,timeStart);
		paneau.add(cadreJeu,BorderLayout.CENTER);

		this.setContentPane(paneau);
		this.setVisible(true);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE); //opération par défaut a la fermeture

		//sortie app
		WindowListener sortieApp = new  WindowAdapter(){
			public void windowClosing (WindowEvent e){
				ConfirmQuit.popup();
			}
		}; 
		this.addWindowListener(sortieApp);
	}

	public void repaint(){
		cadreJeu.repaint();
		valScore.setText(String.valueOf(currentScore));
	}


	public void start(){
		Thread t = new Thread(this);
		t.start();
	}
	
	public void run() {
		long sleep = 30;
		while (true) {
			repaint();
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				System.out.println("Interrupted: " + e.getMessage());
			}         
		}
	}



	public void actionPerformed(ActionEvent even) {
		Object source = even.getSource();
		if (source == this.butpause) {
			System.out.println("pause1");        
		}
		else if (source == this.butquit) {
		}
	}

	public void keyReleased(KeyEvent even){
	}

	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_J :
			System.out.println(Controleur.calculScore(new Note(1,System.currentTimeMillis())));
			break;
		case KeyEvent.VK_K:
			System.out.println(Controleur.calculScore(new Note(2,System.currentTimeMillis())));
			break;
		case KeyEvent.VK_L :
			System.out.println(Controleur.calculScore(new Note(3,System.currentTimeMillis())));
			String input = JOptionPane.showInputDialog(null,"Nom du joueur:");
			System.out.println(input);
		case KeyEvent.VK_P :
			try {
				Controleur.pause();
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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
			
			System.out.println("pause");
			break;
		}
	}

	public void keyTyped(KeyEvent e) {
	};
}	

