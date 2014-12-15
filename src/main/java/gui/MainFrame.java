package gui;

import common.ImageClass;
import common.ImageInput;
import common.ImageUtil;
import common.NeuralNetwork;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by romario on 12/16/14.
 */
public class MainFrame extends JFrame {

	private static final long serialVersionUID = -6228300482272874288L;

	private static final int DEFAULT_WIDTH = 800;
	private static final int DEFAULT_HEIGHT = 550;

	private ArrayList<ImagePanel> imagePanels;
	private ArrayList<ImageClass> imageClasses;
	private NeuralNetwork neuralNetwork;

	private ImagePanel classifiedImagePanel;

	private JMenuItem loadImagesItem;
	private JMenuItem loadClassifiedItem;
	private JMenuItem saveItem;

	private JButton teachButton;
	private JButton classifyButton;
	private JButton cleanClassifiedImagePanel;

	public MainFrame() {
		setTitle("Lab4");
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setResizable(false);

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		JMenu loadMenu = new JMenu("Load...");
		fileMenu.add(loadMenu);
		fileMenu.addSeparator();

		loadImagesItem = new JMenuItem("Images");
		loadMenu.add(loadImagesItem);
		loadClassifiedItem = new JMenuItem("Classified");
		loadMenu.add(loadClassifiedItem);

		saveItem = new JMenuItem("Save first image...");
		fileMenu.add(saveItem);

		teachButton = new JButton("Teach system");
		teachButton.setEnabled(false);
		classifyButton = new JButton("Classify image");
		classifyButton.setEnabled(false);
		cleanClassifiedImagePanel = new JButton("Clean");
		cleanClassifiedImagePanel.setEnabled(false);

		imagePanels = new ArrayList<ImagePanel>();
		ImagePanel firstImagePanel = new ImagePanel();
		imagePanels.add(firstImagePanel);
		ImagePanel secondImagePanel = new ImagePanel();
		imagePanels.add(secondImagePanel);
		ImagePanel thirdImagePanel = new ImagePanel();
		imagePanels.add(thirdImagePanel);
		classifiedImagePanel = new ImagePanel();

		loadImagesItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(".\\pictures\\lab4"));
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int r = chooser.showOpenDialog(MainFrame.this);

				if (r == JFileChooser.APPROVE_OPTION) {
					File imageDirectory = chooser.getSelectedFile();
					try {
						imageClasses = ImageUtil.loadImages(imageDirectory);
						int count = 0;
						for (ImagePanel p: imagePanels) {
							if (count < imageClasses.size()) {
								p.setImageMatrix(ImageUtil.getImageMatrix(imageClasses.get(count).getImages().get(0)));
								count++;
							}
						}
						teachButton.setEnabled(true);
					} catch (IOException ex) {
						JOptionPane.showMessageDialog(null, "Cannot load images!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		loadClassifiedItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(".\\pictures\\lab4"));
				int r = chooser.showOpenDialog(MainFrame.this);

				if (r == JFileChooser.APPROVE_OPTION) {
					File imageFile = chooser.getSelectedFile();
					try {
						ImageInput input = ImageUtil.loadSingleImage(imageFile);
						classifiedImagePanel.setImageMatrix(ImageUtil.getImageMatrix(input));
					} catch (IOException ex) {
						JOptionPane.showMessageDialog(null, "Cannot load images!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		saveItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setCurrentDirectory(new File(".\\pictures\\lab4"));
				int r = chooser.showSaveDialog(MainFrame.this);

				if (r == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					try {
						ImageUtil.writeImage(imagePanels.get(0).getImageMatrix(), file);
						JOptionPane.showMessageDialog(null, "Image was saved.", "Info", JOptionPane.INFORMATION_MESSAGE);
					} catch (IOException ex) {
						JOptionPane.showMessageDialog(null, "Cannot save image!", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		teachButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				neuralNetwork = new NeuralNetwork();
				neuralNetwork.teach(imageClasses);
				classifyButton.setEnabled(true);
				cleanClassifiedImagePanel.setEnabled(true);
			}
		});

		classifyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int classNumber = neuralNetwork.classify(ImageUtil.
						getImageInput(classifiedImagePanel.getImageMatrix()));
				JOptionPane.showMessageDialog(null, "Image belongs to " + imageClasses.get(classNumber).getName() +
						" class.", "Message", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		cleanClassifiedImagePanel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				classifiedImagePanel.clean();
			}
		});

		setLayout(new GridBagLayout());
		add(imagePanels.get(0), new GBC(0, 0, 1, 1).setWeight(1, 1).setFill(GBC.BOTH).setInsets(10, 50, 0, 0));
		add(imagePanels.get(1), new GBC(1, 0, 1, 1).setWeight(2, 1).setFill(GBC.BOTH).setInsets(10, 50, 0, 0));
		add(imagePanels.get(2), new GBC(2, 0, 1, 1).setWeight(2, 1).setFill(GBC.BOTH).setInsets(10, 0, 0, 0));
		add(teachButton, new GBC(0, 1, 1, 1).setInsets(20, 10, 10, 5));
		add(classifiedImagePanel, new GBC(0, 2, 1, 1).setWeight(1, 1).setFill(GBC.BOTH).setInsets(10, 50, 0, 0));
		add(classifyButton, new GBC(0, 3, 1, 1).setInsets(10, 10, 10, 0));
		add(cleanClassifiedImagePanel, new GBC(1, 3, 1, 1));
	}
}
