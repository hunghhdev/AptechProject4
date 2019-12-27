/**
 * @param {string} date
 * @returns {string} date format yyyy.MM.dd
 */
export function formatDate(date) {
  if (date) {
    date = new Date(date);
    let month = date.getMonth() + 1;
    return date.getDate() + "-" + month + "-" + date.getFullYear();
  }
}

export function formatDateTime(date) {
  if (date) {
    date = new Date(date);
    let month = date.getMonth() + 1;
    return date.getHours() + ":" + date.getMinutes() +  " " + date.getDate() + "-" + month + "-" + date.getFullYear();
  }
}

export function isToday(d1, d2) {
  if (d1&&d2) return d1.getUTCFullYear() == d2.getUTCFullYear() &&
    d1.getUTCMonth() == d2.getUTCMonth() &&
    d1.getUTCDate() == d2.getUTCDate();
}
