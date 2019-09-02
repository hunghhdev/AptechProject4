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
        path: "/branch-place",
        name: "Chi nhánh",
        component: () => import("@/views/branchPlace"),
        meta: { title: "branchPlace", icon: "el-icon-s-home" }
      },
      {
        path: "/department",
        name: "Phòng ban",
        component: () => import("@/views/department"),
        meta: { title: "department", icon: "el-icon-s-cooperation" }
      },
      {
        path: "",
        name: "Nhân viên",
        component: () => import("@/views/user"),
        meta: { title: "user", icon: "el-icon-user" }
      },
      {
        path: "/role",
        name: "Phân quyền",
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
