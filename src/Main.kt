import java.io.File

// Códigos de color de fondo
const val BG_BLACK = "\u001B[40m"
const val BG_RED = "\u001B[41m"
const val BG_GREEN = "\u001B[42m"
const val BG_YELLOW = "\u001B[43m"
const val BG_BLUE = "\u001B[44m"
const val BG_PURPLE = "\u001B[45m"
const val BG_CYAN = "\u001B[46m"
const val BG_WHITE = "\u001B[47m"

// Colores ANSI básicos
const val RESET = "\u001B[0m"
const val BLACK = "\u001B[30m"
const val RED = "\u001B[31m"
const val GREEN = "\u001B[32m"
const val YELLOW = "\u001B[33m"
const val BLUE = "\u001B[34m"
const val PURPLE = "\u001B[35m"
const val CYAN = "\u001B[36m"
const val WHITE = "\u001B[37m"
const val BOLD = "\u001B[1m"
const val UNDERLINE = "\u001B[4m"

// Configuración del juego
val NUMERO_DIGITOS = 4
val INTENTOS_MAXIMOS = 10
val TRAZA_ARCHIVO = "traza-archivo.txt"

// Genera un número secreto aleatorio de 4 dígitos únicos entre 1 y 6
fun generarNumeroSecreto(): String {
    val digitos = (1..6).shuffled().take(NUMERO_DIGITOS)
    return digitos.joinToString("")
}

// Muestra el menú principal
fun menu() {
    println("${BOLD}${CYAN}Bienvenido a Guess a Number${RESET}")
    println("${GREEN}1. Jugar")
    println("2. Ver traza de último intento")
    println("3. Salir${RESET}")
    print("Selecciona una opción: ")
}

// Lógica principal del juego
fun jugar() {
    val numeroSecreto = generarNumeroSecreto()
    var intentos = 0

    println("${YELLOW}Intenta adivinar el número secreto de $NUMERO_DIGITOS dígitos entre 1 y 6 sin repetir cifras.${RESET}")

    while (intentos < INTENTOS_MAXIMOS) {
        intentos++
        print("${CYAN}Intento $intentos/$INTENTOS_MAXIMOS: ${RESET}")
        val intentoUsuario = readln()

        // Validación del input del usuario
        if (!esNumeroValido(intentoUsuario)) {
            println("${RED}Entrada no válida. Asegúrate de ingresar un número de $NUMERO_DIGITOS cifras sin repetidos y entre 1 y 6.${RESET}")
            continue
        }

        // Evaluar aciertos y coincidencias
        val (aciertos, coincidencias) = evaluarIntento(intentoUsuario, numeroSecreto)
        println("${GREEN}Aciertos: $aciertos${RESET}, ${YELLOW}Coincidencias: $coincidencias${RESET}")

        // Verificar si el usuario adivinó el número
        if (aciertos == NUMERO_DIGITOS) {
            println("$${GREEN}¡Felicidades! Adivinaste el número en $intentos intentos.${RESET}")
            break
        }

        if (intentos == INTENTOS_MAXIMOS) {
            println("${WHITE}¡Lo siento! No lograste adivinar el número. El número era: $numeroSecreto${RESET}")
        }
    }
}

// Verifica si el número ingresado es válido (4 dígitos, sin repetir, entre 1 y 6)
fun esNumeroValido(numero: String): Boolean {
    return numero.length == NUMERO_DIGITOS &&
            numero.all { it.isDigit() && it.toString().toInt() in 1..6 } &&
            numero.toSet().size == NUMERO_DIGITOS
}

// Evalúa el intento y devuelve el número de aciertos y coincidencias
fun evaluarIntento(intento: String, secreto: String): Pair<Int, Int> {
    var aciertos = 0
    var coincidencias = 0

    intento.forEachIndexed { index, digito ->
        when {
            digito == secreto[index] -> aciertos++
            digito in secreto -> coincidencias++
        }
    }

    return Pair(aciertos, coincidencias)
}

// Guarda la traza del último intento en un archivo
fun guardarTraza(traza: String) {
    File(TRAZA_ARCHIVO).writeText(traza)
}

// Muestra la traza del último intento
fun verTraza() {
    val traza = File(TRAZA_ARCHIVO)
    if (traza.exists()) {
        println("${BLUE}--- Traza del último intento ---${RESET}")
        println(traza.readText())
    }
}

// Función principal que inicia el programa
fun main() {
    menu()
    when (readln()) {
        "1" -> jugar()
        "2" -> println("${BLUE}Funcionalidad de ver última traza en desarrollo.${RESET}")
        "3" -> {
            println("${PURPLE}¡Gracias por jugar!${RESET}")
            return
        }
    }
}