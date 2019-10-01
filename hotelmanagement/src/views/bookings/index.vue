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
          <el-table-column :label="$t('bookings.branch')" min-width="220" align="center">
            <template slot-scope="scope">
              <span>{{ formatColumnBranch(scope.row.branchId) }}</span>
            </template>
          </el-table-column>
          <el-table-column :label="$t('bookings.code')" min-width="150" align="center">
            <template slot-scope="scope">{{ formatColumnCode(scope.row) }}</template>
          </el-table-column>
          <el-table-column :label="$t('bookings.supplies')" min-width="250" align="center">
            <template slot-scope="scope">{{ scope.row.supplies }}</template>
          </el-table-column>
          <el-table-column :label="$t('bookings.type')" min-width="150" align="center">
            <template slot-scope="scope">{{ scope.row.type }}</template>
          </el-table-column>
          <el-table-column :label="$t('bookings.price')" min-width="150" align="center">
            <template slot-scope="scope">{{ scope.row.price }}</template>
          </el-table-column>
          <el-table-column
            fixed="right"
            :label="$t('common.action')"
            align="center"
            min-width="200"
          >
            <template slot-scope="{row}">
              <el-button
                icon="el-icon-plus"
                type="primary"
                size="mini"
                @click="handleBooking(row)"
              >{{ $t("bookings.btnBook") }}</el-button>
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
    <el-dialog :title="$t('bookings.title')" :visible.sync="dialogBookingVisible">
      <el-form
        ref="dataForm"
        :model="tempData"
        label-position="left"
        label-width="30%"
        style="width: 90%; margin-left:30px;"
        :rules="rules"
      >
        <el-form-item :label="$t('room.form.labelBranch')" prop="branchId">
          <el-select
            v-if="branchOptions"
            v-model="tempData.branchId"
            class="filter-item"
            :placeholder="$t('common.select')"
            disabled
          >
            <el-option
              v-for="item in branchOptions"
              :key="item.id"
              :label="item.branchName"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('room.form.labelCode')" prop="code">
          <el-input
            v-model="tempData.code"
            :placeholder="$t('room.form.labelCode')"
            class="filter-item"
            disabled
          >
            <template slot="prepend">{{ prefixCode }}</template>
          </el-input>
        </el-form-item>
        <el-form-item :label="$t('room.form.labelSize')" prop="size">
          <el-input-number
            controls-position="right"
            v-model="tempData.size"
            class="filter-item"
            disabled
          ></el-input-number>
        </el-form-item>
        <el-form-item :label="$t('room.form.labelPrice')" prop="price">
          <el-input-number
            controls-position="right"
            v-model="tempData.price"
            class="filter-item"
            disabled
          ></el-input-number>* 1000
        </el-form-item>
        <el-form-item :label="$t('bookings.fromDate')" prop="fromDate">
          <el-date-picker v-model="tempData.fromDate" type="date" @change="handleCount()"></el-date-picker>
        </el-form-item>
        <el-form-item :label="$t('bookings.toDate')" prop="toDate">
          <el-date-picker v-model="tempData.toDate" type="date" @change="handleCount()"></el-date-picker>
        </el-form-item>
        <el-form-item :label="$t('bookings.nameCus')" prop="nameCus">
          <el-autocomplete
            v-model="tempData.nameCus"
            :fetch-suggestions="querySearch"
            placeholder="Please Input"
            :trigger-on-focus="false"
            @select="handleSelect"
            class="filter-item"
          >
            <table slot-scope="{ item }">
              <tr>
                <th>{{ item.name }}</th>
                <th>{{ item.phone }}</th>
              </tr>
            </table>
          </el-autocomplete>
        </el-form-item>
        <el-form-item :label="$t('bookings.nameCus')" prop="nameCus">
          <el-input v-model="tempData.nameCus" class="filter-item" />
        </el-form-item>
        <el-form-item :label="$t('bookings.phoneCus')" prop="phoneCus">
          <el-input v-model="tempData.phoneCus" class="filter-item" />
        </el-form-item>
        <el-form-item :label="$t('bookings.days')" prop="days">
          <el-input v-model="tempData.days" class="filter-item" disabled />
        </el-form-item>
        <el-form-item :label="$t('bookings.count')" prop="count">
          <el-input v-model="tempData.count" class="filter-item" disabled>
            <template slot="append">VNĐ</template>
          </el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelHandle()">{{ $t("common.btnCancel") }}</el-button>
        <el-button
          type="primary"
          :loading="buttonConfirmLoading"
          @click="bookingData()"
        >{{ $t("common.btnConfirm") }}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { listEmpty, booking } from "@/api/booking";
