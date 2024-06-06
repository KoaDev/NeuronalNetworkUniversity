package org.example;


public class Main {

    public static void main(String[] args) {

        CombinaisonNeuron combinaisonNeuron = new CombinaisonNeuron();
        SinusoidaleNeuron sinusoidaleNeuron = new SinusoidaleNeuron();
        SquareNeuron squareNeuron = new SquareNeuron();
        NoisyNeuron noisyNeuron = new NoisyNeuron();
        SinusoidaleTwoNeuron sinusoidaleTwoNeuron = new SinusoidaleTwoNeuron();
        Sinusoidale3HarmoniqueNeuron sinusoidale3HarmoniqueNeuron = new Sinusoidale3HarmoniqueNeuron();


        combinaisonNeuron.evaluateFile("src/main/resources/Combinaison.wav");
        sinusoidaleNeuron.evaluateFile("src/main/resources/Sinusoide.wav");
        squareNeuron.evaluateFile("src/main/resources/Carre.wav");
        noisyNeuron.evaluateFile("src/main/resources/Bruit.wav");
        sinusoidaleTwoNeuron.evaluateFile("src/main/resources/Sinusoide.wav");
        sinusoidale3HarmoniqueNeuron.evaluateFile("src/main/resources/Sinusoide.wav");

    }

}
