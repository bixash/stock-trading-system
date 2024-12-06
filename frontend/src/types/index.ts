export type User = {
  username: string;
};

export type LoginSchema = {
  username: string;
  password: string;
};
export type SignupSchema = {
  username: string;
  password: string;

};
export type depth = {
  price: number;
  orders: number;
  quantity: number;
};

export type MarketDepth = {
  ticker: string;
  bids: depth[];
  offers: depth[];
};

export type Stock = {
  ticker: string;
  tradeDate:string;
  lastTradedTime: string;
  openingPrice: number;
  closingPrice: number;
  lastTradedPrice: number;
  maxPrice: number;
  minPrice: number;
  totalVolume: number;
  totalAmount: number;
  differenceRs: number;
  percentChange: number;
  lastTradedVolume: number;
  noOfTransactions: number;
  previousClosing: number;
};

export type Order = {
  orderId: number;
  brokerId: number;
  productType: string;
  transactionType: string;
  validity: string;
  clientId: string;
  price: number;
  orderQuantity: number;
  tradedQuantity: number;
  remainingQuantity: number;
  orderType: string;
  ticker: string;
  status: string;
  orderDate: string;
  orderTime: string;
};

export type Circuit = {
  ticker: string;
  upper: number;
  lower: number;
};

export type Holdings = {
  id: number;
  clientId: string;
  ticker: string;
  averageCost: number;
  freezeUnits: number;
  freeUnits: number;
  currentUnits: number;
  remarks: string;
};

export type FundsType = {
  id: number;
  clientId: string;
  availableMargin: number;
  usedMargin: number;
  totalMargin: number;
};

export type GTTOrder = {
  id: number;
  ticker: string;
  clientId: string;
  productType: string;
  transactionType: string;
  price: number;
  orderQuantity: number;
  orderType: string;
  triggerType: string;
  triggerPrice: number;
  status: string;
  orderDate: string;
  expiryDate: string;
};

export type MarketInfo = {
  marketDepth: MarketDepth;
  circuit: Circuit;
}