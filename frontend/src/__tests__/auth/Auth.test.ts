import { isExpired, parseClaims} from '../../javascripts/modules/Auth.ts';
import { auth_login, fetchRole } from '../../javascripts/actions/auth_actions.ts';
import Auth from '../../javascripts/modules/Auth.ts';

global.fetch = require('jest-fetch-mock');

test('isTokenExpired tested with real tokens', () => {
  var expiredToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOiIxMTE0Njk0NTY1In0.M78lggOEMzNTH0Ft5r3ZKlchhmRrev29n4piBj4ZonY";
  //Will expire in the year 2038
  var futureToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOiIyMTU2MDczNzY1In0.cJmoEQKYw4r2MBU1s6NAhS9QtGN7owDsGl8PseXODJ0";

  expect(Auth.isTokenExpired(expiredToken)).toBeTruthy();
  expect(Auth.isTokenExpired(futureToken)).toBeFalsy();
});


test('puttokensafterfetch', () => {
  var init = { status: 200, statusText: "ok!" };
  fetch.mockResponseOnce(JSON.stringify({ refreshToken: { token: "123" }, accessToken: { token: "456" } }),init);
  const s = (data : any) => {
    Auth.authenticateUser(data.refreshToken.token, data.accessToken.token);
    expect(localStorage.getItem('refreshToken')).toBe("123");
    expect(localStorage.getItem('accessToken')).toBe("456");
  };
  auth_login("email", "pass", s);
});
