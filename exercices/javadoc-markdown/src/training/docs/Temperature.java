package training.docs;

/**
 * Convertit des températures.
 *
 * <p>Les échelles gérées sont les suivantes :</p>
 *
 * <ul>
 *   <li>Celsius</li>
 *   <li>Fahrenheit</li>
 * </ul>
 *
 * <p>Voir <a href="https://fr.wikipedia.org/wiki/Degr%C3%A9_Celsius">la page
 * Wikipedia</a> pour les définitions.</p>
 */
public final class Temperature {

    /**
     * Convertit des degrés {@code Celsius} en {@code Fahrenheit}.
     *
     * @param celsius la température en degrés Celsius
     * @return la température en degrés Fahrenheit
     */
    public double toFahrenheit(double celsius) {
        return celsius * 9 / 5 + 32;
    }
}
