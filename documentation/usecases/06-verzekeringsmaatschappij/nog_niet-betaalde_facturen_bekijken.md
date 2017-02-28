## Use case: Nog niet betaalde facturen van een verzekeringsmaatschappij bekijken

**Primaire actor:** Medewerker verzekeringsmaatschappij (voortaan gewoonweg medewerker genoemd)

**Objectief:** De verzekeringsmaatschappij moet kunnen zien welke facturen nog niet betaald geweest zijn door de aangesloten makelaars.

**Normale flow:**

1. <\<include "facturen opvragen">> De medewerker geeft aan het systeem de waarde "niet betaald" mee voor de zoekparameter "betaalstatus".

2. Het systeem toont de gewenste lijst aan de medewerker.

**Pre-condities:** De medewerker is ingelogd op de webapplicatie en is aangesloten als werknemer bij een verzekeringsmaatschappij.

**Post-condities:** De medewerker ziet een lijst van alle niet-betaalde facturen.

**Exceptionele flow:**

* (1). De medewerker heeft geen rechten om de facturen te bekijken.

  a. Het systeem toont een foutmelding, met de mogelijkheid om rechten aan te vragen aan een overste van de verzekeringsmaatschappij.
