import { Info, LogOut, User } from "lucide-react";
import styles from "./Sidebar.module.css";
import classNames from "classnames";
import { useSelector } from "react-redux";
import { RootState } from "@/config/store/store";
import { NavLink } from "react-router-dom";
import { userLinks } from "./links";

const Sidebar = () => {
  const sidebarClosed = useSelector(
    (state: RootState) => state.general.sidebarClosed
  );

  return (
    <div
      className={classNames(styles.sidebarContainer, {
        [styles.sidebar_closed]: sidebarClosed,
      })}
    >
      <div className={styles.header}>
        <div className={styles.name}>
          <span className={styles.title}>VHA</span>
          <span className={styles.secondary_title}>
            Virtual Healthcare Assistant
          </span>
        </div>
      </div>

      <div className={styles.menu}>
        <div className={styles.mainMenu}>
          {userLinks.map(({ to, label, icon: Icon }, index) => (
            <NavLink
              key={index}
              to={to}
              end
              className={({ isActive }) =>
                classNames(styles.menuItem, { [styles.active]: isActive })
              }
            >
              <Icon className={styles.menuIcon} size={22} />
              <span className={styles.menu_text}>{label}</span>
            </NavLink>
          ))}
        </div>
        <div className={styles.footerMenu}>
          <div className={classNames(styles.menuItem)}>
            <User className={styles.menuIcon} size={18} />
            <span className={styles.menu_text}>Profile</span>
          </div>
          <div className={classNames(styles.menuItem)}>
            <Info className={styles.menuIcon} size={18} />
            <span className={styles.menu_text}>Help</span>
          </div>
          <div className={classNames(styles.menuItem, "hover:bg-destructive")}>
            <LogOut className={styles.menuIcon} size={18} />
            <span className={styles.menu_text}>Logout</span>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Sidebar;
