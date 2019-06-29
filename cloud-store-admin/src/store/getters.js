const getters = {
  sidebar: state => state.app.sidebar,
  visitedViews: state => state.app.visitedViews,

  token: state => state.member.token,
  nickname: state => state.member.name,
  memberId: state => state.member.memberId,
  avatar: state => state.member.avatar,
  role: state => state.member.role,
  menus: state => state.member.menus,
  permissions: state => state.member.permissions,

  permission_routers: state => state.permission.routers,
  addRouters: state => state.permission.addRouters
}
export default getters