import { listBranchPlace } from "@/api/branchPlace";
import { findES } from "@/api/customer";
import Pagination from "@/components/Pagination";
export default {
  name: "bookings",
  components: { Pagination },
  data() {
    const validateFromDateRq = (rule, value, callback) => {
      if (!value) {
        callback(new Error(this.$t("bookings.validate.validateRq")));
      } else if (!(value >= new Date(new Date().setHours(0, 0, 0, 0)))) {
        callback(new Error(this.$t("bookings.validate.fromDateNow")));
      } else {
        callback();
      }
    };
    const validateToDateRq = (rule, value, callback) => {
      if (!value) {
        callback(new Error(this.$t("bookings.validate.validateRq")));
      } else if (this.tempData.fromDate >= value) {
        callback(new Error(this.$t("bookings.validate.toDate")));
      } else {
        callback();
      }
    };
    return {
      total: 0,
      list: null,
      listLoading: true,
      listQuery: {
        page: 1,
        size: 10,
        code: "",
        branch: "",
        fromDate: "",
        toDate: ""
      },
      branchOptions: [],
      dialogBookingVisible: false,
      tempData: {
        id: "",
        code: "",
        branchId: "",
        price: "",
        size: "",
        fromDate: "",
        toDate: "",
        nameCus: "",
        phoneCus: "",
        count: "",
        days: "",
        roomId: ""
      },
      prefixCode: "",
      buttonConfirmLoading: false,
      rules: {
        fromDate: [
          {
            trigger: "change",
            required: true,
            validator: validateFromDateRq
          }
        ],
        toDate: [
          {
            trigger: "change",
            required: true,
            validator: validateToDateRq
          }
        ],
        nameCus: [
          {
            trigger: "blur",
            required: true,
            message: this.$t("bookings.validate.validateRq")
          }
        ],
        phoneCus: [
          {
            trigger: "blur",
            required: true,
            message: this.$t("bookings.validate.validateRq")
          }
        ]
      }
    };
  },
  created() {
    this.fetchOptions();
    this.fetchData();
  },
  methods: {
    fetchData() {
      listEmpty(this.listQuery)
        .then(response => {
          this.list = response.data.object;
        })
        .finally(() => {
          this.listLoading = false;
        });
    },
    fetchOptions() {
      listBranchPlace().then(response => {
        this.branchOptions = response.data;
      });
    },
    handleClick(tab, event) {
      console.log(tab);
    },
    cancelHandle() {
      this.resetTemp;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
      this.buttonConfirmLoading = false;
      this.dialogFormVisible = false;
    },
    handleBooking(row) {
      this.dialogBookingVisible = true;
      this.$nextTick(() => {
        this.$refs["dataForm"].clearValidate();
      });
      this.tempData = Object.assign({}, row);
      this.tempData.roomId = row.id;
      console.log(row);
      this.changePrefixCode();
    },
    bookingData() {
      this.$refs["dataForm"].validate(valid => {
        if (valid) {
          this.buttonConfirmLoading = true;
          this.list.forech(element, i => {
            console.log(element);
          });
          // booking(this.tempData)
          //   .then(response => {
          //     this.list.find(element, i => {
          //       console.log(element);
          //     });
          //     const index = this.list.indexOf(this.tempData);
          //     this.list.splice(index, 1);
          //     this.dialogFormVisible = false;
          //   })
          //   .finally(() => {
          //     this.buttonConfirmLoading = false;
          //   });
        }
      });
    },
    querySearch(queryString, cb) {
      findES({ phone: queryString }).then(response => {
        var results = response.data;
        cb(results);
        console.log(results);
      });
    },
    createFilter(queryString) {
      return link => {
        return (
          link.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0
        );
      };
    },
    handleSelect(item) {
      if (item) {
        this.tempData.nameCus = item.name;
      }
    },
    handleCount() {
      if (this.tempData.fromDate && this.tempData.toDate) {
        this.tempData.days =
          (this.tempData.toDate - this.tempData.fromDate) /
          (1000 * 60 * 60 * 24);
        this.tempData.count = this.tempData.days * this.tempData.price;
      }
    },
    changePrefixCode() {
      if (this.branchOptions) {
        this.branchOptions.filter(branchOptions => {
          if (this.tempData.branchId === branchOptions.id) {
            this.prefixCode = branchOptions.branchCode;
          }
        });
      }
    },
    formatColumnBranch(branchId) {
      let text = "";
      if (this.branchOptions) {
        this.branchOptions.find(BranchPlace => {
          if (branchId === BranchPlace.id) text = BranchPlace.branchName;
        });
      }
      return text;
    },
    formatColumnCode(row) {
      let text = "";
      if (this.branchOptions) {
        this.branchOptions.filter(branchOption => {
          if (row.branchId === branchOption.id) {
            text = branchOption.branchCode + "_" + row.code;
          }
        });
      }
      return text;
    }
  }
};
</script>