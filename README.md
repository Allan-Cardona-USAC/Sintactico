<h1 align="center"> <img align="center" src="https://radd3.virtual.usac.edu.gt/sfpu/pluginfile.php/1/core_admin/logocompact/300x300/1716913746/logotipo-compacto-usac%20%281%29.png" alt="Logo" height="100" width="100"/> <img align="center" src="https://dtt-ecys.org/static/build/images/ecys/logo-ecys-fiusac-min.png" alt="Logo" height="100" width="400"/> </h1>

<h1 align="center">Facultad de Ingeniería </h1>

<h2 align="center">Lenguajes & Herramientas:</h2>
<p align="center"> <a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/java/java-icon.svg" alt="java" width="40" height="40"/> </a>   <a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://www.vectorlogo.zone/logos/github/github-icon.svg" alt="git" width="40" height="40"/> </a> <a href="https://www.java.com" target="_blank" rel="noreferrer">

<p align="center">
<h1 align="center" ; color= red ; >LFP_PROYECTO_01_"202200181"</h1>


# PROYECTO 01
| Nombre |  Carnet   |  
| Allan  | 202200181 |

## Autómatas Finitos Deterministas 
Los autómatas son una representación formal muy útil,
que permite modelar el comportamiento de diferentes
dispositivos, máquinas, programas, etc.
- Maquinas expendedoras de refrescos
- El comportamiento de un programa (software)
- El comportamiento de semaforos
## La idea general consiste en modelar un sistema que
- Recibe un conjunto de elementos de entrada (estímulos)
- Realiza algún proceso (cómputo)
- Se produce una salida
# Autómatas finitos deterministas
**Ejemplo**
- Considere un sistema formado por una lámpara y un
interruptor. La lámpara puede estar encendida o apagada. El
sistema sólo puede recibir un estímulo exterior: pulsar el
interruptor. El funcionamiento es habitual: si se pulsa el
interruptor y estaba apagada la lámpara, se pasa al estado de
encendido, o si esta encendida, pasa a pagada. Se desea que la
bombilla este inicialmente apagada.
- Considere que 0: encendido, 1: apagado y la única entrada
posible (pulsar interruptor) es “p”

**¿Cómo sería el autómata que representa a este sistema?**
  
- A = (Q, Σ, δ, q0, F), donde:
- Q = {0,1}
- Σ = {p}
- δ(0,p) = 1, δ(1,p) = 0
- q0 = 0
- F = {}

<img align="center" src="https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEjFjOWN_LePl_CRMxXrVnVxkjbU7d7-tZlRSWbOy-KFL2yQq7CHog0qPOwSUbZ-LYOydOHBqjLhktMPLlZ_F7wZYvWAi5SBvYqMEyH-i8o1VxLQN34gEkdjiItlx-W7GjTFtBQWht9QAvo/w1200-h630-p-k-no-nu/AFD-automata-finito-determinista-1.png" alt="Logo" height="300" width="500"/>" 
