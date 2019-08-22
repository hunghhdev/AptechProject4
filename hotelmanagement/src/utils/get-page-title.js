import defaultSettings from "@/settings";

const title = defaultSettings.title || "Hotel Management";

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`;
  }
  return `${title}`;
}
