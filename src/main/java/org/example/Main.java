package org.example;

public class Main {

    private static final String[] trainingPaths = {
            "src/main/resources/Sinusoide.wav",
            "src/main/resources/Bruit.wav",
            "src/main/resources/Carre.wav",
            "src/main/resources/Sinusoide2.wav",
            "src/main/resources/Combinaison.wav",
            "src/main/resources/Sinusoide3Harmoniques.wav"
    };

    public static void main(String[] args) {

        NeuronSuperMax neuronSquare = new NeuronSuperMax(
                new String[]{"src/main/resources/Carre.wav", "src/main/resources/Bruit.wav", "src/main/resources/Sinusoide.wav", "src/main/resources/Sinusoide2.wav", "src/main/resources/Sinusoide3Harmoniques.wav"},
                new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, "Carré");
        NeuronSuperMax neuronSinus = new NeuronSuperMax(
                new String[]{"src/main/resources/Sinusoide.wav", "src/main/resources/Bruit.wav", "src/main/resources/Carre.wav", "src/main/resources/Sinusoide2.wav", "src/main/resources/Sinusoide3Harmoniques.wav"},
                new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, "Sinusoïde");
        NeuronSuperMax neuronSinusDouble = new NeuronSuperMax(
                new String[]{"src/main/resources/Sinusoide2.wav", "src/main/resources/Bruit.wav", "src/main/resources/Carre.wav", "src/main/resources/Sinusoide.wav", "src/main/resources/Sinusoide3Harmoniques.wav"},
                new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, "Sinusoïde double harmonique");
        NeuronSuperMax neuronSinusTripleHarmonique = new NeuronSuperMax(
                new String[]{"src/main/resources/Sinusoide3Harmoniques.wav", "src/main/resources/Bruit.wav", "src/main/resources/Carre.wav", "src/main/resources/Sinusoide.wav", "src/main/resources/Sinusoide2.wav"},
                new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, "Sinusoïde triple harmonique");
        NeuronSuperMax neuronBruit = new NeuronSuperMax(
                new String[]{"src/main/resources/Bruit.wav", "src/main/resources/Sinusoide.wav", "src/main/resources/Carre.wav", "src/main/resources/Sinusoide2.wav", "src/main/resources/Sinusoide3Harmoniques.wav"},
                new float[]{1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f}, "Bruit");


        for (String path : trainingPaths) {
            System.out.println("---------------Entraînement avec " + path + "----------------------");
            neuronSquare.evaluateFile(path);
            neuronSinus.evaluateFile(path);
            neuronSinusDouble.evaluateFile(path);
            neuronSinusTripleHarmonique.evaluateFile(path);
            neuronBruit.evaluateFile(path);
        }
        System.out.println("------------------------------------------------------------");
    }

}