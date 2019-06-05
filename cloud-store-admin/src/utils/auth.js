import Cookies from 'js-cookie'

const LoginKey = 'hasLogin';

export function getToken() {
  return Cookies.get(LoginKey);
}

export function setToken(token) {
  return Cookies.set(LoginKey, token)
}

export function removeToken() {
  return Cookies.remove(LoginKey)
}
