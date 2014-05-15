package ihm;
import javax.swing.JOptionPane;


public class ConfirmQuit extends JOptionPane {
	
	public static void popup(){
		
		int option = showConfirmDialog(null,"Voulez-vous quitter l'application?","Arrêt de l'application",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
		
		if (option == 0){
			System.exit(0);
		}
	}
}
