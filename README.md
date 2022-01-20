# Danger Application
#System for sharing information on potential hazards

The system consists of several parts: main application, administrator application, and assistance system. The
main application is implemented with help of JSP M2, HTML5, JS, AJAX, MySQL. It consumes several RESTful APIs
for getting information about countries, regions, and weather. Also, the main app consumes some RSS feed for
getting the news. It's a Facebook-like application but for informing about potential hazards. The administrator
application is used to monitor other parts of the system. It uses the same database as the main application and
it's implemented with JSF. The assistance system is used to create calls to help citizens in case of potential danger.
This system consists of a JSF application and a RESTful service. Calls for help from other applications are available
through the RESTful service. The database application model is implemented using the DAO pattern for data
access. The MVC architecture is used to implement this system.

*******************************************
Potrebno je napraviti sistem koji će omogućiti lakše dijeljenje informacija o potencijalnim opasnostima. Sistem se sastoji iz nekoliko dijelova:

Glavna aplikacija

Glavnu aplikaciju koriste registrovani korisnici koji treba da otvore korisnički nalog. Otvaranje naloga se obavlja na odgovarajućoj stranici gdje se unose ime, prezime, korisničko ime, lozinka (2 puta zbog provjere) i mail adresa. Ako je korisničko ime slobodno, a mail nije već korišten, korisnik se uspješno registruje i preusmjerava na stranicu za izmjenu profila. Na toj stranici se osim osnovnih podataka može izabrati država u kojoj korisnik živi iz liste ponuđenih država, izabrati profilna slika i izabrati da li želi primati notifikacije o hitnim upozorenjima (unutar aplikacije ili na mail). Korisnik može da bude samo iz neke od evropskih država koje se popunjavaju konzumiranjem RESTful servisa dostupnog na https://restcountries.eu/rest/v2/region/europe. Korištenjem drugog RESTful servisa, a na osnovu vrijednosti alpha2Code za državu, automatski se popunjavaju regioni te države, a na osnovu regiona i gradovi konzumiranjem odgovarajućih servisa sa http://battuta.medunes.net/. Ukoliko korisnik ne izabere profilnu sliku za upload, kao profilna slika dodjeljuje mu se zastava države u kojoj živi. Zastave su takođe dostupne u samom odgovoru servisa prvog servisa, kao atribut flag.

Korisnici glavne aplikacije treba da se prijave na sistem da bi ga koristili, a bez prijave na sistem jedine dostupne stranice su stranica za prijavu i stranica za registraciju.
Nakon uspješne registracije i prijave na sistem, korisniku se prikazuje stranica na kojoj se na lijevoj strani prikazuje ime i prezime korisnika, njegov avatar, koliko puta se korisnik prijavio na sistem, kao i lista notifikacija o hitnim upozorenjima.

Na sredini stranice treba da se nalazi lista svih objava vezanih za potencijalne opasnosti. Objave mogu biti vijesti ili događaji koji se dobijaju sa RSS feed-a https://europa.eu/newsroom/calendar.xml_en?field_nr_events_by_topic_tid=151, ili objave drugih korisnika. Svaki korisnik može objaviti link, tekst sa slikama ili video (sopstveni snimak ili Youtube video). Pored objave prikazuje se avatar korisnika koji je objavio. Sve vijesti se periodnično osvježavaju (svakih 30 sekundi). Objave se mogu podijeliti na društvenim mrežama (minimalno Facebook i Twitter). Korisnici mogu komentarisati objavu, pri čemu u komentar mogu dodati i sliku.

Registrovani korisnik može da unese objavu o potencijalnoj opasnosti. Pod potencijalnim opasnostima mogu da se podrzumijevaju različite stvari: srušeno drvo na putu, najavljeno olujno nevrijeme, poplava, požar i sl. Potencijalne opasnosti se dodaju u određenu kategoriju prilikom unosa (s tim da jedna objava može da bude klasifikovana u više kategorija). Kategorije opasnosti dodaje administrator. Prilikom unosa, korisnik može da izabere da se radi o hitnom upozorenju, koje se odmah nakon unosa šalje svim korisnicima koji su izabrali takvu vrstu notofikacije. Kod unosa je opciono i da korisnik doda Google maps lokaciju gdje se potencijalna opasnost nalazi.

Na desnoj strani ekrana prikazuje vremenska prognoza za tri slučajno odabrana grada iz države u kojoj korisnik živi. Prognozu preuzimati sa https://openweathermap.org/api.

Podaci se čuvaju u MySQL bazi podataka, a aplikacija mora biti implemenirana pomoću JSP M2 modela, HTML5, JS i AJAX. Po potrebi moguće je koristiti servlete. Aplikacija mora imati dizajn koji se prilagođava upotrebi na mobilnim uređajima.
