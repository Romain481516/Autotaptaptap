public class TripletNote {

	//Attribut de la classe TripletNote
	boolean[] tripletNote = new boolean[3];
	long duration;
	long debut;

	//Constructeur
	public TripletNote(boolean[] triplet,long deb,long dur){
		super();
		for(int i=0; i<3; i++){
			tripletNote[i] = triplet[i];
		}
		duration = dur;
		debut = deb;
	}
	/*Les setters ne servent plus car on construit directement l'objet
	 * avec ces attributs (voir Constructeur)
	 //Methode qui attribut une valeur (true ou false) au touche.
	public void setTouche(){
	}
	public void setDebut(int deb){
	debut =deb;
	}
	public void setDuree(int dur){
	duree = dur;
	}
	 */
	
	public int getTouche(){
		int num =0;
		/*1 gauche - 2 milieu - 3 droite 
		 *4 gauche+milieu - 5 gauche+droite
		 *6 milieu + droite - 7 : les trois touches
		 */
		if(this.tripletNote[0] && !this.tripletNote[1] && !this.tripletNote[2]){
			num = 1; //Gauche
		}
		else if(!this.tripletNote[0] && this.tripletNote[1] && !this.tripletNote[2]){
			num = 2; //Milieu
		}
		else if(!this.tripletNote[0] && !this.tripletNote[1] && this.tripletNote[2]){
			num = 3; //Droite
		}
		else if(this.tripletNote[0] && this.tripletNote[1] && !this.tripletNote[2]){
			num = 4; //Gauche + Milieu
		}
		else if(this.tripletNote[0] && !this.tripletNote[1] && this.tripletNote[2]){
			num = 5; //Gauche + Droite
		}
		else if(!this.tripletNote[0] && this.tripletNote[1] && this.tripletNote[2]){
			num = 6; //Milieu + Droite
		}
		else if(this.tripletNote[0] && this.tripletNote[1] && this.tripletNote[2]){
			num = 7; //Milieu + Droite + gauche
		}
		else {
			System.out.println("Une erreur est survenue. (Voir TripletNote");
		}
		return num;
	}
	
	//TODO choisir ce que renvoie la méthode.
	public long getDuree(){
		return this.duration;
	}
	//TODO choisir ce que renvoie la méthode.
	public long getDebut(){
		return this.debut;
	}
	//Pour les tests
	public String toString(){
		String toReturn = "Note:"+"\n" + "Touches: ";
		for(int i = 0; i<3; i++){
			toReturn = toReturn + tripletNote[i]+ " ";	
		}
		toReturn = toReturn + "\nDebut: " + debut +"\n";
		toReturn = toReturn + "Duree: " + duration +"\n";
		return toReturn;

	}
}
