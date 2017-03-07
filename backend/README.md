# Suggested technologies

* Gradle
* Java Spring (Obligatory)
* JUnit
* Hibernate

# Gebruik

## Databank

1. Installeer Java 8.
2. Installeer [PostgreSQL](https://www.postgresql.org/download/) en start het op. PostgreSQL moet poort 5432 gebruiken.
3. Maak een nieuwe databank `vop`. Maak ook een gebruiker `vop` aan met als wachtwoord `vop`. Op de commandolijn:
    1. Open de commandolijninterface van PostgreSQL:
    ```
    psql
    ```
    2. Maak de nieuwe databank:
    ```sql
    CREATE DATABASE vop;
    ```
    3. Maak de gebruiker en verleen hem de nodige rechten:
    ```sql
    CREATE USER vop WITH PASSWORD 'vop';
    GRANT ALL PRIVILEGES ON DATABASE "vop" TO vop;
    ```
    
Dit is uiteraard slechts eenmaal nodig.
    
## Spring

De default configuratie runnen vanuit Gradle:
```
gradlew bootRun
```

De jar builden:
```
gradlew build
```

De jar uitvoeren:
```
java -jar build/libs/SolvasFleet-0.1.0.jar
```

Er zijn 3 profielen:

- `default` - het standaardprofiel
- `debug` - print de gebruikte SQL-queries
- `clean` - verwijdert de volledige databank bij het opstarten (alle gegevens uit de databank zijn dus weg!)

Voer een profiel uit:
```
java -jar -Dspring.profiles.active=clean build/libs/SolvasFleet-0.1.0.jar
```

De webapplicatie draait op poort 8080.

# Opbouw

De applicatie bestaat uit 3 pakketten:

- `models` - De domeinlaag. Bevat de modellen.
- `persistence` - De persistentielaag. Slaat de modellen op in de databank. Meer uitleg hieronder.
- `rest` - De applicatielaag. De implementatie van de API.

## Persistentielaag

De persistentielaag slaat de modellen op in een PostgreSQL-databank. We gebruiken hiervoor Hibernate.
De configuratie voor Hibernate is te vinden in het bestand `resources/hibernate.properties` en de klasse
`solvas.persistence.HibernateConfig`.

De mapping van de modellen naar de databank gebeurt in `.hbm.xml`-bestanden, die te vinden zijn in de map 
`resources/mappings`.

Databankmigraties zijn te vinden in de map `resources/db.migration` en worden automatisch toegepast bij het 
opstarten van de app.
 
Tip: het wijzigen van de migraties gaat niet zonder de databank te wissen. Bij het werken aan de migraties is
het daarom handig om Spring uit te voeren met het profiel `clean`. Dit zal automatisch de databank wissen elke
keer de applicatie gestart wordt.