export function pluck(object: any, keys: string[]): any {
  const ret: any = {};
  for (const key of keys) {
    ret[key] = object[key];
  }
  return ret;
}

export function humanize(s: string): string {
  if (s === null || s === undefined) {
    return '';
  }

  return s.replace(/_/g, ' ')
  .replace(/(\w+)/g, (match) => (match.charAt(0).toUpperCase() + match.slice(1)));
}

export function hasError(this: any, k: string): boolean {
  const errors = this.state.errors.filter((el: Form.Error) => (el.field === k));
  return (errors.length !== 0);
}

export function th(key: string | number, label: string | number | boolean): Table.Head.Data {
  return { key: (key || '').toString(), label: (label || '').toString() };
}

export function group_by(xs: any[], key: string) {
  return xs.reduce((rv, x) => {
    (rv[x[key]] = rv[x[key]] || []).push(x);
    return rv;
  }, {});
}

export function createQuery(query: any, filter: any) {
  if (query) {
    let newQuery = query;
    if (filter) {
      for (const key in filter) {
        if (filter[key] === null || filter[key] === undefined || filter[key] === '') {
          delete filter[key];
        }
      }
      for (const key in filter) {
        newQuery[key] = filter[key];
      }
    }
    return newQuery;
  } else {
    for (const key in filter) {
      if (filter[key] === null || filter[key] === undefined || filter[key] === '') {
        delete filter[key];
      }
    }
    return filter;
  }
}
