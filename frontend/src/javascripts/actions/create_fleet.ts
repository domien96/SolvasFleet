import { FLEETS_URL } from '../constants/constants.ts';

export default function(fleet_params : Fleet) {
  return fetch(FLEETS_URL, {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(fleet_params)
  });
}
