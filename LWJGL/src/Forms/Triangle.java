package Forms;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import All.Camera;
import All.ColorFloat;

public class Triangle extends Form {

	private Vector3f origin;

	public Triangle(Vector3f[] points, ColorFloat color, boolean model) {
		super(points, color, model);
	}

	public Triangle(Vector3f[] points) {
		super(points);
	}

	public Triangle(Vector3f origin, Vector3f[] pointsForTriangle, ColorFloat colorFloat, boolean model) {
		super(origin, pointsForTriangle, colorFloat, model);
		this.origin = super.origin;
	}

	@Override
	public void draw() {
		super.draw();

		GL11.glBegin(GL11.GL_TRIANGLES);
		for (Vector3f point3d : points) {
			GL11.glVertex3f(point3d.x + origin.x, point3d.y + origin.y, point3d.z + origin.z);
		}
		GL11.glEnd();
		GL11.glFlush();
	}

}
