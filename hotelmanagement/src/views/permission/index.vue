<template>
  <div v-if="checkPermission('PERM_PERMISSION_READ')" class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.name"
        :placeholder="$t('permission.name')"
        style="width: 200px;"
        @keyup.enter.native="handleFilter"
        class="filter-item"
      />
      <el-button
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
      >{{ $t("common.btnSearch") }}</el-button>
    </div>
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column :label="$t('permission.key')" min-width="200" align="left">
        <template slot-scope="scope">{{ scope.row.permissionKey }}</template>
      </el-table-column>
      <el-table-column :label="$t('permission.name')" min-width="300" align="left">
        <template slot-scope="scope">{{ scope.row.permissionName }}</template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.size"
      @pagination="fetchData"
    />
  </div>
</template>

<script>
import { list } from "@/api/permission";
import store from "@/store";
import Pagination from "@/components/Pagination";
import checkPermission from "@/utils/permission";

export default {
  name: "room",
  components: { Pagination },
  data() {
    return {
      total: 0,
      list: null,
      listLoading: true,
      listQuery: {
        page: 0,
        size: 20,
        name: "",
      }
    };
  },
  created() {
    if (this.checkPermission("PERM_PERMISSION_READ")) {
      this.fetchData();
    }
  },
  methods: {
    checkPermission,
    fetchData() {
      this.listLoading = true;
      list(this.listQuery)
        .then(response => {
          this.total = response.data.countRow;
          this.list = response.data.object;
        })
        .finally(() => {
          this.listLoading = false;
        });
    },
    handleFilter() {
      this.fetchData();
    },
    
  }
};
</script>
