package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.graphics.Screen;

public class Flame extends Entity {

	protected Board _board;
	protected int _direction;
	private int _radius;
	protected int xOrigin, yOrigin;
	protected FlameSegment[] _flameSegments = new FlameSegment[0];

	/**
	 *
	 * @param x hoành ?? b?t ??u c?a Flame
	 * @param y tung ?? b?t ??u c?a Flame
	 * @param direction là h??ng c?a Flame
	 * @param radius ?? dài c?c ??i c?a Flame
	 */
	public Flame(int x, int y, int direction, int radius, Board board) {
		xOrigin = x;
		yOrigin = y;
		_x = x;
		_y = y;
		_direction = direction;
		_radius = radius;
		_board = board;
		createFlameSegments();
	}

	/**
	 * T?o các FlameSegment, m?i segment ?ng m?t ??n v? ?? dài
	 */
	private void createFlameSegments() {
		/**
		 * tính toán ?? dài Flame, t??ng ?ng v?i s? l??ng segment
		 */
                
		_flameSegments = new FlameSegment[calculatePermitedDistance()];

		/**
		 * bi?n last dùng ?? ?ánh d?u cho segment cu?i cùng
		 */
                
		boolean last;

		// TODO: t?o các segment d??i ?ây		
		int x = (int)_x;
		int y = (int)_y;
		for (int i = 0; i < _flameSegments.length; i++) {
			last = i == _flameSegments.length -1 ? true : false;
			
			switch (_direction) {
				case 0: y--; break;
				case 1: x++; break;
				case 2: y++; break;
				case 3: x--; break;
			}
			_flameSegments[i] = new FlameSegment(x, y, _direction, last);
		}
	}

	/**
	 * Tính toán ?? dài c?a Flame, n?u g?p v?t c?n là Brick/Wall, ?? dài s? b? c?t ng?n
	 * @return
	 */
	private int calculatePermitedDistance() {
		// TODO: th?c hi?n tính toán ?? dài c?a Flame
                int length = 0;
                int x = (int) _x;
                int y = (int) _y;
                
                while(length < _radius) {
                    if(_direction == 0) y--;
                    if(_direction == 1) x++;
                    if(_direction == 2) y++;
                    if(_direction == 3) x--;
                    
                    Entity a = _board.getEntity(x, y, null);
                    
                    if(!a.collide(this)) 
                        break;                
                    length++;
                }
		return length;
	}
	
	public FlameSegment flameSegmentAt(int x, int y) {
		for (int i = 0; i < _flameSegments.length; i++) {
			if(_flameSegments[i].getX() == x && _flameSegments[i].getY() == y)
				return _flameSegments[i];
		}
		return null;
	}

	@Override
	public void update() {}
	
	@Override
	public void render(Screen screen) {
		for (int i = 0; i < _flameSegments.length; i++) {
			_flameSegments[i].render(screen);
		}
	}

	@Override
	public boolean collide(Entity e) {
		// TODO: x? lý va ch?m v?i Bomber, Enemy. Chú ý ??i t??ng này có v? trí chính là v? trí c?a Bomb ?ã n?
                if(e instanceof Bomber) {
                    ((Bomber)e).kill();
                }
                else if(e instanceof Enemy) {
                    ((Enemy) e).kill();
                }
		return true;
	}
}