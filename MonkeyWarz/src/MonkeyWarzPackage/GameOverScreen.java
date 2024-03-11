package MonkeyWarzPackage;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GameOverScreen extends JDialog {

	private final JPanel contentPanel = new JPanel();
	GameOverScreen frame;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
		//	GameOverScreen dialog = new GameOverScreen();
		//	dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		//	dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public GameOverScreen(MainMenu menu) {
		frame=this;
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		this.setLocationRelativeTo(null);
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lbGameOver = new JLabel("Game Over");
		lbGameOver.setBounds(112, 10, 209, 57);
		lbGameOver.setFont(new Font("Yu Gothic UI", Font.PLAIN, 42));
		contentPanel.add(lbGameOver);
		
		JButton btnGameOver = new JButton("Back To Menu");
		btnGameOver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				menu.setVisible(true);
				frame.dispose();
			}
		});
		btnGameOver.setBounds(127, 142, 178, 44);
		contentPanel.add(btnGameOver);
	}
}
