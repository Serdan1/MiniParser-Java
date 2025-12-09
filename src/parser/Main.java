package parser;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        Lexer lexer = new Lexer();

        String[] pruebas = {
                // --- Casos Correctos ---
                "x = 5;",
                "x = (5 + 2) * 3;",
                "x = 5 + 2 * x;",
                "print(x);",

                // --- Casos con Errores ---
                "x = ;",
                "x = (5 + 2;",
                "print(x",
                "= 5;",
                "x = 5"
        };

        System.out.println("=== Iniciando Pruebas del Parser ===\n");

        for (String codigoFuente : pruebas) {
            System.out.println("Entrada: \"" + codigoFuente + "\"");

            try {
                // 1. Generar Tokens
                List<Token> tokens = lexer.tokenize(codigoFuente);

                // 2. Validar Sintaxis
                Parser parser = new Parser(tokens);
                parser.parse();

                System.out.println("Resultado: ✅ SINTAXIS CORRECTA");

            } catch (Exception e) {
                System.out.println("Resultado: ❌ ERROR -> " + e.getMessage());
            }
            System.out.println("--------------------------------------------------");
        }
    }
}