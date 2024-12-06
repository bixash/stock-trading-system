import { LoginSchema } from "@/types";
import Cookies from "js-cookie";
import api from "../api/auth";
import axios from "axios";

import {
  createContext,
  PropsWithChildren,
  useContext,
  useEffect,
  useState,
} from "react";
import { toast } from "sonner";

type AuthContext = {
  isLoggedIn?: boolean | null;
  authToken?: string | null;
  currentUser?: string | null;
  handleLogin: (loginData: LoginSchema) => Promise<void>;
  handleLogout: () => Promise<void>;
};

const AuthContext = createContext<AuthContext | undefined>(undefined);

type AuthProviderProps = PropsWithChildren;

export default function AuthProvider({ children }: AuthProviderProps) {
  const [authToken, setAuthToken] = useState<string | null>();
  const [currentUser, setCurrentUser] = useState<string | null>();
  const [isLoggedIn, setIsLoggedIn] = useState<boolean | null>();

  useEffect(() => {
    async function fetchUser() {
      try {
        const response = await api.get("/auth/validate");
        if (response.data.success) {
          setAuthToken(response.data.result.token);
          setCurrentUser(response.data.result.client);
          setIsLoggedIn(true);
        } else {
          setCurrentUser(null);
          setAuthToken(null);
          setIsLoggedIn(false);
        }
      } catch (error) {
        if (axios.isAxiosError(error)) {
          if (error.status == undefined) {
            console.log("No internet!");
          }

          // Do something with this error...
        } else {
          console.error(error);
        }
        setAuthToken(null);
        setIsLoggedIn(false);
        setCurrentUser(null);
      }
    }

    fetchUser();
  }, [authToken]);

  async function handleLogin(loginData: LoginSchema) {
    try {
      const response = await api.post("/auth/login", loginData);

      const token = await response.data.result.token;
      const client = await response.data.result.client;

      Cookies.set("token", token);
      setAuthToken(token);
      setIsLoggedIn(true);
      setCurrentUser(client);
    } catch {
      toast.error("Invalid credentials!");
      setCurrentUser(null);
      setAuthToken(null);
      setIsLoggedIn(false);
    }
  }

  async function handleLogout() {
    await api.get("/auth/logout");
    Cookies.remove("token");
    setAuthToken(null);
    setCurrentUser(null);
    setIsLoggedIn(false);
  }

  return (
    <AuthContext.Provider
      value={{
        isLoggedIn,
        authToken,
        currentUser,
        handleLogin,
        handleLogout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export function useAuth() {
  const context = useContext(AuthContext);

  if (context === undefined) {
    throw new Error("useAuth must be used inside of a AuthProvider");
  }

  return context;
}
