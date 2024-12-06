import * as React from "react";
import * as ReactDOM from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import "./index.css";
import { Toaster } from "@/components/ui/sonner";
import App from "./layouts/AppLayout";
import Root from "./layouts/HomeLayout";
// import Home from "./routes/home";
import Order from "./routes/orders";
import Dashboard from "./routes/dashboard";
import Positions from "./routes/positions";
import Holdings from "./routes/holdings";
import Funds from "./routes/funds";
import Login from "./routes/login";
import Signup from "./routes/signup";
import Bids from "./routes/bids";
import Market from "./routes/market";

import ErrorPage from "./error-page";
import AuthProvider from "./components/AuthProvider";
import ProtectedRoute from "./components/ProtectedRoute";
import StockInfoProvider from "./components/StockInfoProvider";
import MarketInfoProvider from "./components/MarketInfoProvider";

const router = createBrowserRouter([
  {
    path: "/",
    element: <Root />,
    errorElement: <ErrorPage />,
    children: [
      {
        errorElement: <ErrorPage />,
        children: [
          { index: true, element: <Login /> },
          {
            path: "login",
            element: <Login />,
          },
          {
            path: "signup",
            element: <Signup />,
          },
        ],
      },
    ],
  },
  {
    path: "app",
    element: (
      <ProtectedRoute>
        <App />
      </ProtectedRoute>
    ),
    errorElement: <ErrorPage />,
    children: [
      { index: true, element: <Holdings /> },
      {
        path: "dashboard",
        element: <Dashboard />,
      },
      {
        path: "holdings",
        element: <Holdings />,
      },

      {
        path: "orders",
        element: <Order />,
      },
      {
        path: "positions",
        element: <Positions />,
      },
      {
        path: "funds",
        element: <Funds />,
      },
      {
        path: "bids",
        element: <Bids />,
      },
      {
        path: "market/:ticker",
        element: (
          <MarketInfoProvider>
            <Market />
          </MarketInfoProvider>
        ),
      },
    ],
  },
]);

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <AuthProvider>
      <StockInfoProvider>
        <RouterProvider router={router} />
        <Toaster position="top-right" richColors />
      </StockInfoProvider>
    </AuthProvider>
  </React.StrictMode>
);
