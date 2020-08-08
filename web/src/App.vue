<template>
  <div id="main-body">
    <div v-if="!beInit" class="loading">
      <Spin></Spin>
    </div>
    <Index v-else-if="isLogin" :token="accessToken"></Index>
    <Login v-else @login="onLoginHandler"></Login>
  </div>
</template>

<script>
import { isDev } from "@/framework/Context";
import Index from "./views/index/Index.vue";
import Login from "./views/Login.vue";

export default {
  name: "App",
  components: { Index, Login },

  data() {
    return {
      beInit: false,
      loadingFlag: false,
      accessToken: ""
    };
  },

  mounted() {
    this.beInit = true;
    this.accessToken = isDev() ? "112233445566" : "";
  },

  computed: {
    isLogin() {
      return !!this.accessToken;
    }
  },

  methods: {
    onLoginHandler(token) {
      this.accessToken = token;
    }
  }
};
</script>

<style lang="scss">
#main-body {
  height: 100%;
}
</style>
