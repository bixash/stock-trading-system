import NavBar from "@/components/NavBar"
import SideBar from "@/components/SideBar"
import { Outlet } from "react-router-dom"


function AppLayout() {
  return (
    <>
      <NavBar />
      <main className="flex flex-row relative -w--wrapper-width">
        <section className="h-full flex-flex border-r-bg_1">
          <SideBar />
        </section>
        <section className=" -w--right-content-width flex-1 pt-5 pl-5">
          <Outlet />
        </section>
      </main>
    </>
  )
}

export default AppLayout