declare var ENVIRONMENT: string;
declare var BASE_API_URL: string;

if (ENVIRONMENT == "development") {
  var BASE_API_URL = "http://localhost:8080";
} else {
  var BASE_API_URL = 'https://vopro6.ugent.be/rest';
}

export const USERS_URL                = `${BASE_API_URL}/users`;
export function USER_URL(id : number) { return `${USERS_URL}/${id}`; }

export const CLIENTS_URL                = `${BASE_API_URL}/companies`;
export function CLIENT_URL(id : number) { return `${CLIENTS_URL}/${id}`; }

export const VEHICLES_URL                = `${BASE_API_URL}/vehicles`;
export function VEHICLE_URL(id : number) { return `${VEHICLES_URL}/${id}`; }

export const FLEETS_URL                = `${BASE_API_URL}/fleets`;
export function FLEET_URL(id : number) { return `${FLEETS_URL}/${id}`; }

export function INVOICES_URL(fleetId : number) { return `${BASE_API_URL}/fleets/${fleetId}/invoices`; }
export function INVOICE_URL(fleetId : number, invoiceId : number) { return `${BASE_API_URL}/fleets/${fleetId}/invoices/${invoiceId}`; }
