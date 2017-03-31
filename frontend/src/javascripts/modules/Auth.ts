class Auth {
  /**
   * Authenticate a user. Save a accessToken and refreshToken string in Local Storage
   */
  static authenticateUser(refreshToken : string, accessToken : string) {
    localStorage.setItem('refreshToken', refreshToken);
    localStorage.setItem('accessToken', accessToken);
  }

  /**
   * Check if a user is authenticated
   */
  static isAuthenticated() {
    return localStorage.getItem('accessToken') !== null;
  }

  /**
   * Deauthenticate a user. Remove tokens from Local Storage.
   *
   */
  static deauthenticateUser() {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
  }

  /**
   * Get a accessToken value.
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
