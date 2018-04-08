package cn.tedu.shoot;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

/** �������� */
public class ShootGame extends JPanel {
	public static final int WIDTH = 400;  //���ڵĿ�
	public static final int HEIGHT = 654; //���ڵĸ�
	
	public static BufferedImage background; //����ͼ
	public static BufferedImage start;		//����ͼ
	public static BufferedImage pause;		//��ͣͼ
	public static BufferedImage gameover;	//��Ϸ����ͼ
	public static BufferedImage airplane;	//�л�
	public static BufferedImage bee;		//�۷�
	public static BufferedImage bullet;		//�ӵ�
	public static BufferedImage hero0;		//Ӣ�ۻ�0
	public static BufferedImage hero1;		//Ӣ�ۻ�1
	static{ //��ʼ����̬ͼƬ
		try{
			background = ImageIO.read(ShootGame.class.getResource("background.png"));
			start = ImageIO.read(ShootGame.class.getResource("start.png"));
			pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
			gameover = ImageIO.read(ShootGame.class.getResource("gameover.png"));
			airplane = ImageIO.read(ShootGame.class.getResource("airplane.png"));
			bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
			bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
			hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));
			hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static final int START = 0;     //����״̬
	public static final int RUNNING = 1;   //����״̬
	public static final int PAUSE = 2;     //��ͣ״̬
	public static final int GAME_OVER = 3; //��Ϸ����״̬
	private int state = START; //��ǰ״̬(Ĭ������״̬)
	
	private Hero hero = new Hero(); //1��Ӣ�ۻ�����
	private List<FlyingObject> flyings = new ArrayList<FlyingObject>(); //1�ѵ���
	private Bullet[] bullets = {}; //1���ӵ�
	
	/** ��дpaint()����  g:���� */
	public void paint(Graphics g){
		g.drawImage(background,0,0,null); //������ͼ
		paintHero(g); //��Ӣ�ۻ�����
		paintFlyingObjects(g); //������(�л�+С�۷�)����
		paintBullets(g); //���ӵ�����
		paintScoreAndLife(g); //���ֺͻ���
		paintState(g); //��״̬
	}
	/** ��Ӣ�ۻ����� */
	public void paintHero(Graphics g){
		g.drawImage(hero.image,hero.x,hero.y,null); //��Ӣ�ۻ�����
	}
	/** ������(�л�+С�۷�)���� */
	public void paintFlyingObjects(Graphics g){
		for(FlyingObject f:flyings){ //��������(�л�+С�۷�)����
			g.drawImage(f.image,f.x,f.y,null); //������(�л�+С�۷�)����
		}
	}
	/** ���ӵ����� */
	public void paintBullets(Graphics g){
		for(int i=0;i<bullets.length;i++){ //�����ӵ�����
			Bullet b = bullets[i]; //��ȡÿһ���ӵ�
			g.drawImage(b.image,b.x,b.y,null); //���ӵ�����
		}
	}
	/** ���ֺͻ��� */
	public void paintScoreAndLife(Graphics g){
		g.setColor(new Color(0xFF0000)); //������ɫ(����)
		g.setFont(new Font(Font.SANS_SERIF,Font.BOLD,24)); //������ʽ(����:SANS_SERIF ��ʽ:BOLD�Ӵ�  �ֺ�:24)
		g.drawString("SCORE: "+score,10,25); //����
		g.drawString("LIFE: "+hero.getLife(),10,45); //����
	}
	/** ��״̬ */
	public void paintState(Graphics g){
		switch(state){ //���ݵ�ǰ״̬����ͬ��ͼ
		case START: //����״̬ʱ������ͼ
			g.drawImage(start,0,0,null);
			break;
		case PAUSE: //��ͣ״̬ʱ����ͣͼ
			g.drawImage(pause,0,0,null);
			break;
		case GAME_OVER: //��Ϸ����״̬ʱ����Ϸ����ͼ
			g.drawImage(gameover,0,0,null);
			break;
		}
	}
	
