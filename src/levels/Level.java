package levels;

public class Level {

	private int[][] lvlData;
// Return the level data 
	public Level(int[][] lvlData) {
		this.lvlData = lvlData;
	}
// Return the player position on the map
	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}
// Return the level data
	public int[][] getLevelData() {
		return lvlData;
	}

}
