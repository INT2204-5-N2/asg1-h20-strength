package uet.oop.bomberman.entities.bomb;

import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.AnimatedEntitiy;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.Character;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.level.Coordinates;

public class Bomb extends AnimatedEntitiy {

    protected double _timeToExplode = 120; //2 seconds
    public int _timeAfter = 20;

    protected Board _board;
    protected Flame[] _flames= new Flame[0];
    protected boolean _exploded = false;
    protected boolean _allowedToPassThru = true;

    public Bomb(int x, int y, Board board) {
        _x = x;
        _y = y;
        _board = board;
        _sprite = Sprite.bomb;
    }

    @Override
    public void update() {
        if (_timeToExplode > 0)
            _timeToExplode--;
        else {
            if (!_exploded)
                explode();
            else
                updateFlames();

            if (_timeAfter > 0)
                _timeAfter--;
            else
                remove();
        }

        animate();
    }

    @Override
    public void render(Screen screen) {
        if (_exploded) {
            _sprite = Sprite.bomb_exploded2;
            renderFlames(screen);
        } else
            _sprite = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, _animate, 60);

        int xt = (int) _x << 4;
        int yt = (int) _y << 4;

        screen.renderEntity(xt, yt, this);
    }

    public void renderFlames(Screen screen) {
        for (int i = 0; i < _flames.length; i++) {
            _flames[i].render(screen);
        }
    }

    public void updateFlames() {
        for (int i = 0; i < _flames.length; i++) {
            _flames[i].update();
        }
    }

    /**
     * X? lý Bomb n?
     */
    protected void explode() {
        _exploded = true;

        // TODO: x? lý khi Character ??ng t?i v? trí Bomb
        Entity entity = _board.getEntity(_x, _y, null);
        if (entity instanceof Character) {
            ((Character) entity).kill();
        }
        // TODO: t?o các Flame
        _flames = new Flame[4];
        for (int i = 0; i < 4; i++) {
            _flames[i] = new Flame((int) _x, (int) _y, i, Game.getBombRadius(), _board);
        }
    }

    public FlameSegment flameAt(int x, int y) {
        if (!_exploded) return null;

        for (int i = 0; i < _flames.length; i++) {
            if (_flames[i] == null) return null;
            FlameSegment e = _flames[i].flameSegmentAt(x, y);
            if (e != null) return e;
        }

        return null;
    }

    @Override
    public boolean collide(Entity e) {
        // TODO: x? lý khi Bomber ?i ra sau khi v?a ??t bom (_allowedToPassThru)
        // TODO: x? lý va ch?m v?i Flame c?a Bomb khác
        if (e instanceof Bomber) {
            double diffirenceX = e.getX() - Coordinates.tileToPixel(_x);
            double diffirenceY = e.getY() - Coordinates.tileToPixel(_y);
            System.out.println(diffirenceX+ " "+ diffirenceY);
            if (!(diffirenceX>=-11&&diffirenceX<=14&&diffirenceY>=1&&diffirenceY<=27)){
                _allowedToPassThru = false;
            }
            return _allowedToPassThru;
        }
        if (e instanceof Flame) {
            _timeToExplode = 0;
        }
        return false;
    }
}