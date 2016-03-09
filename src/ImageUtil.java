import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

/**
 * @author nhfmaster
 */
public class ImageUtil {
	/**
	 * 构造新的BufferedImage空图像用来填充
	 * 
	 * @param src
	 *            BufferedImage类型 输入图像信息
	 * @param dstCM
	 *            ColorModel类型 色彩空间模型
	 * @return BufferedImage类型的图像信息
	 */
	public static BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel dstCM) {
		if (dstCM == null)
			dstCM = src.getColorModel();
		// 不用其他的构造方法是因为无法确定输出的图像色彩空间类型
		return new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()),
				dstCM.isAlphaPremultiplied(), null); // isAlphaPremultiplied方法用于判断
														// ColorModel
														// 转换的像素值中是否预乘
														// alpha。预乘
														// alpha是为了对边缘进行锯齿处理
														// ，使边缘像素变暗
	}

	/**
	 * 为图像设置RGB色彩内容
	 * 
	 * @param image
	 *            BufferImage类型 图像信息
	 * @param x
	 *            图像宽度起始点
	 * @param y
	 *            图像高度起始点
	 * @param width
	 *            图像宽度
	 * @param height
	 *            图像高度
	 * @param pixels
	 *            图像像素数组
	 */
	public static void setRGB(BufferedImage image, int x, int y, int width, int height, int[] pixels) {
		int type = image.getType(); // 得到图像的色彩信息
		if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB)
			image.getRaster().setDataElements(x, y, width, height, pixels); // 为类型
																			// TransferType
																			// 基本数组中的像素矩形设置数据
		else
			image.setRGB(x, y, width, height, pixels, 0, width);
	}
}
