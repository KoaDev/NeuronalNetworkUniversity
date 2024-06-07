package org.example;

import org.example.FFT.Complexe;
import org.example.Son.Son;
import org.example.neurone.NeuroneSigmoide;
import org.example.neurone.iNeurone;
import org.example.utils.NeuronUtil;

import java.util.ArrayList;
import java.util.List;

public class NeuronSuperMax {

    public final iNeurone neurone = new NeuroneSigmoide(512);

    public NeuronSuperMax(String[] trainingPaths, float[] labels) {
        List<float[]> trainingData = new ArrayList<>();
        List<Float> labelsList = new ArrayList<>();

        for (int i = 0; i < trainingPaths.length; i++) {
            String path = trainingPaths[i];
            float label = labels[i];
            Son sound = new Son(path);

            int blockSize = 512; // Taille de chaque bloc de données
            int numberOfBlocks = (int) Math.ceil((double) sound.donnees().length / blockSize);

            for (int j = 0; j < numberOfBlocks; j++) {
                float[] block = sound.bloc_deTaille(j, blockSize);

                Complexe[] fftResult = NeuronUtil.calculerFFT(block);
                float[] characteristics = NeuronUtil.extraireCaracteristiques(fftResult);
                float[] normalizedCharacteristics = NeuronUtil.normaliserCaracteristiques(characteristics);

                trainingData.add(normalizedCharacteristics);
                labelsList.add(label); // Étiquette pour ce type de signal
            }
        }

        float[][] trainingDataArray = trainingData.toArray(new float[0][]);
        float[] labelsArray = new float[labelsList.size()];
        for (int i = 0; i < labelsList.size(); i++) {
            labelsArray[i] = labelsList.get(i);
        }

        System.out.println("Apprentissage du neurone avec des signaux variés...");
        System.out.println("Fait en " + neurone.apprentissage(trainingDataArray, labelsArray) + " tours.");
    }

    public void evaluateFile(String path) {
        Son sound = new Son(path);

        int blockSize = 512;
        int numberOfBlocks = (int) Math.ceil((double) sound.donnees().length / blockSize);

        float averageResult = 0;

        for (int i = 0; i < numberOfBlocks; i++) {
            float[] block = sound.bloc_deTaille(i, blockSize);

            Complexe[] fftResult = NeuronUtil.calculerFFT(block);
            float[] characteristics = NeuronUtil.extraireCaracteristiques(fftResult);
            float[] normalizedCharacteristics = NeuronUtil.normaliserCaracteristiques(characteristics);

            neurone.metAJour(normalizedCharacteristics);
            averageResult += neurone.sortie();
        }

        System.out.println("\nRésultat moyen pour le fichier " + path + " : " + averageResult / numberOfBlocks);
    }
}
