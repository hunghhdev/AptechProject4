<template>
  <div v-if="checkPermission(['PERM_ROOM_READ'])" class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.name"
        :placeholder="$t('room.search.inputName')"
        style="width: 200px; text-align: center !important;"
        @keyup.enter.native="handleFilter"
        class="filter-item"
      />
      <el-select v-model="listQuery.branch" :placeholder="$t('room.search.branch')" class="filter-item">
        <el-option
          v-for="item in listBranchOptions"
          :key="item.value"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>
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
      >{{ $t("common.btnSearch") }}</el-button>
      <el-button
        v-if="checkPermission(['PERM_ROOM_CREATE'])"
        style="margin-left: 10px;"
        type="primary"
        icon="el-icon-document-add"
        @click="handleCreate"
        class="filter-item"
      >{{ $t("common.btnAdd") }}</el-button>
    </div>
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column :label="$t('room.table.branchName')" min-width="300" align="center">
        <template slot-scope="scope">{{ scope.row.branchName }}</template>
      </el-table-column>
      <el-table-column :label="$t('room.table.codeRoom')" min-width="220" align="center">
        <template slot-scope="scope">{{ scope.row.codeRoom }}</template>
      </el-table-column>
      <el-table-column :label="$t('room.table.name')" min-width="300" align="center">
        <template slot-scope="scope">{{ scope.row.name }}</template>
      </el-table-column>
      <el-table-column :label="$t('room.table.price')" min-width="300" align="center">
        <template slot-scope="scope">{{ scope.row.price }}</template>
      </el-table-column>
      <el-table-column :label="$t('room.table.supplies')" min-width="300" align="center">
        <template slot-scope="scope">{{ scope.row.supplies }}</template>
      </el-table-column>
      <el-table-column :label="$t('room.table.desc')" min-width="300" align="center">
        <template slot-scope="scope">{{ scope.row.desc }}</template>
      </el-table-column>
      <el-table-column :label="$t('common.createdBy')" min-width="220" align="center">
        <template slot-scope="scope">{{ scope.row.createdBy }}</template>
      </el-table-column>
      <el-table-column
        :label="$t('common.createdDate')"
        align="center"
        sortable
        prop="createdDate"
        min-width="220"
      >
        <template slot-scope="scope">
          <span>{{ formatDate(scope.row.createdDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column fixed="right" :label="$t('common.action')" align="center" min-width="250">
        <template slot-scope="{row}">
          <el-button
            v-if="checkPermission(['PERM_ROOM_UPDATE'])"
            icon="el-icon-edit"
            type="primary"
            size="mini"
            @click="handleUpdate(row)"
          >{{ $t("common.btnEdit") }}</el-button>
          <el-button
            v-if="checkPermission(['PERM_ROOM_DELETE'])"
            icon="el-icon-delete"
            type="danger"
            size="mini"
            @click="handleDelete(row)"
          >{{ $t("common.btnDelete") }}</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.size"
      @pagination="fetchData"
    />
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        ref="dataForm"
        :model="tempData"
        label-position="left"
        label-width="30%"
        style="width: 90%; margin-left:30px;"
        :rules="rules"
      >
        <el-form-item :label="$t('room.form.labelName')" prop="departmentName">
          <el-input
            v-model="tempData.departmentName"
            :placeholder="$t('room.form.labelName')"
            class="filter-item"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelHandle()">{{ $t("common.btnCancel") }}</el-button>
        <el-button
          type="primary"
          :loading="buttonConfirmLoading"
          @click="dialogStatus==='create'?createData():updateData()"
        >{{ $t("common.btnConfirm") }}</el-button>
      </span>
    </el-dialog>
    <el-dialog :title="$t('role.delete.title')" :visible.sync="dialogDeleteVisible">
      <p>{{ $t('role.delete.msg', { name: tempData.roleName }) }}</p>
      <div slot="footer">
        <el-button @click="dialogDeleteVisible = false">{{ $t("common.btnCancel") }}</el-button>
        <el-button type="danger" @click="deleteData">{{ $t("common.btnConfirm") }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
// import { fetchList, create } from "@/api/room";
import { formatDate } from "@/utils/";
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
      listBranchOptions: null,
      listQuery: {
        page: 0,
        size: 10,
        name: "",
        branch: "",
        fromDate: "",
        toDate: ""
      },
      dateSearchPicker: [new Date() - 2592000000, new Date()],
      textMap: {
        update: this.$t("room.form.titleEdit"),
        create: this.$t("room.form.titleCreate")
      },
      dialogStatus: "",
      dialogFormVisible: false,
      dialogDeleteVisible: false,
      buttonConfirmLoading: false,
      tempData: {
        id: "",
        departmentName: ""
      },
      rules: {
        departmentName: [
          {
            trigger: "blur",
            required: true,
            message: this.$t("room.validate.nameRq")
          }
        ]
      }
    };
  },
  created() {
    if (this.checkPermission(["PERM_ROOM_READ"])) {
      this.fetchData();
    }
  },
  methods: {
    checkPermission,
    formatDate,
    fetchData() {
      this.listLoading = true;
      if (this.dateSearchPicker) {
        this.listQuery.fromDate = this.dateSearchPicker[0];
        this.listQuery.toDate = this.dateSearchPicker[1].getTime();
      }
      // fetchList(this.listQuery).then(response => {
      //   this.total = response.data.countRow;
      //   this.list = response.data.object;
      //   this.listLoading = false;
      // });
      this.listLoading = false;
    },
    handleDelete(row) {
      this.dialogDeleteVisible = true;
      this.tempData = row;
    },
    handleFilter() {
      this.fetchData();
    },
    handleCreate() {
      this.dialogFormVisible = true;
      this.dialogStatus = "create";
      this.resetTemp();
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
    },
    createData() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          this.buttonConfirmLoading = true;
          create(this.tempData)
            .then(response => {
              this.list.unshift(response.data);
              this.$notify({
                title: "Success",
                message: this.$t("room.msg.createSuccess"),
                type: "success",
                duration: 2000
              });
              this.dialogFormVisible = false;
              this.total += 1;
            })
            .finally(() => {
              this.buttonConfirmLoading = false;
            });
        }
      });
    },
    handleUpdate(row) {
      this.dialogFormVisible = true;
      this.dialogStatus = "update";
      this.tempData = Object.assign({}, row);
      this.$nextTick(() => {
        this.$refs.tree.setCheckedNodes(this.tempData.permissions);
      });
    },
    updateData() {
      this.tempData.permissions = [];
      for (const val of this.$refs.tree.getCheckedNodes()) {
        if (val.id % 1000 !== 0) this.tempData.permissions.push(val);
      }
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          this.buttonConfirmLoading = true;
          update(this.tempData)
            .then(response => {
              for (const v of this.list) {
                if (v.id === this.tempData.id) {
                  const index = this.list.indexOf(v);
                  this.list.splice(index, 1, this.tempData);
                  break;
                }
              }
              this.$notify({
                title: "Success",
                message: this.$t("role.msg.updateSuccess"),
                type: "success",
                duration: 2000
              });
              this.dialogFormVisible = false;
            })
            .finally(() => {
              this.buttonConfirmLoading = false;
            });
        }
      });
    },
    deleteData() {
      this.listLoading = true;
      remove({ id: this.tempData.id })
        .then(response => {
          this.$notify({
            title: "Success",
            message: this.$t("role.msg.deletedSuccess"),
            type: "success",
            duration: 2000
          });
          const index = this.list.indexOf(this.tempData);
          this.list.splice(index, 1);
          this.total -= 1;
        })
        .finally(() => {
          this.listLoading = false;
        });
      this.dialogDeleteVisible = false;
    },
    cancelHandle() {
      this.resetTemp;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
      this.$refs.tree.setCheckedKeys([]);
      this.$refs.tree.setCheckedNodes([]);
      this.buttonConfirmLoading = false;
      this.dialogFormVisible = false;
    },
    resetTemp() {
      this.tempData = {
        id: "",
        departmentName: ""
      };
    }
  }
};
</script>
