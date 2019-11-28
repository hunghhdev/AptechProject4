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
      <el-table-column prop="identification" :label="$t('customer.table.identification')" align="center" min-width="150"></el-table-column>
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
        <el-form-item :label="$t('customer.form.name')" prop="name">
          <el-input
            v-model="tempData.name"
            :placeholder="$t('customer.form.name')"
            class="filter-item"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('customer.form.phone')" prop="phone">
          <el-input
            v-model="tempData.phone"
            :placeholder="$t('customer.form.phone')"
            class="filter-item"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('customer.form.identification')" prop="identification">
          <el-input
            :disabled="dialogStatus==='create'?false:true"
            v-model="tempData.identification"
            :placeholder="$t('customer.form.identification')"
            class="filter-item"
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">{{ $t('common.btnCancel') }}</el-button>
        <el-button
          type="primary"
          :loading="buttonConfirmLoading"
          @click="dialogStatus==='create'?createData():updateData()"
        >{{ $t('common.btnConfirm') }}</el-button>
      </span>
    </el-dialog>
    <el-dialog :title="$t('customer.delete.title')" :visible.sync="dialogDeleteVisible">
      <p>{{ $t('customer.delete.msg', { name: tempData.name }) }}</p>
      <div slot="footer">
        <el-button @click="dialogDeleteVisible = false">{{ $t("common.btnCancel") }}</el-button>
        <el-button type="danger" @click="deleteData">{{ $t("common.btnConfirm") }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { list, create, update, remove } from "@/api/customer";
  import Pagination from "@/components/Pagination";
  import store from "@/store";
  import checkPermission from "@/utils/permission";
  export default {
    name: "Customer",
    components: { Pagination },
    data() {
      return {
        total: 0,
        dialogStatus: "",
        dialogDeleteVisible: false,
        buttonConfirmLoading: false,
        listLoading: true,
        listQuery: {
          page: 0,
          size: 10,
          fromDate: "",
          toDate: ""
        },
        tempData: {
          id: "",
          name: "",
          phone: "",
          identification: ""
        },
        dateSearchPicker: [new Date() - 2592000000, new Date()],
        list: [],
        textMap: {
          create: this.$t("customer.form.titleCreate"),
          update: this.$t("customer.form.titleEdit")
        },
        dialogFormVisible: false,
        rules: {
          name: [
            {
              trigger: "blur",
              required: true,
              message: this.$t("customer.validate.nameRq")
            }
          ],
          phone: [
            {
              trigger: "blur",
              required: true,
              message: this.$t("customer.validate.phoneRq")
            }
          ],
          identification: [
            {
              trigger: "blur",
              required: true,
              message: this.$t("customer.validate.identificationRq")
            }
          ]
        }
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
      handleCreate() {
        this.resetTemp();
        this.dialogFormVisible = true;
        this.dialogStatus = "create";
        this.$nextTick(() => {
          this.$refs["dataForm"].clearValidate();
        });
        this.buttonConfirmLoading = false;
      },
      createData(event) {
        this.$refs["dataForm"].validate(valid => {
          if (valid) {
            this.buttonConfirmLoading = true;
            create(this.tempData)
              .then(response => {
                this.list.unshift(response.data);
                this.$notify({
                  title: "Success",
                  message: this.$t("customer.msg.createSuccess"),
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
        this.$nextTick(() => {
          this.$refs["dataForm"].clearValidate();
        });
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
                    this.list.splice(index, 1, response.data);
                    break;
                  }
                }
                this.$notify({
                  title: "Success",
                  message: this.$t("customer.msg.updateSuccess"),
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
      handleDelete(row) {
        if (store.getters.id !== row.id) {
          this.dialogDeleteVisible = true;
          this.tempData = row;
        }
      },
      deleteData() {
        this.listLoading = true;
        remove({ id: this.tempData.id })
          .then(response => {
            this.$notify({
              title: "Success",
              message: response.message,
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
      resetTemp() {
        this.tempData = {
          id: "",
          name: "",
          phone: "",
          identification: ""
        };
      }
    }
  };
</script>
