import java.awt.Event;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import javazoom.jl.decoder.Header;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;


public class mp3Player implements Runnable {


	// the player actually doing all the work
	private Header header;
	private Player adplayer;
	private Thread playerThread;
	private int nbBytes;
	private int bytesStart = 1;
	private int currentFrame = 1;
	private String songPath;
	private FileInputStream fis;
	private Boolean playing = true;
	public int timedebPause;
	public boolean firstRun = true ;


	/*private final static int NOTSTARTED = 0;
	private final static int PLAYING = 1;
	private final static int PAUSED = 2;
	private final static int FINISHED = 3;*/


	// locking object used to communicate with player thread
	//private final Object playerLock = new Object();

	// status variable what player thread is doing/supposed to do
	//private int playerStatus = NOTSTARTED;

	public void initPlayer(String cheminFichierAudio, int firstframe) throws JavaLayerException, IOException{
		this.songPath = cheminFichierAudio;
		this.currentFrame = firstframe;
		fis=new FileInputStream(songPath);
		nbBytes = fis.available();
		//adplayer.setPlayBackListener(new PlaybackListener() {
			// public void playbackFinished(PlaybackEvent event) { 
			  //      currentFrame = (int)event.getFrame()/26;
			    //}
		//});
	}
	public void start() throws JavaLayerException, FileNotFoundException{
		this.adplayer = new Player(fis);
		Thread playerThread = new Thread(this);
		playerThread.start();
	}
		
	public void run(){
		if (bytesStart<Integer.MAX_VALUE){
			try {
				if (firstRun ){ //on releve le temps de debut de partie seulement la premiere fois, pas à la reprise après une pause
				//Controleur.startTime = System.currentTimeMillis()+230; //+200 car il ya a un retard au debut de la partie
				System.out.println("play firt time!"+ System.currentTimeMillis());
				}
				adplayer.play();
				if(adplayer.isComplete()){
					Controleur.endOfGame();
				}
			} 
			catch (JavaLayerException ex) {
				System.out.println("prb1!");
			}
			}else{
				System.out.println("prb!");
			}
	}
	public long getTimeStart(){
		return adplayer.getTimeStart();
	}

	
	public void pause() throws InterruptedException, IOException, JavaLayerException{
		this.timedebPause=adplayer.getPosition(); 			 //position dans la musique du player mp3
		bytesStart = nbBytes - this.fis.available();	 //position dans le INPUT stream
		this.adplayer.close();  			 			//fermeture du player
		fis=new FileInputStream(songPath); 				 // recréation de l'inpustStream
		fis.skip(bytesStart);   						//avance dans l'input stream jusqu'au moment de la pause
		Controleur.windowGame.cadreJeu.timePause=System.currentTimeMillis();
		JOptionPane.showMessageDialog(new JFrame(), "Reprendre la partie?");
		firstRun = false;
		Controleur.windowGame.resume();
	}

	
}
