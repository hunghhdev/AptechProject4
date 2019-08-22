/**
 *
 * @param {Date} date
 * @returns {string} date format yyyy-MM-dd
 */
export function formatDatePicker(date) {
  if (date) {
    let month = date.getMonth() + 1;
    return date.getFullYear() + "-" + month + "-" + date.getDate();
  }
}
/**
 *
 * @param {Date} date
 * @returns {string} date format yyyy-MM-dd HH:mm:ss
 */
export function formatDateStation(dateInput) {
  if (dateInput) {
    let date = new Date(dateInput);
    let month = date.getMonth() + 1;
    return (
      date.getFullYear() +
      "-" +
      month +
      "-" +
      date.getDate() +
      " " +
      date.getHours() +
      ":" +
      date.getMinutes() +
      ":" +
      date.getSeconds()
    );
  }
}
/**
 * @param {string} date
 * @returns {string} date format yyyy.MM.dd
 */
export function formatDate(date) {
  if (date) {
    return date.substr(0, 10).replace(/-/g, ".");
  }
}
