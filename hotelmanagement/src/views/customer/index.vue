<template>
  <div v-if="checkPermission('PERM_CUSTOMER_READ')" class="app-container">
    <div class="filter-container">
      <el-date-picker
        style="width: 400px;"
        v-model="dateSearchPicker"
        type="daterange"
        range-separator="To"
        start-placeholder="Start date"
        end-placeholder="End date"
        class="filter-item"
      ></el-date-picker>
      <el-button
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
      >{{$t('common.btnSearch')}}</el-button>
      <el-button
        v-if="checkPermission('PERM_CUSTOMER_CREATE')"
        style="margin-left: 10px;"
        type="primary"
        icon="el-icon-plus"
        @click="handleCreate"
        class="filter-item"
      >{{$t('common.btnAdd')}}</el-button>
    </div>
    <el-table v-loading="listLoading" border fit highlight-current-row :data="list">
      <el-table-column
        prop="name"
        :label="$t('customer.table.name')"
        align="center"
        min-width="220"
      ></el-table-column>
      <el-table-column
        prop="phone"
        :label="$t('customer.table.phone')"
        align="center"
        min-width="200"
      ></el-table-column>
      <el-table-column fixed="right" :label="$t('common.action')" min-width="200" align="center">
        <template slot-scope="{row}">
          <el-button
            v-if="checkPermission('PERM_CUSTOMER_UPDATE')"
            type="primary"
            size="mini"
            icon="el-icon-edit"
            @click="handleUpdate(row)"
          >{{$t('common.btnEdit')}}</el-button>
          <el-button
            v-if="checkPermission('PERM_CUSTOMER_DELETE')"
            type="danger"
            size="mini"
            icon="el-icon-delete"
            @click="handleDelete(row)"
          >{{$t('common.btnDelete')}}</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { list } from "@/api/customer";
import Pagination from "@/components/Pagination";
import store from "@/store";
import checkPermission from "@/utils/permission";
export default {
  name: "Customer",
  components: { Pagination },
  data() {
    return {
      total: 0,
      listLoading: true,
      listQuery: {
        page: 0,
        size: 10,
        fromDate: "",
        toDate: ""
      },
      dateSearchPicker: [new Date() - 2592000000, new Date()],
      list: []
    };
  },
  created() {
    if (this.checkPermission("PERM_CUSTOMER_READ")) {
      this.fetchData();
    }
  },
  methods: {
    checkPermission,
    fetchData() {
      this.listLoading = true;
      if (this.dateSearchPicker) {
        this.listQuery.fromDate =
          typeof this.dateSearchPicker[0] == "number"
            ? this.dateSearchPicker[0]
            : this.dateSearchPicker[0].getTime();
        this.listQuery.toDate = this.dateSearchPicker[1].getTime();
      }
      list(this.listQuery).then(response => {
        this.list = response.data.object;
        this.total = response.data.countRow;
        this.listLoading = false;
      });
    },
    handleFilter() {
      this.fetchData();
    },
  }
};
</script>
