import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandlerBibliotheque extends DefaultHandler {

	Bibliotheque biblio;
	//Attribut Position
	boolean bChanson = false;
	boolean bNom = false;
	boolean bCheminPartition = false;
	
	String chemin;
	String nom;
	
	//Constructeur
	public SaxHandlerBibliotheque(Bibliotheque bibliotheque) {
		super();
		biblio = bibliotheque;
	}
	//Méthode
	public void startElement(String uri, String localName, String qName, Attributes attributes)
			throws SAXException {
		if(qName.equals("Chanson")){
			bChanson = true;
		}
		if(qName.equals("nom")){
			bNom = true;
		}
		if(qName.equals("cheminPartition")){
			bCheminPartition = true;
		}
	}
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if(qName.equals("Chanson")){
			bChanson = false;
			//TODO Ajouter Get -> Accès listePartition Biblio
			biblio.ListePartition.add(new Partition(chemin,nom));
		}
		if(qName.equals("nom")){
			bNom = false;
		}
		if(qName.equals("cheminPartition")){
			bCheminPartition = false;
		}
	}
	public void characters(char[] chars, int start, int length) throws SAXException {
		if(bCheminPartition){
			chemin = new String(chars,start,length);
		}
		if(bNom){
			nom = new String(chars,start,length);
		}
	}
}
