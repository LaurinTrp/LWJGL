package Forms;
import java.util.ArrayList;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;


public class Polygon {
	
	private ArrayList<Vector3f> points = new ArrayList<>();
	
	public Polygon(Vector3f[] points) {
		for (Vector3f point3d : points) {
			this.points.add(point3d);
		}
	}
	
	public void draw() {

		GL11.glColor4f(0, 1, 1, 1);
		GL11.glBegin(GL11.GL_TRIANGLES);
		for (Vector3f point3d : points) {
			GL11.glVertex2f(point3d.x * 10, point3d.y*10);
		}
		GL11.glEnd();
		
	}
	
	public void addVector3f(Vector3f point3d) {
		points.add(point3d);
	}
	
	
}
