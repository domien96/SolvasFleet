import { VEHICLES_URL } from '../constants/constants.ts';

export default function(id : number) {
  return fetch(VEHICLES_URL + '/' + id, {
    method: 'DELETE'
  });
}
