import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author nhfmaster
 */
public class KMeans {
	private List<ClusterCenter> clusterCenterList; // ÿ�ξ������ĵ�List�б�
	private List<PixelPoint> pointList; // ���ص�List�б�
	private int numOfCluster; // �������������Ϊ���ࣩ

	/**
	 * @param clusters
	 *            �������������Ϊ���ࣩ
	 */
	public KMeans(int clusters) {
		clusterCenterList = new ArrayList<ClusterCenter>();
		pointList = new ArrayList<PixelPoint>();
		numOfCluster = clusters;
	}

	/**
	 * @return numOfCluster �������������Ϊ���ࣩ
	 */
	public int getNumOfCluster() {
		return numOfCluster;
	}

	/**
	 * ���þ��������
	 * 
	 * @param numOfCluster
	 *            ���������
	 */
	public void setNumOfCluster(int numOfCluster) {
		this.numOfCluster = numOfCluster;
	}

	/**
	 * kmeans�����з���
	 * 
	 * @param src
	 *            BufferedImage���� ����ͼ��
	 * @param dest
	 *            BufferedImage���� ���ͼ��(����Ϊ��ͼ��)
	 * @return BufferedImage���� ����kmeans������ͼ��
	 */
	public BufferedImage filter(BufferedImage src, BufferedImage dest) {
		// ��ʼ����������
		int width = src.getWidth();
		int height = src.getHeight();
		int[] inPixels = new int[width * height]; // ����Ϊ������ͼ��ͬ����С����������
		src.getRGB(0, 0, width, height, inPixels, 0, width);// ��ͼ���RGB��Ϣ����inPixels��
		int index = 0;

		// ������������ص�Ϊ��ʼ�ľ�������
		Random random = new Random();
		for (int i = 0; i < numOfCluster; i++) {
			int randomwidth = random.nextInt(width);
			int randomHeight = random.nextInt(height);
			index = randomHeight * width + randomwidth; // ������õ�index
			ClusterCenter clusterCenter = new ClusterCenter(randomwidth, randomHeight); // ��þ������ĵĺ������������
			int argb = inPixels[i]; //
			int red = (argb >> 16) & 0xff; // ������ص��red
			int green = (argb >> 8) & 0xff; // ������ص��green
			int blue = (argb) & 0xff;// ������ص��blue
			clusterCenter.setRGB(new int[] { red, green, blue });
			clusterCenter.setIndex(i);
			clusterCenterList.add(clusterCenter);
		}

		// �����е����ص����pointList�����ھ���
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				index = i * width + j;
				int color = inPixels[index];
				PixelPoint pixelPoint = new PixelPoint(i, j);
				int red = (color >> 16) & 0xff;
				int green = (color >> 8) & 0xff;
				int blue = (color) & 0xff;
				pixelPoint.setRGB(new float[] { red, green, blue });
				pixelPoint.setLable(-1); // ����ֻ������ 0 1 2����˳�ʼ��Ϊ-1
				pointList.add(pixelPoint);
			}
		}

		// Ϊÿ�����ص��ʼ��һ����������
		double[] clusterDisValues = new double[clusterCenterList.size()];
		for (int i = 0; i < pointList.size(); i++) {
			for (int j = 0; j < clusterCenterList.size(); j++) {
				clusterDisValues[j] = calculateEuclideanDistance(clusterCenterList.get(j), pointList.get(i)); // ���ÿ�������������ĵ�ŷ�Ͼ���
			}
			pointList.get(i).setLable(getCloserCluster(clusterDisValues)); // �õ�ÿ������������һ����������
		}

		// ����ǰһ���뱾�ξ��������Ƿ�һ�£���һ����һֱ�ظ����࣬һ����ֹͣ����
		double[] oldClusterCenterColors = reCalculateClusterCenters();
		while (true) {
			stepClusters();
			double[] newClusterCenterColors = reCalculateClusterCenters();
			if (isStop(oldClusterCenterColors, newClusterCenterColors)) {
				break;
			} else {
				oldClusterCenterColors = newClusterCenterColors;
			}
		}

		// �õ����վ�����ͼ��
		dest = ImageUtil.createCompatibleDestImage(src, null);
		index = 0;
		int[] outPixels = new int[width * height];
		for (int j = 0; j < pointList.size(); j++) {
			for (int i = 0; i < clusterCenterList.size(); i++) {
				PixelPoint p = this.pointList.get(j);
				if (clusterCenterList.get(i).getIndex() == p.getLable()) {
					int row = p.getRow();
					int col = p.getCol();
					index = row * width + col;
					int[] rgb = clusterCenterList.get(i).getRGB();
					outPixels[index] = (0xff << 24) | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
				}
			}
		}

		// �����������
		ImageUtil.setRGB(dest, 0, 0, width, height, outPixels);
		return dest;
	}

	/**
	 * �ж�kmeans�����Ƿ�ֹͣ
	 * 
	 * @param oldClusterCenterColors
	 *            ǰһ�εľ�������
	 * @param newClusterCenterColors
	 *            ����һ�εľ�������
	 * @return boolean�� ֹͣ�˷���true ûֹͣ����false
	 */
	private boolean isStop(double[] oldClusterCenterColors, double[] newClusterCenterColors) {
		for (int i = 0; i < oldClusterCenterColors.length; i++) {
			System.out.println(
					"cluster " + i + " old : " + oldClusterCenterColors[i] + ", new : " + newClusterCenterColors[i]);
			if (oldClusterCenterColors[i] != newClusterCenterColors[i]) {
				return false;
			}
		}
		System.out.println();
		return true;
	}

	/**
	 * ����ŷ�Ͼ�����¾�������
	 */
	private void stepClusters() {
		double[] clusterDisValues = new double[clusterCenterList.size()];
		for (int i = 0; i < pointList.size(); i++) {
			for (int j = 0; j < clusterCenterList.size(); j++) {
				clusterDisValues[j] = calculateEuclideanDistance(clusterCenterList.get(j), pointList.get(i)); // ����ÿ�����ص����������ĵ�ŷ�Ͼ���
			}
			pointList.get(i).setLable(getCloserCluster(clusterDisValues)); // ѡȡ����ľ�������
		}

	}

	/**
	 * ����ǰһ����õĸ�����������¼����µľ������ģ�Ȼ�����
	 * 
	 * @return
	 */
	private double[] reCalculateClusterCenters() {

		// ��clusterCenterList�е�ÿ���������Ķ�Ӧ�����ص�����Ϊ0
		for (int i = 0; i < clusterCenterList.size(); i++) {
			clusterCenterList.get(i).setNumOfPixels(0);
		}

		// recalculate the sum and total of points for each cluster
		// ���¼���ÿ����������ص������
		double[] redSums = new double[3];
		double[] greenSum = new double[3];
		double[] blueSum = new double[3];
		for (int i = 0; i < pointList.size(); i++) {
			int cIndex = (int) pointList.get(i).getLable();
			clusterCenterList.get(cIndex).addNumOfPixel();
			int tr = (int) pointList.get(i).getRGB()[0];
			int tg = (int) pointList.get(i).getRGB()[1];
			int tb = (int) pointList.get(i).getRGB()[2];
			redSums[cIndex] += tr;
			greenSum[cIndex] += tg;
			blueSum[cIndex] += tb;
		}

		double[] oldClusterCentersColors = new double[clusterCenterList.size()];
		for (int i = 0; i < clusterCenterList.size(); i++) {
			double sum = clusterCenterList.get(i).getNumOfPixels();
			int cIndex = clusterCenterList.get(i).getIndex();
			int red = (int) (greenSum[cIndex] / sum);
			int green = (int) (greenSum[cIndex] / sum);
			int blue = (int) (blueSum[cIndex] / sum);
			System.out.println("red = " + red + " green = " + green + " blue = " + blue);
			int clusterColor = (255 << 24) | (red << 16) | (green << 8) | blue;
			clusterCenterList.get(i).setRGB(new int[] { red, green, blue });
			oldClusterCentersColors[i] = clusterColor;
		}

		return oldClusterCentersColors;
	}

	/**
	 * ����µľ������ģ�ʹ��ͬ������ص����������ĸ�����
	 * 
	 * @param EuclideanDistance
	 *            double[] ÿ�����ص����������ĵ�ŷ�Ͼ���
	 * @return clusterIndex ŷʽ������С�����index
	 */
	private int getCloserCluster(double[] EuclideanDistance) {
		double min = EuclideanDistance[0];
		int clusterIndex = 0;
		for (int i = 0; i < EuclideanDistance.length; i++) {
			if (min > EuclideanDistance[i]) {
				min = EuclideanDistance[i];
				clusterIndex = i;
			}
		}
		return clusterIndex;
	}

	/**
	 * �������ص����������ĵ�ŷʽ����
	 * 
	 * @param cluster
	 *            ��������
	 * @param pixel
	 *            ���ص�
	 * @return double���͵�ŷ�Ͼ���
	 */
	private double calculateEuclideanDistance(ClusterCenter cluster, PixelPoint pixel) {
		// ��������
		int clusterR = (int) cluster.getRGB()[0];
		int clusterG = (int) cluster.getRGB()[1];
		int clusterB = (int) cluster.getRGB()[2];
		// ÿ�����ص�
		int pixelR = (int) pixel.getRGB()[0];
		int pixelG = (int) pixel.getRGB()[1];
		int pixelB = (int) pixel.getRGB()[2];

		return Math.sqrt(Math.pow((pixelR - clusterR), 2.0) + Math.pow((pixelG - clusterG), 2.0)
				+ Math.pow((pixelB - clusterB), 2.0));
	}

}
