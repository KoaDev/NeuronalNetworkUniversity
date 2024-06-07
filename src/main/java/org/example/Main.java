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
                new String[]{"src/main/resources/Carre.wav", "src/main/resources/Bruit.wav"},
                new float[]{1.0f, 0.0f});

        NeuronSuperMax neuronSinus = new NeuronSuperMax(
                new String[]{"src/main/resources/Sinusoide.wav", "src/main/resources/Bruit.wav"},
                new float[]{1.0f, 0.0f});
        NeuronSuperMax neuronCombination = new NeuronSuperMax(
                new String[]{"src/main/resources/Combinaison.wav", "src/main/resources/Bruit.wav"},
                new float[]{1.0f, 0.0f});

        neuronSinus.evaluateFile("src/main/resources/Sinusoide.wav");
        neuronSquare.evaluateFile("src/main/resources/Carre.wav");
        neuronCombination.evaluateFile("src/main/resources/Combinaison.wav");


    }

}