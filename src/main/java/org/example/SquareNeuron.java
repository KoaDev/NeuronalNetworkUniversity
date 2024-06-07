package org.example;

import org.example.FFT.Complexe;
import org.example.Son.Son;
import org.example.neurone.Neurone;
import org.example.neurone.NeuroneSigmoide;
import org.example.neurone.iNeurone;
import org.example.utils.NeuronUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SquareNeuron {

    public final iNeurone neurone = new NeuroneSigmoide(44100);
    public SquareNeuron() {
        // Génération de signaux carrés pour augmenter le jeu de données
        List<float[]> caracteristiquesList = new ArrayList<>();
        List<Float> resultList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            float[] signal = NeuronUtil.genererSignalCarre(44100);
            Complexe[] resultatFFT = NeuronUtil.calculerFFT(signal);
            float[] caracteristiques = NeuronUtil.normaliserCaracteristiques(NeuronUtil.extraireCaracteristiques(resultatFFT));
            caracteristiquesList.add(caracteristiques);
            resultList.add(1.0f);  // Label pour les signaux carrés
        }

        // Génération de signaux faux (sinus et bruit) pour augmenter le jeu de données
        for (int i = 0; i < 10; i++) {

            // Générer des signaux sinusoïdaux
            float[] signalBruit = NeuronUtil.genererSignalBruit(44100);
            Complexe[] resultatFFTBruit = NeuronUtil.calculerFFT(signalBruit);
            float[] caracteristiquesBruit = NeuronUtil.normaliserCaracteristiques(NeuronUtil.extraireCaracteristiques(resultatFFTBruit));
            caracteristiquesList.add(caracteristiquesBruit);
            resultList.add(0.0f);  // Label pour les signaux sinusoïdaux

            // Générer des signaux sinusoïdaux
            float[] signalSinus = NeuronUtil.genererSignalSinus(44100);
            Complexe[] resultatFFTSinus = NeuronUtil.calculerFFT(signalSinus);
            float[] caracteristiquesSinus = NeuronUtil.normaliserCaracteristiques(NeuronUtil.extraireCaracteristiques(resultatFFTSinus));
            caracteristiquesList.add(caracteristiquesSinus);
            resultList.add(0.0f);  // Label pour les signaux sinusoïdaux

            float[] signalSinus2 = NeuronUtil.genererSignalDoubleSinus(44100);
            Complexe[] resultatFFTSinus2 = NeuronUtil.calculerFFT(signalSinus2);
            float[] caracteristiquesSinus2 = NeuronUtil.normaliserCaracteristiques(NeuronUtil.extraireCaracteristiques(resultatFFTSinus2));
            caracteristiquesList.add(caracteristiquesSinus2);
            resultList.add(0.0f);  // Label pour les signaux carrés

        }

        // Convertir la liste de caractéristiques et de résultats en tableau
        float[][] entrees = caracteristiquesList.toArray(new float[0][]);
        float[] resultats = new float[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            resultats[i] = resultList.get(i);
        }

        // Apprentissage
        System.out.println("Apprentissage du neurone avec des signaux carrés...");
        System.out.println("Fait en " + this.neurone.apprentissage(entrees, resultats) + " tours.");

        // Ajouter du bruit et évaluer la robustesse
        NeuronUtil.evaluatePrecision(this.neurone, entrees, resultats);
    }

    public void evaluateFile(String path) {
        Son squareSound = new Son(path);
        Complexe[] FTTSquare = NeuronUtil.calculerFFT(squareSound.donnees());
        float[] SignalSquare = NeuronUtil.normaliserCaracteristiques(NeuronUtil.extraireCaracteristiques(FTTSquare));

        // Évaluation du signal sinus
        System.out.println("\nRésultats pour un signal carré:");
        this.neurone.metAJour(SignalSquare);
        System.out.println("Sortie du neurone : " + neurone.sortie());
    }

    public void evaluateStream(float[] signal) {
        Complexe[] FTTNoisy = NeuronUtil.calculerFFT(signal);
        float[] SignalNoisy = NeuronUtil.normaliserCaracteristiques(NeuronUtil.extraireCaracteristiques(FTTNoisy));

        // Évaluation du signal bruité
        System.out.println("\nRésultats pour un signal carré:");
        this.neurone.metAJour(SignalNoisy);
        System.out.println("Sortie du neurone : " + neurone.sortie());
    }

}
