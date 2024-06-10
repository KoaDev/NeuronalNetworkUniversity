package org.example;

import org.example.FFT.Complexe;
import org.example.Son.Son;
import org.example.neurone.NeuroneSigmoide;
import org.example.neurone.iNeurone;
import org.example.utils.NeuronUtil;

import java.util.ArrayList;
import java.util.List;

public class NeuronSuperMax {

    // Déclaration d'un neurone utilisant la fonction sigmoïde avec une taille de 512
    public final iNeurone neurone = new NeuroneSigmoide(512);

    private final String name;

    // Constructeur de la classe NeuronSuperMax
    public NeuronSuperMax(String[] trainingPaths, float[] labels, String name) {
        this.name = name; // Nom du neurone pour identifier le modèle
        List<float[]> trainingData = new ArrayList<>(); // Liste pour stocker les données d'entraînement
        List<Float> labelsList = new ArrayList<>(); // Liste pour stocker les étiquettes des données

        // Boucle pour traiter chaque fichier de données d'entraînement
        for (int i = 0; i < trainingPaths.length; i++) {
            String path = trainingPaths[i]; // Chemin du fichier de données
            float label = labels[i]; // Étiquette associée au fichier
            Son sound = new Son(path); // Chargement du son depuis le fichier
            Son noise = new Son("src/main/resources/Bruit.wav"); // Chargement du bruit depuis le fichier

            float[] allData = sound.donnees(); // Données du son original
            float[] noiseData = noise.donnees(); // Données du bruit

            // Ajout du bruit au signal original pour créer un signal bruité
            float[] noisyData = new float[allData.length];
            for (int j = 0; j < allData.length; j++) {
                noisyData[j] = allData[j] + noiseData[j % noiseData.length];
            }

            // Séparation des données en 80% pour l'entraînement et 20% pour le test
            int trainingLength = (int) (noisyData.length * 0.8);
            float[] trainingDataArray = new float[trainingLength];
            System.arraycopy(noisyData, 0, trainingDataArray, 0, trainingLength);

            // Normalisation des données d'entraînement
            float[] normalizedTrainingData = NeuronUtil.normaliserCaracteristiques(trainingDataArray);

            int blockSize = 512; // Taille de chaque bloc de données pour la FFT
            int numberOfBlocks = (int) ((double) normalizedTrainingData.length / blockSize);

            // Boucle pour traiter chaque bloc de données
            for (int j = 0; j < numberOfBlocks; j++) {
                float[] block = new float[blockSize];
                System.arraycopy(normalizedTrainingData, j * blockSize, block, 0, Math.min(blockSize, normalizedTrainingData.length - j * blockSize));

                Complexe[] fftResult = NeuronUtil.calculerFFT(block); // Calcul de la FFT pour le bloc
                float[] characteristics = NeuronUtil.extraireCaracteristiques(fftResult); // Extraction des caractéristiques du signal
                trainingData.add(characteristics); // Ajout des caractéristiques à la liste d'entraînement
                labelsList.add(label); // Ajout de l'étiquette correspondante
            }
        }

        // Conversion des listes de données d'entraînement et d'étiquettes en tableaux
        float[][] trainingDataArray = trainingData.toArray(new float[0][]);
        float[] labelsArray = new float[labelsList.size()];
        for (int i = 0; i < labelsList.size(); i++) {
            labelsArray[i] = labelsList.get(i);
        }

        // Apprentissage du neurone avec les données d'entraînement
        neurone.apprentissage(trainingDataArray, labelsArray);
        System.out.println("Apprentissage du neurone " + name + "...");
    }

    // Méthode pour évaluer un fichier de données
    public void evaluateFile(String path) {
        System.out.println(name + "...");

        Son sound = new Son(path); // Chargement du son depuis le fichier
        Son noise = new Son("src/main/resources/Bruit.wav"); // Chargement du bruit depuis le fichier

        float[] soundData = sound.donnees(); // Données du son original
        float[] noiseData = noise.donnees(); // Données du bruit

        // Ajout du bruit au signal original pour créer un signal bruité
        float[] noisyData = new float[soundData.length];
        for (int i = 0; i < soundData.length; i++) {
            noisyData[i] = soundData[i] + noiseData[i % noiseData.length];
        }

        // Utilisation des 20% restants pour l'évaluation
        int testStartIndex = (int) (noisyData.length * 0.8);
        float[] testData = new float[noisyData.length - testStartIndex];
        System.arraycopy(noisyData, testStartIndex, testData, 0, testData.length);

        int blockSize = 512; // Taille de chaque bloc de données pour la FFT
        int numberOfBlocks = (int) ((double) testData.length / blockSize);

        float averageResult = 0; // Variable pour stocker la moyenne des résultats

        // Boucle pour traiter chaque bloc de données de test
        for (int i = 0; i < numberOfBlocks; i++) {
            float[] block = new float[blockSize];
            System.arraycopy(testData, i * blockSize, block, 0, Math.min(blockSize, testData.length - i * blockSize));

            Complexe[] fftResult = NeuronUtil.calculerFFT(block); // Calcul de la FFT pour le bloc
            float[] characteristics = NeuronUtil.extraireCaracteristiques(fftResult); // Extraction des caractéristiques du signal
            float[] normalizedCharacteristics = characteristics; // Normalisation des caractéristiques (si nécessaire)

            neurone.metAJour(normalizedCharacteristics); // Mise à jour du neurone avec les caractéristiques normalisées
            averageResult += neurone.sortie(); // Ajout du résultat à la somme des résultats
        }

        // Affichage du résultat moyen pour le fichier évalué
        System.out.println("Résultat moyen pour le fichier " + path + " : " + averageResult / numberOfBlocks + "\n");
    }
}
