package training.docs;

/// Convertit des températures.
///
/// Les échelles gérées sont les suivantes :
///
/// - Celsius
/// - Fahrenheit
///
/// Voir [la page Wikipedia](https://fr.wikipedia.org/wiki/Degr%C3%A9_Celsius)
/// pour les définitions.
public final class Temperature {

    /// Convertit des degrés `Celsius` en `Fahrenheit`.
    ///
    /// @param celsius la température en degrés Celsius
    /// @return la température en degrés Fahrenheit
    public double toFahrenheit(double celsius) {
        return celsius * 9 / 5 + 32;
    }
}
