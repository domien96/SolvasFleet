import fetchJSON from './fetch_json.ts';
import { VEHICLE_URL } from '../constants/constants.ts';

export default function(id : number) {
  return fetchJSON(VEHICLE_URL(id));
}
