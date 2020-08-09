<!-- 主页面 -->
<!-- by shicy 2020-08-01 -->

<template>
  <div class="v-index">
    <div class="main-header">
      <PageHeader />
    </div>
    <div class="main-container">
      <div class="searchbar">
        <SearchForm :datas="searchItems" @search="onSearchHandler" />
      </div>
      <MyTable
        :action="action"
        :params="searchParams"
        :columns="columns"
        :head-height="32"
        :row-height="40"
        size="auto"
        @load-before="onLoadBeforeHandler"
        @loaded="onLoadResultHandler"
      />
    </div>
  </div>
</template>

<script>
import { toDateString } from "@scyui/vue-base";
import { SearchForm, MyTable } from "@scyui/vue-base";
import { api } from "@/framework/Context";
import PageHeader from "./PageHeader.vue";

const searchItems = [
  { name: "keyLike", placeholder: "输入键名模糊查询", width: 0 }
];

const tableColumns = [
  { key: "key", title: "键名", width: 200 },
  { key: "value", title: "值", width: 220 },
  { key: "flags", title: "标志", width: 100 },
  { key: "expires", title: "有效期（秒）", width: 150 },
  { key: "deadline", title: "过期时间", width: 180 },
  { key: "createTime", title: "创建时间", width: 180 },
  { key: "updateTime", title: "更新时间" }
];

export default {
  components: { SearchForm, MyTable, PageHeader },

  props: ["token"],

  data() {
    return {
      action: api("/find/by/admin"),
      columns: tableColumns,
      searchItems: searchItems,
      searchParams: {}
    };
  },

  methods: {
    onLoadBeforeHandler(params) {
      params.token = this.token;
    },

    onLoadResultHandler(result) {
      if (result.datas) {
        result.datas.forEach(data => {
          data.expires = parseInt(data.expires) || 0;
          if (data.expires) {
            data.deadline = data.expires * 1000;
            data.deadline += data.updateTime || data.createTime;
            data.deadline = toDateString(data.deadline, "yyyy-MM-dd HH:mm:ss");
          } else {
            data.deadline = "∞";
          }
          // .
          data.createTime = toDateString(
            data.createTime,
            "yyyy-MM-dd HH:mm:ss"
          );
          // .
          if (data.updateTime) {
            data.updateTime = toDateString(
              data.updateTime,
              "yyyy-MM-dd HH:mm:dd"
            );
          } else {
            data.updateTime = "";
          }
        });
      }
    },

    onSearchHandler(params) {
      this.searchParams = params;
    }
  }
};
</script>

<style lang="scss">
.v-index {
  height: 100%;
  padding-top: 50px;
  box-sizing: border-box;

  .main-header {
    position: fixed;
    height: 40px;
    left: 0px;
    right: 0px;
    top: 0px;
    z-index: 100;
    background-color: #fff;
    box-shadow: 0px 2px 10px 0px rgba(0, 0, 0, 0.1);
  }

  .main-container {
    position: relative;
    height: 100%;
    padding-top: 52px;
    box-sizing: border-box;

    .searchbar {
      position: absolute;
      left: 0px;
      right: 0px;
      top: 0px;
      padding: 5px 20px;
    }
  }
}
</style>
