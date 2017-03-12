import fetchJSON from './fetch_json.ts';
import { COMPANIES_URL } from '../constants/constants.ts';

export default function(id : number) {
  	return fetchJSON(COMPANIES_URL + '/fleets/' +id+ '/subfleets'); //TODO fix right url
}