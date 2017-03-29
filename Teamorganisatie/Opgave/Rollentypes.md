Mail van professor Goedgebeur 23 maart
--------------------------------
Rollen en bijkomende info van Solvas

Beste studenten,

Ik heb met Patrick overlegd over de Rollen die hij graag in jullie applicatie zou zijn. Namelijk:

Administrator
Productiebeheerder
Schadebeheerder
Verzekeringsmaatschappij
Klant - verzekeringsnemer
Boekhouding
Mogelijkheid toevoeging bijkomende rollen
 
Algemeen
Om discussie te vermijden laat men in de applicatie per rol enkel de onderwerpen zien waarin men kan werken of raadplegen.
 
Administrator
Kan alles doen
 
Productiebeheerder
Alle acties in applicatie uitgezonderd :

Retroactieve aanpassingen (dekkingsaanvragen, schrappingen of aanpassing van verzekerde kapitalen, i.e. alle acties met financiële gevolgen)
Overschrijven van sommige data
Securitylevels aanpassen
Paswoord administratie (bv: resetten na 3x foutief aanloggen e.d.)
Nieuwe verzekeraars en waarborgen laten opnemen
 
Schadebeheerder en Boekhouding
Kunnen alles raadplegen MAAR niets wijzigen.
 
Verzekeringsmaatschappij
Kan alles raadplegen over haar eigen klanten.
 
Klant
Kan zijn eigen polis en facturen raadplegen.

Opmerking: Een goed werkend rollensysteem is belangrijk, maar sommige andere features van de applicatie zijn nog belangrijker. Dus als jullie merken dat jullie in je planning in tijdsnood zullen komen, kunnen jullie eventueel als vereenvoudiging alle Solvas-medewerkers Administrator rechten geven (en dus voorlopig geen productie- of schadebeheerderrollen implementeren).

Verder heeft Patrick ook nog een aantal andere zaken verder toegelicht:

Elke polis heeft 01/01/xx als hoofdvervaldag van het contract
Aanpassing van verzekerde kapitalen kan principieel enkel op de hoofdvervaldag van het contract (tenzij admin anders beslist)
1 premiezetting per voertuigtype per verzekeringsmaatschappij
Groene kaarten voor voertuigen mogen pas afgeleverd worden NA betaling van de premie (1-3-6-12 maandelijks), een aanduiding van uitgifte en datum van GK is een troef.
Er  wordt 1 polisnummer per takscategorie per klant aangemaakt
Wat moet er zichtbaar zijn voor een klant (en bij uitbreiding minstens voor de andere gebruikers)
Overzicht van polis (= alg gegevens)
Vloot met verzekerde waarden en dekkingen die actief zijn of zullen komen + klikken op button om gearchiveerde voertuigen op te roepen
De voertuigdetails (zie divbis lijst) zijn vanuit de vloot oproepbaar
Jaarpremie en premiedetails per voertuig en/of per afrekeningsperiode (oproepbaar)
De mutaties van de hangende afrekeningsperiode en de stand van zaken qua (financiële) afrekening
Clausules
 

Bij voorkeur kan er van elk scherm een xls of pdf gemaakt worden
 
Elementen voor de premieberekening :
Welke factoren bepalen concreet de delen van een verzekeringspremie?

Als makelaar vraag je aan een verzekeringsmaatschappij offertes op voor een bepaald risico (in voorkomend geval een vloot met diverse verzekeringswaarborgen).
In tegenstelling tot particuliere verzekeringen zijn er bij bedrijfsverzekeringen geen op voorhand gekende premies of tarieven.
Een onderschrijver bij een verzekeringsmaatschappij geeft een offerte in functie van
    De grootte van de vloot = premievolume
    De hoogte van de vrijstelling (ook eigen risico of franchise genoemd)
    Het schadeverleden van de vloot (=statistiek, om de structurele schadelast te bepalen)
    De ervaring van de verzekeraar met bepaalde risico’s (= ADR vervoer (=gevaarlijke stoffen) of het afleveren van pakjes voor Amazon zijn verschillende risico’s…)
    De “honger” om premie te maken op een bepaald ogenblik.
 
De eerste 3 factoren zijn objectief en vanuit die elementen bepaalt men de risicopremie.
Risicopremie + makelaarsloon = nettopremie
Netto-premie + verzekeringstaksen en bijdragen = brutopremie

De structuur van de premie in de applicatie hangt af van de verzekeringswaarborg.
Voor Omnium is dit het verzekerde kapitaal x een tarief (%) + verzekeringstaks OF een forfaitaire premie OF een combinatie van beide
Bvb. 2% op het verzekerde kapitaal met een minimum netto-premie van 500 €
Voor BA Auto is dit een forfaitaire premie.  Men kan dit eventueel koppelen aan een subvloot en binnen een subvloot nog verschillen terug vinden als
bvb. tussen 50 en 100 KW en +100 KW
Rechtsbijstand zal ook meestal een forfaitaire premie zijn in functie van de aard van het type voertuig  (subvloot).
 
Afrekeningsperiodes
- Afrekeningsregime = duur van de groene kaart
4 x 3 vaste maandelijkse betalingen / afrekeningsperiode jaarlijks
4 x 3 vaste maandelijkse betalingen / afrekeningsperiode 6 maandelijks
4 x 3 vaste maandelijkse betalingen / afrekeningsperiode 3 maandelijks
2 x 6 vaste maandelijkse betalingen / afrekeningsperiode jaarlijks
2 x 6 vaste maandelijkse betalingen / afrekeningsperiode 6 maandelijks
1 x vaste jaarlijkse betaling / afrekeningsperiode jaarlijks
12 x vaste maandelijkse betalingen / afrekeningsperiode 6 maandelijks (mits goedkeuring admin)
12 x vaste maandelijkse betalingen / afrekeningsperiode jaarlijks (mits goedkeuring admin)


Met vriendelijke groeten,
Jan
