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
    redirect: "/user",
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
        meta: { title: "user", icon: "el-icon-user" }
      },
      {
        path: "/room",
        name: "Loại phòng",
        component: () => import("@/views/room"),
        meta: { title: "room", icon: "el-icon-s-home" }
      },
      {
        path: "/supplies",
        name: "Vật tư",
        component: () => import("@/views/supplies"),
        meta: { title: "supplies", icon: "el-icon-menu" }
      },
      {
        path: "/branch-place",
        name: "Chi nhánh",
        component: () => import("@/views/branchPlace"),
        meta: { title: "branchPlace", icon: "el-icon-map-location" }
      },
      {
        path: "/role",
        name: "Chức năng",
        component: () => import("@/views/role"),
        meta: { title: "role", icon: "el-icon-s-order" }
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

export function resetRouter() {
  const newRouter = createRouter();
  router.matcher = newRouter.matcher;
}

export default router;
