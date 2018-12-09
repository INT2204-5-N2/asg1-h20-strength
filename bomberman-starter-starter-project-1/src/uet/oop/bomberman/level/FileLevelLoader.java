package uet.oop.bomberman.level;

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

import java.io.*;
import java.net.URL;
import java.util.StringTokenizer;

public class FileLevelLoader extends LevelLoader {

    /**
     * Ma tráº­n chá»©a thÃ´ng tin báº£n Ä‘á»“, má»—i pháº§n tá»­ lÆ°u giÃ¡ trá»‹ kÃ­ tá»± Ä‘á»?c Ä‘Æ°á»£c
     * tá»« ma tráº­n báº£n Ä‘á»“ trong tá»‡p cáº¥u hÃ¬nh
     */
    private static char[][] _map;

    public FileLevelLoader(Board board, int level) throws LoadLevelException {
        super(board, level);
    }

    @Override
    public void loadLevel(int level) {
        // TODO: Ä‘á»?c dá»¯ liá»‡u tá»« tá»‡p cáº¥u hÃ¬nh /levels/Level{level}.txt
        // TODO: cáº­p nháº­t cÃ¡c giÃ¡ trá»‹ Ä‘á»?c Ä‘Æ°á»£c vÃ o _width, _height, _level, _map
        try {
            String path = "levels/Level" + level + ".txt";
            URL absPath = FileLevelLoader.class.getResource("/" + path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(absPath.openStream()));
            String data=reader.readLine();

            //tÃ¡ch _level, _height, _width
            StringTokenizer tokens=new StringTokenizer(data);
            _level=Integer.parseInt(tokens.nextToken());
            _height=Integer.parseInt(tokens.nextToken());//sá»‘ hÃ ng
            _width=Integer.parseInt(tokens.nextToken());//sá»‘ cá»™t

            //lÆ°u trá»¯ ma tráº­n vÃ o _map
            _map=new char[_height][_width];
            String line[]=new String[_height];
            for (int i=0; i < _height; i++) {
                line[i]=reader.readLine().substring(0, _width);
            }
            for (int i=0; i < _height; i++) {
                for (int j=0; j < _width; j++) {
                    _map[i][j]=line[i].charAt(j); // tráº£ vá»? kÃ­ tá»± thá»© j
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createEntities() {
        // TODO: táº¡o cÃ¡c Entity cá»§a mÃ n chÆ¡i
        // TODO: sau khi táº¡o xong, gá»?i _board.addEntity() Ä‘á»ƒ thÃªm Entity vÃ o game
        for (int y = 0; y < getHeight(); y++)
        {
            for (int x = 0; x < getWidth(); x++)
            {
                int pos = x + y * getWidth();
                switch (_map[y][x]) {
                    case '#': //Wall
                        _board.addEntity(pos,new Wall(x, y, Sprite.wall));
                        break;
                    case '*': //Brick
                        _board.addEntity(pos, new LayeredEntity(x, y,
                                new Grass(x, y, Sprite.grass),
                                new Brick(x, y, Sprite.brick)));
                                break;
                    case 'p': //Bomber
                        _board.addCharacter( new Bomber(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board) );
                        Screen.setOffset(0, 0);
                        _board.addEntity(pos, new Grass(x, y, Sprite.grass) );
                        break;
                    case '1': //Enemy
                        _board.addCharacter(new Balloon(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                        _board.addEntity(pos, new Grass(x, y, Sprite.grass));
                        break;
                    case '2': //Enemy
                        _board.addCharacter(new Oneal(Coordinates.tileToPixel(x), Coordinates.tileToPixel(y) + Game.TILES_SIZE, _board));
                        _board.addEntity(pos, new Grass(x, y, Sprite.grass));
                        break;    
                    case 'x': // Portal
                        _board.addEntity(pos,
                                new LayeredEntity(x, y,
                                new Grass(x, y, Sprite.grass),
                                new Portal(x, y, _board, Sprite.portal),
                                new Brick(x, y, Sprite.brick)
                        ));
                        break;
                    case 'f': //powerup _ flame
                        _board.addEntity(pos,
                                new LayeredEntity(x, y,
                                        new Grass(x, y, Sprite.grass),
                                        new FlameItem(x, y, Sprite.powerup_flames),
                                        new Brick(x, y, Sprite.brick)
                                ));
                        break;
                    case 'b': //bombs
                        _board.addEntity(pos,
                                new LayeredEntity(x, y,
                                        new Grass(x, y, Sprite.grass),
                                        new BombItem(x, y, Sprite.powerup_bombs),
                                        new Brick(x, y, Sprite.brick)
                                ));
                        break;
                    case 's': //speed
                        _board.addEntity(pos,
                                new LayeredEntity(x, y,
                                        new Grass(x, y, Sprite.grass),
                                        new SpeedItem(x, y, Sprite.powerup_speed),
                                        new Brick(x, y, Sprite.brick)
                                ));
                        break;
                    default:
                        _board.addEntity(pos, new Grass(x, y, Sprite.grass));
                }
            }
        }
    }
    // TODO: pháº§n code máº«u á»Ÿ dÆ°á»›i Ä‘á»ƒ hÆ°á»›ng dáº«n cÃ¡ch thÃªm cÃ¡c loáº¡i Entity vÃ o game
    // TODO: hÃ£y xÃ³a nÃ³ khi hoÃ n thÃ nh chá»©c nÄƒng load mÃ n chÆ¡i tá»« tá»‡p cáº¥u hÃ¬nh

    /*
		// thÃªm Wall
		for (int x = 0; x < 20; x++) {
			for (int y = 0; y < 20; y++) {
				int pos = x + y * _width;
				Sprite sprite = y == 0 || x == 0 || x == 10 || y == 10 ? Sprite.wall : Sprite.grass;
				_board.addEntity(pos, new Grass(x, y, sprite));
			}
		}

		// thÃªm Bomber
		int xBomber = 1, yBomber = 1;
		_board.addCharacter( new Bomber(Coordinates.tileToPixel(xBomber), Coordinates.tileToPixel(yBomber) + Game.TILES_SIZE, _board) );
		Screen.setOffset(0, 0);
		_board.addEntity(xBomber + yBomber * _width, new Grass(xBomber, yBomber, Sprite.grass));

		// thÃªm Enemy
		int xE = 2, yE = 1;
		_board.addCharacter( new Balloon(Coordinates.tileToPixel(xE), Coordinates.tileToPixel(yE) + Game.TILES_SIZE, _board));
		_board.addEntity(xE + yE * _width, new Grass(xE, yE, Sprite.grass));

		// thÃªm Brick
		int xB = 3, yB = 1;
		_board.addEntity(xB + yB * _width,
				new LayeredEntity(xB, yB,
					new Grass(xB, yB, Sprite.grass),
					new Brick(xB, yB, Sprite.brick)
				)
		);

		// thÃªm Item kÃ¨m Brick che phá»§ á»Ÿ trÃªn
		int xI = 1, yI = 2;
		_board.addEntity(xI + yI * _width,
				new LayeredEntity(xI, yI,
					new Grass(xI ,yI, Sprite.grass),
					new SpeedItem(xI, yI, Sprite.powerup_flames),
					new Brick(xI, yI, Sprite.brick)
				)
		);
    }
    */
}
