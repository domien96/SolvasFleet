declare var ENVIRONMENT: string;
declare var BASE_API_URL: string;

if (ENVIRONMENT == "development") {
  var BASE_API_URL = "http://localhost:8080";
} else {
  var BASE_API_URL = 'https://vopro6.ugent.be/rest';
}

export const USERS_URL                = `${BASE_API_URL}/users`;
export function USER_URL(id : number) { return `${USERS_URL}/${id}`; }

export const COMPANIES_URL               = `${BASE_API_URL}/companies`;
export function COMPANY_URL(id : number) { return `${COMPANIES_URL}/${id}`; }

export const VEHICLES_URL                = `${BASE_API_URL}/vehicles`;
export function VEHICLE_URL(id : number) { return `${VEHICLES_URL}/${id}`; }
