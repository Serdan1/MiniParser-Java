package parser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Lexer lexer = new Lexer();

        [cite_start]// Definimos los casos de prueba sugeridos en la práctica [cite: 36-47]
        String[] pruebas = {
                // --- Casos Correctos ---
                "x = 5;",
                "x = (5 + 2) * 3;",
                "x = 5 + 2 * x;",
                "print(x);",

                // --- Casos con Errores Sintácticos (para probar la validación) ---
                "x = ;",              // Error: falta expresión
                "x = (5 + 2;",        // Error: paréntesis no balanceados
                "print(x",            // Error: falta cierre de paréntesis y punto y coma
                "= 5;",               // Error: falta ID al inicio
                "x = 5"               // Error: falta punto y coma final
        };

        System.out.println("=== Iniciando Pruebas del Parser ===\n");

        for (String codigoFuente : pruebas) {
            System.out.println("Entrada: \"" + codigoFuente + "\"");

            try {
                // 1. Generar Tokens (Lexer)
                List<Token> tokens = lexer.tokenize(codigoFuente);

                // Imprimir tokens (opcional, para depurar)
                // System.out.println("Tokens: " + tokens);

                // 2. Validar Sintaxis (Parser)
                Parser parser = new Parser(tokens);
                parser.parse();

                // Si llega aquí, es que no hubo excepción
                System.out.println("Resultado: ✅ SINTAXIS CORRECTA");

            } catch (Exception e) {
                // Capturamos el error lanzado por el Parser
                System.out.println("Resultado: ❌ ERROR -> " + e.getMessage());
            }
            System.out.println("--------------------------------------------------");
        }
    }
}