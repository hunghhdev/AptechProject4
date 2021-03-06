<template>
  <div v-if="checkPermission('PERM_SUPPLIES_READ')" class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.name"
        :placeholder="$t('supplies.search.inputName')"
        style="width: 200px; text-align: center !important;"
        @keyup.enter.native="handleFilter"
        class="filter-item"
      />
      <el-date-picker
        style="width: 400px;"
        v-model="dateSearchPicker"
        type="daterange"
        :start-placeholder="$t('common.fromDate')"
        :end-placeholder="$t('common.toDate')"
        class="filter-item"
      ></el-date-picker>
      <el-button
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
      >{{ $t("common.btnSearch") }}</el-button>
      <el-button
        v-if="checkPermission('PERM_SUPPLIES_CREATE')"
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
      <el-table-column :label="$t('supplies.table.name')" min-width="220" align="center">
        <template slot-scope="scope">{{ scope.row.name }}</template>
      </el-table-column>
      <el-table-column :label="$t('supplies.table.quantity')" min-width="220" align="center">
        <template slot-scope="scope">{{ scope.row.quantity }}</template>
      </el-table-column>
      <el-table-column :label="$t('supplies.table.used')" min-width="220" align="center">
        <template slot-scope="scope">{{ scope.row.used }}</template>
      </el-table-column>
      <el-table-column :label="$t('supplies.table.note')" min-width="220" align="center">
        <template slot-scope="scope">{{ scope.row.note }}</template>
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
      <el-table-column fixed="right" :label="$t('common.action')" align="center" min-width="200">
        <template slot-scope="{row}">
          <el-button
            v-if="checkPermission('PERM_SUPPLIES_UPDATE')"
            icon="el-icon-edit"
            type="primary"
            size="mini"
            @click="handleUpdate(row)"
          >{{ $t("common.btnEdit") }}</el-button>
          <el-button
            v-if="checkPermission('PERM_SUPPLIES_DELETE')"
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
        <el-form-item :label="$t('supplies.form.labelName')" prop="name">
          <el-input
            v-model="tempData.name"
            :placeholder="$t('supplies.form.labelName')"
            class="filter-item"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('supplies.form.labelQuantity')" prop="quantity">
          <el-input-number v-model="tempData.quantity" :min="1" :max="100"></el-input-number>
        </el-form-item>
        <el-form-item :label="$t('supplies.form.labelNote')" prop="note">
          <el-input
            v-model="tempData.note"
            :placeholder="$t('supplies.form.labelNote')"
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
    <el-dialog :title="$t('supplies.delete.title')" :visible.sync="dialogDeleteVisible">
      <p>{{ $t('supplies.delete.msg', { name: tempData.name }) }}</p>
      <div slot="footer">
        <el-button @click="dialogDeleteVisible = false">{{ $t("common.btnCancel") }}</el-button>
        <el-button type="danger" @click="deleteData">{{ $t("common.btnConfirm") }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { fetchList, create, update, remove } from "@/api/supplies";
import { formatDate } from "@/utils/";
import store from "@/store";
import Pagination from "@/components/Pagination";
import checkPermission from "@/utils/permission";

export default {
  name: "Supplies",
  components: { Pagination },
  data() {
    const validateQuantityRq = (rule, value, callback) => {
      if (value < this.tempData.used) {
        callback(new Error(this.$t("supplies.validate.quantityRq")));
      } else {
        callback();
      }
    };
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
      dateSearchPicker: "",
      textMap: {
        update: this.$t("supplies.form.titleEdit"),
        create: this.$t("supplies.form.titleCreate")
      },
      dialogStatus: "",
      dialogFormVisible: false,
      dialogDeleteVisible: false,
      buttonConfirmLoading: false,
      tempData: {
        id: "",
        name: "",
        quantity: "",
        note: "",
        used: 0
      },
      rules: {
        name: [
          {
            trigger: "blur",
            required: true,
            message: this.$t("supplies.validate.nameRq")
          }
        ],
        quantity: [
          {
            trigger: "blur",
            required: true,
            validator: validateQuantityRq
          }
        ]
      }
    };
  },
  created() {
    if (this.checkPermission("PERM_SUPPLIES_READ")) {
      this.fetchData();
    }
  },
  methods: {
    checkPermission,
    formatDate,
    fetchData() {
      this.listLoading = true;
      if (this.dateSearchPicker) {
        this.listQuery.fromDate =
          typeof this.dateSearchPicker[0] == "number"
            ? this.dateSearchPicker[0]
            : this.dateSearchPicker[0].getTime();
        this.listQuery.toDate = this.dateSearchPicker[1].getTime();
      } else {
        this.listQuery.fromDate = 0;
        this.listQuery.toDate = 0;
      }
      fetchList(this.listQuery).then(response => {
        this.total = response.data.countRow;
        this.list = response.data.object;
        this.listLoading = false;
      });
    },
    handleDelete(row) {
      if (row.used > 0) {
        this.$notify({
          title: "Warning",
          message: this.$t("supplies.msg.deletedWarning"),
          type: "warning",
          duration: 5000
        });
      } else {
        this.tempData = row;
        this.dialogDeleteVisible = true;
      }
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
                message: this.$t("supplies.msg.createSuccess"),
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
    },
    updateData() {
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
                message: this.$t("supplies.msg.updateSuccess"),
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
            message: this.$t("supplies.msg.deletedSuccess"),
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
      this.buttonConfirmLoading = false;
      this.dialogFormVisible = false;
    },
    resetTemp() {
      this.tempData = {
        id: "",
        name: "",
        quantity: "",
        note: "",
        used: 0
      };
    }
  }
};
</script>
