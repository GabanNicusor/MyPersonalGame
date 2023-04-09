package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

	protected float x, y;
	protected int width, height;
	protected Rectangle2D.Float playerBox;
// Set the entity position and size for player rectangle
	public Entity(float x, float y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;

	}
// Draw player Box
	protected void drawHitbox(Graphics g) {
		// For debugging the hitbox
		g.setColor(Color.PINK);
		g.drawRect((int) playerBox.x, (int) playerBox.y, (int) playerBox.width, (int) playerBox.height);

	}
// Set the size and the position after player position and size
	protected void initBox(float x, float y, float width, float height) {
		playerBox = new Rectangle2D.Float(x, y, width, height);
	}

//	protected void updateHitbox() {
//		hitbox.x = (int) x;
//		hitbox.y = (int) y;
//	}
// Give the access to all methods
	public Rectangle2D.Float getHitbox() {
		return playerBox;
	}

}
