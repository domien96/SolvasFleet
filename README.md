# 2016-2017-groep-06

## Aandachtspunten omtrent kwaliteit van code
Hou volgende zaken zeker in het hoofd bij het programmeren! Ze kunnen ons een hoop onnodig werk besparen.
Deze lijst is zeker nog niet af, dus check regelmatig voor toevoegingen.

* Als je code smells ruikt en dus denkt dat bepaalde code vervangen kan worden efficientere, snellere en -het liefste van allemaal- super leesbare code:
  * maak issue met label "code smell"
  * zet in commentaar "//TODO mogelijke verbetering" naast de beschouwde code.
</br></br>Voordelen?
   * Dit zorgt ervoor dat we toch (tijdelijk) door kunnen gaan met lelijke code (ipv blijven hangen op zoek naar betere code) om aan de klant al een werkend product af te leveren en later deze nog kunnen verbeteren.
  Het is natuurlijk niet de bedoeling om vlug vlug van de hele file iets werkend te maken waardoor de hele file vol staat met werkende, maar lelijke code en "//todo mogelijke verbetering" lijnen .
   * Tijdswinst: Anderen kunnen meezoeken of hebben misschien ooit een gelijkaardige code smell gezien 

* Als je veronderstellingen maakt over variabelen of invoerparameters bij het schrijven van je code schrijf die dan ook neer bij de code.
Gebruik hiervoor het assert keyword of Exceptions (zie de [Oracle documentatie](http://docs.oracle.com/javase/8/docs/technotes/guides/language/assert.html#usage-conditions).
</br>Voordeel: Dit versnelt het debugwerk massa's snel in die gevallen waar de programmeur typo's of foute veronderstellingen over expressies heeft gemaakt.
</br>bv. 
```
if (i % 3 == 0) {
   ...
} else if (i % 3 == 1) {
    ...
} else {
    assert i % 3 == 2 : i;
    ...
}
```
```
int sum(int a, int b) {
    assert (Integer.MAX_VALUE - a >= b) : "Value of " + a + " + " + b + " is too large to add.";
  final int result = a + b;
    assert (result - a == b) : "Sum of " + a + " + " + b + " returned wrong sum " + result;
  return result;
}
```
```
Dit voorbeeld is enkel representatief voor private methoden!
/**
* @param age: age always has to be positive for correct functionality.
*/
private void printAge(int age) { 
 assert(age >= 0);
 System.out.print(age);
}
```

## Algemene afspraken
* Er wordt geen code gedeeld naar buiten, noch overgenomen uiteraard.
* Passwoorden worden evenmin naar buiten gebracht.
* **Issues zijn prioritair!**
* De documenteertaal (binnenin de source files) is steeds Engels.
* Wordt zeker vervolgd..
