package game.model;

import constants.ConfigConstant;
import game.logic.MapCell;
import game.logic.MapCellHolder;
import game.logic.Renderable;

public abstract class Ship extends Entity implements Renderable {

	protected double bulletSpeed;
	protected int bulletDamage;
	protected int hp;
	protected int maxHp;
	protected double speed;
	protected double maxSpeed;
	protected double accelerate;
	protected int turnRate;
	protected int direction;

	public Ship(double x, double y, int hp, int maxHp, double speed, double maxSpeed, double accelerate, int turnRate,
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
		this.z = 2;
		this.bulletDamage = 5;
		this.bulletSpeed = 20;
	}
	
	public double getBulletSpeed() {
		return bulletSpeed;
	}

	public void setBulletSpeed(double bulletSpeed) {
		this.bulletSpeed = bulletSpeed;
	}

	public int getBulletDamage() {
		return bulletDamage;
	}

	public void setBulletDamage(int bulletDamage) {
		this.bulletDamage = bulletDamage;
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

	public void setMaxSpeed(double maxSpeed) {
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
			if (this.direction < -180)
				this.direction += 360;
		} else {
			this.direction += this.turnRate;
			if (this.direction >= 180)
				this.direction -= 360;
		}
	}

	public void hit(int damage) {
		this.hp = this.hp - damage;
		if (this.hp < 0) {
			this.hp = 0;
			this.destroyed = true;
		}
	}
	public void heal(){
		this.hp = this.maxHp;
	}

	public void shoot() {
		MapCell mc = MapCellHolder.instance.getPlayerCell();
		mc.getEntities()
				.add(new Bullet(this.x + Math.cos(Math.toRadians(this.direction)) * 30,
						this.y + Math.sin(Math.toRadians(this.direction)) * 30, this.bulletSpeed,
						this.direction, this.bulletDamage, this));
	}

	@Override
	public void update() {

		MapCell mc = MapCellHolder.instance.getPlayerCell();
		mc.addNewEffect(new TailShipEffect(this.x + Math.cos(Math.toRadians(this.direction + 180)) * 10,
				this.y + Math.sin(Math.toRadians(this.direction + 180)) * 10, 40, this.direction));
		this.x += Math.cos(Math.toRadians(this.direction)) * speed;
		this.y += Math.sin(Math.toRadians(this.direction)) * speed;
		if (this.x < radius) {
			this.x = radius;
		}
		if (this.x > ConfigConstant.mapCellWidth - radius) {
			this.x = ConfigConstant.mapCellWidth - radius;
		}
		if (this.y < radius) {
			this.y = radius;
		}
		if (this.y > ConfigConstant.mapCellHeight - radius) {
			this.y = ConfigConstant.mapCellHeight - radius;
		}
	}

}
