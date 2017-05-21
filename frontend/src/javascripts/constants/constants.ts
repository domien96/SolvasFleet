declare const ENVIRONMENT: string;

let BASE_API_URL: string;
if (ENVIRONMENT === 'development') {
  BASE_API_URL = 'http://localhost:8080';
} else {
  BASE_API_URL = 'https://vopro6.ugent.be/rest';
}

export const USERS_URL                = `${BASE_API_URL}/users`;
export function USER_URL(id: number) { return `${USERS_URL}/${id}`; }

export const CLIENTS_URL                = `${BASE_API_URL}/companies`;
export function CLIENT_URL(id: number) { return `${CLIENTS_URL}/${id}`; }

export const VEHICLES_URL                = `${BASE_API_URL}/vehicles`;
export function VEHICLE_URL(id: number) { return `${VEHICLES_URL}/${id}`; }
export function VEHICLE_GREENCARD_PDF_URL(id : number) {
  return `${VEHICLES_URL}/${id}/greencard.pdf`;
}
export const VEHICLES_UPLOAD            = `${VEHICLES_URL}/upload`;

export function FLEETS_URL(id: number) { return `${BASE_API_URL}/companies/${id}/fleets`; }
export function FLEET_URL(id: number, companyId: number) { return `${BASE_API_URL}/companies/${companyId}/fleets/${id}`; }

export const AUTH_LOGIN_URL = `${BASE_API_URL}/auth/login`;
export const AUTH_TOKEN_URL = `${BASE_API_URL}/auth/token`;

export const SIGNED_IN_URL = '/users';

export const AUTH_ROLES_URL     = `${BASE_API_URL}/auth/roles`;
export function AUTH_ROLE_URL(id: number) { return `${BASE_API_URL}/auth/roles/${id}`; }
export const AUTH_PERMISSIONS_URL   = `${BASE_API_URL}/auth/permissions`;

export function FUNCTIONS_URL(userId: number) { return `${USERS_URL}/${userId}/functions`; }
export function FUNCTION_URL(userId: number, functionId: number) {
  return `${USERS_URL}/${userId}/functions/${functionId}`;
}

export const ACTION_LANG = 'CHANGE_LANG';

export function INVOICES_URL(companyId: number, fleetId: number) {
<<<<<<< HEAD
  return `${BASE_API_URL}/companies/${companyId}/fleets/${fleetId}/invoices`;
}
export function INVOICE_URL(companyId: number, fleetId: number, invoiceId: number) {
  return `${BASE_API_URL}/companies/${companyId}/fleets/${fleetId}/invoices/${invoiceId}`;
}
export function INVOICE_PDF_URL(companyId: number, fleetId: number, invoiceId: number) {
  return `${BASE_API_URL}/companies/${companyId}/fleets/${fleetId}/invoices/${invoiceId}${'.pdf'}`;
}

export function CORRECT_INVOICE_URL(fleetId: number) {
  return `${BASE_API_URL}/fleets/${fleetId}/invoices/correct`;
}

export const CONTRACTS_URL                = `${BASE_API_URL}/contracts`;
export function CONTRACT_URL(id: number) { return `${CONTRACTS_URL}/${id}`; }
export function CONTRACTS_COMPANY_URL(id: number) { return `${CLIENTS_URL}/${id}/contracts`; }

export const TYPES_URL                = `${BASE_API_URL}/contracts/types`;

export const AUDIT_URL                = `${BASE_API_URL}/audit`;
export function AUDIT_ENTRY_URL(id: number) { return `${AUDIT_URL}/${id}`; }


export const COMMISSION_URL = `${BASE_API_URL}/commissions`;
