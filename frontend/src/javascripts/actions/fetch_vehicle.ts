import fetchJSON from './fetch_json.ts';
import { VEHICLES_URL } from '../constants/constants.ts';

export default function(id : number) {
  	return fetchJSON(VEHICLES_URL + '/' + id);
}