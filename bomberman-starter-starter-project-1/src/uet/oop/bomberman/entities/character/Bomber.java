package uet.oop.bomberman.entities.character;

import java.util.ArrayList;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.bomb.Bomb;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.input.Keyboard;

import java.util.Iterator;
import java.util.List;
import uet.oop.bomberman.entities.bomb.Flame;
import uet.oop.bomberman.entities.character.enemy.Enemy;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.Item;
import uet.oop.bomberman.level.Coordinates;

public class Bomber extends Character {

    private List<Bomb> _bombs;
    protected Keyboard _input;
    public static List<Item> _powerups = new ArrayList<Item>();
    /**
     * n?u giá tr? này nho hon 0 thì cho phép ??t ??i t??ng Bomb ti?p theo,
     * c? m?i l?n ??t 1 Bomb m?i, giá tr? này s? ???c reset v? 0 và gi?m d?n trong m?i l?n update()
     */
    protected int _timeBetweenPutBombs = 0;

    public Bomber(int x, int y, Board board) {
        super(x, y, board);
        _bombs = _board.getBombs();
        _input = _board.getInput();
        _sprite = Sprite.player_right;
    }
    
    public Board getBoard(){
        return _board;
    }
    
    @Override
    public void update() {
        clearBombs();
        if (!_alive) {
            afterKill();
            return;
        }

        if (_timeBetweenPutBombs < -7500) _timeBetweenPutBombs = 0;
        else _timeBetweenPutBombs--;

        animate();

        calculateMove();

        detectPlaceBomb();
    }

    @Override
    public void render(Screen screen) {
        calculateXOffset();

        if (_alive)
            chooseSprite();
        else
            _sprite = Sprite.player_dead1;

        screen.renderEntity((int) _x, (int) _y - _sprite.SIZE, this);
    }

    public void calculateXOffset() {
        int xScroll = Screen.calculateXOffset(_board, this);
        Screen.setOffset(xScroll, 0);
    }

   
    /**
     * Ki?m tra xem có ??t ???c bom hay không? n?u có thì ??t bom t?i v? trí hi?n t?i c?a Bomber
     */
    private void detectPlaceBomb() {
        // TODO: ki?m tra xem ph?m ?i?u khi?n ??t bom c? ???c g? v? gi? tr? _timeBetweenPutBombs, Game.getBombRate() c? th?a m?n hay kh?ng
        // TODO:  Game.getBombRate() s? tr? v? s? l??ng bom c? th? ??t li?n ti?p t?i th?i ?i?m hi?n t?i
        // TODO: _timeBetweenPutBombs d?ng ?? ng?n ch?n Bomber ??t 2 Bomb c?ng t?i 1 v? tr? trong 1 kho?ng th?i gian qu? ng?n
        // TODO: n?u 3 ?i?u ki?n tr?n th?a m?n th? th?c hi?n ??t bom b?ng placeBomb()
        // TODO: sau khi ??t, nh? gi?m s? l??ng Bomb Rate v? reset _timeBetweenPutBombs v? 0
        if(_input.space && Game.getBombRate() > 0 && _timeBetweenPutBombs < 0) {
            int xt = Coordinates.pixelToTile(_x + _sprite.getSize() / 2);
            int yt = Coordinates.pixelToTile((_y - _sprite.getSize() / 2));

            placeBomb(xt, yt);
            Game.addBombRate(-1);
            _timeBetweenPutBombs =10;
        }
    }

    protected void placeBomb(int x, int y) {
         // TODO: th?c hi?n t?o ??i t??ng bom, d?t vào v? trí (x, y)
         Bomb b = new Bomb(x, y, _board);
         _board.addBomb(b);
    }

    private void clearBombs() {
        Iterator<Bomb> bs = _bombs.iterator();

        Bomb b;
        while (bs.hasNext()) {
            b = bs.next();
            if (b.isRemoved()) {
                bs.remove();
                Game.addBombRate(1);
            }
        }

    }

    @Override
    public void kill() {
        if (!_alive) return;
        _alive = false;
    }

    @Override
    protected void afterKill() {
        if (_timeAfter > 0) --_timeAfter;
        else {
            _board.endGame();
        }
    }

    @Override
    protected void calculateMove() {
        // TODO: x? lí nh?n tín hi?u ?i?u khi?n h??ng ?i t? _input và g?i move() ?? th?c hi?n di chuy?n
        // TODO: nh? c?p nh?t l?i giá tr? c? _moving khi thay ??i tr?ng thái di chuy?n
        int x = 0; 
        int y = 0;
        if(_input.up) y--;
        if(_input.down) y++;
        if(_input.left) x--;
        if(_input.right) x++;
        
        if(x != 0 || y != 0) {
            move(x * Game.getBomberSpeed(), y * Game.getBomberSpeed());
            _moving = true;
        }
        else {
            _moving = false;
        }
    }
    

    @Override
    public boolean canMove(double x, double y) {
            for (int c = 0; c < 4; c++) { 
		double xt = ((_x + x) + c % 2 * 11) / Game.TILES_SIZE; //divide with tiles size to pass to tile coordinate
		double yt = ((_y + y) + c / 2 * 12 - 13) / Game.TILES_SIZE; //these values are the best from multiple tests
			
		Entity a = _board.getEntity(xt, yt, this);
			
		if(!a.collide(this))
                    return false;
            }
            
		return true;
    }

    @Override
    public void move(double xa, double ya) {
        // TODO: s? d?ng canMove() ?? ki?m tra xem có th? di chuy?n t?i ?i?m ?ã tính toán hay không và th?c hi?n thay ??i t?a ?? _x, _y
        // TODO: nh? c?p nh?t giá tr? _direction sau khi di chuy?n
            if(ya < 0) _direction = 0;
            if(xa > 0) _direction = 1;
            if(ya > 0) _direction = 2;
            if(xa < 0) _direction = 3;

            if(canMove(xa, ya)) { 
                  _y += ya;
                  _x += xa;
            }

    }

    @Override
    public boolean collide(Entity e) {
         // TODO: x? lí va ch?m v?i Flame
        // TODO: x? lí va ch?m v?i Enemy
        if(e instanceof Flame || e instanceof Enemy) {
            kill();
            return true;
        }
        return false;
    }

    private void chooseSprite() {
        switch (_direction) {
            case 0:
                _sprite = Sprite.player_up;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_up_1, Sprite.player_up_2, _animate, 20);
                }
                break;
            case 1:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
            case 2:
                _sprite = Sprite.player_down;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_down_1, Sprite.player_down_2, _animate, 20);
                }
                break;
            case 3:
                _sprite = Sprite.player_left;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_left_1, Sprite.player_left_2, _animate, 20);
                }
                break;
            default:
                _sprite = Sprite.player_right;
                if (_moving) {
                    _sprite = Sprite.movingSprite(Sprite.player_right_1, Sprite.player_right_2, _animate, 20);
                }
                break;
        }
    }
    public void addPowerup(Item p) {
		if(p.isRemoved()) return;
		
		_powerups.add(p);
		
		p.setValues();
	}
	
	
	
	
}