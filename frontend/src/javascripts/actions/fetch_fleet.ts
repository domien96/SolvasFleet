import fetchJSON from './fetch_json.ts';
import { FLEET_URL } from '../constants/constants.ts';

export default function(id : number) {
  return fetchJSON(FLEET_URL(id));
}
