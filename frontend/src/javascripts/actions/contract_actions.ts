import { GET, POST, PUT, DELETE, callback } from './fetch_json.ts';
import { CONTRACT_URL, CONTRACTS_URL, TYPES_URL } from '../constants/constants.ts';

export function fetchContract(id : number, success? : callback, fail? : callback) {
  GET( CONTRACT_URL(id), success, fail );
}

export function fetchContracts(success? : callback, fail? : callback, query? : any) {
  GET( CONTRACTS_URL, success, fail, query );
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

export function fetchTypes(success? : callback, fail? : callback, query? : any) {
  GET( TYPES_URL, success, fail, query );
}