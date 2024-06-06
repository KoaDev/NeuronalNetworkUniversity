package org.example;

import org.example.utils.NeuronUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NoisyNeuronTest {

    private final NoisyNeuron noisyNeuron = new NoisyNeuron();

    @Test
    public void testRecognizeGeneratedNoisySignal() {
        // Générer un signal bruité que le neurone est censé reconnaître
        float[] signal = NeuronUtil.generateSignalBruit(44100);
        noisyNeuron.evaluateStream(signal);

        // On s'attend à ce que la sortie soit proche de 1 pour les signaux connus
        assertTrue(noisyNeuron.neurone.sortie() > 0.9, "Le neurone n'a pas reconnu le signal bruité généré.");
    }

    @Test
    public void testRecognizeNoisySignal() {
        // Générer un signal bruité et ajouter plus de bruit
        float[] signal = NeuronUtil.generateSignalBruit(44100);
        float[] noisySignal = NeuronUtil.addNoise(new float[][]{signal}, NeuronUtil.noiseLevel)[0];
        noisyNeuron.evaluateStream(noisySignal);

        // On s'attend à ce que la sortie soit encore raisonnablement haute pour les signaux bruités
        assertTrue(noisyNeuron.neurone.sortie() > 0.5, "Le neurone n'a pas bien reconnu le signal bruité.");
    }

    @Test
    public void testNonRecognizedSignal() {
        // Générer un signal sinusoïdal que le neurone n'est pas censé reconnaître
        float[] signal = NeuronUtil.genererSignalDoubleSinus(44100);
        noisyNeuron.evaluateStream(signal);

        // On s'attend à ce que la sortie soit basse pour les signaux inconnus
        assertTrue(noisyNeuron.neurone.sortie() < 0.5, "Le neurone a mal classé le signal sinusoïdal comme bruité.");
    }
}
