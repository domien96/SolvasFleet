# Toelichting interactiediagrammen
Volgens de api kan men momenteel de backend aanspreken met GET, POST, PUT en DELETE HTTP requests (voortaan verzoekmethoden genoemd).
We gebruiken sequentiediagrammen om de verschillende interne boodschappen, die intern tussen de verschillende backend objecten worden uitgewisseld, weer te geven.
Alle diagrammen zijn te vinden in het projectbestand. Voor het gemak werden er tevens afbeeldingsbestanden van ieder diagram bijgevoegd.

- Het diagram voor de GET verzoekmethode vindt u onder sequentiediagram_GET.png .
- Het diagram voor POST en PUT zijn vanwege analogie samengenomen en vindt u onder sequentiediagram_POST_PUT.png .
- Voor DELETE werd er geen extra sequentiediagram opgesteld aangezien deze op vlak van structuur t.e.m. stap 1.1.5 gelijkaardig aan het sequentiediagram van de GET verzoekmethode is.
	De namen van de acties bij de pijlen zijn echter wel meestal verschillend, maar wijzen zichzelf uit als u de bijhorende code bekijkt. De request komt binnen in de restcontroller
	van het betrokken model. Van daaruit kunt u de programmaflow volgen.
	Vergeleken met het diagram van GET zijn er geen mapping stappen (stap 1.1.6 & 1.1.7), omdat er geen modelobject wordt teruggegeven vanuit de controller.
	De controller geeft enkel http-headerinformatie (en geen body) terug waaronder de statuscode.