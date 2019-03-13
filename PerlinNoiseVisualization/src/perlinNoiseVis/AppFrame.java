package perlinNoiseVis;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

public class AppFrame extends JFrame {

	private JPanel contentPane;

	public static void main(String[] args) {
		AppFrame frame = new AppFrame();
		EventQueue.invokeLater(() -> {
			try {
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		Timer t = new Timer(true);
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				SwingUtilities.invokeLater(() -> {
					if (frame.isVisible()) {
						frame.repaint();
					}
				});
			}
		}, 500, 100);
	}

	public AppFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new NoisePanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

}
