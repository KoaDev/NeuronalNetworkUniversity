package org.example.utils;

import org.example.FFT.Complexe;
import org.example.FFT.ComplexeCartesien;
import org.example.FFT.FFTCplx;
import org.example.neurone.iNeurone;

import java.util.Random;

public class NeuronUtil {

    public static float noiseLevel = 0.15f; // Niveau de bruit (15%)
    private static final Random random = new Random();

    public static float[] extraireCaracteristiques(Complexe[] fftResult) {
        float[] caracteristiques = new float[fftResult.length];
        for (int i = 0; i < fftResult.length; i++) {
            caracteristiques[i] = (float) fftResult[i].mod(); // Par exemple, utiliser le module des coefficients FFT
        }
        return caracteristiques;
    }

    public static float[] normaliserCaracteristiques(float[] caracteristiques) {
        float max = 0;
        for (float value : caracteristiques) {
            if (value > max) {
                max = value;
            }
        }
        float[] normalised = new float[caracteristiques.length];
        for (int i = 0; i < caracteristiques.length; i++) {
            normalised[i] = caracteristiques[i] / max;
        }
        return normalised;
    }

    public static Complexe[] calculerFFT(float[] donneesAudio) {
        Complexe[] signal = new Complexe[donneesAudio.length];
        for (int i = 0; i < donneesAudio.length; i++)
            signal[i] = new ComplexeCartesien(donneesAudio[i], 0); // Créer des nombres complexes avec la partie imaginaire à zéro

        // Appliquer la FFT sur le signal
        return FFTCplx.appliqueSur(signal);
    }

    public static void evaluatePrecision(iNeurone n, float[][] entrees, float[] resultats) {
        float totalPrecision = 0.0f;
        for (int i = 0; i < entrees.length; ++i) {
            // Pour une entrée donnée
            final float[] entree = entrees[i];
            // Mettre à jour la sortie du neurone
            n.metAJour(entree);
            // Calculer la précision pour cette entrée
            float precision = 1.0f - Math.abs(resultats[i] - n.sortie());
            totalPrecision += precision;
            // Afficher cette sortie
            //System.out.println("Entree " + i + " : " + n.sortie() + " (Precision : " + precision + ")");
        }
        // Moyenne de précision
        float averagePrecision = totalPrecision / entrees.length;
        System.out.println("Precision moyenne : " + averagePrecision);
    }

    public static float[][] addNoise(float[][] original, float noiseLevel) {
        Random random = new Random();
        float[][] noisy = new float[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original[i].length; j++) {
                noisy[i][j] = original[i][j] + noiseLevel * (2 * random.nextFloat() - 1); // ajouter du bruit entre -noiseLevel et +noiseLevel
            }
        }
        return noisy;
    }
}
