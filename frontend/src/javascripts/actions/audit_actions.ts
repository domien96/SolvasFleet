import { callback, GET } from './fetch_json.ts';
import { AUDIT_URL, AUDIT_ENTRY_URL } from '../constants/constants.ts';

export function fetchLog(success?: callback, fail?: callback, query?: any) {
  GET( AUDIT_URL, success, fail, query );
}

export function fetchLogEntry(id: number, success?: callback, fail?: callback, query?: any) {
  GET( AUDIT_ENTRY_URL(id), success, fail, query );
}
