import { Button } from "@/components/ui/button";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { Input } from "@/components/ui/input";
import { RadioGroup, RadioGroupItem } from "@/components/ui/radio-group";
import { z } from "zod";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import {
  Dialog,
  DialogClose,
  DialogContent,
  DialogDescription,
  DialogFooter,
  DialogHeader,
  DialogTitle,
  DialogTrigger,
} from "@/components/ui/dialog";

("use client");

import { toast } from "sonner";

import { ReactNode, useEffect, useState } from "react";
import { Separator } from "@/components/ui/separator";
import { useAuth } from "./AuthProvider";
import { UseStockInfo } from "./StockInfoProvider";
import api from "@/api/auth";

interface DialogProps {
  type: string;
  tickerProps: string;
  children: ReactNode;
}

const schema = z.object({
  ticker: z.string(),
  clientId: z.string(),
  productType: z.string().default("cnc"),
  transactionType: z.string(),
  price: z.coerce.number().optional(),
  orderQuantity: z.coerce.number().min(1),
  orderType: z.string().default("limit"),
  validity: z.string().default("day"),
  triggerPrice: z.coerce.number().optional(),
  triggerType: z.string().optional(),
});

type formSchema = z.infer<typeof schema>;

export function OrderDialog({ type, tickerProps, children }: DialogProps) {
  // const { ticker } = useParams<{ ticker: string }>();
  const { currentUser } = useAuth();
  const { stockInfoList } = UseStockInfo();
  const [open, setOpen] = useState(false);
  const [ltp, setLtp] = useState<number | undefined>(0);
  const [transaction, setTransaction] = useState<boolean>();
  const form = useForm<formSchema>({
    resolver: zodResolver(schema),
    defaultValues: {
      ticker: tickerProps,
      orderQuantity: 1,
      transactionType: type,
      price: ltp,
      triggerPrice: 0,
      orderType: "limit",
      validity: "day",
      productType: "cnc",
      clientId: currentUser!,
    },
  });
  useEffect(() => {
    if (type === "buy") {
      setTransaction(false);
    } else if (type === "sell") {
      setTransaction(true);
    }
  }, [type]);

  useEffect(() => {
    if (stockInfoList) {
      for (let stock of stockInfoList) {
        if (stock.ticker === tickerProps) {
          setLtp(stock.lastTradedPrice);
        }
      }
    }
  }, [stockInfoList, tickerProps]);

  useEffect(() => {
    form.reset({
      ticker: tickerProps,
      orderQuantity: 1,
      transactionType: type,
      price: ltp,
      triggerPrice: 0,
      orderType: "limit",
      validity: "day",
      productType: "cnc",
      clientId: currentUser!,
    });
  }, [tickerProps, type, ltp, currentUser, form]);

  function onError(errors: any) {
    console.log(errors);
  }
  const onSubmit = async (orderData: formSchema) => {
    try {
      console.log(orderData);
      if (orderData.orderType == "market") {
        orderData.price = undefined;
      }
      const response = await api.post("/orders", orderData);
      if (response.data) {
        if (response.data.success) {
          toast.success(response.data.msg);
        } else {
          toast.error(response.data.msg);
        }
        setOpen(false);
      }
    } catch (error) {}
  };

  useEffect(() => {
    if (form.formState.isSubmitSuccessful) {
      form.reset();
    }
  }, [form]);

  const triggerType = form.watch("triggerType");
  const orderType = form.watch("orderType");
  const price = form.watch("price");
  const quantity = form.watch("orderQuantity");

  const amount = price! * quantity;

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <Form {...form}>
        <form
          onSubmit={form.handleSubmit(onSubmit, onError)}
          className="space-y-8"
        >
          <DialogTrigger asChild>{children}</DialogTrigger>
          <DialogContent className="w-[600px]">
            <DialogHeader
              className={`flex flex-row justify-between text-white p-4 ${
                transaction ? "bg-text_5" : "bg-text_4"
              }`}
            >
              <div>
                <DialogTitle className="text-[15px]">{tickerProps}</DialogTitle>
                <DialogDescription className="text-[12px]">
                  <span>Rs. {ltp}</span>
                </DialogDescription>
              </div>
            </DialogHeader>

            <div className="px-6 flex flex-col justify-evenly">
              <FormField
                control={form.control}
                name="productType"
                render={({ field }) => (
                  <FormItem className="space-y-3">
                    <FormControl>
                      <RadioGroup
                        onValueChange={field.onChange}
                        defaultValue="cnc"
                        className="flex flex-col space-y-1 "
                      >
                        <FormItem className="flex items-center space-x-3 space-y-0">
                          <FormControl>
                            <RadioGroupItem
                              className={
                                transaction ? "text-text_5" : "text-text_4"
                              }
                              value="cnc"
                            />
                          </FormControl>
                          <FormLabel className="font-normal">
                            Longterm <span className="text-bg_2"> CNC</span>
                          </FormLabel>
                        </FormItem>
                      </RadioGroup>
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />

              <div className="flex flex-row justify-between my-2">
                <FormField
                  control={form.control}
                  name="orderQuantity"
                  render={({ field }) => (
                    <FormItem className="flex flex-col text-bg_3">
                      <FormLabel className="bg-white w-fit ml-[13px] z-[1] mb-[-15px] px-1">
                        Qty.
                      </FormLabel>
                      <FormControl>
                        <Input
                          type="number"
                          className="pt-3 rounded  text-xl border-bg_2 w-[140px]"
                          {...field}
                        />
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />

                <FormField
                  control={form.control}
                  name="price"
                  render={({ field }) => (
                    <FormItem className="flex flex-col text-bg_3">
                      <FormLabel className="bg-white w-fit ml-[13px] z-[1] mb-[-15px] px-1">
                        Price
                      </FormLabel>
                      <FormControl>
                        {orderType == "market" ? (
                          <Input
                            disabled
                            type="number"
                            className="pt-3 rounded text-xl border-bg_2 w-[140px]"
                            {...field}
                          />
                        ) : (
                          <Input
                            type="number"
                            className="pt-3 rounded text-xl border-bg_2 w-[140px]"
                            {...field}
                          />
                        )}
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
                <FormField
                  control={form.control}
                  name="triggerPrice"
                  render={({ field }) => (
                    <FormItem className="flex flex-col text-bg_3">
                      <FormLabel className="bg-white w-fit ml-[13px] z-[1] mb-[-15px] px-1">
                        Trigger Price
                      </FormLabel>
                      <FormControl>
                        {triggerType ? (
                          <Input
                            type="number"
                            className="pt-3 rounded text-xl border-bg_2 w-[140px]"
                            {...field}
                          />
                        ) : (
                          <Input
                            disabled
                            type="number"
                            className="pt-3 rounded text-xl border-bg_2 w-[140px]"
                            {...field}
                          />
                        )}
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>
              <div className="w-full flex flex-row mb-4">
                <div className="ml-[210px]">
                  <FormField
                    control={form.control}
                    name="orderType"
                    render={({ field }) => (
                      <FormItem className="space-y-3 w-fit mx-auto">
                        <FormControl>
                          <RadioGroup
                            onValueChange={field.onChange}
                            defaultValue="limit"
                            className="flex flex-row justify-between w-[130px]"
                          >
                            <FormItem className="flex items-center space-x-2 space-y-0">
                              <FormControl>
                                <RadioGroupItem
                                  className={
                                    transaction ? "text-text_5" : "text-text_4"
                                  }
                                  value="market"
                                />
                              </FormControl>
                              <FormLabel className="font-normal">
                                Market
                              </FormLabel>
                            </FormItem>
                            <FormItem className="flex items-center space-x-2 space-y-0">
                              <FormControl>
                                <RadioGroupItem
                                  className={
                                    transaction ? "text-text_5" : "text-text_4"
                                  }
                                  value="limit"
                                />
                              </FormControl>
                              <FormLabel className="font-normal">
                                Limit
                              </FormLabel>
                            </FormItem>
                          </RadioGroup>
                        </FormControl>
                        <FormMessage />
                      </FormItem>
                    )}
                  />
                </div>

                <FormField
                  control={form.control}
                  name="triggerType"
                  render={({ field }) => (
                    <FormItem className="space-y-3 w-fit ml-[75px]">
                      <FormControl>
                        <RadioGroup
                          disabled
                          onValueChange={field.onChange}
                          className="flex flex-row justify-between w-[105px]"
                        >
                          <FormItem className="flex items-center space-x-2 space-y-0">
                            <FormControl>
                              <RadioGroupItem
                                className={
                                  transaction ? "text-text_5" : "text-text_4"
                                }
                                value="sl-m"
                              />
                            </FormControl>
                            <FormLabel className="font-normal">SL-M</FormLabel>
                          </FormItem>
                          <FormItem className="flex items-center space-x-2 space-y-0">
                            <FormControl>
                              <RadioGroupItem
                                className={
                                  transaction ? "text-text_5" : "text-text_4"
                                }
                                value="sl"
                              />
                            </FormControl>
                            <FormLabel className="font-normal">SL</FormLabel>
                          </FormItem>
                        </RadioGroup>
                      </FormControl>
                      <FormMessage />
                    </FormItem>
                  )}
                />
              </div>
              <Separator className="h-[0.5px] bg-[#efefef]" />
              <FormField
                control={form.control}
                name="validity"
                render={({ field }) => (
                  <FormItem className="space-y-3 mb-3">
                    <FormControl>
                      <RadioGroup
                        onValueChange={field.onChange}
                        defaultValue="day"
                        className="flex flex-col space-y-1"
                      >
                        <span className="text-sm inline-block mt-3">
                          Validity
                        </span>
                        <FormItem className="flex items-center space-x-3 space-y-0">
                          <FormControl>
                            <RadioGroupItem
                              className={
                                transaction ? "text-text_5" : "text-text_4"
                              }
                              value="day"
                            />
                          </FormControl>
                          <FormLabel className="font-normal">Day</FormLabel>
                        </FormItem>
                        <FormItem className="flex items-center space-x-3 space-y-0">
                          <FormControl>
                            <RadioGroupItem
                              disabled
                              className={
                                transaction ? "text-text_5" : "text-text_4"
                              }
                              value="minutes"
                            />
                          </FormControl>
                          <FormLabel className="font-normal">Minutes</FormLabel>
                        </FormItem>
                        <FormItem className="flex items-center space-x-3 space-y-0">
                          <FormControl>
                            <RadioGroupItem
                              disabled
                              className={
                                transaction ? "text-text_5" : "text-text_4"
                              }
                              value="immediate"
                            />
                          </FormControl>
                          <FormLabel className="font-normal">
                            Immediate
                          </FormLabel>
                        </FormItem>
                      </RadioGroup>
                    </FormControl>
                    <FormMessage />
                  </FormItem>
                )}
              />
              <Separator className="h-[0.5px] bg-[#efefef]" />
            </div>
            <DialogFooter className="-mt-4 py-3 px-6 bg-bg_0">
              <div className="flex gap-2 items-center">
                <span className=" inline-block text-sm text-bg_2">
                  Amount{" "}
                  <span className="text-bg_3">
                    {" "}
                    {amount && amount.toFixed(2)}
                  </span>
                </span>
                <span className=" inline-block text-sm text-bg_2">
                  Charges <span className="text-bg_3"> {(amount * 0.02).toFixed(2)}</span>
                </span>
              </div>

              <div className="w-[140px] flex justify-between ">
                <Button
                  type="submit"
                  onClick={form.handleSubmit(onSubmit, onError)}
                  className={`rounded border w-[70px] py-4 px-6 text-white hover:bg-white ${
                    transaction
                      ? " bg-text_5 hover:border-text_5 hover:text-text_5 "
                      : " bg-text_4 hover:border-text_4 hover:text-text_4 "
                  }`}
                >
                  {transaction ? "Sell" : "Buy"}
                </Button>
                <DialogClose asChild>
                  <Button
                    onClick={() => {
                      form.reset();
                    }}
                    className="rounded bg-bg_2 text-white hover:border-bg_2 hover:text-bg_2"
                    type="button"
                    variant="outline"
                  >
                    Close
                  </Button>
                </DialogClose>
              </div>
            </DialogFooter>
          </DialogContent>
        </form>
      </Form>
    </Dialog>
  );
}
