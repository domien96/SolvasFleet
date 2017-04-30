#Antwoorden op de vragen naar de klant:

> antwoord op inloggegevens:

```
Bedankt Nils, ik gooi het in de groep bij onze experten op kantoor.

Zou het kunnen dat het enkel via Google Chrome lukt om in te loggen?

Dit zijn de features van Milestone 2 ?
De pdf download lukt nu al wel, het automatisch opstellen van de factuur voor een gekozen afrekeningsperiode mis ik nog wel (=zéér belangrijk)

Probeer dit goed uit te werken:
Maak meerdere voertuigtypes aan met minstens 2 verzekeringswaarborgen EN de juiste taksen en commissie% en laat er een globale factuur MET detail uitrollen.
Zou TOP zijn.

Je zou de taal van de applicatie ook afhankelijk kunnen maken aan de user-id.

... telkens je een stap hebt gezet mag je mij updaten.

Met vriendelijke groeten,
Voor de Solvas-groep, 
Patrick Oostvogels
```

>Stel dat de verzekering eindigt middenin een vlootinschrijving van een voertuig, bvb de verzekering eindigt op 23 maart en het voertuig is ingeschreven bij een vloot van 1/1/17 tot 1/1/18, moet die premie dan nog steeds volledig betaald worden of slechts deels?

*In voorkomend geval wordt er pro rata een terugbetaling gedaan voor dat voertuig voor de premie die niet "verbruikt" is. 1 dag = 1/365 en in een schrikkeljaar 1/366*


---

>Moet er ook een historie bijgehouden worden van veranderingen van gegevens van klanten, gebruikers, etc. Als voorbeeld: moet er bijgehouden worden op welk tijdstip een bedrijf veranderde van naam?

*Als je logboek bijhoudt van alle acties lijkt het mij logisch van wel.  Het is in elk geval noodzakelijk om de historiek van elk voertuig en dus ook vloot bij te houden.*


---


>Hoewel een voertuig hoofdzakelijk bij maar 1 bedrijf aangesloten zal zijn, is dit geen garantie. Is het voor Solvas interessant dat we een geschiedenis bijhouden voor ieder voertuig, bij welke bedrijven of banken het voertuig ooit aangesloten was (in context van leasing)?

*De geschiedenis van een voertuig is niet onmiddellijk interessant.  Maar eens een voertuig is gekoppeld geweest aan een bank of een leasingmaatschappij dat wordt dit gegeven wel behouden in de DB en kan men dit altijd terugvinden....*

>Zijn overschreven kosten, zoals bvb de commissie, gelinkt aan een voertuig of aan een subvloot? In andere woorden hebben alle voertuigen van een bepaald type van een subvloot dezelfde overschreven kosten, of kan het zijn dat de commissie op de pw van de baas verschilt met de commissie op de pw van een werknemer?

*de commissie hangt vast aan de verzekeringswaarborg van een voertuig.
je kan per verzekeringswaarborg van een  voertuig een default gebruiken maar de mogelijkheid moet bestaan om er van af te wijken...*



---

>Zijn 'burgerlijke aansprakelijkheid', 'omnium', en 'rechtsbijstand' 
>de enige types van verzekeringen, of moeten wij de mogelijkheid ondersteunen om verder nog andere verzekeringen toe te voegen?

*Er zijn  nog  andere  types verzekeringswaarborgen die men "kentekengerelateerd" zou kunnen opnemen in de webapplicatie, dus die ondersteuning is wenselijk.*

>Worden subvloten opgedeeld per type, of per subvlootpolis? In andere
>woorden: in het meegegeven voorbeeldscenario, mogen de drie personenwagens opgesplitst worden in twee subvloten die elders verzekerd zijn?

*Een subvloot wordt gekenmerkt door het type van voertuig en de toepasselijke verzekeringstaks. De 3 pw maken dus deel uit van dezelfde subvloot.*

>Wordt het makelaarsloon bij niet-forfaitaire premies ook berekend door een percentage te nemen van het percentage van de verzekerde waarde? Indien ja, staat er een bovenlimiet (of onderlimiet) op?

*In theorie staat er daar geen limiet op.*
*Voor de goede orde; het makelaarsloon is een % van de netto-premie.*
