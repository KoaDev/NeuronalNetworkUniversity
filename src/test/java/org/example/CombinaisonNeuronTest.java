package org.example;

import org.example.utils.NeuronUtil;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertTrue;


public class CombinaisonNeuronTest {

    private static final CombinaisonNeuron combinaisonNeuron = new CombinaisonNeuron();

    @Test
    public void testRecognizeGeneratedSignal() {
        // Générer un signal que le neurone est censé reconnaître
        float[] signal = NeuronUtil.genererSignalCombinaison(44100);
        combinaisonNeuron.evaluateStream(signal);

        // On s'attend à ce que la sortie soit proche de 1 pour les signaux connus
        assertTrue( "Le neurone n'a pas reconnu le signal généré.", combinaisonNeuron.neurone.sortie() > 0.9);
    }

    @Test
    public void testRecognizeNoisySignal() {
        // Générer un signal bruité
        float[] signal = NeuronUtil.genererSignalCombinaison(44100);
        float[] noisySignal = NeuronUtil.addNoise(new float[][]{signal}, NeuronUtil.noiseLevel)[0];
        combinaisonNeuron.evaluateStream(noisySignal);

        // On s'attend à ce que la sortie soit encore raisonnablement haute pour les signaux bruités
        assertTrue("Le neurone n'a pas bien reconnu le signal bruité.", combinaisonNeuron.neurone.sortie() > 0.5);
    }

    @Test
    public void testNonRecognizedSignal() {
        // Générer un signal que le neurone n'est pas censé reconnaître
        float[] signal = NeuronUtil.genererSignalBruit(44100);
        combinaisonNeuron.evaluateStream(signal);

        // On s'attend à ce que la sortie soit basse pour les signaux inconnus
        assertTrue("Le neurone a mal classé le signal bruité comme connu.", combinaisonNeuron.neurone.sortie() < 0.5);
    }
}
