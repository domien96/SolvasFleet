# Backend testing

### Bestandsindeling
- unit tests: /backend/srs/test/
- integration-tests: /backend/src/itest/

**Unit tests**: het testen van één klasse, met zo weinig mogelijk invloed van externe klassen. Getters/setters worden niet getest, enkel complexe code.

**Integration tests**: Het testen van de samenwerking tussen verschillende componenten zoals
- http requests die frontend aan backend koppelen
- DAO-laag, die de databank met de backend koppelt


### Uitvoeren van tests - Gradle
De gradle build file wordt aangepast zodat integration-tests apart kunnen uitgevoerd worden. De normale unit tests worden meteen uitgevoerd na elke build.

De integration-tests kunnen uitgevoerd worden adhv het commando
```
$ gradle itest
```
Html reports zijn dan te vinden in de map /backend/build/reports/tests



### Mocking

Om klassen gemakkelijk afzonderlijk van elkaar te testen maken we gebruiken van mocking. We mocken de http server met mockmvc, hierdoor kunnen we dus testen of backend correct reageert op bepaalde requests.

Verder gaan we ook objecten van bepaalde klassen mocken aan de hand van mockito. Hiermee kunnen we bijvoorbeeld een valse 'DAO' object aanmaken, zodat we controllers kunnen testen zonder afhankelijk te zijn van implementaties van DAO.


### MockMVC (enkel bij integration tests)
Elke spring controller wordt getest met een object van de klasse MockMvc, voorlopig maken we hem op deze manier aan in de setup() methode van elke test.
```java
...
private MockMvc mockMvc;
...
@Before
public void setup()
{
  ...
  mockMvc= MockMvcBuilders.standaloneSetup(companyRestController).build();
  ...
}

```
Nu kunnen we een http server mocken en kijken of onze controllers aan onze verwachtingen voldoen. Hieronder een voorbeeld van hoe we een GET request zouden testen met mockMVC. De tests zouden falen indien één van de verwachtingen niet klopt.

```java
mockMvc.perform(get("/companies/1"))
                .andExpect(status().isOk()) //http200
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(...)))
                //controleren van de json response
                ...
                );

```

### Mockito (zowel bij unit tests als integration tests)
Mockito gaat ervoor zorgen dat we een volledige klasse kunnen mocken. Hierdoor kunnen we heel veel methodes controleren, zonder afhankelijk te zijn van fouten in andere klassen die complexe methodes bevatten. Het spreekt voor zich dat het niet echt nuttig zou zijn om een simpele data-klasse met enkel getters en setters te mocken.

Als voorbeeld gebruik ik het hier om DAO's te mocken, bij de integratietests van de controllers. Eerst moeten we ervoor zorgen dat mockito geïnitialiseerd wordt.

```java
@Before
public void setup()
{
  ...
  MockitoAnnotations.initMocks(this);
  ...
}
```

Nu kunnen we gebruik maken van annotaties van mockito. Er zijn 2 annotaties waar we gebruik van maken: @Mock en @InjectMocks

De eerste geeft aan dat een object een mock voorstelt, de andere geeft aan dat de mocks moeten geinjecteerd worden in het object.
```java
    @InjectMocks
    private CompanyRestController companyRestController;

    @Mock
    private CompanyDao companyDaoMock;
```
De mock gaat de DAO dus nabootsen. Default geven alle methodes die objecten teruggeven 'null' terug. boolean methodes false en integers 0. void methodes doen niets.

Soms is dit niet gewenst en dit kunnen we dan gemakkelijk aanpassen met de volgende lijn code:

```java
  when(companyDaoMock.find(anyInt())).thenReturn(new Company(...));
```    
Wanneer de controller nu gaat zoeken naar een bepaalde id, dan geeft deze een object terug met de waarden die je in de test zelf aangeeft. anyInt() is een matcher, deze zorgt er dus voor dat dit geldt voor elke integer waarde.

### Mockito + MVC

 Tenslotte geef ik een voorbeeld hoe de twee technologiën samen gebruikt kunnen worden:
```java
 @Test
    public void testGetCompanyById_noerror() throws Exception {
        when(companyDaoMock.find(anyInt())).thenReturn(company); 1
        mockMvc.perform(get("/companies/1")) 2
                .andExpect(status().isOk()) 3
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8)) 4
                .andExpect(content().string(json(...))); 5
    }
```  
Dit doet het volgende:
1. Aangeven dat de methode find(id) het object 'company' teruggeeft bij de mock
2. Een get request uitvoeren via het MVC mock zodat naar de controller wordt gestuurd dat de company met id 1 gevraagd wordt
3. Verwacht dat de response status 200 is (gelukt)
4. Verwacht dat de content van de response in JSON_UTF8 staat
5. Verwacht dat de json content gelijk is aan een bepaalde string

### Future: FEST-assert


### DAO Testing

Het testen van de DAO layer gebeurt aan de hand van een embedded database (moet nog allemaal geconfigureerd worden, h2 of hsqldb) zodat testen vlot gebeurt en dat er niets wordt uitgevoerd op de werkelijke database

Voorlopig begint elke test met een databank met lege tabellen, in de toekomst zou er echter een import script moeten zijn 
