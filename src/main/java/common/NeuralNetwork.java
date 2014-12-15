package common;

import java.util.ArrayList;

/**
 * Created by romario on 12/16/14.
 */
public class NeuralNetwork {

	private static final int HIDDEN_NEURONS_COUNT = 10;
	private static final double ERROR_THRESHOLD = 0.01;
	private double[][] correctOutputs;
	private HiddenNeuronLayer hiddenNeuronLayer;
	private OutputNeuronLayer outputNeuronLayer;

	public void teach(ArrayList<ImageClass> imageClasses) {
		initNetwork(imageClasses);
		boolean flag = true;
		while (flag) {
			int classNumber = 0;
			flag = false;
			for (ImageClass cl: imageClasses) {
				for (ImageInput in: cl.getImages()) {
					int[] input = in.getImageInput();
					double[] hiddenResult = hiddenNeuronLayer.getResult(input);
					double[] outputResult = outputNeuronLayer.getResult(hiddenResult);
					if (checkError(outputResult, classNumber)) {
						flag = true;
						correctWeigths(outputResult, classNumber);
					}
				}
				classNumber++;
			}
		}
	}

	public int classify(ImageInput image) {
		int[] input = image.getImageInput();
		double[] hiddenResult = hiddenNeuronLayer.getResult(input);
		double[] outputResult = outputNeuronLayer.getResult(hiddenResult);
		double error = Double.MAX_VALUE;
		int classNumber = -1;
		for (int i = 0; i < correctOutputs.length; i++) {
			double tempError = 0;
			for (int j = 0; j < correctOutputs[i].length; j++) {
				tempError += Math.abs((correctOutputs[i][j] - outputResult[j]));
			}
			if (tempError < error) {
				error = tempError;
				classNumber = i;
			}
		}
		return classNumber;
	}

	private boolean checkError(double[] outputResult, int classNumber) {
		double maxError = Double.MIN_VALUE;
		for (int i = 0; i < correctOutputs[classNumber].length; i++) {
			if (Math.abs(correctOutputs[classNumber][i] - outputResult[i]) > maxError) {
				maxError = Math.abs(correctOutputs[classNumber][i] - outputResult[i]);
			}
		}
		if (maxError > ERROR_THRESHOLD) {
			return true;
		} else {
			return false;
		}
	}

	private void correctWeigths(double[] outputResult, int classNumber) {
		double[] outputErrors = outputNeuronLayer.getOutputErrors(correctOutputs[classNumber], outputResult);
		double[] hiddenErrors = hiddenNeuronLayer.getHiddenErrors(outputErrors, outputNeuronLayer.getWeightMatrix());
		outputNeuronLayer.correctWeights(outputErrors);
		hiddenNeuronLayer.correctWeights(hiddenErrors);
	}

	private void initNetwork(ArrayList<ImageClass> imageClasses) {
		int inputsCount = imageClasses.get(0).getImages().get(0).getImageInput().length;
		int classesCount = imageClasses.size();
		hiddenNeuronLayer = new HiddenNeuronLayer(HIDDEN_NEURONS_COUNT, inputsCount);
		outputNeuronLayer = new OutputNeuronLayer(classesCount, HIDDEN_NEURONS_COUNT);
		setCorrectOutputs(classesCount);
	}

	private void setCorrectOutputs(int classesCount) {
		correctOutputs = new double[classesCount][classesCount];
		for (int i = 0; i < correctOutputs.length; i++) {
			correctOutputs[i][i] = 1;
		}
	}
}
