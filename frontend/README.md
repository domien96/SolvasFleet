# Frontend

## Installation Guide

Make sure you have [https://nodejs.org/en/](NodeJS).
Run the following command to install all modules.

`npm install`
`npm run typings`

Om de site te builden in production gebruik je
`npm run build`

Indien je in development wilt werken, gebruik je
`npm run dev`
Dit commando blijft runnen, en zal je changes automatisch hercompileren.

In beide gevallen krijg je een public directory, met daarin een `index.html`, 
een `bundle.js` en een `bundle.css`. Open deze in de browser, en voila!

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

`src/javascripts/router.tsx`
Bevat de volledige route logica voor de React App, combineert alle componenten samen tot 1 component.

`src/javascripts/components/`
Bevat de componenten waaruit de frontend opgebouwd zijn.

`src/javascripts/constants/`
Bevat een paar constanten.

`src/javascripts/types/`
Bevat verschillende types voor TypeScript, zoals de interfaces.

`src/javascripts/utils/`
Bevat verschillende hulpfuncties.

`translations`
Bevat `.yml`-files met de vertalingen.
