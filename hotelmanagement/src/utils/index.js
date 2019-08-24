/**
 * @param {string} date
 * @returns {string} date format yyyy.MM.dd
 */
export function formatDate(date) {
  if (date) {
    date = new Date(date);
    let month = date.getMonth() + 1;
    return date.getFullYear() + "-" + month + "-" + date.getDate();
  }
}
