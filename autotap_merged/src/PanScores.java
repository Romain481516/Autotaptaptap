import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;


public class PanScores extends JPanel implements ActionListener {

	public FenetrePrincipale fen;
	public JList listDeroul;
	public DefaultListModel listScores;

	private JButton butret;


	Color couleur1;
	Color couleur2;

	public PanScores(Color couleur1, Color couleur2){

		this.couleur1=couleur1;
		this.couleur2=couleur2;

		//layoutManager
		BorderLayout blay = new BorderLayout(20,20);   
		this.setBackground(couleur1);
		this.setLayout(blay);  


		JPanel pan2 = new JPanel();
		pan2.setBackground(this.couleur1);


		butret=new JButton("retour");
		pan2.add(butret,BorderLayout.EAST);
		butret.addActionListener(this);

		this.add(pan2,BorderLayout.SOUTH);

		//liste musique

		//ListModel lm;
		//listeMus = java.util.Arrays.asList(tabnomChanson);
		listScores = new DefaultListModel();
		listDeroul = new JList(listScores);
		listDeroul.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listDeroul.setLayoutOrientation(JList.VERTICAL);
		JScrollPane listScroller = new JScrollPane(listDeroul);
		listScroller.setPreferredSize(new Dimension(250, 80));
		this.add(listDeroul,BorderLayout.CENTER);

	}


	public void actionPerformed(ActionEvent even) {

		fen.setContentPane(fen.panVide);
		System.out.println("retour");
		fen.setVisible(true);           //affiche la JFrame
	}

	public void miseaJourListeScore(List<Score> list){
		listScores.clear();
		for (int i=0; i <list.size(); ++i) {
			listScores.addElement(list.get(i).getJoueur()+"                "+"chanson:"+"  "+list.get(i).getSongName()+"            "+"niveau:"+"  "+list.get(i).getNiveau()+"               "+"Score:"+"  "+list.get(i).getNbScore());
		}  
	}
}
