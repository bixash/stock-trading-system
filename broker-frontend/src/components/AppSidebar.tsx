import { Calendar, Home, Inbox, Search, Settings } from "lucide-react";

import {
  Sidebar,
  SidebarContent,
  SidebarGroup,
  SidebarGroupContent,
  SidebarMenu,
  SidebarGroupLabel,
  SidebarMenuButton,
  SidebarMenuItem,
} from "@/components/ui/sidebar";
import { Input } from "@/components/ui/input";
import { Separator } from "@/components/ui/separator";

// Menu items.
const items = [
  {
    title: "NABIL",
    url: "#",
    icon: Home,
  },
  {
    title: "NICA",
    url: "#",
    icon: Inbox,
  },
  {
    title: "SANIMA",
    url: "#",
    icon: Calendar,
  },
  {
    title: "HDL",
    url: "#",
    icon: Search,
  },
  {
    title: "ADBL",
    url: "#",
    icon: Settings,
  },
];

const showMarketWatch = (title: string) => {
  console.log(title);
};

export function AppSidebar() {
  return (
    <Sidebar className="bg-white" collapsible="none" >
      <SidebarContent>
        <SidebarGroup>
          <SidebarGroupLabel>
            {/* <div className="flex justify-center items-center"> */}
            <Search />
            <Input
              className="rounded h-5 outline-none border-none"
              placeholder="Search for scrip"
            />
            {/* </div> */}
          </SidebarGroupLabel>
          <Separator />

          <SidebarGroupContent>
            <SidebarMenu>
              {items.map((item) => (
                <SidebarMenuItem key={item.title}>
                  <SidebarMenuButton asChild>
                    <span onClick={() => showMarketWatch(item.title)}>
                      {item.title}
                    </span>
                  </SidebarMenuButton>
                  <Separator />
                </SidebarMenuItem>
              ))}
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>
      </SidebarContent>
    </Sidebar>
  );
}
