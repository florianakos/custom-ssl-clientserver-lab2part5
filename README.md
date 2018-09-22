# SSL Client Server Implementation (Java)

Slight modification of the SSL client-server implementation from course D7001D at LTU. 

![alt text](https://github.com/florianakos/custom-ssl-clientserver-lab2part5/raw/master/success.png  "Success")


## Generate and install SSL keys

### SSL Server side: 

Create a folder and execute the below commands inside the folder:
```
$ keytool -genkeypair -alias some_random_keyname -keyalg RSA -validity 365 -keystore server_keystore
$ keytool -export -alias some_random_keyname -keystore server_keystore -rfc -file server.cert
```
The first command generates a private key and stores it into the "**server_keystore**" file. Important to keep this file safe! Also a password will be asked when executing the command. It should be easy to remember and it will be necessary to run the server so remember it!
The second command extracts a self-signed certificate from the **server_keystore**. This will have to be copied over to the client side.
Alternatively the below command can also be useful for testing whether the server is sending the correct certificate over the network.
```
$ openssl s_client -connect IP_ADDRESS:PORT
```

### SSL Client side:

Create a folder for the source code and other misc files and copy the SSL cert that was obtained from the server. Once the cert is available execute the below command:
```
$ keytool -import -alias servercert -file server.cert -keystore truststore
```
This command will take the **server.cert** and install it in a keystore that will be called **truststore** and it will act as a storage for trusted keys. The distinction between **keystore** and **truststore** is important for Java Security!


## Compile & Execute

Export the java class files into an executable JAR file (how-to [link](https://www.pegaxchange.com/2018/01/11/export-a-java-project-in-eclipse-neon/)).

Copy the server's JAR file to the AWS Instance running in the cloud. On windows there is WinSCP available, on Linux the Nautilus file manager can open an ssh session to the instance to make it easier.

Afterwards start two terminals, one on the AWS Instance and one on the local machine and use the following syntax to run the JAR files respectively.

### Run the server
```
$ java -cp server_executable.jar -Djavax.net.ssl.keyStore=PATH-TO-SERVER-KEYSTORE -Djavax.net.ssl.keyStorePassword=keystore_pw package_path.Class_name
```
- the server_executable.jar is the file which was exported from your favourite IDE from the source code
- Djavax.net.ssl.keyStore flag will define where the keystore is located, from which the server will load it's private key
- Djavax.net.ssl.keyStorePassword is needed to be able to unlock the keystore (it was given during the creation of the keystore)
- if necessary the flag "**-Djavax.net.debug=all**" can be passed to the command when starting the server to see some extra info about the inner workings of SSL

### Run the client
```
$ java -cp client_executable.jar -Djavax.net.ssl.trustStore=PATH-TO-CLIENT-TRUSTSTORE -Djavax.net.ssl.trustStorePassword=truststore_pw  package_path.Class_name
```
- the client_executable.jar is the file output by the IDE during the exporting of the source code
- the Djavax.net.ssl.trustStore is the flag which signals the path to the truststore that was created when the server's certificae was imported
- the Djavax.net.ssl.trustStorePassword is needed to open the trustStore


### PCAP Capture of Client-Server handshake

Traffic exchanged (capture can be found in the repo as well).

![alt text](https://github.com/florianakos/custom-ssl-clientserver-lab2part5/raw/master/pcap.png  "Pcap Capture")


## Acknowledgments

* Mum & Dad
* My comfy chair
