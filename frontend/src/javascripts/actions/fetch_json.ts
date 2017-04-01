import Auth from '../modules/Auth.ts';

export type callback = (value? : any) => any;

function request (
  url : string,
  method : string,
  body? : any,
  success? : callback,
  fail? : callback,
  promise = Auth.getAccessToken()
) {
  promise.then((token) => {
    let headers : any = {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
    }

    if (token !== undefined) {
      headers['X-Authorization'] = `Bearer ${token}`
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
  }, () => {
    console.log('sign out');
  });
}

export function GET(url : string, success? : callback, fail? : callback, query : any = {} ) {
  const querystring = Object.keys(query).map((k) => {
    return `${k}=${query[k]}`
  }).join('&');

  request(`${url}?${querystring}`, 'GET', undefined, success, fail);
}

export function POST(url : string, body : any, success? : callback, fail? : callback) {
  request(url, 'POST', body, success, fail);
}

export function PUT(url : string, body : any, success? : callback, fail? : callback) {
  request(url, 'PUT', body, success, fail);
}

export function DELETE(url : string, success? : callback, fail? : callback) {
  request(url, 'DELETE', undefined, success, fail);
}

export function AUTH(url : string, body: any, promise : any, success? : callback, fail? : callback) {
  request(url, 'POST', body, success, fail, promise);
}
