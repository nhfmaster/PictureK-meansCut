import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author nhfmaster
 */
public class KMeans {
	private List<ClusterCenter> clusterCenterList; // 每次聚类中心的List列表
	private List<PixelPoint> pointList; // 像素点List列表
	private int numOfCluster; // 聚类的数量（分为几类）

	/**
	 * @param clusters
	 *            聚类的数量（分为几类）
	 */
	public KMeans(int clusters) {
		clusterCenterList = new ArrayList<ClusterCenter>();
		pointList = new ArrayList<PixelPoint>();
		numOfCluster = clusters;
	}

	/**
	 * @return numOfCluster 聚类的数量（分为几类）
	 */
	public int getNumOfCluster() {
		return numOfCluster;
	}

	/**
	 * 设置聚类的数量
	 * 
	 * @param numOfCluster
	 *            聚类的数量
	 */
	public void setNumOfCluster(int numOfCluster) {
		this.numOfCluster = numOfCluster;
	}

	/**
	 * kmeans的运行方法
	 * 
	 * @param src
	 *            BufferedImage类型 输入图像
	 * @param dest
	 *            BufferedImage类型 输出图像(设置为空图像)
	 * @return BufferedImage类型 运行kmeans后的输出图像
	 */
	public ArrayList<BufferedImage> filter(BufferedImage src, ArrayList<BufferedImage> des) {
		// 初始化像素数据
		int width = src.getWidth();
		int height = src.getHeight();
		int[] inPixels = new int[width * height]; // 设置为与输入图像同样大小的像素数组
		src.getRGB(0, 0, width, height, inPixels, 0, width);// 将图像的RGB信息存入inPixels中
		int index = 0;

		// 建立随机的像素点为初始的聚类中心
		Random random = new Random();
		for (int i = 0; i < numOfCluster; i++) {
			int randomwidth = random.nextInt(width);
			int randomHeight = random.nextInt(height);
			index = randomHeight * width + randomwidth; // 随机设置的index
			ClusterCenter clusterCenter = new ClusterCenter(randomwidth, randomHeight); // 获得聚类中心的横坐标和纵坐标
			int rgb = inPixels[i]; //
			int red = (rgb >> 16) & 0xff; // 获得像素点的red
			int green = (rgb >> 8) & 0xff; // 获得像素点的green
			int blue = (rgb) & 0xff;// 获得像素点的blue
			clusterCenter.setRGB(new int[] { red, green, blue });
			clusterCenter.setIndex(i);
			clusterCenterList.add(clusterCenter);
		}

		// 将所有的像素点存入pointList中用于聚类
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				index = i * width + j; // 根据像素点横纵坐标计算数组下标
				int color = inPixels[index];
				PixelPoint pixelPoint = new PixelPoint(i, j);
				int red = (color >> 16) & 0xff;
				int green = (color >> 8) & 0xff;
				int blue = (color) & 0xff;
				pixelPoint.setRGB(new float[] { red, green, blue });
				pixelPoint.setClusterCenterIndex(-1); // 由于只有三类 0 1 2，因此初始化为-1
				pointList.add(pixelPoint);
			}
		}

		// 为每个像素点初始化一个聚类中心
		double[] clusterDisValues = new double[clusterCenterList.size()];
		for (int i = 0; i < pointList.size(); i++) {
			for (int j = 0; j < clusterCenterList.size(); j++) {
				clusterDisValues[j] = calculateEuclideanDistance(clusterCenterList.get(j), pointList.get(i)); // 获得每个点距离聚类中心的欧氏距离
			}
			pointList.get(i).setClusterCenterIndex(getCloserCluster(clusterDisValues)); // 得到每个点距离最近的一个聚类中心
		}

		// 计算前一次与本次聚类中心是否一致，不一致则一直重复聚类，一致则停止聚类
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

		/*
		 * for (int j = 0; j < pointList.size(); j++) { for (int i = 0; i <
		 * clusterCenterList.size(); i++) { PixelPoint p =
		 * this.pointList.get(j); if (clusterCenterList.get(i).getIndex() ==
		 * p.getClusterCenterIndex()) { int row = p.getRow(); int col =
		 * p.getCol(); index = row * width + col; int[] rgb =
		 * clusterCenterList.get(i).getRGB(); outPixels[index] = (0xff << 24) |
		 * (rgb[0] << 16) | (rgb[1] << 8) | rgb[2]; } } }
		 */

		// 得到最终聚类后的图像
		BufferedImage cluster1 = ImageUtil.createCompatibleDestImage(src, null);
		BufferedImage cluster2 = ImageUtil.createCompatibleDestImage(src, null);
		BufferedImage cluster3 = ImageUtil.createCompatibleDestImage(src, null);

		index = 0;
		int[] cluster1OutPixels = new int[width * height];
		for (int i = 0; i < pointList.size(); i++) {
			if (pointList.get(i).getClusterCenterIndex() == 0) {
				int row = pointList.get(i).getRow();
				int col = pointList.get(i).getCol();
				index = row * width + col;
				int[] rgb = clusterCenterList.get(1).getRGB();
				cluster1OutPixels[index] = (0xff << 24) | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
			}
		}
		for (int i = 0; i < cluster1OutPixels.length; i++) {
			if (cluster1OutPixels[i] == 0) {
				cluster1OutPixels[i] = -1;
			}
		}

		int[] cluster2OutPixels = new int[width * height];
		for (int i = 0; i < pointList.size(); i++) {
			if (pointList.get(i).getClusterCenterIndex() == 1) {
				int row = pointList.get(i).getRow();
				int col = pointList.get(i).getCol();
				index = row * width + col;
				int[] rgb = clusterCenterList.get(1).getRGB();
				cluster2OutPixels[index] = (0xff << 24) | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
			}
		}
		for (int i = 0; i < cluster2OutPixels.length; i++) {
			if (cluster2OutPixels[i] == 0) {
				cluster2OutPixels[i] = -1;
			}
		}

		int[] cluster3OutPixels = new int[width * height];
		for (int i = 0; i < pointList.size(); i++) {
			if (pointList.get(i).getClusterCenterIndex() == 2) {
				int row = pointList.get(i).getRow();
				int col = pointList.get(i).getCol();
				index = row * width + col;
				int[] rgb = clusterCenterList.get(1).getRGB();
				cluster3OutPixels[index] = (0xff << 24) | (rgb[0] << 16) | (rgb[1] << 8) | rgb[2];
			}
		}
		for (int i = 0; i < cluster3OutPixels.length; i++) {
			if (cluster3OutPixels[i] == 0) {
				cluster3OutPixels[i] = -1;
			}
		}

		ImageUtil.setRGB(cluster1, 0, 0, width, height, cluster1OutPixels);
		ImageUtil.setRGB(cluster2, 0, 0, width, height, cluster2OutPixels);
		ImageUtil.setRGB(cluster3, 0, 0, width, height, cluster3OutPixels);

		des.add(0, cluster1);
		des.add(1, cluster2);
		des.add(2, cluster3);
		return des;
	}

	/**
	 * 判断kmeans聚类是否停止
	 * 
	 * @param oldClusterCenterColors
	 *            前一次的聚类中心
	 * @param newClusterCenterColors
	 *            最新一次的聚类中心
	 * @return boolean型 停止了返回true 没停止返回false
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
	 * 根据欧氏距离更新聚类中心
	 */
	private void stepClusters() {
		double[] clusterDisValues = new double[clusterCenterList.size()];
		for (int i = 0; i < pointList.size(); i++) {
			for (int j = 0; j < clusterCenterList.size(); j++) {
				clusterDisValues[j] = calculateEuclideanDistance(clusterCenterList.get(j), pointList.get(i)); // 计算每个像素点距离聚类中心的欧氏距离
			}
			pointList.get(i).setClusterCenterIndex(getCloserCluster(clusterDisValues)); // 选取最近的聚类中心
		}

	}

	/**
	 * 利用前一次算好的各类的像素重新计算新的聚类中心，然后更新
	 * 
	 * @return
	 */
	private double[] reCalculateClusterCenters() {

		// 将clusterCenterList中的每个聚类中心对应的像素点数清为0
		for (int i = 0; i < clusterCenterList.size(); i++) {
			clusterCenterList.get(i).setNumOfPixels(0);
		}

		// 重新计算每个类族的像素点的总数
		double[] redSum = new double[3];
		double[] greenSum = new double[3];
		double[] blueSum = new double[3];
		for (int i = 0; i < pointList.size(); i++) {
			int clusterIndex = (int) pointList.get(i).getClusterCenterIndex();// 得到前一次计算的每个像素点对应的聚类中心index
			clusterCenterList.get(clusterIndex).addNumOfPixel(); // 增加对应类的总像素点数
			int tempRed = (int) pointList.get(i).getRGB()[0]; // 获得该像素点的red
			int tempGreen = (int) pointList.get(i).getRGB()[1];// 获得该像素点的green
			int tempBlue = (int) pointList.get(i).getRGB()[2];// 获得该像素点的blue
			redSum[clusterIndex] += tempRed;
			greenSum[clusterIndex] += tempGreen;
			blueSum[clusterIndex] += tempBlue;
		}

		double[] clusterCentersColors = new double[clusterCenterList.size()];
		for (int i = 0; i < clusterCenterList.size(); i++) {
			double sum = clusterCenterList.get(i).getNumOfPixels();
			int clusterIndex = clusterCenterList.get(i).getIndex();
			int red = (int) (redSum[clusterIndex] / sum); // 计算该类所有像素点red值的平均值
			int green = (int) (greenSum[clusterIndex] / sum);// 计算该类所有像素点green值的平均值
			int blue = (int) (blueSum[clusterIndex] / sum);// 计算该类所有像素点blue值的平均值
			System.out.println("red = " + red + " green = " + green + " blue = " + blue);
			int clusterColor = (255 << 24) | (red << 16) | (green << 8) | blue;
			System.out.println("clusterColor=" + clusterColor);
			clusterCenterList.get(i).setRGB(new int[] { red, green, blue });
			clusterCentersColors[i] = clusterColor;
		}

		return clusterCentersColors;
	}

	/**
	 * 获得新的聚类中心（使得同类的像素点距离聚类中心更近）
	 * 
	 * @param EuclideanDistance
	 *            double[] 每个像素点距离聚类中心的欧氏距离
	 * @return clusterIndex 欧式距离最小的族的index
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
	 * 计算像素点距离聚类中心的欧式距离
	 * 
	 * @param cluster
	 *            聚类中心
	 * @param pixel
	 *            像素点
	 * @return double类型的欧氏距离
	 */
	private double calculateEuclideanDistance(ClusterCenter cluster, PixelPoint pixel) {
		// 聚类中心
		int clusterRed = (int) cluster.getRGB()[0];
		int clusterGreen = (int) cluster.getRGB()[1];
		int clusterBlue = (int) cluster.getRGB()[2];
		// 每个像素点
		int pixelRed = (int) pixel.getRGB()[0];
		int pixelGreen = (int) pixel.getRGB()[1];
		int pixelBlue = (int) pixel.getRGB()[2];

		return Math.sqrt(Math.pow((pixelRed - clusterRed), 2.0) + Math.pow((pixelGreen - clusterGreen), 2.0)
				+ Math.pow((pixelBlue - clusterBlue), 2.0));
	}

}
