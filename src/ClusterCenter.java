/**
 * @author nhfmaster
 */
public class ClusterCenter { // 聚类中心
	private int row;
	private int col;
	private int[] rgb;
	private int index;
	private int numOfPixels;

	/**
	 * 构造聚类中心
	 * 
	 * @param row
	 *            横坐标
	 * @param col
	 *            纵坐标
	 */
	public ClusterCenter(int row, int col) {
		this.row = row;
		this.col = col;
		this.index = -1;
		this.numOfPixels = 0;
	}

	/**
	 * @return row 横坐标
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @param row
	 *            横坐标
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * @return col 纵坐标
	 */
	public int getCol() {
		return col;
	}

	/**
	 * @param col
	 *            纵坐标
	 */
	public void setCol(int col) {
		this.col = col;
	}

	/**
	 * @return int[]rgb rgb像素数组
	 */
	public int[] getRGB() {
		return rgb;
	}

	/**
	 * @param rgb
	 *            像素数组
	 */
	public void setRGB(int[] rgb) {
		this.rgb = rgb;
	}

	/**
	 * 得到聚类中心的Index（第几个聚类中心）
	 * 
	 * @return index 第几个聚类中心
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * 设置聚类中心的Index（第几个聚类中心）
	 * 
	 * @param index
	 *            第几个聚类中心
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * 得到该聚类中心所在类的总像素点数
	 * 
	 * @return numOfPixels 该聚类中心所在类的总像素点数
	 */
	public int getNumOfPixels() {
		return numOfPixels;
	}

	/**
	 * 设置该聚类中心所在类的总像素点数（本程序用于清空初始化）
	 * 
	 * @param numOfPixels
	 *            该聚类中心所在类的总像素点数
	 */
	public void setNumOfPixels(int numOfPixels) {
		this.numOfPixels = numOfPixels;
	}

	/**
	 * 增加计算该聚类中心所在类的总像素点数
	 */
	public void addNumOfPixel() {
		numOfPixels++;
	}
}
