# Uitbreiding

## Beschrijving

Een mogelijke uitbreiding die in dit stadium toegevoegd zou kunnen worden, 
is het creeëren van een gebruiker door middel van het inlezen van een identiteitskaart.
Deze zou dan later ook kunnen dienen als authenticatie-alternatief.

Het proces om een gebruiker toe te voegen ziet er dan als volgt uit:

1. Een medewerker klikt op "Gebruiker met eID toevoegen"
2. Een medewerker leest een eID in die hij van de gebruiker krijgt, en vult eventuele
    ontbrekende velden in.
3. De gebruiker kan nu inloggen met zijn eID.

## Nieuwe klassen

Deze uitbreiding vereist aanpassingen in zowel de frontend als backend. Zo moet 
er grafische begeleiding zijn voor deze feature. Ook moet er in de backend 
ondersteuning komen voor het OpenID protocol, waarmee eID werkt. 

Voor de authenticatie is het momenteel moeilijk inschatten hoeveel nieuwe klassen 
er vereist zijn, aangezien er nog geen authenticatiesysteem is. Voor OpenID zijn
er echter plugins te vinden die met Spring Boot werken.

Er zou wel zeker een veld moeten komen bij de tabel `User`, die bijhoudt met welke
OpenID account hij geassocieerd dient te worden. Voor de rest hopen we zoveel mogelijk
aan de gebruikte plugin toe te vertrouwen.

## CAS

Er kan ook gebruik gemaakt worden van een eigen CAS-server, om dan via het principe van
"Authentication Delegation" meerdere authenticatie alternatieven aan te bieden naast het 
klassieke gebruikersnaam en wachtwoord. Denk bijvoorbeeld aan Facebook login, eID, Twitter.
Dit vereist iets meer werk, maar we verwachten dat zeker Facebook login een waardevolle
feature zou zijn.
