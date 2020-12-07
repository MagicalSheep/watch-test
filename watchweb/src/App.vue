<template>
  <el-container>
    <el-header>
      <el-row type="flex" justify="center">
        <el-menu :default-active="activeIndex" mode="horizontal" :router="true">
          <el-menu-item index="/">主页</el-menu-item>
          <el-menu-item index="/about">关于</el-menu-item>
        </el-menu>
      </el-row>
    </el-header>
    <el-main>
      <el-row type="flex" justify="center">
        <router-view></router-view>
      </el-row>
    </el-main>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      activeIndex: "/",
    };
  },
};
</script>