# Javadoc Markdown

L'énoncé complet et le corrigé sont dans le **Guide stagiaire**, section « Javadoc Markdown ». Les corrigés y sont regroupés en fin de document.

## En deux lignes

`Temperature` est documentée en HTML, avec `<p>`, `<ul>`, `<a href>` et `{@code}`. La récrire en commentaires `///` et en Markdown.

## Générer la documentation

```
javadoc -d apidocs src/training/docs/Temperature.java
```

Ouvrir ensuite `apidocs/training/docs/Temperature.html`. Générer **avant** puis **après** la réécriture : la page est identique, c'est la source qui change.
