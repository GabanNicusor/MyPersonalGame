package entities;

import static utilz.Constants.PlayerConstants.*;
import static utilz.HelpMethods.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import levels.LevelManager;
import main.Game;
import main.GamePanel;
import utilz.LoadSave;

public class Player extends Entity {
	Game game;
	Player player;
	GamePanel gamePanel;
	LevelManager levelManager;
	private BufferedImage[][] animations;
	private int aniTick, aniIndex, aniSpeed = 25;
	private int playerAction = IDLE;
	private boolean moving = false, attacking = false;
	private boolean left, up, right, down, jump, reload = false;
	private float playerSpeed = 2.0f;
	private int[][] lvlData;
	private float xDrawOffset = 21 * Game.SCALE;
	private float yDrawOffset = 4 * Game.SCALE;
	private final static float DEFAULT_POSITION_Y = 350 * Game.SCALE;
	// Jumping / Gravity
	private float airSpeed = 0f;
	private float gravity = 0.04f * Game.SCALE;
	private float jumpSpeed = -2.25f * Game.SCALE;
	private float fallSpeedAfterCollision = 0.5f * Game.SCALE;
	private boolean inAir = false;

	public Player(float x, float y, int width, int height) {
		super(x, y, width, height);
		loadAnimations();
		initHitbox(x, y, 20 * Game.SCALE, 27 * Game.SCALE);  
	}
// Update the animation and position
	public void update() {
		updatePos();
		updateAnimationTick();
		setAnimation();
	}
// Update and check the next position if we can move there
	private void updateXPos(float xSpeed) {
		if (CanMoveHere(hitbox.x + xSpeed, hitbox.y, hitbox.width, hitbox.height, lvlData)) {
			hitbox.x += xSpeed;
		}else {
			hitbox.x = GetEntityXPosNextToWall(hitbox, xSpeed);
		}
	}
// Update the animation position
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
// Update the player movement and check if we are in air or not
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
			if (!IsEntityOnFloor(hitbox, lvlData)) {
				inAir = true;
			}
		}
		if (inAir) {
			if (CanMoveHere(hitbox.x, hitbox.y + airSpeed, hitbox.width, hitbox.height, lvlData)) {
				hitbox.y += airSpeed;
				airSpeed += gravity;
				updateXPos(xSpeed);
			}else {
				hitbox.y = GetEntityYPosUnderRoofOrAboveFloor(hitbox, airSpeed);
				if (airSpeed > 0) {
					resetInAir();
				}else {
					airSpeed = fallSpeedAfterCollision;
					updateXPos(xSpeed);
				}
			}
		}else {
			updateXPos(xSpeed);
			moving = true;
		}
	}
// Set the animation movement
	private void setAnimation() {
		int startAni = playerAction;
		if (moving) {
			playerAction = RUNNING;
		}else {
			playerAction = IDLE;
		}
		if (hitbox.y >= DEFAULT_POSITION_Y - 50) { 
			playerAction = DIE;
			resetDirBooleans();
			reloadPlayer();
		}
		if (inAir) {
			if (airSpeed < 0) {
				playerAction = JUMP;
			}else {
				playerAction = FALLING;
			}
		}
		if (attacking) {
			playerAction = ATTACK_1;
		}
		if (startAni != playerAction) {
			resetAniTick();
		}
	}
// Load the animation to have a good flow in game
	private void loadAnimations() {
		BufferedImage img = LoadSave.GetSpriteAtlas(LoadSave.PLAYER_ATLAS);
		animations = new BufferedImage[7][8];
		for (int j = 0; j < animations.length; j++) {
			for (int i = 0; i < animations[j].length; i++) {
				animations[j][i] = img.getSubimage(i * 64, j * 40, 64, 40);
			}
		}
	}
// Getters and Setters
	public void setAttacking(boolean attacking) {
		this.attacking = attacking;
	}

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

	public void setReload(boolean reload) {
		this.reload = reload;
	}

	public void reloadPlayer() {
		if (reload) {
			hitbox.x = 150;
			hitbox.y = 200;
			inAir = true;
			reload = false;
		}
	}
// Reset the movement to false
	public void resetDirBooleans() {
		left = false;
		right = false;
		up = false;
		down = false;
	}

	private void resetAniTick() {
		aniTick = 0;
		aniIndex = 0;
	}

	private void jump() {
		if (inAir) {
			return;
		}
		inAir = true;
		airSpeed = jumpSpeed;
	}

	private void resetInAir() {
		inAir = false;
		airSpeed = 0;
	}
// Draw the box and player
	public void render(Graphics g) {
		g.drawImage(animations[playerAction][aniIndex], (int) (hitbox.x - xDrawOffset), (int) (hitbox.y - yDrawOffset), width, height, null);
		// drawHitbox(g);
	}
// Load the level data
	public void loadLvlData(int[][] lvlData) {
		this.lvlData = lvlData;
		if (!IsEntityOnFloor(hitbox, lvlData)) {
			inAir = true;
		}
	}
}
