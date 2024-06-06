package org.example;

import org.example.utils.NeuronUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SinusoidaleNeuronTest {

    private final SinusoidaleNeuron sinusoidaleNeuron = new SinusoidaleNeuron();

    @Test
    public void testRecognizeGeneratedSignal() {
        // Générer un signal que le neurone est censé reconnaître
        float[] signal = NeuronUtil.genererSignalSinus(44100);
        sinusoidaleNeuron.evaluateStream(signal);

        // On s'attend à ce que la sortie soit proche de 1 pour les signaux connus
        assertTrue(sinusoidaleNeuron.neurone.sortie() > 0.9, "Le neurone n'a pas reconnu le signal généré.");
    }

    @Test
    public void testRecognizeNoisySignal() {
        // Générer un signal bruité
        float[] signal = NeuronUtil.genererSignalSinus(44100);
        float[] noisySignal = NeuronUtil.addNoise(new float[][]{signal}, NeuronUtil.noiseLevel)[0];
        sinusoidaleNeuron.evaluateStream(noisySignal);

        // On s'attend à ce que la sortie soit encore raisonnablement haute pour les signaux bruités
        assertTrue(sinusoidaleNeuron.neurone.sortie() > 0.5, "Le neurone n'a pas bien reconnu le signal bruité.");
    }

    @Test
    public void testNonRecognizedSignal() {
        // Générer un signal que le neurone n'est pas censé reconnaître
        float[] signal = NeuronUtil.genererSignalBruit(44100);
        sinusoidaleNeuron.evaluateStream(signal);

        // On s'attend à ce que la sortie soit basse pour les signaux inconnus
        assertTrue(sinusoidaleNeuron.neurone.sortie() < 0.5, "Le neurone a mal classé le signal bruité comme connu.");
    }
}
