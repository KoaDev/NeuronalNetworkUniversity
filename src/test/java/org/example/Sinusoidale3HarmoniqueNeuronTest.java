package org.example;

import org.example.utils.NeuronUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class Sinusoidale3HarmoniqueNeuronTest {

    private final Sinusoidale3HarmoniqueNeuron sinusoidale3HarmoniqueNeuron = new Sinusoidale3HarmoniqueNeuron();

    @Test
    public void testRecognizeGeneratedSinusoidale3HarmoniqueSignal() {
        // Générer un signal sinusoidale à 3 harmoniques que le neurone est censé reconnaître
        float[] signal = NeuronUtil.genererSignalSinusoidale3Harmonique(44100);
        sinusoidale3HarmoniqueNeuron.evaluateStream(signal);

        // On s'attend à ce que la sortie soit proche de 1 pour les signaux connus
        assertTrue(sinusoidale3HarmoniqueNeuron.neurone.sortie() > 0.9, "Le neurone n'a pas reconnu le signal sinusoidale à 3 harmoniques généré.");
    }

    @Test
    public void testRecognizeNoisySignal() {
        // Générer un signal sinusoidale à 3 harmoniques et ajouter du bruit
        float[] signal = NeuronUtil.genererSignalSinusoidale3Harmonique(44100);
        float[] noisySignal = NeuronUtil.addNoise(new float[][]{signal}, NeuronUtil.noiseLevel)[0];
        sinusoidale3HarmoniqueNeuron.evaluateStream(noisySignal);

        // On s'attend à ce que la sortie soit encore raisonnablement haute pour les signaux bruités
        assertTrue(sinusoidale3HarmoniqueNeuron.neurone.sortie() > 0.5, "Le neurone n'a pas bien reconnu le signal bruité.");
    }

    @Test
    public void testNonRecognizedSignal() {
        // Générer un signal carré que le neurone n'est pas censé reconnaître
        float[] signal = NeuronUtil.genererSignalCarre(44100);
        sinusoidale3HarmoniqueNeuron.evaluateStream(signal);

        // On s'attend à ce que la sortie soit basse pour les signaux inconnus
        assertTrue(sinusoidale3HarmoniqueNeuron.neurone.sortie() < 0.5, "Le neurone a mal classé le signal carré comme sinusoidale à 3 harmoniques.");
    }
}
