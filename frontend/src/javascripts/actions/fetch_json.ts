export type callback = (value : any) => any;

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

  if (body) {
    fetch(url, 
      {
        method: method,
        headers, 
        body: JSON.stringify(body)
      }
       ).then((r) => {
    r.json().then((data) => {
      if (r.ok) {
        if (success) { success(data); }
      } else {
        if (fail) { fail(data); }
      }
    });
  });
  }
  else{
    fetch(url, 
      {
        method: method,
        headers  
      }
        ) .then((r) => {
      r.json().then((data) => {
        if (r.ok) {
          if (success) { success(data); }
        } else {
          if (fail) { fail(data); }
        }
      });
    });
  }
}

export function GET(url : string, success? : callback, fail? : callback) {
  request(url, 'GET', undefined, success, fail);
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
