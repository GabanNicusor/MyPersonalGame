package levels;

public class Level {
	private int[][] lvlData;
// Update the level data
	public Level(int[][] lvlData) {
		this.lvlData = lvlData;
	}
// Set the level tile position on map
	public int getSpriteIndex(int x, int y) {
		return lvlData[y][x];
	}
// Return the level data
	public int[][] getLevelData() {
		return lvlData;
	}
}
