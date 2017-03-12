import fetchJSON from './fetch_json.ts';
import { COMPANY_URL } from '../constants/constants.ts';

export default function(id : number) {
  	return fetchJSON(COMPANY_URL(id) + '/fleets');
}
