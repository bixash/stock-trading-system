import {
  Card,
  CardContent,
  CardFooter,
  CardHeader,
} from "@/components/ui/card";
import {
  Pagination,
  PaginationContent,
  PaginationEllipsis,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from "@/components/ui/pagination";

import { Input } from "@/components/ui/input";
import { Separator } from "@/components/ui/separator";

import { Search, ChevronDown, ChevronUp } from "lucide-react";
import { useNavigate } from "react-router-dom";
import { UseStockInfo } from "./StockInfoProvider";
import { useAuth } from "./AuthProvider";
import { useEffect, useState } from "react";
import api from "../api/auth";
import { Stock } from "@/types";


function SideBar() {
  const navigate = useNavigate();
  const [watchlist, setWatchlist] = useState<String[] | undefined>();
  const { stockInfoList, loading } = UseStockInfo();
  const { currentUser } = useAuth();
  const marketWatchList: Stock[] = [];

  function splitStringArray(arr: string[]) {
    if (arr.length === 0) return [];
    return arr[0].split(",");
  }

  useEffect(() => {
    async function fetchWatchList() {
      try {
        const response = await api.get(`/watchlist?clientId=${currentUser}`);
        setWatchlist(splitStringArray(response.data));
      } catch (error) {}
    }
    fetchWatchList();
  }, []);

  if (stockInfoList) {
    for (let stock of stockInfoList) {
      if (watchlist) {
        if (watchlist?.includes(stock.ticker)) {
          marketWatchList.unshift(stock);
        }
      }
    }
  }
  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <>
      <div className="fixed h-[595.333px] shadow-md bg-primary ">
        <Card className="border-none w-[415px] ">
          <CardHeader className="flex flex-row sticky top-0 m-0 p-0 text-bg_2 text-xs">
            <Search className="mt-5 ml-2 h-3" />
            <Input
              id="search"
              className=" outline-none border-none px-1 py-0 m-0 text-s"
              placeholder="Search for scrip"
            />
            <span className="p-3 text-sm">20/50</span>
          </CardHeader>

          <CardContent className="h-[440.333px] overflow-auto p-0">
            <div className="w-full">
              {marketWatchList?.map((stock) => (
                <div
                  key={stock.ticker}
                  onClick={() => navigate(`market/${stock.ticker}`)}
                >
                  <Separator className="h-[0.5px] bg-[#efefef]" />
                  <div className="flex flex-row justify-between p-3 hover:bg-bg_1 text-s ">
                    <div className="symbol-wrapper">
                      <div className="symbol">
                        <span
                          className={`text-s ${
                            stock.differenceRs >= 0
                              ? "text-text_8"
                              : "text-text_10"
                          }`}
                        >
                          {stock.ticker}
                        </span>{" "}
                        <span className="text-xxs text-bg_2">NEPSE</span>{" "}
                      </div>{" "}
                    </div>{" "}
                    <div className="flex flex-row w-[160px] gap-3">
                      <div className="price-change flex flex-row justify-between w-[80px]">
                        <span className="text-bg_2 inline-block">
                          {stock.differenceRs.toFixed(2)}
                        </span>{" "}
                        <span className="text-bg_3">
                          {stock.percentChange.toFixed(2)}
                          <span className="text-xs">%</span>
                        </span>
                      </div>{" "}
                      <div className="price w-[80px] flex flex-row">
                    
                          {stock.differenceRs >= 0 ? (
                            <span className="text-text_8">
                              <ChevronUp className="h-5" strokeWidth="1"/>
                            </span>
                          ) : (
                            <span className="text-text_10">
                              <ChevronDown className="h-5" strokeWidth="1" />
                            </span>
                          )}
                    
                        <span
                          className={`text-s w-full ${
                            stock.differenceRs >= 0
                              ? "text-text_8"
                              : "text-text_10"
                          }`}
                        >
                          {stock.lastTradedPrice.toFixed(2)}
                        </span>
                      </div>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          </CardContent>
          <CardFooter className="fixed bottom-0 p-0 py-1 w-[415px] border-t-[1px] border-bg_1 bg-white">
            <Pagination>
              <PaginationContent className="text-bg_2">
                <PaginationItem>
                  <PaginationPrevious href="#" />
                </PaginationItem>
                <PaginationItem>
                  <PaginationLink href="#" isActive className="text-text_5">
                    1
                  </PaginationLink>
                </PaginationItem>
                <PaginationItem>
                  <PaginationLink href="#">2</PaginationLink>
                </PaginationItem>
                <PaginationItem>
                  <PaginationLink href="#">3</PaginationLink>
                </PaginationItem>
                <PaginationItem>
                  <PaginationLink href="#">4</PaginationLink>
                </PaginationItem>
                <PaginationItem>
                  <PaginationLink href="#">5</PaginationLink>
                </PaginationItem>
                <PaginationItem>
                  <PaginationEllipsis />
                </PaginationItem>
                <PaginationItem>
                  <PaginationNext href="#" />
                </PaginationItem>
              </PaginationContent>
            </Pagination>
          </CardFooter>
        </Card>
      </div>
    </>
  );
}

export default SideBar;
