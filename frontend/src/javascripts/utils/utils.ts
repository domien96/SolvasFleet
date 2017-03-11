export function pluck(object : any, keys : string[]) : any {
  var ret : any = {};
  for(let key of keys) {
    ret[key] = object[key];
  }
  return ret;
}

export function humanize(s : string) : string {
  return s.replace(/_/g, ' ')
    .replace(/(\w+)/g, function(match) {
      return match.charAt(0).toUpperCase() + match.slice(1);
    });
}
