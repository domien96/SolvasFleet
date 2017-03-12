import { COMPANY_URL } from '../constants/constants.ts';

export default function(id : number, fleet_params : Fleet) {
  return fetch(COMPANY_URL(id) + '/' + 'fleets', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(fleet_params)
  });
}
