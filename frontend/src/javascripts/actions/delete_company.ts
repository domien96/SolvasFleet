import { COMPANIES_URL } from '../constants/constants.ts';

export default function(id : number) {
  return fetch(COMPANIES_URL + '/' + id, {
    method: 'DELETE'
  });
}
