package ihm;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;


public class exploFichiers extends JFileChooser {
	
	public int popup() {
		this.setFileSelectionMode(JFileChooser.FILES_ONLY);
		this.setAcceptAllFileFilterUsed(false);
		this.setMultiSelectionEnabled(false);
		this.setFileFilter(new FileNameExtensionFilter("Fichiers mp3","mp3"));
		return this.showDialog(this,"Choisir la chanson à traiter");
	}
}

