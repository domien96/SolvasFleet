class Auth {
  /**
   * Authenticate a user. Save a token and refreshToken string in Local Storage
   */
  static authenticateUser(refreshToken : string, accessToken : string) {
    localStorage.setItem('refreshToken', refreshToken);
    localStorage.setItem('accessToken', accessToken);
  }

  /**
   * Check if a user is authenticated
   */
  static isUserAuthenticated() {
    return localStorage.getItem('accessToken') !== null;
  }

  /**
   * Deauthenticate a user. Remove a token from Local Storage.
   *
   */
  static deauthenticateUser() {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
  }

  /**
   * Get a token value.
   */

  static getAccessToken() {
    return localStorage.getItem('accessToken');
  }

  /**
   *  Get a refreshToken value
   */
  static getRefreshToken() {
    return localStorage.getItem('refreshToken');
  }
}

export default Auth;
