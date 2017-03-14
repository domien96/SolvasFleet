import fetchJSON from './fetch_json.ts';
import { VEHICLES_URL } from '../constants/constants.ts';

export default function(type : string, fleet : number) {
	if(type != '' && fleet != null){
		return fetchJSON(VEHICLES_URL + '?type=' + type +'&fleet=' + fleet);
	}
	if(type == '' && fleet != null){
		return fetchJSON(VEHICLES_URL + '?fleet=' + fleet);
	}
	if(type != '' && fleet == null){
		return fetchJSON(VEHICLES_URL + '?type=' + type);
	}
	return fetchJSON(VEHICLES_URL);
}
