# API

## Specificatie

De specificatie staat in het bestand `volledig.yaml`, en volgt [The Open Specification API](https://github.com/OAI/OpenAPI-Specification/blob/master/versions/2.0.md)
(vroeger de Swagger Specification).

## Lezen

Om de documentatie te lezen, wordt de best het HTML-bestand gelezen in de map generated. Als de documentatie
onduidelijk zou zijn, contacteer de API-beheerder of lees de specificatie zelf.

De voornaamste bestanden zijn `generated/generated-primary.html` en `generated/generated-examples.html`.
Dat laatste bestand bevat ook voorbeelden van antwoorden.


## Generator

Er is een generator om zelf het de HTML (of Markdown, AsciiDoc, PDF) te genereren.

Hiervoor moet eerst het project gebuild worden met Gradle. Dit is normaal gezien eenvoudig. 
Vanuit de `api`-map:
```
gradlew build
```

Daarna kan het worden uitgevoerd:
```
java -jar build/libs/ApiGenerator-1.0.jar -i volledig.yaml -o generated/docs
```
Dit zal een HTML-bestand `generated/docs.html` maken.

Volledig gebruik:
```
usage: generator [-e | -f <md|adoc|html|pdf> | -i <spec.yaml> | -o
       <output>]  [-h]
 -e,--examples                    Generate examples
 -f,--format <md|adoc|html|pdf>   Output format. Can be md for Markdown,
                                  adoc for Asciidoc, html for HTML or pdf
                                  for PDF
 -h,--help                        Display help and usage
 -i,--input <spec.yaml>           The API spec file.
 -o,--output <output>             The output file (without extension)
```
