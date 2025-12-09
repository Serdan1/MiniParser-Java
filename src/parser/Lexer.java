package parser;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    public List<Token> tokenize(String source) {
        List<Token> tokens = new ArrayList<>();

        // 1. Pre-procesamiento: Separar símbolos con espacios para poder usar split
        // Esto facilita separar "x=5;" en "x", "=", "5", ";"
        source = source.replace("=", " = ")
                .replace(";", " ; ")
                .replace("(", " ( ")
                .replace(")", " ) ")
                .replace("+", " + ")
                .replace("-", " - ")
                .replace("*", " * ")
                .replace("/", " / ");

        // 2. Separar por espacios en blanco (split)
        String[] palabras = source.split("\\s+"); // \\s+ significa "uno o más espacios"

        // 3. Clasificación de Tokens [cite: 19]
        for (String palabra : palabras) {
            if (palabra.isEmpty()) continue;
            tokens.add(clasificarToken(palabra));
        }

        // Añadimos un token especial de Fin de Fichero para ayudar al Parser
        tokens.add(new Token(TipoToken.EOF, ""));

        return tokens;
    }

    // Lógica de clasificación pedida en el PDF [cite: 19-28]
    private Token clasificarToken(String lexema) {
        switch (lexema) {
            // Palabras Clave [cite: 8]
            case "int":
            case "if":
            case "print": // Necesario para la práctica sintáctica [cite: 72]
                return new Token(TipoToken.PALABRA_CLAVE, lexema);

            // Operadores [cite: 8, 82]
            case "+":
            case "-":
            case "*":
            case "/":
            case "=":
                return new Token(TipoToken.OPERADOR, lexema);

            // Delimitadores [cite: 8, 81]
            case ";":
            case "(":
            case ")":
                return new Token(TipoToken.DELIMITADOR, lexema);

            default:
                // Literales Numéricos [cite: 27]
                if (lexema.matches("[0-9]+")) {
                    return new Token(TipoToken.LITERAL_NUMERICO, lexema);
                }
                // Identificadores (todo lo demás) [cite: 28]
                else {
                    return new Token(TipoToken.IDENTIFICADOR, lexema);
                }
        }
    }
}