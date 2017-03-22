import fetchJSON from './fetch_json.ts';
import { VEHICLES_URL } from '../constants/constants.ts';

export default function(args : [string]) {
  var url = VEHICLES_URL;
  var first : boolean = true;
  for (var arg in args){
    if(first){
      url += '?'+arg;
      first = false;
    }
    else{
      url += '&'+arg;
    }
  }
  return fetchJSON(url);
}
