<template>
  <div class="app-container">
    <el-tabs type="border-card" @tab-click="handleClick">
      <el-tab-pane>
        <span slot="label">
          <i class="el-icon-mouse"></i>
          {{ $t('bookings.tab1.emptyRoom') }}
        </span>
        <el-table
          v-loading="listLoading"
          :data="list"
          element-loading-text="Loading"
          fit
          highlight-current-row
        >
          <el-table-column :label="$t('bookings.branch')" min-width="300" align="center">
            <template slot-scope="scope">
              <span>{{ formatColumnBranch(scope.row.branchId) }}</span>
            </template>
          </el-table-column>
          <el-table-column :label="$t('room.table.codeRoom')" min-width="220" align="center">
            <template slot-scope="scope">{{ scope.row }}</template>
          </el-table-column>
          <el-table-column
            fixed="right"
            :label="$t('common.action')"
            align="center"
            min-width="200"
          >
            <template slot-scope="{row}">
              <el-button
                v-if="checkPermission('PERM_ROOM_UPDATE')"
                icon="el-icon-edit"
                type="primary"
                size="mini"
                @click="handleUpdate(row)"
              >{{ $t("common.btnEdit") }}</el-button>
              <el-button
                v-if="checkPermission('PERM_ROOM_DELETE')"
                icon="el-icon-delete"
                type="danger"
                size="mini"
                @click="handleDelete(row)"
              >{{ $t("common.btnDelete") }}</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane>
        <span slot="label">
          <i class="el-icon-date"></i>
          {{ $t('bookings.tab2.bookedRoom') }}
        </span>
      </el-tab-pane>
      <el-tab-pane>
        <span slot="label">
          <i class="el-icon-loading"></i>
          {{ $t('bookings.tab3.active') }}
        </span>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import Pagination from "@/components/Pagination";
export default {
  name: "bookings",
  components: { Pagination },
  data() {
    return {
      total: 0,
      list: null,
      listLoading: true,
      listQuery: {
        page: 0,
        size: 10,
        code: "",
        branch: "",
        fromDate: "",
        toDate: ""
      }
    };
  },
  created() {},
  methods: {
    handleClick(tab, event) {
      console.log(tab);
    }
  }
};
</script>