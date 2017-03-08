# API

## Specificatie

De specificatie staat in het bestand `volledig.yaml`, en volgt [The Open Specification API](https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md)
(vroeger de Swagger Specification).

## Lezen

Het lezen van specificatie gebeurt best aan de hand van de gegenereerde bestanden. In de map `generated` zijn
er 3 verschillende versies. Het bestand `generated-adoc.html` leest het gemakkelijkst, maar voor bepaalde onderdelen
zijn de andere versies beter.

Als er onduidelijkheden zijn in de bestanden, raadpleeg de specificatie zelf of contacteer de API-beheerder.

## Genereren

Er zijn twee manieren om leesbare versies te genereren van de specificatie:

1. Met [Swagger CodeGen](http://swagger.io/swagger-codegen/), naar HTML (de beide versies zijn gegeven).
2. Met [Swagger2Markup](https://github.com/Swagger2Markup/swagger2markup), naar Ascii Doc. Vanuit dit formaat kan het
dan naar andere formaten overgezet worden (zoals HTML, ook gegeven).