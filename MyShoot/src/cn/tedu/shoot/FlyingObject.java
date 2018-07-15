package cn.tedu.shoot;
import java.awt.image.BufferedImage;
/**公共类 */
public abstract class FlyingObject {
	protected BufferedImage image; //ͼƬ
	protected int width;           //��
	protected int height;		   //��
	protected int x;			   //x����
	protected int y;			   //y����
	
	/** ��������һ�� */
	public abstract void step();
	
	/** ���������Ƿ�Խ�� */
	public abstract boolean outOfBounds();
	
	/** ���˱��ӵ���� this:����  b:�ӵ� */
	public boolean shootBy(Bullet b){
		int x1 = this.x;             //x1:���˵�x
		int x2 = this.x+this.width;  //x2:���˵�x+���˵Ŀ�
		int y1 = this.y;             //y1:���˵�y
		int y2 = this.y+this.height; //y2:���˵�y+���˵ĸ�
		int x = b.x;                 //x:�ӵ���x
		int y = b.y;                 //y:�ӵ���y
		
		return x>=x1 && x<=x2
			   &&
			   y>=y1 && y<=y2; //x��x1��x2֮�䣬���ң�y��y1��y2֮�䣬��Ϊײ����
	}
	
}














