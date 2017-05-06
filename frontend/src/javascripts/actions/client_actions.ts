import { GET, POST, PUT, DELETE, callback } from './fetch_json.ts';
import { CLIENT_URL, CLIENTS_URL } from '../constants/constants.ts';

export function fetchClient(id : number, success? : callback, fail? : callback) {
  GET( CLIENT_URL(id), success, fail );
}

export function fetchClients(success? : callback, fail? : callback, query? : any) {
  GET( CLIENTS_URL, success, fail, query);
}

export function postClient(body : any, success? : callback, fail? : callback) {
  POST( CLIENTS_URL, body, success, fail );
}

export function putClient(id : number, body : any, success? : callback, fail? : callback) {
  PUT( CLIENT_URL(id), body, success, fail );
}

export function deleteClient(id : number, success? : callback, fail? : callback) {
  DELETE( CLIENT_URL(id), success, fail );
}
