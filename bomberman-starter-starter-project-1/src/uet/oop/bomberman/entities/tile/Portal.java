package uet.oop.bomberman.entities.tile;

import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.graphics.Sprite;

public class Portal extends Tile {

	public Portal(int x, int y, Sprite sprite) {
		super(x, y, sprite);
	}
	
	@Override
	public boolean collide(Entity e) {
		// TODO: xử lý khi Bomber đi vào
                if(e instanceof Bomber ) {			
			if(((Bomber) e).getBoard().detectNoEnemies() == false)
				return false;			
			if(e.getXTile() == getX() && e.getYTile() == getY()) {
				if(((Bomber) e).getBoard().detectNoEnemies())
					((Bomber) e).getBoard().nextLevel();
                               
			}
			
			return true;
		}

		return false;
	}

}
