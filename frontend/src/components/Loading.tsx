import { headerLogo } from "@/assets/images";

export function Loading() {
  return (
    <div className="w-full h-screen ">
      <img
        src={headerLogo}
        alt="logo"
        className="w-[200px] mt-40 mx-auto"
      />
    </div>
  );
}
