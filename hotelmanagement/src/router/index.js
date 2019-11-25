import Vue from "vue";
import Router from "vue-router";
import Layout from "../layout/";

Vue.use(Router);

export const constantRoutes = [
  {
    path: "/login",
    name: "Login",
    component: () => import("@/views/login"),
    hidden: true
  },
  {
    path: "/",
    component: Layout,
    redirect: "/dashboard",
    children: [
      {
        path: "dashboard",
        component: () => import("@/views/dashboard"),
        name: "dashboard",
        meta: { title: "dashboard", icon: "el-icon-s-data", affix: true }
      }
    ]
  },
  { path: "*", redirect: "/login", hidden: true }
];

export const asyncRoutes = [
  {
    path: "/bookings",
    component: Layout,
    children: [
      {
        path: "",
        name: "Đặt phòng",
        component: () => import("@/views/bookings"),
        meta: { title: "bookings", icon: "el-icon-thumb" },
        authority: "PERM_BOOK_ROOM"
      }
    ]
  },
  {
    path: "/customers",
    component: Layout,
    children: [
      {
        path: "",
        name: "Khách hàng",
        component: () => import("@/views/customer"),
        meta: { title: "customer", icon: "el-icon-s-custom" },
        authority: "PERM_CUSTOMER_READ"
      }
    ]
  },
  {
    path: "/mng",
    component: Layout,
    name: "Quản lý",
    meta: {
      title: "management",
      icon: "el-icon-setting"
    },
    children: [
      {
        path: "/user",
        name: "Nhân viên",
        component: () => import("@/views/user"),
        meta: { title: "user", icon: "el-icon-user" },
        authority: "PERM_USER_READ"
      },
      {
        path: "/room",
        name: "Phòng",
        component: () => import("@/views/room"),
        meta: { title: "room", icon: "el-icon-s-home" },
        authority: "PERM_ROOM_READ"
      },
      {
        path: "/supplies",
        name: "Vật tư",
        component: () => import("@/views/supplies"),
        meta: { title: "supplies", icon: "el-icon-menu" },
        authority: "PERM_SUPPLIES_READ"
      },
      {
        path: "/role",
        name: "Chức năng",
        component: () => import("@/views/role"),
        meta: { title: "role", icon: "el-icon-s-order" },
        authority: "PERM_ROLE_READ"
      },
      {
        path: "/permission",
        name: "Quyền truy cập",
        component: () => import("@/views/permission"),
        meta: { title: "permission", icon: "el-icon-key" },
        authority: "PERM_PERMISSION_READ"
      }
    ]
  }
]
const createRouter = () =>
  new Router({
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRoutes,
    mode: "history"
  });

const router = createRouter();

router.beforeEach((to, from, next) => {
  next();
});

export default router;
