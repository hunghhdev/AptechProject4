import Vue from "vue";
import App from "./App";
import router from "./router";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
import locale from "element-ui/lib/locale/lang/vi";
import "@/styles/index.scss";
import store from "./store";
import "normalize.css/normalize.css";
import "@/permission";
import VueI18n from "vue-i18n";
import vi_vn from "./lang/vi_vn.json";
import en_us from "./lang/en_us.json";
import Router from 'vue-router';
import {VueMaskDirective} from 'v-mask';

Vue.config.productionTip = false;
Vue.use(ElementUI, {locale});
Vue.use(VueI18n);
Vue.directive('mask', VueMaskDirective);

const messages = {
  vi: vi_vn,
  en: en_us
};
const i18n = new VueI18n({
  locale: "vi",
  messages,
  fallbackLocale: "vi"
});

const routerPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return routerPush.call(this, location).catch(error => error)
}

new Vue({
  el: "#app",
  router,
  store,
  i18n,
  components: {App},
  template: "<App/>"
});
