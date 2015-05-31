package reversi;

//Imports
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class ScoreTable extends JFrame
{
	private	JPanel		topPanel;
	private	JTable		table;
	private	JScrollPane scrollPane;

	// Constructor of main frame
	public ScoreTable(String player1, String player2)
	{
		// Set the frame characteristics
		setTitle( "scores" );
		setSize( 300, 200 );
		setBackground( Color.gray );

		// Create a panel to hold all other components
		topPanel = new JPanel();
		topPanel.setLayout( new BorderLayout() );
		getContentPane().add( topPanel );

		// Create columns names
		String columnNames[] = { player1, player2 };

		// Create some data
		String dataValues[][] =
		{
			{  "0", "0" }
		};

		// Create a new table instance
		table = new JTable( dataValues, columnNames );
		table.setEnabled(false);
		


		// Add the table to a scrolling pane
		scrollPane = new JScrollPane( table );
		topPanel.add( scrollPane, BorderLayout.CENTER );
        this.setLocation(1000, 250);

		this.setVisible(true);
	}
	public JTable getTable(){
		return table;
	}
}
