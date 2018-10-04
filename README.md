# ProgettoSE

Versione digitalizata del "popolare" gioco da tavolo Sagrada.

Istruzioni per il jar:
	Server:
		per lanciare come server bisogna utilizzare il formato "java -jar sagrada(...).jar false 192.168.0.2 10000 9999 9998"
		dove:
			-false indica che non è un client
			-192.168.0.2 è l'hostname/ip a cui i client vorranno fare le richieste RMI
			-10000 è la porta RMI
			-9999 è la porta richieste di socket
			-9998 è la porta oggetti di socket

	Client:
		per lanciare come client basta utilizzare il formato "java -jar sagrada(...).jar true true)
		dove:
			-il primo true indica che si lancia in modalità client
			-il secondo true indica che si vuole usare la gui(se messo a false lancia la cli)


