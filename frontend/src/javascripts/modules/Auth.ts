import { auth_token } from '../actions/auth_actions.ts';
import _ from 'lodash';

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
        resolve(Auth.getLocalAccessToken());
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

  static isAuthorizedGlobally(scope: string) {
    const parsed = parseClaims(Auth.getLocalAccessToken());
    const scopes = (_.flatten((parsed.scopes).map((u: any) => u.scopes)));
    return _.intersection([scope], scopes).length > 0;
  }

  static isAuthorizedForCompany(scope: string, company: number) {
    const parsed = parseClaims(Auth.getLocalAccessToken());
    const filtered = parsed.scopes.filter((s: any) => company == null || s.companyId === company); //company==null is wildcard
    const scopes = (_.flatten(filtered.scopes)).map((u: any) => u.scopes);
    return _.intersection([scope], scopes).length > 0;
  }

  static canReadCompany(company: number) {
    return Auth.isAuthorizedGlobally('read:companies') || Auth.isAuthorizedForCompany('read:company', company);
  }

  static canWriteCompany(company: number) {
    return Auth.isAuthorizedGlobally('write:companies') || Auth.isAuthorizedForCompany('write:company', company);
  }

  static canReadFleetsOfCompany(company: number) {
    return Auth.isAuthorizedGlobally('read:companies:fleets') || Auth.isAuthorizedForCompany('read:company:fleets', company);
  }

  static canWriteFleetsOfCompany(company: number) {
    return Auth.isAuthorizedGlobally('write:companies:fleets') || Auth.isAuthorizedForCompany('write:company:fleets', company);
  }

  static canReadContractsOfCompany(company: number) {
    return Auth.isAuthorizedGlobally('read:companies:contracts') || Auth.isAuthorizedForCompany('read:company:contracts', company);
  }

  static canWriteContractsOfCompany(company: number) {
    return Auth.isAuthorizedGlobally('write:companies:contracts') || Auth.isAuthorizedForCompany('write:company:contracts', company);
  }

  static canReadRevisions() {
    return Auth.isAuthorizedGlobally('read:revisions');
  }

  static canCreateVehicle() {
    return Auth.isAuthorizedGlobally('write:companies:fleets') || Auth.isAuthorizedGlobally('write:company:fleets');
  }

  static canCreateUser() {
    return Auth.isAuthorizedGlobally('create:users');
  }

  static canCreateClient() {
    return Auth.isAuthorizedGlobally('create:companies');
  }

  static canReadFunctions() {
    return Auth.isAuthorizedGlobally('read:users');
  }

  static canWriteFunctions() {
    return Auth.isAuthorizedGlobally('write:users:roles');
  }

  static canReadUsers() {
    return Auth.isAuthorizedGlobally('read:users');
  }

  static canWriteUsers() {
    return Auth.isAuthorizedGlobally('write:users');
  }

  static canReadRoles() {
    return Auth.isAuthorizedGlobally('read:auth:roles');
  }
  static canWriteRoles() {
    return Auth.isAuthorizedGlobally('write:auth:roles');
  }

  static canClickUsersLink() {
    return Auth.isAuthorizedGlobally('read:users') || Auth.isAuthorizedGlobally('write:users');
  }

  static canClickCompaniesLink() {
    return Auth.isAuthorizedGlobally('read:companies') || Auth.isAuthorizedGlobally('write:companies')
      || Auth.isAuthorizedGlobally('read:company') || Auth.isAuthorizedGlobally('write:company');
  }

  static canClickVehiclesLink() {
    return Auth.isAuthorizedGlobally('read:companies:fleets') || Auth.isAuthorizedGlobally('write:companies:fleets')
      || Auth.isAuthorizedGlobally('read:company:fleets') || Auth.isAuthorizedGlobally('write:company:fleets');
  }
}

export default Auth;
