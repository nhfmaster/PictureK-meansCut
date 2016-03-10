import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

/**
 * @author nhfmaster
 */
public class Test {
	public static void main(String args[]) throws IOException {
		ArrayList<BufferedImage> des = new ArrayList<BufferedImage>();
		KMeans km = new KMeans(3);
		File srcFile = new File("D:\\4.jpg");
		File desFile1 = new File("D:\\e.jpg");
		File desFile2 = new File("D:\\f.jpg");
		File desFile3 = new File("D:\\g.jpg");
		BufferedImage src = ImageIO.read(srcFile);
		ColorModel desCM = src.getColorModel();
		BufferedImage des1 = new BufferedImage(desCM,
				desCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()), desCM.isAlphaPremultiplied(),
				null);
		BufferedImage des2 = new BufferedImage(desCM,
				desCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()), desCM.isAlphaPremultiplied(),
				null);
		BufferedImage des3 = new BufferedImage(desCM,
				desCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()), desCM.isAlphaPremultiplied(),
				null);
		des.add(0, des1);
		des.add(1, des2);
		des.add(2, des3);
		ArrayList<BufferedImage> result = new ArrayList<BufferedImage>();
		result = km.filter(src, des);
		ImageIO.write(result.get(0), "jpg", desFile1);
		ImageIO.write(result.get(1), "jpg", desFile2);
		ImageIO.write(result.get(2), "jpg", desFile3);
	}
}
