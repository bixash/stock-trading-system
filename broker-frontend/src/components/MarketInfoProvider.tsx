"use strict";

import { MarketInfo } from "@/types";

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
import { useParams } from "react-router-dom";

type MarketInfoContext = {
  marketInfo: MarketInfo | null | undefined;
  ticker: string;
  loading: boolean;
};

const MarketInfoContext = createContext<MarketInfoContext | undefined>(
  undefined
);

type MarketInfoProviderProps = PropsWithChildren;

const EXCHANGE_URL = "http://localhost:8081/exchange";

export default function MarketInfoProvider({
  children,
}: MarketInfoProviderProps) {
  const { ticker } = useParams<{ ticker: string }>();
  const [loading, setLoading] = useState(true);
  const [marketInfo, setMarketInfo] = useState<MarketInfo | null>();
  const stompClientRef = useRef<Client | null>(null);

  useEffect(() => {
    async function fetchMarketInfo() {
      try {
        const response = await axios.get(
          `${EXCHANGE_URL}/market?ticker=${ticker}`
        );

        setMarketInfo(response.data);
        setLoading(false);
      } catch (error) {
        setLoading(false);
      }
    }
    if (ticker) {
      fetchMarketInfo();

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
          stompClient.subscribe(`/topic/market/${ticker}`, (response) => {
            console.log("Received message:", response.body);
            // setMarketInfo(JSON.parse(response.body));
            fetchMarketInfo();
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
    }
  }, [ticker]);

  return (
    <MarketInfoContext.Provider
      value={{ marketInfo, loading, ticker: ticker || "" }}
    >
      {children}
    </MarketInfoContext.Provider>
  );
}

export function UseMarketInfo() {
  const context = useContext(MarketInfoContext);

  if (context === undefined) {
    throw new Error("useMarketInfo must be used inside of a StockInfoProvider");
  }

  return context;
}
