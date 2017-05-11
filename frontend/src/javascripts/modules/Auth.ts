import { auth_token } from '../actions/auth_actions.ts';

export function parseClaims(token: string) {
  return (
    JSON.parse(
      atob(
        token.split('.')[1],
      ),
    )
  );
}

function date_from(timestamp: number) {
  return new Date(timestamp * 1000);
}

function isExpired(date: Date) {
  return date <= new Date();
}

class Auth {
  /**
   * Authenticate a user. Save a accessToken and refreshToken string in Local Storage
   */
  static authenticateUser(refreshToken: string, accessToken: string, sub?: string) {
    localStorage.setItem('refreshToken', refreshToken);
    localStorage.setItem('accessToken', accessToken);

    if (sub) {
      localStorage.setItem('sub', sub);
    }
  }

  /**
   * Check if a user is authenticated
   */
  static isAuthenticated() {
    return Auth.getLocalRefreshToken() !== null && !Auth.isRefreshTokenExpired();
  }

  /**
   * Deauthenticate a user. Remove tokens from Local Storage.
   *
   */
  static deauthenticateUser() {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
    localStorage.removeItem('sub');
  }

  static getLocalAccessToken() {
    return localStorage.getItem('accessToken');
  }

  static getLocalRefreshToken() {
    return localStorage.getItem('refreshToken');
  }

  static getLocalSub() {
    return localStorage.getItem('sub');
  }

  /**
   * Get a accessToken value.
   */
  static getAccessToken() {
    return new Promise((resolve, reject) => {
      if (!Auth.isAuthenticated()) {
        reject();
      } else if (Auth.isAccessTokenExpired()) {
        auth_token((data) => {
          Auth.authenticateUser(
            data.refreshToken.token,
            data.accessToken.token,
            data.accessToken.claims.sub
          );
        });
      } else {
        resolve(Auth.getLocalAccessToken());
      }
    });
  }

  static getRefreshToken() {
    return new Promise((resolve, reject) => {
      if (Auth.isRefreshTokenExpired()) {
        reject();
      } else {
        resolve(Auth.getLocalRefreshToken());
      }
    });
  }

  static isTokenExpired(token: string) {
    return isExpired(date_from(parseClaims(token).exp));
  }

  static isAccessTokenExpired() {
    return Auth.isTokenExpired(Auth.getLocalAccessToken());
  }

  static isRefreshTokenExpired() {
    return Auth.isTokenExpired(Auth.getLocalRefreshToken());
  }
}

export default Auth;
