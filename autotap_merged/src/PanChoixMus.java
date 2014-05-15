
import java.awt.event.*;
import java.awt.*;
import java.awt.List;
import javax.swing.*;
import java.util.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class PanChoixMus extends JPanel implements ActionListener{
	public FenetrePrincipale fen;

	private JButton butret,butval;
	private JRadioButton choixniveaudur,choixniveaufacile,choixniveaunormal;

	Color couleur1;
	Color couleur2;

	public JList listDeroul;
	public DefaultListModel listMus;

	public PanChoixMus(Color couleur1,Color couleur2){

		this.couleur1=couleur1;
		this.couleur2=couleur2;

		//layoutManager
		BorderLayout blay = new BorderLayout(20,20);   
		this.setBackground(couleur1);
		this.setLayout(blay);  

		//groupe de boutons radio
		Box radb = new Box(BoxLayout.Y_AXIS);
		ButtonGroup grpradio = new ButtonGroup();

		choixniveaufacile = new JRadioButton("Facile");
		choixniveaufacile.setSelected(true);
		choixniveaufacile.setBackground(couleur1);
		grpradio.add(choixniveaufacile);
		radb.add(choixniveaufacile);

		choixniveaunormal = new JRadioButton("Normal");
		choixniveaunormal.setBackground(couleur1);
		grpradio.add(choixniveaunormal);
		radb.add(choixniveaunormal);

		choixniveaudur = new JRadioButton("Difficile");
		grpradio.add(choixniveaudur);
		choixniveaudur.setBackground(couleur1);
		radb.add(choixniveaudur);

		this.add(radb,BorderLayout.EAST);
		
		//bouton valider&retour
		JPanel pan2 = new JPanel();
		pan2.setBackground(this.couleur1);
		
		butval=new JButton("valider");
		butval.setBackground(couleur2);
		pan2.add(butval,BorderLayout.CENTER);
		butval.addActionListener(this);
		butret=new JButton("retour");
		butret.setBackground(couleur2);
		pan2.add(butret,BorderLayout.EAST);
		butret.addActionListener(this);

		this.add(pan2,BorderLayout.SOUTH);

		listMus = new DefaultListModel();
		listDeroul = new JList(listMus);
		listDeroul.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listDeroul.setLayoutOrientation(JList.VERTICAL);
		JScrollPane listScroller = new JScrollPane(listDeroul);
		listScroller.setPreferredSize(new Dimension(250, 80));
		this.add(listDeroul,BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent even) {
		Object source = even.getSource();
		if (source == this.butret) {
			fen.setContentPane(fen.panVide);
			System.out.println("retour");
			fen.setVisible(true);       
			fen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);}

		else if (source == this.butval) {
			System.out.println("debutjeu avec ");
			if( choixniveaufacile.isSelected() == true) {
				Controleur.lancerPartie(listDeroul.getSelectedIndex(),"Facile" );
			} else if( choixniveaunormal.isSelected() == true) {
				Controleur.lancerPartie(listDeroul.getSelectedIndex(),"Moyen");
			} else if( choixniveaudur.isSelected() == true) {
				Controleur.lancerPartie(listDeroul.getSelectedIndex(),"Difficile");
			}
		}
	}

	public void miseaJourListeMusique(java.util.List<Partition> list){
		listMus.clear();
		for (int i=0; i <list.size(); ++i) {
			listMus.addElement(list.get(i).getNom());
		}
	}
}