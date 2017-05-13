import { callback, GET } from './fetch_json.ts';
import { AUDIT_URL } from '../constants/constants.ts';

export function fetchLog(success?: callback, fail?: callback, query?: any) {
  GET( AUDIT_URL, success, fail, query );
}