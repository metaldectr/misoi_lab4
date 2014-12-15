package common;

import java.util.Random;

/**
 * Created by romario on 12/16/14.
 */
public class HiddenNeuronLayer {

	private final int HIDDEN_NEURONS_COUNT;
	private final double TEACH_SPEED = 1;
	private double hiddenLayerThreshold;
	private double[][] weightMatrix;
	private int[] currentInputs;
	private double[] currentSums;

	public HiddenNeuronLayer(int hiddenNeuronsCount, int inputsCount) {
		HIDDEN_NEURONS_COUNT = hiddenNeuronsCount;
		init(inputsCount);
	}

	public double[] getResult(int[] inputs) {
		currentInputs = inputs;
		currentSums = new double[HIDDEN_NEURONS_COUNT];
		double[] result = new double[HIDDEN_NEURONS_COUNT];
		for (int i = 0; i < HIDDEN_NEURONS_COUNT; i++) {
			double sum = hiddenLayerThreshold;
			for (int j = 0; j < inputs.length; j++) {
				sum += inputs[j] * weightMatrix[j][i];
			}
			currentSums[i] = sum;
			result[i] = getSigmoid(sum);
		}
		return result;
	}

	public double[] getHiddenErrors(double[] outputErrors, double[][] hiddenOutputWeightMatrix) {
		double[] result = new double[HIDDEN_NEURONS_COUNT];
		for (int i = 0; i < result.length; i++) {
			result[i] = 0;
			for (int j = 0; j < hiddenOutputWeightMatrix[i].length; j++) {
				result[i] += outputErrors[j] * hiddenOutputWeightMatrix[i][j];
			}
			result[i] = result[i] * getSigmoidDerivative(currentSums[i]);
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

	private void init(int inputsCount) {
		weightMatrix = new double[inputsCount][HIDDEN_NEURONS_COUNT];
		Random random = new Random();
		hiddenLayerThreshold = random.nextDouble() - 0.5d;
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
