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
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;


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
			System.out.println("play!");
			try {
				Controleur.startTime = System.currentTimeMillis()+230; //+200 car il ya a un retard au debut de la partie
				adplayer.play(); 
				//bytesStart,Integer.MAX_VALUE
			} 
			catch (JavaLayerException ex) {
				System.out.println("prb1!");
			}
			}else{
				System.out.println("prb!");
			}
	}	

	
	public void pause() throws InterruptedException, IOException, JavaLayerException{
		//playing = false;
		this.timedebPause=adplayer.getPosition();
		bytesStart = nbBytes - this.fis.available();
		this.adplayer.close();
		Controleur.fenetreJeuencours.cadreJeu.timePause=System.currentTimeMillis();
		System.out.println(nbBytes);
		fis=new FileInputStream(songPath);
		fis.skip(bytesStart);
		JOptionPane.showMessageDialog(new JFrame(), "Reprendre la partie?");
		Controleur.fenetreJeuencours.resume();
	}

	
}
