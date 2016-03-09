import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RGBToLAB {
	public static double gamma(double x) {
		return x > 0.04045 ? Math.pow((x + 0.055d) / 1.055d, 2.4d) : x / 12.92;
	}

	public static void main(String args[]) throws IOException {
		int count = 0;
		File file = new File("C:/Users/Administrator/Desktop/0_13301800687gGZ.jpg");
		BufferedImage image = ImageIO.read(file);
		int height = image.getHeight();
		int width = image.getWidth();
		int[] rImg = new int[height * width];
		int[] gImg = new int[height * width];
		int[] bImg = new int[height * width];
		double[] RImg = new double[height * width];
		double[] GImg = new double[height * width];
		double[] BImg = new double[height * width];
		double[] XImg = new double[height * width];
		double[] YImg = new double[height * width];
		double[] ZImg = new double[height * width];
		double[] FXImg = new double[height * width];
		double[] FYImg = new double[height * width];
		double[] FZImg = new double[height * width];
		double[] lImg = new double[height * width];
		double[] aImg = new double[height * width];
		double[] lbImg = new double[height * width];
		double[] labImg = new double[height * width];
		int[] rgbImg = new int[height * width];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				rgbImg[count] = image.getRGB(i, j);
				count++;
			}
		}

		for (int i = 0; i < rgbImg.length; i++) {
			int rgb = rgbImg[i];
			rImg[i] = (rgb & 16711680) >> 16;
			gImg[i] = (rgb & 65280) >> 8;
			bImg[i] = (rgb & 255);

			BImg[i] = gamma(bImg[i] / 255.0f);
			GImg[i] = gamma(gImg[i] / 255.0f);
			RImg[i] = gamma(rImg[i] / 255.0f);

			XImg[i] = 0.436052025 * RImg[i] + 0.385081593 * GImg[i] + 0.143087414 * BImg[i];
			YImg[i] = 0.222491598 * RImg[i] + 0.716886060 * GImg[i] + 0.060621486 * BImg[i];
			ZImg[i] = 0.013929122 * RImg[i] + 0.097097002 * GImg[i] + 0.714185470 * BImg[i];

			FXImg[i] = XImg[i] > 0.008856f ? Math.pow(XImg[i], 1.0f / 3.0f) : (7.787f * XImg[i] + 0.137931f);
			FYImg[i] = YImg[i] > 0.008856f ? Math.pow(YImg[i], 1.0f / 3.0f) : (7.787f * YImg[i] + 0.137931f);
			FZImg[i] = ZImg[i] > 0.008856f ? Math.pow(ZImg[i], 1.0f / 3.0f) : (7.787f * ZImg[i] + 0.137931f);

			lImg[i] = YImg[i] > 0.008856f ? (116.0f * FYImg[i] - 16.0f) : (903.3f * YImg[i]);
			aImg[i] = 500.f * (FXImg[i] - FYImg[i]);
			lbImg[i] = 200.f * (FYImg[i] - FZImg[i]);
		}
	}
}
