# Uitbreiding

## Beschrijving

Een mogelijke uitbreiding die in dit stadium al toegevoegd kan worden is het
controloren en/of opvragen van informatie uit externe databanken. Het voorbeeld
dat hier besproken wordt, gebruikt de informatie voortvloeiend uit het BTW-
nummer (uit bv. VIES).

In grote lijnen is het de bedoeling om er zeker van te zijn dat het BTW-nummer
correct is. Hiervoor wordt het toevoegen van een nieuw bedrijf in 2 stappen
verdeeld. Het proces om een bedrijf toe te voegen ziet er dan als volgt uit:

1. De medewerker van Solvas voert het BTW-nummer in van het toe te voegen
   bedrijf.
2. De server gebruikt dit BTW-nummer en zoekt dit op in een externe databank,
   zoals VIES.
3. Als het nummer klopt stuurt de server het nummer terug, samen met de andere
   informatie uit die databank (zoals het adres).
4. De medewerker komt op de huidige pagina om bedrijven toe te voegen, maar de
   gevonden gegevens zijn al ingevuld, en kunnen eventueel verbeterd worden.
   
Als bij stap 3 het BTW-nummer niet correct blijkt, wordt een foutboodschap
gestuurd en wordt gevraagd om het nummer te verbeteren, of om eventueel toch
door te gaan (hierbij is gedacht aan de mogelijkheid dat de exterene databank
zelf verkeerd of onvolledig is).

## Nieuwe klassen

Deze uitbreiding kan zowel in de frontend als backend geïmplementeerd worden.
Er is gekozen om ze volledig op de frontend te implementeren, omdat de fronted
sowieso toch aangepast moeten worden, en op deze manier niets aan de backend 
moet veranderen.

Er is geen probleem met het toevoegen aan de frontend: deze controle maakt geen
deel uit van het probleemdomein, noch van de API. Het is slechts een uitgebreid
hulpmiddel bij het invullen van de gegevens, conceptueel gelijkaardig aan bv.
het rood worden van het e-mailinvoerveld als de e-mail niet het juiste formaat
heeft, behalve dat deze controle volledig optioneel is: voor ons domein maakt
het niet uit of het BTW-nummer al geregistreerd is of niet. Uiteraard is het
wel mogelijk om deze vereiste toe te voegen aan het domeinmodel, maar dat doen
we hier niet.

Een lijst van nieuwe klassen/bestanden:

- `src/javascripts/components/clients/AddClientVat.tsx`, een formulier waar het
  BTW-nummer opgegeven wordt door een medewerker. Dit zal uiteraard lijken op
  reeds bestanden formulieren, maar zal uit 1 in te vullen veld bestaan.
- `src/javascripts/actions/fetch_vat.ts`, het bestand dat de code bevat die
  de informatie ophaalt uit de externe databank.
  
Aan te passen klassen/bestanden:

- `src/javascripts/components/clients/Clients.tsx`, waar de knop voor nieuwe 
  bedrijven aangepast moet worden om naar de nieuwe pagina `AddClientVat.tsx`
  te gaan.
- `src/javascripts/index.tsx`, waar de nieuwe pagina ook als route moet
  toegevoegd worden.