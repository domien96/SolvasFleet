import fetchJSON from './fetch_json.ts';
import { COMPANIES_URL } from '../constants/constants.ts';

export default function() {
  return fetchJSON(COMPANIES_URL);
}
