import Auth from '../modules/Auth.ts';
import { redirect_to } from '../routes/router.tsx';

export type callback = (value?: any) => any;

function request(
  url: string,
  method: string,
  body?: any,
  success?: callback,
  fail?: callback,
  promise = Auth.getAccessToken(),
) {
  promise.then((token) => {
    const headers: any = {};

    if (token !== undefined) {
      headers['X-Authorization'] = `Bearer ${token}`;
    }

    const params: any = { method, headers };

    let fd = new FormData();
    fd.append('file', body);
    params.body = fd;
    
    // TODO fix a bit
    fetch(url, params).then((r) => {
      console.log(r);
      r.json().then((data) => {
        if (r.ok) {
          if (success) { success(data); }
        } else {
          if (fail) { fail(data); }
        }
      }).catch(() => {
        if (r.ok) {
          if (success) { success(); }
        } else {
          if (fail) { fail(); }
        }
      });
    });
  }, () => {
    Auth.deauthenticateUser();
    redirect_to('/');
  });
}

export function POSTFILE(url: string, body: any, success?: callback, fail?: callback) {
  request(url, 'POST', body, success, fail);
}
