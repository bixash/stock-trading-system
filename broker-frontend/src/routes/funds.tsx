import api from "@/api/auth";
import { useAuth } from "@/components/AuthProvider";
import { Button } from "@/components/ui/button";
import { FundsType } from "@/types";
import { formatNumber } from "@/utils";
import { useEffect, useState } from "react";

function Funds() {
  const { currentUser } = useAuth();
  const [fundsInfo, setFundsInfo] = useState<FundsType>();

  useEffect(() => {
    async function fetchFundsInfo() {
      try {
        const response = await api.get(`/funds?clientId=${currentUser}`);
        setFundsInfo(response.data);
      } catch (error) {}
    }

    fetchFundsInfo();
  }, []);

  if (fundsInfo)
    return (
      <div className="py-2 px-5">
        <div className="py-3 flex flex-row items-center border-b-2 border-b-bg_1 pb-2 mb-2">
          <span className="text-bg_3 text-[18px]">Funds Summary</span>
          <div className="flex flex-row gap-6 items-center">
          <span className="inline-block text-bg_3 text-s ml-32">
            Instant, zero-cost fund transfers with NamastePay
          </span>
          <div className="flex flex-row gap-[3px]">
            <Button
              className="rounded text-[14px] font-normal border py-3 px-5 text-white hover:bg-white bg-text_8 hover:border-text_8 hover:text-text_8 "
              variant="outline"
            >
              Add funds
            </Button>
            <Button className="rounded text-[14px] font-normal border py-3 px-5  text-white hover:bg-white bg-text_4 hover:border-text_4 hover:text-text_4 ">
              Withdraw
            </Button>
          </div>
        </div>
        </div>
        <div className="w-fit flex flex-col gap-1 p-4 text-bg_3 border-2 border-bg_1">
          <div className="flex justify-between items-center w-80 hover:bg-bg_0 px-2 py-3">
            <span className="inline-block w-44 text-[14px]">Available margin</span>
            <span className="text-2xl text-text_4">
              {formatNumber(fundsInfo.availableMargin)}
            </span>
          </div>
          <div className="flex justify-between items-center w-80 hover:bg-bg_0 px-2 py-3">
            <span className="inline-block w-44 text-[14px]">Used margin</span>
            <span className="text-2xl">
              {formatNumber(fundsInfo.usedMargin)}
            </span>
          </div>
          <div className="flex justify-between items-center w-80 hover:bg-bg_0 px-2 py-3">
            <span className="inline-block w-44 text-[14px]">Total margin</span>
            <span className="text-2xl">
              {formatNumber(fundsInfo.totalMargin)}
            </span>
          </div>
        </div>
      </div>
    );
}
export default Funds;
