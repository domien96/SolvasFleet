import {
  AUTH_LOGIN_URL,
  AUTH_PERMISSIONS_URL,
  AUTH_ROLE_URL,
  AUTH_ROLES_URL,
  AUTH_TOKEN_URL,
} from '../constants/constants.ts';
import { AUTH, callback, DELETE, GET, POST, PUT } from './fetch_json.ts';

import Auth from '../modules/Auth.ts';

const EMPTY_PROMISE = new Promise((resolve) => resolve());

export function auth_login(email: string, password: string, success?: callback, fail?: callback) {
  AUTH ( AUTH_LOGIN_URL, { username: email, password }, EMPTY_PROMISE, success, fail );
}

export function auth_token(success?: callback, fail?: callback) {
  AUTH ( AUTH_TOKEN_URL, undefined, Auth.getRefreshToken(), success, fail );
}

// Roles & permissions

export function fetchRole(id: number, success?: callback, fail?: callback) {
  GET( AUTH_ROLE_URL(id), success, fail );
}

export function fetchRoles(success?: callback, fail?: callback) {
  GET( AUTH_ROLES_URL, success, fail);
}

export function putRole(id: number, body: any, success?: callback, fail?: callback) {
  PUT( AUTH_ROLE_URL(id), body, success, fail );
}

export function postRole(body: any, success?: callback, fail?: callback) {
  POST( AUTH_ROLES_URL, body, success, fail );
}

export function deleteRole(id: number, success?: callback, fail?: callback) {
  DELETE( AUTH_ROLE_URL(id), success, fail );
}

export function fetchPermissions(success?: callback, fail?: callback) {
  GET( AUTH_PERMISSIONS_URL, success, fail );
}
