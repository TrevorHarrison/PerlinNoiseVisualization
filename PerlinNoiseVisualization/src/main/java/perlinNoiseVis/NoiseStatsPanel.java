package perlinNoiseVis;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;

public class NoiseStatsPanel extends JPanel {

	private NoisePanel sourcePanel;
	private JFreeChart histogram;
	private double min;
	private double max;

	public NoiseStatsPanel(NoisePanel sourcePanel) {
		this.sourcePanel = sourcePanel;

		refreshPlot();
	}

	public double[] getMinMax() {
		return new double[] { min, max };
	}

	public void refreshPlot() {
		Dimension sourceSize = sourcePanel.getSize();
		if (sourceSize.width == 0 || sourceSize.height == 0) {
			sourceSize = new Dimension(100, 100);
		}
		double[] vals = new double[sourceSize.height * sourceSize.width];
		float zoom = sourcePanel.zoom;
		for (int x = 0; x < sourceSize.width; x++) {
			for (int y = 0; y < sourceSize.height; y++) {
				double pnVal = sourcePanel.noise.eval(x / zoom, y / zoom);
				vals[x + y * sourceSize.width] = pnVal;
			}
		}

		min = Double.MAX_VALUE;
		max = -Double.MAX_VALUE;
		for (double d : vals) {
			min = Math.min(d, min);
			max = Math.max(d, max);
		}

		HistogramDataset hd = new HistogramDataset();
		hd.addSeries("series1", vals, 100);
		histogram = ChartFactory.createHistogram("Blah", "X Axis", "Y Axis", hd, PlotOrientation.HORIZONTAL, true,
				false, false);
	}

	@Override
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		histogram.draw(g2d, getVisibleRect());
	}

}
