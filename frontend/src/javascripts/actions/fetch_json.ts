import Auth from '../modules/Auth.ts';

export type callback = (value? : any) => any;

function request (
  url : string,
  method : string,
  body? : any,
  success? : callback,
  fail? : callback
) {

  let headers : any = {
    'Content-Type': 'application/json',
    'Accept': 'application/json',
  }

  if (Auth.isUserAuthenticated()) {
    headers['X-Authorization'] = `Bearer ${Auth.getAccessToken()}`
  }

  let params : any = {
    method: method,
    headers: headers
  }

  if (body) {
    params['body'] = JSON.stringify(body);
  }

  // TODO fix a bit
  fetch(url, params).then((r) => {
    r.json().then((data) => {
      if (r.ok) {
        if (success) { success(data); }
      } else {
        if (fail) { fail(data); }
      }
    }).catch((e : any) => {
      console.log(e);
      if (r.ok) {
        if (success) { success(); }
      } else {
        if (fail) { fail(); }
      }
    });
  });
}

export function GET(url : string, success? : callback, fail? : callback, query : any = {} ) {
  const querystring = Object.keys(query).map((k) => {
    return `${k}=${query[k]}`
  }).join('&');

  request(`${url}?${querystring}`, 'GET', undefined, success, fail);
}

export function POST(url : string, body : any, success? : callback, fail? : callback) {
  console.log('POST');
  request(url, 'POST', body, success, fail);
}

export function PUT(url : string, body : any, success? : callback, fail? : callback) {
  request(url, 'PUT', body, success, fail);
}

export function DELETE(url : string, success? : callback, fail? : callback) {
  request(url, 'DELETE', undefined, success, fail);
}
