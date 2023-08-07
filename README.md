# BehavioralTypes

- Simple *Client-Server* example of how to implement `TypeStates` in Java. The usage example is written in Kotlin, because it's easier to launch two coroutines with client and server in each one.

## Protocols

### File Client

![FileClient](https://github.com/LisandroDiMeo/BehavioralTypes/assets/22039840/4ba54618-f857-4591-a9c2-0a4f74e9b1d1)

### File Server

![FileServer](https://github.com/LisandroDiMeo/BehavioralTypes/assets/22039840/6171e64f-b3b7-4fc2-907b-8932a4d54133)


## How To Run

- To run the sample program, just open the Kotlin Project in IntelliJ. It will load MainKt task to run the Main.kt program. 
There you can change the reading mode (by byte or lines) by changing the `args` received by the program. 
- To run Jaytc, inside the `src/main/kotlin/` directory you will find a `run.sh` script. Inside, you should change the `$JAYTC_PATH` to properly run the program. 

## Notes

- If you have an older JDK, maybe it will prompt to update or change for a more newer one. 
