import router from "./router";
import store from "./store";
import { Message } from "element-ui";
import NProgress from "nprogress";
import "nprogress/nprogress.css";
import { getToken } from "@/utils/auth";
import getPageTitle from "@/utils/get-page-title";

NProgress.configure({ showSpinner: false });

const whiteList = ["/login", "/reset-password"];
router.beforeEach(async (to, from, next) => {
  NProgress.start();

  document.title = getPageTitle(to.name);

  const hasToken = getToken();

  if (hasToken) {
    if (to.path === "/login") {

      next({ path: "/" });

      NProgress.done();
    } else {
      const hasGetUserInfo = store.getters.username;
      if (hasGetUserInfo) {
        next();
      } else {
        try {
          await store.dispatch("user/getInfo");

          next();
        } catch (error) {
          await store.dispatch("user/resetToken");
          Message.error(error || "Has Error");
          next(`/login?redirect=${to.path}`);
          NProgress.done();
        }
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next();
    } else {
      next(`/login?redirect=${to.path}`);
      NProgress.done();
    }
  }
});

router.afterEach(() => {
  NProgress.done();
});
