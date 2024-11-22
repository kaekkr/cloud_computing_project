import HomePage from "@/pages/HomePage";
import { createBrowserRouter } from "react-router-dom";
import ErrorPage from "./ErrorPage";
import AuthPage from "@/pages/AuthPage";
import DashboardPage from "@/pages/DashboardPage";
import ConsultationPage from "@/pages/ConsultationPage";
import DashboardLayout from "./layouts/DashboardLayout";
import RegisterPage from "@/pages/RegisterPage";

const router = createBrowserRouter([
  {
    path: "/",
    errorElement: <ErrorPage />,
    element: <HomePage />,
  },
  {
    path: "/login",
    errorElement: <ErrorPage />,
    element: <AuthPage />,
  },
  {
    path: "/register",
    errorElement: <ErrorPage />,
    element: <RegisterPage />,
  },
  {
    path: "/",
    errorElement: <ErrorPage />,
    element: <DashboardLayout />,
    children: [
      {
        path: "/dashboard",
        errorElement: <ErrorPage />,
        element: <DashboardPage />,
      },
      {
        path: "/consultation",
        errorElement: <ErrorPage />,
        element: <ConsultationPage />,
      },
    ],
  },
]);

export default router;
