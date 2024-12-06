import api from "@/api/auth";
import { useAuth } from "@/components/AuthProvider";
import { UseStockInfo } from "@/components/StockInfoProvider";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";
import { Holdings } from "@/types";
import { formatNumber } from "@/utils";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";

export default function HoldingsOutlet() {
  const navigate = useNavigate();
  const { currentUser } = useAuth();
  const { stockInfoList } = UseStockInfo();
  const [holdings, setHoldings] = useState<Holdings[]>([]);

  useEffect(() => {
    async function fetchholdings() {
      try {
        const response = await api.get(`/holdings?clientId=${currentUser}`);
        setHoldings(response.data);
      } catch (error) {}
    }

    fetchholdings();
  }, [stockInfoList]);

  let holdingsLength = 0;
  if (holdings) {
    holdingsLength = holdings?.length;
  }

  const getLtp = (ticker: string): number | undefined => {
    if (!stockInfoList) return undefined;
    const stock = stockInfoList!.find((s) => s.ticker === ticker);
    return stock ? stock.lastTradedPrice : undefined;
  };
  const getPreviousClosing = (ticker: string): number | undefined => {
    if (!stockInfoList) return undefined;
    const stock = stockInfoList!.find((s) => s.ticker === ticker);
    return stock ? stock.previousClosing : undefined;
  };

  const calculateHoldingsValue = () => {
    return holdings.map((holding) => {
      const ltp = getLtp(holding.ticker);
      const previousClosing = getPreviousClosing(holding.ticker);
      const currentValue = ltp ? ltp * holding.currentUnits : 0;
      const profitLoss = ltp
        ? currentValue - holding.averageCost * holding.currentUnits
        : 0;
      const netChange = ltp ? ((ltp - holding.averageCost) * 100) / ltp : 0;
      const dayChange =
        ltp && previousClosing ? ((ltp - previousClosing) * 100) / ltp : 0;

      return {
        ...holding,
        ltp,
        currentValue,
        profitLoss,
        netChange,
        dayChange,
      };
    });
  };

  const holdingsValue = calculateHoldingsValue();
  return (
    <div className="px-5">
      <div className="my-3">
        <div className="py-2">
          <span className="text-[18px] text-bg_3 mx-2">
            Holdings ({holdingsLength})
          </span>
        </div>

        <div className="stats-row flex gap-28 py-3 mx-2 border-t-2 border-t-bg_1">
          <div className="three columns">
            <div className="label text-bg_2 text-sm">Total investment</div>{" "}
            <h4 className="text-xl font-semibold text-bg_3">
              10,000<span className="text-xs">.55</span>
            </h4>
          </div>{" "}
          <div className="three columns">
            <div className="label text-bg_2 text-sm">Current value</div>{" "}
            <h4 className="text-xl font-semibold text-bg_3">
              20,000<span className="text-xs">.71</span>
            </h4>
          </div>{" "}
          <div className="three columns">
            <div className="label text-bg_2 text-sm">Day's P&amp;L</div>{" "}
            <h4 className="text-xl font-semibold text-text_8">
              <span>
                35<span className="text-xs">.70</span>
              </span>{" "}
              <span className="text-xs">(+0.23%)</span>
            </h4>
          </div>{" "}
          <div className="three columns">
            <div className="label text-bg_2 text-sm">Total P&amp;L</div>{" "}
            <h4 className="text-xl font-semibold text-text_8">
              <span>
                2,530<span className="text-xs">.16</span>
              </span>{" "}
              <span className="text-xs">(+19.13%)</span>
            </h4>
          </div>
        </div>

        {holdingsLength > 0 && (
          <Table className="text-bg_3">
            <TableHeader className="text-bg_2 border-y-2 border-y-bg_1">
              <TableRow>
                <TableHead className="w-[180px]">Instrument</TableHead>
                <TableHead>Qty.</TableHead>
                <TableHead>Avg. cost</TableHead>
                <TableHead>LTP</TableHead>
                <TableHead>Cur. val</TableHead>
                <TableHead>P&L</TableHead>
                <TableHead>Net chg.</TableHead>
                <TableHead>Day chg.</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {holdingsValue.map((holding, index) => (
                <TableRow
                  key={index}
                  className="h-10 hover:bg-bg_0 border-y border-y-bg_1"
                >
                  <TableCell
                    onClick={() => navigate(`/app/market/${holding.ticker}`)}
                  >
                    {holding.ticker}
                  </TableCell>
                  <TableCell>{holding.currentUnits}</TableCell>
                  <TableCell>{holding.averageCost}</TableCell>
                  <TableCell>
                    {holding.ltp !== undefined ? holding.ltp : "N/A"}
                  </TableCell>
                  <TableCell>{formatNumber(holding.currentValue)}</TableCell>
                  <TableCell
                    className={`${
                      holding.profitLoss >= 0 ? "text-text_8" : "text-text_10"
                    }`}
                  >
                    {holding.profitLoss.toFixed(2)}
                  </TableCell>
                  <TableCell
                    className={`${
                      holding.netChange >= 0 ? "text-text_8" : "text-text_10"
                    }`}
                  >
                    {holding.netChange.toFixed(2)}%
                  </TableCell>
                  <TableCell
                    className={`${
                      holding.dayChange >= 0 ? "text-text_8" : "text-text_10"
                    }`}
                  >
                    {holding.dayChange.toFixed(2)}%
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        )}
      </div>
    </div>
  );
}
