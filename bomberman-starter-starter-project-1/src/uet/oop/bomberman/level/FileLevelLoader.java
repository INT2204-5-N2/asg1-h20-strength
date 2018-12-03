package uet.oop.bomberman.level;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.Game;
import uet.oop.bomberman.entities.LayeredEntity;
import uet.oop.bomberman.entities.character.Bomber;
import uet.oop.bomberman.entities.character.enemy.Balloon;
import uet.oop.bomberman.entities.character.enemy.Oneal;
import uet.oop.bomberman.entities.tile.Grass;
import uet.oop.bomberman.entities.tile.Portal;
import uet.oop.bomberman.entities.tile.Wall;
import uet.oop.bomberman.entities.tile.destroyable.Brick;
import uet.oop.bomberman.entities.tile.item.BombItem;
import uet.oop.bomberman.entities.tile.item.FlameItem;
import uet.oop.bomberman.entities.tile.item.SpeedItem;
import uet.oop.bomberman.exceptions.LoadLevelException;
import uet.oop.bomberman.graphics.Screen;
import uet.oop.bomberman.graphics.Sprite;

public class FileLevelLoader extends LevelLoader {

	/**
	 * Ma trận chứa thông tin bản đồ, mỗi phần tử lưu giá trị kí tự đ�?c được
	 * từ ma trận bản đồ trong tệp cấu hình
	 */
	private static char[][] _map;
	
	public FileLevelLoader(Board board, int level) throws LoadLevelException {
		super(board, level);
	}
	
	@Override
	public void loadLevel(int level) {
		// TODO: đ�?c dữ liệu từ tệp cấu hình /levels/Level{level}.txt
		// TODO: cập nhật các giá trị đ�?c được v� o _width, _height, _level, _map
            String Filename = "levels/Level" + level +".txt";
            ClassLoader c = ClassLoader.getSystemClassLoader();
            File file = new File(c.getResource(Filename).getFile());
                
            try {
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
        
                String[] line1 =  br.readLine().split(" ");
                _level = Integer.parseInt(line1[0]);
                _height = Integer.parseInt(line1[1]);
                _width = Integer.parseInt(line1[2]);
       
                 _map = new char[_height][_width];
                 for(int i = 0; i < _height; i++) {
                     String line = br.readLine();
                     for(int j = 0; j < _width; j++) {
                         _map[i][j] = line.charAt(j);
                     }
                 }
                 br.close();
                 fr.close();
                
            } catch (Exception e) {
                e.printStackTrace();
            }
	}

	@Override
	public void createEntities() {
		// TODO: tạo các Entity của m� n chơi
		// TODO: sau khi tạo xong, g�?i _board.addEntity() để thêm Entity v� o game

		// TODO: phần code mẫu ở dưới để hướng dẫn cách thêm các loại Entity v� o game
		// TODO: hãy xóa nó khi ho� n th� nh chức năng load m� n chơi từ tệp cấu hình
		// thêm Wall
            for (int x = 0; x < _height; x++){
		for (int y = 0; y < _width; y++){
                    int pos = y + x * _width;
                    switch (_map[x][y]){
                        
                    /* # - Wall: t??ng?

                     * - Brick: g?ch

                      x - Portal: c?ng k?t th�c game?

                     p - Bomber: Nh�n v?t ch�nh?

                     1 - Balloon: Nh�n v?t Balloon h�nh b�ng bay

                     b - Bomb Item: V?t ph?m t?ng s? l??ng bom

                      f - Flame Item: V?t ph?m t?ng s?c c�ng ph� c?a bom?

                     s - Speed Item: V?t ph?m t?ng t?c ?? c?a ng??i ch?i?
                        */
			case '#':
                            _board.addEntity(pos, new Wall(y, x, Sprite.wall));
                            break;
			case '*':
                            _board.addEntity(pos, new LayeredEntity(y, x,
                                    new Grass(y, x, Sprite.grass),
                                    new Brick(y, x, Sprite.brick)));
                            break;
			case 'x':
                            _board.addEntity(pos, new LayeredEntity(y, x,
                                    new Portal(y, x, Sprite.portal), 
                                    new Brick(y, x, Sprite.brick)));
                            break;
			case 'p':
                            _board.addCharacter(new Bomber(Coordinates.tileToPixel(y), 
                                    Coordinates.tileToPixel(x) + Game.TILES_SIZE, _board));
                            _board.addEntity(pos, new Grass(y, x, Sprite.grass));
                            break;
                        case 'b':
                            _board.addEntity(pos, new LayeredEntity(y, x, new Grass(y, x, Sprite.grass),
                                                     new BombItem(y, x, Sprite.bomb),
                                                     new Brick(y, x, Sprite.brick)));
                            break;
			case 'f':
                            _board.addEntity(pos, new LayeredEntity(y, x,
                                                    new Grass(y, x, Sprite.grass),
                                                    new FlameItem(y, x, Sprite.powerup_flames),
                                                    new Brick(y, x, Sprite.brick)));
                            break;
			case 's':
                            _board.addEntity(pos, new LayeredEntity(y, x,
                                                    new Grass(y, x, Sprite.grass),
                                                    new SpeedItem(y, x, Sprite.powerup_speed),
                                                    new Brick(y, x, Sprite.brick)));
                            break;
			case '1':
                            _board.addCharacter(new Balloon(Coordinates.tileToPixel(y),
                                    Coordinates.tileToPixel(x) + Game.TILES_SIZE, _board));
                            _board.addEntity(pos, new Grass(y, x, Sprite.grass));
						break;
			case '2':
                            _board.addCharacter(new Oneal(Coordinates.tileToPixel(y),
                                    Coordinates.tileToPixel(x) + Game.TILES_SIZE, _board));
                            _board.addEntity(pos, new Grass(y, x, Sprite.grass));
						break;
			default:
                            _board.addEntity(pos, new Grass(y, x, Sprite.grass));
                            break;
		}
            }
        }
    }
}