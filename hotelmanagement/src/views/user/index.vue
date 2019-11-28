<template>
  <div v-if="checkPermission('PERM_USER_READ')" class="app-container">
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
        :start-placeholder="$t('common.fromDate')"
        :end-placeholder="$t('common.toDate')"
        class="filter-item"
      ></el-date-picker>
      <el-button
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
      >{{$t('common.btnSearch')}}</el-button>
      <el-button
        v-if="checkPermission('PERM_USER_CREATE')"
        style="margin-left: 10px;"
        type="primary"
        icon="el-icon-plus"
        @click="handleCreate"
        class="filter-item"
      >{{$t('common.btnAdd')}}</el-button>
    </div>
    <el-table v-loading="listLoading" border fit highlight-current-row :data="list">
      <el-table-column
        prop="fullName"
        :label="$t('user.table.fullname')"
        align="center"
        min-width="220"
      ></el-table-column>
      <el-table-column
        prop="username"
        :label="$t('user.table.username')"
        align="center"
        min-width="200"
      ></el-table-column>
      <el-table-column prop="email" :label="$t('user.table.email')" align="center" min-width="220"></el-table-column>
      <el-table-column prop="roleId" :label="$t('user.table.role')" align="center" min-width="220">
        <template slot-scope="scope">
          <span>{{ formatColumnRole(scope.row.roleId) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="createdBy"
        :label="$t('user.table.createdBy')"
        align="center"
        min-width="220"
      ></el-table-column>
      <el-table-column
        :label="$t('user.table.createdDate')"
        align="center"
        sortable
        prop="createdDate"
        min-width="220"
      >
        <template slot-scope="scope">
          <span>{{ formatDate(scope.row.createdDate) }}</span>
        </template>
      </el-table-column>
      <el-table-column fixed="right" :label="$t('common.action')" min-width="200" align="center">
        <template slot-scope="{row}">
          <el-button
            v-if="checkPermission('PERM_USER_UPDATE')"
            type="primary"
            size="mini"
            icon="el-icon-edit"
            @click="handleUpdate(row)"
          >{{$t('common.btnEdit')}}</el-button>
          <el-button
            v-if="checkPermission('PERM_USER_DELETE')"
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
        <el-form-item :label="$t('user.form.labelAvatar')" prop="avatar">
          <el-upload
            drag
            action
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
        <el-form-item :label="$t('user.form.labelFullname')" prop="fullName">
          <el-input
            v-model="tempData.fullName"
            :placeholder="$t('user.form.labelFullname')"
            class="filter-item"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('user.form.labelUsername')" prop="username">
          <el-input
            :disabled="dialogStatus==='create'?false:true"
            v-model="tempData.username"
            :placeholder="$t('user.form.labelUsername')"
            class="filter-item"
          ></el-input>
        </el-form-item>
        <el-form-item :label="$t('user.form.labelEmail')" prop="email">
          <el-input
            :disabled="dialogStatus==='create'?false:true"
            v-model="tempData.email"
            :placeholder="$t('user.form.labelEmail')"
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
        <el-form-item :label="$t('user.form.labelRole')" prop="roleId">
          <el-select
            v-if="roleOptions"
            v-model="tempData.roleId"
            class="filter-item"
            :placeholder="$t('common.select')"
          >
            <el-option
              v-for="item in roleOptions"
              :key="item.id"
              :label="item.roleName"
              :value="item.id"
            />
          </el-select>
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
import { roles } from "@/api/role";
import { formatDate } from "@/utils/";
import Pagination from "@/components/Pagination";
import store from "@/store";
import checkPermission from "@/utils/permission";
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
        fromDate: 0,
        toDate: 0
      },
      tempData: {
        id: "",
        avatar: "",
        fullName: "",
        username: "",
        email: "",
        password: "",
        passwordRq: "",
        createdBy: "",
        roleId: "",
        role: 0
      },
      textMap: {
        create: this.$t("user.form.titleCreate"),
        update: this.$t("user.form.titleEdit")
      },
      dialogFormVisible: false,
      dateSearchPicker: "",
      list: [],
      roles: [],
      roleOptions: undefined,
      rules: {
        email: [
          {
            trigger: "blur",
            required: true,
            message: this.$t("user.validate.emailRq")
          }
        ],
        fullName: [
          {
            trigger: "blur",
            required: true,
            message: this.$t("user.validate.fullNameRq")
          }
        ],
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
        ],
        roleId: [
          {
            trigger: "blur",
            required: true,
            message: this.$t("user.validate.roleRq")
          }
        ]
      }
    };
  },
  created() {
    if (this.checkPermission("PERM_USER_READ")) {
      this.fetchValueInput();
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
      list(this.listQuery).then(response => {
        this.list = response.data.object;
        this.total = response.data.countRow;
        this.listLoading = false;
      });
    },
    fetchValueInput() {
      roles().then(response => {
        this.roles = response.data;
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
    formatColumnRole(roleId) {
      let text = "";
      if (this.roles) {
        this.roles.filter(role => {
          if (role.id === roleId) {
            text = role.roleName;
          }
        });
      }
      return text;
    },
    resetTemp() {
      this.tempData = {
        id: "",
        avatar: "",
        fullName: "",
        username: "",
        email: "",
        password: "",
        passwordRq: "",
        createdBy: "",
        roleId: "",
        role: 0
      };
    }
  }
};
</script>
