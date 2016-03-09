import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

/**
 * @author nhfmaster
 */
public class ImageUtil {
	/**
	 * �����µ�BufferedImage��ͼ���������
	 * 
	 * @param src
	 *            BufferedImage���� ����ͼ����Ϣ
	 * @param dstCM
	 *            ColorModel���� ɫ�ʿռ�ģ��
	 * @return BufferedImage���͵�ͼ����Ϣ
	 */
	public static BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel dstCM) {
		if (dstCM == null)
			dstCM = src.getColorModel();
		// ���������Ĺ��췽������Ϊ�޷�ȷ�������ͼ��ɫ�ʿռ�����
		return new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()),
				dstCM.isAlphaPremultiplied(), null); // isAlphaPremultiplied���������ж�
														// ColorModel
														// ת��������ֵ���Ƿ�Ԥ��
														// alpha��Ԥ��
														// alpha��Ϊ�˶Ա�Ե���о�ݴ���
														// ��ʹ��Ե���ر䰵
	}

	/**
	 * Ϊͼ������RGBɫ������
	 * 
	 * @param image
	 *            BufferImage���� ͼ����Ϣ
	 * @param x
	 *            ͼ������ʼ��
	 * @param y
	 *            ͼ��߶���ʼ��
	 * @param width
	 *            ͼ����
	 * @param height
	 *            ͼ��߶�
	 * @param pixels
	 *            ͼ����������
	 */
	public static void setRGB(BufferedImage image, int x, int y, int width, int height, int[] pixels) {
		int type = image.getType(); // �õ�ͼ���ɫ����Ϣ
		if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB)
			image.getRaster().setDataElements(x, y, width, height, pixels); // Ϊ����
																			// TransferType
																			// ���������е����ؾ�����������
		else
			image.setRGB(x, y, width, height, pixels, 0, width);
	}
}
