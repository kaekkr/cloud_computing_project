import { Outlet } from "react-router-dom";

const ProtectedRoute = () => {
  //   const dispatch = useDispatch();
  // const token = useSelector((state: RootState) => state.auth.token);

  //   useEffect(() => {
  //     const localStorageToken = localStorage.getItem("token");
  //     const username = localStorage.getItem("username");
  //     const role = localStorage.getItem("userRole");

  //     if (localStorageToken && username && role) {
  //       console.log("dispatching");
  //       dispatch(login({ username, token: localStorageToken, role }));
  //     }
  //   }, [dispatch]);

  //   if (!token && !localStorage.getItem("token")) {
  //     return <Navigate to="/login" replace />;
  //   }
  return <Outlet />;
};

export default ProtectedRoute;
