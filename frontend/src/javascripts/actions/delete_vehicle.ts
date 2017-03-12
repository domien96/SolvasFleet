import { VEHICLE_URL } from '../constants/constants.ts';

export default function(id : number) {
  return fetch(VEHICLE_URL(id), {
    method: 'DELETE'
  });
}
