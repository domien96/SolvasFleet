import { callback, DELETE, GET, POST, PUT } from './fetch_json.ts';
import { GETPDF } from './fetch_pdf.ts';
import {
  FLEETS_URL,
  FLEET_URL,
  INVOICES_URL,
  INVOICE_PDF_URL,
  INVOICE_URL,
} from '../constants/constants.ts';

export function fetchFleet(id : number, companyId : number, success? : callback, fail? : callback) {
  GET( FLEET_URL(id,companyId), success, fail );
}

export function fetchFleets(id: number, success?: callback, fail?: callback, query?: any) {
  GET( FLEETS_URL(id), success, fail, query );
}

export function postFleet(companyId: number, body: any, success?: callback, fail?: callback) {
  POST( FLEETS_URL(companyId), body, success, fail );
}

export function putFleet(id : number, companyId : number, body : any, success? : callback, fail? : callback) {
  PUT( FLEET_URL(id,companyId), body, success, fail );
}

export function deleteFleet(id : number, companyId : number, success? : callback, fail? : callback) {
  DELETE( FLEET_URL(id,companyId), success, fail );
}

export function fetchInvoices(fleetId: number, success?: callback, fail?: callback, query?: any) {
  GET( INVOICES_URL(fleetId), success, fail, query );
}

export function fetchInvoice(fleetId: number, invoiceId: number, success?: callback, fail?: callback, query?: any) {
  GET( INVOICE_URL(fleetId, invoiceId), success, fail, query );
}

export function fetchInvoicePdf(fleetId: number, invoiceId: number, success?: callback, fail?: callback, query?: any) {
  GETPDF( INVOICE_PDF_URL(fleetId, invoiceId), success, fail, query );
}
