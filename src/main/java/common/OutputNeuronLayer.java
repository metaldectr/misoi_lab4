package common;

import java.util.Random;

/**
 * Created by romario on 12/16/14.
 */
public class OutputNeuronLayer {

	private final int OUTPUT_NEURONS_COUNT;
	private final double TEACH_SPEED = 1;
	private double outputLayerThreshold;
	private double[][] weightMatrix;
	private double[] currentInputs;
	private double[] currentSums;

	public OutputNeuronLayer(int outputNeuronCount, int inputsCount) {
		OUTPUT_NEURONS_COUNT = outputNeuronCount;
		init(inputsCount);
	}

	public double[] getResult(double[] inputs) {
		currentInputs = inputs;
		currentSums = new double[OUTPUT_NEURONS_COUNT];
		double[] result = new double[OUTPUT_NEURONS_COUNT];
		for (int i = 0; i < OUTPUT_NEURONS_COUNT; i++) {
			double sum = outputLayerThreshold;
			for (int j = 0; j < inputs.length; j++) {
				sum += inputs[j] * weightMatrix[j][i];
			}
			currentSums[i] = sum;
			result[i] = getSigmoid(sum);
		}
		return result;
	}

	public double[] getOutputErrors(double[] correctOutput, double[] currentOutput) {
		double[] result = new double[OUTPUT_NEURONS_COUNT];
		for (int i = 0; i < result.length; i++) {
			result[i] = (correctOutput[i] - currentOutput[i]) * getSigmoidDerivative(currentSums[i]);
		}
		return result;
	}

	public void correctWeights(double[] errors) {
		for (int i = 0; i < weightMatrix.length; i++) {
			for (int j = 0; j < weightMatrix[i].length; j++) {
				weightMatrix[i][j] += TEACH_SPEED * errors[j] * currentInputs[i];
			}
		}
	}

	public double[][] getWeightMatrix() {
		return weightMatrix;
	}

	private void init(int inputsCount) {
		weightMatrix = new double[inputsCount][OUTPUT_NEURONS_COUNT];
		Random random = new Random();
		outputLayerThreshold = random.nextDouble() - 0.5d;
		for (int i = 0; i < weightMatrix.length; i++) {
			for (int j = 0; j < weightMatrix[i].length; j++) {
				weightMatrix[i][j] = random.nextDouble() - 0.5d;
			}
		}
	}

	private double getSigmoid(double arg) {
		return ((double) 1 / (1 + Math.exp(-arg)));
	}

	private double getSigmoidDerivative(double arg) {
		return (getSigmoid(arg) * (1 - getSigmoid(arg)));
	}
}
