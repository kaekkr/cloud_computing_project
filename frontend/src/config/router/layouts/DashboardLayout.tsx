import { Navigate, Outlet } from "react-router-dom";
import Header from "@/components/widgets/Header/Header";

import styles from "./DashboardLayout.module.css";
import classNames from "classnames";
import { useDispatch, useSelector } from "react-redux";
import { RootState } from "@/config/store/store";
import Sidebar from "@/components/widgets/Sidebar/Sidebar";
import { useEffect } from "react";
import { login } from "@/config/store/authSlice";
import { setHasSubscription } from "@/config/store/generalSlice";

const DashboardLayout = () => {
  const sidebarClosed = useSelector(
    (state: RootState) => state.general.sidebarClosed
  );

  const dispatch = useDispatch();
  const isAuthenticated = useSelector(
    (state: RootState) => state.auth.isAuthenticated
  );

  useEffect(() => {
    const localStorageToken = localStorage.getItem("token");
    const username = localStorage.getItem("username");

    if (localStorageToken && username) {
      const hasSubscription = localStorage.getItem("hasSubscription");
      if (hasSubscription) {
        dispatch(setHasSubscription(true));
      }
      dispatch(login({ username, token: localStorageToken }));
    }
  }, [dispatch]);

  if (!isAuthenticated && !localStorage.getItem("token")) {
    return <Navigate to="/login" replace />;
  }
  return (
    <div className={styles.container}>
      <div
        className={classNames(styles.sidebar, {
          [styles.sidebar_closed]: sidebarClosed,
        })}
      >
        <Sidebar />
      </div>

      <div className={styles.main}>
        <div className={styles.header}>
          <Header />
        </div>
        <div className={styles.content}>
          <Outlet />
        </div>
      </div>
    </div>
  );
};

export default DashboardLayout;
