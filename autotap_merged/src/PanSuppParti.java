
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;


public class PanSuppParti extends JPanel implements ActionListener{
	public FenetrePrincipale fen;

	private JButton butret,butsupp;

	Color couleur1;
	Color couleur2;

	public JList listDeroul;
	public DefaultListModel listMus;

	public PanSuppParti(Color couleur1,Color couleur2){

		this.couleur1=couleur1;
		this.couleur2=couleur2;

		//layoutManager
		BorderLayout blay = new BorderLayout(20,20);   
		this.setBackground(couleur1);
		this.setLayout(blay);  

		
		JPanel pan2 = new JPanel();
		pan2.setBackground(this.couleur1);
		
		//bouton supprimer & retour
		butsupp= new JButton("supprimer");
		pan2.add(butsupp,BorderLayout.CENTER);
		butsupp.addActionListener(this);
		butret=new JButton("retour");
		pan2.add(butret,BorderLayout.EAST);
		butret.addActionListener(this);

		this.add(pan2,BorderLayout.SOUTH);

		listMus = new DefaultListModel();
		listDeroul = new JList(listMus);
		listDeroul.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listDeroul.setLayoutOrientation(JList.VERTICAL);
		listDeroul.setVisibleRowCount(-1);
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
			}
		else if (source == this.butsupp) {
			Controleur.supprimerPartition(listDeroul.getSelectedIndex());
		}
	}

	public void miseaJourListeMusique(List<Partition> list){
		listMus.clear();
		for (int i=0; i <list.size(); ++i) {
			listMus.addElement(list.get(i).getNom());
		}
	}
}
