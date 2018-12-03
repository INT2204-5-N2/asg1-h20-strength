package uet.oop.bomberman.entities.character.enemy.ai;

public class AILow extends AI {

	@Override
	public int calculateDirection() {
		// TODO: cài đặt thuật toán tìm đư�?ng đi
		return random.nextInt(4);
	}

}
