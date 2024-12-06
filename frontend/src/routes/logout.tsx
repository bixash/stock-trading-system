import { useAuth } from "@/components/AuthProvider";


import { useNavigate } from "react-router-dom";

export default function Logout() {
  const { handleLogout } = useAuth();

  let navigate = useNavigate();

  function logout() {
    handleLogout();
    navigate("/login");
  }

  return (
    <>
      <div>logout</div>
      <button onClick={logout} className="w-fit rounded bg-red-500 text-white">
        Logout
      </button>
    </>
  );
}
