package parser;

import java.util.List;

public class Parser {
    private final List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    // Método principal que inicia el análisis
    public void parse() {
        stmtList();
        if (current < tokens.size() && peek().getTipo() != TipoToken.EOF) {
            throw new RuntimeException("Error sintáctico: Token inesperado al final '" + peek().getLexema() + "'");
        }
    }

    // Regla: StmtList -> Stmt StmtList | epsilon
    private void stmtList() {
        // Mientras no lleguemos al final, seguimos parseando sentencias
        while (!isAtEnd()) {
            stmt();
        }
    }

    // Regla: Stmt -> ID '=' Expr ';' | 'print' '(' Expr ')' ';'
    private void stmt() {
        if (match(TipoToken.IDENTIFICADOR)) {
            // Caso: Asignación (ID = Expr ;)
            consumir(TipoToken.OPERADOR, "="); // Esperamos '='
            expr();                            // Analizamos la expresión
            consumir(TipoToken.DELIMITADOR, ";"); // Esperamos ';'

            System.out.println("Sentencia de asignación válida.");
        } else if (match(TipoToken.PALABRA_CLAVE, "print")) {
            // Caso: Print (print ( Expr ) ;)
            consumir(TipoToken.DELIMITADOR, "(");
            expr();
            consumir(TipoToken.DELIMITADOR, ")");
            consumir(TipoToken.DELIMITADOR, ";");

            System.out.println("Sentencia print válida.");
        } else {
            // Si no es ni ID ni print, es un error
            throw new RuntimeException("Error sintáctico: Se esperaba un ID o 'print', pero se encontró: " + peek());
        }
    }

    // Regla: Expr -> Term { ('+' | '-') Term }
    private void expr() {
        term(); // Primero analizamos un término

        // Luego buscamos sumas o restas opcionales
        while (match(TipoToken.OPERADOR, "+") || match(TipoToken.OPERADOR, "-")) {
            term();
        }
    }

    // Regla: Term -> Factor { ('*' | '/') Factor }
    private void term() {
        factor(); // Primero analizamos un factor

        // Luego buscamos multiplicaciones o divisiones opcionales
        while (match(TipoToken.OPERADOR, "*") || match(TipoToken.OPERADOR, "/")) {
            factor();
        }
    }

    // Regla: Factor -> ID | NUM | '(' Expr ')'
    private void factor() {
        if (match(TipoToken.LITERAL_NUMERICO)) {
            // Es un número, avanzamos (ya lo hizo el match)
            return;
        }

        if (match(TipoToken.IDENTIFICADOR)) {
            // Es una variable, avanzamos
            return;
        }

        if (match(TipoToken.DELIMITADOR, "(")) {
            expr(); // Analizamos la expresión dentro del paréntesis
            consumir(TipoToken.DELIMITADOR, ")"); // Verificamos que se cierre
            return;
        }

        throw new RuntimeException("Error sintáctico: Se esperaba Número, ID o '(', pero se encontró: " + peek());
    }

    // --- Métodos Auxiliares ---

    // Verifica si el token actual es del tipo esperado y avanza
    private boolean match(TipoToken tipo) {
        if (check(tipo)) {
            advance();
            return true;
        }
        return false;
    }

    // Sobrecarga para verificar tipo y lexema exacto (ej: operador "+")
    private boolean match(TipoToken tipo, String lexema) {
        if (check(tipo) && peek().getLexema().equals(lexema)) {
            advance();
            return true;
        }
        return false;
    }

    // Verifica el token actual sin avanzar
    private boolean check(TipoToken tipo) {
        if (isAtEnd()) return false;
        return peek().getTipo() == tipo;
    }

    // Intenta consumir un token obligatorio. Si no está, lanza error.
    private void consumir(TipoToken tipo, String lexemaEsperado) {
        if (check(tipo) && peek().getLexema().equals(lexemaEsperado)) {
            advance();
        } else {
            throw new RuntimeException("Error sintáctico: Se esperaba '" + lexemaEsperado + "' pero se encontró '" + peek().getLexema() + "'");
        }
    }

    // Devuelve el token actual
    private Token peek() {
        return tokens.get(current);
    }

    // Avanza al siguiente token
    private Token advance() {
        if (!isAtEnd()) current++;
        return previous();
    }

    private boolean isAtEnd() {
        return peek().getTipo() == TipoToken.EOF;
    }

    private Token previous() {
        return tokens.get(current - 1);
    }
}