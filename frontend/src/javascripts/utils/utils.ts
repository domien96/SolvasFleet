export function pluck(object : any, keys : string[]) : any {
  var ret : any = {};
  for(let key of keys) {
    ret[key] = object[key];
  }
  return ret;
}
