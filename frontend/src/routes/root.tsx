import { Outlet } from "react-router-dom";

import NavBar from "@/components/NavBar";

import { useAuth } from "@/components/AuthProvider";

export default function root() {
  const { authToken } = useAuth();

  if (authToken === null) {
    return (
      <>
        <NavBar />
        <main className="flex flex-row relative -w--wrapper-width py-3 px-3">
          <Outlet />
        </main>
      </>
    );
  }
}
