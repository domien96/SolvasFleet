# USE CASE: Gebruiker oplijsten

### Beschrijving
Het oplijsten van gebruikers met de webapplicatie. Dit gebeurt eventueel aan de hand van een filter. Het is belangrijk dat dit enkel kan gebeuren door gebruikers met de juiste privileges.

Bijvoorbeeld: niet alle gebruikers van een transportbedrijf kunnen zien wie er gebruiker is van het transportbedrijf. Alleen die met de privilege om dit te doen.
### Actoren
1 van de volgende:
- De schade- of productiebeheerder
- Een gebruiker van de verzekeringsmaatschappij
- Een gebruiker van een transportbedrijf
- De administrator

### Preconditie
- De gebruiker die de lijst wil verkrijgen heeft zich reeds ingelogd op de webapplicatie
- De gebruiker heeft privileges om de gebruikers te bekijken

### Normale flow
1. De gebruiker navigeert op het platform naar een plaats waar hij de gebruikers kan oplijsten
2. (optioneel) De gebruikers geeft specifieke filters mee: zoektermen, sorteervormen,...
3. De gebruiker bevestigt de zoekopdracht
4. De gebruiker krijgt de lijst te zien

### Postconditie
- Buiten het toevoegen van een actie aan de log verandert er niets aan de databank
