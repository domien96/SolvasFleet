# Testhandleiding - Frontend

### Inleiding
Het testen van de frontend gebeurt in 2 delen:
1. Unit tests (pure functies)
2. Component tests (react)

De tests bevinden zich onder `/frontend/src/__tests__` en hebben steeds de volgende extentie: \*.test.tsx

### Gebruikte technologiën
- Jest als testomgeving
- Enzyme voor het testen van **React components**

### Uitvoeren van de tests
Dit gebeurt ook via node, de tests gaan er vanuit dat de nodige packages geïnstalleerd werden.
```
$ npm install  (indien nodig)
$ npm test
```

Coverage meten doen we aan de hand van het volgende commando: `npm test -- --coverage`.

Het verslag van de coverage vinden we in `/frontend/coverage/lcov-report/index.html`

### Aanmaken van een test
De tests in het .tsx bestand zullen steeds de volgende vorm hebben:
``` javascript
test('naam test', () => {
  expect(1).toBe(1);
});
```

### Mounten en testen van een component
Voor react components te testen maken we gebruik van Enzyme, hiermee kunnen we componenten shallow renderen of mounten. Vervolgens is het de bedoeling om te controleren of het component aan bepaalde voorwaarden voldoet.
``` javascript
import { mount, shallow } from 'enzyme';
import InvoiceView from '../../javascripts/components/invoices/InvoiceView.tsx';
...

test('...', () => {
  const invoiceView = mount(<InvoiceView invoice={ inv } onDownload={ onDownload }/>);
  ...
  expect(invoiceView.find('DownloadButton').prop('onDownload')).toEqual(onDownload);
  ...
});
```

### Jest Mocks
Via jest kunnen we mocks maken van functies. Hieronder een kort voorbeeld (bekijk de jest documentatie voor meer voorbeelden).

``` javascript
jest.mock('../../javascripts/actions/fleet_actions.ts', () => ({
  fetchFleets: jest.fn().mockImplementation((id, success) =>
    success({ data: [{ id: "1", name: "Transport 1", company: "1",
     facturationPeriod: "1", paymentPeriod: "30" }] }));
  })
);
```
