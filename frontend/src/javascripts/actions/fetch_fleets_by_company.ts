import fetchJSON from './fetch_json.ts';
import { FLEETS_URL } from '../constants/constants.ts';

export default function(companyId : number) {
  	return fetchJSON(FLEETS_URL +'?company=' + companyId); //TODO FILTER BY COMPANY ID
}
