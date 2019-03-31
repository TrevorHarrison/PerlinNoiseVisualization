package perlinNoiseVis;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JPanel;

public class NoisePanel extends JPanel {

	private static final float SIMPLEX_NOISE_MIN = -0.88f;
	private static final float SIMPLEX_NOISE_MAX = 0.88f;
	private static final float SIMPLEX_NOISE_RANGE = SIMPLEX_NOISE_MAX - SIMPLEX_NOISE_MIN;

	/* package */ OpenSimplexNoise noise;
	/* package */ int zoom = 20;
	int cutOffPercent;
	boolean binaryMode;

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

	public int getZoom() {
		return zoom;
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);

		Graphics2D g2d = (Graphics2D) g;

		int ht = getHeight();
		int width = getWidth();
		float cutOff = SIMPLEX_NOISE_MIN + (SIMPLEX_NOISE_RANGE * (cutOffPercent / 100f));

		BufferedImage bufImg = new BufferedImage(width, ht, BufferedImage.TYPE_INT_ARGB);
		for (int x = 0; x < getWidth(); x++) {
			for (int y = 0; y < getHeight(); y++) {
				double pnVal = noise.eval(x / (float) zoom, y / (float) zoom);
				int colorVal = binaryMode
						? colorMapper.mapColor(pnVal < cutOff ? SIMPLEX_NOISE_MIN : SIMPLEX_NOISE_MAX,
								SIMPLEX_NOISE_MIN, SIMPLEX_NOISE_MAX)
						: colorMapper.mapColor(pnVal, SIMPLEX_NOISE_MIN, SIMPLEX_NOISE_MAX);
				bufImg.setRGB(x, y, colorVal);
			}
		}
		g2d.drawImage(bufImg, null, null);
	}

	public int getCutOffPercent() {
		return cutOffPercent;
	}

	public void setCutOffFloor(int cutOffPercent) {
		this.cutOffPercent = cutOffPercent;
	}

	public boolean isBinaryMode() {
		return binaryMode;
	}

	public void setBinaryMode(boolean binaryMode) {
		this.binaryMode = binaryMode;
	}

}
