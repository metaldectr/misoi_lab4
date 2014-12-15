package common;

import java.util.ArrayList;

/**
 * Created by romario on 12/16/14.
 */
public class ImageInput {

	private int[] imageInput;

	public ImageInput(int[] imageInput) {
		this.imageInput = imageInput;
	}

	public ImageInput(int[][] imageMatrix) {
		convert(imageMatrix);
	}

	public int[] getImageInput() {
		return imageInput;
	}

	private void convert(int[][] imageMatrix) {
		imageInput = new int[imageMatrix.length * imageMatrix.length];
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for (int i = 0; i < imageMatrix.length; i++) {
			for (int j = 0; j < imageMatrix[i].length; j++) {
				temp.add(imageMatrix[i][j]);
			}
		}
		for (int i = 0; i < imageInput.length; i++) {
			imageInput[i] = temp.get(i);
		}
	}
}
