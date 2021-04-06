package Forms;

import java.awt.Color;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import All.ColorFloat;

public abstract class Form {

	public Vector3f origin;
	public Vector3f[] points;
	public ColorFloat color;

	public Form(Vector3f[] points) {
		this.points = points;
	}

	public Form(Vector3f[] points, ColorFloat color, boolean model) {
		this.points = points;
		this.color = color;

		if (model) {
			for (Vector3f vector3f : points) {
				vector3f.set(vector3f.x, vector3f.z, vector3f.y);
			}
		}
	}

	public Form(Vector3f origin, Vector3f[] points, ColorFloat color, boolean model) {

		this.origin = origin;
		this.points = points;
		this.color = color;

		if (model) {
			for (Vector3f vector3f : points) {
				vector3f.set(vector3f.x, vector3f.z, vector3f.y);
			}
		}
	}

	public void draw() {
		if (color != null) {
			GL11.glColor4f(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
		}
	}

	
	public Vector3f getOrigin() {
		return origin;
	}
	
}
