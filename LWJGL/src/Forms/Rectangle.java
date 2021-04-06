package Forms;
import java.awt.Color;
import java.awt.image.BufferedImage;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.openvr.Texture;

import All.ColorFloat;
import All.TextureLoader;
import All.Window;

public class Rectangle extends Form {
	

	public Rectangle(Vector3f[] points) {
		super(points);
	}
	public Rectangle(Vector3f[] points, ColorFloat color, boolean model) {
		super(points, color, model);
	}
	public Rectangle(Vector3f origin, Vector3f[] points, ColorFloat color, boolean model) {
		super(origin, points, color, model);
	}

	@Override
	public void draw() {
		super.draw();
		GL11.glBegin(GL11.GL_QUADS);
		for (Vector3f point3d : points) {
			GL11.glVertex3f(point3d.x + origin.x, point3d.y + origin.y, point3d.z + origin.z);
		}
		GL11.glEnd();
		
	}
}
