/**
 * @author nhfmaster
 */
public class ClusterCenter { // ��������
	private int row;
	private int col;
	private int[] rgb;
	private int index;
	private int numOfPixels;

	/**
	 * �����������
	 * 
	 * @param row
	 *            ������
	 * @param col
	 *            ������
	 */
	public ClusterCenter(int row, int col) {
		this.row = row;
		this.col = col;
		this.index = -1;
		this.numOfPixels = 0;
	}

	/**
	 * @return row ������
	 */
	public int getRow() {
		return row;
	}

	/**
	 * @param row
	 *            ������
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * @return col ������
	 */
	public int getCol() {
		return col;
	}

	/**
	 * @param col
	 *            ������
	 */
	public void setCol(int col) {
		this.col = col;
	}

	/**
	 * @return int[]rgb rgb��������
	 */
	public int[] getRGB() {
		return rgb;
	}

	/**
	 * @param rgb
	 *            ��������
	 */
	public void setRGB(int[] rgb) {
		this.rgb = rgb;
	}

	/**
	 * �õ��������ĵ�Index���ڼ����������ģ�
	 * 
	 * @return index �ڼ�����������
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * ���þ������ĵ�Index���ڼ����������ģ�
	 * 
	 * @param index
	 *            �ڼ�����������
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * �õ��þ�������������������ص���
	 * 
	 * @return numOfPixels �þ�������������������ص���
	 */
	public int getNumOfPixels() {
		return numOfPixels;
	}

	/**
	 * ���øþ�������������������ص�����������������ճ�ʼ����
	 * 
	 * @param numOfPixels
	 *            �þ�������������������ص���
	 */
	public void setNumOfPixels(int numOfPixels) {
		this.numOfPixels = numOfPixels;
	}

	/**
	 * ���Ӽ���þ�������������������ص���
	 */
	public void addNumOfPixel() {
		numOfPixels++;
	}
}
