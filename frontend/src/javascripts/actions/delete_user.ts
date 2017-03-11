import { USERS_URL } from '../constants/constants.ts';

export default function(id : number) {
  return fetch(USERS_URL + '/' + id, {
    method: 'DELETE'
  });
}
