import { PropsWithChildren } from "react";
import { useAuth } from "./AuthProvider";
import { Navigate } from "react-router-dom";

import { Loading } from "./Loading";

type ProtectedRouteProps = PropsWithChildren;

export default function ProtectedRoute({ children }: ProtectedRouteProps) {
  const { authToken } = useAuth();

  if (authToken === null) {
    return <Navigate to="/login" />;
  }
  if (authToken === undefined) {
    return <Loading />;
  }

  // if (!isValidateToken) {
  //   return <Navigate to="/login" />;
  // }

  return children;
}
