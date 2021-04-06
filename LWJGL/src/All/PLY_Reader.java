package All;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.joml.Vector3f;

public class PLY_Reader {

	public static ArrayList<Vector3f> getTriangles(String filePath) {

		ArrayList<String> lines = new ArrayList();
		ArrayList<Vector3f> points = new ArrayList();

		BufferedReader reader = null;
		try {

			reader = new BufferedReader(new FileReader(new File(filePath)));
			String line = reader.readLine();

			while (line != null) {
				lines.add(line);
				line = reader.readLine();
			}
			
			int triangleCount = 0;
			for (String currentLine : lines) {
				if (currentLine.contains("element vertex")) {
					triangleCount = Integer.parseInt(currentLine.split(" ")[2]);
				}
			}
			int triangleBegin = lines.indexOf("end_header") + 1;
			for (int i = triangleBegin; i < triangleBegin + triangleCount; i++) {
				String[] currentLine = lines.get(i).split(" ");
				
				Vector3f point = new Vector3f(Float.parseFloat(currentLine[0]),Float.parseFloat(currentLine[1]),Float.parseFloat(currentLine[2]));
				points.add(point);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return points;
	}
	
	public static ArrayList<int[]> getIndexes(String filePath) {
		ArrayList<String> lines = new ArrayList();
		ArrayList<int[]> indexes = new ArrayList();

		BufferedReader reader = null;
		try {

			reader = new BufferedReader(new FileReader(new File(filePath)));
			String line = reader.readLine();

			while (line != null) {
				lines.add(line);
				line = reader.readLine();
			}
			
			int triangleCount = 0;
			for (String currentLine : lines) {
				if (currentLine.contains("element vertex")) {
					triangleCount = Integer.parseInt(currentLine.split(" ")[2]);
				}
			}
			int triangleBegin = lines.indexOf("end_header") + 1;
			for (int i = triangleBegin + triangleCount; i < lines.size(); i++) {
				int index;
				String[] lineArray = lines.get(i).split(" ");
				int[] array = new int[lineArray.length];
				for (int j = 0; j < lineArray.length; j++) {
					array[j] = Integer.parseInt(lineArray[j]);
				}
				indexes.add(array);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return indexes;
	}	
	

}
