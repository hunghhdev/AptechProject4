<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.name"
        :placeholder="$t('user.search.inputUsername')"
        style="width: 300px;"
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
      >{{$t('common.btnSearch')}}</el-button>
      <el-button
        style="margin-left: 10px;"
        type="primary"
        icon="el-icon-plus"
        @click="handleCreate"
        class="filter-item"
      >{{$t('common.btnAdd')}}</el-button>
    </div>

    <el-table v-loading="listLoading" border fit highlight-current-row :data="list">
      <el-table-column prop="username" :label="$t('user.table.username')" align="center"></el-table-column>
      <el-table-column prop="createdBy" :label="$t('user.table.createdBy')" align="center"></el-table-column>
      <el-table-column
        :label="$t('user.table.createdDate')"
        align="center"
        sortable
        prop="createdDate"
      >
        <template slot-scope="scope">
          <span>{{ formatDate(scope.row.createdDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column fixed="right" :label="$t('user.table.action')" width="275" align="center">
        <template slot-scope="{row}">
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-edit"
            @click="handleUpdate(row)"
          >{{$t('common.btnEdit')}}</el-button>
          <el-button
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
        label-width="150px"
        style="width: 600px; margin-left:30px;"
        :rules="rules"
      >
        <el-form-item :label="$t('user.form.labelAvatar')" prop="avatar">
          <el-upload
            drag
            action
            :multiple="false"
            :limit="1"
            :http-request="handleUploadAvatar"
            class="avatar-uploader"
            :show-file-list="false"
            :before-upload="handleBeforeAvatarUpload"
            accept="image/png, image/gif, image/jpg, image/jpeg"
          >
            <img v-if="tempData.avatar" :src="tempData.avatar" class="avatar" />
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item :label="$t('user.form.labelUsername')" prop="username">
          <el-input
            :disabled="dialogStatus==='create'?false:true"
            v-model="tempData.username"
            :placeholder="$t('user.form.labelUsername')"
            class="filter-item"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('user.form.labelPassword')" prop="password">
          <el-input
            type="password"
            v-model="tempData.password"
            :placeholder="$t('user.form.labelPassword')"
            class="filter-item"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('user.form.labelPasswordRq')" prop="passwordRq">
          <el-input
            type="password"
            v-model="tempData.passwordRq"
            :placeholder="$t('user.form.labelPasswordRq')"
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
    <el-dialog :title="$t('user.delete.title')" :visible.sync="dialogDeleteVisible">
      <p>{{ $t('user.delete.msg', { accountName: tempData.username }) }}</p>
      <div slot="footer">
        <el-button @click="dialogDeleteVisible = false">{{ $t("common.btnCancel") }}</el-button>
        <el-button type="danger" @click="deleteData">{{ $t("common.btnConfirm") }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { list, create, update, remove, uploadAvatar } from "@/api/user";
import { formatDate } from "@/utils/";
import Pagination from "@/components/Pagination";
import store from "@/store";
export default {
  name: "User",
  components: { Pagination },
  data() {
    const validatePasswordRq = (rule, value, callback) => {
      if (this.tempData.password !== value) {
        callback(new Error(this.$t("user.validate.passwordNotMatch")));
      } else {
        callback();
      }
    };
    return {
      total: 0,
      dialogStatus: "",
      dialogDeleteVisible: false,
      buttonConfirmLoading: false,
      listLoading: true,
      listQuery: {
        page: 0,
        size: 10,
        name: "",
        fromDate: "",
        toDate: ""
      },
      tempData: {
        id: "",
        avatar: "",
        username: "",
        password: "",
        passwordRq: "",
        createdBy: "",
        role: 2
      },
      textMap: {
        create: this.$t("user.form.titleCreate"),
        update: this.$t("user.form.titleEdit")
      },
      dialogFormVisible: false,
      dateSearchPicker: [new Date() - 2592000000, new Date()],
      list: [],
      rules: {
        avatar: [
          {
            trigger: "blur",
            required: true,
            message: this.$t("user.validate.avatarRq")
          }
        ],
        username: [
          {
            trigger: "blur",
            required: true,
            message: this.$t("user.validate.usernameRq")
          }
        ],
        password: [
          {
            trigger: "blur",
            required: true,
            message: this.$t("user.validate.passwordRq")
          }
        ],
        passwordRq: [
          {
            trigger: "blur",
            required: true,
            validator: validatePasswordRq
          }
        ]
      }
    };
  },
  created() {
    this.fetchData();
  },
  methods: {
    fetchData() {
      if (this.dateSearchPicker) {
        this.listQuery.fromDate = this.dateSearchPicker[0];
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
                message: this.$t("user.msg.createSuccess"),
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
      this.tempData.createdBy = this.createdBy;
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
                message: this.$t("user.msg.updateSuccess"),
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
    handleBeforeAvatarUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 2;
      const isImage = file["type"].split("/")[0] === "image";
      if (!isImage) {
        this.$message.error("File not is a picture!");
      }
      if (!isLt2M) {
        this.$message.error("Avatar picture size can not exceed 2MB!");
      }
      return isLt2M && isImage;
    },
    handleUploadAvatar(file, fileList) {
      uploadAvatar(file.file).then(response => {
        this.tempData.avatar = response.data;
      });
    },
    resetTemp() {
      this.tempData = {
        id: "",
        avatar: "",
        username: "",
        password: "",
        passwordRq: "",
        createdBy: "",
        role: 2
      };
    },
    formatDate(date) {
      return formatDate(date);
    }
  }
};
</script>