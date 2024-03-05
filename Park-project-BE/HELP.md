# ⛰️ Parco regionale dei Colli Euganei - Ricerca escursioni
## 💻 Full Stack Capstone Project

### Link al FRONT-END  https://github.com/3roSavo/FE_Progetto_Parco.git

La seguente applicazione web traghetterà gli utenti, dopo previa registrazione e accesso, alla homepage del sito del parco regionale dei Colli Euganei. Da qui si potrà
avere una dettagliata introduzione del del parco con molte informazioni a riguardo, tra le quali svariate immagini di introduzione al parco e indicazioni per raggiungerlo.
La funzione principale sarà la ricerca di escursioni in una pagina dedicata e il salvataggio di escursioni tra i preferiti, con la possibilità di vedere anche chi ha messo mi piace e confrontare le escursioni preferite in comune tra i vari utenti. Naturalmente se l'utente è autorizzato come admin avrà accesso a tutta una serie di operazioni CRUD sia per quanto riguarda un qualsiasi utente (funzionalità ancora in fase alfa) sia per quanto riguarda un'escursione specifica. Le funzionalità di gestione icone utente e galleria escursioni è delegata ad un servizio di terze parti, cioè Cloudinary , che si occupa di gestire, aggiornare ed eliminare i vari file multimediali.
L'applicazione è stata sviluppata usando React per il front-end e Spring Boot per il back end e postgreSQL per l'immagazzinamento dei dati.

## 📋 Funzionalità
* Registrazione utente e accesso tramite token JWT
* Sezione ricerca escursione per titolo con filtri personalizzati e modalità shuffle
* Funzione aggiunta escursione ai preferiti comodamente accessibile dal proprio profilo
* Sezione visualizza profilo utente con funzionalità di comparazione escursioni preferite in comune
* Sezione profilo con possibilità di accedere ad ogni escursione salvata nei preferiti e gestione totale delle proprie info (CRUD)
* Sezione ricerca utente per confrontare le proprie escursioni con quelle di altre persone -implementazione parziale-
* Scheda dettaglio escursione per ogni specifica escursione con descrizione completa e contenuti multimediali
* Gestione completa delle informazioni riguardo le escursioni -ADMIN- (CRUD)
* Gestione completa delle informazioni riguardo gli utenti -ADMIN- (CRUD) in fase di sviluppo

## 📷 Anteprima del progetto
### Schermata accesso
<img alt="immagine" src="src/main/assets/Screenshot 2024-03-05 alle 14.55.08.png" height="180px" />

## 📦 Pacchetti installati

Assicurati di avere Node.js e npm installati sul tuo sistema.

Questo progetto è stato creato con la libreria JavaScript React

* npx create-react-app nome-cartella

* cd nome-cartella

* npm install bootstrap
* npm install react-bootstrap
* npm install react-router-dom
* npm install @reduxjs/toolkit
* npm install react-redux

Avviare app : npm start.
Arrestare app : control + C

## 🖊️ Autore
Eros Savogin
#### Linkedin => [www.linkedin.com/in/eros-savogin-developer](https://www.linkedin.com/in/eros-savogin-developer/)