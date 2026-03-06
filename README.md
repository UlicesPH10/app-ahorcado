# App Ahorcado v1.2

Este proyecto es una aplicación Android del clásico juego del "Ahorcado", desarrollada como parte del curso de Ingeniería de Software. La aplicación permite a los usuarios jugar en diferentes categorías y niveles de dificultad, con persistencia de datos local.

## Características

*   **Juego del Ahorcado:** Mecánica clásica de adivinar palabras letra por letra.
*   **Categorías y Niveles:** Soporte para múltiples categorías de palabras y diferentes niveles de dificultad.
*   **Multilingüe:** Palabras disponibles en español e inglés.
*   **Base de Datos Local:** Uso de Room para gestionar categorías, palabras y usuarios.
*   **Música de Fondo:** Servicio de música que se ejecuta en segundo plano durante el uso de la aplicación.
*   **Interfaz de Usuario:** Diseños personalizados para el menú principal, configuración y pantallas de victoria/derrota.

## Tecnologías Utilizadas

*   **Lenguaje:** [Kotlin](https://kotlinlang.org/)
*   **Arquitectura:** MVVM (implícito en el uso de Room y Lifecycle)
*   **Base  Datos:** [Room Persistence Library](https://developer.android.com/training/data-storage/room)
*   **UI:** Xml, ViewBinding
*   **Logging:** [Timber](https://github.com/JakeWharton/timber)
*   **Inyección de Dependencias/Generación de Código:** Kapt (para Room)

## Estructura del Proyecto

*   `MainActivity.kt`: Punto de entrada de la aplicación, maneja permisos e inicialización de la base de datos.
*   `Menu.kt`: Pantalla de navegación principal.
*   `Ahorcado.kt`: Actividad principal del juego.
*   `MotorJuego.kt`: Contiene la lógica central del juego.
*   `Configuracion.kt`: Gestión de ajustes de la aplicación.
*   `BackgroudMusicService.kt`: Servicio para la reproducción de música.
*   `dao/`, `entidades/`, `DataBase/`: Capa de datos que utiliza Room.

## Requisitos

*   Android SDK 24 o superior (minSdk 24).
*   Android Studio Iguana | 2023.2.1 o superior.
*   JDK 17.

## Instalación

1.  Clona el repositorio.
2.  Abre el proyecto en Android Studio.
3.  Sincroniza el proyecto con los archivos Gradle.
4.  Ejecuta la aplicación en un emulador o dispositivo físico.

---
© 2024 - Curso Verano - FING SOFTWARE
