<template>
  <div v-if="checkPermission('PERM_ROOM_READ')" class="app-container">
    <div class="filter-container">
      <el-select
        v-model="listQuery.type"
        :placeholder="$t('room.search.inputType')"
        class="filter-item"
        value-key="id"
        clearable
      >
        <el-option v-for="item in typeOptions" :key="item" :label="item" :value="item" />
      </el-select>
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
        v-if="checkPermission('PERM_ROOM_CREATE')"
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
      <el-table-column :label="$t('room.table.code')" min-width="100" align="center">
        <template slot-scope="scope">{{ scope.row.code }}</template>
      </el-table-column>
      <el-table-column :label="$t('room.table.type')" min-width="150" align="center">
        <template slot-scope="scope">{{ scope.row.type }}</template>
      </el-table-column>
      <el-table-column :label="$t('room.table.size')" min-width="120" align="center">
        <template slot-scope="scope">{{ scope.row.size }}</template>
      </el-table-column>
      <el-table-column :label="$t('room.table.price')" min-width="100" align="center">
        <template slot-scope="scope">{{ scope.row.price }}</template>
      </el-table-column>
      <el-table-column :label="$t('room.table.hourlyPrice')" min-width="150" align="center">
        <template slot-scope="scope">{{ scope.row.hourlyPrice }}</template>
      </el-table-column>
      <el-table-column :label="$t('room.table.status')" min-width="200" align="center">
        <template slot-scope="scope">{{ scope.row.status }}</template>
      </el-table-column>
      <el-table-column :label="$t('room.table.supplies')" min-width="400" align="center">
        <template slot-scope="scope">{{ formatColumnSupplies(scope.row.supplies) }}</template>
      </el-table-column>
      <el-table-column :label="$t('room.table.desc')" min-width="300" align="center">
        <template slot-scope="scope">{{ scope.row.description }}</template>
      </el-table-column>
      <el-table-column :label="$t('common.createdBy')" min-width="100" align="center">
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
        <el-form-item :label="$t('room.form.labelCode')" prop="code">
          <el-input
            v-model="tempData.code"
            :placeholder="$t('room.form.labelCode')"
            class="filter-item"
            :disabled="dialogStatus=='update'"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('room.form.labelType')" prop="type">
          <el-select
            v-model="tempData.type"
            :placeholder="$t('common.select')"
            class="filter-item"
            value-key="id"
          >
            <el-option v-for="item in typeOptions" :key="item" :label="item" :value="item" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('room.form.labelStatus')" prop="status">
          <el-select
            v-if="statusOptions"
            v-model="tempData.status"
            class="filter-item"
            :placeholder="$t('common.select')"
            :disabled="dialogStatus=='create'"
          >
            <el-option v-for="item in statusOptions" :key="item" :label="item" :value="item" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('room.form.labelSupplies')" prop="supplies">
          <el-select
            v-if="suppliesOption"
            v-model="tempData.supplies"
            class="filter-item"
            multiple
            filterable
            :placeholder="$t('common.select')"
          >
            <el-option
              v-for="item in suppliesOption"
              :key="item.id"
              :label="item.name"
              :value="item.id"
              :disabled="item.quantity<=item.used"
            />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('room.form.labelDesc')" prop="description">
          <el-input
            type="textarea"
            v-model="tempData.description"
            :placeholder="$t('room.form.labelDesc')"
            class="filter-item"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('room.form.labelSize')" prop="size">
          <el-input-number controls-position="right" v-model="tempData.size" class="filter-item"></el-input-number>
        </el-form-item>
        <el-form-item :label="$t('room.form.labelPrice')" prop="price">
          <el-input-number controls-position="right" v-model="tempData.price" class="filter-item"></el-input-number>
        </el-form-item>
        <el-form-item :label="$t('room.form.labelHourlyPrice')" prop="price">
          <el-input-number controls-position="right" v-model="tempData.hourlyPrice" class="filter-item"></el-input-number>
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
    <el-dialog :title="$t('room.delete.title')" :visible.sync="dialogDeleteVisible">
      <p>{{ $t('room.delete.msg') }}</p>
      <div slot="footer">
        <el-button @click="dialogDeleteVisible = false">{{ $t("common.btnCancel") }}</el-button>
        <el-button type="danger" @click="deleteData">{{ $t("common.btnConfirm") }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { fetchList, create, update, remove } from "@/api/room";
import { listSupplies } from "@/api/supplies";
import { formatDate } from "@/utils/";
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
        size: 10,
        type: "",
        fromDate: "",
        toDate: ""
      },
      dateSearchPicker: "",
      textMap: {
        update: this.$t("room.form.titleEdit"),
        create: this.$t("room.form.titleCreate")
      },
      dialogStatus: "",
      dialogFormVisible: false,
      dialogDeleteVisible: false,
      buttonConfirmLoading: false,
      typeOptions: ["Standard", "Superior ", "Deluxe ", "Suite "],
      statusOptions: ["Active", "Maintenance"],
      suppliesOption: [],
      tempData: {
        id: "",
        code: "",
        type: "",
        status: "Active",
        supplies: "",
        description: "",
        price: "",
        hourlyPrice: "",
        size: ""
      },
      rules: {
        code: [
          {
            trigger: "blur",
            required: true,
            message: this.$t("room.validate.codeRq")
          }
        ],
        price: [
          {
            trigger: "blur",
            required: true,
            message: this.$t("room.validate.priceRq")
          }
        ],
        type: [
          {
            trigger: "blur",
            required: true,
            message: this.$t("room.validate.typeRq")
          }
        ],
        status: [
          {
            trigger: "blur",
            required: true,
            message: this.$t("room.validate.statusRq")
          }
        ],
        supplies: [
          {
            trigger: "blur",
            required: true,
            message: this.$t("room.validate.suppliesRq")
          }
        ],
        size: [
          {
            trigger: "blur",
            required: true,
            message: this.$t("room.validate.sizeRq")
          }
        ]
      }
    };
  },
  created() {
    if (this.checkPermission("PERM_ROOM_READ")) {
      this.fetchOptions();
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
      fetchList(this.listQuery)
        .then(response => {
          this.total = response.data.countRow;
          this.list = response.data.object;
        })
        .finally(() => {
          this.listLoading = false;
        });
    },
    fetchOptions() {
      listSupplies().then(response => {
        this.suppliesOption = response.data;
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
              this.fetchOptions();
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
                  this.list.splice(index, 1, this.tempData);
                  break;
                }
              }
              this.$notify({
                title: "Success",
                message: this.$t("room.msg.updateSuccess"),
                type: "success",
                duration: 2000
              });
              this.dialogFormVisible = false;
              this.fetchOptions();
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
            message: this.$t("room.msg.deletedSuccess"),
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
      this.resetTemp();
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
      this.buttonConfirmLoading = false;
      this.dialogFormVisible = false;
    },
    formatColumnSupplies(supplies) {
      let text = [];
      supplies.map((e) => {
        this.suppliesOption.find(element => {
          if (element.id === e) text.push(element.name);
        });
      });
      return text;
    },
    resetTemp() {
      this.tempData = {
        id: "",
        code: "",
        type: "",
        status: "Active",
        supplies: "",
        description: "",
        price: "",
        hourlyPrice: "",
        size: ""
      };
    }
  }
};
</script>
