import { VEHICLES_URL } from '../constants/constants.ts';

export default function(vehicle_params : Vehicle) {
  return fetch(VEHICLES_URL, {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(vehicle_params)
  });
}
