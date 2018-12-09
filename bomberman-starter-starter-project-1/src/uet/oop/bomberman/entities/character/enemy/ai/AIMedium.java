package uet.oop.bomberman.entities.character.enemy.ai;

import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Enemy;

public class AIMedium extends AI {
	Bomber _bomber;
	Enemy _e;
	
	public AIMedium(Bomber bomber, Enemy e) {
		_bomber = bomber;
		_e = e;
	}

	@Override
	public int calculateDirection() {
		// TODO: cài ??t thu?t toán tìm ???ng ?i
		int rowOrCol = random.nextInt(2);
		if(rowOrCol == 0){
			if(ringhtOrLeft() != 10) // 10 means this enemy and bomber are on the same row
				return ringhtOrLeft();
			return upOrDown();
		}
		else {
			if (upOrDown() != 10)// 10 means this enemy and bomber are on the same column
				return upOrDown();
			return ringhtOrLeft();
		}
	}

	private int upOrDown(){
		if(_bomber.getXTile() < _e.getXTile())
			return 3;
		else if(_bomber.getXTile() > _e.getXTile())
			return 1;
		return 10;
	}
	private int ringhtOrLeft(){
		if(_bomber.getYTile() < _e.getYTile())
			return 0;
		else if(_bomber.getYTile() > _e.getYTile())
			return 2;
		return 10;
	}
}