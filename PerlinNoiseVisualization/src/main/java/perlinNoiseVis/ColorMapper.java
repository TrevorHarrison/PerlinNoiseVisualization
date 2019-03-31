package perlinNoiseVis;

import java.awt.Color;

public class ColorMapper {

	private int[] colors;

	public ColorMapper(Color... gradientColors) {
		colors = new int[2 + (gradientColors.length - 2) * 5];
		colors[0] = gradientColors[0].getRGB();
		colors[colors.length - 1] = gradientColors[gradientColors.length - 1].getRGB();

		for (int colorIndex = 1, rgbIndex = 1; colorIndex < gradientColors.length - 1; colorIndex++) {
			Color c = gradientColors[colorIndex];
			colors[rgbIndex++] = c.brighter().brighter().getRGB();
			colors[rgbIndex++] = c.brighter().getRGB();
			colors[rgbIndex++] = c.getRGB();
			colors[rgbIndex++] = c.darker().getRGB();
			colors[rgbIndex++] = c.darker().darker().getRGB();
		}
	}

	public int mapColor(double d, double min, double max) {
		d = (d + (-min)) / (max - min);
		return colors[Math.min((int) (d * colors.length), colors.length - 1)];
	}

}
