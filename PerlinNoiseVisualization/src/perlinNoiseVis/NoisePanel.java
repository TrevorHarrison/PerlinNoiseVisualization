package perlinNoiseVis;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class NoisePanel extends JPanel {

	private OpenSimplexNoise noise = new OpenSimplexNoise(455563434);
	private float anim;
	private float animStep = .05f;

	public NoisePanel() {
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
				bufImg.setRGB(x, y, mapColor(
						noise.eval(x / 20f, y / 20f) /* / (double) (width * 1), y / (double) (ht * 20)) */, anim));
			}
		}
		g2d.drawImage(bufImg, null, null);
		anim += animStep;
		if (anim < 0 || anim >= 1) {
			animStep = -animStep;
		}
	}

	int mapColor(double val, double lvl) {
		if (val < lvl - .9) {
			return Color.black.getRGB();
		} else if (val < lvl - .5) {
			return Color.DARK_GRAY.getRGB();
		} else if (val < lvl - .1) {
			return Color.gray.getRGB();
		} else if (val < lvl + .1) {
			return Color.blue.getRGB();
		} else if (val < lvl + .5) {
			return Color.green.getRGB();
		} else if (val < lvl + .9) {
			return Color.GREEN.darker().getRGB();
		} else {
			return Color.white.getRGB();
		}
	}

}
