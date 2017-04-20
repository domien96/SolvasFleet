export function pluck(object : any, keys : string[]) : any {
  var ret : any = {};
  for(let key of keys) {
    ret[key] = object[key];
  }
  return ret;
}

export function humanize(s : string) : string {
  if (s == null || s == undefined) {
    return '';
  }

  return s.replace(/_/g, ' ')
    .replace(/(\w+)/g, function(match) {
      return match.charAt(0).toUpperCase() + match.slice(1);
    });
}

export function hasError(this: any, k : string) : boolean {
  const errors = this.state.errors.filter(function(el : Form.Error) { return el.field == k; });
  return (errors.length != 0);
}

export function th(key : string | number, label : string | number | boolean) : Table.Head.Data {
  return { key: (key||'').toString(), label: (label || '').toString() };
}

export function group_by(xs : any[], key : string) {
  return xs.reduce(function(rv, x) {
    (rv[x[key]] = rv[x[key]] || []).push(x);
    return rv;
  }, {});
};
