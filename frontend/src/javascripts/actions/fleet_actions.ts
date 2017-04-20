import { GET, POST, PUT, DELETE, callback } from './fetch_json.ts';
import { FLEET_URL, FLEETS_URL, CLIENT_URL, INVOICE_URL, INVOICES_URL } from '../constants/constants.ts';

export function fetchFleet(id : number, success? : callback, fail? : callback) {
  GET( FLEET_URL(id), success, fail );
}

export function fetchFleets(success? : callback, fail? : callback, query? : any) {
  GET( FLEETS_URL, success, fail, query );
}


export function fetchFleetsByCompany(companyId : number, success? : callback, fail? : callback, query? : any) {
  GET( CLIENT_URL(companyId)+'/fleets', success, fail, query );
}

export function postFleet(body : any, success? : callback, fail? : callback) {
  POST( FLEETS_URL, body, success, fail );
}

export function putFleet(id : number, body : any, success? : callback, fail? : callback) {
  PUT( FLEET_URL(id), body, success, fail );
}

export function deleteFleet(id : number, success? : callback, fail? : callback) {
  DELETE( FLEET_URL(id), success, fail );
}

export function fetchInvoices(fleetId : number, success? : callback, fail? : callback, query? : any) {
  GET( INVOICES_URL(fleetId), success, fail, query );
}

export function fetchInvoice(fleetId : number, invoiceId : number, success? : callback, fail? : callback, query? : any) {
  GET( INVOICE_URL(fleetId, invoiceId), success, fail, query );
}