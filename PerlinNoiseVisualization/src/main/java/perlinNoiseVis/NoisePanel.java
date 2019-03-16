package perlinNoiseVis;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JPanel;

public class NoisePanel extends JPanel {

	/* package */ OpenSimplexNoise noise;
	/* package */ int zoom = 20;
	private ColorMapper colorMapper = new ColorMapper(Color.black, Color.red, Color.orange, Color.yellow, Color.green,
			Color.blue, Color.white);

	public NoisePanel() {
		this(new Random().nextLong());
	}

	public NoisePanel(long seed) {
		noise = new OpenSimplexNoise(seed);
	}

	public void setSeed(long newSeed) {
		noise = new OpenSimplexNoise(newSeed);
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;
		//		int dia = Math.min(getWidth(), getHeight());
		//		int radius = dia / 2;
		//		int steps = dia * 2;
		//		double stepInc = (Math.PI * 2) / steps;
		//
		//		for (double a = 0; a < Math.PI * 2; a += stepInc) {
		//			double p1 = radius + (radius * Math.sin(a));
		//			double p2 = radius + (radius * Math.cos(a));
		//			g2d.drawRect((int) p1, (int) p2, 1, 1);
		//			System.out.println("" + a + ", " + p1 + ", " + p2 + ", " + stepInc);
		//		}

		int ht = getHeight();
		int width = getWidth();
		BufferedImage bufImg = new BufferedImage(width, ht, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				double pnVal = noise.eval(x / (float) zoom, y / (float) zoom);
				bufImg.setRGB(x, y, colorMapper.mapColor(pnVal, -.88, .88));
			}
		}
		g2d.drawImage(bufImg, null, null);
	}

}
