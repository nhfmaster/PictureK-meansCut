import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author nhfmaster
 */
public class Test {
	public static void main(String args[]) throws IOException {
		KMeansAlgo km = new KMeansAlgo(3);
		File srcFile = new File("D:\\a.jpg");
		File desFile = new File("D:\\e.jpg");
		BufferedImage src = ImageIO.read(srcFile);
		ColorModel desCM = src.getColorModel();
		BufferedImage des = new BufferedImage(desCM,
				desCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()), desCM.isAlphaPremultiplied(),
				null);
		ImageIO.write(km.filter(src, des), "jpg", desFile);
	}
}
