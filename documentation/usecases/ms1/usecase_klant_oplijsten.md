# USE CASE: Klant oplijsten

### Beschrijving
Het oplijsten van de klanten met de webapplicatie. Dit gebeurt eventueel aan de hand van een filter. Het is belangrijk dat dit enkel kan gebeuren door gebruikers met de juiste privileges.
### Actoren
1 van de volgende:
- De schade- of productiebeheerder
- Een gebruiker van de verzekeringsmaatschappij
- De administrator

### Preconditie
- De gebruiker die de lijst wil verkrijgen heeft zich ingelogd
- De gebruiker heeft privileges om de klanten te bekijken die in de lijst staan (anders worden ze weggefilterd)

### Normale flow
1. De gebruiker navigeert op het platform naar een plaats waar hij de klanten kan oplijsten
2. (optioneel) De gebruikers geeft specifieke filters mee: zoektermen, sorteervormen,...
3. De gebruiker bevestigt de zoekopdracht
4. De gebruiker krijgt de lijst te zien

### Postconditie
- Buiten het toevoegen van een actie aan de log verandert er niets aan de databank
