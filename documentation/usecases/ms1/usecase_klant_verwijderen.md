# USE CASE: Klant verwijderen

### Beschrijving
Het verwijderen van een specifieke klant. Dit gaat gepaard met een bezoek van de klant aan het kantoor van solvas, die dan meldt dat de samenwerking stopgezet wordt. In principe blijft alles van de klant op de databank staan, om consistentie te behouden.

### Actoren
- De schade- of productiebeheerder (medewerker van solvas)
- De klant

### Preconditie
- De klant heeft reeds een account
- De medewerker van solvas heeft zich ingelogd op het platform

### Normale flow
1. De klant komt bij solvas op kantoor en meldt dat de samenwerking stopt
2. De solvas medewerker zoekt de klant op
3. Het bedrijf van de klant verkrijgt de status "deactivated"

### Postconditie
- De klant kan zijn gegevens niet meer raadplegen via de webapplicatie (de gebruikers van het bedrijf krijgen automatisch geen toegang meer op het platform)
