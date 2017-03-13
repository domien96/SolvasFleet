import { USER_URL } from '../constants/constants.ts';

export default function(id : number) {
  return fetch(USER_URL(id), {
    method: 'DELETE'
  });
}
