import { USERS_URL } from '../constants/constants.ts';

export default function(user_params : User) {
  return fetch(USERS_URL, {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(user_params)
  });
}
