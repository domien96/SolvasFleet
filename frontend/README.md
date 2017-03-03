# Frontend

## Installation Guide

Make sure you have [https://nodejs.org/en/](NodeJS).
Run the following command to install all modules.

`npm install`

Om de site te builden in production gebruik je
`npm run build`

Indien je in development wilt werken, gebruik je
`npm run dev`
Dit commando blijft runnen, en zal je changes automatisch hercompileren.

In beide gevallen krijg je een public directory, met daarin een `index.html`, 
een `bundle.js` en een `bundle.css`. Open deze in de browser, en voila!

## Gettings started

Voeg een file toe in `src/javascripts/src/` met extensie `tsx`.
Gebruik voor de file de volgende structuur:

```typescript
import React from 'react';

export default class Component extends React.Component<ComponentProps, {}> {
  render() {
    <div>
      Hello { this.props.name }!
    </div>
  }
}
```

De 2 type-argumenten voor React.Component zijn de props (properties) en de state.
Vergeet ComponentProps niet toe te voegen aan `src/javascripts/types/interfaces.d.ts`.
(Het maakt niet uit waar ze staan, zolang ze maar ooit geinclude worden. Maar dat is de juiste plek.)
De interface `.d.ts` wijst erop dat de file uiteindelijk geen output zal opleveren. Het zijn enkel
types voor Typescript.
```typescript
// src/javascripts/types/interfaces.d.ts
interface ComponentProps {
  name : string; 
}
```

Als je nu `src/javascripts/index.tsx` aanpast om je component te includen, kun je 
het resultaat hiervan zien.
```typescript
// src/javascripts/index.tsx
...
import Component from './components/Component.tsx';
...
render (<Component name = 'benji' />, document.getElementById('app'));
```

Voeg verder componenten toe, met uitgebreide hulpfuncties en html, tot je een volledige webapp hebt!

## Directory structuur

`package.json`
Config file voor NodeJS, bevat algemene info en de modules die geïnstalleerd moeten worden.

`tsconfig.json`
Config file voor Typescript. Bevat opties voor de typescript loader.

`webpack.config.js`
Config file voor webpack. Bevat alle compile regels, om de TypeScript modules correct te bundlen.

`type_declarations`
Bevat een paar type declaraties. Nodig om TypeScript tevreden te houden.

`src/stylesheets`
Bevat alle Sass files. Geschikt op de frontend op te maken.

`src/javascripts/index.tsx`
Entry voor webpack. Vanuit deze file worden de dependencies afgehandeld, en alle modules gebundled.

`src/javascripts/components/`
Bevat de componenten waaruit de frontend opgebouwd zijn.

`src/javascripts/constants/`
Bevat een paar constanten.

`src/javascripts/types/`
Bevat verschillende types voor TypeScript, zoals de interfaces.

`src/javascripts/utils/`
Bevat verschillende hulpfuncties.
