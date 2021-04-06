package All;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLUtil;
import org.lwjglx.LWJGLUtil;

public class Camera {
	
	private Vector3f position, rotation, currentRotation;
	public static Vector3f currentPosition;
	
	public Camera(Vector3f position, Vector3f rotation) {
		this.position = position;
		this.rotation = rotation;
		Camera.currentPosition = position;
		this.currentRotation = rotation;
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
		position.add(dx, dy, dz);
		currentPosition.add(dx, dy, dz);
	}
	

	public void setRotation(float dx, float dy, float dz) {
		rotation.add(dx, dy, dz);
		currentRotation.add(dx, dy, dz);
		currentRotation.set(currentRotation.x % 360, currentRotation.y % 360, currentRotation.z % 360);
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
	
}
