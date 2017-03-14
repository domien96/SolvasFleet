import fetchJSON from './fetch_json.ts';
import { VEHICLES_URL } from '../constants/constants.ts';

export default function(type : string) {
  	return fetchJSON(VEHICLES_URL +'?type=' + type); 
}
