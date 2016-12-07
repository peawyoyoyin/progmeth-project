package game;

import constants.ConfigConstant;
import gamedata.GameData;

public abstract class Ship extends Entity implements Renderable {

	protected int hp;
	protected int maxHp;
	protected double speed;
	protected int maxSpeed;
	protected double accelerate;
	protected int turnRate;
	protected int direction;

	public Ship(double x, double y, int hp, int maxHp, double speed, int maxSpeed, double accelerate, int turnRate,
			int direction) {
		this.x = x;
		this.y = y;
		this.hp = hp;
		this.maxHp = maxHp;
		this.speed = speed;
		this.maxSpeed = maxSpeed;
		this.accelerate = accelerate;
		this.turnRate = turnRate;
		this.direction = direction;
	}

	public int getHp() {
		return hp;
	}

	public int getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(int maxHp) {
		this.maxHp = maxHp;
	}

	public void setMaxSpeed(int maxSpeed) {
		this.maxSpeed = maxSpeed;
	}

	public void forward() {
		this.speed = this.speed + this.accelerate;
		if (this.speed > this.maxSpeed) {
			this.speed = this.maxSpeed;
		}
	}

	public void back() {
		this.speed = this.speed - this.accelerate;
		if (this.speed < -this.maxSpeed) {
			this.speed = -this.maxSpeed;
		}
	}

	public void turn(boolean left) {
		if (left) {
			this.direction -= this.turnRate;
			if (this.direction < 0)
				this.direction += 360;
		} else {
			this.direction += this.turnRate;
			if (this.direction >= 360)
				this.direction -= 360;
		}
	}

	public void hit(int damage) {
		this.hp = this.hp - damage;
		if (this.hp < 0) {
			this.destroyed = true;
		}
	}

	public void shoot() {
		MapCell mc = MapCellHolder.instance.get(GameData.getInstance().getPlayerData().getSectionX(),
				GameData.getInstance().getPlayerData().getSectionY());
		mc.getEntities().add(new Bullet(this.x+Math.cos(Math.toRadians(this.direction)) * 30, this.y+Math.sin(Math.toRadians(this.direction)) * 30, GameData.getInstance().getPlayerData().getBulletSpeed(),
				this.direction, GameData.getInstance().getPlayerData().getBulletDamage(), this));
	}

	public void healMaxHp() {
		this.hp = this.maxHp;
	}

	@Override
	void update() {
		// TODO Auto-generated method stub
		this.x += Math.cos(Math.toRadians(this.direction)) * speed;
		this.y += Math.sin(Math.toRadians(this.direction)) * speed;
	}

}
