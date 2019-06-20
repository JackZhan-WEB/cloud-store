import store from '../store'

export function hasPermission(permission) {
  let roles = store.getters.role;
  console.log(roles,'roles');
  for(let i = 0; i < roles.length; i++) {
    if (roles[i].name === 'admin') {
      return true;
    }
  }
  let myPermissions = store.getters.permissions;
  return myPermissions.indexOf(permission) > -1;
}
