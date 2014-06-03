import ihm.ImageFond;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
public class CadreJeu extends JPanel {

	public List<TripletNote> ListNote;
	private long timeStart;
	public long timeResume;
	public long timePause;
	public ArrayList<TripletNote> tapableNotes = new ArrayList<TripletNote>();  //calcl score
	public int indexLastNote = 1 ;

	Image backgroundPicture;
	

	public CadreJeu(Color backColor,List<TripletNote> listNote,long timeStart) {
		this.timeStart = timeStart;
		this.ListNote = listNote;
		this.timePause=0;
		setBackground(backColor);
		//image fond
		backgroundPicture = null;
		try {
			backgroundPicture = ImageIO.read(new File("fondfenjeu2.jpg"));
		} catch (IOException e1) {
			System.out.println("image non trouvve");
			e1.printStackTrace();
		}
	}
	
	public void paintComponent (Graphics g){
		super.paintComponent(g);
		tapableNotes.clear();
		g.translate(90, 390);
		g.drawImage(backgroundPicture,-90,-390,600,500,this);
		for(int i=indexLastNote; i <this.ListNote.size(); i++){ // ou indexlast note +50
			if (this.ListNote.get(i).duration < 0){
				long ecarttemps = (System.currentTimeMillis() - timeStart) - (this.ListNote.get(i).debut + timeResume - timePause) ; 
				if  (ecarttemps > -1750  & (ecarttemps < 250 )){
					tapableNotes.add(this.ListNote.get(i));
					int x = (int) (ecarttemps/5);
					switch (this.ListNote.get(i).getTouche()) {
					case 1: g.setColor(Color.blue);
					g.fillOval(0, x, 30,25);;
					break;
					case 2: g.setColor(Color.red); g.fillOval(195, x, 30,25);
					break;
					case 3: g.setColor(Color.green); g.fillOval(395, x, 30,25);
					break;
					case 4:  g.setColor(Color.blue);g.fillOval(0, x, 30,25);
					g.setColor(Color.red); g.fillOval(195, x, 30,25);
					break;
					case 5: g.setColor(Color.blue); g.fillOval(0, x, 30,25);
					g.setColor(Color.green); g.fillOval(395, x, 30,25);
					break;
					case 6: g.setColor(Color.green);  g.fillOval(395, x, 30,25);
					g.setColor(Color.red); g.fillOval(195, x, 30,25);
					break;
					case 7: g.setColor(Color.blue); g.fillOval(0, x, 30,25);
					g.setColor(Color.green);  g.fillOval(395, x, 30,25);
					g.setColor(Color.red); g.fillOval(195, x, 30,25);
					break;
					}
				}
				//if (ecarttemps > 250){indexLastNote=i;}
			} else {
				long ecarttemps = (System.currentTimeMillis() - timeStart) -  (this.ListNote.get(i).debut + timeResume - timePause ) ;
				if  (ecarttemps > -1750  & (ecarttemps < 250 + this.ListNote.get(i).duration)){
					int x = (int) (ecarttemps/5);
					switch (this.ListNote.get(i).getTouche()) {
					case 1: g.setColor(Color.blue);
					g.fillOval(0, x, 30,(int) this.ListNote.get(i).duration/5);;
					break;
					case 2: g.setColor(Color.red); g.fillOval(195, x, 30,25);
					break;
					case 3: g.setColor(Color.green); g.fillOval(395, x, 30,25);
					break;
					case 4:  g.setColor(Color.blue);g.fillOval(0, x, 30,25);
					g.fillOval(195, x, 30,25);
					break;
					case 5: g.setColor(Color.blue); g.fillOval(0, x, 30,25);
					g.setColor(Color.green); g.fillOval(395, x, 30,25);
					break;
					case 6: g.setColor(Color.green);  g.fillOval(395, x, 30,25);
					g.setColor(Color.red); g.fillOval(195, x, 30,25);
					break;
					}
				}
			}
		}
	}
}

