<template>
  <el-row type="flex" justify="center">
    <el-table :data="deviceInfo" :border="true" style="width=100%">
      <el-table-column prop="id" label="id" width="150"> </el-table-column>
      <el-table-column prop="xginfo" label="x轴重力加速度" width="120">
      </el-table-column>
      <el-table-column prop="yginfo" label="y轴重力加速度" width="120">
      </el-table-column>
      <el-table-column prop="zginfo" label="z轴重力加速度" width="120">
      </el-table-column>
      <el-table-column prop="xainfo" label="x轴加速度" width="100">
      </el-table-column>
      <el-table-column prop="yainfo" label="y轴加速度" width="100">
      </el-table-column>
      <el-table-column prop="zainfo" label="z轴加速度" width="100">
      </el-table-column>
      <el-table-column prop="xsinfo" label="x轴旋转速率" width="120">
      </el-table-column>
      <el-table-column prop="ysinfo" label="y轴旋转速率" width="120">
      </el-table-column>
      <el-table-column prop="zsinfo" label="z轴旋转速率" width="120">
      </el-table-column>
    </el-table>
  </el-row>
</template>

<script>
export default {
  name: "Home",
  data() {
    return {};
  },
  computed: {
    deviceInfo() {
      return this.$store.state.deviceInfo;
    },
  },
};
</script>
