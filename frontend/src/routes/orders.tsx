import api from "@/api/auth";
import { useAuth } from "@/components/AuthProvider";
import { GTTOrder, Order } from "@/types";
import { useEffect, useState } from "react";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuTrigger,
} from "@/components/ui/dropdown-menu";

import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { useNavigate } from "react-router-dom";
import { UseStockInfo } from "@/components/StockInfoProvider";
import { AlignJustify, Ellipsis, Info, LogOut, SquarePen } from "lucide-react";
import { DeleteOrderDialog } from "@/components/DeleteOrderDialog";
import { EditOrderDialog } from "@/components/EditOrderDialog";
import { getStock } from "@/utils";

function orders() {
  const navigate = useNavigate();
  const { stockInfoList } = UseStockInfo();
  const { currentUser } = useAuth();
  const [openOrders, setOpenOrders] = useState<Order[] | []>();
  const [executedOrders, setExecutedOrders] = useState<Order[] | []>();
  const [gttOrders, setGttOrders] = useState<GTTOrder[] | []>();
  const [dialog, setDialog] = useState<string | null>(null);
  const [open, setOpen] = useState<boolean>(false);
  const [orderId, setOrderId] = useState<number | null>(null);
  const [ticker, setTicker] = useState<string>("");

  useEffect(() => {
    async function fetchOpenOrders() {
      try {
        const response = await api.get(
          `/orders?clientId=${currentUser}&status=open`
        );
        setOpenOrders(response.data.result);
      } catch (error) {}
    }
    async function fetchExecutedOrders() {
      try {
        const response = await api.get(
          `/orders?clientId=${currentUser}&status=executed`
        );
        setExecutedOrders(response.data.result);
      } catch (error) {}
    }
    async function fetchGttOrders() {
      try {
        const response = await api.get(`/gtt-orders?clientId=${currentUser}`);
        setGttOrders(response.data.result);
      } catch (error) {}
    }

    fetchOpenOrders();
    fetchExecutedOrders();
    fetchGttOrders();
  }, [stockInfoList]);

  let executedOrdersLength = 0;
  let openOrdersLength = 0;
  let gttOrdersLength = 0;

  if (executedOrders && openOrders && gttOrders) {
    executedOrdersLength = executedOrders?.length;
    openOrdersLength = openOrders?.length;
    gttOrdersLength = gttOrders?.length;
  }

  const handleMenuClick = (
    dialogType: string,
    orderId?: number,
    ticker?: string
  ) => {
    setDialog(dialogType);
    if (orderId) {
      setOrderId(orderId);
    }
    if (ticker) {
      setTicker(ticker);
    }
    setOpen(true);
  };

  return (
    <div className="px-5 -mt-3">
      <Tabs defaultValue="orders">
        <TabsList className="w-full">
          <TabsTrigger value="orders">Orders</TabsTrigger>
          <TabsTrigger value="gtt">GTT</TabsTrigger>
        </TabsList>
        <TabsContent value="orders">
          <div>
            <div className="my-3">
              <div className="py-2">
                <span className="text-[18px] text-bg_3">
                  Open orders ({openOrdersLength})
                </span>
              </div>
              {openOrdersLength > 0 && (
                <Table className="text-bg_3 ">
                  <TableHeader className="text-bg_2 border-y-2 border-y-bg_1">
                    <TableRow>
                      <TableHead className="w-[100px]">Time</TableHead>
                      <TableHead className="w-[100px]">Type</TableHead>
                      <TableHead className="w-[150px]">Instrument</TableHead>
                      <TableHead className="w-[100px]">Product</TableHead>
                      <TableHead className="w-[100px]">Qty.</TableHead>
                      <TableHead className="w-[100px]">LTP</TableHead>
                      <TableHead className="w-[100px]">Price</TableHead>
                      <TableHead className="w-[100px] text-center">
                        Status
                      </TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {openOrders?.map((openOrder) => (
                      <TableRow
                        key={openOrder.orderId}
                        className="group h-10 hover:bg-bg_0 border-y border-y-bg_1"
                      >
                        <TableCell>{openOrder.orderTime}</TableCell>
                        <TableCell className="text-xs">
                          <span
                            className={`rounded px-3 py-1 ${
                              openOrder.transactionType == "buy"
                                ? "text-text_4 bg-bg_4"
                                : "text-text_10 bg-bg_5"
                            }`}
                          >
                            {openOrder.transactionType.toUpperCase()}
                          </span>
                        </TableCell>
                        <TableCell className="hover:cursor-pointer">
                          <div className="flex gap-8">
                            <span className="w-24">{openOrder.ticker}</span>
                            <DropdownMenu>
                              <DropdownMenuTrigger className="outline-none">
                                <div className="w-20">
                                  <Ellipsis className="hidden text-text_4 border border-text_4 rounded w-6 h-5 group-hover:block" />
                                </div>
                              </DropdownMenuTrigger>
                              <DropdownMenuContent className="bg-white border-t-[3px] border-bg_1 text-bg_3">
                                <DropdownMenuItem
                                  onClick={() =>
                                    handleMenuClick(
                                      "exit",
                                      openOrder.orderId,
                                      openOrder.ticker
                                    )
                                  }
                                >
                                  <LogOut />
                                  <span>Exit</span>
                                </DropdownMenuItem>

                                <DropdownMenuItem
                                  onClick={() =>
                                    handleMenuClick("modify", openOrder.orderId)
                                  }
                                >
                                  <SquarePen />
                                  <span>Modify</span>
                                </DropdownMenuItem>

                                <DropdownMenuItem>
                                  <Info />
                                  <span>Info</span>
                                </DropdownMenuItem>
                                <DropdownMenuItem
                                  onClick={() =>
                                    navigate(`/app/market/${openOrder.ticker}`)
                                  }
                                >
                                  {" "}
                                  <AlignJustify />
                                  <span>Market depth</span>
                                </DropdownMenuItem>
                              </DropdownMenuContent>
                            </DropdownMenu>
                            {dialog === "exit" && orderId && ticker && (
                              <DeleteOrderDialog
                                orderId={orderId}
                                ticker={ticker}
                                open={open}
                                setOpen={setOpen}
                              />
                            )}{" "}
                            {dialog === "modify" && orderId && (
                              <EditOrderDialog
                                orderId={orderId}
                                open={open}
                                setOpen={setOpen}
                              />
                            )}
                          </div>
                        </TableCell>
                        <TableCell>
                          {openOrder.productType.toUpperCase()}
                        </TableCell>
                        <TableCell>
                          {openOrder.tradedQuantity} / {openOrder.orderQuantity}
                        </TableCell>
                        <TableCell>
                          {
                            getStock(stockInfoList!, openOrder.ticker)
                              ?.lastTradedPrice
                          }
                        </TableCell>
                        <TableCell>
                          {openOrder.orderType == "market"
                            ? "Market"
                            : openOrder.price}
                        </TableCell>
                        <TableCell className="text-xs">
                          <span className="rounded px-5 py-1 bg-[#ececec]">
                            {openOrder.status.toUpperCase()}
                          </span>
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              )}
            </div>
            <div className="my-3">
              <div className="py-2">
                <span className="text-[18px] text-bg_3">
                  Executed orders ({executedOrdersLength})
                </span>
              </div>

              {executedOrdersLength > 0 && (
                <Table className="text-bg_3">
                  <TableHeader className="text-bg_2 border-y-2 border-y-bg_1">
                    <TableRow>
                      <TableHead className="w-[100px]">Time</TableHead>
                      <TableHead className="w-[100px]">Type</TableHead>
                      <TableHead className="w-[200px]">Instrument</TableHead>
                      <TableHead className="w-[100px]">Product</TableHead>
                      <TableHead className="w-[100px]">Qty.</TableHead>
                      <TableHead className="w-[100px]">Avg. Price</TableHead>
                      <TableHead className="w-[100px] text-center">
                        Status
                      </TableHead>
                    </TableRow>
                  </TableHeader>
                  <TableBody>
                    {executedOrders?.map((executedOrder) => (
                      <TableRow
                        key={executedOrder.orderId}
                        className="h-10 hover:bg-bg_0 border-y border-y-bg_1"
                      >
                        <TableCell>{executedOrder.orderTime}</TableCell>
                        <TableCell className="text-xs">
                          <span
                            className={`rounded px-3 py-1 ${
                              executedOrder.transactionType == "buy"
                                ? "text-text_4 bg-bg_4"
                                : "text-text_10 bg-bg_5"
                            }`}
                          >
                            {executedOrder.transactionType.toUpperCase()}
                          </span>
                        </TableCell>
                        <TableCell
                          onClick={() =>
                            navigate(`/app/market/${executedOrder.ticker}`)
                          }
                        >
                          {executedOrder.ticker}
                        </TableCell>
                        <TableCell>
                          {executedOrder.productType.toUpperCase()}
                        </TableCell>
                        <TableCell>
                          {executedOrder.tradedQuantity} /{" "}
                          {executedOrder.orderQuantity}
                        </TableCell>
                        <TableCell>{executedOrder.price}</TableCell>
                        <TableCell className="text-xs">
                          <span
                            className={`rounded px-3 py-1 ${
                              executedOrder.status == "complete"
                                ? "text-text_8 bg-bg_10"
                                : "text-text_10 bg-bg_5"
                            }`}
                          >
                            {executedOrder.status.toUpperCase()}
                          </span>
                        </TableCell>
                      </TableRow>
                    ))}
                  </TableBody>
                </Table>
              )}
            </div>
          </div>
        </TabsContent>
        <TabsContent value="gtt">
          <div className="my-3">
            <div className="py-2">
              <span className="text-[18px] text-bg_3">
                GTT orders ({gttOrdersLength})
              </span>
            </div>
            {gttOrdersLength > 0 && (
              <Table className="text-bg_3">
                <TableHeader className="text-bg_2 border-y-2 border-y-bg_1">
                  <TableRow>
                    <TableHead className="w-[100px]">Created on</TableHead>
                    <TableHead className="w-[200px]">Instrument</TableHead>
                    <TableHead className="w-[150px]">Type</TableHead>
                    <TableHead className="w-[100px]">Trigger</TableHead>
                    <TableHead className="w-[100px]">Price</TableHead>
                    <TableHead className="w-[100px]">Quantity</TableHead>
                    <TableHead className="w-[100px] text-center">
                      Status
                    </TableHead>
                  </TableRow>
                </TableHeader>
                <TableBody>
                  {gttOrders?.map((gttOrder) => (
                    <TableRow
                      key={gttOrder.id}
                      className="h-10 hover:bg-bg_0 border-b border-b-bg_1"
                    >
                      <TableCell>{gttOrder.orderDate}</TableCell>
                      <TableCell
                        className="hover:pointer-events-auto"
                        onClick={() =>
                          navigate(`/app/market/${gttOrder.ticker}`)
                        }
                      >
                        {gttOrder.ticker}
                      </TableCell>
                      <TableCell className="text-xs ">
                        <div className="flex items-center gap-1 m-0">
                          <span className="rounded px-4 py-1 bg-[#ececec]">
                            {gttOrder.triggerType.toUpperCase()}
                          </span>
                          <span
                            className={`rounded px-3 py-1 ${
                              gttOrder.transactionType == "buy"
                                ? "text-text_4 bg-bg_4"
                                : "text-text_10 bg-bg_5"
                            }`}
                          >
                            {gttOrder.transactionType.toUpperCase()}
                          </span>
                        </div>
                      </TableCell>
                      <TableCell>{gttOrder.triggerPrice}</TableCell>
                      <TableCell>{gttOrder.price}</TableCell>
                      <TableCell>{gttOrder.orderQuantity}</TableCell>
                      <TableCell className="text-xs">
                        <span
                          className={`rounded px-3 py-1 ${
                            gttOrder.status == "active"
                              ? "text-text_8 bg-bg_10"
                              : "text-text_4 bg-bg_4"
                          }`}
                        >
                          {gttOrder.status.toUpperCase()}
                        </span>
                      </TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            )}
          </div>
        </TabsContent>
      </Tabs>
    </div>
  );
}

export default orders;
