import fetchJSON from './fetch_json.ts';
import { USERS_URL } from '../constants/constants.ts';

export default function() {
  return fetchJSON(USERS_URL);
}
