package entities;

import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import main.Game;
import utilz.LoadSave;

public class Player extends Entity {
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 25;
	private int playerAction = IDLE;
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down, jump;
	private float playerSpeed = 2.0f;
	private int[][] lvlData;
	private float xDrawOffset = 21 * Game.SCALE;
	private float yDrawOffset = 4 * Game.SCALE;

	// Jumping / Gravity
	private float airSpeed = 0f;
	private float gravity = 0.04f * Game.SCALE;
	private float jumpSpeed = -2.25f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
	private boolean inAir = false;
// Set the size and player position
	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
		initBox(x, y, 20 * Game.SCALE, 27 * Game.SCALE);

	}
// Update the methods for animation and position
	public void update() {
		updatePos();
		updateAnimationTick();
		setAnimation();
	}
// Draw the player animation after player action and add the rectangle
	public void render(Graphics g) {
		g.drawImage(animations[playerAction][aniIndex], (int) (playerBox.x - xDrawOffset), (int) (playerBox.y - yDrawOffset), width, height, null);
		drawHitbox(g);
	}
// Read from the image all the line and column which includes the animation for player
	private void updateAnimationTick() {
		aniTick++;
		if (aniTick >= aniSpeed) {
			aniTick = 0;
			aniIndex++;
			if (aniIndex >= GetSpriteAmount(playerAction)) {
				aniIndex = 0;
				attacking = false;
			}

		}

	}
// Set the animation after player action
	private void setAnimation() {
		int startAni = playerAction;

		if (moving)
			playerAction = RUNNING;
		else
			playerAction = IDLE;

		if (inAir) {
			if (airSpeed < 0)
				playerAction = JUMP;
			else
				playerAction = FALLING;
		}

		if (attacking)
			playerAction = ATTACK_1;

		if (startAni != playerAction)
			resetAniTick();
	}
// Reset the line and column position to 0 for player animation 
	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
	}
// Update the player movement after player action
	private void updatePos() {
		moving = false;
		if (jump) {
			jump();
		}
		if (!left && !right && !inAir) {
			return;
		}
		float xSpeed = 0;
		if (left) {
			xSpeed -= playerSpeed;
		}
		if (right) {
			xSpeed += playerSpeed;
		}
		if (!inAir) { 
			if (!IsEntityOnFloor(playerBox, lvlData))
				inAir = true;
		}
		if (inAir) {
			if (CanMoveHere(playerBox.x, playerBox.y + airSpeed, playerBox.width, playerBox.height, lvlData)) {
				playerBox.y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed);
			} else {
				playerBox.y = GetEntityYPosUnderRoofOrAboveFloor(playerBox, airSpeed);
				if (airSpeed > 0)
					resetInAir();
				else
					airSpeed = fallSpeedAfterCollision;
				updateXPos(xSpeed);
			}
		} else
			updateXPos(xSpeed);
			moving = true;
	}
// Verify if we are in the air or not, if we are not in the air we can jump
	private void jump() {
		if (inAir)
			return;
		inAir = true;
		airSpeed = jumpSpeed;

	}
// Reset the player in air statement
	private void resetInAir() {
		inAir = false;
		airSpeed = 0;

	}
// Check the next position if we can pass through or not
	private void updateXPos(float xSpeed) {
		if (CanMoveHere(playerBox.x + xSpeed, playerBox.y, playerBox.width, playerBox.height, lvlData)) {
			playerBox.x += xSpeed;
		} else {
			playerBox.x = GetEntityXPosNextToWall(playerBox, xSpeed);
		}

	}
// Load the animation from the image, so we can work more easily
	private void loadAnimations() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
		animations = new BufferedImage[9][6];
		for (int j = 0; j < animations.length; j++)
			for (int i = 0; i < animations[j].length; i++)
				animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);

	}
// Load the level data and check if we are on the floor
	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if (!IsEntityOnFloor(playerBox, lvlData))
			inAir = true;

	}
// Reset the direction if we press outside of our window
	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}
// Set the attacking on true if we are attacking
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}
// Set every movement key to true if we press a key 
	public boolean isLeft() {
		return left;
	}

	public void setLeft(boolean left) {
		this.left = left;
	}

	public boolean isUp() {
		return up;
	}

	public void setUp(boolean up) {
		this.up = up;
	}

	public boolean isRight() {
		return right;
	}

	public void setRight(boolean right) {
		this.right = right;
	}

	public boolean isDown() {
		return down;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void setJump(boolean jump) {
		this.jump = jump;
	}
}