import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class PanScores extends JPanel implements ActionListener {

	public FenetrePrincipale fen;
	public JList listDeroul;
	public DefaultListModel listScores;
	public JTable tabScore;
	public DefaultTableModel tabModel;
	public String[][] dataScores = new String[100][4];
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
		
		String[] head ={"Joueur","Nom de la chanson","Difficulté","Score"};
		tabModel = new DefaultTableModel(dataScores,head);
		tabScore = new JTable(tabModel);
		
		listScores = new DefaultListModel();
		listDeroul = new JList(listScores);
		listDeroul.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listDeroul.setLayoutOrientation(JList.VERTICAL_WRAP);
		listDeroul.setVisibleRowCount(4);
		//listScroller.setPreferredSize(new Dimension(250, 80));
		this.add(new JScrollPane(tabScore),BorderLayout.CENTER);
	}

	public void actionPerformed(ActionEvent even) {
		fen.setContentPane(fen.panVide);
		System.out.println("retour");
		fen.setVisible(true);           //affiche la JFrame
	}

	public void miseaJourListeScore(List<Score> list){
		tabModel.setRowCount(list.size());
		for (int i=0; i <list.size(); i++) {
			tabModel.setValueAt(list.get(i).getJoueur(), i, 0);
			tabModel.setValueAt(list.get(i).getSongName(),i,1);
			tabModel.setValueAt(list.get(i).getNiveau(),i,2);
			tabModel.setValueAt(list.get(i).getNbScore(),i,3);
		}  
	}
}
