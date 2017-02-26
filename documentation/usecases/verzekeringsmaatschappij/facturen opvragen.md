Primaire actor: Medewerker verzekeringsmaatschappij (voortaan gewoonweg medewerker genoemd)

Objectief: De verzekeringsmaatschappij moet een overzicht kunnen krijgen met alle facturen van alle aangesloten makelaars.

Normale flow:

1 De medewerker geeft aan dat hij de facturen wil opvragen.

2 Er wordt gevraagd naar een start en einddatum waartussen hij wil zoeken (dit is verplicht).
  Hierbovenop kunnen optionele parameters zoals een bepaalde makelaar, betaalstatus (betaald of nog niet betaald) of bedraginterval kunnen meegegeven worden.

3 De medewerker bevestigt de parameters en geeft deze aan het systeem door.

4 Het systeem stelt intern deze lijst op en toont deze aan de medewerker.

5 De zoekparameters kunnen veranderd worden waardoor men terugvalt naar stap 2 of men kan deze lijst printen.

Pre-condities: De medewerker is ingelogd op de webapplicatie en is verbonden met een verzekeringsmaatschappij.

Post-condities: De medewerker ontvangt een lijst van de facturen waarvoor hij geintresseerd is.

Alternatieve flow:

(5). De printer werd niet gevonden of er vond een andere IO-fout plaats.
      a. Het systeem toont de foutmelding afkomstig van de printer.
      
      b. Ga naar stap 5 en probeer opnieuw.

Exceptionele flow:

(1). De medewerker heeft geen rechten om de facturen te bekijken.
     Het systeem toont een foutmelding, met de mogelijkheid om rechten aan te vragen aan een overste van de verzekeringsmaatschappij.

(4). De medewerker heeft geen rechten om de facturen te bekijken.
     Het systeem toont een foutmelding, met de mogelijkheid om rechten aan te vragen aan een overste van de verzekeringsmaatschappij.
