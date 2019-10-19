const getters = {
  sidebar: state => state.app.sidebar,
  device: state => state.app.device,
  token: state => state.user.token,
  avatar: state => state.user.avatar,
  username: state => state.user.name,
  id: state => state.user.id,
  permissions: state => state.user.permissions,
  service: state => state.user.service,
  permission_routes: state => state.permission.routes,
};
export default getters;
