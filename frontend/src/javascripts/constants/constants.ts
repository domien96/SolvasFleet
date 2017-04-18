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

export const AUTH_LOGIN_URL = `${BASE_API_URL}/auth/login`;
export const AUTH_TOKEN_URL = `${BASE_API_URL}/auth/token`;

export const SIGNED_IN_URL = '/users';

export const AUTH_ROLES_URL 		= `${BASE_API_URL}/auth/roles`;
export const AUTH_PERMISSIONS_URL 	= `${BASE_API_URL}/auth/permissions`;

export function FUNCTIONS_URL(userId: number) { return `${USERS_URL}/${userId}/functions`; }
export function FUNCTION_URL(userId: number, functionId: number) { return `${USERS_URL}/${userId}/functions/${functionId}`; }