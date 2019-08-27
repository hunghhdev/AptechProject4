<template>
  <div v-if="checkPermission(['PERM_ROLE_READ'])" class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.name"
        :placeholder="$t('role.search.inputName')"
        style="width: 200px; text-align: center !important;"
        @keyup.enter.native="handleFilter"
        class="filter-item"
      />
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
        v-if="checkPermission(['PERM_ROLE_CREATE'])"
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
      <el-table-column :label="$t('role.table.name')" min-width="220" align="center">
        <template slot-scope="scope">{{ scope.row.roleName }}</template>
      </el-table-column>
      <el-table-column :label="$t('role.table.desc')" min-width="220" align="center">
        <template slot-scope="scope">{{ scope.row.description }}</template>
      </el-table-column>
      <el-table-column :label="$t('role.table.createdBy')" min-width="220" align="center">
        <template slot-scope="scope">{{ scope.row.createdBy }}</template>
      </el-table-column>
      <el-table-column
        :label="$t('role.table.createdDate')"
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
            v-if="checkPermission(['PERM_ROLE_UPDATE'])"
            icon="el-icon-edit"
            type="primary"
            size="mini"
            @click="handleUpdate(row)"
          >{{ $t("common.btnEdit") }}</el-button>
          <el-button
            v-if="checkPermission(['PERM_ROLE_DELETE'])"
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
        <el-form-item :label="$t('role.form.labelName')" prop="roleName">
          <el-input
            v-model="tempData.roleName"
            :placeholder="$t('role.form.labelName')"
            class="filter-item"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('role.form.labelDesc')" prop="description">
          <el-input
            type="textarea"
            v-model="tempData.description"
            :placeholder="$t('role.form.labelDesc')"
            class="filter-item"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('role.form.labelPermission')">
          <el-tree
            :data="listPermissions"
            show-checkbox
            :props="defaultProps"
            node-key="id"
            ref="tree"
            highlight-current
          ></el-tree>
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
import { fetchList, create, update, remove, permissions } from "@/api/role";
import { formatDate } from "@/utils/";
import store from "@/store";
import Pagination from "@/components/Pagination";
import checkPermission from "@/utils/permission";

export default {
  name: "Role",
  components: { Pagination },
  data() {
    return {
      total: 0,
      list: null,
      listLoading: true,
      listQuery: {
        page: 0,
        size: 10,
        name: "",
        fromDate: "",
        toDate: ""
      },
      dateSearchPicker: [new Date() - 2592000000, new Date()],
      textMap: {
        update: this.$t("role.form.titleEdit"),
        create: this.$t("role.form.titleCreate")
      },
      dialogStatus: "",
      dialogFormVisible: false,
      dialogDeleteVisible: false,
      buttonConfirmLoading: false,
      rules: {
        roleName: [
          {
            min: 1,
            max: 20,
            message: "Role name, limited to 20 characters",
            trigger: "blur",
            required: true
          }
        ],
        description: [
          {
            message: "Remarks, limited to 50 words or less",
            trigger: "blur"
          }
        ]
      },
      tempData: {
        id: "",
        roleName: "",
        description: "",
        permissions: []
      },
      tempPermissions: [],
      defaultProps: {
        children: "children",
        label: "permissionName",
        id: "id"
      },
      listPermissions: ""
    };
  },
  created() {
    if (this.checkPermission(["PERM_ROLE_READ"])) {
      this.fetchData();
      this.fetchPermission();
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
      fetchList(this.listQuery).then(response => {
        this.total = response.data.countRow;
        this.list = response.data.object;
        this.listLoading = false;
      });
    },
    fetchPermission() {
      permissions().then(response => {
        this.listPermissions = response.data;
      });
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
        this.$refs.tree.setCheckedKeys([]);
      });
    },
    createData() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          for (const val of this.$refs.tree.getCheckedKeys()) {
            if (val % 1000 !== 0) this.tempData.permissions.push({ id: val });
          }
          create(this.tempData)
            .then(response => {
              this.list.unshift(response.data);
              this.$notify({
                title: "Success",
                message: this.$t("role.msg.createSuccess"),
                type: "success",
                duration: 2000
              });
              this.dialogFormVisible = false;
              this.total += 1;
            })
            .finally(() => {
              this.buttonLoading = false;
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
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          this.buttonConfirmLoading = true;
          this.tempData.permissions = [];
          for (const val of this.$refs.tree.getCheckedNodes()) {
            if (val.id % 1000 !== 0) this.tempData.permissions.push(val);
          }
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
        roleName: "",
        description: "",
        permissions: []
      };
    }
  }
};
</script>
