import fetchJSON from './fetch_json.ts';
import { FLEETS_URL } from '../constants/constants.ts';

export default function() {
  	return fetchJSON(FLEETS_URL);
}
