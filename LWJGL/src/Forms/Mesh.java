package Forms;

import java.util.ArrayList;
import java.util.Random;

import org.joml.Vector3f;

import All.ColorFloat;

public class Mesh {

	private ArrayList<Triangle> allTriangles = new ArrayList<>();
	private ArrayList<Rectangle> allRectangles = new ArrayList<>();

	public Mesh(Vector3f meshOrigin, ArrayList<Vector3f> points, ArrayList<int[]> indexes, boolean model) {

		Random random = new Random();
		for (int i = 0; i < indexes.size(); i++) {
			if (indexes.get(i)[0] == 3) {
				Vector3f[] pointsForTriangle = new Vector3f[3];
				for (int j = 1; j < indexes.get(i).length; j++) {

					pointsForTriangle[j - 1] = points.get(indexes.get(i)[j]);

				}
				Triangle triangle = new Triangle(meshOrigin, pointsForTriangle,
						new ColorFloat(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1), model);

				allTriangles.add(triangle);
			} else if (indexes.get(i)[0] == 4) {

				Vector3f[] pointsForRectangle = new Vector3f[4];
				for (int j = 1; j < indexes.get(i).length; j++) {

					pointsForRectangle[j - 1] = points.get(indexes.get(i)[j]);

				}
				Rectangle rectangle = new Rectangle(meshOrigin, pointsForRectangle,
						new ColorFloat(random.nextFloat(), random.nextFloat(), random.nextFloat(), 1), model);

				allRectangles.add(rectangle);
			}
		}

	}

	public void addToMeshOrigin(float x, float y, float z) {
		for (Triangle triangle : allTriangles) {
			triangle.getOrigin().add(x, y, z);
		}
		for (Rectangle rectangle : allRectangles) {
			rectangle.getOrigin().add(x, y, z);
		}
	}

	public Vector3f[] collisionBox() {

		Vector3f max, min, temp;
		max = new Vector3f(Float.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE);
		min = new Vector3f(Float.MIN_VALUE, Float.MIN_VALUE, Float.MIN_VALUE);

		for (Rectangle rectangle : allRectangles) {
			for (Vector3f vector : rectangle.points) {
				temp = vector.add(rectangle.origin);
				min.set(Math.min(min.x, temp.x), Math.min(min.y, temp.y), Math.min(min.z, temp.z));
				max.set(Math.max(max.x, temp.x), Math.max(max.y, temp.y), Math.max(max.z, temp.z));
				temp.add(rectangle.origin.negate());
			}
		}
		
		
		return new Vector3f[] { min, max };
	}

	public boolean isColliding(Vector3f[] otherCollisionBox) {
		
		if(collisionBox()[0].x <= otherCollisionBox[0].x && collisionBox()[1].x >= otherCollisionBox[1].x && 
				collisionBox()[0].y <= otherCollisionBox[0].y && collisionBox()[1].y >= otherCollisionBox[1].y && 
				collisionBox()[0].z <= otherCollisionBox[0].z && collisionBox()[1].z >= otherCollisionBox[1].z) {
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
