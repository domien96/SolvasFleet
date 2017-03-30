import { POST, callback } from './fetch_json.ts';
import { AUTH_LOGIN_URL } from '../constants/constants.ts';

export function auth_login(email : string, password : string, success? : callback, fail? : callback) {
  POST ( AUTH_LOGIN_URL, { username: email, password }, success, fail );
}
