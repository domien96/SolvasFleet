import { AUTH, callback } from './fetch_json.ts';
import { AUTH_LOGIN_URL, AUTH_TOKEN_URL } from '../constants/constants.ts';

import Auth from '../modules/Auth.ts';

const EMPTY_PROMISE = new Promise((resolve) => resolve() )

export function auth_login(email : string, password : string, success? : callback, fail? : callback) {
  AUTH ( AUTH_LOGIN_URL, { username: email, password }, EMPTY_PROMISE, success, fail );
}

export function auth_token(success? : callback, fail? : callback) {
  AUTH ( AUTH_TOKEN_URL, Auth.getRefreshToken(), success, fail );
}
