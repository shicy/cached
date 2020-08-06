import Vue from "vue";

import iView from "view-design";
import "view-design/dist/styles/iview.css";

import App from "./App.vue";
import "./css/base.css";
import "./css/iview.scss";

Vue.config.productionTip = false;

Vue.use(iView);

new Vue({
  render: h => h(App)
}).$mount("#app");
