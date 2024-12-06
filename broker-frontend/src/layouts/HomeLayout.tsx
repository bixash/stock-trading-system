
import { Outlet } from "react-router-dom";


function HomeLayout() {

    
  return (
    <>
      {/* <NavBar /> */}
      <main className="flex flex-row relative -w--wrapper-width py-3 px-3 h- bg-[#f8f8f8]">
        <Outlet />
      </main>
    </>
  );
}

export default HomeLayout;
