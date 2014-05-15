import java.util.List;


public class Partition2 {
	public String nomChanson;
	public String cheminFichierAudio;
	public String cheminFichierXml;
	public String niveau;
	public Score[] listScore;
	public Note[] partFacile;
	public List<Score> listScor;
	public List<Note> listTripletNote;

	
	
	//constructeur pour test
	public Partition2 (Note[] tabNotesTest){
		this.partFacile = tabNotesTest;
	}
}
