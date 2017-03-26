import { GET, POST, PUT, DELETE, callback } from './fetch_json.ts';
import { USER_URL, USERS_URL } from '../constants/constants.ts';

export function fetchUser(id : number, success? : callback, fail? : callback) {
  GET( USER_URL(id), success, fail );
}

export function fetchUsers(success? : callback, fail? : callback) {
  GET( USERS_URL, success, fail);
}

export function postUser(body : any, success? : callback, fail? : callback) {
  POST( USERS_URL, body, success, fail );
}

export function putUser(id : number, body : any, success? : callback, fail? : callback) {
  PUT( USER_URL(id), body, success, fail );
}

export function deleteUser(id : number, success? : callback, fail? : callback) {
  DELETE( USER_URL(id), success, fail );
}
