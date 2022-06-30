# ing-sw-2022-pellegrino-porri-perico

Inoltre il readme su github dovrà indicare:

cosa si è implementato (facendo riferimento alla tabella dei voti presente nel file dei requisiti)

Abbiamo implementato: Regole Complete + CLI + GUI + Socket + 2 FA
Le 2 FA sono: Carte Personaggio (tutte), Partite Multiple


coverage dei test come riportato dal tool di IntelliJ

Package GAME (Model + Controller + ModelView):
Classes 100% (24/24)
Methods 95% (170/178)
Lines 88% (871/979)
Package MESSAGES (Messaggi di rete):
Classes 28% (2/7) -> MessageType astratta e UpdateMessage, usate da ModelView
Methods 22% (2/9)
Lines 47% (27/57)
Package SERVER:
Classes 42% (3/7) -> ConnectionManager, GameManager, Starter
Methods 15% (8/52)
Lines 9% (32/328)

Per ottenere questi valori è necessario runnare i test molte volte (run until failure o until stopped)
perché alcuni test hanno una componente casuale

modalità per lanciare server e client dalla linea di comando (usando i jar)

Server: comando java -jar "JAR_PATH" su cmd o PowerShell
CLI: comando java -jar "JAR_PATH" su PowerShell (x86). Su cmd, i colori ASCII non si vedono correttamente
GUI: doppio click sul JAR -> apertura con Java ; oppure comando java -jar "JAR_PATH" su cmd o PowerShell


ogni altra indicazione che possa essere utile a testare l'applicazione

Come specificato anche da Daniele Cattaneo su Slack è necessario aver scaricato il JDK.
Inoltre, naturalmente, è necessario che il server sia avviato prima di cercare di connettersi da uno dei client.
Il server non prevede un comando di chiusura, si chiude alla chiusura della finestra cmd o PowerShell associata.
Una istanza del client non può cambiare il server di riferimento dopo averlo impostato.