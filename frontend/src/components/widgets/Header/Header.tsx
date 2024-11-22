import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import styles from "./Header.module.css";
import { Link } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { AppDispatch, RootState } from "@/config/store/store";
import { Menu } from "lucide-react";
import { toggleSidebar } from "@/config/store/generalSlice";
import { Button } from "@/components/ui/button";

import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { logout } from "@/config/store/authSlice";

const Header = () => {
  const dispatch: AppDispatch = useDispatch();
  const isAuthenticated = useSelector(
    (state: RootState) => state.auth.isAuthenticated
  );
  const username = useSelector((state: RootState) => state.auth.username) || "";
  const pageTitle = useSelector((state: RootState) => state.general.pageTitle);

  return (
    <>
      <div className={styles.left}>
        <Button onClick={() => dispatch(toggleSidebar())} variant="outline">
          <Menu />
        </Button>
        <div className="ml-2 flex gap-2 items-center">
          <h1 className="font-semibold text-xl">{pageTitle}</h1>
        </div>
      </div>
      <div className={styles.right}>
        {isAuthenticated ? (
          <div className={styles.profile}>
            <span className={styles.username}>{username}</span>

            <DropdownMenu>
              <DropdownMenuTrigger asChild>
                <Avatar className="hover:cursor-pointer">
                  <AvatarImage src="" />
                  <AvatarFallback>AA</AvatarFallback>
                </Avatar>
              </DropdownMenuTrigger>
              <DropdownMenuContent className="mr-4">
                <DropdownMenuLabel>{username}</DropdownMenuLabel>
                <DropdownMenuSeparator />
                <DropdownMenuItem>Profile</DropdownMenuItem>
                <DropdownMenuItem
                  className="cursor-pointer"
                  onClick={() => {
                    dispatch(logout());
                  }}
                >
                  Log out
                </DropdownMenuItem>
              </DropdownMenuContent>
            </DropdownMenu>
          </div>
        ) : (
          <Button>
            <Link to={"/login"}>Login</Link>
          </Button>
        )}
      </div>
    </>
  );
};

export default Header;
