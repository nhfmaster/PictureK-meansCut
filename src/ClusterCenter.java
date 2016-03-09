/**
 * @author nhfmaster
 */
public class ClusterCenter { // 聚类中心
	private int row;
	private int col;
	private int[] rgb;
	private int index;
	private int numOfPixels;

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
	 * @param cValue
	 *            像素数组
	 */
	public void setRGB(int[] cValue) {
		this.rgb = cValue;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getNumOfPixels() {
		return numOfPixels;
	}

	public void setNumOfPixels(int numOfPixels) {
		this.numOfPixels = numOfPixels;
	}

	public void addNumOfPixel() {
		numOfPixels++;
	}
}
