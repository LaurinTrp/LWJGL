package Forms;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import org.joml.Vector3f;

import All.ColorFloat;

public class Mesh {

	private ArrayList<Triangle> allTriangles = new ArrayList<>();
	private ArrayList<Rectangle> allRectangles = new ArrayList<>();
	
	private Vector3f meshOrigin;

	public Mesh(Vector3f meshOrigin, ArrayList<Vector3f> points, ArrayList<int[]> indexes, boolean model,
			ColorFloat color) {

		this.meshOrigin = meshOrigin;
		
		Random random = new Random();
		for (int i = 0; i < indexes.size(); i++) {
			if (indexes.get(i)[0] == 3) {
				Vector3f[] pointsForTriangle = new Vector3f[3];
				for (int j = 1; j < indexes.get(i).length; j++) {

					pointsForTriangle[j - 1] = points.get(indexes.get(i)[j]);

				}
				Triangle triangle = null;
				if (color == null) {
					triangle = new Triangle(meshOrigin, pointsForTriangle,
							new ColorFloat(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1), model);
				} else {
					triangle = new Triangle(meshOrigin, pointsForTriangle, color, model);
				}
				allTriangles.add(triangle);
			} else if (indexes.get(i)[0] == 4) {

				Vector3f[] pointsForRectangle = new Vector3f[4];
				for (int j = 1; j < indexes.get(i).length; j++) {

					pointsForRectangle[j - 1] = points.get(indexes.get(i)[j]);

				}
				Rectangle rectangle = null;
				if (color == null) {
					rectangle = new Rectangle(meshOrigin, pointsForRectangle,
							new ColorFloat(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1), model);
				} else {
					rectangle = new Rectangle(meshOrigin, pointsForRectangle,
							color, model);
				}

				allRectangles.add(rectangle);
			}
		}

	}

	public void addToMeshOrigin(float x, float y, float z) {
		
		meshOrigin.add(x, y, z);
		
		for (Triangle triangle : allTriangles) {
			triangle.getOrigin().add(x, y, z);
		}
		for (Rectangle rectangle : allRectangles) {
			rectangle.getOrigin().add(x, y, z);
		}
	}
	
	public void setMeshOrigin(float x, float y, float z) {
		meshOrigin.set(x, y, z);
		for (Triangle triangle : allTriangles) {
			triangle.getOrigin().set(x, y, z);
		}
		for (Rectangle rectangle : allRectangles) {
			rectangle.getOrigin().set(x, y, z);
		}
	}

	public Vector3f getMeshOrigin() {
		return meshOrigin;
	}
	
	public float[] collisionBox() {

		Vector3f max, min, temp, origin;
		max = new Vector3f(-Float.MAX_VALUE, -Float.MAX_VALUE, -Float.MAX_VALUE);
		min = new Vector3f(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);

		for (Rectangle rectangle : allRectangles) {
			origin = rectangle.getOrigin();
			for (Vector3f vector : rectangle.points) {
				temp = new Vector3f(origin.x + vector.x, origin.y + vector.y, origin.z + vector.z);
				min.set(Math.min(min.x, temp.x), Math.min(min.y, temp.y), Math.min(min.z, temp.z));
				max.set(Math.max(max.x, temp.x), Math.max(max.y, temp.y), Math.max(max.z, temp.z));
			}
		}
		for (Triangle triangle : allTriangles) {
			origin = triangle.getOrigin();
			for (Vector3f vector : triangle.points) {
				temp = new Vector3f(origin.x + vector.x, origin.y + vector.y, origin.z + vector.z);
				min.set(Math.min(min.x, temp.x), Math.min(min.y, temp.y), Math.min(min.z, temp.z));
				max.set(Math.max(max.x, temp.x), Math.max(max.y, temp.y), Math.max(max.z, temp.z));
			}
		}

		return new float[] { min.x, max.x, min.y, max.y, min.z, max.z };
	}

	public boolean isColliding(float[] otherCollisionBox) {

		float[] thisCollisionBox = collisionBox();

		if ((otherCollisionBox[0] >= thisCollisionBox[0] && otherCollisionBox[0] <= thisCollisionBox[1]
				|| otherCollisionBox[1] >= thisCollisionBox[0] && otherCollisionBox[0] <= thisCollisionBox[1])
				&& (otherCollisionBox[2] >= thisCollisionBox[2] && otherCollisionBox[2] <= thisCollisionBox[3]
						|| otherCollisionBox[3] >= thisCollisionBox[2] && otherCollisionBox[2] <= thisCollisionBox[3])
				&& (otherCollisionBox[4] >= thisCollisionBox[4] && otherCollisionBox[4] <= thisCollisionBox[5]
						|| otherCollisionBox[5] >= thisCollisionBox[4]
								&& otherCollisionBox[4] <= thisCollisionBox[5])) {
			return true;
		}

		return false;

	}

	public void draw() {
		for (Triangle triangle : allTriangles) {
			triangle.draw();
		}
		for (Rectangle rectangle : allRectangles) {
			rectangle.draw();
		}
	}

}
