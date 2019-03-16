package perlinNoiseVis;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
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
		contentPane = new JPanel(new BorderLayout());
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		NoisePanel np = new NoisePanel();
		NoiseStatsPanel nsp = new NoiseStatsPanel(np);
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, np, nsp);
		splitPane.setResizeWeight(0.5);

		contentPane.add(splitPane, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new FlowLayout());
		contentPane.add(buttonPanel, BorderLayout.SOUTH);

		JButton refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(e -> {
			nsp.refreshPlot();
		});
		buttonPanel.add(refreshButton);

		JLabel minmaxLabel = new JLabel("min/max");
		buttonPanel.add(minmaxLabel);

		JButton reSeedButton = new JButton("Re-Seed");
		reSeedButton.addActionListener(e -> {
			np.setSeed(new Random().nextLong());
			nsp.refreshPlot();
			double[] minMax = nsp.getMinMax();
			minmaxLabel.setText(String.format("%.2f/%.2f", minMax[0], minMax[1]));
		});
		buttonPanel.add(reSeedButton);
	}

}
