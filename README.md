# Simple Messaging Architecture - with Java

Slight modification of the SSL client-server implementation from course D7001D at LTU. 

![alt text](https://github.com/florianakos/custom-ssl-clientserver-lab2part5/raw/master/success.png  "Success")

## Generate and install SSL keys

### SSL Server side: 

Create a folder and execute the below commands inside the folder:

```
keytool -genkeypair -alias key_id -keyalg RSA -validity 365 -keystore server_keystore

keytool -export -alias duke -keystore server_keystore -rfc -file server.cert
```

The first command generates a private key and stores it into the "server_keystore" file. Important to keep this file safe! Also a password will be asked when executing the command. It should be easy to remember and it will be necessary to run the server so remember it!
The second command extracts a self-signed certificate from the keystore. This will have to be copied over to the client side.
Alternatively the below command can also be useful for extracting the server certificate over the network

```
openssl s_client -connect IP_ADDRESS:PORT
```

### SSL Client side:

Create a folder for the source code and other misc files and copy the SSL cert that was obtained from the server. Once the cert is available execute the below command:

```
keytool -import -alias servercert -file server.cert -keystore truststore
```

This command will take the certificate and install it in a keystore that will be called "truststore" and it will act as a storage for trusted keys.

## Compile & Execute

Export the java class files into an executable JAR file (how-to [link](https://www.pegaxchange.com/2018/01/11/export-a-java-project-in-eclipse-neon/)).

Copy the server's JAR file to the AWS Instance running in the cloud. On windows there is WinSCP available, on Linux the Nautilus file manager can open an ssh session to the instance to make it easier.


Afterwards start two terminals, one on the server and one on the local machine and use the following syntax to run the JAR files respectively.

### Run the server

```
java -cp server_executable.jar -Djavax.net.ssl.keyStore=PATH-TO-SERVER-KEYSTORE -Djavax.net.ssl.keyStorePassword=keystore_pw package_path.Class_name
```

### Run the client

```
java -cp client_executable.jar -Djavax.net.ssl.trustStore=PATH-TO-CLIENT-TRUSTSTORE -Djavax.net.ssl.trustStorePassword=truststore_pw  package_path.Class_name
```


### PCAP Capture of Client-Server handshake

Traffic exchanged:

![alt text]("Capture")


## Acknowledgments

* Mum & Dad
* My comfy chair