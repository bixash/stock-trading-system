import { useState } from "react";
import { hamburger } from "../assets/icons";
import { headerLogo } from "../assets/images";
import { appNavLinks, homeNavLinks } from "../constants";
import { NavLink, useNavigate } from "react-router-dom";
import { useAuth } from "./AuthProvider";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuLabel,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";
import { LogOut } from "lucide-react";

function Nav() {
  const { authToken, currentUser } = useAuth();
  const [menuVisibility, setMenuVisibility] = useState(false);
  const { handleLogout } = useAuth();

  let navigate = useNavigate();

  function logout() {
    handleLogout();
    navigate("/login");
  }

  function handleMenuVisibility() {
    if (menuVisibility) {
      setMenuVisibility(false);
    } else {
      setMenuVisibility(true);
    }
  }
  return (
    <>
      <header className="z-10 w-full shadow-md  bg-primary text-sm ">
        <nav className="flex justify-between items-center max-container ">
          <div className="header-left w-[425px] py-3 px-3 border-r-2 border-r-bg_1 ">
            <div className="pinned-instruments flex flex-row justify-between mr-3">
              <div className="instrument-widget">
                <span
                  tooltip-pos="down"
                  className="tradingsymbol link-chart"
                  data-balloon="Open chart"
                  data-balloon-pos="down"
                >
                  <span>NEPSE</span>
                </span>{" "}
                <span className="wrap">
                  <span className="last-price text-text_8 font-semibold">
                    2439.30
                  </span>{" "}
                  <span className="price-change dim">
                    <span className="all dim text-xs text-bg_2 font-semibold">
                      -14.50 (-0.59<span className="text-xxsmall">%</span>)
                    </span>
                  </span>
                </span>
              </div>
              <div className="instrument-widget">
                <span
                  tooltip-pos="down"
                  className="tradingsymbol link-chart"
                  data-balloon="Open chart"
                  data-balloon-pos="down"
                >
                  <span>SENSEX</span>
                </span>{" "}
                <span className="wrap">
                  <span className="last-price text-text_8 font-semibold">
                    1439.30
                  </span>{" "}
                  <span className="price-change dim">
                    <span className="all dim text-xs text-bg_2 font-semibold">
                      -2.50 (-0.29<span className="text-xxsmall">%</span>)
                    </span>
                  </span>
                </span>
              </div>
            </div>
          </div>
          <div className="header-right py-2 px-3 -w--right-content-width flex flex-row justify-between items-center max-container">
            <div>
              <NavLink to="/">
                <img
                  src={headerLogo}
                  alt="logo"
                  width={25}
                  height={25}
                  className="m-0 w-[100px]"
                />
              </NavLink>
            </div>

            {authToken ? (
              <div className="flex flex-row justify-between items-center ">
                <div className="w-[580px]">
                  <ul className="flex-1 flex justify-center items-center gap-10 max-lg:hidden">
                    {appNavLinks.map((item) => (
                      <li key={item.label}>
                        <NavLink
                          to={item.href}
                          className={({ isActive }) =>
                            isActive
                              ? "text-text_5 font-semibold"
                              : "hover:text-text_5"
                          }
                        >
                          {item.label}
                        </NavLink>
                      </li>
                    ))}
                  </ul>
                </div>
                <DropdownMenu>
                  <DropdownMenuTrigger className="outline-none">
                    <div className="flex items-center justify-between w-fit mr-8 outline-none">
                      <Avatar>
                        <AvatarImage
                          src="https://github.com/shadcn.png"
                          alt="@shadcn"
                        />
                        <AvatarFallback>
                          {currentUser!.slice(0, 2)}
                        </AvatarFallback>
                      </Avatar>
                      <div className="ml-1 hover:text-text_5">
                        {currentUser}
                      </div>
                    </div>
                  </DropdownMenuTrigger>
                  <DropdownMenuContent className="bg-white border-t-[3px] border-bg_1 text-bg_3">
                    <DropdownMenuLabel>My Account</DropdownMenuLabel>
                    <DropdownMenuSeparator className="bg-bg_1" />
                    <DropdownMenuItem>Profile</DropdownMenuItem>
                    <DropdownMenuItem>Billing</DropdownMenuItem>
                    <DropdownMenuItem>Settings</DropdownMenuItem>
                    <DropdownMenuItem onClick={logout}>
                      <LogOut />
                      <span className="hover:text-text_5">Logout</span>
                    </DropdownMenuItem>
                  </DropdownMenuContent>
                </DropdownMenu>
              </div>
            ) : (
              <div className="flex flex-row justify-center items-center gap-16">
                <div>
                  <ul className="flex-1 flex justify-center items-center gap-16 max-lg:hidden">
                    {homeNavLinks.map((item) => (
                      <li key={item.label}>
                        <NavLink
                          to={item.href}
                          className={({ isActive }) =>
                            isActive
                              ? "text-text_5 font-semibold"
                              : "hover:text-text_5"
                          }
                        >
                          {item.label}
                        </NavLink>
                      </li>
                    ))}
                  </ul>
                </div>
                <div className="flex gap-2 text-sm leading-normal font-medium text-white max-lg:hidden bg-orange-500 rounded-[5px] py-2 px-3 mr-6">
                  <a href="/signup">Explore now</a>
                </div>
              </div>
            )}

            <div
              className="hidden max-lg:block z-30"
              onClick={handleMenuVisibility}
            >
              <img
                src={hamburger}
                alt="hamburger icon"
                width={25}
                height={25}
              />
            </div>
            {menuVisibility && (
              <div className="hidden max-lg:block fixed w-20 h-60 right-0 top-8 mr-10 ">
                <strong>Holy guacamole!</strong> You should check in on some of
                those fields below.
              </div>
            )}
          </div>
        </nav>
      </header>
    </>
  );
}

export default Nav;
