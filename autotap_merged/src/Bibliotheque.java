import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;


public class Bibliotheque {
	//Attribut de la classe
	//TODO Organisation de la liste de partition - Ajout ? Acc�s par lecture ? Reg�n�rer � chaque fois - Bool�en de modification ?
	List<Partition> ListePartition= new ArrayList<Partition>();
	String biblioXML;
	//TODO A impl�menter - Si la bibliotheque n'existe pas la cr�er - idem partition
	public Bibliotheque(String cheminbiblioXML){
		biblioXML = cheminbiblioXML;
		File biblioFichier = new File(biblioXML);
		if(biblioFichier.exists()){
			//Appelle du parser SAX sur le fichier XML Sp�cifi�
			try{
				SAXParserFactory factory = SAXParserFactory.newInstance(); 
				SAXParser saxParser = factory.newSAXParser();
				InputStream xmlStream = new FileInputStream(biblioFichier);
				//Affectation des attributs nom et cheminFichierAudio
				saxParser.parse(xmlStream, new SaxHandlerBibliotheque(this));
			} catch (Exception e) {
				e.printStackTrace();
			}
			//V�rification de l'existance sur le disque des partitions (recherche des fichiers XML)
			for(int i=0; i<ListePartition.size();i++){
				File Atester = new File(ListePartition.get(i).getCheminPartition());
				if(!Atester.exists()){
					this.deletePartition(ListePartition.get(i));
					i--;
				}
			}
		}else this.createFichierBiblio();
	}
	//TODO Impl�menter la v�rification des partitions par rapport au Schema XML
	//V�rifie l'int�grit� des partitions
	public void VerifIntegritePartition(){

	}
	public void addPartition(Partition part){
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder;
		try {
			docBuilder = docFactory.newDocumentBuilder();
			org.w3c.dom.Document doc ;
			File xml = new File(biblioXML);
			doc = docBuilder.parse(xml);

			//Placement au nivau de BIBLIO
			Node biblio = doc.getElementsByTagName("BIBLIO").item(0);
			//Ajout du noeud Chanson
			Element chanson = doc.createElement("Chanson");
			biblio.appendChild(chanson);

			//Ajout de l'�l�ment Nom
			Element nom = doc.createElement("nom");
			Text nomTxt = doc.createTextNode(part.getNom());
			nom.appendChild(nomTxt);
			chanson.appendChild(nom);
			//Ajout de l'�l�ment cheminPartition
			Element chemin = doc.createElement("cheminPartition");
			Text cheminTxt = doc.createTextNode(part.getCheminPartition());
			chemin.appendChild(cheminTxt);
			chanson.appendChild(chemin);

			//XPath�s normalize-space - Indentation
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodeList;

			nodeList = (NodeList) xPath.evaluate("//text()[normalize-space()='']",doc,XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); ++i) {
				Node node = nodeList.item(i);
				node.getParentNode().removeChild(node);
			}


			//D�finition de la source et de la destination
			DOMSource domSource = new DOMSource(doc);
			StreamResult result = new StreamResult(xml);
			//Cr�ation du transformeur
			TransformerFactory fabrique = TransformerFactory.newInstance();
			Transformer transformer = fabrique.newTransformer();
			//Propri�t� du transformeur
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
			//Transformation
			transformer.transform(domSource, result);

			//Ajout � la liste des partitions
			this.ListePartition.add(part);

			System.out.println("Done"); //TEST
		} catch (XPathExpressionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();


		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (SAXException sae) {
			sae.printStackTrace();
		}
	}

	public void deletePartition(Partition part){
		String nom = part.getNom();
		String chemin = part.getCheminPartition();
		try {


			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder;
			docBuilder = docFactory.newDocumentBuilder();
			org.w3c.dom.Document doc ;
			File xml = new File(biblioXML);
			doc = docBuilder.parse(xml);

			NodeList listeChanson = doc.getElementsByTagName("Chanson");

			if ( listeChanson!= null && listeChanson.getLength() > 0) {
				for (int i = 0; i < listeChanson.getLength(); i++) {

					Node node = listeChanson.item(i);
					Element e = (Element) node;
					NodeList nodeList = e.getElementsByTagName("nom");
					String nomNode = nodeList.item(0).getChildNodes().item(0).getNodeValue();
					nodeList = e.getElementsByTagName("cheminPartition");
					String cheminNode = nodeList.item(0).getChildNodes().item(0).getNodeValue();
					//TEST
					System.out.println(nomNode);
					System.out.println(cheminNode);
					if (nomNode.equals(nom) && cheminNode.equals(chemin)) 
					{
						node.getParentNode().removeChild(node);
					}
				}
			}
			//XPath�s normalize-space - Indentation
			XPath xPath = XPathFactory.newInstance().newXPath();
			NodeList nodeList;

			nodeList = (NodeList) xPath.evaluate("//text()[normalize-space()='']",doc,XPathConstants.NODESET);
			for (int i = 0; i < nodeList.getLength(); ++i) {
				Node node = nodeList.item(i);
				node.getParentNode().removeChild(node);
			}


			//D�finition de la source et de la destination
			DOMSource domSource = new DOMSource(doc);
			StreamResult result = new StreamResult(xml);
			//Cr�ation du transformeur
			TransformerFactory fabrique = TransformerFactory.newInstance();
			Transformer transformer = fabrique.newTransformer();
			//Propri�t� du transformeur
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
			transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
			//Transformation
			transformer.transform(domSource, result);

			//Suppression de la liste de partition
			this.ListePartition.remove(part);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public List<Score> allScore(){
		List<Score> ListeScore= new ArrayList<Score>();
		if ( this.ListePartition!= null && ListePartition.size() > 0) 
		{
			for(int i=0; i < this.ListePartition.size(); i++){
				ListeScore.addAll(ListeScore.size(),this.ListePartition.get(i).accessScore());
			}
		}
		return ListeScore;
	}
	public List<Partition> getListePartition(){
		return ListePartition;

	}
	//TODO Droit d'�criture ? etc ... Comment on g�re...
	protected void createFichierBiblio(){
		try {
			// Cr�ation d'un nouveau DOM
			DocumentBuilderFactory fabrique = DocumentBuilderFactory.newInstance();
			DocumentBuilder constructeur;
			constructeur = fabrique.newDocumentBuilder();
			Document document = constructeur.newDocument();

			// Propri�t�s du DOM
			document.setXmlVersion("1.0");
			document.setXmlStandalone(false);

			// Cr�ation de l'arborescence du DOM
			Element biblio = document.createElement("BIBLIO");
			document.appendChild(biblio);
			Text biblioTxt = document.createTextNode(" ");
			biblio.appendChild(biblioTxt);

			// Cr�ation de la source DOM
			Source source = new DOMSource(document);

			// Cr�ation du fichier de sortie
			File file = new File("Sauvegarde\\Bibliotheque.xml");
			Result resultat = new StreamResult(file);

			// Configuration du transformer
			TransformerFactory fabriqueTrans = TransformerFactory.newInstance();
			Transformer transformer;

			transformer = fabriqueTrans.newTransformer();

			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");

			// Transformation
			transformer.transform(source, resultat);
			//D�finition de lieu de saugarde du XML
			this.biblioXML = "Sauvegarde\\Bibliotheque.xml";
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException et) {
			// TODO Auto-generated catch block
			et.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
