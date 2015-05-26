package reversi;

import java.awt.Component;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.*;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.LinkedList;

public class SettingsFrame extends JFrame {
	
	private JSpinner sizeSpinner;
	
	public SettingsFrame() {
		super("Settings");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
		
		//setSizeSpinner(new SpinnerNumberModel(8, 4, 16, 2));
		JSpinner spinner = new SpinnerNumberModel(8, 4, 16, 2);
		JPanel sizePane = makeSpinnerPanel("The pizza costs:", spinner);
		getContentPane().add(sizePane);
		
		
		setResizable(false);
		setVisible(true);
		pack();
		
	}
	
	public void setSizeSpinner(JSpinner sizeSpinner) {
		this.sizeSpinner = sizeSpinner;
	}
	
	private JPanel makeSpinnerPanel(String label, JSpinner spin) {
		JPanel tPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		tPane.setMaximumSize(new Dimension(250, 50));
		tPane.setMinimumSize(new Dimension(250, 50));
		tPane.setAlignmentX(Component.LEFT_ALIGNMENT);
		JLabel tLabel = new JLabel(label);
		tPane.add(tLabel);
		tPane.add(spin);
		spin.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				//TODO
			}
		});
		return tPane;
	}
	
	public JSpinner getSizeSpinner() {
		return sizeSpinner;
	}

}
