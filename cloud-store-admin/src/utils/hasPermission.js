import store from '../store'

export function hasPermission(permission) {
  let roles = store.getters.role;
  for(let i = 0; i < roles.length; i++) {
    if (roles[i].code === 'admin') {
      return true;
    }
  }
  let myPermissions = store.getters.permissions;
  return myPermissions.indexOf(permission) > -1;
}
