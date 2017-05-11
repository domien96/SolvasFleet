import Auth from '../modules/Auth.ts';
import { redirect_to } from '../routes/router.tsx';

export type callback = (value?: any) => any;

function request(
  url: string,
  method: string,
  success?: callback,
  fail?: callback,
  promise = Auth.getAccessToken(),
) {
  promise.then((token) => {
    const headers: any = {
      'Accept': 'application/pdf',
      'Content-Type': 'application/pdf',
    };

    if (token !== undefined) {
      headers['X-Authorization'] = `Bearer ${token}`;
    }

    const params: any = { method, headers };

    fetch(url, params).then((r) => {
      if (r.ok) {
        r.blob().then((blob) => {
          if (success) {
            success(blob);
          } else {
            if (fail) { fail(r); }
          }
        });
      }
    });
  }, () => {
    Auth.deauthenticateUser();
    redirect_to('/');
  });
}

export function GETPDF(url: string, success?: callback, fail?: callback, query: any = {} ) {
  const querystring = Object.keys(query).map((k) => {
    return `${k}=${query[k]}`;
  }).join('&');

  request(`${url}?${querystring}`, 'GET', success, fail);
}
