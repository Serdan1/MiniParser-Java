### MiniParser Java: Analizador Sintáctico Descendente

Este proyecto implementa un Parser Recursivo Descendente en Java. Es la continuación de la práctica del Nivel Léxico y tiene como objetivo validar sentencias y expresiones matemáticas básicas según una gramática predefinida.

## 📋 Descripción

El analizador toma una cadena de código fuente, la convierte en tokens (usando un Analizador Léxico integrado) y verifica si la estructura cumple con las reglas sintácticas del lenguaje.
Soporta precedencia de operadores y detección de errores básicos.

Funcionalidades

Asignaciones: x = 5;

Salida por pantalla: print(x);

Expresiones Aritméticas: suma, resta, multiplicación, división y paréntesis.

Precedencia de Operadores: * y / tienen prioridad sobre + y -.

Manejo de Errores: indica qué token se esperaba y cuál se encontró.

## 🔧 Gramática Implementada

| No Terminal | Producción |
|------------|------------|
| **StmtList** | `Stmt StmtList`  \| `ε` |
| **Stmt**     | `ID '=' Expr ';'`  \| `print '(' Expr ')' ';'` |
| **Expr**     | `Term { ('+' \| '-') Term }` |
| **Term**     | `Factor { ('*' \| '/') Factor }` |
| **Factor**   | `ID` \| `NUM` \| `'(' Expr ')'` |


## 📂 Estructura del Proyecto

El código fuente se encuentra en el paquete `parser`:

- **Main.java:** Punto de entrada. Contiene una batería de pruebas (casos correctos e incorrectos).
- **Parser.java:** Núcleo del análisis. Implementa un método por cada regla gramatical (`stmt`, `expr`, `term`, etc.).
- **Lexer.java:** Convierte el texto de entrada en una lista de tokens.
- **Token.java:** Representación de la unidad léxica (Tipo + Lexema).
- **TipoToken.java:** Enumeración con los tipos de tokens (`PALABRA_CLAVE`, `ID`, `NUM`, etc.).

## 🚀 Ejecución

### Requisitos

- Java JDK 8 o superior  
- Cualquier IDE (IntelliJ IDEA, Eclipse) o terminal

### Cómo ejecutar

1. Clona el repositorio.
2. Abre el proyecto en tu IDE.
3. Ejecuta la clase `parser.Main`.

El `Main` ejecutará automáticamente los siguientes casos de prueba:

| Entrada            | Resultado Esperado                     |
|--------------------|----------------------------------------|
| `x = 5;`           | ✅ Correcto                            |
| `x = (5 + 2) * 3;` | ✅ Correcto                            |
| `print(x);`        | ✅ Correcto                            |
| `x = (5 + 2;`      | ❌ Error: Falta paréntesis de cierre   |
| `x = ;`            | ❌ Error: Falta expresión              |

---

## 🧠 Diagrama de Lógica del Sistema

El siguiente diagrama detalla el flujo de control, desde la tokenización hasta la toma de decisiones en el análisis sintáctico (Parser):

```mermaid
graph TD
    %% Inicio y Tokenización
    Start((Inicio)) --> Source[Entrada: Codigo Fuente]
    Source --> Lexer[Lexer: Tokenizar String]
    Lexer -->|Genera| TokenList[Lista de Tokens]
    
    %% Entrada al Parser
    TokenList --> ParserStart[Parser: parse]
    
    %% Bucle Principal (StmtList)
    ParserStart --> CheckEnd{Hay mas tokens?}
    
    %% Toma de Decisiones (Stmt)
    CheckEnd -- Si --> Decision{Token actual}
    CheckEnd -- No --> Success((Fin: Sintaxis Correcta))
    
    %% Rama 1: Asignacion
    Decision -- ID --> Assign[Asignacion: consumir igual, evaluar expresion, consumir punto y coma]
    
    %% Rama 2: Print
    Decision -- print --> Print[Print: consumir LPAREN, evaluar expresion, consumir RPAREN, consumir punto y coma]
    
    %% Rama 3: Error
    Decision -- Otro --> Error[Error: Token inesperado]
    
    %% Subrutina de expresiones
    Assign --> ExprCall[Analizar expresion]
    Print --> ExprCall
    
    %% Retorno
    ExprCall --> CheckEnd
    
    %% Estilos
    style Start fill:#f9f,stroke:#333,stroke-width:2px
    style Success fill:#9f9,stroke:#333,stroke-width:2px
    style Error fill:#f99,stroke:#333,stroke-width:2px
    style ExprCall fill:#bbf,stroke:#333,stroke-dasharray: 5 5
