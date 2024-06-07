package org.example;

import org.example.FFT.Complexe;
import org.example.Son.Son;
import org.example.neurone.NeuroneSigmoide;
import org.example.neurone.iNeurone;
import org.example.utils.NeuronUtil;

import java.util.ArrayList;
import java.util.List;

public class CombinaisonNeuron {

    public final iNeurone neurone = new NeuroneSigmoide(44100);
    public CombinaisonNeuron() {
        //Génération de signaux carré pour augmenter le jeu de données
        List<float[]> caracteristiquesList = new ArrayList<>();
        List<Float> resultList = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            float[] signal = NeuronUtil.genererSignalCombinaison(44100);
            Complexe[] resultatFFT = NeuronUtil.calculerFFT(signal);
            float[] caracteristiques = NeuronUtil.normaliserCaracteristiques(NeuronUtil.extraireCaracteristiques(resultatFFT));
            caracteristiquesList.add(caracteristiques);
            resultList.add(1.0f);  // Label pour les signaux carrés
        }

        // Convertir la liste de caractéristiques et de résultats en tableau
        float[][] entrees = caracteristiquesList.toArray(new float[0][]);
        float[] resultats = new float[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            resultats[i] = resultList.get(i);
        }

        //Apprentissage
        System.out.println("Apprentissage du neuron avec des signaux combinés...");
        System.out.println("Fait en " + this.neurone.apprentissage(entrees, resultats) + " tours.");

        // Ajouter du bruit et évaluer la robustesse
        NeuronUtil.evaluatePrecision(this.neurone, NeuronUtil.addNoise(entrees, NeuronUtil.noiseLevel), resultats);

    }

    public void evaluateFile(String path) {

        Son squareSound = new Son(path);
        Complexe[] FTTSquare = NeuronUtil.calculerFFT(squareSound.donnees());
        float[] SignalSquare = NeuronUtil.normaliserCaracteristiques(NeuronUtil.extraireCaracteristiques(FTTSquare));

        //Création du neurone
        System.out.println("\nResultats pour un signal genere (combinaison):");
        this.neurone.metAJour(SignalSquare);
        System.out.println("Sortie du neurone : " + neurone.sortie());

    }

    public void evaluateStream(float[] signal) {
        Complexe[] FTTNoisy = NeuronUtil.calculerFFT(signal);
        float[] SignalNoisy = NeuronUtil.normaliserCaracteristiques(NeuronUtil.extraireCaracteristiques(FTTNoisy));

        // Évaluation du signal bruité
        System.out.println("\nResultats pour un signal genere (combinaison):");
        this.neurone.metAJour(SignalNoisy);
        System.out.println("Sortie du neurone : " + neurone.sortie());
    }
}
