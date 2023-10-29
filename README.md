# Bantumi

> https://github.com/oliver-upm/game-bantumi

> Desarrollado por Oliver Fernández García ([**@oliver-upm**](//github.com/oliver-upm)) para la práctica de Persistencia de Datos de la asignatura Front End para Moviles del Máster en Ingeniería Web de la UPM.

## Funcionalidades obligatorias
### Reiniciar partida
Con su correspondiente diálogo de confirmación.

### Guardar partida
Con su correspondiente diálogo de confirmación. Si la respuesta es afirmativa se puede guardar una partida utilizando el sistema de ficheros. Se guarda:
- Si el juego esta empezado, para comprobaciones posteriores
- Numero inicial de semillas de esa partida. Para un posterior filtrado correcto de los mejores resultados según el número de semillas iniciales.
- Turno actual.
- Nombre del jugador. Para posterioremente poder mostrar el nombre del juador que inicio esa partida que se está guardando y también para guardarlo en BBDD cuando finalice la partida.
- La disposición de semillas por el tablero

### Recuperar partida
Si no hay ninguna partida guardada se indica a través de un snackbar.

Si la partida actual no esta empezada, se recupera directamente la partida guardada.

Si la partida actual está empezada, se pregunta mediante diálogo si está seguro de cargar la partida guardada.

Cuando se recupera la partida se adaptan los distintos parámetros de la partida guardada a la actual.

### Guardar puntuación
Utilizando una BBDD Room, al finalizar la partida se guardan los siguientes datos:
- Fecha/hora
- Nombre del ganador
- Número de semillas del ganador
- Nombre del perdedor
- Número de semillas iniciales (Para posterior filtrado)

(No se considera necesario guardar el caso de empate puesto que en la pantalla de mejores resultados ya se observará con claridad si es un empate)

### Mejores resultados
De las puntuaciones guardadas se muestran los mejores 10 resultados atendiendo a un orden descendente del número de semillas del ganador.
En cuanto a la opciones de esta nueva pantalla existirá una flecha para volver al juego y una opción de borrar todos los resultados (con su dialogo correspondiente)

## Funcionalidades adicionales

En la opción de ajustes se podran cambiar las siguientes **preferencias**
### Nombre del jugador
Mediante un EditTextPreference
### Número de semillas inicial
Mediante un SeekBarPreference con un mínimo de una semilla y hasta un máximo de 8.
### Jugador que hace el primer movimiento
Mediante un ListPreference

Estas preferencias solo se aplicarán en el momento que se reinicie una partida, bien con la opción de reiniciar bien cuando finalice; o también cuando se reinicia la aplicación.

### Filtrar los mejores resultados por número de semillas
En la pantalla de mejores resultados, mediante un spinner, se pueden filtrar los mejores 10 resultados teniendo en cuanta el número de semillas con el que se comenzo. Por defecto, se mostraran los mejores resultados sin tener en cuenta el factor semillas iniciales

### Marcar la casilla elegida por cada jugador
Se marcará la ultima casilla que se ha seleccionado en:
- Verde si es una casilla válida para jugar.
- Rojo, si por contra no es válida para jugar.

Una vez que se pulse una nueva casilla la anterior volverá a su color inicial.

El jugador1 (humano), para que la casilla que ha pulsado sea verde, es decir, sea válida, tendrá que ser una de sus casillas jugables (no la del oponente) y tener semillas para recolectar. En caso contrario será roja. En el caso especial de que tenga varios turnos seguidos sigue la misma lógica.

El jugador2 (máquina), dado que se acepta con pulsar en cualquiera de las casillas del juego y que la verificación de que la casilla a jugar tenga semillas que recolectar es automática, no se verá el color rojo y se marcará en verde la casilla con la que juega (la que elige de forma aleatoria). Igualmente cuando posee varios turnos seguidos.
