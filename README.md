# JAVA-CHAT

Ejemplo de como crear un chat en java utilizando sockets

## Instalacion
### Server

* Descomprime el **.jar** llamado **sqlite-jdbc-3.7.2.jar**.
* Luego ejecuta el **.bat** llamado **Server.bat**.
* Se deberia abrir una consola parecida a esta.

  ![alt text](https://i.imgur.com/aECavia.png) 
* Listo nuestro servidor esta corriendo en el puerto 2000 y la ip de la computadora donde corrimos el servidor.
### Cliente
* Para cargar el cliente puedes usar netbeans.
* Damos click en abrir proyecto y seleccionamos la carpeta en donde esta ubicado.
* Debería llamarse ChatClient por defecto.

## Uso
* Una vez iniciado el servidor podemos usar el comando ``create user [username] [password]`` para crear un nuevo usuario de la siguiente forma:
 
  ![alt text](https://i.imgur.com/6PSUkmU.png) 

* El retorno **true** por pantalla simboliza la correcta ejecución de nuestro comando, podemos usar el comando ``users list`` para verificar que, efectivamente nuestro usuario a sido creado.

  ![alt text](https://i.imgur.com/CKEL4b9.png)

#### NOTA: Puedes usar el comando ``help`` en la consola del servidor para ver toda la lista de comandos.

* Una vez creado nuestro usuario y contraseña debemos compilar y ejecutar nuestro cliente.
* Una vez ejecutado debemos llenar los campos con la información requerida de nuestro servidor y cuenta.
* En el siguiente ejemplo se esta ejecutando el cliente en la misma computadora que el servidor, por eso, en mi caso la ip es el localhost (**127.0.0.1**) y las credenciales son las que acabamos de crear user:***admin*** password:***admin123***, en tu caso deberas sustituirlos por la ip de tu servidor y las credenciales que creaste para acceder.

  ![alt text](https://i.imgur.com/fchI9o1.png)

* Una vez logueado tendrás una ventana parecida a esta, podrás enviar y recibir mensajes correctamente.

  ![alt text](https://i.imgur.com/DKFW5jn.png)

* En el podrás chatear y ver los usuarios conectados

#### Una vez finalizados todos los pasos de instalacion y uso, ya estas listo para invitar a tus amigos, solo recuerda que debes crearles una cuenta y darles los datos de acceso al servidor:
* IP
* Puerto
* Usuario
* Contraseña


## Descargalo
[Version lista para usar](http://www.mediafire.com/file/q87ei3zd1huqlh5/Java-Chat.rar/file)
