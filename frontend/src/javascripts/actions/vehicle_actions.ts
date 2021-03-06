import { GET, POST, PUT, DELETE, callback } from './fetch_json.ts';
import { VEHICLE_URL, VEHICLES_URL, VEHICLE_GREENCARD_PDF_URL, VEHICLES_UPLOAD, VEHICLES_BY_FLEET_URL } from '../constants/constants.ts';
import { GETPDF } from './fetch_pdf.ts';
import { POSTFILE } from './upload_file.ts';

export function fetchVehicle(id: number, success?: callback, fail?: callback) {
  GET( VEHICLE_URL(id), success, fail );
}

export function fetchVehicles(success?: callback, fail?: callback, query?: any) {
  GET ( VEHICLES_URL, success, fail, query );
}

export function fetchVehiclesByFleet(fleedId: number, companyId: number, success?: callback, fail?: callback, query?: any) {
  GET ( VEHICLES_BY_FLEET_URL(fleedId,companyId), success, fail, query );
}

export function postVehicle(body: any, success?: callback, fail?: callback) {
  POST( VEHICLES_URL, body, success, fail );
}

export function putVehicle( id: number, body: any, success?: callback, fail?: callback) {
  PUT( VEHICLE_URL(id), body, success, fail );
}

export function deleteVehicle( id: number, success?: callback, fail?: callback) {
  DELETE( VEHICLE_URL(id), success, fail );
}

export function fetchGreencardPdf(id: number, success?: callback, fail?: callback, query?: any) {
  GETPDF( VEHICLE_GREENCARD_PDF_URL(id), success, fail, query );
}

export function postVehiclesFile(body: any, success?: callback, fail?: callback) {
  POSTFILE( VEHICLES_UPLOAD, body, success, fail );
}
