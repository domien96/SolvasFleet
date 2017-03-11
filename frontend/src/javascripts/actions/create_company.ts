import { COMPANIES_URL } from '../constants/constants.ts';

export default function(company_params : Company) {
  return fetch(COMPANIES_URL, {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(company_params)
  });
}
