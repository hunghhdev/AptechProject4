<template>
  <div class="app-container">
    <el-tabs type="border-card" @tab-click="handleSelectTab">
      <el-tab-pane :label="$t('bookings.tabBooking')">
        <div class="filter-container">
          <el-date-picker
            v-model="dateSearchPicker"
            type="datetimerange"
            :start-placeholder="$t('common.fromDate')"
            :end-placeholder="$t('common.toDate')"
            format="yyyy-MM-dd HH:mm"
            :default-time="['14:00:00', '12:00:00']"
          ></el-date-picker>
          <el-button
            class="filter-item"
            type="primary"
            icon="el-icon-search"
            @click="handleFilter"
          >{{$t('common.btnSearch')}}
          </el-button>
        </div>
        <el-table
          v-loading="listLoading"
          :data="list"
          element-loading-text="Loading"
          fit
          highlight-current-row
          border
          max-height="600px"
        >
          <el-table-column :label="$t('bookings.code')" min-width="150" align="center">
            <template slot-scope="scope">{{ scope.row.code }}</template>
          </el-table-column>
          <el-table-column :label="$t('bookings.size')" min-width="100" align="center">
            <template slot-scope="scope">{{ scope.row.size }}</template>
          </el-table-column>
          <el-table-column :label="$t('bookings.price')" min-width="150" align="center">
            <template slot-scope="scope">{{ scope.row.price }}</template>
          </el-table-column>
          <el-table-column :label="$t('bookings.hourlyPrice')" min-width="200" align="center">
            <template slot-scope="scope">{{ scope.row.hourlyPrice }}</template>
          </el-table-column>
          <el-table-column :label="$t('bookings.type')" min-width="150" align="center">
            <template slot-scope="scope">{{ scope.row.type }}</template>
          </el-table-column>
          <el-table-column :label="$t('bookings.supplies')" min-width="400" align="center">
            <template slot-scope="scope">{{ formatColumnSupplies(scope.row.supplies) }}</template>
          </el-table-column>
          <el-table-column fixed="right" :label="$t('common.action')" align="center" min-width="200">
            <template slot-scope="{row}">
              <el-button icon="el-icon-plus" type="primary" size="mini" @click="handleBooking(row)">
                {{ $t("bookings.btnBook") }}
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane :label="$t('bookings.tabBooked')">
        <el-table
          v-loading="listLoading"
          :data="listBooked"
          element-loading-text="Loading"
          fit
          highlight-current-row
          border
          max-height="600px"
        >
          <el-table-column :label="$t('bookings.code')" min-width="90" align="center">
            <template slot-scope="scope">{{ scope.row.roomCode }}</template>
          </el-table-column>
          <el-table-column :label="$t('bookings.totalPriceExpected')" min-width="150" align="center">
            <template slot-scope="scope">{{ scope.row.totalPriceExpected }}</template>
          </el-table-column>
          <el-table-column :label="$t('bookings.customerName')" min-width="150" align="center">
            <template slot-scope="scope">{{ scope.row.customerName }}</template>
          </el-table-column>
          <el-table-column :label="$t('bookings.customerPhone')" min-width="150" align="center">
            <template slot-scope="scope">{{ scope.row.customerPhone }}</template>
          </el-table-column>
          <el-table-column :label="$t('bookings.identificationCus')" min-width="150" align="center">
            <template slot-scope="scope">{{ scope.row.identification }}</template>
          </el-table-column>
          <el-table-column :label="$t('bookings.fromDate')" min-width="150" align="center">
            <template slot-scope="scope">{{ formatDateTime(scope.row.fromDate) }}</template>
          </el-table-column>
          <el-table-column :label="$t('bookings.toDate')" min-width="150" align="center">
            <template slot-scope="scope">{{ formatDateTime(scope.row.toDate) }}</template>
          </el-table-column>
          <el-table-column fixed="right" :label="$t('common.action')" align="center" min-width="300">
            <template slot-scope="{row}">
              <el-button icon="el-icon-close" type="danger" size="mini" @click="handleCancelBook(row)">
                {{ $t("common.btnCancel") }}
              </el-button>
              <el-button icon="el-icon-check" type="primary" size="mini" @click="handleCheckIn(row)"
                         :disabled="btnCheckIn(row)">
                {{ $t("bookings.btnCheckIn") }}
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane :label="$t('bookings.tabUse')">
        <el-table
          v-loading="listLoading"
          :data="listUsing"
          element-loading-text="Loading"
          fit
          highlight-current-row
          border
          max-height="600px"
        >
          <el-table-column label="" min-width="50" align="center">
            <template slot-scope="scope">{{ scope.row.id }}</template>
          </el-table-column>
          <el-table-column :label="$t('bookings.code')" min-width="90" align="center">
            <template slot-scope="scope">{{ scope.row.roomCode }}</template>
          </el-table-column>
          <el-table-column :label="$t('bookings.customerName')" min-width="150" align="center">
            <template slot-scope="scope">{{ scope.row.customerName }}</template>
          </el-table-column>
          <el-table-column :label="$t('bookings.customerPhone')" min-width="150" align="center">
            <template slot-scope="scope">{{ scope.row.customerPhone }}</template>
          </el-table-column>
          <el-table-column :label="$t('bookings.identificationCus')" min-width="150" align="center">
            <template slot-scope="scope">{{ scope.row.identification }}</template>
          </el-table-column>
          <el-table-column :label="$t('bookings.fromDate')" min-width="150" align="center">
            <template slot-scope="scope">{{ formatDateTime(scope.row.fromDate) }}</template>
          </el-table-column>
          <el-table-column :label="$t('bookings.toDate')" min-width="150" align="center">
            <template slot-scope="scope">{{ formatDateTime(scope.row.toDate) }}</template>
          </el-table-column>
          <el-table-column fixed="right" :label="$t('common.action')" align="center" min-width="100">
            <template slot-scope="{row}">
              <el-button icon="el-icon-check" type="primary" size="mini" @click="handleCheckOut(row)">
                {{ $t("bookings.btnCheckOut") }}
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
    <el-dialog :title="$t('bookings.title')" :visible.sync="dialogBookingVisible">
      <el-form ref="dataForm" :model="tempData" label-position="left" label-width="30%"
               style="width: 90%; margin-left:30px;" :rules="rules">
        <el-form-item :label="$t('room.form.labelCode')" prop="code">
          <el-input v-model="tempData.code" :placeholder="$t('room.form.labelCode')" class="filter-item" disabled/>
        </el-form-item>
        <el-form-item :label="$t('room.form.labelSize')" prop="size">
          <el-input-number controls-position="right" v-model="tempData.size" class="filter-item" disabled/>
        </el-form-item>
        <el-form-item :label="$t('bookings.type')" prop="price">
          <el-switch v-model="tempData.type" :active-text="$t('bookings.hour')" :inactive-text="$t('bookings.day')"
                     disabled/>
        </el-form-item>
        <el-form-item :label="$t('bookings.fromDate')" prop="fromDate">
          <el-date-picker v-model="tempData.fromDate" type="datetime" disabled/>
        </el-form-item>
        <el-form-item :label="$t('bookings.toDate')" prop="toDate">
          <el-date-picker v-model="tempData.toDate" type="datetime" disabled/>
        </el-form-item>
        <el-form-item :label="$t('bookings.customerPhone')" prop="customerPhone">
          <el-autocomplete v-model="tempData.customerPhone" :fetch-suggestions="querySearch" :trigger-on-focus="false"
                           @select="handleSelect" class="filter-item" style="width: 475px">
            <table slot-scope="{ item }" width="475px">
              <tr>
                <th>{{ $t('bookings.customerPhone') }}</th>
                |
                <th>{{ $t('bookings.customerName') }}</th>
                |
                <th>{{ $t('bookings.identificationCus') }}</th>
              </tr>
              <tr>
                <td>{{ item.phone }}</td>
                |
                <td>{{ item.name }}</td>
                |
                <td>{{ item.identification }}</td>
              </tr>
            </table>
          </el-autocomplete>
        </el-form-item>
        <el-form-item :label="$t('bookings.customerName')" prop="customerName">
          <el-input v-model="tempData.customerName" class="filter-item"/>
        </el-form-item>
        <el-form-item :label="$t('bookings.identificationCus')" prop="identification">
          <el-input v-model="tempData.identification" class="filter-item"/>
        </el-form-item>
        <el-form-item :label="$t('bookings.timeUse')" prop="timeUse">
          <el-input v-model="tempData.timeUse" class="filter-item" disabled>
            <template slot="append">{{ tempData.type ? $t('bookings.hour') : $t('bookings.day') }}</template>
          </el-input>
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
    <el-dialog :title="$t('bookings.titleCheckIn')" :visible.sync="dialogCheckInVisible">
      <el-form ref="dataForm" :model="tempData" label-position="left" label-width="30%"
               style="width: 90%; margin-left:30px;">
        <el-form-item :label="$t('room.form.labelCode')" prop="code">
          <el-input v-model="tempData.roomCode" :placeholder="$t('room.form.labelCode')" class="filter-item" disabled/>
        </el-form-item>
        <el-form-item :label="$t('bookings.fromDate')" prop="fromDate">
          <el-date-picker v-model="tempData.fromDate" type="datetime" disabled/>
        </el-form-item>
        <el-form-item :label="$t('bookings.toDate')" prop="toDate">
          <el-date-picker v-model="tempData.toDate" type="datetime" disabled/>
        </el-form-item>
        <el-form-item :label="$t('bookings.customerPhone')" prop="customerPhone">
          <el-input v-model="tempData.customerPhone" class="filter-item" disabled/>
        </el-form-item>
        <el-form-item :label="$t('bookings.customerName')" prop="customerName">
          <el-input v-model="tempData.customerName" class="filter-item" disabled/>
        </el-form-item>
        <el-form-item :label="$t('bookings.identificationCus')" prop="identification">
          <el-input v-model="tempData.identification" class="filter-item" disabled/>
        </el-form-item>
        <el-form-item :label="$t('bookings.count')" prop="count">
          <el-input v-model="tempData.totalPriceExpected" class="filter-item" disabled>
            <template slot="append">VNĐ</template>
          </el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelHandle()">{{ $t("common.btnCancel") }}</el-button>
        <el-button
          type="primary"
          :loading="buttonConfirmLoading"
          @click="handleSubmitCheckIn()"
        >{{ $t("common.btnConfirm") }}</el-button>
      </span>
    </el-dialog>
    <el-dialog :title="$t('bookings.titleCancel')" :visible.sync="dialogCancelBookVisible">
      <span>{{ $t('bookings.msg.cancelBook', {roomCode: tempData.roomCode, customerName: tempData.customerName}) }}</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelHandle()">{{ $t('common.btnCancel') }}</el-button>
        <el-button type="primary" @click="handleSubmitCancelBook()">{{ $t('common.btnConfirm') }}</el-button>
      </span>
    </el-dialog>
    <el-dialog :title="$t('bookings.titleCheckOut')" :visible.sync="dialogCheckOutVisible">
      <el-form ref="dataForm" :model="tempData" label-position="left" label-width="30%"
               style="width: 90%; margin-left:30px;">
        <el-form-item :label="$t('bookings.customerName')" prop="customerName">
          <el-input v-model="tempData.customerName" :placeholder="$t('bookings.customerName')" class="filter-item"
                    disabled/>
        </el-form-item>
        <el-form-item :label="$t('bookings.customerPhone')" prop="customerPhone">
          <el-input v-model="tempData.customerPhone" :placeholder="$t('bookings.customerPhone')" class="filter-item"
                    disabled/>
        </el-form-item>
        <el-form-item :label="$t('bookings.identificationCus')" prop="identificationCus">
          <el-input v-model="tempData.identification" :placeholder="$t('bookings.identificationCus')"
                    class="filter-item" disabled/>
        </el-form-item>
        <el-form-item :label="$t('bookings.code')" prop="code">
          <el-input v-model="tempData.roomCode" :placeholder="$t('bookings.code')" class="filter-item" disabled/>
        </el-form-item>
        <el-form-item :label="$t('bookings.fromDate')" prop="fromDate">
          <el-date-picker v-model="tempData.fromDate" type="datetime" disabled/>
        </el-form-item>
        <el-form-item :label="$t('bookings.toDate')" prop="toDate">
          <el-date-picker v-model="tempData.toDate" type="datetime" disabled/>
        </el-form-item>
        <el-form-item :label="$t('bookings.fromDate')" prop="checkInTime">
          <el-date-picker v-model="tempData.checkInTime" type="datetime" disabled/>
        </el-form-item>
        <el-form-item :label="$t('bookings.toDate')" prop="checkOutTime">
          <el-date-picker v-model="tempData.checkOutTime" type="datetime" disabled/>
        </el-form-item>
        <el-form-item :label="$t('bookings.totalPriceExpected')" prop="totalPriceExpected">
          <el-input v-model="tempData.totalPriceExpected" :placeholder="$t('bookings.totalPriceExpected')"
                    class="filter-item" v-mask="'###.###.###'" disabled/>
        </el-form-item>
        <el-form-item :label="$t('bookings.totalPriceReality')" prop="totalPriceReality">
          <el-input v-model="tempData.totalPriceReality" :placeholder="$t('bookings.totalPriceReality')"
                    class="filter-item" v-mask="'###.###'" disabled/>
        </el-form-item>
        <el-form-item :label="$t('bookings.type')" prop="price">
          <el-switch v-model="tempData.type" :active-text="$t('bookings.hour')" :inactive-text="$t('bookings.day')"
                     disabled/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button
          type="primary"
          :loading="buttonConfirmLoading"
          @click="handleSubmmitCheckOut()"
        >{{ $t("common.btnConfirm") }}</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import {listEmpty, booking, listBooked, listUsing, cancelBook, checkIn, getById, checkOut} from "@/api/booking";
  import {formatDateTime, isToday} from "@/utils/";
  import {listSupplies} from "@/api/supplies";
  import {findES} from "@/api/customer";
  export default {
    name: "bookings",
    data() {
      return {
        total: 0,
        list: null,
        listBooked: null,
        listUsing: null,
        listLoading: false,
        listQuery: {
          code: "",
          fromDate: "",
          toDate: ""
        },
        suppliesOption: "",
        dialogBookingVisible: false,
        dialogCancelBookVisible: false,
        dialogCheckInVisible: false,
        dialogCheckOutVisible: false,
        tempData: {
          id: "",
          code: "",
          price: "",
          hourlyPrice: "",
          size: "",
          fromDate: "",
          toDate: "",
          customerName: "",
          customerPhone: "",
          identification: "",
          count: "",
          type: true,
          timeUse: "",
          roomId: "",
          roomCode: "",
          totalPriceExpected: "",
          totalPriceReality: "",
          checkInTime: "",
          checkOutTime: ""
        },
        saveLocation: "",
        dateSearchPicker: "",
        buttonConfirmLoading: false,
        rules: {
          customerName: [
            {
              trigger: "blur",
              required: true,
              message: this.$t("bookings.validate.validateRq")
            }
          ],
          customerPhone: [
            {
              trigger: "blur",
              required: true,
              message: this.$t("bookings.validate.validateRq")
            }
          ],
          identification: [
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
    },
    methods: {
      formatDateTime,
      fetchData() {
        if (this.dateSearchPicker) {
          this.listQuery.fromDate = this.dateSearchPicker[0].getTime();
          this.listQuery.toDate = this.dateSearchPicker[1].getTime();

          listEmpty(this.listQuery)
            .then(response => {
              this.list = response.data.object;
            })
            .finally(() => {
              this.listLoading = false;
            });
        }
      },
      fetchDataTableBooked() {
        listBooked().then(response => {
          this.listBooked = response.data.object;
        })
          .finally(() => {
            this.listLoading = false;
          })
      },
      fetchDataTableRoomUse() {
        listUsing().then(response => {
          this.listUsing = response.data.object;
        }).finally(() => {
          this.listLoading = false;
        })
      },
      fetchOptions() {
        listSupplies().then(response => {
          this.suppliesOption = response.data;
        });
      },
      handleFilter() {
        if (this.dateSearchPicker && this.dateSearchPicker[0].getTime() > new Date().getTime()) {
          this.fetchData();
        } else {
          this.$notify({
            title: "Warning",
            message: this.$t("bookings.msg.dateWarning"),
            type: "warning",
            duration: 2000
          });
        }
      },
      cancelHandle() {
        this.resetTemp();
        this.$nextTick(() => {
          this.$refs["dataForm"].clearValidate();
        });
        this.buttonConfirmLoading = false;
        this.dialogBookingVisible = false;
        this.dialogCancelBookVisible = false;
        this.dialogCheckInVisible = false;
        this.dialogCheckOutVisible = false;
      },
      handleBooking(row) {
        this.dialogBookingVisible = true;
        this.$nextTick(() => {
          this.$refs["dataForm"].clearValidate();
        });
        this.tempData = Object.assign({}, row);
        this.tempData.roomId = row.id;
        this.tempData.fromDate = this.listQuery.fromDate;
        this.tempData.toDate = this.listQuery.toDate;
        let time = this.tempData.toDate - this.tempData.fromDate;
        this.tempData.type = time <= 7200000;
        this.tempData.timeUse = Math.ceil(time / (1000 * 60 * 60 * (this.tempData.type ? 1 : 24)));
        this.tempData.count = this.tempData.timeUse * (this.tempData.type ? this.tempData.hourlyPrice : this.tempData.price);
      },
      handleCancelBook(row) {
        this.tempData = row;
        this.dialogCancelBookVisible = true;
      },
      handleCheckIn(row) {
        this.tempData = row;
        this.dialogCheckInVisible = true;
      },
      btnCheckIn(row) {
        return !isToday(new Date(row.fromDate), new Date());
      },
      handleCheckOut(row) {
        this.saveLocation = Object.assign({}, row);;
        getById({id: row.id}).then(response => {
          this.dialogCheckOutVisible = true;
          this.tempData = response.data;
          if (!this.tempData.checkInTime) this.tempData.checkInTime = this.tempData.fromDate
          this.tempData.checkOutTime = new Date();
          let time = this.tempData.checkOutTime.getTime() - new Date(this.tempData.fromDate).getTime();
          this.tempData.type = time <= 7200000;
          this.tempData.timeUse = Math.ceil(time / (1000 * 60 * 60 * (this.tempData.type ? 1 : 24)));
          this.tempData.totalPriceReality = this.tempData.timeUse * (this.tempData.type ? this.tempData.hourlyPrice : this.tempData.price);
        }).catch(() => {
          console.log("co gi day sai sai")
        })
      },
      handleSubmitCancelBook() {
        cancelBook(this.tempData).then(() => {
          const index = this.listBooked.indexOf(this.tempData);
          this.listBooked.splice(index, 1);
          this.total -= 1;
          this.dialogCancelBookVisible = false;
          this.$notify({
            title: "Success",
            message: this.$t("bookings.msg.cancelBookedSuccess"),
            type: "success",
            duration: 2000
          });
        })
      },
      handleSubmitCheckIn() {
        checkIn(this.tempData).then(() => {
          const index = this.listBooked.indexOf(this.tempData);
          this.listBooked.splice(index, 1);
          this.total -= 1;
          this.dialogCheckInVisible = false;
          this.$notify({
            title: "Success",
            message: this.$t("bookings.msg.checkInSuccess"),
            type: "success",
            duration: 2000
          });
        })
      },
      handleSubmmitCheckOut() {
        checkOut(this.tempData).then(() => {
          listUsing().then(response => {
            this.listUsing = response.data.object;
          }).finally(() => {
            this.listLoading = false;
          })
          this.dialogCheckOutVisible = false;
          this.$notify({
            title: "Success",
            message: this.$t("bookings.msg.checkOutSuccess"),
            type: "success",
            duration: 2000
          });
        })
      },
      bookingData() {
        this.$refs["dataForm"].validate(valid => {
          if (valid) {
            booking(this.tempData)
              .then(response => {
                const index = this.list.indexOf(this.tempData);
                this.list.splice(index, 1);
                this.dialogBookingVisible = false;
                this.$notify({
                  title: "Success",
                  message: this.$t("bookings.msg.bookingFinish"),
                  type: "success",
                  duration: 2000
                });
              })
              .finally(() => {
                this.buttonConfirmLoading = false;
              });
          }
        });
      },
      querySearch(queryString, cb) {
        findES({phone: queryString}).then(response => {
          let results = response.data;
          cb(results);
        });
      },
      handleSelect(item) {
        if (item) {
          this.tempData.customerName = item.name;
          this.tempData.customerPhone = item.phone;
          this.tempData.customerId = item.id;
          this.tempData.identification = item.identification;
        }
      },
      handleSelectTab(tab) {
        if (tab.index == 0) {
          this.fetchData();
        } else if (tab.index == 1) {
          this.fetchDataTableBooked();
        } else if (tab.index == 2) {
          this.fetchDataTableRoomUse();
        } else {
          console.log("Có gì đấy sai sai")
        }
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
          price: "",
          size: "",
          fromDate: "",
          toDate: "",
          customerName: "",
          customerPhone: "",
          identification: "",
          count: "",
          timeUse: "",
          type: true,
          roomId: "",
        }
      }
    }
  };
</script>
