# Práctica UDP: Palabra más larga

## Formato de intercambio de mensajes

Para transmitir las palabras a través de datagramas UDP se ha elegido una cadena de texto codificada en **UTF-8** con los campos delimitados por punto y coma (`;`).

* **Petición (Cliente -> Servidor):**
  Las palabras introducidas por el usuario se concatenan separadas por `;`.
  *Ejemplo:* `casa;elefante;sol;ordenador`

* **Respuesta (Servidor -> Cliente):**
  El servidor devuelve la palabra de mayor longitud seguida de su número de caracteres, separados también por `;`.
  *Ejemplo:* `ordenador;9`
