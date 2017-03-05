## 18 Februari
### Aandachtspunten omtrent kwaliteit van code
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
Gebruik hiervoor het assert keyword of Exceptions (zie de [Oracle documentatie](http://docs.oracle.com/javase/8/docs/technotes/guides/language/assert.html#usage-conditions)).
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

## 5 maart
We hebben in de slack besloten om SonarQube te gebruiken om onze commits te controleren.
Deze gaat onder andere na dat alle variabalen in Camelcase genoteerd zijn, alle klassen en methoden gedocumenteerd zijn, enz... .
SonarQube is geactiveerd op David zijn Github account.

Er is echter een probleem bij commentaar op een pull request:

Alle commentaar door David zijn account wordt verwijderd indien een nieuwe commit gepusht worden naar de pull request.
Hieronder valt ook de commentaar die niet door SonarQube, maar door David manueel werd geplaatst.
Dit is een probleem, want die manuele commentaar wordt dan niet gezien door de contributors van de pull request.
Het is mogelijk om het automatisch verwijderen van alle commentaren (van David zijn account) uit te schakelen, maar hierdoor zullen de
commentaren van sonarQube blijven staan. Deze zijn meestal relatief groot in aantal en het is daarom handig dat deze automatisch verwijderd worden waardoor de manuele commentaren beter zichtbaar worden.

Domien stelde voor om een extra ugentgithub account speciaal voor sonarQube aan te vragen. Hij zal hiervoor informeren bij de cursusbeheerders.
