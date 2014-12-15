package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

/**
 * Created by romario on 12/16/14.
 */
public class ImagePanel extends JPanel {

	private static final long serialVersionUID = -4076149257415026764L;
	public static final int IMAGE_SIZE = 25;
	private static final int PIXELS_PER_POINT = 7;

	private int[][] imageMatrix;
	private BufferedImage backgroundImage;

	public ImagePanel() {
		init();
		this.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (!((e.getX() >= IMAGE_SIZE * PIXELS_PER_POINT) || (e.getY() >= IMAGE_SIZE * PIXELS_PER_POINT))) {
					inverseImageMatrix(e.getX(), e.getY());
					repaint();
				}
			}
		});
		this.addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (!((e.getX() >= IMAGE_SIZE * PIXELS_PER_POINT) || (e.getY() >= IMAGE_SIZE * PIXELS_PER_POINT) ||
						(e.getX() < 0) || (e.getY() < 0))) {
					fillImageMatrix(e.getX(), e.getY());
					repaint();
				}
			}
		});
		repaint();
	}

	public int[][] getImageMatrix() {
		return imageMatrix;
	}

	public void setImageMatrix(int[][] imageMatrix) {
		this.imageMatrix = imageMatrix;
		repaint();
	}

	public void clean() {
		imageMatrix = new int[IMAGE_SIZE][IMAGE_SIZE];
		for (int i = 0; i < IMAGE_SIZE; i++) {
			for (int j = 0; j < IMAGE_SIZE; j++) {
				imageMatrix[i][j] = -1;
			}
		}
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		repaintBackgroundImage();
		g.drawImage(backgroundImage, 0, 0, backgroundImage.getWidth(), backgroundImage.getHeight(), null);
	}

	private void fillImageMatrix(int panelX, int panelY) {
		int x = panelX / PIXELS_PER_POINT;
		int y = panelY / PIXELS_PER_POINT;
		if (imageMatrix[y][x] == -1) {
			imageMatrix[y][x] = 1;
		}
	}

	private void inverseImageMatrix(int panelX, int panelY) {
		int x = panelX / PIXELS_PER_POINT;
		int y = panelY / PIXELS_PER_POINT;
		imageMatrix[y][x] = -imageMatrix[y][x];
	}

	private void init() {
		imageMatrix = new int[IMAGE_SIZE][IMAGE_SIZE];
		for (int i = 0; i < IMAGE_SIZE; i++) {
			for (int j = 0; j < IMAGE_SIZE; j++) {
				imageMatrix[i][j] = -1;
			}
		}
		backgroundImage = new BufferedImage(IMAGE_SIZE * PIXELS_PER_POINT, IMAGE_SIZE * PIXELS_PER_POINT,
				BufferedImage.TYPE_INT_ARGB);
		int rgb = Color.WHITE.getRGB();
		for (int x = 0; x < backgroundImage.getWidth(); x++) {
			for (int y = 0; y < backgroundImage.getHeight(); y++) {
				backgroundImage.setRGB(x, y, rgb);
			}
		}
		rgb = Color.RED.getRGB();
		for (int x = 0; x < IMAGE_SIZE * PIXELS_PER_POINT; x += PIXELS_PER_POINT) {
			for (int y = 0; y < IMAGE_SIZE * PIXELS_PER_POINT; y++) {
				backgroundImage.setRGB(y, x, rgb);
				backgroundImage.setRGB(x, y, rgb);
			}
		}
	}

	private void repaintBackgroundImage() {
		int rgb = 0;
		for (int x = 0; x < IMAGE_SIZE; x++) {
			for (int y = 0; y < IMAGE_SIZE; y++) {
				if (imageMatrix[x][y] == 1) {
					rgb = Color.BLACK.getRGB();
				} else {
					rgb = Color.WHITE.getRGB();
				}
				for (int i = 1; i < PIXELS_PER_POINT; i++) {
					for (int j = 1; j < PIXELS_PER_POINT; j++) {
						backgroundImage.setRGB((y * PIXELS_PER_POINT) + i, (x * PIXELS_PER_POINT) + j, rgb);
					}
				}
			}
		}
	}
}
