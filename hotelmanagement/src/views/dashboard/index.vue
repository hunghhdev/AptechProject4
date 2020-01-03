<template>
  <div class="app-container">
    <el-row :gutter="40" class="panel-group">
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-using">
            <i class="el-icon-s-home card-panel-icon"></i>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              Số phòng hoạt động
            </div>
            <p class="card-panel-num">{{data.using}}
            <p/>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-booking">
            <i class="el-icon-s-order card-panel-icon"></i>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              Chờ Checkin
            </div>
            <p class="card-panel-num">{{data.booking}}
            <p/>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-people">
            <i class="el-icon-user card-panel-icon"></i>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              Số khách hàng
            </div>
            <p class="card-panel-num">{{data.customer}}
            <p/>
          </div>
        </div>
      </el-col>
      <el-col :xs="12" :sm="12" :lg="6" class="card-panel-col">
        <div class="card-panel">
          <div class="card-panel-icon-wrapper icon-money">
            <i class="el-icon-money card-panel-icon"></i>
          </div>
          <div class="card-panel-description">
            <div class="card-panel-text">
              Lượt Khách
            </div>
            <p class="card-panel-num">{{data.count}}
            <p/>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import {using,booking,customer,count} from "@/api/dashboard";

  export default {
    name: 'dashboard',
    data() {
      return {
        data: {
          using: 0,
          booking: 0,
          customer: 0,
          count: 0
        }
      }
    },
    created() {
      this.using();
      this.booking();
      this.customer();
      this.count();
    },
    methods: {
      using() {
        using().then(response => {
          this.data.using = response.data;
        })
      },
      booking() {
        booking().then(response => {
          this.data.booking = response.data;
        })
      },
      customer() {
        customer().then(response => {
          this.data.customer = response.data;
        })
      },
      count() {
        count().then(response => {
          this.data.count = response.data;
        })
      }
    }
  }
</script>


<style lang="scss" scoped>
  .panel-group {
    margin-top: 18px;

    .card-panel-col {
      margin-bottom: 32px;
    }

    .card-panel {
      height: 108px;
      cursor: pointer;
      font-size: 12px;
      position: relative;
      overflow: hidden;
      color: #666;
      background: #fff;
      box-shadow: 4px 4px 40px rgba(0, 0, 0, .05);
      border-color: rgba(0, 0, 0, .05);

      &:hover {
        .card-panel-icon-wrapper {
          color: #fff;
        }

        .icon-people {
          background: #40c9c6;
        }

        .icon-booking {
          background: #36a3f7;
        }

        .icon-money {
          background: #f4516c;
        }

        .icon-using {
          background: #34bfa3
        }
      }

      .icon-people {
        color: #40c9c6;
      }

      .icon-booking {
        color: #36a3f7;
      }

      .icon-money {
        color: #f4516c;
      }

      .icon-using {
        color: #34bfa3
      }

      .card-panel-icon-wrapper {
        float: left;
        margin: 14px 0 0 14px;
        padding: 16px;
        transition: all 0.38s ease-out;
        border-radius: 6px;
      }

      .card-panel-icon {
        float: left;
        font-size: 48px;
      }

      .card-panel-description {
        float: right;
        font-weight: bold;
        margin: 26px;
        margin-left: 0px;

        .card-panel-text {
          line-height: 18px;
          color: rgba(0, 0, 0, 0.45);
          font-size: 16px;
          margin-bottom: 12px;
        }

        .card-panel-num {
          font-size: 20px;
        }
      }
    }
  }

  @media (max-width: 550px) {
    .card-panel-description {
      display: none;
    }

    .card-panel-icon-wrapper {
      float: none !important;
      width: 100%;
      height: 100%;
      margin: 0 !important;

      .svg-icon {
        display: block;
        margin: 14px auto !important;
        float: none !important;
      }
    }
  }
</style>
