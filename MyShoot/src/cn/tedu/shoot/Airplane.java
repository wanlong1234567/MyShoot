package cn.tedu.shoot;
import java.util.Random;

/** 小敌机: 是飞行物，也是敌人 */
public class Airplane extends FlyingObject implements Enemy {
	private int speed = 2; //移动速度
	/** 构造方法 */
	public Airplane(){
		image = ShootGame.airplane; //图片
		width = image.getWidth();	//宽
		height = image.getHeight(); //高
		Random rand = new Random(); //随机数对象
		x = rand.nextInt(ShootGame.WIDTH-this.width); //x:0到(窗口宽-敌机宽)之内的随机数
		y = -this.height; //y:负的敌机的高
	}
	
	/** 重写getScore()得分 */
	public int getScore(){
		return 5; //打掉一个敌机得5分
	}
	
	/** 重写step()走一步 */
	public void step(){
		y+=speed; //y+(向下)
	}
	
	/** 重写outOfBounds()越界检查 */
	public boolean outOfBounds(){
		return this.y>=ShootGame.HEIGHT; //敌机的y>=窗口的高，即为越界了
	}
	
}














