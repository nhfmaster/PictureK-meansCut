/**
 * @author nhfmaster
 */
public class PixelPoint {
	private int row;
	private int col;
	private float[] rgb;
	private int clusterCenterIndex;

	/**
	 * PixelPoint构造方法
	 * 
	 * @param row
	 *            横坐标
	 * @param col
	 *            纵坐标
	 * @param pixel
	 *            像素信息
	 */
	public PixelPoint(int row, int col, int pixel) {
		this.col = col;
		this.row = row;
	}

	/**
	 * PixelPoint构造方法
	 * 
	 * @param row
	 *            横坐标
	 * @param col
	 *            纵坐标
	 */
	public PixelPoint(int row, int col) {
		this.row = row;
		this.col = col;
	}

	/**
	 * 获得横坐标
	 * 
	 * @return row 横坐标
	 */
	public int getRow() {
		return row;
	}

	/**
	 * 设置横坐标
	 * 
	 * @param row
	 *            横坐标
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * 获得纵坐标
	 * 
	 * @return col 纵坐标
	 */
	public int getCol() {
		return col;
	}

	/**
	 * 设置纵坐标
	 * 
	 * @param col
	 *            纵坐标
	 */
	public void setCol(int col) {
		this.col = col;
	}

	/**
	 * 获得rgb色彩数组
	 * 
	 * @return float[] rgb rgb色彩数组
	 */
	public float[] getRGB() {
		return rgb;
	}

	/**
	 * 设置rgb色彩数组
	 * 
	 * @param value
	 *            int[] 色彩数组
	 */
	public void setRGB(float[] value) {
		this.rgb = value;
	}

	/**
	 * 得到该聚类中心是第几类
	 * 
	 * @return clusterCenterIndex 聚类中心的类别号
	 */
	public int getClusterCenterIndex() {
		return clusterCenterIndex;
	}

	/**
	 * 设置聚类中心类别号
	 * 
	 * @param clusterCenterIndex
	 *            聚类中心类别号
	 */
	public void setClusterCenterIndex(int clusterCenterIndex) {
		this.clusterCenterIndex = clusterCenterIndex;
	}

}
