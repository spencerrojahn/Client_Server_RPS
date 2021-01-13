# Client_Server_RPS
Uses socket programming to establish a TCP connection between participants, allowing the participants to play a game of RPS (Rock-Paper-Scissors)

HOW TO RUN MY PROGRAM:

Once you have uncompressed my zip folder and directed your command line/terminal to my folder, simply run my "Rojahn_ClientServer.class" compiled java file in your command line/terminal like so using the specific arguments:

1) Server: "java Rojahn_ClientServer [NAME_PLAYER1] server [PORT_NUMBER]"
     ex. - "java Rojahn_ClientServer Spencer server 4999"

2) Client: "java Rojahn_ClientServer [NAME_PLAYER2] client [IP_ADDRESS] [PORT_NUMBER]"
     ex. - "java Rojahn_ClientServer Billy client 192.102.1.19 4999"

**NOTES**
-> Run commandline 1) and then 2) in separate terminal windows. Server is always run first.
-> The ex. would work together because the client simply needs to enter the IP address of machine that is being used for the server. Thus, in this example, my machine hosts the server and therefore the client needs to connect to my address (not my real IP address) on the specified port. So, for you as the TA, you would simply plug you IP address into the client command line arguments.
-> Thus, the client's entered IP address must equal be equal to the IP address of the server
-> The port number should be the same for the client and the server.

Once the client has connected to the server, there will be instructions in the two simultaneously run programs that will notify you on how to coninue. You will only be able to play the game once, and if you wish to play again, you must start from the beginning of this HOW TO RUN MY PROGRAM section and repeat.
