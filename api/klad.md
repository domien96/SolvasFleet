# Kladspecificatie API

## Algemeen

De API zal gedefinieerd worden met Swagger/OpenApi.

## Kladversie Milestone 1

Om vlugger te kunnen beginnen staat hieronder een ruwe versie van de API voor Milestone 1.

### Voertuig

#### Attributen
- id
- license_plate
- chassis_number
- brand
- model
- type
- kilometer_count
- year
- leasing_company
- value
- company
- created_at
- updated_at
- url

#### Endpoints

| Methode  | Endpoint         | Beschrijving           |
|----------|------------------|------------------------|
| `POST`   | `/vehicles`      | Aanmaken               |
| `PUT`    | `/vehicles`      | Bijwerken              |
| `GET`    | `/vehicles`      | Ophalen alles          |
| `GET`    | `/vehicles/{id}` | Ophalen individueel    |
| `DELETE` | `/vehicles/{id}` | Verwijderen/archiveren |

### Bedrijf (transportbedrijf)

#### Attributen

- id
- name
- vat_number
- phone_number
- adres
- created_at
- updated_at
- url

#### Endpoints

| Methode  | Endpoint          | Beschrijving           |
|----------|-------------------|------------------------|
| `POST`   | `/companies`      | Aanmaken               |
| `PUT`    | `/companies`      | Bijwerken              |
| `GET`    | `/companies`      | Ophalen alles          |
| `GET`    | `/companies/{id}` | Ophalen individueel    |
| `DELETE` | `/companies/{id}` | Verwijderen/archiveren |

### Gebruikersbeheer

#### Gebruikers

##### Attributen

- id
- firstname
- lastname
- email
- password
- created_at
- updated_at
- url

##### Endpoints

| Methode  | Endpoint      | Beschrijving           |
|----------|---------------|------------------------|
| `POST`   | `/users`      | Aanmaken               |
| `PUT`    | `/users`      | Bijwerken              |
| `GET`    | `/users`      | Ophalen alles          |
| `GET`    | `/users/{id}` | Ophalen individueel    |
| `DELETE` | `/users/{id}` | Verwijderen/archiveren |

#### Rollen

##### Attributen

- id
- company
- function
- user
- start_date
- end_date
- created_at
- updated_at
- url

##### Endpoints

| Methode  | Endpoint      | Beschrijving           |
|----------|---------------|------------------------|
| `POST`   | `/roles`      | Aanmaken               |
| `PUT`    | `/roles`      | Bijwerken              |
| `GET`    | `/roles`      | Ophalen alles          |
| `GET`    | `/roles/{id}` | Ophalen individueel    |
| `DELETE` | `/roles/{id}` | Verwijderen/archiveren |


## Overige

Voor het onderdeel subvloot is geen API aangemaakt, omdat besloten is dat een subvloot
niet veel meer is dan een filter per type op de voertuigen.