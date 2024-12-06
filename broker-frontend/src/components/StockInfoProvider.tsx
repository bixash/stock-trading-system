"use strict";

import { Stock } from "@/types";

import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

import {
  createContext,
  PropsWithChildren,
  useContext,
  useEffect,
  useRef,
  useState,
} from "react";
import axios from "axios";

type StockInfoContext = {
  stockInfoList: Stock[] | null | undefined;
  loading: boolean;
};

const StockInfoContext = createContext<StockInfoContext | undefined>(undefined);

type StockInfoProviderProps = PropsWithChildren;

export default function StockInfoProvider({
  children,
}: StockInfoProviderProps) {
  const [stockInfoList, setStockInfoList] = useState<Stock[] | null>();
  const [loading, setLoading] = useState(true);
  const stompClientRef = useRef<Client | null>(null);

  useEffect(() => {
    const fetchStockPrices = async () => {
      try {
        const response = await axios.get(
          `http://localhost:8081/exchange/stock-prices`
        );
        setStockInfoList(response.data);
        setLoading(false);
      } catch (error) {
        console.error("Error fetching stock prices:", error);
        setLoading(false);
      }
    };

    fetchStockPrices();

    const socket = new SockJS("http://localhost:8081/ws");
    const stompClient = new Client({
      webSocketFactory: () => socket,
      reconnectDelay: 5000,
      debug: (str) => {
        str;
        // console.log(str);
      },
      onConnect: () => {
        console.log("Connected to WebSocket");
        stompClient.subscribe("/topic/stock-prices", (response) => {
          console.log("Received message:", response.body);
          // setStockInfoList(JSON.parse(response.body));
          fetchStockPrices();
        });
      },
      onStompError: (frame) => {
        console.error("Broker reported error: " + frame.headers["message"]);
        console.error("Additional details: " + frame.body);
      },
    });

    stompClient.activate();
    stompClientRef.current = stompClient;
    return () => {
      stompClient.deactivate();
    };
  }, []);

  return (
    <StockInfoContext.Provider value={{ stockInfoList, loading }}>
      {children}
    </StockInfoContext.Provider>
  );
}

export function UseStockInfo() {
  const context = useContext(StockInfoContext);

  if (context === undefined) {
    throw new Error("useStockInfo must be used inside of a StockInfoProvider");
  }

  return context;
}
