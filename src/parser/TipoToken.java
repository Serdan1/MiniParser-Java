package parser;

public enum TipoToken {
    PALABRA_CLAVE,      // Ej: int, if, print
    IDENTIFICADOR,      // Ej: x, variable, contador
    LITERAL_NUMERICO,   // Ej: 5, 10, 3
    OPERADOR,           // Ej: +, -, *, /, =
    DELIMITADOR,        // Ej: ;, (, )
    EOF                 // Fin de fichero (útil para el parser)
}