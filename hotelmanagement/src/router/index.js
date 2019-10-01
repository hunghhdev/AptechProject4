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
    redirect: "/bookings",
    component: Layout,
    children: [
      {
        path: "/bookings",
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
        name: "Loại phòng",
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
        path: "/branch-place",
        name: "Chi nhánh",
        component: () => import("@/views/branchPlace"),
        meta: { title: "branchPlace", icon: "el-icon-map-location" },
        authority: "PERM_BRANCH_PLACE_CREATE"
      },
      {
        path: "/role",
        name: "Chức năng",
        component: () => import("@/views/role"),
        meta: { title: "role", icon: "el-icon-s-order" },
        authority: "PERM_ROLE_READ"
      }
    ]
  },
  { path: "*", redirect: "/login", hidden: true }
];
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
