import { COMPANY_URL } from '../constants/constants.ts';

export default function(id : number) {
  return fetch(COMPANY_URL(id), {
    method: 'DELETE'
  });
}
