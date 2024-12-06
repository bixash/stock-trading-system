import { depth, Stock } from "@/types";

const emptyOrder: depth = { price: 0, orders: 0, quantity: 0 };

export const calculateTotalQuantity = (orders: depth[]): number => {
  return orders.reduce((total, order) => total + order.quantity, 0);
};

export const getFixedOrders = (orders: depth[]) => {
  const fixedOrders = [...orders];
  while (fixedOrders.length < 5) {
    fixedOrders.push(emptyOrder);
  }
  return fixedOrders;
};

export const getStock = (List: Stock[], ticker: string) => {
  for (let stock of List) {
    if (stock.ticker == ticker) {
      return stock;
    }
  }
};

export function getExpiryDate(): string {
  let dt = new Date();
  let expiryDate = new Date(dt.setFullYear(dt.getFullYear() + 1));

  let year = expiryDate.getFullYear();
  let month = (expiryDate.getMonth() + 1).toString().padStart(2, "0"); // Months are zero-indexed
  let day = expiryDate.getDate().toString().padStart(2, "0");

  return `${year}-${month}-${day}`;
}

export function formatNumber(num: number) {
  const string = num.toFixed(2);
  const parts = string.split(".");
  parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
  return parts.join(".");
}

export function formatStockPrices(num: number) {
  const string = num.toFixed(2);
  const parts = string.split(".");
  parts[0] = parts[0].replace(/\B(?=(\d{2})+(?!\d))/g, ",");
  return parts.join(".");
}
