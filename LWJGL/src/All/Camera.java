package All;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLUtil;
import org.lwjglx.LWJGLUtil;

import Forms.Mesh;

public class Camera {

	private Vector3f position, rotation, currentPosition, currentRotation, movement, lastPosition;
	private Mesh collisionBox;
	private float velocity = 0.1f;
	private boolean colliding;

	public Camera(Vector3f position, Vector3f rotation) {
		this.position = position;
		this.rotation = rotation;
		this.currentPosition = position;
		this.currentRotation = rotation;
		this.lastPosition = position;
		movement = new Vector3f();

		collisionBox = new Mesh(position,
				PLY_Reader.getTriangles("/media/laurin/Laurin Festplatte/Blender/Models/box2.ply"),
				PLY_Reader.getIndexes("/media/laurin/Laurin Festplatte/Blender/Models/box2.ply"), true, null);
	}

	public Vector3f getPosition() {
		return position;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setPosition(Vector3f position) {
		this.position = position;

	}

	public void setRotation(Vector3f rotation) {
		this.rotation = rotation;
	}

	public void setPosition(float dx, float dy, float dz) {
		position.add(dx * velocity, dy * velocity, dz * velocity);
		currentPosition.add(dx * velocity, dy * velocity, dz * velocity);

		if (!(currentPosition.x == lastPosition.x && currentPosition.y == lastPosition.y && currentPosition.z == lastPosition.z)) {
			movement.set(dx, dy, dz);
		}else {
			movement.set(0,0,0);
		}

		lastPosition = currentPosition;

	}

	public void setRotation(float dx, float dy, float dz) {
		rotation.add(dx, dy, dz);
		currentRotation.add(dx, dy, dz);
		currentRotation.set(currentRotation.x % 360, currentRotation.y % 360, currentRotation.z % 360);
	}

	public boolean isColliding(float[] otherCollisionBox) {
		if (-currentPosition.x >= otherCollisionBox[0] && -currentPosition.x <= otherCollisionBox[1]
				&& -currentPosition.y >= otherCollisionBox[2] && -currentPosition.y <= otherCollisionBox[3]
				&& -currentPosition.z - 1 >= otherCollisionBox[4] && -currentPosition.z - 1 <= otherCollisionBox[5]) {
			colliding = true;
			return true;
		}
		return false;
	}

	public Vector3f getCurrentTranslation() {
		return currentPosition;
	}

	public void setCurrentTranslation(Vector3f currentTranslation) {
		this.currentPosition = currentTranslation;
	}

	public Vector3f getCurrentRotation() {
		return currentRotation;
	}

	public void setCurrentRotation(Vector3f currentRotation) {
		this.currentRotation = currentRotation;
	}

	public Mesh getCollisionBox() {
		return collisionBox;
	}

	public boolean isColliding() {
		return colliding;
	}

	public void setColliding(boolean colliding) {
		this.colliding = colliding;
	}

	public Vector3f getMovement() {
		return movement;
	}

}
