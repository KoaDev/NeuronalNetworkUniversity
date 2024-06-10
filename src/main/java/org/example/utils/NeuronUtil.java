package org.example.utils;

import org.example.FFT.Complexe;
import org.example.FFT.ComplexeCartesien;
import org.example.FFT.FFTCplx;

public class NeuronUtil {

    // Méthode pour extraire les caractéristiques du résultat de la FFT
    public static float[] extraireCaracteristiques(Complexe[] fftResult) {
        // Crée un tableau pour stocker les caractéristiques
        float[] caracteristiques = new float[fftResult.length];

        // Parcours chaque coefficient FFT
        for (int i = 0; i < fftResult.length; i++) {
            // Utilise le module des coefficients FFT comme caractéristique
            caracteristiques[i] = (float) fftResult[i].mod();
        }

        // Retourne le tableau de caractéristiques
        return caracteristiques;
    }

    // Méthode pour normaliser les caractéristiques
    public static float[] normaliserCaracteristiques(float[] caracteristiques) {
        float max = 0;

        // Trouve la valeur maximale dans les caractéristiques
        for (float value : caracteristiques) {
            if (value > max) {
                max = value;
            }
        }

        // Crée un tableau pour les caractéristiques normalisées
        float[] normalised = new float[caracteristiques.length];

        // Normalise chaque caractéristique en la divisant par la valeur maximale
        for (int i = 0; i < caracteristiques.length; i++) {
            normalised[i] = caracteristiques[i] / max;
        }

        // Retourne le tableau de caractéristiques normalisées
        return normalised;
    }

    // Méthode pour calculer la FFT d'un signal audio
    public static Complexe[] calculerFFT(float[] donneesAudio) {
        // Crée un tableau de nombres complexes à partir des données audio
        Complexe[] signal = new Complexe[donneesAudio.length];
        for (int i = 0; i < donneesAudio.length; i++) {
            // Crée des nombres complexes avec la partie imaginaire à zéro
            signal[i] = new ComplexeCartesien(donneesAudio[i], 0);
        }

        // Applique la FFT sur le signal et retourne le résultat
        return FFTCplx.appliqueSur(signal);
    }
}
