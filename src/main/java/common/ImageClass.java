package common;

import java.util.ArrayList;

/**
 * Created by romario on 12/16/14.
 */
public class ImageClass {

	private String name;
	private ArrayList<ImageInput> images = new ArrayList<ImageInput>();

	public ImageClass(String name) {
		this.name = name;
	}

	public void addImage(ImageInput image) {
		images.add(image);
	}

	public String getName() {
		return name;
	}

	public ArrayList<ImageInput> getImages() {
		return images;
	}

}
