import { USERS_URL } from '../constants/constants.ts';

export default function(user_params : User) {
  return fetch(USERS_URL, {
    method: 'POST',
    body: user_params
  });
}
