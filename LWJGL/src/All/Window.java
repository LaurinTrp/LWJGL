package All;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import Forms.Mesh;
import Forms.Rectangle;
import Forms.Triangle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.*;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;
import java.util.ArrayList;
import java.util.Iterator;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Window {

	private long window;
	private KeyHandler keyHandler;

	public void create() {

		final int width = 800, height = 800;
		init(width, height);

		loop();

		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		glfwTerminate();
		glfwSetErrorCallback(null).free();

	}

	private void init(int width, int height) {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		glfwWindowHint(GLFW_STENCIL_BITS, 4);
		glfwWindowHint(GLFW_SAMPLES, 4);

		window = glfwCreateWindow(width, height, "TEEEST", NULL, NULL);

		glfwSetWindowPos(window, screenSize.width / 2 - width / 2, screenSize.height / 2 - height / 2);

		glfwMakeContextCurrent(window);
		glfwSwapInterval(1);
		glfwShowWindow(window);

		keyHandler = new KeyHandler(window);

	}

	private void loop() {
		GL.createCapabilities();
		glClearColor(0, 0, 0, 1);

		GL11.glLoadIdentity();
		GL11.glMatrixMode(GL11.GL_PROJECTION_MATRIX);
		GL11.glFrustum(-1, 1, -1, 1, 1, 20);

		ArrayList<Vector3f> points = PLY_Reader
				.getTriangles("/media/laurin/Laurin Festplatte/Blender/Models/box.ply");

		ArrayList<int[]> indexes = PLY_Reader.getIndexes("/media/laurin/Laurin Festplatte/Blender/Models/box.ply");

		ArrayList<Mesh> meshes = new ArrayList<>();
		for (int i = 0; i < 2; i++) {
			meshes.add(new Mesh(new Vector3f(-i * 4, 0, 0), points, indexes, true));
		}
//		for (Vector3f vec : meshes.get(1).collisionBox()) {
//			System.err.println(vec);
//		}
//		System.err.println(meshes.get(1).collisionBox()[0]);
//		System.out.println(meshes.get(0).isColliding(meshes.get(1).collisionBox()));

		Mesh box1 = new Mesh(new Vector3f(0,0,0), points, indexes, true);
		Mesh box2 = new Mesh(new Vector3f(0,0,0), points, indexes, true);
		
		Mesh plane = new Mesh(new Vector3f(0, 0, 0),
				PLY_Reader.getTriangles("/media/laurin/Laurin Festplatte/Blender/Models/new.ply"),
				PLY_Reader.getIndexes("/media/laurin/Laurin Festplatte/Blender/Models/new.ply"), true);

		plane.collisionBox();

		Camera camera = new Camera(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0));
		while (true) {
			GL11.glLoadIdentity();
			GL11.glMatrixMode(GL11.GL_PROJECTION_MATRIX);
			GL11.glFrustum(-1, 1, -1, 1, 1, 20);
			keyHandler.updateKey(camera);

			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glDepthMask(true);
			GL11.glEnable(GL11.GL_DEPTH_TEST);

			for (Mesh mesh2 : meshes) {
				mesh2.draw();
			}
			System.out.println(meshes.get(0).isColliding(meshes.get(1).collisionBox()));
			meshes.get(1).addToMeshOrigin(0.001f, 0, 0);
//			meshes.get(1).collisionBox();
			
//			box1.draw();
//			box2.draw();
//			box1.collisionBox();
//			box1.isColliding(box2.collisionBox());
			
//			box1.addToMeshOrigin(0.001f, 0, 0);
			
//			plane.draw();

			glfwSwapBuffers(window);

			glfwPollEvents();

			camera.setRotation(new Vector3f());
			camera.setPosition(new Vector3f());
		}
	}

	private class KeyHandler {

		private long window;

		public KeyHandler(long window) {
			this.window = window;
		}

		private void updateKey(Camera camera) {

			if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GL_TRUE) {
				glfwDestroyWindow(window);
			}

			if (glfwGetKey(window, GLFW_KEY_UP) == GL_TRUE) {
				camera.setRotation(-1, 0, 0);
			}
			if (glfwGetKey(window, GLFW_KEY_DOWN) == GL_TRUE) {
				camera.setRotation(1, 0, 0);
			}
			if (glfwGetKey(window, GLFW_KEY_LEFT) == GL_TRUE) {
				camera.setRotation(0, -1, 0);
			}
			if (glfwGetKey(window, GLFW_KEY_RIGHT) == GL_TRUE) {
				camera.setRotation(0, 1, 0);
			}

			if (glfwGetKey(window, GLFW_KEY_W) == GL_TRUE) {
				camera.setPosition(-(float) Math.sin(Math.toRadians(camera.getCurrentRotation().y)),
						(float) Math.sin(Math.toRadians(camera.getCurrentRotation().x)),
						(float) Math.cos(Math.toRadians(camera.getCurrentRotation().y)));
			}
			if (glfwGetKey(window, GLFW_KEY_S) == GL_TRUE) {
				camera.setPosition((float) Math.sin(Math.toRadians(camera.getCurrentRotation().y)),
						-(float) Math.sin(Math.toRadians(camera.getCurrentRotation().x)),
						-(float) Math.cos(Math.toRadians(camera.getCurrentRotation().y)));
			}

			if (glfwGetKey(window, GLFW_KEY_A) == GL_TRUE) {
				camera.setPosition((float) Math.cos(Math.toRadians(camera.getCurrentRotation().y)), 0,
						(float) Math.sin(Math.toRadians(camera.getCurrentRotation().y)));
			}
			if (glfwGetKey(window, GLFW_KEY_D) == GL_TRUE) {
				camera.setPosition(-(float) Math.cos(Math.toRadians(camera.getCurrentRotation().y)), 0,
						-(float) Math.sin(Math.toRadians(camera.getCurrentRotation().y)));
			}

			if (glfwGetKey(window, GLFW_KEY_Q) == GL_TRUE) {
				camera.setPosition(0, -1f, 0);
			}
			if (glfwGetKey(window, GLFW_KEY_X) == GL_TRUE) {
				camera.setPosition(0, 1f, 0);
			}

			GL11.glRotatef(camera.getCurrentRotation().x, 1, 0, 0);
			GL11.glRotatef(camera.getCurrentRotation().y, 0, 1, 0);
			GL11.glRotatef(camera.getCurrentRotation().z, 0, 0, 1);
			GL11.glTranslatef(camera.getCurrentTranslation().x / 10, camera.getCurrentTranslation().y / 10,
					camera.getCurrentTranslation().z / 10);

		}

	}

}
