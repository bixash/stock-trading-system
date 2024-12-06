import { useAuth } from "@/components/AuthProvider";
import { CardForm } from "@/components/CardForm";
import { LoginForm } from "@/components/LoginForm";
import { useEffect } from "react";

import { useNavigate } from "react-router-dom";

export default function Login() {
  const navigate = useNavigate();
  const { authToken } = useAuth();

  useEffect(() => {
    if (authToken) {
      navigate("/app/dashboard", { replace: true });
    }
  }, [authToken]);

  return (
    <CardForm
      title="Login to TradeExpert"
      description="Don't have an account? Signup Now!"
      content={<LoginForm />}
      handleClick={() => navigate("/signup")}
    />
  );
}
