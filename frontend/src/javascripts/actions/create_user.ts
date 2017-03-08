import { USERS_URL } from '../constants/constants.ts';

export default function(user_params : User) {
  console.log(user_params);
  var data = new FormData();
  data.append( 'json', JSON.stringify( user_params ) );

  return fetch(USERS_URL, {
    method: 'POST',
    headers: {
      'Accept': 'application/json, text/plain, */*',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(user_params)
  });
}
