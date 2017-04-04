import { GET, POST, PUT, DELETE, callback } from './fetch_json.ts';
import { CONTRACT_URL, CONTRACTS_URL, CLIENTS_URL } from '../constants/constants.ts';

export function fetchContract(id : number, success? : callback, fail? : callback) {
  GET( CONTRACT_URL(id), success, fail );
}

export function fetchContracts(success? : callback, fail? : callback, query? : any) {
  GET( CONTRACTS_URL, success, fail, query );
}

export function fetchContractsByParams(companyId : number, fleetId : number, vehicleId : number, success? : callback, fail? : callback, query? : any) {
  let url = `${CLIENTS_URL}/${companyId}/fleets/${fleetId}/vehicles/${vehicleId}/contracts`;
  GET( url, success, fail, query );
}

export function postContract(body : any, success? : callback, fail? : callback) {
  POST( CONTRACTS_URL, body, success, fail );
}

export function putContract(id : number, body : any, success? : callback, fail? : callback) {
  PUT( CONTRACT_URL(id), body, success, fail );
}

export function deleteContract(id : number, success? : callback, fail? : callback) {
  DELETE( CONTRACT_URL(id), success, fail );
}