	/** ���ɵ���(�л�+С�۷�)���� */
	public FlyingObject nextOne(){
		Random rand = new Random(); //���������
		int type = rand.nextInt(20); //0��19֮��������
		if(type<4){ //0��3ʱ������С�۷����
			return new Bee();
		}else{ //4��19ʱ�����ɵл�����
			return new Airplane();
		}
	}
	
	int flyIndex = 0; //�����볡����
	/** ����(�л�+С�۷�)�볡 */
	public void enterAction(){ //10������һ��
		flyIndex++; //ÿ10������1
		if(flyIndex%40==0){ //ÿ400(10*40)������һ��
			flyings.add(nextOne());//��ȡ���˶���
		}
	}
	
	/** ������(�л�+С�۷�+�ӵ�+Ӣ�ۻ�)��һ�� */
	public void stepAction(){ //10������һ��
		hero.step(); //Ӣ�ۻ���һ��
		for(FlyingObject f:flyings){ //�������е���
			f.step(); //������һ��
		}
		for(int i=0;i<bullets.length;i++){ //���������ӵ�
			bullets[i].step(); //�ӵ���һ��
		}
	}
	
	int shootIndex = 0; //�������
	/** �ӵ��볡(Ӣ�ۻ������ӵ�) */
	public void shootAction(){ //10������һ��
		shootIndex++; //ÿ10������1
		if(shootIndex%30==0){ //ÿ300(30*10)������һ��
			Bullet[] bs = hero.shoot(); //��ȡӢ�ۻ�������ӵ�
			bullets = Arrays.copyOf(bullets,bullets.length+bs.length); //����(bs�м���Ԫ�ؾ����󼸸�����)
			System.arraycopy(bs,0,bullets,bullets.length-bs.length,bs.length); //�����׷��
		}
	}
	
	/** ɾ��Խ��ķ�����(�л�+С�۷�+�ӵ�) */
	public void outOfBoundsAction(){ //10������һ��
		int index = 0; //1)��Խ����������±� 2)��Խ����˸���
		List<FlyingObject> flyingLives = new ArrayList<FlyingObject>(); //��Խ���������
		for(FlyingObject f:flyings){ //�������е���,��ȡÿһ������
			if(!f.outOfBounds()){ //��Խ��
				flyingLives.add(f); //����Խ����˶�����ӵ���Խ�����������
				index++; //1)��Խ����������±���һ 2)��Խ����˸�����һ
			}
		}
		flyings = flyingLives;
		
		index = 0; //1)��Խ���ӵ������±���� 2)��Խ���ӵ������������
		Bullet[] bulletLives = new Bullet[bullets.length]; //��Խ���ӵ�����
		for(int i=0;i<bullets.length;i++){ //���������ӵ�
			Bullet b = bullets[i]; //��ȡÿһ���ӵ�
			if(!b.outOfBounds()){ //��Խ��
				bulletLives[index] = b; //����Խ���ӵ�������ӵ���Խ���ӵ�������
				index++; //1)��Խ���ӵ������±���һ 2)��Խ���ӵ�������һ
			}
		}
		bullets = Arrays.copyOf(bulletLives,index); //����Խ���ӵ����鸴�Ƶ�bullets�У�index�м���bullets�ĳ��Ⱦ�Ϊ��
		
	}
	
	int score = 0; //��ҵ÷�
	/** �ӵ������(�л�+С�۷�)����ײ */
	public void bangAction(){ //10������һ��
		for(int i=0;i<bullets.length;i++){ //���������ӵ�
			Bullet b = bullets[i]; //��ȡÿһ���ӵ�
			Iterator<FlyingObject> it = flyings.iterator();
			while(it.hasNext()){ //�������е���
				FlyingObject f = it.next();//��ȡÿһ������
				if(f.shootBy(b)){ //ײ����
					if(f instanceof Enemy){ //����ײ����Ϊ����
						Enemy e = (Enemy)f; //�򽫱�ײ����ǿתΪ��������
						score += e.getScore(); //��ҵ÷�
					}
					if(f instanceof Award){ //����ײ����Ϊ����
						Award a = (Award)f; //�򽫱�ײ����ǿתΪ����
						int type = a.getType(); //��ȡ��������
						switch(type){
						case Award.DOUBLE_FIRE:
							hero.addDoubleFire();
							break;
						case Award.LIFE:
							hero.addLife();
							break;
						}
					}
					it.remove();	
				}
			}
		}
	}
	
