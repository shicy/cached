// 系统环境信息

const isDevelopment = process.env.NODE_ENV === "development";

export function isDev() {
  return isDevelopment;
}

export function api(url) {
  if (!/^http/.test(url)) {
    if (isDevelopment) {
      url = "/api" + url;
    }
  }
  return url;
}
