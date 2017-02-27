# USE CASE: Klant aanmaken

### Beschrijving
Het toevoegen van een de klant aan de databank. Dit geeft de klant zelf nog geen toegang tot de webapplicatie, want hiervoor moet er nog een gebruiker aangemaakt worden.

### Actoren
- De schade- of productiebeheerder (medewerker van solvas)
- De klant

### Preconditie
- De klant (het bedrijf) werd nog niet toegevoegd aan de databank.

### Normale flow
1. Een bedrijf komt bij solvas op kantoor met de intentie om klant te worden
2. De klant geeft de nodige informatie door aan de solvas medewerker
3. De solvas medewerker voegt de klant toe op de webapplicatie

### Postconditie
- De klant werd opgeslaan in de databank
- De geheime data van het bedrijf is onzichtbaar voor gebruikers zonder de juiste privileges (zoals andere klanten)
