package org.example;

import org.example.utils.NeuronUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SinusoidaleTwoNeuronTest {

    private final SinusoidaleTwoNeuron sinusoidaleTwoNeuron = new SinusoidaleTwoNeuron();

    @Test
    public void testRecognizeGeneratedDoubleSinusSignal() {
        // Générer un signal double sinusoidal que le neurone est censé reconnaître
        float[] signal = NeuronUtil.genererSignalDoubleSinus(44100);
        sinusoidaleTwoNeuron.evaluateStream(signal);

        // On s'attend à ce que la sortie soit proche de 1 pour les signaux connus
        assertTrue(sinusoidaleTwoNeuron.neurone.sortie() > 0.9, "Le neurone n'a pas reconnu le signal double sinusoidal généré.");
    }

    @Test
    public void testRecognizeNoisySignal() {
        // Générer un signal double sinusoidal et ajouter du bruit
        float[] signal = NeuronUtil.genererSignalDoubleSinus(44100);
        float[] noisySignal = NeuronUtil.addNoise(new float[][]{signal}, NeuronUtil.noiseLevel)[0];
        sinusoidaleTwoNeuron.evaluateStream(noisySignal);

        // On s'attend à ce que la sortie soit encore raisonnablement haute pour les signaux bruités
        assertTrue(sinusoidaleTwoNeuron.neurone.sortie() > 0.5, "Le neurone n'a pas bien reconnu le signal bruité.");
    }

    @Test
    public void testNonRecognizedSignal() {
        // Générer un signal carré que le neurone n'est pas censé reconnaître
        float[] signal = NeuronUtil.genererSignalCarre(44100);
        sinusoidaleTwoNeuron.evaluateStream(signal);

        // On s'attend à ce que la sortie soit basse pour les signaux inconnus
        assertTrue(sinusoidaleTwoNeuron.neurone.sortie() < 0.5, "Le neurone a mal classé le signal carré comme double sinusoidal.");
    }
}
