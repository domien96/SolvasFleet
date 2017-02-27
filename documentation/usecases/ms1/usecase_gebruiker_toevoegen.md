# USE CASE: Gebruiker aanmaken

### Beschrijving
Het toevoegen van een gebruiker aan de databank. Dit geeft de gebruiker toegang tot de webapplicatie. Een gebruiker kan het volgende zijn:
1. Een administrator
2. Een schade-of productiebeheerder
3. Een klant (transportbedrijf)
4. Een verzekeringsmaatschappij

### Actoren
- Een medewerker van solvas (administrator, schade- of productiebeheerder)
- Een gebruiker (zie lijst)

### Preconditie
- De persoon die een gebruiker wilt staat nog niet in de databank
- Het bedrijf van de persoon staat al in de databank (in het geval van een transportbedrijf of verzekeringsmaatschappij)
- De medewerker van solvas heeft zich reeds ingevuld op de webapplicatie

### Normale flow
1. De persoon contacteert solvas om een gebruiker aan te maken zodat hij de webapplicatie kan gebruiken.
2. De persoon geeft de nodige informatie door aan de solvas medewerker
3. De solvas medewerker voegt de klant toe aan de databank via de webapplicatie

### Postconditie
- De gebruiker werd opgeslaan in de databank
- De geheime data van de gebruiker is onzichtbaar voor gebruikers zonder de juiste privileges (zoals andere klanten)
- De gebruiker kan de webapplicatie gebruiken
