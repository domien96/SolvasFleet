
# Visie van de api

Dit document geeft de visie voor de API, alsook vragen/problemen die nog opgelost moeten worden.

## URL

In het vak Internettechnologie werd een zeer strenge omschrijving gemaakt van wat een REST-api is (zie [deze slide](http://rubenverborgh.github.io/WebFundamentals/web-apis/#no-api) en verder).

Beknopt houdt dit in dat de API dezelfde endpoints heeft als de gewone website, maar waarbij verzoeken middels 'content negotation' een andere representatie (hier json) moeten vragen.

Vaak wordt een api echter op een ander subdomein of url geïmplementeerd.

Welke optie moet gebruikt worden is nog onduidelijk.

## Endpoints

Het systeem voor de API is redelijk eenvoudig. Een voorbeeld voor 2 functionaliteiten voor een vloot staat hieronder uitgewerkt.

### Overzicht

Geeft alle vloten weer.

> GET /fleets

Vereist `Authorisation`-header voor identificatie.

#### Succes

Een JSON-lijst met alle vloten.

#### Fout

`403` indien de `Authorisation`-header leeg was of de gebruiker niet de juiste permissies heeft.

#### Data van een vloot

Toont data voor een individuele vloot.

> GET /fleets/[id]

Vereist `Authorisation` header voor identificatie.

#### Parameter

| Naam | Beschrijving         |
|------|----------------------|
| `id` | Het ID van de vloot. |

#### Succes

Een JSON-object met de informatie voor een vloot.

#### Fout

`403` indien de `Authorisation`-header leeg was of de gebruiker niet de juiste permissies heeft.


## Nog te beslissen

### Hoe moet de JSON-gestructureerd worden?

Er zijn verschillende mogelijkheden, van een letterlijke vertaling (bv. JSON-lijsten, JSON-objecten, ...) tot echte specificaties, zoals o.a. [jsonapi](http://jsonapi.org/).

### De beste manier om aan alles te geraken

Bepaalde groepen zullen waarschijnlijk wel een verzekeraar en een transportbedrijf gesplitst hebben. Zij zullen dan waarschijnlijk een url willen als:

> GET /insurers/[id]/cars

Omdat wij dat niet hebben, zou het bij ons eerder zoiets zijn:

> GET /companies/[id]/cars

In ons geval moet dan nog bijvoorbeeld uitgemaakt worden hoe een onderscheid gemaakt wordt tussen auto's die verzekerd zijn, en degene die in leasing zijn.

### Hoeveel data we in één keer terugsturen

Als bijvoorbeeld een transportbedrijf opgevraagd wordt, hoeveel data sturen we mee in één keer?

> GET /transport/[id]

Geven we enkel de bedrijfsinformatie terug? Moeten we ook al vloten meegeven? En facturen?

Het terugsturen van minder gegevens heeft als voordeel dat de backend minder complex wordt, en dus ook sneller dingen kan terugsturen, waardoor data vlugger gedeeltelijk getoond kan worden (d.w.z. dat een overzichtspagina voor een bedrijf al direct getoond kan worden, zonder alle vloten/facturen). 

Aan de andere kant verhoogt het de complexiteit aan de frontend: dan is deze verantwoordelijk voor het correct ophalen van alle data, wat ook een overhead heeft: meerdere AJAX-verzoeken. Ook zijn misschien niet altijd alle gegevens nodig, wat ook kan leiden tot nutteloze overdrachten.

Een andere mogelijkheid is het conditioneel maken van de informatie, bv.

> GET /transport/[id]?fleets=true&bills=true

Dit verhoogd wel de complexiteit aan de serverzijde, maar ontlast de frontend zonder nutteloos data te versturen. Ik zou aanraden om dit laatste te doen, voor zover dat mogelijk is.

# Status van de api

Er is op dit moment nog niets gebeurd omtrent de api.