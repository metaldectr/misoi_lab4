package common;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by romario on 12/16/14.
 */
public class ImageUtil {

	public static void writeImage(int[][] imageMatrix, File file) throws IOException {
		FileWriter w = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(w);
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < imageMatrix.length; i++) {
			for (int j = 0; j < imageMatrix[i].length; j++) {
				builder.append(imageMatrix[i][j]);
				builder.append(" ");
			}
		}
		bw.write(builder.toString());
		bw.close();
	}

	public static ArrayList<ImageClass> loadImages(File imageDirectory) throws IOException {
		File[] files = imageDirectory.listFiles();
		ArrayList<ImageClass> result = new ArrayList<ImageClass>();
		for (int i = 0; i < files.length; i++) {
			if (files[i].isDirectory()) {
				File[] imageFiles = files[i].listFiles();
				ImageClass newClass = new ImageClass(files[i].getName());
				for (File f: imageFiles) {
					FileReader r = new FileReader(f);
					BufferedReader br = new BufferedReader(r);
					String line = br.readLine();
					br.close();
					String[] digits = line.split(" ");
					int[] imageInput = new int[digits.length];
					for (int j = 0; j < imageInput.length; j++) {
						imageInput[j] = Integer.parseInt(digits[j]);
					}
					ImageInput input = new ImageInput(imageInput);
					newClass.addImage(input);
				}
				result.add(newClass);
			}
		}
		return result;
	}

	public static ImageInput loadSingleImage(File imageFile) throws IOException {
		FileReader r = new FileReader(imageFile);
		BufferedReader br = new BufferedReader(r);
		String line = br.readLine();
		String[] digits = line.split(" ");
		br.close();
		int[] input = new int[digits.length];
		for (int i = 0; i < input.length; i++) {
			input[i] = Integer.parseInt(digits[i]);
		}
		ImageInput result = new ImageInput(input);
		return result;
	}

	public static int[][] getImageMatrix(ImageInput imageInput) {
		int size = (int) Math.sqrt(imageInput.getImageInput().length);
		int[][] imageMatrix = new int[size][size];
		int count = 0;
		for (int i = 0; i < imageMatrix.length; i++) {
			for (int j = 0; j < imageMatrix[i].length; j++) {
				imageMatrix[i][j] = imageInput.getImageInput()[count];
				count++;
			}
		}
		return imageMatrix;
	}

	public static ImageInput getImageInput(int[][] imageMatrix) {
		int size = imageMatrix.length * imageMatrix.length;
		int[] input = new int[size];
		int count = 0;
		for (int i = 0; i < imageMatrix.length; i++) {
			for (int j = 0; j < imageMatrix[i].length; j++) {
				input[count] = imageMatrix[i][j];
				count++;
			}
		}
		ImageInput result = new ImageInput(input);
		return result;
	}
}
