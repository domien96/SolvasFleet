import fetchJSON from './fetch_json.ts';
import { VEHICLES_URL } from '../constants/constants.ts';

export default function(fleetId : number, type : string) {
  	return fetchJSON(VEHICLES_URL +'?fleet=' + fleetId + '&type='+ type); //TODO FILTER VEHICLES ON FLEET AND TYPE
}