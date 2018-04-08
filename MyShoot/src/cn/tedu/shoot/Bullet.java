package cn.tedu.shoot;

/** �ӵ�: �Ƿ����� */
public class Bullet extends FlyingObject {
	private int speed = 3; //�ƶ��ٶ�
	/** ���췽��  x,y:����Ӣ�ۻ������� */
	public Bullet(int x,int y){
		image = ShootGame.bullet;   //ͼƬ
		width = image.getWidth();	//��
		height = image.getHeight(); //��
		this.x = x; //����Ӣ�ۻ���x
		this.y = y; //����Ӣ�ۻ���y
	}
	
	/** ��дstep()��һ�� */
	public void step(){
		y-=speed; //y-(����)
	}
	
	/** ��дoutOfBounds()Խ���� */
	public boolean outOfBounds(){
		return this.y<=-this.height; //�ӵ���y<=�����ӵ��ĸߣ���ΪԽ����
	}
	
}












