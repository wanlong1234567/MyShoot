package cn.tedu.shoot;
import java.util.Random;

/** 飞机大战的总的流程集合类 */
public class Airplane extends FlyingObject implements Enemy {
	private int speed = 2; //�ƶ��ٶ�
	/** ���췽�� */
	public Airplane(){
		image = ShootGame.airplane; //ͼƬ
		width = image.getWidth();	//��
		height = image.getHeight(); //��
		Random rand = new Random(); //���������
		x = rand.nextInt(ShootGame.WIDTH-this.width); //x:0��(���ڿ�-�л���)֮�ڵ������
		y = -this.height; //y:���ĵл��ĸ�
	}
	
	/** ��дgetScore()�÷� */
	public int getScore(){
		return 5; //���һ���л���5��
	}
	
	/** ��дstep()��һ�� */
	public void step(){
		y+=speed; //y+(����)
	}
	
	/** ��дoutOfBounds()Խ���� */
	public boolean outOfBounds(){
		return this.y>=ShootGame.HEIGHT; //�л���y>=���ڵĸߣ���ΪԽ����
	}
	
}














