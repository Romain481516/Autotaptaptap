
public class Note {
	public int hauteur; //1 gauche - 2 milieu - 3 droite - 4 gauche+milieu - 5 gauche+droite  - 6 milieu + droite
	public long durationNote; //en millis ;  -1 si note "ponctuelle"
	public long instantNote;  //en millis depuis le debut de la chanson
	
	//constructeur pour test
	public Note (int hight, long inst){
		this.hauteur = hight ;
		this.instantNote = inst;
		this.durationNote=-1;
	}	
}
