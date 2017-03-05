We hebben in de slack besloten om SonarQube te gebruiken om onze commits te controleren.
Deze gaat onder andere na dat alle variabalen in Camelcase genoteerd zijn, alle klassen en methoden gedocumenteerd zijn, enz... .
SonarQube is geactiveerd op David zijn Github account.

Er is echter een probleem bij commentaar op een pull request:
Alle commentaar door David zijn account wordt verwijderd indien een nieuwe commit gepusht worden naar de pull request.
Hieronder valt ook de commentaar die niet door SonarQube, maar door David manueel werd geplaatst.
Dit is een probleem, want die manuele commentaar wordt dan niet gezien door de contributors van de pull request.
Het is mogelijk om het automatisch verwijderen van alle commentaren (van David zijn account) uit te schakelen, maar hierdoor zullen de
commentaren van sonarQube blijven staan. Deze zijn meestal relatief groot in aantal en het is daarom handig dat deze automatisch verwijderd worden
waardoor de manuele commentaren beter zichtbaar worden.
Domien stelde voor om een extra ugentgithub account speciaal voor sonarQube aan te vragen. Hij zal hiervoor informeren bij de cursusbeheerders.
