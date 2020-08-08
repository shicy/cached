<!-- 登录 -->
<!-- by shicy 2020-08-01 -->

<template>
  <div class="v-login">
    <div class="login-box">
      <Form>
        <FormItem>
          <Input
            v-model="username"
            placeholder="账号"
            @on-enter="onLoginBtnHandler"
          />
        </FormItem>
        <FormItem>
          <Input
            v-model="password"
            type="password"
            placeholder="密码"
            @on-enter="onLoginBtnHandler"
          />
        </FormItem>
      </Form>
      <Button
        class="loginbtn"
        type="primary"
        :loading="loadingFlag"
        :disabled="!isLoginEnabled"
        @click="onLoginBtnHandler"
      >
        登录
      </Button>
    </div>
  </div>
</template>

<script>
import QueryString from "querystring";
import { $post } from "@scyui/vue-base";
import { api } from "@/framework/Context";

export default {
  data() {
    return {
      username: "",
      password: "",
      loadingFlag: false
    };
  },

  computed: {
    isLoginEnabled() {
      let name = this.username.trim();
      let pwd = this.password.trim();
      return name.length > 0 && pwd.length > 0;
    }
  },

  methods: {
    onLoginBtnHandler() {
      if (this.loadingFlag) {
        return;
      }

      let params = {};
      params.name = this.username.trim();
      params.password = this.password.trim();
      params = QueryString.stringify(params);

      this.loadingFlag = true;
      $post(api("/login/by/admin"), params)
        .then(({ data: token }) => {
          // console.log("==>", token);
          this.loadingFlag = false;
          this.$emit("login", token);
        })
        .catch(() => {
          this.loadingFlag = false;
        });
    }
  }
};
</script>

<style lang="scss">
.v-login {
  position: relative;
  height: 100%;
  background-color: #05bbee;

  .login-box {
    position: absolute;
    width: 300px;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    padding: 30px;
    border-radius: 5px;
    background-color: #fff;
  }

  .loginbtn {
    width: 100%;
  }
}
</style>
