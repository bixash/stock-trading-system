import { useParams } from "react-router-dom";
import {
  Table,
  TableBody,
  TableCell,
  TableFooter,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Button } from "@/components/ui/button";
import { OrderDialog } from "@/components/OrderDialog";
import { GttOrderDialog } from "@/components/GttOrderDialog";
import { UseStockInfo } from "@/components/StockInfoProvider";
import { calculateTotalQuantity, getFixedOrders, getStock } from "@/utils";
import { UseMarketInfo } from "@/components/MarketInfoProvider";

function Market() {
  const { ticker } = useParams<{ ticker: string }>();
  const { marketInfo, loading } = UseMarketInfo();
  const { stockInfoList } = UseStockInfo();

  let bids = getFixedOrders(marketInfo?.marketDepth?.bids || []);
  let offers = getFixedOrders(marketInfo?.marketDepth?.offers || []);
  let totalBidsQuantity = calculateTotalQuantity(
    marketInfo?.marketDepth?.bids || []
  );
  let totalOffersQuantity = calculateTotalQuantity(
    marketInfo?.marketDepth?.offers || []
  );

  if (loading) {
    return <div>Loading</div>;
  }
  if (!stockInfoList) {
    return <div>Loading</div>;
  }

  if (marketInfo === null || marketInfo === undefined) {
    return <div>No market information available</div>;
  }

  const stock = getStock(stockInfoList, ticker!);
  if (stock)
    return (
      <div className="w-[750px]">
        <div className="flex flex-col ml-5 h-[400px]">
          <div className="modal-header px-3 ">
            <div className="row market-depth-widget-header">
              <div className="instrument-info-header flex justify-between">
                <div className="tradingsymbol-wrapper">
                  <h3 className="tradingsymbol">
                    <span className="text-bg_3">{stock.ticker}</span>
                    <span className="text-xs px-1 text-bg_2">NEPSE</span>
                  </h3>
                </div>
                <div className="instrument-info ">
                  <span className="down flex flex-row gap-2 items-center">
                    <span className="last-price text-bg_3">
                      {stock.lastTradedPrice.toFixed(2)}
                    </span>
                    <span
                      className={`price-change text-sm flex flex-row gap-1 items-center ${
                        stock.differenceRs >= 0 ? "text-text_8" : "text-text_10"
                      }`}
                    >
                      <span>{stock.differenceRs.toFixed(2)}</span>
                      <span>({stock.percentChange.toFixed(2)}%)</span>
                    </span>{" "}
                  </span>
                </div>
              </div>
            </div>
          </div>
          <div className="modal-body p-3">
            <div className="market-depth">
              <div className="depth-table border-y border-y-bg_1">
                <div className="flex w-full justify-between">
                  <Table className="text-text_4 border-r-2 border-r-bg_1 ">
                    <TableHeader className="text-bg_2 border-y-2 border-y-bg_1">
                      <TableRow>
                        <TableHead className="w-[130px]">Bid</TableHead>
                        <TableHead className="w-[130px]">Orders</TableHead>
                        <TableHead className="w-[130px]">Qty.</TableHead>
                      </TableRow>
                    </TableHeader>
                    <TableBody>
                      {Array.from({ length: 5 }, (_, index) => (
                        <TableRow key={index}>
                          <TableCell>{bids[index].price.toFixed(2)}</TableCell>
                          <TableCell>{bids[index].orders}</TableCell>
                          <TableCell>{bids[index].quantity}</TableCell>
                        </TableRow>
                      ))}
                    </TableBody>
                    <TableFooter className="border-y-2 border-y-bg_1">
                      <TableRow>
                        <TableCell colSpan={2}>Total</TableCell>
                        <TableCell>{totalBidsQuantity}</TableCell>
                      </TableRow>
                    </TableFooter>
                  </Table>

                  <Table className=" text-text_5 border-l-2 border-l-bg_1">
                    <TableHeader className="text-bg_2 border-y-2 border-y-bg_1">
                      <TableRow>
                        <TableHead className="w-[130px]">Offer</TableHead>
                        <TableHead className="w-[130px]">Orders</TableHead>
                        <TableHead className="w-[130px]">Qty.</TableHead>
                      </TableRow>
                    </TableHeader>
                    <TableBody>
                      {Array.from({ length: 5 }, (_, index) => (
                        <TableRow key={index}>
                          <TableCell>
                            {offers[index].price.toFixed(2)}
                          </TableCell>
                          <TableCell>{offers[index].orders}</TableCell>
                          <TableCell>{offers[index].quantity}</TableCell>
                        </TableRow>
                      ))}
                    </TableBody>
                    <TableFooter className="border-y-2 border-y-bg_1">
                      <TableRow>
                        <TableCell colSpan={2}>Total</TableCell>
                        <TableCell>{totalOffersQuantity}</TableCell>
                      </TableRow>
                    </TableFooter>
                  </Table>
                </div>
              </div>
              <div className="pt-3">
                <div className="row flex flex-row justify-between text-s">
                  <div className="left-side">
                    <div className="space-x-2 w-[250px] flex justify-between">
                      <span className="text-bg_2 inline-block ">Open</span>
                      <span className="text-bg_3 inline-block w-1\2">
                        {stock.openingPrice.toFixed(2)}
                      </span>
                    </div>

                    <div className="space-x-2 w-[250px] flex justify-between ">
                      <span className="text-bg_2 inline-block ">Low</span>
                      <span className="text-bg_3 inline-block w-1\2">
                        {stock.minPrice.toFixed(2)}
                      </span>
                    </div>
                    <div className="space-x-2 w-[250px] flex justify-between ">
                      <span className="text-bg_2 inline-block ">Volume</span>
                      <span className="text-bg_3 inline-block w-1\2">
                        {stock.totalVolume}
                      </span>
                    </div>

                    <div className="space-x-2 w-[250px] flex justify-between ">
                      <span className="text-bg_2 inline-block ">LTQ</span>
                      <span className="text-bg_3 inline-block w-1\2">
                        {stock.lastTradedVolume}
                      </span>
                    </div>
                    <div className="space-x-2 w-[250px] flex justify-between ">
                      <span className="text-bg_2 inline-block ">
                        Lower Circuit
                      </span>
                      <span className="text-bg_3 inline-block w-1\2">
                        {marketInfo?.circuit?.lower.toFixed(2)}
                      </span>
                    </div>
                  </div>

                  <div className="right-side">
                    <div className="space-x-2 w-[250px] flex justify-between">
                      <span className="text-bg_2 inline-block ">High</span>
                      <span className="text-bg_3 inline-block w-1\2">
                        {stock?.maxPrice.toFixed(2)}
                      </span>
                    </div>
                    <div className="space-x-2 w-[250px] flex justify-between">
                      <span className="text-bg_2 inline-block ">
                        Prev. Close
                      </span>
                      <span className="text-bg_3 inline-block w-1\2">
                        {stock?.previousClosing.toFixed(2)}
                      </span>
                    </div>
                    <div className="space-x-2 w-[250px] flex justify-between">
                      <span className="text-bg_2 inline-block ">
                        Avg. Price
                      </span>
                      <span className="text-bg_3 inline-block w-1\2">
                        {stock?.lastTradedPrice.toFixed(2)}
                      </span>
                    </div>
                    <div className="space-x-2 w-[250px] flex justify-between">
                      <span className="text-bg_2 inline-block ">LTT</span>
                      <span className="text-bg_3 inline-block w-1\2">
                        {stock?.tradeDate} {stock?.lastTradedTime}
                      </span>
                    </div>
                    <div className="space-x-2 w-[250px] flex justify-between">
                      <span className="text-bg_2 inline-block ">
                        Upper Circuit
                      </span>
                      <span className="text-bg_3 inline-block w-1\2">
                        {marketInfo?.circuit?.upper.toFixed(2)}
                      </span>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="modal-footer p-3">
            <div className="positions-info-footer actions">
              <div className="flex flex-row justify-between">
                <div>
                  <GttOrderDialog tickerProps={ticker!}>
                    <Button
                      variant="outline"
                      className="rounded border-text_4 text-text_4 hover:bg-text_4 hover:text-white"
                    >
                      Create GTT
                    </Button>
                  </GttOrderDialog>
                </div>
                <div className="flex flex-row justify-between  w-[150px]">
                  <OrderDialog type="buy" tickerProps={ticker!}>
                    <Button
                      variant="outline"
                      className=" px-6 text-s rounded border-text_4 text-text_4 hover:bg-text_4 hover:text-white"
                    >
                      Buy
                    </Button>
                  </OrderDialog>

                  <OrderDialog type="sell" tickerProps={ticker!}>
                    <Button
                      variant="outline"
                      className=" px-6 text-s  rounded border-text_5 text-text_5 hover:bg-text_5 hover:text-white"
                    >
                      Sell
                    </Button>
                  </OrderDialog>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    );
}

export default Market;
