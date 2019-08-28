import store from "@/store";

/**
 * @param {Array} value permission
 * @returns {Boolean}
 */
export default function checkPermission(value) {
  if (value && value instanceof Array && value.length > 0) {
    const permissions = store.getters && store.getters.permissions;
    const permissionRoles = value;
    const hasPermission = permissions.some(permission => {
      return permissionRoles.includes(permission.permissionKey);
    });

    if (!hasPermission) {
      return false;
    }
    return true;
  } else {
    return false;
  }
}
