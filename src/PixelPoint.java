/**
 * @author nhfmaster
 */
public class PixelPoint {
	private int row;
	private int col;
	private float[] rgb;
	private int lable;

	/**
	 * PixelPoint���췽��
	 * 
	 * @param row
	 *            ������
	 * @param col
	 *            ������
	 * @param pixel
	 *            ������Ϣ
	 */
	public PixelPoint(int row, int col, int pixel) {
		this.col = col;
		this.row = row;
	}

	/**
	 * PixelPoint���췽��
	 * 
	 * @param row
	 *            ������
	 * @param col
	 *            ������
	 */
	public PixelPoint(int row, int col) {
		this.row = row;
		this.col = col;
	}

	/**
	 * ��ú�����
	 * 
	 * @return row ������
	 */
	public int getRow() {
		return row;
	}

	/**
	 * ���ú�����
	 * 
	 * @param row
	 *            ������
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * ���������
	 * 
	 * @return col ������
	 */
	public int getCol() {
		return col;
	}

	/**
	 * ����������
	 * 
	 * @param col
	 *            ������
	 */
	public void setCol(int col) {
		this.col = col;
	}

	/**
	 * ���rgbɫ������
	 * 
	 * @return float[] rgb rgbɫ������
	 */
	public float[] getRGB() {
		return rgb;
	}

	/**
	 * ����rgbɫ������
	 * 
	 * @param value
	 *            int[] ɫ������
	 */
	public void setRGB(float[] value) {
		this.rgb = value;
	}

	/**
	 * 
	 * @return
	 */
	public int getLable() {
		return lable;
	}

	/**
	 * 
	 * @param lable
	 */
	public void setLable(int lable) {
		this.lable = lable;
	}

}