	/** Ӣ�ۻ������(�л�+С�۷�)����ײ */
	public void hitAction(){ //10������һ��
		Iterator<FlyingObject> it = flyings.iterator();
		while(it.hasNext()){ //�������е���
			FlyingObject f = it.next();//��ȡÿһ������
			if(hero.hit(f)){ //ײ����
				hero.subtractLife(); //Ӣ�ۻ�����
				hero.clearDoubleFire(); //Ӣ�ۻ���ջ���ֵ
				it.remove();
			}
		}
	}
	
	/** �����Ϸ���� */
	public void checkGameOverAction(){ //10������һ��
		if(hero.getLife()<=0){ //��Ϸ������
			state = GAME_OVER; //�޸ĵ�ǰ״̬Ϊ��Ϸ����״̬
		}
	}
	
	/** ���������ִ�� */
	public void action(){
		//��������������
		MouseAdapter l = new MouseAdapter(){
			/** ��д����ƶ��¼� */
			public void mouseMoved(MouseEvent e){
				if(state==RUNNING){ //����״̬ʱִ��
					int x = e.getX(); //��ȡ����x����
					int y = e.getY(); //��ȡ����y����
					hero.moveTo(x, y); //Ӣ�ۻ�������궯
				}
			}
			/** ��д������¼� */
			public void mouseClicked(MouseEvent e){
				switch(state){ //���ݵ�ǰ״̬����ͬ�Ĵ���
				case START:        //����״̬ʱ
					state=RUNNING; //�޸�Ϊ����״̬
					break;
				case GAME_OVER:  //��Ϸ����״̬ʱ
					score = 0; //�����ֳ�(���ݹ���)
					hero = new Hero();
					flyings.clear();
					bullets = new Bullet[0];
					state=START; //�޸�Ϊ����״̬
					break;
				}
			}
			/** ��д����Ƴ��¼� */
			public void mouseExited(MouseEvent e){
				if(state==RUNNING){ //����״̬ʱ
					state=PAUSE; //�޸�Ϊ��ͣ״̬
				}
			}
			/** ��д��������¼� */
			public void mouseEntered(MouseEvent e){
				if(state==PAUSE){ //��ͣ״̬ʱ
					state=RUNNING; //�޸�Ϊ����״̬
				}
			}
		};
		this.addMouseListener(l); //�����������¼�
		this.addMouseMotionListener(l); //������껬���¼�
		
		Timer timer = new Timer(); //��ʱ������
		int intervel = 10; //��ʱ���(�Ժ���Ϊ��λ)
		timer.schedule(new TimerTask(){
			public void run(){ //��ʱ�ɵ��Ǹ���(10������һ��)
				if(state==RUNNING){ //����״̬ʱִ��
					enterAction(); //����(�л�+С�۷�)�볡
					stepAction();  //������(�л�+С�۷�+�ӵ�+Ӣ�ۻ�)��һ��
					shootAction(); //�ӵ��볡(Ӣ�ۻ������ӵ�)
					outOfBoundsAction(); //ɾ��Խ��ķ�����(�л�+С�۷�+�ӵ�)
					bangAction();  //�ӵ������(�л�+С�۷�)����ײ
					hitAction();   //Ӣ�ۻ������(�л�+С�۷�)����ײ
					checkGameOverAction(); //�����Ϸ����
				}
				repaint();     //�ػ�(����paint()����)
			}
		},intervel,intervel); //��ʱ�ճ�
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Fly"); //����
		ShootGame game = new ShootGame(); //���
		frame.add(game); //�������ӵ�������
		frame.setSize(WIDTH, HEIGHT); //���ô��ڴ�С
		frame.setAlwaysOnTop(true); //����������������
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //����Ĭ�Ϲرղ���(�رմ������˳�����)
		frame.setLocationRelativeTo(null); //���ô��ھ���
		frame.setVisible(true); //1)���ô��ڿɼ�  2)�������paint()����
		
		game.action(); //���������ִ��
	}
	
}














