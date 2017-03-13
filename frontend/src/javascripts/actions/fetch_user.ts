import fetchJSON from './fetch_json.ts';
import { USER_URL } from '../constants/constants.ts';

export default function(id : number) {
  return fetchJSON(USER_URL(id));
}
