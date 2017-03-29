import { GET, POST, PUT, DELETE, callback } from './fetch_json.ts';
import { VEHICLE_URL, VEHICLES_URL } from '../constants/constants.ts';

export function fetchVehicle(id : number, success? : callback, fail? : callback) {
  GET( VEHICLE_URL(id), success, fail );
}

export function fetchVehicles(success? : callback, fail? : callback, query? : any) {
  const querystring = Object.keys(query).map((k) => {
    return `${k}=${query[k]}`
  }).join('&');
  GET ( `${VEHICLES_URL}?${querystring}`, success, fail );
}

export function postVehicle(body : any, success? : callback, fail? : callback) {
  POST( VEHICLES_URL, body, success, fail );
}

export function putVehicle( id : number, body : any, success? : callback, fail? : callback) {
  PUT( VEHICLE_URL(id), body, success, fail );
}

export function deleteVehicle( id : number, success? : callback, fail? : callback) {
  DELETE( VEHICLE_URL(id), success, fail );
}
