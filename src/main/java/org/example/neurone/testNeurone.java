package org.example.neurone;

import org.example.FFT.Complexe;
import org.example.FFT.ComplexeCartesien;
import org.example.FFT.FFTCplx;
import org.example.Son.Son;

import java.util.Random;

public class testNeurone
{

	public static void main(String[] args)
	{
		// Tableau des entrées de la fonction ET (0 = faux, 1 = vrai)
		final float[][] entrees = {{0, 0}, {0, 1}, {1, 0}, {1, 1}};

		final float[] resultats = {0, 1};

		String cheminFichierWavCarre = "src/main/resources/Carre.wav";
		String cheminFichierWavSinus = "src/main/resources/Sinusoide.wav";

		// Charger les fichiers audio et calculer la FFT
		Son sonCarre = new Son(cheminFichierWavCarre);
		Complexe[] resultatFFTCarre = calculerFFT(sonCarre.donnees());

		Son sonSinus = new Son(cheminFichierWavSinus);
		Complexe[] resultatFFTSinus = calculerFFT(sonSinus.donnees());

		// On crée un neurone taillé pour apprendre la fonction ET
		//final iNeurone n = new NeuroneHeaviside(entrees[0].length);
		final iNeurone n = new NeuroneSigmoide(entrees[0].length);
		//final iNeurone n = new NeuroneReLU(entrees[0].length);

		System.out.println("Apprentissage…");
		// On lance l'apprentissage de la fonction ET sur ce neurone
		System.out.println("Nombre de tours : " + n.apprentissage(entrees, resultats));

		// On affiche les valeurs des synapses et du biais

		// Conversion dynamique d'une référence iNeurone vers une référence neurone.
		// Sans cette conversion on ne peut pas accéder à synapses() et biais()
		// à partir de la référence de type iNeurone
		// Cette conversion peut échouer si l'objet derrière la référence iNeurone
		// n'est pas de type neurone, ce qui n'est cependant pas le cas ici
		final Neurone vueNeurone = (Neurone) n;
		System.out.print("Synapses : ");
		for (final float f : vueNeurone.synapses())
			System.out.print(f + " ");
		System.out.print("\nBiais : ");
		System.out.println(vueNeurone.biais());

		// On affiche chaque cas appris sans bruit
		System.out.println("\nRésultats sans bruit:");
		evaluatePrecision(n, entrees, resultats);

		// Ajout de bruit et évaluation de la robustesse
		float noiseLevel = 0.15f; // Niveau de bruit (10%)
		float[][] noisyEntrees = addNoise(entrees, noiseLevel);

		// On affiche chaque cas appris avec bruit
		System.out.println("\nRésultats avec bruit:");
		evaluatePrecision(n, noisyEntrees, resultats);
	}

	private static void evaluatePrecision(iNeurone n, float[][] entrees, float[] resultats) {
		float totalPrecision = 0.0f;
		for (int i = 0; i < entrees.length; ++i)
		{
			// Pour une entrée donnée
			final float[] entree = entrees[i];
			// On met à jour la sortie du neurone
			n.metAJour(entree);
			// On calcule la précision pour cette entrée
			float precision = 1.0f - Math.abs(resultats[i] - n.sortie());
			totalPrecision += precision;
			// On affiche cette sortie
			System.out.println("Entree " + i + " : " + n.sortie() + " (Précision : " + precision + ")");
		}
		// Moyenne de précision
		float averagePrecision = totalPrecision / entrees.length;
		System.out.println("Précision moyenne : " + averagePrecision);
	}

	private static float[][] addNoise(float[][] original, float noiseLevel) {
		Random random = new Random();
		float[][] noisy = new float[original.length][original[0].length];
		for (int i = 0; i < original.length; i++) {
			for (int j = 0; j < original[i].length; j++) {
				noisy[i][j] = original[i][j] + noiseLevel * (2 * random.nextFloat() - 1); // ajouter du bruit entre -noiseLevel et +noiseLevel
			}
		}
		return noisy;
	}

	public static Complexe[] calculerFFT(float[] donneesAudio) {
		Complexe[] signal = new Complexe[donneesAudio.length];
		for (int i = 0; i < donneesAudio.length; i++)
			signal[i] = new ComplexeCartesien(donneesAudio[i], 0); // Créer des nombres complexes avec la partie imaginaire à zéro

		// Appliquer la FFT sur le signal
		return FFTCplx.appliqueSur(signal);
	}

}
