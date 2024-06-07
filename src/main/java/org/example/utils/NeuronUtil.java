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

    public static float[] generateSignalBruit(int tauxEchantillonnage) {
        float[] signal = new float[tauxEchantillonnage];
        Random random = new Random();
        for (int i = 0; i < tauxEchantillonnage; i++) {
            signal[i] = (float) Math.sin(random.nextFloat() * random.nextFloat() - random.nextFloat());
        }
        return signal;
    }
    public static float[] genererSignalCarre(int tauxEchantillonnage) {
        float[] signal = new float[tauxEchantillonnage];
        // Ajouter une variation aléatoire à la fréquence
        int baseFrequence = 22050;
        int frequence = baseFrequence + random.nextInt(200) - 100; // fréquence aléatoire entre 430 et 450
        int periode = (int) (tauxEchantillonnage / frequence);
        for (int i = 0; i < tauxEchantillonnage; i++) {
            signal[i] = i % periode < periode / 2 ? (float) 1 : (float) -1;
        }
        return signal;
    }

    public static float[] genererSignalSinus(int tauxEchantillonnage) {
        float[] signal = new float[tauxEchantillonnage];
        // Ajouter une variation aléatoire à la fréquence et à l'amplitude
        int baseFrequence = 22050;
        int frequence = baseFrequence + random.nextInt(200) - 100; // fréquence aléatoire entre 430 et 450
        float amplitude = 1 + random.nextFloat() * 0.2f - 0.1f; // amplitude aléatoire entre 0.9 et 1.1
        for (int i = 0; i < tauxEchantillonnage; i++) {
            signal[i] = amplitude * (float) Math.sin(2 * Math.PI * frequence * i / tauxEchantillonnage);
        }
        return signal;
    }

    public static float[] genererSignalCombinaison(int tauxEchantillonnage) {
        float[] signal = new float[tauxEchantillonnage];
        // Ajouter une variation aléatoire à la fréquence
        int baseFrequence = 22050;
        int frequence = baseFrequence + random.nextInt(200) - 100; // fréquence aléatoire entre 430 et 450
        int periode = (int) (tauxEchantillonnage / frequence);
        // Combinaison de signal carré et sinusoidal
        for (int i = 0; i < tauxEchantillonnage; i++) {
            float square = (i % periode < periode / 2) ? 1 : -1;
            float sinus = (float) Math.sin(2 * Math.PI * frequence * i / tauxEchantillonnage) ;
            float cosinus = (float) Math.cos(2 * Math.PI * frequence * i / tauxEchantillonnage) ;
            signal[i] = (square + sinus + cosinus) / 2; // combinaison des deux signaux
        }
        return signal;
    }

    public static float[] genererSignalBruit(int tauxEchantillonnage) {
        float[] signal = new float[tauxEchantillonnage];
        for (int i = 0; i < tauxEchantillonnage; i++) {
            // Ajouter un bruit aléatoire
            signal[i] = (float) (random.nextFloat() * 2 - 1); // bruit aléatoire entre -1 et 1
        }
        return signal;
    }

    public static float[] genererSignalDoubleSinus(int tauxEchantillonnage) {
        float[] signal = new float[tauxEchantillonnage];
        // Ajouter des variations aléatoires aux fréquences et amplitudes des deux sinusoïdes
        int baseFrequence1 = 22050;
        int frequence1 = baseFrequence1 + random.nextInt(200) - 100; // fréquence aléatoire entre 430 et 450
        int baseFrequence2 = 22050 * 2;
        int frequence2 = baseFrequence2 + random.nextInt(200) - 100; // fréquence 2 aléatoire entre 870 et 890
        float amplitude1 = 1 + random.nextFloat() * 0.2f - 0.1f; // amplitude 1 aléatoire entre 0.9 et 1.1
        float amplitude2 = 1 + random.nextFloat() * 0.2f - 0.1f; // amplitude 2 aléatoire entre 0.9 et 1.1
        for (int i = 0; i < tauxEchantillonnage; i++) {
            signal[i] = amplitude1 * (float) Math.sin(2 * Math.PI * frequence1 * i / tauxEchantillonnage) +
                    amplitude2 * (float) Math.sin(2 * Math.PI * frequence2 * i / tauxEchantillonnage);
        }
        return signal;
    }

    public static float[] genererSignalSinusoidale3Harmonique(int tauxEchantillonnage) {
        float[] signal = new float[tauxEchantillonnage];
        // Ajouter des variations aléatoires aux fréquences et amplitudes des trois sinusoïdes
        int baseFrequence1 = 22050;
        int frequence1 = baseFrequence1 + random.nextInt(200) - 100; // fréquence 1 aléatoire entre 430 et 450
        int baseFrequence2 = 22050 * 2;
        int frequence2 = baseFrequence2 + random.nextInt(200) - 100; // fréquence 2 aléatoire entre 870 et 890
        int baseFrequence3 = 22050 * 3;
        int frequence3 = baseFrequence3 + random.nextInt(200) - 100; // fréquence 3 aléatoire entre 1310 et 1330
        float amplitude1 = 1 + random.nextFloat() * 0.2f - 0.1f; // amplitude 1 aléatoire entre 0.9 et 1.1
        float amplitude2 = 1 + random.nextFloat() * 0.2f - 0.1f; // amplitude 2 aléatoire entre 0.9 et 1.1
        float amplitude3 = 1 + random.nextFloat() * 0.2f - 0.1f; // amplitude 3 aléatoire entre 0.9 et 1.1
        for (int i = 0; i < tauxEchantillonnage; i++) {
            signal[i] = amplitude1 * (float) Math.sin(2 * Math.PI * frequence1 * i / tauxEchantillonnage) +
                    amplitude2 * (float) Math.sin(2 * Math.PI * frequence2 * i / tauxEchantillonnage) +
                    amplitude3 * (float) Math.sin(2 * Math.PI * frequence3 * i / tauxEchantillonnage);
        }
        return signal;
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
