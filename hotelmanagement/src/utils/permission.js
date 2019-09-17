import store from "@/store";

/**
 * @param {Array} value permission
 * @returns {Boolean}
 */
export default function checkPermission(value) {
    return store.getters.permissions.indexOf(value) !== -1;
}
